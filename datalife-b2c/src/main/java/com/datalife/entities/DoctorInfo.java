package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by supriya gondi on 11/3/2014.
 *
 * Entity class for Doctor
 * This entity have Registered Doctor Prefessional Information
 */
@DynamicUpdate
@Entity
@Table(name = "doctorInfo", uniqueConstraints = {
        @UniqueConstraint(columnNames = "doctorInfoId"),
        @UniqueConstraint(columnNames = "MRNumber")})
public class DoctorInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "doctorInfoId", unique = true, nullable = false)
    private Long doctorInfoId;

    @Column(name = "MRNumber")
    private String mlrNumber;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "affiliations")
    private String affiliations;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "accountStatus")
    private boolean accountStatus;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @Transient
    private String _csrf;


    @Transient
    private String name;

    @Transient
    private String specialist;

    @Transient
    private String experience;

    @Transient
    private Long doctorId;


    public DoctorInfo() {
    }

    public Long getDoctorInfoId() {
        return doctorInfoId;
    }

    public void setDoctorInfoId(Long doctorInfoId) {
        this.doctorInfoId = doctorInfoId;
    }

    public String getMlrNumber() {
        return mlrNumber;
    }

    public void setMlrNumber(String mlrNumber) {
        this.mlrNumber = mlrNumber;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(String affiliations) {
        this.affiliations = affiliations;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String get_csrf() {
        return _csrf;
    }

    public void set_csrf(String _csrf) {
        this._csrf = _csrf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public boolean isAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(boolean accountStatus) {
        this.accountStatus = accountStatus;
    }
}
