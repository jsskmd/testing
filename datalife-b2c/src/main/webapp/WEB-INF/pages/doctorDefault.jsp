
<div class="wrapper">
<div class="form-container"  >
<div id="tmm-form-wizard" class="container substrate">
<div class="row">
    <div class="col-xs-12">
        <h2 class="form-login-heading">Appointments </h2>
    </div>
</div>
<!--/ .row-->
<div class="row stage-container doctor-timeslot-info">
    <div class="doctor-timeslot-discription">
        <ul class="doctor-list">
            <li><span class="first-slot"> 1 </span>Search Doctor <span><i class="icon-right-dir"></i></span></li>
            <li><span class="first-slot"> 2 </span>Select Doctor <span ><i class="icon-right-dir"></i></span></li>
            <li><span class="first-slot"> 3 </span>Choose Appointment Date <span ><i class="icon-right-dir"></i></span></li>
            <li><span class="first-slot"> 4 </span>Confirm Appointment </li>
        </ul>
    </div>
</div>
<!--/ .row-->
<!--/ .row-->
<form action="/">
    <div class="form-wizard">
        <div class="">
            <div class="row">
                <div class="col-md-6 no-padding">
                    <div class="col-md-4 search"> <img src="/resources/images/helath-logo.png" alt="Search-doctor" /> </div>
                    <div class="col-md-6  no-pad search  ">
                        <fieldset class="input-block">
                            <label>Select Clinic</label>
                            <div class="dropdown">
                                <select  class="dropdown-select" id="select_homeclinic">
                                    <option disabled="" value="" selected="">--- Select ---</option>
                                    <c:forEach var="clinic" items="${clinics}">
                                        <option value="${clinic.key}">${clinic.value}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <!--/ .tooltip-->
                        </fieldset>
                    </div>
                </div>
                <div class="col-md-6  no-padding">

                    <div class="col-md-3 col-sm-3 no-pad">
                        <div class="form-header-demo-search ">
                            <input type="button" value="Select" class="saveb editbasicinfodemo" >
                            <!--<input type="button"  value="Save" class="saveb savebasicinfo">-->
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12 col-sm-12 location-info"><i class="icon-location"></i>Bangalore <span class="change-location"><a href="#">Change City</a></span></div>
            </div>
        </div>
    </div>
    <!--/ .row-->
    <!--/ .form-wizard-->
</form>
<div class="row services-list">
    <div class="col-md-6 col-sm-6 no-padding"> <a href="#">
        <div class="col-md-4 ">
            <div class="services"> <img src="/resources/images/second-opinion.png" alt="second-opinion" class="appointment-service-info" />
                <div class="secondopnion">Second Opinion doctor</div>
            </div>
        </div>
    </a> <a href="#">
        <div class="col-md-4 ">
            <div class="services"> <img src="/resources/images/online-doc.png" alt="online-doctor" class="appointment-service-info" />
                <div class="secondopnion">Online Doctor Facility</div>
            </div>
        </div>
    </a> <a href="#">
        <div class="col-md-4 ">
            <div class="services"> <img src="/resources/images/emergency.png" alt="emergency"  class="appointment-service-info"/>
                <div class="secondopnion">Non Emergency Transport</div>
            </div>
        </div>
    </a> </div>
    <div class="col-md-6 col-sm-6 no-padding"> <a href="#">
        <div class="col-md-4 ">
            <div class="services"> <img src="/resources/images/diagonstic.png" alt="second-opinion" class="appointment-service-info" />
                <div class="secondopnion">Diagnostic Services</div>
            </div>
        </div>
    </a> <a href="#">
        <div class="col-md-4 ">
            <div class="services"> <img src="/resources/images/pharmacy.png" alt="second-opinion" class="appointment-service-info" />
                <div class="secondopnion">Pharmacy</div>
            </div>
        </div>
    </a> <a href="#">
        <div class="col-md-4 ">
            <div class="services"> <img src="/resources/images/medical-tourism.png" alt="second-opinion" class="appointment-service-info" />
                <div class="secondopnion">Medical Tourism</div>
            </div>
        </div>
    </a> </div>
