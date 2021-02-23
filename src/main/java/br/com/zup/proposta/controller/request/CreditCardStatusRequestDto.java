package br.com.zup.proposta.controller.request;

import javax.validation.constraints.NotBlank;

public class CreditCardStatusRequestDto {

    @NotBlank(message = "É obrigatório informar o sistema responsável pelo bloqueio.")
    private String sistemaResponsavel;

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

    @Deprecated
    public CreditCardStatusRequestDto() {
    }

    public CreditCardStatusRequestDto(
            @NotBlank(message = "É obrigatório informar o sistema responsável pelo bloqueio.") String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }
}
