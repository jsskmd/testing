/**
 * Created by barath on 7/26/2016.
 */

$(document).ready(function () {

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
        /*$('#type').val(data.type);*/
        $("#unitPrice").val(data.price);
        $("#unitstrength").val(data.strength);
        $("#strengthUnits").val(data.strengthUnit);
    });

   /* var doctorId = $("#duserId").text();
    var clinicId = $("#encClinicId").val();
    var today = $("#serverDate").val();*/

    var routeName = new Bloodhound({
        datumTokenizer: function (datum) {
            return Bloodhound.tokenizers.whitespace(datum.value);
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        limit: 10,
        remote: {
            url: 'common/getRouteName/%QUERY',
            filter: function (response) {
                return response;
            }
        }
    });
    routeName.initialize();

    $("#route").typeahead({
        highlight: true
    }, {
        name: 'twitter-oss',
        displayKey: 'name',
        source: routeName.ttAdapter()
    }).on('typeahead:selected', function (event, data) {
        $('#route').val(data.name);
    });


    var formName = new Bloodhound({
        datumTokenizer: function (datum) {
            return Bloodhound.tokenizers.whitespace(datum.value);
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        limit: 10,
        remote: {
            url: 'common/getFormName/%QUERY',
            filter: function (response) {
                return response;
            }
        }
    });
    formName.initialize();

    $("#type").typeahead({
        highlight: true
    }, {
        name: 'twitter-oss',
        displayKey: 'name',
        source: formName.ttAdapter()
    }).on('typeahead:selected', function (event, data) {
        $('#type').val(data.name);
    });


    var frequencyName = new Bloodhound({
        datumTokenizer: function (datum) {
            return Bloodhound.tokenizers.whitespace(datum.value);
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        limit: 10,
        remote: {
            url: 'common/getFrequencyName/%QUERY',
            filter: function (response) {
                return response;
            }
        }
    });
    frequencyName.initialize();

    $("#frequency").typeahead({
        highlight: true
    }, {
        name: 'twitter-oss',
        displayKey: 'name',
        source: frequencyName.ttAdapter()
    }).on('typeahead:selected', function (event, data) {
        $('#frequency').val(data.name);
    });


});

$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });


    $("#enc_feet,#enc_inch,#enc_weight").blur(function () {
        var ft = $('#enc_feet').val();
        var inch = $('#enc_inch').val();
        $("#height,#enc_height").val('');
        if (ft > 8 || inch > 11) {
            $('#errorMessage').empty().append("Enter valid value for Height");
        } else if (ft != '' && ft != 'undefined' && ft != null && inch != '' && inch != 'undefined' && inch != null) {
            $("#height").val(ft + "'" + inch + '"');
            var inchesToFeet = inch / 12;
            var finalFeet = (+ft) + (+inchesToFeet);
            var res = roundToOne(finalFeet);
            var final = res.toString();
            $("#enc_height").val(final);
            var wt = $("#enc_weight").val();
            getbmi(wt, final);
        }
        else {
            $("#enc_bmi").val('');
        }
    });


    $(".cancel-common").click(function (e) {
        $("#pdfdisplay,#shareRecordPopup,#doc-dash-for-dict").removeClass("dailog-show");
        $('#iframe,#imgiframe').attr('src', "");
        e.preventDefault();
    });

    $("#cancel-common").click(function () {
        $("#s_successMessage").removeClass("dailog-show");
    });

    $("#previewreportbtn").click(function (e) {
        var formId = $(this).closest("form").attr('id');
        $(this).html('<img src="resources/images/red_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
        var isError = false;
        var encounterCc = $('#chiefComplaint').val();
        var encounterHpi = $('#hpi').val();

        //checking for required field has been entered are not before Encounter is saved
        if (encounterCc == "" && encounterHpi == "") {
            $("#previewreportbtn").html('Preview');
            $("#s_successMessage").addClass("dailog-show");
            $('#s_errorMessage').empty().prepend("Chief complaint is mandatory !");
            isError = true;
        }

        if (!isError) {
            var url = "api/user/doctor/previewEncounter";
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(EncounterFormToJSON('#'+formId)),
                contentType: 'application/json',

                success: function (data) {
                    var statusCode = data['statusCode'];
                    var patientName = data['patientName'];
                    var patientId = data['patientId'];
                    var message = data['message'];
                    if (statusCode == 200) {
                        var newSrc = data['pdfUrl'];
                        $('#imgiframe').hide();
                        $('#iframe').attr('src', newSrc);
                        $("#load").hide();
                        $("#pdfdisplay").addClass('dailog-show');
                    }
                    $("#previewreportbtn").html('Preview');
                },
                error: function (data) {
                    window.location = 'invalidSession';
                }
            });
        }
        else {
            $("#previewreportbtn").html('Preview');
            $("#s_successMessage").addClass("dailog-show");
            $('#s_errorMessage').empty().prepend("Chief complaint is mandatory !");

            return false;
        }
        e.preventDefault();
    });

    $("#rxlist").on('click', '.prxDF', function () {
        $(this).parent().parent().remove();
    });

    $(".addrxb").click(function () {
        //validating for the required fields
        var medicationName = $("#brandName").val();
        var strength = $("#strength").val();
        var q = $("#quantity").val();

        if (medicationName != '' && strength != '' && q != '') {

            var mn = medicationName;
            var type = $("#type").val();
            if (type != null && type != '' && type != 'undefined') {
                var s = strength + " " + $("#strengthUnits").find("option:selected").text() + " " + "(" + $("#type").val() + ")";
            } else {
                s = strength + " " + $("#strengthUnits").find("option:selected").text();
            }

            var fr = $('#frequency').val();
            var route = $("#route").val();
            var t = fr + " " + "(" + $("input:radio[name=shift]:checked").val() + ")";

            var shift = $("input:radio[name=shift]:checked").val();
            if (fr != null && fr != '' && fr != 'undefined') {
                t = fr + " " + "(" + shift + ")";
            }
            else {
                t = shift;
            }

            // dynamically appending the row to table in CommonEncounter.jsp
            $("#rxlist").append('<tr><td class="tdrx">' +
                "<input  type='hidden'  name='prescriptions.medicationName' value='" + mn + "'>" +
                mn +
                '</td><td class="tdrx">' +
                "<input  type='hidden'  name='prescriptions.strength' value='" + s + "'>" +
                s +
                '</td><td class="tdrx">' +
                "<input  type='hidden'  name='prescriptions.frequency' value='" + t + "'>" +
                t +
                '</td><td class="tdrx">' +
                "<input  type='hidden'  name='prescriptions.quantity' value='" + q + "'>" +
                q +
                '</td><td class="tdrx">' +
                "<input  type='hidden'  name='prescriptions.route' value='" + route + "'>" +

                route +
                '<td class="tdrx del">' +
                '<a href="javascript:void(0);" class="prxDF">Delete</a></td></tr>');


            $(".rxlist tr:last").effect("highlight", {
                color: '#4BADF5'
            }, 1000);
            $("#strength").val("");
            $("#quantity").val("");
            $("#brandName").val("");
            $("#type").val("");
            $('#frequency').val("");
            $("#route").val("");
            $("#strengthUnits").val("mg");

        } else {
            $("#perrorMessage").empty().text("Enter required fields!");
        }
    });

    $("#addICDButton").click(function (event) {
        var icdCode = $("#icdCode").val();
        var icdName = $("#icdName").val();

        var icdValue = icdCode + " - " + icdName + "\n";

        var impression = $("#encounterImpres").val();
        impression += icdValue;
        $("#encounterImpres").val(impression);
        $("#icdCode").val("");
        $("#icdName").val("");
        event.stopPropagation();
    });

    $("#enc_save_history").click(function () {
        $(this).html('<img src="resources/images/red_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
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
                    getHistory(data);
                    $("#encounter_2_edit").find("textarea").val("");
                    $("#back_encounter_2").trigger("click");
                    $(this).html('Save History');
                }

            },
            error: function (data) {
                window.location = 'invalidSession';
            }

        });

    });

    $("#chiefComplaint,#hpi,#subjective").keyup(function () {
        $('#errorMessage,#pperrorMessage').empty();
    });

    $('#encounterform,#consultationform').submit(function (e) {
        e.preventDefault();
       /* $('#confirmMessage,#errorMessage').empty();*/
        var cc = $("#chiefComplaint").val();
        var hpi = $("#hpi").val();
        var flag = true;
        var idwithRed = "";
        var id = this.id;
        if ($(this).find('textarea').hasClass('textareavalidate')) {
            flag = false;
            $('.textareavalidate').each(function () {
                if (idwithRed == "") {
                    idwithRed += $(this).attr("title");
                } else {
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
                    $("#" + id).find(".submit_btn").html('<img src="resources/images/red_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

                    $.ajax({
                        url: "api/user/doctor/saveEncounter",
                        type: 'POST',
                        dataType: 'json',
                        data: JSON.stringify(EncounterFormToJSON('#' + id)),
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
                            $("#" + id).find(".submit_btn").html('Save');
                        },
                        error: function (data) {
                            window.location = 'invalidSession';
                        }
                    });
                }
            });

        } else {
            if (flag) {
                $('#errorMessage').empty().prepend("Chief complaint is mandatory !");
            } else {
                $('#errorMessage').empty().prepend('Please enter valid input in ' + idwithRed).delay(7000).fadeOut(100, function () {
                    $('#message').empty();
                });
            }
        }
        return false;
    });

    $('#encounter_soap').submit(function (e) {
        e.preventDefault();
        var cc = $("#subjective").val();
        var flag = true;
        if ($(this).find('textarea').hasClass('textareavalidate')) {
            flag = false;
            $('.textareavalidate').each(function () {
                if (idwithRed == "") {
                    idwithRed += $(this).attr("title");
                } else {
                    idwithRed += "," + $(this).attr("title");
                }
            });
        }
        if ((cc != null && cc != '' && cc != 'undefined')&& flag){
            $("#saveEncPopup").addClass("dailog-show");
            $(".cancelapp").off().on('click', function () {
                var retVal = this.id;
                $("#saveEncPopup").removeClass("dailog-show");
                if (retVal == "Yes") {
                    $("#encounter_soap").find(".submit_btn").html('<img src="resources/images/red_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
                    $.ajax({
                        url: "api/user/doctor/saveEncounter",
                        type: 'POST',
                        dataType: 'json',
                        data: JSON.stringify(SOAPFormToJSON('#encounter_soap')),
                        contentType: 'application/json',
                        success: function (data) {
                            var saveMessage = data['message'];
                            if (data['statusCode'] == 200) {
                                if (saveMessage != '') {
                                    $('#ppconfirmMessage').show().empty().prepend(data['message']).delay(1500).fadeOut(100, function () {
                                        $('#ppconfirmMessage').empty();
                                    });
                                }
                                document.getElementById('encounter_soap').reset();
                                var jsondata = {"encounterId": data['encId']};
                                $.ajax({
                                    url: "api/user/doctor/generatePdfByEncId",
                                    type: 'POST',
                                    dataType: 'json',
                                    data: JSON.stringify(jsondata),
                                    contentType: 'application/json'
                                });

                            } else {
                                $('#pperrorMessage').empty().prepend(data['message']);
                            }
                            $("#soap_share").val("false");
                            $("#encounter_soap").find(".submit_btn").html('Save');
                        },
                        error: function (data) {
                            window.location = 'invalidSession';
                        }
                    });
                }
            });

        } else {
            if (flag) {
                $('#pperrorMessage').empty().prepend("Subjective is mandatory!");
            } else {
                $('#pperrorMessage').empty().prepend('Please enter valid input in ' + idwithRed).delay(7000).fadeOut(100, function () {
                    $('#pperrorMessage').empty();
                });
            }
        }

        e.preventDefault();
        return false;
    });

    $('.keyup-fake').keyup(function (e) {
        e.preventDefault();
        $(this).removeClass('textareavalidate');
        var inputVal = $(this).val();
        var fakeReg = /(.)\1{2,}/;
        if (fakeReg.test(inputVal)) {
            $(this).addClass('textareavalidate');
        }
    });

});

