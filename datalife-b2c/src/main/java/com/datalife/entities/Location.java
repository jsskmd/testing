package com.datalife.entities;

/**
 * Created by Dipak on 5/4/2015.
 */

import javax.persistence.JoinColumn;
import javax.persistence.*;

@Entity
@Table(name = "location", uniqueConstraints = {
        @UniqueConstraint(columnNames = "locationId")})
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "locationId", unique = true, nullable = false)
    private Long locationId;

    @Column(name = "locationName")
    private String locationName;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private City city;

    public Location() {
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
