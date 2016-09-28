<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="resources/jsplugins/jquery-ui.js"></script>
<script type="text/javascript" src="resources/js/server/doctorProfile.js"></script>

<div class="wrapper">
<div class="form-container">
<div id="tmm-form-wizard" class="container substrate register-margin">
<div class="row">
    <div class="col-xs-12">
        <h2 class="form-login-heading">Demographics </h2>
    </div>
</div>
<!--/ .row-->
<div class=" stage-container">
    <div class="stage tmm-success col-md-3 col-sm-3 " id="a_basicDetails"><a>
        <div class="stage-header head-icon head-icon-lock"></div>
        <div class="stage-content">
            <h3 class="stage-title">Basic Details</h3>
        </div>
    </a></div>
    <!--/ .stage-->
    <div class="stage col-md-3 col-sm-3 " id="a_contactDetails"><a>
        <div class="stage-header head-icon head-icon-user"></div>
        <div class="stage-content">
            <h3 class="stage-title">Contact Details</h3>
        </div>
    </a></div>

    <div class="stage col-md-3 col-sm-3 " id="a_professionaldetails"><a>
        <div class="stage-header head-icon head-icon-details"></div>
        <div class="stage-content">
            <h3 class="stage-title">Professional</h3>
        </div>
    </a></div>
    <div class="clearfix"></div>
    <!--/ .stage-->
</div>
<div id="basicDetails" class="hide">
    <form:form method="post" commandName="user" id="basicinformation" enctype='application/json'>
        <!--/ .row-->
        <div >
            <div class="col-md-8">
                <div class="form-header">
                    <div class="form-title form-icon title-icon-lock"><b>Basic Details</b></div>
                </div>
                <!--/ .form-header-->
            </div>
            <div class="col-md-4">
                <div class="form-header-demo">
                    <input type="button" value="Cancel" class="saveb editbasicinfo-vitals cancelbtns" id="cancelbasicinfo">
                    <input type="submit" value="Save" class="saveb editbasicinfo-vitals" id="savebasicinfo">
                    <!--<input type="button"  value="Save" class="saveb savebasicinfo">-->
                </div>
            </div>
            <div class="clearfix"></div>
        </div>
        <!--/ .row-->

        <div class="form-wizard">
            <div class="">
                <div class="row">
                    <div class="col-md-6 no-padding">
                        <div class="col-md-6 col-sm-6 no-pad ">
                            <fieldset class="input-block">
                                <label>First name<span style="color:red;">*</span></label>
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
                                <input type="text" id="t_lastName" name="userDetails.lastName" placeholder="Last Name"
                                       required
                                       title="Last Name" pattern="^[a-zA-Z ]+$" maxlength="15"/>
                                <!--/ .tooltip-->
                            </fieldset>
                        </div>
                    </div>
                    <div class="col-md-6  no-padding">
                        <div class="col-md-6 col-sm-6 no-pad ">
                            <div class="col-md-6 col-sm-6 no-pad ">

                                <fieldset class="input-block">
                                    <label>Date of Birth </label>
                                    <input type="text" placeholder="Date of Birth" title="Date of Birth"
                                           id="t_dateofBirth" name="userDetails.dateofBirth" maxlength="10" pattern="^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$"
                                           onblur="checkDateofBirth(this.value);this.checkValidity();"/>
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                            <%--<div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Gender</label>

                                    <div class="dropdown">
                                        <select name="userDetails.gender" class="dropdown-select" id="t_gender">
                                            <option disabled="" value="" selected="">Select</option>
                                            <option value="Male">Male</option>
                                            <option value="Female">Female</option>
                                            <option value="Indeterminate">Indeterminate</option>
                                            <option value="Not Identified">Not Identified</option>
                                        </select>
                                    </div>
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>--%>

                        </div>
                        <%--<div class="col-md-6 col-sm-6 no-pad ">
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Blood Group</label>

                                    <div class="dropdown">
                                        <form:select path="userDetails.bloodGroup" id="t_bloodGroup" name="thelist"
                                                     title="Blood Group"
                                                     cssClass="dropdown-select"></form:select>
                                    </div>
                                    <!--/ .dropdown-->
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Marital Status</label>

                                    <div class="dropdown">
                                        <select name="userDetails.maritalStatus" class="dropdown-select"
                                                id="t_maritalStatus">
                                            <option value="" disabled="" selected="">Select</option>
                                            <option value="single">Unmarried</option>
                                            <option value="married">Married</option>
                                        </select>
                                    </div>
                                    <!--/ .dropdown-->
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                        </div>--%>

                    </div>
                </div>
                <div class="row">
                   <%-- <div class="col-md-6  no-padding">
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
                                    <input type="text" id="t_religion" name="userDetails.religion"
                                           placeholder="Religion"
                                           title="Religion" pattern="^[a-zA-Z ]+$" maxlength="15"/>
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                        </div>
                    </div>--%>
                   <%-- <div class="col-md-6  no-padding">


                    </div>--%>
                </div>
                <div class="row">
                   <%-- <div class="col-md-6  no-padding">

                    </div>--%>
                </div>
            </div>
        </div>
        <!--/ .row-->
        <!--/ .form-wizard-->
        <div class="prev" style="display: none;">
            <button class="button button-control" type="button"><span>Prev Step <b>Basic Details</b></span>
            </button>
            <div class="button-divider"></div>
        </div>
        <div class="next">
            <button class="button button-control" type="button" id="b_contactDetails"><span>Next Step <b>Contact Details</b></span></button>
            <div class="button-divider"></div>
        </div>
    </form:form>
