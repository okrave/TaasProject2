package com.example.togroup5.demo.controllers;

import com.example.togroup5.demo.entities.AppUser;
import com.example.togroup5.demo.servicies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestHomeController{

    @Autowired
    private UserService userService;

    @GetMapping(value = "/resthome")
    public String resthome(){
        return "ciao";
    }

    @GetMapping(value = "/ping")
    public boolean ping(){
        return true;
    }


}
