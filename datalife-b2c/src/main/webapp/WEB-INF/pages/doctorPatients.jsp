<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<%--<link rel="stylesheet" href="resources/css/jquery-ui.min.css">
<script src="resources/jsplugins/jquery-ui.js"></script>--%>
<script src="resources/jsplugins/jquery.datetimepicker.min.js"></script>
<script type="text/javascript" src="resources/jsplugins/site.js"></script>
<script type="text/javascript" src="resources/jsplugins/dataTables.responsive.min.js"></script>

<div class="wrapper">
<div class="form-container">
<div id="tmm-form-wizard" class="container substrate register-margin">
<div class="clr form-login-heading">
    <div class="col-md-6 col-sm-6  no-pad">
        <h2 style="border: none; margin:0px; padding:0; border-radius:0px; background: none" class="form-login-heading heding-list-for-db">
            Patients</h2>
    </div>
</div>

    <div class="">
        <div class="confirmpadding">
            <div class="note"></div>
            <div class="form-wrapper">
                <div class="row splitcode hide">
                    <ul class="todayappointmentul">
                    <li class="searchalign">
                        <div id="search_patient"><i class="icon-search-1"></i> Patient Search
                        </div>
                    </li>
                    <li class="searchalign">
                        <div id="add_patient"><i class="icon-plus-circled"></i>Register  New Patient</div>
                    </li>
                    <li class="searchalign">
                        <div id="patient_list"><i class="icon-search-1"></i>Clinic Patients</div>
                    </li>
                    </ul>
                </div>
                <div id="ppconfirmMessage" class="hide"></div>
                <div id="pperrorMessage" class="hide"></div>

                <div id="search_patient_show">
                    <div id="add_patient_view">

        <div id="disc-class">
            <form method="post" id="clinicPatient_search">
                <div class="col-md-8  col-sm-12 no-pad ">
                    <fieldset class="input-block">

                        <input type="text" class="form-icon form-icon-user demo" placeholder="Search by DataLife Id, Mobile Number"
                               required="" title="Search" id="searchPatientInput">

                    </fieldset>
                </div>
                <div class="col-md-4 col-sm-12 no-pad serach">
                    <input type="submit" class="saveb editbasicinfodemo" value="Search">
                </div>
            </form>
            <div class="clearfix"></div>
        </div>
        <div class="background-for-list-space classwrap" id="cliniPatientsDiv">
            <div class=" ">

                <table id="clinicPatientsTable" class="display responsive nowrap no-footer dtr-inline dataTable" cellpadding="0" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th style="text-align:center;">DateLife ID</th>
                        <th style="text-align:center;"> Name</th>
                        <th style="text-align:center;">Mobile Number</th>
                        <th style="text-align:center;">Add to Clinic</th>
                        <th style="text-align:center;">New Consultation</th>

                    </tr>
                    </thead>
                    <tbody id="clinicPatientsTbody">

                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div id="pauthenticate_view" class="hide">
        <div class="row">

            <form method="post" id="pauthenticate_form">
                <div class="col-md-3 no-pad ">
                    <fieldset class="input-block">
                        <input type="hidden" id="otpclinicId" name="clinicId"/>
                        <input type="hidden" id="otpdoctorId" name="doctorId"/>
                        <input type="hidden" id="otppatientId" name="patientId"/>
                        <input type="password" class="form-icon form-icon-user demo" placeholder="Enter OTP"
                               name="authentication.otp" required="" title="Search" id="otp">
                    </fieldset>
                </div>
                <div class="col-md-4 no-pad serach">
                    <button type="submit" class="saveb editbasicinfo-vitals">Authenticate</button>
                    <button type="button" id="pauthencate_back" class="saveb editbasicinfo cancelbtns">Cancel</button>
                </div>
            </form>
        </div>
        <div class="note">Note : Authenticate using OTP sent to patient</div>

    </div>
