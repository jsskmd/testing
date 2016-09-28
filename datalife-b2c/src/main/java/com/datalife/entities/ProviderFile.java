package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by barath on 1/23/2016.
 */
@DynamicUpdate
@Entity
@Table(name = "providerFile", uniqueConstraints = {
        @UniqueConstraint(columnNames = "fileId")})
public class ProviderFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fileId", unique = true, nullable = false)
    private Long fileId;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "fileUrl")
    private String fileUrl;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
    @Column(name = "dateTime")
    private Date dateTime;

    @Column(name = "orderId")
    private String orderId;

    @Transient
    private String error;

    @ManyToOne
    private ServiceProvider serviceProvider;

    public ProviderFile() {
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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ServiceProvider getServiceProvider() { return serviceProvider; }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}
