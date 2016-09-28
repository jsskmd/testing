
<link rel="stylesheet" href="resources/css/sketch.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/site.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/dataTables.responsive.min.css">

<script type="text/javascript" src="resources/jsplugins/jquery-ui.js"></script>
<script type="text/javascript" src="resources/jsplugins/jquery-ui-slider-pips.js"></script>

<script type="text/javascript" src="resources/jsplugins/site.js"></script>
<script type="text/javascript" src="resources/jsplugins/dataTables.responsive.min.js"></script>
<script type="text/javascript" src="resources/jsplugins/jquery.datetimepicker.min.js"></script>
<script type="text/javascript" src="resources/jsplugins/typeahead.bundle.js"></script>
<script type="text/javascript" src="resources/jsplugins/jquery.canvasjs.min.js"></script>
<script type="text/javascript" src="resources/jsplugins/sketch.js"></script>
<script type="text/javascript" src="resources/js/server/vitals.js"></script>
<script type="text/javascript" src="resources/js/server/h&p.js"></script>
<script type="text/javascript" src="resources/js/server/consultationUtils.js"></script>
<script type="text/javascript" src="resources/js/server/doctorMessenger.js"></script>
<link rel="stylesheet" href="resources/css/font-awesome.min.css" />

<script>
    $(document).ready(function () {

        $('li.hideconsult').hide();

        $('#buttonsadvanced').click(function () {
            $('li.hideconsult').show();
            $('#buttonsadvanced').addClass('hide');
            $('#buttonsadvancedhide').removeClass('hide');
        });

        $('#buttonsadvancedhide').click(function () {
            $('li.hideconsult').hide();
            $('#buttonsadvanced').removeClass('hide');
            $('#buttonsadvancedhide').addClass('hide');
        });

        $(".hp").click(function () {
            $(".index-for-doc-dash").slideToggle();
        });
        $("#hp_soap").click(function () {
            $("#hp_soap_head").slideToggle();
        });

    });
</script>


<div class="wrapper">
<div class="form-container">
<div id="tmm-form-wizard" class="container substrate register-margin" style="padding:0">
<div>
    <%--<div class="col-md-12">
        <h2 class="form-login-heading">Consultation</h2>
    </div>--%>
    <div class="clr form-login-heading">
        <div class="col-md-6 col-sm-6 ">
            <h2 style="border: none; margin:0px; padding:0; border-radius:0px; background: none" class="form-login-heading heding-list-for-db">
                Consultation</h2>
        </div>
        <div   class="col-md-6 col-sm-6 ">
            <a id="consultation_back" class="save-for-doc-dash-cancel">Clinic Patients</a>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>
    </div>
</div>
<div class="">
<div class="form-container">
<div class="col-md-5 no-pad set-width">
    <aside class="sec-doc">
        <div class="demo-doc-en">
            <ul class="doc-encounter col-md-12">
                <li class="dict-for-patient"><a ><i class="icon-mic doc-encounter"></i>Dictation</a></li>
                <li id="patient_info"><a ><i class="icon-user-2 doc-encounter"></i>Previous Records</a></li>
            </ul>
        </div>
        <div class="clear"></div>
        <input type="hidden" id="verdict">
        <div>
            <div class="doc-for-encount name_for_consultaion">
                <ul class="clasic-for-doc">
                    <li><a id="hp_total" class="hp icon-down-circled2"> Consultation Note </a><%--<i class="icon-down-dir pos-for-icon"></i>--%>
                    </li>

                    <li><a id="hp_soap">Progress Note </a></li>
                </ul>
            </div>
        </div>
        <div class="index-for-doc-dash add_padding_for_page" id="hp_total_head">
            <div class="doc-for-class">
                <ul>
                    <li class="chief-comp"><a id="aencounter_1" class="enc_color" onclick="display('encounter_1')">History of Present Illness<span style="color:red;">*</span> </a>
                    </li>
                    <li ><a id="aencounter_5" onclick="display('encounter_5')">Vital Signs</a></li>
                    <li><a id="aencounter_9" onclick="display('encounter_9')">E-Prescription</a></li>
                    <li><a id="aencounter_7" onclick="display('encounter_7')">E-Lab Order</a></li>
                    <%--
                                        <li><a onclick="display('encounter_3')">Immunizations</a></li>
                    --%>
                    <li class="hideconsult"><a id="aencounter_2" onclick="display('encounter_2')">Health History</a></li>
                    <li class="hideconsult"><a id="aencounter_4" onclick="display('encounter_4')">Review of Systems</a></li>
                    <li class="hideconsult"><a id="aencounter_6" onclick="display('encounter_6')">Physical Examination</a></li>
                    <li class="hideconsult"><a id="aencounter_8" onclick="display('encounter_8')">Assessment / Plan</a></li>
                    <li class="hideconsult"><a id="aencounter_10" onclick="display('encounter_10')">Procedure/s Performed</a></li>
                    <li class="hideconsult"><a id="aencounter_11" onclick="display('encounter_11')">Follow up / Referral</a></li>
                </ul>
            </div>
            <div class="actionbuttonsconsultaion">
                <a class="advanceoptions" id="buttonsadvanced"> View Advanced Opitions  <span class="wsmenu-arrow fa fa-angle-down menuhide"></span></a>
                <a class="hideoptions hide" id="buttonsadvancedhide" > Hide  Advanced Opitions <span class="wsmenu-arrow fa fa-angle-up menuhide"></span></a>

                <div class="clearfix"></div>
            </div>
        </div>
        <div class="index-for-doc-dash add_padding_for_page hide" id="hp_soap_head">
            <div class="doc-for-class">
                <ul>
                    <li class="chief-comp"><a>Subjective<span style="color:red;">*</span> </a></li>
                    <li><a>Objective</a></li>
                    <li><a>Assessment</a></li>
                    <li><a>Plan</a></li>
                </ul>
            </div>
        </div>
    </aside>
