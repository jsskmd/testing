package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by supriya gondi on 11/6/2014.
 *
 * Entity class for Authentication
 * This is for Doctor to enter generated OTP to access Patient Records and to write encounter, etc...
 */
@DynamicUpdate
@Entity
@Table(name = "authentication", uniqueConstraints = {
        @UniqueConstraint(columnNames = "authId"),
        @UniqueConstraint(columnNames = "otp")})
public class Authentication implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "authId", unique = true, nullable = false)
    private Long authId;

    @Column(name = "otp")
    private String otp;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    public Authentication() {
    }

    public Authentication(String otp, User user) {
        this.otp = otp;
        this.user = user;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
