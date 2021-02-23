package br.com.zup.proposta.handler;

import br.com.zup.proposta.model.CreditCard;
import br.com.zup.proposta.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ObjectHandler {

    @Autowired
    private CreditCardRepository creditCardRepository;

    protected CreditCard checkCreditCardExists(String id) {
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        if (creditCard.isEmpty()) {
            return null;
        }
        else {
            return creditCard.get();
        }
    }
}
