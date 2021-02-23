package br.com.zup.proposta.model;

import br.com.zup.proposta.validator.CEP;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Address {

    @NotBlank(message = "A rua é obrigatória.")
    private String street;

    @NotBlank(message = "O CEP é obrigatório.")
    @CEP(message = "CEP inválido.")
    private String zip;

    @NotBlank(message = "O número é obrigatório.")
    private String number;

    @NotBlank(message = "O complemento é obrigatório.")
    private String complement;

    public Address(@NotBlank String street, @NotBlank String zip, @NotBlank String number, @NotBlank String complement) {
        this.street = street;
        this.zip = zip;
        this.number = number;
        this.complement = complement;
    }

    @Deprecated
    public Address() {
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
        return "Address{" +
                "street='" + street + '\'' +
                ", zip='" + zip + '\'' +
                ", number='" + number + '\'' +
                ", complement='" + complement + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (getStreet() != null ? !getStreet().equals(address.getStreet()) : address.getStreet() != null) return false;
        if (getZip() != null ? !getZip().equals(address.getZip()) : address.getZip() != null) return false;
        if (getNumber() != null ? !getNumber().equals(address.getNumber()) : address.getNumber() != null) return false;
        return getComplement() != null ? getComplement().equals(address.getComplement()) : address.getComplement() == null;
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
