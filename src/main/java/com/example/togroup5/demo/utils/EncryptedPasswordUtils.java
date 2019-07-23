package com.example.togroup5.demo.utils;

import com.example.togroup5.demo.entities.AppUser;
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
        return encoder.encode(password);
    }

    public static boolean testPassword(String rawPassword, AppUser user){
        return encoder.matches(rawPassword, user.getEncryptedPassword());
    }

}
