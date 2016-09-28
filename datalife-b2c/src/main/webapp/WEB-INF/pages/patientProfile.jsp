<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="resources/css/jquery-ui.min.css">
<link rel="stylesheet" href="resources/css/idstyle.css">

<div class="wrapper">
<div class="form-container">
<div id="tmm-form-wizard" class="container substrate register-margin">
<div class="row clr form-login-heading">
    <div class="col-md-6 col-sm-6 no-pad"><h2 class="app_for_patients_cls">Demographics </h2></div>
    <div class="col-md-6 col-sm-6 right-document ">
        <a target=_blank id="dataLifeIdCard" class="view_cls_pat" >DataLife ID Card</a>
    </div>
</div>
<div id="message"></div>

<!--/ .row-->
<div class=" stage-container">
    <div class="stage tmm-success col-md-3 col-sm-3 " id="a_basicDetails"><a>
        <div class="stage-header head-icon head-icon-lock"></div>
        <div class="stage-content">
            <h3 class="stage-title">Personal Details</h3>
        </div>
    </a></div>
    <!--/ .stage-->
    <div class="stage col-md-3 col-sm-3 " id="a_contactDetails"><a>
        <div class="stage-header head-icon head-icon-user"></div>
        <div class="stage-content">
            <h3 class="stage-title">Contact Details</h3>
        </div>
    </a></div>

    <div class="stage col-md-3 col-sm-3 " id="a_emergencyDetails"><a>
        <div class="stage-header head-icon head-icon-details"></div>
        <div class="stage-content">
            <h3 class="stage-title">Emergency Details</h3>
        </div>
    </a></div>
    <div class="stage col-md-3 col-sm-3 " id="a_preferences"><a>
        <div class="stage-header head-icon head-icon-preferences"></div>
        <div class="stage-content">
            <h3 class="stage-title">Preferences</h3>
        </div>
    </a></div>
    <div class="clear"></div>
    <!--/ .stage-->
</div>
<div id="messages" class="hide"></div>
<div id="basicDetails" class="hide">
<form:form method="post" commandName="user" id="basicinformation" enctype='application/json'>
<!--/ .row-->
<div >
    <div class="col-md-8">
        <div class="form-header">
            <div class="form-title form-icon title-icon-lock"><b>Personal Details</b></div>
        </div>
        <!--/ .form-header-->
    </div>
    <div class="col-md-4">
        <div class="form-header-demo">
            <input type="button" value="Cancel" class="saveb editbasicinfo cancelbtns" id="cancelbasicinfo">
            <input type="submit" value="Save" class="saveb editbasicinfo-vitals submit_btn" id="savebasicinfo">
        </div>
    </div>
</div>
<!--/ .row-->

<div class="form-wizard">
    <div class="">
        <div >
            <div class="col-md-6 no-padding">
                <div class="col-md-6 col-sm-6 no-pad ">
                    <fieldset class="input-block">
                        <label>First Name<span style="color:red;">*</span></label>
                        <input type="hidden" id="at_userId" name="userId"/>
                        <input type="text" id="t_firstName" name="userDetails.firstName"
                               class="form-icon form-icon-user" placeholder="First Name"
                               required title="First Name" pattern="^[a-zA-Z ]+$" maxlength="15"/>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
                <div class="col-md-6 col-sm-6 no-pad ">
                    <fieldset class="input-block">
                        <label>Last Name<span style="color:red;">*</span></label>
                        <input type="text" id="t_lastName" name="userDetails.lastName" placeholder="Last Name" required
                               title="Last Name" pattern="^[a-zA-Z ]+$" maxlength="15"/>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
            </div>
            <div class="col-md-6  no-padding">
                <div class="col-md-6 col-sm-6 no-pad ">
                    <div class="col-md-6 col-sm-6 no-pad ">

                        <fieldset class="input-block">
                            <label>Date of Birth<span style="color:red;">*</span> </label>
                            <input type="text" placeholder="dd/MM/yyyy" required title="Date of Birth"
                                   id="t_dateofBirth" name="userDetails.dateofBirth" maxlength="10"
                                   pattern="^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$"
                                   onblur="checkDateofBirth(this.value);this.checkValidity();"/>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad ">
                        <fieldset class="input-block">
                            <label>Gender<span style="color:red;">*</span></label>

                            <div class="dropdown">
                                <select name="userDetails.gender" class="dropdown-select" id="t_gender" required>
                                    <option disabled="" value="" selected="">Select</option>
                                    <option value="Male">Male</option>
                                    <option value="Female">Female</option>
                                    <option value="Indeterminate">Indeterminate</option>
                                    <option value="Not Identified">Not Identified</option>
                                </select>
                            </div>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>

                </div>
                <div class="col-md-6 col-sm-6 no-pad ">
                    <div class="col-md-6 col-sm-6 no-pad ">
                        <fieldset class="input-block">
                            <label>Blood Group<span style="color:red;">*</span></label>
                            <div class="dropdown">
                                <form:select path="userDetails.bloodGroup" id="t_bloodGroup" name="thelist" required="true"
                                             title="Blood Group" cssClass="dropdown-select"></form:select>
                            </div>
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad ">
                        <fieldset class="input-block">
                            <label>Marital Status</label>
                            <div class="dropdown">
                                <select name="userDetails.maritalStatus" class="dropdown-select" id="t_maritalStatus">
                                    <option value="" disabled="" selected="">Select</option>
                                    <option value="single">Unmarried</option>
                                    <option value="married">Married</option>
                                </select>
                            </div>
                        </fieldset>
                    </div>
                </div>

            </div>
        </div>
        <div >
            <div class="col-md-6  no-padding">
                <div class="col-md-6 col-sm-6 no-pad  ">
                    <div class="col-md-6 col-sm-6 no-pad ">
                        <fieldset class="input-block">
                            <label>Language </label>
                            <input type="text" id="t_localLanguage" name="userDetails.localLanguage"
                                   placeholder="Language" title="Language" pattern="^[a-zA-Z ]+$" maxlength="15"/>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad ">
                        <fieldset class="input-block">
                            <label>Religion</label>
                            <input type="text" id="t_religion" name="userDetails.religion" placeholder="Religion"
                                   title="Religion" pattern="^[a-zA-Z ]+$" maxlength="15"/>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>
                <div class="col-md-6 col-sm-6 no-pad  ">

                    <fieldset class="input-block">
                        <label> Nearest Police Station</label>
                        <input type="text" id="t_policeStation" name="userDetails.policeStation"
                               placeholder="Police Station"
                               title="Police Station" pattern="^[a-zA-Z ]+$" maxlength="25"/>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
            </div>
            <div class="col-md-6  no-padding">


            </div>
        </div>
        <div class="row">
            <div class="col-md-6  no-padding">

            </div>
        </div>
    </div>
