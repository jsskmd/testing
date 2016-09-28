<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 8/20/2015
  Time: 6:07 PM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="resources/css/jquery.datetimepicker.min.css">
<link rel="stylesheet" href="resources/css/jPages.css">
<link rel="stylesheet" href="resources/css/font-awesome.min.css" />

<script type="text/javascript" src="resources/jsplugins/site.js"></script>
<script type="text/javascript" src="resources/jsplugins/dataTables.responsive.min.js"></script>
<script type="text/javascript" src="resources/jsplugins/jquery.datetimepicker.min.js"></script>
<script type="text/javascript" src="resources/js/server/doctorSearchFixAppt.js"></script>
<script src="resources/jsplugins/jquery.datetimepicker.min.js"></script>
<script type="text/javascript" src="resources/js/server/common.js"></script>

<div class="wrapper">
<div class="form-container">
<div id="tmm-form-wizard" class="container substrate register-margin">
<div class="row clr form-login-heading">

    <div class="col-md-6 col-sm-6 no-pad"><h2 class="app_for_patients_cls">Appointments </h2></div>
    <div class=" right-document ">
        <button type="button" class="view_cls_pat" id="viewAppt">View Appointments</button>
    </div>
</div>
<!--/ .row-->
<!--/ .row-->
<div id="eMessage"></div>

<form id="searchDoctor">
    <div class="form-wizard margintop-20">
        <div >
            <div >
                <div class="col-md-6 no-padding">
                    <div class="col-md-4 search"> <img src="resources/images/helath-logo.png" alt="Search-doctor"> </div>
                    <div class="col-md-8  no-pad search">
                        <fieldset class="input-block">
                            <input type="hidden" name="searchCat" id="searchCategories" value="">
                            <input type="hidden" name="catSearchId" value="" id="searchDoctorId">
                            <label>Search by</label>
                            <input type="text" id="doctorSearch" pattern="^[a-zA-Z/ ]+$" name="flname" maxlength="45" class="form-icon form-icon-user" placeholder="Search by Doctor name/Clinic/Specialty"  title="Search by Doctor name/Clinic/Specialty">
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>
                <div class="col-md-6  no-padding">
                    <div class="col-md-6  no-pad search ">
                        <fieldset class="input-block">
                            <label>Location</label>
                            <input type="text" id="location" title="Location" pattern="^[a-zA-Z ]+$" maxlength="45" value="" name="location" placeholder="Location">

                        </fieldset>
                    </div>
                    <div class="col-md-3 col-sm-12  no-pad">
                        <div class="form-header-demo-search ">
                            <input type="hidden" id="patientId" value="${user.userId}" name="patientId">
                            <input type="submit" value="Search" class="saveb editbasicinfodemo" >
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">

                <div class="col-md-12 col-sm-12 location-info hide"><i class="icon-location"></i><span id="ipcity"></span><span class="change-location"><a href="#">Change City</a></span></div>
            </div>
        </div>
    </div>
</form>

<div class="list_pages" id="ourServices" >
    <ul>
        <li class="col-md-4  col-sm-6">


            <a class="reg_now_for_pharm phamarcy_cls  not_available servicespremiummain surgeryrefferal" id="surgeryReferral"><span>Surgery Referral</span></a>
            <div class="clearfix"></div>

        </li>
        <li class="col-md-4   col-sm-6">
            <a class="reg_now_for_pharm lab_info   not_available servicespremiummain secondopiniondoctors"  id="secondOpinion"><span>Second Opinion Doctors </span></a>
            <div class="clearfix"></div>

        </li>
        <li class="col-md-4 col-sm-6">
            <a class="reg_now_for_pharm send_opini not_available servicespremiummain telecounsultationdoctor" id="teleConsultation"><span>Tele-consultation</span></a>
            <div class="clearfix"></div>


        </li>
        <li class="col-md-4   col-sm-6">


            <a class="reg_now_for_pharm tele   not_available servicespremiummain  labservices-new" id="lab"><span>Lab Services</span></a>
            <div class="clearfix"></div>
        </li>

        <li class="col-md-4 col-sm-6">
            <a class="reg_now_for_pharm tele  servicespremiummain pharmacynew not_available" id="pharmacy"><span>Pharmacy</span></a>
            <div class="clearfix"></div>
        </li>
        <li class="col-md-4  col-sm-6">
            <a class="reg_now_for_pharm send_opini  not_available servicespremiummain phisotheraphyrehab"  id="rehab"><span>Physiotherapy & Rehab</span></a></div>

