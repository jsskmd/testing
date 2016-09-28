package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by supriya gondi on 2/12/2015.
 * <p/>
 * Entity class for ClinicDoctors
 * This is for Clinic to maintain Doctors list and vice versa
 */
@DynamicUpdate
@Entity
@Table(name = "clinicDoctors", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
public class ClinicDoctors {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "clinicId")
    private Long clinicId;
    @Column(name = "doctorId")
    private Long doctorId;

    private String doctorNodeRef;

    private boolean monStatus_1;
    private String monst_1;
    private String monet_1;
    private boolean monStatus_2=true;
    private String monst_2;
    private String monet_2;

    private boolean tueStatus_1;
    private String tuest_1;
    private String tueet_1;
    private boolean tueStatus_2=true;
    private String tuest_2;
    private String tueet_2;

    private boolean wedStatus_1;
    private String wedst_1;
    private String wedet_1;
    private boolean wedStatus_2=true;
    private String wedst_2;
    private String wedet_2;

    private boolean thuStatus_1;
    private String thust_1;
    private String thuet_1;
    private boolean thuStatus_2=true;
    private String thust_2;
    private String thuet_2;

    private boolean friStatus_1;
    private String frist_1;
    private String friet_1;
    private boolean friStatus_2=true;
    private String frist_2;
    private String friet_2;

    private boolean satStatus_1;
    private String satst_1;
    private String satet_1;
    private boolean satStatus_2=true;
    private String satst_2;
    private String satet_2;

    private boolean sunStatus_1;
    private String sunst_1;
    private String sunet_1;
    private boolean sunStatus_2=true;
    private String sunst_2;
    private String sunet_2;

    private Long specialityId;

    private boolean activate;

    private String consultationFee;

    private Byte experience;

    private String slotTime;

    private Byte slotIntervals;

    @Transient
    private String qualification;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date changeSetEfectFrm;

@Transient
    private String date;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "clinicDoctors")
    private NewTimings newTimings;

    public ClinicDoctors() {
    }

    public ClinicDoctors(Long clinicId, Long doctorId) {
        this.clinicId = clinicId;
        this.doctorId = doctorId;
    }

    public ClinicDoctors(Long clinicId, Long doctorId, Long specialityId) {
        this.clinicId = clinicId;
        this.doctorId = doctorId;
        this.specialityId = specialityId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public boolean isMonStatus_1() {
        return monStatus_1;
    }

    public void setMonStatus_1(boolean monStatus_1) {
        this.monStatus_1 = monStatus_1;
    }

    public String getMonst_1() {
        return monst_1;
    }

    public void setMonst_1(String monst_1) {
        this.monst_1 = monst_1;
    }

    public String getMonet_1() {
        return monet_1;
    }

    public void setMonet_1(String monet_1) {
        this.monet_1 = monet_1;
    }

    public boolean isMonStatus_2() {
        return monStatus_2;
    }

    public void setMonStatus_2(boolean monStatus_2) {
        this.monStatus_2 = monStatus_2;
    }

    public String getMonst_2() {
        return monst_2;
    }

    public void setMonst_2(String monst_2) {
        this.monst_2 = monst_2;
    }

    public String getMonet_2() {
        return monet_2;
    }

    public void setMonet_2(String monet_2) {
        this.monet_2 = monet_2;
    }

    public boolean isTueStatus_1() {
        return tueStatus_1;
    }

    public void setTueStatus_1(boolean tueStatus_1) {
        this.tueStatus_1 = tueStatus_1;
    }

    public String getTuest_1() {
        return tuest_1;
    }

    public void setTuest_1(String tuest_1) {
        this.tuest_1 = tuest_1;
    }

    public String getTueet_1() {
        return tueet_1;
    }

    public void setTueet_1(String tueet_1) {
        this.tueet_1 = tueet_1;
    }

    public boolean isTueStatus_2() {
        return tueStatus_2;
    }

    public void setTueStatus_2(boolean tueStatus_2) {
        this.tueStatus_2 = tueStatus_2;
    }

    public String getTuest_2() {
        return tuest_2;
    }

    public void setTuest_2(String tuest_2) {
        this.tuest_2 = tuest_2;
    }

    public String getTueet_2() {
        return tueet_2;
    }

    public void setTueet_2(String tueet_2) {
        this.tueet_2 = tueet_2;
    }

    public boolean isWedStatus_1() {
        return wedStatus_1;
    }

    public void setWedStatus_1(boolean wedStatus_1) {
        this.wedStatus_1 = wedStatus_1;
    }

    public String getWedst_1() {
        return wedst_1;
    }

    public void setWedst_1(String wedst_1) {
        this.wedst_1 = wedst_1;
    }

    public String getWedet_1() {
        return wedet_1;
    }

    public void setWedet_1(String wedet_1) {
        this.wedet_1 = wedet_1;
    }

    public boolean isWedStatus_2() {
        return wedStatus_2;
    }

    public void setWedStatus_2(boolean wedStatus_2) {
        this.wedStatus_2 = wedStatus_2;
    }

    public String getWedst_2() {
        return wedst_2;
    }

    public void setWedst_2(String wedst_2) {
        this.wedst_2 = wedst_2;
    }

    public String getWedet_2() {
        return wedet_2;
    }

    public void setWedet_2(String wedet_2) {
        this.wedet_2 = wedet_2;
    }

    public boolean isThuStatus_1() {
        return thuStatus_1;
    }

    public void setThuStatus_1(boolean thuStatus_1) {
        this.thuStatus_1 = thuStatus_1;
    }

    public String getThust_1() {
        return thust_1;
    }

    public void setThust_1(String thust_1) {
        this.thust_1 = thust_1;
    }

    public String getThuet_1() {
        return thuet_1;
    }

    public void setThuet_1(String thuet_1) {
        this.thuet_1 = thuet_1;
    }

    public boolean isThuStatus_2() {
        return thuStatus_2;
    }

    public void setThuStatus_2(boolean thuStatus_2) {
        this.thuStatus_2 = thuStatus_2;
    }

    public String getThust_2() {
        return thust_2;
    }

    public void setThust_2(String thust_2) {
        this.thust_2 = thust_2;
    }

    public String getThuet_2() {
        return thuet_2;
    }

    public void setThuet_2(String thuet_2) {
        this.thuet_2 = thuet_2;
    }

    public boolean isFriStatus_1() {
        return friStatus_1;
    }

    public void setFriStatus_1(boolean friStatus_1) {
        this.friStatus_1 = friStatus_1;
    }

    public String getFrist_1() {
        return frist_1;
    }

    public void setFrist_1(String frist_1) {
        this.frist_1 = frist_1;
    }

    public String getFriet_1() {
        return friet_1;
    }

    public void setFriet_1(String friet_1) {
        this.friet_1 = friet_1;
    }

    public boolean isFriStatus_2() {
        return friStatus_2;
    }

    public void setFriStatus_2(boolean friStatus_2) {
        this.friStatus_2 = friStatus_2;
    }

    public String getFrist_2() {
        return frist_2;
    }

    public void setFrist_2(String frist_2) {
        this.frist_2 = frist_2;
    }

    public String getFriet_2() {
        return friet_2;
    }

    public void setFriet_2(String friet_2) {
        this.friet_2 = friet_2;
    }

    public boolean isSatStatus_1() {
        return satStatus_1;
    }

    public void setSatStatus_1(boolean satStatus_1) {
        this.satStatus_1 = satStatus_1;
    }

    public String getSatst_1() {
        return satst_1;
    }

    public void setSatst_1(String satst_1) {
        this.satst_1 = satst_1;
    }

    public String getSatet_1() {
        return satet_1;
    }

    public void setSatet_1(String satet_1) {
        this.satet_1 = satet_1;
    }

    public boolean isSatStatus_2() {
        return satStatus_2;
    }

    public void setSatStatus_2(boolean satStatus_2) {
        this.satStatus_2 = satStatus_2;
    }

    public String getSatst_2() {
        return satst_2;
    }

    public void setSatst_2(String satst_2) {
        this.satst_2 = satst_2;
    }

    public String getSatet_2() {
        return satet_2;
    }

    public void setSatet_2(String satet_2) {
        this.satet_2 = satet_2;
    }

    public boolean isSunStatus_1() {
        return sunStatus_1;
    }

    public void setSunStatus_1(boolean sunStatus_1) {
        this.sunStatus_1 = sunStatus_1;
    }

    public String getSunst_1() {
        return sunst_1;
    }

    public void setSunst_1(String sunst_1) {
        this.sunst_1 = sunst_1;
    }

    public String getSunet_1() {
        return sunet_1;
    }

    public void setSunet_1(String sunet_1) {
        this.sunet_1 = sunet_1;
    }

    public boolean isSunStatus_2() {
        return sunStatus_2;
    }

    public void setSunStatus_2(boolean sunStatus_2) {
        this.sunStatus_2 = sunStatus_2;
    }

    public String getSunst_2() {
        return sunst_2;
    }

    public void setSunst_2(String sunst_2) {
        this.sunst_2 = sunst_2;
    }

    public String getSunet_2() {
        return sunet_2;
    }

    public void setSunet_2(String sunet_2) {
        this.sunet_2 = sunet_2;
    }

    public Long getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Long specialityId) {
        this.specialityId = specialityId;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public String getDoctorNodeRef() {
        return doctorNodeRef;
    }

    public void setDoctorNodeRef(String doctorNodeRef) {
        this.doctorNodeRef = doctorNodeRef;
    }

    public String getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(String consultationFee) {
        this.consultationFee = consultationFee;
    }

    public Byte getExperience() {
        return experience;
    }

    public void setExperience(Byte experience) {
        this.experience = experience;
    }

    public Byte getSlotIntervals() {
        return slotIntervals;
    }

    public void setSlotIntervals(Byte slotIntervals) {
        this.slotIntervals = slotIntervals;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public Date getChangeSetEfectFrm() {
        return changeSetEfectFrm;
    }

    public void setChangeSetEfectFrm(Date changeSetEfectFrm) {
        this.changeSetEfectFrm = changeSetEfectFrm;
    }

    public ClinicDoctors(Long clinicId, Long doctorId, boolean monStatus_1,
                         String monst_1, String monet_1, boolean monStatus_2,
                         String monst_2, String monet_2, boolean tueStatus_1,
                         String tuest_1, String tueet_1, boolean tueStatus_2,
                         String tuest_2, String tueet_2, boolean wedStatus_1,
                         String wedst_1, String wedet_1, boolean wedStatus_2,
                         String wedst_2, String wedet_2, boolean thuStatus_1,
                         String thust_1, String thuet_1, boolean thuStatus_2,
                         String thust_2, String thuet_2, boolean friStatus_1,
                         String frist_1, String friet_1, boolean friStatus_2,
                         String frist_2, String friet_2, boolean satStatus_1,
                         String satst_1, String satet_1, boolean satStatus_2,
                         String satst_2, String satet_2, boolean sunStatus_1,
                         String sunst_1, String sunet_1, boolean sunStatus_2,
                         String sunst_2, String sunet_2) {
        super();
        this.clinicId = clinicId;
        this.doctorId = doctorId;
        this.monStatus_1 = monStatus_1;
        this.monst_1 = monst_1;
        this.monet_1 = monet_1;
        this.monStatus_2 = monStatus_2;
        this.monst_2 = monst_2;
        this.monet_2 = monet_2;
        this.tueStatus_1 = tueStatus_1;
        this.tuest_1 = tuest_1;
        this.tueet_1 = tueet_1;
        this.tueStatus_2 = tueStatus_2;
        this.tuest_2 = tuest_2;
        this.tueet_2 = tueet_2;
        this.wedStatus_1 = wedStatus_1;
        this.wedst_1 = wedst_1;
        this.wedet_1 = wedet_1;
        this.wedStatus_2 = wedStatus_2;
        this.wedst_2 = wedst_2;
        this.wedet_2 = wedet_2;
        this.thuStatus_1 = thuStatus_1;
        this.thust_1 = thust_1;
        this.thuet_1 = thuet_1;
        this.thuStatus_2 = thuStatus_2;
        this.thust_2 = thust_2;
        this.thuet_2 = thuet_2;
        this.friStatus_1 = friStatus_1;
        this.frist_1 = frist_1;
        this.friet_1 = friet_1;
        this.friStatus_2 = friStatus_2;
        this.frist_2 = frist_2;
        this.friet_2 = friet_2;
        this.satStatus_1 = satStatus_1;
        this.satst_1 = satst_1;
        this.satet_1 = satet_1;
        this.satStatus_2 = satStatus_2;
        this.satst_2 = satst_2;
        this.satet_2 = satet_2;
        this.sunStatus_1 = sunStatus_1;
        this.sunst_1 = sunst_1;
        this.sunet_1 = sunet_1;
        this.sunStatus_2 = sunStatus_2;
        this.sunst_2 = sunst_2;
        this.sunet_2 = sunet_2;
    }

   

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public NewTimings getNewTimings() {
        return newTimings;
    }

    public void setNewTimings(NewTimings newTimings) {
        this.newTimings = newTimings;
    }
}