</div>
<!--/ .row-->
<!--/ .form-wizard-->

<div  >
    <div class="col-xs-12 border-id-dt iddetils">
        <div class="form-header">
            <div class="form-title form-icon title-icon-lock  col-md-2   "><b>Identity Details</b></div>
            <div class="col-md-10 col-xs-12 ">
                <input type="checkbox" value="t_idcheckbox" id="t_idcheckbox">
                <span class="checkbox"> Click checkbox to add Identity Details</span></div>
        </div>
        <!--/ .form-header-->
    </div>
</div>
<div id="idmessages"></div>
<div class="clearfix"></div>
<div class="form-wizard" id="t_iddetails">
    <div class="">
        <div >
            <div class="col-md-6 no-padding">
                <div class="col-md-6 col-sm-6 no-pad ">
                    <fieldset class="input-block">
                        <label>Type</label>
                        <div class="dropdown">
                            <select name="idCardDetails.idType" class="dropdown-select" id="t_idType">
                                <option disabled="" value="" selected="">Select Type</option>
                                <option value="Adhar">Adhaar</option>
                                    <%--<option value="PAN">PAN</option>
                                    <option value="Driving Licence">Driving Licence</option>
                                    <option value="SSN">SSN</option>
                                    <option value="SIN">SIN</option>--%>
                            </select>
                        </div>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
                <div class="col-md-6 col-sm-6 no-pad ">
                    <fieldset class="input-block">
                        <label> ID Number</label>
                        <input type="text" name="idCardDetails.idNumber" id="t_idNumber" placeholder="Id number"
                               title="Adhaar Number should be exact 12 digit" maxlength="12" pattern="^[0-9]+$"/>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
                <div class="clearfix"></div>

            </div>
            <div class="col-md-6  no-padding">
                <div class="col-md-6 col-sm-6 no-pad ">
                    <fieldset class="input-block">
                        <label> Country</label>

                        <div class="dropdown">
                            <form:select id="t_idcardcountry" path="idCardDetails.country" name="thelist"
                                         onChange="combo('t_idcardcountry','t_idcardstate','t_idcardstateInput');"
                                         cssClass="dropdown-select"></form:select>
                        </div>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
                <div class="col-md-6 col-sm-6 no-pad hide" id="dt_idcardstate">
                    <fieldset class="input-block">
                        <label>State</label>

                        <div class="dropdown">
                            <form:select path="idCardDetails.state" id="t_idcardstate" title="State Name"
                                         cssClass="dropdown-select"></form:select>

                        </div>
                        <!--/ .dropdown-->
                        <!--/ .tooltip-->
                    </fieldset>
                </div>

                <div class="col-md-6 col-sm-6 no-pad hide" id="dt_idcardstateInput">
                    <fieldset class="input-block">
                        <label>State</label>
                        <input type="text" name="idCardDetails.otherState" id="t_idcardstateInput"
                               placeholder="State Name" title="Id Number" pattern="^[a-zA-Z ]+$" maxlength="20"/>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
            </div>

        </div>
        <div class="float-id-upoad">
            <button class="button button-control" type="button" id="upload_id_but"><span><b>UPLOAD ID</b></span>
            </button>

        </div>
        <div class="row"></div>
    </div>
</div>
<!--/ .row-->
<!--/ .form-wizard-->

<div >
    <div class="col-xs-12 border-id-dt indetils">
        <div class="form-header">
            <div class="form-title form-icon title-icon-lock  col-md-2"><b>Insurance Details</b></div>
            <div class="col-md-10 col-xs-12 ">
                <input type="checkbox" class="idcheckbox" value="t_incheckbox">
                <span class="checkbox"> Click checkbox to add your Health Insurance details</span>
            </div>
        </div>
        <!--/ .form-header-->
    </div>
</div>

