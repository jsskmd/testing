<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 8/17/2015
  Time: 5:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>
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
    <link rel="shortcut icon" type="image/x-icon" href="resources/images/favicon.ico">
    <!--Meta Tag-->
    <link rel="stylesheet" href="resources/css/style.min.css">

    <!--Main Menu File-->
    <link href="resources/css/fonts.css" rel='stylesheet' type='text/css'>
    <script>
        function preventBack(){window.history.forward();}
        setTimeout("preventBack()", 0);
        window.onunload=function(){null};
    </script>
</head>
<body>
<div class="topbar">
    <div class="container">
        <div class="top-info-contact pull-left ">Call Us Today 94498-11444  | <a href="mailto:support@datalife.in ">support@datalife.in</a> </div>
        <div class="social-wrap-head col-md-2 no-pad pull-right">
        </div>
    </div>
</div>
<div class="">
    <div class="wsmenuexpandermain slideRight"><a id="navToggle" class="animated-arrow slideLeft" href="javascript:void(0)"><span></span></a></div>
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

                                <div class="book-an-appointment"><a class="btn btn-success btn-14" href="http://localhost"><i class="icon-home hideicon"></i> BACK TO HOME</a></div>
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
    <div class="wrapper">
        <div class="form-container">
            <div id="tmm-form-wizard" class="substrate demo login">
                <!--/ .row-->
                <div class="row">
                    <div class="col-xs-12">
                        <div class="form-login-heading demo"> Login/ Register</div>
                    </div>
                </div>
                <!--/ .row-->
                <!--/ .row-->
                <div class="">
                    <div class="">
                        <div class="">
                            <div class="col-md-12 no-padding">
                                <form name='loginForm' action="<c:url value='/j_spring_security_check'/>" method='POST'>

                                    <div class="col-md-6 col-sm-12 " style="padding: 15px;float: none; margin: auto">
                                        <%--
                                                                                <div class="datalife-heading">DataLife</div>
                                        --%>                                    <c:if test="${error != null}">
                                        <div id="errorMessage">${error}</div>
                                    </c:if>
                                        <div id="confirmMessage" style="display: none;">${message}</div>
                                        <fieldset class="input-block">
                                            <input type="text"  required="required" name="username"
                                                   placeholder="UserName" title="UserName" class="form-icon form-icon-user" >
                                            <!--<span class="field-icon">
                                            <i class="icon-user-1"></i>
                                            </span>-->
                                            <!--/ .tooltip-->
                                        </fieldset>
                                        <fieldset class="input-block">
                                            <input type="password" required="required" name="password" placeholder="Password"
                                                   title="Password" class="form-icon form-icon-user"  >
                                            <input type="hidden" name="${_csrf.parameterName}"
                                                   value="${_csrf.token}"/>
                                            <!--/ .tooltip-->
                                        </fieldset>
                                        <div class="doctor-timeslot-discription-search">
                                            <input type="submit" value="Sign In" class="saveb editbasicinfo signup">
                                            <span class="forgot-password"><a href="<%=request.getContextPath()%>/forgotPwd">Forgot Password?</a></span> </div>
                                    </div></form>
                                <%-- <form>
                                 <div class="col-md-6 col-sm-6 signup-border">
                                     <div class="datalife-heading-signup">New to DataLife?<br></div>
                                     <span class="signuphere">Sign Up here</span></div>
                                     <fieldset class="input-block">
                                        &lt;%&ndash; <span class="signuphere">Register as</span>&ndash;%&gt;
                                        &lt;%&ndash; <ul class="registration">
                                           <li> <a href="<%=request.getContextPath()%>/reg/patient"> Subscriber</a> </li>
                                             <li> <a href="<%=request.getContextPath()%>/reg/clinic" class="clinics-reg"> Clinic</a> </li>
                                             <li> <a href="<%=request.getContextPath()%>/reg/serviceprovider" class="serviceprovider"> Service Providers</a> </li>
                                         </ul>&ndash;%&gt;
                                         <div class="dropdown">
                                             <select class="dropdown-select"   placeholder="Select User Type" id="signupType">
                                                 <option selected="" disabled="">Select User Type</option>
                                                 <option value="1">Subscriber</option>
                                                 <option value="2">Clinic</option>
                                                 <option value="3">Service Providers</option>
                                                &lt;%&ndash; <option value="4">Referral Doctor</option>&ndash;%&gt;

                                             </select>
                                         </div>
                                         <!--/ .tooltip-->
                                     </fieldset>
                                     <div class="doctor-timeslot-discription-search">
                                         <input type="button" value="Sign Up" class="saveb editbasicinfo signup" id="signupTypeButton">
                                     </div>
                                 </form>--%>
                                <form>
                                    <div class="signup-borders">
                                        <div class="col-md-6 col-sm-12 signup-border " style="float: none; margin: auto" >
                                            <div class="datalife-heading-signup">New to DataLife?<br>
                                                <span class="signuphere">Sign Up here</span></div>
                                            <fieldset class="input-block">
                                                <div class="dropdown" style="margin: auto; ">
                                                    <select class="dropdown-select"   placeholder="Select User Type" id="signupType">
                                                        <option selected="" disabled="">Select User Type</option>
                                                        <option value="1">Subscriber</option>
                                                        <option value="2">Clinic</option>
                                                        <option value="3">Service Providers</option>
                                                        <%--     <option value="4">Referral Doctor</option>--%>

                                                    </select>
                                                </div>
                                                <!--/ .tooltip-->
                                            </fieldset>
                                            <div class="doctor-timeslot-discription-search">
                                                <input type="button" value="Sign Up" class="saveb editbasicinfo signup" id="signupTypeButton">
                                            </div>
                                        </div>
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
<div class="container"><div class="footer-main">© 2016 Datalife. All Rights Reserved</div>  </div>
<!--/ .row-->
<!--/ .form-wizard-->
<!--/ .form-wizard-->
<!--/ .container-->
<script type="text/javascript" src="resources/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="resources/jquery/scripts.js"></script>
<script type="text/javascript" src="resources/jsplugins/bootstrap.min.js"></script>
<script type="text/javascript">

    $(document).ready(function (e) {
        $("#signupTypeButton").click(function () {
            var signupType = $("#signupType").val();
            if (signupType == 1) {
                window.location = '<%=request.getContextPath()%>/reg/patient';
            }
            else if (signupType == 2) {
                window.location = '<%=request.getContextPath()%>/reg/clinic';
            }
            else if (signupType == 3) {
                window.location = '<%=request.getContextPath()%>/reg/serviceprovider';
            }
            /*else if (signupType == 4) {
             window.location = '<%=request.getContextPath()%>/reg/referralDoctor';
             }*/
            e.preventDefault();
        });

    });

</script>
<!-- Go to www.addthis.com/dashboard to customize your tools -->
</body>
</html>

