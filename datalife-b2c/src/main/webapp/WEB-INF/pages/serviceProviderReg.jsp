<%--
  Created by IntelliJ IDEA.
  User: Tanoj
  Date: 07-04-2016
  Time: 23:55
  To change this template use File | Settings | File Templates.
--%>
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
    <meta charset="utf-8">

    <title>Registration page</title>
    <meta charset="UTF-8">
    <!--[if lt IE 9]>
    <script src="https://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/resources/images/favicon.ico">
    <!--[if lt IE 9]>
    <link rel="stylesheet" type="text/css" href="css/ie8.css" />
    <![endif]-->
    <!--Meta Tag-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/jquery-ui.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.min.css">
    <%--<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/formside.css">--%>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/font-awesome.min.css">

    <!--Main Menu File-->
    <link href='https://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700italic,700,900,900italic' rel='stylesheet' type='text/css'>
    <!--Main Menu File-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/dd_style.css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/sumoselect.css" />

    <script type="text/javascript" src="../resources/jquery/jquery-1.11.2.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/jsplugins/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/droope_v1.0.0.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/tooltip.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/jquery.sumoselect.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/js/server/registrationCommon.js"></script>
</head>
<body>
    <div class="topbar">
    <div class="container">
        <div class="top-info-contact pull-left col-md-6">Call Us Today! 901-432-6358  | <a href="mailto:support@datalife.in ">support@datalife.in</a> </div>
        <div class="social-wrap-head col-md-2 no-pad pull-right">

        </div>
    </div>
