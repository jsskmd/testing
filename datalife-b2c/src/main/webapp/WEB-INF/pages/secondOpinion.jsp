<%--
  Created by IntelliJ IDEA.
  User: dscblpo
  Date: 4/11/2016
  Time: 1:51 PM
  To change this template use File | Settings | File Templates.
--%>
<form id="secondOpinionform" name="providerDetails" method="POST" enctype="application/json">
    <input type="hidden" name="role" value="ROLE_REFERRALDOCTOR">
    <input type="hidden" name="type" id="userRole" value="Referral Doctor">
    <div class="form-wizard">
        <div class="">
            <div class="row">
                <div class="col-md-6 no-padding">
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>First Name<span style="color:red;">*</span></label>
                            <input type="text" placeholder="First Name" name="firstName" required="required" pattern="^[a-zA-Z ]+$" class="form-icon form-icon-user"  title="First name">
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label> Last Name<span style="color:red;">*</span></label>
                            <input type="text" pattern="^[a-zA-Z ]+$" required="required" name="lastName" placeholder="Last Name" title="Last name">
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>
                <div class="col-md-6  no-padding">
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label> Mobile Number<span style="color:red;">*</span></label>
                            <input type="text" id="sop_mobilePhone" pattern="^\d{10}$" name="mobilePhone" placeholder="Mobile Number" required  title="Mobile Number eg: 9000000099" onblur="checkMobileExistsAllReady(this.value,'ROLE_REFERRALDOCTOR','sop_mobilePhone')"/>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>Email<span style="color:red;">*</span></label>
                            <input type="text" id="sop_email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="email" placeholder="Email" required onblur="checkEmailExistsAllReady(this.value,'ROLE_REFERRALDOCTOR');"
                                   title="Email"/>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6  no-padding">
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>Specialty<span style="color:red;">*</span></label>
                            <div class="dropdown">
                                <select class="dropdown-select" id="autocomplete_speciality" name="speciality" required="required" title="Specialization"><option value="" disabled="" selected="">Select Speciality</option></select>
                                <%--<input type="hidden" name="speciality" id="specialityId">--%>
                            <!--/ .tooltip-->
                                </div>
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>Medical Licence Number<span style="color:red;">*</span></label>
                            <input type="text" placeholder="Medical Licence Number" name="licNo" id="sec_licNo" required="required"  class="form-icon form-icon-user" pattern="^[a-zA-Z0-9 ]+$" title="Medical Licence Number" onblur="checkLicRegExistsAllReady(this.value,'sec_licNo')">
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>
                <div class="col-md-6  no-padding">
                    <div class="col-md-6 col-sm-6 no-pad">
                        <div class="col-md-6 col-sm-6 no-pad">
                            <fieldset class="input-block">
                                <label>Qualification<span style="color:red;">*</span></label>
                                <input type="text" pattern="^[a-zA-Z,. ]+$" required="required" name="qualification" placeholder="Qualification" title="Qualification">
                                <!--/ .tooltip-->
                            </fieldset>
                        </div>
                        <div class="col-md-6 col-sm-6 no-pad">
                            <fieldset class="input-block">
                                <label>Experience<span style="color:red;">*</span></label>
                                <input type="text" placeholder="Experience" name="experience" required="required" pattern="[0-9]{2}" class="form-icon form-icon-user" title="Experience eg:02 or 12">
                                <!--/ .tooltip-->
                            </fieldset>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>Affiliations</label>
                            <input type="text"  placeholder="Affiliations" name="affiliations" title="Affiliations">
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 no-padding">
                    <div class="col-md-6 col-sm-6 no-pad  ">
                        <fieldset class="input-block">
                            <label>  Address<span style="color:red;">*</span></label>
                            <input type="text" required="required" placeholder="Address" name="address" title="Address">
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad ">
                        <div class="col-md-6 col-sm-6 no-pad ">
                            <fieldset class="input-block">
                                <label>Location<span style="color:red;">*</span> </label>
                                <input type="text" pattern="^[a-zA-Z ]+$" required="required" name="location" placeholder="Location" title="Location">
                                <!--/ .tooltip-->
                            </fieldset>
                        </div>
                        <div class="col-md-6 col-sm-6 no-pad ">
                            <fieldset class="input-block">
                                <label>Zip Code<span style="color:red;">*</span></label>
                                <input type="text" required="required" pattern="[0-9]{4,6}" placeholder="Zip Code" name="zipCode" title="Zip Code">
                                <!--/ .tooltip-->
                            </fieldset>
                        </div>
                    </div>
                </div>
                <div class="col-md-6  no-padding">

                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block" id="sopcity_fieldset">
                            <label> Select City<span style="color:red;">*</span></label>
                            <div class="dropdown">
                                <select class="dropdown-select" id="sopCity" name="city"  required=""></select>
                            </div>
                        </fieldset>
                       <%-- <input type="hidden" name="city" id="ssopcity"/>--%>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>Country<span style="color:red;">*</span></label>
                            <div class="dropdown">
                                <select class="dropdown-select" id="sopCountry" required="required" name="country" ></select>
                                <%--<input type="hidden"  name="country" id="sopcountryName" required=""/>--%>
                            </div>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>

            </div>
            <div class="row">
                <div class="clearfix"></div>
                <div class="margin-top33">
                    <div class="registration-btn">
                        <button type="submit" value="Register" class="register-btn" id="secondOpinionDocRegistration">Sign Up</button>
                    </div>
                    <div class="label-demo"> By clicking Sign Up, you agree to our <a class="terms-link" target="_blank"> Terms and Conditions</a> </div>
                </div>
            </div>
        </div>
    </div>
</form>
<script>
    $(document).ready(function () {
        'use strict';
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });


        fetchCountry("sopCountry");

        $("#sopCountry").on('change', function () {
            var country = $(this).val();
            var id = 'sopcity_fieldset';
            var inputId = 'sopCity';
            fetchHospitalCity(country,id,inputId);
        });

        var url = "../common/getDoctorSpeciality";
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                getSpecialities(data, 'autocomplete_speciality');
            },
            error: function (data) {

            }
        });

        $('form#secondOpinionform').off().submit(function (e) {
            e.preventDefault();
            $("#secondOpinionform").find(".register-btn").html('<img src="../resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">Please wait..');
            var url = "../api/user/provider/submitDetails";

            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(ProviderRegistrationFormToJSON('#secondOpinionform')),
                contentType: 'application/json',
                success: function (data) {
                    var statusCode = data['statusCode'];
                    var message = data['message'];
                    switch(statusCode){
                        case 200 : $("body").load("../activate/afterSubmission", function () {
                            $('#userName').text( data['name']);
                            $("#message").empty().append(message);
                            ChangeUrl('page1', 'activate/afterSubmission');
                        });
                            break;
                        case 406 :
                            $("#message").empty().append(message);
                            break;
                    }
                },
                error: function (data) {

                }
            });
            $(this).find(".register-btn").html("Sign Up");
            return false;
        });
    });
</script>

