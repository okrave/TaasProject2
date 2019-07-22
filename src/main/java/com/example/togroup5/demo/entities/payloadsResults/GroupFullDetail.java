package com.example.togroup5.demo.entities.payloadsResults;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.AppTag;
import com.example.togroup5.demo.entities.AppUser;
import com.example.togroup5.demo.entities.GoogleLocation;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class GroupFullDetail implements Serializable {

    private Long groupId, creatorId;

    private String groupName, description, creator;

    private java.sql.Date groupDate;

    private GoogleLocation location;

    private List<AppTag> tags;

    private List<AppUser> members;

    public GroupFullDetail(AppGroup g, List<AppTag> t, List<AppUser> members, GoogleLocation location) {
        this.groupId = g.getGroupId();
        this.creatorId = g.getCreatorId();
        this.groupName = g.getGroupName();
        this.description = g.getDescription();
        this.groupDate = g.getGroupDate();
        this.creator = g.getCreator();
        this.location = location;
        this.tags = t;
        this.members = members;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(Date groupDate) {
        this.groupDate = groupDate;
    }

    public GoogleLocation getLocation() {
        return location;
    }

    public void setLocation(GoogleLocation location) {
        this.location = location;
    }

    public List<AppTag> getTags() {
        return tags;
    }

    public void setTags(List<AppTag> tags) {
        this.tags = tags;
    }

    public List<AppUser> getMembers() {
        return members;
    }

    public void setMembers(List<AppUser> members) {
        this.members = members;
    }

    //


    @Override
    public String toString() {
        return "GroupWithTags{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", description='" + description + '\'' +
                ", creator='" + creator + '\'' +
                ", groupDate=" + groupDate +
                ", location=" + location +
                ", tags=" + Arrays.toString(tags.toArray()) +
                ", members=" + Arrays.toString(members.toArray()) +
                '}';
    }
}