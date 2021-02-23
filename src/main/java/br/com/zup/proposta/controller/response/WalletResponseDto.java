package br.com.zup.proposta.controller.response;

public class WalletResponseDto {

    private String id;
    private String resultado;

    public WalletResponseDto(String id, String resultado) {
        this.id = id;
        this.resultado = resultado;
    }

    public String getId() {
        return id;
    }

    public String getResultado() {
        return resultado;
    }
}