function display(id) {
    for(var i=1;i<=11;i++){
        $("#aencounter_"+i).removeClass('enc_color');
    }
    $("#a"+id).addClass('enc_color');
    $("#encounter_body > div").addClass("hide");
    $("#" + id).removeClass("hide");

    $("#" + id).find(' input:text ,textarea , input:checkbox:checked').first().focus();

}

$("#edit_encounter_2").click(function () {
    $("#encounter_2").addClass("hide");
    $("#encounter_2_edit").removeClass("hide");
    $("#encounter_2_edit").find('textarea').first().focus();
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
});


function getEncounterLabels(rosCategories,peCategories) {

    var general = "", skn = "", head = "", ears = "", eyes = "", nose = "", throat = "", neck = "", breast = "", resp = "", cardio = "", gastro = "", urinary = "",
        vascular = "", musculoskl = "", neuro = "", hematologic = "", endo = "", psychia = "", rosothers = "";

    var rosheadings = "<ul class='class-for-review'>";

    $.each(rosCategories, function (idxm, obj) {
        var id = 'rosCategory' + (idxm + 1);
        rosheadings += "<li class='general-page'> <a class='rosCategory' onclick=displayROS(" + (idxm + 1) + ") id=" + id + ">" + obj['name'] + "</a></li>";
    });
    rosheadings += "</ul>";

    $("#roscategories").html(rosheadings);

    var peheadings = "<ul class='class-for-review'>";

    $.each(peCategories, function (idxm, obj) {
        var id = 'peCategory' + (idxm + 1);
        peheadings += "<li class='general-page'><a class='pecategory' onclick=displaype(" + (idxm + 1) + ") id=" + id + ">" + obj['name'] + "</a></li>";

    });
    peheadings += "</ul>";
    $("#pecategories").html(peheadings);
}

