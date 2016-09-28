package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.util.Date;

/**
 * Created by Supriya on 8/28/2015.
 */
@DynamicUpdate
@Entity
@Table(name = "bills", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
public class Bills {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "date")
    private Date date;

   @Transient
    private String billDate;


    @Transient
    private String billsDate;

    @Column(name = "paidTo")
    private String paidTo;

    @Column(name = "paidFor")
    private String paidFor;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "url")
    private String url;

    @Transient
    private String vUrl;

    @Transient
    private String dUrl;

    @Column(name = "contentType")
    private String contentType;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Bills() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getPaidTo() {
        return paidTo;
    }

    public void setPaidTo(String paidTo) {
        this.paidTo = paidTo;
    }

    public String getPaidFor() {
        return paidFor;
    }

    public void setPaidFor(String paidFor) {
        this.paidFor = paidFor;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getvUrl() {
        return vUrl;
    }

    public void setvUrl(String vUrl) {
        this.vUrl = vUrl;
    }

    public String getdUrl() {
        return dUrl;
    }

    public void setdUrl(String dUrl) {
        this.dUrl = dUrl;
    }

    public String getBillsDate() {
        return billsDate;
    }

    public void setBillsDate(String billsDate) {
        this.billsDate = billsDate;
    }

}
