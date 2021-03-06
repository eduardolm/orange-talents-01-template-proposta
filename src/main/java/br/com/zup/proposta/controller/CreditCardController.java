package br.com.zup.proposta.controller;

import br.com.zup.proposta.controller.request.BiometryImageRequestDto;
import br.com.zup.proposta.controller.request.TravelNoteRequestDto;
import br.com.zup.proposta.controller.request.WalletRequestDto;
import br.com.zup.proposta.dto.CreditCardDetailsDto;
import br.com.zup.proposta.helper.HttpHelper;
import br.com.zup.proposta.model.BiometryImage;
import br.com.zup.proposta.model.CreditCard;
import br.com.zup.proposta.repository.CreditCardRepository;
import br.com.zup.proposta.service.BlockedService;
import br.com.zup.proposta.service.CreditCardService;
import br.com.zup.proposta.service.TravelNoteService;
import br.com.zup.proposta.service.WalletService;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;

@RestController
@RequestMapping("/api/cartoes")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private BlockedService blockedService;

    @Autowired
    private TravelNoteService travelNoteService;

    @Autowired
    private HttpHelper httpHelper;

    @Autowired
    private CreditCardRepository creditCardRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardController.class);

    private final Tracer tracer;

    public CreditCardController(Tracer tracer) {
        this.tracer = tracer;
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<?> upload(@PathVariable("id") String id,
                                    @Valid BiometryImageRequestDto requestDto,
                                    UriComponentsBuilder uriBuilder) throws Exception {

        LOGGER.info("Iniciando upload de biometria....");
        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("tag.creditcard.action", "Upload biometrics");

        CreditCard creditCard = creditCardService.checkCreditCardExists(id);
        if (creditCard == null) return ResponseEntity.notFound().build();

        BiometryImage uploadedBiometric = creditCardService.addBiometry(requestDto, creditCard);
        URI location = httpHelper.buildUri(id, uriBuilder, uploadedBiometric);

        LOGGER.info("Biometria cadastrada com sucesso: " + uploadedBiometric.toString());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        LOGGER.info("Retornando cartão...");
        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("tag.creditcard.action", "Find Creditcard by id");

        return creditCardRepository.findById(id)
                .map(creditCard -> ResponseEntity.ok().body(new CreditCardDetailsDto(creditCard)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/bloqueios/{id}")
    public ResponseEntity<?> block(@PathVariable("id")
                                   @Valid
                                   @NotBlank(message = "Obrigatório informar o número do cartão.") String id,
                                   HttpServletRequest request) {

        LOGGER.info("Iniciando bloqueio do cartão...");
        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("tag.creditcard.action", "Block creditcard");

        CreditCard creditCard = creditCardService.checkCreditCardExists(id);
        if (creditCard ==  null) {
            return ResponseEntity.notFound().build();
        }

        CreditCard blockedCard = blockedService.block(creditCard, request);
        ResponseEntity<?> response = creditCardService.checkForNull(blockedCard, "Cartão já bloqueado.");
        if (response != null) return response;

        return ResponseEntity.ok().body(new CreditCardDetailsDto(blockedCard));
    }

    @PostMapping("/viagens/{id}")
    public ResponseEntity<?> createTravelNote(@PathVariable("id")
                                              @Valid
                                              @NotBlank(message = "Obrigatório informar o número do cartão.") String id,
                                              @RequestBody @Valid TravelNoteRequestDto travelNoteRequestDto,
                                              HttpServletRequest request) {

        LOGGER.info("Cadastrando aviso de viagem...");
        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("tag.creditcard.action", "Communicate travel note");

        CreditCard creditCard = creditCardService.checkCreditCardExists(id);
        if (creditCard ==  null) {
            return ResponseEntity.notFound().build();
        }

        CreditCard travelNoteCard = travelNoteService.addTravelNote(creditCard, travelNoteRequestDto, request);
        ResponseEntity<?> response = creditCardService.checkForNull(travelNoteCard, "Aviso de viagem já cadastrado.");
        if (response != null) return response;

        return ResponseEntity.ok().body(new CreditCardDetailsDto(travelNoteCard));
    }

    @PostMapping("/carteiras/{id}")
    public ResponseEntity<?> addDigitalWallet(@PathVariable("id")
                                              @Valid
                                              @NotBlank(message = "Obrigatório informar o número do cartão.") String id,
                                              @RequestBody @Valid WalletRequestDto requestDto,
                                              UriComponentsBuilder uriBuilder) {

        LOGGER.info("Cadastrando carteira digital...");
        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("tag.creditcard.action", "Add digital wallet");

        CreditCard creditCard = creditCardService.checkCreditCardExists(id);
        if (creditCard ==  null) {
            LOGGER.error("Cartão não encontrado.");
            return ResponseEntity.notFound().build();
        }

        CreditCard walletCard = walletService.addDigitalWallet(creditCard, requestDto);
        ResponseEntity<?> response = creditCardService.checkForNull(walletCard, "Carteira já cadastrada.");
        LOGGER.error("Carteira já cadastrada.");
        if (response != null) return response;

        URI location = httpHelper.buildUri(id, uriBuilder, walletCard);
        LOGGER.info("Carteira adicionada com sucesso.");
        return ResponseEntity.created(location).body(new CreditCardDetailsDto(walletCard));
    }
}
