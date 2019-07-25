package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.GoogleLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationJpa extends JpaRepository<GoogleLocation,Long> {


    GoogleLocation findGoogleLocationByLatAndLng(double lat, double lng);

}