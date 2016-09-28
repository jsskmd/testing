$(document).ready(function () {

    $("#cancel-email,#cancel-otp,#cancel_verify").click(function () {
        $("#verification").removeClass("dailog-show");
        $("#otpdiv").addClass("hide");
        $("#mobilediv").removeClass("hide");
    });

    $("#verify_mobile").click(function () {
        $("#verification").addClass("dailog-show");
        $('#verifyConfirmMessage,#verifyErrorMessage').empty();
        $("#emailBody,.smsplitcode,.sign_out,.cancel_verification").addClass("hide");
        $("#mobileBody,#cancel_verify").removeClass("hide");
    });

    $("#verify_email").click(function () {
        $("#verification").addClass("dailog-show");
        $('#verifyConfirmMessage,#verifyErrorMessage').empty();
        $("#emailBody,#cancel_verify").removeClass("hide");
        $("#mobileBody,.smsplitcode,.sign_out,.cancel_verification").addClass("hide");
    });

    $("#editClinicProfile").click(function () {
        $("#view_clinicProfile").addClass("hide");
        $("#edit_clinicProfile").removeClass("hide");
    });
    $("#cancelprofile").click(function () {
        $("#view_clinicProfile").removeClass("hide");
        $("#edit_clinicProfile").addClass("hide");
    });
    $(".cancel-common").off().on('click', function (event) {
        $("#clinicProfilePopup").removeClass("dailog-show");
        $("#clinicPreferrencesPopup").removeClass("dailog-show");
        event.stopPropagation();
    });

});

$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $(document).on('submit', '#clinicProfileForm', function (e) {
        e.preventDefault();
        $.ajax({
            url: "api/user/clinic/saveProfile",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(CProfileFormToJSON('#clinicProfileForm')),
            contentType: 'application/json',
            success: function (data) {
                var saveMessage = data['message'];
                if (data['statusCode'] == 200) {
                    getClinicProfile(data, saveMessage);
                    /*appendClinicProfile(data, saveMessage);*/
                        $("#view_clinicProfile").removeClass("hide");
                        $("#edit_clinicProfile").addClass("hide");

                }
                $("#load").hide();
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
        e.preventDefault();
        return false;
    });

});

function appendClinicProfile(data, message) {

    var u = data['clinic'];
    var uci = data['clinic']['user']['userContactInfo'];
    var country = data['countries'];
    getCountry(country, 't_country');
    if (u['userName'] != null && u['userName'] != '' && u['userName'] != 'undefined') {
        $('#userName').text(u['userName']);
    }

    if (u['userId']) {
        $('#clinicUserId').text(u['userId']);
    }
    if (u['clinicName']) {
        $('#clinicUserName').text(u['clinicName']);
        $('#t_clinicName').val(u['clinicName']);
    }
    else {
        $('#clinicName').text("-");
    }
    if (u['mlrNumber']) {
        $('#mrnNumber').text(u['mlrNumber']);
        $('#t_mrnNumber').val(u['mlrNumber']);
    }
    if (uci['city'] != null && uci['city'] != '' && uci['city'] != 'undefined') {
        $('#city').text(uci['city'] + ", ");
        $('#t_city').val(uci['city']);
    }
    else {
        $('#city').text("");
    }
    if (uci['address'] != null && uci['address'] != '' && uci['address'] != 'undefined') {
        $('#address').text(uci['address'] + ", ");
        $('#t_address').val(uci['address']);
    }
    else {
        $('#address').text("-");
    }
    if (uci['zipCode']) {
        $('#zipCode').text(uci['zipCode']);
        $('#t_zipCode').val(uci['zipCode']);
    }
    else {
        $('#zipCode').text("-");
    }
    if (uci['mobilePhone']) {
        $('#mobilePhone').text(uci['mobilePhone']);
        $('#phone').val(uci['mobilePhone']);
    }
    else {
        $('#mobilePhone').text("-");
    }
    if (uci['homePhone']) {
        $('#homePhone').text(uci['homePhone']);
        $('#t_homePhone').val(uci['homePhone']);
    }
    else {
        $('#homePhone').text("-");
    }
    if (uci['email'] ) {
        $('#email').text(uci['email']);
        $('#t_email').val(uci['email']);
    }
    else {
        $('#email').text("-");
    }
    if (uci['location']) {
        $('#location').text(uci['location'] + ", ");
        $('#t_location').val(uci['location']);
    }
    else {
        $('#location').text("");
    }

    if (uci['country'] ) {
        $('#t_country').val(uci['country']);
        var countryval = $('#t_country').find('option:selected').text();

        $('#country').text(countryval);
        if (uci['country'] == 2) {
            fetchStates(uci['country'], "t_state", uci['state']);
            $("#dt_state").removeClass("hide");
            $("#dt_stateInput").addClass("hide");
            $('#country').text(", " + countryval);
        }
        else {
            $("#dt_stateInput").removeClass("hide");
            $("#dt_state").addClass("hide");
            if (uci['otherState'] != '' && uci['otherState'] != 'undefined' && uci['otherState'] != null) {
                $('#state').text(uci['otherState']);
                $('#country').text(", " + countryval);
                $('#t_stateInput').val(uci['otherState']);
            }
            else {
                $('#state').text("");
            }
        }
    }
    else {
        $('#country').text("");
        $("#state").text("");
    }
    if (message != "" && message != undefined && message != null) {
        $("#view_clinicProfile").removeClass("hide");
        $("#edit_clinicProfile").addClass("hide");
    }
}