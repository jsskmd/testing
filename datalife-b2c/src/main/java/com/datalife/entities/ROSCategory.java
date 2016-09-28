package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by supriya gondi on 11/6/2014.
 *
 * Entity class for Review of Systems Types
 */
@DynamicUpdate
@Entity
@Table(name = "roscategories", uniqueConstraints = {
        @UniqueConstraint(columnNames = "categoryId")})
public class ROSCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "categoryId", unique = true, nullable = false)
    private Long categoryId;

    @Column(name = "name")
    private String name;

    public ROSCategory() {
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
