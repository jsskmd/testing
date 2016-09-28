package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by supriya gondi on 10/23/2014.
 *
 * Entity class for Identity Card Details of User
 */
@DynamicUpdate
@Entity
@Table(name = "idcarddetails", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cardDetailsId")
       /* @UniqueConstraint(columnNames = "idNumber")*/})
public class IdCardDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cardDetailsId", unique = true, nullable = false)
    private Long cardDetailsId;

    @Column(name = "idType")
    private String idType;

    @Column(name = "idNumber")
    private String idNumber;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "otherState")
    private String otherState;

    @Column(name = "otherCountry")
    private String otherCountry;

    @Column(name = "thumbnailFileName")
    private String thumbnailFileName;

    @Column(name = "fileName")
    private String fileName;

    @Transient
    private String stateName;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    public IdCardDetails() {
    }

    public Long getCardDetailsId() {
        return cardDetailsId;
    }

    public void setCardDetailsId(Long cardDetailsId) {
        this.cardDetailsId = cardDetailsId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getThumbnailFileName() {
        return thumbnailFileName;
    }

    public void setThumbnailFileName(String thumbnailFileName) {
        this.thumbnailFileName = thumbnailFileName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
