package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by Dipak on 1/7/2015.
 *
 * Entity class for Muscoloskeletal
 */
@DynamicUpdate
@Entity
@Table(name = "muscoloskeletal",uniqueConstraints = {
        @UniqueConstraint(columnNames = "muscoloskeletalId")} )
public class Muscoloskeletal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "muscoloskeletalId", unique = true, nullable = false)
    private Long muscoloskeletalId;

    @Column(name = "descriptions")
    private String descriptions;

    @ManyToOne
    @JoinColumn(name = "muscoloLabelId")
    private MuscoloskeletalLabels muscoloskeletalLabels;

    @ManyToOne
    @JoinColumn(name = "peId")
    private PhysicalExamination physicalExamination;

    public Muscoloskeletal() {
    }

    public Long getMuscoloskeletalId() {
        return muscoloskeletalId;
    }

    public void setMuscoloskeletalId(Long muscoloskeletalId) {
        this.muscoloskeletalId = muscoloskeletalId;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public MuscoloskeletalLabels getMuscoloskeletalLabels() {
        return muscoloskeletalLabels;
    }

    public void setMuscoloskeletalLabels(MuscoloskeletalLabels muscoloskeletalLabels) {
        this.muscoloskeletalLabels = muscoloskeletalLabels;
    }

    public PhysicalExamination getPhysicalExamination() {
        return physicalExamination;
    }

    public void setPhysicalExamination(PhysicalExamination physicalExamination) {
        this.physicalExamination = physicalExamination;
    }
}


