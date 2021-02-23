package br.com.zup.proposta.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {ValidationErrorHandler.class})
@ExtendWith(SpringExtension.class)
public class ValidationErrorHandlerTest {
    @Autowired
    private ValidationErrorHandler validationErrorHandler;

    @Test
    public void testHandleBindExceptionValidationError() {
        // Arrange
        BindException bindException = new BindException("target", "Object Name");
        bindException.addError(new ObjectError("Object Name", "Default Message"));

        // Act and Assert
        List<String> globalErrorMessages = this.validationErrorHandler
                .handleBindExceptionValidationError(
                        new BindException(new BindException(new BindException(new BindException(bindException)))))
                .getGlobalErrorMessages();
        assertEquals(1, globalErrorMessages.size());
        assertEquals("Default Message", globalErrorMessages.get(0));
    }
}

