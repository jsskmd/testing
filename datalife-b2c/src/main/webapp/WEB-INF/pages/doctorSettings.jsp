<script type="text/javascript" src="resources/js/server/settings.js"></script>

<div class="wrapper">
<div class="form-container">
<div id="tmm-form-wizard" class="container substrate register-margin" >
<div class="row">
    <div class="col-xs-12">
        <h2 class="form-login-heading">Settings </h2>
    </div>

</div>

<!--/ .row-->
<!--/ .form-wizard-->

<div> <%--background-color-for-doctor-slot--%>
<div class="background-for-list">

<div id="change_settings" class="hide">
<form method="post" name="clinicDoctors" id="doctorTimings">

<input name="doctorId" id="cdtdoctorId" type="hidden" value=""/>
<input name="clinicId" id="cdtclinicId" type="hidden" value=""/>
<input  id="timingserror1" type="hidden" value=""/>
<input  id="timingserror2" type="hidden" value=""/>
<input  id="timingserror3" type="hidden" value=""/>

<div >
    <div class="col-md-8 col-sm-8 no-pad">
        <div class="form-header">
            <div class="form-title slots"><b>Professional Information</b></div>
        </div>
        <!--/ .form-header-->
    </div>
    <div class="col-md-4 col-sm-8">
        <div class="form-header-demo">
            <input type="button" class="saveb editbasicinfo cancelbtns" value="Cancel" id="cancelClinicSettings">
            <button type="submit" class="saveb editbasicinfo-vitals submit_btn" id="saveClinicSettings">Save</button>

        </div>
    </div>
    <div class="clearfix"></div>
</div>

<div >
    <div class="">
        <div>
            <div class="col-md-6 no-padding">
                <div class="col-md-6 col-sm-6 no-pad ">
                    <fieldset class="input-block">
                        <label>Specialty</label>

                        <div class="dropdown">
                            <select class="dropdown-select" name="specialityId" id="autocomplete_speciality"
                                    title="Specialization"></select>
                            <!--/ .tooltip-->
                        </div>
                    </fieldset>
                </div>
                <div class="col-md-6 col-sm-6 no-pad ">
                    <div class="col-md-6 col-sm-6 no-pad ">

                        <fieldset class="input-block">
                            <label>Experience<span style="color:red;">*</span></label>
                            <input type="text" required title="Experience"  name="experience" placeholder="Experience"
                                   id="cpexperience" maxlength="2" pattern="^[\d]{1,2}"/>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad ">
                        <fieldset class="input-block">
                            <label>Time(in min.)<span style="color:red;">*</span></label>
                            <input type="text" title="Slot Time"  name="slotTime" placeholder="Slot Time"
                                   id="cpslotTime" required>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>

                </div>
                <div class="clearfix"></div>
            </div>
            <div class="col-md-6  no-padding">

                <div class="col-md-6 col-sm-6 no-pad ">
                    <div class="col-md-6 col-sm-6 no-pad ">
                        <fieldset class="input-block">
                            <label>Consultation Fee<span style="color:red;">*</span></label>
                            <input type="text" title="Fee " required="" placeholder="Fee" id="consultationFeeId" maxlength="30" pattern="^[\d]{2,30}"/>
                            <!--/ .dropdown-->
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad ">
                        <fieldset class="input-block">
                            <label>Currency</label>

                            <div class="dropdown">
                                <select class="dropdown-select" id="currencyId">
                                    <option value="" disabled selected>Select</option>
                                    <option value="USD">USD</option>
                                    <option value="CAD">CAD</option>
                                    <option value="INR" selected>INR</option>
                                </select>
                                <input type="hidden" name="consultationFee" id="cpconsultationFee">
                            </div>
                            <!--/ .dropdown-->
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>

            </div>
        </div>


    </div>
</div>
<div class="clearfix"></div>

<div >
    <div class="col-md-9 no-pad">
        <div class="form-header">
            <div class="form-title slots"><b> Time Settings</b></div>
        </div>
        <!--/ .form-header-->
    </div>
    <div class="col-md-3 form-header-demo">
        <a class="add-more-slots" id="addMoreSlots">Add More Slots</a></div>
    <div class="clearfix"></div>
