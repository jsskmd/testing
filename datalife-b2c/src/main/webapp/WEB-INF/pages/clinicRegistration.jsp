<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 8/17/2015
  Time: 6:15 PM
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
    <title>DataLifeEMR</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <meta charset="UTF-8">
    <!--[if lt IE 9]>
    <script src="https://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/resources/images/favicon.ico">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/build/intlTelInput.min.css">
    <!--Meta Tag-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.min.css">

    <!--Main Menu File-->
    <link href='https://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700italic,700,900,900italic' rel='stylesheet' type='text/css'>
    <!--Main Menu File-->
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery/scripts.js"></script>

    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/jsplugins/build/intlTelInput.min.js"></script>
    <%--<script src="<%=request.getContextPath()%>/resources/js/server/common.js"></script>--%>
    <script src="<%=request.getContextPath()%>/resources/js/server/registrationCommon.js"></script>

</head>
<body>
<div class="topbar">
    <div class="container">
        <div class="top-info-contact pull-left col-md-6">Call Us Today! 944-981-1444  | <a href="mailto:support@datalife.in ">support@datalife.in</a> </div>
        <div class="social-wrap-head col-md-2 no-pad pull-right">
           <%-- <ul>
                <li><a target="_blank" href="https://www.facebook.com/pages/DataLife/323394817940306"><i title="Facebook" data-original-title="Facebook" id="face-head" class="icon-facebook head-social-icon"></i></a></li>
                <li><a target="_blank" href=" https://twitter.com/DataLifeEhr"><i title="" data-original-title="" id="tweet-head" class="icon-twitter head-social-icon"></i></a></li>
                <li><a target="_blank" href="https://www.linkedin.com/in/DataLifeEhr"><i title="" data-original-title="" id="link-head" class="icon-linkedin head-social-icon"></i></a></li>
            </ul>--%>
        </div>
    </div>
