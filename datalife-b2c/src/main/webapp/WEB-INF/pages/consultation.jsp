
<script type="text/javascript" src="resources/jsplugins/typeahead.bundle.js"></script>
<script type="text/javascript" src="resources/jsplugins/jquery.canvasjs.min.js"></script>
<%--<script type="text/javascript" src="resources/js/server/pconsultation.js"></script>--%>
<script type="text/javascript" src="resources/js/server/consultationUtils.js"></script>
<link rel="stylesheet" href="resources/css/easy-responsive-tabs.css">

<script>
    $(document).ready(function() {
        var text_max = 1500;
        $('#textarea_feedback').html(text_max + ' characters remaining');

        $('#chiefComplaint').keyup(function() {
            var text_length = $('#chiefComplaint').val().length;
            var text_remaining = text_max - text_length;
            $('#textarea_feedback').html(text_remaining + ' characters remaining');
        });
    });
</script>
<div class="wrapper">
    <div class="form-container">
<div id="tmm-form-wizard" class="container substrate register-margin" style="padding: 0 !important;">
            <div class="">
                <div class="col-md-12">
                    <h2 class="form-login-heading">Consultation/ Progress Note</h2>
                </div>
            </div>
            <div class="">
                <div class="form-container">
                    <div class="col-md-4  set-width">
                        <aside class="sec-doc">
                            <div class="clear"></div>
                            <input type="hidden" id="verdict">
                            <div>
                                <div class="doc-for-encount">
                                    <ul class="clasic-for-doc  ">
                                        <li><a id="hp_total" class="hp icon-down-circled2"> Consultation/ Progress Note </a><%--<i class="icon-down-dir pos-for-icon"></i>--%>
                                        </li>
                                        <%--<li><a class="dict-for-patient icon-mic">Dictation</a></li>--%>
                                    </ul>
                                </div>
                            </div>
                            <div class="index-for-doc-dash" id="hp_total_head">
                                <div class="doc-for-class">
                                    <ul >
                                        <li class="chief-comp"><a id="aencounter_1" class="enc_color" onclick="display('encounter_1')">History of Present Illness<span style="color:red;">*</span> </a></li>
                                        <li><a id="aencounter_2" onclick="display('encounter_2')">Health History</a></li>

                                        <li><a id="aencounter_4" onclick="display('encounter_4')">Review of Systems</a></li>
                                        <li><a id="aencounter_5" onclick="display('encounter_5')">Vital Signs</a></li>
                                        <li><a id="aencounter_6" onclick="display('encounter_6')">Physical Examination</a></li>
                                        <li><a id="aencounter_7" onclick="display('encounter_7')">E-Lab Order</a></li>
                                        <li><a id="aencounter_8" onclick="display('encounter_8')">Assessment / Plan</a></li>
                                        <li><a id="aencounter_10" onclick="display('encounter_10')">Procedure/s Performed</a></li>
                                        <li><a id="aencounter_11" onclick="display('encounter_11')">Follow up / Referral</a></li>
                                    </ul>
                                </div>
                            </div>

                        </aside>
                    </div>
                    <div id="hp_total_body" class="encountersmain col-md-8">
            <form method="post" id="consultationform" name="encounter" enctype='application/json'>
                <div class="col-md-12 no-pad doc-dash-width">
                    <input type="hidden" name="doctorName" id="doctorName" class="con_inputs" readonly>
                    <input type="hidden"  name="clinicName" id="clinicName" class="con_inputs" readonly>
                    <input type="hidden"  name="mobileNumber" id="contactNumber" class="con_inputs" readonly>
                    <input type="hidden"  name="mciNumber" id="mciNumber" class="con_inputs" readonly>

    <div class="col-md-9 no-pad">
        <ul class="padding-list">
            <li class="padding-list-icon"><span class="name-info-app" id="patient_name"></span></li>
            <ul class="enc-details-fr-patient">
                <li>
                    <span  class="enc_li">Doctor Name  </span>
                    <span class="colon_class_1">:</span>
                    <span id="sh_doctorName"></span>
                </li>
                <li>
                    <span  class="enc_li">Licence No. </span>
                    <span class="colon_class_2">:</span>
                    <span id="sh_mciNumber"></span>
                </li>

            </ul>
            <ul class="enc-details-fr-patient">
                <li>
                    <span  class="enc_li">Clinic Name  </span>
                    <span class="colon_class_3">: </span>
                    <span id="sh_clinicName"></span>
                </li>
                <li>
                    <span  class="enc_li">Contact No.</span>
                    <span class="colon_class_4"> : </span>
                    <span id="sh_contactNumber"></span>
                </li>
            </ul>
        </ul>
    </div>

    <div class="col-md-3 no-pad">
        <div class="doc-dash-save-option">
            <a class="save-for-doc-dash">
                <button type="submit" class="submit_btn" style="background: none">SAVE</button>
            </a>
        </div>

    </div>

