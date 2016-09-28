<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 8/17/2016
  Time: 10:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DataLife Pharmacy</title>

    <script type="text/javascript">

        $(document).ready(function() {
            $("#fetchPharmrequests").on('submit', function (e) {
                e.preventDefault();
                var start = $("#date_timepicker_start").val();
                var end = $("#date_timepicker_end").val();
                if (start && end) {
                    $.ajax({
                        url: "api/pharmacy/search/pharmRequest",
                        type: 'POST',
                        dataType: 'json',
                        data: JSON.stringify(LabServiceRequestFormToJSON("#fetchPharmrequests")),
                        contentType: 'application/json',

                        success: function (data) {
                            var status = data['statusCode'];
                            var pharm = data['requests'];

                            if(pharm.length > 0){
                                var viewFileUrl = data['viewFileUrl'];
                                var tr = '<tr style="text-align:center;">';
                                $.each(pharm, function (idx, obj) {
                                    var prescribedRecords = obj['recordIds'];
                                    switch(obj['status']){
                                        case "PENDING":
                                        case "CLOSED":
                                            tr +=   '<td style="text-align:center;"><a  class="getPatientDetails">' + obj['patientId'] + '</a></td>'+
                                                    '<td>' + obj['orderId'] + '</td>' +
                                                    '<td style="text-align:center;">' + obj['orderDate'] + '</td>'+
                                                    '<td style="text-align:center;">'+
                                                    '<button type="button" class="recordsview View" id="' +viewFileUrl+prescribedRecords+"/"+obj['patientId']+ '"><i class="icon-folder"></i> View</button></td>'+
                                                    '<td style="text-align:center;">'+obj['amount']+'</td>' +
                                                    '<td style="text-align:center;">'+obj['status']+'</td>'+
                                                    '<td style="text-align:center;">'+obj['statusReason']+'</td>'+
                                                    '</tr>';
                                            break;
                                        case "COMPLETED":
                                            tr +=  '<td style="text-align:center;"><a  class="getPatientDetails">' + obj['patientId'] + '</a></td>'+
                                                    '<td>' + obj['orderId'] + '</td>' +
                                                    '<td style="text-align:center;">' + obj['orderDate'] + '</td>'+
                                                    '<td style="text-align:center;">'+
                                                    '<button type="button" class="recordsview View" id="' +viewFileUrl+obj['recordIds']+"/"+obj['patientId']+ '"><i class="icon-folder"></i> View</button></td>'+
                                                    '<td style="text-align:center;">'+obj['amount']+'</td>' +
                                                    '<td style="text-align:center;">'+obj['status']+'</td>'+
                                                    '<td style="text-align:center;">'+"-"+'</td>'+
                                                    '</tr>';
                                            break;

                                    }
                                });
                                $("#pharmResultDiv").removeClass("hide");
                                $('#pharmResultTable').dataTable({
                                    "bDestroy": true
                                }).fnDestroy();
                                $('#pharmResultTbody').empty().html(tr);
                                $('#pharmResultTable').dataTable({
                                    responsive: true,
                                    "pagingType": "full_numbers",
                                    "language": {
                                        "emptyTable": "Enter valid DataLife Id and mobile number to get result !"
                                    }
                                });
                            }

                        },
                        error: function (data) {
                            window.location = 'invalidSession';
                        }
                    });
                } else {
                    $('#pperrorMessage').show().empty().prepend("").delay(10000).fadeOut(100);
                }
            });

            $('#pharmResultTbody').on('click','.recordsview', function (e) {
                e.preventDefault();
                var url = this.id;
                var windowSize = $(this).data("popup");
                window.open(url, "popUp", windowSize);
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
                <%--<div class="row">
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
                </div>--%>

                    <form method="post" name="serviceRequests" id="fetchPharmrequests" enctype='application/json'>
                        <input type="hidden" id="providerId" name="providerId" >
                        <div class="row">
                            <div class="col-md-6">
                                <div class="col-md-6 col-sm-6 no-pad ">
                                    <fieldset class="input-block">
                                        <label>From Date </label>
                                        <input id="date_timepicker_start" class="hasDatepicker" name="pharmacyRequests.fromDate" type="text" placeholder="From Date" readonly required="required" pattern="\d{1,2}/\d{1,2}/\d{4}">
                                    </fieldset>
                                </div>
                                <div class="col-md-6 col-sm-6 no-pad hide" id="todate" >
                                    <fieldset class="input-block">
                                        <label>To Date</label>
                                        <input id="date_timepicker_end" name="pharmacyRequests.toDate" class="hasDatepicker" type="text"  placeholder="To Date" readonly required="required" pattern="\d{1,2}/\d{1,2}/\d{4}">
                                    </fieldset>
                                </div>
                                <div class="col-md-3 no-pad serach clinicsepatients">
                                    <button type="submit" class="saveb editbasicinfo submit_btn">Search</button>
                                </div>
                            </div>

                        </div>
                    </form>

            </div>
            <div id="confirmMessage"></div>
            <div class="form-wizard margintop-20 hide" id="pharmResultDiv">
                <div class="row">
                    <div class="background-for-list-space classwrap" >
                        <table id="pharmResultTable" class="display" cellpadding="0" cellspacing="0" width="100%">
                            <thead>
                            <tr>
                                <th style="text-align:center;">Patient-ID</th>
                                <th style="text-align:center;">Order-ID</th>
                                <th style="text-align:center;">Order Date</th>
                                <th style="text-align:center;">View Prescription</th>
                                <th style="text-align:center;">Amount</th>
                                <th style="text-align:center;">Status</th>
                                <th style="text-align:center;">Reason</th>
                            </tr>
                            </thead>
                            <tbody id="pharmResultTbody">

                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
