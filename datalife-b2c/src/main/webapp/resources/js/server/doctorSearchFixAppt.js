
$(document).ready(function () {

    var doctorNames = new Bloodhound({
        datumTokenizer: function (d) {
            return d.name;
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: 'common/%QUERY',
            replace: function(url, query) {
                if(query == ''){
                    query = "getSpeciality";
                    return url.replace("%QUERY",query);
                }else{
                    return url.replace("%QUERY","search/"+query);
                }
            },
            filter: function (response) {
                return response;
            }
        },
        limit : 10
    });
    doctorNames.initialize();
    var doctorSearchInput = $("#doctorSearch");
    doctorSearchInput.typeahead({
        hint:false,
        highlight: true,
        minLength: 0
    },{
        display : 'name',
        source: doctorNames.ttAdapter(),
        templates: {
            empty: [

            ].join('\n'),
            suggestion: Handlebars.compile('<div><strong>{{name}}</strong> <span class="categories">{{categories}}</span></div>')
        }
    }).on('typeahead:selected', function (event, data) {
        $('#searchDoctorId').val(data.userId);
        $('#searchCategories').val(data.categories);
    });
    doctorSearchInput.on("typeahead:opened", function () {
        var ev = $.Event("keydown");
        ev.keyCode = ev.which = 40;
        $(this).trigger(ev);
        return true
    });



    var locationName = new Bloodhound({
        datumTokenizer: function (d) {
            return d.name;
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: 'common/%QUERY',

            replace: function(url, query) {
                if(query == ''){
                    query = "getLocations";
                    return url.replace("%QUERY",query);
                }else{
                    return url.replace("%QUERY","getLocationLike/"+query);
                }
            },
            filter: function (response) {
                return response;
            }
        },
        limit: 10
    });
    locationName.initialize();
    var quicksearchInput = $("#location");
    quicksearchInput.typeahead({
            hint:false,
            highlight: true,
            minLength: 0
        },
        {
            name:'locationName',
            displayKey: 'locationName',
            source: locationName.ttAdapter(),
            templates: {
                empty: [
                ].join('\n'),
                suggestion: Handlebars.compile('<div><strong>{{locationName}}</strong></div>')
            }
        });

    quicksearchInput.on("typeahead:opened", function () {
        var ev = $.Event("keydown");
        ev.keyCode = ev.which = 40;
        $(this).trigger(ev);
        return true
    });


    /*$.get("https://ipinfo.io", function (response) {
         $("#ip").html("IP: " + response.ip);
         $("#country").val(response.country);
         $("#city").val(response.city);
         $("#state").val(response.region);
         $("#details").html(JSON.stringify(response, null, 4));
        $("#ipcity").text("Bangalore");
    }, "jsonp");*/

});


