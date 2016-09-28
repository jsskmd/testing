/**
 * Created by barath on 8/15/2016.
 */

    $(function () {

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

        $("#pharmacy_patientSearch").click(function (e) {
            $(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_").addClass('hide');
            $("ul.mobile-sub li a").removeClass("active");
            $(this).addClass("active");
            var role = $("#role").val();
            $('#ajaxloaddiv').load('lab/patientSearch',function(){
                $("#userrole").val(role);
            });
            e.preventDefault();
        });


        $("#pharm_order").click(function (e) {
            $(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_").addClass('hide');
            $("ul.mobile-sub li a").removeClass("active");
            $(this).addClass("active");
            var providerId = $("#userId").text();
            var jsondata = {"providerId": providerId};
            $.ajax({
                url: "api/pharmacy/fetchReq/ByProviderId",
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(jsondata),
                contentType: 'application/json',

                success: function (data) {
                    var status = data['statusCode'];
                    switch (status) {
                        case 200 :
                            $('#ajaxloaddiv').load('pharmacy/orderStatus', function () {

                                $('#date_timepicker_start').datetimepicker({
                                    changeMonth: true,
                                    scrollMonth: true,
                                    closeOnDateSelect: true,
                                    format: 'd/m/Y',
                                    maxDate: data['curDate'],
                                    timepicker: false,
                                    onSelectDate: function () {
                                        $("#todate").removeClass('hide');
                                    }
                                });

                                $('#date_timepicker_end').datetimepicker({
                                    format: 'd/m/Y',
                                    changeMonth: true,
                                    scrollMonth: true,
                                    closeOnDateSelect: true,
                                    maxDate: data['curDate'],
                                    onShow: function (ct) {
                                        this.setOptions({
                                            minDate: jQuery('#date_timepicker_start').val() ? jQuery('#date_timepicker_start').val() : false
                                        })
                                    },
                                    timepicker: false
                                });

                                $("#providerId").val(providerId);
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

                            });
                            break;
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