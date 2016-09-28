package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by supriya gondi on 1/5/2015.
 *
 * Entity class for CardioVascular ,it is one of the category of Physical Examination specific to Cardiologist
 */
@DynamicUpdate
@Entity
@Table(name = "cardioVascular", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cardioVascularId")})
public class CardioVascular {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cardioVascularId", unique = true, nullable = false)
    private Long cardioVascularId;

    @Column(name = "descriptions")
    private String descriptions;

    @ManyToOne
    @JoinColumn(name = "cardioLabelId")
    private CardiovascularLabels cardiovascularLabels;

    @ManyToOne
    @JoinColumn(name = "peId")
    private PhysicalExamination physicalExamination;

    public CardioVascular() {
    }

    public Long getCardioVascularId() {
        return cardioVascularId;
    }

    public void setCardioVascularId(Long cardioVascularId) {
        this.cardioVascularId = cardioVascularId;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public CardiovascularLabels getCardiovascularLabels() {
        return cardiovascularLabels;
    }

    public void setCardiovascularLabels(CardiovascularLabels cardiovascularLabels) {
        this.cardiovascularLabels = cardiovascularLabels;
    }

    public PhysicalExamination getPhysicalExamination() {
        return physicalExamination;
    }

    public void setPhysicalExamination(PhysicalExamination physicalExamination) {
        this.physicalExamination = physicalExamination;
    }
}
