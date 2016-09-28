$(document).ready(function () {

    /*$("#showUploadPopup").off().on('click',function () {
        $("#uploadRecordsPopup").addClass('dailog-show');
        var userId = $("#userId").text();
        $("#recordUserId").val(userId);
        $("#fileType").val("0");
        $("#datepicker,#chiefcomplaint,#diagnosis").val("");
        $("#recordSpecialities").val(1);
        $("#uploadRecords").addClass("hide");
    });*/

    $('#mybills').off().on('click', '.recordsview', function (e) {
        e.preventDefault();
        var $this = $(this);
        var url = $(this).val();
        var windowName = "popUp";
        var windowSize = $this.data("popup");
        window.open(url, windowName, windowSize);
    });

    $('#myrecordstable,#pharmTable').off().on('click', '.recordsview', function (e) {
        e.preventDefault();
        var $this = $(this);
        var url = $(this).val();
        var windowName = "popUp";
        var windowSize = $this.data("popup");
        window.open(url, windowName, windowSize);
    });
});

$("#fileType").change(function () {
    $("#uploadRecords").removeClass("hide");
    $('#confirmMessage,#errorMessage').text('');
});
function checkEmail(email) {
    var regExp = /(^[a-z]([a-z_0-9\.]*)@([a-z_\.]*)([.][a-z]{2,3})$)|(^[a-z]([a-z_0-9\.]*)@([a-z_\.]*)(\.[a-z]{2,3})(\.[a-z]{2})*$)/i;
    return regExp.test(email);
}

function checkEmails() {
    var verdict = false;
    var emails = document.getElementById("demo-input-facebook-theme").value;
    if (emails != '' && emails != null && emails != 'undefined') {
        var emailArray = emails.split(",");
        for (var i = 0; i <= (emailArray.length - 1); i++) {
            if (checkEmail(emailArray[i])) {
                verdict = true;
            }
        }
    }
    return verdict;
}

