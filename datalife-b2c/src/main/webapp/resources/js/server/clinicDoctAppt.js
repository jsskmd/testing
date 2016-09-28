/**
 * Created by barath on 9/18/2015.
 */


$(document).ready(function () {

    $("#curAppt").click(function () {
        $(this).addClass("show_border_clinic");
        $("#futAppt").removeClass("show_border_clinic");
        $("#pastAppt").removeClass("show_border_clinic");

        $("#curApptSlot").removeClass("hide");
        $("#futApptSlot").addClass("hide");
        $("#pastApptSlot").addClass("hide");
    });

    $("#futAppt").click(function () {
        $(this).addClass("show_border_clinic");
        $("#curAppt").removeClass("show_border_clinic");
        $("#pastAppt").removeClass("show_border_clinic");

        $("#curApptSlot").addClass("hide");
        $("#futApptSlot").removeClass("hide");
        $("#pastApptSlot").addClass("hide");
        $(".futpastmsg").empty();
    });

    $("#pastAppt").click(function () {
        $(this).addClass("show_border_clinic");
        $("#curAppt").removeClass("show_border_clinic");
        $("#futAppt").removeClass("show_border_clinic");

        $("#curApptSlot").addClass("hide");
        $("#futApptSlot").addClass("hide");
        $("#pastApptSlot").removeClass("hide");
        $(".futpastmsg").empty();
    });

    $("#apptadd_patient").click(function(e){
        $(this).addClass("show_border_clinic");
        $("#apptsearch_patient").removeClass("show_border_clinic");
        $("#PDetailsPopUp").addClass("hide");
        $("#apptregisterPatient").removeClass("hide");
        var doctorId, clinicId, selectedclinic;
        selectedclinic = $("#select_clinic").val();
        if (selectedclinic) {
            clinicId = selectedclinic;
            doctorId = $("#userId").text();
        } else {
            doctorId = $("#selectedDoct").val();
            clinicId = $("#userId").text();
        }
        $("#clinicIdInput,#clncId").val(clinicId);
        $("#doctorIdInput,#docId").val(doctorId);
    });

    $("#apptsearch_patient").click(function(){
        $(this).addClass("show_border_clinic");
        $("#apptadd_patient").removeClass("show_border_clinic");
        $("#PDetailsPopUp").removeClass("hide");
        $("#apptregisterPatient").addClass("hide");
    });
});

