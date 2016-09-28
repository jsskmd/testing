package com.datalife.entities;

/**
 * Created by supriya gondi on 9/12/2014.
 *
 * Entity class for User
 * This entity has information required for user to logged into Datalife EMR
 */

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@DynamicUpdate
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "userId"),
        @UniqueConstraint(columnNames = "username")})
@Proxy(lazy = false)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", unique = true, nullable = false)
    private Long userId;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "uuid")
    private String uuid;

    @Transient
    private String password_confirmation;

    @Column(name = "role")
    private String role;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "activation")
    private String activation;

    @Column(name = "enabled")
    private boolean enabled=false;

    @Column(name = "otp")
    private String otp;

    @Transient
    private String email;

    @Transient
    private String profileImageUrl;

    @Column(name = "emailVerfied")
    private boolean emailVerfied=false;

    @Column(name = "mobileVerified")
    private boolean mobileVerified=false;

    @Transient
    private Long doctorId;

    @Transient
    private Long clinicId;

    @Transient
    private Long patientId;

    @Transient
    private Long catSearchId;

    @Transient
    private String searchCat;

    @Transient
    private Long orderId;

    @Transient
    private String reports;

    @Transient
    private String sendType;

    @Transient
    private String fromAppMobActivation;

    @Column(name = "exceeded")
    private boolean exceeded = false;

    @Column(name = "dataLimit")
    private Float dataLimit;

    @Transient
    private String reportPassword;

    @Transient
    private String imageUrl;

    @Transient
    private String speciality;

    @Transient
    private boolean activate;

    @Transient
    private String flname;

    @Transient
    private String name;

    @Transient
    private String mobileNo;

    @Transient
    private String location;

    @Transient
    private String city;

    @Column(name = "imageTicket")
    private String imageTicket;

    @Column(name = "imageFileName")
    private String imageFileName;

    @Column(name = "imageThumbnailFileName")
    private String imageThumbnailFileName;

    @Column(name = "userParentDir")
    private String userParentDir;

    @Column(name = "alfrescoUrl")
    private String alfrescoUrl;


    @Column(name = "serviceType")
    private String serviceType;

    @Transient
    private String dateofBirth;

    @Transient
    private String gender;

    @Transient
    private Date before;

    @Transient
    private Date after;

    @Transient
    private String patRFV;

    @Transient
    private String _csrf;

    @Transient
    private String alfresAuthTicket;

    @Transient
    private String isFromAppReg;

    @Transient
    private String date;

    @Transient
    private String time;

    @Transient
    private int tokenNo;

    @Transient
    private String skype;

    @Transient
    private String state;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Authentication authentication;

    @OneToOne(mappedBy = "user", optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserDetails userDetails;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DoctorInfo doctorInfo;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private ClinicInfo clinicInfo;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private IdCardDetails idCardDetails;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private InsuranceDetails insuranceDetails;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserContactInfo userContactInfo;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserEmergencyInfo> userEmergencyInfos;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserPreferences> userPreferenceses;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private History history;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Encounter> encounters;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Vitals> vitals;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UploadFile> uploadFiles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Bills> bills;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CSRInfo csrInfo;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private HospitalInfo hospitalInfo;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<HospitalPackages> hospitalPackages;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private ServiceProvider serviceProvider;


    public User() {
    }


    public User(Long userId, String userName,String role, String flname, String mobileNo,String email,String state, String city,String sendType,String skype,boolean activate,Long catSearchId) {
        this.userName = userName;
        this.email = email;
        this.flname = flname;
        this.mobileNo = mobileNo;
        this.userId = userId;
        this.city = city;
        this.state = state;
        this.role=role;
        this.sendType=sendType;
        this.skype=skype;
        this.activate=activate;
        this.catSearchId=catSearchId;
    }

    public User(UserDetails userDetails, IdCardDetails idCardDetails, InsuranceDetails insuranceDetails) {
        this.userDetails = userDetails;
        this.idCardDetails = idCardDetails;
        this.insuranceDetails = insuranceDetails;
    }

    public User(Long userId, String flname) {
        this.flname = flname;
        this.userId = userId;
    }

    public User(Long userId, String userName, String flname, String email, String mobileNo) {
        this.userName = userName;
        this.email = email;
        this.flname = flname;
        this.mobileNo = mobileNo;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String get_csrf() {
        return _csrf;
    }

    public void set_csrf(String _csrf) {
        this._csrf = _csrf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReports() {
        return reports;
    }

    public void setReports(String reports) {
        this.reports = reports;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public DoctorInfo getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(DoctorInfo doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public ClinicInfo getClinicInfo() {
        return clinicInfo;
    }

    public void setClinicInfo(ClinicInfo clinicInfo) {
        this.clinicInfo = clinicInfo;
    }

    public IdCardDetails getIdCardDetails() {
        return idCardDetails;
    }

    public void setIdCardDetails(IdCardDetails idCardDetails) {
        this.idCardDetails = idCardDetails;
    }

    public InsuranceDetails getInsuranceDetails() {
        return insuranceDetails;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setInsuranceDetails(InsuranceDetails insuranceDetails) {
        this.insuranceDetails = insuranceDetails;
    }

    public UserContactInfo getUserContactInfo() {
        return userContactInfo;
    }

    public void setUserContactInfo(UserContactInfo userContactInfo) {
        this.userContactInfo = userContactInfo;
    }

    public Set<UserEmergencyInfo> getUserEmergencyInfos() {
        return userEmergencyInfos;
    }

    public void setUserEmergencyInfos(Set<UserEmergencyInfo> userEmergencyInfos) {
        this.userEmergencyInfos = userEmergencyInfos;
    }

    public Set<UserPreferences> getUserPreferenceses() {
        return userPreferenceses;
    }

    public void setUserPreferenceses(Set<UserPreferences> userPreferenceses) {
        this.userPreferenceses = userPreferenceses;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public Set<Encounter> getEncounters() {
        return encounters;
    }

    public void setEncounters(Set<Encounter> encounters) {
        this.encounters = encounters;
    }

    public Set<Vitals> getVitals() {
        return vitals;
    }

    public void setVitals(Set<Vitals> vitals) {
        this.vitals = vitals;
    }

    public Set<UploadFile> getUploadFiles() {
        return uploadFiles;
    }

    public void setUploadFiles(Set<UploadFile> uploadFiles) {
        this.uploadFiles = uploadFiles;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getUserParentDir() {
        return userParentDir;
    }

    public void setUserParentDir(String userParentDir) {
        this.userParentDir = userParentDir;
    }

    public String getAlfrescoUrl() {
        return alfrescoUrl;
    }

    public void setAlfrescoUrl(String alfrescoUrl) {
        this.alfrescoUrl = alfrescoUrl;
    }

    public String getAlfresAuthTicket() {
        return alfresAuthTicket;
    }

    public void setAlfresAuthTicket(String alfresAuthTicket) {
        this.alfresAuthTicket = alfresAuthTicket;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReportPassword() {
        return reportPassword;
    }

    public void setReportPassword(String reportPassword) {
        this.reportPassword = reportPassword;
    }

    public String getImageThumbnailFileName() {
        return imageThumbnailFileName;
    }

    public void setImageThumbnailFileName(String imageThumbnailFileName) {
        this.imageThumbnailFileName = imageThumbnailFileName;
    }

    public String getImageTicket() {
        return imageTicket;
    }

    public void setImageTicket(String imageTicket) {
        this.imageTicket = imageTicket;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }


    public String getFromAppMobActivation() {
        return fromAppMobActivation;
    }

    public void setFromAppMobActivation(String fromAppMobActivation) {
        this.fromAppMobActivation = fromAppMobActivation;
    }

    public String getFlname() {
        return flname;
    }

    public void setFlname(String flname) {
        this.flname = flname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Bills> getBills() {
        return bills;
    }

    public void setBills(Set<Bills> bills) {
        this.bills = bills;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getCatSearchId() {
        return catSearchId;
    }

    public void setCatSearchId(Long catSearchId) {
        this.catSearchId = catSearchId;
    }

    public String getSearchCat() {
        return searchCat;
    }

    public void setSearchCat(String searchCat) {
        this.searchCat = searchCat;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isExceeded() {
        return exceeded;
    }

    public void setExceeded(boolean exceeded) {
        this.exceeded = exceeded;
    }

    public Float getDataLimit() {
        return dataLimit;
    }

    public void setDataLimit(Float dataLimit) {
        this.dataLimit = dataLimit;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getBefore() {
        return before;
    }

    public void setBefore(Date before) {
        this.before = before;
    }

    public Date getAfter() {
        return after;
    }

    public void setAfter(Date after) {
        this.after = after;
    }

    public String getPatRFV() {
        return patRFV;
    }

    public void setPatRFV(String patRFV) {
        this.patRFV = patRFV;
    }

    public String getIsFromAppReg() {
        return isFromAppReg;
    }

    public void setIsFromAppReg(String isFromAppReg) {
        this.isFromAppReg = isFromAppReg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(int tokenNo) {
        this.tokenNo = tokenNo;
    }

    public boolean isEmailVerfied() {
        return emailVerfied;
    }

    public void setEmailVerfied(boolean emailVerfied) {
        this.emailVerfied = emailVerfied;
    }

    public boolean isMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public HospitalInfo getHospitalInfo() {
        return hospitalInfo;
    }

    public void setHospitalInfo(HospitalInfo hospitalInfo) {
        this.hospitalInfo = hospitalInfo;
    }

    public List<HospitalPackages> getHospitalPackages() {
        return hospitalPackages;
    }

    public void setHospitalPackages(List<HospitalPackages> hospitalPackages) {
        this.hospitalPackages = hospitalPackages;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public CSRInfo getCsrInfo() {
        return csrInfo;
    }

    public void setCsrInfo(CSRInfo csrInfo) {
        this.csrInfo = csrInfo;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

}
