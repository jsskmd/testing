package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by DATASCRIBE on 2/18/2015.
 */
@DynamicUpdate
@Entity
@Table(name = "clinicPatients", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
public class ClinicPatients {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "patientId")
    private Long patientId;

    @Column(name = "clinicId")
    private Long clinicId;

    @Column(name = "doctorId")
    private Long doctorId;

    public ClinicPatients() {
    }

    public ClinicPatients(Long clinicId, Long patientId) {
        this.clinicId = clinicId;
        this.patientId = patientId;
    }

    public ClinicPatients(Long patientId, Long clinicId, Long doctorId) {
        this.patientId = patientId;
        this.clinicId = clinicId;
        this.doctorId = doctorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
