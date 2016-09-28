<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!--[if lte IE 8]>              <html class="ie8 no-js" lang="en">     <![endif]-->
<!--[if IE 9]>					<html class="ie9 no-js" lang="en-US">  <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html >
<!--<![endif]-->
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>DataLife</title>
    <meta charset="UTF-8">
    <!--[if lt IE 9]>
    <script src="https://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="shortcut icon" type="image/x-icon" href="resources/images/favicon.ico">
    <!--[if lt IE 9]>
    <link rel="stylesheet" type="text/css" href="resources/css/ie8.css" />
    <![endif]-->
    <!--Meta Tag-->

    <link rel="stylesheet" href="resources/css/cssfiles/blueimp-gallery.min.css">
    <link rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload.min.css">
    <link rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload-ui.min.css">
    <noscript>
        <link rel="stylesheet"
              href="resources/css/cssfiles/jquery.fileupload-noscript.min.css">
    </noscript>
    <noscript>
        <link rel="stylesheet"
              href="resources/css/cssfiles/jquery.fileupload-ui-noscript.min.css">
    </noscript>

    <link rel="stylesheet" href="resources/css/jquery-ui-timepicker-addon.min.css">
    <link rel="stylesheet" href="resources/css/site.min.css">
    <link rel="stylesheet" href="resources/css/dataTables.responsive.min.css">
    <link rel="stylesheet" href="resources/css/jquery-ui.min.css">
    <link rel="stylesheet" href="resources/css/style.min.css">
    <link rel="stylesheet" href="resources/css/jquery.datetimepicker.min.css">

    <!--Main Menu File-->
    <link href="resources/css/fonts.css" rel='stylesheet' type='text/css'>    <!--Main Menu File-->

    <script type="text/javascript" src="resources/jquery/jquery-1.11.2.min.js"></script>

    <script type="text/javascript" src="resources/jsplugins/site.js"></script>
    <script type="text/javascript" src="resources/jsplugins/dataTables.responsive.min.js"></script>
    <script type="text/javascript" src="resources/jquery/scripts.js"></script>

    <script type="text/javascript">
        function confirmBackspaceNavigations() {
            var backspaceIsPressed = false;
            $(document).keydown(function (event) {
                if (event.which == 8) {
                    backspaceIsPressed = true
                }
            });
            $(document).keyup(function (event) {
                if (event.which == 8) {
                    backspaceIsPressed = false
                }
            });
            $(window).on('beforeunload', function () {
                if (backspaceIsPressed) {
                    backspaceIsPressed = false;
                    return "Are you sure you want to leave this page?"
                }
            })
        }

        $(document).ready(function () {
            $("#verfifyEmail").val('${email}');
            $("#verfifyMobile").val('${mobilePhone}');

            var validImage = '${validImage}';
            $("#validImage").val(validImage);
            if (validImage == 'true') {
                $("#getProfilePtientImage").attr("src", '${imageUrl}');
            }
        });
    </script>
</head>
<body>
<div class="topbar">
    <div class="container">
        <div class="top-info-contact pull-left col-md-6">Call Us Today 944-981-1444  | <a href="mailto:support@datalife.in">support@datalife.in</a> </div>

    </div>
