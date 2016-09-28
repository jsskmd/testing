<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 8/8/2016
  Time: 7:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Datalife</title>
    <script type="text/javascript" src="resources/js/server/srcommon.js"></script>

    <script>
        $(document).ready(function () {

            $("#labPatient_search").submit(function (e) {
                e.preventDefault();
                $("#labPatient_search").find(".submit_btn").html('<img src="resources/images/red_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
                var input = $("#searchPatientInput").val();
                $.ajax({
                    url: "api/user/clinic/searchPatients",
                    type: 'POST',
                    dataType: 'json',
                    data: input,
                    contentType: 'application/json',
                    success: function (data) {
                        var statusCode = data['statusCode'];
                        var message = data['message'];
                        switch (statusCode) {
                            case 200 :
                                var tr = '<tr style="text-align:center;">';
                                var users = data['users'];
                                $.each(users, function (idx, obj) {
                                    tr += '<td>' + obj['userId'] + '</td>' +
                                            '<td style="text-align:center;">' + obj['flname'] + '</td>' +
                                            '<td style="text-align:center;">' + obj['mobileNo'] + ' </a></td>' +
                                            '<td style="text-align:center;"><a class="register-btn viewprescription" id=' + obj['userId'] + '>View</a></td>' +
                                            '</tr>';
                                });
                                $("#cliniPatientsDiv").removeClass("hide");
                                $('#clinicPatientsTable').dataTable({
                                    "bDestroy": true
                                }).fnDestroy();
                                $('#clinicPatientsTbody').empty().html(tr);
                                $('#clinicPatientsTable').dataTable({
                                    responsive: true,
                                    "pagingType": "full_numbers",
                                    "language": {
                                        "emptyTable": "Enter valid DataLife Id and mobile number to get result !"
                                    }
                                });
                                break;
                            case 400:
                            case 100:
                                $("#cliniPatientsDiv").addClass("hide");
                                $('#pperrorMessage').show().empty().prepend(message).delay(10000).fadeOut(100);
                                break;
                        }
                        $("#labPatient_search").find(".submit_btn").html('Search');
                    },
                    error: function (data) {
                        window.location = 'invalidSession';
                    }
                });
            });

            $("#clinicPatientsTbody").on('click','.viewprescription',function(){
                var patientId = this.id;
                var role = $("#userrole").val();
                var sendType;
                var url = "api/lab/fetch/labOrder";
                var providerId = $("#userId").text();
                if(role == "ROLE_PHARMACY"){
                    sendType = "prescription";
                    var jsondata = {"userId": patientId, "sendType": sendType};
                    $.ajax({
                        url: url,
                        type: 'POST',
                        dataType: 'json',
                        data: JSON.stringify(jsondata),
                        contentType: 'application/json',

                        success: function (data) {
                            var status = data['statusCode'];
                            switch (status){

                                case 200:
                                    $('#clinicPatientsPopup').load("lab/sharePrescription", function () {
                                        $("#record_type").text(sendType);
                                        $("#patientId").val(patientId);
                                        $("#providerId").val(providerId);
                                        $(this).addClass("dailog-show");
                                        var tr ='';
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

                                            tr +='<tr>'+
                                                    '<td>' + getDateFromSeconds(obj['encounterDate']) + '</td>' +
                                                    '<td style="text-align:center;">' + speciality + '</td>' +
                                                    '<td>' + cc + '</td>' +
                                                    '<td style="text-align:center;"><button type="button" id="' + obj['fileName'] + '" class="recordsview View" value="' + obj['vurl'] + '"><i class="icon-folder"></i> View</button></td>'+
                                                    '<td style="text-align:center;"><input id="amount'+obj['fileId']+'" type="text" ></td>'+
                                                    '<td style="text-align:center;" id="select'+obj['fileId']+'">' +
                                                    '<select size="1" id="'+obj['fileId']+'" class="status"  name="status">'+
                                                    '<option disabled="" selected="selected">Select</option>'+
                                                    '<option value="3">COMPLETED</option>'+
                                                    '<option value="2">PENDING</option>'+
                                                    '<option value="4">CLOSED</option>'+
                                                    '</select>' +
                                                    '<textarea placeholder="Enter Reason" title="Enter Reason"  class="hide" id="txt'+obj['fileId']+'"></textarea>'+
                                                    '</td>'+
                                                    '<td style="text-align:center;"><button type="button"  class=" saveb editbasicinfo-vitals submit_btn transaction" id="' + obj['fileId'] + '">Generate Order</button></td>'+
                                                    '</tr>';
                                        });

                                        $('#pharmTbody').empty().append(tr);
                                        $("#pharmacyTable").removeClass('hide');
                                        $("#labTable").addClass('hide');
                                        $('#pharmTable').dataTable({
                                            "bDestroy": true
                                        }).fnDestroy();
                                        var oTable = $('#pharmTable').dataTable({
                                            "responsive": true,
                                            "pagingType": "full_numbers",
                                            "order": [[ 1, "desc" ]],
                                            "language": {
                                                "emptyTable": "No Records Found !!!"
                                            }
                                        });

                                    });
                                    break;
                                case 100:
                                    $('#pperrorMessage').fadeIn().empty().prepend(data['message']).delay(10000).fadeOut(100, function () {
                                        $('#pperrorMessage').empty();
                                    });
                                    break;
                            }
                        },
                        error: function (data) {
                            window.location = 'invalidSession';
                        }
                    });
                }else{
                    sendType = "laborder";
                    jsondata = {"userId": patientId, "sendType": sendType};
                    $.ajax({
                        url: url,
                        type: 'POST',
                        dataType: 'json',
                        data: JSON.stringify(jsondata),
                        contentType: 'application/json',

                        success: function (data) {
                            var status = data['statusCode'];
                            switch (status){

                                case 200:
                                    $('#clinicPatientsPopup').load("lab/sharePrescription", function () {
                                        $("#record_type").text(sendType);
                                        $("#patientId").val(patientId);
                                        $("#providerId").val(providerId);
                                        $(this).addClass("dailog-show");
                                        var tr ='';
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
                                            tr +='<tr id="' + obj['fileId'] + '">'+
                                                    '<td id="encDate'+obj['fileId']+'">' + getDateFromSeconds(obj['encounterDate']) + '</td>' +
                                                    '<td style="text-align:center;">' + speciality + '</td>' +
                                                    '<td>' + cc + '</td>' +
                                                    '<td style="text-align:center;"><a href="#" class="specality-news">' + dg + '</a>' +
                                                    '<input type="hidden" id="alfrescoTicket" value="' + data['ticket'] + '"/></td>'+
                                                    '<td style="text-align:center;"><button type="button" id="' + obj['fileName'] + '" class="recordsview View" value="' + obj['vurl'] + '"><i class="icon-folder"></i> View</button></td>';

                                            if(obj['serviceRequests'] != null && providerId == obj['serviceRequests']['providerId'] && sendType == "laborder") {
                                                tr += '<td style="text-align:center;">'+ obj['serviceRequests']['status'] +'</td>';
                                            }else{
                                                tr += '<td style="text-align:center;"><button type="button"  class=" saveb editbasicinfo-vitals submit_btn generateorder" id="' + obj['fileId'] + '">Generate Order</button></td>';
                                            }
                                            tr +='</tr>';
                                        });

                                        $('#myrecords').empty().append(tr);
                                        $('#mybills').dataTable({
                                            "bDestroy": true
                                        }).fnDestroy();
                                        var oTable = $('#mybills').dataTable({
                                            "responsive": true,
                                            "pagingType": "full_numbers",
                                            "order": [[ 1, "desc" ]],
                                            "language": {
                                                "emptyTable": "No Records Found !!!"
                                            }
                                        });

                                    });
                                    break;
                                case 100:
                                    $('#pperrorMessage').fadeIn().empty().prepend(data['message']).delay(10000).fadeOut(100, function () {
                                        $('#pperrorMessage').empty();
                                    });
                                    break;
                            }
                        },
                        error: function (data) {
                            window.location = 'invalidSession';
                        }
                    });
                }

            });
        });

    </script>
