<div class="form-wrapper confirmAppForm class-popup" id="confirmAppForm">
    <div class="cancel-common"></div>

    <div class="confirmpadding" id="tmm-form-wizard">

        <div class="note"></div>
        <div class="form-wrapper">
            <div class="confirmappointemnt" style="text-align:center">
                DataLife
            </div>
            <div class=" splitcode">
                <ul class="todayappointmentul">
                    <li>
                        <div class="searchalign">
                            <div class="search-doc show_border_clinic" id="search_doctor"><i class="icon-search-1"></i>
                                Doctor Search
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="searchalign">
                            <div class="ad-doc" id="add_doctor"><i class="icon-plus-circled"></i>Register New Doctor</div>
                        </div>
                    </li>
                </ul>
            </div>
            <div id="confirmMessage"></div>
            <div id="errorMessage"></div>

            <div id="search_doctor_show">
                <div id="add_doctor_view">

                    <div class="row" id="disc-class">
                        <form method="post" id="clinicDoctor_search">
                            <div class="col-md-3 no-pad ">
                                <fieldset class="input-block">

                                    <input type="text" class="form-icon form-icon-user demo"
                                           placeholder="Search by DataLife Id, Mobile Number" required="" title="Search"
                                           id="searchInput">

                                </fieldset>
                            </div>
                            <div class="col-md-3 no-pad serach">
                             <button type="submit" class="saveb editbasicinfo submit_btn">Search</button>

                            </div>
                        </form>
                    </div>
                    <div class="background-for-list-space classwrap" id="cliniDoctorsDiv">
                        <div class=" ">

                            <table id="clinicDoctorsTable" class="display" cellpadding="0" cellspacing="0" width="100%">
                                <thead>
                                <tr>
                                    <th style="text-align:center;">DateLife ID</th>
                                    <th style="text-align:center;"> Name</th>
                                    <th style="text-align:center;">Mobile Number</th>
                                    <th style="text-align:center;">Add to Clinic</th>

                                </tr>
                                </thead>
                                <tbody id="clinicDoctorsTbody">

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div id="authenticate_view" class="hide">
                    <div class="row">

                        <form method="post" id="authenticate_form">
                            <div class="col-md-3 no-pad ">
                                <fieldset class="input-block">
                                    <input type="hidden" id="otpclinicId" name="clinicId"/>
                                    <input type="hidden" id="otpdoctorId" name="doctorId"/>
                                    <input type="password" class="form-icon form-icon-user demo" placeholder="Enter OTP"
                                           name="authentication.otp" required="" title="Search" id="otp">
                                </fieldset>
                            </div>
                            <div class="col-md-3 no-pad serach">
                                <button type="submit" class="saveb editbasicinfo-vitals">Authenticate</button>
                                <button type="button" id="authencate_back" class="saveb editbasicinfo">Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="hidden-status hide" id="add_doctor_show">
                <form method="post" name="user" id="clinicDoctorForm">
                    <input type="hidden" name="role" id="userRole" value="ROLE_DOCTOR">
                    <input type="hidden" id="clinicIdInput" name="clinicId" value="">

                    <div class="form-wizard add_class_for_popup">

                        <div class="row">

                            <div class="col-md-3 no-pad ">
                                <fieldset class="input-block">
                                    <label class="select-records"> First Name<span style="color:red;">*</span></label>
                                    <input type="text" placeholder="First Name" required="" name="userDetails.firstName"
                                         maxlength="20"  pattern="^[a-zA-Z ]+$" title=" First Name">

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

                            <div class="col-md-3 no-pad">

                                <fieldset class="input-block">
                                    <label class="select-records">Medical License Number<span style="color:red;">*</span></label>
                                    <input type="text" placeholder="Medical License Number" required="" id="licRegNo"
                                           name="doctorInfo.mlrNumber" title=" Medical License Number "
                                           onBlur="checkMedLicRegExistsAllReady(this.value);">
                                </fieldset>
                            </div>


                            <div class="col-md-3 no-pad ">
                                <fieldset class="input-block">
                                    <label class="select-records"> Mobile Number<span style="color:red;">*</span></label>
                                    <input id="mobile" type="tel" name="userContactInfo.mobilePhone"
                                           placeholder="Mobile Number" required="" onblur="checkMobileExistsAllReady(this.value,'ROLE_DOCTOR')"
                                           maxlength="13" pattern="^[\+?\d]{10,13}"/>

                                </fieldset>
                            </div>

                            <div class="col-md-3 no-pad ">
                                <fieldset class="input-block">
                                    <label class="select-records"> Email<span style="color:red;">*</span></label>
                                    <input type="email" placeholder="Email" required="" title="Email " id="email"
                                           class="" name="userContactInfo.email"
                                           onBlur="checkEmailExistsAllReady(this.value,'ROLE_DOCTOR');">

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


        </div>
        <div class="clear"></div>

    </div>
</div>


<script type="text/javascript" src="resources/js/server/clinicDoctorPopup.js"></script>
