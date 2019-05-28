package com.example.togroup5.demo.servicies;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.repositories.AppGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private AppGroupRepository appGroupRepository;

    public List<AppGroup> listAllGroup(){
        return appGroupRepository.findAll();
    }
}
