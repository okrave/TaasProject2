package com.example.togroup5.demo.servicies;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.AppTag;
import com.example.togroup5.demo.entities.GoogleLocation;
import com.example.togroup5.demo.entities.GroupTag;
import com.example.togroup5.demo.entities.newEntities.AppGroupNew;
import com.example.togroup5.demo.entities.payloadsResults.GroupSearchAdvPayload;
import com.example.togroup5.demo.repositories.AppGroupRepository;
import com.example.togroup5.demo.repositories.AppTagRepository;
import com.example.togroup5.demo.repositories.GroupTagRepository;
import com.example.togroup5.demo.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // list all

    public List<AppGroup> listAllGroup() {
        return appGroupRepository.findAll();
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

    public void createGroup(AppGroupNew newGroup) {
        AppGroup g;
        AppTag t;
        GroupTag gt;
        GoogleLocation location;

        location = newGroup.getLocation();
        try {
            locationRepository.save(location);
            location = locationRepository.findGoogleLocationByLatAndLng(location.getLat(), location.getLng());
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
    public GoogleLocation findLocationById(Long locationId){
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


    public List<AppGroup> searchGroupAdvanced(GroupSearchAdvPayload groupSearchFilters) {
        return appGroupRepository.advancedSearch(groupSearchFilters);
    }
}
