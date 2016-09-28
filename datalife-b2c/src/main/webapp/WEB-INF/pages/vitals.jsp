

<link type="text/css" rel="stylesheet" href="resources/css/app.min.css" />
<!--end here-->
<link rel="stylesheet" type="text/css" href="resources/css/site.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/dataTables.responsive.min.css">


<div class="wrapper">
<div class="form-container"  >
<div id="tmm-form-wizard" class="container substrate register-margin">
<div class="row clr form-login-heading">
    <div class="col-md-12 col-sm-12 no-pad"><h2 class="app_for_patients_cls">My Vitals</h2></div>

</div>
<div>

</div>
<div id="confirmMessage" class="hide"></div>
<div id="errorMessage" class="hide"></div>
<!--/ .row-->
<div class="row">
    <div class="col-md-8">
        <div class="vitals-date" id="todayDate"></div>
        <div class="note">Note : Graph shown only for values recorded by doctor.</div>
        <!--/ .form-header-->
    </div>
    <div class="col-md-4">
        <div class="form-header-demo-vitals">
            <input type="button" value="Save" class="saveb editbasicinfo-vitals" id="saveVitals">
            <input type="button" value="Recorded Vitals" class="saveb editbasicinfo-vitals recorded" id="viewVitals">

        </div>
    </div>
</div>
<!--/ .row-->
<div class="col-md-6 padding-for-vitals">
    <div class="row">
        <div class="col-md-12 no-pad vitals">
            <div class="col-md-12 border-line">
                <div class="col-md-6 vital-text"> Temperature </div>
                <div class="col-md-6 vital-graph ">
                    <input type="button" value="View Graph" class="saveb editbasicinfo-vitals-graph" id="graph_temp">
                </div>
            </div>
            <div class="col-md-12">

                <div class="vital_inputs">
                    <input type="text" title="Temperature " name="temp" id="editable_div" maxlength="5" pattern="^[\.\d]{2,5}"/>
                </div>
                <div class="vital-pateient-demo"><p>&#8457;</p></div>
            </div>

            <div class="col-md-12 padding">
                <div class="slider alert"></div>
            </div>
            <div id="st_temp"></div>

        </div>
    </div>

    <div class="row">
        <div class="col-md-12 no-pad vitals">
            <div class="col-md-12 border-line">
                <div class="col-md-6 vital-text"> Pulse Rate </div>
                <div class="col-md-6 vital-graph ">
                    <input type="button" value="View Graph" class="saveb editbasicinfo-vitals-graph" id="graph_pulse">
                </div>
            </div>
            <div class="col-md-12">

                <div class="vital_inputs">
                    <input type="text" title="Temperature " name="heartRate" id="editable_divs" maxlength="3" pattern="^[\d]{2,3}"/>
                </div>

                <div class="vital-pateient-demo"> Beats /min</div>
            </div>
            <div class="col-md-12 padding">
                <div class="slider nostylesliders pulse"></div>
            </div>
            <div id="st_pr"></div>

        </div>
    </div>
</div>



<div class="col-md-6 no-pad vitalsa">
    <div class="col-md-12 border-line">
        <div class="col-md-6 vital-text"> Blood Pressure </div>
        <div class="col-md-6 vital-graph ">
            <input type="button" value="View Graph" class="saveb editbasicinfo-vitals-graph" id="graph_bp" >
        </div>
    </div>
    <div class="col-md-12 pressure">

        <div class="vital_inputs">
            <input type="text" title="Temperature " id="editable_div-sys" maxlength="3" pattern="^[\d]{2,3}"/>
        </div>

        <div class="vital-pateient-demo">Systolic (mmHg) </div>
    </div>
    <div class="col-md-12 padding">
        <div class="slider pressure"></div>
    </div>

    <div class="col-md-12 pressure">

        <div class="vital_inputs">
            <input type="text" title="Temperature " id="editable_div-pressure" maxlength="3" pattern="^[\d]{2,3}"/>
        </div>

        <div class="vital-pateient-demo">Diastolic (mmHg)</div>
    </div>
    <div class="col-md-12 padding index">
        <div class="slider normal"></div>
    </div>
    <div id="st_bp"></div>

</div>
<!-- secong row"-->

