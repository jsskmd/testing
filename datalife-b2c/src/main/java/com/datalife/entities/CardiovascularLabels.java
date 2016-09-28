package com.datalife.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by Dipak on 1/22/2015.
 *
 * Entity class for CardiovascularLabels
 */
@Entity
@Table(name = "cardiovascularLabels",uniqueConstraints = {
        @UniqueConstraint(columnNames ="cardioLabelId" ),
        @UniqueConstraint(columnNames ="cardioLabel")
})
public class CardiovascularLabels {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cardioLabelId", unique = true, nullable = false)
    private Long cardioLabelId;

    @Column(name = "cardioLabel")
    private String cardioLabel;

    @Column(name = "labelType")
    private String labelType;

    @OneToMany(mappedBy = "cardiovascularLabels", fetch = FetchType.EAGER)
    private List<CardioVascular> cardioVasculars;

    public CardiovascularLabels() {
    }

    public Long getCardioLabelId() {
        return cardioLabelId;
    }

    public void setCardioLabelId(Long cardioLabelId) {
        this.cardioLabelId = cardioLabelId;
    }

    public String getCardioLabel() {
        return cardioLabel;
    }

    public void setCardioLabel(String cardioLabel) {
        this.cardioLabel = cardioLabel;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public List<CardioVascular> getCardioVasculars() {
        return cardioVasculars;
    }

    public void setCardioVasculars(List<CardioVascular> cardioVasculars) {
        this.cardioVasculars = cardioVasculars;
    }
}
