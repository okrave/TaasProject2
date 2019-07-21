package com.example.togroup5.demo;

import com.example.togroup5.demo.controllers.Bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        //Per usare il bot telegram ricordarsi di decommentare
        /*ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi =  new TelegramBotsApi();
        try{
            telegramBotsApi.registerBot(new Bot());
        }catch (TelegramApiException e){
            e.printStackTrace();
        }*/
        SpringApplication.run(DemoApplication.class, args);
    }

}