$(document).ready(function() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $(".close-showSignRegPopup").click(function(){
        $("#showSignRegPopup").removeClass('dailog-show');
        document.getElementById("patientRegOnApp").reset();
        document.getElementById("scheduledSign").reset();
    });


    // onclick of seach button in Appointment ajax call to get the doctors List
    $("#searchDoctor").on('submit',function (e) {
        e.preventDefault();
       /* if(this.checkValidity()){*/
    var searchCat = $("#searchCategories").val();
    var searchName = $("#doctorSearch").val();
    var loc = $("#location").val();
        var patientId = $("#patientId").val();
    var id = $("#searchDoctorId").val();
    var city =  $("#ipcity").text();
    var url = "common/searchDoctor";
    var jsondata = {"location": loc,"flname":searchName,"city":city ,"patientId":patientId };
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',

        success: function (data) {

            var status = data['statusCode'];

            if (status == 100) {
                $("#pagination").hide();
                $("#addSrcDocList").empty();
                $("#ourServices").show();
                $("#eMessage").empty().prepend('<div id="errorMessage">' + data['message'] + '</div>').children(':first').delay(5000).fadeOut(100);
            } else if (status == 200) {

                $("#ourServices").hide();
                $("#showDoctorList").show();
                $("#addSrcDocList").empty();
                var response = data['users'];
                $("#pagination").show();

                $("#searchDoctorId").val('');
                $("#searchCategories").val('');
                var id = 1;
                for(var i =0; i < response.length; i++){
                    var res = response[i];
                    var div = '';
                    var slotDiv = '';
                    var maniDiv = '<li><div class="form-wizard doctor-info">'+
                        '<div class="row">'+
                        '<div class="doctor-timeslot-discription">'+
                        '<div class="col-md-12 col-xs-12 container-doctors" >'+
                        '<div class="col-md-3 image" id="docImg'+id+'">'+
                        '<div class="doc-image"><img src='+res['doctorImageUrl']+' alt="doc"></div>'+
                        '</div>'+
                        '<div class="col-md-9 col-xs-12 demo">'+
                        '<div class="doc-info" id="docDtls'+id+'">'+

                        '</div>'+
                        '</div>'+
                        '</div>'+
                        '<div class="col-md-6 col-sm-6">'+

                        '<div class="doct-timeslot-demo" id="docTiming'+id+'">'+

                        '</div>'+
                        '</div>'+
                        '<div class="col-md-6 col-sm-6">'+

                        '<div class="float-rt" id="docapt'+id+'">'+

                        '</div>'+
                        '</div>'+
                        '</div>'+
                        '</div>'+
                        '</div>'+
                        '<div class="content-index-demo" id="slotContent'+id+'" style="display: block;">' +
                        '</div></li>' ;

                    if(res['doctorId'] || res['clinicId'] ){
                        div +=      '<input type="hidden" id="clinicId'+id+'" value="'+res['clinicId']+'" >'+
                            '<input type="hidden" id="doctorId'+id+'" value="'+res['doctorId']+'">'+
                            '<input type="hidden" id="maxdate" value="'+data['maxDateToShow']+'">'+
                            '<input type="hidden" id="sdate" value="'+data['serverDate']+'">';
                    }
                    if(res['doctorName']){
                        div +=  '<label class="doctorsearch"> Name</label>'+ '<div class="doc-name doctor-name" id="docName'+id+'">Dr. '+res['doctorName']+'</div>';
                    }
                    if( res['qualification']){
                        div +=  '<label class="doctorsearch"> Qualification</label>'+ '<div class="doc-database doctor-qualification" id="docQlf'+id+'">'+res['qualification']+'</div>';
                    }
                    if(res['specialityName']){
                        div +='<label class="doctorsearch"> Specialty</label>'+  '<div class="doc-database doctor-speciality" id="docSpl'+id+'">'+res['specialityName']+'</div>';
                    }
                    if(res['clinicName']){
                        div += '<label class="doctorsearch">Hospital/Clinic</label>'+ '<div class="doc-database doctor-clinicname" id="docClnName'+id+'">'+res['clinicName']+'</div>';
                    }
                    if(res['experience']){
                        div += '<label class="doctorsearch">Experience</label>' + '<div class="doc-database doctor-yearsofexp"> '+res['experience']+' Years</div>';
                    }

                    var flag = false;
                    if(res['location'] && res['city'] ){
                        flag = true;
                        div +='<label class="doctorsearch">Location</label>'+ '<div class="doc-database doctor-city" id="docClnLocCty'+id+'">'+res['location']+', '+res['city']+'.</div>';
                    }

                    if(res['location'] && !flag){
                        div +='<label class="doctorsearch">Location</label>'+ '<div class="doc-database doctor-location" id="docClnLoc'+id+'">'+res['location']+'</div>';

                    }
                    if(res['city'] && !flag){
                        div += '<label class="doctorsearch">Location</label>'+ '<div class="doc-database doctor-cityname" id="docClnCty'+id+'">'+res['city']+'</div>';
                    }

                    var slotFlag = false;
                    if(res['cdGeneralTime']){
                        slotFlag = true;
                        slotDiv +=  '<div class="week-slot doctor-times">'+res['cdGeneralTime']+'</div>';
                    }
                    if(res['consultationFee']){
                        slotDiv +=  '<div class="fees doctor-fees">Consultation Fee: '+res['consultationFee']+'</div>';
                    }

                    if(div != ''){
                        $("#addSrcDocList").append(maniDiv);
                        $("#docDtls"+id).append(div);
                        if(slotDiv != '' && slotFlag){
                            var aptbtn = '<input type="button" value="Book Appointment" class="saveb editbasicinfo-info hasDatepicker"  id="'+id+'">';
                            $("#docTiming"+id).append(slotDiv);
                            $("#docapt"+id).show().append(aptbtn);

                        }else{
                            slotDiv += '<a  onclick="showCustCareNo('+id+')" class="saveb editbasicinfo-infophone "  id="'+id+'"  ><i class="icon-phone hideicon"></i> Show Number</a>';
                            $("#docTiming"+id).parent().closest('div').removeClass('col-md-6 col-xs-6').addClass('col-md-12 col-xs-12').append(slotDiv);
                            $("#docapt"+id).hide();
                        }
                    }
                    id = id+1;
                }
                $.getScript('resources/jsplugins/jPages.js',function(){

                    $("div.holder").jPages({
                        containerID : "addSrcDocList",
                        perPage : 7
                    });

                    $("select").change(function(){
                        var newPerPage = parseInt( $(this).val() );

                        $("div.holder").jPages("destroy").jPages({
                            containerID   : "addSrcDocList",
                            perPage       : newPerPage
                        });
                    });

                });
                scrollToAnchor("addSrcDocList");
                $("#siderbar").removeClass('hide');
            }
        },
        error: function (data) {

        }
    });
/*}*/
    });


