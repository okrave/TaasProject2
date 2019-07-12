package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.GroupTag;
import com.example.togroup5.demo.entities.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupUserJpa extends JpaRepository<GroupUser,Long> {

    @Override
    List<GroupUser> findAll();
}
