package br.com.zup.proposta.service;

import br.com.zup.proposta.controller.request.CreditCardStatusRequestDto;
import br.com.zup.proposta.controller.request.TravelNoteRequestDto;
import br.com.zup.proposta.controller.request.WalletRequestDto;
import br.com.zup.proposta.controller.response.CreditCardStatusResponseDto;
import br.com.zup.proposta.controller.response.LegacyCreditCardResponseDto;
import br.com.zup.proposta.controller.response.TravelNoteResponseDto;
import br.com.zup.proposta.controller.response.WalletResponseDto;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Service
@FeignClient(name = "fetchCreditCard", url = "${createCreditCard.service.url}")
public interface FetchCreditCard {

    @GetMapping("/api/contas")
    Response findAll();

    @GetMapping("/api/cartoes/{id}")
    LegacyCreditCardResponseDto findById(@PathVariable("id") String id);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    CreditCardStatusResponseDto blockCard(@PathVariable("id") String id,
                                          @RequestBody @Valid CreditCardStatusRequestDto requestDto);

    @PostMapping("/api/cartoes/{id}/avisos")
    TravelNoteResponseDto communicateTravelNote(@PathVariable("id") String id,
                                                @RequestBody @Valid TravelNoteRequestDto requestDto);

    @PostMapping("/api/cartoes/{id}/carteiras")
    WalletResponseDto addWallet(@PathVariable("id") String id,
                                @RequestBody @Valid WalletRequestDto requestDto);
}

