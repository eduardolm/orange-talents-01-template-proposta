package br.com.zup.proposta.dto;

import br.com.zup.proposta.model.DueDate;

import java.time.LocalDateTime;

public class DueDateDetailsDto {

    private String id;
    private int dia;
    private LocalDateTime dataDeCriacao;

    @Deprecated
    public DueDateDetailsDto() {
    }

    public DueDateDetailsDto(String id, int dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public DueDateDetailsDto(DueDate dueDate) {
        this.id = dueDate.getId();
        this.dia = dueDate.getDay();
        this.dataDeCriacao = dueDate.getCreatedAt();
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
}
