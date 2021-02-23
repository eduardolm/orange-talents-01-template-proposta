package br.com.zup.proposta.controller.response;

import br.com.zup.proposta.enums.CreditCardStatus;
import br.com.zup.proposta.model.*;
import br.com.zup.proposta.repository.ProposalRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Set;

public class CreditCardResponseDto {

    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private Set<Blocked> bloqueios;
    private Set<Note> avisos;
    private Set<Wallet> carteiras;
    private Set<Installment> parcelas;
    private int limite;
    private Renegotiation renegociacao;
    private DueDateResponseDto vencimento;
    private Long idProposta;
    private CreditCardStatus status;

    @Deprecated
    public CreditCardResponseDto() {
    }

    public CreditCardResponseDto(String id,
                                 LocalDateTime emitidoEm,
                                 String titular,
                                 Set<Blocked> bloqueios,
                                 Set<Note> avisos,
                                 Set<Wallet> carteiras,
                                 Set<Installment> parcelas,
                                 int limite,
                                 Renegotiation renegociacao,
                                 DueDateResponseDto vencimento,
                                 Long idProposta) {

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

    public Set<Blocked> getBloqueios() {
        return bloqueios;
    }

    public Set<Note> getAvisos() {
        return avisos;
    }

    public Set<Wallet> getCarteiras() {
        return carteiras;
    }

    public Set<Installment> getParcelas() {
        return parcelas;
    }

    public int getLimite() {
        return limite;
    }

    public Renegotiation getRenegociacao() {
        return renegociacao;
    }

    public DueDateResponseDto getVencimento() {
        return vencimento;
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public CreditCardStatus getStatus() {
        return status;
    }

    public void setStatus(CreditCardStatus status) {
        this.status = status;
    }

    public CreditCard toModel(ProposalRepository proposalRepository) {
        return new CreditCard(id, emitidoEm, titular, bloqueios, avisos, carteiras, parcelas, limite,
                renegociacao, vencimento.toModel(), proposalRepository.findById(idProposta)
                .orElseThrow(() -> new NoSuchElementException("Proposta não encontrada.")), status);
    }

    @Override
    public String toString() {
        return "CreditCardResponseDto{" +
                "id='" + id + '\'' +
                ", emitidoEm=" + emitidoEm +
                ", titular='" + titular + '\'' +
                ", bloqueios=" + bloqueios +
                ", avisos=" + avisos +
                ", carteiras=" + carteiras +
                ", parcelas=" + parcelas +
                ", limite=" + limite +
                ", renegociacao=" + renegociacao +
                ", vencimento=" + vencimento +
                ", idProposta=" + idProposta +
                ", status=" + status +
                '}';
    }
}