<div class="clearfix"></div>

</li>
<div class="clearfix"></div>

</ul>



<%--
 <div class="list_pages" id="ourServicess" >
    <ul>
        <li class="col-md-4  col-sm-6">
            <div class="box-shaow">
&lt;%&ndash;             <img alt="Pharmacy" src="<%=request.getContextPath()%>/resources/images/icons-1.png">&ndash;%&gt;
                <div class="permiumservices surgeryreferal"></div>
                <div class="pflotleft">
                    <h6 class="pharmacy_place">Surgery Referral </h6>
                  &lt;%&ndash;  <a class="reg_now_for_pharm phamarcy_cls btn not_available" id="surgeryReferral"> Request for Service</a>&ndash;%&gt;
                </div>
                <div class="clearfix"></div>
            </div>
        </li>
        <li class="col-md-4   col-sm-6">
            <div class="box-shaow">
&lt;%&ndash;
                <img alt="Pharmacy" src="<%=request.getContextPath()%>/resources/images/icons-4.png">
&ndash;%&gt;
    <div class="permiumservices secondopiniondoctor"></div>
    <div class="pflotleft">
    <h6 class="pharmacy_place labs">Second Opinion Doctors </h6>

                <a class="reg_now_for_pharm lab_info btn not_available"  id="secondOpinion">Request for  Service</a></div>
    <div class="clearfix"></div>
</div>
        </li>
        <li class="col-md-4 col-sm-6">
            <div class="box-shaow">
                <div class="permiumservices teleconsultation"></div>
                <div class="pflotleft">
                    <h6 class="pharmacy_place opinions">Tele-consultation</h6>
                    <a class="reg_now_for_pharm send_opini btn not_available" id="teleConsultation">Request  for  Service</a>
                </div>
               <div class="clearfix"></div>
            </div>
        </li>
        <li class="col-md-4   col-sm-6">
            <div class="box-shaow">
                <div class="permiumservices labservices"></div>
                <div class="pflotleft">
                    <h6 class="pharmacy_place consult">Lab Services </h6>
                <a class="reg_now_for_pharm tele btn not_available" id="lab">Request for  Service</a></div>
                <div class="clearfix"></div>
            </div>
        </li>

        <li class="col-md-4 col-sm-6">
            <div class="box-shaow">
                <div class="permiumservices pharmacy"></div>
                    <div class="pflotleft">
                        <h6 class="pharmacy_place consult">Pharmacy </h6>
                        <a class="reg_now_for_pharm tele btn not_available" id="pharmacy">Request for  Service</a>
                    </div>
                <div class="clearfix"></div>
            </div>
        </li>
        <li class="col-md-4  col-sm-6">
            <div class="box-shaow">
    <div class="permiumservices physiotherapy"></div>
<div class="pflotleft"><h6 class="pharmacy_place opinions">Physiotherapy & Rehab </h6>
    <a class="reg_now_for_pharm send_opini btn not_available"  id="rehab">Request for  Service</a></div>
    <div class="clearfix"></div>
            </div>
        </li>
    </ul>
