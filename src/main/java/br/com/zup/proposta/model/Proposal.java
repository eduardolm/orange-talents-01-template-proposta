package br.com.zup.proposta.model;

import br.com.zup.proposta.config.security.Crypto;
import br.com.zup.proposta.enums.Status;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "propostas")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Convert(converter = Crypto.class)
    private String document;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private BigDecimal income;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "proposal")
    private CreditCard creditCard;

    @Deprecated
    public Proposal() {
    }

    public Proposal(String document, String name, String email, BigDecimal income, Address address, Status status) {
        this.document = document;
        this.name = name;
        this.email = email;
        this.income = income;
        this.address = address;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public Address getAddress() {
        return address;
    }

    public Status getStatus() {
        return status;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proposal)) return false;

        Proposal proposal = (Proposal) o;

        if (getDocument() != null ? !getDocument().equals(proposal.getDocument()) : proposal.getDocument() != null)
            return false;
        if (getName() != null ? !getName().equals(proposal.getName()) : proposal.getName() != null) return false;
        if (getEmail() != null ? !getEmail().equals(proposal.getEmail()) : proposal.getEmail() != null) return false;
        if (getIncome() != null ? !getIncome().equals(proposal.getIncome()) : proposal.getIncome() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(proposal.getAddress()) : proposal.getAddress() != null)
            return false;
        return getStatus() == proposal.getStatus();
    }

    @Override
    public int hashCode() {
        int result = getDocument() != null ? getDocument().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getIncome() != null ? getIncome().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", document='" + document + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", income=" + income +
                ", address=" + address +
                '}';
    }

    public void updateStatus(String response) {
        this.status = convertStatus(response);
    }

    private Status convertStatus(String response) {
        if (response.equals("COM_RESTRICAO")) {
            return Status.NAO_ELEGIVEL;
        } else if (response.equals("SEM_RESTRICAO")) {
            return Status.ELEGIVEL;
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ocorreu um erro ao consultar parceiro.");
        }
    }

    public void updateCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
