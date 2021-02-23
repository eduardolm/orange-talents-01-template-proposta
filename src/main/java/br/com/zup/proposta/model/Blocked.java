package br.com.zup.proposta.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "bloqueios")
public class Blocked {

    @Id
    private String id;
    private LocalDateTime blockedAt;
    private String responsibleSystem;
    private boolean active;

    @ManyToOne
    private CreditCard creditCard;

    @Deprecated
    public Blocked() {
    }

    public Blocked(String responsibleSystem, boolean active, CreditCard creditCard) {
        this.blockedAt = LocalDateTime.now();
        this.responsibleSystem = responsibleSystem;
        this.active = active;
        this.creditCard = creditCard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getBlockedAt() {
        return blockedAt;
    }

    public String getResponsibleSystem() {
        return responsibleSystem;
    }

    public boolean isActive() {
        return active;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }
}
