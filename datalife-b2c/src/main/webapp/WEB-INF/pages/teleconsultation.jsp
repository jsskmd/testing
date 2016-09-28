<%--
  Created by IntelliJ IDEA.
  User: dscblpo
  Date: 4/11/2016
  Time: 1:51 PM
  To change this template use File | Settings | File Templates.
--%>
<form id="teleConsultationForm" name="providerDetails" method="POST"  enctype="application/json">
    <input type="hidden" name="role" value="ROLE_TELECONSULTANT">
    <input type="hidden" name="type" id="userRole" value="Tele Consultant">
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
                            <input type="text" id="telcon_mobilePhone" pattern="^\d{10,12}$" name="mobilePhone" placeholder="Phone Number" required onblur="checkMobileExistsAllReady(this.value,'ROLE_TELECONSULTANT','telcon_mobilePhone')"
                                   title="Phone Number"/>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>Email<span style="color:red;">*</span></label>
                            <input type="text" id="telcon_email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="email" placeholder="Email" required onblur="checkEmailExistsAllReady(this.value,'ROLE_TELECONSULTANT','telcon_email');"
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
                                <select class="dropdown-select" id="tc_autocomplete_speciality" required="required" title="Specialization"><option value="" disabled="" selected="">Select Speciality</option></select>
                                <input type="hidden" name="speciality" id="specialityId">
                                <!--/ .tooltip-->
                            </div>
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>Medical Licence Number<span style="color:red;">*</span></label>
                            <input type="text" placeholder="Medical Licence Number" name="licNo" required="required" id="telcon_licNo" class="form-icon form-icon-user"  title="Medical Licence Number" onblur="checkLicRegExistsAllReady(this.value,'telcon_licNo')">
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
                                <input type="text" required="required" pattern="[0-9]{4,6}" placeholder="Zip Code" name="zipCode" title="Zip Code" min="4" maxlength="6">
                                <!--/ .tooltip-->
                            </fieldset>
                        </div>
                    </div>
                </div>
                <div class="col-md-6  no-padding">
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block" id="city_fieldset">
                            <label> Select City<span style="color:red;">*</span></label>
                            <div class="dropdown">
                                <select class="dropdown-select" id="tcCity" name="city" required=""></select>
                            </div>
                        </fieldset>
                        <%--<input type="hidden" name="city" id="stcCity" name="city"/>--%>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>Country<span style="color:red;">*</span></label>
                            <div class="dropdown">
                                <select class="dropdown-select" id="tcCountry" ></select>
                                <input type="hidden"  name="country" id="tcCountryName" required=""/>
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
                        <button type="submit" value="Register" class="register-btn" id="teleConsultation">Sign Up</button>
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

        fetchCountry("tcCountry");

        $("#tcCountry").on('change', function () {
            var country = $(this).val();
            var id = 'city_fieldset';
            var inputId = 'tcCity';
            fetchHospitalCity(country,id,inputId);
        });

        var url = "../common/getDoctorSpeciality";
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                getSpecialities(data, 'tc_autocomplete_speciality');
            },
            error: function (data) {

            }
        });

        $('form#teleConsultationForm').off().submit(function (e) {
            e.preventDefault();
            $("#teleConsultation").html('<img src="../resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">Please wait..');

            var text=$('#tcCountry').find('option:selected').text();
            $("#tcCountryName").val(text);

            var specialityId=$('#autocomplete_speciality').find('option:selected').val();
            $("#specialityId").val(specialityId);

           /* var city=$('#tcCity').find('option:selected').text();
            $("#stcCity").val(city);*/

            var url = "../api/user/provider/submitDetails";

            var sec3 = ProviderRegistrationFormToJSON('#teleConsultationForm');

            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(sec3),
                contentType: 'application/json',
                success: function (data) {
                    var statusCode = data['statusCode'];
                    var message = data['message'];
                    var name = data['name'];
                    switch(statusCode){
                        case 200 : $("body").load("../activate/afterSubmission", function () {
                            $('#userName').text(name);
                           /* message = '<div class="notification alert-error spacer-t10"><p>'+message+'</p><a href="#" class="close-btn">?</a></div>';*/
                            $("#message").empty().append(message);
                            ChangeUrl('page1', 'activate/afterSubmission');
                        });
                            break;
                        case 406 :  /*message = '<div class="notification alert-error spacer-t10"><p>'+message+'</p><a class="close-btn">?</a></div>';*/
                            $("#message").empty().append(message);
                            break;
                    }
                },
                error: function (data) {

                }
            });
            $("#teleConsultation").html('Sign Up');
            return false;
        });
    });
</script>
