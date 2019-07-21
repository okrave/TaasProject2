package com.example.togroup5.demo.entities.payloadsResults;

import com.example.togroup5.demo.entities.AppMessage;

import java.io.Serializable;
import java.sql.Date;

/**Classe utilizzata esclusivamente da {@link MessagesGroupResponse}, motivo per cui non si ripete il ridondante "groupId"*/
public class MessageSent implements Serializable {
    private Long messageId, userId;
    private String testo, userName;
    private Date dateCreation;

    public MessageSent(AppMessage m, String userName){
        this.messageId = m.getMessId();
        //this.groupId = m.getGroupId();
        this.userId = m.getUserId();
        this.dateCreation = m.getDateCreation();
        this.testo = m.getTesto();
        this.userName = userName;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /*
    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }
     */

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "MessageSent{" +
                "messageId=" + messageId +
                //", groupId=" + groupId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", dateCreation=" + dateCreation +
                ", testo='" + testo + '\'' +
                '}';
    }
}