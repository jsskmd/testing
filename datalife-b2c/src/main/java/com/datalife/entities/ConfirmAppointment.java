package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Dipak on 5/1/2015.
 */
@Entity
@DynamicUpdate
@Table(name = "confirmAppointment",uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class ConfirmAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",unique = true,nullable = false)
    private Long id;

    @Column(name = "doctorId")
    private Long doctorId;

    @Column(name = "clinicId")
    private Long clinicId;

    @Column(name = "patientId")
    private Long patientId;

    @Column(name = "date")
    private String date;

    @Transient
    private String confirmDate;

    @Column(name = "time")
    private String time;

    @Column(name = "status")
    private SchedulingStatus status;

    @Column(name = "remark")
    private String remark;

    @Column(name = "reasonForVisit")
    private String reasonForVisit;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "createdDateTime")
    private String createdDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    @Column(name = "scheduledOn")
    private Date scheduledOn;

    @Column(name = "scheduledBy")
    private String scheduledBy;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    @Column(name = "cancelledOn")
    private Date cancelledOn;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "vacaStartDate")
    private Date vacaStartDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "vacaEndDate")
    private Date vacaEndDate;

    @Column(name = "tokenNo")
    private int tokenNo;

    @Transient
    private int curTokenNo;

    @Transient
    private boolean value;

    @Transient
    private boolean pastDate;

    @Transient
    private boolean curDate;
    @Transient
    private List<Slots> slots;
    
    @Transient
    private List<ConfirmAppointment> multipleDayCancel;

    @Transient
    private String patientName;

    @Transient
    private String clinicName;

    @Transient
    private String before;

    @Transient
    private String after;

    @Transient
    private String doctorName;

    @Transient
    private String password;
    @Transient
    private String userName;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getScheduledOn() {
        return scheduledOn;
    }

    public void setScheduledOn(Date scheduledOn) {
        this.scheduledOn = scheduledOn;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public SchedulingStatus getStatus() {
        return status;
    }

    public void setStatus(SchedulingStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReasonForVisit() {
        return reasonForVisit;
    }

    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }


    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public Date getCancelledOn() {
        return cancelledOn;
    }

    public void setCancelledOn(Date cancelledOn) {
        this.cancelledOn = cancelledOn;
    }

    public Date getVacaStartDate() {
        return vacaStartDate;
    }

    public void setVacaStartDate(Date vacaStartDate) {
        this.vacaStartDate = vacaStartDate;
    }

    public Date getVacaEndDate() {
        return vacaEndDate;
    }

    public void setVacaEndDate(Date vacaEndDate) {
        this.vacaEndDate = vacaEndDate;
    }

    public String getScheduledBy() {
        return scheduledBy;
    }

    public void setScheduledBy(String scheduledBy) {
        this.scheduledBy = scheduledBy;
    }

    public List<ConfirmAppointment> getMultipleDayCancel() {
        return multipleDayCancel;
    }

    public void setMultipleDayCancel(List<ConfirmAppointment> multipleDayCancel) {
        this.multipleDayCancel = multipleDayCancel;

    }

    public int getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(int tokenNo) {
        this.tokenNo = tokenNo;
    }

    public boolean isPastDate() {
        return pastDate;
    }

    public void setPastDate(boolean pastDate) {
        this.pastDate = pastDate;
    }

    public boolean isCurDate() {
        return curDate;
    }

    public void setCurDate(boolean curDate) {
        this.curDate = curDate;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getCurTokenNo() {
        return curTokenNo;
    }

    public void setCurTokenNo(int curTokenNo) {
        this.curTokenNo = curTokenNo;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public List<Slots> getSlots() {
        return slots;
    }

    public void setSlots(List<Slots> slots) {
        this.slots = slots;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