</div>
<div id="view_basicDetails">
    <form action="/">
        <div >
            <div class="col-md-8">
                <div class="form-header">
                    <div class="form-title form-icon title-icon-lock"><b>Basic Details</b></div>
                </div>
                <!--/ .form-header-->
            </div>
            <div class="col-md-4">
                <div class="form-header-demo">
                    <input type="button" value="Edit" class="saveb editbasicinfo" id="editbasicinfo">
                    <!--<input type="button"  value="Save" class="saveb savebasicinfo">-->
                </div>
            </div>
            <div class="clearfix"></div>
        </div>
        <div >
            <div >
                <div class="col-md-12 col-sm-12">
                    <div class="data-container">
                        <dl>
                            <dt>First name</dt>
                            :
                            <dd id="firstName"></dd>
                        </dl>

                        <dl>
                            <dt>Last Name</dt>
                            :
                            <dd id="lastName"></dd>
                        </dl>
                        <dl>
                            <dt>Datalife ID</dt>
                            :
                            <dd id="datalifeId"></dd>
                        </dl>
                        <%--<dl>
                            <dt>Language</dt>
                            :
                            <dd id="localLanguage"></dd>
                        </dl>--%>
                        <dl>
                            <dt>Date of Birth</dt>
                            :
                            <dd id="dateofBirth"></dd>
                        </dl>
                       <%-- <dl>
                            <dt>Gender</dt>
                            :
                            <dd id="gender"></dd>
                        </dl>
                        <dl>
                            <dt>Blood Group</dt>
                            :
                            <dd id="bloodGroup"></dd>
                        </dl>
                        <dl>
                            <dt>Marital Status</dt>
                            :
                            <dd id="maritalStatus"> Unmarried</dd>
                        </dl>
                        <dl>
                            <dt>Religion</dt>
                            :
                            <dd id="religion"> Universal</dd>
                        </dl>--%>
                    </div>
                    <!--/ .data-container-->
                </div>
            </div>
            <!--/ .row-->
        </div>

        <div class="prev" style="display: none;">
            <button class="button button-control" type="button"><span>Prev Step <b>Basic Details</b></span>
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
<div id="contactDetails" class="hide">

    <form:form method="post" commandName="userContactInfo" id="userContactInfo" enctype='application/json'>
        <div >
            <div class="col-md-8">
                <div class="form-header">
                    <div class="form-title form-icon title-icon-lock" id="contact-details"><b>Contact Details</b></div>
                </div>
                <!--/ .form-header-->
            </div>
            <div class="col-md-4">
                <div class="form-header-demo">
                    <input type="button" value="Cancel" class="saveb editbasicinfo-vitals cancelbtns" id="cancelcontactinfo">

                    <input type="submit" value="Save" class="saveb editbasicinfo-vitals" id="savecontactinfo">
                    <!--<input type="button" value="Save" class="saveb savebasicinfo">-->
                </div>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="form-wizard">
            <div class="">
                <div class="">
                    <div class="row">
                        <div class="col-md-6 no-padding">
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label> Country</label>

                                    <div class="dropdown">
                                        <input type="hidden" name="user.userId" id="pciUserId" value=""/>
                                        <input type="hidden" name="mobilePhone" id="t_mobilePhone" value=""/>
                                        <input type="hidden" name="email" id="t_email" value=""/>
                                        <form:select id="t_ucicountry" path="country" name="thelist"
                                                     onChange="combo('t_ucicountry','t_ucistate','t_ucistateInput');"
                                                     cssClass="dropdown-select"></form:select>

                                    </div>
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                            <div class="col-md-6 col-sm-6 no-pad hide" id="dt_ucistate">
                                <fieldset class="input-block">
                                    <label>State</label>

                                    <div class="dropdown">
                                        <form:select path="state" id="t_ucistate" title="State Name"
                                                     cssClass="dropdown-select" onchange=""></form:select>

                                    </div>
                                    <!--/ .dropdown-->
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>

                            <div class="col-md-6 col-sm-6 no-pad" id="dt_ucistateInput">
                                <fieldset class="input-block">
                                    <label>State</label>
                                    <input type="text" name="otherState" id="t_ucistateInput"
                                           placeholder="State Name" title="Id Number" pattern="^[a-zA-Z ]+$" maxlength="15"/>
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="col-md-6 no-padding">
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>City<span style="color:red;">*</span></label>
                                    <input type="text" id="t_city" name="city" placeholder="City" required pattern="^[a-zA-Z ]+$" maxlength="15"
                                           title="City"/>
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Location<span style="color:red;">*</span></label>
                                    <input type="text" id="t_location" name="location" placeholder="Location" required pattern="^[a-zA-Z ]+$" maxlength="15"
                                           title="Location"/>
                                    <!--/ .dropdown-->
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                    <!--/ .row-->
                    <!--/ .row-->
                    <div class="row">
                        <div class="col-md-6 no-padding">
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label> House No<span style="color:red;">*</span></label>
                                    <input type="text" id="t_address" name="address" placeholder=" House No" required  maxlength="50"
                                           title=" House No"/>
                                    <!--/ .dropdown-->
                                </fieldset>
                            </div>
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Zip Code<span style="color:red;">*</span></label>
                                    <input type="text" id="t_zipCode" name="zipCode" placeholder="Zip Code" required maxlength="7" pattern="^[\+?\d]{6,7}"
                                           title="Zip Code"/>
                                    <!--/ .dropdown-->
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>


                        </div>
                        <div class="col-md-6 no-padding">

                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Home phone /Landline</label>
                                    <input type="text" id="t_homePhone" name="homePhone"
                                           class="form-icon form-icon-phone" placeholder=" Home Phone/Landline"
                                           title="Home Phone/Landline" maxlength="13" pattern="^[\+?\d]{10,13}"/>
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>Work Phone</label>
                                    <input type="text" id="t_workPhone" name="workPhone"
                                           class="form-icon form-icon-phone" placeholder=" Work Phone"
                                           title="Work Phone" maxlength="13" pattern="^[\+?\d]{10,13}"/>
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                                <%--<div class="col-md-6 col-sm-6 no-pad ">
                                    <fieldset class="input-block">
                                        <label>Occupation</label>
                                        <input type="text" name="occupation" id="t_occupation" class=""
                                               placeholder="Occupation"
                                               title="Occupation" maxlength="20" pattern="^[a-zA-Z ]+$"/>
                                        <!--/ .tooltip-->
                                    </fieldset>
                                </div>--%>

                        </div>
                    </div>
                    <!--/ .row-->
                        <%--
                                            <div class="row">
                                                <div class="col-md-6 no-padding">


                                                </div>
                                            </div>--%>

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
            <button class="button button-control" type="button" id="b_professionaldetails">
                <span>Next Step <b>Professional Details</b></span></button>
            <div class="button-divider"></div>
        </div>
    </form:form>
