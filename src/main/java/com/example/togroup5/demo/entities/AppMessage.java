package com.example.togroup5.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class AppMessage implements Serializable {

    @Id
    @GeneratedValue
    private Long messId;

    private String testo;

    private Long userId;

    private Long groupId;

    public AppMessage(){}

    public AppMessage(String testo, Long userId, Long groupId) {
        this.testo = testo;
        this.userId = userId;
        this.groupId = groupId;
    }

    public Long getMessId() {
        return messId;
    }

    public void setMessId(Long messId) {
        this.messId = messId;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
