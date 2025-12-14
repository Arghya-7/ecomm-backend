package com.ecommerce.config;

import org.jasypt.util.text.AES256TextEncryptor;

import java.time.LocalDateTime;

public class JasyptEncryptor {
    public static void main(String[] args) {
        AES256TextEncryptor encryptor = new AES256TextEncryptor();
        encryptor.setPassword("Arghyadey@789"); // your master key
        String encrypted = encryptor.encrypt("5367566859703373367639792F423F452848284D6251655468576D5A71347437");
        System.out.println(encrypted);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime afterAddingFiveMints = LocalDateTime.now().plusMinutes(2);
        System.out.println(now.isBefore(afterAddingFiveMints));
    }
}
