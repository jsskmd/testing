package com.datalife.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by supriya gondi on 11/8/2014.
 *
 * Entity class for Lab Categories
 */

@Entity
@Table(name = "labcategories", uniqueConstraints = {
        @UniqueConstraint(columnNames = "labCategoriesId")})
public class LabCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "labCategoriesId", unique = true, nullable = false)
    private Long labCategoriesId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "labCategories", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<LabTests> labTests;

    public LabCategories() {
    }

    public Long getLabCategoriesId() {
        return labCategoriesId;
    }

    public void setLabCategoriesId(Long labCategoriesId) {
        this.labCategoriesId = labCategoriesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LabTests> getLabTests() {
        return labTests;
    }

    public void setLabTests(List<LabTests> labTests) {
        this.labTests = labTests;
    }
}
