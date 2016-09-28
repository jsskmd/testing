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

function checkValidity(userName) {
    var selectedDate = $("#t_validity").datepicker('getDate');
    var now = new Date();
    var uName = document.getElementById("t_validity");
    if (selectedDate < now) {
        uName.setCustomValidity("Enter valid date");
    } else {
        uName.setCustomValidity("");
    }
}

$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    $('#dataLifeIdCard').on('click', function () {
        var userId = $("#cp_patientId").val();
        if (!userId) {
            userId = $("#userId").text();
        }
        var url = "api/user/patient/validateIDCardDetails";
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: userId,
            contentType: 'application/json',
            //important don't edit this line async is used to over come popup blocking in browser
            async: false,
            success: function (data) {
                switch (data['statusCode']) {
                    case 400 :
                        $('#errormessages').empty().removeClass().show().prepend('Fill mandatory fields in demographics to generate ID Card !').delay(5000).fadeOut(100, function () {
                            $('#errormessages').empty();
                        });
                        break;
                    case 200 :
                        window.open(window.location.href+"/patient/idCard/" + userId, "DataLife");
                        break;
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    /* $("#viewDataLifeId").click(function () {
     $("#datalifeIdCard").addClass("dailog-show");
     });*/
    /*$(".close-btndemo").click(function () {
        $('#messages').empty();
    });*/
    $("#cancel-email,#cancel-otp,#cancel_verify").click(function () {
        $("#verification").removeClass("dailog-show");
        $("#otpdiv").addClass("hide");
        $("#mobilediv").removeClass("hide");
    });

    $("#verify_mobile").click(function () {
        $("#verification").addClass("dailog-show");
        $('#verifyConfirmMessage,#verifyErrorMessage').empty();
        $("#emailBody,.smsplitcode,.sign_out,.cancel_verification").addClass("hide");
        $("#mobileBody,#cancel_verify").removeClass("hide");
    });
    $("#verify_email").click(function () {
        $("#verification").addClass("dailog-show");
        $('#verifyConfirmMessage,#verifyErrorMessage').empty();
        $("#emailBody,#cancel_verify").removeClass("hide");
        $("#mobileBody,.smsplitcode,.sign_out,.cancel_verification").addClass("hide");
    });

    $("#cancelbasicinfo").click(function () {
        $("#view_basicDetails").removeClass("hide");
        $("#contactDetails,#emergencyDetails,#preferences,#professionaldetails,#basicDetails,#view_contactDetails,#view_emergencyDetails,#view_preferences,#view_professionaldetails").addClass("hide");
    });
    $("#cancelcontactinfo").click(function () {
        $("#view_contactDetails").removeClass("hide");
        $("#contactDetails,#emergencyDetails,#preferences,#basicDetails,#professionaldetails,#view_basicDetails,#view_emergencyDetails,#view_preferences,#view_professionaldetails").addClass("hide");
    });
    $("#cancelemergencydetails,#cancelemergencydetailsone").click(function () {
        $("#view_emergencyDetails").removeClass("hide");
        $("#contactDetails,#emergencyDetails,#preferences,#basicDetails,#view_basicDetails,#view_contactDetails,#view_preferences").addClass("hide");
    });

    $("#canceledpreferences").click(function () {
        $("#view_preferences").removeClass("hide");
        $("#contactDetails,#emergencyDetails,#preferences,#basicDetails,#view_basicDetails,#view_contactDetails,#view_emergencyDetails").addClass("hide");
    });

    $("#canceladpreferences").click(function () {
        $("#view_preferences").removeClass("hide");
        $("#adpreference").addClass("hide");
        $("#contactDetails,#emergencyDetails,#preferences,#basicDetails,#view_basicDetails,#view_contactDetails,#view_emergencyDetails").addClass("hide");
    });

    $(".cancel-common,.resetbutton").click(function (event) {
        $("#identityUplaod").removeClass('dailog-show');
        $("#viewUploadIdImage").removeClass('dailog-show');
        $("#upload_id").removeClass("hide");
        $(".progress-extended").html("");
        event.stopPropagation();
    });
    $("#view_id_but").click(function (event) {
        $("#viewUploadIdImage").addClass('dailog-show');
        event.stopPropagation();
    });
    $('#upload_id_but').click(function (event) {

        var idtype = $("#t_idType").val();
        var idno = $("#t_idNumber").val();
        var idstate = $("#t_idcardstate").val();
        var idcountry = $("#t_idcardcountry").val();
        if( !idtype && !idno && !idstate && !idcountry){
            $('#idmessages').prepend('<div class="notification alert-error spacer-t10">' +
                '<p>Enter ID number,country and state, before uploading ID proof</p><span class="close-btn" ></span></div>').children(':first').delay(15000).fadeOut(100, function () {
                $('#idmessages').empty();
            });
        }else{
            $("#idtype").val(idtype);
            $("#idno").val(idno);
            $("#idcountry").val(idcountry);
            $("#idstate").val(idstate);
            $("tbody.files").empty();
            $('#errorMessage').empty();
            $("#identityUplaod").addClass('dailog-show');
        }

        event.stopPropagation();
    });

    $("#basicinformation").off().on("submit", function (e) {
        e.preventDefault();
        $("#basicinformation").find(".submit_btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

        var url = "api/user/patient/basicInfo/save";
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(PDemoBasicFormToJSON('#basicinformation')),
            contentType: 'application/json',
            success: function (data) {
                /*      var saveMessage = data['message'];
                 var saveStatus = data['statusCode'];*/
                if (data['statusCode'] == 200) {
                    getBasicInfo(data);
                }
                else {
                    $('#errormessages').empty().removeClass().show().prepend(data['message']).delay(15000).fadeOut(100);
                }
                $("#basicinformation").find(".submit_btn").html('Save');

            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });

    });

    $("#userContactInfo").off().on("submit", function (e) {
        e.preventDefault();
        $("#userContactInfo").find(".submit_btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
        var url = "api/user/patient/contactInfo/save";

        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(PDemoContactFormToJSON('#userContactInfo')),
            contentType: 'application/json',
            success: function (data) {
                /* var saveMessage = data['message'];*/
                if (data['statusCode'] == 200) {
                    getContactInfo(data);
                }
                else {
                    $('#errormessages').empty().removeClass().show().prepend(data['message']).delay(15000).fadeOut(100, function () {
                        $('#errormessages').empty();
                    });
                }
                $("#userContactInfo").find(".submit_btn").html('Save');

            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#userEmergencyInfo,#userEmergencyInfoOne").submit(function (e) {
        e.preventDefault();
        $("#userEmergencyInfo").find(".submit_btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
        var url = "api/user/patient/emergencyInfo/save";
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(PDemoEmergencyFormToJSON('#' + this.id)),
            contentType: 'application/json',
            success: function (data) {
                /*var saveMessage = data['message'];*/
                if (data['statusCode'] == 200) {
                    getEmergencyInfo(data);
                }
                else {
                    $('#errormessages').empty().removeClass().show().prepend( data['message']).delay(15000).fadeOut(100, function () {
                        $('#errormessages').empty();
                    });
                }
                $("#userEmergencyInfo").find(".submit_btn").html('Save');

            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#userpreferences").submit(function (e) {
        e.preventDefault();
        $("#userpreferences").find(".submit_btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

        var url = "api/user/patient/preferences/save";
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(PDemoPreferencesToJSON('#' + this.id)),
            contentType: 'application/json',
            success: function (data) {
                /*var saveMessage = data['message'];*/
                if (data['statusCode'] == 200) {
                    $("#a_preferences").click();
                    if (data['message'] == "Failure") {
                        $('#message').prepend('<p  align="center"  style="color:red; font-size: 140%" > Clinic/DoctorName, ContactNumber, and Address already Exists. </p>').children(':first').delay(5000).fadeOut(100, function () {
                            $('#message').empty();
                        });
                    }
                    else {
                        $('#message').prepend('<p align="center"  style="color:green; font-size: 140%" > Saved Successfully.</p>').children(':first').delay(5000).fadeOut(100, function () {
                            $('#message').empty();
                        });
                    }

                    /*getPreferences(data);*/
                }
                else {
                    $('#errormessages').empty().removeClass().show().prepend(data['message']).delay(15000).fadeOut(100, function () {
                        $('#errormessages').empty();
                    });
                }
                $("#userpreferences").find(".submit_btn").html('Save');
                document.getElementById("userpreferences").reset();

            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#a_basicDetails,#b_basicDetails").off().on('click', function (e) {
        e.preventDefault();
        var url = "api/user/patient/basicInfo";
        var userId = $("#userId").text();
        var id = $("#cp_patientId").val() || $("#accesspatientId").val();
        if (id != null && id != '' && id != 'undefined') {
            userId = id;
        }
        var jsondata = {"userId": userId};
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsondata),
            contentType: 'application/json',

            success: function (data) {
                var status = data['statusCode'];
                if (status == 400) {

                } else if (status == 200) {
                    getBasicInfo(data);
                    $("#view_basicDetails").removeClass("hide");
                    $("#contactDetails,#emergencyDetails,#preferences,#basicDetails,#view_contactDetails,#view_emergencyDetails,#view_preferences").addClass("hide");
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });


        /*  tmm-current*/

    });

    $("#a_contactDetails,#b_contactDetails").off().on('click', function () {
        var url = "api/user/patient/contactInfo";
        var userId = $("#userId").text();
        var id = $("#cp_patientId").val();
        if (id != null && id != '' && id != 'undefined') {
            userId = id;
        }
        var jsondata = {"userId": userId};
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsondata),
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                var status = data['statusCode'];
                var message = data['message'];
                if (status == 400) {
                    $('body').load("/");
                } else if (data['statusCode'] == 200) {
                    $("#pciUserId").val(userId);
                    getContactInfo(data);

                    $("#view_contactDetails").removeClass("hide");
                    $("#contactDetails,#emergencyDetails,#preferences,#basicDetails,#view_basicDetails,#view_emergencyDetails,#view_preferences").addClass("hide");
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });

    });

    $("#a_emergencyDetails,#b_emergencyDetails").click(function () {

        var url = "api/user/patient/emergencyInfo";
        var userId = $("#userId").text();
        var id = $("#cp_patientId").val();
        if (id != null && id != '' && id != 'undefined') {
            userId = id;
        }
        var jsondata = {"userId": userId};
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsondata),
            contentType: 'application/json',

            success: function (data) {

                var status = data['statusCode'];
                /*var saveMessage = data['message'];*/
                if (status == 400) {

                } else if (status == 200) {
                    $("#eduserId,#edtuserId").val(userId);
                    getEmergencyInfo(data);

                    $("#view_emergencyDetails").removeClass("hide");
                    $("#contactDetails,#emergencyDetails,#preferences,#basicDetails,#view_basicDetails,#view_contactDetails,#view_preferences").addClass("hide");
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });


    });

    $("#a_preferences,#b_preferences").click(function () {

        var url = "api/user/patient/preferences";
        var userId = $("#userId").text();
        var id = $("#cp_patientId").val();
        if (id != null && id != '' && id != 'undefined') {
            userId = id;
        }
        var jsondata = {"userId": userId};
        /* var saveMessage = '';*/
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsondata),
            contentType: 'application/json',
            success: function (data) {

                //alert(JSON.stringify(data));

                var status = data['statusCode'];
                /* var saveMessage = data['message'];*/

                if (status == 400) {

                } else if (status == 200) {
                    $(".preferencs_userId").val(userId);
                    $("#view_preferences").removeClass("hide");
                    $("#contactDetails,#emergencyDetails,#preferences,#basicDetails,#view_basicDetails,#view_contactDetails,#view_emergencyDetails").addClass("hide");
                    getPreferences(data);
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });


    });

});

$(document).ready(function () {

    var locationName = new Bloodhound({
        datumTokenizer: function (d) {
            return d.name;
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: 'common/%QUERY',
            replace: function (url, query) {
                var stateId = $("#t_ucistate").val();
                if (query == '') {
                    query = "getCities/" + stateId;
                    return url.replace("%QUERY", query);
                } else {
                    return url.replace("%QUERY", "getCitiesLike/" + stateId + "/" + query);
                }
            },
            filter: function (response) {
                return response;
            }
        },
        limit: 10
    });
    locationName.initialize();
    var quicksearchInput = $("#t_city");
    quicksearchInput.typeahead({
            hint: false,
            highlight: true,
            minLength: 0
        },
        {
            name: 'name',
            displayKey: 'name',
            source: locationName.ttAdapter(),
            templates: {
                empty: [].join('\n'),
                suggestion: Handlebars.compile('<div><strong>{{name}}</strong></div>')
            }
        });

    quicksearchInput.on("typeahead:opened", function () {
        var ev = $.Event("keydown");
        ev.keyCode = ev.which = 40;
        $(this).trigger(ev);
        return true
    });


    $("#editbasicinfo").click(function () {
        $("#basicDetails").removeClass("hide");
        $("#preferences,#contactDetails,#emergencyDetails,#view_basicDetails,#view_contactDetails,#view_emergencyDetails,#view_preferences").addClass("hide");
    });

    $("#editcontactinfo").click(function () {
        $("#contactDetails").removeClass("hide");
        $("#preferences,#basicDetails,#emergencyDetails,#view_basicDetails,#view_contactDetails,#view_emergencyDetails,#view_preferences").addClass("hide");

    });

    $("#editemergencydetails").click(function () {
        $("#emergencyDetails").removeClass("hide");
        $("#contactDetails,#preferences,#basicDetails,#view_basicDetails,#view_contactDetails,#view_emergencyDetails,#view_preferences").addClass("hide");
    });

    $("#editpreferences").click(function () {
        $("#preferences").removeClass("hide");
        $("#edpreferences").removeClass("hide");
        $("#adpreference").addClass("hide");
        $("#contactDetails,#emergencyDetails,#basicDetails,#view_basicDetails,#view_contactDetails,#view_emergencyDetails,#view_preferences").addClass("hide");
    });
    $("#addpreferences").click(function () {
        $("#preferences").removeClass("hide");
        $("#adpreference").removeClass("hide");
        $("#edpreferences").addClass("hide");
        $("#contactDetails,#emergencyDetails,#basicDetails,#view_basicDetails,#view_contactDetails,#view_emergencyDetails,#view_preferences").addClass("hide");
    });


    $("#t_dateofBirth").datepicker({
        dateFormat: "dd/mm/yy",
        maxDate: new Date()
    });

    $("#t_validity").datepicker({
        dateFormat: "dd/mm/yy",
        minDate: new Date()
    });

    $('input[type="checkbox"]').off().on('click', function () {
        if ($(this).attr("value") == "idcheckbox") {
            $(".iddetails ").toggle();
            $(".form-wizard.iddetails ").css('margin-top', '15px');
        }

        if ($(this).attr("value") == "incheckbox") {
            $(".insurancedetails").toggle();
            $(".form-wizard.insurancedetails").css('margin-top', '15px');
        }

        if ($(this).attr("value") == "t_idcheckbox") {
            $("#t_iddetails ").toggle();
            $(".form-wizard#t_iddetails ").css('margin-top', '15px');
        }

        if ($(this).attr("value") == "t_incheckbox") {
            $("#t_insurancedetails").toggle();
            $(".form-wizard#t_insurancedetails").css('margin-top', '15px');

        }

        if ($(this).attr("value") == "another-emergency-chck") {
            $(".another-emergency").toggle();
            $(".form-wizard.another-emergency ").css('margin-top', '15px');
        }
        if ($(this).attr("value") == "t_another-emergency-chck") {
            if (this.checked) {
                $("#uedother").removeClass('hide');
            } else {
                $("#uedother").addClass('hide');
            }
            $("#t_another-emergency").toggle();
            $(".form-wizard.another-emergency ").css('margin-top', '15px');
        }

        if ($(this).attr("value") == "pharmacy-dtls-chck") {
            $(".pharmacy-dtls").toggle();
            $(".form-wizard.pharmacy-dtls ").css('margin-top', '15px');
        }
        if ($(this).attr("value") == "t_pharmacy-dtls-chck") {

            $("#t_pharmacy-dtls").toggle();
            $(".form-wizard#t_pharmacy-dtls ").css('margin-top', '15px');

        }

    });


});


function combo(countryId, stateId, stateInput) {
    var value = jQuery('#' + countryId + ' option:selected').val();

    $("#d" + stateId).addClass("hide");
    $("#d" + stateInput).addClass("hide");

    /* if (value == '2') {*/

    $("#d" + stateId).removeClass("hide");
    fetchStates(value, stateId);
    /*}
     else {

     $("#d" + stateInput).removeClass("hide");
     }*/
}

function showHide(shID) {
    if (document.getElementById(shID)) {
        if (document.getElementById(shID + '-show').style.display != 'none') {
            document.getElementById(shID + '-show').style.display = 'none';
            document.getElementById(shID).style.display = 'block';
        }
        else {
            document.getElementById(shID + '-show').style.display = 'inline';
            document.getElementById(shID).style.display = 'none';
        }
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

function PDemoContactFormToJSON(form) {
    var array = jQuery(form).serializeArray();
    var json = {};
    var newName;
    var parentName;
    var newVal;
    var user = {};

    jQuery.each(array, function () {
        var name = this.name;
        if (name.indexOf(".") > -1) {
            parentName = name.split('.')[0];
            newName = name.split('.')[1];
            if (parentName == 'user') {
                user[newName] = this.value || '';
                newVal = user;
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

function PDemoEmergencyFormToJSON(form) {
    var array = jQuery(form).serializeArray();
    var json = [];
    var newName;
    var parentName;
    var innerjson = {};
    var user = {};
    var name;
    jQuery.each(array, function () {
        name = this.name;

        if (name.indexOf(".") > -1) {
            parentName = name.split('.')[0];
            newName = name.split('.')[1];
            if (parentName == 'user') {
                user[newName] = this.value || '';
                innerjson[parentName] = user || '';
                user = {};
            }
        } else {
            innerjson[name] = this.value || '';
        }
        if (name == 'homePhone') {
            json.push(innerjson);
            innerjson = {};
        }
    });
    return json;
}

function PDemoPreferencesToJSON(form) {
    var array = jQuery(form).serializeArray();
    var json = [];
    var newName;
    var parentName;
    var innerjson = {};
    var user = {};
    var name;
    jQuery.each(array, function () {
        name = this.name;

        if (name.indexOf(".") > -1) {
            parentName = name.split('.')[0];
            newName = name.split('.')[1];
            if (parentName == 'user') {
                user[newName] = this.value || '';
                innerjson[parentName] = user || '';
                user = {};
            }
        } else {
            innerjson[name] = this.value || '';
        }
        if (name == 'contactNumber') {
            json.push(innerjson);
            innerjson = {};
        }
    });
    return json;
}