</div>
<div class="wsmenucontainer clearfix">
<div class="wsmenuexpandermain slideRight"><a id="navToggle" class="animated-arrow slideLeft"><span></span></a></div>
<div class="wsmenucontent overlapblackbg"></div>
<div class="header">
    <div class="container-demo-index">
        <div class="container no-padding">
            <div class="">
                <div class="logo"> <a href="/"> <a href="/">
                    <h1> <img src="resources/images/datalifelogo.png" alt="datalife"></h1>
                </a></a> </div>
                <div class="nav-top clearfix">
                    <div class="user-deatils">
                        <ul>
                            <li>
                                <div class="patient-details">
                                    <h1 class="userName"> ${userName}</h1>
                                    <span class="patient-id"> DataLife ID :<span id="userId">${userId}</span></span></div>

                                <input type="hidden" value="${request.getContextPath()}/emr/api/user/patient/userthumbnail/" id="imageUrl">
                                <div class="patient-image"  >
                                    <div class="image-upload" id="patientImageUpload">
                                        <img id="getProfilePtientImage" src="<%=request.getContextPath() %>/resources/images/1.png"/>
                                    </div>
                                </div>
                            </li>
                            <li class="popbox" style="width: 188px;"><a class="open expander"></a>
                                <div class="">
                                    <div style="display: none; top: 50px; left: 20px;" class="box">
                                        <div style="left: 150px;" class="arrow"></div>
                                        <div style="left: 150px;" class="arrow-border"></div>
                                        <ul>
                                            <li><a class="logout" id="logout" ><i class=" icon-logout-1"></i><span>Sign out</span></a> </li>
                                        </ul>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--Select Color -->
    <!--Select Color -->
    <div id="nav" class="affix-top">
        <div class="nav-width">
            <!--Menu HTML Code-->
            <nav class="wsmenu slideLeft clearfix">
                <ul class="mobile-sub wsmenu-list" id="headerBar">
                    <li style="display:none;" class="items-for-navs">
                        <jsp:include page="/profileImage"></jsp:include>
                        <h1 class="userName info-mobilenav">${userName}</h1>
                        <span class="patient-id warpper"> DataLife ID :<span id="userId_mob">${userId}</span></span></li>
                    <li><a class="active" id="search_refpatient"><i class="icon-group hideicon"></i>&nbsp;&nbsp; Patient Search </a></li>
                    <li><a  id="doctor_profile"><i class="icon-chart-pie hideicon"></i>&nbsp;&nbsp;Profile </a> </li>
                    <li class="sign-out-demo" style="display:none;">
                        <div class="book-an-appointment"><a id="logout2" class="btn btn-success btn-14 saveb editbasicinfo-book"><i
                                class="icon-login hideicon"></i>Sign out</a></div>
                    </li>
                </ul>
            </nav>
        </div>
        <!--Menu HTML Code-->
    </div>
</div>

<div id="ajaxloaddiv">

<div class="wrapper">
<div class="form-container"  >
<div id="tmm-form-wizard" class="container substrate">
<div class="row">
    <div class="col-xs-12">
        <h2 class="form-login-heading">Welcome Dr. ${userName} ! </h2>
    </div>
</div>
<!--/ .row-->

<div class="row services-list">
    <div class="col-md-6 col-sm-6 no-padding"> <a href="#">
        <div class="col-md-4 ">
            <div class="services"> <img src="resources/images/second-opinion.png" alt="second-opinion" class="appointment-service-info" />
                <div class="secondopnion">Second Opinion doctor</div>
            </div>
        </div>
    </a> <a href="#">
        <div class="col-md-4 ">
            <div class="services"> <img src="resources/images/online-doc.png" alt="online-doctor" class="appointment-service-info" />
                <div class="secondopnion">Online Doctor Facility</div>
            </div>
        </div>
    </a> <a href="#">
        <div class="col-md-4 ">
            <div class="services"> <img src="resources/images/emergency.png" alt="emergency"  class="appointment-service-info"/>
                <div class="secondopnion">Non Emergency Patient Transport</div>
            </div>
        </div>
    </a> </div>
    <div class="col-md-6 col-sm-6 no-padding"> <a href="#">
        <div class="col-md-4 ">
            <div class="services"> <img src="resources/images/diagonstic.png" alt="second-opinion" class="appointment-service-info" />
                <div class="secondopnion">Diagnostic Services</div>
            </div>
        </div>
    </a> <a href="#">
        <div class="col-md-4 ">
            <div class="services"> <img src="resources/images/pharmacy.png" alt="second-opinion" class="appointment-service-info" />
                <div class="secondopnion">Pharmacy</div>
            </div>
        </div>
    </a> <a href="#">
        <div class="col-md-4 ">
            <div class="services"> <img src="resources/images/medical-tourism.png" alt="second-opinion" class="appointment-service-info" />
                <div class="secondopnion">Medical Tourism</div>
            </div>
        </div>
    </a> </div>
