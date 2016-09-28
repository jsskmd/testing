package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by barath on 1/23/2016.
 */
@DynamicUpdate
@Entity
@Table(name = "hospitalInfo", uniqueConstraints = {
        @UniqueConstraint(columnNames = "hospitalId")})
public class HospitalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hospitalId", unique = true, nullable = false)
    private Long hospitalId;

    @Column(name = "hospitalName")
    private String hospitalName;

    @Column(name = "specialities")
    private String specialities;

    @Column(name = "awardsIfAny")
    private String awardsIfAny;

    @Column(name = "accrediations")
    private String accrediations;

    @Column(name = "trackRecords")
    private String trackRecords;

    @Column(name = "insuranceAccepted")
    private boolean insuranceAccepted;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;



    public HospitalInfo() {
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public String getAwardsIfAny() {
        return awardsIfAny;
    }

    public void setAwardsIfAny(String awardsIfAny) {
        this.awardsIfAny = awardsIfAny;
    }

    public String getAccrediations() {
        return accrediations;
    }

    public void setAccrediations(String accrediations) {
        this.accrediations = accrediations;
    }

    public String getTrackRecords() {
        return trackRecords;
    }

    public void setTrackRecords(String trackRecords) {
        this.trackRecords = trackRecords;
    }

    public boolean isInsuranceAccepted() {
        return insuranceAccepted;
    }

    public void setInsuranceAccepted(boolean insuranceAccepted) {
        this.insuranceAccepted = insuranceAccepted;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
