package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by barath on 7/12/2016.
 */
@DynamicUpdate
@Entity
@Table(name = "pharmacyRequests")
public class PharmacyRequests extends  ServiceRequests implements Serializable{

    @Column(name = "instruction")
    private String instruction;

    @Column(name = "preferredTime")
    private String preferredTime;

    @Column(name = "recordIds")
    private Long recordIds;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    @Column(name = "assignedDate")
    private Date assignedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    @Column(name = "completionDate")
    private Date completionDate;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "statusReason")
    private String statusReason;

    @Transient
    private String fromDate;

    @Transient
    private String toDate;

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getPreferredTime() {
        return preferredTime;
    }

    public void setPreferredTime(String preferredTime) {
        this.preferredTime = preferredTime;
    }

    public Long getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(Long recordIds) {
        this.recordIds = recordIds;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
