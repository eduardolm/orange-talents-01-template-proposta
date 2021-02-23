package br.com.zup.proposta.builder;

import br.com.zup.proposta.controller.request.AddressRequestDto;
import br.com.zup.proposta.controller.request.ProposalRequestDto;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;

import java.math.BigDecimal;

public class ProposalRequestDtoBuilder {

    private String document;
    private String name;
    private String email;
    private BigDecimal income;
    private AddressRequestDto address;

    public ProposalRequestDtoBuilder withDocument(String document) {
        this.document = document;
        return this;
    }

    public ProposalRequestDtoBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProposalRequestDtoBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public ProposalRequestDtoBuilder withIncome(BigDecimal income) {
        this.income = income;
        return this;
    }

    public ProposalRequestDtoBuilder withAddress(AddressRequestDto address) {
        this.address = address;
        return this;
    }

    public ProposalRequestDto build() {
        return new ProposalRequestDto(document, name, email, income, address);
    }
}
