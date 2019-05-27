package com.example.togroup5.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestHomeController{

    @GetMapping(value = "/resthome")
    public String resthome(){
        return "ciao";
    }
}