function getlabdata(labCategories,hmtValues,sgyValues,cpgyValues,cgyValues,bcyValues,mbgyValues,hrtValues) {

    var hmt = "<ul class='general-cases-in-enc'>",
        sgy = "<ul class='general-cases-in-enc'>" ,
        cpgy = "<ul class='general-cases-in-enc'>" ,
        cgy = "<ul class='general-cases-in-enc'>" ,
        bcy = "<ul class='general-cases-in-enc'>",
        mbgy = "<ul class='general-cases-in-enc'>",
        hrt = "<ul class='general-cases-in-enc'>",
        labothers = "<ul class='general-cases-in-enc'>";
    var htmlData = "<ul class='class-for-review'>";

    // fetch the labCategories
    $.each(labCategories, function (idxm, obj) {

        htmlData += "<li class='general-page'> <a class='labtype' onclick=displaylab(" + (idxm + 1) + ") id=" + (idxm + 1) + ">" + obj + "</a></li>";

        //based on labCategories id fetching sub-categories
        if (idxm + 1 == 1) {
            $.each(hmtValues, function (idx, obj) {
                hmt += "<li><input type='checkbox' class='encLabOrd' name='labOrders.labtests.labTestsId' onchange='displaylabother(" + idxm + ")' value=" + obj[0] +
                    " id=lt1" + idx + "><span class='encont-demo'>" + obj[1] + "</span></li>";
            });
            hmt += "<input type='text' name='labOrders.labtests.otherDesp'  placeholder='Other Lab Order' class='nclo ind inot lt13' style='display: none; width: auto; padding-left:10px;' id='labCatOth1'>";
            $('#labCat1').append(hmt + "</ul>");

        } else if (idxm + 1 == 2) {
            $.each(sgyValues, function (idx, obj) {
                sgy += "<li><input type='checkbox' class='encLabOrd' name='labOrders.labtests.labTestsId' onchange='displaylabother(" + idxm + ")' value=" + obj[0] +
                    " id=lt2" + idx + "><span class='encont-demo'></span>" + obj[1] + "</li>";
            });
            sgy += "<input type='text' name='labOrders.labtests.otherDesp' placeholder='Other Lab Order' class='nclo ind inot lt23' style='display: none; width: auto; padding-left:10px;' id='labCatOth2'>";
            $('#labCat2').append(sgy + "</ul>");

        } else if (idxm + 1 == 3) {
            $.each(cpgyValues, function (idx, obj) {
                cpgy += "<li><input type='checkbox' class='encLabOrd' name='labOrders.labtests.labTestsId' onchange='displaylabother(" + idxm + ")' value=" + obj[0] +
                    " id=lt3" + idx + "><span class='encont-demo'></span>" + obj[1] + "</li>";
            });
            cpgy += "<input type='text' name='labOrders.labtests.otherDesp' placeholder='Other Lab Order' class='nclo ind inot lt33' style='display: none; width: auto; padding-left:10px;' id='labCatOth3'>";

            $('#labCat3').append(cpgy + "</ul>");
        } else if (idxm + 1 == 4) {
            $.each(cgyValues, function (idx, obj) {
                cgy += "<li><input type='checkbox' class='encLabOrd' name='labOrders.labtests.labTestsId' onchange='displaylabother(" + idxm + ")' value=" + obj[0] +
                    " id=lt4" + idx + "><span class='encont-demo'></span>" + obj[1] + "</li>";
            });
            cgy += "<input type='text' name='labOrders.labtests.otherDesp' placeholder='Other Lab Order' class='nclo ind inot lt43' style='display: none; width: auto; padding-left:10px;' id='labCatOth4'>";
            $('#labCat4').append(cgy + "</ul>");
        } else if (idxm + 1 == 5) {
            $.each(bcyValues, function (idx, obj) {
                bcy += "<li><input type='checkbox' class='encLabOrd' name='labOrders.labtests.labTestsId' onchange='displaylabother(" + idxm + ")' value=" + obj[0] +
                    " id=lt5" + idx + "><span class='encont-demo'></span>" + obj[1] + "</li>";
            });
            bcy += "<input type='text' name='labOrders.labtests.otherDesp' placeholder='Other Lab Order' class='nclo ind inot lt53' style='display: none; width: auto; padding-left:10px;' id='labCatOth5'>";
            $('#labCat5').append(bcy + "</ul>");
        } else if (idxm + 1 == 6) {
            $.each(mbgyValues, function (idx, obj) {
                mbgy += "<li><input type='checkbox' class='encLabOrd' name='labOrders.labtests.labTestsId' onchange='displaylabother(" + idxm + ")' value=" + obj[0] +
                    " id=lt6" + idx + "><span class='encont-demo'></span>" + obj[1] + "</li>";
            });
            mbgy += "<input type='text' name='labOrders.labtests.otherDesp' placeholder='Other Lab Order' class='nclo ind inot lt63' style='display: none; width: auto; padding-left:10px;' id='labCatOth6'>";
            $('#labCat6').append(mbgy + "</ul>");
        } else if (idxm + 1 == 7) {
            $.each(hrtValues, function (idx, obj) {
                hrt += "<li><input type='checkbox' class='encLabOrd' name='labOrders.labtests.labTestsId' onchange='displaylabother(" + idxm + ")' value=" + obj[0] +
                    " id=lt7" + idx + "><span class='encont-demo'></span>" + obj[1] + "</li>";
            });
            hrt += "<input type='text' name='labOrders.labtests.otherDesp' placeholder='Other Lab Order' class='nclo ind inot lt73' style='display: none; width: auto; padding-left:10px;' id='labCatOth7'>";
            $('#labCat7').append(hrt + "</ul>");
        }
        if (idxm + 1 == 8) {
            labothers += '<div class="col-md-12 background-for-doctor"><fieldset class="input-block doc-dash-bord ">' +
                '<a href="#" data-toggle="tooltip" data-placement="right" title="Chief Complaints."> <label>Others</label></a>' +
                '<textarea style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;" title="Chief Complaint" placeholder="" class="nclo intarea hst"  name="labOrders.otherDesp"></textarea></fieldset></div>';

            $('#labCat8').append(labothers);
        }
    });
    htmlData += "</ul>";

    $('#labheadings').append(htmlData);


}

