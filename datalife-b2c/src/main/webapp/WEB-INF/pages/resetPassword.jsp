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
    <script src="https://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/resources/images/favicon.ico">
    <!--Meta Tag-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.min.css">
    <!--Main Menu File-->
    <link href='https://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700italic,700,900,900italic' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/theme-default.min.css" type="text/css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery/scripts.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/jquery.form-validator.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/security.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/toggleDisabled.js"></script>
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
    <div class="wsmenuexpandermain slideRight"><a id="navToggle" class="animated-arrow slideLeft" href="#"><span></span></a></div>
    <div class="wsmenucontent overlapblackbg"></div>
    <div class="header">
        <div class="container-demo-index">
            <div class="container no-padding">
                <div class="">
                    <div class="logo postion">  <a href="/"><h1> <img src="<%=request.getContextPath()%>/resources/images/datalifelogo.png" alt="datalife"></h1></a></div>
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
    <div class="wrapper">
        <div class="form-container">
            <div id="tmm-form-wizard" class="substrate demo reset register-margin">
                <!--/ .row-->
                <div id="ajaxload">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="form-login-heading demo">Reset Password</div>
                        </div>
                    </div>
                    <form method="post" name="user" id="resetPassword" enctype="application/json" class="form-horizontal well test-form toggle-disabled has-validation-callback">
                    <div id="errorMessage" class="hide">${message}</div>
                    <div class="">
                        <div class="row">
                            <div class="col-md-12 no-padding">

                                <div class="col-md-12 col-sm-12  signup-border">


                                    <fieldset class="input-block">
                                        <label>New Password<span style="color:red;">*</span></label>
                                        <input type="hidden" value="${userId}" name="userId"/>
                                        <%--<input type="password" required="" placeholder="Enter Passowrd" class="form-icon form-icon-user"  name='password' id="confirm_password" pattern="^.{8,12}$" maxlength="12" title="Use at least 8 characters">--%>
                                        <input type="password" name='password' id="confirm_password"  placeholder="Password" pattern="^.{8,12}$" maxlength="12"  data-validation="length" data-validation-length="min8"  data-validation-error-msg="Password should be minimum 8 characters" />
                                    </fieldset>
                                    <fieldset class="input-block">
                                        <label>Confirm Password<span style="color:red;">*</span></label>
                                        <input type="password"  name="password_confirmation"  id="password"  placeholder="Confirm  Password"  data-validation="confirmation"  data-validation-confirm="password"  maxlength="12" data-validation-error-msg="Password doesn't match " />
                                        <%--<input type="password" required="" placeholder="Enter Password" class="form-icon form-icon-user" title="Mobile" id="confirm_password" pattern="^.{8,12}$" maxlength="12">--%>

                                    </fieldset>
                                    <div class="doctor-timeslot-discription-search">
                                        <button type="submit" class="saveb editbasicinfo-vitals register-btn" >Reset</button>

                                    </div>

                                    <p style="float: right; margin-bottom: 15px;" class="sign_out"><a href="/emr/login" style="text-decoration: underline">Back to Sign in</a></p>
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

</body>
<script>
    $(function () {

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

        $.validate({
            showErrorDialogs : true,
            validateOnBlur : true, // disable validation when input looses focus
            modules : 'toggleDisabled,security',
            disabledFormFilter : 'form.toggle-disabled',
            scrollToTopOnError : true
        });

        $('form').submit(function (e) {
            e.preventDefault();
            $("#resetPassword").find(".register-btn").html('<img src="../resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
        /*    var p=$("#password").val();
            var cp=$("#confirm_password").val();
            if(p!=cp){
                var message="Incorrect Password entered !";
                $("#errorMessage").empty().append(message);
                $("#resetPassword").find(".register-btn").html('Reset');
            }else{*/
                var url = "../api/user/password/changePassword";
                var userName = $('#userName').val();
                $.ajax({
                    url: url,
                    type: 'POST',
                    dataType: 'json',
                    data: JSON.stringify(LoginFormToJSON('#resetPassword')),
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    success: function (data) {
                        var statusCode = data['statusCode'];
                        var message = data['message'];
                        var userName = data['userName'];
                        if (statusCode == 200) {
                            $("#ajaxload").empty().load("../password/afterResetPassword", function () {
                                $("#userName").text(userName);
                                $("#success").show();
                                $("#error").hide();
                            });
                        } else {
                            $("#errorMessage").removeClass('hide').empty().append(message);
                        }
                        document.getElementById("resetPassword").reset();
                        $("#resetPassword").find(".register-btn").html('Reset');
                    },
                    error: function (data) {

                    }
                });
           /* }*/


            return false;

        });

        $('#resendOtp').click(function (e) {
            e.preventDefault();
            var userName = $("#userName").val();
            var url = "../api/user/activation/resend";
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

    function LoginFormToJSON(form) {
        var array = jQuery(form).serializeArray();
        var json = {};

        jQuery.each(array, function () {
            json[this.name] = this.value || '';
        });

        return json;
    }
</script>
</html>
