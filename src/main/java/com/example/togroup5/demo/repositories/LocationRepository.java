package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.GoogleLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository("LocationRepository")
@Transactional
public class LocationRepository {


    @Autowired
    private LocationJpa locationJpa;

    @Autowired
    private EntityManager entityManager;

    //basic methods

    public void save(GoogleLocation location) {
        //GoogleLocation supportLocation=
        locationJpa.save(location);
    }

    public boolean delete(Long locationId) {
        locationJpa.deleteById(locationId);
        return true;
    }

    // query

    public GoogleLocation findGoogleLocationByID(Long locationId) {
        return locationJpa.getOne(locationId);
    }

    public GoogleLocation findGoogleLocationByLatAndLng(double lat, double lng){
        return locationJpa.findGoogleLocationByLatAndLng(lat,lng);
    }

    public List<GoogleLocation> findAll() {
        return locationJpa.findAll();
    }

}
