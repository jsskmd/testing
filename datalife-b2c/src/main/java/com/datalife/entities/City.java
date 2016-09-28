package com.datalife.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by supriya gondi on 1/28/2015.
 * Entity class for City
 * This is for user to select City from Dropdown.
 */
@Entity
@Table(name = "city", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cityId"),
        @UniqueConstraint(columnNames = "name")})
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cityId", unique = true, nullable = false)
    private Long cityId;

    @Column(name = "name")
    private String name;

    @Column(name = "stateId")
    private Long stateId;

    public City() {
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }
}
