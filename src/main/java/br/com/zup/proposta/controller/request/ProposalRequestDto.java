package br.com.zup.proposta.controller.request;

import br.com.zup.proposta.enums.Status;
import br.com.zup.proposta.model.Proposal;
import br.com.zup.proposta.validator.CPFeCNPJ;
import br.com.zup.proposta.validator.UniqueValue;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class ProposalRequestDto {

    @NotBlank(message = "O documento é oobrigatório.")
    @CPFeCNPJ(message = "Documento invalido.")
    @UniqueValue(domainClass = Proposal.class, fieldName = "document", message = "Documento já cadastrado.")
    private String document;

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotBlank(message = "O e-mail é obrigatótio.")
    @Email(message = "Formato inválido.")
    private String email;

    @NotNull(message = "O salário é obrigatório.")
    @Positive(message = "O salário deve ser positivo.")
    private BigDecimal income;

    @Valid
    @NotNull(message = "É obrigatório informar o endereço.")
    private AddressRequestDto address;
    private Status status;

    public ProposalRequestDto(@NotBlank(message = "O documento é oobrigatório.") String document,
                              @NotBlank(message = "O nome é obrigatório.") String name,
                              @NotBlank(message = "O e-mail é obrigatótio.")
                              @Email(message = "Formato inválido.") String email,
                              @NotNull(message = "O salário é obrigatório.")
                              @Positive(message = "O salário deve ser positivo.") BigDecimal income,
                              @Valid @NotNull(message = "É obrigatório informar o endereço.") AddressRequestDto address) {

        this.document = document;
        this.name = name;
        this.email = email;
        this.income = income;
        this.address = address;
        this.status = Status.CRIADA;
    }

    @Deprecated
    public ProposalRequestDto() {
    }

    public Proposal toModel() {
        return new Proposal(document, name, email, income, address.toModel(), status);
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

    public AddressRequestDto getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "ProposalRequestDto{" +
                "document='" + document + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", income=" + income +
                ", address=" + address +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProposalRequestDto)) return false;

        ProposalRequestDto that = (ProposalRequestDto) o;

        if (getDocument() != null ? !getDocument().equals(that.getDocument()) : that.getDocument() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) return false;
        if (getIncome() != null ? !getIncome().equals(that.getIncome()) : that.getIncome() != null) return false;
        if (getAddress() != null ? !getAddress().equals(that.getAddress()) : that.getAddress() != null) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = getDocument() != null ? getDocument().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getIncome() != null ? getIncome().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
