package com.datalife.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by Dipak on 1/24/2015.
 * <p/>
 * Entity class for RespiratoryLabels
 */
@Entity
@Table(name = "respiratoryLabels", uniqueConstraints = {
        @UniqueConstraint(columnNames = "respiratoryLabelId"),
        @UniqueConstraint(columnNames = "respiratoryLabel")
})
public class RespiratoryLabels {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "respiratoryLabelId", unique = true, nullable = false)
    private Long respiratoryLabelId;

    @Column(name = "respiratoryLabel")
    private String respiratoryLabel;

    @Column(name = "labelType")
    private String labelType;

    @OneToMany(mappedBy = "respiratoryLabels", fetch = FetchType.EAGER)
    private List<Respiratory> respiratories;

    public RespiratoryLabels() {
    }

    public Long getRespiratoryLabelId() {
        return respiratoryLabelId;
    }

    public void setRespiratoryLabelId(Long respiratoryLabelId) {
        this.respiratoryLabelId = respiratoryLabelId;
    }

    public String getRespiratoryLabel() {
        return respiratoryLabel;
    }

    public void setRespiratoryLabel(String respiratoryLabel) {
        this.respiratoryLabel = respiratoryLabel;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public List<Respiratory> getRespiratories() {
        return respiratories;
    }

    public void setRespiratories(List<Respiratory> respiratories) {
        this.respiratories = respiratories;
    }
}
