package br.com.zup.proposta.service;

import br.com.zup.proposta.controller.request.StatusRequestDto;
import br.com.zup.proposta.controller.response.StatusResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(name = "customerAnalysisClient", url = "${analysisClient.service.url}")
public interface AnalysisClient {

    @PostMapping("/api/solicitacao")
    StatusResponseDto checkCustomer(@RequestBody StatusRequestDto statusRequestDto);
}
