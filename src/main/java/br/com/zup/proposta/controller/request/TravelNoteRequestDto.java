package br.com.zup.proposta.controller.request;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class TravelNoteRequestDto {

    @NotBlank(message = "Obrigatório informar o destino.")
    private String destino;

    @NotNull(message = "Obrigatório informar o término da viagem.")
    @Future(message = "O término da viagem precisa obrigatoriamente ser uma data futura.")
    private LocalDate validoAte;

    public TravelNoteRequestDto(@NotBlank(message = "Obrigatório informar o destino.") String destino,
                                @NotNull(message = "Obrigatório informar o término da viagem.")
                                @Future(message = "O término da viagem precisa obrigatoriamente ser uma data futura.") LocalDate validoAte) {

        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

}
