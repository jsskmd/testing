$(document).ready(function () {
    $("#search_doctor").click(function () {
        $(this).addClass("show_border_clinic");
        $("#add_doctor").removeClass("show_border_clinic");
        $("#add_doctor_show").addClass("hide");
        $("#search_doctor_show").removeClass("hide");
        $('#confirmMessage').empty();
    });

    $("#add_doctor").click(function () {
        $(this).addClass("show_border_clinic");
        $("#search_doctor").removeClass("show_border_clinic");
        $("#search_doctor_show").addClass("hide");
        $("#add_doctor_show").removeClass("hide");
        $('#confirmMessage').empty();
    });

    $(".cancel-common").off().on('click', function (event) {
        $("body").css("overflow","auto");
        $('#confirmMessage').empty();
        var userId = $("#userId").text();
        getDoctors(userId);
        $("#clinicDoctorsPopup").removeClass("dailog-show");
        event.stopPropagation();
    });

});

$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $("#authencate_back").click(function () {
        $("#add_doctor_view").removeClass("hide");
        $("#authenticate_view").addClass("hide");
        $("#otp").val("");
        $("#otpclinicId").val("");
        $("#otpdoctorId").val("");
        $("#confirmMessage").empty();
    });

    $("#clinicDoctorsPopup").on('submit', '#clinicDoctorForm', function (e) {
        $("#clinicDoctorForm").find(".register-btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
        /*$('#confirmMessage,#errorMessage').empty();*/
        $("#clinicIdInput").val($("#userId").text());
        $.ajax({
            url: "api/user/register",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(DRegistrationFormToJSON('#clinicDoctorForm')),
            contentType: 'application/json',
            success: function (data) {
               /* var saveMessage = data['message'];
                var saveStatus = data['statusCode'];*/
                switch(data['statusCode']){
                    case 200:
                        $("form#clinicDoctorForm input").val("");
                        $("#userRole").val("ROLE_DOCTOR");
                        $('#confirmMessage').show().empty().prepend( data['message']).delay(5000).fadeOut(100, function () {
                            $('#confirmMessage').empty();
                        });
                        break;
                    case 406:
                        $('#errorMessage').show().empty().prepend( data['message']).delay(5000).fadeOut(100, function () {
                            $('#errorMessage').empty();
                        });
                }
                $("#clinicDoctorForm").find(".register-btn").html('Save');
            },
            error: function (data) {

            }
        });
        e.preventDefault();
        return false;
    });

    $("#clinicDoctorsPopup").on('submit', '#clinicDoctor_search', function (e) {
        e.preventDefault();
        $("#errorMessage,#confirmMessage").empty();
        $("#clinicDoctor_search").find(".submit_btn").html('<img src="resources/images/red_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
        $('#clinicDoctorsTable').dataTable({
            "bDestroy": true
        }).fnDestroy();
        $('#clinicDoctorsTbody').empty();
        $('#clinicDoctorsTable').dataTable({
            responsive: true,
            "pagingType": "full_numbers",
            "language": {
                "emptyTable": "Enter valid DataLife Id and mobile number to get result !"
            }
        });
        $('#confirmMessage').empty();
        var input = $("#searchInput").val();
        if (input != '') {
         var tr=   doctorSearchResults(input,"");
            doctorSearchResults(input,tr);
        } else {

        }

        e.preventDefault();
        return false;
    });

    $(document).on('submit', '#authenticate_form', function (e) {
        /*$('#confirmMessage,#errorMessage').empty();*/
        $.ajax({
            url: "api/user/clinic/addDoctorToClinic",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(AuthenticateFormJson('#authenticate_form')),
            contentType: 'application/json',
            success: function (data) {
                var saveMessage = data['message'];
                if (data['statusCode'] == 200) {
                    $("#authencate_back").trigger("click");
                    if (saveMessage != '') {

                        $('#confirmMessage').show().empty().prepend( data['message']).delay(5000).fadeOut(100, function () {
                            $('#confirmMessage').empty();
                        });
                    }
                } else {
                    $('#errorMessage').show().empty().prepend( data['message']).delay(5000).fadeOut(100, function () {
                        $('#errorMessage').empty();
                    });
                }
                $("#load").hide();
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
        e.preventDefault();
        return false;
    });

});

function DRegistrationFormToJSON(form) {
    var array = jQuery(form).serializeArray();

    var json = {};
    var newName;
    var parentName;
    var newVal;
    var userDetails = {};
    var userContactInfo = {};
    var doctorInfo = {};
    jQuery.each(array, function () {
        var name = this.name;
        if (name.indexOf(".") > -1) {
            parentName = name.split('.')[0];
            newName = name.split('.')[1];
            if (parentName == 'userDetails') {
                userDetails[newName] = this.value || '';
                newVal = userDetails;
            }
            if (parentName == 'doctorInfo') {
                doctorInfo[newName] = this.value || '';
                newVal = doctorInfo;
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

function getUserOTP(doctorId) {
    $("a#"+doctorId).html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
    $('#confirmMessage').empty();
    var clinicId = $("#userId").text();
    var url = "api/user/clinic/addDoctorToClinicAuthenticate";
    var jsondata = {"doctorId": doctorId, "clinicId": clinicId};
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
                $("#add_doctor_view").addClass("hide");
                $("#authenticate_view").removeClass("hide");
                $("#otp").val("");

                $("#otpclinicId").val(clinicId);
                $("#otpdoctorId").val(doctorId);

            } else if (statusCode == 406) {
                $('#errorMessage').show().empty().prepend( data['message']).delay(5000).fadeOut(100, function () {
                    $('#errorMessage').empty();
                });

            } else {

            }
            $("a#"+doctorId).html('Add');
        },
        error: function (data) {
            window.location = 'invalidSession';
        }
    });
}

function AuthenticateFormJson(form) {
    var array = jQuery(form).serializeArray();
    var json = {};
    var newName;
    var parentName;
    var newVal;
    var authentication = {};
    var userContactInfo = {};
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
function doctorSearchResults(input,tr){

    if(tr!="") {

    }else {
        $.ajax({
            url: "api/user/clinic/searchDoctors",
            type: 'POST',
            dataType: 'json',
            data: input,
            contentType: 'application/json',
            success: function (data) {
                var users = data['users'];
                var saveStatus = data['statusCode'];
                if (data['statusCode'] == 200) {
                    var tr = "";
                    $.each(users, function (idx, obj) {
                        tr += '<tr style="text-align:center;"><td>' + obj['userId'] + '</td>' +
                            '<td style="text-align:center;">' + obj['flname'] + '</td>' +
                            '<td style="text-align:center;">' + obj['mobileNo'] + ' </a></td>' +
                            '<td style="text-align:center;"><a onclick="getUserOTP(' + obj['userId'] + ')" id='+obj['userId']+'>Add</a></td></tr>';

                    });
                    if (tr != null && tr != '' && tr != 'undefined') {
                        $('#clinicDoctorsTable').dataTable({
                            "bDestroy": true
                        }).fnDestroy();
                        $('#clinicDoctorsTbody').empty().html(tr);
                        $('#clinicDoctorsTable').dataTable({
                            responsive: true,
                            "pagingType": "full_numbers",
                            "language": {
                                "emptyTable": "Enter valid DataLife Id and mobile number to get result !"
                            }
                        });

                    } else {
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
                    }

                } else {
                    $('#errorMessage').show().empty().prepend( data['message']).delay(5000).fadeOut(100, function () {
                        $('#errorMessage').empty();
                    });
                }
                $("#clinicDoctor_search").find(".submit_btn").html('Search');
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    }

    return tr;
}