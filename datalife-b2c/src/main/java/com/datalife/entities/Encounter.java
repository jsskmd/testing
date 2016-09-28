package com.datalife.entities;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by supriya gondi on 11/6/2014.
 * <p/>
 * Entity class for Encounter
 */
@DynamicUpdate
@Entity
@Table(name = "encounter", uniqueConstraints = {
        @UniqueConstraint(columnNames = "encounterId")})
@Proxy(lazy = false)
public class Encounter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "encounterId", unique = true, nullable = false)
    private Long encounterId;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
    @Column(name = "date")
    private Date date;

    @Column(name = "doctorId")
    private Long doctorId;

    @Column(name = "clinicId")
    private Long clinicId;

    @Column(name = "share")
    private boolean share = true;

    @Transient
    private Long patientId;

    @Transient
    private String shift;

    @Transient
    private String age;

    @OneToOne(mappedBy = "encounter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MiniEncounter miniEncounter;

    @OneToOne(mappedBy = "encounter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private SOAP soap;

    @OneToOne(mappedBy = "encounter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Vitals vitals;

    @OneToMany(mappedBy = "encounter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Prescription> prescriptions;

    @OneToMany(mappedBy = "encounter", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PhysicalExamination> physicalExaminations;

    @OneToMany(mappedBy = "encounter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReviewofSystems> reviewofSystems;

    @OneToMany(mappedBy = "encounter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LabOrder> labOrders;

    @OneToMany(mappedBy = "encounter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UploadFile> uploadFiles;

    @Transient
    private History history;

    @Transient
    private String encounterDate;

    @Transient
    private String graphs;

    @Transient
    private String _csrf;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


    @Transient
    private String vthumbnailUrl;

    @Transient
    private String vurl;

    @Transient
    private String ticket;

    @Transient
    private String speciality;

    @Transient
    private String vdownloadUrl;

   @Column
    private String doctorName;

    @Column
    private String clinicName;

    @Column
    private String mobileNumber;

    @Column
    private String mciNumber;

    public Encounter() {
    }

    public Encounter(Long encounterId, String encounterDate, String summaryPath, String prescriptionPath, String labOrderPath) {
        this.encounterId = encounterId;
        this.encounterDate = encounterDate;
       }

    public Long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Long encounterId) {
        this.encounterId = encounterId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public MiniEncounter getMiniEncounter() {
        return miniEncounter;
    }

    public void setMiniEncounter(MiniEncounter miniEncounter) {
        this.miniEncounter = miniEncounter;
    }

    public Vitals getVitals() {
        return vitals;
    }

    public void setVitals(Vitals vitals) {
        this.vitals = vitals;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public List<PhysicalExamination> getPhysicalExaminations() {
        return physicalExaminations;
    }

    public void setPhysicalExaminations(List<PhysicalExamination> physicalExaminations) {
        this.physicalExaminations = physicalExaminations;
    }

    public List<ReviewofSystems> getReviewofSystems() {
        return reviewofSystems;
    }

    public void setReviewofSystems(List<ReviewofSystems> reviewofSystems) {
        this.reviewofSystems = reviewofSystems;
    }

    public List<LabOrder> getLabOrders() {
        return labOrders;
    }

    public void setLabOrders(List<LabOrder> labOrders) {
        this.labOrders = labOrders;
    }

    public String getEncounterDate() {
        return encounterDate;
    }

    public void setEncounterDate(String encounterDate) {
        this.encounterDate = encounterDate;
    }

    public String getGraphs() {
        return graphs;
    }

    public void setGraphs(String graphs) {
        this.graphs = graphs;
    }

    public String get_csrf() {
        return _csrf;
    }

    public void set_csrf(String _csrf) {
        this._csrf = _csrf;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getVthumbnailUrl() {
        return vthumbnailUrl;
    }

    public void setVthumbnailUrl(String vthumbnailUrl) {
        this.vthumbnailUrl = vthumbnailUrl;
    }

    public String getVurl() {
        return vurl;
    }

    public void setVurl(String vurl) {
        this.vurl = vurl;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getVdownloadUrl() {
        return vdownloadUrl;
    }

    public void setVdownloadUrl(String vdownloadUrl) {
        this.vdownloadUrl = vdownloadUrl;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public SOAP getSoap() {
        return soap;
    }

    public void setSoap(SOAP soap) {
        this.soap = soap;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMciNumber() {
        return mciNumber;
    }

    public void setMciNumber(String mciNumber) {
        this.mciNumber = mciNumber;
    }

    public List<UploadFile> getUploadFiles() {
        return uploadFiles;
    }

    public void setUploadFiles(List<UploadFile> uploadFiles) {
        this.uploadFiles = uploadFiles;
    }
}
