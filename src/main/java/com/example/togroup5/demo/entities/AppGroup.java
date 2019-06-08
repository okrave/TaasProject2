package com.example.togroup5.demo.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class AppGroup {

    @Id
    @GeneratedValue
    private long groupId;

    private String creator;

    @Column(unique = true)
    private String groupName;

    //@ManyToOne(fetch = FetchType.LAZY)
    //private GoogleLocation location; // use GOOGLE MAPS's documentation

    private java.sql.Date groupDate;

    private String description;


    // private String genre;

    // @questa-e'-una-roba-presa-da-un'-altra-entita'-non-so-come-linkarla o.o
    // private java.util.List<AppTag> tags; // IN REALTA' e' tutto risolto con la classe GroupTag

    //

    // private String genre;

    // @questa-e'-una-roba-presa-da-un'-altra-entita'-non-so-come-linkarla o.o
    // private java.util.List<AppTag> tags; // IN REALTA' e' tutto risolto con la classe GroupTag

    //

    public AppGroup() {}

    public AppGroup(String groupName, String description, Date groupDate, String creator) {
        this.groupName = groupName;
        this.description = description;
        this.groupDate = groupDate;
        this.creator = creator;
    }

    //

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
