package br.com.zup.proposta.exception.dto;

import java.util.ArrayList;
import java.util.List;

public class NoSuchElementExceptionOutputDto {

    private List<String> errors = new ArrayList<>();

    public void addError(String message) {
        errors.add(message);
    }

    public List<String> getErrors() {
        return errors;
    }
}
