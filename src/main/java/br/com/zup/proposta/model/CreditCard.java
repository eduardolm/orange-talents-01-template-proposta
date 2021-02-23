package br.com.zup.proposta.model;

import br.com.zup.proposta.enums.CreditCardStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cartoes")
public class CreditCard {

    @Id
    @Column(nullable = false, unique = true)
    private String id;
    private LocalDateTime createdAt;
    private String name;

    @OneToMany(mappedBy = "creditCard", cascade = CascadeType.PERSIST)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Blocked> blockedSet = new HashSet<>();

    @OneToMany(mappedBy = "creditCard", cascade = CascadeType.PERSIST)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<TravelNote> travelNotes = new HashSet<>();

    @OneToMany(mappedBy = "creditCard", cascade = CascadeType.PERSIST)
    private Set<Wallet> wallets = new HashSet<>();

    @OneToMany(mappedBy = "creditCard", cascade = CascadeType.ALL)
    private Set<Installment> installments = new HashSet<>();
    private int creditLimit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "renegotiation_id", referencedColumnName = "id")
    private Renegotiation renegotiation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dueDate_id", referencedColumnName = "id")
    private DueDate dueDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "proposal_id", referencedColumnName = "id")
    private Proposal proposal;

    @OneToMany(mappedBy = "creditCard", cascade = CascadeType.MERGE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<BiometryImage> images = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private CreditCardStatus status;

    @Deprecated
    public CreditCard() {
    }

    public CreditCard(String id,
                      LocalDateTime createdAt,
                      String name,
                      Set<Blocked> blockedSet,
                      Set<TravelNote> travelNotes,
                      Set<Wallet> wallets,
                      Set<Installment> installments,
                      int creditLimit,
                      Renegotiation renegotiation,
                      DueDate dueDate,
                      Proposal proposal,
                      CreditCardStatus status) {

        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.blockedSet = blockedSet;
        this.travelNotes = travelNotes;
        this.wallets = wallets;
        this.installments = installments;
        this.creditLimit = creditLimit;
        this.renegotiation = renegotiation;
        this.dueDate = dueDate;
        this.proposal = proposal;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public Set<Blocked> getBlockedSet() {
        return blockedSet;
    }

    public Set<TravelNote> getNotes() {
        return travelNotes;
    }

    public Set<Wallet> getWallets() {
        return wallets;
    }

    public Set<Installment> getInstallments() {
        return installments;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public Renegotiation getRenegotiation() {
        return renegotiation;
    }

    public DueDate getDueDate() {
        return dueDate;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public CreditCardStatus getStatus() {
        return status;
    }

    public void setStatus(CreditCardStatus status) {
        this.status = status;
    }

    public Set<BiometryImage> getImages() {
        return images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCard)) return false;

        CreditCard that = (CreditCard) o;

        if (getCreditLimit() != that.getCreditLimit()) return false;
        if (getCreatedAt() != null ? !getCreatedAt().equals(that.getCreatedAt()) : that.getCreatedAt() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getBlockedSet() != null ? !getBlockedSet().equals(that.getBlockedSet()) : that.getBlockedSet() != null)
            return false;
        if (getNotes() != null ? !getNotes().equals(that.getNotes()) : that.getNotes() != null) return false;
        if (getWallets() != null ? !getWallets().equals(that.getWallets()) : that.getWallets() != null) return false;
        if (getInstallments() != null ? !getInstallments().equals(that.getInstallments()) : that.getInstallments() != null)
            return false;
        if (getRenegotiation() != null ? !getRenegotiation().equals(that.getRenegotiation()) : that.getRenegotiation() != null)
            return false;
        return getDueDate() != null ? getDueDate().equals(that.getDueDate()) : that.getDueDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getCreatedAt() != null ? getCreatedAt().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getBlockedSet() != null ? getBlockedSet().hashCode() : 0);
        result = 31 * result + (getNotes() != null ? getNotes().hashCode() : 0);
        result = 31 * result + (getWallets() != null ? getWallets().hashCode() : 0);
        result = 31 * result + (getInstallments() != null ? getInstallments().hashCode() : 0);
        result = 31 * result + getCreditLimit();
        result = 31 * result + (getRenegotiation() != null ? getRenegotiation().hashCode() : 0);
        result = 31 * result + (getDueDate() != null ? getDueDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", blockedSet=" + blockedSet +
                ", notes=" + travelNotes +
                ", wallets=" + wallets +
                ", installments=" + installments +
                ", creditLimit=" + creditLimit +
                ", renegotiation=" + renegotiation +
                ", dueDate=" + dueDate +
                ", proposal=" + proposal.getId() +
                ", images=" + images +
                '}';
    }

    public void addImage(String imageFile, String originalFileName) {
        BiometryImage image = new BiometryImage(this, imageFile, originalFileName);
        this.images.add(image);
    }

    public void updateStatus(String newStatus) {
        if (newStatus.equals("BLOQUEADO")) {
            this.setStatus(CreditCardStatus.BLOQUEADO);
        }
        else if (!newStatus.equals("FALHA")) {
            this.setStatus(CreditCardStatus.ATIVO);
        }
    }

    public void addTravelNote(TravelNote travelNote) {
        this.travelNotes.add(travelNote);
    }
}
