package com.datalife.entities;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by dscblpo on 4/1/2016.
 */


@Entity
@Table(name = "lab")
public class Lab extends ServiceProvider implements Serializable{

    @Column(name = "accrediations")
    private String accrediations;

    @Column(name = "services")
    private String services;

    @Column(name = "workdays")
    private String workdays;

    @Column(name = "workTiming")
    private String workTiming;

    @Column(name = "addedInfo")
    private String addedInfo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lab",fetch = FetchType.LAZY)
    private Set<ClinicInfo> clinic;

    @Transient
    private String location;

    @Transient
    private String labName;

    @Transient
    private Long labId;

    @Transient
    private Long clinicId;

    public Lab() {
    }

    public Lab(Long labId,String labName,String location){
        this.labId = labId;
        this.labName = labName;
        this.location = location;
    }


    public String getWorkTiming() {
        return workTiming;
    }

    public void setWorkTiming(String workTiming) {
        this.workTiming = workTiming;
    }

    public String getWorkdays() {
        return workdays;
    }

    public void setWorkdays(String workdays) {
        this.workdays = workdays;
    }

    public String getAccrediations() {
        return accrediations;
    }

    public void setAccrediations(String accrediations) {
        this.accrediations = accrediations;
    }

    public String getAddedInfo() {
        return addedInfo;
    }

    public void setAddedInfo(String addedInfo) {
        this.addedInfo = addedInfo;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getLabId() {
        return labId;
    }

    public void setLabId(Long labId) {
        this.labId = labId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

}
