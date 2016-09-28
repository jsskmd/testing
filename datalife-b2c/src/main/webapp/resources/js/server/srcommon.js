/**
 * Created by barath on 7/5/2016.
 */

function getShareRecords(data) {

    $(".dataSize").text(data['size']);
    var tr = "";
    $.each(data['requestedFiles'], function (idx, obj) {

        var speciality = "-";
        if (obj['speciality']) {
            var str = obj['speciality'];
            speciality = str.replace(/[^a-zA-Z ]/g, "");
        }
        var cc = "-";
        if (obj['chiefComplaint']) {
            cc = obj['chiefComplaint'];
        }
        var dg = "-";
        if (obj['diagnosis']) {
            dg = obj['diagnosis'];
        }
        tr += '<tr id="' + obj['fileId'] + '" style="text-align:center;">' +
            '<td>' + getDateFromSeconds(obj['encounterDate']) + '</td>' +
            '<td style="text-align:center;">' + speciality + '</td>' +
            '<td>' + cc + '</td>' +
            '<td style="text-align:center;"><a href="#" class="specality-news">' + dg + '</a></td>' +
            '<td style="text-align:center;"><button type="button" id="' + obj['fileName'] + '" class="recordsview View" value="' + obj['vurl'] + '"><i class="icon-folder"></i> View</button>' +
            '<input type="hidden" id="alfrescoTicket" value="' + data['ticket'] + '"/></td>' +
            '</tr>';
    });

    $('#myrecords').empty().append(tr);
    $('#mybills').dataTable({
        "bDestroy": true
    }).fnDestroy();
    var oTable = $('#mybills').dataTable({
        "responsive": true,
        "pagingType": "full_numbers",
        "language": {
            "emptyTable": "No Records Found !!!"
        }
    });

    if(oTable.fnGetData().length > 0){
        $("#shareLabOrdersRecords,#shareLabOrdersRecordsD").removeClass('hide');
    }else{
        $("#shareLabOrdersRecords,#shareLabOrdersRecordsD").addClass('hide');
    }
}

function viewLaborders() {
    var url = "api/user/patient/viewOrderRequests";
    var userId = $("#userId").text();
    var serviceType = $("#sendType").val();
    var jsondata = {"patientId": userId, "serviceType": serviceType};
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',

        success: function (data) {
            alert(JSON.stringify(data));
        },
        error: function (data) {
            window.location = 'invalidSession';
        }
    });
}

