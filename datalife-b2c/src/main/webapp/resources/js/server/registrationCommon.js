$( document ).ready(function(){
    var password = document.getElementById("password")
        , confirm_password = document.getElementById("confirm_password");

    function validatePassword(){
        if(password.value != confirm_password.value) {
            confirm_password.setCustomValidity("Passwords Don't Match");
        } else {
            confirm_password.setCustomValidity('');
        }
    }
    if(password){
        password.onchange = validatePassword;
        confirm_password.onkeyup = validatePassword;
    }
});

function ChangeUrl(e, i) {
    if ("undefined" != typeof history.pushState) {
        var s = {Page: e, Url: i};
        history.pushState(s, s.Page, s.Url)
    }
}


function checkUserExistsAllReady(userName) {
    if (userName != "") {
        var url = "../api/user/isExists/" + userName;

        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                var statusCode = data['statusCode'];

                var message = data['message'];
                var uName = document.getElementById("userName");
                if (statusCode == 406) {
                    uName.setCustomValidity(message);
                } else {
                    uName.setCustomValidity('');

                }
            },
            error: function (data) {

            }
        });
    }
}

function checkLicRegExistsAllReady(licRegNo,id) {
    if (licRegNo != "") {
        var url = "../api/user/licRegExists/" + licRegNo;
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                var statusCode = data['statusCode'];
                var message = data['message'];
                var licNo = document.getElementById(id);
                if (statusCode == 406) {
                    licNo.setCustomValidity(message);
                } else {
                    licNo.setCustomValidity('');
                }
            },
            error: function (data) {

            }
        });
    }
}

function checkMedLicRegExistsAllReady(licRegNo) {
    if (licRegNo != "") {
        var url = "../api/user/medLicRegNoExists/" + licRegNo;
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                var statusCode = data['statusCode'];
                var message = data['message'];
                var licNo = document.getElementById("licRegNo");
                if (statusCode == 406) {
                    licNo.setCustomValidity(message);
                } else {
                    licNo.setCustomValidity('');
                }
            },
            error: function (data) {

            }
        });
    }
}

function checkEmailExistsAllReady(clinicEmailId,role,id) {

    if (clinicEmailId != "") {
        var url = "../api/user/isEmailIdExists";
        var jsondata = {"email": clinicEmailId,"role":role};
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsondata),
            contentType: 'application/json',

            success: function (data) {
                var statusCode = data['statusCode'];
                var message = data['message'];
                var email = document.getElementById(id);

                if (statusCode == 406) {
                    email.setCustomValidity(message);
                } else {
                    email.setCustomValidity("");
                }
            },
            error: function (data) {

            }
        });
    }
}

function checkMobileExistsAllReady(mobileNo,role,id) {

    if (mobileNo != "") {
        var url = "../api/user/isMobileNumberExists";
        var jsondata = {"mobileNo": mobileNo,"role":role};
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsondata),
            contentType: 'application/json',

            success: function (data) {
                var statusCode = data['statusCode'];
                var message = data['message'];
                var mobile = document.getElementById(id);
                if (statusCode == 406) {
                    mobile.setCustomValidity(message);
                } else {
                    mobile.setCustomValidity("");
                }
            },
            error: function (data) {

            }
        });
    }
}

function getCountry(data, id) {
    $('#' + id).empty().append("<option value='' disabled selected>Select Country</option>");
    $.each(data, function (idx, obj) {
        if (obj['countryName'] == 'Select Country') {
            $('#' + id).append($("<option value='' disabled selected></option>")
                .text(obj['countryName']));
        } else {

            if (obj['countryName'] == 'India'){
                $('#' + id).append($("<option></option>")
                    .attr("value", obj['countryId']).prop("selected","selected")
                    .text(obj['countryName']));
            }else{
                $('#' + id).append($("<option></option>")
                    .attr("value", obj['countryId'])
                    .text(obj['countryName']));
            }
        }
    });
}

function getSpecialities(data, id) {
    $.each(data, function (idx, obj) {
        if (obj['name'] == 'Select Speciality') {
            $('#' + id).append($("<option value='' disabled selected></option>")
                .attr("value", obj['specialityId'])
                .text(obj['name']));
        } else {
            $('#' + id).append($("<option></option>")
                .attr("value", obj['specialityId'])
                .text(obj['name']));
        }

    });
}

