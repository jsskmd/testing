/*$(document).ready(function () {

 var icdCodes = new Bloodhound({
 datumTokenizer: function (datum) {
 return Bloodhound.tokenizers.whitespace(datum.value);
 },
 queryTokenizer: Bloodhound.tokenizers.whitespace,
 limit: 10,
 remote: {
 url: 'common/getICD/%QUERY',
 filter: function (response) {
 return response;
 }
 }
 });
 icdCodes.initialize();

 $("#icdName").typeahead({
 highlight: true
 }, {
 name: 'twitter-oss',
 displayKey: 'name',
 source: icdCodes.ttAdapter()
 }).on('typeahead:selected', function (event, data) {
 $('#icdCode').val(data.icdCode);
 });


 var icdName = new Bloodhound({
 datumTokenizer: function (datum) {
 return Bloodhound.tokenizers.whitespace(datum.value);
 },
 queryTokenizer: Bloodhound.tokenizers.whitespace,
 limit: 10,
 remote: {
 url: 'common/getICDName/%QUERY',
 filter: function (response) {
 return response;
 }
 }
 });
 icdName.initialize();

 $("#icdCode").typeahead({
 highlight: true
 }, {
 name: 'twitter-oss',
 displayKey: 'icdCode',
 source: icdName.ttAdapter()
 }).on('typeahead:selected', function (event, data) {
 $('#icdName').val(data.name);
 });


 var brandNames = new Bloodhound({
 datumTokenizer: function (datum) {
 return Bloodhound.tokenizers.whitespace(datum.value);
 },
 queryTokenizer: Bloodhound.tokenizers.whitespace,
 limit: 10,
 remote: {
 url: 'common/getBrandName/%QUERY',
 filter: function (response) {
 return response;
 }
 }
 });
 brandNames.initialize();

 $("#genericName").typeahead({
 highlight: true
 }, {
 name: 'twitter-oss',
 displayKey: 'genericName',
 source: brandNames.ttAdapter()
 }).on('typeahead:selected', function (event, data) {
 $('#brandName').val(data.brandName);
 });


 var genericNames = new Bloodhound({
 datumTokenizer: function (datum) {
 return Bloodhound.tokenizers.whitespace(datum.value);
 },
 queryTokenizer: Bloodhound.tokenizers.whitespace,
 limit: 10,
 remote: {
 url: 'common/getGenericName/%QUERY',
 filter: function (response) {
 return response;
 }
 }
 });
 genericNames.initialize();

 $("#brandName").typeahead({
 highlight: true
 }, {
 name: 'twitter-oss',
 displayKey: 'brandName',
 source: genericNames.ttAdapter()
 }).on('typeahead:selected', function (event, data) {
 $('#type').val(data.type);
 $("#unitPrice").val(data.price);
 $("#unitstrength").val(data.strength);
 $("#strengthUnits").val(data.strengthUnit);
 });

 var doctorId = $("#duserId").text();
 var clinicId = $("#encClinicId").val();
 var today = $("#serverDate").val();

 });*/

/*function display(id) {
 for(var i=1;i<=11;i++){
 $("#aencounter_"+i).removeClass('enc_color');
 }
 $("#a"+id).addClass('enc_color');
 $("#encounter_body > div").addClass("hide");
 $("#" + id).removeClass("hide");
 }
 $("#edit_encounter_2").click(function () {
 $("#encounter_2").addClass("hide");
 $("#encounter_2_edit").removeClass("hide");
 });
 $("#back_encounter_2").click(function () {
 $("#encounter_2").removeClass("hide");
 $("#encounter_2_edit").addClass("hide");
 });
 $("#view_encounter_3").click(function () {
 $("#encounter_3").addClass("hide");
 $("#encounter_3_edit").removeClass("hide");
 });
 $("#back_encounter_3").click(function () {
 $("#encounter_3").removeClass("hide");
 $("#encounter_3_edit").addClass("hide");
 });*/

$("#hp_soap").click(function () {
    $("#hp_soap_body,#hp_soap_head").removeClass("hide");
    $("#hp_total_body,#hp_total_head").addClass("hide");
    /*$('#confirmMessage,#errorMessage,#ppconfirmMessage,#pperrorMessage').empty().addClass("hide");*/

});
$("#hp_total,#progess_cancel").click(function () {
    /*$('#confirmMessage,#errorMessage,#ppconfirmMessage,#pperrorMessage').empty().addClass("hide");*/
    $("#hp_soap_body,#hp_soap_head").addClass("hide");
    $("#hp_total_body,#hp_total_head").removeClass("hide");
});
function checkEmail(email) {
    var regExp = /(^[a-z]([a-z_0-9\.]*)@([a-z_\.]*)([.][a-z]{2,3})$)|(^[a-z]([a-z_0-9\.]*)@([a-z_\.]*)(\.[a-z]{2,3})(\.[a-z]{2})*$)/i;
    return regExp.test(email);
}

function checkEmails() {
    var verdict = false;
    var emails = document.getElementById("demo-input-facebook-theme").value;
    if (emails != '' && emails != null && emails != 'undefined') {
        var emailArray = emails.split(",");
        for (var i = 0; i <= (emailArray.length - 1); i++) {
            if (checkEmail(emailArray[i])) {
                verdict = true;
            }
        }
    }
    return verdict;
}

