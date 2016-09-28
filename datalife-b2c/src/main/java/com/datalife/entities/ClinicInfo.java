package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by supriya gondi on 2/1/2015.
 *
 * Entity class for ClinicInfo
 * This is to capture Clinic Information
 */
@DynamicUpdate
@Entity
@Table(name = "clinicInfo", uniqueConstraints = {
        @UniqueConstraint(columnNames = "MRNumber")})
public class ClinicInfo implements Serializable{

    @Id
    @Column(name = "userId")
    private Long userId;

    @Column(name = "clinicName")
    private String clinicName;

    @Column(name = "MRNumber")
    private String mlrNumber;

    @Column(name = "domainName")
    private String domainName;

    @MapsId
    @OneToOne(mappedBy = "clinicInfo",fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "labId")
    private Lab lab;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacyId")
    private Pharmacy pharmacy;

    @Transient
    private String _csrf;


    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getMlrNumber() {
        return mlrNumber;
    }

    public void setMlrNumber(String mlrNumber) {
        this.mlrNumber = mlrNumber;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
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

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
}
