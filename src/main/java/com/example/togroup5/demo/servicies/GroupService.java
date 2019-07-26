package com.example.togroup5.demo.servicies;

import com.example.togroup5.demo.entities.*;
import com.example.togroup5.demo.entities.payloadsResults.AppGroupNew;
import com.example.togroup5.demo.entities.payloadsResults.GroupFullDetail;
import com.example.togroup5.demo.entities.payloadsResults.GroupSearchAdvPayload;
import com.example.togroup5.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    private AppGroupRepository appGroupRepository;

    @Autowired
    private AppTagRepository appTagRepository;

    @Autowired
    private GroupTagRepository groupTagRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private GroupUserRepository groupUserRepository;

    // list all

    public List<AppGroup> listAllGroup() {
        return appGroupRepository.findAll();
    }

    public List<GroupFullDetail> listGroupFullDetail() {
        return groupFullDetailFromGroups(this.listAllGroup());
    }

    public AppGroup findGroupById(Long id) {
        return appGroupRepository.findAppGroupByID(id);
    }

    public List<AppGroup> listGroupByCreator() {
        return appGroupRepository.findDistinctByCreator("ciao");
    }

    public List<AppTag> listAllTag() {
        return appTagRepository.findAll();
    }

    public List<GoogleLocation> listAllGoogleLocation() {
        return locationRepository.findAll();
    }

    // save

    public void saveTag(AppTag newTag) {
        appTagRepository.save(newTag);
    }

    public void saveGroup(AppGroup newGroup) {
        appGroupRepository.save(newGroup);
    }

    public boolean deleteGroupById(Long groupId) {
        return appGroupRepository.delete(groupId);
    }

    public void createGroup(AppGroupNew newGroup) {
        AppGroup g;
        AppTag t;
        GroupTag gt;
        GoogleLocation location;

        location = newGroup.getLocation();
        try {
            GoogleLocation locationYetPresent;
            locationYetPresent = locationRepository.findGoogleLocationByLatAndLng(location.getLat(), location.getLng());
            if (locationYetPresent == null) {
                System.out.println("location saved: " + location);
                locationRepository.save(location);
                location = locationRepository.findGoogleLocationByLatAndLng(location.getLat(), location.getLng());
            } else {
                location = locationYetPresent;
            }
            newGroup.setLocation(location);
            // get the id
            System.out.println("location found again: " + location);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        g = appGroupRepository.findDistinctByGroupName(newGroup.getGroupName()); // to load the ID
        if (g == null) {
            g = newGroup.toAppGroup();
            g.setLocation(location.getLocationId());
            appGroupRepository.save(g);
            g = appGroupRepository.findDistinctByGroupName(newGroup.getGroupName()); // to load the ID
        }


        addMembership(g.getGroupId(),
                //appUserRepository.findAppUserByUserName(g.getCreator()).getUserId()
                g.getCreatorId());

        System.out.println(Arrays.toString(newGroup.getTags().toArray()));
        for (String ts : newGroup.getTags()) {
            try {
                t = findTagByName(ts);
                if (t == null) {
                    t = new AppTag(ts);
                    saveTag(t);
                    t = findTagByName(ts); // to load the ID
                }
                gt = groupTagRepository.findGroupTagsByGroupIdAndTagId(g.getGroupId(), t.getTagId());
                if (gt == null) {
                    gt = new GroupTag();
                    //gt.setAppGroup(g);
                    //gt.setAppTag(t);
                    gt.setGroupId(g.getGroupId());
                    gt.setTagId(t.getTagId());
                    try {
                        groupTagRepository.save(gt);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveGroupTag(GroupTag gt) {
        groupTagRepository.save(gt);
    }

    // find


    public AppTag findTagByName(String name) {
        return appTagRepository.findTagByName(name);
    }

    public AppGroup findByGroupName(String groupName) {
        return appGroupRepository.findDistinctByGroupName(groupName);
    }

    /*
    public boolean removeTag(String tagName){
        appTagRepository.delete(appTagRepository.);
    }
    */

    /*
    public GoogleLocation findLocationByGroupId(Long groupId){
        return locationRepository.findGoogleLocationByLatAndLng()
    }*/
    public GoogleLocation findLocationById(Long locationId) {
        return locationRepository.findGoogleLocationByID(locationId);
    }

    public void removeAllTags() {
        for (AppTag t : appTagRepository.findAll()) {
            appTagRepository.delete(t.getTagId());
        }
    }

    public List<AppTag> listTagsByAppGroupId(Long groupId) {
        return appTagRepository.listTagsByGroupId(groupId);
    }

    public List<AppUser> listUsersByAppGroupId(Long groupId) {
        return appUserRepository.listUsersByGroupId(groupId);
    }

    /**
     * @param groupId the first {@link Long} is the {@link AppGroup}'s ID
     * @param userId  the second {@link Long} is the {@link AppUser}'s ID
     */
    public GroupUser findMembership(Long groupId, Long userId) {
        return groupUserRepository.findGroupUserByGroupIdAndUserId(groupId, userId);
    }

    /**
     * Implementor's note: it's assumed that all checks about the existence of the group and the user
     * are performed BEFORE this call.
     *
     * @param groupId the first {@link Long} is the {@link AppGroup}'s ID
     * @param user    the {@link AppUser} which desires to apply onto the given group
     */
    public void addMembership(Long groupId, AppUser user) {
        addMembership(groupId, user.getUserId());
    }

    /**
     * Implementor's note: it's assumed that all checks about the existence of the group and the user
     * are performed BEFORE this call.
     *
     * @param groupId the first {@link Long} is the {@link AppGroup}'s ID
     * @param userId  the second {@link Long} is the {@link AppUser}'s ID which desires to apply onto the given group
     */
    public void addMembership(Long groupId, Long userId) {
        if (groupUserRepository.findGroupUserByGroupIdAndUserId(groupId, userId) == null)
            groupUserRepository.save(new GroupUser(groupId, userId));
    }

    /**
     * Implementor's note: it's assumed that all checks about the existence of the group and the user
     * are performed BEFORE this call.
     *
     * @param groupId the first {@link Long} is the {@link AppGroup}'s ID
     * @param user    the {@link AppUser} which desires to be removed from the given group
     */
    public void removeMembership(Long groupId, AppUser user) {
        removeMembership(groupId, user.getUserId());
    }

    /**
     * Implementor's note: it's assumed that all checks about the existence of the group and the user
     * are performed BEFORE this call.
     *
     * @param groupId the first {@link Long} is the {@link AppGroup}'s ID
     * @param userId  the second {@link Long} is the {@link AppUser}'s ID which desires to be removed from the given group
     */
    public void removeMembership(Long groupId, Long userId) {
        groupUserRepository.deleteGroupUserByGroupIdAndUserId(groupId, userId);
    }

    public void removeMembershipByGroupUserId(Long groupUserId) {
        groupUserRepository.delete(groupUserId);
    }

    public List<GroupFullDetail> searchGroupAdvanced(GroupSearchAdvPayload groupSearchFilters) {
        return groupFullDetailFromGroups(appGroupRepository.advancedSearch(groupSearchFilters));
    }

    public List<GroupFullDetail> listGroupByUserId(Long userId) {
        return groupFullDetailFromGroups(appGroupRepository.listGroupsByUserId(userId));
    }

    // refactoring after 24-07-2019

    public GroupFullDetail infoGroup(Long groupId) {
        AppGroup newGroup;
        GroupFullDetail gfd;
        System.out.println("groupId:" + groupId);
        newGroup = findGroupById(groupId);
        if (newGroup == null)
            return null;

        System.out.println("Il gruppo:" + newGroup.getGroupName());
        gfd = fetchGroupDetails(newGroup);
        System.out.println(gfd.toString());
        return gfd;
    }


    //utils

    protected List<GroupFullDetail> groupFullDetailFromGroups(List<AppGroup> groups) {
        List<GroupFullDetail> gts;
        GroupFullDetail gfd;
        if (groups == null) return null;
        gts = new ArrayList<>(groups.size());
        for (AppGroup g : groups) {
            gfd = fetchGroupDetails(g);
            gfd.setLocation(findLocationById(g.getLocation()));
            gts.add(gfd);
        }
        return gts;
    }

    protected GroupFullDetail fetchGroupDetails(AppGroup g) {
        return new GroupFullDetail(g,//
                listTagsByAppGroupId(g.getGroupId()),//
                listUsersByAppGroupId(g.getGroupId()), //
                findLocationById(g.getLocation())
        );
    }
}