$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $('.keyup-fake').keyup(function () {
        $('span.error-keyup-6').remove();
        $(this).removeClass('textareavalidate');
        var inputVal = $(this).val();
        var fakeReg = /(.)\1{2,}/;
        if (fakeReg.test(inputVal)) {
            $(this).after('<span class="error error-keyup-6" style="display: none">Invalid text.</span>');
            $(this).addClass('textareavalidate');
        }
    });

    $(".close-btndemo").on('click', function () {
        $('#message').empty();
    });

    $("#cancel_verify").click(function(){
        $("#verification").removeClass("dailog-show");
    });

    $("#verify_mobile").click(function () {
        $("#verification").addClass("dailog-show");
        $('#verifyConfirmMessage,#verifyErrorMessage').empty();
        $("#emailBody,.smsplitcode,.sign_out,.cancel_verification").addClass("hide");
        $("#mobileBody,#cancel_verify").removeClass("hide");
    });

    $("#shareRecordsPopup").click(function (e) {
        $(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_").addClass('hide');
        var url = "api/user/records/getFiles";
        var userId = $("#userId").text();
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

                    $('#recordUploadPopup').load("patient/shareRecordsPopup", function () {
                        getShareRecords(data);
                        $("#mybills_info").text("Showing 1 to 5 of " + data['count'] + " entries");
                        if (data['count'] < 5) {
                            $("#mybills_info").text("Showing 1 to " + data['count'] + " of " + data['count'] + " entries");
                        }
                    });
                    $("#recordUploadPopup").addClass("dailog-show");
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
        e.preventDefault();
    });

    $("#shareRecords").click(function (e) {
        $(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_").addClass('hide');
        var url = "api/user/records/getFiles";
        var userId = $("#userId").text();
        var sendType = "hospital";
        var select = $("#servType").val();
        alert(select);
            if(select){
                sendType =  select;
            }
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

    $("#shareRecordBtn,#shareRecordBtnD").on('click',function(){
        var recordsId = "";
        var index = 0;
        $('#myrecords').find('.selected').each(function(){
            index++;
            if (recordsId == "") {
                recordsId += this.id;
            }else {
                recordsId += "," + this.id;
            }
        });
        var message = "Selected "+index+"  files";
        if(recordsId != "" && recordsId != ","){
            $("#eMessage").empty().prepend('<div id="confirmMessage">' + message + '</div>')
        }else{
            $("#eMessage").empty().prepend('<div id="errorMessage">' + message + '</div>').children(':first').delay(5000).fadeOut(100);
        }
        $("#recordIds").val(recordsId);
        $("#recordUploadPopup").removeClass('dailog-show');
    });

    $("#shareLabOrdersRecords,#shareLabOrdersRecordsD").on('click',function(){
        var recordsId = "";
        var index = 0;

        $('#myrecords').find('.selected').each(function(){
            index++;
            if (recordsId == "") {
                recordsId += this.id;
            } else {
                recordsId += "," + this.id;
            }
        });
        if(index > 0){
            var userId = $("#userId").text();
            var url = "api/user/patient/contactInfo";
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
                    if (status == 400) {
                        $('body').load("/");
                    } else if (data['statusCode'] == 200) {
                        $("#pciUserId").val(userId);
                        getContactInfo(data);
                        getCountry(data['countries'], 'alt_ucicountry');
                        var message = "Selected "+index+"  files";
                        if(recordsId != "" && recordsId != ","){
                            $("#eMessage").empty().prepend('<div id="confirmMessage">' + message + '</div>');
                        }
                        $("#recordIds").val(recordsId);
                        $("#recordUpload").addClass('hide');
                        $("#confirmAddress").removeClass('hide');
                        $("#stepone").addClass('active');

                    }
                },
                error: function (data) {
                    window.location = 'invalidSession';
                }
            });

        }else{
            $("#eMessage").empty().prepend('<div id="errorMessage">Prescription is mandatory</div>').children(':first').delay(5000).fadeOut(100);
        }
    });

    $("#userContact").off().on('submit',function (e) {
        e.preventDefault();
        $("#userContact").find(".submit_btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
        var url = "api/user/patient/contactInfo/save";
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(PDemoContactFormToJSON('#userContact')),
            contentType: 'application/json',
            success: function (data) {
                /* var saveMessage = data['message'];*/
                if (data['statusCode'] == 200) {
                    getContactInfo(data);
                    $("#view_clinicProfile").removeClass("hide");
                    $("#edit_clinicProfile,#addAddress").addClass("hide");
                    $("#eMessage").empty().prepend('<div id="confirmMessage">' + data['message'] + '</div>').children(':first').delay(5000).fadeOut(100);
                }
                else {
                    $('#confirmMessage').prepend('<div class="notification alert-error spacer-t10">' +
                        '<p>' + data['message'] + '</p><span class="close-btn" ></span></div>').children(':first').delay(15000).fadeOut(100, function () {
                        $('#confirmMessage').empty();
                    });
                }
                $("#userContactInfo").find(".submit_btn").html('Save');

            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("form#labrequestForm,form#pharmacyrequestForm").off().on('submit',function (e) {
        e.preventDefault();
        var id = this.id;

        var addr = $("#alt_address").val();
        var location = $("#alt_location").val();
        var city = $("#alt_city").val();
        var requestForm;
        if(addr && location && city ){
            var contInfo = LabServiceRequestFormToJSON('#altuserContactInfo');
           requestForm = LabServiceRequestFormToJSON('#'+id);
            $.extend(true,requestForm,contInfo);
        }else{
            requestForm = LabServiceRequestFormToJSON('#'+id);
        }
        var url = "api/user/patient/saveServiceRequests";
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(requestForm),
            contentType: 'application/json',
            success: function (data) {
                var saveMessage = data['message'];
                var saveStatus = data['statusCode'];
                switch (saveStatus){
                    case 200 :
                        document.getElementById(id).reset();
                        document.getElementById("altuserContactInfo").reset();
                        $('#s_confirmMessage').empty().prepend(saveMessage);
                        var callbackId = $("#services").val();
                        $('#'+callbackId).trigger('click');
                        break;
                    case 400:
                        $('#s_errorMessage').empty().prepend('Bad Request');
                        break;
                }
                if (saveStatus == '200') {

                } else {
                    $('#s_errorMessage').empty().prepend(saveMessage);
                }
                $("#s_successMessage").addClass("dailog-show");
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#viewlaborders").off().on('click', function () {
        $("#viewlaborders").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">Please wait..');
        $(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_").css("display", "none");
        viewLaborders();
    });

    $("#cancelprofile,#canceladdress").click(function () {
        $("#view_clinicProfile").removeClass("hide");
        $("#edit_clinicProfile,#addAddress").addClass("hide");
        $("#t_addresscheckbox").prop("checked",false);
    });

    $("#editClinicProfile").click(function () {
        $("#view_clinicProfile").addClass("hide");
        $("#edit_clinicProfile").removeClass("hide");
    });

    $("#t_addresscheckbox").on('change',function () {
        if(this.checked){
            $("#view_clinicProfile").addClass("hide");
            $("#addAddress").removeClass("hide");
        }
    });

    $("#t_checkbox").on('change',function () {
        if(this.checked){
            $("#confirmAddress,#recordUpload").addClass("hide");
            $("#instrusctions").removeClass("hide");
            $("#steptwo,#stepthree").addClass('active');
        }
    });

    $("form#altuserContactInfo").submit(function (e) {
        e.preventDefault();
        $("#confirmAddress,#recordUpload").addClass("hide");
        $("#steptwo,#stepthree").addClass('active');
        $("#instrusctions").removeClass("hide");
    });

    $("#stepone").click(function () {

        if($("#stepone").hasClass('active')){
            $("#recordUpload").removeClass('hide');
            $("#confirmAddress,#instrusctions").addClass('hide');
        }
    });

    $("#steptwo").click(function () {
        if($("#steptwo").hasClass('active')) {
            $("#confirmAddress").removeClass('hide');
            $("#recordUpload,#instrusctions").addClass('hide');
        }
    });

    $("#stepthree").click(function () {
        if($("#stepthree").hasClass('active')) {
            $("#instrusctions").removeClass('hide');
            $("#confirmAddress,#recordUpload").addClass('hide');
        }
    });

});