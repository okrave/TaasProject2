package com.example.togroup5.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class AppTag implements Serializable {

    @Id
    @GeneratedValue
    private Long tagId;

    @Column(unique = true)
    private String name;

    public AppTag(){}

    public AppTag(String name){
        this.name = name;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
