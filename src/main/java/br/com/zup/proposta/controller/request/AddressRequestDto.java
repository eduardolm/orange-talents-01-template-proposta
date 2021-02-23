package br.com.zup.proposta.controller.request;

import br.com.zup.proposta.model.Address;
import br.com.zup.proposta.validator.CEP;
import br.com.zup.proposta.validator.ValidAddress;

import javax.validation.constraints.NotBlank;

@ValidAddress
public class AddressRequestDto {

    @NotBlank(message = "A rua é obrigatória.")
    private String street;

    @NotBlank(message = "O CEP é obrigatório.")
    @CEP(message = "Formato inválido.")
    private String zip;

    @NotBlank(message = "Obrigatório informar o número.")
    private String number;

    @NotBlank(message = "O complemento é obrigatório.")
    private String complement;

    public AddressRequestDto(@NotBlank(message = "A rua é obrigatória.") String street,
                             @NotBlank(message = "O CEP é obrigatório.") @CEP(message = "Formato inválido.") String zip,
                             @NotBlank(message = "Obrigatório informar o número.") String number,
                             @NotBlank(message = "O complemento é obrigatório.") String complement) {

        this.street = street;
        this.zip = zip;
        this.number = number;
        this.complement = complement;
    }

    @Deprecated
    public AddressRequestDto() {
    }

    public Address toModel(){
        return new Address(street, zip, number, complement);
    }

    public String getStreet() {
        return street;
    }

    public String getZip() {
        return zip;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    @Override
    public String toString() {
        return "AddressRequestDto{" +
                "street='" + street + '\'' +
                ", zip='" + zip + '\'' +
                ", number='" + number + '\'' +
                ", complement='" + complement + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressRequestDto)) return false;

        AddressRequestDto that = (AddressRequestDto) o;

        if (getStreet() != null ? !getStreet().equals(that.getStreet()) : that.getStreet() != null) return false;
        if (getZip() != null ? !getZip().equals(that.getZip()) : that.getZip() != null) return false;
        if (getNumber() != null ? !getNumber().equals(that.getNumber()) : that.getNumber() != null) return false;
        return getComplement() != null ? getComplement().equals(that.getComplement()) : that.getComplement() == null;
    }

    @Override
    public int hashCode() {
        int result = getStreet() != null ? getStreet().hashCode() : 0;
        result = 31 * result + (getZip() != null ? getZip().hashCode() : 0);
        result = 31 * result + (getNumber() != null ? getNumber().hashCode() : 0);
        result = 31 * result + (getComplement() != null ? getComplement().hashCode() : 0);
        return result;
    }
}
