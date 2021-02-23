package br.com.zup.proposta.dto;

import br.com.zup.proposta.enums.Status;
import br.com.zup.proposta.model.Address;
import br.com.zup.proposta.model.Proposal;

import java.math.BigDecimal;

public class ProposalDto {

    private Long id;
    private String document;
    private String name;
    private String email;
    private BigDecimal income;
    private Address address;
    private Status status;

    public ProposalDto(Proposal proposal) {
        this.id = proposal.getId();
        this.document = proposal.getDocument();
        this.name = proposal.getName();
        this.email = proposal.getEmail();
        this.income = proposal.getIncome();
        this.address = proposal.getAddress();
        this.status = proposal.getStatus();
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

    @Override
    public String toString() {
        return "ProposalDto{" +
                "id=" + id +
                ", document='" + document + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", income=" + income +
                ", address=" + address +
                ", status=" + status +
                '}';
    }
}
