package br.com.zup.proposta.dto;

import br.com.zup.proposta.model.BiometryImage;

import java.time.LocalDateTime;

public class BiometryImageDetailsDto {

    private Long id;
    private String originalFileName;
    private LocalDateTime createdAt;

    public BiometryImageDetailsDto(BiometryImage biometryImage) {
        this.id = biometryImage.getId();
        this.originalFileName = biometryImage.getOriginalFileName();
        this.createdAt = biometryImage.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