$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $("#shareReportForm").submit(function (e) {
        if (checkEmails()) {
           /* $(".small_loading").removeClass("hide");*/
            $("#shareReportForm").find(".shareRecordbut").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
            var val = $("#demo-input-facebook-theme").val();
            var favorite = "";

            $.each($("input[name='selectedRecords']:checked"), function () {
                if (favorite == "") {
                    favorite += $(this).val();
                }
                else {
                    favorite += "," + $(this).val();
                }
            });

            $("#selectedsummary").val(favorite);
            var url = "api/user/patient/shareRecord";

            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(PShareReportFormToJSON('#shareReportForm')),
                contentType: 'application/json',
                success: function (data) {

                    if (data['statusCode'] == 200) {
                        $("#reportPassword").val("");
                        $("#shareRecordPopup").removeClass('dailog-show');
                        $('#s_confirmMessage').empty().prepend(data['message']);
                        $("#s_successMessage").addClass("dailog-show");
                        $('input:checkbox').removeAttr('checked');
                    }
                    if (data['statusCode'] == 406) {
                        /*$('#perrorMessage').empty().text(data['message']);*/
                        $('#s_errorMessage').empty().prepend(data['message']);
                        $("#s_successMessage").addClass("dailog-show");
                        $("#reportPassword").val("");

                    }
                    $("#demo-input-facebook-theme").val("");

                },
                error: function (data) {
                    window.location = 'invalidSession';
                }
            });
        } else {
            $('#s_errorMessage').empty().prepend("Invalid Email entered.");
            $("#s_successMessage").addClass("dailog-show");
        }
        e.preventDefault();
    });

    $("#moveReportForm").submit(function (e) {
        e.preventDefault();
        $("#moveReportForm").find(".moveRecordbut").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
        var url = "api/user/patient/moveRecord";

        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(PShareReportFormToJSON('#moveReportForm')),
            contentType: 'application/json',
            success: function (data) {
                if (data['statusCode'] == 200) {

                    var var1 = $("#recordType").val();
                    $("select#movefileType option[value*=" + var1 + "]").removeAttr('disabled');

                    $("#reportPassword").val("");
                    $("#moveRecordPopup").removeClass('dailog-show');

                    var sendType = $("#movefileType").val();
                    if (sendType != '' && sendType != null && sendType != 'undefined') {
                        $("#recordType").val(sendType).change();
                    }
                    $("#movefileType").val("0");
                    $('#s_confirmMessage').empty().prepend(data['message']);
                    $("#s_successMessage").addClass("dailog-show");

                }
                $("#moveReportForm").find(".moveRecordbut").html('Move');
                $("#demo-input-facebook-theme").val("");

            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });

    });

    $(".close-btndemo").click(function () {
        $('#successMessages').empty();
    });

    $('body').on('click', '.shareRecord', function (event) {

        $("#sharedmessage").text("");
        var userId = $("#userId").text();
        var ticket = $("#alfrescoTicket").val();
        $("#shareticket").val(ticket);
        $('#shareUserId').val(userId);

        $("#shareRecordPopup").addClass('dailog-show');
       var id= $(this).attr("id");
        $("input[value='" + id + "']").prop('checked', true);

        event.stopPropagation();
    });

    $('body').on('click', '.moveRecord', function (event) {
        $("#movefileType").val("");
        var var1 = $("#recordType").val();
        $("select#movefileType option[value*=" + var1 + "]").prop('disabled', true);

        $("#moveMessage").text("");

        var fileId = $(this).attr("id");

        $("#fileId").val(fileId);
        $("#moveRecordPopup").addClass('dailog-show');

        event.stopPropagation();
    });


    $(".cancel-common").off().on('click', function (e) {
        $('#perrorMessage').empty();
        $('#iframe').attr('src', "");
        $("#shareRecordPopup,#recordUploadPopup,#moveRecordPopup,#pdfdisplay").removeClass('dailog-show');
        var sendType = $("#movefileType").val();
        var var1 = $("#recordType").val();
        $("select#movefileType option[value*=" + var1 + "]").removeAttr('disabled');
        $("#movefileType").val("0");
        e.stopPropagation();
    });

    $(".close-recordupload").click(function (event) {
        $("#uploadRecordsPopup").removeClass('dailog-show');
        $("tbody.files").empty();
        var sendType = $("#fileType").val();
        if (sendType != '' && sendType != null && sendType != 'undefined') {
            $("#recordType").val(sendType).change();
        }
        event.stopPropagation();
    });

    $("#viewmore_records,#viewmore_labrecords").click(function () {
        var id = this.id;
        var url = "api/user/records/getallFiles";
        var userId = $("#userId").text();
        var sendType = $("#recordType").val();
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
                    switch(id){
                        case "viewmore_records":
                            $('#mybills').dataTable({
                                "bDestroy": true
                            }).fnDestroy();
                            $('#myrecords').html("");
                            getRecords(data);
                            break;
                        case "viewmore_labrecords":
                            $('#myrecordstable').dataTable({
                                "bDestroy": true
                            }).fnDestroy();
                            $('#myrecords').html("");
                            getLabRequestRecords(data);
                            break;
                    }
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#recordType,#typeofrecords").change(function () {
        $("#viewmore_records").text("View More");
        var url = "api/user/records/getFiles";
        var userId = $("#userId").text();
        var sendType = $("#recordType").val();
        var fromlabservices = false;
        if(!sendType){
            sendType = $("#typeofrecords").val();
            fromlabservices = true;
        }

        var jsondata = {"userId": userId, "sendType": sendType};
        var type = "Clinic Records";
        if (sendType == 'prescription') {
            type = "Prescription";
        }
        if (sendType == 'laborder') {
            type = "Lab Order";
        }
        if (sendType == 'diagnostic') {
            type = "Lab Reports";
        }
        if (sendType == 'mri') {
            type = "MRI";
        }
        if (sendType == 'xray') {
            type = "X-Ray";
        }
        if (sendType == 'others') {
            type = "Miscellaneous";
        }
        $("#record_type").text(type);

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
                    $('#mybills').dataTable({
                        "bDestroy": true
                    }).fnDestroy();
                    $('#myrecords').html("");
                    if(fromlabservices){
                        getShareRecords(data)
                    }else{
                        getRecords(data);
                    }
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });
});

