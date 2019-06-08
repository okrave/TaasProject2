package com.example.togroup5.demo.servicies;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.AppTag;
import com.example.togroup5.demo.entities.payloads.GroupSearchAdvPayload;
import com.example.togroup5.demo.repositories.AppGroupRepository;
import com.example.togroup5.demo.repositories.AppTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Iterator;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    private AppGroupRepository appGroupRepository;

    @Autowired
    private AppTagRepository appTagRepository;

    public List<AppGroup> listAllGroup(){
        return appGroupRepository.findAll();
    }

    
    public List<AppGroup> listGroupByCreator(){
        return appGroupRepository.findDistinctByCreator("ciao");
    }

    public List<AppTag> listAllTag() {
        return appTagRepository.findAll();
    }

    public void saveTag(AppTag newTag) {
        appTagRepository.save(newTag);
    }

    public void saveGroup(AppGroup newGroup) {
        appGroupRepository.save(newGroup);
    }

    public AppGroup findByGroupName(String groupName) {
        return appGroupRepository.findDistinctByGroupName(groupName);
    }

    /*
    public boolean removeTag(String tagName){
        appTagRepository.delete(appTagRepository.);
    }
    */

    public void removeAllTags(){
        appTagRepository.deleteAll();
        appTagRepository.flush();
        for( AppTag t : appTagRepository.findAll()){
            appTagRepository.delete(t);
            appTagRepository.deleteById(t.getTagId());
        }
        appTagRepository.flush();
    }

    public List<AppGroup> searchGroupAdvanced(GroupSearchAdvPayload groupSearchFilters) {
        return appGroupRepository.advancedSearch(groupSearchFilters);
    }
}
