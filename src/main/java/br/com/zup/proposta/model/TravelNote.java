package br.com.zup.proposta.model;

import br.com.zup.proposta.controller.request.TravelNoteRequestDto;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "avisos")
public class TravelNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private LocalDate validUntil;

    private LocalDateTime createdAt;
    private String clientIp;
    private String userAgent;

    @ManyToOne
    private CreditCard creditCard;

    @Deprecated
    public TravelNote() {
    }

    public TravelNote(TravelNoteRequestDto travelNoteRequestDto, CreditCard creditCard, List<String> remoteProperties) {

        this.destination = travelNoteRequestDto.getDestino();
        this.validUntil = travelNoteRequestDto.getValidoAte();
        this.createdAt = LocalDateTime.now();
        this.clientIp = remoteProperties.get(1);
        this.userAgent = remoteProperties.get(0);
        this.creditCard = creditCard;
    }

    public Long getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getClientIp() {
        return clientIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    @Override
    public String toString() {
        return "TravelNote{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", validUntil=" + validUntil +
                ", createdAt=" + createdAt +
                ", clientIp='" + clientIp + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TravelNote)) return false;

        TravelNote that = (TravelNote) o;

        if (getDestination() != null ? !getDestination().equals(that.getDestination()) : that.getDestination() != null)
            return false;
        if (getValidUntil() != null ? !getValidUntil().equals(that.getValidUntil()) : that.getValidUntil() != null)
            return false;
        if (getClientIp() != null ? !getClientIp().equals(that.getClientIp()) : that.getClientIp() != null)
            return false;
        return getUserAgent() != null ? getUserAgent().equals(that.getUserAgent()) : that.getUserAgent() == null;
    }

    @Override
    public int hashCode() {
        int result = getDestination() != null ? getDestination().hashCode() : 0;
        result = 31 * result + (getValidUntil() != null ? getValidUntil().hashCode() : 0);
        result = 31 * result + (getClientIp() != null ? getClientIp().hashCode() : 0);
        result = 31 * result + (getUserAgent() != null ? getUserAgent().hashCode() : 0);
        return result;
    }
}
