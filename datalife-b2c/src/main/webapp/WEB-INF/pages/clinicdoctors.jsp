<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="wrapper">
    <div class="form-container"  >
        <div id="tmm-form-wizard" class="container substrate">
            <div class="row clr">
            <div class="row clr form-login-heading">
                <div class="col-md-6 col-sm-6 "><h2 class="app_for_patients_cls">Doctors</h2></div>
                <div class="col-md-6 col-sm-6 right-document ">
                    <span class="before">Search /Register</span>
                    <button type="button" class="share activedrpt" id="clinic_doctors">Doctors</button>
                    <button type="button" class="share" id="clinic_patients"> Patients</button>
                    <button type="button" class="share" id="clinic_settings"> Set Preferences</button>
                </div>
            </div>

            <div class=" back_grd_slots_info">
                <div class="background-for-list-space">
                    <div class=" ">
                        <table id="myclinicDoctorTable" class="display" cellspacing="0" width="100%">
                            <thead>
                            <tr>
                                <th style=" width: 250px;">Doctor Details</th>
                                <th style=" width: 250px;">Appointments</th>
                                <th style=" width: 250px;">Settings</th>
                            </tr>
                            </thead>
                            <tbody id="mydoctors">
                            <c:forEach var="doctor" begin="0" items="${doctors}">
                                <c:choose>
                                    <c:when test="${doctor.activate}">
                                        <tr style="background-color:#fff;" >
                                            <td  style="background-color:#fff;"><img src="${doctor.sendType}" alt="clinic-image" class="alt-text doctor_image">
                                                <div class="index-class">
                                                    <p class="name">${doctor.userDetails.firstName} ${doctor.userDetails.lastName} </p>
                                                    <c:choose>
                                                        <c:when test="${not empty doctor.speciality}">
                                                            <c:out default="None" value="${doctor.speciality}"/>
                                                        </c:when>
                                                    </c:choose>
                                                    <p class="specality-for-clinic">Phone: ${doctor.userContactInfo.mobilePhone}</p>
                                                    <p class="specality-for-clinic">Email Id:  ${doctor.userContactInfo.email}</p></div></td>
                                            <td style="vertical-align:middle"><button class="View  " type="button" onclick="getAppointment(${doctor.userId},${userId})">View </button>
                                                <button  type="button" class="share" onclick="apptCancellationSetting(${doctor.userId},${userId})">Cancel</button></td>
                                            <td style="vertical-align:middle"><button class="view-trash" type="button" onclick=getSettings(${doctor.userId},${userId},'true');>Change Settings</button></td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <tr style="background-color:#eee;" >
                                            <td  style="background-color:#EEE;"><img src="${doctor.sendType}" alt="clinic-image" class="alt-text doctor_image">
                                                <div class="index-class">
                                                    <p class="name">${doctor.userDetails.firstName} ${doctor.userDetails.lastName} </p>
                                                    <c:choose>
                                                        <c:when test="${not empty doctor.speciality}">
                                                            <c:out default="None" value="${doctor.speciality}"/>
                                                        </c:when>
                                                    </c:choose>
                                                    <p class="specality-for-clinic">Phone: ${doctor.userContactInfo.mobilePhone}</p>
                                                    <p class="specality-for-clinic">Email Id:  ${doctor.userContactInfo.email}</p></div></td>
                                            <td style="vertical-align:middle"><button class="View  " type="button" onclick="getAppointment(${doctor.userId},${userId})">View </button>
                                                <button  type="button" class="share" onclick="apptCancellationSetting(${doctor.userId},${userId})">Cancel</button></td>
                                            <td style="vertical-align:middle"><button class="view-trash" type="button" onclick=getSettings(${doctor.userId},${userId},'true');>Change Settings</button>
                                                <p  title="To activate go to settings." style="font-weight: bold;color: red;">Deactivated</p></td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Appointmant Cancellation Setting PopUp -->
    <div id="cancel-date-schedule" style="display: block;">
        <div class="form-wrapper confirmAppForm class-popup" id="cancelselectdate" style=" max-width:600px !important;">
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
                                <li> <input type="radio" value="1" name="first_item" id="radio1">
                                    <label class="cancel-days-for" >Cancel Multiple days</label></li>


                                <li>
                                    <input type="radio" value="2" name="first_item" id="radio2">
                                    <label class="cancel-for-demo">Cancel single day</label></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-12 cancel-mul-date" id="multipleDayCancel">
                <div class="col-md-12 appont-cancel form-wizard stands-text" id="showExistCancelDay">

                </div>
                <div class="clear"></div>
                <div id="messages"></div>
                <div class="col-md-12 no-padding cancel-dates-for-appointments">
                    <div class="col-md-6 col-sm-6 no-pad ">
                        <div class="col-md-6 col-sm-6 no-pad ">
                            <fieldset class="input-block">
                                <label class="cancel-date-for-mul">From Date </label>
                                <input type="text" class="hasDatepicker cancel-date-for-mul" placeholder="From Date" title=" From date" id="CMDSfrom">
                                <!--/ .tooltip-->
                            </fieldset>
                        </div>
                        <div class="col-md-6 col-sm-6 no-pad ">
                            <fieldset class="input-block">
                                <label class="cancel-date-for-mul">To Date</label>
                                <input type="text" class="hasDatepicker cancel-date-for-mul" placeholder="To Date" title=" To date  " id="CMDSTo">
                                <!--/ .tooltip-->
                            </fieldset>
                        </div>

                    </div>
                    <div class="col-md-6 col-sm-6 no-pad ">
                        <div class="col-md-3 canl-meassge"><a href="#" class="cancel-demo-page-wrap " id="cancelDays">Cancel</a></div>
                    </div>
                </div>

            </div>

            <div class="col-md-12">
                <div class="cancel-time-design hidecanceltb" id="singleDayCancel" style="background:#fff;">
                    <div id="SDmessages"></div>
                    <div class="col-md-12">
                        <div class="col-md-6 col-sm-6 no-pad ">
                            <fieldset class="input-block">
                                <label class="cancel-date-for-mul">To Date<span style="color:red;">*</span></label>
                                <input type="text" class="hasDatepicker cancel-date-for-mul" placeholder="To Date" required="" title=" To date  " id="CSdatepicker">
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

                            <span id="selectallCkbx" style="display: none"> <input type="checkbox" id="selectall" > <span class="select-all-time">Select all</span></span>
                        </div>
                        <form method="post" id="cancelSlots" name="confirmAppointment">
                            <input type="hidden" name="doctorId" id="doctorId" value="">
                            <input type="hidden" name="clinicId" id="clinicId" value="">
                            <input type="hidden"  name="date" id="cancelledOn" value="">
                            <input type="hidden" name="status" id="status" value="0">
                            <div class="check-list">
                                <ul id="doctTimeList">

                                </ul>
                            </div>
                            <div class="btn-for-cancel-date">
                                <a class="cancel-demo-page" id="editSlotsBtn" >Edit</a>
                                <a class="cancel-demo-page"  id="cancelSlotsBtn" style="display: none">Save</a>
                                <a class="cancel-demo-page" id="backSlotsBtn" style="display: none">Cancel</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class=" hideall demo-for-text">
                <div class="col-md-12">
                    <div class="appont-cancel form-wizard stands">Do you want to continue?
                        <span class="alloctae-btn"><a  class="yes-btn">yes</a></span>
                        <span class="cancel-info"><a  class="no-btn">No</a></span></div>
                </div>
            </div>
            <div class=" hideall demo-for-text-index" id="showRhideConfirmBox">
                <div class="col-md-12">
                    <div class="appont-cancel form-wizard stands-text" style="    height:78px;">
                        <span id="confirmBox" style="margin-bottom:10px;padding:5px 8px;"></span>

                        <div class="alloctae-btn"><a class="yes-btn" id="upRsavebtn">Update</a></div>
                        <div class="cancel-info"><a class="no-btn" id="cancel">Cancel</a></div>
                    </div>
                </div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
<script type="application/javascript">

    $(document).ready(function(){

        $('#cancel').on('click',function () {
            $('#showRhideConfirmBox').hide();
        });

        $('#selectall').click(function () {
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

        $("#doctTimeList").on("change",'.selectall',function(){
            if($(this).is(":not(:checked)")){
                $('#selectall').prop('checked',false);
                $("#status").val('0');
            }
        });
    });

    $(".cancel-close1-canceldate").click(function () {
        $("#cancel-date-schedule").removeClass('dailog-show');
        $("#multipleDayCancel,#singleDayCancel,#showInfoOnSingleDay").hide();
        $("#radio1,#radio2").prop('checked',false);
        $("#CSdatepicker").val('');
        $("#CMDSfrom").val('');
        $("#CMDSTo").val('');
        $("#doctTimeList,#showExistCancelDay").empty();
        $("#showRhideConfirmBox").empty().hide();
        $('#CMDSfrom,#CMDSTo,#CSdatepicker').datetimepicker('destroy');
    });



</script>
