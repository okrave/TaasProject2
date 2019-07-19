package com.example.togroup5.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncryptedPasswordUtils {


    public static BCryptPasswordEncoder encoder;
    // Encryte Password with BCryptPasswordEncoder
    public EncryptedPasswordUtils(){
        this.encoder = new BCryptPasswordEncoder();
    }
    public static String encryptePassword(String password) {
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("----------------------------------------ASD: "+ password);
        System.out.println("----------------------------------------ASD:" + encoder.encode(password));

        System.out.println("----------------------------------------ASD:" + encoder.encode("ciccio"));
        System.out.println("----------------------------------------ASD:" + encoder.encode("ciccio"));
        return encoder.encode(password);
    }
}
