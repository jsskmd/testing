package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by barath on 7/1/2016.
 */
    @DynamicUpdate
    @Entity
    @Table(name = "surgeryRequest")
public class SurgeryRequests extends ServiceRequests implements Serializable {


    @Column(name = "speciality")
    private String speciality;

    @Column(name = "typeOfProcedure")
    private String typeOfProcedure;

    @Column(name = "typeOfProcedureText")
    private String typeOfProcedureText;

    @Column(name = "institutionName")
    private String institutionName;

    @Column(name = "surgeon")
    private String surgeon;

    @Column(name = "preferredDate")
    private String preferredDate;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "accommodationType")
    private String accommodationType;

    @Column(name = "budget")
    private String budget;

    @Column(name = "ifAnyInsurance")
    private boolean ifAnyInsurance;

    @Column(name = "insuranceType")
    private String insuranceType;

    @Column(name = "coverageLimit")
    private String coverageLimit;

    @Column(name = "recordIds")
    private String recordIds;


    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getTypeOfProcedure() {
        return typeOfProcedure;
    }

    public void setTypeOfProcedure(String typeOfProcedure) {
        this.typeOfProcedure = typeOfProcedure;
    }

    public String getTypeOfProcedureText() {
        return typeOfProcedureText;
    }

    public void setTypeOfProcedureText(String typeOfProcedureText) {
        this.typeOfProcedureText = typeOfProcedureText;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getSurgeon() {
        return surgeon;
    }

    public void setSurgeon(String surgeon) {
        this.surgeon = surgeon;
    }

    public String getPreferredDate() {
        return preferredDate;
    }

    public void setPreferredDate(String preferredDate) {
        this.preferredDate = preferredDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(String accommodationType) {
        this.accommodationType = accommodationType;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public boolean isIfAnyInsurance() {
        return ifAnyInsurance;
    }

    public void setIfAnyInsurance(boolean ifAnyInsurance) {
        this.ifAnyInsurance = ifAnyInsurance;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getCoverageLimit() {
        return coverageLimit;
    }

    public void setCoverageLimit(String coverageLimit) {
        this.coverageLimit = coverageLimit;
    }

    public String getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(String recordIds) {
        this.recordIds = recordIds;
    }
}
