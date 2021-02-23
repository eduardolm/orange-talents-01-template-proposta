package br.com.zup.proposta.controller;

import br.com.zup.proposta.controller.request.BiometryImageRequestDto;
import br.com.zup.proposta.controller.request.TravelNoteRequestDto;
import br.com.zup.proposta.controller.request.WalletRequestDto;
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
import javax.validation.constraints.NotBlank;
import javax.websocket.server.PathParam;
import java.net.URI;

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
        LOGGER.info("Retornando cartão...");
        CreditCard creditCard = checkCreditCardExists(id);
        if (creditCard == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(new CreditCardDetailsDto(creditCard));
    }

    @PostMapping("/bloqueios")
    public ResponseEntity<?> block(@PathParam("id")
                                   @Valid
                                   @NotBlank(message = "Obrigatório informar o número do cartão.") String id,
                                   HttpServletRequest request) {

        LOGGER.info("Iniciando bloqueio do cartão...");

        CreditCard creditCard = checkCreditCardExists(id);
        if (creditCard ==  null) {
            return ResponseEntity.notFound().build();
        }

        CreditCard blockedCard = creditCardService.block(creditCard, request);
        ResponseEntity<?> response = creditCardService.checkForNull(blockedCard, "Cartão já bloqueado.");
        if (response != null) return response;

        return ResponseEntity.ok().body(new CreditCardDetailsDto(blockedCard));
    }

    @PostMapping("/viagens")
    public ResponseEntity<?> createTravelNote(@PathParam("id")
                                              @Valid
                                              @NotBlank(message = "Obrigatório informar o número do cartão.") String id,
                                              @RequestBody @Valid TravelNoteRequestDto travelNoteRequestDto,
                                              HttpServletRequest request) {

        LOGGER.info("Cadastrando aviso de viagem...");

        CreditCard creditCard = checkCreditCardExists(id);
        if (creditCard ==  null) {
            return ResponseEntity.notFound().build();
        }

        CreditCard travelNoteCard = creditCardService.addTravelNote(creditCard, travelNoteRequestDto, request);
        ResponseEntity<?> response = creditCardService.checkForNull(travelNoteCard, "Aviso de viagem já cadastrado.");
        if (response != null) return response;

        return ResponseEntity.ok().body(new CreditCardDetailsDto(travelNoteCard));
    }

    @PostMapping("/carteiras")
    public ResponseEntity<?> addDigitalWallet(@PathParam("id")
                                              @Valid
                                              @NotBlank(message = "Obrigatório informar o número do cartão.") String id,
                                              @RequestBody @Valid WalletRequestDto requestDto,
                                              UriComponentsBuilder uriBuilder) {

        LOGGER.info("Cadastrando carteira digital...");

        CreditCard creditCard = checkCreditCardExists(id);
        if (creditCard ==  null) {
            return ResponseEntity.notFound().build();
        }

        CreditCard walletCard = creditCardService.addDigitalWallet(creditCard, requestDto);
        ResponseEntity<?> response = creditCardService.checkForNull(walletCard, "Carteira já cadastrada.");
        if (response != null) return response;

        URI location = creditCardService.buildUri(id, uriBuilder, walletCard);
        return ResponseEntity.created(location).body(new CreditCardDetailsDto(walletCard));
    }
}