$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $(".cancel-close").click(function () {
        $("#patient_info_popup,#apptCancelCnfmPopup").removeClass("dailog-show");
    });

    $(".spImage-close").click(function () {
        $("#specialityImagePopup").removeClass("dailog-show");
    });

    $("#shareReportForm").submit(function (e) {
       /* $('#perrorMessage,#pconfirmMessage').empty();*/
        if (checkEmails()) {
            $(".small_loading").removeClass("hide");
            var val = $("#demo-input-facebook-theme").val();
            var favorite = "";
            $.each($("input[name='selectedRecords']:checked"), function () {
                if (favorite == "") {
                    favorite += $(this).val();
                }
                else {
                    favorite += "," + $(this).val();
                }
            });


            $("#selectedsummary").val(favorite);
            var url = "api/user/patient/shareRecord";
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(PShareReportFormToJSON('#shareReportForm')),
                contentType: 'application/json',
                success: function (data) {
                    var saveStatus = data['statusCode'];
                    var message = data['message'];
                    if (saveStatus == 200) {
                        $("#reportPassword").val("");
                        $("#shareRecordPopup").removeClass('dailog-show');
                        $('#pconfirmMessage').empty().removeClass().show().prepend(message).delay(5000).fadeOut(100, function () {
                            $('#pconfirmMessage').empty();
                        });
                    }
                    if (saveStatus == 406) {

                        $('#perrorMessage').empty().removeClass().show().prepend(message).delay(5000).fadeOut(100, function () {
                            $('#perrorMessage').empty();
                        });
                        $("#reportPassword").val("");
                    }
                    $(".small_loading").addClass("hide");
                    $("#demo-input-facebook-theme").val("");
                },
                error: function (data) {
                    window.location = 'invalidSession';
                }
            });
        } else {
            $('#perrorMessage').empty().removeClass().show().prepend('Invalid Email entered.').delay(5000).fadeOut(100, function () {
                $('#perrorMessage').empty();
            });
        }
        e.preventDefault();
    });

    $('#myrecords').on('click', '.shareRecord', function (e) {
        e.preventDefault();
       /* $('#perrorMessage,#pconfirmMessage').empty();*/
        var userId = $("#userId").text();
        var ticket = $("#alfrescoTicket").val();
        $("#shareticket").val(ticket);
        $('#shareUserId').val($("#patient_id").text());

        $("#shareRecordPopup").addClass('dailog-show');
        var id = $(this).attr("id");
        $("input[value='" + id + "']").prop('checked', true);

    });

    $('#myrecords').on('click', '.recordsview', function (e) {
        e.preventDefault();
        var ext = $(this).attr("id").split('.').pop();
        var newSrc = $(this).val();
        $('#iframe,#imgiframe').show();
        if (ext != 'jpg' && ext != 'png' && ext != 'gif' && ext != 'jpeg') {
            $("#imgiframe").hide();
            $('#iframe').attr('src', newSrc);
            $("#pdfdisplay").addClass('dailog-show');

        } else {
            $('#iframe').hide();
            $("#imgiframe").attr('src', newSrc);
            $("#pdfdisplay").addClass('dailog-show');
        }
    });

    $(".dict-for-patient").click(function () {
        $("#doc-dash-for-dict").addClass("dailog-show");
        $('#livedictation').empty();
        $.get('doctor/livedictation', function(result){
            $result = $(result);
            $result.find('script').appendTo('#livedictation');
            $result.find('style').appendTo('#livedictation');
            $result.find('link').appendTo('#livedictation');
            $result.find('#append').appendTo('#livedictation');
        });

        /*$("#livedictation").removeClass("hide");
        $("#hp_total_body,#hp_total_head,#hp_soap_body,#hp_soap_head").addClass("hide");
        $('#confirmMessage,#errorMessage,#ppconfirmMessage,#pperrorMessage').empty();*/

    });

    $("#doctor_monitored").click(function (e) {
        e.preventDefault();
        var url = "api/user/patient/getDoctorVitals";
        insertVitals(url);
        $(this).addClass("show_border_clinic");
        $("#patient_monitored").removeClass("show_border_clinic");
    });

    $("#patient_monitored").click(function (e) {
        e.preventDefault();
        var url = "api/user/patient/getPatientVitals";
        insertVitals(url);
        $(this).addClass("show_border_clinic");
        $("#doctor_monitored").removeClass("show_border_clinic");
    });

    $("#enc_vitalGraph").click(function () {
        $("#graphDisplay").addClass("dailog-show");
    });

    $("#cdpspecialityImages").off().on("click", function (e) {
        $("#specialityImagePopup").addClass("dailog-show");
        e.preventDefault();
    });


    $("#cancel-common").click(function () {
        $("#s_successMessage").removeClass("dailog-show");
    });

    $(".cancel-close1").click(function () {
        $("#tab-for-enc").hide();
        $("#script-state").show();
        $("#enc").addClass('hide');
        $("#patient_info_popup,#apptCancelCnfmPopup").removeClass("dailog-show");

    });

    $("#consultation_back,#progress_back").click(function (e) {
        e.preventDefault();
        var id = $("#verdict").val();
        if (id == 'true') {

            getAppointment($("#encDoctorId").val(), $("#encClinicId").val(), true);

        } else if (id == 'false') {

            var clinicName = $("#select_clinic").val();
            if (clinicName != '' && clinicName != 'undefined' && clinicName != null) {
                $("#headerBar a.active").removeClass('active');
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
                });

            }
        }
        else {
            var url = "doctor/SearchReferralPatient";
            if ($("#verdict").val() == 1) {
                url = "doctor/physicianPatient";
            }
            $('#ajaxloaddiv').load(url, function () {
                $('#clinicPatientsTable').dataTable({
                    responsive: true,
                    "pagingType": "full_numbers",
                    "language": {
                        "emptyTable": "Enter valid search field to get result !"
                    }
                });
            });
        }

    });

    $("#viewmore_records").click(function () {
        var value = $(this).text("");
        $('#myrecords').html("");
        var url = "api/user/records/getallFiles";
        var userId = $("#patient_id").text();
        var sendType = $("#recordType").val();
        var jsondata = {"userId": userId, "sendType": sendType, "doctorId": $("#encDoctorId").val(), "clinicId": $("#encClinicId").val()};
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
                    getRecords(data);
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

   /* $("#enc_save_history").click(function () {

        var jsonData = {"user": {"userId": $("#encPatientId").val()}, "medicalHistory": $("#edit_mh").val(), "surgicalHistory": $("#edit_sh").val(),
            "familyHistory": $("#edit_fh").val(), "socialHistory": $("#edit_sch").val(),
            "riskFactors": $("#edit_rf").val(), "pastMedications": $("#edit_pm").val(), "allergies": $("#edit_all").val()};

        $.ajax({
            url: "api/user/patient/history/save",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsonData),
            contentType: 'application/json',
            success: function (data) {
                if (data['statusCode'] == 200) {
                    $("#encounter_2_edit").find("textarea").val("");
                    getHistory(data);
                    $("#back_encounter_2").trigger("click");
                }
                $("#load").hide();
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });*/

/*
    $("#chiefComplaint,#hpi,#subjective").keyup(function () {
        $('#errorMessage,#pperrorMessage').empty();
    });
*/

  /*  $('.keyup-fake').keyup(function (e) {
        e.preventDefault();
        $(this).removeClass('textareavalidate');
        var inputVal = $(this).val();
        var fakeReg = /(.)\1{2,}/;
        if (fakeReg.test(inputVal)) {
            $(this).addClass('textareavalidate');
        }
    });*/

    /*$('#encounterform').submit(function (e) {
        e.preventDefault();
        $('#confirmMessage,#errorMessage').empty();
        var cc = $("#chiefComplaint").val();
        var hpi = $("#hpi").val();
        var flag = true;
        var idwithRed = "";
        var id = this.id;
        if($(this).find('textarea').hasClass('textareavalidate')){
            flag = false;
            $('.textareavalidate').each(function(){
                if (idwithRed == "") {
                    idwithRed += $(this).attr("title");
                }else {
                    idwithRed += "," + $(this).attr("title");
                }
            });

        }
        if ((cc != null && cc != '' && cc != 'undefined') && flag) {
            $("#saveEncPopup").addClass("dailog-show");
            $(".cancelapp").off().on('click', function () {
                var retVal = this.id;
                $("#saveEncPopup").removeClass("dailog-show");
                if (retVal == "Yes") {
                    $("#"+id).find(".submit_btn").html('<img src="resources/images/red_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

                    $.ajax({
                        url: "api/user/doctor/saveEncounter",
                        type: 'POST',
                        dataType: 'json',
                        data: JSON.stringify(EncounterFormToJSON('#'+id)),
                        contentType: 'application/json',
                        success: function (data) {
                            if (data['statusCode'] == 200) {
                                var age = $("#enc_age").val();
                                var pid = $("#encPatientId").val();
                                var did = $("#encDoctorId").val();
                                var cid = $("#encClinicId").val();

                                document.getElementById(id).reset();

                                $('#confirmMessage').show().empty().prepend(data['message']).delay(1500).fadeOut(100, function () {
                                    $('#confirmMessage').empty();
                                });
                                $("#enc_age,#hp_enc_age").val(age);
                                $("#encPatientId,#hp_encPatientId").val(pid);
                                $("#encDoctorId,#hp_encDoctorId").val(did);
                                $("#encClinicId,#hp_encClinicId").val(cid);

                                $("#pebody").load("doctor/physicalExamination");
                                $("#cdpspecialityImages").css("display", "none");
                                $("#toolsketech").html('');
                                $("#enc_share").val("false");
                                var jsondata = {"encounterId": data['encId']};
                                $.ajax({
                                    url: "api/user/doctor/generatePdfByEncId",
                                    type: 'POST',
                                    dataType: 'json',
                                    data: JSON.stringify(jsondata),
                                    contentType: 'application/json'
                                });
                            } else {
                                $('#errorMessage').empty().prepend(data['message']);
                            }
                            $("#"+id).find(".submit_btn").html('Save');
                        },
                        error: function (data) {
                            window.location = 'invalidSession';
                        }
                    });
                }
            });

        } else {
            if(flag){
                $('#errorMessage').empty().prepend("Chief complaint is mandatory !");
            }else{
                $('#errorMessage').empty().prepend('Please enter valid input in '+idwithRed).delay(7000).fadeOut(100, function () {
                    $('#message').empty();
                });
            }

        }

        e.preventDefault();
        return false;
    });*/

    $("#recordType").on('change',function (e) {
        e.preventDefault();
        fetchPatientRecords();
    });

    $(".search-doc.for.scrip").click(function () {
        $("#tab-for-enc").hide();
        $("#script-state").show();
        $("#enc").addClass('hide');
    });

    $(".ad-doc.for.prescr-heal-rec").on('click',function (e) {
        e.preventDefault();
        $("#enc").removeClass('hide');
        $("#script-state").hide();
        $("#tab-for-enc").hide();
        $("#recordType").val("hospital");
        fetchPatientRecords();
    });

    $("#patient_info").click(function (e) {
        var patientId = $("#patient_id").text();
        var url = "api/user/patient/getPatientDetails";
        var jsondata = {"patientId": patientId};

        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsondata),
            contentType: 'application/json',
            mimeType: 'application/json',

            success: function (data) {
                var statusCode = data['statusCode'];

                if (statusCode == 200) {
                    $("#patient_info_popup").addClass("dailog-show");
                    $("#p_id").text(data['userId']);
                    $("#p_name").text(data['patientName']);
                    var info = data['contactInfo'];
                    $("#p_email").text(info['email']);
                    $("#p_mobile").text(info['mobilePhone']);
                    $("#p_address").text(info['address']);
                    $("#p_city").text(info['city']);
                    $("#p_location").text(info['location']);
                    $("#p_state").text(data['state']);
                    if (data['state'] == 'Select State') {
                        $("#p_state").text(" - ");
                    }
                    $("#p_country").text(data['country']);
                    $("#p_zipcode").text(info['zipCode']);

                }

            },
            error: function () {
                window.location = 'invalidSession';
            }
        });
        e.preventDefault();
    });

    $("#get_template").on('change', function (e) {
        e.preventDefault();

        var myContextPath = $("#myContextPath").val();
        setTimeout(function(){ }, 5000);
        var speciality = $(this).val();
        $("#pebody").load("doctor/physicalExamination");
        if (speciality == 0) {
            displaype(1);
            $("#cdpspecialityImages").css("display", "none");
            $("#toolsketech").html('');
        } else {
            $.ajax({
                url: "api/user/doctor/getCardiology",
                type: 'POST',
                dataType: 'json',
                data: speciality,
                contentType: 'application/json',
                success: function (data) {
                    var status = data['statusCode'];

                    if (status == 200) {
                        displaype(1);
                        var verdict=false;
                        $("#cdpspecialityImages").css("display", "block");
                        if (data['dataStatus'] == 101) {
                            if(!verdict){
                                $("#peCategory5").trigger("click");
                                var respCheckbox = '<div class="togglecontents"><div class="option-group field">',
                                    respTarea = '<div class="form-main-container">',
                                    cardText = ' <div class="togglecontents form-wrapper"><div class="row">',
                                    cardCheckbox = '<div class="togglecontents"><div class="option-group field">',
                                    cardTarea = '<div class="form-main-container">';
                                $.each(data['cardioLabels'], function (i, obj) {
                                    var str = obj[1];
                                    var id = str.replace(" ", "");
                                    if (obj[2] == 'checkbox') {
                                        cardCheckbox += '<label class="uit-option">' +
                                            '<input type="checkbox" name="cardioVascular.cardiovascularLabels.cardioLabelId" value="' + obj[0] + '" class="spcbox cardio"/>' +
                                            '<span class="checkbox-option">' + obj[1] + '</span>' +
                                            '</label><input type="hidden" name="cardioVascular.cardiovascularLabels.labelType" value="checkbox"/>' +
                                            '<input type="hidden" name="cardioVascular.descriptions"/>';

                                    }
                                    if (obj[2] == 'text') {
                                        cardText += '<div class="col-md-4 col-sm-6 no-pad">' +
                                            '<fieldset class="input-block">' +
                                            '<label>' + obj[1] + '</label>' +
                                            '<input type="hidden" name="cardioVascular.cardiovascularLabels.cardioLabelId"  value="' + obj[0] + '"/>' +
                                            '<input type="hidden" name="cardioVascular.cardiovascularLabels.labelType" value="text"/>' +
                                            '<input type="text" name="cardioVascular.descriptions" class="nc keyup-characters"' +
                                            ' id="' + id + '" title="' + obj[1] + '" placeholder="' + obj[1] + '"/></div>';

                                    }
                                    if (obj[2] == 'textarea') {
                                        cardTarea += '<div class="columns-5"><div class="input-wrapper"><div class="form-left"></div><div class="form-right">' +
                                            '<input type="hidden" name="cardioVascular.cardiovascularLabels.cardioLabelId" value="' + obj[0] + '">' +
                                            '<input type="hidden" name="cardioVascular.cardiovascularLabels.labelType" value="textarea"/>' +
                                            '<textarea style=" word-wrap: break-word; resize: vertical; height: 70px;" class="keyup-fake" title="Cardiovascular Description" name="cardioVascular.descriptions"' +
                                            'value="' + obj[0] + '" /></textarea></div></div></div><div class="clear"></div>';
                                    }
                                });
                                $.each(data['respiratoryLabels'], function (ind, object) {
                                    var str = object[1];
                                    var id = str.replace(" ", "");
                                    if (object[2] == 'checkbox') {
                                        respCheckbox += '<label class="uit-option">' +
                                            '<input type="checkbox" name="respiratory.respiratoryLabels.respiratoryLabelId" value="' + object[0] + '" class="spcbox resp" id="' + id + '"/>' +
                                            '<span class="checkbox-option">' + object[1] + '</span>' +
                                            '</label>' +
                                            '<input type="hidden" name="respiratory.respiratoryLabels.labelType" value="checkbox"/>' +
                                            '<input type="hidden" name="respiratory.descriptions"/>';
                                    }
                                    if (object[2] == 'textarea') {
                                        respTarea += '<div class="columns-5"><div class="input-wrapper"><div class="form-left"></div><div class="form-right">' +
                                            '<input type="hidden" name="respiratory.respiratoryLabels.respiratoryLabelId" value="' + object[0] + '" >' +
                                            '<input type="hidden" name="respiratory.respiratoryLabels.labelType" value="textarea"/>' +
                                            '<textarea style=" word-wrap: break-word; resize: vertical; height: 70px;"class="keyup-fake" title="Respiratory Descriptions" name="respiratory.descriptions"' +
                                            'value="' + object[0] + '" /></textarea></div></div></div><div class="clear"></div>';

                                    }
                                });
                                cardCheckbox += '<div class="clear"></div></div></div>';
                                cardText += '</div></div>';
                                cardTarea += '</div></div>';
                                var cardio = cardCheckbox + cardText + cardTarea;

                                respTarea += '</div></div>';
                                respCheckbox += '<div class="clear"></div></div></div>';
                                var pulmonary = respCheckbox + respTarea;
                                $("#resptemplate").html(pulmonary);
                                $("#cardiotemplate").html(cardio);
                                verdict=true;
                                setTimeout(
                                    function()
                                    {

                                    }, 5000);
                            }
                            if(verdict){
                                $("#cdpspecialityImages").attr("src", "resources/images/speciality/heart.png");
                                $("#toolsketech").html('<canvas class="img-responsive canvasimg1" id="tools_sketch" width="550" height="350" ></canvas>');
                                $(function () {
                                    $('#tools_sketch').sketch({defaultColor: "#ff0"});
                                });
                                verdict=false;
                            }

                        }
                        if (data['dataStatus'] == 201) {
                            if(!verdict){
                                $("#peCategory12").trigger("click");
                                var muscoloskeletalLabels = data['muscoloskeletalLabels'];

                                var musText = ' <div class="togglecontents form-wrapper"><div class="row">',
                                    musCheckbox = '<div class="togglecontents"><div class="option-group field">',
                                    musTarea = '<div class="form-main-container">';
                                $.each(muscoloskeletalLabels, function (i, obj) {
                                    var str = obj[1];
                                    var id = str.replace(" ", "");
                                    if (obj[2] == 'checkbox') {
                                        musCheckbox += '<label class="uit-option">' +
                                            '<input type="checkbox" name="muscoloskeletal.muscoloskeletalLabels.muscoloLabelId" value="' + obj[0] + '" class="spcbox cardio"/>' +
                                            '<span class="checkbox-option"></span>' + obj[1] +
                                            '</label>' +
                                            '<input type="hidden" name="muscoloskeletal.muscoloskeletalLabels.labelType" value="checkbox"/>' +
                                            '<input type="hidden" name="muscoloskeletal.descriptions"/>';

                                    }
                                    if (obj[2] == 'text') {
                                        musText += '<div class="col-md-4 col-sm-6 no-pad">' +
                                            '<fieldset class="input-block">' +
                                            '<label>' + obj[1] + '</label>' +
                                            '<input type="hidden" name="muscoloskeletal.muscoloskeletalLabels.muscoloLabelId"  value="' + obj[0] + '"/>' +
                                            '<input type="hidden" name="muscoloskeletal.muscoloskeletalLabels.labelType" value="text"/>' +
                                            '<input type="text" name="muscoloskeletal.descriptions" class="nc keyup-characters"' +
                                            ' id="' + id + '" title="' + obj[1] + '" placeholder="' + obj[1] + '"/></fieldset></div>';

                                    }
                                    if (obj[2] == 'textarea') {
                                        musTarea += '<div class="columns-5"><div class="input-wrapper"><div class="form-left"></div><div class="form-right">' +
                                            '<input type="hidden" name="muscoloskeletal.muscoloskeletalLabels.muscoloLabelId" value="' + obj[0] +
                                            '" placeholder="' + obj[1] + '"class="spcbox" type="text" id="cardioDiagnoses" placeholder="Enter Differential Diagnoses">' +
                                            '<input type="hidden" name="muscoloskeletal.muscoloskeletalLabels.labelType" value="textarea"/>' +
                                            '<textarea style=" word-wrap: break-word; resize: vertical; height: 70px;" class="keyup-fake" title="Muscoloskeletal Descriptions" name="muscoloskeletal.descriptions"' +
                                            'value="' + obj[0] + '" /></textarea></div></div></div><div class="clear"></div>';
                                    }
                                });
                                musCheckbox += '<div class="clear"></div></div></div>';
                                musText += '</div></div>';
                                musTarea += '</div></div>';
                                var mus = musCheckbox + musText + musTarea;

                                $("#mustemplate").html(mus);
                                verdict=true;
                                setTimeout(
                                    function()
                                    {

                                    }, 5000);
                            }
                            if(verdict){
                                $("#cdpspecialityImages").attr("src", "resources/images/speciality/1375.jpg");
                                $("#toolsketech").html('<canvas class="img-responsive canvasimg2-ortho" id="tools_sketch" width="800" height="700" ></canvas>');
                                $(function () {
                                    $('#tools_sketch').sketch({defaultColor: "#ff0"});
                                });
                                verdict=false;
                            }


                        }
                        if (data['dataStatus'] == 301) {
                            if(!verdict){
                                $("#peCategory8").trigger("click");
                                var gastrointestinalLabels = data['gastrointestinalLabels'];

                                var gastroText = ' <div class="togglecontents form-wrapper"><div class="row">',
                                    gastroCheckbox = '<div class="togglecontents"><div class="option-group field">',
                                    gastroTarea = '<div class="form-main-container">';
                                $.each(gastrointestinalLabels, function (i, obj) {

                                    var str = obj[1];
                                    var id = str.replace(" ", "");
                                    if (obj[2] == 'checkbox') {
                                        gastroCheckbox += '<label class="uit-option">' +
                                            '<input type="checkbox" name="gastrointestinal.gastrointestinalLabels.gastroLabelId" value="' + obj[0] + '" class="spcbox cardio"/>' +
                                            '<span class="checkbox-option"></span>' + obj[1] +
                                            '</label>' +
                                            '<input type="hidden" name="gastrointestinal.gastrointestinalLabels.labelType" value="checkbox"/>' +
                                            '<input type="hidden" name="gastrointestinal.descriptions"/>';

                                    }
                                    if (obj[2] == 'text') {
                                        gastroText += '<div class="col-md-4 col-sm-6 no-pad"><fieldset class="input-block">' +
                                            '<label>' + obj[1] + '</label>' +
                                            '<input type="hidden" name="gastrointestinal.gastrointestinalLabels.gastroLabelId"  value="' + obj[0] + '"/>' +
                                            '<input type="hidden" name="gastrointestinal.gastrointestinalLabels.labelType" value="text"/>' +
                                            '<input type="text" name="gastrointestinal.descriptions" class="nc keyup-characters"' +
                                            ' id="' + id + '" title="' + obj[1] + '" placeholder="' + obj[1] + '"/></fieldset></div>';

                                    }
                                    if (obj[2] == 'textarea') {
                                        gastroTarea += '<div class="columns-5"><div class="input-wrapper"><div class="form-left"></div><div class="form-right">' +
                                            '<input type="hidden" name="gastrointestinal.gastrointestinalLabels.gastroLabelId" value="' + obj[0] +
                                            '" placeholder="' + obj[1] + '"class="spcbox" type="text" id="cardioDiagnoses" placeholder="Enter Differential Diagnoses">' +
                                            '<input type="hidden" name="gastrointestinal.gastrointestinalLabels.labelType" value="textarea"/>' +
                                            '<textarea style=" word-wrap: break-word; resize: vertical; height: 70px;"class="keyup-fake" title="Gastrointestinal Descriptions" name="gastrointestinal.descriptions"' +
                                            'value="' + obj[0] + '" /></textarea></div></div></div><div class="clear"></div>';
                                    }
                                });
                                gastroCheckbox += '<div class="clear"></div></div></div>';
                                gastroText += '</div></div>';
                                gastroTarea += '</div></div>';
                                var gastro = gastroCheckbox + gastroText + gastroTarea;

                                $("#gastrotemplate").html(gastro);
                                verdict=true;
                                setTimeout(
                                    function()
                                    {

                                    }, 5000);
                            }
                            if(verdict){
                                $("#cdpspecialityImages").attr("src", "resources/images/speciality/Digestive-Body-Diagram.jpg");

                                $("#toolsketech").html('<canvas class="img-responsive canvasimg3-digestive" id="tools_sketch" width="550" height="350" ></canvas>');

                                $(function () {
                                    $('#tools_sketch').sketch({defaultColor: "#ff0"});
                                });
                                verdict=false;
                            }

                        }
                    }
                    else {

                    }
                  /*  $("#load").hide();*/

                },
                error: function (data) {
                    window.location = 'invalidSession';
                }

            });
        }
    });
});



