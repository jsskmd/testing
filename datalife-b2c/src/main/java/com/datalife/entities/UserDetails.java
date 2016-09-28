package com.datalife.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by supriya gondi on 10/23/2014.
 * <p/>
 * Entity class for UserDetails
 * This entity has Basic Information of User i.e Blood Group, Gender ,etc...
 */
@DynamicUpdate
@Entity
@Table(name = "userdetails", uniqueConstraints = {
        @UniqueConstraint(columnNames = "userDetailsId")})
public class UserDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userDetailsId", unique = true, nullable = false)
    private Long userDetailsId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "localLanguage")
    private String localLanguage;

    @Column(name = "dateofBirth")
    private String dateofBirth;

    @Column(name = "gender")
    private String gender;

    @Column(name = "bloodGroupId")
    private Integer bloodGroup;


    @Column(name = "maritalStatus")
    private String maritalStatus;

    @Column(name = "religion")
    private String religion;

    @Column(name = "policeStation")
    private String policeStation;

    @OneToOne
    @JoinColumn(name = "userId")

    private User user;

    public UserDetails() {
    }

    public Long getUserDetailsId() {
        return userDetailsId;
    }

    public void setUserDetailsId(Long userDetailsId) {
        this.userDetailsId = userDetailsId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocalLanguage() {
        return localLanguage;
    }

    public void setLocalLanguage(String localLanguage) {
        this.localLanguage = localLanguage;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(Integer bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }
}
