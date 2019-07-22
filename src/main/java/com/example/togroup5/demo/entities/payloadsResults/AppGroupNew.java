package com.example.togroup5.demo.entities.payloadsResults;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.AppUser;
import com.example.togroup5.demo.entities.GoogleLocation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;


public class AppGroupNew implements Serializable {

    private Long creatorId;

    private String groupName, description, creator;

    private java.sql.Date groupDate;

    private List<String> tags;

    private GoogleLocation location;

    //


    @JsonCreator
    public AppGroupNew(
            @JsonProperty("creatorId") Long creatorId,
            @JsonProperty("creator") String creator,
            @JsonProperty("groupName") String groupName,
            @JsonProperty("location") LocationReceived location,
            @JsonProperty("tags") List<String> tags,
            @JsonProperty("description") String description,
            @JsonProperty("groupDate") Date groupDate
    ) {
        this.creatorId = creatorId;
        this.groupName = groupName;
        this.description = description;
        this.groupDate = groupDate;
        this.creator = creator;
        this.location = location.toGoogleLocation();
        this.tags = tags;
    }

    //

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

    public AppGroup toAppGroup() {
        return new AppGroup(this.creatorId, creator, groupName, description, groupDate);
    }

    public GoogleLocation getLocation() {
        return location;
    }

    public void setLocation(GoogleLocation location) {
        this.location = location;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "AppGroupNew{" +
                "creatorId='" + creatorId + '\'' +
                ", creator='" + creator + '\'' +
                ", groupName='" + groupName + '\'' +
                ", location=" + location +
                ", groupDate=" + groupDate +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                '}';
    }

    //

    public static class LocationReceived {
        private double lat, lng;
        private String name;

        public LocationReceived(){
        }

        public LocationReceived(String toBeDeserialized){
            // format ('someValue')
            // example: ('40, 42.5')
            String[] vals;
            vals = toBeDeserialized.substring(toBeDeserialized.indexOf('\'')+1, toBeDeserialized.lastIndexOf('\'')).split(",");
            this.lat = Double.parseDouble(vals[0].trim());
            this.lng = Double.parseDouble(vals[1].trim());
        }


        @JsonCreator
        public LocationReceived(
                @JsonProperty("lat") double lat,
                @JsonProperty("lng") double lng,
                @JsonProperty("name") String name) {
            this.lat = lat;
            this.lng = lng;
            this.name = name;
        }

        public GoogleLocation toGoogleLocation() {
            return new GoogleLocation(lat, lng,name);
        }
    }
}
