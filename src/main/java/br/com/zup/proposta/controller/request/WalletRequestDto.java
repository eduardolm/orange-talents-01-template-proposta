package br.com.zup.proposta.controller.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class WalletRequestDto {

    @NotBlank(message = "E-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    private String carteira;

    public WalletRequestDto(@NotBlank(message = "E-mail é obrigatório.")
                            @Email(message = "Formato de e-mail inválido.") String email,
                            String carteira) {

        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }
}


