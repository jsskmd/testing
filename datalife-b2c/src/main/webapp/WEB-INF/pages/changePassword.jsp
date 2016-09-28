<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 8/19/2015
  Time: 2:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!--[if lte IE 8]> <html class="ie8 no-js" lang="en"> <![endif]-->
<!--[if IE 9]> <html class="ie9 no-js" lang="en-US"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html>
<!--<![endif]-->
<head>
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
    <link href='https://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700italic,700,900,900italic'
          rel='stylesheet' type='text/css'>

    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery/scripts.js"></script>

    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/bootstrap.min.js"></script>

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

    <div class="header">    <div class="wsmenucontent overlapblackbg"></div>

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

    <div class="header">
        <div class="container-demo-index">

        </div>
        <!--Select Color -->
        <!--Select Color -->
    </div>
    <div class="wrapper pop-up-msg">
        <div class="form-container">
            <div id="tmm-form-wizard" class="substrate demo">
                <form>
                    <div class="margin30">
                        <div class="">
                            <div><a href="/">
                                <h1 style="text-align: center;"><img src="../resources/images/datalifelogo.png" alt="datalife"></h1>
                            </a></div>
                            <div class="row">
                                <div class="col-md-12 no-padding">

                                    <div class="col-md-12 col-sm-12  signup-border">
                                        <div class="maintitle "><b>Congratulations</b></div>
                                        <div class="clearfix"></div>
                                        <div class="form-wizard">
                                            <div id="success" style="display: none"><h2 class="sucessuser" id="userName">${userName}</h2>
                                                <p>Your password is updated successfully!
                                                </p></div>
                                            <div id="error"><h2 class="sucessuser">Dear User,</h2>
                                                <p>This link is already used to reset password!
                                                </p></div>
                                            <p> To Sign in <a href="/emr/login">Click Here</a></p>

                                        </div>
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

