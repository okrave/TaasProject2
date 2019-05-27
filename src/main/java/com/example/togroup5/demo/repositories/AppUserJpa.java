package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.AppUser;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//https://www.baeldung.com/spring-data-derived-queries
public interface AppUserJpa extends JpaRepository<AppUser,Long>{
    AppUser findAppUserByUserName(String userName);

    @Override
    List<AppUser> findAll();
}
