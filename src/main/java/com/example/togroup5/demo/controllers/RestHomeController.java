package com.example.togroup5.demo.controllers;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.AppUser;
import com.example.togroup5.demo.servicies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

import static com.example.togroup5.demo.utils.EncryptedPasswordUtils.encryptePassword;

@RestController
public class RestHomeController{

    @Autowired
    private UserService userService;

    @GetMapping(value = "/resthome")
    public String resthome(){
        return "ciao";
    }

    @GetMapping(value = "/User/createUser")
    public void createAllGroup(){
        //(String userName, String encrytedPassword, String userEmail, boolean enabled)
        AppUser user = new AppUser("dbuser1",encryptePassword("123"),"lol@lol.it",true);
        userService.save(user);
        user = new AppUser("dbadmin1",encryptePassword("123"),"lol@lolool.it",true);
        userService.save(user);

    }

    @GetMapping(value= "/listaUtenti")
    public List<AppUser> userList(Model model){
        List<AppUser> allUser = userService.findAll();
        return allUser;
    }

}
