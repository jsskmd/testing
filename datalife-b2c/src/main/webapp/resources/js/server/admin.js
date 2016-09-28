/**
 * Created by barath on 8/25/2016.
 */

$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });


    $("#cancelprofile,#cancel_profile").on('click', function() {
        $("#verifyAndCreateProvider").removeClass("dailog-show");
    });

    $("#cancel-common").on('click',function(){
        $("#s_successMessage").removeClass("dailog-show");
    });

    $("#service_providers").on('click', function() {
        $("#serviceprovider_show").removeClass('hide');
        $("#clinicRegister").addClass('hide');
    });

    $("#createClinic").on('click', function() {
        $("#clinicRegister").removeClass('hide');
        $("#serviceprovider_show").addClass('hide');
    });


    $("#selectSPType").on('change', function() {
        var role = $(this).val();
        if (role) {
            var jsondata = {"role": role};
            alert(JSON.stringify(jsondata));
            switch (role) {
                case "ROLE_HOSPITAL":
                case "ROLE_PHARMACY":
                case "ROLE_REFERRALDOCTOR":
                case "ROLE_TELECONSULTANT":
                case "ROLE_DIAGNOSTICLABS":
                    $.ajax({
                        url: "api/provider/fetch/enquiredProviders",
                        type: 'POST',
                        dataType: 'json',
                        data: JSON.stringify(jsondata),
                        contentType: 'application/json',

                        success: function (data) {
                            var statusCode = data['statusCode'];
                            $('#providerTable1').dataTable({
                                "bDestroy": true
                            }).fnDestroy();
                            switch (statusCode){
                                case 200:
                                    setProviders(data['providers']);
                                    break;
                                case 100:

                                    alert();
                                    break;
                            }
                            if (data['statusCode'] == "200") {

                            }
                        },
                        error: function (data) {
                            window.location = 'invalidSession';
                        }
                    });
                    break;
            }
        }
    });


    $("#providerTable").on('click', 'span.verifyprovider', function () {
        var id =  this.id;
        var jsondata = {"id": id};
        $.ajax({
            url: "api/provider/getProviderDetails",
            type: 'POST',
            dataType: 'json',
            data:JSON.stringify(jsondata),
            contentType: 'application/json',

            success: function (data) {
                if (data['statusCode'] == "200") {
                    var obj = data['providerData'];
                    switch(obj['role']){
                        case "ROLE_REFERRALDOCTOR":
                        case "ROLE_TELECONSULTANT":
                            $("#providerId").val(obj['id']);
                            $("#firstName").val(obj['firstName']);
                            $("#lastName").val(obj['lastName']);
                            $("#emailId").val(obj['email']);
                            $("#mobile").val(obj['mobilePhone']);
                            $("#licNo").val(obj['licNo']);
                            $("#country").val(obj['country']);
                            $("#city").val(obj['city']);

                            $("#verifyAndCreateProvider").addClass('dailog-show');
                            $("#providerSignupForm1").show();
                            $("#providerSignupForm").hide();
                            break;
                        case "ROLE_HOSPITAL":
                        case "ROLE_PHARMACY":
                        case "ROLE_DIAGNOSTICLABS":
                            $("#in_name").val(obj['name']);
                            $("#in_providerId").val(obj['id']);
                            $("#in_contactPerson").val(obj['contactPerson']);
                            $("#in_email").val(obj['email']);
                            $("#in_mobile").val(obj['mobilePhone']);
                            $("#in_website").val(obj['website']);
                            $("#in_workPhone").val(obj['workPhone']);
                            $("#in_yearofEstablishment").val(obj['yearofEstablishment']);
                            $("#in_licNo").val(obj['licNo']);
                            $("#verifyAndCreateProvider").addClass('dailog-show');
                            $("#providerSignupForm").show();
                            $("#providerSignupForm1").hide();
                            break;
                    }

                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#providerSignupForm,#providerSignupForm1").submit(function(e){
        e.preventDefault();
        $("#in_type,#type").val($("#selectSPType").val());
        var id = $(this).attr("id");

        $("#saveEncPopup").addClass("dailog-show");
        $(".cancelapp").off().on('click', function () {
            var retVal = this.id;
            $("#saveEncPopup").removeClass("dailog-show");
            if (retVal == "Yes") {

                /* if( $("#verified").is(':checked') && $("#in_verified").is(':checked')){*/
                $.ajax({
                    url: "api/provider/userSignup",
                    type: 'POST',
                    dataType: 'json',
                    data: JSON.stringify(ProviderRegistrationFormToJSON('#' + id)),
                    contentType: 'application/json',
                    success: function (data) {
                        if (data['statusCode'] == "200") {
                            $("#verifyAndCreateProvider").removeClass('dailog-show');
                            $('#s_confirmMessage').empty().prepend(data['message']);
                            $('#s_successMessage').addClass('dailog-show');
                            $("#selectSPType").trigger('change');
                        }
                    },
                    error: function (data) {
                        window.location = 'invalidSession';
                    }
                });
            }
            else {
                $('#s_confirmMessage').empty().prepend("verification is required before create an account");
                $('#s_successMessage').addClass('dailog-show');
            }
        });

    });


    $('#clinicRegistration').submit(function (e) {
        e.preventDefault();
        $("#clinicRegistration").find(".register-btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

        var url = "api/user/register";

        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(CRegistrationFormToJSON('#clinicRegistration')),
            contentType: 'application/json',
            success: function (data) {
                var statusCode = data['statusCode'];
                var message = data['message'];
                switch (statusCode){
                    case 200:
                        document.getElementById("clinicRegistration").reset();
                        message = '<div class="notification alert-error spacer-t10"><p>'+message+'</p><a class="close-btn">?</a></div>';
                        $("#eMessage").empty().append(message);
                        break;
                    case 406 :
                        message = '<div class="notification alert-error spacer-t10"><p>'+message+'</p><a class="close-btn">?</a></div>';
                        $("#eMessage").empty().append(message);
                        break;
                }

                $("#clinicRegistration").find(".register-btn").html('Sign Up');
            },
            error: function (data) {

            }
        });
        return false;
    });

});


function setProviders(data) {
    var tr = "";
    var id ="providerTable";
    var id2 ="providerTableBody";
    $.each(data, function (idx, obj) {

        if(obj['role'] != null){
            switch (obj['role']){
                case "ROLE_HOSPITAL":
                case "ROLE_PHARMACY":
                case "ROLE_DIAGNOSTICLABS":
                    tr += '<tr><td class="view">' + obj['name'] + '</td>' +
                        '<td class="view">' + obj['contactPerson'] + '</td>' +
                        '<td>' + obj['email'] + '</td>'+
                        '<td>' + obj['workPhone'] + '/' + obj['mobilePhone'] + '</td>'+
                        '<td>' + obj['website'] + '</td>'+
                        '<td><span class="verifyprovider" id="'+obj['id']+'">' + 'Verify' + '</span></td></tr>';

                    break;
                case "ROLE_REFERRALDOCTOR":
                case "ROLE_TELECONSULTANT":
                    id ="providerTable1";
                    id2 ="providerTableBody1";
                    tr += '<tr><td class="view">' + obj['firstName'] +' '+obj['lastName']+'</td>' +
                        '<td class="view">' + obj['mobilePhone'] + '</td>' +
                        '<td>' + obj['email'] + '</td>'+
                        '<td>' + obj['country'] + ' - ' + obj['city'] + '</td>'+
                        '<td><span class="verifyprovider" id="'+obj['id']+'">' + 'Verify' + '</span></td></tr>';

                    break;
            }
        }
    });
    $('#'+id).dataTable({
        "bDestroy": true
    }).fnDestroy();
    $('#'+id2).empty().append(tr);
    $('#'+id).dataTable({
        "responsive": true,
        "pagingType": "full_numbers",
        "language": {
            "emptyTable": "No Users Found !!!"
        }
    }).removeClass('hide');
}