package br.com.zup.proposta.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusTest {

    @Test
    public void testValueOf2() {
        // Arrange, Act and Assert
        assertEquals(Status.ELEGIVEL, Status.valueOf("ELEGIVEL"));
    }

    @Test
    public void testValues() {
        // Arrange, Act and Assert
        assertEquals(4, Status.values().length);
    }
}