</div>
<div id="hp_total_body" class="encountersmain col-md-7">
<form method="post" id="encounterform" name="encounter" enctype='application/json'>
<div class="col-md-12 no-pad doc-dash-width">
    <div class="col-md-12  no-pad">
        <div class="col-md-2 doc-dash-for-encount"><img alt="doc-name" src="" id="patient_image"></div>
        <!-- <div class="col-md-6 no-pad">-->
        <ul class="padding-list" style="float: left;">
            <li class="padding-list-icon"><span class="name-info-app" id="patient_name"></span></li>
            <ul class="enc-details-fr-patient doc_dash_cls">
                <li class=""> ID <span class="ids-cls">:</span><span class="" id="patient_id"></span></li>
                <li class="">Gender <span class="generation"></span><span class="" id="patient_gender">  </span></li>
                <li class="">DOB<span class="doc-date-en"> </span><span class="" id="patient_dob"> </span></li>
                <li class="">Age <span class="birth-day">:</span><span class="" id="patient_age"> </span></li>
            </ul>
        </ul>
    </div>

    <div class="col-md-12 no-pad add_prop_for_dbase ">
        <div class="doc-dash-save-option">
            <a class="save-for-doc-dash">
                <button type="submit" class="submit_btn" style="background: none">SAVE</button>
            </a>
            <%-- <a id="consultation_back" class="save-for-doc-dash-cancel">BACK</a>--%>
        </div>

    </div>
</div>
<div class="clearfix"></div>
<div id="confirmMessage" class="hide"></div>
<div id="errorMessage" class="hide"></div>
<div class="col-md-12 no-pad doc-dash-width">

<div id="encounter_body">
<input type="hidden" id="encPatientId" name="patientId"/>
<input type="hidden" id="encDoctorId" name="doctorId"/>
<input type="hidden" id="encClinicId" name="clinicId"/>
<input type="hidden" name="age" id="enc_age">

<div class="col-md-7 background-for-doctor" id="encounter_1">

    <fieldset class="input-block doc-dash-bord ">
        <a href="#" data-toggle="tooltip" data-placement="right" title="Chief Complaints."> <label>Chief Complaint<span style="color:red;">*</span></label></a>
        <textarea id="chiefComplaint" style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;" maxlength="1500"
                  title="Chief Complaint" placeholder="" class="nclo intarea hst keyup-fake" name="miniEncounter.chiefComplaint"></textarea>
    </fieldset>

    <fieldset class="input-block doc-dash">
        <a href="#" data-toggle="tooltip" data-placement="right" title="History of Present Illness"> <label> History of Present Illness</label></a>
        <textarea style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;" id="hpi" maxlength="1500"
                  title="History of Present Illness" placeholder="" class="nclo intarea hst keyup-fake" name="miniEncounter.hpi"></textarea>
    </fieldset>

</div>

<div class="col-md-7 background-for-doctor hide" id="encounter_2">
    <div class="domain-classs">
        <a id="edit_encounter_2" class="popup-btn">Edit History</a>
    </div>
    <div class="doc-dashboard-for-histroy historyscroll " id="enc_history">

    </div>
</div>
<div class="col-md-7 background-for-doctor hide" id="encounter_2_edit">
    <div class="domain-classs">
        <a id="enc_save_history" class="save-for-doc-dash">Save History</a>
        <a class="popup-btn immmun" id="back_encounter_2">Back</a>
    </div>
    <div class="col-md-6  no-pad ">
        <fieldset class="input-block doc-dash-bord ">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Medical History"><label>
                Medical History</label></a>
            <textarea name="history.medicalHistory" class="nclo intarea hst keyup-fake" placeholder="" id="edit_mh"
                      title="Medical History"
                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
            <!--/ .tooltip-->
        </fieldset>
    </div>
    <div class="col-md-6  no-pad ">
        <fieldset class="input-block doc-dash-bord">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Family Histroy"> <label>
                Family History</label></a>
            <textarea name="history.familyHistory" class="nclo intarea hst keyup-fake" placeholder="" id="edit_fh"
                      title="Family History" maxlength="1500"
                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
            <!--/ .tooltip-->
        </fieldset>
    </div>


    <div class="col-md-6  no-pad ">
        <fieldset class="input-block doc-dash-bord ">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Surgical History"> <label>
                Surgical History</label></a>
            <textarea name="history.surgicalHistory" class="nclo intarea hst keyup-fake" placeholder="" id="edit_sh"
                      title="Surgical History" maxlength="1500"
                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
            <!--/ .tooltip-->
        </fieldset>
    </div>
    <div class="col-md-6  no-pad ">
        <fieldset class="input-block doc-dash-bord ">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Social History"> <label>
                Social History</label></a>
            <textarea name="history.socialHistory" class="nclo intarea hst keyup-fake" placeholder="" id="edit_sch"
                      title="Social History" maxlength="1500"
                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
            <!--/ .tooltip-->
        </fieldset>
    </div>


    <div class="col-md-6  no-pad ">
        <fieldset class="input-block doc-dash-bord ">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Risk Factors"> <label>
                Risk Factors </label></a>
            <textarea name="history.riskFactors" class="nclo intarea hst keyup-fake" placeholder="" id="edit_rf"
                      title="Risk Factors" maxlength="1500"
                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
            <!--/ .tooltip-->
        </fieldset>
    </div>
    <div class="col-md-6  no-pad ">
        <fieldset class="input-block doc-dash-bord ">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Allergies"> <label>
                Allergies</label></a>
            <textarea name="history.allergies" class="nclo intarea hst keyup-fake" placeholder="" id="edit_all"
                      title="Allergies" maxlength="1500"
                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
            <!--/ .tooltip-->
        </fieldset>
    </div>
    <div class="col-md-6 no-pad ">
        <fieldset class="input-block doc-dash-bord ">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Past Medication"> <label>
                Past Medications </label></a>
            <textarea name="history.pastMedications" class="nclo intarea hst keyup-fake" placeholder="" id="edit_pm"
                      title="Past Medications" maxlength="1500"
                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
            <!--/ .tooltip-->
        </fieldset>
    </div>

