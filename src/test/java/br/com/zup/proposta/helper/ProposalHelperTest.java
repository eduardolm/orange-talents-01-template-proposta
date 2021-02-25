package br.com.zup.proposta.helper;

import br.com.zup.proposta.builder.AddressRequestDtoBuilder;
import br.com.zup.proposta.builder.CreditCardResponseDtoBuilder;
import br.com.zup.proposta.builder.ProposalRequestDtoBuilder;
import br.com.zup.proposta.controller.request.AddressRequestDto;
import br.com.zup.proposta.controller.request.ProposalRequestDto;
import br.com.zup.proposta.controller.response.DueDateResponseDto;
import br.com.zup.proposta.model.CreditCard;
import br.com.zup.proposta.model.Proposal;
import br.com.zup.proposta.repository.ProposalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
public class ProposalHelperTest {

    @Autowired
    private ProposalRepository proposalRepository;

    @Autowired
    private ProposalHelper proposalHelper;

    private AddressRequestDto address;
    private ProposalRequestDto proposalRequest;

    @BeforeEach
    public void setup() {
        // Create address
        AddressRequestDto address = new AddressRequestDtoBuilder()
                .withStreet("Rua da Bica")
                .withZip("77823-300")
                .withNumber("254")
                .withComplement("Casa")
                .build();

        this.address = address;


        // Create proposal
        ProposalRequestDto proposalRequestDto = new ProposalRequestDtoBuilder()
                .withDocument("116.238.120-54")
                .withName("José Santos")
                .withEmail("jose@email.com")
                .withIncome(new BigDecimal("2500"))
                .withAddress(this.address)
                .build();

        this.proposalRequest = proposalRequestDto;
    }

    @AfterEach
    public void rollbackDatabase() {
        proposalRepository.deleteAll();
    }

    @Test
    public void shouldReturnNull() {

        assertNull(proposalHelper.checkExistsProposal(this.proposalRequest));
    }

    @Test
    public void shouldReturn422IfProposalAlreadyExists() {
        Proposal proposal = this.proposalRequest.toModel();
        proposalRepository.save(proposal);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, proposalHelper.checkExistsProposal(this.proposalRequest).getStatusCode());
    }

    @Test
    public void shouldAssociateCardToProposal() throws IOException {
        ProposalRequestDto proposalRequestDto2 = new ProposalRequestDtoBuilder()
                .withDocument("853.673.630-59")
                .withName("José da Silva")
                .withEmail("jose@email.com")
                .withIncome(new BigDecimal("2500"))
                .withAddress(this.address)
                .build();

        Proposal proposal = proposalRequestDto2.toModel();
        proposalRepository.save(proposal);

        CreditCard creditCard = new CreditCardResponseDtoBuilder()
                .withId("8619-8673-3201-9953")
                .withEmitidoEm(LocalDateTime.now())
                .withTitular("José da Silva")
                .withBloqueios(null)
                .withAvisos(null)
                .withCarteiras(null)
                .withParcelas(null)
                .withLimite(4135)
                .withRenegociacao(null)
                .withVencimento(new DueDateResponseDto("b2a77d4f-1e15-4287-b66c-552172705a27", 30, LocalDateTime.now()))
                .withIdProposta(proposal.getId())
                .build().toModel(proposalRepository);

        proposalHelper.associateCardToProposal(proposal, creditCard);

        assertEquals(proposal.getId(), creditCard.getProposal().getId());
    }
}
