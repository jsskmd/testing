package com.datalife.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by supriya gondi on 12/4/2014.
 * <p/>
 * Entity class for UploadFile
 * This entity has all information about uploaded File
 */
@DynamicUpdate
@Entity
@Table(name = "records", uniqueConstraints = {
        @UniqueConstraint(columnNames = "fileId")})
@JsonIgnoreProperties(value = {"thumbnailFilename", "newFilename", "thumbnailSize", "deleteUrl", "lastModifiedDate", "encDate", "user"})
public class UploadFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fileId", unique = true, nullable = false)
    private Long fileId;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "contentType")
    private String contentType;

    @Column(name = "fileType")
    private String fileType;

    @Column(name = "chiefComplaint")
    private String chiefComplaint;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "url")
    private String url;

    @Column(name = "share")
    private boolean share;

    @Column(name = "clinicId")
    private Long clinicId;

    @Column(name = "doctorId")
    private Long doctorId;

    @Transient
    private Long providerId;

    @Transient
    private String clinicName;

    @Transient
    private String deleteUrl;

    @Transient
    private String dataSize;

    @Transient
    private String vthumbnailUrl;

    @Transient
    private String vurl;

    @Transient
    private String ticket;

    @Transient
    private String vdownloadUrl;

    @Transient
    private String error;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
    @Column(name = "encounterDate")
    private Date encounterDate;

    @Transient
    private String encDate;

    @Transient
    private BigDecimal amount;

    @Transient
    private Long orderId;

    @Transient
    private Long patientId;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
    @Column(name = "lastModifiedDate")
    private Date lastModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounterId")
    private Encounter encounter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    private ServiceRequests serviceRequests;

    public UploadFile() {
    }

    public UploadFile(String fileName, String contentType, String fileType, String chiefComplaint, String diagnosis, String speciality, String url, User user, Date encounterDate,Date lastModifiedDate,boolean share,Long clinicId,Long doctorId) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileType = fileType;
        this.chiefComplaint = chiefComplaint;
        this.diagnosis = diagnosis;
        this.speciality = speciality;
        this.url = url;
        this.user = user;
        this.encounterDate = encounterDate;
        this.lastModifiedDate=lastModifiedDate;
        this.share=share;
        this.clinicId=clinicId;
        this.doctorId=doctorId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getDeleteUrl() {
        return deleteUrl;
    }

    public void setDeleteUrl(String deleteUrl) {
        this.deleteUrl = deleteUrl;
    }

    public Date getEncounterDate() {
        return encounterDate;
    }

    public void setEncounterDate(Date encounterDate) {
        this.encounterDate = encounterDate;
    }

    public String getEncDate() {
        return encDate;
    }

    public void setEncDate(String encDate) {
        this.encDate = encDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getVthumbnailUrl() {
        return vthumbnailUrl;
    }

    public void setVthumbnailUrl(String vthumbnailUrl) {
        this.vthumbnailUrl = vthumbnailUrl;
    }

    public String getVurl() {
        return vurl;
    }

    public void setVurl(String vurl) {
        this.vurl = vurl;
    }

    public String getVdownloadUrl() {
        return vdownloadUrl;
    }

    public void setVdownloadUrl(String vdownloadUrl) {
        this.vdownloadUrl = vdownloadUrl;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDataSize() {
        return dataSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize;
    }

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public ServiceRequests getServiceRequests() {
        return serviceRequests;
    }

    public void setServiceRequests(ServiceRequests serviceRequests) {
        this.serviceRequests = serviceRequests;
    }
}
