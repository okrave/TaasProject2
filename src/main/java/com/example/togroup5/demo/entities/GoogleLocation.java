package com.example.togroup5.demo.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "location", uniqueConstraints={@UniqueConstraint(columnNames ={"lat","lng"})})
public class GoogleLocation implements Serializable {
    @Id
    @GeneratedValue
    private Long locationId;
    private Double lat, lng;

    public GoogleLocation(){
        this(0.0,0.0);
    }
    public GoogleLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}
