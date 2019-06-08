package com.example.togroup5.demo.entities.payloads;

import com.example.togroup5.demo.entities.GoogleLocation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.util.List;

public class GroupSearchAdvPayload {

    private String groupName, creator; // genre

    private java.sql.Date dateStartRange, dateEndRange;

    private GoogleLocation location; // use GOOGLE MAPS's documentation

    private Double maxDistance;

    private List<String> tags;

    //

    public GroupSearchAdvPayload() {    }

    @JsonCreator
    public GroupSearchAdvPayload(
        @JsonProperty("creator")        String creator,
        @JsonProperty("groupName")      String groupName,
        @JsonProperty("location")       GoogleLocation location,
        //@JsonProperty("genre")        String genre,
        @JsonProperty("dateStartRange") Date dateStartRange,
        @JsonProperty("dateEndRange")   Date dateEndRange,
        @JsonProperty("maxDistance")    Double maxDistance,
        @JsonProperty("tags")           List<String> tags) {
        this.creator = creator;
        this.groupName = groupName;
        this.location = location;
        //this.genre = genre;
        this.dateStartRange = dateStartRange;
        this.dateEndRange = dateEndRange;
        this.maxDistance = maxDistance;
        this.tags = tags;
    }

    //


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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
                "creator='" + creator + '\'' +
                ", groupName='" + groupName + '\'' +
                ", location='" + location + '\'' +
                //", genre='" + genre + '\'' +
                ", dateStartRange=" + dateStartRange +
                ", dateEndRange=" + dateEndRange +
                ", maxDistance=" + maxDistance +
                ", tags=" + tags +
                '}';
    }
}
