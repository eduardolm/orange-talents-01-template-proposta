package br.com.zup.proposta.exception.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidationErrorsOutputDtoTest {
    @Test
    public void testAddError() {
        ValidationErrorsOutputDto validationErrorsOutputDto = new ValidationErrorsOutputDto();

        validationErrorsOutputDto.addError("Not all who wander are lost");

        List<String> globalErrorMessages = validationErrorsOutputDto.getGlobalErrorMessages();
        assertEquals(1, globalErrorMessages.size());
        assertEquals("Not all who wander are lost", globalErrorMessages.get(0));
    }

    @Test
    public void testAddFieldError() {
        ValidationErrorsOutputDto validationErrorsOutputDto = new ValidationErrorsOutputDto();

        validationErrorsOutputDto.addFieldError("Field", "Not all who wander are lost");

        List<FieldErrorOutputDto> errors = validationErrorsOutputDto.getErrors();
        assertEquals(1, errors.size());
        FieldErrorOutputDto getResult = errors.get(0);
        assertEquals("Field", getResult.getField());
        assertEquals("Not all who wander are lost", getResult.getMessage());
    }
}