</div>
    <div class="wsmenucontainer clearfix">
    <div class="wsmenuexpandermain slideRight"><a id="navToggle" class="animated-arrow slideLeft" href=""><span></span></a></div>
    <div class="wsmenucontent overlapblackbg"></div>
    <div class="header">
    <div class="container-demo-index">
        <div class="container no-padding">
            <div class="">
                <div class="logo"> <a href="/"> <a href="/">
                    <h1> <img src="<%=request.getContextPath()%>/resources/images/datalifelogo.png" alt="datalife"></h1>
                </a></a> </div>
                <div id="nav" class="">
                    <div class="">
                        <!--Menu HTML Code-->
                        <nav class="wsmenu slideLeft clearfix">
                            <ul class="mobile-sub wsmenu-list demo" id="signin">
                                <div class="book-an-appointment"><a class="btn btn-success btn-14 " href="<%=request.getContextPath()%>"><i class="icon-home hideicon"></i> BACK TO LOGIN</a></div>
                            </ul>
                        </nav>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
    <div class="wrapper">
        <div class="form-container"  >
            <div id="tmm-form-wizard" class="container substrate">
                <div class="row">
                    <div class="col-xs-12">
                        <h2 class="form-login-heading">Service Provider Registration </h2>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <ul id="serviceprovider">
                            <li class="col-md-3 serviceprovider"><input type="radio" name="serviceprovider" value="Hospital"><i class="icon-hospital"></i>Hospital(Surgery Referral) </li>
                            <li class="col-md-3 serviceprovider"><input type="radio" name="serviceprovider" value="SecondOpinion"> <i class="icon-user-md"></i>Second Opinion</li>
                            <li class="col-md-3 serviceprovider"> <input type="radio" name="serviceprovider" value="TeleConsultation">  <i class="icon-user"></i>TeleConsultation</li>
                            <li class="col-md-3 serviceprovider" > <input type="radio"  name="serviceprovider" value="Pharmacy">  <i class="icon-plus-circled"></i>Pharmacy</li>
                            <li class="col-md-3 serviceprovider"> <input type="radio" name="serviceprovider" value="Lab">  <i class="icon-user"></i>Lab</li>
                        </ul>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div id="default">
                    <div class="form-wizard">
                        <h1 style='text-align:center;'>Please select the type of service you would like to register...</h1>
                    </div>
                </div>
                <div id="Hospital" class="hide">
                    <%--<jsp:include page="surgeryReferral.jsp"/>--%>
                </div>
                <div id="SecondOpinion" class="hide">
                   <%-- <jsp:include page="secondOpinion.jsp"/>--%>
                </div>
                <div id="TeleConsultation" class="hide">
                   <%-- <jsp:include page="teleconsultation.jsp"/>--%>
                </div>
                <div id="Pharmacy" class="hide">
                </div>
                <div id="Lab" class="hide">
                    <%--<form action="/">
                        <div class="form-wizard">
                            <div class="">
                            <div class="row">
                                <div class="col-md-6 no-padding">
                                    <div class="col-md-6 col-sm-6 no-pad ">
                                        <fieldset class="input-block">
                                            <label>Lab Name</label>
                                            <input type="text" title="Lab Name" required="" placeholder="" class="form-icon form-icon-user" >
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                    <div class="col-md-6 col-sm-6 no-pad ">
                                        <fieldset class="input-block">
                                            <label>Registration Number</label>
                                            <input type="text" title="Registration Number" required="" placeholder=" ">
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                </div>
                                <div class="col-md-6  no-padding">
                                    <div class="col-md-6 col-sm-6 no-pad ">
                                        <div class="col-md-6 col-sm-6 no-pad ">

                                            <fieldset class="input-block">
                                                <label>Year of Establishment </label>
                                                <input type="text" id="datepicker" title="Year of Establishment" required="" placeholder=" " class="hasDatepicker">
                                                <!--/ .tooltip-->
                                            </fieldset>
                                        </div>
                                        <div class="col-md-6 col-sm-6 no-pad ">
                                            <fieldset class="input-block">
                                                <label>Contact Person</label>
                                                <input type="text" title="Registration Number" required="" placeholder=" ">
                                                <!--/ .tooltip-->
                                            </fieldset>
                                        </div>
                                    </div>
                                    <div class="col-md-6 col-sm-6 no-pad ">
                                        <div class="col-md-6 col-sm-6 no-pad ">
                                            <fieldset class="input-block">
                                                <label> Work Phone</label>
                                                <input type="text" title="Registration Number" required="" placeholder=" ">
                                            </fieldset>
                                        </div>
                                        <div class="col-md-6 col-sm-6 no-pad ">
                                            <fieldset class="input-block">
                                                <label>Phone Number</label>
                                                <input type="text" title="Registration Number" required="" placeholder=" ">
                                            </fieldset>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6  no-padding">
                                    <div class="col-md-6 col-sm-6 no-pad  ">
                                        <fieldset class="input-block">
                                            <label>  Email</label>
                                            <input type="text" title=" Email" required="" placeholder=" ">
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                    <div class="col-md-6 col-sm-6 no-pad ">
                                        <fieldset class="input-block">
                                            <label>Website</label>
                                            <input type="text" title=" " required="" placeholder=" ">
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                </div>
                                <div class="col-md-6  no-padding">
                                    <div class="col-md-6 col-sm-6 no-pad  ">
                                        <fieldset class="input-block">
                                            <label>  Address</label>
                                            <input type="text" title=" Address" required="" placeholder=" ">
                                        </fieldset>
                                    </div>
                                    <div class="col-md-6 col-sm-6 no-pad ">
                                        <fieldset class="input-block">
                                            <label>Country</label>
                                            <div class="dropdown">
                                                <select class="dropdown-select" id="splCountry"><option value="" disabled="" selected="">Select Country</option><option value="4">Canada</option><option value="2">India</option><option value="7">Malaysia</option><option value="1">Select Country</option><option value="8">Singapore</option><option value="6">Sri Lanka</option><option value="5">United Kingdom</option><option value="3">United States of America</option></select>
                                                <input type="hidden" name="country" id="lcountryName" required="">
                                            </div>
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6  no-padding">
                                    <div class="col-md-6 col-sm-6 no-pad  ">
                                        <fieldset class="input-block">
                                            <label> City Name</label>
                                            <input type="text" title=" City Name" required="" placeholder=" ">
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                    <div class="col-md-6 col-sm-6 no-pad ">
                                        <div class="col-md-6 col-sm-6 no-pad ">
                                            <fieldset class="input-block">
                                                <label>Location </label>
                                                <input type="text" placeholder=" " required="" title="Location ">
                                                <!--/ .tooltip-->
                                            </fieldset>
                                        </div>
                                        <div class="col-md-6 col-sm-6 no-pad ">
                                            <fieldset class="input-block">
                                                <label>Zip Code</label>
                                                <input type="text" placeholder=" " required="" title="Zip Code">
                                            </fieldset>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6  no-padding">
                                    <div class="col-md-6 col-sm-6 no-pad ">
                                        <fieldset class="input-block">
                                            <label>Accrediations</label>
                                            <input type="text" title=" Address" required="" placeholder=" ">
                                        </fieldset>
                                    </div>
                                    <div class="col-md-6 col-sm-6 no-pad  ">
                                        <fieldset class="input-block">
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12  no-padding">
                                    <div class="col-md-12 col-sm-12 no-pad  ">
                                        <fieldset class="input-block">
                                            <ul> <li><a href="#"> Haematology</a></li></ul>
                                        </fieldset>

                                    </div>
                                </div>

                                <div class="clearfix"></div>
                                <div class="margin-top33">
                                    <div class="registration-btn">
                                        <button type="submit" value="Register" class="register-btn" >Sign Up</button>
                                    </div>
                                    <div class="label-demo"> By clicking Sign Up, you agree to our <a class="terms-link" target="_blank"> Terms and Conditions</a> </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6  no-padding">

                                </div>
                            </div>
                        </div>
                        </div>
                    </form>--%>
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
                    <li><a href="<%=request.getContextPath()%>/terms">Terms &amp; Conditions</a></li>
                </ul>
                <p class="col-xs-12 col-md-5 no-pad text-right ">Copyright 2016 Datalife | All Rights Reserved </p>
            </div>
        </div>
    </div>
