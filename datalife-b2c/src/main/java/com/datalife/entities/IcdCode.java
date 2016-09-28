package com.datalife.entities;

import javax.persistence.*;

/**
 * Created by Dipak on 12/17/2014.
 * <p/>
 * Entity class for ICD-9 Codes
 */
@Entity
@Table(name = "icdCodes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "icdCode")})
public class IcdCode {

    @Id
    @Column(name = "icdCode")
    private String icdCode;

    @Column(name = "name")
    private String name;

    public IcdCode() {
    }

    public String getIcdCode() {
        return icdCode;
    }

    public void setIcdCode(String icdCode) {
        this.icdCode = icdCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