// onclick of fixAppointment btn in search list of doctor, init datePicker with required date validation and fetch the slot of selected doctor
    $("#addSrcDocList").on('click', '.hasDatepicker', function() {
        var id = this.id;
        var clinicId = $("#clinicId"+id).val();
        var doctorId = $("#doctorId"+id).val();
        var curDate = $('#sdate').val();
        var maxDateToshow = $('#maxdate').val();
        /*var maxDate = new Date(curDate);
        maxDate.setDate(maxDate.getDate() + 180);
        var dd = maxDate.getDate();
        var mm = maxDate.getMonth();
        var y = maxDate.getFullYear();
        var maxDateToshow = new Date(y, mm, dd);*/

        $(this).datetimepicker().datetimepicker({
            changeYear: true,
            changeMonth: true,
            scrollMonth: false,
            minDate: curDate,
            maxDate: maxDateToshow,
            onSelect: function (selectedDate) {
                var option = this.id == "from" ? "minDate" : "maxDate",
                    instance = $(this).data("datepicker"),
                    date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
            },
            onSelectDate: function () {
                getDoctorAvailableSlots(clinicId,doctorId,id);
                $(".xdsoft_datetimepicker,.xdsoft_noselect,.xdsoft_").hide();
            },
            timepicker: false,
            format: 'd/m/Y'
        });

    });

    $('form#cnfAppointment').submit(function(e){
        e.preventDefault();
        $("#cnfAppointment").find(".submit_btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;"> Please wait...');

        var url = "common/saveConfirmedAppt";
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(cnfApptJson('#cnfAppointment')),
            contentType: 'application/json',

            success: function (data) {
                var message = data['message'];
                var mainId = $("#mainUlsolt").val();
                $("#appConfirmPopup").removeClass('dailog-show');

                if (data['statusCode'] == 200) {

                    var ul = '#ulslots'+mainId+' li:contains('+data['slotTime']+') > a';
                    $(ul).prop('onclick',null).off('click').addClass('schedule').closest( "li").attr('title','NOT AVAILABLE');
                    $('#slotContent'+mainId).empty().prepend('<div id="confirmMessage">' + data['message'] + '</div>').children(':first').delay(5000).fadeOut(100);
                }else{
                    $('#slotContent'+mainId).empty().prepend('<div id="errorMessage">' + data['message'] + '</div>').children(':first').delay(5000).fadeOut(100);

                }
                document.getElementById('cnfAppointment').reset();
                $("#cnfAppointment").find(".submit_btn").html("Confirm");
            },
            error: function (data) {

            }
        });
    });

