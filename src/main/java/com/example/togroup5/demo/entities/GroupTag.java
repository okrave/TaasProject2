package com.example.togroup5.demo.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class GroupTag implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    /*
    @ManyToOne//(fetch = FetchType.LAZY)
    private AppGroup appGroup;

    @ManyToOne//(fetch = FetchType.LAZY)
    private AppTag appTag;

    public GroupTag(AppGroup appGroup, AppTag appTag){
        this.appGroup = appGroup;
        this.appTag = appTag;
    }
     */

    /*
     */

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "tag_id")
    private Long tagId;

    public GroupTag(Long groupId, Long tagId) {
        this.setGroupId(groupId);
        this.setTagId(tagId);
    }


    public GroupTag() {
    }

    //

    /*
    public GroupTag(AppGroup appGroup, AppTag appTag) {
        this.appGroup = appGroup;
        this.appTag = appTag;
    }
*/

    //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*
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


    public Long getGroupId() {
        return appGroup.getGroupId();
    }

    public Long getTagId() {
        return appTag.getTagId();
    }
*/

   /*
    */
    public Long getGroupId() {
        return groupId;
    }

    public Long getTagId() {
        return tagId;
    }
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
