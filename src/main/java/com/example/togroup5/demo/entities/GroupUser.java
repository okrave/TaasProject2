package com.example.togroup5.demo.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "groupuser", uniqueConstraints={@UniqueConstraint(columnNames ={"group_id","user_id"})})
public class GroupUser implements Serializable {

    @Id
    @GeneratedValue
    private Long id;


    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "user_id")
    private Long userId;

    public GroupUser(){}

    public GroupUser(Long groupId, Long userId) {
        this.setGroupId(groupId);
        this.setUserId(userId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "GroupUser{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", userId=" + userId +
                '}';
    }
}