</div>

<div id="mutipleSlots">
<input type="hidden" id="count" value="1"/>
<input type="hidden" id="slotCount" value="1"/>
<div id="singleSlot1">
<div class="slots">

<div >

    <div class="col-md-3 col-sm-3 no-pad ">

        <fieldset class="input-block">
            <label>Effect from<span style="color:red;"></span></label>
            <input type="text" readonly required="" id="changeSetEfectFrm" title="Effect from" name="date" placeholder="PickDate">
            <!--/ .tooltip-->
        </fieldset>
    </div>
    <div class="clearfix"></div>
</div>
<div class="clearfix"></div>

<div >
<div class="col-md-12 no-pad seesionslots">
<div class="commonslot">
    <div class="col-md-6 no-pad">
        <%--
                    <div class="col-md-12 session1">  <div class="col-md-6 session1">Session 1  </div>   <div class="col-md-6 session1">Session 2 </div> </div>
        --%>
        <div class="col-md-6  values marginbtm-20">
            <h4 class="seesions">Session 1</h4>
            <div class="col-md-6  col-sm-12 col-xs-3 no-pad issues">
                <label>From</label>
                <div class="dropdown">
                    <select class="dropdown-select demo-page st_1">
                        <option value="" >Select</option>
                        <option value="600" >06:00 AM</option>
                        <option value="630" >06:30 AM</option>
                        <option value="700" >07:00 AM</option>
                        <option value="730" >07:30 AM</option>
                        <option value="800">08:00 AM</option>
                        <option value="830">08:30 AM</option>
                        <option value="900">09:00 AM</option>
                        <option value="930">09:30 AM</option>
                        <option value="1000" >10:00 AM</option>
                        <option value="1030" >10:30 AM</option>
                        <option value="1100" >11:00 AM</option>
                        <option value="1130" >11:30 AM</option>
                        <option value="1200">12:00 PM</option>
                        <option value="1230">12:30 PM</option>
                        <option value="1300">01:00 PM</option>
                        <option value="1330">01:30 PM</option>
                        <option value="1400">02:00 PM</option>
                        <option value="1430">02:30 PM</option>
                        <option value="1500">03:00 PM</option>
                        <option value="1530">03:30 PM</option>
                        <option value="1600">04:00 PM</option>
                        <option value="1630">04:30 PM</option>
                        <option value="1700">05:00 PM</option>
                        <option value="1730">05:30 PM</option>
                        <option value="1800">06:00 PM</option>
                        <option value="1830">06:30 PM</option>
                        <option value="1900">07:00 PM</option>
                        <option value="1930">07:30 PM</option>
                        <option value="2000">08:00 PM</option>
                        <option value="2030">08:30 PM</option>
                        <option value="2100">09:00 PM</option>
                        <option value="2130">09:30 PM</option>
                        <option value="2200">10:00 PM</option>
                        <option value="2230">10:30 PM</option>
                        <option value="2300">11:00 PM</option>
                        <option value="2330">11:30 PM</option>

                    </select>
                </div>
            </div>
            <div class="col-md-6 col-sm-12 col-xs-3  no-pad issues ">
                <label>To</label>
                <div class="dropdown">
                    <select class="dropdown-select demo-page et_1">
                        <option value="" >Select</option>
                        <option value="600" >06:00 AM</option>
                        <option value="630" >06:30 AM</option>
                        <option value="700" >07:00 AM</option>
                        <option value="730" >07:30 AM</option>
                        <option value="800">08:00 AM</option>
                        <option value="830">08:30 AM</option>
                        <option value="900">09:00 AM</option>
                        <option value="930">09:30 AM</option>
                        <option value="1000" >10:00 AM</option>
                        <option value="1030" >10:30 AM</option>
                        <option value="1100" >11:00 AM</option>
                        <option value="1130" >11:30 AM</option>
                        <option value="1200">12:00 PM</option>
                        <option value="1230">12:30 PM</option>
                        <option value="1300">01:00 PM</option>
                        <option value="1330">01:30 PM</option>
                        <option value="1400">02:00 PM</option>
                        <option value="1430">02:30 PM</option>
                        <option value="1500">03:00 PM</option>
                        <option value="1530">03:30 PM</option>
                        <option value="1600">04:00 PM</option>
                        <option value="1630">04:30 PM</option>
                        <option value="1700">05:00 PM</option>
                        <option value="1730">05:30 PM</option>
                        <option value="1800">06:00 PM</option>
                        <option value="1830">06:30 PM</option>
                        <option value="1900">07:00 PM</option>
                        <option value="1930">07:30 PM</option>
                        <option value="2000">08:00 PM</option>
                        <option value="2030">08:30 PM</option>
                        <option value="2100">09:00 PM</option>
                        <option value="2130">09:30 PM</option>
                        <option value="2200">10:00 PM</option>
                        <option value="2230">10:30 PM</option>
                        <option value="2300">11:00 PM</option>
                        <option value="2330">11:30 PM</option>
                    </select>
                </div>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="col-md-6  marginbtm-20">
            <h4 class="seesions">Session 2</h4>
            <div class="col-md-6 col-sm-12 col-xs-3  issues no-pad ">
                <label>From</label>
                <div class="dropdown">
                    <select class="dropdown-select demo-page st_2">
                        <option value="" >Select</option>
                        <option value="600" >06:00 AM</option>
                        <option value="630" >06:30 AM</option>
                        <option value="700" >07:00 AM</option>
                        <option value="730" >07:30 AM</option>
                        <option value="800">08:00 AM</option>
                        <option value="830">08:30 AM</option>
                        <option value="900">09:00 AM</option>
                        <option value="930">09:30 AM</option>
                        <option value="1000" >10:00 AM</option>
                        <option value="1030" >10:30 AM</option>
                        <option value="1100" >11:00 AM</option>
                        <option value="1130" >11:30 AM</option>
                        <option value="1200">12:00 PM</option>
                        <option value="1230">12:30 PM</option>
                        <option value="1300">01:00 PM</option>
                        <option value="1330">01:30 PM</option>
                        <option value="1400">02:00 PM</option>
                        <option value="1430">02:30 PM</option>
                        <option value="1500">03:00 PM</option>
                        <option value="1530">03:30 PM</option>
                        <option value="1600">04:00 PM</option>
                        <option value="1630">04:30 PM</option>
                        <option value="1700">05:00 PM</option>
                        <option value="1730">05:30 PM</option>
                        <option value="1800">06:00 PM</option>
                        <option value="1830">06:30 PM</option>
                        <option value="1900">07:00 PM</option>
                        <option value="1930">07:30 PM</option>
                        <option value="2000">08:00 PM</option>
                        <option value="2030">08:30 PM</option>
                        <option value="2100">09:00 PM</option>
                        <option value="2130">09:30 PM</option>
                        <option value="2200">10:00 PM</option>
                        <option value="2230">10:30 PM</option>
                        <option value="2300">11:00 PM</option>
                        <option value="2330">11:30 PM</option>

                    </select>
                </div>
            </div>
            <div class="col-md-6   col-sm-12 col-xs-3  no-pad issues ">
                <label>To</label>
                <div class="dropdown">
                    <select class="dropdown-select demo-page et_2">
                        <option value="" >Select</option>
                        <option value="600" >06:00 AM</option>
                        <option value="630" >06:30 AM</option>
                        <option value="700" >07:00 AM</option>
                        <option value="730" >07:30 AM</option>
                        <option value="800">08:00 AM</option>
                        <option value="830">08:30 AM</option>
                        <option value="900">09:00 AM</option>
                        <option value="930">09:30 AM</option>
                        <option value="1000" >10:00 AM</option>
                        <option value="1030" >10:30 AM</option>
                        <option value="1100" >11:00 AM</option>
                        <option value="1130" >11:30 AM</option>
                        <option value="1200">12:00 PM</option>
                        <option value="1230">12:30 PM</option>
                        <option value="1300">01:00 PM</option>
                        <option value="1330">01:30 PM</option>
                        <option value="1400">02:00 PM</option>
                        <option value="1430">02:30 PM</option>
                        <option value="1500">03:00 PM</option>
                        <option value="1530">03:30 PM</option>
                        <option value="1600">04:00 PM</option>
                        <option value="1630">04:30 PM</option>
                        <option value="1700">05:00 PM</option>
                        <option value="1730">05:30 PM</option>
                        <option value="1800">06:00 PM</option>
                        <option value="1830">06:30 PM</option>
                        <option value="1900">07:00 PM</option>
                        <option value="1930">07:30 PM</option>
                        <option value="2000">08:00 PM</option>
                        <option value="2030">08:30 PM</option>
                        <option value="2100">09:00 PM</option>
                        <option value="2130">09:30 PM</option>
                        <option value="2200">10:00 PM</option>
                        <option value="2230">10:30 PM</option>
                        <option value="2300">11:00 PM</option>
                        <option value="2330">11:30 PM</option>

                    </select>
                </div>
            </div>
            <div class="clearfix"></div>

        </div>

    </div>
