package br.com.zup.proposta.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@SpringBootTest
@TestConfiguration
@ActiveProfiles("test")
public class WireMockConfig  {


    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockAnalysisClient() {
        return new WireMockServer(options().dynamicPort());
    }
}