//onclick of view navigate to viewComplete.jsp and display the current day scheduled status
function getAppointment(doctorId, clinicId, isFromDoc) {

    var url = "api/user/appointment/getDoctorSlotsOnDate";
    // value indicates to fetch current day appointment
    var jsondata = {"doctorId": doctorId, "clinicId": clinicId, "value": true};

    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',

        success: function (data) {

            var saveStatus = data['statusCode'];
            var settings = data['settings'];
            var tr = "";
            var msg = "No data available in table";
            $("#ajaxloaddiv").load("clinic/getCDMainViewAppt", function () {
                var docDetails = data['doctorDetails'];

                if (isFromDoc) {
                    var clinicName = docDetails['clinicName'];
                    $("#displayDocName").text(clinicName);
                    $("#docImage").attr('src', docDetails['clinicImageUrl']);
                    $("#docCancelSetting").show();
                    $("#isFromDocLogin").val(true);
                } else {
                    var docName = docDetails['doctorName'];
                    var specialityName = '';
                    if (docDetails['specialityName'] != null && docDetails['specialityName'] != 'undefined') {
                        specialityName = docDetails['specialityName'];
                    }
                    $("#docSpeciality").text(specialityName);
                    $("#displayDocName").text(docName);
                    $("#docImage").attr('src', docDetails['doctorImageUrl']);
                    $("#selectedDoct").val(doctorId);
                }
                $("#curDate").val(data['curDate']);
                $("#today_date").text(data['curDate']);
                $("#serverDate").val(data['serverDate']);
                $("#maxDateToShow").val(data['maxDateToShow']);
                $("#pastDateToShow").val(data['pastDateToShow']);

                if (saveStatus == 200) {

                    $.each(data['resultedSlots'], function (idx, obj) {
                        if (!$.isEmptyObject(obj)) {

                            var flag = true;

                            $.each(obj, function (i, str) {

                                if (str['status'] != null) {
                                    switch (str['status']) {
                                        case "SCHEDULED":
                                        case "ARRIVED":
                                        case "INPROGRESS":
                                        case "COMPLETED":
                                            tr += '<tr id="' + str['tokenNo'] + '">' +
                                                '<td>' + str['tokenNo'] + '</td>' +
                                                '<td>' +
                                                ' <div class="col-md-12 today-appointment-line">';
                                            if (data['showNewEncounter']) {
                                                tr += '<a class="image-function" id="' + str['tokenNo'] + '" onclick="showPatDetailOnSlot(' + doctorId + "," + clinicId + "," + str['patientId'] + ',this,' + data['showNewEncounter'] + ')">';
                                            } else {
                                                tr += '<a class="image-function" id="' + str['tokenNo'] + '" onclick="showPatDetailOnSlot(' + doctorId + ',' + clinicId + ',' +  str['patientId']  + ', this,false)">';
                                            }
                                            tr += '<input type="hidden" id="date' + str['tokenNo'] + '" value="' + idx + '">' +
                                                '<div class="col-md-12 no-pad doc-name-for-app"><span class="name-class-for-doctor">' + ' Mr/Ms ' + str['patientName'] + '</span><br>DataLife Id:<span id="patId' + str['tokenNo'] + '">' + str['patientId'] + '</span></div>' +
                                                '</a>'+
                                                '</div>' +
                                                '</td>' +
                                                '<td>' +
                                                '<div class="col-md-12 padding-assign "> <a class="scheduled-times-for-doc-' + str['status'] + '" href="#">' + str['status'] + '</a>' +
                                                '<div class="today-appointment-line-bottom" id="time' + str['tokenNo'] + '">' + i + '</div></div>' +
                                                '</td>';
                                            /*'<div class="col-md-5  no-pad select-info ">' +
                                             '<div class="list-pad">' +*/
                                            /*'<td>';*/
                                            if (str['status'] == "SCHEDULED") {
                                                tr += '<td><input type="checkbox" class="red" checked name="' + str['tokenNo'] + '" value="SCHEDULED"></td>' +
                                                    '<td><input type="checkbox" class="red" name="' + str['tokenNo'] + '" value="ARRIVED"></td>' +
                                                    '<td><input type="checkbox" class="red" name="' + str['tokenNo'] + '" value="INPROGRESS"></td>' +
                                                    '<td><input type="checkbox"  class="red" name="' + str['tokenNo'] + '" value="COMPLETED"></td>';

                                            } else if (str['status'] == "ARRIVED") {
                                                tr += '<td><input type="checkbox" disabled class="red" name="' + str['tokenNo'] + '" value="SCHEDULED"></td>' +
                                                    '<td><input type="checkbox"  class="red" checked name="' + str['tokenNo'] + '" value="ARRIVED"></td>' +
                                                    '<td><input type="checkbox"  class="red" name="' + str['tokenNo'] + '" value="INPROGRESS"></td>' +
                                                    '<td><input type="checkbox"  class="red" name="' + str['tokenNo'] + '" value="COMPLETED"></td>';
                                            } else if (str['status'] == "INPROGRESS") {
                                                tr += '<td><input type="checkbox"  disabled class="red" name="' + str['tokenNo'] + '" value="SCHEDULED"></td>' +
                                                    '<td><input type="checkbox" disabled class="red" name="' + str['tokenNo'] + '" value="ARRIVED"></td>' +
                                                    '<td><input type="checkbox"  checked  class="red" name="' + str['tokenNo'] + '" value="INPROGRESS"></td>' +
                                                    '<td><input type="checkbox" class="red" name="' + str['tokenNo'] + '" value="COMPLETED"></td>';
                                            } else {
                                                tr += '<td><input type="checkbox" disabled class="red" name="' + str['tokenNo'] + '" value="SCHEDULED" ></td>' +
                                                    '<td><input type="checkbox"  disabled class="red" name="' + str['tokenNo'] + '" value="ARRIVED"></td>' +
                                                    '<td><input type="checkbox"  disabled class="red" name="' + str['tokenNo'] + '" value="INPROGRESS"></td>' +
                                                    '<td><input type="checkbox"  checked class="red" name="' + str['tokenNo'] + '" value="COMPLETED"></td>';
                                            }
                                            tr += '</tr>';
                                            /*'</td>' +
                                             '</div>' +
                                             '</td>' +*/

                                            break;
                                        case "CANCELDAY":
                                            msg = data['message'];
                                            break;
                                        case "ONLEAVE":
                                            msg = data['message'];
                                            break;
                                    }
                                } else {
                                    if (flag) {
                                        $('#onDemandList').append($('<option>').text("Pick a Slot").attr({value: 0,
                                            selected: true,
                                            disabled: ''})).append($('<option>').attr({
                                            id: idx,
                                            value: str['tokenNo']
                                        }).text(i));
                                        flag = false;
                                        $('#onDemand').show();
                                    } else {
                                        $('#onDemandList').append($('<option>').attr({
                                            id: idx,
                                            value: str['tokenNo']
                                        }).text(i));
                                    }
                                }

                            });
                        }
                    });

                    $('#todayAppttable').dataTable({
                        "bDestroy": true,
                        responsive: true
                    }).fnDestroy();
                    $('#todayAppt').append(tr);
                    $('#todayAppttable').addClass('nowrap').dataTable({
                        responsive: true,
                            bRetrieve: true,
                        language: {
                            "emptyTable": msg
                        }
                    });


                } else if (saveStatus == 100) {

                    $('#todayAppttable').addClass('nowrap').dataTable({
                        responsive: true,
                        "language": {
                            "emptyTable": data['message']
                        },
                        columnDefs: [
                            { targets: [-1, -3], className: 'dt-body-right' }
                        ]
                    });

                }
                datePickerInit(data['serverDate'], data['maxDateToShow'], 'future');
                datePickerInit(data['pastDate'], data['pastDateToShow'], 'past');
            });
        },
        error: function (data) {

        }
    });

}

//initialize the datepickers in viewCompleteAppt.jsp
function datePickerInit(dateToday, maxDateToshow, status) {
    var maxDate, minDate, id;
    if (status == 'future') {
        maxDate = maxDateToshow;
        minDate = dateToday;
        id = '#futFrmDatepicker,#furToDatepicker';
    } else {
        maxDate = dateToday;
        minDate = maxDateToshow;
        id = '#pastFrmDatepicker,#pastToDatepicker';
    }

    $(id).datetimepicker({
        changeYear: true,
        changeMonth: true,
        scrollMonth: true,
        closeOnDateSelect: true,
        minDate: minDate,
        maxDate: maxDate,
        onSelect: function (selectedDate) {
            var option = this.id == "from" ? "minDate" : "maxDate",
                instance = $(this).data("datepicker"),
                date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
        },
        timepicker: false,
        format: 'd/m/Y'
    });
}

$(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    //onclick of curdate tab in viewcompleteAppt.js get the current day scheduled Status
    $('#todayAppt').on('click', '.red', function () {
        var nam = $(this).attr('name');
        var checkedVal = $(this).val();
        $('input[type="checkbox"][name=' + nam + ']').each(function () {
            var val = $(this).val();
            switch (checkedVal) {
                case "ARRIVED":
                    if (val == "SCHEDULED") {
                        $(this).attr({
                            disabled: true,
                            checked: false
                        });
                    }
                    break;
                case "INPROGRESS":
                    if (val == "ARRIVED" || val == "SCHEDULED") {
                        $(this).attr({
                            disabled: true,
                            checked: false
                        });
                    }
                    break;
                case "COMPLETED":
                    if (val == "ARRIVED" || val == "SCHEDULED" || val == "INPROGRESS") {
                        $(this).attr({
                            disabled: true,
                            checked: false
                        });
                    }
                    break;
            }
        });
        var patientId = $("#patId" + nam).text();
        var time = $("#time" + nam).text();
        var doctorId, clinicId, isFromDoc;
        if ($("#isFromDocLogin").val()) {
            doctorId = $("#userId").text();
            clinicId = $("#select_clinic").val();
            isFromDoc = true;
        } else {
            clinicId = $("#userId").text();
            doctorId = $("#selectedDoct").val();
        }

        var jsondata = {"doctorId": doctorId, "clinicId": clinicId, "value": true, "time": time, "patientId": patientId, "status": checkedVal, "curTokenNo": nam};

        var url = "api/user/appointment/updateStatus";
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsondata),
            contentType: 'application/json',

            success: function (data) {

                var saveStatus = data['statusCode'];
                if (saveStatus == 200) {
                    if (isFromDoc) {
                        getAppointment(doctorId, clinicId, isFromDoc);
                    } else {
                        getAppointment(doctorId, clinicId);
                    }

                }
            },
            error: function (data) {

            }
        });
    });

    //on selecting the slot in current tab
    $("#onDemandList").on('change', function () {

        var clinicId = $("#userId").text();
        var doctorId = $("#selectedDoct").val();
        var liSlot = $(this).val();
        var tokenNo = $(this).find("option:selected").val();
        askForPatientDetails(clinicId, doctorId, liSlot, true, this, tokenNo);

    });

