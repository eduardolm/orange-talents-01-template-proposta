package br.com.zup.proposta.controller.response;

import br.com.zup.proposta.model.*;

import java.time.LocalDateTime;
import java.util.List;

public class LegacyCreditCardResponseDto {

    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private List<Blocked> bloqueios;
    private List<TravelNote> avisos;
    private List<Wallet> carteiras;
    private List<Installment> parcelas;
    private int limite;
    private Renegotiation renegociacao;
    private DueDate vencimento;
    private String idProposta;

    public LegacyCreditCardResponseDto(String id, LocalDateTime emitidoEm, String titular, List<Blocked> bloqueios,
                                       List<TravelNote> avisos, List<Wallet> carteiras, List<Installment> parcelas,
                                       int limite, Renegotiation renegociacao, DueDate vencimento, String idProposta) {

        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.avisos = avisos;
        this.carteiras = carteiras;
        this.parcelas = parcelas;
        this.limite = limite;
        this.renegociacao = renegociacao;
        this.vencimento = vencimento;
        this.idProposta = idProposta;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public List<Blocked> getBloqueios() {
        return bloqueios;
    }

    public List<TravelNote> getAvisos() {
        return avisos;
    }

    public List<Wallet> getCarteiras() {
        return carteiras;
    }

    public List<Installment> getParcelas() {
        return parcelas;
    }

    public int getLimite() {
        return limite;
    }

    public Renegotiation getRenegociacao() {
        return renegociacao;
    }

    public DueDate getVencimento() {
        return vencimento;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