</div>
<div id="view_contactDetails" class="hide">
    <form action="/">
        <div >
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
            <div class="clearfix"></div>
        </div>
        <div >
            <div >
                <div class="col-md-12 col-sm-12">
                    <div class="data-container">
                        <dl>
                            <dt>Country</dt>
                            :
                            <dd id="ucicountry"></dd>
                        </dl>
                        <dl>
                            <dt>State</dt>
                            :
                            <dd id="ucistate"></dd>
                        </dl>
                        <dl>
                            <dt>City</dt>
                            :
                            <dd id="ucicity"></dd>
                        </dl>
                        <dl>
                            <dt>Location</dt>
                            :
                            <dd id="ucilocation"></dd>
                        </dl>
                        <dl>
                            <dt>Zip Code</dt>
                            :
                            <dd id="ucizipcode"></dd>
                        </dl>
                        <dl>
                            <dt>Address</dt>
                            :
                            <dd id="uciaddress"></dd>
                        </dl>

                        <dl>
                            <dt>Mobile</dt>
                            :
                            <dd id="ucimobile"></dd><a id="verify_mobile">Edit & Verify</a>
                        </dl>
                        <dl>
                            <dt>Phone</dt>
                            :
                            <dd id="ucihomephone"></dd>
                        </dl>
                        <dl>
                            <dt>Email</dt>
                            :
                            <dd id="uciemail"></dd><a id="verify_email">Edit & Verify</a>
                        </dl>
                        <%--<dl>
                            <dt>Occupation</dt>
                            :
                            <dd id="ucioccupation"></dd>
                        </dl>--%>
                        <dl>
                            <dt>Work Phone</dt>
                            :
                            <dd id="uciworkphone"></dd>
                        </dl>
                    </div>
                    <!--/ .data-container-->
                </div>
            </div>
            <div class="clearfix"></div>
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
            <button class="button button-control" type="button" id="b_professionaldetails">
                <span>Next Step <b>Professional Details</b></span></button>
            <div class="button-divider"></div>
        </div>
    </form>
