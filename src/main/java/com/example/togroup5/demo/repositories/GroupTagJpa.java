package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.GroupTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupTagJpa extends JpaRepository<GroupTag,Long> {

    @Override
    List<GroupTag> findAll();
}
