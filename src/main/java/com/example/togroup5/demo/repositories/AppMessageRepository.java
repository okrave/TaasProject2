package com.example.togroup5.demo.repositories;


import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.AppMessage;
import com.example.togroup5.demo.entities.GroupUser;
import com.example.togroup5.demo.entities.payloadsResults.MessageQueryPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import javax.persistence.NoResultException;

import javax.persistence.Query;
import javax.validation.constraints.Max;
import java.util.List;
import java.util.Optional;

@Repository("AppMessageRepository")
public class AppMessageRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AppMessageJpa appMessageJpa;


    public void save(AppMessage message){
        appMessageJpa.save(message);
    }

    public List<AppMessage> findAll(){
        return appMessageJpa.findAll();
    }


    /*
    public AppMessage findAppMessageByID(Long messageId){ return appMessageJpa.getOne(messageId); }
    public List<AppMessage> findAppMessageByGroupID(Long groupId){ return appMessageJpa.findAllByGroupId(); }
    public List<AppMessage> findAppMessageByUserId(Long userId){ return appMessageJpa.findAllByUserId(); }
*/

    public AppMessage findAppMessageById(Long messageId){
        return appMessageJpa.getOne(messageId);
    }

    public List<AppMessage> findAppMessageByGroupId(Long groupId){
        String sql = "Select e from " + AppMessage.class.getName() + " e " //
                + " Where e.groupId= :groupId ";

        Query query = entityManager.createQuery(sql, AppMessage.class);
        query.setParameter("groupId", groupId);
        return query.getResultList();
    }

    public List<AppMessage> findAppMessageByUserId(Long userId){
        String sql = "Select e from " + AppMessage.class.getName() + " e " //
                + " Where e.userId = :userId ";
        Query query = entityManager.createQuery(sql, AppMessage.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<AppMessage> fetchMessages(MessageQueryPayload msgQuery){
        try {
            String sql ;
            Query query;
            sql = "SELECT m FROM " + AppMessage.class.getName() //
                    + " m WHERE m.groupId = :groupId";
            if(msgQuery.getDateStart() != null)
                sql = sql + " AND m.dateCreation >= :dateStart";

            query = entityManager.createQuery(sql, AppMessage.class);
            query.setParameter("groupId", msgQuery.getGroupId());
            if(msgQuery.getDateStart() != null)
                query.setParameter("dateStart", msgQuery.getDateStart());
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    
}
