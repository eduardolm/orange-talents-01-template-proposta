package br.com.zup.proposta.validator;

import br.com.zup.proposta.validator.constraints.AddressValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = { AddressValidator.class })
public @interface ValidAddress {

    String message() default "Endereço inválido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
