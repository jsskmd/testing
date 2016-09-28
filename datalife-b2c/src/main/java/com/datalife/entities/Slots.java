package com.datalife.entities;

import java.util.Date;

/**
 * Created by Dipak on 5/18/2015.
 */
public class Slots {

    private String slotTime;
    private boolean value;
    private Long patientId;

    private String updatedBy;
    private SchedulingStatus status;
    private String scheduledBy;
    private String patientName;
    private Date before;
    private Date after;
    private String imageUrl;
    private int tokenNo;
    private String message;

    public Slots(String slotTime, Long patientId,String updatedBy,String scheduledBy,SchedulingStatus status,String patientName,int tokenNo) {
        this.slotTime = slotTime;
        this.patientId = patientId;
        this.updatedBy = updatedBy;
        this.status = status;
        this.scheduledBy = scheduledBy;
        this.patientName = patientName;
        this.tokenNo = tokenNo;
    }

    public Slots() {
    }

    public int getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(int tokenNo) {
        this.tokenNo = tokenNo;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public SchedulingStatus getStatus() {
        return status;
    }

    public void setStatus(SchedulingStatus status) {
        this.status = status;
    }

    public String getScheduledBy() {
        return scheduledBy;
    }

    public void setScheduledBy(String scheduledBy) {
        this.scheduledBy = scheduledBy;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Date getBefore() {
        return before;
    }

    public void setBefore(Date before) {
        this.before = before;
    }

    public Date getAfter() {
        return after;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAfter(Date after) {
        this.after = after;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