</div>
<div class="col-md-7 background-for-doctor hide" id="encounter_3">
    <div class="domain-classs">
        <a class="popup-btn immmun" id="view_encounter_3">View</a>
    </div>
    <table style="width:100%" class="responsive-stacked-table-medical with-mobile-labels enconter-tables">
        <tr>
            <th></th>
            <th>Immunizations</th>
            <th>Reason for Vaccination</th>
        </tr>
        <tr>
            <td><label><input type="checkbox"></label></td>
            <td>Hep B</td>
            <td>Hepatitis B Virus (chronic inflammation of the liver, life-long complication)</td>
        </tr>
        <tr>
            <td><label><input type="checkbox"></label></td>
            <td>DTap/Tdap/Td Booster</td>
            <td>Diphtheria, Tetanus and Pertussis(whooping cough)</td>
        </tr>
        <tr>
            <td><label><input type="checkbox"></label></td>
            <td>MMR</td>
            <td>Measles, Mumps and Rubella (German measles)</td>
        </tr>
        <tr>
            <td><label><input type="checkbox"></label></td>
            <td>Varicella</td>
            <td>Chickenpox</td>
        </tr>
        <tr>
            <td><label><input type="checkbox"></label></td>
            <td>Flu Vaccine</td>
            <td>Flu and Complication</td>
        </tr>
        <tr>
            <td><label><input type="checkbox"></label></td>
            <td>Hep A</td>
            <td>Hepatitis A virus (inflammation of the liver)</td>
        </tr>
        <tr>
            <td><label><input type="checkbox"></label></td>
            <td>HPV before age 27</td>
            <td>Human Papilloma virus</td>
        </tr>
        <tr>
            <td><label><input type="checkbox"></label></td>
            <td>Meningococcal (upto age 55)</td>
            <td>Meningococcal disease meningitis</td>
        </tr>
    </table>

</div>
<div class="col-md-7 background-for-doctor hide" id="encounter_3_edit">
    <div class="domain-classs">
        <a class="popup-btn immmun" id="back_encounter_3">Back</a>
    </div>
    <ul class="immuni-power">
        <%-- <li><input type="radio" name="demo1" id="child-age" />01 - 12</li>
         <li><input type="radio" name="demo1"  id="age-class" />13 - 18</li>
         <li><input type="radio" name="demo1" id="age-demo"/>19 - 23</li>
         <li><input type="radio" name="demo1" id="age-index"/>24 - 60</li>--%>
    </ul>
</div>
<div class="col-md-7 background-for-review hide" id="encounter_4">

    <div class="form-right" id="roscategories">
    </div>

    <jsp:include page="reviewofSystem.jsp"></jsp:include>