</div>
--%>
<div class="" id="pagination">
    <div class="form-container">
        <div id="" class="">
            <div class="holder col-md-12"></div>

            <div class="doctor-full-slots col-md-8">
                <div class="clear"></div>
                <ul id="addSrcDocList" class="doctorsearch-cont">
                </ul>
            </div>

            <div class="col-md-4 sidebarcolor hide" id="siderbar">
                <div class="adverstiment1 sidebox"><h3>Click here to advertise your brand with DataLife</h3></div>
                <div class="list_pages" id="ourServices1">
                    <ul id="enqireforservices">
                        <li class="col-md-12  col-sm-12">


                            <a class="reg_now_for_pharm phamarcy_cls  not_available servicespremiummain surgeryrefferal" id="surgeryReferral"><span>Surgery Referral</span></a>
                            <div class="clearfix"></div>

                        </li>

                        <%-- <li class="">
                             <div class="box-shaow">
                                 <div class="permiumservices surgeryreferal"></div>
                                 <div class="pflotleft">
                                     <h6 class="pharmacy_place">Surgery Referral </h6>
                                     <a class="reg_now_for_pharm phamarcy_cls btn not_available" id="surgeryReferral"> Request for Service</a>
                                 </div>
                                 <div class="clearfix"></div>
                             </div>
                         </li>--%>

                        <li class="col-md-12   col-sm-12">
                            <a class="reg_now_for_pharm lab_info   not_available servicespremiummain secondopiniondoctors"  id="secondOpinion"><span>Second Opinion Doctors </span></a>
                            <div class="clearfix"></div>

                        </li>
                        <%--   <li class="col-md-12   col-sm-12">
                               <div class="box-shaow">
                                   <div class="permiumservices secondopiniondoctor"></div>
                                   <div class="pflotleft">
                                       <h6 class="pharmacy_place labs">Second Opinion Doctors </h6>

                                       <a class="reg_now_for_pharm lab_info btn not_available"  id="secondOpinion">Request for  Service</a></div>
                                   <div class="clearfix"></div>
                               </div>
                           </li>--%>
                        <li class="col-md-12 col-sm-12">
                            <a class="reg_now_for_pharm send_opini not_available servicespremiummain telecounsultationdoctor" id="teleConsultation"><span>Tele-consultation</span></a>
                            <div class="clearfix"></div>


                        </li>

                        <%--  <li class="col-md-12 col-sm-12">
                              <div class="box-shaow">
                                  <div class="permiumservices teleconsultation"></div>
                                  <div class="pflotleft">
                                      <h6 class="pharmacy_place opinions">Tele-consultation</h6>
                                      <a class="reg_now_for_pharm send_opini btn not_available" id="teleConsultation">Request  for  Service</a></div>
                                  <div class="clearfix"></div>

                              </div>
                          </li>--%>

                        <li class="col-md-12  col-sm-12">


                            <a class="reg_now_for_pharm tele   not_available servicespremiummain  labservices-new" id="lab"><span>Lab Services</span></a>
                            <div class="clearfix"></div>
                        </li>
                        <%-- <li class="col-md-12   col-sm-12">
                             <div class="box-shaow">
                                 <div class="permiumservices labservices"></div>
                                 <div class="pflotleft">
                                     <h6 class="pharmacy_place consult">Lab Services </h6>

                                     <a class="reg_now_for_pharm tele btn not_available" id="lab">Request for  Service</a></div>
                                 <div class="clearfix"></div>

                             </div>
                         </li>
--%>  <li class="col-md-12 col-sm-12">
                        <a class="reg_now_for_pharm tele  servicespremiummain pharmacynew not_available" id="pharmacy"><span>Pharmacy</span></a>
                        <div class="clearfix"></div>
                    </li>
                        <%-- <li class="col-md-12 col-sm-12">
                             <div class="box-shaow">
                                 <div class="permiumservices pharmacy"></div>
                                 <div class="pflotleft">
                                     <h6 class="pharmacy_place consult">Pharmacy </h6>
                                     <a class="reg_now_for_pharm tele btn not_available" id="pharmacy">Request for  Service</a></div>
                                 <div class="clearfix"></div>

                             </div>
                         </li>--%>

                        <li class="col-md-12  col-sm-12">
                            <a class="reg_now_for_pharm send_opini  not_available servicespremiummain phisotheraphyrehab"  id="rehab"><span>Physiotherapy & Rehab</span></a></div>

                <div class="clearfix"></div>

                </li>
                <%-- <li class="col-md-12  col-sm-12">
                     <div class="box-shaow">
                         <div class="permiumservices physiotherapy"></div>
                         <div class="pflotleft"><h6 class="pharmacy_place opinions">Physiotherapy & Rehab </h6>
                             <a class="reg_now_for_pharm send_opini btn not_available"  id="rehab">Request for  Service</a></div>
                         <div class="clearfix"></div>
                     </div>
                 </li>--%>
                </ul>
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


