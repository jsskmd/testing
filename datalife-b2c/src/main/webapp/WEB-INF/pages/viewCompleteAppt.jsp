<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 9/14/2015
  Time: 4:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="resources/css/site.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/dataTables.responsive.min.css">
<link rel="stylesheet" href="resources/css/jquery.datetimepicker.min.css">

<script type="text/javascript" src="resources/jsplugins/site.js"></script>
<script type="text/javascript" src="resources/jsplugins/dataTables.responsive.min.js"></script>
<script type="text/javascript" src="resources/js/server/consultation.js"></script>
<%--<script type="text/javascript" src="resources/js/server/consultationUtils.js"></script>--%>
<script type="text/javascript" src="resources/jsplugins/jquery.datetimepicker.min.js"></script>
<script type="text/javascript" src="resources/js/server/clinicDoctAppt.js"></script>

<div class="bodycontent background-color-for-slot">
<div class="wrapper">
    <div class="form-container">
        <div id="tmm-form-wizard" class="container substrate register-margin ">

            <div class="clr form-login-heading">
                <div class="col-md-6 col-sm-6  no-pad">
                    <h2 class="form-login-heading heding-list-for-db"
                        style="border: none; margin:0px; padding:0; border-radius:0px; background: none">
                        Appointments</h2>
                </div>
                <div class="col-md-6 col-sm-6 " id="docCancelSetting" style="display: none">
                    <button id="viewDocCnclSetting" class="search-patients cancel-appointments-list" type="button"> Cancel Appointments
                    </button>
                    <div class="clear"></div>
                </div>
                <div class="clear"></div>
            </div>
            <div class=" border-todayappointment">

                <ul class="padding-list" style="width:100%;">
                    <input type="hidden" id="selectedDoct" value="">
                    <input type="hidden" id="curDate" value="">
                    <input type="hidden" id="serverDate" value="">
                    <input type="hidden" id="maxDateToShow" value="">
                    <input type="hidden" id="pastDateToShow" value="">
                    <input type="hidden" id="isFromDocLogin" value="">
                    <li class="padding-list-icon" style="float:left;width:50%;"><span class="name-info-app" id="displayDocName"></span></li>
                    <li class="padding-list-icon" style="float: right;width:50%;">
                        <a href="/emr"><button class="search-patients" type="button" style="background: #248c6b;color:#fff !important;">Back</button></a>
                    </li>
                    <div class="clearfix"></div>
                </ul>
                <div class="clearfix"></div>

            </div>
            <div >


                <div class="col-md-6 col-sm-12   no-padding">
                    <ul class="todayappointmentul">
                        <li class="searchalign">
                            <div id="curAppt" class="show_border_clinic futureappt"><i class="icon-calendar-empty"></i> Today's
                            </div>
                        </li>
                        <li class="searchalign">
                            <div id="futAppt" class="futureappt"><i class="icon-right"></i> Next <i class="icon-calendar-empty"></i>
                            </div>
                        </li>
                        <li class="searchalign">
                            <div id="pastAppt" class="futureappt"> <i class="icon-calendar-empty"></i> Previous <i class="icon-left"></i>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="col-md-6 col-sm-12 no-padding on-demand">

                    <div id="onDemand" style="display: none">
                        <fieldset class="input-block ondemandflot">
                            <label>On Demand Book Appointment</label>

                            <div class="dropdown">
                                <select class="dropdown-select" id="onDemandList">
                                </select>
                            </div>
                        </fieldset>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <div id="msg"></div>
            <div class="" style="background:#fff;padding:0 12px !important;">
                <div class="">
                    <div >
                        <div id="curApptSlot">
                            <div >

                                <div class=" doctor-info" style="border:0">
                                    <div class="today-appointment">
                                        <div> Today's Appointments ( <span id="today_date"></span> ) </div>

                                    </div>
                                    <table id="todayAppttable" class="display responsive nowrap" cellspacing="0"
                                           style="width:100%;">
                                        <thead>
                                        <tr>
                                            <th style="text-align:center">Token No</th>
                                            <th style="text-align:center"> Patient Details</th>
                                            <th style="text-align:center"> Status/Time</th>
                                            <th style="text-align:center;" class="schedule">Scheduled</th>
                                            <th style="text-align:center;" class="arri">Arrived</th>
                                            <th style="text-align:center;" class="progress-page">In Progress</th>
                                            <th style="text-align:center;" class="complete">Completed</th>
                                        </tr>
                                        </thead>
                                        <tbody id="todayAppt">


                                        </tbody>
                                    </table>
                                </div>
                            </div>

                        </div>
                        <div id="futApptSlot" class="hide">
                            <div class="futpastmsg" id="futmsg"></div>
                            <div class="row">
                                <div class="col-md-5  ">
                                    <div class="col-md-6 col-sm-6 no-pad ">
                                        <fieldset class="input-block">
                                            <label>From Date </label>
                                            <input type="text" id="futFrmDatepicker" title=" From date"
                                                   placeholder="From Date" class="hasDatepicker">
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                    <div class="col-md-6 col-sm-6 no-pad ">
                                        <fieldset class="input-block">
                                            <label>To Date</label>
                                            <input type="text" class="hasDatepicker" placeholder="To Date"
                                                   title=" To date  " id="furToDatepicker">
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                </div>
                                <div class="col-md-4 ">
                                    <div class="col-md-6 col-sm-6 no-pad ">
                                        <fieldset class="input-block">
                                            <label>Select Status</label>
                                            <div class="dropdown">
                                                <select class="dropdown-select" id="futureStatusList">
                                                    <option value="0" selected="selected" disabled="">Select
                                                        Status
                                                    </option>
                                                    <option value="9">All Status</option>
                                                    <option value="4">Scheduled</option>
                                                </select>
                                            </div>
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                            <div class="content-index-demo" style="display: block">
                                <div class="full-time-slots" id="showFutureSlot">

                                </div>
                            </div>
                        </div>
                        <div id="pastApptSlot" class="hide">
                            <div class="futpastmsg" id="pastmsg"></div>
                            <div class="row">
                                <div class="col-md-5  ">
                                    <div class="col-md-6 col-sm-6 no-pad ">
                                        <fieldset class="input-block">
                                            <label>From Date </label>
                                            <input type="text" class="hasDatepicker" title=" From date"
                                                   placeholder="From Date" id="pastFrmDatepicker">
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                    <div class="col-md-6 col-sm-6 no-pad">
                                        <fieldset class="input-block">
                                            <label>To Date</label>
                                            <input type="text" class="hasDatepicker" placeholder="To Date"
                                                   title=" To date  " id="pastToDatepicker">
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                </div>
                                <div class="col-md-4 ">
                                    <div class="col-md-6 col-sm-6 no-pad ">
                                        <fieldset class="input-block">
                                            <label>Select Status</label>

                                            <div class="dropdown">
                                                <select class="dropdown-select" id="pastStatusList">
                                                    <option value="0" selected="selected" disabled="">Select
                                                        Status
                                                    </option>
                                                    <option value="9">All Status</option>
                                                    <option value="6">NoShow</option>
                                                    <option value="7">Completed</option>
                                                </select>
                                            </div>
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                            <div class="content-index-demo" style="display: block">
                                <div class="full-time-slots" id="showPastSlot">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<div id="appointmentclinic">