</div>
<div class="hide" id="encounter_5">
    <div class="col-md-7 background-for-doctor">
        <input type="hidden" name="vitals.monitored" value="true" id="monitored">

        <div class="col-md-12 no-pad remove-pad">
            <div class="col-md-3 no-pad">
                <fieldset class="input-block">
                    <label>Temperature</label>
                    <input type="text" title="Temperature " name="vitals.temp" placeholder="Fahrenheit" maxlength="5" pattern="^[\.\d]{2,5}"/>
                </fieldset>
            </div>

            <div class="col-md-3 no-pad ">
                <fieldset class="input-block">
                    <label>Pulse rate</label>
                    <input type="text" title="pulse rate " name="vitals.heartRate" placeholder="Beats/ min" maxlength="3" pattern="^[\d]{2,3}"/>
                </fieldset>
            </div>
            <div class="col-md-6 no-pad">
                <div class="col-md-6 no-pad ">
                    <fieldset class="input-block">
                        <label>BP</label>
                        <input type="text" class="demo-slash" title="BP " id="enc_systolic" placeholder="Systolic" maxlength="3" pattern="^[\d]{2,3}"/>
                    </fieldset>
                </div>
                <div class="col-md-6 no-pad ">
                    <fieldset class="input-block">
                        <label></label>
                        <input type="text" class="demo-slash" title="Diastolic" id="enc_diastolic" placeholder="Diastolic" maxlength="3" pattern="^[\d]{2,3}"/>
                        <input type="hidden" name="vitals.bp" id="enc_bp"/>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
            </div>
        </div>

        <div class="col-md-6 no-pad ">

            <div class="col-md-6 no-pad">
                <fieldset class="input-block">
                    <label>Resp. Rate</label>
                    <input type="text" title="Resp. Rate " name="vitals.respRate" placeholder="Breaths/ min" maxlength="2" pattern="^[\d]{2}"/>
                    <span class="temp-slatrt"></span>

                </fieldset>
            </div>
            <div class="col-md-6 space-for-link no-pad">
                <fieldset class="input-block">
                    <label>RBS</label>
                    <input type="text" class="demo-slash" title="RBS " name="vitals.sugar" placeholder="mm/dL" maxlength="3" pattern="^[\d]{2,3}"/>
                    <span class="temp-slatrt"></span>

                </fieldset>
            </div>
        </div>

        <div class="col-md-6 no-pad ">

            <div class="col-md-6 no-pad">
                <div class="col-md-6 no-pad ">
                    <fieldset class="input-block">
                        <label>Height</label>
                        <input type="text" title="Height " id="enc_feet" placeholder="Feet" maxlength="1" pattern="^[\d]{1}"/>
                    </fieldset>
                </div>
                <div class="col-md-6 no-pad ">
                    <fieldset class="input-block">
                        <label></label>
                        <input type="text" class="demo-slash" title="Temperature " id="enc_inch" placeholder="Inch" maxlength="2" pattern="^[\.?\d]{1,2}" value="0" />
                    </fieldset>
                    <input type="hidden" id="enc_height"/>
                    <input type="hidden" name="vitals.height" id="height"/>
                </div>
            </div>
            <div class="col-md-6 no-pad">
                <div class="col-md-6 no-pad">
                    <fieldset class="input-block">
                        <label>Weight</label>
                        <input type="text" title="Weight " name="vitals.weight" id="enc_weight" placeholder="Kg" maxlength="6" pattern="^[\.\d]{2,6}"/>
                    </fieldset>
                </div>

                <div class="col-md-6 no-pad">
                    <fieldset class="input-block">
                        <label>BMI</label>
                        <input type="text" title="BMI " name="vitals.bmi" id="enc_bmi" placeholder="Kg/m2"/>
                    </fieldset>
                </div>
            </div>
        </div>
        <div id="vital_message" class="hide"></div>
    </div>

    <div style="color: salmon;">Click here to view recorded vitals by Doctor/ Patient</div>
    <div class="col-md-12 no-pad "> <%--encounter-width--%>
        <div class="col-md-7 after-before-food">
            <ul class="todayappointmentul">
                <li class="searchalign">
                    <div id="doctor_monitored" class="show_border_clinic"><i class="icon-search-1"></i>  Recorded by Doctor
                    </div>

                </li>
                <li class="searchalign">
                    <div id="patient_monitored"><i class="icon-plus-circled"></i>Recorded by Patient</div>
                </li>

            </ul>

        </div>
        <div class="col-md-5 after-before-food align">

            <a class="preview-page" id="enc_vitalGraph">View Graph</a>
        </div>
    </div>

    <div class="col-md-12  no-pad "> <%--encounter-width--%>
        <div class="en-tab">
            <table id="encVitalsTable"  class="display hide"  cellpadding="0" cellspacing="0" width="100%">
                <thead>
                <tr ><%--class="color-tab-for-heading"--%>
                    <th>Date</th>
                    <th>Temp</th>
                    <th>BP</th>
                    <th>Resp.rate</th>
                    <th>Pulse rate</th>
                    <th>Height</th>
                    <th>Weight</th>
                    <th>BMI</th>
                    <th>Sugar</th>
                </tr>
                </thead>
                <tbody id="encVitals">

                </tbody>
            </table>

        </div>
    </div>
</div>
<div class="col-md-7 background-for-review hide" id="encounter_6">
    <div class="col-md-12 no-pad">
        <div class="col-md-8 no-pad">
            <fieldset class="input-block no-pad">
                <label>Select Specialty Template</label>

                <div class="dropdown">
                    <select class="dropdown-select" id="get_template">
                        <option value="0" selected="">General</option>
                        <option value=1>Cardiology</option>
                        <option value=2>Orthopedics</option>
                        <option value=3>Gastroenterology</option>
                    </select>
                </div>
                <!--/ .tooltip-->
            </fieldset>
        </div>
        <div class="col-md-4 no-pad">
            <img  id="cdpspecialityImages" style="height:30px;width:30px;cursor: pointer;" title="Speciality Image" src=""/>
        </div>

        <div class="clear"></div>
    </div>

    <div class="form-right" id="pecategories"></div>
    <div id="pebody">
        <jsp:include page="physicalExamination.jsp"></jsp:include>
    </div>

</div>
<div class="col-md-7 background-for-review hide" id="encounter_7">
    <div class="form-right" id="labheadings">

    </div>

    <div class="togglecontents" id="labbody">

        <div class="option-group field labCat1">
            <div class="general-case-for-en">
                <div class="enconut-heding">Hematology</div>
                <div id="labCat1"></div>
            </div>
        </div>
        <div class="option-group field labCat2" style="display: none">
            <div class="general-case-for-en">
                <div class="enconut-heding">Serology</div>
                <div id="labCat2"></div>
            </div>
        </div>
        <div class="option-group field labCat3" style="display: none">
            <div class="general-case-for-en">
                <div class="enconut-heding">Clinical Pathology</div>
                <div id="labCat3"></div>
            </div>
        </div>
        <div class="option-group field labCat4" style="display: none">
            <div class="general-case-for-en">
                <div class="enconut-heding">Cytology</div>
                <div id="labCat4"></div>
            </div>
        </div>
        <div class="option-group field labCat5" style="display: none">
            <div class="general-case-for-en">
                <div class="enconut-heding">Biochemistry</div>
                <div id="labCat5"></div>
            </div>
        </div>
        <div class="option-group field labCat6" style="display: none">
            <div class="general-case-for-en">
                <div class="enconut-heding">Microbiology</div>
                <div id="labCat6"></div>
            </div>
        </div>
        <div class="option-group field labCat7" style="display: none">
            <div class="general-case-for-en">
                <div class="enconut-heding">Hormonal Test</div>
                <div id="labCat7"></div>
            </div>
        </div>
        <div class="option-group field labCat8" style="display: none">
            <div class="general-case-for-en">
                <div id="labCat8"></div>
            </div>
        </div>
        <div class="clear"></div>
    </div>


