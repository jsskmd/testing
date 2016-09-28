<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 8/2/2016
  Time: 2:15 PM
  To change this template use File | Settings | File Templates.
--%>
<script type="text/javascript" src="resources/jsplugins/typeahead.bundle.js"></script>
<script type="text/javascript" src="resources/jsplugins/handlebars-v3.0.3.min.js"></script>

<script>
    $(document).ready(function(){

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

        $(".cancel-common").off().on('click', function (event) {
            $("#clinicPreferrencesPopup").removeClass("dailog-show");
            event.stopPropagation();
        });

        $("#clinic_doctorList").on('click',function(){
            $("#ajaxloaddiv").removeClass('hide');
            $("#ajaxload").addClass('hide');
        });

        $("#editClinicPreferences").click(function () {
            $("#view_clinicPreference").addClass("hide");
            $("#edit_clinicPreference").removeClass("hide");
        });

        $("#ledit,#pedit").click(function(){
            $("#readmode").addClass("hide");
            $("#writemode").removeClass("hide");
        });

        $("#cancelwmode").on('click',function(){
            $("#readmode").removeClass("hide");
            $("#writemode").addClass("hide");
        });

        $("#labPreferences,#pharmacyPreferences").on('submit',function(e){
            e.preventDefault();
            var id = this.id;
            var jsondata;
            var url;
            if(id == 'labPreferences'){

                jsondata = JSON.stringify(mobileActiveFormToJSON('#labPreferences'));
                url = "api/user/clinic/addLabToClinic";
            }else{

                jsondata = JSON.stringify(mobileActiveFormToJSON('#pharmacyPreferences'));
                url = "api/user/clinic/addPharmacyToClinic";
            }

            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: jsondata,
                contentType: 'application/json',

                success: function (data) {
                    var  statusCode = data['statusCode'];
                    var message = data['message'];
                    switch (statusCode){
                        case 200 :
                            var lab = data['lab'];
                            var pharmacy = data['pharmacy'];
                            if(id == 'labPreferences'){
                                $("#changeLabPreferences").removeClass('hide');
                                $("#"+id).addClass('hide');
                                appendLab(lab);
                            }else{
                                $("#changePharmPreferences").removeClass('hide');
                                $("#"+id).addClass('hide');
                                appendPharmacy(pharmacy);
                            }
                            $("#ppconfirmMessage").empty().prepend(message).delay(10000).fadeOut(100);
                            $("#clinic_settings").trigger('click');
                            break;
                        case 100 :
                            $("#pperrorMessage").empty().prepend(message).delay(10000).fadeOut(100);
                            break;
                    }
                },
                error: function (data) {

                }
            });
        });

        $("#changeLabPreferences,#changePharmPreferences").on('submit',function(e){
            e.preventDefault();
            var id = this.id;
            if(id == 'changeLabPreferences'){
                $("#labPreferences").removeClass('hide');
                $(this).addClass('hide');
                $("#lab").val($("#setLab").text());
                $("#searchLabId").val($("#labId").val());
            }else{
                $("#pharmacyPreferences").removeClass('hide');
                $(this).addClass('hide');
                $("#pharmacy").val($("#setPharm").text());
                $("#searchPharmId").val($("#pharmacyId").val());
            }
        });

        $('#clinicdoctors').on('click',function () {
            $("body").css("overflow", "hidden");
            $(this).addClass('activedrpt');
           /* $("#ajaxload").addClass('hide');
            $("#ajaxloaddiv").removeClass('hide');*/
            $("#clinicdoctors,#clinic_settings").removeClass('activedrpt');
            $("#clinicDoctorsPopup").load("clinic/clinicDoctorPopup", function () {

                $('#clinicDoctorsTable').dataTable({
                    "bDestroy": true
                }).fnDestroy();

                $('#clinicDoctorsTable').dataTable({
                    responsive: true,
                    "pagingType": "full_numbers",
                    "language": {
                        "emptyTable": "Enter valid DataLife Id and mobile number to get result !"
                    }
                });
                $(this).addClass("dailog-show");
            })
        });

        $('#clinicpatients').on('click',function () {
            $("body").css("overflow", "hidden");
            $(this).addClass('activedrpt');
            /*$("#ajaxload").addClass('hide');
            $("#ajaxloaddiv").removeClass('hide');*/
            $("#clinic_doctors,#clinic_settings").removeClass('activedrpt');
            $("#clinicPatientsPopup").load("clinic/clinicpatientsPopup", function () {
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
                $(this).addClass("dailog-show");
            })
        });


        var labNames = new Bloodhound({
            datumTokenizer: function (d) {
                return d.name;
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            remote: {
                url: "common/getLabName/%QUERY",
                filter: function (response) {
                    return response;
                }
            },
            limit : 10
        });
        labNames.initialize();
        var labSearchInput = $("#lab");
        labSearchInput.typeahead({
            hint:false,
            highlight: true,
            minLength: 0
        },{
            display : 'labName',
            source: labNames.ttAdapter(),
            templates: {
                empty: [].join('\n'),
                suggestion: Handlebars.compile('<div><strong>{{labName}}</strong> <span class="categories">{{location}}</span></div>')
            }
        }).on('typeahead:selected', function (event, data) {
            $('#searchLabId').val(data.labId);
        });
        labSearchInput.on("typeahead:opened", function () {
            var ev = $.Event("keydown");
            ev.keyCode = ev.which = 40;
            $(this).trigger(ev);
            return true
        });


        var pharmacyNames = new Bloodhound({
            datumTokenizer: function (d) {
                return d.name;
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,

            remote: {
                url: "common/getPharmacyName/%QUERY",
                filter: function (response) {
                    return response;
                }
            },
            limit : 10
        });
        pharmacyNames.initialize();
        var pharmacySearchInput = $("#pharmacy");
        pharmacySearchInput.typeahead({
            hint:false,
            highlight: true,
            minLength: 0
        },{
            display : 'pharmacyName',
            source: pharmacyNames.ttAdapter(),
            templates: {
                empty: [

                ].join('\n'),
                suggestion: Handlebars.compile('<div><strong>{{pharmacyName}}</strong> <span class="categories">{{location}}</span></div>')
            }
        }).on('typeahead:selected', function (event, data) {
            $('#searchPharmId').val(data.pharmacyId);
        });
        pharmacySearchInput.on("typeahead:opened", function () {
            var ev = $.Event("keydown");
            ev.keyCode = ev.which = 40;
            $(this).trigger(ev);
            return true
        });
    });