</div>
<div class="wsmenucontainer clearfix">
    <div class="wsmenuexpandermain slideRight"><a id="navToggle" class="animated-arrow slideLeft menuclose" href="javascript:void(0)"><span></span></a></div>
    <div class="wsmenucontent overlapblackbg menuclose"></div>
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
                              <div class="book-an-appointment"><a class="btn btn-success btn-14" href="/emr"><i class="icon-home hideicon"></i> BACK TO Login</a> </div>
                            </ul>
                        </nav>
                    </div>
                    <!--Menu HTML Code-->
                </div>
            </div>
        </div>
        <!--Select Color -->
        <!--Select Color -->
    </div>

    <div class="message" id="eMessage"></div>

    <div class="wrapper">
        <div class="form-container">
            <div id="tmm-form-wizard" class="substrate demo">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="form-login-heading demo"> CLINIC REGISTRATION</div>
                    </div>
                </div>
                <!--/ .row-->
                <!--/ .row-->
                <!--/ .row-->
                <form method="post" name="user" id="clinicRegistration" enctype='application/json'>

                    <input type="hidden" name="role" id=userRole" value="ROLE_CLINIC">

                    <div class="form-wizard">
                        <div class="">
                            <div class="row">
                                <div class="col-md-12 no-padding">
                                    <div class="col-md-6 col-sm-6 no-pad">
                                        <fieldset class="input-block">
                                            <label>Clinic Name<span style="color:red;">*</span></label>
                                            <input required="" placeholder="Clinic Name" class="form-icon form-icon-user" name="clinicInfo.clinicName" title="Clinic Name" type="text" >
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                    <div class="col-md-6 col-sm-6 no-pad">
                                        <fieldset class="input-block">
                                            <label> Licence /Registration No<span style="color:red;">*</span></label>
                                            <input type="text" required="required" id="licRegNo" placeholder="Licence/Registration No" name="clinicInfo.mlrNumber" title="Licence/Registration No"  onblur="checkLicRegExistsAllReady(this.value,'licRegNo');">
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12  no-padding">
                                    <div class="col-md-12 col-sm-12 no-pad">
                                        <fieldset class="input-block">
                                            <label> User Name<span style="color:red;">*</span></label>
                                            <input type="text" pattern="^[a-zA-Z0-9]+$" required="required" maxlength="15" id="userName" placeholder="User Name" title="You can use letters, numbers. Eg: JOHNXX001" name="userName" onblur="checkUserExistsAllReady(this.value);">
                                            <!--/ .dropdown-->
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12  no-padding">
                                    <div class="col-md-6 col-sm-6 no-pad">
                                        <fieldset class="input-block">
                                            <label>Password<span style="color:red;">*</span></label>
                                            <input type="password" name='password'  id="password" required='required' title="password" placeholder="Password" pattern="^.{4,12}$" maxlength="12"/>
                                            <!--/ .tooltip-->
                                        </fieldset>
                                        <div class="input_note">Note: Enter 4-12 characters</div>
                                    </div>
                                    <div class="col-md-6 col-sm-6 no-pad">
                                        <fieldset class="input-block">
                                            <label> Confirm Password<span style="color:red;">*</span></label>
                                            <input type="password" name="password_confirmation" id="confirm_password" required='required' placeholder="Confirm Password"  title="confirm password"
                                                   pattern="^.{4,12}$" maxlength="12"/>
                                            <!--/ .dropdown-->
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12  no-padding">
                                    <div class="col-md-6 col-sm-6 no-pad">
                                        <fieldset class="input-block">
                                            <label>Email<span style="color:red;">*</span></label>
                                            <input type="text" required="required" name="userContactInfo.email" placeholder="Email" id="email" title="email" pattern="^[_\.0-9a-zA-Z-]+@([0-9a-zA-Z][0-9a-zA-Z-]+\.)+[a-zA-Z]{2,6}$" onblur="checkEmailExistsAllReady(this.value,'ROLE_CLINIC','email');">
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                    <div class="col-md-6 col-sm-6 no-pad">
                                        <fieldset class="input-block">
                                            <label> Mobile Number<span style="color:red;">*</span></label>
                                            <input id="mobile" type="tel"  placeholder="Mobile Number" name="userContactInfo.mobilePhone" maxlength="10" onblur="checkMobileExistsAllReady(this.value,'ROLE_CLINIC','mobile')" required='required' pattern="^[\+?\d]{10,12}" />

                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="registration-btn">
                        <button type="submit" value="Register" class="register-btn">Sign Up</button>
                    </div>
                    <div class="label-demo">
                        By clicking  Sign Up, you agree to our <a class="terms-link"  target="_blank"> Terms and Conditions</a> </div>
                </form>
                <!--/ .row-->
                <!--/ .form-wizard-->
                <!--/ .form-wizard-->
            </div>
            <!--/ .container-->
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

        $('form').submit(function (e) {
            e.preventDefault();
            $("#clinicRegistration").find(".register-btn").html('<img src="../resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

            var url = "../api/user/register";
                $.ajax({
                    url: url,
                    type: 'POST',
                    dataType: 'json',
                    data: JSON.stringify(CRegistrationFormToJSON('#clinicRegistration')),
                    contentType: 'application/json',
                    success: function (data) {
                        var statusCode = data['statusCode'];
                        var message = data['message'];
                        switch (statusCode){
                            case 200:
                                $("body").load("../activate/afterRegistration", function () {
                                    $('#userName').text(data['userName']);
                                    message = '<div class="notification alert-error spacer-t10"><p>'+message+'</p><a href="#" class="close-btn">?</a></div>';
                                    $("#eMessage").empty().append(message);
                                    ChangeUrl('page1', 'activate/afterRegistration');
                                });
                                break;
                            case 406 :
                                message = '<div class="notification alert-error spacer-t10"><p>'+message+'</p><a class="close-btn">?</a></div>';
                                $("#eMessage").empty().append(message);
                                break;
                        }

                        $("#clinicRegistration").find(".register-btn").html('Sign Up');
                    },
                    error: function (data) {

                    }
                });
            return false;
        });
    });

</script>
</html>

