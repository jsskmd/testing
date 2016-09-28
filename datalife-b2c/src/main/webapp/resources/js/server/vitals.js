<!--graph plugins-->
$(".cancel-common").off().on('click', function (event) {
    $("#saveVitalsPopup").removeClass('dailog-show');
    $("#vitalsdisplay").removeClass("dailog-show");
    $("#graphDisplay").removeClass("dailog-show");
    event.stopPropagation();
});

$("#saveVitals").click(function () {
    $('#confirmMessage,#errorMessage').empty().addClass("hide");
    var t = $("#editable_div").val();
    var s = $("#editable_div-sys").val();
    var d = $("#editable_div-pressure").val();
    var hr = $("#editable_divs").val();
    var rr = $("#editable_div-rate").val();
    var ht = $("#vitals_height").val();
    var ft = $("#editable_div-height").val();
    var inch = $("#editable_div-height-inches").val();
    var wt = $("#editable_div-weight").val();
    var bmi = $("#editable_div-bmi").val();
    var rbs = $("#editable_div-sugar").val();
    if (!t && !s && !d && !hr && !rr && !ht && !ft && !wt && !bmi && !rbs) {
        $('#successMessages').empty().prepend('<div class="notification alert-error spacer-t10">' +
            '<p>' + 'Enter atleast one vital Value!!!' + '</p><span class="close-btndemo"></span></div>').children(':first').delay(5000).fadeOut(100, function () {
            $('#successMessages').empty();
        });
    } else {
        $("#saveVitalsPopup").addClass("dailog-show");
        $(".pop_temp").val(t);
        if (t != null && t != 'undefined' && t != '' && !isNaN(t)) {
            $(".pop_temp").html(": " + t + " <span style='padding: 0px'>&#8457;</span>");
        } else {
            $(".pop_temp").html(": -");
        }

        if (s != '' && s != 'undefined' && s != null && d != '' && d != null && d != 'undefined' && !isNaN(d) && !isNaN(s)) {
            $(".pop_bp").val(s + "/" + d);
            $(".pop_bp").text(": " + s + "/" + d + " mm Hg");
        } else {
            $(".pop_bp").text(": " + "-");
        }

        $(".pop_hr").val(hr);
        if (hr != null && hr != 'undefined' && hr != '' && !isNaN(hr)) {
            $(".pop_hr").text(": " + hr + " beats/ min");
        }
        else {
            $(".pop_hr").text(": " + "-");
        }

        $(".pop_rr").val(rr);
        if (rr != null && rr != 'undefined' && rr != '' && !isNaN(rr)) {
            $(".pop_rr").text(": " + rr + " breaths/ min");
        }
        else {
            $(".pop_rr").text(": " + "-");
        }


        if (ft != null && ft != 'undefined' && ft != '' && inch != null && inch != 'undefined' && inch != '' && !isNaN(ft) && !isNaN(inch)) {
            var htval = ft + "' " + inch + '"';
            $("#vitals_height,.pop_ht").val(htval);
            $(".pop_ht").text(": " + htval);
        }
        else {
            $(".pop_ht").text(": " + "-");
        }


        $(".pop_wt").val(wt);
        if (wt != null && wt != 'undefined' && wt != '' && !isNaN(wt)) {
            $(".pop_wt").text(": " + wt + " Kg");
        }
        else {
            $(".pop_wt").text(": " + "-");
        }


        $(".pop_bmi").val(bmi);
        if (bmi != null && bmi != 'undefined' && bmi != '' && !isNaN(bmi)) {
            $(".pop_bmi").text(": " + bmi + " Kg/m2");
        }
        else {
            $(".pop_bmi").text(": " + "-");
        }

        $(".pop_rbs").val(rbs);
        if (rbs != null && rbs != 'undefined' && rbs != '' && !isNaN(rbs)) {
            $(".pop_rbs").text(": " + rbs + " mg/dL");
        }
        else {
            $(".pop_rbs").text(": " + "-");
        }
    }

});


