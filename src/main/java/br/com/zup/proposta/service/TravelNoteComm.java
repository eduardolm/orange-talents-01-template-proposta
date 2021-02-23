package br.com.zup.proposta.service;

import br.com.zup.proposta.controller.request.TravelNoteRequestDto;
import br.com.zup.proposta.controller.response.TravelNoteResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Service
@FeignClient(name = "communicateTravelNote", url = "${createCreditCard.service.url}")
public interface TravelNoteComm {

    @PostMapping("/api/cartoes/{id}/avisos")
    TravelNoteResponseDto communicateTravelNote(@PathVariable("id") String id, @RequestBody @Valid TravelNoteRequestDto requestDto);
}