<div class="form-wizard" id="t_insurancedetails">
    <div class="">
        <div >
            <div class="col-md-6 no-padding">
                <div class="col-md-6 col-sm-6 no-pad ">
                    <fieldset class="input-block">
                        <label>Insurance Provider</label>
                        <input type="text" name="insuranceDetails.provider" id="t_provider"
                               class="form-icon form-icon-user" placeholder="Insurance Provider"
                               title="Insurance Provider" pattern="^[a-zA-Z ]+$" maxlength="20"/>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
                <div class="col-md-6 col-sm-6 no-pad ">
                    <fieldset class="input-block">
                        <label> Policy Number</label>
                        <input type="text" name="insuranceDetails.policyNumber" id="t_policyNumber"
                               placeholder="Policy Number" title="Policy Number" maxlength="20"/>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
            </div>
            <div class="col-md-6  no-padding">
                <div class="col-md-6 col-sm-6 no-pad ">
                    <fieldset class="input-block">
                        <label> Validity</label>
                        <input type="text" placeholder="Validity" title="Validity"
                               name="insuranceDetails.validity" id="t_validity" maxlength="10"
                               pattern="(((0[1-9]|[12][0-9]|3[01])([/])(0[13578]|10|12)([/])(\d{4}))|(([0][1-9]|[12][0-9]|30)([/])(0[469]|11)([/])(\d{4}))|((0[1-9]|1[0-9]|2[0-8])([/])(02)([/])(\d{4}))|((29)(\.|-|\/)(02)([/])([02468][048]00))|((29)([/])(02)([/])([13579][26]00))|((29)([/])(02)([/])([0-9][0-9][0][48]))|((29)([/])(02)([/])([0-9][0-9][2468][048]))|((29)([/])(02)([/])([0-9][0-9][13579][26])))"
                               onblur="checkValidity(this.value);this.checkValidity();"/>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
            </div>
        </div>
    </div>
</div>
<!--/ .row-->
<!--/ .form-wizard-->
<div class="prev" style="display: none;">
    <button class="button button-control" type="button"><span>Prev Step <b>Account Information</b></span></button>
    <div class="button-divider"></div>
</div>
<div class="next">
    <button class="button button-control" type="button" id="b_contactDetails"><span>Next Step <b>Contact
        Details</b></span></button>
    <div class="button-divider"></div>
</div>
</form:form>
</div>
<div id="view_basicDetails">
    <form action="/">
        <div >
            <div class="col-md-8">
                <div class="form-header">
                    <div class="form-title form-icon title-icon-lock"><b>Personal Details</b></div>
                </div>
                <!--/ .form-header-->
            </div>
            <div class="col-md-4">
                <div class="form-header-demo">
                    <input type="button" value="Edit" class="saveb editbasicinfo" id="editbasicinfo">
                    <!--<input type="button"  value="Save" class="saveb savebasicinfo">-->
                </div>
            </div>
        </div>
        <div class="form-wizard">
            <div >
                <div class="col-md-12 col-sm-12 no-pad">
                    <div class="data-container">
                        <dl>
                            <dt>First Name</dt>
                            <%--:--%>
                            <dd id="firstName"> </dd>
                        </dl>

                        <dl>
                            <dt>Last Name</dt>
                            <%--:--%>
                            <dd id="lastName"></dd>
                        </dl>
                        <dl>
                            <dt>Datalife ID</dt>
                            <%--:--%>
                            <dd id="datalifeId"></dd>
                        </dl>
                        <dl>
                            <dt>Language</dt>
                            <%-- :--%>
                            <dd id="localLanguage"></dd>
                        </dl>
                        <dl>
                            <dt>Date of Birth</dt>
                            <%--:--%>
                            <dd id="dateofBirth"> </dd>
                        </dl>
                        <dl>
                            <dt>Gender</dt>
                            <%-- :--%>
                            <dd id="gender"> </dd>
                        </dl>
                        <dl>
                            <dt>Blood Group</dt>
                            <%--:--%>
                            <dd id="bloodGroup"></dd>
                        </dl>
                        <dl>
                            <dt>Marital Status</dt>
                            <%-- :--%>
                            <dd id="maritalStatus"></dd>
                        </dl>
                        <dl>
                            <dt>Religion</dt>
                            <%--:--%>
                            <dd id="religion"></dd>
                        </dl>
                        <dl>
                            <dt>Nearest Police Station</dt>
                            <%-- :--%>
                            <dd id="policeStation"></dd>
                        </dl>
                    </div>
                    <!--/ .data-container-->
                </div>
            </div>
            <!--/ .row-->
        </div>
        <div >
            <div class="col-xs-12 border-id-dt iddetils">
                <div class="form-header">
                    <div class="form-title form-icon title-icon-lock  col-md-2"><b>Identity Details</b></div>
                    <div class="col-md-10 col-xs-12 ">
                        <input type="checkbox" value="idcheckbox" id="idcheckbox">
                        <span class="checkbox"> Click checkbox to view your Identity Details</span></div>
                </div>
            </div>
        </div>
        <div class="clearfix"></div>

        <div class="form-wizard iddetails">
            <div >
                <div class="col-md-12 col-sm-12 no-pad">
                    <div class="data-container">
                        <dl>
                            <dt>Type</dt>
                            <%--:--%>
                            <dd id="idType"></dd>
                        </dl>
                        <dl>
                            <dt>ID Number</dt>
                            <%-- :--%>
                            <dd id="idNumber"></dd>
                        </dl>
                        <dl>
                            <dt>Country</dt>
                            <%-- :--%>
                            <dd id="country"></dd>
                        </dl>
                        <dl>
                            <dt>State</dt>
                            <%-- :--%>
                            <dd id="state"></dd>
                        </dl>
                    </div>
                </div>
            </div>
            <div >
                <div class="float-id-upoad">
                    <button class="button button-control" type="button" id="view_id_but"><span><b>VIEW ID</b></span>
                    </button>
                </div>
            </div>
            <!--/ .row-->
        </div>
        <div >
            <div class="col-xs-12 border-id-dt indetils">
                <div class="form-header">
                    <div class="form-title form-icon title-icon-lock  col-md-2   "><b>Insurance Details</b></div>
                    <div class="col-md-10 col-xs-12 ">
                        <input type="checkbox" class="idcheckbox" value="incheckbox">
                        <span class="checkbox"> Click checkbox to view your Health Insurance Details</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="clearfix"></div>

        <div class="form-wizard insurancedetails">
            <div>
                <div class="col-md-12 col-sm-12 no-pad">
                    <div class="data-container">
                        <dl>
                            <dt>Insurance Provider</dt>
                            <%--:--%>
                            <dd id="provider"></dd>
                        </dl>
                        <dl>
                            <dt>Policy Number</dt>
                            <%--:--%>
                            <dd id="policyNumber"></dd>
                        </dl>
                        <dl>
                            <dt>Validity</dt>
                            <%-- :--%>
                            <dd id="validity"></dd>
                        </dl>
                    </div>
                </div>
            </div>
        </div>

        <div class="prev" style="display: none;">
            <button class="button button-control" type="button"><span>Prev Step <b>Account Information</b></span>
            </button>
            <div class="button-divider"></div>
        </div>
        <div class="next">
            <button class="button button-control" type="button" id="b_contactDetails"><span>Next Step <b>Contact
                Details</b></span></button>
            <div class="button-divider"></div>
        </div>
    </form>
