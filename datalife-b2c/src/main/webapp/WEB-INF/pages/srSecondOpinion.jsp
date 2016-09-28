
<meta name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<link rel="stylesheet" href="resources/css/jquery.datetimepicker.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/site.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/dataTables.responsive.min.css">

<script src="resources/jsplugins/jquery.datetimepicker.min.js"></script>
<script type="text/javascript" src="resources/jsplugins/site.js"></script>
<script type="text/javascript" src="resources/jsplugins/dataTables.responsive.min.js"></script>

<script src="resources/js/server/srSecondOpinion.js"></script>
<script type="text/javascript" src="resources/js/server/srcommon.js"></script>


<div class="wrapper">
    <div class="form-container">
    <div id="tmm-form-wizard" class="container substrate">
    <div class="row clr form-login-heading">
    <div class="col-md-6 col-sm-6 no-pad"><h2 class="app_for_patients_cls">Second Opinion Doctor </h2></div>
    <div class=" right-document "> </div>
</div>

    <div id="dynamic_div">
    <input type="hidden" id="role" value="ROLE_REFERRALDOCTOR"/>
    <div>
        <form name="serviceRequests" id="secondOpinionForm" method="post" enctype="application/json">
            <div class="row">
                <div class="col-md-8">
                    <div class="form-header">
                        <div class="form-title form-icon title-icon-lock"><b>Fill the Details to get Second Opinion</b></div>
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
                <input type="hidden" name="patientId" id="referral_userId">
                <input type="hidden" name="serviceType" value="secondOpinion" id="sendType">
                <input type="hidden" name="secondOpnRequests.recordIds" id="recordIds">
                    <div id="message"></div>
                    <div class="" >
                        <div class="row">
                            <div class="col-md-12 no-padding">
                                <div class="col-md-3 col-sm-3 no-pad">
                                    <fieldset class="input-block">
                                        <label class="secnd-opinion-label">Specialty<span style="color:red;">*</span></label>
                                        <div class="dropdown">
                                            <select class="select-specality dropdown-select" id="speciality" name="secondOpnRequests.speciality" required="">
                                            </select>
                                        </div>
                                    </fieldset>
                                </div>
                                <div class="col-md-3 col-sm-3  no-pad">
                                    <fieldset class="input-block">
                                        <label class="secnd-opinion-label ">Priority<span style="color:red;">*</span></label>
                                        <div class="dropdown">
                                            <select class="select-specality dropdown-select " name="secondOpnRequests.priority" required="" >
                                                <option value='' disabled selected>Select Priority </option>
                                                <option class="high-pri">High</option>
                                                <option class="medium-pri ">Moderate</option>
                                                <option class="low-pri ">Low</option>
                                            </select>
                                        </div>
                                    </fieldset>
                                </div>
                                <div class="col-md-3 col-sm-3  no-pad">
                                    <fieldset class="input-block">
                                        <label>Preferred Country<span style="color:red;">*</span></label>
                                        <div class="dropdown">
                                            <select class="dropdown-select" id="providerCountry" required="" name="secondOpnRequests.country">
                                            </select>
                                        </div>
                                    </fieldset>
                                </div>

                                <div class="col-md-3 col-sm-3 no-pad">
                                    <fieldset class="input-block">
                                        <label>Preferred City<span style="color:red;">*</span></label>
                                            <input type="text" required=""  class="form-control" name="secondOpnRequests.city" id="providerCity" placeholder="Preferred City" pattern="^[a-zA-Z ]+$"/>
                                    </fieldset>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="row">
                            <div class=" ">
                                <div class="col-md-6 col-sm-6 no-pad">
                                    <fieldset class="input-block">
                                        <label class="secnd-opinion-label">Reason<span style="color:red;">*</span></label>
                                        <textarea class="text-page keyup-fake" name="secondOpnRequests.reason" required="" id="pwd"></textarea>
                                    </fieldset>
                                </div>
                                <div class="col-md-6 col-sm-6 no-pad">
                                    <fieldset class="input-block">
                                        <label class="secnd-opinion-label">Description</label>
                                        <textarea class="text-page keyup-fake" name="secondOpnRequests.description" ></textarea>
                                    </fieldset>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="float-id-upoad">
                                <button type="button" class="image-upload imguploadauto saveb editbasicinfo submit_btn start" id="shareRecordsPopup"><i class="icon-search-circled"></i>Share Records
                                </button>
                                <button type="submit" class=" saveb editbasicinfo-vitals submit_btn">Submit request</button>
                            </div>
                        </div>
                    </div>
            </div>
        </form>
    </div>
</div>
<!--/ .form-wizard-->
</div>
<!--/ .container-->
</div>
</div>

<div id="pdfdisplay">
    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-popup" style="max-width: 800px !important;">
        <div id="cancel-common"></div>
        <div class="confirmpadding">
            <iframe src="" id="iframe" class="preview-iframe"></iframe>
            <img src="" id="imgiframe" class="preview-iframe"/>
        </div>
    </div>
</div>

<div id="recordUploadPopup">

</div>





