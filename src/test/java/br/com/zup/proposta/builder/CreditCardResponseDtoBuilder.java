package br.com.zup.proposta.builder;

import br.com.zup.proposta.controller.response.CreditCardResponseDto;
import br.com.zup.proposta.controller.response.DueDateResponseDto;
import br.com.zup.proposta.model.*;

import java.time.LocalDateTime;
import java.util.Set;

public class CreditCardResponseDtoBuilder {

    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private Set<Blocked> bloqueios;
    private Set<TravelNote> avisos;
    private Set<Wallet> carteiras;
    private Set<Installment> parcelas;
    private int limite;
    private Renegotiation renegociacao;
    private DueDateResponseDto vencimento;
    private Long idProposta;

    public CreditCardResponseDtoBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public CreditCardResponseDtoBuilder withEmitidoEm(LocalDateTime emitidoEm) {
        this.emitidoEm = emitidoEm;
        return this;
    }

    public CreditCardResponseDtoBuilder withTitular(String titular) {
        this.titular = titular;
        return this;
    }

    public CreditCardResponseDtoBuilder withBloqueios(Set<Blocked> bloqueios) {
        this.bloqueios = bloqueios;
        return this;
    }

    public CreditCardResponseDtoBuilder withAvisos(Set<TravelNote> avisos) {
        this.avisos = avisos;
        return this;
    }

    public CreditCardResponseDtoBuilder withCarteiras(Set<Wallet> carteiras) {
        this.carteiras = carteiras;
        return this;
    }

    public CreditCardResponseDtoBuilder withParcelas(Set<Installment> parcelas) {
        this.parcelas = parcelas;
        return this;
    }

    public CreditCardResponseDtoBuilder withLimite(int limite) {
        this.limite = limite;
        return this;
    }

    public CreditCardResponseDtoBuilder withRenegociacao(Renegotiation renegociacao) {
        this.renegociacao = renegociacao;
        return this;
    }

    public CreditCardResponseDtoBuilder withVencimento(DueDateResponseDto vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public CreditCardResponseDtoBuilder withIdProposta(Long idProposta) {
        this.idProposta = idProposta;
        return this;
    }

    public CreditCardResponseDto build() {
        return new CreditCardResponseDto(id, emitidoEm, titular, bloqueios, avisos, carteiras, parcelas,
                limite, renegociacao, vencimento, idProposta);
    }
}
