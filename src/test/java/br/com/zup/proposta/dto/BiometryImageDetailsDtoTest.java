package br.com.zup.proposta.dto;

import br.com.zup.proposta.enums.CreditCardStatus;
import br.com.zup.proposta.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BiometryImageDetailsDtoTest {
    @Test
    public void testConstructor() {
        BiometryImageDetailsDto actualBiometryImageDetailsDto = new BiometryImageDetailsDto(new BiometryImage());

        assertNull(actualBiometryImageDetailsDto.getCreatedAt());
        assertNull(actualBiometryImageDetailsDto.getId());
        assertNull(actualBiometryImageDetailsDto.getOriginalFileName());
    }

    @Test
    public void testConstructor2() {
        LocalDateTime createdAt = LocalDateTime.of(1, 1, 1, 1, 1);
        HashSet<Blocked> blockedSet = new HashSet<>();
        HashSet<TravelNote> travelNotes = new HashSet<>();
        HashSet<Wallet> wallets = new HashSet<>();
        HashSet<Installment> installments = new HashSet<>();
        Renegotiation renegotiation = new Renegotiation();
        DueDate dueDate = new DueDate();

        BiometryImageDetailsDto actualBiometryImageDetailsDto = new BiometryImageDetailsDto(
                new BiometryImage(new CreditCard("42", createdAt, "Name", blockedSet, travelNotes, wallets, installments, 1,
                        renegotiation, dueDate, new Proposal(), CreditCardStatus.ATIVO), "Image File", "foo.txt"));

        assertNull(actualBiometryImageDetailsDto.getId());
        assertEquals("foo.txt", actualBiometryImageDetailsDto.getOriginalFileName());
    }
}

