$(document).ready(function () {
    $("#search_patient").click(function () {
        /*$('#ppconfirmMessage,#pperrorMessage').empty();*/
        $(this).addClass("show_border_clinic");
        $("#add_patient,#patient_list").removeClass("show_border_clinic");
        $("#add_patient_show,#patient_list_show,#pauthenticate_view").addClass("hide");
        $("#search_patient_show,#add_patient_view").removeClass("hide");
    });

    $("#add_patient").click(function () {
        /*$('#ppconfirmMessage,#pperrorMessage').empty();*/
        $(this).addClass("show_border_clinic");
        $("#search_patient,#patient_list").removeClass("show_border_clinic");
        $("#search_patient_show,#patient_list_show").addClass("hide");
        $("#add_patient_show").removeClass("hide");
    });
});


$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });


    $("#pauthencate_back").click(function () {
        $("#patient_list_view,#add_patient_view").removeClass("hide");
        $("#pauthenticate_view").addClass("hide");
        $("#otp,#otpclinicId,#otppatientId").val("");
       /* $('#confirmMessage,#errorMessage').empty();*/

    });
    $("#passign_back").click(function () {
        $("#patient_list_view").removeClass("hide");
        $("#assign_view").addClass("hide");
        $("#assignDoctorId,#assignclinicId,#assignpatientId").val("");
        /*$('#ppconfirmMessage,#pperrorMessage').empty();*/
    });

    $("#clinicPatient_search").submit(function (e) {
        e.preventDefault();
       /* $('#ppconfirmMessage,#pperrorMessage').empty();*/
        $("#clinicPatient_search").find(".submit_btn").html('<img src="resources/images/red_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

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

        var userId = $("#userId").text();
        var cid = $("#select_clinic").val();
        var doctorId;
        var clinicId;
        if (cid != null && cid != 'undefined' && cid != '') {
            doctorId = userId;
            clinicId = cid;
        } else {
            clinicId = userId;
        }
        var input = $("#searchPatientInput").val();
        if (input != '') {
            var tr = patientSearchResults(input, doctorId, clinicId, "");
            patientSearchResults(input, doctorId, clinicId, tr);
        } else {

        }
        return false;
    });

    $("#patient_list").click(function () {

        /*$('#ppconfirmMessage,#pperrorMessage,#confirmMessage').empty();*/
        $(this).addClass("show_border_clinic");
        $("#search_patient,#add_patient").removeClass("show_border_clinic");
        $("#search_patient_show,#add_patient_show").addClass("hide");
        $("#patient_list_show").removeClass("hide");

        var userId = $("#userId").text();
        var cid = $("#select_clinic").val();
        var doctorId;
        var clinicId;
        if (cid != null && cid != 'undefined' && cid != '') {
            doctorId = userId;
            clinicId = cid;
        } else {
            clinicId = userId;
        }
        var jsonData = {"clinicId": clinicId, "doctorId": doctorId};
        $.ajax({
            url: "api/user/clinic/getPatients",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsonData),
            contentType: 'application/json',
            success: function (data) {
                var patients = data['patients'];
                if (data['statusCode'] == 200) {
                    var role = data['role'];
                    var tr = "";
                    if (role == 'clinic') {
                        $.each(patients, function (idx, obj) {
                            tr += '<tr style="text-align:center;"><td>' + obj['userId'] + '</td>' +
                                '<td style="text-align:center;">' + obj['flname'] + '</td>' +
                                '<td style="text-align:center;">' + obj['mobileNo'] + ' </a></td>' +
                                '<td style="text-align:center;">' + obj['name'] +
                                '<a title="Assign patient to doctor" onclick="assignDoctor(' + obj['userId'] + ')" class="view2" > Assign</a><a title="Patient dashboard" onclick="accesspatient(' + obj['userId'] + ')" class="view2 view3">Access</a></td></tr>';
                        });
                    } else {
                        $.each(patients, function (idx, obj) {
                            tr += '<tr style="text-align:center;"><td>' + obj['userId'] + '</td>' +
                                '<td style="text-align:center;">' + obj['flname'] + '</td>' +
                                '<td style="text-align:center;">' + obj['mobileNo'] + ' </a></td>' +
                            '<td style="text-align:center;"><a class="newconsultants" id="accessencounter" onclick="getEncounter(' + obj['userId'] + ',' + doctorId + ',' + clinicId + ',' + false + ')"> New Consultation</a></td>' +
                                '</tr>';
                        });
                    }


                    if (tr != null && tr != '' && tr != 'undefined') {
                        $('#clinicPatients').dataTable({
                            "bDestroy": true
                        }).fnDestroy();
                        $('#clinicPatientsList').empty().append(tr);
                        $('#clinicPatients').dataTable({
                            "responsive": true,
                            "pagingType": "full_numbers",
                            "language": {
                                "emptyTable": "No patients added to the clinic !!!"
                            }
                        });

                    }
                    else {
                        $('#clinicPatients').dataTable({
                            "bDestroy": true
                        }).fnDestroy();
                        $('#clinicPatients').dataTable({
                            "responsive": true,
                            "pagingType": "full_numbers",
                            "language": {
                                "emptyTable": "No patients added to the clinic !!!"
                            }
                        });
                    }

                } else {
                    $('#clinicPatients').dataTable({
                        "bDestroy": true
                    }).fnDestroy();
                    $('#clinicPatientsList').empty();
                    $('#clinicPatients').dataTable({
                        "responsive": true,
                        "pagingType": "full_numbers",
                        "language": {
                            "emptyTable": "No patients added to the clinic !!!"
                        }
                    });

                }
                $("#load").hide();
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });


        $("#basicinformation").submit(function (e) {
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

                    if (data['statusCode'] == 200) {
                        getBasicInfo(data);
                    }
                    else {
                        $('#messages').prepend('<div class="notification alert-error spacer-t10">' +
                            '<p>' + data['message'] + '</p><span class="close-btn"></span></div>').children(':first').delay(15000).fadeOut(100);
                    }
                    $("#basicinformation").find(".submit_btn").html('Processed');

                },
                error: function (data) {
                    window.location = 'invalidSession';
                }
            });

        });

    });

    $(document).on('submit', '#pauthenticate_form', function (e) {
        e.preventDefault();
        /*$('#ppconfirmMessage,#pperrorMessage').empty();*/

        $.ajax({
            url: "api/user/clinic/addPatientToClinic",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(AuthenticateFormJson('#pauthenticate_form')),
            contentType: 'application/json',
            success: function (data) {
                var saveMessage = data['message'];
                var saveStatus = data['statusCode'];
                if (saveStatus == 200) {
                    $("#pauthencate_back").trigger("click");
                    $('#ppconfirmMessage').empty().removeClass('hide').show().prepend(saveMessage).delay(5000).fadeOut(100, function () {
                        $('#ppconfirmMessage').empty();
                    });
                } else {
                    $('#pperrorMessage').removeClass('hide').show().empty().prepend(saveMessage).delay(5000).fadeOut(100, function () {
                        $('#pperrorMessage').empty();
                    });
                }
                /*$("#load").hide();*/
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#add_patient_show").on('submit', '#clinicPatientForm', function (e) {

        $("#clinicPatientForm").find(".register-btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
/*        $('#ppconfirmMessage,#pperrorMessage').empty();*/
        var userId = $("#userId").text();
        var cid = $("#select_clinic").val();
        var doctorId;
        if (cid != null && cid != 'undefined' && cid != '') {
            doctorId = userId;
            userId = cid;
        }
        $("#clinicIdInput").val(userId);
        $("#doctorIdInput").val(doctorId);
        $.ajax({
            url: "api/user/register",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(PRegistrationFormToJSON('#clinicPatientForm')),
            contentType: 'application/json',
            success: function (data) {
                switch(data['statusCode']){
                    case 200:
                        $("form#clinicPatientForm input").val("");
                        $("#userRole").val("ROLE_PATIENT");
                        $('#ppconfirmMessage').empty().removeClass('hide').show().prepend(data['message']).delay(1500).fadeOut(100, function () {
                            $('#ppconfirmMessage').empty();
                        });

                        break;
                    case 406:
                        $('#pperrorMessage').removeClass('hide').show().empty().prepend(data['message']).delay(1500).fadeOut(100, function () {
                            $('#pperrorMessage').empty();
                        });
                        break;
                }
                $("#clinicPatientForm").find(".register-btn").html('Save');
            },
            error: function (data) {

            }
        });
        e.preventDefault();
    });


    $(document).on('submit', '#passign_form', function (e) {
        $("#passign_form").find(".submit_btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

        /*$('#ppconfirmMessage,#pperrorMessage').empty();*/
        $.ajax({
            url: "api/user/clinic/assignPatient",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(AuthenticateFormJson('#passign_form')),
            contentType: 'application/json',
            success: function (data) {
                var saveMessage = data['message'];
                if (data['statusCode'] == 200) {
                    $("#passign_back,#patient_list").trigger("click");

                    if (saveMessage != '') {
                        $('#ppconfirmMessage').empty().removeClass('hide').show().prepend(data['message']).delay(1500).fadeOut(100, function () {
                            $('#ppconfirmMessage').empty();
                        });
                    }
                } else {
                    $('#pperrorMessage').removeClass('hide').show().empty().prepend(data['message']).delay(1500).fadeOut(100, function () {
                        $('#pperrorMessage').empty();
                    });
                }
                $("#passign_form").find(".submit_btn").html('Assign');
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
        e.preventDefault();
        return false;
    });

});

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

function getUserOTP(patientId) {
    $("a#" + patientId).html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
    /*$('#ppconfirmMessage,#pperrorMessage').empty().show();*/
    var clinicId = $("#userId").text();
    var doctorId;
    var cid = $("#select_clinic").val();
    if (cid != null && cid != 'undefined' && cid != '') {
        clinicId = cid;
        doctorId = $("#userId").text();
    }
    var jsondata = {"patientId": patientId, "clinicId": clinicId};
    var url = "api/user/clinic/addPatientToClinicAuthenticate";
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
                $("#add_patient_view").addClass("hide");
                $("#pauthenticate_view").removeClass("hide");
                $("#otp").val("");
                $("#otpclinicId").val(clinicId);
                $("#otpdoctorId").val(doctorId);
                $("#otppatientId").val(patientId);

            } else if (statusCode == 406) {

                $('#pperrorMessage').removeClass('hide').show().prepend(data['message']).delay(1500).fadeOut(100, function () {
                    $('#pperrorMessage').empty();
                });
            }
            $("a#" + patientId).html('Add');
        },
        error: function (data) {
            window.location = 'invalidSession';
        }
    });
}

function accesspatient(patientId) {
    $("#accesspatientId").val(patientId);
    $("#access_clinicpatient").load("patient/access", function () {
        $("#cp_patientId").val(patientId);
        $("#recordUserId").val(patientId);
        $("#at_userId").val(patientId);
        $("#cp_profile").load("patient/profile",function(){
            $("#a_basicDetails").trigger('click');
        });
        /* var url = "api/user/patient/basicInfo";
         var jsondata = {"userId": patientId};
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
         $("#at_userId").val(patientId);
         getBasicInfo(data);
         }
         },
         error: function (data) {
         window.location = 'invalidSession';
         }
         });*/
    });

    $("#clinic_accesspatient").addClass("dailog-show");
    $("body").css("overflow", "hidden");

    /* var clinicId = $("#userId").text();*/
}

function patientSearchResults(input, doctorId, clinicId, tr) {
    if (tr != "") {

    } else {
        $.ajax({
            url: "api/user/clinic/searchPatients",
            type: 'POST',
            dataType: 'json',
            data: input,
            contentType: 'application/json',
            success: function (data) {
                var users = data['users'];
                var statusCode = data['statusCode'];
                switch (statusCode){
                    case 200 :
                    $.each(users, function (idx, obj) {
                        if (doctorId != null && doctorId != 'undefined' && doctorId != '') {
                            tr += '<tr style="text-align:center;"><td>' + obj['userId'] + '</td>' +
                                '<td style="text-align:center;">' + obj['flname'] + '</td>' +
                                '<td style="text-align:center;">' + obj['mobileNo'] + ' </a></td>' +
                                '<td style="text-align:center;"><a class="register-btn newconsultants" onclick="getUserOTP(' + obj['userId'] + ')" id=' + obj['userId'] + '>Add</a></td>' +
                                '<td style="text-align:center;"><a class="newconsultants" onclick="getEncounterAccess(' + obj['userId'] + ',' + doctorId + ',' + clinicId + ')" id="get' + obj['userId'] + '">New Consultation</a></td>' +
                                '</tr>';
                        } else {
                            tr += '<tr style="text-align:center;"><td>' + obj['userId'] + '</td>' +
                                '<td style="text-align:center;">' + obj['flname'] + '</td>' +
                                '<td style="text-align:center;">' + obj['mobileNo'] + ' </a></td>' +
                                '<td style="text-align:center;"><a class="newconsultants" onclick="getUserOTP(' + obj['userId'] + ')" id=' + obj['userId'] + '>Add</a></td></tr>';
                        }
                    });

                        $('#clinicPatientsTable').dataTable({
                            "bDestroy": true
                        }).fnDestroy();
                        $('#clinicPatientsTbody').empty().html(tr);
                        $('#clinicPatientsTable').dataTable({
                            responsive: true,
                            "pagingType": "full_numbers",
                            "language": {
                                "emptyTable": "Enter valid DataLife Id and mobile number to get result !"
                            },
                            "autoWidth": false,
                            "paging":   false,
                            "ordering": false,
                            "info":     false,
                            "searching": false,
                            "order": []
                        });
                        break;

                    case 100:

                        $('#clinicPatientsTable').dataTable({
                            "bDestroy": true
                        }).fnDestroy();
                        $('#clinicPatientsTbody').empty();
                        $('#clinicPatientsTable').dataTable({
                            responsive: true,
                            "pagingType": "full_numbers",
                            "language": {
                                "emptyTable": "Enter valid DataLife Id and mobile number to get result !"
                            },
                            "paging":   false,
                            "ordering": false,
                            "info":     false,
                            "searching": false,
                            "order": []
                        });
                        break;
                }
                $("#cliniPatientsDiv").removeClass("hide");
                $("#clinicPatient_search").find(".submit_btn").html('Search');

            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    }
    return tr;
}

function assignDoctor(patientId) {
   /* $("#pperrorMessage,#ppconfirmMessage").empty();*/
    var clinicId = $("#userId").text();

    $.ajax({
        url: "api/user/clinic/getDoctorsFromClinic",
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(clinicId),
        contentType: 'application/json',
        mimeType: 'application/json',

        success: function (data) {

            $("#patient_list_view").addClass("hide");
            $("#assign_view").removeClass("hide");

            $("#assignDoctorId").empty().append('<option value="" disabled selected>Select Doctor</option>');

            $.each(data, function (idx, obj) {
                $("#assignDoctorId").append($("<option></option>")
                    .attr("value", idx)
                    .text(obj.toString()));


            });

            $("#assignclinicId").val(clinicId);
            $("#assignpatientId").val(patientId);


            $("#load").hide();
        },
        error: function (data) {
            window.location = 'invalidSession';
        }
    });
}