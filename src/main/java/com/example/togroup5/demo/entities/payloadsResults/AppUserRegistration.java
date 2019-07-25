package com.example.togroup5.demo.entities.payloadsResults;

import com.example.togroup5.demo.entities.AppUser;
import com.example.togroup5.demo.utils.EncryptedPasswordUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AppUserRegistration implements Serializable {

    private String userName;
    private String password;
    private String repeatPassword;
    private String email;

    public AppUserRegistration(){}

    @JsonCreator
    public AppUserRegistration(
            @JsonProperty("userName")       String userName,
            @JsonProperty("password")       String password,
            @JsonProperty("passwordRepeat") String repeatPassword,
            @JsonProperty("email")          String email) {
        if(!password.equals(repeatPassword))
            throw new IllegalArgumentException("Password non combaciano");

        this.setUserName(userName);
        this.setPassword(password);
        this.repeatPassword = this.password; //faster and not-repeating than this.setRepeatPassword(repeatPassword);
        this.setEmail(email);

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = EncryptedPasswordUtils.encryptePassword(password);
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = EncryptedPasswordUtils.encryptePassword(repeatPassword);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "AppUserRegistration{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public AppUser toAppUser(){
        return new AppUser(userName, password, email, true);
    }
}
