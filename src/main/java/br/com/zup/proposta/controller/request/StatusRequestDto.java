package br.com.zup.proposta.controller.request;

import br.com.zup.proposta.model.Proposal;

public class StatusRequestDto {

    private final String documento;
    private final String nome;
    private final Long idProposta;

    public StatusRequestDto(Proposal proposal) {
        this.documento = proposal.getDocument();
        this.nome = proposal.getName();
        this.idProposta = proposal.getId();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public Long getIdProposta() {
        return idProposta;
    }
}
