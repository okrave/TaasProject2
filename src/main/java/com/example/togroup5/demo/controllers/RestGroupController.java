package com.example.togroup5.demo.controllers;

import com.example.togroup5.demo.entities.*;
import com.example.togroup5.demo.entities.newEntities.AppGroupNew;
import com.example.togroup5.demo.entities.payloadsResults.GroupSearchAdvPayload;
import com.example.togroup5.demo.entities.payloadsResults.GroupFullDetail;
import com.example.togroup5.demo.entities.payloadsResults.MemberGroupPayload;
import com.example.togroup5.demo.servicies.GroupService;
import com.example.togroup5.demo.servicies.UserService;
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
    @Autowired
    RestHomeController x;

    @Autowired
    UserService userService;

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
    public List<GroupFullDetail> listGroupFullDetail() {
        List<AppGroup> groups;
        List<GroupFullDetail> gfd;
        groups = this.listGroup();
        gfd = groupFullDetailFromGroups(groups);
        return gfd;
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
    public GroupFullDetail infoGroup(@PathVariable String groupId ){
        AppGroup newGroup ;
        GroupFullDetail gfd;
        System.out.println("groupId:" + groupId);
        newGroup = groupService.findGroupById(Long.valueOf(groupId));
        if(newGroup == null)
            return null;

        System.out.println("Il gruppo:"+ newGroup.getGroupName());
        gfd = fetchGroupDetails(newGroup);
        System.out.println(x.toString());
        return gfd;
    }

    @RequestMapping(value = "/advGroupSearch", method = RequestMethod.GET)
    public List<GroupFullDetail> searchGroupAdvanced(@RequestBody GroupSearchAdvPayload groupSearchFilters) {
        List<AppGroup> groups;
        //groupService.saveGroup(appGroupNew.toAppGrou());
        System.out.println("search filters: ");
        System.out.println(groupSearchFilters);
        groups = groupService.searchGroupAdvanced(groupSearchFilters);
        System.out.println("found " + groups.size() + " groups, now add tags");
        return groupFullDetailFromGroups(groups);
    }

    @RequestMapping(value = "/addMember", method = RequestMethod.PATCH)
    public boolean addUserToGroupMember(@RequestBody MemberGroupPayload userGroupInfo){
        System.out.println(userGroupInfo);
        UserGroupFound guf;
        guf = fetchGroupUser(userGroupInfo);
        if(guf == null){
            System.out.println("errore fetchGroupUser");
            return false; // error
        }
        if(guf.gu != null) return true; // yet present but it's not an error. TODO: is it an error?

        groupService.addMembership(userGroupInfo.getGroupId(), userGroupInfo.getUserId());
        return true;
    }

    @RequestMapping(value = "/removeMember", method = RequestMethod.PATCH)
    public boolean removeUserToGroupMember(@RequestBody MemberGroupPayload userGroupInfo){
        UserGroupFound guf;
        guf = fetchGroupUser(userGroupInfo);
        if(guf == null) return false; // error
        if(guf.gu == null) return true; // not present but it's not an error. TODO: is it an error?

        groupService.removeMembershipByGroupUserId(guf.gu.getId());
        return true;
    }

    protected UserGroupFound fetchGroupUser(MemberGroupPayload userGroupInfo){
        AppGroup group;
        GroupUser gu;
        if(userGroupInfo == null || userGroupInfo.getGroupId() == null || userGroupInfo.getUserId() == null ||
                (userGroupInfo.getUserName() == null || "".equals(userGroupInfo.getUserName().toLowerCase())))
            return null;
        group = groupService.findGroupById(userGroupInfo.getGroupId());
        if(group == null) return null;
        // TODO the following call should be performed without explicitly call the userService instance
        if(! userService.containsUser(userGroupInfo.getUserId()))
            return null;
        return new UserGroupFound(groupService.findMembership(userGroupInfo.getGroupId(), userGroupInfo.getUserId()));
    }

    //utils
    protected List<GroupFullDetail> groupFullDetailFromGroups(List<AppGroup> groups) {
        List<GroupFullDetail> gts;
        GroupFullDetail gfd;
        if (groups == null) return null;
        gts = new ArrayList<>(groups.size());
        for (AppGroup g : groups){
            gfd = fetchGroupDetails(g);
            gfd.setLocation(groupService.findLocationById(g.getLocation()));
            gts.add(gfd);
        }
        return gts;
    }

    protected GroupFullDetail fetchGroupDetails(AppGroup g){
        return new GroupFullDetail(g,//
                groupService.listTagsByAppGroupId(g.getGroupId()),//
                groupService.listUsersByAppGroupId(g.getGroupId())
        );
    }


    // crea i default

    //@RequestMapping(value = "/createAll", method = RequestMethod.POST)
    @PostMapping(value = "/createAll")
    public void createDefaults() {
        createAllTag();
        createAllGroup();
        this.x.createUsers();
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

    protected class UserGroupFound {
        //boolean found;
        GroupUser gu;
        protected UserGroupFound(GroupUser gu){
            //found = false;
            this.gu = gu;
        }
    }
}
