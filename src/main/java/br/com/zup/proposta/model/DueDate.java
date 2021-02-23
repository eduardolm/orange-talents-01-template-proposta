package br.com.zup.proposta.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "vencimentos")
public class DueDate {

    @Id
    private String id;

    private int day;
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "dueDate")
    private CreditCard creditCard;

    @Deprecated
    public DueDate() {
    }

    public DueDate(String id, int day, LocalDateTime createdAt) {
        this.id = id;
        this.day = day;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Vencimento{" +
                "id='" + id + '\'' +
                ", dia=" + day +
                ", dataDeCriacao=" + createdAt +
                '}';
    }

    public String getId() {
        return id;
    }

    public int getDay() {
        return day;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }
}