</div>
<div id="contactDetails" class="">

    <form id="userContactInfo" action="/emr/patient/profile" method="post" enctype="application/json">
        <div>
            <div class="col-md-8">
                <div class="form-header">
                    <div class="form-title form-icon title-icon-lock" id="contact-details"><b>Contact Details</b></div>
                </div>
                <!--/ .form-header-->
            </div>
            <div class="col-md-4">
                <div class="form-header-demo">
                    <input type="button" value="Cancel" class="saveb editbasicinfo" id="cancelcontactinfo">
                    <input type="submit" value="Save" class="saveb editbasicinfo-vitals submit_btn" id="savecontactinfo">

                </div>
            </div>
        </div>
        <div class="form-wizard">
            <div class="">
                <div class="">
                    <div class="row">
                        <div class="col-md-6 no-padding">
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label> Country<span style="color:red;">*</span></label>
                                    <div class="dropdown">
                                        <input type="hidden" name="user.userId" id="pciUserId" value="10">
                                        <input type="hidden" name="mobilePhone" id="t_mobilePhone" value="9900187504">
                                        <input type="hidden" name="email" id="t_email" value="arpitha1025@gmail.com">
                                        <select id="t_ucicountry" name="country" class="dropdown-select" onchange="combo('t_ucicountry','t_ucistate','t_ucistateInput');" required="required"><option value="" disabled="" selected="">Select Country</option><option value="4">Canada</option><option value="2">India</option><option value="" disabled="" selected="">Select Country</option><option value="5">United Kingdom</option><option value="3">United States of America</option></select>

                                    </div>
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                            <div class="col-md-6 col-sm-6 no-pad" id="dt_ucistate">
                                <fieldset class="input-block">
                                    <label>State</label>

                                    <div class="dropdown">
                                        <select id="t_ucistate" name="state" class="dropdown-select" title="State Name"><option value="" disabled="" selected="">Select State</option><option value="1">Select State</option><option value="2">Andhra Pradesh</option><option value="3">Arunachal Pradesh</option><option value="4">Assam</option><option value="5">Bihar</option><option value="6">Chhattisgarh</option><option value="7">Goa</option><option value="8">Gujarat</option><option value="9">Haryana</option><option value="10">Himachal Pradesh</option><option value="11">Jammu and Kashmir</option><option value="12">Jharkhand</option><option value="13">Karnataka</option><option value="14">Kerala</option><option value="15">Madhya Pradesh</option><option value="16">Maharashtra</option><option value="17">Manipur</option><option value="18">Meghalaya</option><option value="19">Mizoram</option><option value="20">Nagaland</option><option value="21">Odisha(Orissa)</option><option value="22">Punjab</option><option value="23">Rajasthan</option><option value="24">Sikkim</option><option value="25">Tamil Nadu</option><option value="26">Tripura</option><option value="27">Uttar Pradesh</option><option value="28">Uttarakhand</option><option value="29">West Bengal</option><option value="30">New Delhi</option><option value="31">Telangana</option><option value="32">Chandigarh</option><option value="33">Dadra &amp; Nagar Haveli</option><option value="34">Daman &amp; Diu</option><option value="35">Puducherry (Pondicherry)</option><option value="36">Lakshadweep</option><option value="37">Paschim Banga ( West Bengal)</option></select>
                                    </div>

                                </fieldset>
                            </div>

                            <div class="col-md-6 col-sm-6 no-pad hide" id="dt_ucistateInput">
                                <fieldset class="input-block">
                                    <label>State</label>
                                    <input type="text" name="otherState" id="t_ucistateInput" placeholder="State Name" title="Id Number" pattern="^[a-zA-Z ]+$" maxlength="20">
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                        </div>
                        <div class="col-md-6 no-padding">
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>City<span style="color:red;">*</span></label>
                                    <span class="twitter-typeahead" style="position: relative; display: inline-block; direction: ltr;"><input type="text" id="t_city" name="city" placeholder="City" required="" title="City" pattern="^[a-zA-Z ]+$" maxlength="20" class="tt-input" autocomplete="off" spellcheck="false" dir="auto" style="position: relative; vertical-align: top;"><pre aria-hidden="true" style="position: absolute; visibility: hidden; white-space: pre; font-family: Raleway, sans-serif; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 500; word-spacing: 0px; letter-spacing: 0px; text-indent: 4px; text-rendering: auto; text-transform: none;"></pre><span class="tt-dropdown-menu" style="position: absolute; top: 100%; left: 0px; z-index: 100; display: none; right: auto;"><div class="tt-dataset-name"></div></span></span>
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Location<span style="color:red;">*</span></label>
                                    <input type="text" id="t_location" name="location" placeholder="Location" required="" title="Location" pattern="^[a-zA-Z ]+$" maxlength="20">
                                    <!--/ .dropdown-->
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 no-padding">
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label> Address<span style="color:red;">*</span></label>
                                    <input type="text" id="t_address" name="address" required="" placeholder="Address" title="Address" maxlength="40">
                                    <!--/ .dropdown-->
                                </fieldset>
                            </div>
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Zip Code<span style="color:red;">*</span></label>
                                    <input type="text" id="t_zipCode" name="zipCode" required="" placeholder="Zip Code" title="Zip Code" maxlength="7" pattern="^[\+?\d]{6,7}">
                                    <!--/ .dropdown-->
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>


                        </div>
                        <div class="col-md-6 no-padding">

                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Home phone /Landline</label>
                                    <input type="text" id="t_homePhone" name="homePhone" class="form-icon form-icon-phone" placeholder=" Home Phone/Landline" pattern="^[\+?\d]{10,13}" maxlength="13" title="Home Phone/Landline">
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Occupation</label>
                                    <input type="text" name="occupation" id="t_occupation" class="" placeholder="Occupation" pattern="^[a-zA-Z ]+$" maxlength="12" title="Occupation">
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                        </div>
                    </div>
                    <!--/ .row-->

                    <div class="row">
                        <div class="col-md-6 no-padding">

                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Work Phone</label>
                                    <input type="text" id="t_workPhone" name="workPhone" class="form-icon form-icon-phone" placeholder=" Work Phone" pattern="^[\+?\d]{4,13}" max="13" title="Work Phone">
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>

                        </div>


                    </div>
                </div>
            </div>
            <!--/ .row-->
        </div>
        <!--/ .form-wizard-->
        <div class="prev">
            <button class="button button-control" type="button" id="b_basicDetails"><span>Prev Step <b>Personal
                Details</b></span></button>
            <div class="button-divider"></div>
        </div>
        <div class="next">
            <button class="button button-control" type="button" id="b_emergencyDetails">
                <span>Next Step <b>Emergency Details</b></span></button>
            <div class="button-divider"></div>
        </div>
        <div>
            <input type="hidden" name="_csrf" value="091cdc35-8e90-4b56-afad-3cba7374ba3b">
        </div></form>