//trigger : on change of status slot in past and future tab(viewCompleteAppt.js)
// uses : validate and fetch the slot on mentioned dates
    $("#futureStatusList,#pastStatusList").on('change', function () {
        var status, startDate, endDate, appendId, pastDate, clinicId, doctorId,strtDate,edDate;

        if (this.id == "pastStatusList") {
            status = $("#pastStatusList").val();
            startDate = $("#pastFrmDatepicker").val();
            strtDate = convertToJsDateObj(startDate);
            endDate = $("#pastToDatepicker").val();
            edDate = convertToJsDateObj(endDate);
            $('#pastStatusList').prop('selectedIndex',0);
            appendId = 'showPastSlot';
            pastDate = true;
        } else {
            status = $("#futureStatusList").val();
            startDate = $("#futFrmDatepicker").val();
            strtDate = convertToJsDateObj(startDate);
            endDate = $("#furToDatepicker").val();
            edDate = convertToJsDateObj(endDate);
            $('#futureStatusList').prop('selectedIndex',0);
            appendId = 'showFutureSlot';
            pastDate = false;
        }

        if ($("#isFromDocLogin").val()) {
            doctorId = $("#userId").text();
            clinicId = $("#select_clinic").val();
        } else {
            clinicId = $("#userId").text();
            doctorId = $("#selectedDoct").val();
        }

        if (status) {
            if (strtDate && edDate) {
                if (strtDate < edDate || strtDate === edDate ) {
                    var url;
                    var jsondata = {"doctorId": doctorId, "clinicId": clinicId, "before": startDate, "after": endDate, "status": status, "pastDate": pastDate};
                    if (status == 9) {
                        url = "api/user/appointment/getSlotsBtwDateForAllStatus";
                    } else {
                        url = "api/user/appointment/getDoctorSlotsBasedOnDateAndStatus";
                    }
                    $("#" + appendId).empty();
                    getSlotOnStatus(jsondata, url, status, appendId, doctorId, clinicId);
                }
                else {
                    $("#" + appendId).empty();
                    $('#msg').empty().prepend('<div class="notification alert-info spacer-t10">' +
                        '<p>To date should be greater than From date</p><a class="close-btn"></a></div>').children(':first').delay(5000).fadeOut(100);
                }

            } else {
                $("#" + appendId).empty();
                $('#msg').empty().prepend('<div class="notification alert-info spacer-t10">' +
                    '<p>Select From and To dates  </p><a class="close-btn"></a></div>').children(':first').delay(5000).fadeOut(100);
            }
        }

    });

    $("form#checkPatientExist").click(function () {
       /* $("#confirmMessage").empty().addClass("hide");*/
        var puserName = $("#pUsrName").val();
        var pUid = $("#pUid").val();
        var doctorId, clinicId, selectedclinic;
        selectedclinic = $("#select_clinic").val();
        if (selectedclinic) {
            clinicId = selectedclinic;
            doctorId = $("#userId").text();
        } else {
            doctorId = $("#selectedDoct").val();
            clinicId = $("#userId").text();
        }

        var date = $("#selectedDate").val();
        var patSelectedDate = dateIndDDMMyyy(date);
        var patSelectedTime = $("#selectedTime").val();
        if (puserName || pUid) {
            var jsondata = {"userName": puserName, "userId": pUid, "doctorId": doctorId, "clinicId": clinicId};
            var url = "common/checkPatientExist";

            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(jsondata),
                contentType: 'application/json',
                mimeType: 'application/json',

                success: function (data) {
                    var saveStatus = data['statusCode'];
                    if (saveStatus == 200) {

                        $("#patientDetailPopUp").show();
                        $("#askForPDetailsPop").hide();
                        $("#PDetailsPopUp").hide();

                        //get the result and assign to the respective id
                        var patDetails = data['patientDetails'];
                        $("#patientImage").attr('src', patDetails['imageUrl']);
                        $("#patientId").text(patDetails['userId']);
                        $("#patName").text(patDetails['flname']);
                        $("#patPhNo").text(patDetails['mobileNo']);
                        $("#patEmail").text(patDetails['email']);
                        $("#patId").val(patDetails['userId']);
                        $("#docId").val(doctorId);
                        $("#clncId").val(clinicId);

                        var docDetails = data['doctorDetails'];
                        $("#patSelectedDate").text(patSelectedDate);
                        $("#patSelectedTime").text(patSelectedTime);
                        $("#docSplityOnPopup").text(docDetails['specialityName']);
                        $("#docNameOnPopup").text(docDetails['doctorName']);
                        $("#clnNameOnPopup").text(docDetails['clinicName']);
                        var loc = '';
                        if (docDetails['address']) {
                            loc += docDetails['address']
                        }
                        if (docDetails['location']) {
                            loc += " " + docDetails['location'];
                        }
                        if (docDetails['city']) {
                            loc += " " + docDetails['city'];
                        }
                        if (loc != '') {
                            $("#clncLocOnPopup").text(loc);
                        }

                    }
                    if (saveStatus == 400) {
                        $('#confirmMessage').removeClass('hide').show().empty().prepend(data['message']).delay(1500).fadeOut(100, function () {
                            $('#confirmMessage').empty();
                        });
                    }
                },
                error: function (data) {

                }
            });
        } else {
            $('#confirmMessage').removeClass('hide').show().empty().prepend("Enter atleast one field").delay(1500).fadeOut(100, function () {
                $('#confirmMessage').empty();
            });
            return false;
        }
    });
    $('#apptclinicPatientForm').submit(function (e) {
        var date = $("#selectedDate").val();
        var patSelectedDate = dateIndDDMMyyy(date);
        var patSelectedTime = $("#selectedTime").val();
        $("#apptclinicPatientForm").find(".register-btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
          $.ajax({
            url: "api/user/register",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(PRegistrationFormToJSON('#apptclinicPatientForm')),
            contentType: 'application/json',
            success: function (data) {
                /* var saveMessage = data['message'];
                 var saveStatus = data['statusCode'];*/
                if (data['statusCode'] == 200) {

                    $("form#clinicPatientForm input").val("");
                    $("#userRole").val("ROLE_PATIENT");
                    $("#patientDetailPopUp").show();
                    $("#askForPDetailsPop").hide();

                    //get the result and assign to the respective id
                    var patDetails = data['patientDetails'];
                    $("#patientImage").attr('src', patDetails['imageUrl']);
                    $("#patientId").text(patDetails['userId']);
                    $("#patName").text(patDetails['flname']);
                    $("#patPhNo").text(patDetails['mobileNo']);
                    $("#patEmail").text(patDetails['email']);
                    $("#patId").val(patDetails['userId']);

                    var docDetails = data['doctorDetails'];
                    $("#patSelectedDate").text(patSelectedDate);
                    $("#patSelectedTime").text(patSelectedTime);
                    $("#docSplityOnPopup").text(docDetails['specialityName']);
                    $("#docNameOnPopup").text(docDetails['doctorName']);
                    $("#clnNameOnPopup").text(docDetails['clinicName']);
                    var loc = '';
                    if (docDetails['address']) {
                        loc += docDetails['address']
                    }
                    if (docDetails['location']) {
                        loc += " " + docDetails['location'];
                    }
                    if (docDetails['city']) {
                        loc += " " + docDetails['city'];
                    }
                    if (loc != ''&&loc!=undefined&&loc!=null) {
                        $("#clncLocOnPopup").text(loc);
                    }else{
                        $("#clncLocOnPopup").text("");
                    }
                } else {
                    $('#pperrorMessage').removeClass('hide').show().empty().prepend(data['message']).delay(1500).fadeOut(100, function () {
                        $('#pperrorMessage').empty();
                    });
                }
                $("#apptclinicPatientForm").find(".register-btn").html('Save');
            },
            error: function (data) {

            }
        });
        e.preventDefault();
    });

    $("#confirmAppt").submit(function (e) {
        e.preventDefault();
        var url = "common/saveConfirmedAppt";
        $("#patientDetailPopUp").find(".submit_btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(LoginFormToJSON('#confirmAppt')),
            contentType: 'application/json',
            success: function (data) {
                var statusCode = data['statusCode'];

                if (data['statusCode'] == 200) {
                    var patientId = $("#patId").val();
                    var doctorId = $("#docId").val();
                    var clinicId = $("#clncId").val();
                    var isCurDate = data['isCurDate'];

                    var flag = false;
                    if (data['role'] == "ROLE_DOCTOR") {
                        flag = true;
                    }
                    if (typeof isCurDate === 'boolean' && isCurDate === true) {

                        getAppointment(doctorId, clinicId, flag);
                    } else {
                        var id = $("#selectedLislot").val();

                        $("." + id).attr({
                            "onclick": "showPatDetailOnSlot(" + doctorId + "," + clinicId + "," + patientId + ", this)",
                            "title": "SCHEDULED",
                            "class": "schedule"
                        });

                        $(".cancel-close1-clinic").trigger('click');

                        $('#msg').empty().prepend('<div class="notification alert-success spacer-t10">' +
                            '<p>' + data['message'] + '</p><a href="#" class="close-btn"></a></div>').children(':first').delay(5000).fadeOut(100);
                    }

                }
                if (data['statusCode'] == 100) {
                    $(".cancel-close1-clinic").trigger('click');
                    $('#msg').empty().prepend('<div class="notification alert-info spacer-t10">' +
                        '<p>' + data['message'] + '</p><a class="close-btn" ></a></div>').children(':first').delay(5000).fadeOut(100);

                }
                $("#patientDetailPopUp").find(".submit_btn").html('Confirm');

            },
            error: function (data) {

            }
        });
    });

