
<div class="wrapper">
    <div class="form-container">
        <div id="tmm-form-wizard" class="container substrate">
            <div class="row">
                <div class="col-xs-12">
                    <h2 class="form-login-heading">Register New Patient </h2>
                </div>

            </div>
            <div class="">

                <div class="confirmpadding" >

                    <div class="note"></div>
                    <div class="form-wrapper">

                        <div id="pconfirmMessage"></div>
                        <div id="perrorMessage"></div>

                        <div class="hidden-status" id="add_patient_show">
                            <form method="post" name="user" id="physicianPatientForm">
                                <input type="hidden" name="role" id="userRole" value="ROLE_PATIENT">
                                <div class="form-wizard">

                                    <div class="row">

                                        <div class="col-md-3 no-pad ">
                                            <fieldset class="input-block">
                                                <label class="select-records"> First Name<span style="color:red;">*</span></label>
                                                <input type="text" placeholder="First Name" required="" name="userDetails.firstName"
                                                       pattern="^[a-zA-Z ]+$" title=" First Name">

                                            </fieldset>
                                        </div>
                                        <div class="col-md-3 no-pad">

                                            <fieldset class="input-block">
                                                <label class="select-records">Last Name<span style="color:red;">*</span></label>
                                                <input type="text" placeholder=" Last Name" required="" name="userDetails.lastName"
                                                       pattern="^[a-zA-Z ]+$" title=" Last Name " class="">
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
                                                <input id="phone" type="tel" name="userContactInfo.mobilePhone"
                                                       placeholder="Mobile Number" required=""
                                                       maxlength="13" pattern="^[\+?\d]{10,13}"/>

                                            </fieldset>
                                        </div>

                                        <div class="col-md-3 no-pad ">
                                            <fieldset class="input-block">
                                                <label class="select-records"> Email<span style="color:red;">*</span></label>
                                                <input type="email" placeholder="Email" required="" title="Email " id="email"
                                                       class="" name="userContactInfo.email"/>

                                            </fieldset>
                                        </div>


                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="btn-static">
                                            <button type="submit" id="submit_clinicDoctors" class="register-btn submit_btn">Save</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>


                    </div>
                    <div class="clear"></div>
                </div>
            </div>

            <!--/ .form-wizard-->
        </div>



        <!--/ .container-->
    </div>
</div>

<script type="text/javascript" src="resources/js/server/consultation.js"></script>