</div>
<div id="view_contactDetails" class="hide">
    <form action="/">
        <div>
            <div class="col-md-8">
                <div class="form-header">
                    <div class="form-title form-icon title-icon-lock"><b>Contact Details</b></div>
                </div>
                <!--/ .form-header-->
            </div>
            <div class="col-md-4">
                <div class="form-header-demo">
                    <input type="button" value="Edit" class="saveb editbasicinfo" id="editcontactinfo">
                    <!--<input type="button" value="Save" class="saveb savebasicinfo">-->
                </div>
            </div>
        </div>
        <div class="form-wizard ">
            <div>
                <div class="col-md-12 col-sm-12">
                    <div class="data-container">
                        <dl>
                            <dt>Country</dt>

                            <dd id="ucicountry">India</dd>
                        </dl>
                        <dl>
                            <dt>State</dt>

                            <dd id="ucistate">Karnataka</dd>
                        </dl>
                        <dl>
                            <dt>City</dt>

                            <dd id="ucicity">Bangaluru</dd>
                        </dl>
                        <dl>
                            <dt>Location</dt>

                            <dd id="ucilocation">Rajajinagar</dd>
                        </dl>
                        <dl>
                            <dt>Zip Code</dt>

                            <dd id="ucizipcode">5600035</dd>
                        </dl>
                        <dl>
                            <dt> Address</dt>

                            <dd id="uciaddress">No 256, 3nd cross</dd>
                        </dl>

                        <dl>
                            <dt>Mobile</dt>

                            <dd id="ucimobile">9900187504</dd>
                            <a id="verify_mobile">Edit &amp; Verify</a>
                        </dl>
                        <dl>
                            <dt>Home Phone</dt>

                            <dd id="ucihomephone">-</dd>
                        </dl>
                        <dl>
                            <dt>E-mail</dt>

                            <dd id="uciemail">arpitha1025@gmail.com</dd>
                            <a id="verify_email">Edit &amp; Verify</a>
                        </dl>
                        <dl>
                            <dt>Occupation</dt>

                            <dd id="ucioccupation">-</dd>
                        </dl>
                        <dl>
                            <dt>Work Phone</dt>

                            <dd id="uciworkphone">-</dd>
                        </dl>
                    </div>
                    <!--/ .data-container-->
                </div>
            </div>
            <!--/ .row-->
        </div>
        <!--/ .form-wizard-->
        <!--/ .form-wizard-->
        <div class="prev">
            <button class="button button-control" type="button" id="b_basicDetails"><span>Prev Step <b>Personal
                Details</b></span></button>
            <div class="button-divider"></div>
        </div>
        <div class="next">
            <button class="button button-control" type="button" id="b_emergencyDetails">
                <span>Next Step <b>Emergency Details</b></span></button>
            <div class="button-divider"></div>
        </div>
    </form>
