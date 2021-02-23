package br.com.zup.proposta.controller.response;

public class StatusResponseDto {

    private final String documento;
    private final String nome;
    private final String resultadoSolicitacao;
    private final Long idProposta;

    public StatusResponseDto(String documento, String nome, String resultadoSolicitacao, Long idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public Long getIdProposta() {
        return idProposta;
    }
}
