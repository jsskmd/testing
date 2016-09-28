package com.datalife.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by supriya gondi on 10/28/2014.
 *
 * Entity class for Country
 * This is for user to select Country from Dropdown.
 */
@Entity
@Table(name = "country", uniqueConstraints = {
        @UniqueConstraint(columnNames = "countryId"),
        @UniqueConstraint(columnNames = "countryName")})
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "countryId", unique = true, nullable = false)
    private Long countryId;

    @Column(name = "countryName")
    private String countryName;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<State> stateList;


    public Country() {
    }


    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<State> getStateList() {
        return stateList;
    }

    public void setStateList(List<State> stateList) {
        this.stateList = stateList;
    }

}