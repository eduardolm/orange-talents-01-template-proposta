package br.com.zup.proposta.exception.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IllegalArgumentExceptionOutputDtoTest {
    @Test
    public void testAddError() {
        IllegalArgumentExceptionOutputDto illegalArgumentExceptionOutputDto = new IllegalArgumentExceptionOutputDto();

        illegalArgumentExceptionOutputDto.addError("An error occurred");

        List<String> errors = illegalArgumentExceptionOutputDto.getErrors();
        assertEquals(1, errors.size());
        assertEquals("An error occurred", errors.get(0));
    }
}

