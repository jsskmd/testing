$(document).ready(function () {

    $("#billsdatepicker,#toDate,#fromDate").keyup(function () {
        if ($(this).val().length == 2 || $(this).val().length == 5) {
            $(this).val($(this).val() + "/");
        }
    });


    $("#billsdatepicker,#toDate").datepicker({
        dateFormat: "dd/mm/yy",
        maxDate: new Date()

    });
    $("#fromDate").datepicker({
        dateFormat: "dd/mm/yy",
        maxDate: new Date()
    });


});

function checkBillDate(id) {
    var selectedDate = $("#" + id).datepicker('getDate');
    var now = new Date();
    var uName = document.getElementById(id);
    if (selectedDate > now) {
        uName.setCustomValidity("Enter valid date");
    } else {
        uName.setCustomValidity("");
    }

}
$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });



    $(".close-btn").click(function () {
        $("#successMessages").empty();
    });
    $(".cancel-close1").click(function (event) {
        $("#billUploadPopup").removeClass('dailog-show');
        $("#bill_id").val('');
        event.stopPropagation();
    });

    $('body#body').off().on('click', 'a.downloadbill ', function (e) {
        e.preventDefault();

        var ref = $(this).attr("href");
        if (ref != null && ref != '' && ref != 'undefined') {

        } else {
            $("#billUploadPopup").addClass('dailog-show');
            var userId = $("#userId").text();
            $("#bill_userId").val(userId);
            var billId = $(this).parent().find("a.uploadBill").attr("id");
            $("#bill_id").val(billId);
            return false;
        }
    });

    $("#billsuploadform").submit(function (e) {
        e.preventDefault();
        $("#confirmMessage , #errorMessage ").addClass("hide");
        $("#billsuploadform").find(".submit_btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

        var url = "api/user/records/saveBill";
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(getTotalBillsJson('#billsuploadform')),
            contentType: 'application/json',

            success: function (data) {
                var status = data['statusCode'];
                /*   var saveMessage = data['message'];*/
                if (status == 400) {
                    $('#errorMessage').empty().removeClass("hide").prepend(data['message']);
                } else if (status == 200) {
                    $('#confirmMessage').empty().removeClass("hide").prepend(data['message']);
                    $("#bills").trigger("click");
                }
                $("#billsuploadform").find(".submit_btn").html('Save');

            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });


    });
    $("#billsTotalForm").submit(function (e) {
        e.preventDefault();
        var verdict=false;
        var selectedDate = $("#toDate").datepicker('getDate');
        var fromDate = $("#fromDate").datepicker('getDate');
        var uName = document.getElementById("toDate");
        if (fromDate > selectedDate) {
            uName.setCustomValidity("Enter valid date");
            verdict=false;
        } else {
            uName.setCustomValidity("");
            verdict=true;
        }
        if(verdict) {
            $("#billsTotalForm").find(".submit_btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
            var url = "api/user/records/getTotal";
            var userId = $("#userId").text();
            $("#tbUserId").val(userId);
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(getTotalBillsJson('#billsTotalForm')),
                contentType: 'application/json',

                success: function (data) {
                    var status = data['statusCode'];
                    /*  var saveMessage = data['message'];*/
                    if (status == 400) {

                    } else if (status == 200) {
                        var amount = data['amount'];
                        if (amount != null && amount != '' && amount != 'undefined') {
                            $("#totalAmount").text(data['amount']);
                        } else {
                            $("#totalAmount").text(00);
                        }

                    }
                    $("#billsTotalForm").find(".submit_btn").html('Get Total');

                },
                error: function (data) {
                    window.location = 'invalidSession';
                }
            });
        }
    });

    $('body#body').on('click', '.viewBill', function (e) {
        e.preventDefault();
        var newSrc = $(this).attr("id");
        var ext = $(this).attr("href").split('.').pop();
        if (newSrc != 'null' && newSrc != '' && newSrc != 'undefined') {
            $('#iframe,#imgiframe').show();
            if (ext != 'jpg' && ext != 'png' && ext != 'gif') {
                $("#imgiframe").hide();
                $('#iframe').attr('src', newSrc);
                $("#pdfdisplay").addClass('dailog-show');
            } else {
                $('#iframe').hide();
                $("#imgiframe").attr('src', newSrc);
                $("#pdfdisplay").addClass('dailog-show');
            }
        } else {
            $("#billUploadPopup").addClass('dailog-show');
            var userId = $("#userId").text();
            $("#bill_userId").val(userId);
            var billId = $(this).parent().find("a.uploadBill").attr("id");
            $("#bill_id").val(billId);
        }

    });
    $('body#body').on('click', '.uploadBill', function (e) {
        e.preventDefault();
        $("#billUploadPopup").addClass('dailog-show');
        var userId = $("#userId").text();
        $("#bill_userId").val(userId);
        var billId = $(this).attr("id");
        $("#bill_id").val(billId);

    });
    $(".cancel-common").off().on('click', function (event) {
        $("#pdfdisplay").removeClass('dailog-show');
        $('#iframe').attr('src', "");
        event.stopPropagation();
    });


    $("#viewmore_bills").click(function () {
        var value = $(this).text("");
        var url = "api/user/records/getAllBills";
        var userId = $("#userId").text();
        var jsondata = {"userId": userId};
        /* var saveMessage = '';*/
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsondata),
            contentType: 'application/json',

            success: function (data) {

                var status = data['statusCode'];
                /* var saveMessage = data['message'];*/
                if (status == 400) {

                } else if (status == 200) {
                    $('#billsTable').dataTable({
                        "bDestroy": true
                    }).fnDestroy();
                    $('#billFiles').html("");
                    getBills(data);

                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

});