function displaylabother(main) {
    var m = main + 1;

    if ($("#lt" + m + "3").is(":checked")) {
        $(".lt" + m + "3").css("display", "block");
    }
    else {
        $(".lt" + m + "3").css("display", "none");
    }

}

function displayROS(id) {
    for (var i = 1; i <= 20; i++) {
        if (i == id) {
            $('#roscat' + id).css('display', 'block');
            $('#rosCategory' + id).addClass('enc_select');
        } else {
            $('#roscat' + i).css('display', 'none');
            $('#rosCategory' + i).removeClass('enc_select');
        }
    }
    $("#roscat" + id).find('textarea').focus();
}

function displaype(id) {

    for (var i = 1; i <= 17; i++) {

        if (i == id) {
            $('#pecat' + id).css('display', 'block');
            $('#peCategory' + id).addClass('enc_select');
        } else {
            $('#pecat' + i).css('display', 'none');
            $('#peCategory' + i).removeClass('enc_select');
        }
        $("#pecat" + id).find('textarea').focus();

    }
}

function displaylab(id) {

    for (var i = 1; i <= 8; i++) {

        if (i == id) {
            $('.labCat' + id).css('display', 'block');
            $('#' + id).addClass('enc_select');
        } else {
            $('.labCat' + i).css('display', 'none');
            $('#' + i).removeClass('enc_select');
        }


    }
}

