package br.com.zup.proposta.controller.response;

import br.com.zup.proposta.builder.AddressRequestDtoBuilder;
import br.com.zup.proposta.builder.CreditCardResponseDtoBuilder;
import br.com.zup.proposta.builder.ProposalRequestDtoBuilder;
import br.com.zup.proposta.controller.request.AddressRequestDto;
import br.com.zup.proposta.controller.request.ProposalRequestDto;
import br.com.zup.proposta.enums.CreditCardStatus;
import br.com.zup.proposta.model.Proposal;
import br.com.zup.proposta.repository.ProposalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class CreditCardResponseDtoTest {

    @MockBean
    private ProposalRepository proposalRepository;

    private AddressRequestDto address;
    private ProposalRequestDto proposalRequest;

    @Test
    public void testSetStatus() {
        CreditCardResponseDto creditCardResponseDto = new CreditCardResponseDto();

        creditCardResponseDto.setStatus(CreditCardStatus.ATIVO);

        assertEquals(CreditCardStatus.ATIVO, creditCardResponseDto.getStatus());
    }

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
    public void testOverloadedConstructor() {
        Proposal proposal = proposalRequest.toModel();
        proposalRepository.save(proposal);

        CreditCardResponseDto creditCardResponseDto = new CreditCardResponseDtoBuilder()
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
                .build();

        assertEquals("8619-8673-3201-9953", creditCardResponseDto.getId());
        assertEquals("José da Silva", creditCardResponseDto.getTitular());
        assertEquals("b2a77d4f-1e15-4287-b66c-552172705a27", creditCardResponseDto.getVencimento().getId());
    }

    @Test
    public void testToString() {
        assertEquals(
                "CreditCardResponseDto{id='null', emitidoEm=null, titular='null', bloqueios=null, avisos=null,"
                        + " carteiras=null, parcelas=null, limite=0, renegociacao=null, vencimento=null, " +
                        "idProposta=null, status=null}",
                (new CreditCardResponseDto()).toString());
        assertEquals("CreditCardResponseDto{id='null', emitidoEm=null, titular='null', bloqueios=null, avisos=null,"
                + " carteiras=null, parcelas=null, limite=0, renegociacao=null, vencimento=null, idProposta=null,"
                + " status=null}", (new CreditCardResponseDto()).toString());
    }
}

