package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Dipak on 6/3/2015.
 */
public class Sms {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "smsId", unique = true, nullable = false)
    private Long smsId;

    @Column(name = "message")
    private String message;

    @Column(name = "status")
    private String status;


    @OneToMany(mappedBy = "sms", fetch = FetchType.LAZY)
    private List<Data> data;


    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", status = "+status+", data = "+data+"]";
    }
}