$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $("#vitalsForm").submit(function (e) {
        e.preventDefault();
        $("#vitalsForm").find(".submit_btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

        var url = "api/user/patient/vitals/save";
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(VitalsFormToJSON('#vitalsForm')),
            contentType: 'application/json',

            success: function (data) {
                var message = data['message'];
                if (data['statusCode'] == 400) {
                    $('#errorMessage').removeClass("hide").show().empty().prepend( data['message']).delay(5000).fadeOut(100, function () {
                        $('#errorMessage').empty();
                    });

                } else if (data['statusCode'] == 200) {

                    $("#vitalsForm").find(".submit_btn").html('Confirm');
                    var url = "patient/vitals";
                    $.ajax({
                        url: url,
                        type: 'GET',

                        success: function (data) {

                            $('#ajaxloaddiv').load(url, function () {
                                var date = getDate();
                                $("#todayDate").text(date);
                                $("#pop_date").val(date);
                                $("#pop_monitored").val(false);
                                $("#pop_userId").val($("#userId").text());
                                $('#confirmMessage').removeClass("hide").show().empty().prepend(message).delay(5000).fadeOut(100, function () {
                                    $('#confirmMessage').empty();
                                });
                            });

                        },
                        error: function (data) {
                            window.location = 'invalidSession';
                        }
                    });
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
        $("#saveVitalsPopup").removeClass("dailog-show");
        $("#pop_vitals").text("");
        $("#editable_div,#editable_divs,#editable_div-sys,#editable_div-pressure,#vitals_bp,#editable_div-rate,#editable_div-height,#editable_div-height-inches,#vitals_height,#editable_div-weight,#editable_div-sugar,#editable_div-bmi").val("");

    });

    $("#graph_temp").click(function (e) {
        var patientId = $("#patient_id").text();
        var userId = $("#userId").text();
        if (patientId != null && patientId != '' && patientId != 'undefined') {
            userId = patientId;
        }
        var url = "common/getTempValues/" + userId;
        $.ajax({
            url: url,
            type: 'GET',

            success: function (data) {
                $("#graphDisplay").addClass("dailog-show");
                var temp = [];

                if (!(jQuery.isEmptyObject(data))) {
                    if (Object.keys(data).length >= 2) {
                        $.each(data, function (key, value) {
                            var d = new Date(key);
                            var v = parseFloat(value);
                            if (!isNaN(v)) {
                                temp.push({x: d, y: v});
                            }
                        });
                        tempGraph(temp);
                    }
                    else {
                        $("#chartContainer").html("<div class='novitals-doc'>Multiple values to be recorded to view graph</div>");
                    }
                } else {
                    $("#chartContainer").html("<div class='novitals-doc'>No values recorded to generate graph</div>");
                }


            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#graph_bp").click(function (e) {
        var patientId = $("#patient_id").text();
        var userId = $("#userId").text();
        if (patientId != null && patientId != '' && patientId != 'undefined') {
            userId = patientId;
        }

        var url = "common/getBpValues/" + userId;
        $.ajax({
            url: url,
            type: 'GET',

            success: function (data) {
                $("#graphDisplay").addClass("dailog-show");
                if (!(jQuery.isEmptyObject(data[0]))) {
                    if (Object.keys(data).length >= 2) {
                        var bpd = [];
                        var bps = [];
                        $.each(data[0], function (key, value) {
                            var d = new Date(key);
                            var v = parseFloat(value);
                            if (!isNaN(v)) {
                                if ((v > 110 && v < 130 ) || ( v == 110) || ( v == 130)) {
                                    bps.push({
                                        x: d,
                                        y: v,
                                        indexLabelFontSize: 12,
                                        indexLabel: "Normal",
                                        markerType: "circle",
                                        markerColor: "#6B8E23",
                                        markerSize: 8
                                    });
                                } else {
                                    bps.push({
                                        x: d,
                                        y: v,
                                        indexLabelFontSize: 12,
                                        indexLabel: "Consult your doctor",
                                        markerType: "cross",
                                        markerColor: "red",
                                        markerSize: 8
                                    });
                                }
                            }

                        });

                        $.each(data[1], function (key, value) {
                            var d = new Date(key);
                            var v = parseFloat(value);
                            if (!isNaN(v)) {
                                if (v < 70 || v == 60) {
                                    bpd.push({
                                        x: d,
                                        y: v,
                                        indexLabel: "Low",
                                        indexLabelFontSize: 12,
                                        markerType: "cross",
                                        markerSize: 8,
                                        markerColor: "DarkSlateGrey"
                                    });
                                } else if ((v > 70 && v < 90) || (v == 70) || (v == 90)) {
                                    bpd.push({
                                        x: d,
                                        y: v,
                                        indexLabel: "Normal",
                                        indexLabelFontSize: 12,
                                        markerType: "circle",
                                        markerColor: "#6B8E23",
                                        markerSize: 8
                                    });
                                } else if (v < 60 || v == 60) {
                                    bpd.push({
                                        x: d,
                                        y: v,
                                        indexLabel: "Very Low",
                                        indexLabelFontSize: 12,
                                        markerType: "cross",
                                        markerSize: 8,
                                        markerColor: "DarkSlateGrey"
                                    });

                                } else if ((v > 90 && v < 120) || (v == 120)) {
                                    bpd.push({
                                        x: d,
                                        y: v,
                                        indexLabel: "high",
                                        indexLabelFontSize: 12,
                                        markerType: "triangle",
                                        markerSize: 8,
                                        markerColor: "red"
                                    });
                                }
                                else if ((v > 120 && v < 150) || (v == 150)) {
                                    bpd.push({
                                        x: d,
                                        y: v,
                                        indexLabel: "Very high",
                                        indexLabelFontSize: 12,
                                        markerType: "triangle",
                                        markerSize: 8,
                                        markerColor: "red"
                                    });
                                }
                            }
                        });
                        bpGraph(bps, bpd);
                    } else {
                        $("#chartContainer").html("<div class='novitals-doc'>Multiple values to be recorded to view graph</div>");
                    }

                } else {
                    $("#chartContainer").html("<div class='novitals-doc'>No values recorded to generate graph</div>");
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#graph_pulse").click(function () {
        var patientId = $("#patient_id").text();
        var userId = $("#userId").text();
        if (patientId != null && patientId != '' && patientId != 'undefined') {
            userId = patientId;
        }

        var url = "common/getPulseValues/" + userId;
        $.ajax({
            url: url,
            type: 'GET',

            success: function (data) {
                $("#graphDisplay").addClass("dailog-show");
                if (!(jQuery.isEmptyObject(data))) {
                    if (Object.keys(data).length >= 2) {
                        var heartrate = [];
                        $.each(data, function (key, value) {
                            var d = new Date(key);
                            var v = parseFloat(value);
                            if (!isNaN(v)) {
                                if (v < 60) {
                                    heartrate.push({
                                        x: d,
                                        y: v,
                                        indexLabel: "Low",
                                        indexLabelFontSize: 12,
                                        markerType: "cross",
                                        markerSize: 8,
                                        markerColor: "DarkSlateGrey"
                                    });
                                } else if ((v >= 60 && v <= 100)) {
                                    heartrate.push({
                                        x: d,
                                        y: v,
                                        indexLabel: "Normal",
                                        indexLabelFontSize: 12,
                                        markerType: "circle",
                                        markerColor: "#6B8E23",
                                        markerSize: 8
                                    });
                                } else if (v > 100) {
                                    heartrate.push({
                                        x: d,
                                        y: v,
                                        indexLabel: "High",
                                        indexLabelFontSize: 12,
                                        markerType: "triangle",
                                        markerSize: 8,
                                        markerColor: "red"
                                    });
                                }
                            }
                        });
                        heartRateGraph(heartrate);
                    } else {
                        $("#chartContainer").html("<div class='novitals-doc'>Multiple values to be recorded to view graph</div>");
                    }

                } else {
                    $("#chartContainer").html("<div class='novitals-doc'>No values recorded to generate graph</div>");
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#graph_resp").click(function () {
        var patientId = $("#patient_id").text();
        var userId = $("#userId").text();
        if (patientId != null && patientId != '' && patientId != 'undefined') {
            userId = patientId;
        }
        var url = "common/getRespValues/" + userId;
        $.ajax({
            url: url,
            type: 'GET',

            success: function (data) {
                $("#graphDisplay").addClass("dailog-show");
                if (!(jQuery.isEmptyObject(data))) {
                    if (Object.keys(data).length >= 2) {
                        var resp = [];
                        $.each(data, function (key, value) {
                            var d = new Date(key);
                            var v = parseFloat(value);
                            if (!isNaN(v)) {

                                if (v < 8) {
                                    resp.push({
                                        x: d,
                                        y: v,
                                        indexLabel: "Below Normal",
                                        indexLabelFontSize: 12,
                                        markerType: "cross",
                                        markerSize: 8,
                                        markerColor: "DarkSlateGrey"
                                    });
                                } else if ((v >= 8 && v <= 18)) {
                                    resp.push({
                                        x: d,
                                        y: v,
                                        indexLabel: "Normal",
                                        indexLabelFontSize: 12,
                                        markerType: "circle",
                                        markerColor: "#6B8E23",
                                        markerSize: 8
                                    });
                                } else if (v > 18) {
                                    resp.push({
                                        x: d,
                                        y: v,
                                        indexLabel: "Above Normal",
                                        indexLabelFontSize: 12,
                                        markerType: "triangle",
                                        markerSize: 8,
                                        markerColor: "red"
                                    });
                                }
                            }
                        });
                        respGraph(resp);
                    } else {
                        $("#chartContainer").html("<div class='novitals-doc'>Multiple values to be recorded to view graph</div>");
                    }

                } else {
                    $("#chartContainer").html("<div class='novitals-doc'>No values recorded to generate graph</div>");
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#graph_bmi").click(function () {
        var patientId = $("#patient_id").text();
        var userId = $("#userId").text();
        if (patientId != null && patientId != '' && patientId != 'undefined') {
            userId = patientId;
        }
        var url = "common/getBmiValues/" + userId;
        $.ajax({
            url: url,
            type: 'GET',

            success: function (data) {
                $("#graphDisplay").addClass("dailog-show");
                if (!(jQuery.isEmptyObject(data))) {
                    if (Object.keys(data).length >= 2) {
                        var bmi = [];
                        $.each(data, function (key, value) {
                            var d = new Date(key);
                            var v = parseFloat(value);
                            if (!isNaN(v)) {

                                if (v < 18.5) {
                                    bmi.push({
                                        x: d,
                                        y: v,
                                        indexLabel: "Under weight",
                                        indexLabelFontSize: 12,
                                        markerType: "cross",
                                        markerColor: "DarkSlateGrey",
                                        markerSize: 10
                                    });
                                } else if (v > 10.5 && v < 24.9) {
                                    bmi.push({
                                        x: d,
                                        y: v,
                                        indexLabel: "Healthy weight",
                                        indexLabelFontSize: 12,
                                        markerType: "circle",
                                        markerColor: "#6B8E23",
                                        markerSize: 10
                                    });

                                } else if (v > 25 && v < 29.9) {
                                    bmi.push({
                                        x: d,
                                        y: v,
                                        indexLabel: "Over weight",
                                        indexLabelFontSize: 12,
                                        markerType: "triangle",
                                        markerColor: "yellow",
                                        markerSize: 10
                                    });

                                } else if (v > 30) {
                                    bmi.push({
                                        x: d,
                                        y: v,
                                        indexLabel: "Obesity",
                                        indexLabelFontSize: 12,
                                        markerType: "triangle",
                                        markerColor: "red",
                                        markerSize: 10
                                    });
                                }
                            }
                        });
                        bmiGraph(bmi);
                    } else {
                        $("#chartContainer").html("<div class='novitals-doc'>Multiple values to be recorded to view graph</div>");
                    }

                } else {
                    $("#chartContainer").html("<div class='novitals-doc'>No values recorded to generate graph</div>");
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#graph_sugar").click(function () {
        var patientId = $("#patient_id").text();
        var userId = $("#userId").text();
        if (patientId != null && patientId != '' && patientId != 'undefined') {
            userId = patientId;
        }
        var url = "common/getSugarValues/" + userId;
        $.ajax({
            url: url,
            type: 'GET',

            success: function (data) {
                $("#graphDisplay").addClass("dailog-show");
                var sugar = [];
                if (!(jQuery.isEmptyObject(data))) {
                    if (Object.keys(data).length >= 2) {
                        $.each(data, function (key, value) {
                            var d = new Date(key);
                            var v = parseFloat(value);
                            if (!isNaN(v)) {
                                sugar.push({x: d, y: v});
                            }
                        });
                        bloodSugarGraph(sugar);
                    } else {
                        $("#chartContainer").html("<div class='novitals-doc'>Multiple values to be recorded to view graph</div>");
                    }

                } else {
                    $("#chartContainer").html("<div class='novitals-doc'>No values recorded to generate graph</div>");
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#doctor_Vt").click(function (e) {
        e.preventDefault();
        var url = "api/user/patient/getDoctorMonitoredVitals";
        insertVitals(url);
        $(this).addClass("show_border_clinic");
        $("#patient_Vt").removeClass("show_border_clinic");
    });

    $("#viewVitals,#patient_Vt").click(function (e) {
        e.preventDefault();
        var url = "api/user/patient/getPatientMonitoredVitals";
        insertVitals(url);
        $("#patient_Vt").addClass("show_border_clinic");
        $("#doctor_Vt").removeClass("show_border_clinic");
        $("#vitalsdisplay").addClass("dailog-show");
    });
});

$("#editable_div").blur(function () {
    var val = $(this).val();
    $(".slider.alert").slider('value', val);
    if (val != '' && val != null && val != 'undefined') {
        tempValidation(t);
    } else {
        $(this).val("");
        $("#st_temp").empty().removeClass("greenvitals").removeClass("redvitals");
    }
});

$("#editable_divs").blur(function () {
    var val = $(this).val();
    $(".slider.nostylesliders.pulse").slider('value', val);
    if (val != '' && val != null && val != 'undefined') {
        pulseValidation(val);
    }
    else {
        $(this).val("");
        $("#st_pr").empty().removeClass("greenvitals").removeClass("redvitals");
    }
});

$("#editable_div-pressure").blur(function () {
    var val = $(this).val();
    $(".slider.normal").slider('value', val);
    if (val != '' && val != null && val != 'undefined') {
        var s = $("#editable_div-sys").val();
        bpValidation(s, val);
    }
    else {
        $(this).val("");
        $("#st_bp").empty().removeClass("greenvitals").removeClass("redvitals");
    }
});

$("#editable_div-sys").blur(function () {
    var val = $(this).val();
    $(".slider.pressure").slider('value', val);
    if (val != '' && val != null && val != 'undefined') {
        var d = $("#editable_div-pressure").val();
        bpValidation(val, d);
    }
    else {
        $(this).val("");
        $("#st_bp").empty().removeClass("greenvitals").removeClass("redvitals");
    }
});

$("#editable_div-sugar").blur(function () {
    var val = $(this).val();
    $(".slider.pass").slider('value', val);
    if (val != '' && val != null && val != 'undefined') {
        sugarValidation(val);
    }
    else {
        $(this).val("");
        $("#st_sugar").empty().removeClass("greenvitals").removeClass("redvitals");
    }
});


$("#editable_div-height,#editable_div-height-inches").blur(function () {
    $('#st_ht').removeClass("greenvitals").removeClass("redvitals").empty();
    var ft = $('#editable_div-height').val();
    var inch = $('#editable_div-height-inches').val();
    if (ft != '' && ft != 'undefined' && ft != null && inch != '' && inch != 'undefined' && inch != null) {
        if (ft <= 8 && inch <= 11) {
            var inchesToFeet = inch / 12;
            var finalFeet = (+ft) + (+inchesToFeet);
            var res = roundToOne(finalFeet);
            var final = res.toString();
            $("#vitals_height").val(final);
            if (inch == 12) {
                $('#editable_div-height-inches').val("0");
            } else {
                $('#editable_div-height-inches').val(inch);
            }
            var wt = $("#editable_div-weight").val();
            getBMI(wt, final);

        } else {
            $('#st_ht').removeClass("greenvitals").addClass("redvitals").text("Enter Valid Height");
            $('#editable_div-height-inches').val("0");
            $('#editable_div-bmi').val('');
            $(".slider.demo").slider('value', '18');
            $("#st_bmi").empty().removeClass("greenvitals").removeClass("redvitals");
        }
    } else {
        $(".slider.demo").slider('value', '');
        $('#st_bmi').removeClass("greenvitals").removeClass("redvitals").empty();
        $('#editable_div-bmi').val("");
        $('#editable_div-bmi').val('');
        $(".slider.demo").slider('value', '18');
        $("#st_bmi").empty().removeClass("greenvitals").removeClass("redvitals");

    }


});


$("#enc_feet,#enc_inch").blur(function () {
    $("#vital_message").empty();
    var ft = $('#enc_feet').val();
    var inch = $('#enc_inch').val();
    $("#height,#enc_height").val('');
    if (ft > 8 || inch > 11) {
        $("#vital_message").append("Enter valid value for Height");
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

$("#enc_systolic,#enc_diastolic").blur(function () {
    var s = $('#enc_systolic').val();
    var d = $('#enc_diastolic').val();
    if (s != '' && s != 'undefined' && s != null && d != '' && d != 'undefined' && d != null) {
        $("#enc_bp").val(s + "/" + d);
    } else {
        $("#enc_bp").val('');
    }
});
$("#enc_weight").blur(function () {
    var val = $(this).val();

    if (val != '' && val != null && val != 'undefined') {
        var ht = $("#enc_height").val();
        getbmi(val, ht);
    } else {
        $("#enc_bmi").val('');
    }


});
function getbmi(wt, ht) {
    if (wt != '' && wt != 'undefined' && wt != null && ht != '' && ht != 'undefined' && ht != null) {
        var hieghtInMtr = ht * 0.3048;
        var va = hieghtInMtr * hieghtInMtr;
        var bmi = roundToOne(wt / va);
        $("#enc_bmi").val(bmi);
    }
}

function getBMI(wt, ht) {
    if (wt != '' && wt != 'undefined' && wt != null && ht != '' && ht != 'undefined' && ht != null) {
        var hieghtInMtr = ht * 0.3048;
        var va = hieghtInMtr * hieghtInMtr;
        var bmi = roundToOne(wt / va);
        $('#editable_div-bmi').val(bmi);
        $(".slider.demo").slider('value', bmi);
        bmiValidation(bmi);
    }
}

$("#editable_div-weight").blur(function () {
    var val = $(this).val();
    if (val != '' && val != null && val != 'undefined') {
        $(".slider.nostyleslider.weight").slider('value', val);
        var ht = $("#vitals_height").val();
        getBMI(val, ht);
    }else{
        $('#editable_div-bmi').val('');
        $(".slider.demo").slider('value', '18');
        $("#st_bmi").empty().removeClass("greenvitals").removeClass("redvitals");
    }
});

$("#editable_div-rate").blur(function () {
    var val = $(this).val();
    $(".slider.nostylesliders.rate-value").slider('value', val);
    if (val != '' && val != null && val != 'undefined') {
        respValidation(val);
    }
    else {
        $(this).val("");
        $("#st_rr").empty().removeClass("greenvitals").removeClass("redvitals");
    }
});


$(".slider.alert").slider({min: 90, max: 110, step: 0.1}).slider("pips").slider('float').on("slidechange", function () {
    var t = $(".slider").slider("value");
    $('#editable_div').val(t);
    tempValidation(t);
});

$(".slider.demo").slider({min: 18, max: 40, step: 0.1}).slider("pips").slider('float').on("slidechange", function () {
    var sliderval = $(".slider.demo").slider("value");
    $('#editable_div-bmi').val(sliderval);
});


$(".slider.pass").slider({min: 70, max: 250, step: 1}).slider("pips").slider('float').on("slidechange", function () {
    var sliderval = $(".slider.pass").slider("value");

    $('#editable_div-sugar').val(sliderval);
    sugarValidation(sliderval);
});
;

$(".slider.nostylesliders.rate-value").slider({min: 10, max: 60, step: 1}).slider("pips").slider('float').on("slidechange", function () {
    var sliderval = $(".slider.nostylesliders.rate-value").slider("value");

    $('#editable_div-rate').val(sliderval);
    respValidation(sliderval);
});
;

$(".slider.nostylesliders.pulse").slider({min: 40, max: 150, step: 1}).slider("pips").slider('float').on("slidechange", function () {
    var sliderval = $(".slider.nostylesliders.pulse").slider("value");

    $('#editable_divs').val(sliderval);
    pulseValidation(sliderval);
});
;

$(".slider.pressure").slider({min: 70, max: 190, step: 1}).slider("pips").slider('float').on("slidechange", function () {
    var sliderval = $(".slider.pressure").slider("value");
    $('#editable_div-sys').val(sliderval);
    var s = $('#editable_div-pressure').val();
    bpValidation(sliderval, s);
});
;

$(".slider.normal").slider({min: 40, max: 100, step: 1}).slider("pips").slider('float').on("slidechange", function () {
    var sliderval = $(".slider.normal").slider("value");

    $('#editable_div-pressure').val(sliderval);
    var d = $('#editable_div-sys').val();
    bpValidation(d, sliderval);
});
;


$(".slider.nostyleslider.weight").slider({min: 1, max: 180, step: 1}).slider("pips").slider('float').on("slidechange", function () {
    var sliderval = $(".slider.nostyleslider.weight").slider("value");
    $('#editable_div-weight').val(sliderval);

    var ht = $("#vitals_height").val();
    getBMI(sliderval, ht);
});
;

$(".slider.nostyleslider.height").slider({min: 1, max: 8, step: 0.1}).slider("pips").slider('float').on("slidechange", function () {
    var sliderval = $(".slider.nostyleslider.height").slider("value");

    var input = sliderval.toString();
    var val = input.split('.');
    var ft = val[0];
    var inch = 0;
    if (val[1] != '' && val[1] != 'undefined' && val[1] != null) {
        inch = Math.round(val[1] * 12 * 0.1);
    }
    $("#vitals_height").val(sliderval);
    $('#editable_div-height').val(ft);
    $('#editable_div-height-inches').val(inch);
    var wt = $("#editable_div-weight").val();
    getBMI(wt, sliderval);
});

$(".nostyleslider")

    .slider("pips", {
        rest: false
    });


$(document).ready(function () {

    $("#editable_div,#editable_divs,#editable_div-height-inches,#editable_div-height,#editable_div-sys,#editable_div-weight,#editable_div-rate,#editable_div-pressure,#editable_div-bmi,#editable_div-sugar").keydown(function (e) {
        // Allow: backspace, delete, tab, escape and .
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 110, 190, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57]) !== -1 ||
            // Allow: Ctrl+A
            (e.keyCode == 65 && e.ctrlKey === true) ||
            // Allow: Ctrl+C
            (e.keyCode == 67 && e.ctrlKey === true) ||
            // Allow: Ctrl+X
            (e.keyCode == 88 && e.ctrlKey === true) ||
            // Allow: home, end, left, right
            ( e.keyCode >= 35 && e.keyCode <= 39)) {
            // let it happen, don't do anything
            return;
        }
        // Ensure that it is a number and stop the keypress
        if ((e.shiftKey || (e.keyCode < 3 || e.keyCode > 3)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });
});


function roundToOne(num) {
    return +(Math.round(num + "e+1") + "e-1");
}


function tempValidation(t) {

    if (t >= 97.8 && t <= 99.1) {
        $("#st_temp").removeClass("redvitals").addClass("greenvitals").text("Normal");
    } else {
        $("#st_temp").removeClass("greenvitals").addClass("redvitals").text("Consult your Doctor");
    }
}
function sugarValidation(s) {
    if (s >= 70 && s <= 110) {
        $("#st_sugar").removeClass("redvitals").addClass("greenvitals").text("Normal");
    }
    else {
        $("#st_sugar").removeClass("greenvitals").addClass("redvitals").text("Consult your doctor");
    }


}
function pulseValidation(p) {


    if (p >= 60 && p <= 100) {
        $("#st_pr").removeClass("redvitals").addClass("greenvitals").text("Normal");
    }
    else {
        $("#st_pr").removeClass("greenvitals").addClass("redvitals").text("Consult your doctor");
    }
}


function respValidation(r) {

    if (r >= 8 && r <= 18) {
        $("#st_rr").removeClass("redvitals").addClass("greenvitals").text("Normal");
    }
    else {
        $("#st_rr").removeClass("greenvitals").addClass("redvitals").text("Consult your doctor");
    }
}

function bmiValidation(bmi) {

    if (bmi < 18.5) {
        $("#st_bmi").removeClass("greenvitals").addClass("redvitals").text("You are underweight");
    }
    else if (bmi >= 30) {
        $("#st_bmi").removeClass("greenvitals").addClass("redvitals").text("You are obese - Consult your doctor");
    }
    else if (bmi >= 18.5 && bmi <= 24.9) {
        $("#st_bmi").removeClass("redvitals").addClass("greenvitals").text("You have a healthy weight");
    }
    else if (bmi >= 25 && bmi <= 29.9) {
        $("#st_bmi").removeClass("greenvitals").addClass("redvitals").text("You are overweight");
    }
}
function bpValidation(s, d) {


    if (s >= 110 && s <= 130 && d >= 70 && d <= 90) {

        $("#st_bp").removeClass("redvitals").addClass("greenvitals").text("Normal");
    }
    else {
        $("#st_bp").removeClass("greenvitals").addClass("redvitals").text("Consult your doctor");
    }


}

function tempGraph(temp) {
    var chart = new CanvasJS.Chart("chartContainer",
        {
            theme: "theme2",
            title: {
                text: "Temperature",
                titleFontStyle: "ProximaNova-Regular",
                fontSize: 20
            },
            animationEnabled: true,
            axisX: {
                valueFormatString: "DD/MMM hh:mm tt",
                gridColor: "Silver",
                tickColor: "silver",
                labelFontSize: 12,
                labelMaxWidth: 60,
                labelWrap: true

            },
            axisY: {
                title: "Temperature (℉)",
                interval: 2,
                minimum: 90,
                maximum: 110,
                gridColor: "Silver",
                tickColor: "silver"

            },
            legend: {
                verticalAlign: "center",
                horizontalAlign: "right"
            },
            data: [
                {
                    type: "line",
                    markerSize: 10,
                    lineThickness: 2,
                    markerType: "circle",
                    markerColor: "#6B8E23",
                    dataPoints: temp

                }
            ]
        });
    chart.render();
}
function bpGraph(bps, bpd) {

    var chart = new CanvasJS.Chart("chartContainer",
        {
            theme: "theme2",
            title: {
                text: "Blood Pressure",
                titleFontStyle: "ProximaNova-Regular",
                fontSize: 20
            },
            animationEnabled: true,
            axisX: {

                gridColor: "Silver",
                tickColor: "silver",
                labelFontSize: 12,
                labelMaxWidth: 70,
                labelWrap: true,
                valueFormatString: "DD/MMM hh:mm tt"

            },
            toolTip: {
                shared: true
            },

            axisY: {
                title: "Blood Pressure (mm/Hg)",
                gridColor: "Silver",
                tickColor: "silver",
                interval: 20,
                minimum: 40,
                maximum: 190
            },
            legend: {
                verticalAlign: "center",
                horizontalAlign: "right"
            },
            data: [
                {
                    type: "line",
                    showInLegend: true,
                    lineThickness: 2,
                    name: "Systolic",
                    markerType: "square",
                    color: "#F08080",
                    dataPoints: bps
                },
                {
                    type: "line",
                    showInLegend: true,
                    name: "Diastolic",
                    color: "#20B2AA",
                    lineThickness: 2,
                    dataPoints: bpd
                }
            ],
            legend: {
                cursor: "pointer",
                itemclick: function (e) {
                    if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
                        e.dataSeries.visible = false;
                    }
                    else {
                        e.dataSeries.visible = true;
                    }
                    chart.render();
                }
            }
        });

    chart.render();
}
function respGraph(resp) {
    var chart = new CanvasJS.Chart("chartContainer",
        {
            title: {
                text: "Respiratory Rate",
                titleFontStyle: "ProximaNova-Regular",
                fontSize: 20
            },
            animationEnabled: true,
            axisX: {
                valueFormatString: "DD/MMM hh:mm tt",
                gridColor: "Silver",
                tickColor: "silver",
                labelFontSize: 12,
                labelMaxWidth: 70,
                labelWrap: true
            },
            theme: "theme2",
            axisY: {
                title: "Resp.Rate/min",
                interval: 5,
                minimum: 10,
                maximum: 60,
                gridColor: "Silver",
                tickColor: "silver"
            },
            legend: {
                verticalAlign: "center",
                horizontalAlign: "right"
            },
            data: [
                {
                    type: "line",
                    markerSize: 10,
                    lineThickness: 2,
                    markerType: "circle",
                    markerColor: "#6B8E23",
                    dataPoints: resp
                }
            ]
        });
    chart.render();
}
function heartRateGraph(heartrate) {
    var chart = new CanvasJS.Chart("chartContainer",
        {
            title: {
                text: "Heart Rate",
                titleFontStyle: "ProximaNova-Regular",
                fontSize: 20
            },
            animationEnabled: true,
            axisX: {
                valueFormatString: "DD/MMM hh:mm tt",
                gridColor: "Silver",
                tickColor: "silver",
                labelFontSize: 12,
                labelMaxWidth: 70,
                labelWrap: true
            },
            theme: "theme2",
            axisY: {
                title: "Heart Rate (Beats/min)",
                interval: 15,
                minimum: 40,
                maximum: 150,
                gridColor: "Silver",
                tickColor: "silver"
            },
            legend: {
                verticalAlign: "center",
                horizontalAlign: "right"
            },
            data: [
                {
                    type: "line",
                    markerSize: 10,
                    lineThickness: 2,
                    markerType: "circle",
                    markerColor: "#6B8E23",
                    dataPoints: heartrate

                }
            ]
        });
    chart.render();
}
function bmiGraph(bmi) {

    var chart = new CanvasJS.Chart("chartContainer",
        {
            title: {
                text: "Body mass index",
                titleFontStyle: "ProximaNova-Regular",
                fontSize: 20
            },
            theme: "theme2",
            animationEnabled: true,
            axisX: {
                valueFormatString: "DD/MMM hh:mm tt",
                gridColor: "Silver",
                tickColor: "silver",
                labelFontSize: 12,
                labelMaxWidth: 70,
                labelWrap: true
            },
            axisY: {

                title: "Body mass index",
                interval: 2,
                minimum: 18,
                maximum: 40,
                gridColor: "Silver",
                tickColor: "silver"
            },
            data: [
                {
                    type: "line",
                    showInLegend: true,
                    legendText: "Bmi = weight/height(m)*height(m)",
                    dataPoints: bmi
                }
            ]
        });

    chart.render();
}
function bloodSugarGraph(heartrate) {
    var chart = new CanvasJS.Chart("chartContainer",
        {
            title: {
                text: "Random Blood Sugar",
                titleFontStyle: "ProximaNova-Regular",
                fontSize: 20
            },
            animationEnabled: true,
            axisX: {
                valueFormatString: "DD/MMM hh:mm tt",
                gridColor: "Silver",
                tickColor: "silver",
                labelFontSize: 12,
                labelMaxWidth: 70,
                labelWrap: true
            },
            theme: "theme2",
            axisY: {
                title: "Random Blood Sugar (mm/dL)",
                interval: 15,
                minimum: 70,
                maximum: 250,
                gridColor: "Silver",
                tickColor: "silver"
            },
            legend: {
                verticalAlign: "center",
                horizontalAlign: "right"
            },
            data: [
                {
                    type: "line",
                    markerSize: 10,
                    lineThickness: 2,
                    markerType: "circle",
                    markerColor: "#6B8E23",
                    dataPoints: heartrate

                }
            ]
        });
    chart.render();
}
function insertVitals(url) {

    $('#vitalstable').dataTable({
        "bDestroy": true
    }).fnDestroy();
    $('#vitalstbody').empty();
    var userId = $("#userId").text();
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

                $('#vitalstbody').empty().append(tr);

                $('#vitalstable').dataTable({
                    "responsive": true,
                    "pagingType": "full_numbers",
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

        }
    });
}