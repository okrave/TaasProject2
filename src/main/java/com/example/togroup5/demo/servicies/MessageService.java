package com.example.togroup5.demo.servicies;

import com.example.togroup5.demo.entities.AppMessage;
import com.example.togroup5.demo.entities.payloadsResults.MessageQueryPayload;
import com.example.togroup5.demo.repositories.AppMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private AppMessageRepository appMessageRepository;

    public void save(){
        AppMessage x = new AppMessage("Primo messaggio",Long.valueOf(1), Long.valueOf(2), new Date(System.currentTimeMillis()));
        this.appMessageRepository.save(x);
    }

    public List<AppMessage> findAll(){
        return appMessageRepository.findAll();
    }

    public AppMessage findAppMessageById(Long userId){
        return appMessageRepository.findAppMessageById(userId);
    }

    public List<AppMessage> findAppMessageByGroupId(Long groupId){
        return appMessageRepository.findAppMessageByGroupId(groupId);
    }

    public List<AppMessage> findAppMessageByUserId(Long userId){
        return appMessageRepository.findAppMessageByUserId(userId);
    }

    public boolean saveMessage(AppMessage m){
        appMessageRepository.save(m);
        return true;
    }

    public List<AppMessage> fetchMessages(MessageQueryPayload msgQuery){
        return appMessageRepository.fetchMessages(msgQuery);
    }
}