</div>
<div class="col-md-7 background-for-doctor hide" id="encounter_8">
    <div class="col-md-6 no-pad ">
        <div class="col-md-4  no-pad ">
            <fieldset class="input-block">
                <a href="#" data-toggle="tooltip" data-placement="right" title="Assessment"> <label>ICD
                    Code </label></a>
                <input type="text" title="ICD Code " id="icdCode">
                <!--/ .tooltip-->
            </fieldset>
        </div>
        <div class="col-md-8  no-pad ">
            <fieldset class="input-block">
                <label>Disease Name </label>
                <input type="text" title="Disease Name " id="icdName">
                <!--/ .tooltip-->
            </fieldset>
        </div>
    </div>
    <div class="col-md-6  no-pad ">
        <div class="col-md-4 no-pad switch">
            <a id="addICDButton" class="add-btn-for-enconter"><i class="icon-plus-1"></i> Add</a>
        </div>
    </div>
    <div class="col-md-7 margin-for-encounter ">
        <fieldset class="input-block doc-dash-bord ">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Chief Complaints."> <label>Assessment</label></a>
            <textarea id="encounterImpres" class="nclo intarea hst keyup-fake" placeholder="" title="Assessment" name="miniEncounter.impression" style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea></fieldset>
        <fieldset class="input-block doc-dash-bord ">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Chief Complaints."> <label>Plan</label></a>
            <textarea id="encounterPlan" class="nclo intarea hst keyup-fake" placeholder="" title="Plan" name="miniEncounter.plan" style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea> </fieldset>
    </div>
</div>
<div class="hide" id="encounter_9">
    <div class="col-md-7 background-for-doctor">
        <div class="col-md-6  no-pad ">
            <div class="col-md-6  no-pad ">
                <fieldset class="input-block">
                    <label>Drug Name <span style="color:red;">*</span></label>
                    <input type="text" title="Brand Name " id="brandName" title="Drug Name">
                </fieldset>
            </div>
            <div class="col-md-6 no-pad">
                <div class="col-md-6 no-pad">
                    <fieldset class="input-block">
                        <label>Strength<span style="color:red;">*</span> </label>
                        <input type="text" title="Strength " id="strength" onkeyup="if (/\D/g.test(this.value)) this.value = this.value.replace(/\D/g,'')">
                    </fieldset>
                </div>

                <div class="col-md-6 no-pad">
                    <fieldset class="input-block">
                        <label></label>
                        <div class="dropdown">
                            <select class="dropdown-select demo-page" id="strengthUnits">
                                <option value="mg" selected>mg</option>
                                <option value="ml">ml</option>
                                <option value="Vial">Vial</option>
                                <option value="Tablet">Tablet</option>
                                <option value="Capsule">Capsule</option>
                                <option value="Kit">Kit</option>
                                <option value="gm">gm</option>
                            </select>
                        </div>
                    </fieldset>
                </div>
            </div>

        </div>
        <div class="col-md-6  no-pad ">
            <div class="col-md-6 no-pad">
                <fieldset class="input-block">
                    <label>Frequency</label>
                    <input type="text" title="Frequency " id="frequency">
                </fieldset>
            </div>
            <div class="col-md-6 no-pad">
                <fieldset class="input-block">
                    <label>No. of Days<span style="color:red;">*</span></label>
                    <input type="text" title="No. of Days" id="quantity" onkeyup="if (/\D/g.test(this.value)) this.value = this.value.replace(/\D/g,'')">
                    <!--/ .tooltip-->
                </fieldset>
            </div>
        </div>


        <div class="col-md-6  no-pad ">
            <div class="col-md-6 no-pad ">
                <fieldset class="input-block">
                    <label>Form </label>
                    <input type="text" title="Form " id="type">
                    <input type="hidden" id="unitPrice"/>
                    <input type="hidden" id="unitstrength"/>
                </fieldset>
            </div>
            <div class="col-md-6 no-pad">

                <fieldset class="input-block">
                    <label>Route </label>
                    <input type="text" title="Route " id="route">

                </fieldset>
            </div>


        </div>
        <div class="col-md-6 no-pad ">

            <div class="col-md-12 no-pad">
                <ul class="after-food pat-demo">
                    <li><input type="radio" name="shift" value="After Food" checked>After Food</li>
                    <li><input type="radio" name="shift" value="Before Food">Before food</li>
                </ul>
            </div>

        </div>


        <div class="en-specail">

            <div class="col-md-12 after-before-food align">


                <a class="preview-page addrxb">Add to Rx list</a>
            </div>
            <div id="pre_perrorMessage" class="hide"></div>
        </div>


    </div>
    <div class="col-md-12 no-pad ">
        <div class="en-tab">
            <table class="responsive-stacked-table with-mobile-labels" style="width:100%;font-size:16px;">
                <thead>
                <tr class="color-tab-for-heading">
                    <th>Drug Name</th>
                    <th>Strength</th>
                    <th>Frequency</th>
                    <th>No. of Days</th>
                    <th>Route</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody id="rxlist">

                </tbody>
            </table>

        </div>
    </div>
</div>
<div class="col-md-7 background-for-doctor hide" id="encounter_10">

    <fieldset class="input-block doc-dash-bord ">
        <a href="#" data-toggle="tooltip" data-placement="right" title="Procedure/s Performed"><label>
            Procedure/s Performed</label></a>
        <textarea style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"
                  title="Procedure/s Performed" placeholder="" class="nclo intarea hst keyup-fake" name="miniEncounter.procedures"
                  id="encounterProcedures"></textarea>
        <!--/ .tooltip-->
    </fieldset>