</div>

<div class="col-md-6">
    <div class="col-md-12 session1">  <div class="col-md-12 session1">Select Day </div>  </div>


    <div class="strike-pad-info">
        <div class="row">
            <div class="label-demo-class days">

                <label class="demo-dates"><input type="checkbox" id="mon" value="1"/> Mon</label>
                <label class="demo-dates"><input type="checkbox"  id="tue" value="1"/> Tue</label>
                <label class="demo-dates"><input type="checkbox"  id="wed" value="1"/> Wed</label>
                <label class="demo-dates"><input type="checkbox"  id="thu" value="1"/> Thu</label>
                <label class="demo-dates"><input type="checkbox" id="fri" value="1"/> Fri</label>
                <label class="demo-dates"><input type="checkbox"  id="sat" value="1"/> Sat</label>
                <label class="demo-dates"><input type="checkbox"  id="sun" value="1"/> Sun</label>
            </div>
        </div>
    </div>
</div>


</div>


</div>

</div>
</div>
</div>
<div>
    <div class="week-slot">
        <div>
            <h4 class="timings_head">Current time settings</h4>
            <table class="dtimings" style="display: none">
                <thead>
                <tr>  <th>Day</th>
                    <th>Time</th></tr>

                </thead>
                <tbody class="t_timings"></tbody>
            </table>
            <div class="timings_message"></div>
        </div>
        <div class="clearfix"></div>
        <div class="newTimings_div"  style="display: none">
            <h4 class="timings_head">Below timings will affet from <span class="effectedDate"></span></h4>
            <table class="dnewtimings">
                <thead>
                <tr>  <th>Day</th>
                    <th>Time</th></tr>

                </thead>
                <tbody class="t_newtimings"></tbody>
            </table>
        </div>
    </div>
