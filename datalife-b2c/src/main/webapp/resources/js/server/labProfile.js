function checkDateofBirth(userName) {
    var selectedDate=$("#t_dateofBirth").datepicker('getDate');
    var now = new Date();
    var uName = document.getElementById("t_dateofBirth");
    if (selectedDate > now) {
        uName.setCustomValidity("Enter valid date of birth");
    }else{
        uName.setCustomValidity("");
    }

}

$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    $("#cancel-email,#cancel-otp,#cancel_verify,#cancel-authenticate").click(function () {
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



    $("#basicinformation").submit(function (e) {
        e.preventDefault();
        var url = "api/user/patient/basicInfo/save";
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(PDemoBasicFormToJSON('#basicinformation')),
            contentType: 'application/json',
            success: function (data) {
                /*var saveMessage = data['message'];
                 var saveStatus = data['statusCode'];*/
                if (data['statusCode'] == 200) {
                    getBasicInfo(data);
                }
                else {
                    $('#successMessages').prepend('<div class="notification alert-error spacer-t10">' +
                        '<p>' + data['message'] + '</p><span class="close-btn"></span></div>').children(':first').delay(1500000).fadeOut(100);
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });

        e.preventDefault();


    });
    $("#userContactInfo").submit(function (e) {
        e.preventDefault();
        var url = "api/user/patient/contactInfo/save";

        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(PDemoContactFormToJSON('#userContactInfo')),
            contentType: 'application/json',
            success: function (data) {
                var saveMessage = data['message'];
                if (data['statusCode'] == 200) {
                    getContactInfo(data);
                }
                else {
                    $('#successMessages').prepend('<div class="notification alert-error spacer-t10">' +
                        '<p>' + data['message'] + '</p><span class="close-btn" ></span></div>').children(':first').delay(15000).fadeOut(100,function(){
                        $('#successMessages').empty();
                    });
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#doctorInfo").submit(function (e) {
        e.preventDefault();
        var url = "api/user/doctor/professionalInfo/save";
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(PDemoProfessInfoFormToJSON('#doctorInfo')),
            contentType: 'application/json',
            success: function (data) {
                /*var saveMessage = data['message'];*/
                if (data['statusCode'] == 200) {
                    getDoctorInfo(data);

                }
                else {
                    $('#successMessages').prepend('<div class="notification alert-error spacer-t10">' +
                        '<p>' + data['message'] + '</p><span class="close-btn" ></span></div>').children(':first').delay(15000).fadeOut(100,function(){
                        $('#successMessages').empty();
                    });
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#a_basicDetails,#b_basicDetails").click(function (e) {

        var url = "api/user/patient/basicInfo";
        var userId = $("#userId").text();
        var jsondata = {"userId": userId};
        var saveMessage = '';
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

                    getBasicInfo(data);
                    $("#a_basicDetails").addClass("tmm-success");
                    $("#view_basicDetails").removeClass("hide");
                    $("#contactDetails,#professionaldetails,#basicDetails,#view_contactDetails,#view_professionaldetails").addClass("hide");
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });


        /*  tmm-current*/

    });


    $("#a_contactDetails,#b_contactDetails").click(function (e) {
        var url = "api/user/patient/contactInfo";
        var userId = $("#userId").text();
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
                    $("#a_contactDetails").addClass("tmm-success");
                    $("#view_contactDetails").removeClass("hide");
                    $("#contactDetails,#professionaldetails,#basicDetails,#view_basicDetails,#view_professionaldetails").addClass("hide");
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });

    });



    $("#a_professionaldetails,#b_professionaldetails").click(function (e) {

        var url = "api/user/doctor/professionalInfo";
        var userId = $("#userId").text();
        var jsondata = {"userId": userId};
        /* var saveMessage = '';*/
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
                    $("#c_userId").val(userId);
                    getDoctorInfo(data);
                    $("#a_professionaldetails").addClass("tmm-success");
                    $("#view_professionaldetails").removeClass("hide");
                    $("#contactDetails,#professionaldetails,#basicDetails,#view_basicDetails,#view_contactDetails").addClass("hide");
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });

    });

});

$(document).ready(function () {

    $("#cancelbasicinfo").click(function(e){
        $("#view_basicDetails").removeClass("hide");
        $("#contactDetails,#professionaldetails,#basicDetails,#view_contactDetails,#view_professionaldetails").addClass("hide");
    });
    $("#cancelcontactinfo").click(function(e){
        $("#view_contactDetails").removeClass("hide");
        $("#contactDetails,#basicDetails,#professionaldetails,#view_basicDetails,#view_professionaldetails").addClass("hide");
    });
    $("#cancelprofessionaldetails").click(function(e) {
        $("#view_professionaldetails").removeClass("hide");
        $("#contactDetails,#basicDetails,#view_basicDetails,#view_contactDetails,#professionaldetails").addClass("hide");
    });

    $("#editbasicinfo").click(function (e) {
        $("#basicDetails").removeClass("hide");
        $("#professionaldetails,#contactDetails,#view_basicDetails,#view_contactDetails,#view_professionaldetails").addClass("hide");
    });

    $("#editcontactinfo").click(function (e) {
        $("#contactDetails").removeClass("hide");
        $("#basicDetails,#professionaldetails,#view_basicDetails,#view_contactDetails,#view_professionaldetails").addClass("hide");

    });

    $("#editprofessionaldetails").click(function (e) {
        $("#professionaldetails").removeClass("hide");
        $("#contactDetails,#basicDetails,#view_basicDetails,#view_contactDetails,#view_professionaldetails").addClass("hide");
    });


    $("#t_dateofBirth").datepicker({
        dateFormat:"dd/mm/yy",
        maxDate: new Date()
    });

    $('input[type="checkbox"]').click(function () {

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

    });

    $('input[type="checkbox"]').click(function () {

        if ($(this).attr("value") == "another-emergency-chck") {

            $(".another-emergency").toggle();
            $(".form-wizard.another-emergency ").css('margin-top', '15px');

        }
        if ($(this).attr("value") == "t_another-emergency-chck") {

            $("#t_another-emergency").toggle();
            $(".form-wizard.another-emergency ").css('margin-top', '15px');

        }
    });

    $('input[type="checkbox"]').click(function () {

        if ($(this).attr("value") == "pharmacy-dtls-chck") {

            $(".pharmacy-dtls").toggle();
            $(".form-wizard.pharmacy-dtls ").css('margin-top', '15px');

        }


    });


});
/*function combo(countryId, stateId, stateInput) {
 var value = jQuery('#' + countryId + ' option:selected').val();

 $("#d" + stateId).addClass("hide");
 $("#d" + stateInput).addClass("hide");


 if (value == '2') {

 $("#d" + stateId).removeClass("hide");
 fetchStates(value, stateId);
 }
 else {

 $("#d" + stateInput).removeClass("hide");
 }
 }*/



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

function PDemoProfessInfoFormToJSON(form) {
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



