package com.ecommerce.components;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class EnvPrinter {
    @PostConstruct
    public void print() {
        System.out.println("Profile: " + System.getenv("SPRING_PROFILES_ACTIVE"));
        System.out.println("Jasypt: " + System.getenv("JASYPT_ENCRYPTOR_PASSWORD"));
        System.out.println("Mongo: " + System.getenv("MONGO_URL"));
    }
}