<div class="form-wrapper confirmAppForm" id="clinicpagedisc" style="display: block;">
<div class="cancel-close1-clinic"></div>
<div class="confirmpadding" id="tmm-form-wizard">

<div class="note"></div>
<div id="askForPDetailsPop">
    <div class="form-wrapper">
        <div class="confirmappointemnt">
            Fix Appointment
        </div>
        <div class=" splitcode">
            <ul class="todayappointmentul newappointtodays " style="text-align:center;">
                <li>
                    <div class=" searchalign">
                        <div class="search-doc show_border_clinic futureappt" id="apptsearch_patient"><i class="icon-search-1"></i>
                            Patient Search
                        </div>
                    </div>
                </li>
                <%--<li>
                    <div class="searchalign">
                        <div class="ad-doc" id="apptadd_patient"><i class="icon-plus-circled"></i>Register New Patient</div>
                    </div>
                </li>--%>
            </ul>
        </div>
        <div class="clinic-app" id="PDetailsPopUp">
            <form id="checkPatientExist" method="post" name="confirmAppointment">
                <div id="confirmMessage" class="hide"></div>
                <div class="basicdetails-modal add_class_for_popup">
                    <div class="row">
                        <div class="col-md-6 col-sm-6 no-pad ">
                            <fieldset class="input-block">
                                <label class="select-records">DataLife Id</label>
                                <input type="text" placeholder="User Id" title=" User Id " class="hasDatepicker"
                                       id="pUid">
                                <!--/ .dropdown-->
                            </fieldset>
                        </div>
                        <div class="col-md-6 no-pad">

                            <fieldset class="input-block">
                                <label class="select-records">User Name<span style="color:red;">*</span></label>
                                <input type="text" placeholder=" User Name" required="" title=" User Name "
                                       class="hasDatepicker" id="pUsrName">
                            </fieldset>
                        </div>
                        <!--/ .tooltip-->
                    </div>
                </div>

                <div class="input-wrapper space">

                    <div class="form-right">

                        <div class="form-right-right confirmgreen">
                            <a class="sharereportbtn greenbtn">Get Details</a>
                        </div>
                    </div>
                </div>
            </form>
            <div class="clear"></div>
        </div>
        <div class="clear"></div>

        <div class="clear"></div>
        <div class="clinic-app hide" id="apptregisterPatient">

            <form method="post" name="user" id="apptclinicPatientForm">
                <input type="hidden" name="role" id="userRole" value="ROLE_PATIENT">
                <input type="hidden" id="clinicIdInput" name="clinicId" value="">
                <input type="hidden" id="doctorIdInput" name="doctorId" value="">
                <div class="form-wizard add_class_for_popup">
                    <div class="row">

                        <div class="col-md-6 no-pad ">
                            <fieldset class="input-block">
                                <label class="select-records"> First Name<span style="color:red;">*</span></label>
                                <input type="text" placeholder="First Name" required="" name="userDetails.firstName"
                                       maxlength="20" pattern="^[a-zA-Z ]+$" title=" First Name">

                            </fieldset>
                        </div>
                        <div class="col-md-6 no-pad">

                            <fieldset class="input-block">
                                <label class="select-records">Last Name<span style="color:red;">*</span></label>
                                <input type="text" placeholder=" Last Name" required="" name="userDetails.lastName"
                                       maxlength="20"  pattern="^[a-zA-Z ]+$" title=" Last Name " class="">
                            </fieldset>
                        </div>

                        <div class="col-md-6 no-pad">

                            <fieldset class="input-block">
                                <label class="select-records">User Name<span style="color:red;">*</span></label>
                                <input type="text" placeholder=" User Name" required="" id="userName"
                                       pattern="^[a-zA-Z0-9]+$" name="userName" title=" User Name "
                                       onblur="checkUserExistsAllReady(this.value);">
                            </fieldset>
                        </div>

                        <div class="col-md-6 no-pad ">
                            <fieldset class="input-block">
                                <label class="select-records"> Mobile Number<span style="color:red;">*</span></label>
                                <input id="phone" type="tel" name="userContactInfo.mobilePhone"
                                       placeholder="Mobile Number" required=""
                                       maxlength="13" pattern="^[\+?\d]{10,13}"/>

                            </fieldset>
                        </div>

                        <div class="col-md-12 no-pad ">
                            <fieldset class="input-block">
                                <label class="select-records"> Email<span style="color:red;">*</span></label>
                                <input type="email" placeholder="Email" required="" title="Email " id="email"
                                       class="" name="userContactInfo.email">

                            </fieldset>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="btn-static"><a class="">
                            <button type="submit" id="submit_clinicDoctors" class="register-btn">Save</button>
                        </a></div>
                    </div>
                </div>
            </form>


            <div class="clear"></div>
        </div>
        <div class=" splitcode">
            <ul class="todayappointmentul newappointtodays " style="text-align:center;">
                <%--<li>
                    <div class=" searchalign">
                        <div class="search-doc show_border_clinic" id="apptsearch_patient"><i class="icon-search-1"></i>
                            Patient Search
                        </div>
                    </div>
                </li>--%>
                <li>
                    <div class="searchalign">
                        <div class="ad-doc futureappt" id="apptadd_patient"><i class="icon-plus-circled"></i>Register New Patient</div>
                    </div>
                </li>
            </ul>
        </div>
    </div>

