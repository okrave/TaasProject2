package com.example.togroup5.demo.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity

public class AppGroup {

    @Id
    @GeneratedValue
    private long groupId;


    private String groupName;


    private String description;


    private java.sql.Date groupDate;

    private String creator;

    public AppGroup() {}

    public AppGroup(String groupName, String description, Date groupDate, String creator) {
        this.groupName = groupName;
        this.description = description;
        this.groupDate = groupDate;
        this.creator = creator;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
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

    public void setCreator(String groupCreator) {
        this.creator = groupCreator;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String toString(){
        return " "+ this.groupName+" "+ this.description+" " + this.groupDate.toString();
    }

}
