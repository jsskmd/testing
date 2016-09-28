
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
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
    <link rel="shortcut icon" type="image/x-icon" href="../resources/images/favicon.ico">
    <link rel="stylesheet" href="../resources/css/build/intlTelInput.min.css">
    <!--Meta Tag-->
    <link rel="stylesheet" href="../resources/css/style.min.css">
    <!--Main Menu File-->
    <link href='https://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700italic,700,900,900italic' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="../resources/css/theme-default.min.css" type="text/css"/>
    <script type="text/javascript" src="../resources/jquery/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="../resources/jquery/scripts.js"></script>
    <script type="text/javascript" src="../resources/jsplugins/tooltip.js"></script>
    <script src="../resources/jsplugins/build/intlTelInput.min.js"></script>
    <%-- <script src="../resources/js/server/common.js"></script>--%>
    <script src="../resources/js/server/registrationCommon.js"></script>
    <script type="text/javascript" src="../resources/jsplugins/jquery.form-validator.js"></script>
    <script type="text/javascript" src="../resources/jsplugins/security.js"></script>
    <script type="text/javascript" src="../resources/jsplugins/toggleDisabled.js"></script>
</head>
<body>
<div class="topbar">
    <div class="container">
        <div class="top-info-contact pull-left ">Call Us Today! 94498-11444  | <a href="mailto:support@datalife.in ">support@datalife.in</a> </div>
        <div class="social-wrap-head col-md-2 no-pad pull-right">
            <%--  <ul>
                  <li><a target="_blank" href="https://www.facebook.com/pages/DataLife/323394817940306"><i title="Facebook" data-original-title="Facebook" id="face-head" class="icon-facebook head-social-icon"></i></a></li>
                  <li><a target="_blank" href=" https://twitter.com/DataLifeEhr"><i title="" data-original-title="" id="tweet-head" class="icon-twitter head-social-icon"></i></a></li>
                  <li><a target="_blank" href="https://www.linkedin.com/in/DataLifeEhr"><i title="" data-original-title="" id="link-head" class="icon-linkedin head-social-icon"></i></a></li>
              </ul>--%>
        </div>
    </div>
</div>