</div>
<div class="full-width hideall" id="patientDetailPopUp">
    <div class="form-wrapper">
        <div class="confirmappointemnt-for-app">
            Confirm Appointment
        </div>
        <div>
            <form name="confirmAppointment" method="post" id="confirmAppt" enctype="application/json">
                <input type="hidden" id="selectedDate" name="date" value="">
                <input type="hidden" id="selectedTime" name="time" value="">
                <input type="hidden" id="patId" name="patientId" value="">
                <input type="hidden" id="docId" name="doctorId" value="">
                <input type="hidden" id="clncId" name="clinicId" value="">
                <input type="hidden" id="tokenNo" name="tokenNo" value="">
                <input type="hidden" id="selectedLislot" value="">
                <input type="hidden" id="isFromCurDate" value="">

                <div class="basicdetails-modal form-wizard stnc">
                    <div class="doctordetails-modal ">
                        <div class="dcolumn-3">
                            <div class=""><img alt="clinic-image" id="patientImage"></div>
                            <ul class="padding-list">
                                <li class="padding-list-icon"><span class="id-for-pateints block_item">Patient ID</span><span
                                        class="name-info"> : </span><span class="class-for-demo-patient"
                                                                          id="patientId"></span></li>
                                <li class="padding-list-icon"><span class="id-for-pateints block_item">Name</span> <span
                                        class="name-info-class"> : </span><span class="class-for-demo-patient"
                                                                                id="patName"> </span></li>
                                <li class="padding-list-icon"><span class="id-for-pateints block_item">Mobile Number</span><span
                                        class="name-info-index"> : </span><span class="class-for-demo-patient"
                                                                                id="patPhNo"></span></li>
                                <li class="padding-list-icon"><span class="id-for-pateints block_item">Email Id</span> <span
                                        class="name-info-index-for-gmail"> : </span><span class="class-for-demo-patient"
                                                                                          id="patEmail"></span></li>
                            </ul>

                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
                <div class="basicdetails-modal scheduledtimes">
                    <div class="doctordetails-modal ">
                        <div class="dcolumn-3">
                            <p class="doctorqulifation timeconfirmapptoiment"><span id="patSelectedDate" class="date"></span>
                                <span id="patSelectedTime" class="time"></span></p>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>

                <div class="basicdetails-modal form-wizard stnc">
                    <div class="doctordetails-modal ">
                        <div class="dcolumn-3">
                            <div class="frankin" id="docNameOnPopup">

                            </div>
                            <h3 id="docSplityOnPopup"></h3>

                            <h1 id="clnNameOnPopup"></h1>

                            <div><i class="icon-location-1"></i><span id="clncLocOnPopup"></span></div>

                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
                <div class="input-wrapper">
                    <div class="form-left">
                        <h1 class="formheading-left-index">Reason for visit</h1>
                    </div>
                    <div class="form-right">
                        <div class="form-right-left margin-bottom20">
                            <textarea name="reasonForVisit" class="nclo intarea hst"  required="required"
                                      placeholder="Reason for visit" title="Reason for visit" id="mainmh"
                                      style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px"></textarea>
                        </div>
                        <div class="form-right-right confirmgreen">
                            <button type="submit" class="sharereportbtn greenbtn submit_btn" id="confirmApptBtn">
                                Confirm
                            </button>
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
            </form>
        </div>
        <div class="clear"></div>
    </div>
