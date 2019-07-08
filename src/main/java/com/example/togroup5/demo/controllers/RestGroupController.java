package com.example.togroup5.demo.controllers;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.AppTag;
import com.example.togroup5.demo.entities.GoogleLocation;
import com.example.togroup5.demo.entities.newEntities.AppGroupNew;
import com.example.togroup5.demo.entities.payloadsResults.GroupSearchAdvPayload;
import com.example.togroup5.demo.entities.payloadsResults.GroupWithTags;
import com.example.togroup5.demo.servicies.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "Group")
public class RestGroupController {

    @Autowired
    GroupService groupService;

    @GetMapping(value = "/listGroupSimple")
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


    @GetMapping(value = "/listGroupRest")
    public List<GroupWithTags> listGroupWithTags() {
        List<AppGroup> groups;
        List<GroupWithTags> gts;
        GroupWithTags gt;
        groups = this.listGroup();
        gts = groupWithTagsFromGroups(groups);
        return gts;
    }

    @GetMapping(value = "/listLocations")
    public List<GoogleLocation> listLocations() {
        return groupService.listAllGoogleLocation();
    }

    @GetMapping(value = "/{groupName}")
    public AppGroup findGroupByName(@PathVariable String groupName) {
        return groupService.findByGroupName(groupName);
    }


    @RequestMapping(value = "/createGroupString", method = RequestMethod.POST)
    public void newGroup(@RequestBody String appGroup) {
        System.out.println(appGroup);
        //groupService.saveGroup(appGroup);
    }

    @RequestMapping(value = "/createGroup", method = RequestMethod.POST)
    public boolean newGroup(@RequestBody AppGroupNew appGroupNew) {
        groupService.createGroup(appGroupNew);
        return true;
    }

    @GetMapping(value = "/info/{groupId}")
    public AppGroup infoGroup(@PathVariable String groupId ){
        System.out.println("groupId:" + groupId);
        AppGroup newGroup = groupService.findGroupById(new Long(groupId));
        System.out.println("Il gruppo:"+ newGroup.getGroupName());
        return newGroup;
    }

    @RequestMapping(value = "/advGroupSearch", method = RequestMethod.GET)
    public List<GroupWithTags> searchGroupAdvanced(@RequestBody GroupSearchAdvPayload groupSearchFilters) {
        List<AppGroup> groups;
        //groupService.saveGroup(appGroupNew.toAppGrou());
        System.out.println("search filters: ");
        System.out.println(groupSearchFilters);
        groups = groupService.searchGroupAdvanced(groupSearchFilters);
        System.out.println("found " + groups.size() + " groups, now add tags");
        return groupWithTagsFromGroups(groups);
    }


    //utils

    protected List<GroupWithTags> groupWithTagsFromGroups(List<AppGroup> groups) {
        List<GroupWithTags> gts;
        GroupWithTags gwt;
        if (groups == null) return null;
        gts = new ArrayList<>(groups.size());
        for (AppGroup g : groups){
            gwt = new GroupWithTags(g, groupService.listTagsByAppGroupId(g.getGroupId()));
            gwt.setLocation(groupService.findLocationById(g.getLocation()));
            gts.add(gwt);
        }
        return gts;
    }

    // crea i default

    //@RequestMapping(value = "/createAll", method = RequestMethod.POST)
    @PostMapping(value = "/createAll")
    public void createDefaults() {
        createAllTag();
        createAllGroup();
    }


    @PostMapping(value = "/createAllTag")
    public void createAllTag() {
        String[] defaultTags;
        AppTag newTag;

        defaultTags = new String[]{
                "Vacanze", "Famiglia", "Serate di giochi", "Happy Hour", "Cibo", "Danza", "Relax", "Bere", "Alcolici",
                "Lavoro", "Cavalli", "Natura", "Divertimento", "Amici", "Sport", "TAASS", "Progetto", "Pranzo",
                "Università", "Studio"};

        try {
            for (String tag : defaultTags) {
                newTag = new AppTag(tag.toLowerCase());
                groupService.saveTag(newTag);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @PostMapping(value = "/createRandomGroup")
    public void createAllGroup() {
        //AppGroup(String groupName, String description, Date groupDate, String creator)
        long id;
        String[][][] groupsDatas;
        String[] groupInfo, locationInfo, tagsInfo;

        AppGroupNew newGroup;
        /*
        AppTag tag;
        AppGroup group;
        GoogleLocation location;
        */
        AppGroupNew.LocationReceived location;
        List<String> tags;

        groupsDatas = new String[][][]{
                //
                new String[][]{
                        new String[]{ //group info
                                "Luca", "Gita a cavallo", "incredibile gita a cavallo", "1995-08-07"
                        },
                        new String[]{"('35.0, 40.0')"}, // location
                        new String[]{"divertimento", "amici", "natura", "cavalli", "vacanza", "vacanze"} //tags
                }
                , //
                new String[][]{
                        new String[]{ //group info
                                "Davide", "Ballo di gruppo", "incredibile ballo i gruppo", "1995-08-07"
                        },
                        new String[]{"('35.50, 40.50')"}, // location
                        new String[]{"divertimento", "amici", "ballo", "happy hour", "danza"} //tags
                }
                , //
                new String[][]{
                        new String[]{ //group info
                                "lonevetad", "TarTAASSiamoci", "Lavoriamo al progetto!", "2019-06-06"
                        },
                        new String[]{"('35.10, 40.10')"}, // location
                        new String[]{"progetto", "amici", "taass", "lavoro", "studio", "università"} //tags
                }
                , //
                new String[][]{
                        new String[]{ //group info
                                "lonevetad", "IngrAASSiamo", "Si ragiona meglio a stomaco pieno: all you can eat!", "2019-06-06"
                        },
                        new String[]{"('35.40, 39.8')"}, // location
                        new String[]{"divertimento", "amici", "relax", "chiacchiere", "pranzo", "cibo"} //tags
                }
                , //
                new String[][]{
                        new String[]{ //group info
                                "lonevetad", "Lindy Hop after-work event", "Un ballo di coppia stile anni 20-30-40, molto rilassante ma energico, per ben concludere la serata.", "2019-06-06"
                        },
                        new String[]{"('35.40, 39.8')"}, // location
                        new String[]{"divertimento", "amici", "ballo", "danza", "lindy hop", "relax"} //tags
                }
                , //
                new String[][]{
                        new String[]{ //group info
                                "Bender", "Cin Cin", "Bevuta rinfrescante a tema relax.", "2019-06-07"
                        },
                        new String[]{"('35.0, 40.0')"}, // location
                        new String[]{"bere", "divertimento", "amici", "happy hour", "alcool", "alcoolici", "bere"} //tags
                }
        };

        id = 0;
        for (String[][] newGroupInfo : groupsDatas) {
            groupInfo = newGroupInfo[0];
            locationInfo = newGroupInfo[1];
            tagsInfo = newGroupInfo[2];

            tags = new ArrayList<>(tagsInfo.length);
            for (String t : tagsInfo)
                tags.add(t);


            newGroup = new AppGroupNew(groupInfo[0], groupInfo[1], //
                    new AppGroupNew.LocationReceived(locationInfo[0]),//
                    tags, groupInfo[2], Date.valueOf(groupInfo[3]));
            newGroup.getLocation().setLocationId(id++);
            groupService.createGroup(newGroup);
        }
    }


}