//Triggered : on click of radio button ie(multipleDayCancellation or singleDayCancellation)
//uses : if checked on multipleDayCancel tab call chkDocAsCancelMd() and display the tab irrespectively
    $("input[type=radio]").on('click', function () {
        if ($('#radio1').is(':checked')) {
            var doctorId, clinicId, selectedclinic;
            selectedclinic = $("#select_clinic").val();
            if (selectedclinic) {
                clinicId = selectedclinic;
                doctorId = $("#userId").text();
            } else {
                doctorId = $("#selectedDoctCMDS").val();
                clinicId = $("#userId").text();
            }

            $("#showExistCancelDay,#singleDayCancel").hide();
            chkDocAsCancelMd(doctorId, clinicId);
            $("#multipleDayCancel").show();
        } else {
            $("#showExistCancelDay,#showRhideConfirmBox,#multipleDayCancel").hide();
            $("#singleDayCancel").show();

        }
    });

//triggered : on click of cancel in multiple day cancel tab(clinicdoctors.jsp)
//uses : fetch if there is any scheduled slot in selected date to cancel
    $("#cancelDays").on('click', function () {
        var clinicId, doctorId, selectedclinic;
        var startDate = $("#CMDSfrom").val();
        var endDate = $("#CMDSTo").val();
        selectedclinic = $("#select_clinic").val();
        if (selectedclinic) {
            clinicId = selectedclinic;
            doctorId = $("#userId").text();
        } else {
            doctorId = $("#selectedDoctCMDS").val();
            clinicId = $("#userId").text();
        }
        /*var clinicId = $("#userId").text();
         var doctorId = $("#selectedDoctCMDS").val();*/

        startDate = convertToJsDateObj(startDate);
        endDate = convertToJsDateObj(endDate);
        if (endDate && startDate) {
            if (endDate > startDate) {
                var url = "api/user/appointment/checkDayOrSlotsCnclInBtwDates";
                var jsondata = {"doctorId": doctorId, "clinicId": clinicId, "vacaStartDate": startDate, "vacaEndDate": endDate};
                $.ajax({
                    url: url,
                    type: 'POST',
                    dataType: 'json',
                    data: JSON.stringify(jsondata),
                    contentType: 'application/json',

                    success: function (data) {

                        var status = data['statusCode'];
                        if (status == 409) {
                            $('#showRhideConfirmBox').removeClass('hideall').show();
                            $('#showExistCancelDay').addClass('hide');
                            var patientScheduled = data['patientScheduled'];
                            var multipleDayCancel = data['multipleDayCancel'];
                            /* var daySlotCancel = data['daySlotCancel'];*/
                            var message = "";
                            if (patientScheduled.length > 0) {
                                message += patientScheduled.length + " Patient/s has scheduled the appointments <br>";
                            }

                            if (multipleDayCancel.length > 0) {
                                message += "You have already cancelled appointments from ";
                                for (var i = 0; i < multipleDayCancel.length; i++) {
                                    var obj = multipleDayCancel[i];
                                    var vacaStartDate = dateIndDDMMyyy(obj['vacaStartDate']);
                                    var vacaEndDate = dateIndDDMMyyy(obj['vacaEndDate']);
                                    message += vacaStartDate + " to " + vacaEndDate + "<br>";
                                }
                            }

                            if (message != '') {
                                $('#confirmBox').empty().append(message).show();
                            }

                            /*  for (var key in daySlotCancel) {
                             var li = '';
                             var id = 1;
                             var ul = "<div>You have already cancelled the slots </div></div>" +
                             "<div>" + key + "</div><ul id =" + id + "></ul>";
                             for (var i = 0; i < daySlotCancel[key].length; i++) {
                             var obj = daySlotCancel[key][i];
                             li += "<li>" + obj['time'] + "</li>";
                             }
                             if (li != '') {
                             $('#confirmBox').append(ul);
                             $('#' + id).append(li);
                             }
                             id = id + 1;
                             }*/
                        }
                        if (status == 200) {
                            $('#showRhideConfirmBox').removeClass('hideall').show();
                            $('#confirmBox').empty().text("Do you want to cancel this appointment ?").show();
                            $('#upRsavebtn').text("Yes");
                        }
                    },
                    error: function (data) {

                    }
                });
            } else {
                $('#errormessages').empty().removeClass().show().prepend('To date should be greater then From date').delay(5000).fadeOut(100, function () {
                    $('#errormessages').empty();
                });
            }
        } else {
            $('#errormessages').empty().removeClass().show().prepend('Please fill From date and To date').delay(5000).fadeOut(100);
        }
        return true;

    });

