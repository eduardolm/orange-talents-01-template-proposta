package br.com.zup.proposta.builder;

import br.com.zup.proposta.controller.request.AddressRequestDto;

public class AddressRequestDtoBuilder {

    private String street;
    private String zip;
    private String number;
    private String complement;

    public AddressRequestDtoBuilder withStreet(String street) {
        this.street = street;
        return this;
    }

    public AddressRequestDtoBuilder withZip(String zip) {
        this.zip = zip;
        return this;
    }

    public AddressRequestDtoBuilder withNumber(String number) {
        this.number = number;
        return this;
    }

    public AddressRequestDtoBuilder withComplement(String complement) {
        this.complement = complement;
        return this;
    }

    public AddressRequestDto build() {
        return new AddressRequestDto(street, zip, number, complement);
    }
}
