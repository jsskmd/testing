<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 8/9/2016
  Time: 2:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>DataLife</title>
    <link rel="stylesheet" href="resources/css/jquery.datetimepicker.min.css">
    <script type="text/javascript" src="resources/jsplugins/jquery.datetimepicker.min.js"></script>
    <script type="text/javascript" src="resources/js/jsfiles/main.js"></script>

    <script type="text/javascript">

        $(document).ready(function(){

            $("#labPatientsearch").on('submit',function(e){
                e.preventDefault();
                var searchInput = $("#searchInput").val();
                $("#mobilePhone").val(searchInput);
                $("#lastsearchInput").val(searchInput);

                $.ajax({
                        url: "api/lab/search/labOrder",
                        type: 'POST',
                        dataType: 'json',
                        data: JSON.stringify(LabServiceRequestFormToJSON("#labPatientsearch")),
                        contentType: 'application/json',

                        success: function (data) {
                            var status = data['statusCode'];
                            switch (status){
                                case 200 :
                                    var tr = '<tr style="text-align:center;">';
                                    var lab = data['requests'];
                                    var viewFileUrl = data['viewFileUrl'];
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
                                        "order": [[ 2, "desc" ]],
                                        "language": {
                                            "emptyTable": "Enter valid DataLife Id and mobile number to get result !"
                                        }
                                    });
                                    break;
                                case 100 :
                                    $('#pperrorMessage').show().empty().prepend(data['message']).delay(10000).fadeOut(100);
                                    break;
                                case 400 :
                                    $('#pperrorMessage').show().empty().prepend("Oops something went wrong,Please try again later ").delay(10000).fadeOut(100);
                                    break;
                            }
                        },
                        error: function (data) {
                            window.location = 'invalidSession';
                        }
                    });
            });

            $('#labResultTbody').on('click','.recordsview', function (e) {
                e.preventDefault();
                var url = this.id;
                var windowSize = $(this).data("popup");
                window.open(url,  "popUp", windowSize);
            });

            $('#labResultTable').off().on('click','.getPatientDetails', function (e) {
                e.preventDefault();

                var jsondata = {"patientId": $(this).text()};
                $.ajax({
                    url: 'api/user/patient/getPatientDetails',
                    type: 'POST',
                    dataType: 'json',
                    data: JSON.stringify(jsondata),
                    contentType: 'application/json',

                    success: function (data) {

                        if(data['statusCode'] == 200){
                            $("#showPatDtlsPopup").addClass('dailog-show');
                            $("#patDtlsId").text(data['userId']);
                            $("#patDtlsName").text(data['patientName']);
                            $("#patDtlsPhNo").text(data['contactInfo']['mobilePhone']);
                            $("#patDtlsEmail").text(data['contactInfo']['email']);

                        }
                    },
                    error: function (data) {
                        window.location = 'invalidSession';
                    }

                });
            });

            $(".close-showPatDetail").click(function (event) {
                $("#showPatDtlsPopup").removeClass('dailog-show');
                event.stopPropagation();
            });

            $(".close-recordupload,.btn.btn-warning.cancel").click(function (event) {
                $("#appConfirmPopup").removeClass('dailog-show');
                $("tbody.files").empty();
                var search = $("#uploaded").val();
                if(search){
                    $("#lab_settings").trigger('click');
                }else{
                    $("#labPatientsearch").trigger('submit');
                }
                event.stopPropagation();
            });

        });

    </script>
</head>
<body>

