<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 8/19/2015
  Time: 5:15 PM
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
    <title>DataLife</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <meta charset="UTF-8">
    <!--[if lt IE 9]>
    <script src="https://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="shortcut icon" type="image/x-icon" href="resources/images/favicon.ico">
    <!--Meta Tag-->
    <link rel="stylesheet" href="resources/css/style.min.css">

    <!--Main Menu File-->
    <link href="resources/css/fonts.css" rel='stylesheet' type='text/css'>
    <script type="text/javascript" src="resources/jquery/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="resources/jsplugins/bootstrap.min.js"></script>
    <script type="text/javascript" src="resources/js/server/common.js"></script>
    <script type="text/javascript" src="resources/jquery/scripts.js"></script>
    <script src="resources/jsplugins/build/intlTelInput.min.js"></script>

</head>

<body>
<div class="topbar">
    <div class="container">
        <div class="top-info-contact pull-left col-md-6">Call Us Today! 94498-11444 | <a href="mailto:support@datalife.in ">support@datalife.in</a> </div>
        <div class="social-wrap-head col-md-2 no-pad pull-right">

        </div>
    </div>
</div>
<div class="">
    <div class="wsmenuexpandermain slideRight"><a id="navToggle" class="animated-arrow slideLeft  " href="javascript:void(0)"><span></span></a></div>
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

                                <div class="book-an-appointment"><a class="btn btn-success btn-14" href="/"><i class="icon-home hideicon"></i> BACK TO HOME</a> </div>
                            </ul>
                        </nav>
                    </div>
                    <!--Menu HTML Code-->
                </div>
            </div>
        </div>
    </div>
    <div class="container">

    </div>
    <div class="wrapper">
        <div class="form-container">
            <div id="tmm-form-wizard" class="substrate demo reset register-margin">
                <!--/ .row-->
                <div class="row">
                    <div class="col-xs-12">
                        <div class="form-login-heading demo">  Reset Password </div>
                    </div>
                </div>
                <!--/ .row-->
                <!--/ .row-->
                <form id="forgotpassword">
                    <div id="confirmMessage" style="display: none;"></div>
                    <div id="errorMessage" style="display: none;"></div>
                    <div class="">
                        <div class="row">
                            <div class="col-md-12 no-padding">

                                <div class="col-md-12 col-sm-12  signup-border">

                                    <fieldset class="input-block">
                                        <label>User name<span style="color:red;">*</span></label>
                                        <input type="text" pattern="^[a-zA-Z0-9]+$" required="required"
                                               maxlength="15" id="userName" placeholder="User Name"
                                               title="User name" name="userName">
                                    </fieldset>

                                    <div class="doctor-timeslot-discription-search">
                                        <button type="submit" class="saveb editbasicinfo signup register-btn" id="pwd">Reset Password</button>

                                    </div>
                                    <p style="float: right;"><a href="/emr/login" style="text-decoration: underline">Back to Sign in</a></p>

                                </div>

                            </div>
                        </div>
                    </div>
                </form>

            </div>

        </div>
    </div>
</div>

</body>
<script>

    $(document).ready(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

        $("#userName").blur(function () {
            $("#errorMessage").empty();
        });
        $('form').submit(function (e) {
            e.preventDefault();
            $("#forgotpassword").find(".register-btn").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;"><span>Sending mail...</span>');
            $("#confirmMessage,#errorMessage").empty();


            var url = "api/user/password/forgot/" + $('#userName').val();

            $.ajax({
                url: url,
                type: 'GET',
                dataType: 'json',
                contentType: 'application/json',
                success: function (data) {
                    if (data['statusCode'] == 200) {
                        document.getElementById('confirmMessage').style.display = 'block';
                        document.getElementById('errorMessage').style.display = 'none';
                        $("#pwd").prop('disabled', true);
                        $("#confirmMessage").prepend(data['message']);
                    } else {
                        document.getElementById('errorMessage').style.display = 'block';
                        document.getElementById('confirmMessage').style.display = 'none';
                        $("#errorMessage").prepend(data['message']);
                    }
                    document.getElementById("forgotpassword").reset();
                    $("#forgotpassword").find(".register-btn").html('Reset Password');
                },
                error: function (data) {

                }
            });

            return false;
        });
    });

</script>

</html>
