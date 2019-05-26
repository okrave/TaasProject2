package com.example.togroup5.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value="/")
    public String index(){
        return "index";
    }

    @GetMapping(value="/home")
    public String home(){
        return "home";
    }

    @GetMapping(value="/client_home")
    public String client_home(){
        return "client_home";
    }

    @GetMapping(value="/login")
    public String login(){
        return "login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/user")
    public String user() {
        return "user/index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError",true);
        return "login";
    }



}
