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
@Table(name = "miniencounter", uniqueConstraints = {
        @UniqueConstraint(columnNames = "miniEncounterId")})
public class MiniEncounter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "miniEncounterId", unique = true, nullable = false)
    private Long miniEncounterId;

    @Column(name = "chiefComplaint")
    private String chiefComplaint;

    @Column(name = "hpi")
    private String hpi;

    @Column(name = "impression")
    private String impression;

    @Column(name = "plan")
    private String plan;

    @Column(name = "followup")
    private String followup;

    @Column(name = "procedures")
    private String procedures;

    @OneToOne
    @JoinColumn(name = "encounterId")
    private Encounter encounter;

    public MiniEncounter() {
    }

    public Long getMiniEncounterId() {
        return miniEncounterId;
    }

    public void setMiniEncounterId(Long miniEncounterId) {
        this.miniEncounterId = miniEncounterId;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public String getHpi() {
        return hpi;
    }

    public void setHpi(String hpi) {
        this.hpi = hpi;
    }

    public String getImpression() {
        return impression;
    }

    public void setImpression(String impression) {
        this.impression = impression;
    }

    public String getFollowup() {
        return followup;
    }

    public void setFollowup(String followup) {
        this.followup = followup;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }

    public String getProcedures() {
        return procedures;
    }

    public void setProcedures(String procedures) {
        this.procedures = procedures;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
