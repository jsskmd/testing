package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Dipak on 2/6/2015.
 *
 * Entity class for Audit
 * This entity is  to log each action in application
 */
@DynamicUpdate
@Entity

public class Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "auditId", unique = true, nullable = false)
    private Long auditId;

    @Column(name = "userName")
    private String userName;

    @Column(name = "clinicId")
    private Long clinicId;

    @Column(name = "ipAddr")
    private String ipAddr;

    @Column(name = "userRole")
    private String userRole;

    @Column(name = "actionPerform")
    private String actionPerform;

    @Column(name = "dateTime")
    private String dateTime;

    @Column(name = "doctorId")
    private Long doctorId;

    @Column(name = "patientId")
    private Long patientId;

    public Audit(String userName, Long clinicId, String ipAddr, String userRole, String actionPerform, String dateTime, Long doctorId, Long patientId) {
        this.userName = userName;
        this.clinicId = clinicId;
        this.ipAddr = ipAddr;
        this.userRole = userRole;
        this.actionPerform = actionPerform;
        this.dateTime = dateTime;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public Audit() {
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getActionPerform() {
        return actionPerform;
    }

    public void setActionPerform(String actionPerform) {
        this.actionPerform = actionPerform;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
