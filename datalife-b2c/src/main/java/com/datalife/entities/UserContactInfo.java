package com.datalife.entities;



import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by supriya gondi on 10/23/2014.
 * <p/>
 * Entity class for User Contact Information
 */
@DynamicUpdate
@Entity
@Table(name = "userContactInfo", uniqueConstraints = {
        @UniqueConstraint(columnNames = "userContactInfoId")})

public class UserContactInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userContactInfoId", unique = true, nullable = false)
    private Long userContactInfoId;

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

    @Column(name = "mobilePhone")
    private String mobilePhone;

    @Column(name = "homePhone")
    private String homePhone;

    @Column(name = "email")
    private String email;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "workPhone")
    private String workPhone;

    @Transient
    private String _csrf;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    public UserContactInfo() {
    }

    public Long getUserContactInfoId() {
        return userContactInfoId;
    }

    public void setUserContactInfoId(Long userContactInfoId) {
        this.userContactInfoId = userContactInfoId;
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
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
