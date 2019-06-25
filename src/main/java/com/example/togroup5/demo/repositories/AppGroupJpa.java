package com.example.togroup5.demo.repositories;


import com.example.togroup5.demo.entities.AppGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//https://www.baeldung.com/spring-data-derived-queries
public interface AppGroupJpa extends JpaRepository<AppGroup,Long>{
        //AppGroup findAppGroupByUserName(String userName);

        @Override
        List<AppGroup> findAll();

}