//triggered : on click of update/save in multiple day cancel tab(clinicdoctors.jsp)
//uses : save the cancelled date in db
    $("#upRsavebtn").on('click', function () {

        var startDate = $("#CMDSfrom").val();
        var endDate = $("#CMDSTo").val();
        var clinicId, doctorId, selectedClinic;
        selectedClinic = $("#select_clinic").val();

        if (selectedClinic) {
            clinicId = selectedClinic;
            doctorId = $("#userId").text();
        } else {
            doctorId = $("#selectedDoctCMDS").val();
            clinicId = $("#userId").text();
        }

        startDate = convertToJsDateObj(startDate);
        endDate = convertToJsDateObj(endDate);

        var url = "api/user/appointment/saveOrUpdateMultipleDayLeaves";
        var jsondata = {"doctorId": doctorId, "clinicId": clinicId, "vacaStartDate": startDate, "vacaEndDate": endDate};
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsondata),
            contentType: 'application/json',

            success: function (data) {
                var status = data['statusCode'];
                if (status == 200) {
                    $('#messages').empty().removeClass().show().prepend(data['message']).delay(5000).fadeOut(100);
                    $('#showRhideConfirmBox').addClass('hideall').hide();
                    $('#showExistCancelDay').empty().removeClass('hide');
                    $("#radio1").trigger('click');
                }
                $("#CMDSfrom").val('');
                $("#CMDSTo").val('');
            },
            error: function (data) {

            }
        });

    });

    $('#editSlotsBtn').on('click', function () {

        $('ul#doctTimeList li').each(function () {
            var flag = $(this).hasClass('canceledbydoctor');
            $("#selectallCkbx,#cancelSlotsBtn,#backSlotsBtn").show();
            $('#editSlotsBtn').hide();

            if (flag) {
                $(this).find("input[type='checkbox']").prop({
                    checked: true,
                    disabled: false
                });
            } else {
                $(this).find("input[type='checkbox']").prop({
                    disabled: false
                });
            }
        });
    });

    $('#backSlotsBtn').on('click', function () {
        $('ul#doctTimeList li').each(function () {
            var flag = $(this).hasClass('canceledbydoctor');
            $("#selectallCkbx,#cancelSlotsBtn,#backSlotsBtn").hide();
            $('#editSlotsBtn').show();

            if (flag) {
                $(this).find("input[type='checkbox']").prop({
                    checked: false,
                    disabled: true
                });
            } else {
                $(this).find("input[type='checkbox']").prop({
                    disabled: true
                });
            }
        });
    });

    $("#cancelSlotsBtn").off().on("click", function (e) {
        e.preventDefault();
        var cancellength = $("ul#doctTimeList li.canceledbydoctor > input:checkbox:checked").length;
        var cancel = $("ul#doctTimeList li.canceledbydoctor").length;
        if ($("ul#doctTimeList li input:checkbox:checked").length > 0 || cancellength > 0 || cancel >= 0) {

            var url = "api/user/appointment/cancelAppointmentonSlots";
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(CancelSlotsFormToJSON('#cancelSlots')),
                contentType: 'application/json',

                success: function (data) {
                    if (data['statusCode'] == 200) {
                        $('#doctTimeList').empty();
                        $("#showInfoOnSingleDay").hide();
                        $('#SDmessages').prepend('<div class="notification alert-success spacer-t10">' +
                            '<p>' + data['message'] + '</p><a class="close-btn" ></a></div>').children(':first').delay(5000).fadeOut(100, function () {
                            $('#SDmessages').empty();
                        });
                        /* $(".xdsoft_datetimepicker,.xdsoft_noselect,.xdsoft_").hide();*/
                    }
                },
                error: function (data) {

                }
            });
            return true;
        } else {
            $('#errormessages').empty().removeClass().show().prepend('Please select the slot before save').delay(5000).fadeOut(100, function () {
                $('#errormessages').empty();
            });

        }
    });