</div>
<div class="clearfix"></div>
<div id="confirmMessage" class="hide"></div>
<div id="errorMessage" class="hide"></div>
<div class="col-md-12 no-pad doc-dash-width">

<div id="encounter_body">
<input type="hidden" id="encPatientId" name="patientId"/>
<input type="hidden" name="age" id="enc_age">
<li class="rresp-tab-active"><a onclick="display('encounter_1')" class="" id="aencounter_1">History of Present Illness<span style="color:red;">*</span>  <i class="icon-plus-squared rightgreen"></i></a>
</li>
<div class="col-md-7 background-for-doctor" id="encounter_1">

    <fieldset class="input-block doc-dash-bord ">
        <a href="#" data-toggle="tooltip" data-placement="right" title="Chief Complaints."> <label>
            Chief Complaint<span style="color:red;">*</span></label></a>
        <textarea id="chiefComplaint"
                  style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"
                  title="Chief Complaint" placeholder="" class="nclo intarea hst"
                  name="miniEncounter.chiefComplaint" maxlength="1500"></textarea>
        <div id="chiefComplaint_feedback"></div>
        <!--/ .tooltip-->
    </fieldset>

    <fieldset class="input-block doc-dash">
        <a href="#" data-toggle="tooltip" data-placement="right" title="History of Present Illness"> <label>
            History of Present Illness</label></a>
        <textarea style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;" id="hpi"
                  title="History of Present Illness" placeholder="" class="nclo intarea hst"
                  name="miniEncounter.hpi" maxlength="1500"></textarea>
        <div id="hpi_feedback"></div>
        <!--/ .tooltip-->
    </fieldset>


</div>
<li class="rresp-tab-active"><a id="aencounter_2" onclick="display('encounter_2')">Health History <i class="icon-plus-squared rightgreen"></i></a></li>
<div class="col-md-7 background-for-doctor hide" id="encounter_2">

    <div class="domain-classs">
        <a id="edit_encounter_2" class="popup-btn">Edit History</a>
    </div>


    <div class="doc-dashboard-for-histroy historyscroll" id="enc_history">

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
            <textarea name="history.medicalHistory" class="nclo intarea hst" placeholder="" id="edit_mh" maxlength="1500"
                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
            <!--/ .tooltip-->
        </fieldset>
    </div>
    <div class="col-md-6  no-pad ">
        <fieldset class="input-block doc-dash-bord">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Family Histroy">
                <label>Family History</label></a>
            <textarea name="history.familyHistory" class="nclo intarea hst" placeholder="" id="edit_fh"
                      title="Family History" maxlength="1500"
                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
            <!--/ .tooltip-->
        </fieldset>
    </div>


    <div class="col-md-6  no-pad ">
        <fieldset class="input-block doc-dash-bord ">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Surgical History"> <label>
                Surgical History</label></a>
            <textarea name="history.surgicalHistory" class="nclo intarea hst" placeholder="" id="edit_sh"
                      title="Surgical History" maxlength="1500"
                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
            <!--/ .tooltip-->
        </fieldset>
    </div>
    <div class="col-md-6  no-pad ">
        <fieldset class="input-block doc-dash-bord ">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Social History"> <label>
                Social History</label></a>
            <textarea name="history.socialHistory" class="nclo intarea hst" placeholder="" id="edit_sch"
                      title="Social History" maxlength="1500"
                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
            <!--/ .tooltip-->
        </fieldset>
    </div>


    <div class="col-md-6  no-pad ">
        <fieldset class="input-block doc-dash-bord ">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Risk Factors"> <label>
                Risk Factors </label></a>
            <textarea name="history.riskFactors" class="nclo intarea hst" placeholder="" id="edit_rf"
                      title="Risk Factors" maxlength="1500"
                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
            <!--/ .tooltip-->
        </fieldset>
    </div>
    <div class="col-md-6  no-pad ">
        <fieldset class="input-block doc-dash-bord ">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Allergies">
                <label>Allergies</label></a>
            <textarea name="history.allergies" class="nclo intarea hst" placeholder="" id="edit_all" title="Allergies" maxlength="1500"
                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea>
            <!--/ .tooltip-->
        </fieldset>
    </div>
    <div class="col-md-6 no-pad ">
        <fieldset class="input-block doc-dash-bord ">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Past Medication">
                <label>Past Medications </label></a>
            <textarea name="history.pastMedications" class="nclo intarea hst" placeholder="" id="edit_pm"  title="Past Medications" maxlength="1500"
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
<li class="rresp-tab-active"><a id="aencounter_4" onclick="display('encounter_4')">Review of Systems <i class="icon-plus-squared rightgreen"></i></a></li>

