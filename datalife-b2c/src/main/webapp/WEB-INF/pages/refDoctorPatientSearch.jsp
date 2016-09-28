
<div class="wrapper">
    <div class="form-container">
        <div id="tmm-form-wizard" class="container substrate">
            <div class="row">
                <div class="col-xs-12">
                    <h2 class="form-login-heading">Patients </h2>
                </div>

            </div>
            <div class="">

                <div class="confirmpadding" >

                    <div class="note"></div>
                    <div class="form-wrapper">

                        <div id="confirmMessage"></div>

                        <div id="search_patient_show">
                            <div id="add_patient_view">

                                <div class="row" id="disc-class">
                                    <form method="post" id="refDoctorPatient_search">
                                        <div class="col-md-3 no-pad ">
                                            <fieldset class="input-block">

                                                <input type="text" class="form-icon form-icon-user demo" placeholder="Search"
                                                       required="" title="Search" id="searchPatientInput">

                                            </fieldset>
                                        </div>
                                        <div class="col-md-3 no-pad serach">
                                            <input type="submit" class="saveb editbasicinfo" value="Search">
                                        </div>
                                    </form>
                                </div>
                                <div class="background-for-list-space classwrap" id="cliniPatientsDiv">
                                    <div class=" ">

                                        <table id="clinicPatientsTable" class="display" cellpadding="0" cellspacing="0" width="100%">
                                            <thead>
                                            <tr>
                                                <th style="text-align:center;">DateLife ID</th>
                                                <th style="text-align:center;"> Name</th>
                                                <th style="text-align:center;">Mobile Number</th>
                                                <th style="text-align:center;">New Consultation</th>

                                            </tr>
                                            </thead>
                                            <tbody id="clinicPatientsTbody">

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>

                        </div>


                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <script src="resources/jsplugins/jquery.datetimepicker.min.js"></script>

            <script type="text/javascript" src="resources/js/server/consultation.js"></script>

            <!--/ .form-wizard-->
        </div>

        <!--/ .container-->
    </div>
</div>