//trigger : on click of signIn button in showSignRegPopup(after selecting time slot in homepage (index.jsp))
// uses : check for username and password valid if valid save the selected date and time in Db else display the error
    $("form#scheduledSign").submit(function(e){
        e.preventDefault();
        var url = "api/user/appointment/checkPatExistAndSaveApp";
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(cnfApptJson('#scheduledSign')),
            contentType: 'application/json',

            success: function (data) {
                var id = $("#Sid").val();
                var message = data['message'];
                switch (data['statusCode']) {
                    case 200 :
                    case 100 :
                        $(".close-showSignRegPopup").trigger('click');
                        $("#slotContent"+id).empty().prepend('<div id="confirmMessage">' + data['message'] + '</div>').children(':first').delay(5000).fadeOut(100);

                        if(data['statusCode'] === 200){
                            var ul = '#ulslots'+id+' li:contains('+data['slotTime']+') > a';
                            $(ul).prop('onclick',null).off('click').addClass('schedule').closest( "li").attr('title','NOT AVAILABLE');
                        }
                        break;
                    case 203 :
                        $("#message").empty().html('<div id="errorMessage">' + data['message'] + '</div>').children(':first').delay(5000).fadeOut(100);
                        break;
                }
            },
            error: function (data) {

            }
        });
    });

//trigger : on check of radio button in registration tab in showSignRegPopup(after selecting time slot in homepage (index.jsp))
// uses : show the otp field and ajax call to backend to generate the otp and save the patient Details
    $('form#patientRegOnApp').submit(function (e) {
        e.preventDefault();
        var url = "api/user/signup";
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(PRegistrationFormToJSON('#patientRegOnApp')),
            contentType: 'application/json',

            success: function (data) {
                var statusCode = data['statusCode'];
                var message = data['message'];
                var userName = data['userName'];

                if (statusCode == 100 ) {
                    $("#verifyUserName").val(userName);
                    $("#registration-btn").hide();
                    $("#activation").show();
                }
                else {

                    $("#eMessage").empty().html('<div id="errorMessage">' + data['message'] + '</div>').children(':first').delay(5000).fadeOut(100);
                }

                $( "input:radio[name=activation]:checked" ).val(true);
            },
            error: function (data) {

                $( "input:radio[name=activation]:checked" ).val(true);
            }
        });

        return false;
    });

