package br.com.zup.proposta.model;

import br.com.zup.proposta.enums.CreditCardStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CreditCardTest {
    @Test
    public void testConstructor() {
        CreditCard actualCreditCard = new CreditCard();

        Set<Wallet> wallets = actualCreditCard.getWallets();
        assertTrue(wallets instanceof java.util.HashSet);
        Set<Installment> installments = actualCreditCard.getInstallments();
        assertTrue(installments instanceof java.util.HashSet);
        assertTrue(wallets.isEmpty());
        assertTrue(installments.isEmpty());
        assertNull(actualCreditCard.getId());
        Set<TravelNote> travelNotes = actualCreditCard.getNotes();
        assertTrue(travelNotes instanceof java.util.HashSet);
        assertNull(actualCreditCard.getProposal());
        assertTrue(travelNotes.isEmpty());
        assertNull(actualCreditCard.getDueDate());
        Set<Blocked> blockedSet = actualCreditCard.getBlockedSet();
        assertTrue(blockedSet instanceof java.util.HashSet);
        assertNull(actualCreditCard.getName());
        assertTrue(blockedSet.isEmpty());
        assertEquals(0, actualCreditCard.getCreditLimit());
        assertNull(actualCreditCard.getCreatedAt());
        assertNull(actualCreditCard.getRenegotiation());
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
        Proposal proposal = new Proposal();

        CreditCard actualCreditCard = new CreditCard("42", createdAt, "Name", blockedSet, travelNoteSet, walletSet,
                installmentSet, 1, renegotiation, dueDate, proposal, CreditCardStatus.ATIVO);

        assertSame(walletSet, actualCreditCard.getWallets());
        assertSame(installmentSet, actualCreditCard.getInstallments());
        assertEquals("42", actualCreditCard.getId());
        assertSame(travelNoteSet, actualCreditCard.getNotes());
        assertSame(proposal, actualCreditCard.getProposal());
        assertSame(renegotiation, actualCreditCard.getRenegotiation());
        assertEquals(1, actualCreditCard.getCreditLimit());
        assertSame(dueDate, actualCreditCard.getDueDate());
        assertEquals("Name", actualCreditCard.getName());
        assertSame(blockedSet, actualCreditCard.getBlockedSet());
    }

    @Test
    public void testConstructor3() {
        CreditCard actualCreditCard = new CreditCard();

        Set<Wallet> wallets = actualCreditCard.getWallets();
        assertTrue(wallets instanceof java.util.HashSet);
        assertNull(actualCreditCard.getStatus());
        assertTrue(wallets.isEmpty());
        assertNull(actualCreditCard.getDueDate());
        Set<Blocked> blockedSet = actualCreditCard.getBlockedSet();
        assertTrue(blockedSet instanceof java.util.HashSet);
        assertEquals(0, actualCreditCard.getCreditLimit());
        assertTrue(blockedSet.isEmpty());
        assertNull(actualCreditCard.getCreatedAt());
        Set<Installment> installments = actualCreditCard.getInstallments();
        assertTrue(installments instanceof java.util.HashSet);
        assertNull(actualCreditCard.getId());
        assertTrue(installments.isEmpty());
        Set<TravelNote> notes = actualCreditCard.getNotes();
        assertTrue(notes instanceof java.util.HashSet);
        assertNull(actualCreditCard.getProposal());
        assertTrue(notes.isEmpty());
        Set<BiometryImage> images = actualCreditCard.getImages();
        assertTrue(images instanceof java.util.HashSet);
        assertNull(actualCreditCard.getName());
        assertTrue(images.isEmpty());
        assertNull(actualCreditCard.getRenegotiation());
    }

    @Test
    public void testConstructor4() {
        LocalDateTime createdAt = LocalDateTime.of(1, 1, 1, 1, 1);
        HashSet<Blocked> blockedSet = new HashSet<>();
        HashSet<TravelNote> travelNoteSet = new HashSet<>();
        HashSet<Wallet> walletSet = new HashSet<>();
        HashSet<Installment> installmentSet = new HashSet<>();
        Renegotiation renegotiation = new Renegotiation();
        DueDate dueDate = new DueDate();
        Proposal proposal = new Proposal();

        CreditCard actualCreditCard = new CreditCard("42", createdAt, "Name", blockedSet, travelNoteSet, walletSet,
                installmentSet, 1, renegotiation, dueDate, proposal, CreditCardStatus.ATIVO);

        assertSame(walletSet, actualCreditCard.getWallets());
        assertEquals(CreditCardStatus.ATIVO, actualCreditCard.getStatus());
        assertSame(dueDate, actualCreditCard.getDueDate());
        assertSame(blockedSet, actualCreditCard.getBlockedSet());
        assertSame(renegotiation, actualCreditCard.getRenegotiation());
        assertEquals("42", actualCreditCard.getId());
        assertSame(installmentSet, actualCreditCard.getInstallments());
        assertEquals(1, actualCreditCard.getCreditLimit());
        assertEquals("Name", actualCreditCard.getName());
        assertSame(proposal, actualCreditCard.getProposal());
        assertSame(travelNoteSet, actualCreditCard.getNotes());
    }

    @Test
    public void testSetStatus() {
        CreditCard creditCard = new CreditCard();

        creditCard.setStatus(CreditCardStatus.ATIVO);

        assertEquals(CreditCardStatus.ATIVO, creditCard.getStatus());
    }

    @Test
    public void testEquals() {
        LocalDateTime createdAt = LocalDateTime.of(1, 1, 1, 1, 1);
        HashSet<Blocked> blockedSet = new HashSet<>();
        HashSet<TravelNote> travelNotes = new HashSet<>();
        HashSet<Wallet> wallets = new HashSet<>();
        HashSet<Installment> installments = new HashSet<>();
        Renegotiation renegotiation = new Renegotiation();
        DueDate dueDate = new DueDate();
        CreditCard creditCard = new CreditCard("42", createdAt, "BLOQUEADO", blockedSet, travelNotes, wallets, installments,
                1, renegotiation, dueDate, new Proposal(), CreditCardStatus.ATIVO);
        LocalDateTime createdAt1 = LocalDateTime.of(1, 1, 1, 1, 1);
        HashSet<Blocked> blockedSet1 = new HashSet<>();
        HashSet<TravelNote> travelNotes1 = new HashSet<>();
        HashSet<Wallet> wallets1 = new HashSet<>();
        HashSet<Installment> installments1 = new HashSet<>();
        Renegotiation renegotiation1 = new Renegotiation();
        DueDate dueDate1 = new DueDate();

        assertNotEquals(new CreditCard("42", createdAt1, "Name", blockedSet1, travelNotes1, wallets1,
                installments1, 1, renegotiation1, dueDate1, new Proposal(), CreditCardStatus.ATIVO), creditCard);
    }

    @Test
    public void testEquals1() {
        Blocked blocked = new Blocked();
        blocked.setId("42");

        HashSet<Blocked> blockedSet = new HashSet<>();
        blockedSet.add(blocked);
        LocalDateTime createdAt = LocalDateTime.of(1, 1, 1, 1, 1);
        HashSet<TravelNote> travelNotes = new HashSet<>();
        HashSet<Wallet> wallets = new HashSet<>();
        HashSet<Installment> installments = new HashSet<>();
        Renegotiation renegotiation = new Renegotiation();
        DueDate dueDate = new DueDate();
        CreditCard creditCard = new CreditCard("42", createdAt, "Name", blockedSet, travelNotes, wallets, installments, 1,
                renegotiation, dueDate, new Proposal(), CreditCardStatus.ATIVO);
        LocalDateTime createdAt1 = LocalDateTime.of(1, 1, 1, 1, 1);
        HashSet<Blocked> blockedSet1 = new HashSet<>();
        HashSet<TravelNote> travelNotes1 = new HashSet<>();
        HashSet<Wallet> wallets1 = new HashSet<>();
        HashSet<Installment> installments1 = new HashSet<>();
        Renegotiation renegotiation1 = new Renegotiation();
        DueDate dueDate1 = new DueDate();

        assertNotEquals(new CreditCard("42", createdAt1, "Name", blockedSet1, travelNotes1, wallets1,
                installments1, 1, renegotiation1, dueDate1, new Proposal(), CreditCardStatus.ATIVO), creditCard);
    }

    @Test
    public void testEquals2() {
        HashSet<Wallet> walletSet = new HashSet<>();
        walletSet.add(new Wallet());
        LocalDateTime createdAt = LocalDateTime.of(1, 1, 1, 1, 1);
        HashSet<Blocked> blockedSet = new HashSet<>();
        HashSet<TravelNote> travelNotes = new HashSet<>();
        HashSet<Installment> installments = new HashSet<>();
        Renegotiation renegotiation = new Renegotiation();
        DueDate dueDate = new DueDate();
        CreditCard creditCard = new CreditCard("42", createdAt, "Name", blockedSet, travelNotes, walletSet, installments, 1,
                renegotiation, dueDate, new Proposal(), CreditCardStatus.ATIVO);
        LocalDateTime createdAt1 = LocalDateTime.of(1, 1, 1, 1, 1);
        HashSet<Blocked> blockedSet1 = new HashSet<>();
        HashSet<TravelNote> travelNotes1 = new HashSet<>();
        HashSet<Wallet> wallets = new HashSet<>();
        HashSet<Installment> installments1 = new HashSet<>();
        Renegotiation renegotiation1 = new Renegotiation();
        DueDate dueDate1 = new DueDate();

        assertNotEquals(new CreditCard("42", createdAt1, "Name", blockedSet1, travelNotes1, wallets,
                installments1, 1, renegotiation1, dueDate1, new Proposal(), CreditCardStatus.ATIVO), creditCard);
    }

    @Test
    public void testEquals3() {
        HashSet<Installment> installmentSet = new HashSet<>();
        installmentSet.add(new Installment());
        LocalDateTime createdAt = LocalDateTime.of(1, 1, 1, 1, 1);
        HashSet<Blocked> blockedSet = new HashSet<>();
        HashSet<TravelNote> travelNotes = new HashSet<>();
        HashSet<Wallet> wallets = new HashSet<>();
        Renegotiation renegotiation = new Renegotiation();
        DueDate dueDate = new DueDate();
        CreditCard creditCard = new CreditCard("42", createdAt, "Name", blockedSet, travelNotes, wallets, installmentSet, 1,
                renegotiation, dueDate, new Proposal(), CreditCardStatus.ATIVO);
        LocalDateTime createdAt1 = LocalDateTime.of(1, 1, 1, 1, 1);
        HashSet<Blocked> blockedSet1 = new HashSet<>();
        HashSet<TravelNote> travelNotes1 = new HashSet<>();
        HashSet<Wallet> wallets1 = new HashSet<>();
        HashSet<Installment> installments = new HashSet<>();
        Renegotiation renegotiation1 = new Renegotiation();
        DueDate dueDate1 = new DueDate();

        assertNotEquals(new CreditCard("42", createdAt1, "Name", blockedSet1, travelNotes1, wallets1,
                installments, 1, renegotiation1, dueDate1, new Proposal(), CreditCardStatus.ATIVO), creditCard);
    }

    @Test
    public void testHashCode() {
        assertEquals(0, (new CreditCard()).hashCode());
        assertEquals(0, (new CreditCard()).hashCode());
        assertEquals(0, (new CreditCard()).hashCode());
    }

    @Test
    public void testAddImage() {
        CreditCard creditCard = new CreditCard();

        creditCard.addImage("Image File", "foo.txt");

        assertEquals(1, creditCard.getImages().size());
    }

    @Test
    public void testUpdateStatus() {
        CreditCard creditCard = new CreditCard();

        creditCard.updateStatus("New Status");

        assertEquals(CreditCardStatus.ATIVO, creditCard.getStatus());
    }

    @Test
    public void testUpdateStatus2() {
        CreditCard creditCard = new CreditCard();

        creditCard.updateStatus("BLOQUEADO");

        assertEquals(CreditCardStatus.BLOQUEADO, creditCard.getStatus());
    }
}

