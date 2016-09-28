package com.datalife.entities;

/**
 * Created by Dipak on 2/12/2015.
 */
public class AlfrescoAuthTicket {

    private Data data;
    private Status status;
    private String fileName;
    private String nodeRef;
    private String success;

    private String url;
    private String userName;
    private boolean enabled;
    private String firstName;
    private String lastName;
    private String jobtitle;
    private String organization;
    private String organizationId;
    private String location;
    private String telephone;
    private String mobile;
    private String email;
    private String companyaddress1;
    private String companyaddress2;
    private String companyaddress3;
    private String companypostcode;
    private String companytelephone;
    private String companyfax;
    private String companyemail;
    private String skype;
    private String instantmsg;
    private String userStatus;
    private String userStatusTime;
    private String googleusername;
    private String quota;
    private String sizeCurrent;
    private boolean emailFeedDisabled;
    private String persondescription;
    private String authorizationStatus;
    private Boolean isDeleted;
    private Boolean isAdminAuthority;

    private Capabilities capabilities;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getNodeRef() {
        return nodeRef;
    }

    public void setNodeRef(String nodeRef) {
        this.nodeRef = nodeRef;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersondescription() {
        return persondescription;
    }

    public void setPersondescription(String persondescription) {
        this.persondescription = persondescription;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyaddress1() {
        return companyaddress1;
    }

    public void setCompanyaddress1(String companyaddress1) {
        this.companyaddress1 = companyaddress1;
    }

    public String getCompanyaddress2() {
        return companyaddress2;
    }

    public void setCompanyaddress2(String companyaddress2) {
        this.companyaddress2 = companyaddress2;
    }

    public String getCompanyaddress3() {
        return companyaddress3;
    }

    public void setCompanyaddress3(String companyaddress3) {
        this.companyaddress3 = companyaddress3;
    }

    public String getCompanypostcode() {
        return companypostcode;
    }

    public void setCompanypostcode(String companypostcode) {
        this.companypostcode = companypostcode;
    }

    public String getCompanytelephone() {
        return companytelephone;
    }

    public void setCompanytelephone(String companytelephone) {
        this.companytelephone = companytelephone;
    }

    public String getCompanyfax() {
        return companyfax;
    }

    public void setCompanyfax(String companyfax) {
        this.companyfax = companyfax;
    }

    public String getCompanyemail() {
        return companyemail;
    }

    public void setCompanyemail(String companyemail) {
        this.companyemail = companyemail;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getInstantmsg() {
        return instantmsg;
    }

    public void setInstantmsg(String instantmsg) {
        this.instantmsg = instantmsg;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserStatusTime() {
        return userStatusTime;
    }

    public void setUserStatusTime(String userStatusTime) {
        this.userStatusTime = userStatusTime;
    }

    public String getGoogleusername() {
        return googleusername;
    }

    public void setGoogleusername(String googleusername) {
        this.googleusername = googleusername;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public String getSizeCurrent() {
        return sizeCurrent;
    }

    public void setSizeCurrent(String sizeCurrent) {
        this.sizeCurrent = sizeCurrent;
    }

    public boolean isEmailFeedDisabled() {
        return emailFeedDisabled;
    }

    public void setEmailFeedDisabled(boolean emailFeedDisabled) {
        this.emailFeedDisabled = emailFeedDisabled;
    }

    public Capabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Capabilities capabilities) {
        this.capabilities = capabilities;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsAdminAuthority() {
        return isAdminAuthority;
    }

    public void setIsAdminAuthority(Boolean isAdminAuthority) {
        this.isAdminAuthority = isAdminAuthority;
    }

    public String getAuthorizationStatus() {
        return authorizationStatus;
    }

    public void setAuthorizationStatus(String authorizationStatus) {
        this.authorizationStatus = authorizationStatus;
    }
}
