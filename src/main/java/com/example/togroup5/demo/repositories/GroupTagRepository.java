package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.AppTag;
import com.example.togroup5.demo.entities.GroupTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class GroupTagRepository {

    @Autowired
    private GroupTagJpa groupTagJpa;

    @Autowired
    private EntityManager entityManager;


    //basic methods

    public void save(GroupTag group) {
        //AppGroup supportUser =
        groupTagJpa.save(group);
    }

    public boolean delete(Long gtId) {
        groupTagJpa.deleteById(gtId);
        return true;
    }

    // query

    public GroupTag findGroupTagByID(Long gtId) {
        return groupTagJpa.getOne(gtId);
    }

    public List<GroupTag> findAll() {
        return groupTagJpa.findAll();
    }

    public List<GroupTag> findGroupTagsByGroupId(Long groupId){
        try {
            String sql = "Select gt from " + GroupTag.class.getName() + " gt JOIN " //
                    + AppGroup.class.getName() + " g ON gt.groupId = :groupId";
            Query query = entityManager.createQuery(sql, GroupTag.class);
            query.setParameter("groupId", groupId);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    public GroupTag findGroupTagsByGroupIdAndTagId(Long groupId, Long tagId){
        try {
            String sql = "Select gt from " + GroupTag.class.getName() + " gt JOIN " //
                    + AppGroup.class.getName() + " g ON gt.groupId = :groupId WHERE gt.tagId = :tagId";
            Query query = entityManager.createQuery(sql, GroupTag.class);
            query.setParameter("groupId", groupId);
            query.setParameter("tagId", tagId);
            return (GroupTag) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
