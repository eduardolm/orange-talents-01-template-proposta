package br.com.zup.proposta.helper;

import br.com.zup.proposta.controller.request.ProposalRequestDto;
import br.com.zup.proposta.controller.request.StatusRequestDto;
import br.com.zup.proposta.controller.response.StatusResponseDto;
import br.com.zup.proposta.dto.CreditCardDetailsDto;
import br.com.zup.proposta.dto.ProposalDto;
import br.com.zup.proposta.enums.Status;
import br.com.zup.proposta.model.CreditCard;
import br.com.zup.proposta.model.Proposal;
import br.com.zup.proposta.repository.CreditCardRepository;
import br.com.zup.proposta.repository.ProposalRepository;
import br.com.zup.proposta.service.AnalysisClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ProposalHelper {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private AnalysisClient analysisClient;

    @Autowired
    private ObjectMapper mapper;

    private StatusResponseDto response;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProposalHelper.class);

    public void checkCustomerCredit(Proposal proposal) throws JsonProcessingException {
        try {

            LOGGER.info("Iniciando análise de crédito do cliente...");
            response = analysisClient.checkCustomer(new StatusRequestDto(proposal));
        }
        catch (FeignException.UnprocessableEntity ex) {

            LOGGER.warn("Cliente possui restrições: " + ex.getLocalizedMessage());
            response = mapper.readValue(ex.contentUTF8(), StatusResponseDto.class);
        }
        catch (Exception e) {
            LOGGER.error("Erro de conexão: " + e.getLocalizedMessage());
            return;
        }
        finally {

            LOGGER.info("Retorno de análise de crédito do cliente {}: {}", proposal.getName(),  response.getResultadoSolicitacao());
            proposal.updateStatus(response.getResultadoSolicitacao());
        }
    }

    public ResponseEntity<HashMap<String, Object>> checkExistsProposal(ProposalRequestDto proposalRequestDto) {
        if (proposalRepository.existsByDocument(proposalRequestDto.getDocument())) {

            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "Documento já cadastrado");
            response.put("field", proposalRequestDto.getDocument());
            LOGGER.warn("Já existe proposta para este documento: " + response);

            return ResponseEntity.unprocessableEntity().body(response);
        }
        return null;
    }

    public void associateCardToProposal(Proposal proposal, CreditCard creditCard) {

        LOGGER.info("Associando cartão à proposta....");
        if (proposal.getStatus() == Status.ELEGIVEL) {
            creditCardRepository.save(creditCard);
            LOGGER.info("O cartão {} foi associado à proposta {}", new CreditCardDetailsDto(creditCard),
                    new ProposalDto(proposal));
        }
    }
}
