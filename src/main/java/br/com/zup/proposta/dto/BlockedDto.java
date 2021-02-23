package br.com.zup.proposta.dto;

import br.com.zup.proposta.model.Blocked;

import java.time.LocalDateTime;

public class BlockedDto {

    private String id;
    private LocalDateTime blockedAt;
    private String responsibleSystem;
    private boolean active;

    public BlockedDto(Blocked blocked) {
        this.id = blocked.getId();
        this.blockedAt = blocked.getBlockedAt();
        this.responsibleSystem = blocked.getResponsibleSystem();
        this.active = blocked.isActive();
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getBlockedAt() {
        return blockedAt;
    }

    public String getResponsibleSystem() {
        return responsibleSystem;
    }

    public boolean isActive() {
        return active;
    }
}
