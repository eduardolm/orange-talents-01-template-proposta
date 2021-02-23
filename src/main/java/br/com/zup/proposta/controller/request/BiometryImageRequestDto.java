package br.com.zup.proposta.controller.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BiometryImageRequestDto {

    @NotNull(message = "Item obrigatório.")
    private MultipartFile image;

    public BiometryImageRequestDto(@Size(min = 1, message = "Obrigatório enviar pelo menos uma imagem da biometria.")
                                   @NotNull(message = "Item obrigatório.") MultipartFile image) {

        this.image = image;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImages(MultipartFile image) {
        this.image = image;
    }
}
