package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Created by barath on 1/29/2016.
 */
@DynamicUpdate
@Entity
@Table(name = "hospitalPackages", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
public class HospitalPackages {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "typeOfProcedure")
    private String typeOfProcedure;

    @Column(name = "generalWard")
    private float generalWard;

    @Column(name = "specialWard")
    private float specialWard;

    @Column(name = "deluxWard")
    private float deluxWard;

    @Column(name = "isInscAccepted")
    private boolean isInscAccepted;

    @Column(name = "insGeneralWard")
    private float insGeneralWard;

    @Column(name = "insSpecialWard")
    private float insSpecialWard;

    @Column(name = "addtional")
    private String addtional;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Transient
    private String procedureName;

    @Transient
    private Long userId;

    @Transient
    private String specialityName;

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public float getInsSpecialWard() {
        return insSpecialWard;
    }

    public void setInsSpecialWard(float insSpecialWard) {
        this.insSpecialWard = insSpecialWard;
    }

    public float getInsGeneralWard() {
        return insGeneralWard;
    }

    public void setInsGeneralWard(float insGeneralWard) {
        this.insGeneralWard = insGeneralWard;
    }

    public boolean isInscAccepted() {
        return isInscAccepted;
    }

    public void setInscAccepted(boolean isInscAccepted) {
        this.isInscAccepted = isInscAccepted;
    }

    public float getDeluxWard() {
        return deluxWard;
    }

    public void setDeluxWard(float deluxWard) {
        this.deluxWard = deluxWard;
    }

    public float getSpecialWard() {
        return specialWard;
    }

    public void setSpecialWard(float specialWard) {
        this.specialWard = specialWard;
    }

    public float getGeneralWard() {
        return generalWard;
    }

    public void setGeneralWard(float generalWard) {
        this.generalWard = generalWard;
    }

    public String getTypeOfProcedure() {
        return typeOfProcedure;
    }

    public void setTypeOfProcedure(String typeOfProcedure) {
        this.typeOfProcedure = typeOfProcedure;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddtional() {
        return addtional;
    }

    public void setAddtional(String addtional) {
        this.addtional = addtional;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


}
