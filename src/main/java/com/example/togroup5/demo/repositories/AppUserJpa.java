package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

//https://www.baeldung.com/spring-data-derived-queries
public interface AppUserJpa extends JpaRepository<AppUser,Long>{
    //AppUser findByUsername(String userName);

}
