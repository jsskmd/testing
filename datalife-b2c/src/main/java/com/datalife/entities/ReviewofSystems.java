package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by supriya gondi on 11/6/2014.
 *
 * Entity class for Review of Systems
 */
@DynamicUpdate
@Entity
@Table(name = "reviewofsystems", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
public class ReviewofSystems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "rosId")
    private Byte rosId;

    @ManyToOne
    @JoinColumn(name = "encounterId")
    private Encounter encounter;

    public ReviewofSystems() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getRosId() {
        return rosId;
    }

    public void setRosId(Byte rosId) {
        this.rosId = rosId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }
}
