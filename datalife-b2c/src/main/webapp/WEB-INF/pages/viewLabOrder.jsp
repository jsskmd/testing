<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 5/11/2016
  Time: 6:24 PM
  To change this template use File | Settings | File Templates.
--%>

<script type="text/javascript" src="resources/jsplugins/site.js"></script>
<script type="text/javascript" src="resources/jsplugins/dataTables.responsive.min.js"></script>

<div class="form-container"  >
    <div id="tmm-form-wizard" class="container substrate">

        <div class="row clr form-login-heading">

            <div class="col-md-6 col-sm-6 no-pad"><h2 class="app_for_patients_cls"> Lab Orders</h2></div>
            <div class=" right-document ">
                <a class="saveb editbasicinfo-stage-demo" href=""><i class="icon-left hideicon"></i> Back</a>
            </div>
        </div>

        <div class="form-wizard doctor-info">
            <div id="message"></div>
            <div class=" ">
                <table id="labOrderTable" class="display" style="width:99%;">
                    <thead>
                    <tr>
                        <th style="text-align:center;">OrderID</th>
                        <th style="text-align:center;">Date of Order</th>
                        <th style="text-align:center;">Lab Name</th>
                        <th style="text-align:center;">Test Name</th>
                        <th style="text-align:center;">Status</th>
                        <th style="text-align:center;">Date of Result</th>
                    </tr>
                    </thead>

                    <tbody id="labOrderList">

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


