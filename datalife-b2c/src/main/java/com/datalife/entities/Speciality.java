package com.datalife.entities;

import javax.persistence.*;

/**
 * Created by supriya gondi on 1/3/2015.
 * <p/>
 * Entity class for Speciality, This is for user i.e Doctor to select speciality from Dropdown.
 */
@Entity
@Table(name = "speciality", uniqueConstraints = {
        @UniqueConstraint(columnNames = "specialityId"),
        @UniqueConstraint(columnNames = "name")})
public class Speciality {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "specialityId", unique = true, nullable = false)
    private Long specialityId;

    @Column(name = "name")
    private String name;

    public Speciality() {
    }

    public Long getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Long specialityId) {
        this.specialityId = specialityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