</div>
<%--<div class="doctor-full-slots" style="display:none;">
    <div class="form-wizard doctor-info">
        <div class="row">
            <div class="doctor-timeslot-discription">
                <div class="col-md-6">
                    <div class="col-md-3 image">
                        <div class="doc-image"><img src="/resources/images/doc-image.png" alt="doc" /></div>
                    </div>
                    <div class="col-md-4 demo">
                        <div class="doc-info">
                            <div class="doc-name">Dr. Simon</div>
                            <div class="doc-database">Dentist</div>
                            <div class="doc-database">Polyclinic</div>
                            <div class="doc-database"> 03 Years of Experience</div>
                            <div class="doc-database">LangFord Town, United State.</div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-sm-6">
                    <div class="doct-timeslot-demo">
                        <div class="week-slot">MON - FRI [10:00 AM - 01:00 PM, 04:00 PM - 08:00 PM]</div>
                        <div class="week">SAT - SUN [10:00 AM - 01:00 PM]</div>
                        <div class="fees">Consultation Fee: 230 USD</div>
                        <input type="button" onClick="window.location='doctor-profile.html';" class="saveb editbasicinfo-doc" value="View Doctor Profile">
                        <input type="button" value="Fix an Appointment" class="saveb editbasicinfo-info" id="datepicker">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="content-index-demo">
        <div class="full-time-slots">
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
            <div class="form-wizard doctor-info">
                <div class="row">
                    <div class="slot-time">
                        <div class="demo-date">28 Jul 2015 (Tues)</div>
                    </div>
                    <div class="slot-time-demo">
                        <div class="demo-list">
                            <ul class="ulslots">
                                <li class="" ><a class="available" href="#">10:00 AM</a></li>
                                <li class=""><a href="#">10:20 AM</a></li>
                                <li><a href="#">10:40 AM</a></li>
                                <li class=""><a  href="#">11:00 AM</a></li>
                                <li><a href="#">11:20 AM</a></li>
                                <li><a href="#">11:40 AM</a></li>
                                <li><a  class="confirmApp 6"  href="#">12:00 PM</a></li>
                                <li class=""><a  class="confirmApp 7"  href="#">12:20 PM</a></li>
                                <li><a  class="confirmApp 8"  href="#">12:40 PM</a></li>
                                <li><a  class="confirmApp 9"  href="#">04:00 PM</a></li>
                                <li class=""><a  class="confirmApp 10"  href="#">04:20 PM</a></li>
                                <li><a  class="confirmApp 11"  href="#">04:40 PM</a></li>
                                <li><a  class="confirmApp 12"  href="#">05:00 PM</a></li>
                                <li><a  class="confirmApp 13"  href="#">05:20 PM</a></li>
                                <li><a class="confirmApp 14"  href="#">05:40 PM</a></li>
                                <li><a  class="confirmApp 15"  href="#">06:00 PM</a></li>
                                <li><a class="cancledslotclr" title="CANCELLED"  href="#">06:20 PM</a></li>
                                <li><a  class="confirmApp 17"  href="#">06:40 PM</a></li>
                                <li><a  class="confirmApp 18"  href="#">07:00 PM</a></li>
                                <li><a class="compltedslotclr" title="COMPLETED"  href="#">07:20 PM</a></li>
                                <li><a  class="confirmApp 20"  href="#">07:40 PM</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="form-wizard doctor-info">
        <div class="row">
            <div class="doctor-timeslot-discription">
                <div class="col-md-6">
                    <div class="col-md-3 image">
                        <div class="doc-image"><img src="/resources/images/doc-image.png" alt="doc" /></div>
                    </div>
                    <div class="col-md-4 demo">
                        <div class="doc-info">
                            <div class="doc-name">Dr.Samuel Paul</div>
                            <div class="doc-database">Cardiologist</div>
                            <div class="doc-database">Polyclinic</div>
                            <div class="doc-database"> 03 Years of Experience</div>
                            <div class="doc-database">LangFord Town, United State.</div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-sm-6">
                    <div class="doct-timeslot-demo">
                        <div class="week-slot">MON - FRI [10:00 AM - 01:00 PM, 04:00 PM - 08:00 PM]</div>
                        <div class="week">SAT - SUN [10:00 AM - 01:00 PM]</div>
                        <div class="fees">Consultation Fee: 230 USD</div>
                        <input type="button" onClick="window.location='doctor-profile.html';" class="saveb editbasicinfo-doc" value="View Doctor Profile">
                        <input type="button" value="Fix an Appointment" class="saveb editbasicinfo-info" id="datepicker-demo">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="content-index-demo-page" style="display:none">
        <div class="full-time-slots">
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
            <div class="form-wizard doctor-info">
                <div class="row">
                    <div class="slot-time">
                        <div class="demo-date">28 Jul 2015 (Tues)</div>
                    </div>
                    <div class="slot-time-demo">
                        <div class="demo-list">
                            <ul class="ulslots">
                                <li><a href="#">10:00 AM</a></li>
                                <li class=""><a href="#">10:20 AM</a></li>
                                <li><a href="#">10:40 AM</a></li>
                                <li class=""><a  href="#">11:00 AM</a></li>
                                <li><a href="#">11:20 AM</a></li>
                                <li><a href="#">11:40 AM</a></li>
                                <li><a  class="confirmApp 6"  href="#">12:00 PM</a></li>
                                <li class=""><a  class="confirmApp 7"  href="#">12:20 PM</a></li>
                                <li><a  class="confirmApp 8"  href="#">12:40 PM</a></li>
                                <li><a  class="confirmApp 9"  href="#">04:00 PM</a></li>
                                <li class=""><a  class="confirmApp 10"  href="#">04:20 PM</a></li>
                                <li><a  class="confirmApp 11"  href="#">04:40 PM</a></li>
                                <li><a  class="confirmApp 12"  href="#">05:00 PM</a></li>
                                <li><a  class="confirmApp 13"  href="#">05:20 PM</a></li>
                                <li><a  class="confirmApp 14"  href="#">05:40 PM</a></li>
                                <li><a  class="confirmApp 15"  href="#">06:00 PM</a></li>
                                <li><a class="cancledslotclr" title="CANCELLED"  href="#">06:20 PM</a></li>
                                <li><a  class="confirmApp 17"  href="#">06:40 PM</a></li>
                                <li><a  class="confirmApp 18"  href="#">07:00 PM</a></li>
                                <li><a class="compltedslotclr" title="COMPLETED"  href="#">07:20 PM</a></li>
                                <li><a  class="confirmApp 20"  href="#">07:40 PM</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>--%>
