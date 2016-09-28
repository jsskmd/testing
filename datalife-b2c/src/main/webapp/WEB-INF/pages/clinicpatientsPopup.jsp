<script>
    $(document).ready(function () {
        $(".pcancel-common").off().on('click', function (event) {
            $("body").css("overflow","auto");
            $("#clinicPatientsPopup").removeClass("dailog-show");
            $("#access_encounter").removeClass("dailog-show");
            event.stopPropagation();
        });

    });
</script>
<div class="form-wrapper confirmAppForm class-popup" id="confirmAppForm">
<div class="pcancel-common"></div>
<div class="confirmpadding" id="tmm-form-wizard">

<div class="note"></div>
<div class="form-wrapper">
    <div class="confirmappointemnt" style="text-align:center">
        DataLife
    </div>
    <div class=" splitcode">
        <ul class="todayappointmentul">
            <li>
                <div class=" searchalign">
                    <div class="search-doc show_border_clinic" id="search_patient"><i class="icon-search-1"></i>
                        Patient Search
                    </div>
                </div>
            </li>
            <li>
                <div class="searchalign">
                    <div class="ad-doc" id="add_patient"><i class="icon-plus-circled"></i>Register New Patient</div>
                </div>
            </li>
            <li><div class="searchalign">
                <div class="ad-doc" id="patient_list"><i class="icon-search-1"></i>Clinic Patients</div>
            </div> </li>
        </ul>
    </div>
    <div id="ppconfirmMessage"></div>
    <div id="pperrorMessage"></div>

    <div id="search_patient_show">
        <div id="add_patient_view">
            <div class="row" id="disc-class">
                <form method="post" id="clinicPatient_search">
                    <div class="col-md-3 no-pad ">
                        <fieldset class="input-block">
                            <input type="text" class="form-icon form-icon-user demo" placeholder="Search by DataLife Id, Mobile Number"
                                   required="" title="Search" id="searchPatientInput">
                        </fieldset>
                    </div>
                    <div class="col-md-3 no-pad serach clinicsepatients">
                        <button type="submit" class="saveb editbasicinfo submit_btn">Search</button>
                    </div>
                </form>
            </div>
            <div class="background-for-list-space classwrap" id="cliniPatientsDiv">
                    <table id="clinicPatientsTable" class="display" cellpadding="0" cellspacing="0"
                           width="100%">
                        <thead>
                        <tr>
                            <th style="text-align:center;">DateLife ID</th>
                            <th style="text-align:center;"> Name</th>
                            <th style="text-align:center;">Mobile Number</th>
                            <th style="text-align:center;">Add to Clinic</th>
                        </tr>
                        </thead>
                        <tbody id="clinicPatientsTbody">

                        </tbody>
                    </table>
            </div>
        </div>
        <div id="pauthenticate_view" class="hide">
            <div class="row">

                <form method="post" id="pauthenticate_form">
                    <div class="col-md-3 no-pad ">
                        <fieldset class="input-block">
                            <input type="hidden" id="otpclinicId" name="clinicId"/>
                            <input type="hidden" id="otppatientId" name="patientId"/>
                            <input type="password" class="form-icon form-icon-user demo" placeholder="Enter OTP"
                                   name="authentication.otp" required="" title="Search" id="otp">
                        </fieldset>
                    </div>
                    <div class="col-md-3 no-pad serach">
                        <button type="submit" class="saveb editbasicinfo-vitals">Authenticate</button>
                        <button type="button" id="pauthencate_back" class="saveb editbasicinfo">Cancel</button>
                    </div>
                </form>
            </div>
            <div class="note">Note : Authenticate using OTP sent to patient</div>
        </div>
    </div>

    <div class="hidden-status hide" id="add_patient_show">
        <form method="post" name="user" id="clinicPatientForm">
            <input type="hidden" name="role" id="userRole" value="ROLE_PATIENT">
            <input type="hidden" id="clinicIdInput" name="clinicId" value="">

            <div class="form-wizard add_class_for_popup">

                <div class="row">

                    <div class="col-md-3 no-pad ">
                        <fieldset class="input-block">
                            <label class="select-records"> First Name<span style="color:red;">*</span></label>
                            <input type="text" placeholder="First Name" required="" name="userDetails.firstName"
                                   maxlength="20" pattern="^[a-zA-Z ]+$" title=" First Name">

                        </fieldset>
                    </div>
                    <div class="col-md-3 no-pad">

                        <fieldset class="input-block">
                            <label class="select-records">Last Name<span style="color:red;">*</span></label>
                            <input type="text" placeholder=" Last Name" required="" name="userDetails.lastName"
                                   maxlength="20"  pattern="^[a-zA-Z ]+$" title=" Last Name " class="">
                        </fieldset>
                    </div>

                    <div class="col-md-3 no-pad">

                        <fieldset class="input-block">
                            <label class="select-records">User Name<span style="color:red;">*</span></label>
                            <input type="text" placeholder=" User Name" required="" id="userName"
                                   pattern="^[a-zA-Z0-9]+$" name="userName" title=" User Name "
                                   onblur="checkUserExistsAllReady(this.value);">

                        </fieldset>
                    </div>

                    <div class="col-md-3 no-pad ">
                        <fieldset class="input-block">
                            <label class="select-records"> Mobile Number<span style="color:red;">*</span></label>
                            <input id="phone" type="tel" name="userContactInfo.mobilePhone" placeholder="Mobile Number" required=""
                                   maxlength="13" pattern="^[\+?\d]{10,13}"/>

                        </fieldset>
                    </div>

                    <div class="col-md-3 no-pad ">
                        <fieldset class="input-block">
                            <label class="select-records"> Email<span style="color:red;">*</span></label>
                            <input type="email" placeholder="Email" required="" title="Email " id="email"
                                   class="" name="userContactInfo.email">

                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="btn-static"><a class="">
                        <button type="submit" id="submit_clinicDoctors" class="register-btn">Save</button>
                    </a></div>
                </div>
            </div>
        </form>
    </div>

    <div id="patient_list_show" class="hidden-status hide" style="padding-bottom: 15px;">
        <div id="patient_list_view">
            <div class="background-for-list-space classwrap">
                <table id="clinicPatients" class="display no-footer dataTable dtr-inline" cellpadding="0" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th style="text-align:center;">DateLife ID</th>
                        <th style="text-align:center;"> Name</th>
                        <th style="text-align:center;">Mobile Number</th>
                        <th style="text-align:center;">Assign to</th>
                    </tr>
                    </thead>
                    <tbody id="clinicPatientsList">

                    </tbody>
                </table>
            </div>
        </div>
        <div id="assign_view" class="hide add_class_for_popup">
            <div class="note assign_pat_to_doc">Assign patient to doctor</div>
            <div class="row">
                <form method="post" id="passign_form">
                    <div class="col-md-3 no-pad ">
                        <fieldset class="input-block">
                            <input type="hidden" id="assignclinicId" name="clinicId"/>
                            <input type="hidden" id="assignpatientId" name="patientId"/>

                            <div class="dropdown">
                                <select class="dropdown-select" id="assignDoctorId" name="doctorId" title="Select Doctor" required="">
                                </select>
                            </div>
                        </fieldset>
                    </div>
                    <div class="col-md-3 no-pad serach clinicsepatients">
                        <button type="submit" class="saveb editbasicinfo-vitals submit_btn">Assign</button>
                        <button type="button" id="passign_back" class="saveb editbasicinfo">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="clear"></div>
</div>
</div>
<script type="text/javascript" src="resources/jsplugins/jquery.datetimepicker.min.js"></script>
<script type="text/javascript" src="resources/js/server/consultation.js"></script>
<script type="text/javascript" src="resources/js/server/clinicPatientsPopup.js"></script>

