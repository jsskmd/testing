package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by supriya gondi on 1/5/2015.
 * <p/>
 * Entity class for Respiratory, ,it is one of the category of Physical Examination specific to Cardiologist
 */
@DynamicUpdate
@Entity
@Table(name = "respiratory", uniqueConstraints = {
        @UniqueConstraint(columnNames = "respiratoryId")})
public class Respiratory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "respiratoryId", unique = true, nullable = false)
    private Long respiratoryId;

    @Column(name = "descriptions")
    private String descriptions;

    @ManyToOne
    @JoinColumn(name = "respiratoryLabelId")
    private RespiratoryLabels respiratoryLabels;

    @ManyToOne
    @JoinColumn(name = "peId")
    private PhysicalExamination physicalExamination;

    public Respiratory() {
    }

    public Long getRespiratoryId() {
        return respiratoryId;
    }

    public void setRespiratoryId(Long respiratoryId) {
        this.respiratoryId = respiratoryId;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public RespiratoryLabels getRespiratoryLabels() {
        return respiratoryLabels;
    }

    public void setRespiratoryLabels(RespiratoryLabels respiratoryLabels) {
        this.respiratoryLabels = respiratoryLabels;
    }

    public PhysicalExamination getPhysicalExamination() {
        return physicalExamination;
    }

    public void setPhysicalExamination(PhysicalExamination physicalExamination) {
        this.physicalExamination = physicalExamination;
    }
}
