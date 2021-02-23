package br.com.zup.proposta.validator;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@CPF
@CNPJ
@ConstraintComposition(CompositionType.OR)
@Documented
public @interface CPFeCNPJ {

    String message() default "CPF/CNPJ inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