</div>
</div>
</div>
</div>

<div id="showPatDtlsPopup">
    <div class="form-wrapper confirmAppForm" id="showPatDtls" style="display: block;">
        <div class="close-showPatDetail"></div>
        <div class="confirmpadding">
            <div class="full-width " id="PatDtlsPopUp">
                <div class="form-wrapper">
                    <div class="confirmappointemnt-for-app">
                        Scheduled Details
                    </div>
                    <div>

                        <div class="basicdetails-modal form-wizard stnc">
                            <div class="doctordetails-modal ">
                                <div class="dcolumn-3">
                                    <div class="centerimages"><img alt="clinic-image" id="patImage" style="width: 70px;"></div>
                                    <ul class="padding-list">
                                        <li class="padding-list-icon"><span class="id-for-pateints">Patient ID</span><span
                                                class="name-info"> : </span><span class="class-for-demo-patient"
                                                                                  id="patDtlsId"></span></li>
                                        <li class="padding-list-icon"><span class="id-for-pateints">Name</span> <span
                                                class="name-info-class"> : </span><span class="class-for-demo-patient"
                                                                                        id="patDtlsName"></span></li>
                                        <li class="padding-list-icon"><span class="id-for-pateints">Mobile Number</span><span
                                                class="name-info-index"> : </span><span class="class-for-demo-patient"
                                                                                        id="patDtlsPhNo"></span></li>
                                        <li class="padding-list-icon"><span class="id-for-pateints">Email Id</span> <span
                                                class="name-info-index-for-gmail"> : </span><span class="class-for-demo-patient"
                                                                                                  id="patDtlsEmail"></span></li>
                                    </ul>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="clearfix"></div>

                        <div class="basicdetails-modal scheduledtimes">
                            <div class="doctordetails-modal ">
                                <div class="dcolumn-3">
                                    <p class="doctorqulifation"><span id="patDtlsDate" class="date"></span>
                                        <span id="patDtlsTime" class="time"></span></p>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="clearfix"></div>
                        </div>

                        <div class="input-wrapper">
                            <div class="form-left">
                                <h1 class="formheading-left-index" style="border-bottom:none !important;">Reason for visit</h1>
                            </div>
                            <div class="form-right">
                                <div class="form-right-left margin-bottom20 col-md-12">
                                    <div id="patDtlsRFV" style="font-size:14px;"></div>
                                </div>

                                <div class="form-right-right confirmgreen" id="newHP" style="display: none">
                                    <button type="button" class="sharereportbtn greenbtn" id="getConsultation">New consultation
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="clear"></div>

                    </div>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- Appointmant Cancellation Setting PopUp -->