</div>

        <div class="hidden-status" id="add_patient_show">
            <form method="post" name="user" id="clinicPatientForm">
                <input type="hidden" name="role" id="userRole" value="ROLE_PATIENT">
                <input type="hidden" name="activation" value="noactivation">
                <input type="hidden" id="clinicIdInput" name="clinicId" value="">
                <input type="hidden" id="doctorIdInput" name="doctorId" value="">

                <div class="form-wizard">

                    <div>

                        <div class="col-md-6 col-sm-12 no-pad ">
                            <fieldset class="input-block">
                                <label class="select-records"> First Name<span style="color:red;">*</span></label>
                                <input type="text" placeholder="First Name" required="" name="userDetails.firstName"
                                       pattern="^[a-zA-Z ]+$" title=" First Name">

                            </fieldset>
                        </div>
                        <div class="col-md-6 col-sm-12 no-pad">

                            <fieldset class="input-block">
                                <label class="select-records">Last Name<span style="color:red;">*</span></label>
                                <input type="text" placeholder=" Last Name" required="" name="userDetails.lastName"
                                       pattern="^[a-zA-Z ]+$" title=" Last Name " class="">
                            </fieldset>
                        </div>

                        <div class="col-md-6 col-sm-12 no-pad">

                            <fieldset class="input-block">
                                <label class="select-records">User Name<span style="color:red;">*</span></label>
                                <input type="text" placeholder=" User Name" required="" id="userName"
                                       pattern="^[a-zA-Z0-9]+$" name="userName" title=" User Name "
                                       onblur="checkUserExistsAllReady(this.value);">

                            </fieldset>
                        </div>

                        <div class="col-md-6 col-sm-12 no-pad ">
                            <fieldset class="input-block">
                                <label class="select-records"> Mobile Number<span style="color:red;">*</span></label>
                                <input id="phone" type="tel" name="userContactInfo.mobilePhone"
                                       placeholder="Mobile Number" required=""
                                       maxlength="13" pattern="^[\+?\d]{10,13}"/>

                            </fieldset>
                        </div>

                        <div class="col-md-12 col-sm-12 no-pad ">
                            <fieldset class="input-block">
                                <label class="select-records"> Email<span style="color:red;">*</span></label>
                                <input type="email" placeholder="Email" required="" title="Email " id="email"
                                       class="" name="userContactInfo.email"/>

                            </fieldset>
                        </div>


                    </div>
                </div>
                <div >
                    <div class="col-md-12">
                        <div class="btn-static">
                            <button type="submit" id="submit_clinicDoctors" class="register-btn">Submit</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>

<div id="patient_list_show" class="hidden-status ">
    <div id="patient_list_view">
        <div class="background-for-list-space classwrap">

            <table id="clinicPatients" class="display" cellpadding="0" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th style="text-align:center;"> DateLife ID</th>
                    <th style="text-align:center;"> Name</th>

                    <th style="text-align:center;">Mobile Number</th>
                    <th style="text-align:center;">Action</th>
                </tr>
                </thead>
                <tbody id="clinicPatientsList">

                </tbody>
            </table>

        </div>
    </div>
    <div id="assign_view" class="hide">
        <div class="row">
            <form method="post" id="passign_form">
                <div class="col-md-3 no-pad ">
                    <fieldset class="input-block">
                        <input type="hidden" id="assignclinicId" name="clinicId"/>
                        <input type="hidden" id="assignpatientId" name="patientId"/>

                        <div class="dropdown">
                            <select class="dropdown-select" id="assignDoctorId" name="doctorId" title="Select Doctor">
                            </select>
                        </div>
                    </fieldset>
                </div>
                <div class="col-md-3 no-pad serach">
                    <button type="submit" class="View">Assign</button>
                    <button type="button" id="passign_back">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</div>

</div>
<div class="clear"></div>
</div>
</div>
</div>

</div>
</div>
<div id="access_encounter">
    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-smpopup">
        <div class="cancel-common"></div>
        <div class="confirmpadding">
            <div id="bodypsv">
                <div class="confirmappointemnt">Patient Authentication</div>

                <form name="user" method="post" id="get_encounter_form">
                    <input type="hidden" id="dp_patientId" name="userId">
                    <input type="hidden" id="dp_doctorId">
                    <input type="hidden" id="dp_clinicId">
                    <div id="perrorMessage"></div>
                    <div class="shareemailpasswordholder">
                        <div class="shareemail">
                            <div id="tmm-form-wizard" class="no-pad">
                                <fieldset class="input-block no-pad">
                                    <label>Enter OTP<span style="color:red;">*</span></label>

                                    <input type="password" id="dp_otp" name="authentication.otp" required
                                           placeholder="Enter OTP"/>
                                </fieldset>
                            </div>

                        </div>

                        <div class="clear"></div>
                    </div>


                    <div id="sharebuttons">
                        <fieldset class="input-block">
                        <input type="submit" value="Authenticate" class="saveb editbasicinfodemo shareRecordbut submit_btn">

                            </fieldset>

                    </div>
                </form>
                <div class="note">Note : Authenticate using OTP sent to patient</div>

                <div>
                </div>
            </div>
        </div>
    </div>


