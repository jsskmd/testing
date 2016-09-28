package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by Dipak on 1/7/2015.
 * <p/>
 * Entity class for Gastrointestinal speciality based encounter
 */
@DynamicUpdate
@Entity
@Table(name = "gastrointestinal", uniqueConstraints = {
        @UniqueConstraint(columnNames = "gastrointestinalId")})
public class Gastrointestinal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gastrointestinalId", unique = true, nullable = false)
    private Long gastrointestinalId;

    @Column(name = "descriptions")
    private String descriptions;

    @ManyToOne
    @JoinColumn(name = "gastroLabelId")
    private GastrointestinalLabels gastrointestinalLabels;

    @ManyToOne
    @JoinColumn(name = "peId")
    private PhysicalExamination physicalExamination;

    public Gastrointestinal() {
    }

    public Long getGastrointestinalId() {
        return gastrointestinalId;
    }

    public void setGastrointestinalId(Long gastrointestinalId) {
        this.gastrointestinalId = gastrointestinalId;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public GastrointestinalLabels getGastrointestinalLabels() {
        return gastrointestinalLabels;
    }

    public void setGastrointestinalLabels(GastrointestinalLabels gastrointestinalLabels) {
        this.gastrointestinalLabels = gastrointestinalLabels;
    }

    public PhysicalExamination getPhysicalExamination() {
        return physicalExamination;
    }

    public void setPhysicalExamination(PhysicalExamination physicalExamination) {
        this.physicalExamination = physicalExamination;
    }
}