//trigger : on click of Activation button in registration tab in showSignRegPopup(after selecting time slot in homepage (index.jsp))
// uses : submit the otp entered and validate if valid activate the user
    $('form#activation').submit(function (e) {
        e.preventDefault();

        var url = "api/user/activation/mobileOTP";
        var userName = $('#userName').val();
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(mobileActiveFormToJSON('#activation')),
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                var statusCode = data['statusCode'];
                var userName = data['userName'];
                var id = $("#Rid").val();

                if (statusCode == 201) {
                    $(".close-showSignRegPopup").trigger('click');
                    var ul = '#ulslots'+id+' li:contains('+data['slotTime']+') > a';
                    $(ul).prop('onclick',null).off('click').addClass('schedule').closest( "li").attr('title','NOT AVAILABLE');

                    $("#slotContent"+id).prepend('<div id="confirmMessage">' + data['message'] + '</div>').children(':first').delay(5000).fadeOut(100);

                } if (statusCode == 100) {
                    $(".close-showSignRegPopup").trigger('click');
                    $("#slotContent"+id).empty().prepend('<div id="errorMessage">' + data['message'] + '</div>').children(':first').delay(5000).fadeOut(100);
                }else if(statusCode == 203){
                    $("#eMessage").empty().append('<div id="errorMessage">' + data['message'] + '</div>').children(':first').delay(5000).fadeOut(100);
                }else{
                    $("#slotContent"+id).empty().prepend('<div id="errorMessage">' + data['message'] + '</div>').children(':first').delay(5000).fadeOut(100);
                }
            },
            error: function (data) {

            }
        });

        return false;

    });



    $("#enqireforservices,#ourServices").off().on('click','.not_available',function(){
        var userId = $("#userId").text();
        switch(this.id){
            case "secondOpinion":
                $('#ajaxloaddiv').load("service/secondOpinion", function () {
                    $("#records_datepicker").datetimepicker({
                        format: 'd/m/Y H:i'
                    });
                    $("#referral_userId").val(userId);
                    $("#recordUserId").val(userId);
                });
                break;
            case "teleConsultation":
                /*$('#ajaxloaddiv').load("/service/teleConsultation", function () {
                    /!* $("#records_datepicker").datetimepicker({
                     format: 'd/m/Y H:i'
                     });*!/
                });*/
                $("#teleconsultInfo").addClass("dailog-show");
                break;
            case "surgeryReferral" :
                $('#ajaxloaddiv').load("service/surgeryReferral", function () {
                    $("#surgery_userId").val(userId);
                    $(".has").val(userId);
                });
                break;
            case "pharmacy":
            case "lab":
            case "rehab":
                $("#not_available").addClass("dailog-show");
                break;
        }
    });

});

function showCustCareNo(id){
    var maindiv = "<div class='phoneshow'> <h4>Contact Customer Care To Book Appointment</h4> <p><i class='icon-phone hideicon'></i> +  944-981-1444</p></div>";
    $("#slotContent"+id).empty().append(maindiv).show();

}
//used to fetch the slot of selected doctor
function getDoctorAvailableSlots(clinicId,doctorId,id){
    var url = "api/user/appointment/getDoctorSlotsOnDate";
    var dateObject = convertToJsDateObj($("#"+id).val());
    var jsondata = {"clinicId": clinicId,"doctorId":doctorId,"scheduledOn":dateObject};
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(jsondata),
        contentType: 'application/json',
        success: function (data) {
            if (data['statusCode'] == 200) {
                var maindiv =   '<div class="full-time-slots">'+
                    '<div class=" doctor-info slots">'+
                    '<div class="row">'+
                    '<div class="timesoltheading">'+
                    '<div class="timesoltheadingleft">'+
                    '<ul>'+
                    '<li> <span class="green-available"></span>Available</li>'+
                    '<li> <span class="grey-notavailable"></span>Not Available </li>'+
                    '</ul>'+
                    '</div>'+
                    '<div class="clear"></div>'+
                    '</div>'+
                    '</div>'+
                    '</div>'+
                    '<div class="form-wizard doctor-info">'+
                    '<div class="row">'+
                    '<div class="slot-time">'+
                    '<div class="demo-date" id="displDate'+id+'">' +
                    '</div>'+
                    '</div>'+
                    '<div class="slot-time-demo">'+
                    '<div class="demo-list">'+
                    '<ul class="ulslots" id="ulslots'+id+'">'+

                    '</ul>'+
                    '</div>'+
                    '</div>'+
                    '</div>'+
                    '</div>'+
                    '</div>';

                var li = '';
                var date = '';

                $.each(data['resultedSlots'],function (idx, obj){
                    date = dateIndDDMMyyy(idx);
                    $.each(obj,function (i,ojt) {

                        if(( ojt['status'])){
                            li += '<li title="NOT AVAILABLE"><a class="schedule">' + i + '</a></li>';
                        } else{
                            //this indicates request from index.jsp so, onclick of slot show sign-in popup
                            if(data['showNewEncounter']){
                                li += '<li title="AVAILABLE"><a class ="confirmApp" href="javascript:;" id="' + idx + '" onclick="return showSignRegisPopup(' + doctorId + ',' + clinicId + ','+ojt['tokenNo']+','+id+',this)">' + i + '</a></li>';
                            }else{
                                //this indicates request from appointment.jsp, onclick of slot show sign-in popup
                                li += '<li title="AVAILABLE"><a class ="confirmApp" href="javascript:;" id="' + idx + '" onclick="return showConfirmDetails(' + doctorId + ',' + clinicId + ','+ojt['tokenNo']+','+id+',this)">' + i + '</a></li>';
                            }
                        }
                    });
                });

                if(li != ''){
                    $("#slotContent"+id).empty().append(maindiv).show();
                    $("#ulslots"+id).append(li);
                    if(date != ''){
                        $("#displDate"+id).text(date);
                    }
                }
            }
            if(data['statusCode'] == 100){
                $("#slotContent"+id).empty().prepend("<div id='errorMessage'>"+ data['message']+"</div>").children(':first').delay(5000).fadeOut(100);
            }
        },
        error: function (data) {

        }
    });
}

