package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * Created by supriya gondi on 11/6/2014.
 * <p/>
 * Entity class for Physical Examination
 */
@DynamicUpdate
@Entity
@Table(name = "physicalexamination", uniqueConstraints = {
        @UniqueConstraint(columnNames = "peId")})
public class PhysicalExamination {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "peId", unique = true, nullable = false)
    private Long peId;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "physicalExamination", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CardioVascular> cardioVascular;

    @OneToMany(mappedBy = "physicalExamination", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Gastrointestinal> gastrointestinal;

    @OneToMany(mappedBy = "physicalExamination", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Muscoloskeletal> muscoloskeletal;

    @OneToMany(mappedBy = "physicalExamination", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Respiratory> respiratory;

    @Column(name = "peTypeId")
    private Long peTypeId;

    @ManyToOne
    @JoinColumn(name = "encounterId")
    private Encounter encounter;

    public PhysicalExamination() {
    }

    public Long getPeId() {
        return peId;
    }

    public void setPeId(Long peId) {
        this.peId = peId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CardioVascular> getCardioVascular() {
        return cardioVascular;
    }

    public void setCardioVascular(List<CardioVascular> cardioVascular) {
        this.cardioVascular = cardioVascular;
    }

    public List<Gastrointestinal> getGastrointestinal() {
        return gastrointestinal;
    }

    public void setGastrointestinal(List<Gastrointestinal> gastrointestinal) {
        this.gastrointestinal = gastrointestinal;
    }

    public List<Muscoloskeletal> getMuscoloskeletal() {
        return muscoloskeletal;
    }

    public void setMuscoloskeletal(List<Muscoloskeletal> muscoloskeletal) {
        this.muscoloskeletal = muscoloskeletal;
    }

    public List<Respiratory> getRespiratory() {
        return respiratory;
    }

    public void setRespiratory(List<Respiratory> respiratory) {
        this.respiratory = respiratory;
    }

    public Long getPeTypeId() {
        return peTypeId;
    }

    public void setPeTypeId(Long peTypeId) {
        this.peTypeId = peTypeId;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }
}
