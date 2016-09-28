package com.datalife.entities;

import javax.persistence.*;

/**
 * Created by dscblpo on 1/23/2016.
 */
@Entity
@Table(name = "hospitalCity", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
public class HospitalCity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "countryId")
    private Long countryId;

    public HospitalCity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
}