</div>
<div id="emergencyDetails" class="hide">
    <form method="post" name="userEmergencyInfo" id="userEmergencyInfo" enctype='application/json'>
        <div >
            <div class="col-md-8">
                <div class="form-header">
                    <div class="form-title form-icon title-icon-lock"><b>Emergency Contact Details</b></div>
                </div>
                <!--/ .form-header-->
            </div>
            <div class="col-md-4">
                <div class="form-header-demo">
                    <input type="button" value="Cancel" class="saveb editbasicinfo" id="cancelemergencydetails">
                    <input type="submit" value="Save" class="saveb editbasicinfo-vitals submit_btn" id="saveemergencydetails">
                </div>
            </div>
        </div>
        <div class="form-wizard">
            <div class="">
                <div class="">
                    <div class="" id="emergencyone">
                        <div class="col-md-6  no-padding">
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Name<span style="color:red;">*</span></label>
                                    <input type="text" id="t_edname" name="name" placeholder="Name" required
                                           title="Name" pattern="^[A-Za-z ]+$" maxlength="20"/>
                                    <input type="hidden" id="eduserId" name="user.userId">
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Relationship<span style="color:red;">*</span></label>
                                    <input type="text" id="t_edrelation" name="relation" placeholder="Relation" required
                                           title="Relation" pattern="^[A-Za-z ]+$" maxlength="15"/>
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                        </div>
                        <div class="col-md-6  no-padding">
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Mobile Number<span style="color:red;">*</span></label>
                                    <input class="phone" type="tel" name="mobileNumber" required id="t_edmobileNumber" onblur="checkEmergencyMobileExit(this.value,'t_edmobileNumber')"
                                           placeholder="Mobile Number" maxlength="10" pattern="^[\+?\d]{10}"/>
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Home Phone/ Landline</label>
                                    <input type="text" name="homePhone" id="t_edhomePhone"
                                           placeholder="Home Phone/ Landline" title="Home Phone/ Landline"    pattern="^[\+?\d]{10}" maxlength="10" />
                                </fieldset>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--/ .row-->
        </div>
        <!--/ .form-wizard-->

    </form>

    <form method="post" name="userEmergencyInfo" id="userEmergencyInfoOne" enctype='application/json'>
        <div class="row hide" id="uedother">
            <div class="col-md-8">
                <div class="form-header">
                    <%--<div class="form-title form-icon title-icon-lock"><b></b></div>--%>
                </div>
                <!--/ .form-header-->
            </div>
            <div class="col-md-4">
                <div class="form-header-demo">
                    <%--<input type="button" value="Cancel" class="saveb editbasicinfo" id="cancelemergencydetailsone">--%>
                    <input type="submit" value="Save" class="saveb editbasicinfo-vitals submit_btn">
                </div>
            </div>
        </div>
        <div class="form-wizard" id="t_another-emergency">
            <div class="">
                <div class="">
                    <div class="row">
                        <div class="col-md-6  no-padding">
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Name<span style="color:red;">*</span></label>
                                    <input type="text" id="t_edtname" name="name" placeholder="Name" title="Name" required=""
                                           pattern="^[A-Za-z ]+$" maxlength="20"/>
                                    <input type="hidden" id="edtuserId" name="user.userId">
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Relationship<span style="color:red;">*</span></label>
                                    <input type="text" id="t_edtrelation" name="relation" placeholder="Relation" required=""
                                           title="Relation" pattern="^[A-Za-z ]+$" maxlength="15"/>
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                        </div>
                        <div class="col-md-6  no-padding">
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Mobile Number<span style="color:red;">*</span></label>
                                    <input class="phone" type="tel" name="mobileNumber" id="t_edtmobileNumber" onblur="checkEmergencyMobileExit(this.value,'t_edtmobileNumber')" required=""
                                           placeholder="Mobile Number" maxlength="10" pattern="^[\+?\d]{10}"/>
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Home Phone/ Landline</label>
                                    <input type="number" name="homePhone" id="t_edthomePhone"  placeholder="Home Phone/ Landline" title="Home Phone/ Landline" pattern="^[\+?\d]" />

                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--/ .row-->
        </div>
        <!--/ .form-wizard-->
        <div class="prev">
            <button class="button button-control" type="button" id="b_contactDetails"><span>Prev Step <b>Contact
                Details</b></span></button>
            <div class="button-divider"></div>
        </div>
        <div class="next">
            <button class="button button-control" type="button" id="b_preferences">
                <span>Next Step <b>Preferences</b></span></button>
            <div class="button-divider"></div>
        </div>
    </form>
