package br.com.zup.proposta.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Component
public class CryptographyMethods{

    @Value("${crypto.secret.key}")
    private String secretKey;

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private byte[] KEY;

    public String convertToDatabaseColumn(String inputValue) {
        KEY = convertToByte(this.secretKey);
        Key key = new SecretKeySpec(KEY, "AES");
        try {
            final Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(c.doFinal(inputValue.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String convertToEntityAttribute(String dbData) {
        KEY = convertToByte(this.secretKey);
        Key key = new SecretKeySpec(KEY, "AES");
        try {
            final Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            return new String(c.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] convertToByte(String key) {
        return key.getBytes();
    }
}
