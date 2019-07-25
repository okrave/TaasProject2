package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.GroupUser;
import com.example.togroup5.demo.entities.GroupUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class GroupUserRepository {

    @Autowired
    private GroupUserJpa groupUserJpa;

    @Autowired
    private EntityManager entityManager;


    //basic methods

    public void save(GroupUser gu) {
        //AppGroup supportUser =
        groupUserJpa.save(gu);
    }

    public boolean delete(Long guId) {
        groupUserJpa.deleteById(guId);
        return true;
    }

    // query

    public GroupUser findGroupUserByID(Long guId) {
        return groupUserJpa.getOne(guId);
    }

    public List<GroupUser> findAll() {
        return groupUserJpa.findAll();
    }

    public List<GroupUser> findGroupUsersByGroupId(Long groupId) {
        try {
            String sql = "Select gu from " + GroupUser.class.getName() + " gu JOIN " //
                    + AppGroup.class.getName() + " g ON gu.groupId = :groupId";
            Query query = entityManager.createQuery(sql, GroupUser.class);
            query.setParameter("groupId", groupId);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public GroupUser findGroupUserByGroupIdAndUserId(Long groupId, Long userId) {
        try {
            String sql = "Select gu from " + GroupUser.class.getName() + " gu JOIN " //
                    + AppGroup.class.getName() + " g ON gu.groupId = :groupId WHERE gu.userId = :userId";
            Query query = entityManager.createQuery(sql, GroupUser.class);
            query.setParameter("groupId", groupId);
            query.setParameter("userId", userId);
            return (GroupUser) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    public void deleteGroupUserByGroupIdAndUserId(Long groupId, Long userId) {
        GroupUser gu;
        gu = findGroupUserByGroupIdAndUserId(groupId, userId);
        if (gu != null)
            delete(gu.getId());
    }
}