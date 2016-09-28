package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by supriya gondi on 11/6/2014.
 *
 * Entity class for Physical Examination Categories
 */
@DynamicUpdate
@Entity
@Table(name = "pecategories")
public class PETypes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "peTypeId", unique = true, nullable = false)
    private Long peTypeId;

    @Column(name = "name")
    private String name;

    @Column(name = "peId")
    private Long peId;

    public PETypes() {
    }

    public Long getPeTypeId() {
        return peTypeId;
    }

    public void setPeTypeId(Long peTypeId) {
        this.peTypeId = peTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPeId() {
        return peId;
    }

    public void setPeId(Long peId) {
        this.peId = peId;
    }
}
