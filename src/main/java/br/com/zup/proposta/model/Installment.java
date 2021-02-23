package br.com.zup.proposta.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "parcelas")
public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal amount;

    @ManyToOne
    private CreditCard creditCard;


    @Deprecated public Installment() {
    }
}
