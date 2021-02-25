package br.com.zup.proposta.controller.request;

import br.com.zup.proposta.builder.AddressRequestDtoBuilder;
import br.com.zup.proposta.builder.ProposalRequestDtoBuilder;
import br.com.zup.proposta.model.Proposal;
import br.com.zup.proposta.repository.ProposalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
public class StatusRequestDtoTest {

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
        StatusRequestDto actualStatusRequestDto = new StatusRequestDto(new Proposal());

        assertNull(actualStatusRequestDto.getDocumento());
        assertNull(actualStatusRequestDto.getIdProposta());
        assertNull(actualStatusRequestDto.getNome());
    }

    @Test
    public void testOverloadedConstructor() {
        Proposal proposal = proposalRequest.toModel();
        proposalRepository.save(proposal);
        StatusRequestDto actualStatusRequestDto = new StatusRequestDto(proposal);

        assertEquals("116.238.120-54", actualStatusRequestDto.getDocumento());
        assertEquals(proposal.getId(), actualStatusRequestDto.getIdProposta());
        assertEquals("José Santos", actualStatusRequestDto.getNome());
    }
}

