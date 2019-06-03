package com.example.togroup5.demo.controllers;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.repositories.AppGroupRepository;
import com.example.togroup5.demo.servicies.GroupService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


import org.slf4j.Logger;

import javax.annotation.PostConstruct;

@Component
public class Bot extends TelegramLongPollingBot {


    @Override
    public String getBotToken() {
        return "781537653:AAEchX7A3HFBMYicEZrSU57W_lNwr_i-lwU";
    }

    @Override
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();
        if(message != null && message.hasText()){
            switch(message.getText()){
                case ("/help"):
                    sendMsg(message,"Help page");
                    break;
                case ("/list"):
                    sendMsg(message,"Entrato");
                    break;
            }
        }
    }

    private void sendMsg(Message message, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(s);
        try{
            sendMessage(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "groupTaasBot";
    }


}
