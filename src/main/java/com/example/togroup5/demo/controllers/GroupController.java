package com.example.togroup5.demo.controllers;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.servicies.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GroupController {

    @Autowired
    GroupService groupService;

    @GetMapping(value = "/listGroup")
    public String listGroup(Model model){
        List<AppGroup> allGroup = groupService.listAllGroup();
        model.addAttribute("groups",allGroup);
        return "listaGroups";
    }

    @GetMapping(value = "/listGroupByCreator")
    public String listGroupByCreator(Model model){
        List<AppGroup> allGroup = groupService.listGroupByCreator();
        model.addAttribute("groups",allGroup);
        return "listaGroups";
    }

    @GetMapping(value = "/newGroup")
    public String newGroup(){
        return "newGroup";
    }

}
