package com.datalife.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by barath on 8/4/2016.
 */
@Entity
@Table(name = "pharmacy")
public class Pharmacy extends ServiceProvider implements Serializable {


    @Column(name = "accrediations")
    private String accrediations;

    @Column(name = "addedInfo")
    private String addedInfo;

    @Column(name = "workdays")
    private String workdays;

    @Column(name = "workTiming")
    private String workTiming;

    @Column(name = "isDatabase")
    private Boolean isDatabase;

    @Column(name = "isBillingProcess")
    private Boolean isBillingProcess;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pharmacy",fetch = FetchType.LAZY)
    private Set<ClinicInfo> clinic;

    @Transient
    private String location;

    @Transient
    private String pharmacyName;

    @Transient
    private Long pharmacyId;

    @Transient
    private Long clinicId;

    public Pharmacy() {
    }

    public Pharmacy(Long pharmacyId,String pharmacyName,String location){
        this.pharmacyId = pharmacyId;
        this.pharmacyName = pharmacyName;
        this.location = location;
    }


    public String getAccrediations() {
        return accrediations;
    }

    public void setAccrediations(String accrediations) {
        this.accrediations = accrediations;
    }

    public String getWorkdays() {
        return workdays;
    }

    public void setWorkdays(String workdays) {
        this.workdays = workdays;
    }

    public String getWorkTiming() {
        return workTiming;
    }

    public void setWorkTiming(String workTiming) {
        this.workTiming = workTiming;
    }

    public Boolean getIsDatabase() {
        return isDatabase;
    }

    public void setIsDatabase(Boolean isDatabase) {
        this.isDatabase = isDatabase;
    }

    public Boolean getIsBillingProcess() {
        return isBillingProcess;
    }

    public void setIsBillingProcess(Boolean isBillingProcess) {
        this.isBillingProcess = isBillingProcess;
    }

    public Set<ClinicInfo> getClinic() {
        return clinic;
    }

    public void setClinic(Set<ClinicInfo> clinic) {
        this.clinic = clinic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

    public String getAddedInfo() {
        return addedInfo;
    }

    public void setAddedInfo(String addedInfo) {
        this.addedInfo = addedInfo;
    }
}