<div class="col-md-7 background-for-review hide" id="encounter_4">


    <div class="form-right" id="roscategories">
    </div>

    <jsp:include page="reviewofSystem.jsp"></jsp:include>

</div>
<li class="rresp-tab-active"><a id="aencounter_5" onclick="display('encounter_5')">Vital Signs <i class="icon-plus-squared rightgreen"></i></a></li>

<div class="hide" id="encounter_5">
    <div class="col-md-7 background-for-doctor">
        <input type="hidden" name="vitals.monitored" value="true" id="monitored">

        <div class="col-md-12 no-pad remove-pad">
            <div class="col-md-3 no-pad">
                <fieldset class="input-block">
                    <label>Temperature (f)</label>
                    <input type="text" title="Temperature " name="vitals.temp" placeholder="Fahrenheit" maxlength="5" pattern="^[\.\d]{2,5}"/>


                    <!--/ .tooltip-->
                </fieldset>
            </div>

            <div class="col-md-3 no-pad ">
                <fieldset class="input-block">
                    <label>Pulse rate</label>
                    <input type="text" title="pulse rate " name="vitals.heartRate" placeholder="Beats/ min" maxlength="3" pattern="^[\d]{2,3}"/>
                    <%--
                                                    <span class="temp2"></span>
                    --%>

                    <!--/ .tooltip-->
                </fieldset>
            </div>
            <div class="col-md-6 no-pad">
                <div class="col-md-6 no-pad ">
                    <fieldset class="input-block">
                        <label>BP</label>
                        <input type="text" class="demo-slash" title="BP " id="enc_systolic" placeholder="Systolic" maxlength="3" pattern="^[\d]{2,3}"/>
                        <%--
                                                            <span class="temp3">/</span>
                        --%>

                        <!--/ .tooltip-->
                    </fieldset>
                </div>
                <div class="col-md-6 no-pad ">
                    <fieldset class="input-block">
                        <label></label>
                        <input type="text" class="demo-slash" title="Temperature " id="enc_diastolic" placeholder="Diastolic" maxlength="3" pattern="^[\d]{2,3}"/>

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

                    <!--/ .tooltip-->
                </fieldset>
            </div>
            <div class="col-md-6 space-for-link no-pad">
                <fieldset class="input-block">
                    <label>RBS</label>
                    <input type="text" class="demo-slash" title="FBS " name="vitals.sugar" placeholder="mm/dL" maxlength="3" pattern="^[\d]{2,3}"/>
                    <span class="temp-slatrt"></span>

                    <!--/ .tooltip-->
                </fieldset>
            </div>


        </div>

        <div class="col-md-6 no-pad ">

            <div class="col-md-6 no-pad">
                <div class="col-md-6 no-pad ">
                    <fieldset class="input-block">
                        <label>Height</label>
                        <input type="text" title="Height " id="enc_feet" placeholder="Feet" maxlength="1" pattern="^[\d]{1}"/>

                        <!--/ .tooltip-->
                    </fieldset>
                </div>
                <div class="col-md-6 no-pad ">
                    <fieldset class="input-block">
                        <label></label>
                        <input type="text" class="demo-slash" title="Temperature " id="enc_inch" placeholder="Inch" maxlength="3" pattern="^[\.?\d]{1,3}"  value="0"/>
                        <!--/ .tooltip-->
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
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
            </div>
        </div>
    </div>

</div>
<li class="rresp-tab-active"><a id="aencounter_6" onclick="display('encounter_6')">Physical Examination <i class="icon-plus-squared rightgreen"></i></a></li>
<div class="col-md-7 background-for-review hide" id="encounter_6">
    <div class="form-right" id="pecategories"></div>
    <div id="pebody">
        <jsp:include page="physicalExamination.jsp"></jsp:include>
    </div>

