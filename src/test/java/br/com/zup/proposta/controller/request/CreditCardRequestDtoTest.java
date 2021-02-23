package br.com.zup.proposta.controller.request;

import br.com.zup.proposta.builder.AddressRequestDtoBuilder;
import br.com.zup.proposta.builder.ProposalRequestDtoBuilder;
import br.com.zup.proposta.model.Proposal;
import br.com.zup.proposta.repository.ProposalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CreditCardRequestDtoTest {

    @MockBean
    private ProposalRepository proposalRepository;

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
    public void testConstructor() {
        CreditCardRequestDto actualCreditCardRequestDto = new CreditCardRequestDto(new Proposal());

        assertNull(actualCreditCardRequestDto.getDocumento());
        assertNull(actualCreditCardRequestDto.getIdProposta());
        assertNull(actualCreditCardRequestDto.getNome());
    }

    @Test
    public void testOverloadedConstructor() {
        Proposal proposal = this.proposalRequest.toModel();
        proposalRepository.save(proposal);
        CreditCardRequestDto actualCreditCardRequestDto = new CreditCardRequestDto(proposal);

        assertEquals("116.238.120-54", actualCreditCardRequestDto.getDocumento());
        assertEquals(proposal.getId(), actualCreditCardRequestDto.getIdProposta());
        assertEquals("José Santos", actualCreditCardRequestDto.getNome());
    }
}

