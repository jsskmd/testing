<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 8/9/2016
  Time: 11:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>DataLife</title>
    <script type="text/javascript" src="resources/js/server/patientRecords.js"></script>
    <script>
        $(document).ready(function () {

            $(".pcancel-common").on('click', function (event) {
                $("body").css("overflow","auto");
                $("#clinicPatientsPopup").removeClass("dailog-show");
                $("#access_encounter").removeClass("dailog-show");
                event.stopPropagation();
            });

            $("#myrecords").on('click','.generateorder',function(e){
                e.preventDefault();
                var patientId = $("#patientId").val();
                var providerId = $("#providerId").val();
                var recordIds = this.id;
                var sendType = $("#sendType").val();

                var json = {"providerId":providerId,"patientId":patientId,"labRequests":{"recordIds":recordIds},"serviceType":sendType};
                    saveRequest(json);
            });

            $("#pharmTbody").on('click','.transaction',function(e){
                e.preventDefault();
                var patientId = $("#patientId").val();
                var providerId = $("#providerId").val();
                var recordIds = this.id;
                    var amount = $("#amount"+recordIds).val();
                    var status = $("#select"+recordIds).find(".status option:selected").val();
                    if(amount && status){
                        var validinput = /^[0-9]*$/.test(amount);
                        if(validinput){
                            if(status == 2 || status == 4){
                                var reason = $("#txt"+recordIds).val();
                                if(reason){
                                    var json = {"providerId":providerId,"patientId":patientId,"pharmacyRequests":{"recordIds":recordIds,"amount":amount,"statusReason":reason},"serviceType":'pharmacy',"status":status};
                                    saveRequest(json);
                                }else{
                                    $("#pperrorMessage").empty().append("Enter the Reason").delay(7000).fadeOut(100);
                                }
                            }else{
                                json = {"providerId":providerId,"patientId":patientId,"pharmacyRequests":{"recordIds":recordIds,"amount":amount},"serviceType":'pharmacy',"status":status};
                                saveRequest(json);
                            }

                        }else{
                            $("#pperrorMessage").empty().append("Please Enter the valid Input Eg: 5000 (with out ' . ' & ',' and any special character)").delay(7000).fadeOut(100);
                        }
                    }else{
                        $("#pperrorMessage").empty().append("Please Enter the amount before generating Order").delay(7000).fadeOut(100);
                    }

            });

            $("#pharmTbody").on('change','.status',function(e){
                e.preventDefault();
                var status = $(this).val();
                var id = this.id;
                if(status == 2 || status == 4 ){
                    $("#txt"+id).removeClass('hide');
                }else{
                    $("#txt"+id).addClass('hide');
                }

            });
        });

    </script>
</head>
<body>

<div class="form-wrapper confirmAppForm class-popup" id="confirmAppForm">
    <div class="pcancel-common"></div>
    <div class="confirmpadding" id="tmm-form-wizard">

        <div class="note"></div>

        <div class="form-wrapper">

                <input type="hidden" name="serviceType" value="diagnosticLabs" id="sendType">
                <input type="hidden" name="patientId" id="patientId">
                <input type="hidden" name="labRequests.recordIds" id="recordIds">
                <input type="hidden" name="providerId" id="providerId">


                <div id="ppconfirmMessage"></div>
                <div id="pperrorMessage"></div>
                    <div class="col-md-7">
                        <div class="hospital-records" id="record_type">Clinic Records</div>
                    </div>

                <div class="clearfix"></div>
                <div class="form-wizard doctor-info">
                    <div id="labTable">
                        <table id="mybills" class="display" cellspacing="0" width="99%">
                            <thead>
                            <tr>
                                <th style="text-align:center;">Date of visit</th>
                                <th style="text-align:center;">Specialty</th>
                                <th style="text-align:center;">Reason for visit</th>
                                <th style="text-align:center;">Diagnosis</th>
                                <th style="text-align:center;">View </th>
                                <th style="text-align:center;">Action</th>
                            </tr>
                            </thead>
                            <tbody id="myrecords">

                            </tbody>
                        </table>
                    </div>
                    <div id="pharmacyTable" class="hide">
                        <table id="pharmTable" class="display" cellspacing="0" width="99%">
                            <thead>
                            <tr>
                                <th style="text-align:center;">Date of visit</th>
                                <th style="text-align:center;">Specialty</th>
                                <th style="text-align:center;">Reason for visit</th>
                                <th style="text-align:center;">View </th>
                                <th style="text-align:center;">Amount</th>
                                <th style="text-align:center;">Status</th>
                                <th style="text-align:center;">Action</th>
                            </tr>
                            </thead>
                            <tbody id="pharmTbody">

                            </tbody>
                        </table>
                    </div>
                </div>

        </div>
        <div class="clear"></div>
    </div>
</div>

</body>
</html>
