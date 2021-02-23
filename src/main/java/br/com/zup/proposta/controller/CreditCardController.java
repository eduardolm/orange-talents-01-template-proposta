package br.com.zup.proposta.controller;

import br.com.zup.proposta.controller.request.BiometryImageRequestDto;
import br.com.zup.proposta.dto.CreditCardDetailsDto;
import br.com.zup.proposta.handler.ObjectHandler;
import br.com.zup.proposta.model.BiometryImage;
import br.com.zup.proposta.model.CreditCard;
import br.com.zup.proposta.service.CreditCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cartoes")
public class CreditCardController extends ObjectHandler {

    @Autowired
    private CreditCardService creditCardService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardController.class);

    @PostMapping("{id}/images")
    public ResponseEntity<?> upload(@PathVariable("id") String id,
                                    @Valid BiometryImageRequestDto requestDto,
                                    UriComponentsBuilder uriBuilder) throws Exception {

        LOGGER.info("Iniciando upload de biometria....");
        CreditCard creditCard = checkCreditCardExists(id);
        if (creditCard == null) return ResponseEntity.notFound().build();

        BiometryImage uploadedBiometric = creditCardService.addBiometry(requestDto, creditCard);
        URI location = creditCardService.buildUri(id, uriBuilder, uploadedBiometric);

        LOGGER.info("Biometria cadastrada com sucesso: " + uploadedBiometric.toString());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        CreditCard creditCard = checkCreditCardExists(id);
        if (creditCard == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(new CreditCardDetailsDto(creditCard));
    }

    @PostMapping("/bloqueios")
    public ResponseEntity<?> block(@PathParam("id") String id, HttpServletRequest request) {

        if (id == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Obrigatório informar o número do cartão.");
            return ResponseEntity.badRequest().body(response);
        }

        CreditCard creditCard = checkCreditCardExists(id);
        if (creditCard ==  null) {
            return ResponseEntity.notFound().build();
        }

        CreditCard blockedCard = creditCardService.block(creditCard, request);
        if (blockedCard == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Cartão já bloqueado.");
            return ResponseEntity.unprocessableEntity().body(response);
        }

        return ResponseEntity.ok().body(new CreditCardDetailsDto(creditCard));
    }
}