<div class="wrapper">
    <div class="form-container">
        <div id="tmm-form-wizard" class="container substrate">
            <div class="row clr form-login-heading">
                <div class="col-md-6 col-sm-6 no-pad">
                    <h2 class="app_for_patients_cls">Order Status</h2></div>
            </div>
            <div id="ppconfirmMessage"></div>
            <div id="pperrorMessage"></div>

            <div class="form-wizard margintop-20">
                <div class="row">
                    <div class="col-md-12 no-padding">
                        <div class="col-md-12 no-pad">
                            <input type="hidden" id="lastsearchInput">
                            <input type="hidden" id="uploaded">

                            <form method="post" id="labPatientsearch">
                                <input type="hidden" id="providerId" name="providerId" >
                                <input type="hidden" id="mobilePhone" name="mobilePhone" >
                                <div class="col-md-3 no-pad ">
                                    <fieldset class="input-block">
                                        <input type="text" class="form-icon form-icon-user demo" placeholder="Search by OrderId" name="orderId"
                                               required="" title="Search" id="searchInput">
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
            <div id="confirmMessage"></div>
            <div class="form-wizard margintop-20 hide" id="labResultDiv">
                <div class="row">
                    <div class="background-for-list-space classwrap" >
                        <table id="labResultTable" class="display" cellpadding="0" cellspacing="0" width="100%">
                            <thead>
                            <tr>
                                <th style="text-align:center;">Patient-ID</th>
                                <th style="text-align:center;">Order-ID</th>
                                <th style="text-align:center;">Order Date</th>
                                <th style="text-align:center;">View Lab Order</th>
                                <th style="text-align:center;">Action</th>
                                <th style="text-align:center;">Status</th>
                            </tr>
                            </thead>
                            <tbody id="labResultTbody">

                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<div id="appConfirmPopup">
    <div class="form-wrapper confirmAppForm" id="confirmAppForm">
        <div class="close-recordupload"></div>
        <div class="confirmpadding" id="tmm-form-wizard">
            <form id="uploadLabReport" name="uploadFile" method="POST" enctype="multipart/form-data">
                <input type="hidden" id="resproviderId" name="providerId">
                <input type="hidden" id="patientId" name="patientId">
                <input type="hidden" id="orderId" name="orderId">
                <div class="note"></div>
                <div class="form-wrapper">
                    <div class="confirmappointemnt">Upload Records </div>
                    <div>
                        <div class="basicdetails-modal sidebox">
                            <div class="row">
                                <div class="col-md-12 no-pad hide">
                                    <fieldset class="input-block">
                                        <label class="select-records">Select Record Type</label>
                                        <div class="dropdown">
                                            <select id="fileType" class="dropdown-select" name="fileType">
                                                <option value="diagnostic" selected>Lab Reports</option>
                                            </select>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>
                        </div>
                        <div id="uploadRecords">
                            <div class="basicdetails-modal sidebox">
                                <div class="row">
                                    <div class="col-md-6  no-pad">
                                        <fieldset class="input-block">
                                            <label class="select-records">Amount</label>
                                            <input type="text" title="Enter Amount" required="true" name="labRequests.amount" placeholder=" Enter Amount" pattern="^(0|[1-9][0-9]*)$"/>
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                            <div class=" basicdetails-modal button-information sidebox">
                                <div class="row fileupload-buttonbar" style="clear: both; padding: 0 15px">
                                    <div class="col-md-12">
                                        <div class="btn btn-success fileinput-button">
                                            <i class="icon-search-circled"></i>
                                            <span class="upload-information">Browse</span>
                                            <input type="file" name="multiUploadedFileList" multiple="multiple">
                                        </div>
                                        <button type="submit" class="btn btn-primary start">
                                            <i class="icon-up-circled"></i>
                                            <span class="upload-information">Upload</span>
                                        </button>
                                        <span class="fileupload-process"></span>
                                    </div>
                                    <div class="col-lg-5 fileupload-progress width100per ">
                                        <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                                            <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                                        </div>
                                        <div class="progress-extended">&nbsp;</div>
                                        <div id="errorMessage"></div>
                                    </div>
                                </div>
                                <table role="presentation" class="table table-striped">
                                    <tbody class="files"></tbody>
                                </table>
                            </div>

                            <div class="note">Note: Multiple files can be uploaded.</div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div class="clearfix"></div>
    </div>
</div>

<div id="showPatDtlsPopup">
    <div class="form-wrapper confirmAppForm" id="showPatDtls" style="display: block;">
        <div class="close-showPatDetail"></div>
        <div class="confirmpadding">
            <div class="full-width " id="PatDtlsPopUp">
                <div class="form-wrapper">
                    <div class="confirmappointemnt-for-app">
                        Patient Details
                    </div>
                    <div>
                        <div class="basicdetails-modal form-wizard stnc">
                            <div class="doctordetails-modal ">
                                <div class="dcolumn-3">
                                    <%--<div class=""><img alt="clinic-image" id="patImage" style="width: 70px;"></div>--%>
                                    <ul class="padding-list">
                                        <li class="padding-list-icon">
                                            <span class="id-for-pateints">Patient ID</span>
                                            <span class="name-info"> : </span><span class="class-for-demo-patient" id="patDtlsId"></span></li>
                                        <li class="padding-list-icon"><span class="id-for-pateints">Name</span>
                                            <span class="name-info-class"> : </span><span class="class-for-demo-patient" id="patDtlsName"></span></li>
                                        <li class="padding-list-icon"><span class="id-for-pateints">Mobile Number</span>
                                            <span class="name-info-index"> : </span><span class="class-for-demo-patient" id="patDtlsPhNo"></span></li>
                                        <li class="padding-list-icon"><span class="id-for-pateints">Email Id</span>
                                            <span class="name-info-index-for-gmail"> : </span><span class="class-for-demo-patient" id="patDtlsEmail"></span></li>
                                    </ul>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                        <div class="clear"></div>

                    </div>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
        <td class='hide'>
            <p class="size">Processing...</p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start hide" disabled>
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>Start</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name">
                {% if (file.url) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div style="font-size:13px;"><span class="label label_name">{%=file.fileName%}</span> <span class='label_status'>{%=file.error%}</span></div>
            {% } %}
        </td>
        <td class='hide'>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}
                <button class="btn btn-danger delete hide" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>Delete</span>
                </button>
                <input type="checkbox" name="delete" value="1" class="toggle">
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <span>Close</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
</html>