</div>

<div class="col-md-7 background-for-doctor hide" id="encounter_11">


    <fieldset class="input-block doc-dash-bord ">
        <a href="#" data-toggle="tooltip" data-placement="right" title="Follow up/Referral"><label>Follow
            up/Referral </label></a>

        <textarea style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"
                  title="Follow up/Referral " placeholder="" class="nclo intarea hst keyup-fake" name="miniEncounter.followup"
                  id="encounterFupRef"></textarea>
        <!--/ .tooltip-->
    </fieldset>


    <div class="col-md-3 doc-encounter-right" id="followupdiv">
        <input type="text"  readonly id="HPfollowup" value="" style="padding-left: 33px;" placeholder="Pick Date">
        <i class="icon-calendar add-position"></i>
    </div>


    <div class="full-time-slots" id="showHPfollowupSlot" style="display: none">
        <div class=" doctor-info slots">
            <div class="row">
                <div class="timesoltheading">
                    <div class="timesoltheadingleft">
                        <ul>
                            <li> <span class="green-available"></span>Available</li>
                            <li> <span class="grey-notavailable"></span>Not Available </li>
                        </ul>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
        <div class="form-wizard doctor-info" id="showslots">
            <div class="row">
                <div class="slot-time">
                    <div class="demo-date" id="displDate">
                    </div>
                </div>
                <div class="slot-time-demo">
                    <div class="demo-list">
                        <ul class="ulslots" id="doctTimeList">

                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div id="msg"></div>
    </div>

</div>

</div>

</div>
<div class="col-md-12 no-pad ">
    <div class="col-md-8 check-for-doc-dash">
        <span id="sharediv">
            <span class="demo-accees-for-patient" style="margin-left: 5px;">Do you want to retain the summary report with Doctor/ Clinic only?</span><input type="checkbox" name="share" value="false" id="enc_share"><span class="demo-accees-for-patient">Yes</span>
        </span>
    </div>
    <div class="col-md-4 no-pad preview">
        <a class="preview-page" id="previewreportbtn">Preview</a>
    </div>
</div>

</form>
</div>
<div id="hp_soap_body" class="hide col-md-7 encountersmain ">
    <form method="post" id="encounter_soap" name="encounter" enctype='application/json'>
        <div class="col-md-12 no-pad doc-dash-width">


            <div class="col-md-9 no-pad">
                <div class="col-md-2 doc-dash-for-encount"><img alt="doc-name" src="" id="hp_patient_image"></div>
                <!-- <div class="col-md-6 no-pad">-->
                <ul class="padding-list">
                    <li class="padding-list-icon"><span class="name-info-app" id="hp_patient_name"></span></li>
                    <ul class="enc-details-fr-patient">
                        <li class=""> ID <span class="ids-cls">:</span><span class="" id="hp_patient_id"></span></li>
                        <li class="">Gender <span class="generation"></span><span class=""
                                                                                  id="hp_patient_gender">  </span></li>
                        <li class="">DOB<span class="doc-date-en"></span><span class="" id="hp_patient_dob"> </span>
                        </li>
                        <li class="">Age <span class="birth-day">:</span><span class="" id="hp_patient_age"> </span>
                        </li>
                    </ul>

                </ul>
            </div>

            <div class="col-md-3 no-pad add_prop_for_dbase">
                <div class="doc-dash-save-option">
                    <a class="save-for-doc-dash">
                        <button type="submit" class="submit_btn" style="background: none">SAVE</button>
                    </a>
                    <%--<a id="progress_back" class="save-for-doc-dash-cancel">BACK</a>--%>
                </div>

            </div>

        </div>
        <div class="clearfix"></div>
        <div id="ppconfirmMessage" class="hide"></div>
        <div id="pperrorMessage" class="hide"></div>
        <div class="col-md-12 no-pad doc-dash-width">
            <div id="hp_encounter_body">
                <input type="hidden" id="hp_encPatientId" name="patientId"/>
                <input type="hidden" id="hp_encDoctorId" name="doctorId"/>
                <input type="hidden" id="hp_encClinicId" name="clinicId"/>
                <input type="hidden" name="age" id="hp_enc_age">
                <div class="col-md-7 background-for-doctor">
                    <div class="col-md-6  no-pad ">
                        <fieldset class="input-block doc-dash-bord">
                            <a href="#" data-toggle="tooltip" data-placement="right" title=""
                               data-original-title="Subjective"><label>
                                Subjective<span style="color:red;">*</span></label></a>
                            <textarea id="subjective" name="soap.subjective" class="nclo intarea hst" placeholder=""
                                      title="Subjective"
                                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="col-md-6  no-pad ">
                        <fieldset class="input-block doc-dash-bord ">
                            <a href="#" data-toggle="tooltip" data-placement="right" title=""
                               data-original-title="Objective"><label>
                                Objective</label></a>
                            <textarea id="objective" name="soap.objective" class="nclo intarea hst" placeholder=""
                                      title="Objective"
                                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="col-md-6  no-pad ">
                        <fieldset class="input-block doc-dash-bord ">
                            <a href="#" data-toggle="tooltip" data-placement="right" title=""
                               data-original-title="Assessment"><label>
                                Assessment</label></a>
                            <textarea id="assessment" name="soap.assessment" class="nclo intarea hst" placeholder=""
                                      title="Assessment"
                                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="col-md-6  no-pad ">
                        <fieldset class="input-block doc-dash-bord ">
                            <a href="#" data-toggle="tooltip" data-placement="right" title=""
                               data-original-title="Plan"><label>
                                Plan</label></a>
                            <textarea id="plan" name="soap.plan" class="nclo intarea hst" placeholder="" title="Plan"
                                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>

            </div>


        </div>
        <div class="col-md-12  no-pad "><%--encounter-width--%>
            <div class="col-md-12 check-for-doc-dash"><input type="checkbox" name="share" value="false" id="soap_share"><span
                    class="demo-accees-for-patient">Do you want to retain the summary report with Doctor/ Clinic only?</span></div>
            <%--<div class="col-md-4 no-pad preview">
                <!--/ .container-->
            </div>--%>
        </div>
    </form>

