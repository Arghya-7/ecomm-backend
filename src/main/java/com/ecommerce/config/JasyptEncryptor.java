package com.ecommerce.config;

import org.jasypt.util.text.AES256TextEncryptor;

import java.time.LocalDateTime;

public class JasyptEncryptor {
    public static void main(String[] args) {
        AES256TextEncryptor encryptor = new AES256TextEncryptor();
        encryptor.setPassword("Arghyadey@789"); // your master key
        String encrypted = encryptor.encrypt("mongodb://mongo:UMfJzWIEMoeDBsnHggkTQUGzAZeurJSD@metro.proxy.rlwy.net:14410/ecommerce?authSource=admin");
        System.out.println(encrypted);
    }
}
