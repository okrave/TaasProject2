package com.example.togroup5.demo.controllers;

import com.example.togroup5.demo.entities.*;
import com.example.togroup5.demo.entities.payloadsResults.AppGroupNew;
import com.example.togroup5.demo.entities.payloadsResults.*;
import com.example.togroup5.demo.servicies.GroupService;
import com.example.togroup5.demo.servicies.MessageService;
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
    RestHomeController restHomeController;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;


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
        return groupService.listGroupFullDetail();
    }


    @GetMapping(value = "/listLocations")
    public List<GoogleLocation> listLocations() {
        return groupService.listAllGoogleLocation();
    }

    @GetMapping(value = "/{groupName}")
    public AppGroup findGroupByName(@PathVariable String groupName) {
        return groupService.findByGroupName(groupName);
    }


    @RequestMapping(value = "/createGroup", method = RequestMethod.POST)
    public boolean newGroup(@RequestBody AppGroupNew appGroupNew) {
        System.out.println(appGroupNew);
        groupService.createGroup(appGroupNew);
        return true;
    }


    @GetMapping(value = "/info/{groupId}")
    public GroupFullDetail infoGroup(@PathVariable Long groupId) {
        return groupService.infoGroup(groupId);
    }

    @RequestMapping(value = "deleteGroup/{groupId}", method = RequestMethod.DELETE)
    public boolean deleteGroupById(@PathVariable Long groupId) {
        return groupService.deleteGroupById(groupId);
    }


    @RequestMapping(value = "/advGroupSearch", method = RequestMethod.PATCH)
    public List<GroupFullDetail> searchGroupAdvanced(@RequestBody GroupSearchAdvPayload groupSearchFilters) {
        List<GroupFullDetail> groups;
        System.out.println("search filters: ");
        System.out.println(groupSearchFilters);
        groups = groupService.searchGroupAdvanced(groupSearchFilters);
        System.out.println("found " + groups.size() + " groups");
        return groups;
    }

    @RequestMapping(value = "/advGroupSearch", method = RequestMethod.POST)
    public List<GroupFullDetail> searchGroupAdvanced_Android(@RequestBody GroupSearchAdvPayload groupSearchFilters) {
        return searchGroupAdvanced(groupSearchFilters);
    }

    @RequestMapping(value = "/addMember", method = RequestMethod.PATCH)
    public boolean addUserToGroupMember(@RequestBody MemberGroupPayload userGroupInfo) {
        System.out.println(userGroupInfo);
        UserGroupFound guf;
        guf = fetchGroupUser(userGroupInfo);
        if (guf == null) {
            System.out.println("errore fetchGroupUser");
            return false; // error
        }
        if (guf.gu != null) return true; // yet present but it's not an error. TODO: is it an error?
        groupService.addMembership(userGroupInfo.getGroupId(), userGroupInfo.getUserId());
        return true;
    }


    @RequestMapping(value = "/addMember", method = RequestMethod.POST)
    public boolean addUserToGroupMember_Android(@RequestBody MemberGroupPayload userGroupInfo) {
        return addUserToGroupMember(userGroupInfo);
    }

    @RequestMapping(value = "/removeMember", method = RequestMethod.PATCH)
    public boolean removeUserToGroupMember(@RequestBody MemberGroupPayload userGroupInfo) {

        UserGroupFound guf;
        guf = fetchGroupUser(userGroupInfo);
        if (guf == null) return false; // error
        if (guf.gu == null) return true; // not present but it's not an error. TODO: is it an error?

        groupService.removeMembershipByGroupUserId(guf.gu.getId());
        return true;
    }

    @RequestMapping(value = "/removeMember", method = RequestMethod.POST)
    public boolean removeUserToGroupMember_Android(@RequestBody MemberGroupPayload userGroupInfo) {
        return removeUserToGroupMember(userGroupInfo);
    }

    @RequestMapping(value = "/isMember", method = RequestMethod.PATCH)
    public boolean isMember(@RequestBody MemberGroupPayload userGroupInfo) {
        UserGroupFound guf;
        guf = fetchGroupUser(userGroupInfo);
        return (guf != null && guf.gu != null);
    }

    @RequestMapping(value = "/isMember", method = RequestMethod.POST)
    public boolean isMember_Android(@RequestBody MemberGroupPayload userGroupInfo) {
        return isMember(userGroupInfo);
    }


    @GetMapping(value = "/userGroups/{id}")
    public List<GroupFullDetail> listGroupsByUserId(@PathVariable Long id) {
        return groupService.listGroupByUserId(id);
    }

    @GetMapping(value = "/createTag/{name}")
    public boolean createTag(@PathVariable String name) {
        groupService.saveTag(new AppTag(name));
        return true;
    }


    //-------------------------------------Messaggi--------------------------------

    @GetMapping(value = "/createMessage")
    public void createMessage() {
        messageService.save();
    }

    @RequestMapping(value = "/removeAllMessages", method = RequestMethod.DELETE)
    public void removeAllMessages() {
        messageService.removeAll();
    }

    @GetMapping(value = "/findMessageById/{id}")
    public AppMessage findMessageById(@PathVariable Long id) {
        return messageService.findAppMessageById(id);
    }

    @GetMapping(value = "/findAllMessage")
    public List<AppMessage> findAllMessage() {
        return messageService.findAll();
    }

    @GetMapping(value = "/findMessageByUserId/{id}")
    public List<AppMessage> findMessageByUserId(@PathVariable Long id) {
        return messageService.findAppMessageByUserId(id);
    }

    @GetMapping(value = "/findMessageByGroupId/{id}")
    public List<AppMessage> findMessageByGroupId(@PathVariable Long id) {
        return messageService.findAppMessageByGroupId(id);
    }


    @PostMapping(value = "/sendMessage")
    public boolean sendMessage(@RequestBody MessageNewPayload msgNew) {
        Date dateNow;
        AppMessage m;
        String text;
        final int MAX_MSG_LENGTH = 255; // deciso a livello di database
        dateNow = new Date(System.currentTimeMillis());
        text = msgNew.getTesto();
        if (text.length() > MAX_MSG_LENGTH) {
            /*messaggio troppo lungo: spezziamolo in sottoparti*/
            boolean res;
            int i, len;
            len = text.length();
            i = 0;
            res = true;
            while (i < len) {
                m = new AppMessage(text.substring(i, Math.min(i += MAX_MSG_LENGTH, len))//
                        , msgNew.getUserId(), msgNew.getGroupId(), dateNow);
                res &= messageService.saveMessage(m);
            }
            return res;
        }
        m = new AppMessage(msgNew.getTesto(), msgNew.getUserId(), msgNew.getGroupId(), dateNow);
        return messageService.saveMessage(m);
    }


    @RequestMapping(value = "/fetchMessages", method = RequestMethod.PATCH)
    public MessagesGroupResponse fetchMessages(@RequestBody MessageQueryPayload msgQuery) {
        List<MessageSent> msgs;
        List<AppMessage> fetchedMsgs;

        fetchedMsgs = messageService.fetchMessages(msgQuery);
        if (fetchedMsgs == null) return null;
        msgs = new ArrayList<>(fetchedMsgs.size());
        fetchedMsgs.forEach(m -> {
                    AppUser user;
                    user = userService.findUserById(m.getUserId());
                            //restHomeController.infoUser(m.getUserId());
                    if (user != null)
                        msgs.add(new MessageSent(m, user.getUserName()));
                    else
                        throw new NullPointerException("On fetching group " + m.getGroupId() + " 's messages, cannot find user with id: " + m.getUserId());
                }
        );
        return new MessagesGroupResponse(msgQuery.getGroupId(), msgs);
    }

    @RequestMapping(value = "/fetchMessages", method = RequestMethod.POST)
    public MessagesGroupResponse fetchMessages_Android(@RequestBody MessageQueryPayload msgQuery) {
        return fetchMessages(msgQuery);
    }

    //-------------------------------------Fine Messaggi---------------------------


    //

    //

    //

    protected UserGroupFound fetchGroupUser(MemberGroupPayload userGroupInfo) {
        AppGroup group;
        GroupUser gu;
        if (userGroupInfo == null || userGroupInfo.getGroupId() == null || userGroupInfo.getUserId() == null ||
                (userGroupInfo.getUserName() == null || "".equals(userGroupInfo.getUserName().toLowerCase())))
            return null;
        group = groupService.findGroupById(userGroupInfo.getGroupId());
        if (group == null) return null;
        // TODO the following call should be performed without explicitly call the userService instance
        if (!userService.containsUser(userGroupInfo.getUserId()))
            return null;
        return new UserGroupFound(groupService.findMembership(userGroupInfo.getGroupId(), userGroupInfo.getUserId()));
    }

    //utils
    /*
    protected List<GroupFullDetail> groupFullDetailFromGroups(List<AppGroup> groups) {
        List<GroupFullDetail> gts;
        GroupFullDetail gfd;
        if (groups == null) return null;
        gts = new ArrayList<>(groups.size());
        for (AppGroup g : groups) {
            gfd = fetchGroupDetails(g);
            gfd.setLocation(groupService.findLocationById(g.getLocation()));
            gts.add(gfd);
        }
        return gts;
    }

    protected GroupFullDetail fetchGroupDetails(AppGroup g) {
        return new GroupFullDetail(g,//
                // userService.findAppUserByUserName(g.getCreator()).getUserId(), // 20-07-2019: già fornito dalla creazione front-end: solo gli utenti loggati possono creare gruppi.
                groupService.listTagsByAppGroupId(g.getGroupId()),//
                groupService.listUsersByAppGroupId(g.getGroupId()), //
                groupService.findLocationById(g.getLocation())
        );
    }*/


    // crea i default

    //@RequestMapping(value = "/createAll", method = RequestMethod.POST)
    @PostMapping(value = "/createAll")
    public void createDefaults() {
        this.restHomeController.createUsers();
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
        } catch (Exception e) {
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
                , //
                new String[][]{
                        new String[]{ //group info
                                "lonevetad", "Sushitiamo gioia", "Pranzo orientale tra amici.", "2019-07-30"
                        },
                        new String[]{"('35.0, 40.0')", "Torino"}, // location
                        new String[]{"amici", "sushi", "pranzo", "relax"} //tags
                }
                , //
                new String[][]{
                        new String[]{ //group info
                                "Davide", "Vola", "Deltaplano\nche vola lontano\ntassiamo un rapporto sano\ntendoci mano per mano\ne tutti assieme, tra spighe di grano\nfacciamo ciò che è più umano:\nuniti viviamo e sognamo.\n\nLa gioia di vivere\nspicca il volo.\nLOL.", "2019-09-17"
                        },
                        new String[]{"('50.0, 50.0')", "Aosta"}, // location
                        new String[]{"amici", "volo", "deltaplano", "volare", "sogno", "relax"} //tags
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


            newGroup = new AppGroupNew(
                    userService.findAppUserByUserName(groupInfo[0]).getUserId(), //
                    groupInfo[0], groupInfo[1], //
                    new AppGroupNew.LocationReceived(locationInfo[0]),//
                    tags, groupInfo[2], Date.valueOf(groupInfo[3]));
            newGroup.getLocation().setLocationId(id++);
            groupService.createGroup(newGroup);
        }
    }

    protected static class UserGroupFound {
        //boolean found;
        GroupUser gu;

        protected UserGroupFound(GroupUser gu) {
            //found = false;
            this.gu = gu;
        }
    }

}