</div>

<div id="fillPatDtlsPopup">

    <div class="form-wrapper confirmAppForm class-popup" id="confirmAppForm" style="max-width: 700px !important;">
        <div class="close-showPatDetail"></div>
        <div class="confirmpadding" id="tmm-form-wizard">
            <div class="note"></div>
            <div class="form-wrapper">
                <div class="confirmappointemnt">
                    Fill Patient Details before Consultation
                </div>

                    <div class="clear"></div>

                    <div class="basicdetails-modal spce">
                        <form method="post" name="user" id="basicinformation" enctype='application/json'>
                            <div class="hide-info">
                                <input type="hidden" id="patientId" name="patientId"/>
                                <div class="row">
                                    <div class="col-md-4 no-pad ">
                                        <fieldset class="input-block">
                                            <label>Date of Birth<span style="color:red;">*</span> </label>
                                            <input type="text" placeholder="dd/MM/yyyy" required title="Date of Birth"
                                                   id="t_dateofBirth" name="userDetails.dateofBirth" maxlength="10"
                                                   pattern="^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$"
                                                   onblur="checkDateofBirth(this.value);this.checkValidity();"/>
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>

                                    <div class="col-md-4 no-pad ">
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
                                    <div class="col-md-4 no-pad">
                                        <fieldset class="input-block">
                                            <label>Blood Group</label>
                                            <div class="dropdown">
                                                <select name="userDetails.bloodGroup" id="t_bloodGroup" title="Blood Group" class="dropdown-select"></select>
                                            </div>

                                        </fieldset>
                                       </div>
                                    <!--/ .tooltip-->
                                </div>
                                <div class="row">
                                    <div class="col-md-4 no-pad ">
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
                                <div class="assign-space" style="padding-top: 10px">
                                    <input type="button" value="Cancel" class="saveb editbasicinfo" id="cancelprofile">
                                    <button type="submit" class="saveb editbasicinfo-vitals" id="saveClinicProfile">Save</button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="clear"></div>

            </div>
            <div class="clear"></div>

        </div>
    </div>

</div>

<script>
    $(document).ready(function () {
        $("#t_dateofBirth").keyup(function () {
            if ($(this).val().length == 2 || $(this).val().length == 5) {
                $(this).val($(this).val() + "/");
            }
        });

        $(".close-showPatDetail").click(function () {
            $("#fillPatDtlsPopup").removeClass("dailog-show");
        });

        $("#cancelprofile").click(function () {
            $("#fillPatDtlsPopup").removeClass("dailog-show");
        });

    });

    function checkDateofBirth(userName) {
        var selectedDate = $("#t_dateofBirth").datepicker('getDate');
        var now = new Date();
        var uName = document.getElementById("t_dateofBirth");
        if (selectedDate > now) {
            uName.setCustomValidity("Enter valid date of birth");
        } else {
            uName.setCustomValidity("");
        }

    }

    function PDemoBasicFormToJSON(form) {
        var array = jQuery(form).serializeArray();
        var json = {};
        var newName;
        var parentName;
        var newVal;
        var userDetails = {};
        var idCardDetails = {};
        var insuranceDetails = {};
        jQuery.each(array, function () {
            var name = this.name;
            if (name.indexOf(".") > -1) {
                parentName = name.split('.')[0];
                newName = name.split('.')[1];
                if (parentName == 'userDetails') {
                    userDetails[newName] = this.value || '';
                    newVal = userDetails;
                }
                if (parentName == 'idCardDetails') {
                    idCardDetails[newName] = this.value || '';
                    newVal = idCardDetails;
                }
                if (parentName == 'insuranceDetails') {
                    insuranceDetails[newName] = this.value || '';
                    newVal = insuranceDetails;
                }
            }

            else {
                parentName = this.name;
                newVal = this.value;
            }
            json[parentName] = newVal || '';
        });
        return json;
    }

</script>

<script type="text/javascript" src="resources/js/server/consultation.js"></script>
<script type="text/javascript" src="resources/js/server/clinicPatientsPopup.js"></script>

</html>