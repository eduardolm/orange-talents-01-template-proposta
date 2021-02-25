package br.com.zup.proposta.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackages = "br.com.zup.proposta.*")
@PropertySource("classpath:application.properties")
public class EnvironmentProperties implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public String load(String propertyName)
    {
        return environment.getProperty(propertyName);
    }
}