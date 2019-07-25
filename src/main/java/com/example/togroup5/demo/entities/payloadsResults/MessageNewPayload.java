package com.example.togroup5.demo.entities.payloadsResults;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class MessageNewPayload implements Serializable {
    Long userId, groupId;
    String testo;

    @JsonCreator
    public MessageNewPayload(
            @JsonProperty("userId") Long userId,//
            @JsonProperty("groupId") Long groupId,//
            @JsonProperty("testo") String testo) {
        this.userId = userId;
        this.groupId = groupId;
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

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    @Override
    public String toString() {
        return "MessageNewPayload{" +
                "userId=" + userId +
                ", groupId=" + groupId +
                ", testo='" + testo + '\'' +
                '}';
    }
}