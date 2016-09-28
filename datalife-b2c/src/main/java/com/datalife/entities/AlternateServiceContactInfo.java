package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by barath on 5/14/2016.
 */

@DynamicUpdate
@Entity
@Table(name = "alternateServiceContactInfo", uniqueConstraints = {
        @UniqueConstraint(columnNames = "altServContactInfoId")})
public class AlternateServiceContactInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "altServContactInfoId", unique = true, nullable = false)
    private Long altServContactInfoId;

    @Column(name = "address")
    private String address;

    @Column(name = "location")
    private String location;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "otherState")
    private String otherState;

    @Column(name = "otherCountry")
    private String otherCountry;

    @Column(name = "zipCode")
    private String zipCode;


    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "orderId")
    private LabRequests labRequests;


    public AlternateServiceContactInfo(String address, String location, String city, String state, String country, String otherState, String otherCountry, String zipCode, LabRequests labRequests) {
        this.address = address;
        this.location = location;
        this.city = city;
        this.state = state;
        this.country = country;
        this.otherState = otherState;
        this.otherCountry = otherCountry;
        this.zipCode = zipCode;
        this.labRequests = labRequests;
    }

    public AlternateServiceContactInfo() {
    }

    public Long getAltServContactInfoId() {
        return altServContactInfoId;
    }

    public void setAltServContactInfoId(Long altServContactInfoId) {
        this.altServContactInfoId = altServContactInfoId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOtherState() {
        return otherState;
    }

    public void setOtherState(String otherState) {
        this.otherState = otherState;
    }

    public String getOtherCountry() {
        return otherCountry;
    }

    public void setOtherCountry(String otherCountry) {
        this.otherCountry = otherCountry;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public LabRequests getLabRequests() {
        return labRequests;
    }

    public void setLabRequests(LabRequests labRequests) {
        this.labRequests = labRequests;
    }
}