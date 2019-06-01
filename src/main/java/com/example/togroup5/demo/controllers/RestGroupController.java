package com.example.togroup5.demo.controllers;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.AppTag;
import com.example.togroup5.demo.servicies.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestGroupController {

    @Autowired
    GroupService groupService;

    @GetMapping(value = "/Group/listGroupRest")
    public List<AppGroup> listGroup(){
        List<AppGroup> allGroup = groupService.listAllGroup();
        return allGroup;
    }

    @GetMapping(value = "/Tag/listTagRest")
    public List<AppTag> listTagRest(){
        List<AppTag> allTag = groupService.listAllTag();
        return allTag;
    }
}