$(function () {
    $("#changeSetEfectFrm").datetimepicker({
        changeYear: true,
        changeMonth: true,
        scrollMonth: false,
        minDate: new Date(),
        timepicker: false,
        format: 'd/m/Y'
    });

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $('body').on('change', ':checkbox', function () {
        var th = $(this), id = th.prop('id');
        if (th.is(':checked')) {
            $(':checkbox[id="' + id + '"]').not($(this)).prop('checked', false);
        }
    });

    $("#addMoreSlots").on('click',function () {
        var count = $("#count").val();
        if (count <= 6) {
            ++count;
            var val = "<div id='singleSlot" + count + "' class='form-wizard'>  <div class='slots'>" +
                "<div class='clearfix'></div>" +
                "<div class='row'><div class='col-md-12 no-pad marginbtm20 seesionslots'>" +
                $(".commonslot").html() +
                "<div class='cancel cslots' onclick='closeSlot(" + count + ")'><a>Cancel</a></div>" +
                "<div class='col-md-6'><div class='col-md-12 session1'><div class='col-md-12 session1'>Select Day </div></div>" +
                "<div class='strike-pad-info'><div class='row'><div class='label-demo-class days'>" +
                "<label class='demo-dates'><input type='checkbox' id='mon' value='" + count + "'/> Mon</label>" +
                "<label class='demo-dates'><input type='checkbox' id='tue' value='" + count + "'/> Tue</label>" +
                "<label class='demo-dates'><input type='checkbox' id='wed' value='" + count + "'/> Wed</label>" +
                "<label class='demo-dates'><input type='checkbox' id='thu' value='" + count + "'/>Thu</label>" +
                "<label class='demo-dates'><input type='checkbox' id='fri' value='" + count + "'/> Fri</label>" +
                "<label class='demo-dates'><input type='checkbox' id='sat' value='" + count + "'/> Sat</label>" +
                "<label class='demo-dates'><input type='checkbox' id='sun' value='" + count + "'/> Sun</label></div></div></div>" +
                "</div></div></div></div></div>";

            $("#mutipleSlots").append(val);
            $("#count,#slotCount").val(count);
        }
    });

    $("#session2_toggle").click(function () {
        $("#session_two").slideToggle("slow");
    });

    $("#consultationFeeId,#currencyId").blur(function () {
        var consultationFeeId = $("#consultationFeeId").val();
        var currencyId = $("#currencyId").val();
        var consultationFee = consultationFeeId + " " + currencyId;
        if (consultationFeeId != "") {
            $("#cpconsultationFee").val(consultationFee);
        }
    });
    $("#changeClinicSettings,#changeVisitSettings").click(function (e) {
        e.preventDefault();
        $("#show_settings").addClass("hide");
        $("#change_settings").removeClass("hide");
    });
    $("#cancelClinicSettings").click(function () {
        $("#show_settings").removeClass("hide");
        $("#change_settings").addClass("hide");
    });

    $("#doctorTimings").submit(function (e) {
        e.preventDefault();
        $("#doctorTimings").find(".submit_btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
        var effectedDate = $("#changeSetEfectFrm").val();
        var st_1 = $('.st_1').val();
        var et_1 = $('.et_1').val();
        var st_2 = $('.st_2').val();
        var et_2 = $('.et_2').val();
        var flag = true;
        var days = $("[type='checkbox']:checked").length;
        var session1 = $('#timingserror1').val();
        var session2 = $('#timingserror2').val();
        var session3 = $('#timingserror3').val();
        if(session1 == 'true'){
            flag = false;
            $('#successMessages').empty().prepend('<div class="notification alert-error spacer-t10">' +
                '<p>In Session-1 To Time should be greater than From Time </p></div>').children(':first').delay(5000).fadeOut(100, function () {
                $('#successMessages').empty();
            });
            $("#doctorTimings").find(".submit_btn").html('Save');
        }else if(session2 == 'true'){
            flag = false;
            $('#successMessages').empty().prepend('<div class="notification alert-error spacer-t10">' +
                '<p>In Session-1 To Time should be greater than From Time </p></div>').children(':first').delay(5000).fadeOut(100, function () {
                $('#successMessages').empty();
            });
            $("#doctorTimings").find(".submit_btn").html('Save');
        }else if(session3 == 'true'){
            flag = false;
            $('#successMessages').empty().prepend('<div class="notification alert-error spacer-t10">' +
                '<p>Session-1 To Time should be greater than Session-2 From Time </p></div>').children(':first').delay(5000).fadeOut(100, function () {
                $('#successMessages').empty();
            });
            $("#doctorTimings").find(".submit_btn").html('Save');
        }

        if(flag){
            if((((st_1 && et_1) || (st_2 && et_2)) && effectedDate && days > 0) ){
                flag = false;
                var cid = $("#select_clinic").val();
                var userId = $("#userId").text();
                if (cid != null && cid != 'undefined' && cid != '') {
                    $("#cdtdoctorId").val(userId);
                    $("#cdtclinicId").val(cid);
                } else {
                    $("#cdtdoctorId").val($("#clinicDoctorList").val());
                    $("#cdtclinicId").val(userId);
                }
                userId = $("#cdtdoctorId").val();
                cid = $("#cdtclinicId").val();

                var dateObject = convertToJsDateObj(effectedDate);
                var url = "api/user/appointment/getAllScheduledApp";
                var jsondata = {"doctorId": userId, "clinicId": cid,"scheduledOn": dateObject};
                $.ajax({
                    url: url,
                    type: 'POST',
                    dataType: 'json',
                    data: JSON.stringify(jsondata),
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    success: function (data) {
                        var status = data['statusCode'];
                        switch (status){
                            case 200 : $("#apptCancelCnfmPopup").addClass("dailog-show");
                                $("#anyNote").empty().text(data['message']);
                                break;
                            case 100 : $("#apptCancelCnfmPopup").addClass("dailog-show");
                                break;
                        }
                    },error: function (data) {
                        window.location = 'invalidSession';
                    }
                });
            }
            if((((!st_1 && !et_1) && (!st_2 && !et_2)) && !effectedDate)){
                flag = false;
                $("#apptCancelCnfmPopup").addClass("dailog-show");
            }
            if(flag){
                $('#successMessages').empty().prepend('<div class="notification alert-error spacer-t10">' +
                    '<p>Please select the effected date </p><span class="close-btn"></span></div>').children(':first').delay(3000).fadeOut(100, function () {
                    $('#successMessages').empty();
                    $("#doctorTimings").find(".submit_btn").html('Save');
                });
            }
        }
    });

    $(".cancelapp").off().on('click', function () {
        var retVal = this.id;
        $("#apptCancelCnfmPopup").removeClass("dailog-show");
        if (retVal == "Yes") {
            var cid = $("#select_clinic").val();
            var userId = $("#userId").text();
            if (cid != null && cid != 'undefined' && cid != '') {
                $("#cdtdoctorId").val(userId);
                $("#cdtclinicId").val(cid);
            } else {
                $("#cdtdoctorId").val($("#clinicDoctorList").val());
                $("#cdtclinicId").val(userId);
            }

            setSlotValues("mon");
            setSlotValues("tue");
            setSlotValues("wed");
            setSlotValues("thu");
            setSlotValues("fri");
            setSlotValues("sat");
            setSlotValues("sun");
            var url = "api/user/clinic/saveSettings";
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(DTimingsFormToJSON("#doctorTimings")),
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {

                    var saveStatus = data['statusCode'];

                    if (saveStatus == 200) {
                        $("#changeSetEfectFrm").datetimepicker('destroy');
                        var settings = data['settings'];
                        var specialities = data['specialities'];
                        var timings = data['timings'];
                        var newTimings = data['newtimings'];
                        var changeSetEfectFrm = data['changeSetEfectFrm'];
                        getSettingData(settings, specialities, timings,changeSetEfectFrm,newTimings);

                        $("#show_settings").removeClass("hide");
                        $("#change_settings").addClass("hide");
                        $('#successMessages').empty().prepend('<div class="notification alert-success spacer-t10">' +
                            '<p>' + data['message'] + '</p><span class="close-btn"></span></div>').children(':first').delay(3000).fadeOut(100, function () {
                            $('#successMessages').empty();
                        });

                    } else {
                        $('#successMessages').empty().prepend('<div class="notification alert-error spacer-t10">' +
                            '<p>' + data['message'] + '</p><span class="close-btn"></span></div>').children(':first').delay(3000).fadeOut(100, function () {
                            $('#successMessages').empty();
                        });
                    }
                },
                error: function (data) {
                    window.location = 'invalidSession';
                }
            });
        }
        $("#doctorTimings").find(".submit_btn").html('Save');
    });

    $(".et_1").on('change',function(){

        var st1 = $('.st_1').val();
        var et1 = $('.et_1').val();
        var st2 = $('.st_2').val();
        if(st1 && et1 ){
            if(parseInt(st1) > parseInt(et1)){
                $('#successMessages').empty().prepend('<div class="notification alert-error spacer-t10">' +
                    '<p>In Session-1 To Time should be greater than From Time </p></div>').children(':first').delay(5000).fadeOut(100, function () {
                    $('#successMessages').empty();
                });
                $("#timingserror1").val(true);
            }else if(parseInt(st1) == parseInt(et1)){
                $('#successMessages').empty().prepend('<div class="notification alert-error spacer-t10">' +
                    '<p>In Session-1 To Time and From Time should not be equal </p></div>').children(':first').delay(5000).fadeOut(100, function () {
                    $('#successMessages').empty();
                });
                $("#timingserror1").val(true);
            }
            else{
                $("#timingserror1").val(false);
            }
        }

        if(st2 && et1 ){
            if(parseInt(et1) > parseInt(st2)){
                $('#successMessages').empty().prepend('<div class="notification alert-error spacer-t10">' +
                    '<p>Session-1 To Time should be greater than Session-2 From Time </p></div>').children(':first').delay(5000).fadeOut(100, function () {
                    $('#successMessages').empty();
                });
                $("#timingserror3").val(true);
            }else{
                $("#timingserror3").val(false);
            }
        }
    });

    $(".et_2").on('change',function(){
        var st2 = $('.st_2').val();
        var et2 = $('.et_2').val();
        if(st2 && et2 ){
            if(parseInt(et2) < parseInt(st2)){
                $('#successMessages').empty().prepend('<div class="notification alert-error spacer-t10">' +
                    '<p>In Session-2 To Time should be greater than From Time </p></div>').children(':first').delay(5000).fadeOut(100, function () {
                    $('#successMessages').empty();
                });
                $("#timingserror2").val(true);
            }else if(parseInt(st2) == parseInt(et2)){
                $('#successMessages').empty().prepend('<div class="notification alert-error spacer-t10">' +
                    '<p>In Session-2 To Time and From Time should not be equal </p></div>').children(':first').delay(5000).fadeOut(100, function () {
                    $('#successMessages').empty();
                });
                $("#timingserror2").val(true);
            }
            else{
                $("#timingserror2").val(false);
            }
        }
    });

    $(".st_2").on('change',function(){
        var st2 = $('.st_2').val();
        var et1 = $('.et_1').val();
        if(st2 && et1 ){
            if(parseInt(st2) <= parseInt(et1)){
                $('#successMessages').empty().prepend('<div class="notification alert-error spacer-t10">' +
                    '<p>Session-1 To Time should be greater than Session-2 From Time </p><span class="close-btn"></span></div>').children(':first').delay(5000).fadeOut(100, function () {
                    $('#successMessages').empty();
                });
                $("#timingserror3").val(true);
            }else{
                $("#timingserror3").val(false);
            }
        }
    });

});

function DTimingsFormToJSON(form) {
    var array = jQuery(form).serializeArray();

    var json = {};
    var parentName;
    var newVal;

    jQuery.each(array, function () {

        parentName = this.name;
        newVal = this.value;

        json[parentName] = newVal || '';
    });

    return json;
}
$(".cancel-close").click(function () {
    $("#apptCancelCnfmPopup").removeClass("dailog-show");
});