//triggered : on click of cancellation setting button in doctor login(viewCompleteAppt.js)
//uses : display the cancellation popup by triggering apptCancellationSetting()
    $("#viewDocCnclSetting").on('click', function () {
        var clinicId = $("#select_clinic").val();
        var doctorId = $("#userId").text();
        apptCancellationSetting(doctorId, clinicId, true);
    });
})
;

//Triggered : on click of cancel multiple day tab (clinicdoctors.jsp)
//uses : make ajax call and check doctor as any multiple day leaves,if there display in cancel multiple day tab
function chkDocAsCancelMd(doctorId, clinicId) {

    var url = "api/user/appointment/checkHasAnyMultipleDayCancel";
    var jsondata = {"doctorId": doctorId, "clinicId": clinicId};
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',

        success: function (data) {
            var status = data['statusCode'];
            if (status == 409) {
                var multipleDayCancel = data['multipleDayCancel'];

                if (multipleDayCancel.length > 0) {
                    var vacaStartDate, vacaEndDate, message = '';

                    for (var i = 0; i < multipleDayCancel.length; i++) {
                        var obj = multipleDayCancel[i];
                        vacaStartDate = dateIndDDMMyyy(obj['vacaStartDate']);
                        vacaEndDate = dateIndDDMMyyy(obj['vacaEndDate']);
                        message += "You have already cancelled appointments from ";
                        message += vacaStartDate + " to " + vacaEndDate;
                        var id = obj['id'];
                        $('#showExistCancelDay').empty().append(message + '<input type="button" value="Delete" class="share" style="border:none; margin-left:15px;margin-top:10px;" onclick="deleteMultipleDayCncl(' + clinicId + ',' + doctorId + ',' + id + ')">').removeClass('hide');

                    }

                }
            }
        },
        error: function (data) {

        }
    });

}

//triggered : on click of delete in cancel multiple day tab, (clinicdoctors.jsp)
//uses : make ajax call to delete the row in confirmappointment table based on doctorId,clinicId and most important confirmAppointment table id
function deleteMultipleDayCncl(clinicId, doctorId, id) {

    var url = "api/user/appointment/deleteMultipleDayCancel";
    var jsondata = {"doctorId": doctorId, "clinicId": clinicId, "id": id};

    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',

        success: function (data) {
            var status = data['statusCode'];
            if (status == 200) {
                $('#confirmBox').empty().hide();
                $('#showExistCancelDay').addClass('hide');
                $('#showRhideConfirmBox').addClass('hideall');
                $('#messages').removeClass().show().prepend(data['message']).delay(5000).fadeOut(100, function () {
                    $('#messages').empty();
                });
            }
        },
        error: function (data) {

        }
    });
}


//triggered : onselect of date in single day cancel
//uses : display the slot on selected date
function fetchSlotOnDate(doctorId, clinicId, date) {
    var dateObject = convertToJsDateObj(date);
    var url = "api/user/appointment/getDoctorSlotsOnDate";
    var jsondata = {"doctorId": doctorId, "clinicId": clinicId, "scheduledOn": dateObject};
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',

        success: function (data) {

            if (data['statusCode'] == 200) {
                var slotTime = data['resultedSlots'];
                var docDtls = data['doctorDetails'];

                if (docDtls['specialityName'] != null) {
                    $("#SDdocSplty").text(" - " + docDtls['specialityName']);
                }
                $("#SDdocName").text(docDtls['doctorName']);

                $('#doctTimeList,#SDmessages').empty();
                $('#showInfoOnSingleDay,#editSlotsBtn').show();
                var liSlot = 0;
                var date;
                $.each(slotTime, function (idx, obj) {
                    date = dateIndDDMMyyy(idx);
                    if (!$.isEmptyObject(obj)) {
                        var li = "";
                        $.each(obj, function (i, str) {

                            if (str['status'] != null) {
                                switch (str['status']) {
                                    case "CANCELLED":
                                        li += '<li class="normal-slot canceledbydoctor col-md-3 col-sm-3"><input type="checkbox" class="selectall" disabled name="slots.slotTime" value="' + i + '">' +
                                            '<a title="CANCELLED"  href="#" >' + i + '</a></<input></li>';
                                        break;
                                    case "SCHEDULED":
                                        li += '<li class="normal-slot greere col-md-3 col-sm-3"><input type="checkbox" disabled name="slots.slotTime" class="selectall" value="' + i + '">' +

                                            '<a title="SCHEDULED" href="#" class=" ' + liSlot + '"  id="' + idx + '" onclick="showPatDetailOnSlot(' + doctorId + ',' + clinicId + ', ' + str['patientId'] + ',this)">' + i + '</a></li>';
                                        break;
                                }
                            } else {
                                li += '<li class="normal-slot col-md-3 col-sm-3"><input type="checkbox" disabled class="selectall" name="slots.slotTime" value="' + i + '">' +
                                    '<a href="#">' + i + '</a></input></li>';

                            }
                            liSlot = liSlot + 1;
                        });
                        $('#doctTimeList').append(li);
                    }
                    $('#SDdate').text(date);
                });
            }
            if (data['statusCode'] == 100) {
                var li;
                $('#showInfoOnSingleDay').show();
                $("#selectallCkbx,#editSlotsBtn").hide();
                if (data['vacaStartDate'] != null && data['vacaStartDate'] != undefined && data['vacaEndDate'] != null && data['vacaEndDate'] != undefined) {

                    li = '<p class="normal-slot">' + 'Doctor is already on leave from ' + data['vacaStartDate'] + ' to ' + data['vacaEndDate'] + '</p>';

                } else if (data['scheduledOn'] != null && data['scheduledOn'] != undefined && data['cancelledOn'] != null && data['cancelledOn'] != undefined) {

                    li = '<p class="normal-slot">' + 'Doctor as already cancelled this (' + data['scheduledOn'] + ') day on ' + data['cancelledOn'] + '</p>';

                } else {

                    li = '<p class="normal-slot">' + data['message'] + '</p>';
                }
                $('#SDmessages').empty().append(li);
                $('#SDdate').text("");

            }

        },
        error: function (data) {
        }
    });
    return true;
}