<!--/ .form-wizard-->
</div>
<!--/ .container-->
</div>
</div>


</div>

<div class="bottom-footer">
    <div class="container">
        <div class="row">
            <!--Foot widget-->
            <div class="col-xs-12 col-sm-12 col-md-12 foot-widget-bottom">
                <ul class="foot-menu col-xs-12 col-md-7 no-pad">
                    <li><a>Terms &amp; Conditions</a></li>
                </ul>
                <p class="col-xs-12 col-md-5 no-pad text-right ">Copyright 2015 Datalife | All Rights Reserved </p>
            </div>
        </div>
    </div>
</div>
</div>
<div id="successMessages"></div>

<div id="apptCancelCnfmPopup">
    <div id="confirmAppForm" class="form-wrapper ">
        <div class="cancel-close1"></div>
        <div class="confirmpadding">
            <div id="bodypsv">
                <div id="headingpsv">DataLife</div>
                <div class="note">Do you want to continue?
                </div>
                <button type="button" class="cancelapp View" id="Yes">Continue</button>
                <button type="button" class="cancelapp share" id="No">Cancel</button>
            </div>
        </div>
    </div>
</div>

<div id="imageuploadpatient">

    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-smpopup" style="max-width:450px">
        <div class="cancel-close"></div>
        <div class="confirmappointemnt">Profile Image Upload</div>
        <div class="confirmpadding" style="overflow: hidden;padding-bottom: 10px;">
            <div class="image-upload">

                <img id="sampleimg" src=""/>
                <%--  <jsp:include page="/profileImage"></jsp:include>--%>
            </div>
            <form class="profileImageUpload" name="user"
                  method="POST"
                  enctype="multipart/form-data"
                  data-upload-template-id="template-upload-7"
                  data-download-template-id="template-download-7">
                <!-- Redirect browsers with JavaScript disabled to the origin page -->
                <noscript><input type="hidden" name="redirect"
                                 value="https://blueimp.github.io/jQuery-File-Upload/">
                </noscript>
                <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->

                <input type="hidden" name="userId" id="uploadUserId" value="${userId}"/>
                <input type="hidden" name="sendType" value="profileImage"/>

                <div class="row fileupload-buttonbar">
                    <div class="col-lg-9">
                        <!-- The fileinput-button span is used to style the file input field as button -->
                <span class="btn btn-success fileinput-button">
                    <i class="icon-plus"></i>
                    <span>Choose Profile picture ...</span>
                    <input type="file" name="multiUploadedFileList">
                </span>


                        <%--<button type="button" class="btn btn-danger delete">
                            <i class="glyphicon glyphicon-trash"></i>
                            <span>Delete</span>
                        </button>
                        <input type="checkbox" class="toggle">--%>
                        <!-- The global file processing state -->
                        <span class="fileupload-process"></span>
                    </div>
                    <!-- The global progress state -->
                    <div class="col-lg-5 fileupload-progress fade">
                        <!-- The global progress bar -->
                        <div class="progress progress-striped active" role="progressbar"
                             aria-valuemin="0"
                             aria-valuemax="100">
                            <div class="progress-bar progress-bar-success"
                                 style="width:0%;"></div>
                        </div>
                        <!-- The extended global progress state -->
                        <div class="progress-extended">&nbsp;</div>
                    </div>
                </div>
                <!-- The table listing the files available for upload/download -->
                <table role="presentation" class="table table-striped hide">
                    <tbody class="files"></tbody>
                </table>

            </form>
            <div id="perrorMessage"></div>
        </div>
    </div>
