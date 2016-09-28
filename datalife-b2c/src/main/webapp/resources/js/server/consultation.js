$(".cancel-common").off().on('click', function (event) {
    $("#access_encounter").removeClass("dailog-show");
    event.stopPropagation();
});

$(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $(document).on('submit', '#get_encounter_form', function (e) {
        $("#get_encounter_form").find(".submit_btn").html('<img src="resources/images/red_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

        $('#perrorMessage').empty();
        $.ajax({
            url: "api/user/doctor/accessPatient",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(AuthenticateFormJson('#get_encounter_form')),
            contentType: 'application/json',
            success: function (data) {
                if (data['statusCode'] == 200) {
                    getEncounter($("#dp_patientId").val(), $("#dp_doctorId").val(), $("#dp_clinicId").val(), false);
                } else {
                    $('#perrorMessage').show().empty().prepend(data['message']).delay(3000).fadeOut(100, function () {
                        $('#perrorMessage').empty();
                    });
                }
                $("#get_encounter_form").find(".submit_btn").html('Authenticate');
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
        e.preventDefault();
        return false;
    });

    $(document).on('submit', '#physicianPatientForm', function (e) {
        $("#physicianPatientForm").find(".submit_btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
        $('#confirmMessage,#errorMessage,#pconfirmMessage,#perrorMessage').empty();

        $.ajax({
            url: "api/user/register",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(PRegistrationFormToJSON('#physicianPatientForm')),
            contentType: 'application/json',
            success: function (data) {
                var statusCode = data['statusCode'];
                if (statusCode == 202) {
                    $('#confirmMessage,#pconfirmMessage').show().empty().text(data['message']).delay(1500).fadeOut(100, function () {
                        $('#confirmMessage,#pconfirmMessage').empty();
                    });
                    $("form#physicianPatientForm input").val("");
                } else {
                    $('#errorMessage#perrorMessage').show().empty().prepend(data['message']).delay(1500).fadeOut(100, function () {
                        $('#errorMessage,#perrorMessage').empty();
                    });
                }
                $("#physicianPatientForm").find(".submit_btn").html('Save');
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
        e.preventDefault();
        return false;
    });

    $("#getConsultation").click(function () {
        getEncounter($("#patDtlsId").text(), $("#userId").text(), $("#select_clinic").val(), true);
    });


    $("#physicianPatient_search").submit(function (e) {
        $("#physicianPatient_search").find(".submit_btn").html('<img src="resources/images/red_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

        $('#clinicPatientsTable').dataTable({
            "bDestroy": true
        }).fnDestroy();

        $('#clinicPatientsTbody').empty();
        $('#clinicPatientsTable').dataTable({
            responsive: true,
            "pagingType": "full_numbers",
            "language": {
                "emptyTable": "Enter valid search field to get result !"
            }
        });
        var doctorId = $("#userId").text();
        var input = $("#searchPatientInput").val();
        $.ajax({
            url: "api/user/clinic/searchPatients",
            type: 'POST',
            dataType: 'json',
            data: input,
            contentType: 'application/json',
            success: function (data) {
                var users = data['users'];
                if (data['statusCode'] == 200) {
                    var tr = "";
                    $.each(users, function (idx, obj) {

                        tr += '<tr style="text-align:center;"><td>' + obj['userId'] + '</td>' +
                            '<td style="text-align:center;">' + obj['flname'] + '</td>' +
                            '<td style="text-align:center;">' + obj['mobileNo'] + ' </a></td>' +
                            '<td style="text-align:center;"><a onclick="getEncounterAccess(' + obj['userId'] + ',' + doctorId + ',' + null + ',' + 1 + ')">New Consultation</a></td>' +
                            '</tr>';
                    });

                    $('#clinicPatientsTable').dataTable({
                        "bDestroy": true
                    }).fnDestroy();

                    $('#clinicPatientsTbody').empty().html(tr);
                    $('#clinicPatientsTable').dataTable({
                        responsive: true,
                        "pagingType": "full_numbers",
                        "language": {
                            "emptyTable": "Enter valid search field to get result !"
                        }
                    });
                    $("#cliniPatientsDiv").removeClass("hide");
                } else {
                    $('#errorMessage').show().empty().prepend(data['message']);

                }
                $("#physicianPatient_search").find(".submit_btn").html('Search');
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });

        e.preventDefault();
        return false;
    });
    $("#refDoctorPatient_search").submit(function (e) {
        e.preventDefault();
        $("#refDoctorPatient_search").find(".submit_btn").html('<img src="resources/images/red_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

        $('#clinicPatientsTable').dataTable({
            "bDestroy": true
        }).fnDestroy();

        $('#clinicPatientsTbody').empty();
        $('#clinicPatientsTable').dataTable({
            responsive: true,
            "pagingType": "full_numbers",
            "language": {
                "emptyTable": "Enter valid search field to get result !"
            }
        });
        var doctorId = $("#userId").text();
        var input = $("#searchPatientInput").val();
        $.ajax({
            url: "api/user/clinic/searchPatients",
            type: 'POST',
            dataType: 'json',
            data: input,
            contentType: 'application/json',
            success: function (data) {
                var users = data['users'];
                if (data['statusCode'] == 200) {
                    var tr = "";
                    $.each(users, function (idx, obj) {

                        tr += '<tr style="text-align:center;"><td>' + obj['userId'] + '</td>' +
                            '<td style="text-align:center;">' + obj['flname'] + '</td>' +
                            '<td style="text-align:center;">' + obj['mobileNo'] + ' </a></td>' +
                            '<td style="text-align:center;"><a onclick="getEncounter(' + obj['userId'] + ',' + doctorId + ',' + null + ',' + 0 + ')">New Consultation</a></td>' +
                            '</tr>';


                    });

                    $('#clinicPatientsTable').dataTable({
                        "bDestroy": true
                    }).fnDestroy();

                    $('#clinicPatientsTbody').empty().html(tr);
                    $('#clinicPatientsTable').dataTable({
                        responsive: true,
                        "pagingType": "full_numbers",
                        "language": {
                            "emptyTable": "Enter valid search field to get result !"
                        }
                    });

                    $("#cliniPatientsDiv").removeClass("hide");

                } else {
                    $('#errorMessage').show().empty().prepend(data['message']).delay(1500).fadeOut(100, function () {
                        $('#errorMessage').empty();
                    });

                }
                $("#physicianPatient_search").find(".submit_btn").html('Search');
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });

        e.preventDefault();
        return false;
    });
});
function AuthenticateFormJson(form) {
    var array = jQuery(form).serializeArray();
    var json = {};
    var newName;
    var parentName;
    var newVal;
    var authentication = {};
/*    var userContactInfo = {};*/
    jQuery.each(array, function () {
        var name = this.name;
        if (name.indexOf(".") > -1) {
            parentName = name.split('.')[0];
            newName = name.split('.')[1];
            if (parentName == 'authentication') {
                authentication[newName] = this.value || '';
                newVal = authentication;
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


/*function displayConsultation(patientId, doctorId, clinicId, verdict,data){
    $('#ajaxloaddiv').load("doctor/encounter", function () {
        if (clinicId == null || clinicId == '' || clinicId == 'undefined') {
            $("#showHPfollowupSlot,#followupdiv,#sharediv").hide();
        }
        $("#verdict").val(verdict);
        $("#patient_image,#hp_patient_image").attr("src", data['imageUrl']);
        $("#patient_id,#hp_patient_id").text(data['patientId']);
        $("#patient_name,#hp_patient_name").text(data['patientName']);
        var gender = data['gender'];
        if (gender != null && gender != '' && gender != 'undefined') {
            $("#patient_gender,#hp_patient_gender").text(" : " + data['gender']);
        } else {
            $("#patient_gender,#hp_patient_gender").text(" : -");
        }
        var dob = data['dob'];
        if (dob != null && dob != '' && dob != 'undefined') {
            $("#patient_dob,#hp_patient_dob").text(" : " + data['dob']);
            var age = CalculateAge(data['dob']);
            $("#patient_age,#hp_patient_age").text(age);
        } else {
            $("#patient_dob,#hp_patient_dob").text(" : -");
            $("#patient_age,#hp_patient_age").text("-");
        }

        $("#enc_age,#hp_enc_age").val(age);
        $("#encPatientId,#hp_encPatientId").val(data['patientId']);
        $("#encDoctorId,#hp_encDoctorId").val(data['doctorId']);
        $("#encClinicId,#hp_encClinicId").val(data['clinicId']);

       *//* getEncounterLabels1(data['rosCategories'],data['peCategories']);
        getlabdata1(data['labCategories'],data['hmtValues'],data['sgyValues'],data['cpgyValues'],data['cgyValues'],data['bcyValues'],data['mbgyValues'],data['hrtValues']);
        getHistory1(data['history']);*//*
        getEncounterLabels1(data);
        getlabdata1(data);
        getHistory1(data);
        var curdate = data['date'];
        var maxDateToshow = data['maxDateToShow'];

        $("#HPfollowup").datetimepicker({
            changeYear: true,
            changeMonth: true,
            timepicker: false,
            scrollMonth: true,
            minDate: curdate,
            maxDate: maxDateToshow,
            onSelect: function (selectedDate) {
                var option = this.id == "from" ? "minDate" : "maxDate",
                    instance = $(this).data("datepicker"),
                    date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
            },
            onSelectDate: function () {
                var date = $("#HPfollowup").val();
                fetchSlotOnDateHpfollowup(doctorId, clinicId, date);
            },

            format: 'd/m/Y'
        });

        $("#cdpspecialityImages").css("display", "none");
    });
}*/

function CalculateAge(birthday) {
    var y;
    var m;
    var d;
    var re = /^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d+$/;

    if (birthday != '') {

        if (re.test(birthday)) {
            var birthdayDate = Date.parseDate(birthday, "d/m/Y");
            var dateNow = new Date();
            var years = dateNow.getFullYear() - birthdayDate.getFullYear();
            var months = dateNow.getMonth() - birthdayDate.getMonth();
            var days = dateNow.getDate() - birthdayDate.getDate();
            if (months < 0) {
                years = years - 1;
                months = 12 - (months * -1);
            }
            if (days < 0) {
                months = months - 1;
                days = dateNow.getDaysInMonth() - (days * -1);
            }
            if (months < 0 || (months == 0 && days < 0)) {
                years = parseInt(years) - 1;
            }
            y = years + " years ";
            m = months + " months ";
            d = days + " days ";
            if (years == 1) {
                y = years + " year ";
            }
            if (months == 1) {
                m = months + " month ";
            }
            if (days == 1) {
                d = days + " day ";
            }

        }
    }
    return y + m + d;
}
function getEncounterAccess(patientId, doctorId, clinicId) {

    $("a#get" + patientId).html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

    var jsondata = {"patientId": patientId, "doctorId": doctorId, "clinicId": clinicId};
    var url = "api/user/clinic/validateClinicPatientExists";
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',
        mimeType: 'application/json',

        success: function (data) {
            var statusCode = data['statusCode'];

            if (statusCode == 200) {
                if (data['exists'] && !data['flag']) {
                    getEncounter(patientId, doctorId, clinicId, false);
                }else if(data['flag']){
                    $("#pperrorMessage").empty().prepend('<div id="errorMessage">' + data['message'] + '</div>').children(':first').delay(5000).fadeOut(100);
                } else {
                    var jsondata = {"patientId": patientId};
                    var url = "api/user/doctor/authenticate";
                    $.ajax({
                        url: url,
                        type: 'POST',
                        dataType: 'json',
                        data: JSON.stringify(jsondata),
                        contentType: 'application/json',
                        mimeType: 'application/json',

                        success: function (data) {
                            var statusCode = data['statusCode'];
                            if (statusCode == 200) {
                                $("#access_encounter").addClass("dailog-show");
                                $("#dp_otp").val("");
                                $("#dp_patientId").val(patientId);
                                $("#dp_doctorId").val(doctorId);
                                $("#dp_clinicId").val(clinicId);
                            }
                            $("a#get" + patientId).html('New Consultation');
                        },
                        error: function (data) {

                        }
                    });
                }
                $("a#get" + patientId).html('New Consultation');
            }
        },
        error: function (data) {
            window.location = 'invalidSession';
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

function getEncounter(patientId, doctorId, clinicId, verdict) {
    var url = "api/user/doctor/getEncounter";
    var jsondata = {"patientId": patientId, "doctorId": doctorId, "clinicId": clinicId};
    $("#accessencounter").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',
        mimeType: 'application/json',

        success: function (data) {
            var statusCode = data['statusCode'];
            switch (statusCode){
                case 200 :
                    $('#ajaxloaddiv').load("doctor/encounter", function () {
                        if (clinicId == null || clinicId == '' || clinicId == 'undefined') {
                            $("#showHPfollowupSlot,#followupdiv,#sharediv").hide();
                        }
                        $("#verdict").val(verdict);
                        $("#patient_image,#hp_patient_image").attr("src", data['imageUrl']);
                        $("#patient_id,#hp_patient_id").text(data['patientId']);
                        $("#patient_name,#hp_patient_name").text(data['patientName']);
                        var gender = data['gender'];
                        if (gender != null && gender != '' && gender != 'undefined') {
                            $("#patient_gender,#hp_patient_gender").text(" : " + data['gender']);
                        } else {
                            $("#patient_gender,#hp_patient_gender").text(" : -");
                        }
                        var dob = data['dob'];
                        if (dob != null && dob != '' && dob != 'undefined') {
                            $("#patient_dob,#hp_patient_dob").text(" : " + data['dob']);
                            var age = CalculateAge(data['dob']);
                            $("#patient_age,#hp_patient_age").text(age);
                        } else {
                            $("#patient_dob,#hp_patient_dob").text(" : -");
                            $("#patient_age,#hp_patient_age").text("-");
                        }

                        $("#enc_age,#hp_enc_age").val(age);
                        $("#encPatientId,#hp_encPatientId").val(data['patientId']);
                        $("#encDoctorId,#hp_encDoctorId").val(data['doctorId']);
                        $("#encClinicId,#hp_encClinicId").val(data['clinicId']);

                        getEncounterLabels(data['rosCategories'],data['peCategories']);
                        getlabdata(data['labCategories'],data['hmtValues'],data['sgyValues'],data['cpgyValues'],data['cgyValues'],data['bcyValues'],data['mbgyValues'],data['hrtValues']);
                        getHistory(data);
                        var curdate = data['date'];
                        var maxDateToshow = data['maxDateToShow'];

                        $("#HPfollowup").datetimepicker({
                            changeYear: true,
                            changeMonth: true,
                            timepicker: false,
                            scrollMonth: true,
                            minDate: curdate,
                            maxDate: maxDateToshow,
                            onSelect: function (selectedDate) {
                                var option = this.id == "from" ? "minDate" : "maxDate",
                                    instance = $(this).data("datepicker"),
                                    date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
                            },
                            onSelectDate: function () {
                                var date = $("#HPfollowup").val();
                                fetchSlotOnDateHpfollowup(doctorId, clinicId, date);
                            },
                            format: 'd/m/Y'
                        });
                        $("#cdpspecialityImages").css("display", "none");
                    });

                    $("#accessencounter").html('New Consultation');
                    break;
                case 100:
                    $("#access_encounter").removeClass("dailog-show");
                    $("#dp_otp").val("");
                    $("#pperrorMessage").empty().prepend('<div id="errorMessage">' + data['message'] + '</div>').children(':first').delay(5000).fadeOut(100);
                    $("#accessencounter").html('New Consultation');
                    break;
            }

        },
        error: function (data) {
            window.location = 'invalidSession';
        }
    });

}