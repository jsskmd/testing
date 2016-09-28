
function closeSlot(count) {
    var slotCount = $("#slotCount").val();
    $("#mutipleSlots").children("#singleSlot" + count).remove();
    --slotCount;
    $("#slotCount").val(slotCount);
    $("#count").val(slotCount);
}

function ChangeUrl1(page, url) {
    if (typeof (history.pushState) != "undefined") {
        var obj = { Page: page, Url: url };
        history.pushState(obj, obj.Page, obj.Url);
    }
}

$(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $("#mobilehead").click(function () {
        $("#email").removeClass("show_border_clinic");
        $("#mobile").addClass("show_border_clinic");
        $('#verifyConfirmMessage,#verifyErrorMessage').empty();
        $('#verifyConfirmMessage,#verifyErrorMessage').addClass("hide");
        $("#emailBody").addClass("hide");
        $("#mobileBody").removeClass("hide");
    });
    $("#emailhead").click(function () {
        $("#email").addClass("show_border_clinic");
        $("#mobile").removeClass("show_border_clinic");
        $('#verifyConfirmMessage,#verifyErrorMessage').empty();
        $('#verifyConfirmMessage,#verifyErrorMessage').addClass("hide");
        $("#emailBody").removeClass("hide");
        $("#mobileBody").addClass("hide");
    });

    $(document).unbind('submit').on('submit', '#emailVerificationForm', function (e) {
        e.preventDefault();
        $('#verifyConfirmMessage,#verifyErrorMessage').addClass('hide');
        $("#emailVerificationForm").find(".submit_btn").html('<img src="resources/images/red_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
        var jsonData = {"email": $("#verfifyEmail").val(), "userId": $("#userId").text(),"role": $("#role").val()};
        $.ajax({
            url: "api/user/verifyEmailAddress",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsonData),
            contentType: 'application/json',
            success: function (data) {
                if (data['statusCode'] == 200) {
                    $('#verifyConfirmMessage').empty().text(data['message']).removeClass('hide');
                } else {
                    $('#verifyErrorMessage').empty().prepend(data['message']).removeClass('hide');
                }
                $("#emailVerificationForm").find(".submit_btn").html('Verify');
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
        e.preventDefault();
        return false;
    });

    $(document).on('submit', '#mobileVerificationForm', function (e) {
        e.preventDefault();
        $('#verifyConfirmMessage,#verifyErrorMessage,#confirmMessage').addClass("hide");
        $("#mobileVerificationForm").find(".submit_btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
        var jsonData = {"mobileNo": $("#verfifyMobile").val(), "userId": $("#userId").text(),"role": $("#role").val()};

        $.ajax({
            url: "api/user/verifyMobileNumber",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsonData),
            contentType: 'application/json',
            success: function (data) {
                var statusCode = data['statusCode'];
                switch (statusCode){
                    case 200:
                        $('#verifyConfirmMessage').empty().text(data['message']);
                        $("#mobilediv").addClass("hide");
                        $("#otpdiv").removeClass("hide");
                        break;
                    case 406:
                    $('#verifyErrorMessage').empty().prepend(data['message']).removeClass('hide');
                        break;
                }
                $("#mobileVerificationForm").find(".submit_btn").html('Send OTP');
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
        return false;
    });

    $("#logout,#logout1,#logout2,#logout3,#logout4").click(function () {
        var url = "api/user/logout";
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                $("body").load("/emr/login", function () {
                    document.getElementById('confirmMessage').style.display = 'block';
                    //document.getElementById('errorMessage').style.display = 'none';
                    $("#confirmMessage").text("You've logged out successfully.").delay(1500).fadeOut(100, function () {
                        $('#confirmMessage').empty();
                    });
                });
                ChangeUrl('page2', '/emr/login');
            },
            error: function (data) {

            }
        });
    });

    $(document).on('submit', '#otpVerificationForm', function (e) {
        e.preventDefault();
        $('#verifyConfirmMessage,#verifyErrorMessage').empty();
        $("#otpVerificationForm").find(".submit_btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
        var jsonData = {"otp": $("#verifyOtp").val(), "userId": $("#userId").text()};
        $.ajax({
            url: "api/user/verifyOTP",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsonData),
            contentType: 'application/json',
            success: function (data) {
                if (data['statusCode'] == 200) {
                    $('#verifyConfirmMessage').empty().text(data['message']);
                    $("#mobilediv").addClass("hide");
                    $("#otpdiv").removeClass("hide");
                    $("#ver_authenticate,#verifyOtp,#cancel-authenticate").hide();
                    $("#ver_refresh").show();
                } else {
                    $('#verifyErrorMessage').empty().prepend(data['message']);
                }
                $("#otpVerificationForm").find(".submit_btn").html('Authenticate');
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
        e.preventDefault();
        return false;
    });

    $('body').on('click', '#changeVisitSettings', function () {
        $("#show_settings").addClass("hide");
        $("#change_settings").removeClass("hide");
    });

    $('body').on('click', '.close-btndemo', function () {
        $("#successMessages").empty();
    });

    $('#getProfilePtientImage').click(function () {
        $("tbody.files").empty();
        var newSrc = $(this).attr("src");
        $('#perrorMessage').empty();
        $("#sampleimg").attr("src", newSrc);

        $("#imageuploadpatient").addClass('dailog-show');
        event.stopPropagation();
    });


    $(".cancel-close").click(function (event) {
        $("#imageuploadpatient").removeClass('dailog-show');
        $(".progress-extended").html("");
        event.stopPropagation();
    });

    $(".cancel-close1").click(function (event) {
        $("#appConfirmPopup").removeClass('dailog-show');
        $("#apptCancelCnfmPopup").removeClass('dailog-show');
        document.getElementById('cnfAppointment').reset();
        event.stopPropagation();
    });

    $("#viewAppt").on('click', function () {
        $("#viewAppt").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">Please wait..');
        $(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_").css("display", "none");
        viewAppointment();
    });

});
function viewAppointment() {
    var url = "api/user/patient/viewAppointment";
    var userId = $("#userId").text();
    var jsondata = {"userId": userId};
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',

        success: function (data) {
            if (data['statusCode'] == 200) {
                var appointmentList = data['appointmentList'];
                $('#ajaxloaddiv').load("patient/viewAppointment", function () {
                    var tr = "<tr style='text-align:center;'>";

                    if (appointmentList.length > 0) {
                        for (var i = 0; i < appointmentList.length; i++) {
                            var obj = appointmentList[i];
                            var date = obj['date'];
                            var time = obj['time'];

                            tr += "<td style='text-align:center;'>" + date + " " + time + "</td>";
                            tr += "<td style='text-align:center;'>" + obj['createdDateTime'] + "</td>";
                            tr += "<td style='text-align:center;'>" + obj['doctorName'] + "</td>";
                            tr += "<td style='text-align:center;'>" + obj['clinicName'] + "</td>";

                            if (obj['reasonForVisit'] != null && obj['reasonForVisit'] != "") {
                                tr += "<td style='text-align:center;'>" + obj['reasonForVisit'] + "</td>";
                            } else {
                                tr += "<td><span class='size'></span>" + '-' + "</td>";
                            }

                            if (obj['pastDate'] == true) {

                                if (obj['status'] == 'CANCELLED') {
                                    tr += "<td style='text-align:center;'>" + obj['status'] + "</td>";
                                } else {
                                    tr += "<td style='text-align:center;' id='" + date + "-" + time + "'>" +
                                        "<a onclick='confirmForCancelAppt(" + obj['patientId'] + "," + obj['clinicId'] + "," + obj['doctorId'] + ",this)'>" + obj['status'] + " (want to cancel ?)</a></td>";
                                }
                            } else if (obj['curDate']) {

                                if (obj['status'] == "SCHEDULED") {
                                    tr += "<td style='text-align:center;' id='" + date + "-" + time + "'>" +
                                        "<a  onclick='confirmForCancelAppt(" + obj['patientId'] + "," + obj['clinicId'] + "," + obj['doctorId'] + ",this)'>" + obj['status'] + " (want to cancel ?)</a></td>";
                                } else if (obj['status'] == "ARRIVED") {
                                    tr += "<td style='text-align:center;' class='ARRIVED' id='" + date + "-" + time + "'>" + obj['status'] + "</td>";
                                } else if (obj['status'] == "INPROGRESS") {
                                    tr += "<td style='text-align:center;' class='INPROGRESS' id='" + date + "-" + time + "'>" + obj['status'] + "</td>";
                                } else {
                                    tr += "<td style='text-align:center;' id='" + date + "-" + time + "'>" + obj['status'] + "</td>";
                                }

                            } else {
                                tr += "<td style='text-align:center;'>" + obj['status'] + "</td>";
                            }
                            tr += "</tr>";
                        }
                    }
                    $('#appointmentList').append(tr);
                    $('#scheduledTable').dataTable({
                        "bDestroy": true
                    }).fnDestroy();

                    $('#scheduledTable').dataTable({
                        "pagingType": "full_numbers",
                        responsive:true,
                        "aaSorting": [],
                        "language": {
                            "emptyTable": "No Scheduled Appointment Found !!!"
                        }
                    });


                });
            }
            if (data['statusCode'] == 100) {
                $('#eMessage').empty().prepend('<div class="notification alert-warning spacer-t10">' +
                    '<p>' + data['message'] + '</p><!--<a class="close-btn" href="#"></a>--></div>').children(':first').delay(5000).fadeOut(100);
            }
            $("#viewAppt").html("View Appointments");
        },
        error: function (data) {

        }
    });

}

//on click of cancel Appt hyperlink in view Appt table
//used to display the confirm pop if yes call cancellAppt function with required details
function confirmForCancelAppt(patientId, clinicId, doctorId, these) {

    $("#apptCancelCnfmPopup").addClass("dailog-show");
    $(".cancelapp").off().on('click', function () {
        var retVal = this.id;
        $("#apptCancelCnfmPopup").removeClass("dailog-show");
        if (retVal === "Yes") {
            var date = $(these).parent().closest('td').attr('id');
            cancelScheduledAppt(doctorId, clinicId, patientId, date);
        }
    });
}

function cancelScheduledAppt(doctorId, clinicId, patientId, date) {

    var dateTime = date.split("-");
    var jsondata = {"doctorId": doctorId, "clinicId": clinicId, "patientId": patientId, "date": dateTime[0], "time": dateTime[1], "status": 0};
    var url = "api/user/appointment/updateStatus";

    $.ajax({
        url: url,
        type: 'POST',
        crossDomain: true,
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',

        success: function (data) {
            if (data['statusCode'] == 200) {

                $('#message').empty().prepend('<div class="notification alert-success spacer-t10">' +
                    '<p>' + data['message'] + '</p><a class="close-btn" href="#"></a></div>').children(':first').delay(5000).fadeOut(100);
                viewAppointment();
            }
        },
        error: function (data) {

        }
    });
}

function ChangeUrl(e, i) {
    if ("undefined" != typeof history.pushState) {
        var s = {Page: e, Url: i};
        history.pushState(s, s.Page, s.Url)
    }
}

function convertToJsDateObj(date) {
    var serverDate, today;
    serverDate = date.split("/");
    today = new Date(serverDate[2], serverDate[1] - 1, serverDate[0]);
    return today;
}


function dateIndDDMMyyy(these) {
    var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    ];
    var dayNames = ["Sun", "Mon", "Tues", "Wed", "Thu", "Fri", "Sat"];
    var serverDate, today, dd, mm, yyyy, date, time, d;
    serverDate = these.split("/");
    today = new Date(serverDate[2], serverDate[1] - 1, serverDate[0]);
    if (these == serverDate) {
        serverDate = these.split("-");
        today = new Date(serverDate[0], serverDate[1] - 1, serverDate[2]);
    }

    dd = dayNames[today.getDay()];
    d = today.getDate();

    mm = monthNames[today.getMonth()];
    yyyy = today.getFullYear();
    date = d + " " + mm + " " + yyyy + "  (" + dd + ")";
    return date;
}

function checkUserExistsAllReady(userName) {

    if (userName != "") {
        var url = "api/user/isExists/" + userName;

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


function checkLicRegExistsAllReady(licRegNo) {
    if (licRegNo != "") {
        var url = "api/user/licRegExists/" + licRegNo;
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

function checkMedLicRegExistsAllReady(licRegNo) {
    if (licRegNo != "") {
        var url = "api/user/medLicRegNoExists/" + licRegNo;
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

function checkEmailExistsAllReady(clinicEmailId,role) {

    if (clinicEmailId != "") {
        var url = "api/user/isEmailIdExists";
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
                var email = document.getElementById("email");

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

function checkMobileExistsAllReady(mobileNo,role) {

    if (mobileNo != "") {
        var url = "api/user/isMobileNumberExists";
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
                var mobile = document.getElementById("mobile");
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

function LoginFormToJSON(form) {
    var array = jQuery(form).serializeArray();
    var json = {};

    jQuery.each(array, function () {
        json[this.name] = this.value || '';
    });

    return json;
}

function CancelSlotsFormToJSON(form) {
    var array = jQuery(form).serializeArray();
    var json = {};
    var newName;
    var parentName;
    var newVal;
    var slots = [];
    var slotsTime = {};

    jQuery.each(array, function () {
        var name = this.name;
        if (name.indexOf(".") > -1) {
            parentName = name.split('.')[0];
            newName = name.split('.')[1];
            if (parentName == 'slots') {
                slotsTime[newName] = this.value || '';
                slots.push(slotsTime);
                newVal = slots;
                slotsTime = {};
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

function getBloodGroup(data, id) {
    $("#"+id).empty().append("<option value='' disabled selected>Select BloodGroup</option>");
    $.each(data, function (idx, obj) {
       /* if (obj['type'] == 'Select') {
            $('#' + id).append($("<option value='' disabled selected></option>")
                .text(obj['type']));
        } else {*/
            $('#' + id).append($("<option></option>")
                .attr("value", obj['bloodGroupId'])
                .text(obj['type']));
       /* }*/
    });
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

function combo(countryId, stateId, stateInput) {
    var value = jQuery('#' + countryId + ' option:selected').val();

    $("#d" + stateId).addClass("hide");
    $("#d" + stateInput).addClass("hide");
    /*if (value == '2') {*/
        $("#d" + stateId).removeClass("hide");
        fetchStates(value, stateId);
        $("#d" + stateInput).removeAttr("required");
        $("#"+stateInput).removeAttr("required");
   /* }
    else {
        $("#d" + stateInput).removeClass("hide");
        $("#d" + stateId).removeAttr("required");
        $("#" + stateId).removeAttr("required");
    }*/
}

function fetchStates(countryId, stateId, stateval) {
    var url = "common/getStates/" + countryId;
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
                if(idx == 0){
                    $('#' + stateId).append($("<option value='' disabled selected></option>")
                        .text('Select State')).append($("<option></option>")
                        .attr("value", obj.stateId)
                        .text(obj.stateName));
                }else {
                    $('#' + stateId).append($("<option></option>")
                        .attr("value", obj.stateId)
                        .text(obj.stateName));
                }
            });
            $('#state,#ucistate').text(name);
            $('#' + stateId).val(stateval);

        },
        error: function (data) {
            window.location = 'invalidSession';
        }
    });
}

function getDate() {
    var date = new Date();
    var mnth = ("0" + (date.getMonth() + 1)).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0' + minutes : minutes;
    var strTime = hours + ':' + minutes + ' ' + ampm;
    return [day, mnth, date.getFullYear()].join("/") + " " + strTime;

}

function getDateFromSeconds(milliseconds) {
    var date = new Date(milliseconds);
    var mnth = ("0" + (date.getMonth() + 1)).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);

    var hours = date.getHours();
    var minutes = date.getMinutes();
    var ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0' + minutes : minutes;
    var strTime = hours + ':' + minutes + ' ' + ampm;

    return [day, mnth, date.getFullYear()].join("/") + " " + strTime;
}

function getOnlyDateFromSeconds(milliseconds) {
    var date = new Date(milliseconds);
    var mnth = ("0" + (date.getMonth() + 1)).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);

    return [day, mnth, date.getFullYear()].join("/");
}

function getBasicInfo(data) {
    var ud = data['ud'];
    var icd = data['icd'];
    var u = data['user'];
    var insd = data['insd'];
    var bg = data['bloodGroups'];
    var country = data['countries'];
    var message = data['message'];

    getBloodGroup(bg, 't_bloodGroup');
    getCountry(country, 't_idcardcountry');
    fetchRecordSpecialities('specialitylist');
    fetchRecordSpecialities('specialitylist1');

    if (ud['firstName'] != null && ud['firstName'] != '' && ud['firstName'] != 'undefined') {
        $('#firstName').text(ud['firstName']);
        $('#t_firstName').val(ud['firstName']);
    }
    else {
        $('#firstName').text("-");
    }
    if (ud['lastName'] != null && ud['lastName'] != '' && ud['lastName'] != 'undefined') {
        $('#lastName').text(ud['lastName']);
        $('#t_lastName').val(ud['lastName']);
    }
    else {
        $('#lastName').text("-");
    }
    $('#datalifeId').text(u['userId']);
    $('#at_userId').val(u['userId']);


    if (ud['localLanguage'] != null && ud['localLanguage'] != '' && ud['localLanguage'] != 'undefined') {
        $('#localLanguage').text(ud['localLanguage']);
        $('#t_localLanguage').val(ud['localLanguage']);
    } else {
        $('#localLanguage').text("-");
    }
    if (ud['religion'] != null && ud['religion'] != '' && ud['religion'] != 'undefined') {
        $('#religion').text(ud['religion']);
        $('#t_religion').val(ud['religion']);
    }
    else {
        $('#religion').text("-");
    }

    if (ud['policeStation'] != null && ud['policeStation'] != '' && ud['policeStation'] != 'undefined') {
        $('#policeStation').text(ud['policeStation']);
        $('#t_policeStation').val(ud['policeStation']);
    }
    else {
        $('#policeStation').text("-");
    }

    if (ud['dateofBirth'] != null && ud['dateofBirth'] != '' && ud['dateofBirth'] != 'undefined') {
        $('#dateofBirth').text(ud['dateofBirth']);
        $('#t_dateofBirth').val(ud['dateofBirth']);
    }
    else {
        $('#dateofBirth').text("-");
    }


    if (ud['gender'] != null && ud['gender'] != '' && ud['gender'] != 'undefined') {
        $('#gender').text(ud['gender']);
        $('#t_gender').val(ud['gender']);
    }
    else {
        $('#gender').text("-");
    }
    if (ud['bloodGroup'] != null && ud['bloodGroup'] != 'undefined' && ud['bloodGroup'] != "") {

        $('#t_bloodGroup').val(ud['bloodGroup']);
        var bloodGroup = $('#t_bloodGroup option:selected').text();
        $('#bloodGroup').text(bloodGroup);
    }
    if (ud['maritalStatus'] != '' && ud['maritalStatus'] != null && ud['maritalStatus'] != 'undefined') {
        $('#maritalStatus').text(ud['maritalStatus']);
        $('#t_maritalStatus').val(ud['maritalStatus']);
    }
    else {
        $('#maritalStatus').text("-");
    }

    if (icd != null && icd != '' && icd != 'undefined') {

        if (icd['fileName'] != '' && icd['fileName'] != 'undefined' && icd['fileName'] != null) {
            $("#viewIdentityimg").attr("src", data['idImageUrl']);
        }

        if (icd['idType'] != '' && icd['idType'] != 'undefined' && icd['idType'] != null) {
            $('#idType').text(icd['idType']);
            $('#t_idType').val(icd['idType']);
        }
        else {
            $('#idType').text("-");
        }
        if (icd['idNumber'] != '' && icd['idNumber'] != 'undefined' && icd['idNumber'] != null) {
            $('#idNumber').text(icd['idNumber']);
            $('#t_idNumber').val(icd['idNumber']);
        }
        else {
            $('#idNumber').text("-");
        }

        if (icd['country'] != null && icd['country'] != 'undefined' && icd['country'] != "") {
            $('#t_idcardcountry').val(icd['country']);
            var countryval = $('#t_idcardcountry option:selected').text();
            $('#country').text(countryval);

            if (icd['country'] == 2) {
                fetchStates(icd['country'], "t_idcardstate", icd['state']);
                $("#dt_idcardstate").removeClass("hide");
            }
            else {
                $("#dt_idcardstateInput").removeClass("hide");
                if (icd['otherState'] != '' && icd['otherState'] != 'undefined' && icd['otherState'] != null) {
                    $('#state').text(icd['otherState']);
                    $('#t_idcardstateInput').val(icd['otherState']);
                }
                else {
                    $('#state').text("-");
                }
            }
        }
        else {
            $('#country').text("-");
            $("#state").text("-");
        }
    } else {
        $('#idType').text("-");
        $('#idNumber').text("-");
        $('#country').text("-");
        $("#state").text("-");
    }
    if (insd != null && insd != 'undefined' && insd != '') {
        if (insd['policyNumber'] != '' && insd['policyNumber'] != 'undefined' && insd['policyNumber'] != null) {

            $('#policyNumber').text(insd['policyNumber']);
            $('#t_policyNumber').val(insd['policyNumber']);
        }
        else {
            $('#policyNumber').text("-");
        }
        if (insd['provider'] != '' && insd['provider'] != 'undefined' && insd['provider'] != null) {

            $('#provider').text(insd['provider']);
            $('#t_provider').val(insd['provider']);
        }
        else {
            $('#provider').text("-");
        }
        if (insd['validity'] != '' && insd['validity'] != 'undefined' && insd['validity'] != null) {
            $('#validity').text(insd['validity']);
            $('#t_validity').val(insd['validity']);
        }
        else {
            $('#validity').text("-");
        }

    } else {
        $('#policyNumber').text("-");
        $('#provider').text("-");
        $('#validity').text("-");
    }
    if (message != "" && message != undefined && message != null) {
        $("#view_basicDetails").removeClass("hide");
        $("#contactDetails,#emergencyDetails,#preferences,#professionaldetails,#basicDetails,#view_contactDetails,#view_emergencyDetails,#view_preferences,#view_professionaldetails").addClass("hide");
    }
    $("#a_basicDetails").addClass("tmm-success");

}

function getContactInfo(data) {

    var uci = data['uci'];
    var country = data['countries'];
    var message = data['message'];

    getCountry(country, 't_ucicountry');

    if (uci['address'] != null && uci['address'] != '' && uci['address'] != 'undefined') {
        $('#uciaddress').text(uci['address']);
        $('#t_address').val(uci['address']);
    }
    else {
        $('#uciaddress').text("-");
    }
    if (uci['location'] != null && uci['location'] != '' && uci['location'] != 'undefined') {
        $('#ucilocation').text(uci['location']);
        $('#t_location').val(uci['location']);
    }
    else {
        $('#ucilocation').text("-");
    }
    if (uci['city'] != null && uci['city'] != '' && uci['city'] != 'undefined') {
        $('#ucicity').text(uci['city']);
        $('#t_city').val(uci['city']);
    }
    else {
        $('#ucicity').text("-");
    }
    if (uci['zipCode'] != null && uci['zipCode'] != '' && uci['zipCode'] != 'undefined') {
        $('#ucizipcode').text(uci['zipCode']);
        $('#t_zipCode').val(uci['zipCode']);
    }
    else {
        $('#ucizipcode').text("-");
    }
    if (uci['mobilePhone'] != null && uci['mobilePhone'] != '' && uci['mobilePhone'] != 'undefined') {
        $('#ucimobile').text(uci['mobilePhone']);
        $('#t_mobilePhone').val(uci['mobilePhone']);
    }
    else {
        $('#ucimobile').text("-");
    }
    if (uci['homePhone'] != null && uci['homePhone'] != '' && uci['homePhone'] != 'undefined') {
        $('#ucihomephone').text(uci['homePhone']);
        $('#t_homePhone').val(uci['homePhone']);
    }
    else {
        $('#ucihomephone').text("-");
    }
    if (uci['email'] != null && uci['email'] != '' && uci['email'] != 'undefined') {
        $('#uciemail').text(uci['email']);
        $('#t_email').val(uci['email']);
    }
    else {
        $('#uciemail').text("-");
    }
    if (uci['occupation'] != null && uci['occupation'] != '' && uci['occupation'] != 'undefined') {
        $('#ucioccupation').text(uci['occupation']);
        $('#t_occupation').val(uci['occupation']);
    }
    else {
        $('#ucioccupation').text("-");
    }
    if (uci['workPhone'] != null && uci['workPhone'] != '' && uci['workPhone'] != 'undefined') {
        $('#uciworkphone').text(uci['workPhone']);
        $('#t_workPhone').val(uci['workPhone']);
    }
    else {
        $('#uciworkphone').text("-");
    }

    if (uci['country'] != null && uci['country'] != 'undefined' && uci['country'] != "") {
        $('#t_ucicountry').val(uci['country']);
        var countryval = $('#t_ucicountry option:selected').text();
        $('#ucicountry').text(countryval);
        if (uci['country'] == 2) {
            fetchStates(uci['country'], "t_ucistate", uci['state']);
            $("#dt_ucistate").removeClass("hide");
            $("#dt_ucistateInput").addClass("hide");
        }
        else {
            $("#dt_ucistateInput").removeClass("hide");
            if (uci['otherState'] != '' && uci['otherState'] != 'undefined' && uci['otherState'] != null) {
                $('#ucistate').text(uci['otherState']);
                $('#t_ucistateInput').val(uci['otherState']);
            }
            else {
                $('#ucistate').text("-");
            }
        }
    }
    else {
        $('#ucicountry').text("-");
        $("#ucistate").text("-");
    }


    if (message != "" && message != undefined && message != null) {
        $("#view_contactDetails").removeClass("hide");
        $("#contactDetails,#emergencyDetails,#preferences,#basicDetails,#professionaldetails,#view_basicDetails,#view_emergencyDetails,#view_preferences,#view_professionaldetails").addClass("hide");
    }
    $("#a_contactDetails").addClass("tmm-success");

}

function getEmergencyInfo(data) {

    var ueis = data['ueis'];
    var message = data['message'];
    if (ueis != null && ueis != 'undefined' && ueis.length > 0) {
        var uei = ueis[0];
        if (uei != null && uei != 'undefined') {
            if (uei['name'] != null && uei['name'] != '' && uei['name'] != 'undefined') {
                $('#edname').text(uei['name']);
                $('#t_edname').val(uei['name']);
            }
            else {
                $('#edname').text("-");
            }

            if (uei['relation'] != null && uei['relation'] != '' && uei['relation'] != 'undefined') {
                $('#edrelation').text(uei['relation']);
                $('#t_edrelation').val(uei['relation']);
            }
            else {
                $('#edrelation').text("-");
            }

            if (uei['mobileNumber'] != null && uei['mobileNumber'] != '' && uei['mobileNumber'] != 'undefined') {
                $('#edmobileNumber').text(uei['mobileNumber']);
                $('#t_edmobileNumber').val(uei['mobileNumber']);
            }
            else {
                $('#edmobileNumber').text("-");
            }

            if (uei['homePhone'] != null && uei['homePhone'] != '' && uei['homePhone'] != 'undefined') {
                $('#edhomePhone').text(uei['homePhone']);
                $('#t_edhomePhone').val(uei['homePhone']);
            }
            else {
                $('#edhomePhone').text("-");
            }
        }
        else {
            $('#edname,#edrelation,#edmobileNumber,#edhomePhone').text("-");
        }
        var ueit = ueis[1];
        if (ueit != null && ueit != 'undefined') {

            if (ueit['name'] != null && ueit['name'] != '' && ueit['name'] != 'undefined') {
                $('#edtname').text(ueit['name']);
                $('#t_edtname').val(ueit['name']);
            }
            else {
                $('#edtname').text("-");
            }
            if (ueit['relation'] != null && ueit['relation'] != '' && ueit['relation'] != 'undefined') {
                $('#edtrelation').text(ueit['relation']);
                $('#t_edtrelation').val(ueit['relation']);
            }
            else {
                $('#edtrelation').text("-");
            }

            if (ueit['mobileNumber'] != null && ueit['mobileNumber'] != '' && ueit['mobileNumber'] != 'undefined') {
                $('#edtmobileNumber').text(ueit['mobileNumber']);
                $('#t_edtmobileNumber').val(ueit['mobileNumber']);
            }
            else {
                $('#edtmobileNumber').text("-");
            }

            if (ueit['homePhone'] != null && ueit['homePhone'] != '' && ueit['homePhone'] != 'undefined') {
                $('#edthomePhone').text(ueit['homePhone']);
                $('#t_edthomePhone').val(ueit['homePhone']);
            }
            else {
                $('#edthomePhone').text("-");
            }


        } else {
            $('#edtname,#edtrelation,#edtmobileNumber,#edthomePhone').text("-");
        }
        $("#a_emergencyDetails").removeClass("tmm-current").addClass("tmm-success");
    }
    else {
        $('#edname,#edrelation,#edmobileNumber,#edhomePhone,#edtname,#edtrelation,#edtmobileNumber,#edthomePhone').text("-");
        $("#a_emergencyDetails").removeClass("tmm-success").addClass("tmm-current");
    }
    if (message != "" && message != undefined && message != null) {
        $("#view_emergencyDetails").removeClass("hide");
        $("#contactDetails,#emergencyDetails,#preferences,#basicDetails,#view_basicDetails,#view_contactDetails,#view_preferences").addClass("hide");
    }

}

function getPreferences(data) {
    var ups = data['ups'];
    var message = data['message'];
    $('#dynamicdiv,#dynamiceditdiv').empty();
    if (ups != null && ups != 'undefined' && message != "") {
        var keys = Object.keys(data['ups']);
        var values = keys.map(function(v) {
            var resultArrayLength=ups[v].length;
            var speciality=[];
            for(var i=0;i<resultArrayLength;i++) {
                var data=JSON.stringify(ups[v][i]);
                var div = "<div  class='col-md-12'>";
                var div1 = "<div class='col-md-12'>" +"<div class='row'>";
                if(speciality.indexOf(ups[v][i]["specialityId"]) === -1)
                {
                    speciality.push(ups[v][i]["specialityId"]);
                    div += "<div class='col-md-12'>" + "<h2 class='mydoctors mydoctors-view'>" + ups[v][i]["speciality"] + "</h2></div>";
                    div1 += "<div class='col-md-9'>" +"<h2 class='mydoctors mydoctors-view'>" + ups[v][i]['speciality'] + "</h2></div>";
                }
                div += "<div class='col-md-8 col-sm-7'> <div class='data-container'>";
                div += "<dl><dt>Clinic/ Doctor Name</dt>: <dd>" + ups[v][i]['name'] + "</dd></dl>";
                div += "<dl><dt>Contact Number</dt>: <dd>" + ups[v][i]['contactNumber'] + "</dd></dl>";
                div += "<dl><dt>Address</dt>: <dd>" + ups[v][i]['address'] + "</dd></dl>";
                div += "</div></div>";
                div += "</div>";

                $('#dynamicdiv').append(div);

                div1 += "<div class='col-md-6  no-padding'>"+ "<div class='col-md-6 col-sm-6 no-pad '>"+"<fieldset class='input-block'>";
                div1 += "<label>Clinic/ Doctor Name</label>";
                div1 += "<input type='text'  name='name' value=" +ups[v][i]['name']+">";
                div1 += "<input type='hidden' name='user.userId' value="+ups[v][i]['user']['userId']+">";
                //div1 += "<input type='hidden' name='speciality' id='speciality' value="+ups[v][i]['speciality']+">";
                div1 += "<input type='hidden' name='specialityId' id='specialityId' value="+ups[v][i]['specialityId']+">";
                div1 += "</fieldset>"+" </div>";
                div1 += "<div class='col-md-6 col-sm-6  no-pad '>"+ "<fieldset class='input-block'>";
                div1 += "<label>Address</label>";
                div1 += "<input type='text' name='address' value=" +ups[v][i]['address']+">";
                div1 += "</fieldset>"+" </div>"+" </div>";
                div1 += "<div class='col-md-6  no-padding'>"+ "<div class='col-md-6 col-sm-6 no-pad '>"+"<fieldset class='input-block'>";
                div1 += "<label>Contact Number</label>";
                div1 += "<input type='text' name='contactNumber' value=" +ups[v][i]['contactNumber']+">";
                div1 += "</fieldset>"+" </div>"+" </div>";
                div1 += "</div>"+"</div>";

                $('#dynamiceditdiv').append(div1);
                $("#canceladpreferences").removeClass("hide");
            }

        });
        $("#editpreferences").removeClass("hide");
        $("#a_preferences").removeClass("tmm-current").addClass("tmm-success");
    } else {
        $("#editpreferences").addClass("hide");
        $("#addpreferences").click();
        $("#canceladpreferences").addClass("hide");
        $("#a_preferences").removeClass("tmm-success").addClass("tmm-current");

    }
    if (message != "" && message != undefined && message != null &&  message != "Success") {
        $("#successMessages").removeClass("hide");
        $('#successMessages').empty().prepend('<div class="notification alert-success spacer-t10">' +
            '<p>' +message + '</p><span class="close-btndemo"></span></div>').children(':first').delay(150000).fadeOut(100, function () {
            $('#successMessages').empty();
        });
        $("#edpreferences").removeClass("hide");
        $("#view_preferences").removeClass("hide");
        $("#contactDetails,#emergencyDetails,#preferences,#basicDetails,#view_basicDetails,#view_contactDetails,#view_emergencyDetails,#adpreference").addClass("hide");
    }
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

function getDoctorInfo(data) {
    var doctorInfo = data['doctorInfo'];
    var message = data['message'];

    if (doctorInfo['mlrNumber'] != null && doctorInfo['mlrNumber'] != 'undefined' && doctorInfo['mlrNumber'] != '') {
        $('#medicalLicenceNumber').text(doctorInfo['mlrNumber']);
        $('#t_medicalLicenceNumber').val(doctorInfo['mlrNumber']);
    } else {
        $('#medicalLicenceNumber').text("-");
    }
    if (doctorInfo['qualification'] != null && doctorInfo['qualification'] != 'undefined' && doctorInfo['qualification'] != '') {
        $('#qualification').text(doctorInfo['qualification']);
        $('#t_qualification').val(doctorInfo['qualification']);
    } else {
        $('#qualification').text("-");
    }
    if (doctorInfo['affiliations'] != null && doctorInfo['affiliations'] != 'undefined' && doctorInfo['affiliations'] != '') {
        $('#affiliations').text(doctorInfo['affiliations']);
        $('#t_affiliations').val(doctorInfo['affiliations']);
    } else {
        $('#affiliations').text("-");

    }

    if (message != "" && message != undefined && message != null) {
        /*       $('#successMessages').empty();
         $('#successMessages').prepend('<div class="notification alert-success spacer-t10">' +
         '<p>' +message + '</p><span class="close-btndemo"></span></div>').children(':first').delay(150000).fadeOut(100, function () {
         $('#successMessages').empty();
         });*/
        $("#view_professionaldetails").removeClass("hide");
        $("#contactDetails,#basicDetails,#professionaldetails,#view_basicDetails,#view_contactDetails").addClass("hide");
    }
    $("#a_professionaldetails").removeClass("tmm-current").addClass("tmm-success");
}

function getSettings(doctorId, clinicId, verdict) {

    var url = "api/user/clinic/getSettingsData";
    var jsondata = {"clinicId": clinicId, "doctorId": doctorId};
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',
        success: function (data) {
            var settings = data['settings'];
            var specialities = data['specialities'];
            var timings = data['timings'];
            var newTimings=data['newtimings'];
            var changeSetEfectFrm = data['changeSetEfectFrm'];
            if (verdict == 'true') {
                $('#ajaxloaddiv').load("clinic/settings", function () {
                    var name = "";
                    $.each(data['clinicDoctors'], function (key, value) {
                        if (key == doctorId) {
                            name = value;
                        }
                        $('#clinicDoctorList').append($('<option>').text(value).attr('value', key));
                    });
                    $('#clinicDoctorList').val(doctorId);
                    $("#doctor_id").text(doctorId);
                    $("#doctor_name").text(name);
                    getSettingData(settings, specialities, timings,changeSetEfectFrm,newTimings);
                });
            }
            if (verdict == 'both') {
                $('#ajaxloaddiv').load("doctor/settings", function () {
                    getSettingData(settings, specialities, timings,changeSetEfectFrm,newTimings);
                });
            } else {
                getSettingData(settings, specialities, timings,changeSetEfectFrm,newTimings);
            }
        },
        error: function (data) {
            window.location = 'invalidSession';
        }
    });
}

function getSettingData(settings, specialities, timings,changeSetEfectFrm,newTimings) {

    if (settings != 'undefined' && settings != null && settings != "") {

        if (settings['qualification'] != null && settings['qualification'] != 'undefined' && settings['qualification'] != '') {
            $("#cpQualification").val(settings['qualification']);
            $("#t_qualification").text(settings['qualification']);
        } else {
            $("#t_qualification").text("-");
            $("#cpQualification").val("");
        }
        if (settings['experience'] != null && settings['experience'] != 'undefined' && settings['experience'] != '') {
            $("#cpexperience").val(settings['experience']);
            $("#t_experience").text(settings['experience']);
        } else {
            $("#t_experience").text("-");
            $("#cpexperience").val("");
        }

        var consultationFee = settings['consultationFee'];
        if (consultationFee != "" && consultationFee != 'undefined' && consultationFee != null) {
            var main = consultationFee.split(" ");
            $("#cpconsultationFee").val(consultationFee);
            $("#t_consulationFee").text(consultationFee);
            $("#consultationFeeId").val(main[0]);
            $("#currencyId").val(main[1]);
        } else {
            $("#consultationFeeId").val("");
            $("#currencyId").val("");
        }
        if (settings['slotTime'] != null && settings['slotTime'] != 'undefined' && settings['slotTime'] != '') {
            $("#cpslotTime").val(settings['slotTime']);
            $("#t_slotTime").text(settings['slotTime']);
        } else {
            $("#t_slotTime").text("-");
            $("#cpslotTime").val("");
        }


    } else {
        $("#t_qualification").text("-");
        $("#t_experience").text("-");
        ("#t_consulationFee").text("-");
        $("#t_slotTime").text("-");
        $("#cpslotTime").val("");
        $("#consultationFeeId").val("");
        $("#currencyId").val("INR");
        $("#cpexperience").val("");
        $("#cpQualification").val("");


    }
    var activate = settings['activate'];

    if (activate == true) {
        $("#cpActivate").val('true');
        $("#t_activate").text('This doctor is active in clinic');
    } else {

        $("#cpActivate").val('false');
        $("#t_activate").text('This doctor is deactivated');
    }
    getSpecialities(specialities, 'autocomplete_speciality');
    if (settings['specialityId'] != null && settings['specialityId'] != "" && settings['specialityId'] != 'undefined') {
        $("#autocomplete_speciality").val(settings['specialityId']);
    }
    var specialityText = $("#autocomplete_speciality option:selected").text();
    if (specialityText == 'Select Speciality') {
        $("#t_speciality").text('-');
    } else {
        $("#t_speciality").text($("#autocomplete_speciality option:selected").text());
    }

    setValues("mon", settings['monst_1'], settings['monet_1'], settings['monst_2'], settings['monet_2']);
    setValues("tue", settings['monst_1'], settings['monet_1'], settings['monst_2'], settings['monet_2']);
    setValues("wed", settings['monst_1'], settings['monet_1'], settings['monst_2'], settings['monet_2']);
    setValues("thu", settings['monst_1'], settings['monet_1'], settings['monst_2'], settings['monet_2']);
    setValues("fri", settings['monst_1'], settings['monet_1'], settings['monst_2'], settings['monet_2']);
    setValues("sat", settings['monst_1'], settings['monet_1'], settings['monst_2'], settings['monet_2']);
    setValues("sun", settings['monst_1'], settings['monet_1'], settings['monst_2'], settings['monet_2']);
    $(".t_timings").html("");
    var tr = "";
    if (jQuery.isEmptyObject(timings)) {
        $(".dtimings").hide();
        $(".timings_message").html("<a style='text-decoration: underline' id='changeVisitSettings'>Set Doctor Visit Timings !!!</a>");
    } else {

        $.each(timings, function (key, value) {
            tr += "<tr style='text-align: center'>";
            tr += '<td>' + key.toString() + '</td><td>' + value.toString() + '</td></tr>';

        });
        $(".t_timings").append(tr);
        $(".dtimings").show();
        $(".timings_message").text("");

    }

    $(".t_newtimings").html("");
    tr = "";
    if (jQuery.isEmptyObject(newTimings)) {
        $(".newTimings_div").hide();
        $(".newtimings_message").html("<a style='text-decoration: underline' id='changeVisitSettings'>Set Doctor Visit Timings !!!</a>");
    } else {
        $(".effectedDate").text(changeSetEfectFrm);
        $.each(newTimings, function (key, value) {
            tr += "<tr style='text-align: center'>";
            tr += '<td>' + key.toString() + '</td><td>' + value.toString() + '</td></tr>';

        });
        $(".t_newtimings").append(tr);
        $(".newTimings_div").show();
    }
}

function setValues(data, st_1, et_1, st_2, et_2) {
    var array = [st_1, et_1, st_2, et_2];
    $("#" + data + "st_1").val(st_1);
    $("#" + data + "et_1").val(et_1);
    $("#" + data + "st_2").val(st_2);
    $("#" + data + "et_2").val(et_2);
}

function setSlotValues(data) {
    var input = $('input[type="checkbox"][id="' + data + '"]:checked').map(function () {
        return this.value;
    }).get();
    if (input != "" && input != null && input != 'undefined') {
        $("#" + data + "Status_1").val(true);
    }
    var ss = $("#singleSlot" + input);

    $("#" + data + "st_1").val(ss.find(".st_1 option:selected").text());
    $("#" + data + "et_1").val(ss.find(".et_1 option:selected").text());
    $("#" + data + "st_2").val(ss.find(".st_2 option:selected").text());
    $("#" + data + "et_2").val(ss.find(".et_2 option:selected").text());

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

function serviceRequestFormToJSON(form) {
    var array = jQuery(form).serializeArray();
    var json = {};
    var newName;
    var parentName;
    var newVal;
    var labRequests = {};
    var alternateServiceContactInfo = {};
    var surgeryRequests = {};
    var secondOpnRequests = {};

    jQuery.each(array, function () {
        var name = this.name;
        if (name.indexOf(".") > -1) {
            parentName = name.split('.')[0];
            newName = name.split('.')[1];

            if (parentName == 'surgeryRequests') {
                surgeryRequests[newName] = this.value || '';
                newVal = surgeryRequests;
            }

            if (parentName == 'alternateServiceContactInfo') {
                alternateServiceContactInfo[newName] = this.value || '';
                newVal = alternateServiceContactInfo;
            }

            if (parentName == 'labRequests') {
                labRequests[newName] = this.value || '';
                newVal = labRequests;
            }
            if (parentName == 'secondOpnRequests') {
                secondOpnRequests[newName] = this.value || '';
                newVal = secondOpnRequests;
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

function LabServiceRequestFormToJSON(form) {
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
            if (parentName == 'alternateServiceContactInfo') {
                user[newName] = this.value || '';
                newVal = user;
            }

            if (parentName == 'labRequests') {
                user[newName] = this.value || '';
                newVal = user;
            }

            if (parentName == 'pharmacyRequests') {
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

function fetchRecordSpecialities(id){
    var url = "common/getRecordSpecialities";
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            $("#"+id).empty().append("<option value='' disabled selected>Select Speciality</option>");
            $.each(data, function (idx, obj) {
                $("#"+id).append($("<option></option>")
                    .attr("value", obj['specialityId'])
                    .text(obj['name']));
            });
        },
        error: function (data) {

        }
    });
}

function fetchCountry(countryId){
    var url = "common/getCountries";
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
    var url = "common/getHospitalCities/" + country;
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

function checkEmergencyMobileExit(input,id){
    if (input != "") {
        var userId = $("#eduserId").val();
            var url = "api/user/isEmergencyMobileExit";
            var jsondata = {"mobileNumber": input,"userId":userId};
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