<div id="appConfirmPopup">
    <div class="form-wrapper confirmAppForm" id="confirmAppForm" style="display: block;">
        <div class="cancel-close1"></div>
        <div id="confirmMessage" class="hide"></div>
        <div class="confirmpadding">
            <form id="cnfAppointment" method="post" name="confirmAppointment">
                <input type="hidden" name="doctorId" id="cnfDoctorId">
                <input type="hidden" name="clinicId" id="cnfClinicId">
                <input type="hidden" name="patientId" id="cnfPatientId">
                <input type="hidden" name="date" id="cnfDate">
                <input type="hidden" name="time" id="cnfTime">
                <input type="hidden" name="tokenNo" id="tokenNo">
                <input type="hidden"  id="mainUlsolt">
                <input type="hidden" name="doctorName" id="cnfDoctorName">
                <div class="note"> </div>
                <div class="form-wrapper">
                    <div class="confirmappointemnt">
                        <h1> Confirm Appointment</h1>
                    </div>
                    <div>
                        <div class="basicdetails-modal sidebox">
                            <div class="doctordetails-modal ">
                                <div class="dcolumn-3">
                                    <ul class="padding-list">
                                        <li class="padding-list-icon"><div class="col-md-6 no-pad bold ">Doctor Name</div><div class=" col-md-6 no-pad" id="cnfDocName"> : Dr.simon</div><div class="clear"></div></li>
                                        <li class="padding-list-icon"><div class="col-md-6 no-pad bold ">Specialist</div><div class="col-md-6 no-pad" id="cnfDocSpl"> : Dentist</div><div class="clear"></div></li>
                                        <li class="padding-list-icon"><div class="col-md-6 no-pad bold ">Appointment Time</div><div class="col-md-6 no-pad" id="cnfDateTime"> : 17 Aug 2015(Mon) 07:00PM</div><div class="clear"></div></li>
                                    </ul>
                                    <ul>
                                        <li></li>
                                        <li></li>
                                        <li></li>
                                    </ul>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                        <div class="basicdetails-modal sidebox">
                            <div class="doctordetails-modal ">
                                <div class="dcolumn-3">
                                    <h1 id="clinicName">Clinic: </h1>
                                    <div class="col-md-12 no-pad clinic location-clinic"><span id="cnfClnName"></span><br>
                                        <span id="cnfClnLoc"></span></div>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                        <div class="input-wrapper">
                            <div class="form-left">
                                <h1 class="formheading-left-index">Reason for visit<span style="color:red;">*</span></h1>
                            </div>
                            <div class="form-right">
                                <div class="form-right-left margin-bottom20">
                                    <textarea style="overflow: hidden; word-wrap: break-word; resize: vertical;  padding-left: 10px" id="mainmh" title="Reason for visit" placeholder="Reason for visit" class="nclo intarea hst" name="reasonForVisit" required="required"></textarea>
                                </div>
                                <div class="form-right-right confirmgreen">
                                    <button id="appointmentBtn" class="sharereportbtn greenbtn submit_btn" type="submit">Confirm</button>
                                </div>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="clear"></div>
            </form>
        </div>
    </div>
</div>

<script>

    $(document).ready(function(){

        $('input:radio[name=tabion]').each(function() {
            $(this).attr('checked',false);
        });

        $('#tab11').attr('checked', true);

        $('.tabion .tabtile').on('click', function(){
            $(this).parents('ul').find('.active').removeClass('active');
            $(this).parents('li').addClass('active');
        });

    });
</script>
</html>