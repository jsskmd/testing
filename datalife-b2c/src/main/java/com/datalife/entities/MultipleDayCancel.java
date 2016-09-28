package com.datalife.entities;

import java.util.Date;

/**
 * Created by Dipak on 6/9/2015.
 */
public class MultipleDayCancel {

    private int diffDay;
    private boolean isDoctorOnLeave;
    private boolean isAfterCurDateToBlock;
    private boolean isBeforeCurDateToBlock ;
    private boolean isCurDateToBlock;
    private boolean isVacaStrIsInBetween;
    private boolean isVacaEndIsInBetween;
    private Long conformAppId;
    private Date vacaStartDate;
    private Date vacaEndDate;

    public int getDiffDay() {
        return diffDay;
    }

    public void setDiffDay(int diffDay) {
        this.diffDay = diffDay;
    }

    public boolean isDoctorOnLeave() {
        return isDoctorOnLeave;
    }

    public void setDoctorOnLeave(boolean isDoctorOnLeave) {
        this.isDoctorOnLeave = isDoctorOnLeave;
    }

    public boolean isBeforeCurDateToBlock() {
        return isBeforeCurDateToBlock;
    }

    public void setBeforeCurDateToBlock(boolean isBeforeCurDateToBlock) {
        this.isBeforeCurDateToBlock = isBeforeCurDateToBlock;
    }

    public boolean isAfterCurDateToBlock() {
        return isAfterCurDateToBlock;
    }

    public void setAfterCurDateToBlock(boolean isAfterCurDateToBlock) {
        this.isAfterCurDateToBlock = isAfterCurDateToBlock;
    }

    public boolean isCurDateToBlock() {
        return isCurDateToBlock;
    }

    public void setCurDateToBlock(boolean isCurDateToBlock) {
        this.isCurDateToBlock = isCurDateToBlock;
    }

    public Date getVacaStartDate() {
        return vacaStartDate;
    }

    public void setVacaStartDate(Date vacaStartDate) {
        this.vacaStartDate = vacaStartDate;
    }

    public Date getVacaEndDate() {
        return vacaEndDate;
    }

    public void setVacaEndDate(Date vacaEndDate) {
        this.vacaEndDate = vacaEndDate;
    }

    public boolean isVacaStrIsInBetween() {
        return isVacaStrIsInBetween;
    }

    public void setVacaStrIsInBetween(boolean isVacaStrIsInBetween) {
        this.isVacaStrIsInBetween = isVacaStrIsInBetween;
    }

    public boolean isVacaEndIsInBetween() {
        return isVacaEndIsInBetween;
    }

    public void setVacaEndIsInBetween(boolean isVacaEndIsInBetween) {
        this.isVacaEndIsInBetween = isVacaEndIsInBetween;
    }

    public Long getConformAppId() {
        return conformAppId;
    }

    public void setConformAppId(Long conformAppId) {
        this.conformAppId = conformAppId;
    }
}
