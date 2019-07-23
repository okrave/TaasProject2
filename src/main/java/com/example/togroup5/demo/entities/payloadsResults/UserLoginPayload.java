package com.example.togroup5.demo.entities.payloadsResults;

import com.example.togroup5.demo.utils.EncryptedPasswordUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserLoginPayload implements Serializable {
    private String email, password;


    public UserLoginPayload() {
    }

    @JsonCreator
    public UserLoginPayload(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginPayload{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}