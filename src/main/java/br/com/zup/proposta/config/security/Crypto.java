package br.com.zup.proposta.config.security;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class Crypto implements AttributeConverter<String, String> {

    @Autowired
    private CryptographyMethods crypto;

    @Override
    public String convertToDatabaseColumn(String s) {
        return crypto.convertToDatabaseColumn(s);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return crypto.convertToEntityAttribute(s);
    }
}
