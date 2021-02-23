package br.com.zup.proposta.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardStatusTest {

    @Test
    public void testValueOf2() {
        assertEquals(CreditCardStatus.ATIVO, CreditCardStatus.valueOf("ATIVO"));
    }

    @Test
    public void testValues() {
        assertEquals(2, CreditCardStatus.values().length);
    }
}

