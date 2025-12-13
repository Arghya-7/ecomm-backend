package com.ecommerce.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;

@Component
public class EncryptionService {
    private static final String ALGO = "AES";

    // Store in environment variable or secrets vault
    @Value("${app.secret.key}")
    private String secretKey;

    private SecretKeySpec getKey() {
        return new SecretKeySpec(secretKey.getBytes(), ALGO);
    }

    public String encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
    }

    public String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));
    }
}