function fetchPatientRecords(){

    $("#viewmore_records").text("View More");
    var url = "api/user/records/getFiles";
    var userId = $("#patient_id").text();
    var sendType = $("#recordType").val();
    var jsondata = {"userId": userId, "sendType": sendType, "doctorId": $("#encDoctorId").val(), "clinicId": $("#encClinicId").val()};
    var type = "Hospital Records";
    if (sendType == 'prescription') {
        type = "Prescription";
    }
    if (sendType == 'laborder') {
        type = "Lab Order";
    }
    if (sendType == 'diagnostic') {
        type = "Lab Reports";
    }
    if (sendType == 'mri') {
        type = "MRI";
    }
    if (sendType == 'xray') {
        type = "X-Ray";
    }
    if (sendType == 'others') {
        type = "Miscellaneous";
    }
    $("#record_type").text(type);

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
                getRecords(data);
                $("#mybills_info").text("Showing 1 to 5 of " + data['count'] + " entries");
                if (data['count'] < 5) {
                    $("#mybills_info").text("Showing 1 to " + data['count'] + " of " + data['count'] + " entries");
                }
            }
        },
        error: function (data) {
            window.location = 'invalidSession';
        }
    });
}


function SOAPFormToJSON(form) {
    var array = jQuery(form).serializeArray();
    var json = {};
    var newName;
    var parentName;
    var innernewName;
    var newVal;
    var user = {};
    var miniEncounter = {};

    jQuery.each(array, function (inx, element) {
        var name = this.name;
        if (name.indexOf(".") > -1) {
            parentName = name.split('.')[0];
            innernewName = name.split('.')[2];
            newName = name.split('.')[1];
            if (parentName == 'soap') {
                miniEncounter[newName] = this.value || '';
                newVal = miniEncounter;
            }
            if (parentName == 'user') {
                user[newName] = this.value || '';
                newVal = user;
            }
        }
        else {
            parentName = this.name;
            newVal = this.value;
        }
        json[parentName] = newVal || '';
    });
    delete json['share'];
    var share = $("#soap_share").is(':checked');
    json['share'] = !share;
    delete json['shift'];
    return json;
}