</div>
<div id="verification">
    <div class="form-wrapper confirmAppForm class-smpopup" id="confirmAppForm" style="display: block;">
        <a href="/"><div class="cancel_verification"></div></a>
        <div id="confirmMessage"></div>
        <div class="confirmpadding">
            <div class="note"></div>
            <div class="form-wrapper">
                <div class="confirmappointemnt">
                    <h1> Datalife Verification</h1>
                </div>

                <div class="row smsplitcode">
                    <ul class="todayappointmentul">
                        <li style="border-right:none;" id="emailhead">
                            <div class="searchalign">
                                <div id="email" class="search-doc show_border_clinic"><i
                                        class="icon-mail-1"></i>
                                    Email-verification
                                </div>

                            </div>
                        </li>
                        <li style="border-right:none;" id="mobilehead">
                            <div class="searchalign">
                                <div id="mobile" class="ad-doc"><i class="icon-mobile"></i>Mobile
                                    verification
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>

                <div id="verifyConfirmMessage"></div>
                <div id="verifyErrorMessage"></div>
                <div class="data-verification" id="emailBody">
                    <form method="post" name="user" id="emailVerificationForm">
                        <fieldset class="input-block">

                            <input type="text" id="verfifyEmail" title="Search" required="required"
                                   placeholder="Enter Email Address" class="form-icon form-icon-user demo">
                            <!--/*<i class="icon-edit"></i> */-->
                            <button type="button" class="saveb editbasicinfo" id="cancel-email">
                                Cancel
                            </button>
                            <button type="submit" class="saveb editbasicinfo-vitals submit_btn">Verify</button>
                            <p class="sign_out"><a id="logout2" style="text-decoration: underline">Signout</a></p>
                        </fieldset>

                    </form>


                </div>
                <div id="mobileBody" class="hide">
                    <div class="basicdetails-modal mobile-verification" id="mobilediv">

                        <form method="post" name="user" id="mobileVerificationForm">
                            <fieldset class="input-block">

                                <input type="text" id="verfifyMobile" title="Search" required="required"
                                       placeholder="Enter Mobile Number" class="form-icon form-icon-user demo">
                                <!--/*<i class="icon-edit"></i> */-->
                                <button type="button" class="saveb editbasicinfo" id="cancel-otp">
                                    Cancel
                                </button>
                                <button type="submit" class="saveb editbasicinfo-vitals submit_btn" id="send-otp">Send
                                    OTP
                                </button>
                                <p class="sign_out"><a id="logout3" style="text-decoration: underline">Signout</a></p>
                            </fieldset>
                        </form>


                    </div>

                    <div class="basicdetails-modal mobile-verification-demo hide" id="otpdiv">
                        <form method="post" name="user" id="otpVerificationForm">
                            <fieldset class="input-block">

                                <input type="password" id="verifyOtp" title="Enter OTP" placeholder="Enter OTP"
                                       class="form-icon form-icon-user demo" required="required">
                                <!--/*<i class="icon-edit"></i> */-->
                                <button type="button" class="saveb editbasicinfo" id="cancel-authenticate">
                                    Cancel
                                </button>
                                <button type="submit" class="saveb editbasicinfo-vitals submit_btn" id="ver_authenticate">Authenticate
                                </button>
                                <a href="/" id="ver_refresh" style="display: none;    margin-left: 28%;">To access click on
                                    <button type="button" class="saveb editbasicinfo-vitals" style="    margin: 10px 35%;">Refresh
                                    </button></a>
                                <p class="sign_out"><a id="logout4" style="text-decoration: underline">Signout</a></p>
                            </fieldset>
                        </form>


                    </div>
                </div>
                <div class="note">Note: To continue please verify email & mobile number</div>
            </div>

        </div>
    </div>
