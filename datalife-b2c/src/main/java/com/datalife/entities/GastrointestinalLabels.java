package com.datalife.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by Dipak on 1/24/2015.
 * <p/>
 * Entity class for GastrointestinalLabels
 */
@Entity
@Table(name = "gastrointestinalLabels", uniqueConstraints = {
        @UniqueConstraint(columnNames = "gastroLabelId"),
        @UniqueConstraint(columnNames = "gastroLabel")
})
public class GastrointestinalLabels {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gastroLabelId", unique = true, nullable = false)
    private Long gastroLabelId;

    @Column(name = "gastroLabel")
    private String gastroLabel;

    @Column(name = "labelType")
    private String labelType;

    @OneToMany(mappedBy = "gastrointestinalLabels", fetch = FetchType.EAGER)
    private List<Gastrointestinal> gastrointestinals;

    public GastrointestinalLabels() {
    }

    public Long getGastroLabelId() {
        return gastroLabelId;
    }

    public void setGastroLabelId(Long gastroLabelId) {
        this.gastroLabelId = gastroLabelId;
    }

    public String getGastroLabel() {
        return gastroLabel;
    }

    public void setGastroLabel(String gastroLabel) {
        this.gastroLabel = gastroLabel;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public List<Gastrointestinal> getGastrointestinals() {
        return gastrointestinals;
    }

    public void setGastrointestinals(List<Gastrointestinal> gastrointestinals) {
        this.gastrointestinals = gastrointestinals;
    }
}
