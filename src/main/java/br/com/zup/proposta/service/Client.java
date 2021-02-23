package br.com.zup.proposta.service;

import feign.Response;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@Service
@FeignClient(name = "login", url = "http://localhost:18080/auth/realms/nosso-cartao/protocol/openid-connect/token", configuration = Client.Configuration.class)
public interface Client {

    @PostMapping(consumes = APPLICATION_FORM_URLENCODED_VALUE, headers = "application/x-www-formurlencoded")
    Response login(@RequestParam Map<String, String> form);

    class Configuration {

        @Bean
        Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> converters) {
            return new SpringFormEncoder(new SpringEncoder(converters));
        }
    }
}
