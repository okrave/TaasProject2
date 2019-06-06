package com.example.togroup5.demo.entities.newEntities;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.AppUser;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class AppGroupNew {

    private String groupName;

    private String description;

    private java.sql.Date groupDate;

    private String creator;

    //

    public AppGroupNew() {}

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

    //

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(Date groupDate) {
        this.groupDate = groupDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "AppGroupNew{" +
                "groupName='" + groupName + '\'' +
                ", description='" + description + '\'' +
                ", groupDate=" + groupDate +
                ", creator='" + creator + '\'' +
                '}';
    }

    public AppGroup toAppGrou(){
        return new AppGroup(groupName, description, groupDate, creator);
    }
}
