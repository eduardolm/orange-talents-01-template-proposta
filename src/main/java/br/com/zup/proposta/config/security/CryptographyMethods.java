package br.com.zup.proposta.config.security;

import br.com.zup.proposta.config.EnvironmentProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Component
public class CryptographyMethods{

    @Resource
    private EnvironmentProperties env;

    private final String ALGORITHM = "AES/ECB/PKCS5Padding";

    private byte[] KEY = "MySuperSecretKey".getBytes();

    public String convertToDatabaseColumn(String inputValue) {
        var teste = env.load("crypto.secret.key");
        Key key = new SecretKeySpec(KEY, "AES");
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(c.doFinal(inputValue.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String convertToEntityAttribute(String dbData) {
        Key key = new SecretKeySpec(KEY, "AES");
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedBytes = Base64.getDecoder().decode(dbData);
            return new String(c.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
