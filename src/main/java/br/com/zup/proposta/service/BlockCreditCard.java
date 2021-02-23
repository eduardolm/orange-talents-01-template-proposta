package br.com.zup.proposta.service;

import br.com.zup.proposta.controller.request.CreditCardStatusRequestDto;
import br.com.zup.proposta.controller.response.CreditCardStatusResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Service
@FeignClient(name = "blockCreditCard", url = "${createCreditCard.service.url}")
public interface BlockCreditCard {

    @PostMapping("/api/cartoes/{id}/bloqueios")
    CreditCardStatusResponseDto blockCard(@PathVariable("id") String id, @RequestBody @Valid CreditCardStatusRequestDto requestDto);
}
