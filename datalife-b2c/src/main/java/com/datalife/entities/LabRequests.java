package com.datalife.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Created by barath on 5/14/2016.
 */

@Entity
@Table(name = "labRequests")
public class LabRequests extends ServiceRequests implements Serializable{

    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "labRequestsId", unique = true, nullable = false)
    private Long labRequestsId;*/

    @Column(name = "labtestCatId")
    private String labtestCatId;

    @Column(name = "instruction")
    private String instruction;

    @Column(name = "preferredTime")
    private String preferredTime;

    @Column(name = "recordIds")
    private Long recordIds;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "labReportFileId")
    private Long labReportFileId;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    @Column(name = "assignedDate")
    private Date assignedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    @Column(name = "completionDate")
    private Date completionDate;


    @OneToOne(fetch = FetchType.LAZY,mappedBy = "labRequests",optional = false)
    private AlternateServiceContactInfo alternateServiceContactInfo;


    public LabRequests() {
        super();
    }

    public String getPreferredTime() {
        return preferredTime;
    }

    public void setPreferredTime(String preferredTime) {
        this.preferredTime = preferredTime;
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

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getLabtestCatId() {
        return labtestCatId;
    }

    public void setLabtestCatId(String labtestCatId) {
        this.labtestCatId = labtestCatId;
    }

    public Long getLabReportFileId() {
        return labReportFileId;
    }

    public void setLabReportFileId(Long labReportFileId) {
        this.labReportFileId = labReportFileId;
    }

    public AlternateServiceContactInfo getAlternateServiceContactInfo() {
        return alternateServiceContactInfo;
    }

    public void setAlternateServiceContactInfo(AlternateServiceContactInfo alternateServiceContactInfo) {
        this.alternateServiceContactInfo = alternateServiceContactInfo;
    }

    public Long getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(Long recordIds) {
        this.recordIds = recordIds;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
