package com.datalife.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by barath on 5/12/2016.
 */
@Entity
@Table(name = "labTestCategories", uniqueConstraints = {
        @UniqueConstraint(columnNames = "labTestCategoriesId")})
public class LabTestCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "labTestCategoriesId", unique = true, nullable = false)
    private Long labTestCategoriesId;

    @Column(name = "labTestName")
    private String labTestName;

    @OneToMany(mappedBy = "labTestCategories", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<LabTestSubCategories> labTestSubCategories;

    public Long getLabTestCategoriesId() {
        return labTestCategoriesId;
    }

    public void setLabTestCategoriesId(Long labTestCategoriesId) {
        this.labTestCategoriesId = labTestCategoriesId;
    }

    public String getLabTestName() {
        return labTestName;
    }

    public void setLabTestName(String labTestName) {
        this.labTestName = labTestName;
    }

    public List<LabTestSubCategories> getLabTestSubCategories() {
        return labTestSubCategories;
    }

    public void setLabTestSubCategories(List<LabTestSubCategories> labTestSubCategories) {
        this.labTestSubCategories = labTestSubCategories;
    }

}
