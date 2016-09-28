
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/typeahead.bundle.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/handlebars-v3.0.3.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/server/srSurgeryReferral.js"></script>
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
    <div class="col-md-6 col-sm-6 no-pad"><h2 class="app_for_patients_cls">Surgery Referral </h2></div>
    <div class=" right-document ">
    </div>
</div>


<div class="clearfix"></div>
<div id="dynamic_div">
    <input type="hidden" id="role" value="ROLE_HOSPITAL"/>
    <input type="hidden" id="curDate" value="${curDate}">
    <div>
        <form  id="surgeryRequest" name="serviceRequests" method="post" enctype="multipart/form-data">
            <input type="hidden" name="orderId" id="orderId">
            <input type="hidden" name="serviceType" value="surgeryReferral" id="sendType">
            <input type="hidden" name="patientId" id="surgery_userId">
            <input type="hidden" name="surgeryRequests.recordIds" id="recordIds">

            <div class="row">
                <div class="col-md-8">
                    <div class="form-header">
                        <div class="form-title form-icon title-icon-lock"><b>Fill the Details to get Surgery Referral</b></div>
                    </div>
                    <!--/ .form-header-->
                </div>
                <div class="col-md-4">
                    <div class="form-header-demo">
                        <div id="eMessage"></div>
                    </div>
                </div>
            </div>

            <div class="form-wizard">
                <div>
                    <div id="message"></div>
                    <div class="row">
                        <div class="">
                            <div class="col-md-3 col-sm-3  no-pad">
                                <fieldset class="input-block">
                                    <label>Specialty<span style="color:red;">*</span></label>
                                    <div class="dropdown">
                                        <select class=" dropdown-select select-specality" id="speciality" required="" name="surgeryRequests.speciality" onChange="getProcedureTypeBySpeciality(this.value,'typeOfprocedure')">
                                        </select>
                                    </div>
                                </fieldset>
                            </div>
                            <div class="col-md-3 col-sm-3  no-pad">
                                <fieldset class="input-block">
                                    <label class="secnd-opinion-label">Surgery/Procedure Required :<span style="color:red;">*</span></label>
                                    <textarea required="" title="Treatment Required" class="form-control hide" id="typeOfprocedureText"></textarea>
                                    <div class="dropdown">
                                        <select class="dropdown-select" title="Treatment Required" id="typeOfprocedure" required=""  name="surgeryRequests.typeOfProcedure"></select>
                                    </div>
                                </fieldset>
                            </div>
                            <div class="col-md-3 col-sm-3  no-pad">
                                <fieldset class="input-block">
                                    <label class="secnd-opinion-label">Country<span style="color:red;">*</span></label>
                                    <div class="dropdown">
                                        <select class="dropdown-select" id="providerCountry" required="" name="surgeryRequests.country"></select>
                                    </div>
                                </fieldset>
                            </div>
                            <div class="col-md-3 col-sm-3 no-pad ">
                                <fieldset class="input-block">
                                    <label class="secnd-opinion-label">City <span style="color:red;">*</span></label>
                                    <input type="text" required="" title="City Eg: Rajajinagar" class="form-control" name="surgeryRequests.city" id="city"  pattern="^[a-zA-Z ]+$"/>
                                </fieldset>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="">
                            <div class="col-md-6 no-padding">
                                <div class="col-md-6 col-sm-6 no-pad ">
                                    <fieldset class="input-block">
                                        <label class="secnd-opinion-label">Preferred Hospital/ Institution Name </label>
                                        <input type="text"  title="Preferred Hospital Eg: Fortis" pattern="^[A-Za-z ]+$" class="form-control" name="surgeryRequests.institutionName"/>
                                    </fieldset>
                                </div>
                                <div class="col-md-6 col-sm-6 no-pad">
                                    <fieldset class="input-block">
                                        <label class="secnd-opinion-label">Preferred Accommodation<span style="color:red;">*</span></label>
                                        <div class="dropdown">
                                            <select class="dropdown-select" required="" name="surgeryRequests.accommodationType">
                                                <option value='' disabled selected>Select Accommodation</option>
                                                <option value="General">General</option>
                                                <option value="Special">Special</option>
                                                <option value="Delux">Deluxe</option>
                                            </select>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>
                            <div class="col-md-6 no-padding">
                                <div class="col-md-6 col-sm-6  no-pad">
                                    <fieldset class="input-block">
                                        <label class="secnd-opinion-label"> Preferred Budget</label>
                                        <div class="dropdown">
                                            <select class="dropdown-select form-control" title="Preferred Budget" id="" name="surgeryRequests.budget">
                                                <option value='' disabled selected>Select Budget</option>
                                                <option value="1">1-3 Lakh</option>
                                                <option value="2">3-5 Lakh</option>
                                                <option value="4">5 Lakh & above</option>
                                            </select>
                                        </div>
                                    </fieldset>
                                </div>
                                <div class="col-md-6 col-sm-6 no-pad">
                                    <div class="col-md-6 col-sm-6 no-pad">
                                    <fieldset class="input-block">
                                        <label class="secnd-opinion-label">Preferred Date<span style="color:red;">*</span></label>
                                    <div>
                                        <input type="hidden"  title="Preferred Date" name="surgeryRequests.preferredDate" id="preferredDate" />
                                        <input id="date_timepicker_start" class="form-control" type="text" value="" placeholder="From" readonly required="required" pattern="\d{1,2}/\d{1,2}/\d{4}">
                                    </div>
                                </fieldset>
                                </div>
                                    <div class="col-md-6 col-sm-6 no-pad">
                                    <fieldset class="input-block">
                                        <div>
                                            <input id="date_timepicker_end" class="form-control hide" style="margin-top: 18px;" type="text"  placeholder="End" readonly required="required" pattern="\d{1,2}/\d{1,2}/\d{4}">
                                        </div>
                                    </fieldset>
                                </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="">
                            <div class="col-md-6 col-sm-6 no-pad">
                                <fieldset class="input-block">
                                    <label class="secnd-opinion-label">Preferred Surgeon</label>
                                    <textarea title="Preferred Surgeon" class="form-control keyup-fake" name="surgeryRequests.surgeon" id="surgeon" <%--onblur="textareaValidation('surgeon')"--%>></textarea>
                                </fieldset>
                            </div>
                            <div class="col-md-6 col-sm-6 no-pad">

                                <div id="showInsField" class="hide">
                                    <div class="">
                                        <div class="col-md-6 col-sm-6 no-pad">
                                            <fieldset class="input-block">
                                                <label class="secnd-opinion-label">Insurance Type</label>
                                                <input type="text" class="form-control" name="surgeryRequests.insuranceType"/>
                                            </fieldset>
                                        </div>
                                        <div class="col-md-6 col-sm-6 no-pad ">
                                            <fieldset class="input-block">
                                                <label class="secnd-opinion-label">Coverage Limit</label>
                                                <input type="text" class="form-control" name="surgeryRequests.coverageLimit" maxlength="8" pattern="^[0-9]+$" title="numbers with max 8 digits"/>
                                            </fieldset>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="">
                            <div class="col-md-12 col-sm-12 form-group">
                                <label>
                                    <input type="checkbox" name="surgeryRequests.ifAnyInsurance" id="ifAnyInsurance"> Insurance (if any)
                                </label>
                            </div>
                        </div>
                    </div>
                        <div class="row">
                            <div class="float-id-upoad">
                                <button type="button" class="image-upload imguploadauto saveb editbasicinfo submit_btn start" id="shareRecordsPopup"><i class="icon-search-circled"></i>Share Files</button>
                                <button type="submit" class=" saveb editbasicinfo-vitals submit_btn">Submit request</button>
                            </div>
                        </div>
                </div>
            </div>
        </form>
    </div>
</div>

</div>
<!--/ .container-->
</div>
</div>

<div id="recordUploadPopup">

</div>