</div>
<%--<div class="col-md-7 background-for-doctor hide"  id="livedictation">
</div>--%>
<!--/ .row-->
<!--/ .row-->
<!--/ .row-->
<!--/ .form-wizard-->
</div>
<!--/ .container-->
</div>
</div>
</div>
</div>

<!-- end here-->
<div id="doc-dash-for-dict">
    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-smpopup">
        <div class="cancel-common"></div>
        <div id="tmm-form-wizard" class="confirmpadding">
            <form>
                <div class="note"></div>
                <div class="form-wrapper">
                    <div>
                        <div class="basicdetails-modal">
                            <div class="">
                                <div class="doc-for-dectating">Stop Typing, Start Dictating</div>
                                <div class="flag-histroy">
                                    <div id="livedictation">

                                    </div>
                                    <ul class="dictate-phase">
                                        <li class="to-dicatet">To Dictate Call</li>
                                        <li><img src="resources/images/cn1.png"
                                                 alt="contry-flag"><span class="conty-code">985-208-3056 (Access code: 1234#)</span></li>
                                        <li><img alt="contry-flag"
                                                 src="resources/images/cn2.png"><span
                                                class="conty-code">985-208-3061 (Access code: 1234#)</span></li>
                                        <li><img alt="contry-flag"
                                                 src="resources/images/cn3.png"><span
                                                class="conty-code">944-981-1333 (Access code: 1234#)</span></li>
                                    </ul>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div class="clearfix"></div>
    </div>
</div>

<!-- previous records pop up-->
<div id="patient_info_popup">
    <script type="text/javascript" src="resources/jsplugins/site.js"></script>
    <script type="text/javascript" src="resources/jsplugins/dataTables.responsive.min.js"></script>

    <div class="form-wrapper confirmAppForm class-popup" id="confirmAppForm" style="display: block;">
        <div class="cancel-close1"></div>

        <div class="confirmpadding" id="tmm-form-wizard">
            <div class="note"></div>
            <div class="form-wrapper">
                <div class="confirmappointemnt" style="text-align:center">
                    Previous Records
                </div>
                <div class="row splitcode">
                    <ul class="todayappointmentul">
                        <li>
                            <div class=" searchalign">
                                <div class="search-doc for scrip"><i class="icon-chart-pie-1 hideicon"></i>Profile Details
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class=" searchalign">
                                <div class="ad-doc for prescr-heal-rec"><i class="icon-folder hideicon"></i>Health Records
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>


                <div class=" col-md-12 background-for-reviews" id="script-state">
                    <div class="col-md-4 no-pad ">
                        <ul>
                            <li><p class="words"><span class="formheading-left2"> <i class="icon-group"></i> Name:  </span>
                                <span id="p_name">-</span></p></li>

                            <li><p class="words"><span class="formheading-left2"><i class=" icon-mail-alt"></i> Email Id:  </span>
                                <span id="p_email">-</span></p></li>

                            <li><p class="words"><span class="formheading-left2"><i class=" icon-home-3"></i> Address:  </span>
                                <span id="p_address">-</span></p></li>

                            <li><p class="words"><span class="formheading-left2"><i class=" icon-home-3"></i> City: </span>
                                <span id="p_city">-</span></p></li>

                            <li><p class="words">
                                    <span class="formheading-left2"><i
                                            class=" icon-flag-checkered"></i> Country: </span>
                                <span id="p_country">-</span></p>
                            </li>
                        </ul>

                    </div>
                    <div class="col-md-6 no-pad">
                        <ul>

                            <li><p class="words"><span class="formheading-left2"> <i class="icon-user-2"></i> Patient id:  </span>
                                <span id="p_id">-</span></p></li>

                            <li><p class="words"><span class="formheading-left2"><i class=" icon-phone-1"></i> Mobile Number: </span>
                                <span id="p_mobile">-</span></p></li>

                            <li><p class="words"><span class="formheading-left2"><i class="icon-location"></i> Location: </span>
                                <span id="p_location">-</span></p></li>

                            <li><p class="words">
                                <span class="formheading-left2"><i class=" icon-flag"></i> State: </span>
                                <span id="p_state">-</span></p></li>

                            <li><p class="words"><span class="formheading-left2"><i class=" icon-code"></i> Zip Code: </span>
                                <span id="p_zipcode">-</span></p></li>

                        </ul>
                    </div>

                </div>
                <div id="enc" class="hide">
                    <div class="list-for-tab-popup">
                        <div class="row">
                            <div class="col-md-6 no-padding">
                                <div class="col-md-4 search"><img src="resources/images/records.png" alt="Search-doctor" class="health-list"/></div>
                                <div class="col-md-8  no-pad search  ">
                                    <fieldset class="input-block">
                                        <label>My Medical Records</label>
                                        <div class="dropdown">
                                            <select class="dropdown-select" id="recordType">
                                                <option value="hospital">Hospital Records</option>
                                                <option value="prescription">Prescription</option>
                                                <option value="laborder">Lab Order</option>
                                                <option value="diagnostic">Lab Reports</option>
                                                <option value="xray">X-ray</option>
                                                <option value="mri">MRI</option>
                                                <option value="others">Miscellaneous</option>
                                            </select>
                                        </div>
                                    </fieldset>
                                </div>

                            </div>

                        </div>

                    </div>
                    <div class="background-for-list-space classwraps  " >

                        <table id="mybills" class="display" cellspacing="0" width="100%">
                            <thead>
                            <tr>
                                <th style="text-align:center;">Select</th>
                                <th style="text-align:center;">Date of visit</th>
                                <th style="text-align:center;">Specialty</th>
                                <th style="text-align:center;">Reason for visit</th>
                                <th style="text-align:center;">Diagnosis</th>
                                <th style="text-align:center;">View / Share</th>
                            </tr>
                            </thead>
                            <tbody id="myrecords">

                            </tbody>
                        </table>
                        <div id="viewmore_records" style="cursor: pointer;">View More</div>
                    </div>
                </div>

            </div>
            <div class="clear"></div>

        </div>
    </div>
</div>
<!--end here-->


<div id="graphDisplay">
    <div class="form-wrapper confirmAppForm class-popup" id="confirmAppForm" style="max-width: 950px !important;">
        <div class="cancel-common"></div>
        <div class="confirmpadding" id="tmm-form-wizard">
            <div class="confirmappointemnt">
                Vital Signs
            </div>
            <div class="row splitcode">
                <ul class="todayappointmentul">
                    <li>
                        <div class=" searchalign">
                            <div class="search-doc for scrip" id="graph_temp"><i class="icon-chart-pie-1 hideicon"></i>Temperature
                            </div>

                        </div>
                    </li>
                    <li>
                        <div class=" searchalign">
                            <div class="ad-doc for prescr-heal-rec" id="graph_bp"><i class="icon-folder hideicon"></i>Blood Pressure
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class=" searchalign">
                            <div class="ad-doc for prescr-heal-rec" id="graph_resp"><i class="icon-folder hideicon"></i>Respiratory
                                Rate
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class=" searchalign">
                            <div class="ad-doc for prescr-heal-rec" id="graph_pulse"><i
                                    class="icon-folder hideicon"></i>Pulse Rate
                            </div>
                        </div>
                    </li>

                    <li>
                        <div class=" searchalign">
                            <div class="ad-doc for prescr-heal-rec" id="graph_bmi"><i class="icon-folder hideicon"></i>BMI
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class=" searchalign">
                            <div class="ad-doc for prescr-heal-rec" id="graph_sugar"><i
                                    class="icon-folder hideicon"></i>Random Blood Sugar
                            </div>
                        </div>
                    </li>

                </ul>

            </div>
            <div id="chartContainer" style="height: 350px; width:100%;">
                <div class='novitals-doc'>Select specific vital to view graph !</div>
            </div>

        </div>
    </div>
</div>
<div id="pdfdisplay">

    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-popup" style="max-width: 800px !important;">
        <div class="cancel-common"></div>
        <div class="confirmpadding">
            <iframe src="" id="iframe" class="preview-iframe">
            </iframe>
            <img src="" id="imgiframe" class="preview-iframe"/>
        </div>
    </div>


</div>
<div id="saveEncPopup">
    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-smpopup">
        <div class="confirmappointemnt">DataLife Confirmation</div>
        <div class="confirmpadding">
            <div id="bodypsv" class="dywcpopup" style="padding: 15px !important;">
                <div class="note">Do you want to save Consultation/ Progess note and generate report?
                </div>
                <button type="button" class="cancelapp View" id="Yes">Yes</button>
                <button type="button" class="cancelapp share" id="No">No</button>
            </div>
        </div>
    </div>


</div>
<div id="shareRecordPopup">
    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-smpopup">
        <div class="cancel-common"></div>
        <div class="confirmpadding">
            <div id="bodypsv">
                <div class="confirmappointemnt">
                    <h1> Share Document</h1>
                </div>
                <div class="note">Note: Enter email id (can enter multiple email id's with comma separation) to share documents.
                </div>
                <div id="pconfirmMessage" class="hide"></div>
                <div id="perrorMessage" class="hide"></div>
                <form name="user" method="post" id="shareReportForm">
                    <input type="hidden" id="shareUserId" name="userId" value="">

                    <div class="shareemailpasswordholder">
                        <div class="shareemail">

                            <div class=""><label>Enter Email</label></div>

                            <input type="text" id="demo-input-facebook-theme" name="email"
                                   placeholder="Enter Email Address" required=""/>
                        </div>

                        <div class="clear"></div>
                    </div>

                    <input type="hidden" id="selectedsummary" name="reports"/>
                    <input type="hidden" id="shareticket" name="alfresAuthTicket"/>

                    <div id="sharebuttons">
                        <input type="submit" value="Share" class="saveb editbasicinfodemo shareRecordbut">
                        <img src="resources/images/small_loading.gif" class="hide small_loading">
                        <div class="clear"></div>
                    </div>
                </form>
                <div>

                </div>

            </div>
        </div>
    </div>
</div>

<div id="specialityImagePopup">

    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-popup " style="max-width: 600px !important;">
        <div class="spImage-close"></div>
        <div id="specialityImagePopupdiv" style="padding: 10px;">

            <div class="tools">
                <a href="#tools_sketch" data-tool="marker">Marker</a>
                <a href="#tools_sketch" data-tool="eraser">Eraser</a>
            </div>
            <div id="toolsketech">

            </div>
        </div>
    </div>
</div>
<div id="loadingDiv">
    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-smpopup">
        <div class="confirmpadding">
            <div id="bodypsv " class="dywcpopup" style="padding: 15px !important;">
                <img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">Please wait...
            </div>
        </div>
    </div>
</div>


