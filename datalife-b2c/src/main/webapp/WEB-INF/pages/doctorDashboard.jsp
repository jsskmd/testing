<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--[if lte IE 8]> <html class="ie8 no-js" lang="en"> <![endif]-->
<!--[if IE 9]> <html class="ie9 no-js" lang="en-US"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html>
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
    <link rel="stylesheet" type="text/css" href="/css/ie8.css"/>
    <![endif]-->
    <!--Meta Tag-->
    <link href="resources/css/home/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="resources/css/messenger.css">
    <link rel="stylesheet" href="resources/css/cssfiles/blueimp-gallery.min.css">
    <link rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload.min.css">
    <link rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload-ui.min.css">
    <noscript>
        <link rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload-noscript.min.css">
    </noscript>
    <noscript>
        <link rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload-ui-noscript.min.css">
    </noscript>

    <link rel="stylesheet" href="resources/css/jquery-ui-timepicker-addon.min.css">
    <link rel="stylesheet" href="resources/css/jquery-ui.min.css">
    <link rel="stylesheet" href="resources/css/style.min.css">

    <link rel="stylesheet" href="resources/css/jquery.datetimepicker.min.css">
    <link rel="stylesheet" href="resources/css/site.min.css">
    <link rel="stylesheet" href="resources/css/dataTables.responsive.min.css">
    <!--Main Menu File-->
    <link rel='stylesheet' href="resources/css/fonts.css" type='text/css'>
    <!--Main Menu File-->

    <script type="text/javascript" src="resources/jquery/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="resources/jsplugins/jquery-ui.js"></script>
    <script type="text/javascript" src="resources/jquery/scripts.js"></script>
    <%--<script type="text/javascript" src="resources/jsplugins/build/intlTelInput.min.js"></script>--%>
    <script type="text/javascript" src="resources/js/server/common.js"></script>
    <script type="text/javascript" src="resources/js/server/clinicDoctAppt.js"></script>
    <script type="text/javascript" src="resources/js/server/doctorDashboard.js"></script>
    <script type="text/javascript" src="resources/js/server/doctorMessenger.js"></script>
    <script type="text/javascript" src="resources/jsplugins/popupbox.js"></script>

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
                $("#getProfilePtientImage,#getProfilePtientImage_mob").attr("src", '${imageUrl}');
            }
            count();
        });

            function count() {
                $('#messages_count').load('api/user/doctor/count/${userId}', function (data) {

                    if (data == 0) {
                        $("#messages_count").text('');
                    } else if (data == 1) {
                        $("#messages_count").text(data + "new message");
                    } else {
                        $("#messages_count").text(data + " new messages");
                    }
                    $(this).unwrap();
                });
            }

            // This will run on page load
            setInterval(function () {
                count(); // this will run after every 5 seconds
            }, 300000);
    </script>
</head>
<body>

<div class="topbar">
    <div class="container">
        <div class="top-info-contact pull-left col-md-6 col-sm-6 ">Call Us Today 94498-11444 | <a href="mailto:support@datalife.in">support@datalife.in</a></div>
        <div class="col-md-6 col-sm-6 hide" id="choose_clinic">
            <ul class="messanger_demo ">
                <li class="col-md-6 col-sm-12 adding_allign"  style="float:right;">
                    <fieldset style="text-align: left;" class="input-block">

                        <div class="dropdown">
                            <select id="select_clinic" class="dropdown-select">
                                <option selected="selected" value="" disabled="">--- Choose Clinic ---</option>
                                <c:forEach var="clinic" items="${clinics}">
                                    <option value="${clinic.key}">${clinic.value}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </fieldset>
                </li>
            </ul>
        </div>
    </div>
</div>

