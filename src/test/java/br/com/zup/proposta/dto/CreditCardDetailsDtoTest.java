package br.com.zup.proposta.dto;

import br.com.zup.proposta.enums.CreditCardStatus;
import br.com.zup.proposta.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class CreditCardDetailsDtoTest {

    @Test
    public void testConstructor() {
        LocalDateTime createdAt = LocalDateTime.of(1, 1, 1, 1, 1);
        HashSet<Blocked> blockedSet = new HashSet<>();
        HashSet<TravelNote> travelNoteSet = new HashSet<>();
        HashSet<Wallet> walletSet = new HashSet<>();
        HashSet<Installment> installmentSet = new HashSet<>();
        Renegotiation renegotiation = new Renegotiation();
        DueDate dueDate = new DueDate();

        CreditCardDetailsDto actualCreditCardDetailsDto = new CreditCardDetailsDto(new CreditCard("42", createdAt, "Name",
                blockedSet, travelNoteSet, walletSet, installmentSet, 1, renegotiation, dueDate, new Proposal(), CreditCardStatus.ATIVO));

        assertSame(walletSet, actualCreditCardDetailsDto.getWallets());
        assertSame(renegotiation, actualCreditCardDetailsDto.getRenegotiation());
        assertEquals("42", actualCreditCardDetailsDto.getId());
        assertEquals(1, actualCreditCardDetailsDto.getCreditLimit());
        assertSame(installmentSet, actualCreditCardDetailsDto.getInstallments());
        assertEquals("Name", actualCreditCardDetailsDto.getName());
        ProposalDto proposal = actualCreditCardDetailsDto.getProposal();
        assertNull(proposal.getEmail());
        assertNull(proposal.getAddress());
        assertNull(proposal.getStatus());
        assertEquals("0", proposal.getCreditCard());
        assertNull(proposal.getName());
        assertNull(proposal.getDocument());
        assertNull(proposal.getIncome());
        DueDateDetailsDto dueDate1 = actualCreditCardDetailsDto.getDueDate();
        assertNull(dueDate1.getDataDeCriacao());
        assertEquals(0, dueDate1.getDia());
        assertNull(dueDate1.getId());
        assertNull(proposal.getId());
    }

    @Test
    public void testConstructor2() {
        LocalDateTime createdAt = LocalDateTime.of(1, 1, 1, 1, 1);
        HashSet<Blocked> blockedSet = new HashSet<>();
        HashSet<TravelNote> travelNoteSet = new HashSet<>();
        HashSet<Wallet> walletSet = new HashSet<>();
        HashSet<Installment> installmentSet = new HashSet<>();
        Renegotiation renegotiation = new Renegotiation();
        DueDate dueDate = new DueDate();

        CreditCardDetailsDto actualCreditCardDetailsDto = new CreditCardDetailsDto(new CreditCard("0", createdAt, "Name",
                blockedSet, travelNoteSet, walletSet, installmentSet, 1, renegotiation, dueDate, new Proposal(), CreditCardStatus.ATIVO));

        assertSame(walletSet, actualCreditCardDetailsDto.getWallets());
        assertSame(renegotiation, actualCreditCardDetailsDto.getRenegotiation());
        assertEquals("0", actualCreditCardDetailsDto.getId());
        assertEquals(1, actualCreditCardDetailsDto.getCreditLimit());
        assertSame(installmentSet, actualCreditCardDetailsDto.getInstallments());
        assertEquals("Name", actualCreditCardDetailsDto.getName());
        ProposalDto proposal = actualCreditCardDetailsDto.getProposal();
        assertNull(proposal.getEmail());
        assertNull(proposal.getAddress());
        assertNull(proposal.getStatus());
        assertEquals("0", proposal.getCreditCard());
        assertNull(proposal.getName());
        assertNull(proposal.getDocument());
        assertNull(proposal.getIncome());
        DueDateDetailsDto dueDate1 = actualCreditCardDetailsDto.getDueDate();
        assertNull(dueDate1.getDataDeCriacao());
        assertEquals(0, dueDate1.getDia());
        assertNull(dueDate1.getId());
        assertNull(proposal.getId());
    }

    @Test
    public void testConstructor3() {
        HashSet<Blocked> blockedSet = new HashSet<>();
        blockedSet.add(new Blocked());
        LocalDateTime createdAt = LocalDateTime.of(1, 1, 1, 1, 1);
        HashSet<TravelNote> travelNoteSet = new HashSet<>();
        HashSet<Wallet> walletSet = new HashSet<>();
        HashSet<Installment> installmentSet = new HashSet<>();
        Renegotiation renegotiation = new Renegotiation();
        DueDate dueDate = new DueDate();

        CreditCardDetailsDto actualCreditCardDetailsDto = new CreditCardDetailsDto(new CreditCard("42", createdAt, "Name",
                blockedSet, travelNoteSet, walletSet, installmentSet, 1, renegotiation, dueDate, new Proposal(), CreditCardStatus.ATIVO));

        assertSame(walletSet, actualCreditCardDetailsDto.getWallets());
        assertSame(renegotiation, actualCreditCardDetailsDto.getRenegotiation());
        assertEquals("42", actualCreditCardDetailsDto.getId());
        assertEquals(1, actualCreditCardDetailsDto.getCreditLimit());
        assertSame(installmentSet, actualCreditCardDetailsDto.getInstallments());
        assertEquals("Name", actualCreditCardDetailsDto.getName());
        assertEquals(travelNoteSet, actualCreditCardDetailsDto.getNotes());
        ProposalDto proposal = actualCreditCardDetailsDto.getProposal();
        assertNull(proposal.getEmail());
        assertNull(proposal.getAddress());
        assertNull(proposal.getStatus());
        assertEquals("0", proposal.getCreditCard());
        assertNull(proposal.getName());
        assertNull(proposal.getDocument());
        assertNull(proposal.getIncome());
        DueDateDetailsDto dueDate1 = actualCreditCardDetailsDto.getDueDate();
        assertNull(dueDate1.getDataDeCriacao());
        assertEquals(0, dueDate1.getDia());
        assertNull(dueDate1.getId());
        assertNull(proposal.getId());
    }
}

