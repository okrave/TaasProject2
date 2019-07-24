package com.example.togroup5.demo.entities.payloadsResults;

import com.example.togroup5.demo.entities.GoogleLocation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class GroupSearchAdvPayload implements Serializable {

    private boolean isSearchingByCreator = true;

    private String groupName, creatorMember;

    private java.sql.Date dateStartRange, dateEndRange;

    private GoogleLocation location; // use GOOGLE MAPS's documentation

    private Double maxDistance;

    private List<String> tags;

    //

    public GroupSearchAdvPayload() {
    }

    @JsonCreator
    public GroupSearchAdvPayload(
            @JsonProperty("isSearchingByCreator") boolean isSearchingByCreator,
            @JsonProperty("creatorMember") String creatorMember,
            @JsonProperty("groupName") String groupName,
            @JsonProperty("location") GoogleLocation location,
            @JsonProperty("dateStartRange") Date dateStartRange,
            @JsonProperty("dateEndRange") Date dateEndRange,
            @JsonProperty("maxDistance") Double maxDistance, // in kilometri
            @JsonProperty("tags") List<String> tags) {
        this.isSearchingByCreator = isSearchingByCreator;
        this.creatorMember = creatorMember;
        this.groupName = groupName;
        this.location = location;
        //this.genre = genre;
        this.dateStartRange = dateStartRange;
        this.dateEndRange = dateEndRange;
        this.maxDistance = maxDistance;
        this.tags = tags;
    }

    //


    public boolean isSearchingByCreator() {
        return isSearchingByCreator;
    }

    public void setSearchingByCreator(boolean searchingByCreator) {
        isSearchingByCreator = searchingByCreator;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreatorMember() {
        return creatorMember;
    }

    public void setCreatorMember(String creatorMember) {
        this.creatorMember = creatorMember;
    }

    public Date getDateStartRange() {
        return dateStartRange;
    }

    public void setDateStartRange(Date dateStartRange) {
        this.dateStartRange = dateStartRange;
    }

    public Date getDateEndRange() {
        return dateEndRange;
    }

    public void setDateEndRange(Date dateEndRange) {
        this.dateEndRange = dateEndRange;
    }

    /** In kilometers*/
    public Double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public GoogleLocation getLocation() {
        return location;
    }

    public void setLocation(GoogleLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "GroupSearchAdvPayload{" +
                "isSearchingByCreator=" + isSearchingByCreator +
                ", creatorMember='" + creatorMember + '\'' +
                ", groupName='" + groupName + '\'' +
                ", location='" + location + '\'' +
                ", dateStartRange=" + dateStartRange +
                ", dateEndRange=" + dateEndRange +
                ", maxDistance=" + maxDistance +
                ", tags=" + tags +
                '}';
    }
}