<div class="wsmenucontainer clearfix">
    <div class="wsmenuexpandermain slideRight"><a id="navToggle" class="animated-arrow slideLeft"><span></span></a></div>
    <div class="wsmenucontent overlapblackbg"></div>
    <div class="header">
        <div class="container-demo-index">
            <div class="container no-padding">
                <div class="">
                    <div class="logo"><a href="/emr"> <a href="/emr">
                        <h1><img src="resources/images/datalifelogo.png" alt="datalife"></h1>
                    </a></a></div>
                    <div class="nav-top clearfix">
                        <div class="user-deatils">
                            <ul>
                                <li>
                                    <div class="patient-details">
                                        <h1 class="userName" id="userName"> ${userName}</h1>
                                        <span class="patient-id"> DataLife ID :<span id="userId">${userId}</span></span>
                                    </div>
                                    <input type="hidden" id="monitored" value=false>
                                    <input type="hidden" id="grantPermission" value="true">
                                    <input type="hidden" id="granted" value="true">
                                    <input type="hidden" value="${request.getContextPath()}/emr/api/user/patient/userthumbnail/" id="imageUrl">
                                <input type="hidden" id="myContextPath" value="${pageContext.request.contextPath}">
                                    <input type="hidden" name="role" id="role" value="${role}"/>
                                    <div class="patient-image">
                                        <div class="image-upload" id="patientImageUpload">
                                            <img id="getProfilePtientImage" src="resources/images/1.png"/>
                                            <%--<jsp:include page="/profileImage"></jsp:include>--%>
                                        </div>
                                    </div>
                                </li>
                                <li class="popbox" style="width: 188px;"><a class="open expander"></a>

                                <div class="">
                                    <div style="display: none; top: 50px; left: 20px;" class="box">
                                        <div style="left: 150px;" class="arrow"></div>
                                        <div style="left: 150px;" class="arrow-border"></div>
                                        <ul>
                                            <li><a class="logout" id="logout"><i class=" icon-logout-1"></i><span>Sign out</span></a></li>
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
    <div id="nav" class="affix-top">
        <div class="nav-width">
            <!--Menu HTML Code-->
            <nav class="wsmenu slideLeft clearfix">
                <ul class="mobile-sub wsmenu-list hide docotnav " id="headerBar">
                    <li style="display:none;" class="items-for-navs">
                        <jsp:include page="/profileImage"></jsp:include>
                        <h1 class="userName info-mobilenav">${userName}</h1>
                        <span class="patient-id warpper"> DataLife ID :<span id="userId_mob">${userId}</span></span>
                    </li>
                        <li><a class="active  doc_headers" id="appointments"><i class="icon-calendar"></i>&nbsp;&nbsp;Appointments</a></li>
                        <li><a class=" doc_headers" id="doctor_settings"><i class="icon-cog-1 "></i>&nbsp;&nbsp;Settings</a></li>
                        <li><a class=" doc_headers" id="doctor_patients"><i class="icon-group "></i>&nbsp;&nbsp;Patients <span class="wsmenu-arrow fa fa-angle-down menuhide"></span></a>

                            <ul class="wsmenu-submenu">
                                <li><a class="doctor_patients" name="patient_list">View</a></li>
                                <li><a class="doctor_patients" name="add_patient">Register</a></li>
                                <li><a class="doctor_patients" name="search_patient">Search</a></li>
                            </ul>
                        </li>
                        <li><a class=" doc_headers" id="doctor_profile"><i class="icon-chart-pie "></i>&nbsp;&nbsp;Profile</a></li>
                        <li class="sign-out-demo" style="display:none;">
                            <div class="book-an-appointment"><a id="logout1" class="btn btn-success btn-14 saveb editbasicinfo-book"><i class="icon-login hideicon"></i>Sign out</a></div>
                        </li>
                    </ul>
                </nav>
            </div>
            <!--Menu HTML Code-->
        </div>
    </div>

    <div id="ajaxloaddiv">
        <div class="wrapper">
            <div class="form-container">
                 <div id="tmm-form-wizard" class="container substrate">
                    <div class="row">
                <div class="col-xs-12">
                    <h2 class="form-login-heading">Welcome Dr. ${userName} ! </h2>
                </div>
            </div>
                    <div class="row stage-container doctor-timeslot-info">
                <div class="doctor-timeslot-discription">
                    <ul class="doctor-list">
                        <li><span class="first-slot"> 1 </span>Choose Clinic <span><i class="icon-right-dir"></i></span></li>
                    </ul>
                </div>
            </div>
                    <div id="select_clinicOnDocDshb" class="dailog-show">
                    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-popup selectclinicpopup margintop-20">
                        <div class="confirmpadding">
                            <form action="/">
                                <div class="form-wizard">
                                    <div class="">
                                        <div class="row">
                                            <div class="col-md-12 no-padding">
                                             <%--   <div class="col-md-4 search"><img src="resources/images/helath-logo.png" alt="Search-doctor"/>
                                                </div>--%>
                                                <div class="col-md-12  no-pad search">
                                                    <fieldset class="input-block">
                                                        <label>Select Clinic</label>

                                                        <div class="dropdown">
                                                            <select class="dropdown-select" id="select_homeclinic">
                                                                <option disabled="" value="" selected="">--- Select ---</option>
                                                                <c:forEach var="clinic" items="${clinics}">
                                                                    <option value="${clinic.key}">${clinic.value}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                        <!--/ .tooltip-->
                                                    </fieldset>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--/ .row-->
                                <!--/ .form-wizard-->
                            </form>
                        </div>
                    </div>
                </div>
                </div>
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
                    <p class="col-xs-12 col-md-5 no-pad text-right ">Copyright 2016 Datalife | All Rights Reserved </p>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="successMessages"></div>

