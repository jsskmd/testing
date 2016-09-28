package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by supriya gondi on 11/6/2014.
 *
 * Entity class for Prescription
 */
@DynamicUpdate
@Entity
@Table(name = "prescription", uniqueConstraints = {
        @UniqueConstraint(columnNames = "rxId")})
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rxId", unique = true, nullable = false)
    private Long rxId;

    @Column(name = "medicationName")
    private String medicationName;

    @Column(name = "strength")
    private String strength;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "refill")
    private String refill;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "route")
    private String route;

    @Column(name = "estimatedCost")
    private Double estimatedCost;

    @ManyToOne
    @JoinColumn(name = "encounterId")
    private Encounter encounter;

    public Prescription() {
    }

    public Long getRxId() {
        return rxId;
    }

    public void setRxId(Long rxId) {
        this.rxId = rxId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRefill() {
        return refill;
    }

    public void setRefill(String refill) {
        this.refill = refill;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }
}
