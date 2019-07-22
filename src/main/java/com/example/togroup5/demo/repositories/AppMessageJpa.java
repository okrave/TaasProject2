package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.AppMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppMessageJpa extends JpaRepository<AppMessage,Long>{


    //@Query("select m from com.example.togroup5.demo.entities.AppMessage m where m.groupId = :groupId")
    List<AppMessage> findAllByGroupId(@Param("groupId") Long groupId);

}
