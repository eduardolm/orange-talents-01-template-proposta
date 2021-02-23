package br.com.zup.proposta.exception.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoSuchElementExceptionOutputDtoTest {
    @Test
    public void testAddError() {
        NoSuchElementExceptionOutputDto noSuchElementExceptionOutputDto = new NoSuchElementExceptionOutputDto();

        noSuchElementExceptionOutputDto.addError("An error occurred");

        List<String> errors = noSuchElementExceptionOutputDto.getErrors();
        assertEquals(1, errors.size());
        assertEquals("An error occurred", errors.get(0));
    }
}

