package com.example.togroup5.demo.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GroupTag {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppGroup appGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppTag appTag;

    //

    public GroupTag(){}

    public GroupTag(AppGroup appGroup, AppTag appTag) {
        this.appGroup = appGroup;
        this.appTag = appTag;
    }

    //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppGroup getAppGroup() {
        return appGroup;
    }

    public void setAppGroup(AppGroup appGroup) {
        this.appGroup = appGroup;
    }

    public AppTag getAppTag() {
        return appTag;
    }

    public void setAppTag(AppTag appTag) {
        this.appTag = appTag;
    }
}
