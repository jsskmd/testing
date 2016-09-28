package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by Supriya on 12/30/2015.
 */
@DynamicUpdate
@Entity
@Table(name = "serviceProvider", uniqueConstraints = {
        @UniqueConstraint(columnNames = "licenceNumber")})
@Inheritance(strategy=InheritanceType.JOINED)
public class ServiceProvider implements Serializable {

    @Id
    @Column(name = "userId")
    private Long userId;

    @Column(name = "name")
    protected String name;

    @Column(name = "contactPerson")
    protected String contactPerson;

    @Column(name = "licenceNumber")
    protected String licenceNumber;

    @Column(name = "establishmentYear")
    protected String establishmentYear;

    @Column(name = "website")
    protected String website;

    @Column(name = "facilities")
    protected String facilities;

    @MapsId
    @OneToOne(mappedBy = "serviceProvider",fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;


    public ServiceProvider() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getEstablishmentYear() {
        return establishmentYear;
    }

    public void setEstablishmentYear(String establishmentYear) {
        this.establishmentYear = establishmentYear;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

/*    public List<ProviderFile> getProviderFile() {
        return providerFile;
    }

    public void setProviderFile(List<ProviderFile> providerFile) {
        this.providerFile = providerFile;
    }*/

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