$(".close-btn").click(function(){
    $(".notification,.alert-error,.spacer-t10").css('display', 'none');
});

//on click of particular time slot, used to display the confirmation pop of selected doctor with dateTime and reasonforvist
function showConfirmDetails(doctorId,clinicId,tokenNo,id,these){
    $("#appConfirmPopup").addClass('dailog-show');
    var docName = $("#docName"+id).text();
    //value required to call form submit
    $("#cnfDoctorId").val(doctorId);
    $("#cnfClinicId").val(clinicId);
    $("#cnfPatientId").val($("#userId").text());
    $("#cnfDate").val(these.id);
    $("#cnfTime").val($(these).text());
    $("#cnfDoctorName").val(docName);
    $("#tokenNo").val(tokenNo);
    $("#mainUlsolt").val(id);

    //to display the value in confirmation pop
    $("#cnfDocName").text(docName);
    $("#cnfDocSpl").text($("#docSpl"+id).text());
    $("#cnfDateTime").text($("#displDate"+id).text() +' '+$(these).text());
    $("#cnfClnName").text($("#docClnName"+id).text());

    var clnLocCity = $("#docClnLocCty"+id).text();
    if(clnLocCity){
        $("#cnfClnLoc").text(clnLocCity);
    }else{
        var clnLoc = $("#docClnLoc"+id).text();
        var clnCity = $("#docClnCty"+id).text();
        if(clnLoc){
            $("#cnfClnLoc").text(clnLoc);
        }
        if(clnCity){
            $("#cnfClnLoc").text(clnCity);
        }
    }
}


function scrollToAnchor(aid) {
    var aTag = $("#" + aid);
    $('html,body').animate({scrollTop: aTag.offset().top}, 'slow');
}

//trigger : on click of time slot in homePage(index.jsp, display showSignRegPopup div)
// uses : get required details and add to signin form and Registor form
function showSignRegisPopup(doctorId,clinicId,tokenNo,id,these){
    $("#showSignRegPopup").addClass('dailog-show');
    $("#SdocId,#RdocId").val(doctorId);
    $("#SclinicId,#RclinicId").val(clinicId);
    $("#StokenNo,#RtokenNo").val(tokenNo);
    $("#Sid,#Rid").val(id);
    $("#Sdate,#Rdate").val(these.id);
    $("#Stime,#Rtime").val($(these).text());
}


function cnfApptJson(form) {
    var array = jQuery(form).serializeArray();
    var json = {};
    var parentName;
    var newVal;
    jQuery.each(array, function () {
        parentName = this.name;
        newVal = this.value;
        json[parentName] = newVal || '';
    });

    return json;
}

