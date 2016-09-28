<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 9/9/2015
  Time: 3:51 PM
  To change this template use File | Settings | File Templates.
--%>

<script type="text/javascript" src="resources/jsplugins/site.js"></script>
<script type="text/javascript" src="resources/jsplugins/dataTables.responsive.min.js"></script>

<div class="form-container"  >
    <div id="tmm-form-wizard" class="container substrate register-margin">
        <%--
                    <div class="row">
                        <div class="col-xs-12">
                            <h2 class="form-login-heading">View Appointments</h2>
                        </div>
                    </div>
        --%>

        <div class="row clr form-login-heading">

            <div class="col-md-6 col-sm-6 no-pad"><h2 class="app_for_patients_cls"> View Appointments </h2></div>
            <div class=" right-document ">

                <a class="saveb editbasicinfo-stage-demo" href=""><i class="icon-left hideicon"></i> Back</a>


            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-header-demo-appointment">
                    <%--
                                            <a class="saveb editbasicinfo-stage-demo" href="">Back</a>
                    --%>
                </div>
            </div>
        </div>
        <!--/ .row-->

        <div class="form-wizard doctor-info">
            <div id="message"></div>
            <div class=" ">
                <table id="scheduledTable" class="display" style="width:99%;">
                    <thead>
                    <tr>
                        <th style="text-align:center;">Date of Appointment</th>
                        <th style="text-align:center;">Date of Booking</th>
                        <th style="text-align:center;">Doctor Name</th>
                        <th style="text-align:center;">Clinic Name</th>
                        <th style="text-align:center;"> Reason</th>
                        <th style="text-align:center;">Status</th>

                    </tr>
                    </thead>

                    <tbody id="appointmentList">

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

