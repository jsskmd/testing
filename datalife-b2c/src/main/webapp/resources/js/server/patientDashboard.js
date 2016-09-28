$(document).ready(function () {
    $('.popbox').popbox();
});

$(function () {
    "use strict";
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    $(".cancel-available").click(function () {
        $("#not_available").removeClass("dailog-show");
        $("#teleconsultInfo").removeClass("dailog-show");
    });

    $(".cancel-close").click(function () {
        $("#apptCancelCnfmPopup").removeClass("dailog-show");
    });

    $("#new_consultation").click(function (e) {
        e.preventDefault();
        $(".xdsoft_datetimepicker .xdsoft_ .xdsoft_noselect,#errormessages").addClass('hide');
        $("ul.mobile-sub li a").removeClass("active");
        $(this).addClass("active");
        var url = "api/user/patient/validateNewConsultation";
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data:$("#userId").text(),
            contentType: 'application/json',
            success: function (data) {
                switch(data['statusCode']){
                    case 400 :
                        $('#errormessages').removeClass('hide').show().empty().prepend(data['message']).delay(5000).fadeOut(100, function () {
                            $('#errormessages').empty();
                        });
                        break;
                    case 200 :
                        $("#newConsultation").addClass("dailog-show");
                        break;
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });

    });

    $("#cancel-common").click(function () {
        $("#s_successMessage").removeClass("dailog-show");
    });

    $(".cancel-consultation").click(function () {
        $("#newConsultation").removeClass("dailog-show");
    });
    $("#newConsultationForm").submit(function (e) {
        e.preventDefault();
        $("#newConsultation").removeClass("dailog-show");
        getConsultation($("#userId").text(), $("#con_doctorName").val(), $("#con_clinicName").val(), $("#con_mobile").val(),$("#con_mciNumber").val());
    });

    $("#vitals").click(function (e) {
        $(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_").addClass('hide');
        $("ul.mobile-sub li a").removeClass("active");
        $(this).addClass("active");
        var url = "patient/vitals";
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                $('#ajaxloaddiv').load(url, function () {
                    var date = getDate();
                    $("#todayDate").text(date);
                    $("#pop_date").val(date);
                    $("#pop_monitored").val(false);
                    $("#pop_userId").val($("#userId").text());
                });
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
        e.preventDefault();
    });

    $("#patient_profile").off().on('click',function (e) {
        $(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_").addClass('hide');
        $("ul.mobile-sub li a").removeClass("active");
        $(this).addClass("active");
        var url = "api/user/patient/basicInfo";
        var userId = $("#userId").text();
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
                    $('#ajaxloaddiv').load("patient/profile", function () {
                        $("#at_userId").val(userId);
                        getBasicInfo(data);
                    });
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
        e.preventDefault();
    });

    $("#bills").off().on('click',function (e) {
       /* $("#view_messenger").getNiceScroll().remove();*/
        $(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_").addClass('hide');
        $("ul.mobile-sub li a").removeClass("active");
        $(this).addClass("active");
        $('#billFiles').html("");
        var url = "api/user/records/getBills";
        var userId = $("#userId").text();
        var jsondata = {"userId": userId};
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsondata),
            contentType: 'application/json',
            success: function (data) {
                $('#ajaxloaddiv').load("patient/bills", function () {
                    $("#billUserId").val(userId);
                    $("#totalBill").text(data['total']);
                    getBills(data);
                    $("#billsTable_info").text("Showing 1 to 5 of " + data['count']);
                    if (data['count'] < 5) {
                        $("#billsTable_info").text("Showing 1 to " + data['count'] + " of " + data['count']);
                    }
                });
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
        e.preventDefault();
    });

    $("#records").click(function (e) {
        $(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_").addClass('hide');
        $("ul.mobile-sub li a").removeClass("active");
        $(this).addClass("active");
        $('#myrecords').html("");
        var url = "api/user/records/getFiles";
        var userId = $("#userId").text();
        var sendType = $("#recordType").val();
        var jsondata = {"userId": userId, "sendType": "hospital"};
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

                    $('#ajaxloaddiv').load("patient/records", function () {
                        getRecords(data);
                        $("#mybills_info").text("Showing 1 to 5 of " + data['count']+" entries");
                        if (data['count'] < 5) {
                            $("#mybills_info").text("Showing 1 to " + data['count'] + " of " + data['count']+" entries");
                        }
                    });
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
        e.preventDefault();
    });

    $("#serviceRequestmenu").off().on('click','.not_available',function(){
        $(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_").addClass('hide');
        $("ul.mobile-sub li a").removeClass("active");
        $("#serviceRequest").addClass("active");
        var userId = $("#userId").text();
        var services = this.id;
        switch(services){
            case "secondOpinion":
                $('#ajaxloaddiv').load("service/secondOpinion", function () {
                    $("#records_datepicker").datetimepicker({
                        format: 'd/m/Y H:i'
                    });
                    $("#referral_userId").val(userId);
                    $("#recordUserId").val(userId);
                });
                break;
            case "teleConsultation":
                $("#teleconsultInfo").addClass("dailog-show");
                break;
            case "surgeryReferral" :
                $('#ajaxloaddiv').load("service/surgeryReferral", function () {
                    $("#surgery_userId").val(userId);
                    $(".has").val(userId);
                });
                break;
            case "pharmacy":
            case "diagnosticLabs":
                $('#ajaxloaddiv').load("service/lab", function (e) {
                    $("#lab_userId,#pharmacy_userId").val(userId);
                    $("#services").val(services);
                    var sendType = "hospital";
                    if("pharmacy" == services){
                        sendType = "prescription";
                        $("#sevicename").text("Pharmacy");
                        $("#labrequestForm").addClass('hide');
                        $("#pharmacyrequestForm").removeClass('hide');
                    }else{
                        sendType = "laborder";
                    }
                    $(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_").addClass('hide');
                    var url = "api/user/records/getFiles";
                    var jsondata = {"userId": userId, "sendType": sendType};
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

                                $('#recordUpload').load("patient/shareRecords", function () {
                                    $("#typeofrecords").val(sendType);
                                    sendType = $("#typeofrecords").find("option:selected").text();
                                    $("#record_type").text(sendType);
                                    getShareRecords(data);
                                    $("#mybills_info").text("Showing 1 to 5 of " + data['count'] + " entries");
                                    if (data['count'] < 5) {
                                        $("#mybills_info").text("Showing 1 to " + data['count'] + " of " + data['count'] + " entries");
                                    }
                                });
                            }
                        },
                        error: function (data) {
                            window.location = 'invalidSession';
                        }
                    });
                    e.preventDefault();

                });

                break;

            case "rehab":
                $("#not_available").addClass("dailog-show");
                break;
        }
    });

    $("#showUploadPopup").off().on('click',function () {
        $("#uploadRecordsPopup").addClass('dailog-show');
        $('#ajaxloaddiv').load("patient/records",function(){
            $("#uploadRecords").addClass("hide");
            var userId = $("#userId").text();
            $("#recordUserId").val(userId);
            $("#fileType").val("0");
            $("#datepicker,#chiefcomplaint,#diagnosis").val("");
            $("#recordSpecialities").val(1);
        });
    });

});

function getRecords(data) {

    $(".dataSize").text(data['size']);
    var tr = "";
    /*alert(JSON.stringify(data['requestedFiles'].length));*/
    $.each(data['requestedFiles'], function (idx, obj, _blank) {
        var speciality = "-";
        if (obj['speciality'] != null && obj['speciality'] != "") {
            var str = obj['speciality'];
            speciality = str.replace(/[^a-zA-Z ]/g, "");
        }
        var cc = "-";
        if (obj['chiefComplaint'] != null && obj['chiefComplaint'] != '' && obj['chiefComplaint'] != 'undefined') {
            cc = obj['chiefComplaint'];
        }
        var dg = "-";
        if (obj['diagnosis'] != null && obj['diagnosis'] != '' && obj['diagnosis'] != 'undefined') {
            dg = obj['diagnosis'];
        }
        tr += '<tr style="text-align:center;">' +
            '<td><input type="checkbox" class="select" name="selectedRecords" value="' + obj['fileId'] + '"/></td>' +
            '<td>' + getDateFromSeconds(obj['encounterDate']) + '</td>' +
            '<td style="text-align:center;">' + speciality + '</td>' +
            '<td>' + cc + '</td>' +
            '<td style="text-align:center;"><a href="#" class="specality-news">' + dg + '</a></td>' +
            '<td style="text-align:center;"><button type="button" id="' + obj['fileName'] + '" class="recordsview View" value="' + obj['vurl'] + '"><i class="icon-folder"></i> View</button>' +
            '<button type="button" class="shareRecord share" id="' + obj['fileId'] + '"><i class="icon-export"></i> Share</button>' +
            '<input type="hidden" id="alfrescoTicket" value="' + data['ticket'] + '"/></td>' +
            '<td style="text-align:center;"><button type="button"  class="view-trash moveRecord " id="' + obj['fileId'] + '" > <i class="icon-move" ></i> Move to <span class="caret"></span></button>' +
            '<a  download target="_blank" href="' + obj['vurl'] + '" ><i class="icon-down-circled info-demo icon-join"></i></a>'+
            '</td></tr>';
    });

    $('#myrecords').append(tr);
    $('#mybills').dataTable({
        "bDestroy": true
    }).fnDestroy();
    $('#mybills').dataTable({
        "responsive": true,
        "pagingType": "full_numbers",
        "order": [],
        "columnDefs": [
            { "targets": [0, 1, 2, 3, 4, 5, 6], "orderable": false }
        ],
        "language": {
            "emptyTable": "No Records Found !!!"
        }
    });
}

function getLabRequestRecords(data) {
    $(".dataSize").text(data['size']);
    var tr = "";
        $.each(data['requestedFiles'], function (idx, obj) {
            var speciality = "NM";
            if (obj['speciality'] != null && obj['speciality'] != "") {
                var str = obj['speciality'];
                speciality = str.replace(/[^a-zA-Z ]/g, "");
            }
            var cc = "-";
            if (obj['chiefComplaint'] != null && obj['chiefComplaint'] != '' && obj['chiefComplaint'] != 'undefined') {
                cc = obj['chiefComplaint'];
            }
            var dg = "-";
            if (obj['clinicName'] != null && obj['clinicName'] != '' && obj['clinicName'] != 'undefined') {
                dg = obj['clinicName'];
            }
            tr += '<tr style="text-align:center;">' +
                '<td><input type="checkbox" class="select" name="selectedRecords" value="' + obj['fileId'] + '"/></td>' +
                '<td>' + getDateFromSeconds(obj['encounterDate']) + '</td>' +
                '<td style="text-align:center;">' + speciality + '</td>' +
                '<td>' + cc + '</td>' +
                '<td style="text-align:center;"><a href="#" class="specality-news">' + dg + '</a></td>' +
                '<td style="text-align:center;"><button type="button" id="' + obj['fileName'] + '" class="recordsview View" value="' + obj['vurl'] + '"><i class="icon-folder"></i> View</button>' +
                /* '<button type="button" class="shareRecord share" id="' + obj['fileId'] + '"><i class="icon-export"></i> Share</button>' +*/
                '<input type="hidden" id="alfrescoTicket" value="' + data['ticket'] + '"/></td>' +
                '</tr>';
        });

    $('#myrecords').append(tr);
    $('#myrecordstable').dataTable({
        "bDestroy": true
    }).fnDestroy();
    $('#myrecordstable').dataTable({
        "responsive": true,
        "pagingType": "full_numbers",
        "order": [],
        "columnDefs": [
            { "targets": [0, 1, 2, 3, 4, 5, 6], "orderable": false }
        ],
        "language": {
            "emptyTable": "No Records Found !!!"
        }

    });

}

function getBills(data) {
    $(".dataSize").text(data['size']);
    var tr = "";
    $.each(data['bills'], function (idx, obj) {
        var vUrl = obj['vUrl'];

        tr += '<tr>' +
            '<td>' + getOnlyDateFromSeconds(obj['date']) + '</td>' +
            '<td>' + obj['paidTo'] + '</td>' +
            '<td>' + obj['paidFor'] + '</td>' +
            '<td>' + obj['amount'] + '</td>' +
            '<td  class="billsanchor"><a class="uploadBill noneback" title="Upload Records" id="' + obj['id'] + '"><i class="icon-upload "></i><span style="display:block"> upload</span></a>';

        if (vUrl != null && vUrl != '' && vUrl != 'undefined') {
            tr += '<a href="' + obj['vUrl'] + '" class="viewBill noneback" title="View Records" id="' + obj['vUrl'] + '"><i class="icon-folder "></i><span style="display:block">View</span></a>'+
                '<a  download target="_blank" href="' + obj['vUrl'] + '" title="Download Record" ><i class="icon-download info-demo downloadbill noneback"></i></a>';
        } else {
            tr +='<a href="' + obj['vUrl'] + '" class="viewBill noneback" title="View Records" id="' + obj['vUrl'] + '"><i class="icon-folder "></i><span style="display:block">View</span></a>'+
            '<a title="Download Record"><i class="icon-download info-demo downloadbill noneback"></i> </a>';
        }

        tr += '</td>' +
            '</tr>';
    });
    $('#billFiles').append(tr);
    $('#billsTable').dataTable({
        "bDestroy": true
    }).fnDestroy();
    $('#billsTable').dataTable({
        "responsive": true,
        "pagingType": "full_numbers",
        "order": [],
        "columnDefs": [
            { "targets": [0, 1, 3, 4], "orderable": false }
        ],
        "language": {
            "emptyTable": "No Bills Found !!!"
        }

    });

}

function PShareReportFormToJSON(form) {
    var array = jQuery(form).serializeArray();
    var json = {};

    jQuery.each(array, function () {
        json[this.name] = this.value || '';
    });

    return json;
}


function getTotalBillsJson(form) {
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

function VitalsFormToJSON(form) {
    var array = jQuery(form).serializeArray();

    var json = {};
    var newName;
    var parentName;
    var user = {};
    var newVal;

    jQuery.each(array, function (inx, element) {
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
    delete json['graphs'];
    return json;
}

$("#appointments").click(function () {
    $(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_").css("display", "none");
    $("ul.mobile-sub li a").removeClass("active");
    $(this).addClass("active");
    var userId = $("#userId").text();
    $('#ajaxloaddiv').load("patient/appointments/"+userId);
});

function getConsultation(patientId, doctorName, clinicName, contactNumber,mciNumber) {
    var url = "api/user/doctor/getConsultation";
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: patientId,
        contentType: 'application/json',
        mimeType: 'application/json',

        success: function (data) {
            var statusCode = data['statusCode'];
            $('#ajaxloaddiv').load("doctor/consultation", function () {
                $("#doctorName").val(doctorName);
                $("#clinicName").val(clinicName);
                $("#contactNumber").val(contactNumber);
                $("#mciNumber").val(mciNumber);

                $("#sh_doctorName").text(doctorName);
                $("#sh_clinicName").text(clinicName);
                $("#sh_contactNumber").text(contactNumber);
                $("#sh_mciNumber").text(mciNumber);

                $("#patient_id").text(patientId);

                var dob = data['dob'];
                if (dob != null && dob != '' && dob != 'undefined') {
                    var age = CalculateAge(data['dob']);
                    $("#enc_age").val(age);
                } else {
                    $("#enc_age").val("-");
                }

                $("#enc_age,#hp_enc_age").val(age);
                $("#encPatientId").val(patientId);

                getEncounterLabels(data['rosCategories'],data['peCategories']);
                getlabdata(data['labCategories'],data['hmtValues'],data['sgyValues'],data['cpgyValues'],data['cgyValues'],data['bcyValues'],data['mbgyValues'],data['hrtValues']);
                getHistory(data);

            });
        },
        error: function (data) {
            window.location = 'invalidSession';
        }
    });

}

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