</div>
<input type="hidden" name="monst_1" id="monst_1">
<input type="hidden" name="monet_1" id="monet_1">
<input type="hidden" name="monst_2" id="monst_2">
<input type="hidden" name="monet_2" id="monet_2">
<input type="hidden" name="monStatus_1" id="monStatus_1" value="false">

<input type="hidden" name="tuest_1" id="tuest_1">
<input type="hidden" name="tueet_1" id="tueet_1">
<input type="hidden" name="tuest_2" id="tuest_2">
<input type="hidden" name="tueet_2" id="tueet_2">
<input type="hidden" name="tueStatus_1" id="tueStatus_1" value="false">

<input type="hidden" name="wedst_1" id="wedst_1">
<input type="hidden" name="wedet_1" id="wedet_1">
<input type="hidden" name="wedst_2" id="wedst_2">
<input type="hidden" name="wedet_2" id="wedet_2">
<input type="hidden" name="wedStatus_1" id="wedStatus_1" value="false">


<input type="hidden" name="thust_1" id="thust_1">
<input type="hidden" name="thuet_1" id="thuet_1">
<input type="hidden" name="thust_2" id="thust_2">
<input type="hidden" name="thuet_2" id="thuet_2">
<input type="hidden" name="thuStatus_1" id="thuStatus_1" value="false">