</div>
<div id="view_emergencyDetails" class="hide">
    <form action="/">
        <div >
            <div class="col-md-8">
                <div class="form-header">
                    <div class="form-title form-icon title-icon-lock"><b>Emergency Contact Details</b></div>
                </div>
                <!--/ .form-header-->
            </div>
            <div class="col-md-4">
                <div class="form-header-demo">
                    <input type="button" value="Edit" class="saveb editbasicinfo" id="editemergencydetails">

                </div>
            </div>
        </div>
        <div class="form-wizard">
            <div class="row">
                <div class="col-md-12 col-sm-12">
                    <div class="data-container">
                        <dl>
                            <dt>Name</dt>
                            <%--:--%>
                            <dd id="edname"></dd>
                        </dl>
                        <dl>
                            <dt>Relationship</dt>
                            <%--  :--%>
                            <dd id="edrelation"></dd>
                        </dl>

                        <dl>
                            <dt>Mobile Number</dt>
                            <%--:--%>
                            <dd id="edmobileNumber"></dd>
                        </dl>
                        <dl>
                            <dt>Home Phone/Landline</dt>
                            <%--:--%>
                            <dd id="edhomePhone"></dd>
                        </dl>

                    </div>
                    <!--/ .data-container-->
                </div>
            </div>
            <!--/ .row-->
        </div>
        <!--/ .form-wizard-->
        <!--/ .form-wizard-->

        <!--/ form-->
        <div>
            <div class="col-xs-12">
                <div class="form-header">
                    <div class=" ">
                        <!--   <input type="checkbox" class="checkbox">  <label class="checkbox">To Add another Emergency Contact Details Click on check box</label>
          -->
                        <input type="checkbox" class="another-emergency-chck" value="another-emergency-chck">
                        <span class="checkbox"> Click checkbox to view additional Emergency Contact Details</span>
                    </div>
                </div>
                <!--/ .form-header-->
            </div>
        </div>

        <div class="form-wizard  another-emergency">
            <div >
                <div class="col-md-12 col-sm-12">
                    <div class="data-container">
                        <dl>
                            <dt>Name</dt>
                            <%-- :--%>
                            <dd id="edtname"></dd>
                        </dl>
                        <dl>
                            <dt>Relationship</dt>
                            <%--:--%>
                            <dd id="edtrelation"></dd>
                        </dl>

                        <dl>
                            <dt>Mobile Number</dt>
                            <%--  :--%>
                            <dd id="edtmobileNumber"></dd>
                        </dl>
                        <dl>
                            <dt>Home Phone/Landline</dt>
                            <%-- :--%>
                            <dd id="edthomePhone"></dd>
                        </dl>

                    </div>
                    <!--/ .data-container-->
                </div>
            </div>
            <!--/ .row-->
        </div>
        <!--/ .form-wizard-->
        <!--/ .form-wizard-->
        <div class="prev">
            <button class="button button-control" type="button" id="b_contactDetails"><span>Prev Step <b>Contact
                Details</b></span></button>
            <div class="button-divider"></div>
        </div>
        <div class="next">
            <button class="button button-control" type="button" id="b_preferences">
                <span>Next Step <b>Preferences</b></span></button>
            <div class="button-divider"></div>
        </div>
    </form>
</div>
<div id="preferences" class="hide">
<form method="post" name="userPreferences" id="userpreferences">

<div id="edpreferences" class="hide">
    <div>
        <div class="col-md-8">
            <div class="form-header">
                <div class="form-title form-icon title-icon-lock"><b>Edit Preferred Doctors</b></div>
            </div>
            <!--/ .form-header-->
        </div>
        <div class="col-md-4">

            <div class="form-header-demo">
                <input type="button" value="Cancel" class="saveb editbasicinfo" id="canceledpreferences">
                <input type="submit" value="Save" class="saveb editbasicinfo-vitals submit_btn" id="savepreferences">
            </div>

        </div>

    </div>

    <div class="form-wizard">
        <div class="row">
            <div class="">

                <div id="dynamiceditdiv">

                </div>


            </div>
        </div>
        <!--/ .row-->

    </div>
</div>
<!--/ .form-wizard-->

<%--            <div class="row">

                <div class="col-xs-12">

                    <div class="form-header">

                        <div class="">


                            <input type="checkbox" class="pharmacy-dtls-chck" value="t_pharmacy-dtls-chck"><span class="checkbox">Click checkbox to add Preferred Doctors</span>


                        </div>


                    </div>
                    <!--/ .form-header-->

                </div>

            </div>--%>
<div id="adpreference" class="hide" >

    <div class="row">
        <div class="col-md-8">
            <div class="form-header">
                <div class="form-title form-icon title-icon-lock"><b>Add Preferred Doctors</b></div>
            </div>
            <!--/ .form-header-->
        </div>
        <div class="col-md-4">
            <div class="form-header-demo">
                <input type="button" value="Cancel" class="saveb editbasicinfo" id="canceladpreferences">
                <input type="submit" value="Save" class="saveb editbasicinfo-vitals submit_btn" id="savepreferences">
            </div>

        </div>
    </div>
    <div class="form-wizard">
        <div class="row">
            <div class="col-md-12" style="display: yes">
                <div class="col-md-6 col-sm-6 no-pad ">
                    <fieldset class="input-block">
                        <div class="dropdown">
                            <select class="select-specality dropdown-select" id="specialitylist" name="specialityId"
                                    onchange="return displaydoct();">
                            </select>
                        </div>
                    </fieldset>
                </div>
            </div>
    <div class="clearfix"></div>

            <div class="col-md-12" id="selectedspec" style="display:none;">

                <div class="row">

                    <div class="col-md-9 ">
                        <h2 id="header" class="mydoctors "><span class="generalicon generalphy"></span></h2>
                    </div>

                    <div class="col-md-6  no-padding">
                        <div class="col-md-6 col-sm-6 no-pad ">

                            <fieldset class="input-block">
                                <label>Clinic/ Doctor Name</label>


                                <input type="text" id="t_doctor" name="name" placeholder=" Clinic/ Doctor Name"
                                       title="Clinic/ Doctor Name" maxlength="30"/>
                                <input type="hidden" name="user.userId" class="preferencs_userId">
                                <input type="hidden" name="specialityId" id="specialityId1" value="">
                                <!--/ .tooltip-->
                            </fieldset>
                        </div>

                        <div class="col-md-6 col-sm-6  no-pad ">

                            <fieldset class="input-block">
                                <label> Address</label>
                                <input type="text" name="address" placeholder="Address" title="address" id="t_address"/>
                                <!--/ .tooltip-->
                            </fieldset>
                        </div>
                    </div>

                    <div class="col-md-6  no-padding">

                        <div class="col-md-6 col-sm-6 no-pad">
                            <fieldset class="input-block">
                                <label>Contact Number</label>

                                <input class="phone" type="text" id="t_dnumber" name="contactNumber"
                                       placeholder="Contact Number"
                                       maxlength="13" pattern="^[\+?\d]{10,13}"/>
                                <!--/ .dropdown-->
                                <!--/ .tooltip-->
                            </fieldset>
                        </div>
                    </div>
                </div>
            </div>

        </div>


    </div>
    <!--/ .row-->

