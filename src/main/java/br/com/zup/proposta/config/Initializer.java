package br.com.zup.proposta.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Initializer {

    private String storedKey;

    public Initializer(
            @Value("${crypto.secret.key}") String storedKey) {

        this.storedKey = storedKey;
    }

//    public Crypto initClass() {
//        return new Crypto(this.storedKey);
//    }
}