<div class="wsmenucontainer clearfix">
    <div class="wsmenuexpandermain slideRight"><a id="navToggle" class="animated-arrow slideLeft" href="javascript:void(0)"><span></span></a></div>
    <div class="wsmenucontent overlapblackbg"></div>
    <div class="header">
        <div class="container-demo-index">
            <div class="container no-padding">
                <div class="">
                    <div class="logo postion"> <a href="/emr">
                        <h1> <img src="../resources/images/datalifelogo.png" alt="datalife"></h1>
                    </a> </div>
                    <div id="nav" class="">
                        <div class="">
                            <!--Menu HTML Code-->
                            <nav class="wsmenu slideLeft clearfix">
                                <ul class="mobile-sub wsmenu-list demo" id="signin">
                                    <div class="book-an-appointment"><a class="btn btn-success btn-14" href="/emr"><i class="icon-home hideicon"></i> BACK TO LOGIN</a> </div>
                                </ul>
                            </nav>
                        </div>
                        <!--Menu HTML Code-->
                    </div>
                </div>
            </div>
        </div>
        <!--Select Color -->
        <!--Select Color -->
    </div>
    <div class="message" id="eMessage"></div>
    <div class="wrapper">
        <div class="form-container"  >
            <div id="tmm-form-wizard" class="substrate demo register-margin">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="form-login-heading demo"> SUBSCRIBER REGISTRATION</div>
                    </div>
                </div>
                <!--/ .row-->
                <form method="post" name="user" id="patientRegistration" enctype='application/json' class="form-horizontal well test-form toggle-disabled has-validation-callback ">

                    <input type="hidden" name="role" value="ROLE_PATIENT">
                    <div class="form-wizard">
                        <div class="">
                            <div class="row">
                                <div class="col-md-12 no-padding">
                                    <div class="col-md-6 col-sm-6 no-pad">
                                        <fieldset class="input-block">
                                            <label>First Name<span style="color:red;">*</span></label>
                                            <input type="text" placeholder="First Name" name="userDetails.firstName"    class="form-icon form-icon-user"  data-validation="alphanumeric"  data-validation-error-msg="Enter First Name " >
                                        </fieldset>
                                    </div>
                                    <div class="col-md-6 col-sm-6 no-pad">
                                        <fieldset class="input-block">
                                            <label>Last Name<span style="color:red;">*</span></label>
                                            <input type="text"    name="userDetails.lastName" placeholder="Last Name"  data-validation="alphanumeric"  data-validation-error-msg="Enter Last Name"  >
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12  no-padding">
                                    <div class="col-md-6 col-sm-6 no-pad">
                                        <fieldset class="input-block">
                                            <label> User Name<span style="color:red;">*</span></label>
                                            <input type="text" data-validation="length alphanumeric"
                                                   data-validation-length="3-12"
                                                   data-validation-error-msg="Enter User Name Min (3-12 chars)"   maxlength="12" id="userName" placeholder="User Name"     name="userName" onfocus="checkUserExistsAllReady(this.value);this.checkValidity();">
                                        </fieldset>
                                    </div>
                                    <div class="col-md-6 col-sm-6 no-pad">
                                        <fieldset class="input-block">
                                            <label>Email<span style="color:red;">*</span></label>
                                            <input type="text"  name="userContactInfo.email" placeholder="Email" data-validation-error-msg="Enter Email Id eg:subscribe@abc.com" id="email" data-validation="email"  onblur="return checkEmailExistsAllReady(this.value,'ROLE_PATIENT','email')" >
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
                                            <input type="password" name='password' id="confirm_password"  placeholder="Password" pattern="^.{8,12}$" maxlength="12"  data-validation="length" data-validation-length="min8"  data-validation-error-msg="Password should be minimum 8 characters" />
                                        </fieldset>
                                        <%-- <div class="input_note">Note: Enter 4-12 characters</div>--%>
                                    </div>
                                    <div class="col-md-6 col-sm-6 no-pad">
                                        <fieldset class="input-block">
                                            <label> Confirm Password<span
                                                    style="color:red;">*</span></label>
                                            <input type="password"  name="password_confirmation"  id="password"  placeholder="Confirm  Password"  data-validation="confirmation"  data-validation-confirm="password"  maxlength="12" data-validation-error-msg="Password doesn't match " />
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
                                            <label> Mobile Number<span style="color:red;">*</span></label>
                                            <input id="phone" data-validation="number"   placeholder="Mobile Number" type="tel" name="userContactInfo.mobilePhone" maxlength="10" onblur="checkMobileExistsAllReady(this.value,'ROLE_PATIENT','phone')" data-validation-error-msg="Enter Mobile Number Eg:9876543210"  />
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="registration-btn">
                        <button type="submit" value="Register" class="register-btn" id="send">Sign Up</button>
                    </div>
                    <div class="label-demo"> By clicking Sign Up, you agree to our <a class="terms-link" target="_blank"> Terms and Conditions</a> </div>
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


        $.validate({
            showErrorDialogs : true,
            validateOnBlur : true, // disable validation when input looses focus
            modules : 'toggleDisabled,security',
            disabledFormFilter : 'form.toggle-disabled',
            scrollToTopOnError : true

        });

        $('form').submit(function (e) {
            e.preventDefault();
            $("#patientRegistration").find(".register-btn").html('<img src="../resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');

            var url = "../api/user/register";
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(PRegistrationFormToJSON('#patientRegistration')),
                contentType: 'application/json',
                success: function (data) {
                    var statusCode = data['statusCode'];
                    var message = data['message'];
                    var userName = data['userName'];

                    if (statusCode == 200) {
                        $("body").load("../activate/afterRegistration", function () {
                            $('#userName').text(userName);
                            ChangeUrl('page1', '../activate/afterRegistration');
                        });
                    }
                    else {
                        message = '<div class="notification alert-error spacer-t10"><p>'+message+'</p><a class="close-btn">?</a></div>';
                        $("#eMessage").empty().append(message);
                    }
                    $("#patientRegistration").find(".register-btn").html('Sign Up');

                },
                error: function (data) {
                    $( "input:radio[name=activation]:checked" ).val(true);
                }
            });

            return false;
        });
    });

</script>
<%--
 <script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
--%>
</html>
