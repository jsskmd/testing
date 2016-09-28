package com.datalife.entities;

import javax.persistence.*;

/**
 * Created by supriya gondi on 11/8/2014.
 *
 * Entity class for Lab Test
 */

@Entity
@Table(name = "labtests")
public class LabTests {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "labTestsId", unique = true, nullable = false)
    private Long labTestsId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "labCategoriesId")
    private LabCategories labCategories;

    public LabTests() {
    }

    public Long getLabTestsId() {
        return labTestsId;
    }

    public void setLabTestsId(Long labTestsId) {
        this.labTestsId = labTestsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LabCategories getLabCategories() {
        return labCategories;
    }

    public void setLabCategories(LabCategories labCategories) {
        this.labCategories = labCategories;
    }
}