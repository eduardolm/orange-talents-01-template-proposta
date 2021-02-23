package br.com.zup.proposta.validator.constraints;

import br.com.zup.proposta.controller.request.AddressRequestDto;
import br.com.zup.proposta.validator.ValidAddress;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class AddressValidator implements ConstraintValidator<ValidAddress, AddressRequestDto> {

    @Override
    public void initialize(ValidAddress constraintAnnotation) {

    }

    @Override
    public boolean isValid(AddressRequestDto address, ConstraintValidatorContext constraintValidatorContext) {
        return address.getStreet() != null && address.getNumber() != null && address.getZip() != null && address.getComplement() != null;
    }
}
