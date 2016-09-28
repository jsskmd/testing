package com.datalife.entities;

import javax.persistence.*;

/**
 * Created by barath on 5/12/2016.
 */
@Entity
@Table(name = "labTestSubCategories", uniqueConstraints = {
        @UniqueConstraint(columnNames = "labTestSubCateId")})
public class LabTestSubCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "labTestSubCateId", unique = true, nullable = false)
    private Long labTestSubCateId;

    @Column(name = "labTestSubCateName")
    private String labTestSubCateName;

    @Column(name = "labTestLenOfStay")
    private String labTestLenOfStay;

    @ManyToOne
    @JoinColumn(name = "labTestCategoriesId")
    private LabTestCategories labTestCategories;

    public Long getLabTestSubCateId() {
        return labTestSubCateId;
    }

    public void setLabTestSubCateId(Long labTestSubCateId) {
        this.labTestSubCateId = labTestSubCateId;
    }

    public String getLabTestSubCateName() {
        return labTestSubCateName;
    }

    public void setLabTestSubCateName(String labTestSubCateName) {
        this.labTestSubCateName = labTestSubCateName;
    }

    public LabTestCategories getLabTestCategories() {
        return labTestCategories;
    }

    public void setLabTestCategories(LabTestCategories labTestCategories) {
        this.labTestCategories = labTestCategories;
    }

    public String getLabTestLenOfStay() {
        return labTestLenOfStay;
    }

    public void setLabTestLenOfStay(String labTestLenOfStay) {
        this.labTestLenOfStay = labTestLenOfStay;
    }
}
