package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by supriya gondi on 11/8/2014.
 * <p/>
 * Entity class for LabOrder
 */
@DynamicUpdate
@Entity
@Table(name = "laborder", uniqueConstraints = {
        @UniqueConstraint(columnNames = "labOrderId")})
public class LabOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "labOrderId", unique = true, nullable = false)
    private Long labOrderId;

    @Column(name = "otherDesp")
    private String otherDesp;

    @ManyToOne
    @JoinColumn(name = "encounterId")
    private Encounter encounter;

    @Column(name = "labTestsId")
    private Long labTestsId;

    public LabOrder() {
    }

    public Long getLabOrderId() {
        return labOrderId;
    }

    public void setLabOrderId(Long labOrderId) {
        this.labOrderId = labOrderId;
    }

    public String getOtherDesp() {
        return otherDesp;
    }

    public void setOtherDesp(String otherDesp) {
        this.otherDesp = otherDesp;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }

    public Long getLabTestsId() {
        return labTestsId;
    }

    public void setLabTestsId(Long labTestsId) {
        this.labTestsId = labTestsId;
    }
}