function iterateHistory(history) {
    var his = "";
    if (history != null && history != '' && history != 'undefined') {
        $.each(history, function (key, value) {
            his += "<div class='doc-dash-medical-histroy-introduction'>" + value + "</div><div class='time-date'><i>" + key + "</i></div>";
        });
    }
    return his;
}

function getHistory(history) {
    JSON.stringify(history);
    if (history != 'NOT_FOUND') {
        $("#enc_history").load("patient/history", function () {

            var mh = iterateHistory(history['medical']);
            if (mh != '') {
                $("#enc_mh").html(mh);
            } else {
                $("#enc_mh").html("Details not entered!!!");
            }
            var sh = iterateHistory(history['surgical']);
            if (sh != '') {
                $("#enc_sh").html(sh);
            } else {
                $("#enc_sh").html("Details not entered!!!");
            }
            var fh = iterateHistory(history['family']);
            if (fh != '') {
                $("#enc_fh").html(fh);
            } else {
                $("#enc_fh").html("Details not entered!!!");
            }
            var sch = iterateHistory(history['social']);
            if (sch != '') {
                $("#enc_sch").html(sch);
            } else {
                $("#enc_sch").html("Details not entered!!!");
            }
            var rf = iterateHistory(history['rf']);
            if (rf != '') {
                $("#enc_rf").html(rf);
            } else {
                $("#enc_rf").html("Details not entered!!!");
            }
            var pm = iterateHistory(history['medications']);
            if (pm != '') {
                $("#enc_pm").html(pm);
            } else {
                $("#enc_pm").html("Details not entered!!!");
            }
            var all = iterateHistory(history['allergies']);
            if (all != '') {
                $("#enc_all").html(all);
            } else {
                $("#enc_all").html("Details not entered!!!");
            }

        });
    } else {
        $("#enc_history").text("Health history not updated for this patient !!!");
    }
}

