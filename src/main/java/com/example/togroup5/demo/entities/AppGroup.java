package com.example.togroup5.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class AppGroup implements Serializable {

    @Id
    @GeneratedValue
    private Long groupId;

    private Long creatorId;

    private String creator;

    @Column(unique = true)
    private String groupName;

    private java.sql.Date groupDate;

    //@ManyToOne(fetch = FetchType.LAZY)
    //private GoogleLocation location; // use GOOGLE MAPS's documentation
    private Long locationId;

    private String description;


    //

    public AppGroup() {}

    public AppGroup(Long creatorId, String creator, String groupName, String description, Date groupDate) {
        this.creatorId = creatorId;
        this.groupName = groupName;
        this.description = description;
        this.groupDate = groupDate;
        this.creator = creator;
        this.locationId = null;
    }

    //

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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

    public Long getLocation() {
        return locationId;
    }

    public void setLocation(Long locationId) {
        this.locationId = locationId;
    }

    //

    @Override
    public String toString() {
        return "AppGroup{" +
                "groupId='" + groupId +
                ", creator='" + creator + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", groupDate='" + groupDate +
                ", locationId='" + locationId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
