package br.com.zup.proposta.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ContextConfiguration(classes = {CustomExceptionHandler.class})
@ExtendWith(SpringExtension.class)
public class CustomExceptionHandlerTest {
    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    @Test
    public void testHandleIllegalArgumentErrors() {
        List<String> errors = this.customExceptionHandler.handleIllegalArgumentErrors(new IllegalArgumentException())
                .getErrors();
        assertEquals(1, errors.size());
        assertNull(errors.get(0));
    }

    @Test
    public void testHandleIllegalArgumentErrors2() {
        List<String> errors = this.customExceptionHandler.handleIllegalArgumentErrors(new IllegalArgumentException(""))
                .getErrors();
        assertEquals(1, errors.size());
        assertEquals("", errors.get(0));
    }

    @Test
    public void testHandleIllegalArgumentErrors3() {
        List<String> errors = this.customExceptionHandler.handleIllegalArgumentErrors(new IllegalStateException())
                .getErrors();
        assertEquals(1, errors.size());
        assertNull(errors.get(0));
    }

    @Test
    public void testHandleIllegalArgumentErrors4() {
        List<String> errors = this.customExceptionHandler.handleIllegalArgumentErrors(new IllegalStateException(""))
                .getErrors();
        assertEquals(1, errors.size());
        assertEquals("", errors.get(0));
    }
}

