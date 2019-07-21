package com.example.togroup5.demo.servicies;

import com.example.togroup5.demo.entities.AppMessage;
import com.example.togroup5.demo.repositories.AppMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private AppMessageRepository appMessageRepository;

    public void save(){
        AppMessage x = new AppMessage("Primo messaggio",new Long(1),new Long(2));
        this.appMessageRepository.save(x);
    }
}
