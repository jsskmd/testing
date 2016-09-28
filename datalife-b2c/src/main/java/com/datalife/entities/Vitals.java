package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by supriya gondi on 10/31/2014.
 * <p/>
 * Entity class for Vitals of Patient entered By Doctor or Self Monitored
 */
@DynamicUpdate
@Entity
@Table(name = "vitals", uniqueConstraints = {
        @UniqueConstraint(columnNames = "vitalsId")})
public class Vitals {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vitalsId", unique = true, nullable = false)
    private Long vitalsId;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
    @Column(name = "date")
    private Date date;

    @Column(name = "temperature")
    private String temp;

    @Column(name = "bp")
    private String bp;

    @Column(name = "respRate")
    private String respRate;

    @Column(name = "heartRate")
    private String heartRate;

    @Column(name = "height")
    private String height;

    @Column(name = "weight")
    private String weight;

    @Column(name = "bmi")
    private String bmi;

    @Column(name = "sugar")
    private String sugar;

    @Column(name = "monitored")
    private boolean monitored;

    @Transient
    private String dateString;

    @OneToOne
    @JoinColumn(name = "encounterId")
    private Encounter encounter;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Vitals() {
    }

    public Long getVitalsId() {
        return vitalsId;
    }

    public void setVitalsId(Long vitalsId) {
        this.vitalsId = vitalsId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getRespRate() {
        return respRate;
    }

    public void setRespRate(String respRate) {
        this.respRate = respRate;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }

    public boolean isMonitored() {
        return monitored;
    }

    public void setMonitored(boolean monitored) {
        this.monitored = monitored;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
