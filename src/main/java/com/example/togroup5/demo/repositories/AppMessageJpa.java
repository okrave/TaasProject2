package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.AppMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppMessageJpa extends JpaRepository<AppMessage,Long>{

}
