package br.com.zup.proposta.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "imagens_biometria")
public class BiometryImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Valid
    private CreditCard creditCard;

    @Lob
    @Column(name = "imagem", columnDefinition = "LONGBLOB", unique = true)
    private String imageFile;
    private String originalFileName;
    private LocalDateTime createdAt;

    @Deprecated
    public BiometryImage() {}

    public BiometryImage(@NotNull @Valid CreditCard creditCard, @NotBlank String imageFile, String originalFileName) {
        this.creditCard = creditCard;
        this.imageFile = imageFile;
        this.originalFileName = originalFileName;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "BiometryImage{" +
                "id=" + id +
                ", creditCard=" + creditCard.getId() +
                ", imageFile='" + imageFile + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BiometryImage)) return false;

        BiometryImage that = (BiometryImage) o;

        if (getImageFile() != null ? !getImageFile().equals(that.getImageFile()) : that.getImageFile() != null)
            return false;
        if (getOriginalFileName() != null ? !getOriginalFileName().equals(that.getOriginalFileName()) : that.getOriginalFileName() != null)
            return false;
        return getCreatedAt() != null ? getCreatedAt().equals(that.getCreatedAt()) : that.getCreatedAt() == null;
    }

    @Override
    public int hashCode() {
        int result = getImageFile() != null ? getImageFile().hashCode() : 0;
        result = 31 * result + (getOriginalFileName() != null ? getOriginalFileName().hashCode() : 0);
        result = 31 * result + (getCreatedAt() != null ? getCreatedAt().hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public String getImageFile() {
        return imageFile;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
