package br.com.zup.proposta.controller.response;

import br.com.zup.proposta.model.DueDate;

import java.time.LocalDateTime;

public class DueDateResponseDto {

    private String id;
    private int dia;
    private LocalDateTime dataDeCriacao;

    @Deprecated
    public DueDateResponseDto() {
    }

    public DueDateResponseDto(String id, int dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getId() {
        return id;
    }

    public int getDia() {
        return dia;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

    public DueDate toModel() {
        return new DueDate(id, dia, dataDeCriacao);
    }

    @Override
    public String toString() {
        return "DueDateResponseDto{" +
                "id='" + id + '\'' +
                ", dia=" + dia +
                ", dataDeCriacao=" + dataDeCriacao +
                '}';
    }
}


