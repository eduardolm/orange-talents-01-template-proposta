package br.com.zup.proposta.dto;

import br.com.zup.proposta.model.CreditCard;
import br.com.zup.proposta.model.Installment;
import br.com.zup.proposta.model.Renegotiation;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class CreditCardDetailsDto {

    private String id;
    private LocalDateTime createdAt;
    private String name;
    private Set<BlockedDto> blockedSet;
    private Set<TravelNoteDto> travelNotes;
    private Set<WalletDto> wallets;
    private Set<Installment> installments;
    private int creditLimit;
    private Renegotiation renegotiation;
    private DueDateDetailsDto dueDate;
    private ProposalDto proposal;
    private Set<BiometryImageDetailsDto> images;
    private String status;

    public CreditCardDetailsDto(CreditCard creditCard) {
        this.id = creditCard.getId();
        this.createdAt = creditCard.getCreatedAt();
        this.name = creditCard.getName();
        this.blockedSet = creditCard.getBlockedSet().stream().map(BlockedDto::new).collect(Collectors.toSet());
        this.travelNotes = creditCard.getNotes().stream().map(TravelNoteDto::new).collect(Collectors.toSet());
        this.wallets = creditCard.getWallets().stream().map(WalletDto::new).collect(Collectors.toSet());
        this.installments = creditCard.getInstallments();
        this.creditLimit = creditCard.getCreditLimit();
        this.renegotiation = creditCard.getRenegotiation();
        this.dueDate = new DueDateDetailsDto(creditCard.getDueDate());
        this.proposal = new ProposalDto(creditCard.getProposal());
        this.images = creditCard.getImages().stream().map(BiometryImageDetailsDto::new).collect(Collectors.toSet());
        this.status = creditCard.getStatus().toString();
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

    public Set<BlockedDto> getBlockedSet() {
        return blockedSet;
    }

    public Set<TravelNoteDto> getNotes() {
        return travelNotes;
    }

    public Set<WalletDto> getWallets() {
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

    public DueDateDetailsDto getDueDate() {
        return dueDate;
    }

    public ProposalDto getProposal() {
        return proposal;
    }

    public String getStatus() {
        return status;
    }

    public Set<BiometryImageDetailsDto> getImages() {
        return images;
    }

    @Override
    public String toString() {
        return "CreditCardDetailsDto{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", blockedSet=" + blockedSet +
                ", travelNotes=" + travelNotes +
                ", wallets=" + wallets +
                ", installments=" + installments +
                ", creditLimit=" + creditLimit +
                ", renegotiation=" + renegotiation +
                ", dueDate=" + dueDate +
                ", proposal=" + proposal +
                ", images=" + images +
                ", status='" + status + '\'' +
                '}';
    }
}
