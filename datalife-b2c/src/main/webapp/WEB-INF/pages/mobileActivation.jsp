<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 8/19/2015
  Time: 12:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>DataLife</title>
    <meta charset="UTF-8">
    <!--[if lt IE 9]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="shortcut icon" type="image/x-icon" href="resources/images/favicon.ico">
    <!--Meta Tag-->
    <link rel="stylesheet" href="resources/css/style.min.css">
    <!--Main Menu File-->
    <link href="resources/css/fonts.css" rel='stylesheet' type='text/css'>

    <script type="text/javascript" src="resources/jquery/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="resources/jquery/scripts.js"></script>

    <script type="text/javascript" src="resources/jsplugins/bootstrap.min.js"></script>


</head>
<body>
<div class="topbar">
    <div class="container">
        <div class="top-info-contact pull-left col-md-6">Call Us Today! 944-981-1444  | <a href="mailto:support@datalife.in ">support@datalife.in</a> </div>
        <div class="social-wrap-head col-md-2 no-pad pull-right">

        </div>
    </div>
</div>
<div class="">
    <div class="wsmenuexpandermain slideRight"><a id="navToggle" class="animated-arrow slideLeft" href="#"><span></span></a></div>
    <div class="wsmenucontent overlapblackbg"></div>
    <div class="header">
        <div class="container-demo-index">
            <div class="container no-padding">
                <div class="">
                    <div class="logo postion">  <a href="/"><h1> <img src="resources/images/datalifelogo.png" alt="datalife"></h1></a></div>
                </div>
                <div id="nav" class="">
                    <div class="">
                        <!--Menu HTML Code-->
                        <nav class="wsmenu slideLeft clearfix">
                            <ul class="mobile-sub wsmenu-list demo" id="signin">

                                <div class="book-an-appointment"><a class="btn btn-success btn-14 saveb editbasicinfo-book" href="/"><i class="icon-calendar hideicon"></i> Book an Appointment</a> </div>
                            </ul>
                        </nav>
                    </div>
                    <!--Menu HTML Code-->
                </div>
            </div>
        </div>

    </div>
    <div class="wrapper">
        <div class="form-container">
            <div id="tmm-form-wizard" class="substrate demo reset">
                <!--/ .row-->
                <div class="row">
                    <div class="col-xs-12">
                        <div class="form-login-heading demo"> Mobile OTP</div>
                    </div>
                </div>

                <form method="post" name="user" id="activation" enctype="application/json">
                    <div class="margin30">
                        <div class="">
                            <div class="row">
                                <div class="col-md-12 no-padding">

                                    <div class="col-md-12 col-sm-12  signup-border">
                                        <p >Note : To activate your account enter your OTP sent to your mobile phone or email during the registration.<br>

                                        </p>
                                        <div id="message">${message}</div>
                                        <fieldset class="input-block">
                                            <label>User name<span style="color:red;">*</span></label>
                                            <input type="text" required="" placeholder="User Name" class="form-icon form-icon-user" name="userName" id="userName" title="User name">

                                        </fieldset>
                                        <fieldset class="input-block">
                                            <label>Enter OTP<span style="color:red;">*</span></label>

                                            <input type="password" required="" placeholder="Enter OTP" class="form-icon form-icon-user" title="Mobile" name="otp">

                                        </fieldset>
                                        <div class="doctor-timeslot-discription-search">
                                            <input type="submit" value="Activate" class="saveb editbasicinfo" >

                                        </div>
                                        <p>      If you didn't receive an activation code,click on  <a href="#"  id="resendOtp">Resend OTP</a></p>

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

</body>
<script>
    $(function () {

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

        $('form').submit(function (e) {
            e.preventDefault();
            var url = "api/user/activation/mobileOTP";
            var userName = $('#userName').val();
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(mobileActiveFormToJSON('#activation')),
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    var statusCode = data['statusCode'];
                    var message = data['message'];
                    var userName = data['userName'];
                    if (statusCode == 200) {
                        $("body").load("activate/" + userName, function () {
                            $("#userName").text(userName);
                        });
                    } else {
                        message = '<div class="notification alert-error spacer-t10"><p>' + message + '</p><a class="close-btn"></a></div>';
                        $("#message").append(message);
                    }
                },
                error: function (data) {

                }
            });

            return false;

        });

        $('#resendOtp').click(function (e) {
            e.preventDefault();
            var userName = $("#userName").val();
            var url = "api/user/activation/resend";
            var jsondata = {"userName": userName};
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(jsondata),
                contentType: 'application/json',
                mimeType: 'application/json',
                success: function (data) {
                    var statusCode = data['statusCode'];
                    var userName = data['userName'];
                    $('#userName').val(userName);
                },
                error: function (data) {

                }
            });
            return false;
        });
    });
</script>
</html>