</div>
<li class="rresp-tab-active"><a id="aencounter_7" onclick="display('encounter_7')">E-Lab Order <i class="icon-plus-squared rightgreen"></i></a></li>

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
<li class="rresp-tab-active"><a id="aencounter_8" onclick="display('encounter_8')">Assessment / Plan <i class="icon-plus-squared rightgreen"></i></a></li>

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


    <div class="col-md-12 no-pad ">
        <fieldset class="input-block doc-dash-bord ">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Chief Complaints."> <label>Assessment</label></a>
            <textarea id="encounterImpres" class="nclo intarea hst" placeholder="" title="" name="miniEncounter.impression" style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea></fieldset>
        <fieldset class="input-block doc-dash-bord ">
            <a href="#" data-toggle="tooltip" data-placement="right" title="Chief Complaints."> <label>Plan</label></a>
            <textarea id="encounterPlan" class="nclo intarea hst" placeholder="" title="" name="miniEncounter.plan" style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"></textarea> </fieldset>
    </div>


</div>
<div class="hide" id="encounter_9">
    <div class="col-md-7 background-for-doctor">


        <div class="col-md-6  no-pad ">
            <div class="col-md-6  no-pad ">

                <fieldset class="input-block">
                    <label>Drug Name<span style="color:red;">*</span> </label>
                    <input type="text" title="Brand Name " id="brandName">
                    <!--/ .tooltip-->
                </fieldset>
            </div>
            <div class="col-md-6 no-pad">
                <div class="col-md-6 no-pad">

                    <fieldset class="input-block">
                        <label>Strength<span style="color:red;">*</span> </label>
                        <input type="text" title="ICD Code " id="strength" onkeyup="if (/\D/g.test(this.value)) this.value = this.value.replace(/\D/g,'')">
                        <!--/ .tooltip-->
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
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
            </div>

        </div>
        <div class="col-md-6  no-pad ">

            <div class="col-md-6 no-pad">
                <fieldset class="input-block">
                    <label>Quantity<span style="color:red;">*</span></label>
                    <input type="text" title="ICD Code " id="quantity"  onkeyup="if (/\D/g.test(this.value)) this.value = this.value.replace(/\D/g,'')">
                    <!--/ .tooltip-->
                </fieldset>
            </div>
            <div class="col-md-6 no-pad ">

                <fieldset class="input-block">
                    <label>Form </label>

                    <input type="text" title="ICD Code " id="type">
                    <input type="hidden" id="unitPrice"/>
                    <input type="hidden" id="unitstrength"/>

                </fieldset>
            </div>
        </div>


        <div class="col-md-6  no-pad ">
            <div class="col-md-6 no-pad">
                <fieldset class="input-block">
                    <label>Route </label>
                    <input type="text" title="ICD Code " id="route">

                </fieldset>
            </div>
            <div class="col-md-6 no-pad">
                <fieldset class="input-block">
                    <label>Frequency</label>
                    <input type="text" title="ICD Code " id="frequency">

                    <!--/ .tooltip-->
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
            <div id="perrorMessage"></div>
        </div>


    </div>
    <div class="col-md-12 no-pad ">
        <div class="en-tab">
            <table class="responsive-stacked-table with-mobile-labels" style="width:100%;font-size:16px;">
                <thead>
                <tr class="color-tab-for-heading">
                    <th>Medication Name</th>
                    <th>Strength</th>
                    <th>Frequency</th>
                    <th>Quantity</th>
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
<li class="rresp-tab-active"><a id="aencounter_10" onclick="display('encounter_10')">Procedure/s Performed <i class="icon-plus-squared rightgreen"></i></a></li>

<div class="col-md-7 background-for-doctor hide" id="encounter_10">

    <fieldset class="input-block doc-dash-bord ">
        <a href="#" data-toggle="tooltip" data-placement="right" title="Procedure/s Performed"><label>
            Procedure/s Performed</label></a>
        <textarea style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"
                  title="Procedure/s Performed" placeholder="" class="nclo intarea hst" name="miniEncounter.procedures"
                  id="encounterProcedures"></textarea>
        <!--/ .tooltip-->
    </fieldset>


</div>
<li class="rresp-tab-active"><a id="aencounter_11" onclick="display('encounter_11')">Follow up / Referral <i class="icon-plus-squared rightgreen"></i></a></li>

