package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by barath on 5/14/2016.
 */
@DynamicUpdate
@Entity
@Table(name = "transaction", uniqueConstraints = {
        @UniqueConstraint(columnNames = "transactionId")})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transactionId", unique = true, nullable = false)
    private Long transactionId;

    @Column(name = "userName")
    private String userName;

    @Column(name = "csrId")
    private Long csrId;

    @Column(name = "feedBack")
    private String feedBack;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm a")
    @Column(name = "date")
    private Date date;

    @Transient
    private boolean canEdit;

    @Transient
    private RequestStatus status=RequestStatus.INITIATED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private ServiceRequests serviceRequests;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public ServiceRequests getServiceRequests() {
        return serviceRequests;
    }

    public void setServiceRequests(ServiceRequests serviceRequests) {
        this.serviceRequests = serviceRequests;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public Long getCsrId() {
        return csrId;
    }

    public void setCsrId(Long csrId) {
        this.csrId = csrId;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }


}
