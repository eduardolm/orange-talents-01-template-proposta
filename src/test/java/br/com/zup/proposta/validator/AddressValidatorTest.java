package br.com.zup.proposta.validator;

import br.com.zup.proposta.controller.request.AddressRequestDto;
import br.com.zup.proposta.validator.constraints.AddressValidator;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ContextConfiguration(classes = {AddressValidator.class})
@ExtendWith(SpringExtension.class)
public class AddressValidatorTest {
    @Autowired
    private AddressValidator addressValidator;

    @Test
    public void testIsValid() {
        // Arrange
        AddressRequestDto address = new AddressRequestDto();

        // Act and Assert
        assertFalse(this.addressValidator.isValid(address,
                new ConstraintValidatorContextImpl(null, PathImpl.createRootPath(), null, "constraintValidatorPayload")));
    }
}

