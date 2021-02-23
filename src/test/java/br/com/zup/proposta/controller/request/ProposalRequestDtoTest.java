package br.com.zup.proposta.controller.request;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ProposalRequestDtoTest {

    @Test
    public void testConstructor() {
        AddressRequestDto address = new AddressRequestDto("Rua Arthur de Azevedo", "05470-050",
                "235", "Casa");
        ProposalRequestDto proposalRequestDto = new ProposalRequestDto("865.829.190-16",
                "Jorge Lacerda", "jorge@email.com", BigDecimal.TEN, address);

        assertEquals("865.829.190-16", proposalRequestDto.getDocument());
        assertEquals("Jorge Lacerda", proposalRequestDto.getName());
        assertEquals("jorge@email.com", proposalRequestDto.getEmail());
        assertEquals(BigDecimal.TEN, proposalRequestDto.getIncome());
        assertEquals("Rua Arthur de Azevedo", proposalRequestDto.getAddress().getStreet());
        assertEquals("05470-050", proposalRequestDto.getAddress().getZip());
        assertEquals("235", proposalRequestDto.getAddress().getNumber());
        assertEquals("Casa", proposalRequestDto.getAddress().getComplement());
    }

    @Test
    public void testEquals() {
        AddressRequestDto address = new AddressRequestDto("Rua Arthur de Azevedo", "05470-050",
                "235", "Casa");
        ProposalRequestDto proposalRequestDto = new ProposalRequestDto("865.829.190-16",
                "Jorge Lacerda", "jorge@email.com", BigDecimal.TEN, address);
        ProposalRequestDto proposalRequestDto2 = new ProposalRequestDto("865.829.190-16",
                "Jorge Lacerda", "jorge@email.com", BigDecimal.TEN, address);
        ProposalRequestDto proposalRequestDto3 = new ProposalRequestDto();

        assertEquals(proposalRequestDto, proposalRequestDto2);
        assertNotEquals(proposalRequestDto, proposalRequestDto3);
    }

    @Test
    public void testHashCode() {
        AddressRequestDto address = new AddressRequestDto("Rua Arthur de Azevedo", "05470-050",
                "235", "Casa");
        ProposalRequestDto proposalRequestDto = new ProposalRequestDto("865.829.190-16",
                "Jorge Lacerda", "jorge@email.com", BigDecimal.TEN, address);
        ProposalRequestDto proposalRequestDto2 = new ProposalRequestDto("865.829.190-16",
                "Jorge Lacerda", "jorge@email.com", BigDecimal.TEN, address);

        assertEquals(proposalRequestDto.hashCode(), proposalRequestDto2.hashCode());
    }
}