<div id="new_messenger" style="margin-top:50px">
    <div class=" ">
        <div class="row chat-window col-xs-5 col-md-3" id="chat_window_1" style="margin-left:10px;">
            <div class="col-xs-12 col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading top-bar">
                        <div class="col-md-8 col-xs-8" id="doctor_messenger">
                            <a style="float:left;" ><h3 class="panel-title"><i class="icon-chat"></i>Messenger</h3>
                                <span id="messages_count" style="color: green;"></span></a>
                        </div>
                        <div class="col-md-4 col-xs-4" style="text-align: right;">
                            <a><span id="minim_chat_window"
                                     class="glyphicon glyphicon-plus icon_minim panel-collapsed"></span></a>
                        </div>
                    </div>
                    <div id="collapse">
                        <div id="show_names" style="min-height: 200px;" class="hide">
                            <ul id="visitedPatientsList"></ul>
                        </div>
                        <div id="show_messages" class="hide">
                            <div id="msg_fullname"></div>
                            <input type="hidden" id="msg_userId"/>

                            <div class="panel-body msg_container_base" id="view_messenger"></div>
                            <div class="panel-footer">
                                <form name="message" method="post" id="senddMessageForm">
                                    <input type="hidden" name="patientId" id="msg_patientId"/>
                                    <input type="hidden" name="doctorId" id="doctorId" value="${userId}"/>
                                    <input type="hidden" name="sent" value="false"/>
                                    <div class="input-group">
                                        <input type="text" class="form-control input-sm chat_input" placeholder="Write your message here..." id="message" name="message" autocomplete="off"/>
                                        <span class="input-group-btn">
                                            <button type="submit" class="btn btn-success btn-sm" id="btn-chat">Send</button>
                                        </span>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="apptCancelCnfmPopup">
    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-smpopup">
        <div class="cancel-close"></div>
        <div class="confirmappointemnt">DataLife Confirmation</div>
        <div class="confirmpadding">
            <div id="bodypsv " class="dywcpopup" style="padding: 15px !important;">
                <div class="note">Do you want to continue?
                </div>
                <button type="button" class="cancelapp View" id="Yes">Yes</button>
                <button type="button" class="cancelapp share" id="No">No</button>
            </div>
        </div>
    </div>

</div>
<div id="selectClinicPopup">
    <div id="confirmAppForm" class="form-wrapper ">
    <div class="cancel-clinic"></div>
    <div class="confirmpadding">
        <div class="confirm-title"><h3 class="dat-heading"  style="border-bottom: 1px solid #000;">DATALIFE</h3></div>
        <div class="confirm-content"><p class="confirm-paragraph">Select clinic to continue...</p></div>
        <div class="sign-to-account"><a class="sign-again ok_btn">Ok</a></div>
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
        <%--   <a href="/">
               <div class="cancel_verification"></div>
           </a>--%>
        <div class="hide" id="cancel_verify"></div>
        <div id="confirmMessage" class="hide"></div>
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
                            <%-- <button type="button" class="saveb editbasicinfo" id="cancel-email">
                                 Cancel
                             </button>--%>
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
                                <%--<button type="button" class="saveb editbasicinfo" id="cancel-otp">
                                    Cancel
                                </button>--%>
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
                                <%--<button type="button" class="saveb editbasicinfo" id="cancel-authenticate">
                                    Cancel
                                </button>--%>
                                <button type="submit" class="saveb editbasicinfo-vitals submit_btn"
                                        id="ver_authenticate">Authenticate
                                </button>
                                <a href="/emr" id="ver_refresh" style="display: none;    margin-left: 28%;">To access click
                                    on
                                    <button type="button" class="saveb editbasicinfo-vitals"
                                            style="    margin: 10px 35%;">Refresh
                                    </button>
                                </a>

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

<div id="s_successMessage">
    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-popup" style="max-width: 500px !important;min-height: 45px;    position: absolute;
    top: 45%;">
        <div id="cancel-common"></div>
        <div class="confirmpadding">
            <div id="s_confirmMessage" class="s_message"></div>
            <div id="s_errorMessage" class="s_message"></div>
        </div>
    </div>
</div>


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
<script src="resources/jsplugins/jquery.nicescroll.js"></script>

<%--<script src="resources/js/server/messenger.js"></script>--%>
</body>
</html>
