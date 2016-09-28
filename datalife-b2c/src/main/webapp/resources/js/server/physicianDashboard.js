$(document).ready(function () {
    $('.popbox').popbox();
});
    $(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });



    $("#headerBar").on('click', '#doctor_profile', function () {
        $("#headerBar a.active").removeClass('active');
        $(this).addClass('active');
        var url = "api/user/patient/basicInfo";
        var userId = $("#userId").text();
        var jsondata = {"userId": userId};
        var saveMessage = '';
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

                    $('#ajaxloaddiv').load("doctor/profile", function (e) {
                        $("#at_userId").val(userId);
                        getBasicInfo(data);

                    });
                }
            },
            error: function (data) {

            }
        });
    });
    $("#headerBar").on('click', '#search_patient', function () {
        $("#headerBar a.active").removeClass('active');
        $(this).addClass('active');
        var saveMessage = '';
        $('#ajaxloaddiv').load("doctor/physicianPatient", function () {
            $('#clinicPatientsTable').dataTable({
                responsive: true,
                "pagingType": "full_numbers",
                "language": {
                    "emptyTable": "Enter valid search field to get result !"
                }
            });
        });
    });
        $("#headerBar").on('click', '#search_refpatient', function () {
            $("#headerBar a.active").removeClass('active');
            $(this).addClass('active');
            var saveMessage = '';
            $('#ajaxloaddiv').load("doctor/SearchReferralPatient", function (e) {
                $('#clinicPatientsTable').dataTable({
                    responsive: true,
                    "pagingType": "full_numbers",
                    "language": {
                        "emptyTable": "Enter valid search field to get result !"
                    }
                });
            });
        });

    $("#headerBar").on('click', '#add_patient', function () {

        $("#headerBar a.active").removeClass('active');
        $(this).addClass('active');
        var url = "api/user/patient/basicInfo";
        var userId = $("#userId").text();
        var jsondata = {"userId": userId};
        var saveMessage = '';
        $('#ajaxloaddiv').load("doctor/addPatient", function (e) {
            $('#confirmMessage,#errorMessage').empty();
        });
    });

        $("#referralDoctorPatient_search").submit(function (e) {
            e.preventDefault();
            $("#referralDoctorPatient_search").find(".submit_btn").html('<img src="resources/images/red_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

            $('#clinicPatientsTable').dataTable({
                "bDestroy": true
            }).fnDestroy();

            $('#clinicPatientsTbody').empty();
            $('#clinicPatientsTable').dataTable({
                responsive: true,
                "pagingType": "full_numbers",
                "language": {
                    "emptyTable": "Enter valid search field to get result !"
                }
            });
            var doctorId = $("#userId").text();
            var input = $("#searchPatientInput").val();
            $.ajax({
                url: "api/user/clinic/searchPatients",
                type: 'POST',
                dataType: 'json',
                data: input,
                contentType: 'application/json',
                success: function (data) {
                    var users = data['users'];
                    var saveStatus = data['statusCode'];
                    if (data['statusCode'] == 200) {
                        var tr = "";
                        $.each(users, function (idx, obj) {

                            tr += '<tr style="text-align:center;"><td>' + obj['userId'] + '</td>' +
                                '<td style="text-align:center;">' + obj['flname'] + '</td>' +
                                '<td style="text-align:center;">' + obj['mobileNo'] + ' </a></td>' +
                                '<td style="text-align:center;"><a onclick="getEncounter(' + obj['userId'] + ',' + doctorId + ',' + null +',' + null + ')">New Consultation</a></td>' +
                                '</tr>';

                        });

                        $('#clinicPatientsTable').dataTable({
                            "bDestroy": true
                        }).fnDestroy();

                        $('#clinicPatientsTbody').empty().html(tr);
                        $('#clinicPatientsTable').dataTable({
                            responsive: true,
                            "pagingType": "full_numbers",
                            "language": {
                                "emptyTable": "Enter valid search field to get result !"
                            }
                        });

                        $("#cliniPatientsDiv").removeClass("hide");

                    } else {
                        $('#errorMessage').empty().prepend(data['message']);

                    }
                    $("#referralDoctorPatient_search").find(".submit_btn").html('Search');
                },
                error: function (data) {
                    window.location = 'invalidSession';
                }
            });

            e.preventDefault();
            return false;
        });

});