</div>
<div class="doctor-full-slots" style="display:none;">
    <div class="form-wizard doctor-info">
        <div class="row">
            <div class="doctor-timeslot-discription">
                <div class="col-md-6">
                    <div class="col-md-3 image">
                        <div class="doc-image"><img src="/resources/images/doc-image.png" alt="doc" /></div>
                    </div>
                    <div class="col-md-4 demo">
                        <div class="doc-info">
                            <div class="doc-name">Dr. Simon</div>
                            <div class="doc-database">Dentist</div>
                            <div class="doc-database">Polyclinic</div>
                            <div class="doc-database"> 03 Years of Experience</div>
                            <div class="doc-database">LangFord Town, United State.</div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-sm-6">
                    <div class="doct-timeslot-demo">
                        <div class="week-slot">MON - FRI [10:00 AM - 01:00 PM, 04:00 PM - 08:00 PM]</div>
                        <div class="week">SAT - SUN [10:00 AM - 01:00 PM]</div>
                        <div class="fees">Consultation Fee: 230 USD</div>
                        <input type="button" onClick="window.location='doctor-profile.html';" class="saveb editbasicinfo-doc" value="View Doctor Profile">
                        <input type="button" value="Fix an Appointment" class="saveb editbasicinfo-info" id="datepicker">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="content-index-demo">
        <div class="full-time-slots">
            <div class=" doctor-info slots">
                <div class="row">
                    <div class="timesoltheading">
                        <div class="timesoltheadingleft">
                            <ul>
                                <li> <span class="green-available"></span>Available</li>
                                <li> <span class="grey-notavailable"></span>Not Available </li>
                            </ul>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
            </div>
            <div class="form-wizard doctor-info">
                <div class="row">
                    <div class="slot-time">
                        <div class="demo-date">28 Jul 2015 (Tues)</div>
                    </div>
                    <div class="slot-time-demo">
                        <div class="demo-list">
                            <ul class="ulslots">
                                <li class="" ><a class="available" href="#">10:00 AM</a></li>
                                <li class=""><a href="#">10:20 AM</a></li>
                                <li><a href="#">10:40 AM</a></li>
                                <li class=""><a  href="#">11:00 AM</a></li>
                                <li><a href="#">11:20 AM</a></li>
                                <li><a href="#">11:40 AM</a></li>
                                <li><a  class="confirmApp 6"  href="#">12:00 PM</a></li>
                                <li class=""><a  class="confirmApp 7"  href="#">12:20 PM</a></li>
                                <li><a  class="confirmApp 8"  href="#">12:40 PM</a></li>
                                <li><a  class="confirmApp 9"  href="#">04:00 PM</a></li>
                                <li class=""><a  class="confirmApp 10"  href="#">04:20 PM</a></li>
                                <li><a  class="confirmApp 11"  href="#">04:40 PM</a></li>
                                <li><a  class="confirmApp 12"  href="#">05:00 PM</a></li>
                                <li><a  class="confirmApp 13"  href="#">05:20 PM</a></li>
                                <li><a class="confirmApp 14"  href="#">05:40 PM</a></li>
                                <li><a  class="confirmApp 15"  href="#">06:00 PM</a></li>
                                <li><a class="cancledslotclr" title="CANCELLED"  href="#">06:20 PM</a></li>
                                <li><a  class="confirmApp 17"  href="#">06:40 PM</a></li>
                                <li><a  class="confirmApp 18"  href="#">07:00 PM</a></li>
                                <li><a class="compltedslotclr" title="COMPLETED"  href="#">07:20 PM</a></li>
                                <li><a  class="confirmApp 20"  href="#">07:40 PM</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="form-wizard doctor-info">
        <div class="row">
            <div class="doctor-timeslot-discription">
                <div class="col-md-6">
                    <div class="col-md-3 image">
                        <div class="doc-image"><img src="/resources/images/doc-image.png" alt="doc" /></div>
                    </div>
                    <div class="col-md-4 demo">
                        <div class="doc-info">
                            <div class="doc-name">Dr.Samuel Paul</div>
                            <div class="doc-database">Cardiologist</div>
                            <div class="doc-database">Polyclinic</div>
                            <div class="doc-database"> 03 Years of Experience</div>
                            <div class="doc-database">LangFord Town, United State.</div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 col-sm-6">
                    <div class="doct-timeslot-demo">
                        <div class="week-slot">MON - FRI [10:00 AM - 01:00 PM, 04:00 PM - 08:00 PM]</div>
                        <div class="week">SAT - SUN [10:00 AM - 01:00 PM]</div>
                        <div class="fees">Consultation Fee: 230 USD</div>
                        <input type="button" onClick="window.location='doctor-profile.html';" class="saveb editbasicinfo-doc" value="View Doctor Profile">
                        <input type="button" value="Fix an Appointment" class="saveb editbasicinfo-info" id="datepicker-demo">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="content-index-demo-page" style="display:none">
        <div class="full-time-slots">
            <div class=" doctor-info slots">
                <div class="row">
                    <div class="timesoltheading">
                        <div class="timesoltheadingleft">
                            <ul>
                                <li> <span class="green-available"></span>Available</li>
                                <li> <span class="grey-notavailable"></span>Not Available </li>
                            </ul>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
            </div>
            <div class="form-wizard doctor-info">
                <div class="row">
                    <div class="slot-time">
                        <div class="demo-date">28 Jul 2015 (Tues)</div>
                    </div>
                    <div class="slot-time-demo">
                        <div class="demo-list">
                            <ul class="ulslots">
                                <li><a href="#">10:00 AM</a></li>
                                <li class=""><a href="#">10:20 AM</a></li>
                                <li><a href="#">10:40 AM</a></li>
                                <li class=""><a  href="#">11:00 AM</a></li>
                                <li><a href="#">11:20 AM</a></li>
                                <li><a href="#">11:40 AM</a></li>
                                <li><a  class="confirmApp 6"  href="#">12:00 PM</a></li>
                                <li class=""><a  class="confirmApp 7"  href="#">12:20 PM</a></li>
                                <li><a  class="confirmApp 8"  href="#">12:40 PM</a></li>
                                <li><a  class="confirmApp 9"  href="#">04:00 PM</a></li>
                                <li class=""><a  class="confirmApp 10"  href="#">04:20 PM</a></li>
                                <li><a  class="confirmApp 11"  href="#">04:40 PM</a></li>
                                <li><a  class="confirmApp 12"  href="#">05:00 PM</a></li>
                                <li><a  class="confirmApp 13"  href="#">05:20 PM</a></li>
                                <li><a  class="confirmApp 14"  href="#">05:40 PM</a></li>
                                <li><a  class="confirmApp 15"  href="#">06:00 PM</a></li>
                                <li><a class="cancledslotclr" title="CANCELLED"  href="#">06:20 PM</a></li>
                                <li><a  class="confirmApp 17"  href="#">06:40 PM</a></li>
                                <li><a  class="confirmApp 18"  href="#">07:00 PM</a></li>
                                <li><a class="compltedslotclr" title="COMPLETED"  href="#">07:20 PM</a></li>
                                <li><a  class="confirmApp 20"  href="#">07:40 PM</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--/ .form-wizard-->
</div>
<!--/ .container-->
</div>
</div>
