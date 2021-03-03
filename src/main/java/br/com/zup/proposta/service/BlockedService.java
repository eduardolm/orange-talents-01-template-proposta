package br.com.zup.proposta.service;

import br.com.zup.proposta.controller.request.CreditCardStatusRequestDto;
import br.com.zup.proposta.controller.response.CreditCardStatusResponseDto;
import br.com.zup.proposta.controller.response.LegacyCreditCardResponseDto;
import br.com.zup.proposta.dto.CreditCardDetailsDto;
import br.com.zup.proposta.helper.HttpHelper;
import br.com.zup.proposta.model.Blocked;
import br.com.zup.proposta.model.CreditCard;
import br.com.zup.proposta.repository.BlockedRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class BlockedService {

    @Autowired
    private FetchCreditCard fetchCreditCard;

    @Autowired
    private BlockedRepository blockedRepository;

    @Autowired
    private HttpHelper httpHelper;

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardService.class);

    public CreditCard block(CreditCard creditCard, HttpServletRequest request) {

        if (creditCard.checkIfCardIsAlreadyBlocked()) {
            LOGGER.warn("Cartão já se encontra bloqueado.");
            return null;
        }

        LOGGER.info("Bloqueando cartão...");
        List<String> remoteProperties = httpHelper.getRemoteInfo(request);
        String responsibleSystem = remoteProperties.get(0) + "- " + remoteProperties.get(1);

        Blocked blocked = new Blocked(responsibleSystem, true, creditCard);

        try {
            CreditCardStatusResponseDto newStatus = fetchCreditCard.blockCard(creditCard.getId(),
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
}
