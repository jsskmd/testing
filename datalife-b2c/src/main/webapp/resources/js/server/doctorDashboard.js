$(document).ready(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $('.popbox').popbox();

    $(".cancel-clinic,.ok_btn").click(function(){
        $("#selectClinicPopup").removeClass("dailog-show");
    });

    $("#headerBar").on('click', '#doctor_profile', function () {
        $("#headerBar").find("a.active").removeClass('active');
        $(this).addClass('active');
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

                    $('#ajaxloaddiv').load("doctor/profile", function (e) {
                        $("#at_userId").val(userId);
                        getBasicInfo(data);

                    });
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#select_homeclinic").change(function () {
        $("#select_clinic").val($("#select_homeclinic").val());
        $("#choose_clinic,#headerBar").removeClass("hide");
        $("#appointments").trigger("click");
    });


    $("#select_clinic").change(function () {
        $("#select_clinic").val($("#select_clinic").val());
        $("#choose_clinic,#headerBar").removeClass("hide");
        $("#appointments").trigger("click");
    });

    $("#headerBar").on('click', '.doctor_patients', function () {
        var id = $(this).attr('name');
        var clinicName = $("#select_clinic").val();
        if (clinicName != '' && clinicName != 'undefined' && clinicName != null) {
            $("#headerBar ").find("a.active").removeClass('active');
            $(this).addClass('active');
            $('#ajaxloaddiv').load("doctor/patients", function () {
                $('#clinicPatientsTable').dataTable({
                    "bDestroy": true
                }).fnDestroy();

                $('#clinicPatientsTable').dataTable({
                    responsive: true,
                    "pagingType": "full_numbers",
                    "language": {
                        "emptyTable": "Enter valid DataLife Id and mobile number to get result !"
                    }
                });
                $("#"+id).trigger('click');
            });
        }else{
            $("#selectClinicPopup").addClass("dailog-show");
        }
    });

    $("#headerBar").on('click', '#doctor_settings', function () {
        var clinicId = $("#select_clinic").val();
        if (clinicId != '' && clinicId != 'undefined' && clinicId != null) {
            $("#headerBar").find("a.active").removeClass('active');
            $(this).addClass('active');
            var doctorId = $("#userId").text();
            getSettings(doctorId, clinicId, 'both');
            $("#show_settings").removeClass("hide");
            $("#change_settings").addClass("hide");
        }else{
            $("#selectClinicPopup").addClass("dailog-show");

        }

    });

    $("#headerBar").on('click', '#appointments', function () {
        var clinicId = $("#select_clinic").val();
        if (clinicId != '' && clinicId != 'undefined' && clinicId != null) {
            $("#headerBar").find("a.active").removeClass('active');
            $(this).addClass('active');
            var doctorId = $("#userId").text();
            getAppointment(doctorId, clinicId, true);
        }else{
            $("#selectClinicPopup").addClass("dailog-show");
        }

    });

});



