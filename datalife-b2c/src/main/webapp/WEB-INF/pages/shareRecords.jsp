<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 7/5/2016
  Time: 11:47 AM
  To change this template use File | Settings | File Templates.
--%>

<script type="text/javascript" src="resources/js/server/patientRecords.js"></script>
<script type="text/javascript" src="resources/js/server/srcommon.js"></script>

<script>
    $(document).ready(function () {
        var url = "common/getRecordSpecialities";
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                $("#recordSpecialities").empty();
                $.each(data, function (idx, obj) {
                    if (obj['name'] == 'Select Speciality') {
                        $("#recordSpecialities").append($("<option value='1' disabled selected></option>").text(obj['name']));
                    } else {
                        $("#recordSpecialities").append($("<option></option>").attr("value", obj['name']).text(obj['name']));
                    }
                });
            },
            error: function (data) {

            }
        });

        var fullDate=new Date();
        $("#records_datepicker").datetimepicker({
            format:'d/m/Y H:i',
            value: getDateInFormat(fullDate),
            maxDate:new Date()
        });

        $('#mybills').find('tbody').on( 'click', 'tr', function () {
            $(this).toggleClass('selected');
        });

    });
    function getDateInFormat(date) {
        var mnth = ("0" + (date.getMonth() + 1)).slice(-2);
        var day = ("0" + date.getDate()).slice(-2);
        return [day, mnth, date.getFullYear()].join("/") + " " + date.getHours() + ":" + date.getMinutes();
    }

</script>


<div class="form-wrapper">
   <%-- <div class="row">
        <div class="col-xs-12">
            <h2 class="form-login-heading">My Records </h2>
        </div>
    </div>--%>
    <form action="/">
        <div class="form-wizard margintop-20">
            <div class="">
                <div >
                    <div class="col-md-6 col-sm-6 no-padding recorsrightborder">
                        <div class="col-md-4search"><img src="resources/images/records.png" alt="Search-doctor" class="health-list"/></div>
                        <div class="col-md-8  no-pad search  ">
                            <fieldset class="input-block" >
                                <label>Select here to view records
                                </label>

                                <div class="dropdown">
                                    <select class="dropdown-select" id="typeofrecords">
                                        <option value="hospital">Clinic Records</option>
                                        <option value="prescription">Prescription</option>
                                        <option value="laborder">Lab Order</option>
                                        <option value="diagnostic">Lab Reports</option>
                                        <option value="xray">X-ray</option>
                                        <option value="mri">MRI</option>
                                        <option value="others">Miscellaneous</option>
                                    </select>
                                </div>
                                <!--/ .tooltip-->

                            </fieldset>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-6  no-padding ">
                        <div class="col-md-4search"> <img alt="Search-doctor" src="resources/images/upload.png"> </div>
                        <div class="col-md-8 ">
                            <div class="form-header-demo-search margin-top33 " id="showUploadPopup">
                                <label style="font-size: 14px;display:block;">Click here to upload Records</label>
                                <input type="button" value="Upload Records" class="saveb editbasicinfo available ">
                                <span id="upload"><img alt="uploadbtn" src="resources/images/uploadicon.png"></span>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </form>
    <div class="">
        <div class="col-md-12"><h4>Note: Click on row to select records to share</h4></div>
        <div id="eMessage"></div>
        <div class="col-md-7">
            <div class="hospital-records" id="record_type">Clinic Records</div>
        </div>
        <div class="col-md-5">
            <div class="form-header-demo-page">
                <div class="col-md-12 click">
                    <div class="dataSize"></div>
                </div>
                <button type="button" id="shareLabOrdersRecords" class=" saveb editbasicinfo-vitals submit_btn">Proceed</button>

            </div>
        </div>
        <div class="clearfix"></div>
    </div>
    <div class="form-wizard doctor-info">
        <div class=" ">
            <table id="mybills" class="display" cellspacing="0" width="99%">
                <thead>
                <tr>
                    <th style="text-align:center;">Date of visit</th>
                    <th style="text-align:center;"> Specialty</th>
                    <th style="text-align:center;">Reason for visit</th>
                    <th style="text-align:center;">Diagnosis</th>
                    <th style="text-align:center;">View </th>
                </tr>
                </thead>
                <tbody id="myrecords">

                </tbody>
            </table>
        </div>
        <div id="viewmore_records"  style="cursor: pointer">View More</div>
        <div class="form-header-demo-page">
            <button type="button" id="shareLabOrdersRecordsD" class=" saveb editbasicinfo-vitals submit_btn">Proceed</button>
        </div>
        <div class="note">Note: Microsoft word, excel files cannot be viewed.  Download such files before opening.</div>
    </div>

</div>
