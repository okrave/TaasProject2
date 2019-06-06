package com.example.togroup5.demo.entities.payloads;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.util.List;

public class GroupSearchAdvPayload {

    private String groupName, creator, genre;

    private java.sql.Date dateStartRange, dateEndRange;

    private Double maxDistance;

    private List<String> tags;

    // private XXX location; use GOOGLE MAPS's documentation


    //

    public GroupSearchAdvPayload() {    }

    @JsonCreator
    public GroupSearchAdvPayload(
        @JsonProperty("creator")        String groupName,
        @JsonProperty("creator")        String creator,
        @JsonProperty("genre")          String genre,
        @JsonProperty("dateStartRange") Date dateStartRange,
        @JsonProperty("dateEndRange")   Date dateEndRange,
        @JsonProperty("maxDistance")    Double maxDistance,
        @JsonProperty("tags")           List<String> tags) {
        this.groupName = groupName;
        this.creator = creator;
        this.genre = genre;
        this.dateStartRange = dateStartRange;
        this.dateEndRange = dateEndRange;
        this.maxDistance = maxDistance;
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "GroupSearchAdvPayload{" +
                "groupName='" + groupName + '\'' +
                ", creator='" + creator + '\'' +
                ", genre='" + genre + '\'' +
                ", dateStartRange=" + dateStartRange +
                ", dateEndRange=" + dateEndRange +
                ", maxDistance=" + maxDistance +
                ", tags=" + tags +
                '}';
    }
}
