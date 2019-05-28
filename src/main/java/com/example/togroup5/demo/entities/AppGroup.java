package com.example.togroup5.demo.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="app_group",
        uniqueConstraints = { //
        @UniqueConstraint(name = "APP_GROUP_UK", columnNames = { "GROUP_ID"}) })
public class AppGroup {

    @Id
    @GeneratedValue
    @Column(name = "GROUP_ID", nullable = false)
    private long groupId;

    @Column(name = "GROUP_NAME", nullable = false)
    private String groupName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DATE")
    private java.sql.Date groupDate;

    @JoinColumn(name = "CREATOR", nullable = false)
    private String creator;

    public AppGroup() {}

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


}
