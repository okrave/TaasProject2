package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppUserRoleRepository extends  JpaRepository<UserRole,Long>{

}