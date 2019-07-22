package com.example.togroup5.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class AppMessage implements Serializable {

    @Id
    @GeneratedValue
    private Long messId;

    private Long groupId;

    private Long userId;

    private String testo;

    private Date dateCreation;

    public AppMessage(){}

    public AppMessage(String testo, Long userId, Long groupId, Date dateCreation) {
        this.testo = testo;
        this.userId = userId;
        this.groupId = groupId;
        this.dateCreation = dateCreation;
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


    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "AppMessage{" +
                "messId=" + messId +
                ", userId=" + userId +
                ", groupId=" + groupId +
                ", dateCreation=" + dateCreation +
                ", testo='" + testo + '\'' +
                '}';
    }

}
