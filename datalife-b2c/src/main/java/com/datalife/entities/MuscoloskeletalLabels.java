package com.datalife.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by Dipak on 1/24/2015.
 *
 * Entity class for MuscoloskeletalLabels
 */

@Entity
@Table(name = "muscoloskeletalLabels",uniqueConstraints = {
        @UniqueConstraint(columnNames ="muscoloLabelId" ),
        @UniqueConstraint(columnNames ="muscoloLabel")
})
public class MuscoloskeletalLabels {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "muscoloLabelId", unique = true, nullable = false)
    private Long muscoloLabelId;

    @Column(name = "muscoloLabel")
    private String muscoloLabel;

    @Column(name = "labelType")
    private String labelType;

    @OneToMany(mappedBy = "muscoloskeletalLabels", fetch = FetchType.EAGER)
    private List<Muscoloskeletal> muscoloskeletals;

    public MuscoloskeletalLabels() {
    }

    public Long getMuscoloLabelId() {
        return muscoloLabelId;
    }

    public void setMuscoloLabelId(Long muscoloLabelId) {
        this.muscoloLabelId = muscoloLabelId;
    }

    public String getMuscoloLabel() {
        return muscoloLabel;
    }

    public void setMuscoloLabel(String muscoloLabel) {
        this.muscoloLabel = muscoloLabel;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public List<Muscoloskeletal> getMuscoloskeletals() {
        return muscoloskeletals;
    }

    public void setMuscoloskeletals(List<Muscoloskeletal> muscoloskeletals) {
        this.muscoloskeletals = muscoloskeletals;
    }
}
