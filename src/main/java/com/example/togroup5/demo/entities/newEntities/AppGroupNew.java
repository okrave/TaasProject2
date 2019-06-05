package com.example.togroup5.demo.entities.newEntities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class AppGroupNew {

    private String groupName;

    private String description;

    private java.sql.Date groupDate;

    private String creator;

    @JsonCreator
    public AppGroupNew(
            @JsonProperty("groupName")      String groupName,
            @JsonProperty("description")    String description,
            @JsonProperty("groupDate")      Date groupDate,
            @JsonProperty("creator")        String creator) {
        this.groupName = groupName;
        this.description = description;
        this.groupDate = groupDate;
        this.creator = creator;
    }

    public AppGroupNew() {
    }
}