<div class="col-md-7 background-for-doctor hide" id="encounter_11">


    <fieldset class="input-block doc-dash-bord ">
        <a href="#" data-toggle="tooltip" data-placement="right" title="Follow up/Referral"><label>Follow
            up/Referral </label></a>

        <textarea style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px;height:83px;"
                  title="Follow up/Referral " placeholder="" class="nclo intarea hst" name="miniEncounter.followup"
                  id="encounterFupRef"></textarea>
        <!--/ .tooltip-->
    </fieldset>


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
        <div class="form-wizard doctor-info" id="msg">
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
    </div>

</div>

</div>

</div>
<div class="col-md-12 no-pad ">
    <div class="col-md-12 check-for-doc-dash"><input type="checkbox" name="share" value="true"  id="enc_share" checked style="display: none"> <a
            class="preview-page" id="previewreportbtn">Preview</a></div>
    <%-- <div class="col-md-4 no-pad preview">
         <!--/ .container-->
     </div>--%>
</div>
</form>
</div>
<!--/ .form-wizard-->
</div>
</div>
</div>
</div>
</div>


<!-- end here-->
<%--<div id="doc-dash-for-dict">
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
                                    <jsp:include page="liveDictation.jsp"></jsp:include>
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
</div>--%>

<!--end here-->

<div id="saveEncPopup">
    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-smpopup">
        <div class="confirmappointemnt">DataLife Confirmation</div>
        <div class="confirmpadding">
            <div id="bodypsv" class="dywcpopup" style="padding: 15px !important;">
                <div class="note">Do you want to save Consultation/ Progress note and generate report?
                </div>
                <button type="button" class="cancelapp View" id="Yes">Yes</button>
                <button type="button" class="cancelapp share" id="No">No</button>
            </div>
        </div>
    </div>


</div>

<div id="pdfdisplay">
    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-popup" style="max-width: 800px !important;">
        <div class="cancel-common"></div>
        <div class="confirmpadding">
            <iframe src="" id="iframe" class="preview-iframe">  [Your user agent does not support frames or is currently configured
                not to display frames. However, you may visit
            </iframe>
        </div>
    </div>
</div>

<script type="text/javascript" src="resources/jsplugins/easyResponsiveTabs.js"></script>
<script type="text/javascript">

    $('#focusguard-1').on('focus', function() {
        $('#chiefComplaint').focus();
    });
    $('#focusguard-2').on('focus', function() {
      $('#edit_mh').focus();
    });
    $('#focusguard-3').on('focus', function() {
      $('#vitaltemp').focus();
    });

    $(document).ready(function() {

        $(".hp").click(function(){
            $(".index-for-doc-dash").slideToggle();
        });

        //Horizontal Tab
        $('#parentHorizontalTab').easyResponsiveTabs({
            type: 'default', //Types: default, vertical, accordion
            width: 'auto', //auto or any width like 600px
            fit: true, // 100% fit in a container
            tabidentify: 'hor_1', // The tab groups identifier
            activate: function(event) { // Callback function if tab is switched
                var $tab = $(this);
                var $info = $('#nested-tabInfo');
                var $name = $('span', $info);
                $name.text($tab.text());
                $info.show();
            }
        });

        // Child Tab
        $('#ChildVerticalTab_1').easyResponsiveTabs({
            type: 'vertical',
            width: 'auto',
            fit: true,
            tabidentify: 'ver_1', // The tab groups identifier
            activetab_bg: '#fff', // background color for active tabs in this group
            inactive_bg: '#F5F5F5', // background color for inactive tabs in this group
            active_border_color: '#c1c1c1', // border color for active tabs heads in this group
            active_content_border_color: '#5AB1D0' // border color for active tabs contect in this group so that it matches the tab head border
        });

        //Vertical Tab
        $('#parentVerticalTab').easyResponsiveTabs({
            type: 'vertical', //Types: default, vertical, accordion
            width: 'auto', //auto or any width like 600px
            fit: true, // 100% fit in a container
            closed: 'accordion', // Start closed if in accordion view
            tabidentify: 'hor_1', // The tab groups identifier
            activate: function(event) { // Callback function if tab is switched
                var $tab = $(this);
                var $info = $('#nested-tabInfo2');
                var $name = $('span', $info);
                $name.text($tab.text());
                $info.show();
            }
        });
    });
</script>