</div>

<script src="resources/jsplugins/jquery-ui.js"></script>
<script type="text/javascript" src="resources/jsplugins/build/intlTelInput.min.js"></script>
<script type="text/javascript" src="resources/js/server/common.js"></script>
<script src="resources/jsplugins/jquery.datetimepicker.min.js"></script>
<script type="text/javascript" src="resources/js/server/consultation.js"></script>
<script type="text/javascript" src="resources/js/server/physicianDashboard.js"></script>
<script type="text/javascript" src="resources/jsplugins/popupbox.js"></script>

<!-- The template to display files available for upload -->
<script id="template-upload-7" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
        <td>
            <p class="size">Processing...</p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start">
                    <i class="icon-up-circled"></i>
                    <span>Start</span>
                </button>
            {% } %}
            {% if (!i) { %}

            {% } %}
        </td>
    </tr>
{% } %}









</script>
<!-- The template to display files available for download -->
<script id="template-download-7" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.vthumbnailUrl) { %}
                    <a href="{%=file.vurl+"&alf_ticket="+file.ticket%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.vthumbnailUrl+"&alf_ticket="+file.ticket%}"></a>
                {% } %}
            </span>
        </td>

        <td>
            <p class="name">
                {% if (file.vdownloadUrl) { %}
                    <a href="{%=file.vdownloadUrl+"&alf_ticket="+file.ticket%}" title="{%=file.fileName%}">{%=file.fileName%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">Error</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}

            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="icon-up-circled"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}

</script>

<!-- The template to display files available for upload -->
<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
<script src="resources/js/jsfiles/vendor/jquery.ui.widget.js"></script>
<!-- The Templates plugin is included to render the upload/download listings -->
<script src="resources/js/jsfiles/tmpl.min.js"></script>
<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
<script src="resources/js/jsfiles/load-image.all.min.js"></script>
<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script src="resources/js/jsfiles/canvas-to-blob.min.js"></script>
<!-- Bootstrap JS is not required, but included for the responsive demo navigation -->
<script src="resources/jsplugins/bootstrap.min.js"></script>
<!-- blueimp Gallery script -->
<script src="resources/js/jsfiles/jquery.blueimp-gallery.min.js"></script>
<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
<script src="resources/js/jsfiles/jquery.iframe-transport.js"></script>
<!-- The basic File Upload plugin -->
<script src="resources/js/jsfiles/jquery.fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script src="resources/js/jsfiles/jquery.fileupload-process.js"></script>
<!-- The File Upload image preview & resize plugin -->
<script src="resources/js/jsfiles/jquery.fileupload-image.js"></script>
<!-- The File Upload audio preview plugin -->
<script src="resources/js/jsfiles/jquery.fileupload-audio.js"></script>
<!-- The File Upload video preview plugin -->
<script src="resources/js/jsfiles/jquery.fileupload-video.js"></script>
<!-- The File Upload validation plugin -->
<script src="resources/js/jsfiles/jquery.fileupload-validate.js"></script>
<!-- The File Upload user interface plugin -->
<script src="resources/js/jsfiles/jquery.fileupload-ui.js"></script>
<!-- The main application script -->
<script src="resources/js/server/uploadmain.js"></script>
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
<!--[if (gte IE 8)&(lt IE 10)]-->
<script src="resources/js/jsfiles/cors/jquery.xdr-transport.js"></script>

</body>
</html>
