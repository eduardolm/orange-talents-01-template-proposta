package br.com.zup.proposta.dto;

import br.com.zup.proposta.enums.DigitalWallet;
import br.com.zup.proposta.model.Wallet;

import java.time.LocalDateTime;

public class WalletDto {

    private String id;
    private String email;
    private LocalDateTime associatedAt;
    private DigitalWallet issuer;

    public WalletDto(Wallet wallet) {
        this.id = wallet.getId();
        this.email = wallet.getEmail();
        this.associatedAt = wallet.getAssociatedAt();
        this.issuer = wallet.getIssuer();
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
}
