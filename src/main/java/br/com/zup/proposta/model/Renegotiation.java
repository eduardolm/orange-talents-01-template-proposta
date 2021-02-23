package br.com.zup.proposta.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "renegociacoes")
public class Renegotiation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal amount;
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(mappedBy = "renegotiation")
    private CreditCard creditCard;

    @Deprecated
    public Renegotiation() {
    }
}
