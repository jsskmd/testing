
$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $('#clinic_doctors').on('click',function () {
        $("body").css("overflow", "hidden");
        $(this).addClass('activedrpt');
        $("#clinic_patients,#clinic_settings").removeClass('activedrpt');
        $("#clinicDoctorsPopup").load("clinic/clinicDoctorPopup", function () {

            $('#clinicDoctorsTable').dataTable({
                "bDestroy": true
            }).fnDestroy();

            $('#clinicDoctorsTable').dataTable({
                responsive: true,
                "pagingType": "full_numbers",
                "language": {
                    "emptyTable": "Enter valid DataLife Id and mobile number to get result !"
                }
            });
            $(this).addClass("dailog-show");
        })
    });

    $('#clinic_patients').on('click',function () {
        $("body").css("overflow", "hidden");
        var id = $(this).attr('name');
        $(this).addClass('activedrpt');
        $("#clinic_doctors,#clinic_settings").removeClass('activedrpt');
        $("#ajaxloaddiv").load("clinic/clinicpatientsPopup", function () {
            $('#clinicPatientsTable').dataTable({
                "bDestroy": true
            }).fnDestroy();
            $('#clinicPatientsTable').dataTable({
                responsive: true,
                "pagingType": "full_numbers",
                "language": {
                    "emptyTable": "Enter valid DataLife Id and mobile number to get result !"
                }
            });
           /* $(this).addClass("dailog-show");*/
            $("#"+id).trigger('click');
        })
    });

    $("#clinic_home").click(function () {
        var userId = $("#userId").text();
        getDoctors(userId);
    });

    $("#clinic_settings").on('click',function(e){
        var userId = $("#userId").text();
        $(this).addClass('activedrpt');
        $("#clinic_patients,#clinic_doctors").removeClass('activedrpt');
        $.ajax({
            url: "api/user/clinic/getPreference",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(userId),
            contentType: 'application/json',
            success: function (data) {
                var status = data['statusCode'];
                switch(status){
                    case 200 :
                        $("#ajaxloaddiv").addClass('hide');
                        $("#ajaxload").load("clinic/preferrence",function(){
                            $("#labClinicId").val(userId);
                            $("#pharmClinicId").val(userId);
                            var lab = data['lab'];
                            var pharmacy = data['pharmacy'];

                            if( lab && pharmacy){
                                appendLab(lab);
                                appendPharmacy(pharmacy);
                                $("#readPharmacy,#readLab,#pedit,#changePharmPreferences,#changeLabPreferences,#cancelwmode").removeClass('hide');

                            }else{
                                if(lab){
                                    appendLab(lab);
                                    $("#readLab,#ledit,#changeLabPreferences,#cancelwmode,#pharmacyPreferences").removeClass('hide');
                                }
                                if(pharmacy){
                                    appendPharmacy(pharmacy);
                                    $("#readPharmacy,#pedit,#changePharmPreferences,#cancelwmode,#labPreferences").removeClass('hide');
                                }

                            }
                        }).removeClass('hide');
                        break;
                    case 100:
                        $("#ajaxloaddiv").addClass('hide');
                        $("#ajaxload").load("clinic/preferrence",function() {
                            $("#labClinicId").val(userId);
                            $("#pharmClinicId").val(userId);
                            $("#readmode").addClass('hide');
                            $("#writemode,#labPreferences,#pharmacyPreferences").removeClass('hide');

                        }).removeClass('hide');
                        break;
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
        e.preventDefault();
    });


    $("#clinicProfile,#sm_clinicProfile").click(function (e) {
        var userId = $("#userId").text();
        $.ajax({
            url: "api/user/clinic/getProfile",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(userId),
            contentType: 'application/json',
            success: function (data) {
                var saveMessage = data['message'];
                if (data['statusCode'] == 200) {
                    $("#clinicProfilePopup").load("clinic/profile", function () {
                        $(this).addClass("dailog-show");
                       /* getClinicProfile(data, saveMessage);*/
                        appendClinicProfile(data, saveMessage);
                    });
                }
                $("#load").hide();

            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
        e.preventDefault();
    });
});

function getChangeSettings() {
    var doctorId = $("#clinicDoctorList").val();
    $("#doctor_id").text(doctorId);
    $("#doctor_name").text($("#clinicDoctorList").find("option:selected").text());
    getSettings(doctorId, $("#userId").text(), 'false');
    $("#show_settings").removeClass("hide");
    $("#change_settings").addClass("hide");
}

function getClinicProfile(data, message) {

    var uci = data['uci'];
    var ci = data['ci'];
    var u = data['user'];
    var country = data['countries'];

    getCountry(country, 't_country');

    if (u['userName'] != null && u['userName'] != '' && u['userName'] != 'undefined') {
        $('#userName').text(u['userName']);
    }

    if (u['userId'] != null && u['userId'] != '' && u['userId'] != 'undefined') {
        $('#clinicUserId').text(u['userId']);
    }

    if (ci['clinicName'] != null && ci['clinicName'] != '' && ci['clinicName'] != 'undefined') {
        $('#clinicUserName').text(ci['clinicName']);
        $('#t_clinicName').val(ci['clinicName']);
    }
    else {
        $('#clinicName').text("-");
    }
    if (ci['mlrNumber'] != null && ci['mlrNumber'] != '' && ci['mlrNumber'] != 'undefined') {
        $('#mrnNumber').text(ci['mlrNumber']);
        $('#t_mrnNumber').val(ci['mlrNumber']);
    }
    if (uci['city'] != null && uci['city'] != '' && uci['city'] != 'undefined') {
        $('#city').text(uci['city'] + ", ");
        $('#t_city').val(uci['city']);
    }
    else {
        $('#city').text("");
    }
    if (uci['address'] != null && uci['address'] != '' && uci['address'] != 'undefined') {
        $('#address').text(uci['address'] + ", ");
        $('#t_address').val(uci['address']);
    }
    else {
        $('#address').text("-");
    }
    if (uci['zipCode'] != null && uci['zipCode'] != '' && uci['zipCode'] != 'undefined') {
        $('#zipCode').text(uci['zipCode']);
        $('#t_zipCode').val(uci['zipCode']);
    }
    else {
        $('#zipCode').text("-");
    }
    if (uci['mobilePhone'] != null && uci['mobilePhone'] != '' && uci['mobilePhone'] != 'undefined') {
        $('#mobilePhone').text(uci['mobilePhone']);
        $('#phone').val(uci['mobilePhone']);
    }
    else {
        $('#mobilePhone').text("-");
    }
    if (uci['homePhone'] != null && uci['homePhone'] != '' && uci['homePhone'] != 'undefined') {
        $('#homePhone').text(uci['homePhone']);
        $('#t_homePhone').val(uci['homePhone']);
    }
    else {
        $('#homePhone').text("-");
    }
    if (uci['email'] != null && uci['email'] != '' && uci['email'] != 'undefined') {
        $('#email').text(uci['email']);
        $('#t_email').val(uci['email']);
    }
    else {
        $('#email').text("-");
    }
    if (uci['location'] != null && uci['location'] != '' && uci['location'] != 'undefined') {
        $('#location').text(uci['location'] + ", ");
        $('#t_location').val(uci['location']);
    }
    else {
        $('#location').text("");
    }

    if (uci['country'] != null && uci['country'] != 'undefined' && uci['country'] != "") {
        $('#t_country').val(uci['country']);
        var countryval = $('#t_country').find('option:selected').text();

        $('#country').text(countryval);
        if (uci['country'] == 2) {
            fetchStates(uci['country'], "t_state", uci['state']);
            $("#dt_state").removeClass("hide");
            $("#dt_stateInput").addClass("hide");
            $('#country').text(", " + countryval);
        }
        else {
            $("#dt_stateInput").removeClass("hide");
            $("#dt_state").addClass("hide");
            if (uci['otherState'] != '' && uci['otherState'] != 'undefined' && uci['otherState'] != null) {
                $('#state').text(uci['otherState']);
                $('#country').text(", " + countryval);
                $('#t_stateInput').val(uci['otherState']);
            }
            else {
                $('#state').text("");
            }
        }
    }
    else {
        $('#country').text("");
        $("#state").text("");
    }
    if (message != "" && message != undefined && message != null) {
        $("#view_clinicProfile").removeClass("hide");
        $("#edit_clinicProfile").addClass("hide");
    }
}

function CProfileFormToJSON(form) {
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
            if (parentName == 'userContactInfo') {
                userContactInfo[newName] = this.value || '';
                newVal = userContactInfo;
            }
            if (parentName == 'clinicInfo') {
                clinicInfo[newName] = this.value || '';
                newVal = clinicInfo;
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

function appendLab(lab){
    var labcontact = lab['user']['userContactInfo'];
    var div ='<li class="padding-list-icon"><span class="wcp">Name</span><span> <span class="index-span"> : </span><span id="labName">'+lab['name']+'</span></span></li>'+
        '<li class="padding-list-icon"><span class="wcp">Contact Person</span><span> <span class="index-span"> : </span><span id="contactperson">'+lab['contactPerson']+'</span></span></li>'+
        '<li class="padding-list-icon"><span class="wcp">Address</span><span><span class="index-span"> : </span>'+
        '<span id="address">'+labcontact['address']+'</span> <span id="location">'+labcontact['location']+'</span> <span id="city">'+labcontact['city']+'</span>-&nbsp;';
    /* if(labcontact['state']){
     div += '<span id="state">'+lab['state']+'</span>';
     }
     if(labcontact['country']){
     div += '<span> <span  id="country">'+labcontact['country']+'</span>-&nbsp;';
     }*/
    if(labcontact['zipCode']){
        div +='<span id="zipCode">'+labcontact['zipCode']+'</span>';
    }
    div += '</span></span></li>';
    if(labcontact['email']) {
        div += '<li class="padding-list-icon"><span class="wcp">E-mail</span><span> <span class="index-span"> : </span><span id="email">'+labcontact['email']+'</span></span></li>';
    }
    if(labcontact['mobilePhone']) {
        div += '<li class="padding-list-icon"><span class="wcp">Mobile</span><span> <span class="index-span"> : </span><span id="mobilePhone">'+labcontact['mobilePhone']+'</span></span></li>';
    }
    if(labcontact['homePhone']){
        div += '<li class="padding-list-icon"><span class="wcp">Work Phone</span><span> <span class="index-span"> : </span><span id="homePhone">'+labcontact['homePhone']+'</span></span></li>'
    }
    $('#appendlab').empty().append(div);
    $('#setLab').text(lab['name']);
    $('#labId').val(lab['userId']);
}

function appendPharmacy(pharmacy){
    var pharmacycontact = pharmacy['user']['userContactInfo'];
    var ul = '<li class="padding-list-icon"><span class="wcp">Name</span><span> <span class="index-span"> : </span><span id="labName">'+pharmacy['name']+'</span></span></li>'+
        '<li class="padding-list-icon"><span class="wcp">Contact Person</span><span> <span class="index-span"> : </span><span id="contactperson">'+pharmacy['contactPerson']+'</span></span></li>'+
        '<li class="padding-list-icon"><span class="wcp">Address</span><span><span class="index-span"> : </span>'+
        '<span id="address">'+pharmacycontact['address']+'</span> <span id="location">'+pharmacycontact['location']+'</span> <span id="city">'+pharmacycontact['city']+'</span>';

    if(pharmacycontact['zipCode']){
        ul +='<span id="zipCode">'+pharmacycontact['zipCode']+'</span>';
    }
    ul += '</span></span></li>';
    if(pharmacycontact['email']) {
        ul += '<li class="padding-list-icon"><span class="wcp">E-mail</span><span> <span class="index-span"> : </span><span id="email">'+pharmacycontact['email']+'</span></span></li>';
    }
    if(pharmacycontact['mobilePhone']) {
        ul += '<li class="padding-list-icon"><span class="wcp">Mobile</span><span> <span class="index-span"> : </span><span id="mobilePhone">'+pharmacycontact['mobilePhone']+'</span></span></li>';
    }
    if(pharmacycontact['homePhone']){
        ul += '<li class="padding-list-icon"><span class="wcp">Work Phone</span><span> <span class="index-span"> : </span><span id="homePhone">'+pharmacycontact['homePhone']+'</span></span></li>'
    }
    $('#appendpharmacy').empty().append(ul);
    $('#setPharm').text(pharmacy['name']);
    $('#pharmId').val(pharmacy['userId']);
}

function getDoctors(userId) {
    $.ajax({
        url: "api/user/clinic/getDoctors",
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(userId),
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function (data) {

            var saveStatus = data['statusCode'];
            var settings = data['settings'];
            if (saveStatus == 200) {
                var tr = "";
                var doctors = data['doctors'];
                if (doctors != null && doctors != '' && doctors != 'undefined') {
                    $.each(data['doctors'], function (idx, obj) {
                        var speciality = "";
                        if (obj.speciality != null && obj.speciality != '' && obj.speciality != 'undefined') {
                            speciality = obj.speciality;
                        }
                        tr += ' <tr style="background-color:#fff;" >' +
                            '<td  style="background-color:#fff;"><img src="' + obj.sendType + '" alt="clinic-image" class="alt-text doctor_image">' +
                            '<div class="index-class">' +
                            '<p class="name">' + obj.userDetails.firstName + ' ' + obj.userDetails.lastName + '</p>' +
                            '<p class="specality-for-clinic">' + speciality + '</p>' +
                            '<p class="specality-for-clinic">Phone: ' + obj.userContactInfo.mobilePhone + '</p>' +
                            '<p class="specality-for-clinic">Email Id: ' + obj.userContactInfo.email + '</p></div></td>' +
                            '<td style="vertical-align:middle"><button class="View  " type="button" id="view-status" onclick="getAppointment(' + obj.userId + ',' + userId + ');">View ' +
                            '</button><button  type="button" class="share" onclick="apptCancellationSetting(' + obj.userId + ',' + userId + ');">Cancel</button></td>' +
                            '<td style="vertical-align:middle"><button class="view-trash" type="button" onclick=getSettings(' + obj.userId + ',' + userId + ',"true");>' +
                            'Change Settings</button>';

                        if (!obj['activate']) {
                            tr += '<p title="To activate go to settings." style="font-weight: bold;color: red;">Deactivated </p></td></tr>';
                        }else{
                            tr += '</td></tr>';
                        }

                    });
                }

                $('#myclinicDoctorTable').dataTable({
                    "bDestroy": true
                }).fnDestroy();

                $('#mydoctors').empty().html(tr);
                $('#myclinicDoctorTable').dataTable({
                    responsive: true,
                    "pagingType": "full_numbers",
                    "language": {
                        "emptyTable": "No Doctors Found !!!"
                    }
                });

            } else {
                $('#successMessages').empty().prepend('<div class="notification alert-error spacer-t10">' +
                    '<p>' + data['message'] + '</p><span class="close-btn"></span></div>').children(':first').delay(15000).fadeOut(100, function () {
                    $('#successMessages').empty();
                });
            }
            $("#load").hide();
        },
        error: function (data) {
            window.location = 'invalidSession';
        }
    });
}