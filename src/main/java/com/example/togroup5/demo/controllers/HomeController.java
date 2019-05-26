package com.example.togroup5.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value="/home")
    public String home(){
        return "home";
    }

    @GetMapping(value="/client_home")
    public String client_home(){
        return "client_home";
    }

    @GetMapping(value="/login")
    public String client_login(){
        return "login";
    }



}
