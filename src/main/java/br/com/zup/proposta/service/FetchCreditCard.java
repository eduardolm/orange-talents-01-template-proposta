package br.com.zup.proposta.service;

import br.com.zup.proposta.controller.response.LegacyCreditCardResponseDto;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(name = "createCreditCard", url = "${createCreditCard.service.url}")
public interface FetchCreditCard {

    @GetMapping("/api/contas")
    Response findAll();

    @GetMapping("/api/cartoes/{id}")
    LegacyCreditCardResponseDto findById(@PathVariable("id") String id);
}