<div id="cancel-date-schedule" style="display: block;">
    <div class="form-wrapper confirmAppForm class-popup" id="cancelselectdate" style="max-width:600px !important;">
        <div class="cancel-close1-canceldate"></div>
        <div class="confirmpadding">
            <div class="confirmappointemnt" style="text-align:center">
                Cancel Setting
            </div>
            <input type="hidden" id="serverDateCMDS" value="${serverDateOne}">
            <input type="hidden" id="serDateCMDS" value="${serverDate}">
            <input type="hidden" id="maxDateToShowCMDS" value="${maxDateToShow}">
            <input type="hidden" id="selectedDoctCMDS" value="">

            <div class="row">
                <div class="strike-pad">
                    <div class="col-md-12">
                        <ul class="cancel-days-for-app">

                            <li><input type="radio" value="1" name="first_item" id="radio1">
                                <label class="cancel-days-for">Cancel Multiple days</label></li>

                            <li><input type="radio" value="2" name="first_item" id="radio2">
                                <label class="cancel-for-demo">Cancel single day</label></li>


                        </ul>



                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-12 cancel-mul-date" id="multipleDayCancel">
            <div class="col-md-12 appont-cancel form-wizard stands-text hide" id="showExistCancelDay">

            </div>
            <div class="clear"></div>
            <div id="messages" class="hide"></div>
            <div id="errormessages" class="hide"></div>
            <div class="col-md-12 no-padding cancel-dates-for-appointments">
                <div class="col-md-6 col-sm-6 no-pad ">
                    <div class="col-md-6 col-sm-6 no-pad ">

                        <fieldset class="input-block">
                            <label class="cancel-date-for-mul">From Date </label>
                            <input type="text" class="hasDatepicker cancel-date-for-mul" placeholder="From Date"
                                   title=" From date" id="CMDSfrom">
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="col-md-6 col-sm-6 no-pad ">
                        <fieldset class="input-block">
                            <label class="cancel-date-for-mul">To Date</label>
                            <input type="text" class="hasDatepicker cancel-date-for-mul" placeholder="To Date"
                                   title=" To date  " id="CMDSTo">
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>
                <div class="col-md-6 col-sm-6 no-pad ">
                    <div class="col-md-3 canl-meassge"><a <%--href="#"--%> class="cancel-demo-page-wrap" id="cancelDays">Cancel</a></div>
                </div>
            </div>
        </div>

        <div class="col-md-12  " >
            <div class="cancel-time-design hidecanceltb" id="singleDayCancel" >
                <div id="SDmessages"></div>
                <div >
                    <div class="col-md-6 col-sm-6 no-pad ">
                        <fieldset class="input-block no-pad">
                            <label class="cancel-date-for-mul">To Date<span style="color:red;">*</span></label>
                            <input type="text" class="hasDatepicker cancel-date-for-mul" placeholder="To Date"
                                   required="" title=" To date  " id="CSdatepicker">
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="clear"></div>
                <div id="showInfoOnSingleDay" style="display: none">
                    <div class="doctor-name">
                        <span class="doctorName-spec" id="SDdocName"></span>
                        <span class="specalist-for-date" id="SDdocSplty"></span>
                        <span class="date-for-single" id="SDdate"></span>

                        <span id="selectallCkbx" style="display: none"> <input type="checkbox" id="selectall"> <span
                                class="select-all-time">Select all</span></span>
                    </div>
                    <form method="post" id="cancelSlots" name="confirmAppointment">
                        <input type="hidden" name="doctorId" id="doctorId" value="">
                        <input type="hidden" name="clinicId" id="clinicId" value="">
                        <input type="hidden" name="date" id="cancelledOn" value="">
                        <input type="hidden" name="status" id="status" value="0">

                        <div class="check-list">
                            <ul id="doctTimeList">

                            </ul>
                        </div>
                        <div class="btn-for-cancel-date" style="margin: 13px 0;">
                            <a class="cancel-demo-page" id="editSlotsBtn">Edit</a>
                            <a class="cancel-demo-page" id="cancelSlotsBtn" style="display: none">Save</a>
                            <a class="cancel-demo-page" id="backSlotsBtn" style="display: none">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class=" hideall demo-for-text">
            <div class="col-md-12">
                <div class="appont-cancel form-wizard stands" style="height:50px;">Do you want to continue?
                    <span class="alloctae-btn"><a class="yes-btn">yes</a></span>
                    <span class="cancel-info"><a class="no-btn">No</a></span></div>
            </div>
        </div>

        <div class=" hideall demo-for-text-index" id="showRhideConfirmBox">
            <div class="col-md-12">
                <div class="appont-cancel form-wizard stands-text">
                    <span id="confirmBox" class="confirm_click"></span>

                    <div class="alloctae-btn"><a class="yes-btn" id="upRsavebtn">Update</a></div>
                    <div class="cancel-info"><a class="no-btn" id="cancel">Cancel</a></div>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
        <div class="clearfix"></div>
    </div>
