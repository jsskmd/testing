package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by Dipak on 2/12/2015.
 */
public class Data
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dataId", unique = true, nullable = false)
    private Long dataId;

    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "status")
    private String status;

    @Column(name = "customid")
    private String customid;

    @Column(name = "customid2")
    private String customid2;

    @Column(name = "customid1")
    private String customid1;

    @Column(name = "mobile")
    private String mobile;

    @ManyToOne
    @JoinColumn(name = "smsId")
    private Sms sms;

    @Transient
    private String ticket;

    public String getTicket ()
    {
        return ticket;
    }

    public void setTicket (String ticket)
    {
        this.ticket = ticket;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getCustomid ()
    {
        return customid;
    }

    public void setCustomid (String customid)
    {
        this.customid = customid;
    }

    public String getCustomid2 ()
    {
        return customid2;
    }

    public void setCustomid2 (String customid2)
    {
        this.customid2 = customid2;
    }

    public String getCustomid1 ()
    {
        return customid1;
    }

    public void setCustomid1 (String customid1)
    {
        this.customid1 = customid1;
    }

    public String getMobile ()
    {
        return mobile;
    }

    public void setMobile (String mobile)
    {
        this.mobile = mobile;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", status = "+status+", customid = "+customid+", customid2 = "+customid2+", customid1 = "+customid1+", mobile = "+mobile+"]";
    }
}
