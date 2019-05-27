package com.example.togroup5.demo.entities;

import com.example.togroup5.demo.utils.EncryptedPasswordUtils;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "App_User", uniqueConstraints = { //
        @UniqueConstraint(name = "APP_USER_UK", columnNames = "User_Name") })
public class AppUser {

    @Id
    @GeneratedValue
    @Column(name = "User_Id", nullable = false)
    private Long userId;

    @Column(name = "User_Name", length = 36, nullable = false)
    private String userName;

    @Column(name = "Encryted_Password", length = 128, nullable = false)
    private String encrytedPassword;

    @Column(name = "User_Email", nullable = false)
    private String UserEmail;

    @Column(name = "Enabled", length = 1, nullable = false)
    private Integer enabled;

    public AppUser (){};

    public AppUser(String userName, String encrytedPassword, String userEmail, boolean enabled) {
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.UserEmail  = userEmail;
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

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public String toString(){
        return "{userName:"+ this.UserEmail+ ", password: "+ this.encrytedPassword  +"}";
    }
}
