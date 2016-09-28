<!DOCTYPE html>
<!--[if lte IE 8]> <html class="ie8 no-js" lang="en"> <![endif]-->
<!--[if IE 9]> <html class="ie9 no-js" lang="en-US"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html>
<!--<![endif]-->
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>DataLife</title>
    <meta charset="UTF-8">
    <!--[if lt IE 9]>
    <script src="https://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/resources/images/favicon.ico">
    <!--[if lt IE 9]>
    <link rel="stylesheet" type="text/css" href="/resources/css/ie8.css"/>
    <![endif]-->


    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/site.min.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/dataTables.responsive.min.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.min.css"/>

    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/site.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/dataTables.responsive.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/server/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/server/admin.js"></script>

</head>
    <body id="body">

        <div class="topbar">
            <div class="container">
                <div class="top-info-contact pull-left col-md-6">Call Us Today 944-981-1444 |
                    <a href="mailto:support@datalife.in">support@datalife.in</a>
                </div>
            </div>
        </div>

        <div class="wsmenucontainer clearfix">
            <div class="wsmenuexpandermain slideRight"><a id="navToggle" class="animated-arrow slideLeft"><span></span></a></div>
            <div class="wsmenucontent overlapblackbg"></div>
            <div class="header">
                <div class="container-demo-index">
                    <div class="container no-padding">
                        <div class="">
                            <div class="logo">
                                <a href="/">
                                    <a href="/">
                                        <h1><img src="<%=request.getContextPath()%>/resources/images/datalifelogo.png" alt="datalife"></h1>
                                    </a>
                                </a>
                            </div>
                        </div>
                        <div >${userId}</div> <div >${userName}</div>
                        <div>
                            <p class="sign_out"><a href="<%=request.getContextPath()%>/logout" style="text-decoration: underline">Signout</a></p>
                        </div>
                    </div>
                </div>
                <div>
                    <div id="nav" class="affix-top">
                    <div class="nav-width">
                        <nav class="wsmenu slideLeft clearfix">
                            <ul class="mobile-sub wsmenu-list mob_nav_list">
                             <li><a id="home">Home</a></li>
                                <li>
                                    <a>Master Data</a>
                                    <ul class="wsmenu-submenu admin_submenu">
                                        <li class="col-md-2">
                                            <ul>
                                                <li><a class="head_li">Users</a></li>
                                                <li><a id="user_master">User</a></li>
                                            </ul>
                                        </li>
                                        <li class="col-md-5">
                                            <ul>
                                                <li><a class="head_li">General</a></li>
                                                <li><a>Country</a></li>
                                                <li><a>State</a></li>
                                                <li><a>City</a></li>
                                                <li><a>Location</a></li>
                                                <li><a>Provider City</a></li>
                                                <li><a>record Specialty</a></li>
                                                <li><a>Service City</a></li>
                                                <li><a>Specialty</a></li>
                                                <li><a>Blood Group</a></li>
                                            </ul>
                                        </li>
                                        <li class="col-md-5">
                                            <ul>
                                                <li><a class="head_li">Consultation</a></li>
                                                <li><a>Cardiovascular Labels</a></li>
                                                <li><a>Gastrointestinal Labels</a></li>
                                                <li><a>Lab Categories</a></li>
                                                <li><a>Lab Tests</a></li>
                                                <li><a>Muscoloskeletal Labels</a></li>
                                                <li><a>PE Categories</a></li>
                                                <li><a>Respiratory Labels</a></li>
                                            </ul>
                                        </li>
                                    </ul>
                                </li>
                                <li><a>Settings</a>
                                    <ul class="wsmenu-submenu admin_password">
                                        <li><a id="changePassword">Change Password</a></li>
                                    </ul>
                                </li>
                                <li><a id="messages">Messages</a></li>
                                <li><a id="complaints">Complaints</a></li>
                                <li><a>Create Users</a>
                                    <ul class="wsmenu-submenu admin_password">
                                        <li><a id="service_providers">Service Provider</a></li>
                                        <li><a id="createClinic">Clinic</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
                </div>
                <div id="ajaxloaddiv">

                </div>
                <input type="hidden" id="userId" value="${userId}"/>
                <div id="serviceprovider_show" class="hide">
                    <div class="wrapper">
                        <div class="form-container">
                            <div id="tmm-form-wizard" class="container substrate">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <h2 class="form-login-heading">Service Provider</h2>
                                    </div>
                                </div>
                                <div class="form-wizard margintop-20">
                                    <div class=" ">
                                        <div class="col-md-8  no-pad search ">
                                            <fieldset class="input-block" >
                                                <label>Select the role of Service Provider </label>
                                                <div class="dropdown">
                                                    <select class="dropdown-select" id="selectSPType">
                                                        <option value="">Select Type</option>
                                                        <option value="ROLE_PHARMACY">Pharmacy</option>
                                                        <option value="ROLE_DIAGNOSTICLABS">Diagnostic Labs</option>
                                                        <option value="ROLE_REFERRALDOCTOR">Second Opinion</option>
                                                        <option value="ROLE_TELECONSULTANT">Tele Consultation</option>
                                                        <option value="ROLE_HOSPITAL">Hospital</option>
                                                    </select>
                                                </div>
                                            </fieldset>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-wizard doctor-info tablediv">
                                    <div class=" ">
                                        <table id="providerTable" class="display hide" cellspacing="0" width="100%">
                                            <thead>
                                            <tr>
                                                <th style="width:250px;text-align: center">Name</th>
                                                <th style=" width: 250px;text-align: center">Contact Person</th>
                                                <th style=" width: 250px;text-align: center">Email</th>
                                                <th style=" width: 250px;text-align: center">Contact No</th>
                                                <th style=" width: 250px;text-align: center">Website</th>
                                                <th style=" width: 250px;text-align: center">Action</th>
                                            </tr>
                                            </thead>
                                            <tbody id="providerTableBody">

                                            </tbody>
                                        </table>

                                        <table id="providerTable1" class="display hide" cellspacing="0" width="100%">
                                            <thead>
                                            <tr>
                                                <th style="width:250px;text-align: center">Name</th>
                                                <th style=" width: 250px;text-align: center">MobileNo</th>
                                                <th style=" width: 250px;text-align: center">Email</th>
                                                <th style=" width: 250px;text-align: center">Location</th>
                                                <th style=" width: 250px;text-align: center">Action</th>
                                            </tr>
                                            </thead>
                                            <tbody id="providerTableBody1">

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="clinicRegister" class="hide">
                    <div class="wrapper">
                        <div class="form-container">
                            <div id="tmm-form-wizard" class="container substrate">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <h2 class="form-login-heading">Clinic Registration</h2>
                                    </div>
                                </div>
                                <form method="post" name="user" id="clinicRegistration" enctype='application/json'>
                                    <input type="hidden" name="role" id=userRole" value="ROLE_CLINIC">
                                    <div class="form-wizard">
                                        <div class="">
                                            <div class="row">
                                                <div class="col-md-6 no-padding">
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
                                                <div class="col-md-6 no-padding">
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
                                                            <input id="cmobile" type="tel"  placeholder="Mobile Number" name="userContactInfo.mobilePhone" maxlength="10" onblur="checkMobileExistsAllReady(this.value,'ROLE_CLINIC','mobile')" required='required' pattern="^[\+?\d]{10,12}" />

                                                        </fieldset>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="registration-btn">
                                        <button type="submit" value="Register" class="register-btn">Create User</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="verifyAndCreateProvider">
        <div class="form-wrapper confirmAppForm class-smpopup" id="confirmAppForm" style="display: block;max-width:500px !important;">
            <div id="cancel-management"></div>
            <div class="confirmpadding">
                <div class="note"></div>
                <div class="form-wrapper">
                    <div class="confirmappointemnt">
                        <h1>Verify & SignUp</h1>
                    </div>
                    <div>
                        <form name="providerDetails" method="post" enctype="application/json" id="providerSignupForm">
                            <input type="hidden" id="in_providerId" name="id">
                            <label class="select-records"> Name</label>
                            <input type="text" name="name" placeholder="Name" required="" id="in_name">
                            <label class="select-records"> Registration No</label>
                            <input type="text" name="licNo" placeholder="Registration No" id="in_licNo">
                            <label class="select-records"> Contact Person</label>
                            <input type="text" name="contactPerson" placeholder="Contact Person" required="" id="in_contactPerson">
                            <label class="select-records"> EmailId</label>
                            <input type="text" name="email" placeholder="Email Id" required="" id="in_email">
                            <label class="select-records"> Mobile Number</label>
                            <input type="text" name="mobilePhone" placeholder="Mobile Number" required="" id="in_mobile">
                            <label class="select-records"> Work Phone</label>
                            <input type="text" name="workPhone" placeholder="Work Phone" required="" id="in_workPhone">
                            <label class="select-records"> Website</label>
                            <input type="text" name="website" placeholder="Website"  id="in_website">

                            <%--<input type="text" name="yearofEstablishment" placeholder="Year of Establishment" id="in_yearofEstablishment">--%>
                           <%-- <input type="text" name="providerInfo.licenceNumber" placeholder="Skype Id" id="in_licNo">--%>
                            <%--<label><input type="checkbox" name="accountStatus" value="true" checked id="in_verified"/>Enabled</label>--%>
                            <button type="submit" value="Save" class="register-btn">Create User</button>
                            <button type="button" value="Cancel" id="cancel_profile">Cancel</button>
                        </form>

                        <form name="providerDetails" method="post" enctype="application/json" id="providerSignupForm1">
                            <input type="hidden" id="type" name="type">
                            <input type="hidden" id="providerId" name="id">
                            <label class="select-records"> First Name</label>
                            <input type="text" name="firstName" placeholder="First Name" required="" id="firstName">
                            <label class="select-records"> Last Name</label>
                            <input type="text" name="lastName" placeholder="Last Name" required="" id="lastName">
                            <label class="select-records"> Medical Lincese No </label>
                            <input type="text" name="licNo" placeholder="Lincese No" id="licNo">
                            <label class="select-records"> Email Id</label>
                            <input type="text" name="email" placeholder="Email Id" required="" id="emailId">
                            <label class="select-records"> Mobile Number</label>
                            <input type="text" name="mobilePhone" placeholder="Mobile Number" required="" id="mobile">
                            <label class="select-records"> City</label>
                            <input type="text" name="city" placeholder="City" required="" id="city">
                            <label class="select-records"> Country</label>
                            <input type="text" name="country" placeholder="Country" required="" id="country">

                           <%-- <label><input type="checkbox" name="accountStatus" value="true" checked id="verified"/>Enabled</label>--%>
                            <button type="submit" value="Save" class="register-btn">Create User</button>
                            <button type="button" value="Cancel" id="cancelprofile">Cancel</button>
                        </form>

                    </div>
                </div>

            </div>
        </div>
    </div>

        <div id="s_successMessage">
            <div id="confirmAppForm" class="form-wrapper confirmAppForm class-popup" style="max-width: 500px !important;min-height: 45px; position: absolute;top: 45%;">
                <div id="cancel-common"></div>
                <div class="confirmpadding">
                    <div id="s_confirmMessage" class="s_message"></div>
                    <div id="s_errorMessage" class="s_message"></div>
                </div>
            </div>
        </div>

        <div id="saveEncPopup">
            <div id="confirmAppForm" class="form-wrapper confirmAppForm class-smpopup">
                <div class="confirmappointemnt">DataLife Confirmation</div>
                <div class="confirmpadding">
                    <div id="bodypsv" class="dywcpopup" style="padding: 15px !important;">
                        <div class="note">Do you want to continue?
                        </div>
                        <button type="button" class="cancelapp View" id="Yes">Yes</button>
                        <button type="button" class="cancelapp share" id="No">No</button>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
