package com.example.togroup5.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeRestController {
    @RequestMapping("/user")
    public Principal user (Principal principal) {
        return principal;
    }
}

