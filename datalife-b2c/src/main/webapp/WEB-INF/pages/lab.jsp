<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 5/12/2016
  Time: 11:39 AM
  To change this template use File | Settings | File Templates.
--%>


<form id="labForm" name="providerDetails" method="POST"  enctype="application/json">
<input type="hidden" name="role" value="ROLE_DIAGNOSTICLABS">
<input type="hidden" name="type" id="userRole" value="Diagnostic Labs">
<div class="form-wizard">
<div class="">
<div class="row">
    <div class="col-md-9">
        <div class="form-header">
            <div class="form-title slots"><b> Lab Details</b></div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-6 no-padding">
        <div class="col-md-6 col-sm-6 no-pad">
            <fieldset class="input-block">
                <label>Lab Name<span style="color:red;">*</span></label>
                <input type="text" placeholder="Lab Name" name="name" required="required" pattern="^[a-zA-Z ]+$" maxlength="30" class="form-icon form-icon-user"  title="only Alphabets with 30 character">
                <!--/ .tooltip-->
            </fieldset>
        </div>
        <div class="col-md-6 col-sm-6 no-pad">
            <fieldset class="input-block">
                <label>Registration/Licence No<span style="color:red;">*</span></label>
                <input type="text" placeholder="Registration/Licence No" name="licNo" required="required" id="lab_licRegNo" pattern="^[a-zA-Z0-9]*$" class="form-icon form-icon-user"  title="Registration/Licence No" onblur="checkLicRegExistsAllReady(this.value,'lab_licRegNo')">
                <!--/ .tooltip-->
            </fieldset>
        </div>
    </div>
    <div class="col-md-6 no-padding">
        <div class="col-md-6 col-sm-6 no-pad ">
            <fieldset class="input-block">
                <label>Services<span style="color:red;">*</span></label>
                <div class="dropdown overflow-visible">
                    <select class="dropdown-select SlectBox-grp" required multiple="multiple" id="labServices">

                    </select>
                    <input type="hidden" name="servicesOfLab" id="servicesOfLab">
                </div>
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
            </fieldset>
        </div>
        <div class="col-md-6 col-sm-6 no-pad">
            <fieldset class="input-block">
                <label>Contact Person Mobile No<span style="color:red;">*</span></label>
                <input type="text" pattern="^\d{10}$" required="required" name="mobilePhone" maxlength="10" placeholder="Mobile No" id="lab_mobNo" title="Use number only Eg: 9*******99" onblur="checkMobileExistsAllReady(this.value,'ROLE_DIAGNOSTICLABS','lab_mobNo')">
            </fieldset>
        </div>
    </div>
    <div class="col-md-6  no-padding">
        <div class="col-md-6 col-sm-6 no-pad">
            <fieldset class="input-block">
                <label> Work Phone<span style="color:red;">*</span></label>
                <input type="text" pattern="^\d{10}$" name="workPhone" placeholder="Work Phone" maxlength="10" required title="Use number only Eg: 9*******99 "/>
            </fieldset>
        </div>
        <div class="col-md-6 col-sm-6 no-pad">
            <fieldset class="input-block">
                <label>Email<span style="color:red;">*</span></label>
                <input type="text" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="email" placeholder="Email" id="lab_email" required onblur="checkEmailExistsAllReady(this.value,'ROLE_DIAGNOSTICLABS','lab_email');"
                       title="Eg:xyz@datalife.com"/>
            </fieldset>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-6 no-padding">
        <div class="col-md-6 col-sm-6 no-pad ">
            <fieldset class="input-block">
                <label>Website</label>
                <input type="text" id="hosp_website" name="website" placeholder="Website"  pattern="^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\?([^#]*))?(#(.*))?" title="http://www.datalife.in or http://www.datalife.com">
            </fieldset>
        </div>
        <div class="col-md-6 col-sm-6 no-pad">
            <fieldset class="input-block">
                <label>Address <span style="color:red;">*</span></label>
                <input type="text" required="required" name="address" placeholder="Address" title="Eg: #00 00th main 00th cross street">
            </fieldset>
        </div>
    </div>
    <div class="col-md-6  no-padding">

        <div class="col-md-6 col-sm-6 no-pad">
            <fieldset class="input-block">
                <label>Location<span style="color:red;">*</span></label>
                <input type="text" maxlength="30" pattern="^[a-zA-Z ]+$" name="location" placeholder="Location" required
                       title="Allows only 30 character Eg: Rajajinagar,Kormagala"/>
            </fieldset>
        </div>

        <div class="col-md-6 col-sm-6 no-pad">
            <fieldset class="input-block" id="sopcity_fieldset">
                <label> Select City<span style="color:red;">*</span></label>
                <div class="dropdown">
                    <select class="dropdown-select" id="labCity" name="city"  required=""></select>
                </div>
            </fieldset>
        </div>

    </div>
</div>
<div class="row">
    <div class="col-md-6 no-padding">
        <div class="col-md-6 col-sm-6 no-pad">
            <fieldset class="input-block">
                <label>ZipCode<span style="color:red;">*</span></label>
                <input type="text" maxlength="7" pattern="^[\+?\d]{6,7}" name="zipCode" placeholder="zipCode" required
                       title="Allows only 6 to 7 digits Eg: 560010 or 5600101"/>
                <!--/ .tooltip-->
            </fieldset>
        </div>
        <div class="col-md-6 col-sm-6 no-pad">
            <fieldset class="input-block">
                <label>Country <span style="color:red;">*</span></label>
                    <div class="dropdown">
                        <select class="dropdown-select" id="lab_ucicountry" required="required" name="country" ></select>
                        <%--<input type="hidden"  name="country" id="sopcountryName" required=""/>--%>
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
        <div class="col-md-6 col-sm-6 no-pad">
            <fieldset class="input-block">
                <label> Facilities</label>
                <textarea  id="hosp_facilities" placeholder="Facilities" name="facilities" maxlength="200" title="Allows 200 character"></textarea>                    <!--/ .tooltip-->
            </fieldset>
        </div>
        <div class="col-md-6 col-sm-6 no-pad">
            <fieldset class="input-block">
                <label>Accreditations/Awards If Any </label>
                <textarea name="awardsIfAny" placeholder="Accreditations/Awards If Any" maxlength="200" title="Allows 200 character"></textarea>
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

        fetchCountry("lab_ucicountry");

        $("#lab_ucicountry").on('change', function () {
            var country = $(this).val();
            var id = 'labcity_fieldset';
            var inputId = 'labCity';
            fetchHospitalCity(country,id,inputId);
        });

        var url1 = "../common/getLabTestCategories";
        $.ajax({
            url: url1,
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                $.each(data, function (idx, obj) {
                    $('#labServices').append($("<option></option>").attr("value", obj['labTestCategoriesId']).text(obj['labTestName']));
                });
                $('.SlectBox-grp').SumoSelect({okCancelInMulti:true, selectAll:true});
            },
            error: function (data) {

            }
        });

        $("form#labForm").submit(function (e) {
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

            var requestForm = ProviderRegistrationFormToJSON('#labForm');
            var url = "../api/user/provider/submitDetails";
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
            $(this).find(".register-btn").html("Sign Up");
        });

    });

</script>