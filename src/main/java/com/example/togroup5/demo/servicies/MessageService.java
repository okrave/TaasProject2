package com.example.togroup5.demo.servicies;

import com.example.togroup5.demo.entities.AppMessage;
import com.example.togroup5.demo.repositories.AppMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MessageService {

    @Autowired
    private AppMessageRepository appMessageRepository;

    public void save(){
        AppMessage x = new AppMessage("Primo messaggio",new Long(1),new Long(2));
        this.appMessageRepository.save(x);
    }

    public AppMessage findAppMessageByID(Long messageId){
        return appMessageRepository.findAppMessageByID(messageId);
    }

    public List<AppMessage> findAll(){
        return appMessageRepository.findAll();
    }

    public List<AppMessage> findAppMessageByGroupId(Long groupId){
        return appMessageRepository.findAppMessageByGroupId(groupId);
    }

    public List<AppMessage> findAppMessageByUserId(Long userId){
        return appMessageRepository.findAppMessageByUserId(userId);
    }
}