function fetchSlotOnDateHpfollowup(doctorId, clinicId, date) {

    var dateObject = convertToJsDateObj(date);
    var url = "api/user/appointment/getDoctorSlotsOnDate";
    var jsondata = {"doctorId": doctorId, "clinicId": clinicId, "scheduledOn": dateObject};
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',
        success: function (data) {
            var patientId = $("#patient_id").text();
            if (data['statusCode'] == 200) {

                var li = '';
                var date = '';

                $.each(data['resultedSlots'], function (idx, obj) {
                    date = dateIndDDMMyyy(idx);
                    $.each(obj, function (i, ojt) {

                        if (( ojt['status'])) {
                            li += '<li title="NOT AVAILABLE"><a class="schedule">' + i + '</a></li>';
                        } else {
                            //this indicates request from appointment.jsp, onclick of slot show sign-in popup
                            li += '<li title="AVAILABLE"><a class ="confirmApp ' + ojt['tokenNo'] + '" href="javascript:;" id="' + idx + '" onclick="confirmForScheduledAppt(' + doctorId + ',' + clinicId + ',' + patientId + ',' + ojt['tokenNo'] + ',this)">' + i + '</a></li>';
                        }

                    });
                });

                if (li != '') {
                    $("#showHPfollowupSlot,#showslots").show();
                    $("#msg").hide();
                    $("#doctTimeList").empty().append(li);
                    if (date != '') {
                        $("#displDate").empty().text(date);
                    }
                }
            }
            if (data['statusCode'] == 100) {
                $("#showslots").hide();
                $("#showHPfollowupSlot,#msg").show();
                $("#msg").empty().prepend("<div id='errorMessage'>" + data['message'] + "</div>").children(':first').delay(5000).fadeOut(100);
            }
        },
        error: function (data) {

        }
    });
}


function confirmForScheduledAppt(doctorId, clinicId, patientId, tokenNo, these) {
    $("#confirmMessage").empty().addClass("hide");
    $("#apptCancelCnfmPopup").addClass("dailog-show");
    $(".cancelapp").off().on('click', function () {
        var retVal = this.id;
        $("#apptCancelCnfmPopup").removeClass("dailog-show");
        if (retVal == "Yes") {

            $("#loadingDiv").addClass("dailog-show");

            var date = these.id;
            var time = $(these).text();
            var url = "common/saveConfirmedAppt";
            var jsondata = {"doctorId": doctorId, "clinicId": clinicId, "tokenNo": tokenNo, "date": date, "time": time, "patientId": patientId};

            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(jsondata),
                contentType: 'application/json',
                success: function (data) {
                    var statusCode = data['statusCode'];
                    if (data['statusCode'] == 200) {

                        $("." + tokenNo).attr({
                            "onclick": "showPatDetailOnSlot(" + doctorId + "," + clinicId + "," + patientId + ", this)",
                            "title": "SCHEDULED",
                            "class": "schedule"
                        });
                        if ($("#msg").length > 0) {
                            $("#confirmMessage").empty().addClass("hide");
                        }
                        $('#msg').show().prepend("<div id='confirmMessage' style='display: block !important;'>" + data['message'] + "</div>").children(':first').delay(5000).fadeOut(100);


                    }
                    if (data['statusCode'] == 100) {
                        if ($("#msg").length > 0) {
                            $("#errorMessage").empty();
                        }
                        $('#msg').show().prepend("<div id='errorMessage'  style='display: block !important;'>" + data['message'] + "</div>").children(':first').delay(5000).fadeOut(100);

                    }
                    $("#loadingDiv").removeClass("dailog-show");

                },
                error: function (data) {

                }
            });

        }
    });
}

function getSlotOnStatus(jsondata, url, status, toappendId, doctorId, clinicId) {

    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',

        success: function (data) {

            var saveStatus = data['statusCode'];
            if (saveStatus == 200) {
                var id = 0;
                var liSlot = 1;
                $.each(data['resultedSlots'], function (idx, obj) {
                    if (!$.isEmptyObject(obj)) {
                        var li = '';
                        var date = dateIndDDMMyyy(idx);
                        var div = '<div class="form-wizard doctor-info">' +
                            '<div class="row">' +
                            '<div class="slot-time">' +
                            '<div class="demo-date">' + date + '</div>' +
                            '</div>' +
                            '<div class="slot-time-demo">' +
                            '<div class="demo-list">' +
                            '<ul class="ulslots" id="' + toappendId + id + '" >' +

                            '</ul>' +
                            '</div>' +
                            '</div>' +
                            '</div>' +
                            '</div>';

                        $.each(obj, function (i, str) {
                            if (status != 9) {
                                i = str['time'];
                            }

                            if (str['status'] != null) {
                                switch (str['status']) {

                                    case "CANCELDAY" :
                                        li += '<li>' + str['message'] + '</a></li>';
                                        break;
                                    case "CANCELLED" :
                                        li += '<li><a class="cancledslotclr" title="CANCELLED" href="#">' + i + '</a></li>';
                                        break;
                                    case "SCHEDULED" :
                                        li += '<li><a class="schedule" title="SCHEDULED" href="#" id="' + idx + '" onclick="showPatDetailOnSlot(' + doctorId + "," + clinicId + "," + str['patientId'] + ',this)">' + i + '</a></li>';
                                        break;
                                    case "NOSHOW" :
                                        li += '<li><a class="noshowslotclr" title="NOSHOW" id="' + idx + '" onclick="showPatDetailOnSlot(' + doctorId + "," + clinicId + "," + str['patientId'] + ',this)" >' + i + '</a></li>';
                                        break;
                                    case "COMPLETED" :
                                        li += '<li><a class="compltedslotclr" title="COMPLETED"  href="#" id="' + idx + '" onclick="showPatDetailOnSlot(' + doctorId + "," + clinicId + "," + str['patientId'] + ',this)">' + i + '</a></li>';
                                        break;
                                    case "ONLEAVE":
                                        var before = dateIndDDMMyyy(str['before']);
                                        var after = dateIndDDMMyyy(str['after']);
                                        li += '<li><span>Doctor is on leave from ' + before + ' to ' + after + '</span></li>';
                                        break;
                                }
                            } else {

                                if (status = 9) {
                                    if (toappendId === 'showPastSlot') {

                                        li += '<li><a class="no-show" title="Not Scheduled" id="' + idx + '" onclick="showPatDetailOnSlot(' + doctorId + "," + clinicId + "," + str['patientId'] + ',this)">' + i + '</a></li>';
                                    } else {
                                        li += '<li><a class="no-scheduled ' + liSlot + '" title="Available" href="#" id="' + idx + '" onclick="askForPatientDetails(' + clinicId + "," + doctorId + "," + liSlot + ',false,this,' + str['tokenNo'] + ')">' + i + '</a></li>';
                                    }
                                }
                            }
                            liSlot += 1;
                        });
                        if (li != '') {

                            $("#" + toappendId).append(div);
                            $("#" + toappendId + id).append(li);

                        }
                    }
                    id = id + 1;
                });
            }
            if (saveStatus == 100 || saveStatus == 102) {
                if (toappendId === 'showFutureSlot') {
                    $('#futmsg').empty().prepend('<div class="notification alert-info spacer-t10">' +
                        '<p>' + data['message'] + '</p><a class="close-btn"></a></div>').children(':first').delay(3000).fadeOut(100, function () {
                    });
                } else {
                    $('#pastmsg').empty().prepend('<div class="notification alert-info spacer-t10">' +
                        '<p>' + data['message'] + '</p><a class="close-btn" ></a></div>').children(':first').delay(3000).fadeOut(100, function () {
                    });
                }

            }

        },
        error: function (data) {

        }
    });
}


