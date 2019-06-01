package com.example.togroup5.demo.controllers;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.AppTag;
import com.example.togroup5.demo.servicies.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
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

    @GetMapping(value = "/Tag/createAllTag")
    public void createAllTag(){
        AppTag newTag = new AppTag("Vacanze");
        groupService.saveTag(newTag);
        newTag = new AppTag("Famiglia");
        groupService.saveTag(newTag);
        newTag = new AppTag("Serate di giochi");
        groupService.saveTag(newTag);
        newTag = new AppTag("Happy Hour");
        groupService.saveTag(newTag);
        newTag = new AppTag("Cibo");
        groupService.saveTag(newTag);
        newTag = new AppTag("Studio");
        groupService.saveTag(newTag);
        newTag = new AppTag("Danza");
        groupService.saveTag(newTag);
    }

    @GetMapping(value = "/Group/createAllGroup")
    public void createAllGroup(){
        //AppGroup(String groupName, String description, Date groupDate, String creator)
        AppGroup newGroup = new AppGroup("Gita a cavallo","incredibile gita a cavallo",new Date(1995,8,7),"Luca");
        groupService.saveGroup(newGroup);
        newGroup = new AppGroup("Ballo di gruppo","incredibile ballo i gruppo",new Date(1996,8,7),"Davide");
        groupService.saveGroup(newGroup);

    }


}