function getRecords(data) {

    $(".dataSize").text(data['size']);
    var tr = "";

    $.each(data['requestedFiles'], function (idx, obj) {
        var speciality = "NM";
        if (obj['speciality'] != null && obj['speciality'] != "") {
            var str = obj['speciality'];
            speciality = str.replace(/[^a-zA-Z ]/g, "");
        }
        var cc = "-";
        if (obj['chiefComplaint'] != null && obj['chiefComplaint'] != '' && obj['chiefComplaint'] != 'undefined') {
            cc = obj['chiefComplaint'];
        }
        var dg = "-";
        if (obj['diagnosis'] != null && obj['diagnosis'] != '' && obj['diagnosis'] != 'undefined') {
            dg = obj['diagnosis'];
        }
        tr += '<tr style="text-align:center;">' +
            '<td><input type="checkbox" class="select" name="selectedRecords" value="' + obj['fileId'] + '"/></td>' +
            '<td>' + getDateFromSeconds(obj['encounterDate']) + '</td>' +
            '<td style="text-align:center;">' + speciality + '</td>' +
            '<td>' + cc + '</td>' +
            '<td style="text-align:center;"><a href="#" class="specality-news">' + dg + '</a></td>' +
            '<td style="text-align:center;"><button type="button" id="' + obj['fileName'] + '" class="recordsview View" value="' + obj['vurl'] + '">View</button>' +
            '<button type="button" class="shareRecord share" id="' + obj['fileId'] + '">Share</button>' +
            '<input type="hidden" id="alfrescoTicket" value="' + data['ticket'] + '"/></td></tr>';
    });

    $.getScript('resources/jsplugins/site.js',function() {

        $('#mybills').dataTable({
            "bDestroy": true
        }).fnDestroy();

        $('#myrecords').html("").append(tr);
        $('#mybills').dataTable({
            "responsive": true,
            "pagingType": "full_numbers",
            "order": [],
            "columnDefs": [
                { "targets": [0, 1, 2, 3, 4, 5], "orderable": false }
            ],
            "language": {
                "emptyTable": "No Records Found !!!"
            }
        });
    });
}

