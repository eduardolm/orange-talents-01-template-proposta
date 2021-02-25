package br.com.zup.proposta.model;

import br.com.zup.proposta.controller.request.WalletRequestDto;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {
    @Test
    public void testConstructor() {
        WalletRequestDto walletRequestDto = new WalletRequestDto("jane.doe@example.org", "PAYPAL");
        CreditCard creditCard = new CreditCard();

        new Wallet("42", walletRequestDto, creditCard);

        assertEquals("jane.doe@example.org", walletRequestDto.getEmail());
        assertEquals("PAYPAL", walletRequestDto.getCarteira());
        Set<Wallet> wallets = creditCard.getWallets();
        assertTrue(wallets instanceof java.util.HashSet);
        assertNull(creditCard.getStatus());
        assertTrue(wallets.isEmpty());
        assertNull(creditCard.getDueDate());
        Set<Blocked> blockedSet = creditCard.getBlockedSet();
        assertTrue(blockedSet instanceof java.util.HashSet);
        assertEquals(0, creditCard.getCreditLimit());
        assertTrue(blockedSet.isEmpty());
        assertNull(creditCard.getCreatedAt());
        Set<Installment> installments = creditCard.getInstallments();
        assertTrue(installments instanceof java.util.HashSet);
        assertNull(creditCard.getId());
        assertTrue(installments.isEmpty());
        Set<TravelNote> notes = creditCard.getNotes();
        assertTrue(notes instanceof java.util.HashSet);
        assertNull(creditCard.getProposal());
        assertTrue(notes.isEmpty());
        Set<BiometryImage> images = creditCard.getImages();
        assertTrue(images instanceof java.util.HashSet);
        assertNull(creditCard.getName());
        assertTrue(images.isEmpty());
        assertNull(creditCard.getRenegotiation());
    }

    @Test
    public void shouldThrowIllegalArgumentExcpetionWhenWalletNotInEnum() {
        WalletRequestDto walletRequestDto = new WalletRequestDto("jane.doe@example.org", "Carteira");
        CreditCard creditCard = new CreditCard();

        assertThrows(IllegalArgumentException.class, () -> new Wallet("42", walletRequestDto, creditCard));
    }
}