</div>
<div id="professionaldetails" class="hide">
    <form method="post" name="doctorInfo" id="doctorInfo" enctype='application/json'>
        <div >
            <div class="col-md-8">
                <div class="form-header">
                    <div class="form-title form-icon title-icon-lock"><b>Professional Details</b></div>
                </div>
                <!--/ .form-header-->
            </div>
            <div class="col-md-4">
                <div class="form-header-demo">
                    <input type="button" value="Cancel" class="saveb editbasicinfo-vitals cancelbtns" id="cancelprofessionaldetails">

                    <input type="submit" value="Save" class="saveb editbasicinfo-vitals" id="saveprofessionaldetails">

                </div>
            </div>
        </div>
        <div class="form-wizard">
            <div class="">
                <div class="">
                    <div>

                        <div class="col-md-6 no-padding">
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label>
                                        Medical Licence Number<span style="color:red;">*</span></label>
                                    <input type="hidden" id="c_userId" name="user.userId"/>
                                    <input type="text" readonly name="mlrNumber" id="t_medicalLicenceNumber" title="Medical Licence Number" required="" placeholder="Medical Licence Number">
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>
                            <div class="col-md-6 col-sm-6 no-pad ">
                                <fieldset class="input-block">
                                    <label> Qualification<span style="color:red;">*</span></label>
                                    <input type="text" name="qualification"  id="t_qualification" title="Qualification" required="" placeholder="Qualification" maxlength="50">

                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>

                        </div>

                        <div class="col-md-12 no-padding">
                            <div class="col-md-6 col-sm-12 no-pad ">
                                <fieldset class="input-block">
                                    <label>
                                        Affiliations</label>
                                    <textarea name="affiliations" class="nclo intarea hst" placeholder="Affiliations" id="t_affiliations"
                                              title="Affiliations"
                                              style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px" ></textarea>
                                    <!--/ .tooltip-->
                                </fieldset>
                            </div>


                        </div>
                        <div class="clearfix"></div>
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

    </form>
</div>
<div id="view_professionaldetails" class="hide">
    <div >
        <div class="col-md-8">
            <div class="form-header">
                <div class="form-title form-icon title-icon-lock"><b>Professional Details</b></div>
            </div>
            <!--/ .form-header-->
        </div>
        <div class="col-md-4">
            <div class="form-header-demo">
                <input type="button" value="Edit" class="saveb editbasicinfo" id="editprofessionaldetails">

            </div>
        </div>
    </div>
    <div >
        <div >

            <div class="col-md-12 col-sm-12">
                <div class="data-container">
                    <dl>
                        <dt>Medical Licence Number</dt>
                        :
                        <dd id="medicalLicenceNumber"></dd>
                    </dl>
                    <dl>
                        <dt>Qualification</dt>
                        :
                        <dd id="qualification"></dd>
                    </dl>
                    <dl>
                        <dt>Affiliations</dt>
                        :
                        <dd id="affiliations"></dd>
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


</div>

<!--/ .form-wizard-->

</div>
<!--/ .container-->
</div>
</div>


<script>
    $(document).ready(function() {
        $("#t_dateofBirth").keyup(function(){
            if ($(this).val().length == 2 || $(this).val().length == 5){
                $(this).val($(this).val() + "/");
            }
        });
    });
</script>