function combo(countryId, stateId, stateInput) {
    var value = jQuery('#' + countryId + ' option:selected').val();
    $("#d" + stateId).addClass("hide");
    $("#d" + stateInput).addClass("hide");
    if (value == '2') {
        $("#d" + stateId).removeClass("hide");
        fetchStates(value, stateId);
        $("#d" + stateInput).removeAttr("required");
        $("#"+stateInput).removeAttr("required");
    }
    else {
        $("#d" + stateInput).removeClass("hide");
        $("#d" + stateId).removeAttr("required");
        $("#" + stateId).removeAttr("required");
    }
}

function fetchStates(countryId, stateId, stateval) {
    var url = "../common/getStates/" + countryId;
    var name = "";
    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',

        success: function (data) {
            $('#' + stateId).empty();
            $.each(data, function (idx, obj) {

                if (obj.stateId == stateval) {
                    name = obj.stateName;
                }
                if (obj.stateName == 'Select State') {
                    $('#' + stateId).append($("<option value='' disabled selected></option>")
                        .text(obj.stateName));
                } else {
                    $('#' + stateId).append($("<option></option>")
                        .attr("value", obj.stateId)
                        .text(obj.stateName));
                }

            });
            $('#' + stateId).val(stateval);
            $('#state,#ucistate').text(name);
        },
        error: function (data) {
            window.location = 'invalidSession';
        }
    });
}

function fetchCountry(countryId){
    var url = "../common/getCountries";
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            $("#"+countryId).empty().append("<option value='' disabled selected>Select Country</option>");
            $.each(data, function (idx, obj) {
                $("#"+countryId).append($("<option></option>")
                    .attr("value", obj['countryId'])
                    .text(obj['countryName']));

            });
            $("#"+countryId).val('2').change();
        },
        error: function (data) {

        }
    });
}

function fetchHospitalCity(country,id,inputId){
    var url = "../common/getHospitalCities/" + country;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            if(data==''){
                $("#"+id).html('<label>City Name</label>' +
                    '<input type="text" required="" id='+inputId+' placeholder="City Name" title="Name of City" name="city">');
            }else{
                $("#"+id).html(' <label> Select City</label><div class="dropdown">'+
                    '<select class="dropdown-select" id='+inputId+' required=""   name="city" ></select></div>');
                $("#"+inputId).empty().append("<option value='' disabled selected>Select City</option>");
                $.each(data, function (idx, obj) {
                    $("#"+inputId).append($("<option></option>")
                        .attr("value", obj['name'])
                        .text(obj['name']));
                });
            }
        },
        error: function (data) {

        }
    });
}

function PRegistrationFormToJSON(form) {
    var array = jQuery(form).serializeArray();

    var json = {};
    var newName;
    var parentName;
    var newVal;
    var userDetails = {};
    var userContactInfo = {};
    jQuery.each(array, function () {
        var name = this.name;
        if (name.indexOf(".") > -1) {
            parentName = name.split('.')[0];
            newName = name.split('.')[1];
            if (parentName == 'userDetails') {
                userDetails[newName] = this.value || '';
                newVal = userDetails;
            }
            if (parentName == 'userContactInfo') {
                userContactInfo[newName] = this.value || '';
                newVal = userContactInfo;
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

function CRegistrationFormToJSON(form) {
    var array = jQuery(form).serializeArray();

    var json = {};
    var newName;
    var parentName;
    var newVal;
    var userContactInfo = {};
    var clinicInfo = {};
    jQuery.each(array, function () {
        var name = this.name;
        if (name.indexOf(".") > -1) {
            parentName = name.split('.')[0];
            newName = name.split('.')[1];

            if (parentName == 'clinicInfo') {
                clinicInfo[newName] = this.value || '';
                newVal = clinicInfo;
            }
            if (parentName == 'userContactInfo') {
                userContactInfo[newName] = this.value || '';
                newVal = userContactInfo;
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

function ProviderRegistrationFormToJSON(form) {
    var json = {};
    var newName;
    var parentName;
    var newVal;
    var array = jQuery(form).serializeArray();
    jQuery.each(array, function () {

        var name = this.name;
        if (name.indexOf(".") > -1) {
            parentName = name.split('.')[0];
            newName = name.split('.')[1];
        }
        else {
            parentName = this.name;
            newVal = this.value;
        }
        json[parentName] = newVal || '';

    });
    return json;
}

function mobileActiveFormToJSON(form) {
    var array = jQuery(form).serializeArray();

    var json = {};
    var newName;
    var parentName;
    var newVal;

    jQuery.each(array, function () {
        var name = this.name;
        if (name.indexOf(".") > -1) {
            parentName = name.split('.')[0];
            newName = name.split('.')[1];
        }
        else {
            parentName = this.name;
            newVal = this.value;

        }
        json[parentName] = newVal || '';
    });

    return json;
}