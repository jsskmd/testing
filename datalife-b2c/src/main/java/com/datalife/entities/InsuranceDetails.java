package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by supriya gondi on 10/23/2014.
 *
 * Entity class for Insurance Deatils of User
 */
@DynamicUpdate
@Entity
@Table(name = "insurancedetails", uniqueConstraints = {
        @UniqueConstraint(columnNames = "insuranceId")})
public class InsuranceDetails implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "insuranceId", unique = true, nullable = false)
    private Long insuranceId;

    @Column(name = "provider")
    private String provider;

    @Column(name = "policyNumber")
    private String policyNumber;

    @Column(name = "validity")
    private String validity;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    public InsuranceDetails() {
    }

    public Long getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Long insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
