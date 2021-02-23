package br.com.zup.proposta.dto;

import br.com.zup.proposta.controller.request.AddressRequestDto;
import br.com.zup.proposta.controller.request.ProposalRequestDto;
import br.com.zup.proposta.enums.Status;
import br.com.zup.proposta.model.Address;
import br.com.zup.proposta.model.CreditCard;
import br.com.zup.proposta.model.Proposal;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProposalDtoTest {
    @Test
    public void testConstructor() {
        ProposalDto actualProposalDto = new ProposalDto(new Proposal());

        assertNull(actualProposalDto.getIncome());
        assertNull(actualProposalDto.getId());
        assertNull(actualProposalDto.getEmail());
        assertNull(actualProposalDto.getAddress());
        assertNull(actualProposalDto.getStatus());
        assertNull(actualProposalDto.getName());
        assertNull(actualProposalDto.getDocument());
    }

    @Test
    public void testConstructor2() {
        Proposal proposal = new Proposal();
        proposal.updateStatus("SEM_RESTRICAO");

        ProposalDto actualProposalDto = new ProposalDto(proposal);

        assertNull(actualProposalDto.getIncome());
        assertNull(actualProposalDto.getId());
        assertNull(actualProposalDto.getEmail());
        assertNull(actualProposalDto.getAddress());
        assertEquals(Status.ELEGIVEL, actualProposalDto.getStatus());
        assertNull(actualProposalDto.getName());
        assertNull(actualProposalDto.getDocument());
    }

    @Test
    public void testConstructor3() {
        ProposalDto actualProposalDto = new ProposalDto(new Proposal());

        assertNull(actualProposalDto.getIncome());
        assertNull(actualProposalDto.getId());
        assertNull(actualProposalDto.getEmail());
        assertNull(actualProposalDto.getAddress());
        assertNull(actualProposalDto.getStatus());
        assertNull(actualProposalDto.getName());
        assertNull(actualProposalDto.getDocument());
    }

    @Test
    public void testConstructor4() {
        BigDecimal income = BigDecimal.valueOf(42L);
        Address address = new Address();

        ProposalDto actualProposalDto = new ProposalDto(
                new Proposal("Document", "Name", "jane.doe@example.org", income, address, Status.ELEGIVEL));

        assertNull(actualProposalDto.getId());
        assertEquals("Document", actualProposalDto.getDocument());
        assertEquals(Status.ELEGIVEL, actualProposalDto.getStatus());
        assertEquals("jane.doe@example.org", actualProposalDto.getEmail());
        assertSame(address, actualProposalDto.getAddress());
        assertEquals("Name", actualProposalDto.getName());
        assertEquals("42", actualProposalDto.getIncome().toString());
    }

    @Test
    public void testConstructor5() {
        CreditCard creditCard = new CreditCard();
        creditCard.addImage("0", "foo.txt");

        Proposal proposal = new Proposal();
        proposal.updateCreditCard(creditCard);

        ProposalDto actualProposalDto = new ProposalDto(proposal);

        assertNull(actualProposalDto.getIncome());
        assertNull(actualProposalDto.getId());
        assertNull(actualProposalDto.getEmail());
        assertNull(actualProposalDto.getAddress());
        assertNull(actualProposalDto.getStatus());
        assertNull(actualProposalDto.getName());
        assertNull(actualProposalDto.getDocument());
    }

    @Test
    public void testGetter() {
        AddressRequestDto address = new AddressRequestDto("Rua Arthur de Azevedo", "05470-050",
                "235", "Casa");
        ProposalRequestDto proposalRequestDto = new ProposalRequestDto("865.829.190-16",
                "Jorge Lacerda", "jorge@email.com", BigDecimal.TEN, address);
        Proposal proposal = proposalRequestDto.toModel();
        proposal.updateStatus("SEM_RESTRICAO");

        assertEquals(BigDecimal.TEN, proposal.getIncome());
        assertEquals("jorge@email.com", proposal.getEmail());
        assertEquals(address.toModel(), proposal.getAddress());
        assertEquals(Status.ELEGIVEL, proposal.getStatus());
        assertEquals("Jorge Lacerda", proposal.getName());
        assertEquals("865.829.190-16", proposal.getDocument());
    }

    @Test
    public void shouldThrowResponseStatusExceptionWhenOtherValue() {
        Proposal proposal = new Proposal();

        assertThrows(ResponseStatusException.class, () -> proposal.updateStatus("Request"));
    }
}

