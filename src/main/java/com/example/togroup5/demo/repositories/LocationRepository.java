package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.GoogleLocation;
import com.example.togroup5.demo.entities.GroupUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
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
        //return locationJpa.findGoogleLocationByLatAndLng(lat,lng);
        try {
            String sql = "Select lo from " + GoogleLocation.class.getName() +
                     " lo WHERE lo.lat = :lat AND lo.lng = :lng";
            Query query = entityManager.createQuery(sql, GoogleLocation.class);
            query.setParameter("lat", lat);
            query.setParameter("lng", lng);
            return (GoogleLocation) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<GoogleLocation> findAll() {
        return locationJpa.findAll();
    }

}
