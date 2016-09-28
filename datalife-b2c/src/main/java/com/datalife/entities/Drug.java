package com.datalife.entities;

import javax.persistence.*;

/**
 * Created by Supriya on 6/24/2015.
 */
@Entity
@Table(name = "drugs", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
public class Drug {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "brandName")
    private String brandName;

    @Column(name = "strength")
    private String strength;

    public Drug() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

}
