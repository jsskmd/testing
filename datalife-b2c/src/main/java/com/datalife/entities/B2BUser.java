package com.datalife.entities;


import java.util.Date;
import java.util.List;

/**
 * Created by dscblpo on 1/23/2016.
 */
public class B2BUser {

    private Long userId;

    private String userName;

    private String password;

    private String uuid;

    private String password_confirmation;

    private String role;

    private Date creationDate;

    private String activation;

    private boolean enabled=false;

    private String otp;

    private String email;

    private String profileImageUrl;

    private boolean emailVerfied=false;

    private boolean mobileVerified=false;

    private Long doctorId;
    private Long clinicId;
    private Long patientId;
    private Long catSearchId;

    private String searchCat;

    private String reports;
    private String sendType;

    private String fromAppMobActivation;

    private boolean exceeded = false;

    private Float dataLimit;

    private String reportPassword;

    private String imageUrl;

    private String speciality;

    private boolean activate;

    private String flname;

    private String skype;

    private String name;

    private String mobileNo;

    private String location;

    private String city;

    private String state;

    private String imageTicket;

    private String imageFileName;

    private String imageThumbnailFileName;

    private String userParentDir;

    private String alfrescoUrl;

    private Date before;

    private Date after;

    private String patRFV;

    private String _csrf;

    private String alfresAuthTicket;

    private String isFromAppReg;

    private String date;

    private String time;

    private String token;

    private String country;
    private String providerName;
    private String contactperson;
    private String website;

    private String address;

    private int tokenNo;

    private boolean active = true;

    private Long orderId;

    private String workTiming;

    private String workdays;

    private String workPhone;

    private String zipCode;

    private CSRInfo csrInfo;

    private UserContactInfo userContactInfo;

    private ServiceProvider serviceProvider;

    private HospitalInfo hospitalInfo;

    private List<HospitalPackages> hospitalPackages;

    private UserDetails userDetails;

    private DoctorInfo doctorInfo;

    public B2BUser() {
    }

    public B2BUser(String flname, String email,String mobileNo,String speciality) {
        this.mobileNo = mobileNo;
        this.flname = flname;
        this.email = email;
        this.speciality = speciality;
    }

    public B2BUser(Long userId,String flname, String email,String mobileNo,String country,String state, String city,String speciality) {
        this.country = country;
        this.email = email;
        this.flname = flname;
        this.mobileNo = mobileNo;
        this.userId = userId;
        this.city = city;
        this.state = state;
        this.speciality=speciality;

    }

    public B2BUser(Long userId,String username,String flname, String email,String mobileNo) {
        this.userName=username;
        this.email = email;
        this.flname = flname;
        this.mobileNo = mobileNo;
        this.userId = userId;


    }

    public B2BUser(Long userId,String email,String mobileNo,String providerName,String contactperson, String website,String city,String address,String location,String country) {

        this.userId = userId;
        this.email = email;
        this.mobileNo = mobileNo;
        this.providerName = providerName;
        this.contactperson = contactperson;
        this.website = website;
        this.city = city;
        this.address = address;
        this.location = location;
        this.country = country;

    }

    public B2BUser(Long userId,String providerName,String contactperson,String mobileNo,String workPhone,String email,String website,String country,String state,String city,String location,String address,String zipCode,String workTiming,String workdays) {

        this.userId = userId;
        this.providerName = providerName;
        this.contactperson = contactperson;
        this.mobileNo = mobileNo;
        this.workPhone = workPhone;
        this.email = email;
        this.website = website;
        this.country = country;
        this.state = state;
        this.city = city;
        this.location = location;
        this.address = address;
        this.zipCode = zipCode;
        this.workTiming = workTiming;
        this.workdays = workdays;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
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

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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

    public String getFromAppMobActivation() {
        return fromAppMobActivation;
    }

    public void setFromAppMobActivation(String fromAppMobActivation) {
        this.fromAppMobActivation = fromAppMobActivation;
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

    public String getReportPassword() {
        return reportPassword;
    }

    public void setReportPassword(String reportPassword) {
        this.reportPassword = reportPassword;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public String getFlname() {
        return flname;
    }

    public void setFlname(String flname) {
        this.flname = flname;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImageTicket() {
        return imageTicket;
    }

    public void setImageTicket(String imageTicket) {
        this.imageTicket = imageTicket;
    }

    public HospitalInfo getHospitalInfo() {
        return hospitalInfo;
    }

    public void setHospitalInfo(HospitalInfo hospitalInfo) {
        this.hospitalInfo = hospitalInfo;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getImageThumbnailFileName() {
        return imageThumbnailFileName;
    }

    public void setImageThumbnailFileName(String imageThumbnailFileName) {
        this.imageThumbnailFileName = imageThumbnailFileName;
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

    public String get_csrf() {
        return _csrf;
    }

    public void set_csrf(String _csrf) {
        this._csrf = _csrf;
    }

    public String getAlfresAuthTicket() {
        return alfresAuthTicket;
    }

    public void setAlfresAuthTicket(String alfresAuthTicket) {
        this.alfresAuthTicket = alfresAuthTicket;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public CSRInfo getCsrInfo() {
        return csrInfo;
    }

    public void setCsrInfo(CSRInfo csrInfo) {
        this.csrInfo = csrInfo;
    }

    public UserContactInfo getUserContactInfo() {
        return userContactInfo;
    }

    public void setUserContactInfo(UserContactInfo userContactInfo) {
        this.userContactInfo = userContactInfo;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public DoctorInfo getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(DoctorInfo doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<HospitalPackages> getHospitalPackages() {
        return hospitalPackages;
    }

    public void setHospitalPackages(List<HospitalPackages> hospitalPackages) {
        this.hospitalPackages = hospitalPackages;
    }

    public String getWorkTiming() {
        return workTiming;
    }

    public void setWorkTiming(String workTiming) {
        this.workTiming = workTiming;
    }

    public String getWorkdays() {
        return workdays;
    }

    public void setWorkdays(String workdays) {
        this.workdays = workdays;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}

