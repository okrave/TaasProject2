package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.AppGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppGroupRepository extends JpaRepository<AppGroup,Long> {

}