<div class="col-md-6 no-pad vitals">
    <div class="col-md-12 border-line">
        <div class="col-md-6 vital-text"> Respiratory Rate </div>
        <div class="col-md-6 vital-graph ">
            <input type="button" value="View Graph" class="saveb editbasicinfo-vitals-graph" id="graph_resp" >
        </div>
    </div>
    <div class="col-md-12">

        <div class="vital_inputs">
            <input type="text" title="Temperature " id="editable_div-rate" name="respRate" maxlength="2" pattern="^[\d]{2}"/>
        </div>

        <div class="vital-pateient-demo">Breaths /min</div>
    </div>
    <div class="col-md-12 padding">
        <div class="slider nostylesliders rate-value"></div>
    </div>
    <div id="st_rr"></div>

</div>
<div class="col-md-6 no-pad vitalsa histroy">
    <div class="col-md-6 no-pad vitalsa demo page" style="height: 196px;">
        <div class="col-md-12 border-line">
            <div class="col-md-6 vital-text-for-height"> Height </div>

        </div>

        <div class="col-md-12" style="margin: 44px 0 0px">

            <div class="col-md-6 no-pad set-width">

                <div class="vital_inputs">
                    <input type="text" title="Temperature " id="editable_div-height" maxlength="1" pattern="^[\d]{1}"/>

                </div><div class="vital-pateient-demo">Ft</div>
                <div class="clear"></div>
                <div class="input_note">max: 8</div>
            </div>

            <div class="col-md-6 no-pad set-width">

                <div class="vital_inputs">
                    <input type="text" title="Temperature" value="0" id="editable_div-height-inches" maxlength="3" pattern="^[\.?\d]{1,3}" />

                </div><div class="vital-pateient-demo">In</div>
                <input type="hidden" id="vitals_height" name="height">
                <div class="clear"></div>
                <div class="input_note">max: 11</div>
            </div>

        </div>

        <div id="st_ht" style="max-width: 200px !important;"></div>
    </div>

    <div class="col-md-6 no-pad vitalsa demo " style="height: 196px;">
        <div class="col-md-12 border-line">
            <div class="col-md-6 vital-text-for-height"> Weight </div>
        </div>

        <div class="col-md-12" style="margin: 44px 0">

            <div class="vital_inputs">
                <input type="text" title="Temperature " id="editable_div-weight" name="weight" maxlength="6" pattern="^[\.\d]{2,6}"/>


            </div>
            <div class="vital-pateient-demo">Kg</div>
        </div>
        <div class="col-md-12 paddings">

        </div>
    </div>
</div>


<!--end-->

<!--3rd row-->

<div class="row no-space">
    <div class="col-md-6 no-pad vitals">
        <div class="col-md-12 border-line">
            <div class="col-md-6 vital-text">Random Blood Sugar </div>
            <div class="col-md-6 vital-graph ">
                <input type="button" value="View Graph" class="saveb editbasicinfo-vitals-graph" id="graph_sugar">
            </div>
        </div>
        <div class="col-md-12">

            <div class="vital_inputs">
                <input type="text" title="Temperature " id="editable_div-sugar" name="sugar" maxlength="3" pattern="^[\d]{2,3}"/>

            </div>
            <div class="vital-pateient-demo">mg/dL</div>
        </div>
        <div class="col-md-12 padding sugar">
            <div class="slider pass"></div>
        </div>
        <div id="st_sugar"></div>
    </div>
    <div class="col-md-6 no-pad vitalsa bmi">
        <div class="col-md-12 border-line">
            <div class="col-md-6 vital-text"> Auto Calculated BMI </div>
            <div class="col-md-6 vital-graph ">
                <input type="button" value="View Graph" class="saveb editbasicinfo-vitals-graph" id="graph_bmi" readonly>
            </div>
        </div>
        <div class="col-md-12">

            <div class="vital_inputs">
                <input type="text" title="Temperature " id="editable_div-bmi" name="bmi" readonly>

            </div>
            <div class="vital-pateient-demo">Kg/m2</div>
        </div>
        <div class="col-md-12 paddingdemo">
            <div class="slider demo pointer"></div>
        </div>
        <div id="st_bmi"></div>

    </div>
</div>


