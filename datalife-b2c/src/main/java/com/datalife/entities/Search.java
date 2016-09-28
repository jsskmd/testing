package com.datalife.entities;

import java.io.Serializable;

/**
 * Created by Dipak on 4/22/2015.
 */


public class Search implements Serializable {

    private Long id;

    private Long doctorId;

    private String doctorName;

    private String firstName;

    private String lastName;

    private String serviceType;

    private String clinicName;

    private Long clinicId;

    private Long specialityId;

    private String location;

    private String address;

    private String role;

    private String doctorImageUrl;

    private String clinicImageUrl;

    private String city;

    private String specialityName;

    private String qualification;

    private String doctorAffliation;

    private String cdGeneralTime;

    private String consultationFee;

    private String mobilePhone;

    private String homePhone;

    private Byte experience;

    private Byte slotTime;


    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Search)) {
            return false;
        }
        Search other = (Search) obj;
        return this.getDoctorId().equals(other.getDoctorId());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDoctorImageUrl() {
        return doctorImageUrl;
    }

    public void setDoctorImageUrl(String doctorImageUrl) {
        this.doctorImageUrl = doctorImageUrl;
    }

    public String getClinicImageUrl() {
        return clinicImageUrl;
    }

    public void setClinicImageUrl(String clinicImageUrl) {
        this.clinicImageUrl = clinicImageUrl;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDoctorAffliation() {
        return doctorAffliation;
    }

    public void setDoctorAffliation(String doctorAffliation) {
        this.doctorAffliation = doctorAffliation;
    }

    public Long getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Long specialityId) {
        this.specialityId = specialityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCdGeneralTime() {
        return cdGeneralTime;
    }

    public void setCdGeneralTime(String cdGeneralTime) {
        this.cdGeneralTime = cdGeneralTime;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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

    public Byte getExperience() {
        return experience;
    }

    public void setExperience(Byte experience) {
        this.experience = experience;
    }

    public Byte getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(Byte slotTime) {
        this.slotTime = slotTime;
    }

    public String getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(String consultationFee) {
        this.consultationFee = consultationFee;
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
}