</div>

<script type="application/javascript">

    $(document).ready(function () {



        $('#cancel').on('click',function () {
            $('#showRhideConfirmBox').hide();
        });


        $('#selectall').on('click',function () {
            if (isChecked('selectall')) {
                $("#status").val('1');
            } else {
                $("#status").val('0');
            }
            $('.selectall').prop('checked', isChecked('selectall'));
        });

        function isChecked(checkboxId) {
            var id = '#' + checkboxId;
            return $(id).is(":checked");
        }

        $("#doctTimeList").on("change", '.selectall', function () {
            if ($(this).is(":not(:checked)")) {
                $('#selectall').prop('checked', false);
                $("#status").val('0');
            }
        });
    });


    $(".cancel-close1-canceldate").click(function () {
        $("#cancel-date-schedule").removeClass('dailog-show');
        $("#multipleDayCancel,#singleDayCancel,#showInfoOnSingleDay,#showRhideConfirmBox,#cancelSlotsBtn,#backSlotsBtn").hide();
        $("#radio1,#radio2").prop('checked', false);
        $("#CSdatepicker").val('');
        $("#CMDSfrom").val('');
        $("#CMDSTo").val('');
        $("#doctTimeList,#showExistCancelDay").empty();
        $('#CMDSfrom,#CMDSTo,#CSdatepicker').datetimepicker('destroy');
    });

</script>
