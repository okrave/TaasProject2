package com.example.togroup5.demo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptedPasswordUtils {

    // Encryte Password with BCryptPasswordEncoder
    public static String encryptePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("----------------------------------------ASD: "+ password);
        return encoder.encode(password);
    }
}
