package br.com.zup.proposta.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "avisos")
public class Note {

    @Id
    private String destination;
    private LocalDateTime validUntil;

    @ManyToOne
    private CreditCard creditCard;

    @Deprecated
    public Note() {
    }
}
