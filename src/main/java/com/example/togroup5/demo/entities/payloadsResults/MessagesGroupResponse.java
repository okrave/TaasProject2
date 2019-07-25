package com.example.togroup5.demo.entities.payloadsResults;

import com.example.togroup5.demo.entities.AppMessage;

import java.io.Serializable;
import java.util.List;

public class MessagesGroupResponse implements Serializable {

    private Long groupId;

    private List<MessageSent> messages;

    public MessagesGroupResponse(Long groupId, List<MessageSent> msgs) {
        this.groupId = groupId;
        this.messages = msgs;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public List<MessageSent> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageSent> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "MessagesGroupResponse{" +
                "groupId=" + groupId +
                ", messages=" + messages +
                '}';
    }
}