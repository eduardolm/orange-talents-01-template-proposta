package br.com.zup.proposta.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

    @Value("${crypto.secret.key}")
    private String myKey;

    @Bean
    public String getMyKey() {
        return myKey;
    }
}
