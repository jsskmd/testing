<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 7/5/2016
  Time: 10:18 AM
  To change this template use File | Settings | File Templates.
--%>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/server/srcommon.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery.datetimepicker.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/site.min.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/dataTables.responsive.min.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/jquery.datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/site.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/dataTables.responsive.min.js"></script>


<div class="wrapper">
<div class="form-container">
<div id="tmm-form-wizard" class="container substrate" style="overflow: visible !important">
<div class="row clr form-login-heading">
    <div class="col-md-6 col-sm-6 no-pad"><h2 class="app_for_patients_cls" id="sevicename">Lab  Services</h2></div>
    <div class=" right-document ">
        <button type="button" class="view_cls_pat" id="viewlaborders">View Order Status</button>
    </div>
</div>


<div class="clearfix"></div>
<div id="dynamic_div">
        <div>
            <div class="row stage-container doctor-timeslot-info">
                <div class="doctor-timeslot-discription">
                    <ul class="doctor-list">
                        <li><span class="first-slot"> 1 </span><b style="cursor: pointer" id="stepone">Share prescription</b><span><i class="icon-right-dir"></i></span></li>
                        <li><span class="first-slot"> 2 </span><b style="cursor: pointer" id="steptwo">Confirm Address</b><span><i class="icon-right-dir"></i></span></li>
                        <li><span class="first-slot"> 3 </span><b style="cursor: pointer" id="stepthree">Instructions if any</b><span><i class="icon-right-dir"></i></span></li>
                        <li id="eMessage"></li>
                    </ul>

                </div>
            </div>
            <div class="form-wizard">
                <div>
                    <input type="hidden" id="servType" value="">
                    <input type="hidden" id="services" value="">
                    <button type="button" class="image-upload imguploadauto saveb editbasicinfo submit_btn start hide" id="shareRecords"><i class="icon-search-circled"></i>Share Records
                    </button>
                    <div class="row">
                        <div id="recordUpload">

                        </div>
                       <div class="hide" id="confirmAddress">
                           <div class="basicdetails-modal list-pge " id="view_clinicProfile">
                               <div class="form-wizard doctor-info">
                                   <div class="dcolumn-3">
                                           <div class="col-md-12">
                                               <div class="col-md-6 no-pad alignright" id="clinicUserName">Current Address</div>
                                               <div class="col-md-6 no-pad alignrightbtn" id="editClinicProfile">
                                                   <a class="popup-btn"><i class="icon-edit"></i></a>
                                               </div>
                                           </div>

                                       <div id="confirmMessage"></div>
                                           <div class="col-md-12 aligntop">
                                               <div class="col-md-12 no-pad">
                                                   <ul>
                                                       <li class="padding-list-icon details">Details</li>
                                                       <li class="padding-list-icon"><span class="wcp">Address</span><span class=""><span class="index-span"> : </span><span id="uciaddress"></span> <span class="" id="ucilocation"></span> <span class="" id="ucicity"></span> <span class="" id="ucistate"></span><span class=""> <span id="ucicountry"></span>  -&nbsp;<span id="ucizipcode"></span></span></span></li>
                                                       <li class="padding-list-icon"><span class="wcp">Mobile</span><span class=""><span class="index-span"> : </span><span id="ucimobile"></span><a id="verify_mobile">Edit &amp; Verify</a></span></li>
                                                   </ul>
                                               </div>
                                               <div class="col-md-12 no-pad">
                                                   <ul class="">
                                                       <li class="padding-list-icon"><input type="checkbox" value="t_checkbox" id="t_checkbox">
                                                           <span class="checkbox">Go with current Address only </span></li>
                                                       <li class="padding-list-icon"><input type="checkbox" value="t_idcheckbox" id="t_addresscheckbox">
                                                           <span class="checkbox"> Click checkbox to add sample collect/delivery address</span></li>
                                                   </ul>
                                               </div>
                                           </div>
                                   </div>
                                   <div class="clear"></div>
                               </div>
                           </div>
                           <div class="clear"></div>

                           <div class="basicdetails-modal spce hide" id="edit_clinicProfile">
                               <form id="userContact" name="userContactInfo" method="post" >
                                   <input type="hidden" name="user.userId" id="pciUserId" value=""/>
                                   <input type="hidden" name="mobilePhone" id="t_mobilePhone" value=""/>
                                   <input type="hidden" name="email" id="t_email" value=""/>
                                   <div class="hide-info">
                                       <div class="row">
                                           <div class="col-md-4 no-pad ">
                                               <fieldset class="input-block">
                                                   <label> Address<span style="color:red;">*</span></label>
                                                   <input type="text" id="t_address" name="address" required placeholder=" Address"
                                                          title=" Address" maxlength="40"/>
                                               </fieldset>
                                           </div>

                                           <div class="col-md-4 no-pad ">
                                               <fieldset class="input-block">
                                                   <label>Location<span style="color:red;">*</span></label>
                                                   <input type="text" id="t_location" name="location" placeholder="Location" required
                                                          title="Location" pattern="^[a-zA-Z ]+$" maxlength="20"/>
                                                   <!--/ .dropdown-->
                                               </fieldset>
                                           </div>
                                           <div class="col-md-4 no-pad">
                                               <fieldset class="input-block">
                                                   <label class="select-records">Country<span style="color:red;">*</span></label>
                                                   <div class="dropdown">
                                                       <select id="t_ucicountry" name="country" class="dropdown-select" onChange="combo('t_ucicountry','t_ucistate','t_ucistateInput');" required="required">
                                                       </select>
                                                   </div>
                                               </fieldset></div>
                                            </div>

                                       <div class="row">
                                           <div class="col-md-4 no-pad" id="dt_ucistate">
                                               <fieldset class="input-block">
                                                   <label class="select-records">State</label>
                                                   <div class="dropdown">
                                                       <select id="t_ucistate" name="state" class="dropdown-select" title="State Name"></select>
                                                   </div>
                                               </fieldset></div>
                                           <div class="col-md-4 no-pad hide" id="dt_ucistateInput">
                                               <fieldset class="input-block">
                                                   <label class="select-records">State</label>
                                                   <input type="text" name="otherState" id="t_ucistateInput" placeholder="State Name" title="Id Number" maxlength="20" pattern="^[a-zA-Z ]+$">
                                               </fieldset>
                                           </div>


                                           <div class="col-md-4 no-pad ">
                                               <fieldset class="input-block">
                                                   <label class="select-records">City<span style="color:red;">*</span></label>
                                                   <input type="text" placeholder="City" required="" title="City " id="t_city" name="city" maxlength="20" pattern="^[a-zA-Z ]+$">
                                                   <!--/ .dropdown-->
                                               </fieldset>
                                           </div>

                                           <div class="col-md-4 no-pad ">
                                               <fieldset class="input-block">
                                                   <label class="select-records">Zip Code<span style="color:red;">*</span></label>
                                                   <input type="text" placeholder="Zip Code" required="" title=" Zip Code " id="t_zipCode" name="zipCode" maxlength="7" pattern="^[\d]{6,7}">
                                                   <!--/ .dropdown-->
                                               </fieldset>
                                           </div>
                                       </div>

                                       <div class="assign-space" style="padding-top: 10px">
                                           <input type="button" value="Cancel" class="saveb editbasicinfo" id="cancelprofile">
                                               <button type="submit" class="saveb editbasicinfo-vitals submit_btn" id="saveClinicProfile">Confirm Address</button>
                                       </div>
                                   </div>
                               </form>
                           </div>
                           <div class="basicdetails-modal spce hide" id="addAddress">
                               <form method="post" name="serviceRequests" id="altuserContactInfo" enctype='application/json'>
                                   <div class="row">
                                       <div class="col-md-6 no-padding">
                                           <div class="col-md-6 col-sm-6 no-pad ">
                                               <fieldset class="input-block">
                                                   <label> Address<span style="color:red;">*</span></label>
                                                   <input type="text" id="alt_address" name="alternateServiceContactInfo.address" required="" placeholder=" Address" title=" Address" maxlength="40">
                                               </fieldset>
                                           </div>
                                           <div class="col-md-6 col-sm-6 no-pad ">
                                               <fieldset class="input-block">
                                                   <label>Location<span style="color:red;">*</span></label>
                                                   <input type="text" id="alt_location" name="alternateServiceContactInfo.location" placeholder="Location" required="" title="Location" pattern="^[a-zA-Z ]+$" maxlength="20">
                                               </fieldset>
                                           </div>
                                       </div>
                                       <div class="col-md-6 no-padding">
                                           <div class="col-md-6 col-sm-6 no-pad ">
                                               <fieldset class="input-block">
                                                   <label>City<span style="color:red;">*</span></label>
                                                   <input type="text" id="alt_city" name="alternateServiceContactInfo.city" placeholder="City" required="" title="City" pattern="^[a-zA-Z ]+$" maxlength="20">
                                                   <!--/ .tooltip-->
                                               </fieldset>
                                           </div>
                                           <div class="col-md-6 col-sm-6 no-pad ">
                                               <fieldset class="input-block">
                                                   <label> Country<span style="color:red;">*</span></label>
                                                   <div class="dropdown">
                                                       <select id="alt_ucicountry" name="alternateServiceContactInfo.country" required="required"  class="dropdown-select" onchange="combo('alt_ucicountry','t_altstate','t_altstateInput')">
                                                       </select>
                                                   </div>
                                               </fieldset>
                                           </div>
                                       </div>
                                       <div class="col-md-6 no-padding">
                                           <div class="col-md-6 col-sm-6 no-pad hide" id="dt_altstate">
                                               <fieldset class="input-block">
                                                   <label>State</label>
                                                   <div class="dropdown">
                                                       <select  id="t_altstate" name="alternateServiceContactInfo.state" title="State Name" class="dropdown-select"></select>
                                                   </div>
                                               </fieldset>
                                           </div>

                                           <div class="col-md-6 col-sm-6 no-pad" id="dt_altstateInput">
                                               <fieldset class="input-block">
                                                   <label>State</label>
                                                   <input type="text" name="alternateServiceContactInfo.otherState" id="t_altstateInput" placeholder="State Name" title="State Name" readonly pattern="^[a-zA-Z ]+$" maxlength="20">
                                                   <!--/ .tooltip-->
                                               </fieldset>
                                           </div>
                                           <div class="col-md-6 col-sm-6 no-pad ">
                                               <fieldset class="input-block">
                                                   <label>Zip Code<span style="color:red;">*</span></label>
                                                   <input type="text" id="alt_zipCode" name="alternateServiceContactInfo.zipCode" required="" placeholder="Zip Code" title="Zip Code" maxlength="7" pattern="^[\+?\d]{6,7}">
                                               </fieldset>
                                           </div>
                                       </div>
                                   </div>
                                   <div class="row">
                                       <div class="float-id-upoad">
                                           <input type="button" value="Cancel" class="saveb editbasicinfo" id="canceladdress">
                                           <button type="submit" class=" saveb editbasicinfo-vitals submit_btn">Confirm Address</button>
                                       </div>
                                   </div>
                               </form>
                           </div>
                           <div class="clear"></div>
                        </div>

                        <div class="hide" id="instrusctions">
                            <form name="serviceRequests" id="labrequestForm" method="post" enctype="application/json" >
                                <input type="hidden" name="serviceType" value="diagnosticLabs" id="sendType">
                                <input type="hidden" name="patientId" id="lab_userId">
                                <input type="hidden" name="labRequests.recordIds" id="recordIds">

                                <div class="col-md-6 col-sm-6 no-pad">
                                   <fieldset class="input-block">
                                       <label class="secnd-opinion-label">Enter instruction  if any</label>
                                       <textarea class="text-page keyup-fake" name="labRequests.instruction"></textarea>
                                   </fieldset>
                               </div>
                                <div class="col-md-6 col-sm-6 no-pad">
                                   <fieldset class="input-block">
                                       <label class="secnd-opinion-label">Preferred time for sample collection</label>
                                       <ul >
                                           <li>
                                               <input type="radio" name="labRequests.preferredTime" value="Morning"><label>Morning</label>
                                           </li>
                                           <li>
                                               <input type="radio" name="labRequests.preferredTime" value="Afternoon"><label>Afternoon</label>
                                           </li>
                                           <li>
                                               <input type="radio" name="labRequests.preferredTime" value="Evening"><label>Evening</label>
                                           </li>
                                       </ul>
                                   </fieldset>
                               </div>
                                <div class="row">
                                    <div class="float-id-upoad">
                                        <button type="submit" class=" saveb editbasicinfo-vitals submit_btn" id="labrequest">Submit request</button>
                                    </div>
                                </div>
                            </form>
                            <form name="serviceRequests" id="pharmacyrequestForm" method="post" enctype="application/json" class="hide">
                                <input type="hidden" name="serviceType" value="pharmacy" id="serviceType">
                                <input type="hidden" name="patientId" id="pharmacy_userId">
                                <input type="hidden" name="pharmacyRequests.recordIds" id="recordIds">

                                <div class="col-md-6 col-sm-6 no-pad">
                                    <fieldset class="input-block">
                                        <label class="secnd-opinion-label">Enter instruction  if any</label>
                                        <textarea class="text-page keyup-fake" name="pharmacyRequests.instruction"></textarea>
                                    </fieldset>
                                </div>
                                <div class="col-md-6 col-sm-6 no-pad">
                                    <fieldset class="input-block">
                                        <label class="secnd-opinion-label">Preferred time for medicines delivery</label>
                                        <ul >
                                            <li>
                                                <input type="radio" name="pharmacyRequests.preferredTime" value="Morning"><label>Morning</label>
                                            </li>
                                            <li>
                                                <input type="radio" name="pharmacyRequests.preferredTime" value="Afternoon"><label>Afternoon</label>
                                            </li>
                                            <li>
                                                <input type="radio" name="pharmacyRequests.preferredTime" value="Evening"><label>Evening</label>
                                            </li>
                                        </ul>
                                    </fieldset>
                                </div>
                                <div class="row">
                                    <div class="float-id-upoad">
                                        <button type="submit" class=" saveb editbasicinfo-vitals submit_btn" id="pharmacyrequest">Submit request</button>
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
<!--/ .container-->
</div>
</div>