function insertVitals(url) {
    $('#encVitals').empty();
    var userId = $("#patient_id").text();
    var jsondata = {"userId": userId};

    $.ajax({
        url: url,
        type: 'POST',
        crossDomain: true,
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',
        mimeType: 'application/json',

        success: function (data) {
            var tr = "";
            if (data['statusCode'] == 200) {

                $.each(data['vitals'], function (idx, obj) {
                    var d = new Date(obj['date']);
                    var date = d.getDate() + "/" + (d.getMonth() + 1) + "/" + d.getFullYear() + " " + d.getHours() + ":" + d.getMinutes();
                    tr += "<tr>";
                    tr += "<td class='td rdt'>" + date + "</td>";
                    tr += "<td class='td'>" + obj['temp'] + "</td>";
                    tr += "<td class='td'>" + obj['bp'] + "</td>";
                    tr += "<td class='td'>" + obj['respRate'] + "</td>";
                    tr += "<td class='td'>" + obj['heartRate'] + "</td>";
                    tr += "<td class='td'>" + obj['height'] + "</td>";
                    tr += "<td class='td'>" + obj['weight'] + "</td>";
                    tr += "<td class='td'>" + obj['bmi'] + "</td>";
                    tr += "<td class='td'>" + obj['sugar'] + "</td>";
                    tr += "</tr>";
                });
                $('#encVitalsTable').dataTable({
                    "bDestroy": true
                }).fnDestroy();
                $('#encVitals').empty().append(tr);
                $('#encVitalsTable').removeClass("hide").dataTable({
                    "responsive": true,
                    "pagingType": "full_numbers",
                    "bPaginate": false,
                    "bFilter": false,
                    "bInfo": false,
                    "columnDefs": [
                        { "targets": [1, 2, 3, 4, 5, 6, 7, 8], "orderable": false }
                    ],
                    "language": {
                        "emptyTable": "No Vital Records Entered !!!"
                    }
                });

                $("#load").hide();

            }

        },
        error: function (data) {
            window.location = 'invalidSession';
        }
    });
}

