package com.datalife.entities;

import javax.persistence.*;

/**
 * Created by supriya gondi on 10/27/2014.
 *
 * Entity class for BloodGroup
 * This is for user to select BloodGroup from Dropdown.
 */
@Entity
@Table(name = "bloodgroup", uniqueConstraints = {
        @UniqueConstraint(columnNames = "bloodGroupId"),
        @UniqueConstraint(columnNames = "type")})
public class BloodGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bloodGroupId", unique = true, nullable = false)
    private Long bloodGroupId;

    @Column(name = "type")
    private String type;

    public BloodGroup() {
    }

    public Long getBloodGroupId() {
        return bloodGroupId;
    }

    public void setBloodGroupId(Long bloodGroupId) {
        this.bloodGroupId = bloodGroupId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}