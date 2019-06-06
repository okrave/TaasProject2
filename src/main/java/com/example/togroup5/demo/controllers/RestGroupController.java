package com.example.togroup5.demo.controllers;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.AppTag;
import com.example.togroup5.demo.entities.newEntities.AppGroupNew;
import com.example.togroup5.demo.entities.payloads.GroupSearchAdvPayload;
import com.example.togroup5.demo.servicies.GroupService;
import org.glassfish.jersey.jaxb.internal.XmlJaxbElementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping(value = "Group")
public class RestGroupController {

    @Autowired
    GroupService groupService;

    @GetMapping(value = "/listGroupRest")
    public List<AppGroup> listGroup() {
        List<AppGroup> allGroup = groupService.listAllGroup();
        return allGroup;
    }

    @GetMapping(value = "/listTagRest")
    public List<AppTag> listTagRest() {
        List<AppTag> allTag = groupService.listAllTag();
        return allTag;
    }

    @GetMapping(value = "/removeAllTags")
    public void removeAllTags() {
        groupService.removeAllTags();
    }

    @GetMapping(value = "/createAllTag")
    public void createAllTag() {
        String[] defaultTags;
        AppTag newTag;

        defaultTags = new String[]{
                "Vacanze", "Famiglia", "Serate di giochi", "Happy Hour", "Cibo", "Danza", "Relax", "Bere", "Alcolici",
                "Lavoro", "Cavalli", "Natura", "Divertimento", "Amici", "Sport", "TAASS", "Progetto", "Pranzo",
                "Università", "Studio"};

        for (String tag : defaultTags) {
            newTag = new AppTag(tag);
            groupService.saveTag(newTag);
        }
    }

    @GetMapping(value = "/createRandomGroup")
    public void createAllGroup() {
        //AppGroup(String groupName, String description, Date groupDate, String creator)
        AppGroup newGroup = new AppGroup("Gita a cavallo", "incredibile gita a cavallo", new Date(1995, 8, 7), "Luca");
        groupService.saveGroup(newGroup);
        newGroup = new AppGroup("Ballo di gruppo", "incredibile ballo i gruppo", new Date(1996, 8, 7), "Davide");
        groupService.saveGroup(newGroup);
        newGroup = new AppGroup("TarTAASSiamoci", "Lavoriamo al progetto!", Date.valueOf("2019-06-06"), "lonevetad");
        groupService.saveGroup(newGroup);
        newGroup = new AppGroup("IngrAASSiamo", "Si ragiona meglio a stomaco pieno: all you can eat!", Date.valueOf("2019-06-06"), "lonevetad");
        groupService.saveGroup(newGroup);
        newGroup = new AppGroup("Lindy Hop", "Un ballo di coppia stile anni 20-30-40, molto rilassante ma energico, per ben concludere la serata.", Date.valueOf("2019-06-06"), "lonevetad");
        groupService.saveGroup(newGroup);
        newGroup = new AppGroup("Cin Cin", "Bevuta rinfrescante a tema relax.", Date.valueOf("2019-06-07"), "Bender");
        groupService.saveGroup(newGroup);

    }

    @GetMapping(value = "/{groupName}")
    public List<AppGroup> findGroupByName(@PathVariable String groupName) {
        return groupService.findByGroupName(groupName);
    }


    @RequestMapping(value = "/createGroupString", method = RequestMethod.POST)
    public void newGroup(@RequestBody String appGroup) {
        System.out.println(appGroup);
        //groupService.saveGroup(appGroup);
    }

    @RequestMapping(value = "/createGroup", method = RequestMethod.POST)
    public void newGroup(@RequestBody AppGroupNew appGroupNew) {
        groupService.saveGroup(appGroupNew.toAppGrou());
    }


    @RequestMapping(value = "/advGroupSearch", method = RequestMethod.GET)
    public void searchGroupAdvanced(@RequestBody GroupSearchAdvPayload groupSearchFilters) {
        //groupService.saveGroup(appGroupNew.toAppGrou());
        System.out.println("search filters: ");
        System.out.println(groupSearchFilters);
    }

}