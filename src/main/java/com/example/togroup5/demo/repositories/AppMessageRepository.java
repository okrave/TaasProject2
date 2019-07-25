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
@Transactional
public class AppMessageRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AppMessageJpa appMessageJpa;


    public void save(AppMessage message) {
        appMessageJpa.save(message);
    }

    public List<AppMessage> findAll() {
        return appMessageJpa.findAll();
    }

    public void removeAll() {
        appMessageJpa.deleteAll();
    }


    public List<AppMessage> findAllByGroupId(Long groupId) {
        return findAppMessageByGroupId(groupId);
    }

    public AppMessage findAppMessageById(Long messageId) {
        return appMessageJpa.getOne(messageId);
    }

    public List<AppMessage> findAppMessageByGroupId(Long groupId) {
        String sql = "Select e from " + AppMessage.class.getName() + " e " //
                + " Where e.groupId= :groupId ";
        Query query = entityManager.createQuery(sql, AppMessage.class);
        query.setParameter("groupId", groupId);
        return query.getResultList();
    }

    public List<AppMessage> findAppMessageByUserId(Long userId) {
        String sql = "Select e from " + AppMessage.class.getName() + " e " //
                + " Where e.userId = :userId ";
        Query query = entityManager.createQuery(sql, AppMessage.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<AppMessage> fetchMessages(MessageQueryPayload msgQuery) {
        try {
            StringBuilder sb;
            Query query;
            sb = new StringBuilder(128);
            sb.append("SELECT DISTINCT m FROM " ).append(AppMessage.class.getName() ).append(
                     " m WHERE m.groupId = :groupId");
            if (msgQuery.getDateStart() != null)
                sb.append( " AND m.dateCreation >= :dateStart");
            sb.append(" GROUP BY m ORDER BY m.dateCreation, m.messId DESC ");

            query = entityManager.createQuery(sb.toString(), AppMessage.class);
            sb = null;
            query.setParameter("groupId", msgQuery.getGroupId());
            if (msgQuery.getDateStart() != null)
                query.setParameter("dateStart", msgQuery.getDateStart());
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}