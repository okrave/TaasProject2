package com.example.togroup5.demo.entities.payloadsResults;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Date;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageQueryPayload implements Serializable {

    @JsonProperty("groupId")
    private Long groupId;

    @JsonProperty("dateStart")
    private java.sql.Date dateStart;

    //

    public MessageQueryPayload() {
    }

    @JsonCreator
    public MessageQueryPayload(
            @JsonProperty("groupId") Long groupId,
            @JsonProperty("dateStart") Date dateStart) {
        this.groupId = groupId;
        this.dateStart = dateStart;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
}
