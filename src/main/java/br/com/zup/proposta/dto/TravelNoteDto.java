package br.com.zup.proposta.dto;

import br.com.zup.proposta.model.TravelNote;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TravelNoteDto {

    private Long id;
    private String destination;
    private LocalDate validUntil;
    private LocalDateTime createdAt;
    private String clientIp;
    private String userAgent;

    public TravelNoteDto(TravelNote travelNote) {

        this.id = travelNote.getId();
        this.destination = travelNote.getDestination();
        this.validUntil = travelNote.getValidUntil();
        this.createdAt = travelNote.getCreatedAt();
        this.clientIp = travelNote.getClientIp();
        this.userAgent = travelNote.getUserAgent();
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
}
