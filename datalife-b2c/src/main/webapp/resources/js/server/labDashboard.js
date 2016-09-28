/**
 * Created by barath on 8/8/2016.
 */

$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $("#lab_patientSearch").click(function (e) {
        $(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_").addClass('hide');
        $("ul.mobile-sub li a").removeClass("active");
        $(this).addClass("active");
        var role = $("#role").val();
        $('#ajaxloaddiv').load('lab/patientSearch',function(){
            $("#userrole").val(role);
        });
        e.preventDefault();
    });

    $("#lab_settings").click(function (e) {
        $(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_").addClass('hide');
        $("ul.mobile-sub li a").removeClass("active");
        $(this).addClass("active");
        var providerId = $("#userId").text();
        var jsondata = {"providerId": providerId};
        $.ajax({
            url: "api/lab/fetchReq/ByProviderId",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsondata),
            contentType: 'application/json',

            success: function (data) {
                var status = data['statusCode'];
                if (status == 200) {
                    $('#ajaxloaddiv').load('lab/labOrderStatus',function(){
                        $("#providerId").val(providerId);
                        var lab = data['requests'];
                        if(lab.length > 0){
                            var viewFileUrl = data['viewFileUrl'];
                            var tr = '<tr style="text-align:center;">';
                            $.each(lab, function (idx, obj) {
                                var prescribedRecords = obj['recordIds'];
                                switch(obj['status']){
                                    case "INITIATED":
                                        tr +=   '<td style="text-align:center;"><a  class="getPatientDetails">' + obj['patientId'] + '</a></td>'+
                                            '<td>' + obj['orderId'] + '</td>' +
                                            '<td style="text-align:center;">' + obj['orderDate'] + '</td>'+
                                            '<td style="text-align:center;">'+
                                            '<button type="button" class="recordsview View" id="' +viewFileUrl+prescribedRecords+"/"+obj['patientId']+ '"><i class="icon-folder"></i> View</button></td>'+
                                            '<td style="text-align:center;"><a class="register-btn" onclick="uploadResult('+ obj['orderId']+','+obj['providerId']+','+obj['patientId']+')" id=' + obj['userId'] + '>Upload Result</a></td>' +
                                            '<td style="text-align:center;">'+obj['status']+'</td>'+
                                            '</tr>';
                                        break;
                                    case "COMPLETED":
                                        tr +=  '<td style="text-align:center;"><a  class="getPatientDetails">' + obj['patientId'] + '</a></td>'+
                                            '<td>' + obj['orderId'] + '</td>' +
                                            '<td style="text-align:center;">' + obj['orderDate'] + '</td>'+
                                            '<td style="text-align:center;">'+
                                            '<button type="button" class="recordsview View" id="' +viewFileUrl+obj['recordIds']+"/"+obj['patientId']+ '"><i class="icon-folder"></i> View</button></td>'+
                                            '<td style="text-align:center;"><button type="button" class="recordsview View" id="' +viewFileUrl+obj['labReportFileId']+"/"+obj['patientId']+ '"><i class="icon-folder"></i> View</button></td>' +
                                            '<td style="text-align:center;">'+obj['status']+'</td>'+
                                            '</tr>';
                                        break;


                                }
                            });
                            $("#labResultDiv").removeClass("hide");
                            $('#labResultTable').dataTable({
                                "bDestroy": true
                            }).fnDestroy();
                            $('#labResultTbody').empty().html(tr);
                            $('#labResultTable').dataTable({
                                responsive: true,
                                "pagingType": "full_numbers",
                                "language": {
                                    "emptyTable": "Enter valid DataLife Id and mobile number to get result !"
                                }
                            });
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


    $("#lab_profile").click(function (e) {
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
                if (status == 200) {
                    $('#ajaxloaddiv').load("lab/labProfile", function (e) {
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

});

function saveRequest(data){
    var url = "api/user/patient/saveServiceRequests";
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function (data) {
            var saveMessage = data['message'];
            var saveStatus = data['statusCode'];
            $("#cliniPatientsDiv").addClass("hide");
            switch (saveStatus){
                case 200 :
                    $(".pcancel-common").trigger('click');
                    $('#clinicPatientsTable').dataTable({
                        "bDestroy": true
                    }).fnDestroy();
                    $('#ppconfirmMessage').show().empty().prepend(saveMessage).delay(10000).fadeOut(100, function () {
                        $('#ppconfirmMessage').empty();
                    });
                    document.getElementById('labPatient_search').reset();
                    break;
                case 100:
                case 400:
                    $('#pperrorMessage').show().empty().prepend(saveMessage).delay(10000).fadeOut(100, function () {
                        $('#pperrorMessage').empty();
                    });
            }
        },
        error: function (data) {
            window.location = 'invalidSession';
        }
    });
}

function getLabInfo(data) {
    alert(JSON.stringify(data));
    var sp = data['sp'];

    var message = data['message'];

    if (sp['name'] != null && sp['name'] != '' && sp['name'] != 'undefined') {
        $('#name').text(sp['name']);
        $('#t_name').val(sp['name']);
    }
    else {
        $('#name').text("-");
    }
    if (sp['licenceNumber'] != null && sp['licenceNumber'] != '' && sp['licenceNumber'] != 'undefined') {
        $('#licenceNumber').text(sp['licenceNumber']);
        $('#t_licenceNumber').val(sp['licenceNumber']);
    }
    else {
        $('#licenceNumber').text("-");
    }
    $('#datalifeId').text(u['userId']);
    $('#at_userId').val(u['userId']);

/*    if (message != "" && message != undefined && message != null) {
        $("#view_basicDetails").removeClass("hide");
        $("#contactDetails,#emergencyDetails,#preferences,#professionaldetails,#basicDetails,#view_contactDetails,#view_emergencyDetails,#view_preferences,#view_professionaldetails").addClass("hide");
    }
    $("#a_basicDetails").addClass("tmm-success");*/

}

function uploadResult(orderId,providerId,patientId){
    $("#resproviderId").val(providerId);
    $("#orderId").val(orderId);
    $("#patientId").val(patientId);
    $("#appConfirmPopup").addClass('dailog-show');
}