function EncounterFormToJSON(form) {
    var array = jQuery(form).serializeArray();
    var json = {};
    var newName;
    var parentName;
    var innernewName;
    var innerparentName = false;
    var newVal;
    var vitals = {};
    var user = {};
    var respiratory = [];
    var cardioVascular = [];
    var miniCardio = {};
    var genitourinaryFemale = {};
    var genitourinaryMale = {};
    var gastrointestinal = [];
    var muscoloskeletal = [];
    var prescriptions = [];
    var innerPrescriptions = {};
    var physicalExaminations = [];
    var innerPhysicalExamination = {};
    var reviewofSystems = [];
    var innerReviewofSystems = {};
    var labOrders = [];
    var innerLabOrders = {};
    var miniEncounter = {};
    var history = {};
    var peTypes = {};
    var labtests = {};
    var cardiovascularLabels = {};
    var respiratoryLabels = {};
    var miniRespiratory = {};
    var gastrointestinalLabels = {};
    var miniGastrointestinal = {};
    var muscoloskeletalLabels = {};
    var miniMuscoloskeletal = {};


    jQuery.each(array, function (inx, element) {
        var name = this.name;
        if (name.indexOf(".") > -1) {
            parentName = name.split('.')[0];
            innernewName = name.split('.')[2];
            newName = name.split('.')[1];
            if (parentName == 'miniEncounter') {
                miniEncounter[newName] = this.value || '';
                newVal = miniEncounter;
            }
            if (parentName == 'history') {
                history[newName] = this.value || '';
                newVal = history;
            }
            if (parentName == 'user') {
                user[newName] = this.value || '';
                newVal = user;
            }

            if (parentName == 'vitals') {
                vitals[newName] = this.value || '';
                newVal = vitals;
            }

            if (parentName == 'labOrders') {


                var item = this.value || '';
                if (newName == 'labtests') {
                    if (innernewName == "labTestsId" && item % 4 != 0) {

                        innerLabOrders[innernewName] = this.value || '';
                        labOrders.push(innerLabOrders);
                        innerLabOrders = {};

                    } else {
                        if (innernewName == "labTestsId") {

                            innerLabOrders[innernewName] = this.value || '';
                        } else {
                            if (this.value != "") {
                                innerparentName = true;
                                innerLabOrders[innernewName] = this.value || '';
                            }
                        }
                        if (innerparentName == true) {
                            labOrders.push(innerLabOrders);
                            innerLabOrders = {};

                            innerparentName = false;
                        }
                    }
                } else {
                    if (this.value != "") {
                        innerLabOrders[newName] = this.value;
                    }
                    if (!$.isEmptyObject(innerLabOrders)) {
                        labOrders.push(innerLabOrders);
                        innerLabOrders = {};
                    }
                }
                newVal = labOrders;
            }

            if (parentName == 'prescriptions') {
                innerPrescriptions[newName] = this.value || '';
                if (newName == 'route') {
                    prescriptions.push(innerPrescriptions);
                    innerPrescriptions = {};
                }
                newVal = prescriptions;
            }
            if (parentName == 'respiratory') {
                if (newName == 'respiratoryLabels' || newName == 'labelType') {
                    respiratoryLabels[innernewName] = this.value || '';
                    if (innernewName == "labelType") {
                        miniRespiratory[newName] = respiratoryLabels || '';
                    }

                }
                if (newName == 'descriptions') {
                    if (jQuery.isEmptyObject(respiratoryLabels)) {

                    } else {

                        miniRespiratory[newName] = this.value || '';
                        respiratory.push(miniRespiratory);
                        miniRespiratory = {};
                        respiratoryLabels = {};
                    }

                }

            }

            if (parentName == 'cardioVascular') {
                if (newName == 'cardiovascularLabels' || newName == 'labelType') {
                    cardiovascularLabels[innernewName] = this.value || '';
                    if (innernewName == "labelType") {
                        miniCardio[newName] = cardiovascularLabels || '';
                    }
                }
                if (newName == 'descriptions') {
                    if (jQuery.isEmptyObject(cardiovascularLabels)) {

                    } else {

                        miniCardio[newName] = this.value || '';
                        cardioVascular.push(miniCardio);
                        miniCardio = {};
                        cardiovascularLabels = {};
                    }

                }

            }


            if (parentName == 'gastrointestinal') {
                if (newName == 'gastrointestinalLabels' || newName == 'labelType') {
                    gastrointestinalLabels[innernewName] = this.value || '';
                    if (innernewName == "gastroLabelId") {

                        miniGastrointestinal[newName] = gastrointestinalLabels || '';
                    }

                }
                if (newName == 'descriptions') {
                    if (jQuery.isEmptyObject(gastrointestinalLabels)) {

                    } else {

                        miniGastrointestinal[newName] = this.value || '';
                        gastrointestinal.push(miniGastrointestinal);
                        miniGastrointestinal = {};
                        gastrointestinalLabels = {};
                    }

                }
            }

            if (parentName == 'muscoloskeletal') {
                if (newName == 'muscoloskeletalLabels' || newName == 'labelType') {
                    muscoloskeletalLabels[innernewName] = this.value || '';
                    if (innernewName == "muscoloLabelId" || innernewName == "labelType") {

                        miniMuscoloskeletal[newName] = muscoloskeletalLabels || '';
                    }


                }
                if (newName == 'descriptions') {
                    if (jQuery.isEmptyObject(muscoloskeletalLabels)) {

                    } else {

                        miniMuscoloskeletal[newName] = this.value || '';
                        muscoloskeletal.push(miniMuscoloskeletal);

                        miniMuscoloskeletal = {};
                        muscoloskeletalLabels = {};
                    }

                }
            }

            if (parentName == 'physicalExaminations') {

                if (newName == "description") {
                    if (this.value != "") {
                        innerPhysicalExamination[newName] = this.value || '';
                    }
                }

                if (newName == "peTypeId") {
                    /*  peTypes[innernewName] = this.value || '';*/
                    innerPhysicalExamination[newName] = this.value || '';

                    if (this.value == 5) {

                        if (jQuery.isEmptyObject(respiratory)) {

                        } else {

                            innerPhysicalExamination['respiratory'] = respiratory || '';
                        }
                    }
                    if (this.value == 6) {
                        if (jQuery.isEmptyObject(cardioVascular)) {

                        }
                        else {
                            innerPhysicalExamination['cardioVascular'] = cardioVascular || '';
                        }
                    }
                    if (this.value == 8) {
                        if (jQuery.isEmptyObject(gastrointestinal)) {

                        }
                        else {
                            innerPhysicalExamination['gastrointestinal'] = gastrointestinal || '';
                        }
                    }
                    if (this.value == 9) {
                        if (jQuery.isEmptyObject(genitourinaryMale)) {

                        }
                        else {
                            innerPhysicalExamination['genitourinaryMale'] = genitourinaryMale || '';
                        }
                    }
                    if (this.value == 10) {
                        if (jQuery.isEmptyObject(genitourinaryFemale)) {

                        }
                        else {
                            innerPhysicalExamination['genitourinaryFemale'] = genitourinaryFemale || '';
                        }
                    }
                    if (this.value == 12) {
                        if (jQuery.isEmptyObject(muscoloskeletal)) {

                        }
                        else {
                            innerPhysicalExamination['muscoloskeletal'] = muscoloskeletal || '';
                        }
                    }
                    physicalExaminations.push(innerPhysicalExamination);
                    innerPhysicalExamination = {};
                }
                peTypes = {};
                newVal = physicalExaminations;
            }

            if (parentName == 'reviewofSystems') {
                innerReviewofSystems[newName] = this.value || '';

                reviewofSystems.push(innerReviewofSystems);

                innerReviewofSystems = {};
                newVal = reviewofSystems;
            }
        }
        else {
            parentName = this.name;
            newVal = this.value;
        }
        json[parentName] = newVal || '';
    });

    delete json['respiratory'];
    delete json['cardioVascular'];
    delete json['gastrointestinal'];
    delete json['genitourinaryMale'];
    delete json['genitourinaryFemale'];
    delete json['muscoloskeletal'];
    delete json['shift'];
    return json;
}

function getbmi(wt, ht) {
    if (wt != '' && wt != 'undefined' && wt != null && ht != '' && ht != 'undefined' && ht != null) {
        var hieghtInMtr = ht * 0.3048;
        var va = hieghtInMtr * hieghtInMtr;
        var bmi = roundToOne(wt / va);
        $("#enc_bmi").val(bmi);
    }
}

function roundToOne(num) {
    return +(Math.round(num + "e+1") + "e-1");
}





