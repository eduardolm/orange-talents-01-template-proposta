package br.com.zup.proposta.model;

import br.com.zup.proposta.controller.request.WalletRequestDto;
import br.com.zup.proposta.enums.DigitalWallet;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "carteiras")
public class Wallet {

    @Id
    private String id;

    @Column(nullable = false)
    private String email;
    private LocalDateTime associatedAt;

    @Enumerated(EnumType.STRING)
    private DigitalWallet issuer;

    @ManyToOne
    private CreditCard creditCard;

    @Deprecated
    public Wallet() {
    }

    public Wallet(String id, WalletRequestDto requestDto, CreditCard creditCard) {
        this.id = id;
        this.email = requestDto.getEmail();
        this.associatedAt = LocalDateTime.now();
        this.issuer = DigitalWallet.valueOf(requestDto.getCarteira());
        this.creditCard = creditCard;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getAssociatedAt() {
        return associatedAt;
    }

    public DigitalWallet getIssuer() {
        return issuer;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }
}
