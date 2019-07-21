package com.example.togroup5.demo.repositories;


import com.example.togroup5.demo.entities.AppMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.constraints.Max;
import java.util.List;
import java.util.Optional;

@Repository("AppMessageRepository")
public class AppMessageRepository {

    @Autowired
    private AppMessageJpa appMessageJpa;

    @Autowired
    private EntityManager entityManager;

    public void save(AppMessage message){
        appMessageJpa.save(message);
    }

    public List<AppMessage> findAll(){
        return appMessageJpa.findAll();
    }

    public AppMessage findAppMessageByID(Long messageId){
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
}