<!--end here-->
<!--/ .form-wizard-->
</div>
<!--/ .container-->
</div>
</div>
<div id="saveVitalsPopup">
    <div class="form-wrapper confirmAppForm class-smpopup" id="confirmAppForm">
        <div class="cancel-common"></div>
        <div class="confirmpadding" id="tmm-form-wizard">
            <div id="bodypsv">
                <div class="confirmappointemnt">Vital Records</div>
                <form method="post" id="vitalsForm">

                    <input type="hidden" name="dateString" value="" id="pop_date">
                    <input type="hidden" name="monitored"  id="pop_monitored">
                    <input type="hidden" name="user.userId"  id="pop_userId" value="">
                    <div>
                        <div><span class="boldvitals">Temperature  </span><span class="pop_vitals pop_temp"></span><input type="hidden" readonly name="temp" class="pop_vitals pop_temp"></div>
                        <div><span class="boldvitals">Blood Pressure  </span><span class="pop_vitals pop_bp"></span><input type="hidden" readonly name="bp"  class="pop_vitals pop_bp"></div>
                        <div><span class="boldvitals">Pulse Rate  </span><span class="pop_vitals pop_hr"></span><input type="hidden" readonly name="heartRate"  class="pop_vitals pop_hr"></div>
                        <div><span class="boldvitals">Respiratory Rate  </span><span class="pop_vitals pop_rr"></span><input type="hidden" readonly name="respRate"  class="pop_vitals pop_rr"></div>
                        <div><span class="boldvitals">Height </span><span class="pop_vitals pop_ht"></span><input type="hidden" readonly name="height"  class="pop_vitals pop_ht"></div>
                        <div><span class="boldvitals">Weight  </span><span class="pop_vitals pop_wt"></span><input type="hidden" readonly name="weight"  class="pop_vitals pop_wt"></div>
                        <div><span class="boldvitals">Calculated BMI  </span><span class="pop_vitals pop_bmi"></span><input type="hidden" readonly name="bmi"  class="pop_vitals pop_bmi"></div>
                        <div><span class="boldvitals">Random Blood Sugar  </span><span class="pop_vitals pop_rbs"></span><input type="hidden" readonly name="sugar"  class="pop_vitals pop_rbs"></div>
                    </div>

                    <div id="sharebuttons">
                        <button type="submit" id="confirmSaveVitals" class="saveb editbasicinfo-vitals submit_btn">Confirm</button>

                    </div>

                </form>
            </div>

        </div>
    </div>
</div>


<div id="vitalsdisplay">
    <div class="form-wrapper confirmAppForm class-popup" id="confirmAppForm" style="max-width:95%;">

        <div class="cancel-common"></div>
        <div class="confirmpadding" id="tmm-form-wizard">
            <div class="form-wrapper">
                <div class="confirmappointemnt" style="text-align:center">
                    Vital Records
                </div>
                <div class=" splitcode">
                    <ul class="todayappointmentul">
                        <li>
                            <div class="searchalign">
                                <div class="search-doc show_border_clinic"  id="patient_Vt"><i class="icon-search-1"></i>Recorded by Patient</div>

                            </div>
                        </li>
                        <li>
                            <div class="searchalign">
                                <div class="ad-doc" id="doctor_Vt"><i class="icon-plus-circled" ></i>Recorded by Doctor</div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div id="patient_list_show" class="hidden-status">
                    <div id="patient_list_view">
                        <div class="background-for-list-space classwrap">

                            <table id="vitalstable" class="display"  cellpadding="0" cellspacing="0" width="100%">
                                <thead>
                                <tr>
                                    <th>Date<P>(dd/mm/yyyy hh:mm)</P></th>
                                    <th>Temp<p>(&#8457;)</p></th>
                                    <th>BP <p>(mmHg)</p></th>
                                    <th>Resp. Rate<p>(beats/min)</p></th>
                                    <th>Pulse Rate<p>(breaths/min)</p></th>
                                    <th>Height <p>(ft /inch)</p></th>
                                    <th>Weight  <p>(Kg)</p></th>
                                    <th>BMI <p>(Kg/m2)</p></th>
                                    <th>RBS <p>(mg/dL)</p></th>
                                </tr>
                                </thead>

                                <tbody id="vitalstbody">


                                </tbody>
                            </table>
                        </div>

                    </div>


                </div>
            </div>
        </div>

    </div>


</div>




<div id="graphDisplay">

    <div class="form-wrapper confirmAppForm class-smpopup" id="confirmAppForm" style="max-width: 700px !important;">
        <div class="cancel-common"></div>

        <div class="confirmpadding" id="tmm-form-wizard">
            <div class="confirmappointemnt">
                Vital Graph
            </div>
            <div id="chartContainer" style="height: 350px; width:100%;"></div>

        </div></div></div>


<script type="text/javascript" src="resources/jsplugins/jquery-ui.js"></script>
<script type="text/javascript" src="resources/jsplugins/jquery-ui-slider-pips.js"></script>
<!--graph plugins-->
<script type="text/javascript" src="resources/js/server/vitals.js"></script>
<script type="text/javascript" src="resources/jsplugins/site.js"></script>
<script type="text/javascript" src="resources/jsplugins/dataTables.responsive.min.js"></script>
<script type="text/javascript" src="resources/jsplugins/jquery.canvasjs.min.js"></script>





