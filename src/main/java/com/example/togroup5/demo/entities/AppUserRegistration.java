package com.example.togroup5.demo.entities;

import com.example.togroup5.demo.utils.EncryptedPasswordUtils;

public class AppUserRegistration {

    private String userName;
    private String password;
    private String repeatPassword;
    private String email;

    public AppUserRegistration(){}

    public AppUserRegistration(String userName, String password, String repeatPassword, String email) {
        if(!password.equals(repeatPassword))
            throw new IllegalArgumentException("Password non combaciano");

        this.setUserName(userName);
        this.setPassword(password);
        this.setRepeatPassword(repeatPassword);
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

    public String toString(){
        return "userName: " + this.userName+ "password: " + this.password + "email: "  + this.email;

    }
}
