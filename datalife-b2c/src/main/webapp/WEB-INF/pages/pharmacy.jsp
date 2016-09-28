<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 5/12/2016
  Time: 11:39 AM
  To change this template use File | Settings | File Templates.
--%>

<form id="pharmacyForm" name="providerDetails" method="POST"  enctype="application/json">
    <input type="hidden" name="role" value="ROLE_PHARMACY">
    <input type="hidden" name="type" id="userRole" value="Pharmacy">
    <div class="form-wizard">
        <div class="">
            <div class="row">
                <div class="col-md-9">
                    <div class="form-header">
                        <div class="form-title slots"><b> Pharmacy Details</b></div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 no-padding">
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>Pharmacy Name<span style="color:red;">*</span></label>
                            <input type="text" placeholder="Pharmacy Name" name="name" required="required" pattern="^[a-zA-Z ]+$" maxlength="30" class="form-icon form-icon-user"  title="only Alphabets with 30 character">
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>Registration/Licence No<span style="color:red;">*</span></label>
                            <input type="text" placeholder="Registration/Licence No" name="licNo" required="required" id="phar_licRegNo" pattern="^[a-zA-Z0-9]*$" class="form-icon form-icon-user"  title="Registration/Licence No" onblur="checkLicRegExistsAllReady(this.value,'phar_licRegNo')">
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-9">
                    <div class="form-header">
                        <div class="form-title slots"><b> Contact Info</b></div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 no-padding">
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>Contact Person<span style="color:red;">*</span></label>
                            <input type="text" placeholder="Contact Person" name="contactPerson" required="required" pattern="^[a-zA-Z ]+$" class="form-icon form-icon-user"  title="only Alphabets with 30 character">
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>Contact Person Mobile No<span style="color:red;">*</span></label>
                            <input type="text" pattern="^\d{10}$" required="required" name="mobilePhone" placeholder="Mobile No" maxlength="10" id="phr_mobNo" onblur="checkMobileExistsAllReady(this.value,'ROLE_PHARMACY','phr_mobNo')" title="Use number only Eg: 9*******99">
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>
                <div class="col-md-6  no-padding">
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label> Work Phone<span style="color:red;">*</span></label>
                            <input type="text" pattern="^\d{10}$" maxlength="10" name="workPhone" placeholder="Work Phone" required title="Use number only Eg: 9*******99"/>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>Email<span style="color:red;">*</span></label>
                            <input type="text" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="email" placeholder="Email" id="phr_email" required onblur="checkEmailExistsAllReady(this.value,'ROLE_PHARMACY','phr_email');"
                                   title="Eg:xyz@datalife.com"/>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 no-padding">
                    <div class="col-md-6 col-sm-6 no-pad ">
                        <fieldset class="input-block">
                            <label>Website</label>
                            <input type="text" id="hosp_website" name="website" placeholder="Website" pattern="^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\?([^#]*))?(#(.*))?" title="http://www.datalife.in or http://www.datalife.com">
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>Address <span style="color:red;">*</span></label>
                            <input type="text" required="required" name="address" placeholder="Address" title="Eg: #00 00th main 00th cross street">
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>
                <div class="col-md-6 no-padding">
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block">
                            <label>Location<span style="color:red;">*</span></label>
                            <input type="text" maxlength="30" pattern="^[a-zA-Z ]+$" name="location" placeholder="Location" required
                                   title="Allows only 30 character Eg: Rajajinagar,Kormagala"/>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad">
                        <fieldset class="input-block" id="phrcity_fieldset">
                            <label> Select City<span style="color:red;">*</span></label>
                            <div class="dropdown">
                                <select class="dropdown-select" id="phrCity" name="city" required="" title="Eg:Bangalore,Mumbai"></select>
                            </div>
                        </fieldset>
                    </div>

                </div>
            </div>
            <div class="row">

            <div class="col-md-6 col-sm-6 no-pad">
                <div class="col-md-6 col-sm-6 no-pad">
                    <fieldset class="input-block">
                        <label>Zip Code<span style="color:red;">*</span></label>
                        <input type="text" maxlength="7" pattern="^[\+?\d]{6,7}" name="zipCode" placeholder="Zip Code" required
                               title="Allows only 6 to 7 digits Eg: 560010 or 5600101"/>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
                <div class="col-md-6 col-sm-6 no-pad">
                    <fieldset class="input-block">
                    <label>Country <span style="color:red;">*</span></label>
                    <div class="dropdown">
                        <select id="phr_ucicountry" name="country" required="required"  class="dropdown-select">

                        </select>
                        <!--/ .tooltip-->
                    </div>
                </fieldset>
               </div>
            </div>

        </div>

            <div class="row">
                <div class="col-md-9">
                    <div class="form-header">
                        <div class="form-title slots"><b> Additional Information</b></div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 no-padding">
                    <div class="col-md-6 col-sm-6 no-pad ">
                        <fieldset class="input-block">
                            <label>Additional Info If Any </label>
                            <textarea name="addedInfo" placeholder="Additional Info If Any"  maxlength="200" title="Allows 200 character"></textarea>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div class="margin-top33">
                    <div class="registration-btn">
                        <button class="register-btn" id="labRegistration" type="submit">Sign Up</button>
                    </div>
                    <div class="label-demo"> By clicking Sign Up, you agree to our <a target="_blank" class="terms-link"> Terms and Conditions</a> </div>
                </div>
            </div>

        </div>
    </div>
</form>
<script>
    $(document).ready(function(){

        fetchCountry("phr_ucicountry");

        $("#phr_ucicountry").on('change', function () {
            var country = $(this).val();
            var id = 'phrcity_fieldset';
            var inputId = 'phrCity';
            fetchHospitalCity(country,id,inputId);
        });

        $("form#pharmacyForm").submit(function (e) {
            e.preventDefault();
            $(this).find(".register-btn").html('<img src="../resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">Please wait..');
            var sol = "";
            $('.SlectBox-grp option:selected').each(function(){
                if(sol != ""){
                    sol +=','+$(this).val();
                }else{
                    sol = $(this).val();
                }
                $("#servicesOfLab").val(sol);
            });

            var requestForm = ProviderRegistrationFormToJSON('#pharmacyForm');
            var url = "../api/user/provider/submitDetails";
            alert(JSON.stringify(requestForm));
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(requestForm),
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
                            var licNo = document.getElementById("licRegNo");
                            licNo.setCustomValidity(message);
                            break;
                    }
                },
                error: function (data) {
                    window.location = 'invalidSession';
                }
            });
            $("#pharmacyForm").find(".register-btn").html("Sign Up");
        });

    });

</script>