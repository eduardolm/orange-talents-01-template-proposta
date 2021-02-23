package br.com.zup.proposta.exception;

import br.com.zup.proposta.exception.dto.NoSuchElementExceptionOutputDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ElementNotFoundErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public NoSuchElementExceptionOutputDto handleNoSuchElementErrors(NoSuchElementException exception) {
        List<String> globalErrors = new ArrayList<>();
        globalErrors.add(exception.getMessage());

        return buildNoSuchElementError(globalErrors);
    }

    private NoSuchElementExceptionOutputDto buildNoSuchElementError(List<String> globalErrors) {
        NoSuchElementExceptionOutputDto notFoundErrors = new NoSuchElementExceptionOutputDto();

        globalErrors.forEach(notFoundErrors::addError);
        return notFoundErrors;
    }
}
