package br.com.zup.proposta.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BlockedTest {

    @Test
    public void testConstructor() {
        Blocked blocked = new Blocked("Testing", false, new CreditCard());

        assertEquals(LocalDateTime.now(), blocked.getBlockedAt());
        assertEquals("Testing", blocked.getResponsibleSystem());
        assertFalse(blocked.isActive());
    }
}
