package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Supriya on 1/10/2016.
 */
@DynamicUpdate
@Entity
@Table(name = "providerDetails", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = "licNo")})
public class ProviderDetails implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "licNo")
    private String licNo;

    @Column(name = "contactPerson")
    private String contactPerson;

    @Column(name = "email")
    private String email;

    @Column(name = "mobilePhone")
    private String mobilePhone;

    @Column(name = "workPhone")
    private String workPhone;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "yearofEstablishment")
    private String yearofEstablishment;

    @Column(name = "website")
    private String website;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "fileUrl")
    private String fileUrl;

    @Column(name = "role")
    private String role;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
    @Column(name = "dateTime")
    private Date dateTime;

    @Column(name = "awardsIfAny")
    private String awardsIfAny;

    @Column(name = "accrediations")
    private String accrediations;

    @Column(name = "address")
    private String address;

    @Column(name = "location")
    private String location;

    @Column(name = "country")
    private String country;

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "affiliations")
    private String affiliations;

    @Column(name = "servicesOfLab")
    private String servicesOfLab;

    @Column(name = "experience")
    private String experience;

    @Column(name = "facilities")
    private String facilities;

    @Column(name = "addedInfo")
    private String addedInfo;

    public ProviderDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLicNo() {
        return licNo;
    }

    public void setLicNo(String licNo) {
        this.licNo = licNo;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
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

    public String getYearofEstablishment() {
        return yearofEstablishment;
    }

    public void setYearofEstablishment(String yearofEstablishment) {
        this.yearofEstablishment = yearofEstablishment;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getServicesOfLab() {
            return servicesOfLab;
    }

    public void setServicesOfLab(String servicesOfLab) {
        this.servicesOfLab = servicesOfLab;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getAddedInfo() {
        return addedInfo;
    }

    public void setAddedInfo(String addedInfo) {
        this.addedInfo = addedInfo;
    }
}
