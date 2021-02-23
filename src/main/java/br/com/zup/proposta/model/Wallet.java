package br.com.zup.proposta.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "carteiras")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private LocalDateTime associatedAt = LocalDateTime.now();
    private String issuer;

    @ManyToOne
    private CreditCard creditCard;

    @Deprecated
    public Wallet() {
    }
}
