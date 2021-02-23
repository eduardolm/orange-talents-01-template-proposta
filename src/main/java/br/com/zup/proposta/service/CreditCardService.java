package br.com.zup.proposta.service;

import br.com.zup.proposta.controller.request.BiometryImageRequestDto;
import br.com.zup.proposta.controller.request.CreditCardStatusRequestDto;
import br.com.zup.proposta.controller.response.CreditCardResponseDto;
import br.com.zup.proposta.controller.response.CreditCardStatusResponseDto;
import br.com.zup.proposta.controller.response.LegacyCreditCardResponseDto;
import br.com.zup.proposta.dto.CreditCardDetailsDto;
import br.com.zup.proposta.enums.CreditCardStatus;
import br.com.zup.proposta.enums.Status;
import br.com.zup.proposta.helper.ProposalHelper;
import br.com.zup.proposta.model.BiometryImage;
import br.com.zup.proposta.model.Blocked;
import br.com.zup.proposta.model.CreditCard;
import br.com.zup.proposta.model.Proposal;
import br.com.zup.proposta.repository.BlockedRepository;
import br.com.zup.proposta.repository.CreditCardRepository;
import br.com.zup.proposta.repository.ProposalRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CreditCardService {

    @Autowired
    private FetchCreditCard fetchCreditCard;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BiometricService biometricService;

    @Autowired
    private ProposalHelper proposalHelper;

    @Autowired
    private BlockedRepository blockedRepository;

    @Autowired
    private BlockCreditCard blockCreditCard;

    @Autowired
    TransactionTemplate transactionTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardService.class);

    @Scheduled(fixedDelayString = "${timed.run-operation}")
    public void fetchNewCreditCards() throws IOException {

        boolean pending = true;
        while (pending) {

            pending = transactionTemplate.execute((transactionStatus -> {

                List<CreditCardResponseDto> creditCardList = createCreditCards();
                if (creditCardList == null) return false;

                creditCardList.forEach(card -> {
                    if (proposalRepository.findById(card.getIdProposta()).isPresent()) {
                        card.setStatus(CreditCardStatus.ATIVO);
                        creditCardRepository.save(card.toModel(proposalRepository));
                    }

                });

                List<Proposal> eligibleProposals = proposalRepository.findTop5ByStatusOrderByIdAsc(Status.ELEGIVEL);
                if (eligibleProposals.isEmpty()) {
                    return false;
                }

                eligibleProposals.forEach((proposal -> {
                    LOGGER.info("Buscando por novos cartões...");

                    Optional<CreditCard> creditCard = creditCardRepository.findByProposal_Id(proposal.getId());
                    if (creditCard.isPresent()) {
                        proposalHelper.associateCardToProposal(proposal, creditCard.get());
                        proposal.updateCreditCard(creditCard.get());
                        proposal.setStatus(Status.ELEGIVEL_CARTAO_ASSOCIADO);
                        proposalRepository.save(proposal);

                        creditCardRepository.save(creditCard.get());
                        LOGGER.info("Cartão {} associado a Proposta {}.", creditCard.get().getId(), proposal.getId());
                    }
                }));
                return true;
            }));
        }
    }

    private List<CreditCardResponseDto> createCreditCards() {
        LOGGER.info("Chamando API para buscar novos cartões.....");

        try {
            String bodyStream = StreamUtils.copyToString(fetchCreditCard.findAll().body().asInputStream(),
                    StandardCharsets.UTF_8);

            if (!bodyStream.equals("[]")) {
                List<CreditCardResponseDto> creditCardList;
                creditCardList = Arrays.stream(mapper.readValue(bodyStream, CreditCardResponseDto[].class))
                        .collect(Collectors.toList());

                List<CreditCardResponseDto> newList = avoidCardsWithoutProposalId(creditCardList);
                if (newList.isEmpty()) return null;

                LOGGER.info("Retornando lista de cartões criados.... {}", newList);
                return newList;
            }
        }
        catch (JsonProcessingException | feign.RetryableException ex) {
            LOGGER.warn(ex.getMessage());
            return null;
        } catch (IOException e) {
            LOGGER.error("Erro ao processar os dados" + e.getMessage());
            return null;
        }
        LOGGER.info("Nenhum novo cartão localizado.");
        return null;
    }

    private List<CreditCardResponseDto> avoidCardsWithoutProposalId(List<CreditCardResponseDto> creditCardList) {
        List<CreditCardResponseDto> newList = new ArrayList<>();
        for (CreditCardResponseDto card : creditCardList) {
            if (card.getIdProposta() != null && !creditCardRepository.existsById(card.getId())) {
                newList.add(card);
            }
        }
        return newList;
    }

    public URI buildUri(String id, UriComponentsBuilder uriBuilder, BiometryImage uploadedBiometric) {
        return uriBuilder
                .path("/api/cartoes/{id}/images/{imageId}")
                .buildAndExpand(id, uploadedBiometric.getId()).toUri();
    }

    public CreditCard block(CreditCard creditCard, HttpServletRequest request) {
        if (checkIfCardIsAlreadyBlocked(creditCard)) {
            LOGGER.warn("Cartão já se encontra bloquado.");
            return null;
        }

        LOGGER.info("Bloqueando cartão...");
        String responsibleSystem = getRemoteInfo(request);

        Blocked blocked = new Blocked(responsibleSystem, true, creditCard);

        try {
            CreditCardStatusResponseDto newStatus = blockCreditCard.blockCard(creditCard.getId(),
                    new CreditCardStatusRequestDto(responsibleSystem));

            LegacyCreditCardResponseDto legacyResponse = fetchCreditCard.findById(creditCard.getId());

            if (newStatus.getResultado().equals("BLOQUEADO") && !legacyResponse.getBloqueios().isEmpty()) {
                creditCard.updateStatus(newStatus.getResultado());
                blocked.setId(legacyResponse.getBloqueios().get(0).getId());
                blockedRepository.save(blocked);
                LOGGER.info("Cartão bloqueado: " + new CreditCardDetailsDto(creditCard));
            }
        } catch (FeignException ex) {
            LOGGER.error("Erro ao bloquear o cartão: " + ex);
        }

        return creditCard;
    }

    public boolean checkIfCardIsAlreadyBlocked(CreditCard creditCard) {
        Blocked blocked = new Blocked(null, false, null);
        LocalDateTime latestDate = LocalDateTime.MIN;
        for (Blocked blockedCard : creditCard.getBlockedSet()) {
            if (blockedCard.isActive() && blockedCard.getBlockedAt().isAfter(latestDate)) blocked = blockedCard;
            latestDate = blockedCard.getBlockedAt();
        }
        return blocked.getCreditCard() != null;
    }

    public BiometryImage addBiometry(BiometryImageRequestDto requestDto, CreditCard creditCard) throws Exception {
        BiometryImage uploadedImage = biometricService.uploadImage(requestDto, creditCard);
        Assert.notNull(uploadedImage, "Biometria já cadastrada.");

        return uploadedImage;
    }

    private String getRemoteInfo(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String remoteAddr = request.getRemoteAddr();
        Map<String, String> result = new HashMap<>();
        result.put("User-Agent", userAgent);
        result.put("Remote_Addr", remoteAddr);

        return result.get("User-Agent") + " - " + result.get("Remote_Addr");
    }
}
