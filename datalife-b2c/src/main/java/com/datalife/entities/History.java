package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by supriya gondi on 10/23/2014.
 *
 * Entity class for History
 * This entity has all Patient Past Medical History
 */
@DynamicUpdate
@Entity
@Table(name = "history", uniqueConstraints = {
        @UniqueConstraint(columnNames = "historyId")})
@Proxy(lazy = false)
public class History implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "historyId", unique = true, nullable = false)
    private Long historyId;

    @Column(name = "medicalHistory")
    private String medicalHistory;

    @Column(name = "surgicalHistory")
    private String surgicalHistory;

    @Column(name = "familyHistory")
    private String familyHistory;

    @Column(name = "socialHistory")
    private String socialHistory;

    @Column(name = "pastMedications")
    private String pastMedications;

    @Column(name = "riskFactors")
    private String riskFactors;

    @Column(name = "allergies")
    private String allergies;

    @Column(name = "immunizations")
    private String immunizations;

    @Transient
    private String _csrf;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    public History() {
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getSurgicalHistory() {
        return surgicalHistory;
    }

    public void setSurgicalHistory(String surgicalHistory) {
        this.surgicalHistory = surgicalHistory;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public String getSocialHistory() {
        return socialHistory;
    }

    public void setSocialHistory(String socialHistory) {
        this.socialHistory = socialHistory;
    }

    public String getPastMedications() {
        return pastMedications;
    }

    public void setPastMedications(String pastMedications) {
        this.pastMedications = pastMedications;
    }

    public String getRiskFactors() {
        return riskFactors;
    }

    public void setRiskFactors(String riskFactors) {
        this.riskFactors = riskFactors;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getImmunizations() {
        return immunizations;
    }

    public void setImmunizations(String immunizations) {
        this.immunizations = immunizations;
    }

    public String get_csrf() {
        return _csrf;
    }

    public void set_csrf(String _csrf) {
        this._csrf = _csrf;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
