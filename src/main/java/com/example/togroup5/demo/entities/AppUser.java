package com.example.togroup5.demo.entities;

import com.example.togroup5.demo.utils.EncryptedPasswordUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
public class AppUser {

    @Id
    @GeneratedValue
    private Long userId;

    @Column(unique = true)
    private String userName;

    private String encrytedPassword;

    private String userEmail;

    private Integer enabled;

    public AppUser (){}

    @JsonCreator
    public AppUser(
            @JsonProperty("userName")   String userName,
            @JsonProperty("password")   String encrytedPassword,
            @JsonProperty("email")      String userEmail,
            @JsonProperty("enabled")    boolean enabled) {
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.userEmail  = userEmail;
        this.enabled = enabled?1:0;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }

    public Integer isEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getuserEmail() {
        return userEmail;
    }

    public void setuserEmail(String userEmail) {
        userEmail = userEmail;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public String toString(){
        return "{userName:"+ this.userEmail+ ", password: "+ this.encrytedPassword  +"}";
    }
}
