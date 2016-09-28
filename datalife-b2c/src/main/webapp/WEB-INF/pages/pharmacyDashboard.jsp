<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 8/15/2016
  Time: 1:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>DataLife</title>
    <meta charset="UTF-8">
    <!--[if lt IE 9]>
    <script src="https://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="shortcut icon" type="image/x-icon" href="resources/images/favicon.ico">
    <!--[if lt IE 9]>
    <link rel="stylesheet" type="text/css" href="/resources/css/ie8.css"/>
    <![endif]-->
    <!--Meta Tag-->

    <link type='text/css' rel="stylesheet" href="resources/css/jquery-ui.min.css"/>
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
    <link rel="stylesheet" href="resources/css/jquery.datetimepicker.min.css">
    <link type='text/css' rel="stylesheet" href="resources/css/site.min.css"/>
    <link type='text/css' rel="stylesheet" href="resources/css/dataTables.responsive.min.css"/>
    <link type='text/css' rel="stylesheet" href="resources/css/style.min.css"/>
    <!--Main Menu File-->
    <link type='text/css' rel='stylesheet' href="resources/css/fonts.css">

    <script type="text/javascript" src="resources/jquery/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="resources/jsplugins/jquery-ui.js"></script>
    <script type="text/javascript" src="resources/jsplugins/build/intlTelInput.min.js"></script>
    <script type="text/javascript" src="resources/jsplugins/popupbox.js"></script>

    <script type="text/javascript" src="resources/jsplugins/site.js"></script>
    <script type="text/javascript" src="resources/jsplugins/dataTables.responsive.min.js"></script>
    <script type="text/javascript" src="resources/jsplugins/jquery.datetimepicker.min.js"></script>
    <script type="text/javascript" src="resources/js/server/common.js"></script>
    <script type="text/javascript" src="resources/js/server/pharmDashboard.js"></script>


    <script>
        $(document).ready(function () {
            $('.popbox').popbox();
            $("#pharmacy_patientSearch").trigger('click');
        });
    </script>

</head>

<body>

<div class="topbar">
    <div class="container">
        <div class="top-info-contact pull-left col-md-6">Call Us Today 944-981-1444 | <a href="mailto:support@datalife.in">support@datalife.in</a></div>
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
                    <div class="logo">
                        <a href="/">
                            <h1> <img src="<%= request.getContextPath()%>/resources/images/datalifelogo.png" alt="datalife"></h1>
                        </a>
                    </div>
                    <div class="nav-top clearfix">
                        <div class="user-deatils">
                            <ul>
                                <li>
                                    <div class="patient-details">
                                        <h1 class="userName"> ${user.userName}</h1>
                                        <span class="patient-id"> DataLife ID :<span id="userId">${user.userId}</span></span></div>
                                    <input type="hidden" value="${user.role}" id="role">
                                    <input type="hidden" value="https://datalife.in/api/user/patient/userthumbnail/" id="imageUrl">
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

        <div id="nav" class="affix-top">
            <div class="nav-width">
                <!--Menu HTML Code-->
                <nav class="wsmenu slideLeft clearfix">
                    <ul class="mobile-sub wsmenu-list docotnav" id="dashboard_menu">

                        <li style="display:none;" class="items-for-navs">
                            <jsp:include page="/profileImage"></jsp:include>
                            <h1 class="userName info-mobilenav">${user.userName}</h1>
                            <span class="patient-id warpper"> DataLife ID :<span id="userId_mob">${user.userId}</span></span>
                        </li>

                        <%-- <li><a id="lab_profile" class="active"><i class="icon-calendar hideicon"></i>Profile</a></li>--%>
                        <li><a id="pharmacy_patientSearch"><i class="icon-search hideicon"></i>Patient Search </a></li>
                        <li><a id="pharm_order"><i class="icon-doc-1 hideicon"></i>Order Status</a></li>
                        <li class="sign-out-demo" style="display:none;">
                            <div class="book-an-appointment">
                                <a id="logout1" class="btn btn-success btn-14 saveb editbasicinfo-book">
                                    <i class="icon-login hideicon"></i>Sign out</a>
                            </div>
                        </li>
                    </ul>
                </nav>
            </div>
            <!--Menu HTML Code-->
        </div>


    </div>

    <div id="ajaxloaddiv">

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
</body>

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

</html>