<input type="hidden" name="frist_1" id="frist_1">
<input type="hidden" name="friet_1" id="friet_1">
<input type="hidden" name="frist_2" id="frist_2">
<input type="hidden" name="friet_2" id="friet_2">
<input type="hidden" name="friStatus_1" id="friStatus_1" value="false">


<input type="hidden" name="satst_1" id="satst_1">
<input type="hidden" name="satet_1" id="satet_1">
<input type="hidden" name="satst_2" id="satst_2">
<input type="hidden" name="satet_2" id="satet_2">
<input type="hidden" name="satStatus_1" id="satStatus_1" value="false">


<input type="hidden" name="sunst_1" id="sunst_1">
<input type="hidden" name="sunet_1" id="sunet_1">
<input type="hidden" name="sunst_2" id="sunst_2">
<input type="hidden" name="sunet_2" id="sunet_2">
<input type="hidden" name="sunStatus_1" id="sunStatus_1" value="false">


</form>

</div>
<div id="show_settings">

    <div>
        <div class="col-md-8 col-sm-8 no-pad">
            <div class="form-header">
                <div class="form-title slots"><b> Professional Information</b></div>
            </div>
            <!--/ .form-header-->
        </div>
        <div class="col-md-4 col-sm-4">
            <div class="form-header-demo">
                <input type="button" class="saveb editbasicinfo" value="Change" id="changeClinicSettings">

            </div>
        </div>
        <div class="clearfix"></div>
    </div>
    <div >
        <div class="row">
            <div class="col-md-12 col-sm-12">
                <div class="data-container">
                    <dl>
                        <dt>Specialty</dt>
                        :
                        <dd id="t_speciality"></dd>
                    </dl>

                    <dl>
                        <dt>Experience</dt>
                        :
                        <dd id="t_experience"></dd>
                    </dl>
                    <dl>
                        <dt>Slot Time</dt>
                        :
                        <dd id="t_slotTime"></dd>
                    </dl>
                    <dl>
                        <dt>Consultation fee</dt>
                        :
                        <dd id="t_consulationFee"></dd>
                    </dl>

                </div>
                <!--/ .data-container-->
            </div>
        </div>
        <!--/ .row-->
    </div>
    <div>
        <div class="col-md-12 no-pad ">
            <div >
                <div class="form-title slots"><b> Time Settings</b></div>
            </div>
            <div class="clearfix"></div>
            <!--/ .form-header-->
        </div>
        <div class="clearfix"></div>
    </div>
    <div >
        <div class="week-slot">
            <div class="col-md-12 no-pad">
                <h4 class="timings_head">Current time settings</h4>
                <table class="dtimings" style="display: none">
                    <thead>
                    <tr>  <th>Day</th>
                        <th>Time</th></tr>

                    </thead>
                    <tbody class="t_timings"></tbody>
                </table>
                <div class="timings_message"></div>
            </div>
            <div class="clearfix"></div>
            <div class="newTimings_div"  style="display: none">
                <h4 class="timings_head">Below timings will affet from <span class="effectedDate"></span></h4>
                <table class="dnewtimings">
                    <thead>
                    <tr>  <th>Day</th>
                        <th>Time</th></tr>

                    </thead>
                    <tbody class="t_newtimings"></tbody>
                </table>
            </div>
        </div>
    </div>

</div>


</div>
</div>


<!--/ .form-wizard-->
</div>

<!--/ .container-->
</div>
</div>

<div id="apptCancelCnfmPopup">
    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-smpopup">
        <div class="cancel-close"></div>
        <div class="confirmappointemnt">DataLife Confirmation</div>
        <div class="confirmpadding">
            <div id="bodypsv " class="dywcpopup" style="padding: 15px !important;">
                <div id="anyNote"></div>
                <div class="note">Do you want to continue?
                </div>
                <button type="button" class="cancelapp View" id="Yes">Yes</button>
                <button type="button" class="cancelapp share" id="No">No</button>
            </div>
        </div>
    </div>

</div>


