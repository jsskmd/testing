package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by barath on 5/14/2016.
 */

@DynamicUpdate
@Entity
@Table(name = "serviceRequests", uniqueConstraints = {
        @UniqueConstraint(columnNames = "orderId")})
@Inheritance(strategy=InheritanceType.JOINED)
public class ServiceRequests implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderId", unique = true, nullable = false)
    private Long orderId;

    @Column(name = "providerId")
    private Long providerId;

    @Column(name = "csrId")
    private Long csrId;

    @Column(name = "patientId",nullable = false)
    private Long patientId;

    @Column(name = "status")
    private RequestStatus status=RequestStatus.INITIATED;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    @Column(name = "orderDateTime")
    private Date orderDateTime;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "orderDate")
    private Date orderDate;

    @OneToOne(mappedBy = "serviceRequests", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private UploadFile uploadFile;

    @Transient
    private String mobilePhone;

    @Transient
    private  AlternateServiceContactInfo alternateServiceContactInfo;

    @Transient
    private  PharmacyRequests pharmacyRequests;

    @Transient
    private LabRequests labRequests;

    @Transient
    private SurgeryRequests surgeryRequests;

    @Transient
    private String serviceType;

    @Transient
    private String viewFileUrl;

    @Transient
    private String fileName;

    @Transient
    private String labTestName;

    @Transient
    private String patientName;

    @Transient
    private String _csrf;

    @Transient
    private String file;

    @Transient
    private String contentType;

    @Transient
    private byte[] bytes;

    @Transient
    private String error;

    @Transient
    private String fileType;

    @Transient
    private String before;

    @Transient
    private String after;

    @Transient
    private String searchInput;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String get_csrf() {
        return _csrf;
    }

    public void set_csrf(String _csrf) {
        this._csrf = _csrf;
    }

    public String getViewFileUrl() {
        return viewFileUrl;
    }

    public void setViewFileUrl(String viewFileUrl) {
        this.viewFileUrl = viewFileUrl;
    }


    public String getLabTestName() {
        return labTestName;
    }

    public void setLabTestName(String labTestName) {
        this.labTestName = labTestName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
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

    public Long getCsrId() {
        return csrId;
    }

    public void setCsrId(Long csrId) {
        this.csrId = csrId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public PharmacyRequests getPharmacyRequests() {
        return pharmacyRequests;
    }

    public void setPharmacyRequests(PharmacyRequests pharmacyRequests) {
        this.pharmacyRequests = pharmacyRequests;
    }

    public LabRequests getLabRequests() {
        return labRequests;
    }

    public void setLabRequests(LabRequests labRequests) {
        this.labRequests = labRequests;
    }

    public AlternateServiceContactInfo getAlternateServiceContactInfo() {
        return alternateServiceContactInfo;
    }

    public void setAlternateServiceContactInfo(AlternateServiceContactInfo alternateServiceContactInfo) {
        this.alternateServiceContactInfo = alternateServiceContactInfo;
    }

    public SurgeryRequests getSurgeryRequests() {
        return surgeryRequests;
    }

    public void setSurgeryRequests(SurgeryRequests surgeryRequests) {
        this.surgeryRequests = surgeryRequests;
    }

    public Date getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(Date orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public UploadFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }


}
