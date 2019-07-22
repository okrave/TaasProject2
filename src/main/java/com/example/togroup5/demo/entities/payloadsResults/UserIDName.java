package com.example.togroup5.demo.entities.payloadsResults;

import java.io.Serializable;

public class UserIDName implements Serializable {
    private Long userId;
    private String userName;


    public UserIDName(){}

    public UserIDName(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
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

    @Override
    public String toString() {
        return "UserIDName{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