function PShareReportFormToJSON(form) {
    var array = jQuery(form).serializeArray();
    var json = {};

    jQuery.each(array, function () {
        json[this.name] = this.value || '';
    });

    return json;
}

//onclick of view navigate to viewComplete.jsp and display the current day scheduled status
function getAppointment(doctorId, clinicId, isFromDoc) {
    var url = "api/user/appointment/getDoctorSlotsOnDate";
    // value indicates to fetch current day appointment
    var jsondata = {"doctorId": doctorId, "clinicId": clinicId, "value": true};

    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',

        success: function (data) {

            var saveStatus = data['statusCode'];
            var settings = data['settings'];
            var tr = "";
            var msg = "No data available in table";
            $("#ajaxloaddiv").load("clinic/getCDMainViewAppt", function () {
                var docDetails = data['doctorDetails'];

                if (isFromDoc) {
                    var clinicName = docDetails['clinicName'];
                    $("#displayDocName").text(clinicName);
                    $("#docImage").attr('src', docDetails['clinicImageUrl']);
                    $("#docCancelSetting").show();
                    $("#isFromDocLogin").val(true);
                } else {
                    var docName = docDetails['doctorName'];
                    var specialityName = '';
                    if (docDetails['specialityName'] != null && docDetails['specialityName'] != 'undefined') {
                        specialityName = docDetails['specialityName'];
                    }
                    $("#docSpeciality").text(specialityName);
                    $("#displayDocName").text(docName);
                    $("#docImage").attr('src', docDetails['doctorImageUrl']);
                    $("#selectedDoct").val(doctorId);
                }
                $("#curDate").val(data['curDate']);
                $("#serverDate").val(data['serverDate']);
                $("#maxDateToShow").val(data['maxDateToShow']);
                $("#pastDateToShow").val(data['pastDateToShow']);

                if (saveStatus == 200) {

                    $.each(data['resultedSlots'], function (idx, obj) {
                        if (!$.isEmptyObject(obj)) {

                            var flag = true;

                            $.each(obj, function (i, str) {

                                if (str['status'] != null) {
                                    switch (str['status']) {
                                        case "SCHEDULED":
                                        case "ARRIVED":
                                        case "INPROGRESS":
                                        case "COMPLETED":
                                            tr += '<tr id="' + str['tokenNo'] + '">' +
                                                '<td>' + str['tokenNo'] + '</td>' +
                                                '<td>' +
                                                ' <div class="col-md-12 today-appointment-line">';
                                            if (data['showNewEncounter']) {
                                                tr += '<a class="image-function" id="' + str['tokenNo'] + '" href="#" onclick="showPatDetailOnSlot(' + doctorId + "," + clinicId + "," + str['patientId'] + ',this,' + data['showNewEncounter'] + ')">' +
                                                    '<input type="hidden" id="date' + str['tokenNo'] + '" value="' + idx + '">' +
                                                    '<div class="col-md-12 no-pad doc-name-for-app"><span class="name-class-for-doctor">' + ' Mr/Ms ' + str['patientName'] + '</span><br>DataLife Id:<span id="patId' + str['tokenNo'] + '">' + str['patientId'] + '</span></div>' +
                                                    '</a>';
                                            } else {
                                                tr += '<a class="image-function" href="#">' +
                                                    '<div class="col-md-12 no-pad doc-name-for-app"><span class="name-class-for-doctor">' + ' Mr/Ms ' + str['patientName'] + '</span><br>DataLife Id:<span id="patId' + str['tokenNo'] + '">' + str['patientId'] + '</span></div>' +
                                                    '</a>';
                                            }
                                            tr += '</div>' +
                                                '</td>' +
                                                '<td>' +
                                                '<div class="col-md-12 padding-assign "> <a class="scheduled-times-for-doc-' + str['status'] + '" href="#">' + str['status'] + '</a>' +
                                                '<div class="today-appointment-line-bottom" id="time' + str['tokenNo'] + '">' + i + '</div></div>' +
                                                '</td>';

                                            if (str['status'] == "SCHEDULED") {
                                                tr += '<td><input type="checkbox" class="red" checked name="' + str['tokenNo'] + '" value="SCHEDULED"></td>' +
                                                    '<td><input type="checkbox" class="red" name="' + str['tokenNo'] + '" value="ARRIVED"></td>' +
                                                    '<td><input type="checkbox" class="red" name="' + str['tokenNo'] + '" value="INPROGRESS"></td>' +
                                                    '<td><input type="checkbox"  class="red" name="' + str['tokenNo'] + '" value="COMPLETED"></td>';

                                            } else if (str['status'] == "ARRIVED") {
                                                tr += '<td><input type="checkbox" disabled class="red" name="' + str['tokenNo'] + '" value="SCHEDULED"></td>' +
                                                    '<td><input type="checkbox"  class="red" checked name="' + str['tokenNo'] + '" value="ARRIVED"></td>' +
                                                    '<td><input type="checkbox"  class="red" name="' + str['tokenNo'] + '" value="INPROGRESS"></td>' +
                                                    '<td><input type="checkbox"  class="red" name="' + str['tokenNo'] + '" value="COMPLETED"></td>';
                                            } else if (str['status'] == "INPROGRESS") {
                                                tr += '<td><input type="checkbox"  disabled class="red" name="' + str['tokenNo'] + '" value="SCHEDULED"></td>' +
                                                    '<td><input type="checkbox" disabled class="red" name="' + str['tokenNo'] + '" value="ARRIVED"></td>' +
                                                    '<td><input type="checkbox"  checked  class="red" name="' + str['tokenNo'] + '" value="INPROGRESS"></td>' +
                                                    '<td><input type="checkbox" name="" class="red" name="' + str['tokenNo'] + '" value="COMPLETED"></td>';
                                            } else {
                                                tr += '<td><input type="checkbox" disabled class="red" name="' + str['tokenNo'] + '" value="SCHEDULED" ></td>' +
                                                    '<td><input type="checkbox"  disabled class="red" name="' + str['tokenNo'] + '" value="ARRIVED"></td>' +
                                                    '<td><input type="checkbox"  disabled class="red" name="' + str['tokenNo'] + '" value="INPROGRESS"></td>' +
                                                    '<td><input type="checkbox"  checked class="red" name="' + str['tokenNo'] + '" value="COMPLETED"></td>';
                                            }
                                            tr += '</tr>';
                                            break;
                                        case "CANCELDAY":
                                            msg = data['message'];
                                            break;
                                        case "ONLEAVE":
                                            msg = data['message'];
                                            break;
                                    }
                                } else {
                                    if (flag) {
                                        $('#onDemandList').append($('<option>').text("Pick a Slot").attr({value: 0,
                                            selected: true,
                                            disabled: ''})).append($('<option>').attr({
                                            id: idx,
                                            value: str['tokenNo']
                                        }).text(i));
                                        flag = false;
                                        $('#onDemand').show();
                                    } else {
                                        $('#onDemandList').append($('<option>').attr({
                                            id: idx,
                                            value: str['tokenNo']
                                        }).text(i));
                                    }
                                }

                            });
                        }
                    });

                    $('#todayAppt').append(tr);
                    $('#todayAppttable').dataTable({
                        "bDestroy": true
                    }).fnDestroy();
                    $('#todayAppttable').addClass('nowrap').dataTable({
                        responsive: true,
                        bRetrieve: true,
                        language: {
                            "emptyTable": msg
                        }
                    });


                } else if (saveStatus == 100) {

                    $('#todayAppttable').addClass('nowrap').dataTable({
                        responsive: true,
                        "language": {
                            "emptyTable": data['message']
                        },
                        columnDefs: [
                            { targets: [-1, -3], className: 'dt-body-right' }
                        ]
                    });
                }
                datePickerInit(data['serverDate'], data['maxDateToShow'], 'future');
                datePickerInit(data['pastDate'], data['pastDateToShow'], 'past');
            });
        },
        error: function (data) {
            window.location = 'invalidSession';
        }
    });

}