</head>
<body>
<div class="wrapper">
    <div class="form-container">
        <div id="tmm-form-wizard" class="container substrate">
            <div class="row clr form-login-heading">
                <div class="col-md-6 col-sm-6 no-pad"><h2 class="app_for_patients_cls">Patient Search</h2></div>
            </div>
            <div id="ppconfirmMessage"></div>
            <div id="pperrorMessage"></div>
            <input type="hidden" id="userrole">

            <div class="form-wizard margintop-20">
                <div class="row">
                    <div class="col-md-12 no-padding">
                        <div class="col-md-12 no-pad">
                            <form method="post" id="labPatient_search">
                                <div class="col-md-3 no-pad ">
                                    <fieldset class="input-block">
                                        <input type="text" class="form-icon form-icon-user demo" placeholder="Search by DataLife Id, Mobile Number"
                                               required="" title="Search" id="searchPatientInput">
                                    </fieldset>
                                </div>
                                <div class="col-md-3 no-pad serach clinicsepatients">
                                    <button type="submit" class="saveb editbasicinfo submit_btn">Search</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-wizard margintop-20 hide" id="cliniPatientsDiv">
                <div class="row">
                    <div class="background-for-list-space classwrap" >
                    <table id="clinicPatientsTable" class="display" cellpadding="0" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th style="text-align:center;">UserId</th>
                            <th style="text-align:center;"> Name</th>
                            <th style="text-align:center;">Mobile Number</th>
                            <th style="text-align:center;">View Prescription</th>
                        </tr>
                        </thead>
                        <tbody id="clinicPatientsTbody">

                        </tbody>
                    </table>
                </div>

                </div>
            </div>
        </div>
    </div>
</div>

<div id="clinicPatientsPopup">

</div>

</body>
</html>
