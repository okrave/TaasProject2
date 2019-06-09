package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.AppTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppTagJpa extends JpaRepository<AppTag,Long> {

    @Override
    List<AppTag> findAll();
}