</div>
</div>

    <script>
    $(function () {
        'use strict';
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

        $("#serviceprovider").on('click','input',function(){
            var sp = $(this).val();
            $("#"+sp).removeClass('hide');
            switch(sp){
                case "Hospital":
                        $("#Hospital").load("<%=request.getContextPath()%>/reg/hospital");
                    $("#SecondOpinion,#TeleConsultation,#Pharmacy,#Lab,#default").addClass('hide');
                    break;
                case "SecondOpinion":
                    $("#SecondOpinion").load("<%=request.getContextPath()%>/reg/secondOpinion");
                    $("#Hospital,#TeleConsultation,#Pharmacy,#Lab,#default").addClass('hide');
                    break;
                case "TeleConsultation":
                    $("#TeleConsultation").load("<%=request.getContextPath()%>/reg/teleConsultation");
                    $("#Hospital,#SecondOpinion,#Pharmacy,#Lab,#default").addClass('hide');
                    break;
                case "Pharmacy":
                    $("#Pharmacy").load("<%=request.getContextPath()%>/reg/pharmacy");
                    $("#Hospital,#SecondOpinion,#TeleConsultation,#Lab,#default").addClass('hide');
                    break;

                case "Lab":
                    $("#Lab").load("<%=request.getContextPath()%>/reg/lab");
                    $("#Hospital,#SecondOpinion,#Pharmacy,#TeleConsultation,#default").addClass('hide');
                    break;
            }
        });

    });

    function ProviderRegistrationForm(form) {
        var json = {};
        var parentName;
        var newVal;
        jQuery.each(form, function () {

            parentName = this.name;
            newVal = this.specialityId;
            json[newVal] = parentName || '';
        });
        return json;
    }

</script>
</body>
</html>

