package com.example.togroup5.demo.repositories;


import com.example.togroup5.demo.entities.AppMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Max;
import java.util.List;

@Repository("AppMessageRepository")
@Transactional
public class AppMessageRepository {

    @Autowired
    private AppMessageJpa appMessageJpa;

    public void save(AppMessage message){
        appMessageJpa.save(message);
    }

    public List<AppMessage> findAll(){
        return appMessageJpa.findAll();
    }

    public AppMessage findAppMessageByID(Long messageId){
        return appMessageJpa.getOne(messageId);
    }

    public AppMessage findAppMessageByGroupID(Long groupId){
       return null;
    }
}