</script>

<div class="wrapper">
    <div class="form-container"  >
        <div id="tmm-form-wizard" class="container substrate">
            <div class="row clr">
                <div class="row clr form-login-heading">
                    <div class="col-md-6 col-sm-6 "><h2 class="app_for_patients_cls">Preferences</h2></div>
                    <div class="col-md-6 col-sm-6 right-document">
                        <span class="before">Search /Register</span>
                        <button type="button" class="share" id="clinicdoctors">Doctors</button>
                        <button type="button" class="share" id="clinicpatients"> Patients</button>
                        <button type="button" class="share" id="clinic_doctorList">Back</button>
                    </div>
                </div>
                <div id="ppconfirmMessage"></div>
                <div id="pperrorMessage"></div>
                <div class=" back_grd_slots_info">
                    <div class="background-for-list-space" id="readmode">
                        <div id="readPharmacy" class="hide">
                            <div class="row">
                                <div class="col-md-8">
                                    <div class="form-header">
                                        <div class="form-title form-icon title-icon-lock"><b>Pharmacy</b></div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-header-demo">
                                        <input type="submit" value="Edit" class="saveb editbasicinfo-vitals submit_btn hide" id="pedit">
                                    </div>
                                </div>
                            </div>
                            <div class="form-wizard">
                                <div class="row">
                                    <div class="col-md-8 col-sm-7">
                                        <div class="data-container">
                                            <ul id="appendpharmacy">

                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="readLab" class="hide">
                            <div class="row">
                            <div class="col-xs-12 border-id-dt iddetils">
                                <div class="form-header">
                                    <div class="form-title form-icon title-icon-lock  col-md-12"><b>Lab & Diagnostics</b></div>
                                    <div class="col-md-4">
                                        <div class="form-header-demo">
                                            <input type="submit" value="Edit" class="saveb editbasicinfo-vitals submit_btn hide" id="ledit">
                                        </div>
                                    </div>
                                </div>
                                <!--/ .form-header-->
                            </div>
                        </div>
                            <div class="form-wizard" id="showlab">
                            <div class="row">
                                <div class="col-md-8 col-sm-7">
                                    <div class="data-container">
                                        <ul id="appendlab">

                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </div>
                    </div>
                    <div class="background-for-list-space hide" id="writemode">
                        <div id="writePharmacy">
                            <div class="row">
                                <div class="col-md-8">
                                    <div class="form-header">
                                        <div class="form-title form-icon title-icon-lock"><b>Pharmacy</b></div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-header-demo">
                                        <input type="button" value="Cancel" class="saveb editbasicinfodemo hide" id="cancelwmode">
                                    </div>
                                </div>
                            </div>
                            <div class="form-wizard">
                                <div class="row">
                                    <form id="pharmacyPreferences" class="hide">
                                        <div class="col-md-6 no-padding">
                                            <div class="col-md-6 col-sm-6 no-pad ">
                                                <fieldset class="input-block">
                                                    <label>Pharmacy Search</label>
                                                    <input type="text" id="pharmacy" title="Location" pattern="^[a-zA-Z ]+$" maxlength="45" value="" name="name" placeholder="Pharmacy Search">
                                                    <input type="hidden" name="userId" value="" id="searchPharmId">
                                                    <input type="hidden" name="clinicId" value="" id="pharmClinicId">
                                                </fieldset>
                                            </div>
                                            <div class="col-md-3 col-sm-3 no-pad">
                                                <div class="form-header-demo-search setpref">
                                                    <input type="submit" value="Add" class="saveb editbasicinfo-vitals submit_btn">
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                    <form id="changePharmPreferences" class="hide">
                                        <div class="col-md-6 no-padding">
                                            <div class="col-md-6 col-sm-6 no-pad ">
                                                <fieldset class="input-block">
                                                    <h4><span id="setPharm" style="display: inline-block;padding: 4px;color: #8CAD6B;font-weight: bold;"></span></h4>
                                                    <input type="hidden" id="pharmId" value="">
                                                </fieldset>
                                            </div>
                                            <div class="col-md-3 col-sm-3 no-pad">
                                                    <input type="submit" value="Change" class="saveb editbasicinfo-vitals submit_btn">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div id="writeLab">
                            <div class="row">
                                <div class="col-xs-12 border-id-dt iddetils">
                                    <div class="form-header">
                                        <div class="form-title form-icon title-icon-lock  col-md-2"><b>Lab </b></div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-wizard">
                                <div class="row">
                                    <form id="labPreferences" class="hide">
                                        <div class="col-md-6 no-padding">
                                            <div class="col-md-6 col-sm-6 no-pad ">
                                                <fieldset class="input-block">
                                                    <label>Lab Search</label>
                                                    <input type="text" id="lab" title="Location" pattern="^[a-zA-Z ]+$" maxlength="45" value="" name="name" placeholder="Lab Search">
                                                    <input type="hidden" name="userId" value="" id="searchLabId">
                                                    <input type="hidden" name="clinicId" value="" id="labClinicId">
                                                </fieldset>
                                            </div>
                                            <div class="col-md-3 col-sm-3 no-pad">
                                                <div class="form-header-demo-search ">
                                                    <input type="submit" value="Add" class="saveb editbasicinfo-vitals submit_btn">
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                    <form id="changeLabPreferences" method="post" class="hide">
                                        <div class="col-md-6 no-padding">
                                            <div class="col-md-6 col-sm-6 no-pad ">
                                                <fieldset class="input-block">
                                                    <h4><span id="setLab" style="display: inline-block;padding: 4px;color: #8CAD6B;font-weight: bold;"></span></h4>
                                                    <input type="hidden" id="labId" value="">
                                                </fieldset>
                                            </div>
                                            <div class="col-md-3 col-sm-3 no-pad">
                                                <%--div class="form-header-demo-search ">--%>
                                                    <input type="SUBMIT" value="Change" class="saveb editbasicinfo-vitals submit_btn">
                                               <%-- </div>--%>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