//to display the patient search popup
function askForPatientDetails(clinicId, doctorId, lislot, isCur, these, tokenNo) {
    $("#appointmentclinic").addClass('dailog-show');
    $("#selectedLislot").val(lislot);
    $("#isFromCurDate").val(isCur);
    $("#tokenNo").val(tokenNo);
    if (isCur) {
        var time = $(these).find("option:selected").text();
        var date = $(these).find("option:selected").attr('id');
        $("#selectedTime").val(time);
        $("#selectedDate").val(date);
    } else {

        $("#selectedTime").val($(these).text());
        $("#selectedDate").val(these.id);
    }
}


function showPatDetailOnSlot(docId, clncId, patId, these, showNewHp) {
    var time, d;
    if (showNewHp || showNewHp == false) {
        var id = these.id;
        time = $("#time" + id).text();
        d = $("#date" + id).val();
    } else {
        time = $(these).text();
        d = these.id;
    }

    var jsondata = {"doctorId": docId, "clinicId": clncId, "patientId": patId, "date": d, "time": time};
    var url = "api/user/appointment/getPatientsDetails";
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',
        success: function (data) {
            var saveStatus = data['statusCode'];
            if (saveStatus == 200) {

                var res = data['patInfo'];
                $("#showPatDtlsPopup").addClass('dailog-show');
                var date = dateIndDDMMyyy(d);
                $("#patImage").attr("src", res['imageUrl']);
                $("#patDtlsDate").text(date);
                $("#patDtlsTime").text(time);
                $("#patDtlsId").text(res['userId']);
                $("#patDtlsName").text(res['flname']);
                $("#patDtlsPhNo").text(res['mobileNo']);
                $("#patDtlsEmail").text(res['email']);
                if (res['patRFV'] != null) {
                    $("#patDtlsRFV").text(res['patRFV']);
                }
                if (showNewHp) {
                    $("#newHP").show();
                }
            }
        },
        error: function (data) {

        }
    });
}

//used to close the showPatDetail and ApptCancellationSetting popup
$(".close-showPatDetail").on('click', function () {
    $("#showPatDtlsPopup").removeClass('dailog-show');
});

$(".cancel-close1-clinic").on('click', function () {
    $("#confirmMessage").empty().addClass("hide");
    $("#patientDetailPopUp").hide();
    $("#askForPDetailsPop,#PDetailsPopUp").show();
    $('#checkPatientExist').children().find('input:text').val('');
    $("#appointmentclinic").removeClass('dailog-show');
});

$(".cancel-close").click(function () {
    $("#apptCancelCnfmPopup").removeClass("dailog-show");

});

//trigger : onclick of cancel in clinicDoctor.js and on click of cancellation setting button in doctor login(viewCompleteAppt.js)
// uses : to show ApptCancellationSetting popup
function apptCancellationSetting(doctorId, clinicId, isFromDoc) {
    $("#cancel-date-schedule").addClass('dailog-show');
    $("#selectedDoctCMDS").val(doctorId);

    var dateToday, maxDateToshow, curDay;
    if (isFromDoc) {
        dateToday = $("#serverDate").val();
        maxDateToshow = $("#maxDateToShow").val();
        curDay = $("#curDate").val();
        $('#radio1').prop("checked", true).trigger("click");
    } else {
        dateToday = $("#serverDateCMDS").val();
        maxDateToshow = $("#maxDateToShowCMDS").val();
        curDay = $("#serDateCMDS").val();
    }

    $("#CMDSfrom,#CMDSTo").datetimepicker({
        changeYear: true,
        changeMonth: true,
        timepicker: false,
        scrollMonth: true,
        minDate: dateToday,
        maxDate: maxDateToshow,
        onSelect: function (selectedDate) {
            var option = this.id == "from" ? "minDate" : "maxDate",
                instance = $(this).data("datepicker"),
                date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
        },

        format: 'd/m/Y'
    });


    $("#CSdatepicker").datetimepicker({
        changeYear: true,
        changeMonth: true,
        scrollMonth: true,
        minDate: curDay,
        maxDate: maxDateToshow,
        onSelect: function (selectedDate) {
            var option = this.id == "from" ? "minDate" : "maxDate",
                instance = $(this).data("datepicker"),
                date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
        },
        onSelectDate: function () {
            var date = $("#CSdatepicker").val();
            $("#clinicId").val(clinicId);
            $("#doctorId").val(doctorId);
            $("#cancelledOn").val(date);
            $("#selectallCkbx,#cancelSlotsBtn,#backSlotsBtn").hide();
            $("#selectall").prop("checked", false);
            fetchSlotOnDate(doctorId, clinicId, date);
        },
        timepicker: false,
        closeOnDateSelect: true,
        format: 'd/m/Y'

    });
}
