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
    <link rel="stylesheet" type="text/css" href="resources/css/ie8.css"/>
    <![endif]-->
    <!--Meta Tag-->
   <link href="resources/css/home/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="resources/css/messenger.css">

    <link type='text/css' rel="stylesheet" href="resources/css/cssfiles/blueimp-gallery.min.css"/>
    <link type='text/css' rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload.min.css"/>
    <link type='text/css' rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload-ui.min.css"/>
    <noscript>
        <link type='text/css' rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload-noscript.min.css"/>
    </noscript>
    <noscript>
        <link type='text/css' rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload-ui-noscript.min.css"/>
    </noscript>
    <link type='text/css' rel="stylesheet" href="resources/css/jquery-ui.min.css"/>

    <link type='text/css' rel="stylesheet" href="resources/css/site.min.css"/>
    <link type='text/css' rel="stylesheet" href="resources/css/dataTables.responsive.min.css"/>
    <link type='text/css' rel="stylesheet" href="resources/css/style.min.css"/>
    <!--Main Menu File-->
    <link type='text/css' href="resources/css/fonts.css" rel='stylesheet'>

    <script type="text/javascript" src="resources/jquery/jquery-1.11.3.min.js"></script>
   <%-- <script src="resources/jsplugins/build/intlTelInput.min.js"></script>--%>
    <script type="text/javascript" src="resources/jsplugins/typeahead.bundle.js"></script>
    <script type="text/javascript" src="resources/jsplugins/handlebars-v3.0.3.min.js"></script>


    <script type="text/javascript">
        $(document).ready(function () {

            var grant = ${grant};
            $("#verfifyEmail").val('${email}');
            $("#verfifyMobile").val('${mobilePhone}');
            var emailVerified = ${emailVerified};
            var mobileVerified = ${mobileVerified};

            if (grant) {
                if (!(emailVerified || mobileVerified)) {
                    $("#verification").addClass("dailog-show");
                    $("#content").addClass('hide');
                }
            } else {
                if (!emailVerified && !mobileVerified) {
                    $("#verification").addClass("dailog-show");
                    $("#content").addClass('hide');
                }
                else {
                    if (emailVerified && !mobileVerified) {

                        $("#verification").addClass("dailog-show");
                        $("#emailBody,#emailhead,#content").addClass("hide");
                        $("#mobileBody,#mobilehead").removeClass("hide");
                        $("#mobile").addClass("show_border_clinic");

                    }
                    if (mobileVerified && !emailVerified) {
                        $("#email").addClass("show_border_clinic");
                        $("#emailBody,#emailhead").removeClass("hide");
                        $("#mobileBody,#mobilehead,#content").addClass("hide");
                        $("#verification").addClass("dailog-show");
                    }
                }
            }

            var validImage = '${validImage}';
            $("#validImage").val(validImage);
            if (validImage == 'true') {
                $("#getProfilePtientImage,#getProfilePtientImage_mob").attr("src", '${imageUrl}');
            }
            count();
        });

        function count() {
            $('#messages_count').load('api/user/patient/count/${userId}', function (data) {
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
        }, 3000000);
    </script>
</head>
<body id="body">

<div id="content">
    <div class="topbar">
        <div class="container">
            <div class="top-info-contact pull-left ">Call Us Today 94498-11444 | <a
                    href="mailto:support@datalife.in">support@datalife.in</a></div>
        </div>
    </div>

    <div class="wsmenucontainer clearfix">
        <div class="wsmenuexpandermain slideRight">
            <a id="navToggle" class="animated-arrow slideLeft"><span></span></a>
        </div>
        <div class="wsmenucontent overlapblackbg"></div>
        <div class="header">
            <div class="container-demo-index">
                <div class="container no-padding">
                    <div class="">
                        <div class="logo"><a href="/"> <a href="/">
                            <h1><img src="resources/images/datalifelogo.png" alt="datalife">
                            </h1>
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
                                        <div class="patient-image">
                                            <div class="image-upload" id="patientImageUpload">
                                                <jsp:include page="/profileImage"></jsp:include>
                                            </div>
                                        </div>

                                    </li>
                                    <li class="popbox" style="width: 188px;"><a class="open expander"></a>
                                        <div class="">
                                            <div style="display: none; top: 50px; left: 20px;" class="box">
                                                <div style="left: 150px;" class="arrow"></div>
                                                <div style="left: 150px;" class="arrow-border"></div>
                                                <ul>
                                                    <li><a class="logout" id="logout"><i
                                                            class=" icon-logout-1"></i><span>Sign out</span></a>
                                                    </li>
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
                        <ul class="mobile-sub wsmenu-list" id="dashboard_menu">
                            <li style="display:none;" class="items-for-navs">
                                <jsp:include page="/profileImage"></jsp:include>
                                <h1 class="userName info-mobilenav">${userName}</h1>
                                <span class="patient-id warpper"> DataLife ID :<span id="userId_mob">${userId}</span></span>
                            </li>

                            <li><a id="appointments" class="active"><i class="icon-calendar hideicon"></i>&nbsp;&nbsp;Appointments
                            </a></li>
                            <li id="serviceRequestmenu">
                                <a id="serviceRequest"><i class="icon-chat hideicon"></i>&nbsp;&nbsp;Service Request <span class="wsmenu-arrow fa fa-angle-down menuhide"></span></a>
                                <ul class="wsmenu-submenu">
                                    <li><a class="not_available" id="surgeryReferral"> Surgery Referral</a></li>
                                    <li><a class="not_available" id="secondOpinion">Second Opinion Doctor</a></li>
                                    <li><a class="not_available" id="teleConsultation">Teleconsultation</a></li>
                                    <li><a class="not_available" id="diagnosticLabs"> Lab Services</a></li>
                                    <li><a class="not_available" id="pharmacy"> Pharmacy</a></li>
                                    <li><a class="not_available" id="rehab"> Physiotherapy & Rehab</a></li>

                                    <%--    <li><a class="not_available"> Non-Emergency Patient Transport</a></li>
                                        <li><a class="not_available"> Insurance Renewals</a></li>
                                        <li><a class="not_available"> Medical Tourism</a></li>--%>

                                </ul>
                            </li>
                            <li><a href="javascript:void(0)"><i class="icon-folder hideicon"></i>&nbsp;&nbsp;My Records </a>

                                <ul class="wsmenu-submenu">
                                    <li><a class="not_available" id="showUploadPopup"> Upload Records</a></li>
                                    <li><a class="not_available" id="records" > View Records</a></li>

                                </ul>
                            </li>
                            <li><a id="vitals"><i class="icon-medkit hideicon"></i>&nbsp;&nbsp;My Vitals </a></li>
                            <li><a id="patient_profile"><i class="icon-chart-pie-1 hideicon"></i>&nbsp;&nbsp;My Demographics</a>
                            </li>
                            <li><a id="bills"><i class="icon-rupee hideicon"></i>&nbsp;&nbsp;My Medical Bills </a></li>
                            <li><a id="new_consultation"><i class="icon-doc hideicon"></i>&nbsp;&nbsp;New Consultation </a>
                            </li>
                            <li class="sign-out-demo" style="display:none;">
                                <div class="book-an-appointment"><a id="logout1" class="btn btn-success btn-14 saveb editbasicinfo-book"><i
                                        class="icon-login hideicon"></i>Sign out</a></div>
                            </li>
                        </ul>
                    </nav>
                </div>
                <!--Menu HTML Code-->
            </div>
        </div>
        <div class="container">
            <div id="successMessages"></div>
            <div id="messages" class="hide"></div>
            <div id="errormessages" class="hide"></div>
        </div>

        <div id="ajaxloaddiv"></div>

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

</div>



<div id="new_messenger" style="margin-top:50px">
    <div class=" ">
        <div class="row chat-window col-xs-5 col-md-3" id="chat_window_1" style="margin-left:10px;">
            <div class="col-xs-12 col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading top-bar">
                        <div class="col-md-8 col-xs-8"  id="messenger_but">
                            <h3 class="panel-title">  <a href="#"> <i class="icon-chat"></i>Messenger <span id="messages_count" style="color: green;" class="spancount"></span> </a></h3>
                        </div>
                        <div class="col-md-4 col-xs-4" style="text-align: right;">
                            <a class="arrowchatcolor"><span id="minim_chat_window" class="glyphicon glyphicon-plus icon_minim panel-collapsed"></span></a>
                        </div>
                    </div>
                    <div id="collapse"><div id="show_names" style="min-height: 200px;" class="hide">
                        <ul id="visitedDoctorsList"></ul>
                    </div>
                        <div id="show_messages" class="hide">
                            <div id="msg_fullname"></div>
                            <input type="hidden" id="msg_userId"/>
                            <div class="panel-body msg_container_base" id="view_messenger">

                            </div>
                            <div class="panel-footer">

                                <form name="message" method="post" id="sendMessageForm">
                                    <input type="hidden" name="patientId" id="patientId" value="${userId}"/>
                                    <input type="hidden" name="doctorId" id="doctorId"/>
                                    <input type="hidden" name="sent" value="true"/>
                                    <div class="input-group">
                                        <input type="text" class="form-control input-sm chat_input" placeholder="Write your message here..." id="message" name="message" autocomplete="off" />
                        <span class="input-group-btn">
                        <button type="submit" class="btn btn-success btn-sm" id="btn-chat">Send</button>
                        </span>
                                    </div>
                                </form>
                            </div></div></div>
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
                <div class="note">Do you want to cancel appointment?
                </div>
                <button type="button" class="cancelapp View" id="Yes">Yes</button>
                <button type="button" class="cancelapp share" id="No">No</button>
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
            <form class="profileImageUpload" name="user" method="POST" enctype="multipart/form-data" data-upload-template-id="template-upload-7"
                  data-download-template-id="template-download-7">
                <!-- Redirect browsers with JavaScript disabled to the origin page -->
                <noscript><input type="hidden" name="redirect" value="https://blueimp.github.io/jQuery-File-Upload/">
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
            <div id="perrorMessage" class="hide"></div>
        </div>
    </div>
</div>

<div id="identityUplaod">
    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-smpopup">
        <div class="cancel-common"></div>
        <div class="confirmappointemnt">Identity Card</div>

        <div class="confirmpadding" style="overflow: hidden;" id="upload_id">
            <div class="image-upload">

            </div>
            <div class="note">Note: Save ID details to upload.</div>
            <form class="IdentityImageUpload" name="user" method="POST" enctype="multipart/form-data" data-upload-template-id="template-upload-7"
                  data-download-template-id="template-download-7">
                <!-- Redirect browsers with JavaScript disabled to the origin page -->
                <noscript><input type="hidden" name="redirect" value="https://blueimp.github.io/jQuery-File-Upload/">
                </noscript>
                <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->

                <input type="hidden" name="userId" value="${userId}"/>
                <input type="hidden" name="sendType" id="idCard" value="idCard"/>
                <input type="hidden" name="role" id="role" value="${role}"/>
                <input type="hidden" name="idCardDetails.idType" id="idtype"/>
                <input type="hidden" name="idCardDetails.idNumber" id="idno" />
                <input type="hidden" name="idCardDetails.country" id="idcountry"/>
                <input type="hidden" name="idCardDetails.state" id="idstate"/>

                <div class="row fileupload-buttonbar">
                    <div class="col-lg-12" style="    padding: 2% 20% 0%;">
                        <!-- The fileinput-button span is used to style the file input field as button -->
                <span class="btn btn-success fileinput-button">
                    <i class="icon-plus"></i>
                    <span>Choose ID Proof..</span>
                    <input type="file" name="multiUploadedFileList">
                </span>

                        <button type="reset" class="btn btn-warning cancel resetbutton">
                            <span>Cancel</span>
                        </button>

                        <!-- The global file processing state -->
                        <span class="fileupload-process"></span>
                    </div>
                    <!-- The global progress state -->
                    <div class="col-lg-12 fileupload-progress fade">
                        <!-- The global progress bar -->
                        <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                            <div class="progress-bar progress-bar-success" style="width:0%;"></div>
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
            <div id="errorMessage" class="hide"></div>
        </div>
        <img id="sampleIdentityimg" src="" style="width:100%;"/>
    </div>
</div>

<div id="viewUploadIdImage">
    <div class="form-wrapper confirmAppForm class-smpopup" id="confirmAppForm">
        <div class="cancel-common"></div>
        <div id="moveMessage"></div>
        <div class="confirmpadding" id="tmm-form-wizard">
            <div class="note"></div>
            <div class="form-wrapper">
                <div class="confirmappointemnt">
                    Identity Card
                </div>
                <div>
                    <img id="viewIdentityimg" src="resources/images/nopreview.jpg" style="width:100%;"/>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="verification">
    <div class="form-wrapper confirmAppForm class-smpopup" id="confirmAppForm" style="display: block;">
        <div class="hide" id="cancel_verify"></div>
        <div id="confirmMessage" class="hide"></div>
        <div ><%--class="confirmpadding"--%>
            <%--
                        <div class="note"></div>
            --%> <div class="confirmappointemnt headingpopups">
                <h1> Datalife Verification</h1>
            </div>
            <div class="form-wrapper confirmpadding margin-top15 ">
                <fieldset class="input-block emailverify">
                    <p > Please verify your Email and  Mobile number to activate the account.</p>
                </fieldset>
                <div >
                    <ul>
                        <%--<li style="border-right:none;" id="emailhead">
                            <div class="searchalign">
                                <div id="email" class="search-doc show_border_clinic">
                                    <i class="icon-mail-1"></i>
                                    Email-verification
                                </div>
                            </div>
                        </li>--%>
                        <%--  <li style="border-right:none;" id="mobilehead">
                              <div class="searchalign">
                                  <div id="mobile" class="ad-doc"><i class="icon-mobile"></i>
                                      Mobile verification
                                  </div>
                              </div>
                          </li>--%>
                    </ul>
                </div>

                <div id="verifyConfirmMessage" class="hide"></div>
                <div id="verifyErrorMessage" class="hide"></div>
                <div class="data-verification" id="emailBody">
                    <form method="post" name="user" id="emailVerificationForm">

                        <fieldset class="input-block">
                            <label>Email Id</label>
                            <input type="text" id="verfifyEmail" title="Search" required="required" onblur=""
                                   placeholder="Enter Email Address" class="form-icon form-icon-user demo">
                            <button type="submit" class="saveb editbasicinfo-vitals submit_btn">Verify Now</button>
                            <p class="sign_out"><a id="logout2" style="text-decoration: underline">Signout</a></p>
                        </fieldset>
                    </form>


                </div>

                    <div >
                        <ul >
                            <%--  <li style="border-right:none;" id="emailhead">
                                  <div class="searchalign">
                                      <div id="email" class="search-doc show_border_clinic">
                                          <i class="icon-mail-1"></i>
                                          Email-verification
                                      </div>
                                  </div>
                              </li>--%>
                            <li style="border-right:none;" id="mobilehead">
                                <div class="searchalign">
                                    <div id="mobile" class="ad-doc"><i class="icon-mobile"></i>
                                        Mobile verification
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                <div id="mobileBody" class="hide">
                    <div class="basicdetails-modal mobile-verification" id="mobilediv">
                        <form method="post" name="user" id="mobileVerificationForm">
                            <fieldset class="input-block">
                                <input type="text" id="verfifyMobile" title="Search" required="required"
                                       placeholder="Enter Mobile Number" class="form-icon form-icon-user demo">
                                <button type="submit" class="saveb editbasicinfo-vitals submit_btn" id="send-otp">Send OTP
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
                                <button type="submit" class="saveb editbasicinfo-vitals submit_btn" id="ver_authenticate">Authenticate
                                </button>
                                <a href="/emr" id="ver_refresh" style="display: none;margin-left: 28%;">To access click on
                                    <button type="button" class="saveb editbasicinfo-vitals" style="margin: 10px 35%;">Refresh</button>
                                </a>

                                <p class="sign_out"><a id="logout4" style="text-decoration: underline">Signout</a></p>
                            </fieldset>
                        </form>
                    </div>

                </div>
               <%-- <div class="note">Note: To continue please verify email & mobile number</div>--%>
            </div>

        </div>
    </div>
</div>

<div id="newConsultation">
    <div class="form-wrapper confirmAppForm class-smpopup" id="confirmAppForm" style="display: block;max-width:500px !important;">
        <div class="cancel-consultation"></div>
        <div class="confirmpadding">
            <div class="note"></div>
            <div class="form-wrapper">
                <div class="confirmappointemnt">
                    <h1>New Consultation/ Progress Note</h1>
                </div>

                <div class="data-verification">
                    <div class="note" style="color: red;">Note: For physician use with patient consent</div>
                    <form id="newConsultationForm">
                        <div class="basicdetails-modal new-conult">
                            <div class="row" >

                                <div class="col-md-6 no-pad ">
                                    <fieldset style="padding: 0px !important;" class="input-block">
                                        <label class="select-records">Doctor Name<span
                                                style="color:red;">*</span></label>
                                        <input type="text" class="form-icon form-icon-user demo"
                                               placeholder="Doctor Name" maxlength="40" pattern="^[a-zA-Z\. ]+$"
                                               required="required" title="Doctor Name" id="con_doctorName">
                                    </fieldset>
                                </div>

                                <div class="col-md-6 no-pad ">
                                    <fieldset style="padding: 0px !important;" class="input-block">
                                        <label class="select-records">Clinic Name<span
                                                style="color:red;">*</span></label>
                                        <input type="text" class="form-icon form-icon-user demo"
                                               placeholder="Clinic Name" maxlength="50" pattern="^[a-zA-Z\. ]+$"
                                               required="required" title="Clinic Name" id="con_clinicName">
                                    </fieldset>
                                </div>

                                <div class="col-md-6 no-pad ">
                                    <fieldset style="padding: 0px !important;" class="input-block">
                                        <label class="select-records">Registration Number<span
                                                style="color:red;">*</span></label>
                                        <input type="text" class="form-icon form-icon-user demo"
                                               placeholder="Registration Number" maxlength="50"
                                               required="required" title="Registration Number" id="con_mciNumber">
                                    </fieldset>
                                </div>

                                <div class="col-md-6 no-pad ">
                                    <fieldset style="padding: 0px !important;" class="input-block">
                                        <label class="select-records">Mobile Number<span
                                                style="color:red;">*</span></label>
                                        <input type="text" class="form-icon form-icon-user demo"
                                               placeholder="Enter 10 digit number" pattern="^[\+?\d]{10,13}"
                                               maxlength="13" required="required" title="Mobile Number" id="con_mobile">
                                        <!--/*<i class="icon-edit"></i> */-->

                                    </fieldset>
                                </div>
                                <div class="col-md-12 " style="text-align:center;margin-bottom:18px;margin-top:10px">
                                    <button id="con_continue" class="saveb editbasicinfo-vitals submit_btn" type="submit">Continue</button></div>
                            </div>
                        </div>

                    </form>

                </div>


            </div>

        </div>
    </div>
</div>

<div id="not_available">
    <div class="form-wrapper confirmAppForm class-smpopup" id="confirmAppForm">
        <div class="cancel-available"></div>
        <div id="moveMessage"></div>
        <div class="confirmpadding" id="tmm-form-wizard">

            <div class="note"></div>
            <div class="form-wrapper">
                <div class="confirmappointemnt">
                    Service Request
                </div>
                <div style="display:table-cell; vertical-align:middle; text-align:center;">
                    <img style="width: 100%;" src="resources/images/comingsoon.jpg"/>

                </div>

            </div>


        </div>
    </div>
</div>

<div id="teleconsultInfo">
    <div class="form-wrapper confirmAppForm class-smpopup" id="confirmAppForm" style="min-height: 200px !important;">
        <div class="cancel-available"></div>
        <div id="moveMessage"></div>
        <div class="confirmpadding" id="tmm-form-wizard">

            <div class="note"></div>
            <div class="form-wrapper">
                <h2 style="line-height: 23px; font-size: 22px; font-weight: 800; color: green; padding-top: 25px; text-align: center;">  For Tele-Consultation Service <br><span style="color: rgb(0, 0, 0); font-size: 29px; padding-top: 15px; display: block;"> <i class="icon-phone hideicon"></i> 944-981-1444</span> </h2> <p style="text-align: center; padding-top: 15px;"><strong>Note:</strong> Provide your DataLife ID for identification to Customer Support.</p>
            </div>


        </div>
    </div>
</div>

<div id="s_successMessage" >
    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-popup" style="max-width: 500px !important;min-height: 45px; position: absolute;
    top: 45%;">
        <div id="cancel-common"></div>
        <div class="confirmpadding">
            <div id="s_confirmMessage" class="s_message"></div>
            <div id="s_errorMessage" class="s_message"></div>
        </div>
    </div>
</div>

<div id="idcardPopup" class="hide">
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

<div id="uploadRecordsPopup">
    <div class="form-wrapper confirmAppForm" id="confirmAppForm">
        <div class="close-recordupload"></div>
        <div class="confirmpadding" id="tmm-form-wizard">
            <form id="fileupload" name="uploadFile" method="POST" enctype="multipart/form-data">
                <div class="note"></div>
                <div class="form-wrapper">
                    <div class="confirmappointemnt">
                        Upload Records
                    </div>
                    <div>
                        <div class="basicdetails-modal sidebox">
                            <div >
                                <div class="col-md-12 no-pad ">

                                    <fieldset class="input-block">
                                        <label class="select-records">Select Record Type</label>

                                        <div class="dropdown">
                                            <select id="fileType" class="dropdown-select" name="fileType">
                                                <option value="0" selected disabled>Select</option>
                                                <option value="hospital">Clinic Records</option>
                                                <option value="prescription">Prescription</option>
                                                <option value="laborder">Lab Order</option>
                                                <option value="diagnostic">Lab Reports</option>
                                                <option value="xray">X-ray</option>
                                                <option value="mri">MRI</option>
                                                <option value="others">Miscellaneous</option>
                                            </select>
                                        </div>
                                        <!--/ .tooltip-->
                                    </fieldset>
                                </div>
                            </div>
                        </div>
                        <div id="uploadRecords" class="hide">
                            <div class="basicdetails-modal sidebox">
                                <div>
                                    <div class="col-md-6  no-pad ">
                                        <fieldset class="input-block">
                                            <label class="select-records"> Choose Visit Date</label>
                                            <input type="text" title=" Choose Visit Date" name="encDate" required
                                                   placeholder="dd/mm/yyyy hh:mm a" id="records_datepicker" readonly>
                                            <input type="hidden" name="user.userId" id="recordUserId" value=""/>
                                            <!--/ .dropdown-->
                                        </fieldset>
                                    </div>
                                    <div class="col-md-6  no-pad ">
                                        <fieldset class="input-block">
                                            <label class="select-records">Specialty</label>
                                            <div class="dropdown">
                                                <select class="dropdown-select" id="recordSpecialities" name="speciality" title="Specialty"></select>
                                            </div>
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                            <div class="basicdetails-modal sidebox">
                                <div >
                                    <div class="col-md-6  no-pad">
                                        <fieldset class="input-block">
                                            <label class="select-records">Reason for visit</label>
                                            <input type="text" title=" Reason for visit " name="chiefComplaint"
                                                   placeholder=" Reason for visit" id="chiefcomplaint"/>
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                    <div class="col-md-6  no-pad">
                                        <fieldset class="input-block">
                                            <label class="select-records">Diagnosis</label>
                                            <input type="text" title=" Diagnosis " required="required" name="diagnosis"
                                                   placeholder=" Diagnosis" id="diagnosis">
                                            <!--/ .dropdown-->
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                            <div class=" basicdetails-modal button-information sidebox">
                                <div class=" fileupload-buttonbar" style="clear: both; padding: 0 15px">
                                    <div class="col-md-12">
                                        <div class="btn btn-success fileinput-button">
                                            <i class="icon-search-circled"></i>
                                            <span class="upload-information">Browse</span>
                                            <input type="file" name="multiUploadedFileList" multiple="multiple">
                                        </div>
                                        <button type="submit" class="btn btn-primary start">
                                            <i class="icon-up-circled"></i>
                                            <span class="upload-information">Upload</span>
                                        </button>
                                        <span class="fileupload-process"></span>
                                    </div>
                                    <div class="col-lg-5 fileupload-progress width100per ">
                                        <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                                            <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                                        </div>
                                        <div class="progress-extended">&nbsp;</div>
                                        <div id="confirmMessage" class="hide"></div>
                                        <div id="errorMessage" class="hide"></div>
                                    </div>
                                </div>
                                <table role="presentation" class="table table-striped">
                                    <tbody class="files"></tbody>
                                </table>
                            </div>
                            <div class="note">Note: Multiple files can be uploaded.</div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div class="clearfix"></div>
    </div>
</div>

<div id="pdfdisplay">

    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-popup" style="max-width: 800px !important;">
        <div class="cancel-common"></div>
        <div class="confirmpadding">
            <iframe src="" id="iframe" class="preview-iframe"></iframe>
            <img src="" id="imgiframe" class="preview-iframe"/>
        </div>
    </div>
</div>

<div id="shortListDiv" class="" style="right: -140px;"> <a href="javascript:void(0);" class="shortListButton btn"> <div class="filler" style="width: 0%;"></div> <span class="heart-icon"></span> <span class="text">Shortlist</span> <span class="counter">0</span> <span title="Close" class="close"></span> </a>  </div>

<script type="text/javascript" src="resources/js/server/common.js"></script>
<script type="text/javascript" src="resources/jquery/scripts.js"></script>
<script type="text/javascript" src="resources/js/server/messenger.js"></script>
<script type="text/javascript" src="resources/js/server/patientDashboard.js"></script>
<script type="text/javascript" src="resources/jsplugins/popupbox.js"></script>


<script>
    $(document).ready(function () {
        $("#appointments").trigger("click");
    });
</script>


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
<script src="resources/js/jsfiles/messenger.js"></script>
<script src="resources/jsplugins/jquery.nicescroll.js"></script>

</body>
</html>
