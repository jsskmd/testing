<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 8/19/2015
  Time: 12:49 PM
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
    <meta charset="UTF-8">
    <!--[if lt IE 9]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/resources/images/favicon.ico">
    <!--Meta Tag-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.min.css">
    <!--Main Menu File-->
    <link href="<%=request.getContextPath()%>/resources/css/fonts.css" rel='stylesheet' type='text/css'>

    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery/scripts.js"></script>

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
            <div id="tmm-form-wizard" class="substrate demo">
                <!--/ .row-->

                <!--/ .row-->
                <!--/ .row-->
                <form>
                    <div class="margin30">
                        <div class="">
                            <div class="row">
                                <div class="col-md-12 no-padding">

                                    <div class="col-md-12 col-sm-12  signup-border">
                                        <div class="maintitle "> <b>Congratulations</b> </div>
                                        <div class="clearfix"></div>
                                        <div class="form-wizard">

                                            <h2 class="sucessuser" id="userName"></h2>
                                            <p id="message">Thank you for submitting details with DataLife.</p>

                                        </div>
                                        <%--<p style="float: right;"><a class="btn btn-success btn-14" href="/" style="text-decoration: underline">Back to Home</a></p>--%>
                                    </div>

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
</html>

