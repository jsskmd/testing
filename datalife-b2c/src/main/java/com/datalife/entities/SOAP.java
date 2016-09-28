package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by supriya gondi on 11/7/2014.
 *
 * Entity class for Mini Encounter
 */
@DynamicUpdate
@Entity
@Table(name = "soap", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
public class SOAP {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "subjective")
    private String subjective;

    @Column(name = "objective")
    private String objective;

    @Column(name = "assessment")
    private String assessment;

    @Column(name = "plan")
    private String plan;

    @OneToOne
    @JoinColumn(name = "encounterId")
    private Encounter encounter;

    public SOAP() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjective() {
        return subjective;
    }

    public void setSubjective(String subjective) {
        this.subjective = subjective;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }
}