</div>


<%--            <div class="row">

                <div class="col-xs-12">

        <div class="form-header">

            <div class="">


                <input type="checkbox" class="pharmacy-dtls-chck" value="t_pharmacy-dtls-chck"><span class="checkbox">Click checkbox to add Pharmacy details</span>


            </div>


        </div>
        <!--/ .form-header-->

    </div>

</div>
<div class="form-wizard" id="t_pharmacy-dtls">

    <div class="">

        <div class="">
            <div >
                <div class="col-md-9">
                    <h2 class="mydoctors ">Preferred Pharmacy<span class="generalicon generalphy"></span></h2>
                </div>
                <div class="col-md-6  no-padding">
                    <div class="col-md-6 col-sm-6 no-pad ">

                        <fieldset class="input-block">
                            <label>Pharmacy Name</label>


                            <input type="text" id="t_doctor13" name="name" placeholder=" Pharmacy Name"
                                   title="Pharmacy Name" maxlength="30"/>
                            <input type="hidden" name="type" value="true">
                            <input type="hidden" name="user.userId" class="preferencs_userId">

                            <!--/ .tooltip-->
                        </fieldset>
                    </div>

                    <div class="col-md-6 col-sm-6  no-pad ">

                        <fieldset class="input-block">
                            <label>Address</label>
                            <input type="text" name="address" placeholder="Address" title="Full address"
                                   id="t_address13"/>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>

                <div class="col-md-6  no-padding">

                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>Contact Number</label>

                            <input class="phone" type="text" id="t_dnumber13" name="contactNumber"
                                   placeholder="Contact Number" maxlength="10" pattern="^[\+?\d]{10}"/>
                            <!--/ .dropdown-->
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>
            </div>


            <!--/ .row-->


        </div>


    </div>
    <!--/ .row-->

            </div>--%>
<!--/ .form-wizard-->
<div class="prev">
    <button class="button button-control" type="button" id="b_emergencyDetails"><span>Prev Step <b> Emergency
        Details</b></span></button>
    <div class="button-divider"></div>
</div>
</form>


</div>
<div id="view_preferences" class="hide">
    <form action="/">
<div >
            <div class="col-md-8">
                <div class="form-header">
                    <div class="form-title form-icon title-icon-lock"><b>My Doctor</b></div>
                </div>
                <!--/ .form-header-->
            </div>
            <div class="col-md-4">
                <div class="form-header-demo">
                    <input type="button" value="Edit" class="saveb editbasicinfo" id="editpreferences">
                    <input type="button" value="Add" class="saveb editbasicinfo-vitals submit_btn" id="addpreferences">

                </div>
            </div>
        </div>
        <div class="form-wizard">
            <div class="row">
                <div id="dynamicdiv">

                </div>
            </div>

            <!--/ .row-->
        </div>
        <!--/ .form-wizard-->
        <!--/ .form-wizard-->
<div >
            <div class="col-xs-12">
                <div class="form-header">
                    <div class=""><b> </b>
                        <input type="checkbox" class="pharmacy-dtls-chck" value="pharmacy-dtls-chck">
                        <span class="checkbox"> Click checkbox to view Pharmacy details</span></div>
                </div>
                <!--/ .form-header-->
            </div>
        </div>
        <div class="form-wizard pharmacy-dtls">
            <div class="row">
                <div class="col-md-8 col-sm-7">
                    <div class="data-container">
                        <dl>
                            <dt> Pharmacy Name</dt>
                    <%--:--%>
                    <dd id="doctor13"></dd>
                </dl>
                <dl>
                    <dt>Address</dt>
                    <%--:--%>
                    <dd id="address13"></dd>
                </dl>
                <dl>
                    <dt>Mobile Number</dt>
                    <%-- :--%>
                    <dd id="dnumber13"></dd>
                </dl>

                    </div>
                    <!--/ .data-container-->
                </div>
            </div>
            <!--/ .row-->
        </div>
        <!--/ .form-wizard-->
        <!--/ .form-wizard-->
        <div class="prev">
            <button class="button button-control" type="button" id="b_emergencyDetails"><span>Prev Step <b> Emergency
                Details</b></span></button>
            <div class="button-divider"></div>
        </div>

    </form>
</div>
<!--/ .form-wizard-->

</div>
<!--/ .container-->
</div>
</div>

<script src="resources/jsplugins/jquery-ui.js"></script>
<script src="resources/jsplugins/jquery.datetimepicker.min.js"></script>
<script src="resources/js/server/patientDemographics.js" type="text/javascript"></script>
<script>
    $(document).ready(function () {
        $("#t_dateofBirth").keyup(function () {
            if ($(this).val().length == 2 || $(this).val().length == 5) {
                $(this).val($(this).val() + "/");
            }
        });

        /* $("#t_idType").on('change',function(){
         var idType = $(this).val();
         alert(idType);
         });*/
    });


    function displaydoct() {
        var e = document.getElementById("specialitylist");
        var specialityid = e.options[e.selectedIndex].value;
        var speciality = e.options[e.selectedIndex].text;
        var element = document.getElementById("header");
        element.innerHTML = speciality;
        document.getElementById('selectedspec').style.display = 'block';
        document.getElementById('specialityId1').value = specialityid;
    }

</script>
