<%--
  Created by IntelliJ IDEA.
  User: barath
  Date: 5/9/2016
  Time: 12:29 PM
  To change this template use File | Settings | File Templates.
--%>
<link rel="stylesheet" href="resources/css/jquery.datetimepicker.min.css">
<script src="resources/jsplugins/jquery.datetimepicker.min.js"></script>
<div class="container">
    <div id="tmm-form-wizard" class="container substrate">
        <div class="row clr form-login-heading">
            <div class="col-md-6 col-sm-6 no-pad"><h2 class="app_for_patients_cls">Lab Services </h2></div>
            <div class=" right-document ">
                <button type="button" class="view_cls_pat" id="viewlaborders">View Order Status</button>
            </div>
        </div>

        <form action="/">
            <div class="form-wizard margintop-20">
                <div class="">
                    <div>
                        <div class="col-md-6 col-sm-6 no-padding recorsrightborder">
                            <div class="col-md-4search"><img class="health-list" alt="Search-doctor" src="resources/images/records.png"></div>
                            <div class="col-md-8  no-pad search  ">
                                <fieldset class="input-block">
                                    <label>Select here to view records
                                    </label>

                                    <div class="dropdown">
                                        <select id="recordType" class="dropdown-select">
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
                            <div class="col-md-4search"> <img src="resources/images/upload.png" alt="Search-doctor"> </div>
                            <div class="col-md-8 ">
                                <div id="showUploadPopup" class="form-header-demo-search margin-top33 ">
                                    <label style="font-size: 14px;display:block;">Click here to upload Records</label>
                                    <input type="button" class="saveb editbasicinfo available " value="Upload Records">
                                    <span id="upload"><img src="resources/images/uploadicon.png" alt="uploadbtn"></span>
                                </div>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
            <!--/ .row-->
            <!--/ .form-wizard-->
        </form>

       <%-- <div class="ser-req-for-phar">
            <ul>
                <li>  <a href="my-order.html" class="my-ord">MY ORDER STATUS</a></li>
            </ul>
        </div>--%>
        <div class="note">Note: Microsoft word, excel files cannot be viewed.<br>
            1.Doctor prescription is mandatory for submitting lab request.<br>
            2.Please fill the below fields for sample collection.
        </div>
        <!--lab order-->
        <div class="service-requset-method hidepage">

            <div class="form-wizard doctor-info">
                <div class=" ">
                    <table id="myrecordstable" class="display" cellspacing="0" width="99%">
                        <thead>
                        <tr>
                            <th style="text-align:center;">Select</th>
                            <th style="text-align:center;">Date of visit</th>
                            <th style="text-align:center;">Specialty</th>
                            <th style="text-align:center;">Clinic Name</th>
                            <th style="text-align:center;">Reason for visit</th>
                            <th style="text-align:center;">View</th>

                        </tr>
                        </thead>
                        <tbody id="myrecords">

                        </tbody>
                    </table>
                </div>
                <div id="viewmore_labrecords"  style="cursor: pointer">View More</div>
                <form name="serviceRequests" id="labrequestForm" method="post" enctype="application/json">
                    <input type="hidden" name="serviceType" value="diagnosticLabs" id="sendType">
                    <input type="hidden" name="patientId" id="lab_userId">
                    <input type="hidden" name="labRequests.recordIds" id="recordIds">
                    <div class="ser-req-class">
                        <div class="col-md-6 no-pad pahrmacist">
                            <label><h5 class="req-for-pham">Enter instruction  if any</h5>
                                <textarea class="text-page" name="labRequests.instruction"></textarea>
                            </label>
                        </div>
                        <div class="col-md-6" id="demo-services">
                            <div class="">
                            <h5 class="req-for-pham">Preferred time for sample collection</h5>
                            <ul>
                                <li>
                                    <input type="radio" name="labRequests.preferredTime" value="Morning"><label>Morning</label>
                                </li>
                                <li>
                                    <input type="radio" name="labRequests.preferredTime" value="Afternoon"><label>Afternoon</label>
                                </li>
                                <li>
                                    <input type="radio" name="labRequests.preferredTime" value="Evening"><label>Evening</label>
                                </li>
                            </ul>
                            <div class="clearfix"></div>
                            <div class="submit-resqu">
                                <%--<a href="#" class="sub-req-for-pharmy">SUBMIT REQUEST</a>--%>
                               <%-- <button type="submit" value="SUBMIT REQUEST" class="sub-req-for-pharmy" id="send"></button>--%>
                                   <button type="button" class="saveb editbasicinfo-vitals submit_btn" id="labrequest">Submit request</button>
                            </div>
                        </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div id="addressPopup">
    <div class="form-wrapper confirmAppForm class-popup" id="confirmAppForm">
        <div class="cancel-close1"></div>
        <div class="form-wrapper">
            <div class="confirmappointemnt" style="text-align:center">
                Address for Sample Collection
            </div>
                <div class="confirmpadding" id="tmm-form-wizard">
                    <form method="post" name="userContactInfo" id="userContactInfo" enctype='application/json'>
                        <div class="" id="maincontactInfo">
                            <div class="col-md-6 no-padding">
                                <div class="col-md-6 col-sm-6 no-pad ">
                                    <fieldset class="input-block">
                                        <label> Country<span style="color:red;">*</span></label>

                                        <div class="dropdown">
                                           <%-- <input type="hidden" name="user.userId" id="pciUserId" value="">
                                            <input type="hidden" name="mobilePhone" id="t_mobilePhone" value="">
                                            <input type="hidden" name="email" id="t_email" value="">--%>
                                            <select id="t_ucicountry" name="country"  class="dropdown-select" onchange="combo('t_ucicountry','t_ucistate','t_ucistateInput');"></select>

                                        </div>
                                        <!--/ .tooltip-->
                                    </fieldset>
                                </div>
                                <div class="col-md-6 col-sm-6 no-pad hide" id="dt_ucistate">
                                    <fieldset class="input-block">
                                        <label>State</label>

                                        <div class="dropdown">
                                            <select name="state" id="t_ucistate" title="State Name" class="dropdown-select"></select>
                                        </div>
                                        <!--/ .dropdown-->
                                        <!--/ .tooltip-->
                                    </fieldset>
                                </div>

                                <div class="col-md-6 col-sm-6 no-pad" id="dt_ucistateInput">
                                    <fieldset class="input-block">
                                        <label>State</label>
                                        <input type="text" name="otherState" id="t_ucistateInput" placeholder="State Name" title="Id Number" readonly pattern="^[a-zA-Z ]+$" maxlength="20">
                                        <!--/ .tooltip-->
                                    </fieldset>
                                </div>
                            </div>
                            <div class="col-md-6 no-padding">
                                <div class="col-md-6 col-sm-6 no-pad ">
                                    <fieldset class="input-block">
                                        <label>City<span style="color:red;">*</span></label>
                                        <input type="text" id="t_city" name="city" placeholder="City" readonly required="" title="City" pattern="^[a-zA-Z ]+$" maxlength="20">
                                        <!--/ .tooltip-->
                                    </fieldset>
                                </div>
                                <div class="col-md-6 col-sm-6 no-pad ">
                                    <fieldset class="input-block">
                                        <label>Location<span style="color:red;">*</span></label>
                                        <input type="text" id="t_location" name="location" placeholder="Location" readonly required="" title="Location" pattern="^[a-zA-Z ]+$" maxlength="20">
                                    </fieldset>
                                </div>
                            </div>
                            <div class="col-md-6 no-padding">
                                <div class="col-md-6 col-sm-6 no-pad ">
                                    <fieldset class="input-block">
                                        <label> Address<span style="color:red;">*</span></label>
                                        <input type="text" id="t_address" name="address" required="" readonly placeholder=" House No" title=" House No" maxlength="40">
                                        <!--/ .dropdown-->
                                    </fieldset>
                                </div>
                                <div class="col-md-6 col-sm-6 no-pad ">
                                    <fieldset class="input-block">
                                        <label>Zip Code<span style="color:red;">*</span></label>
                                        <input type="text" id="t_zipCode" name="zipCode" required="" readonly  placeholder="Zip Code" title="Zip Code" maxlength="7" pattern="^[\+?\d]{6,7}">
                                    </fieldset>
                                </div>
                            </div>
                        </div>
                        <div class="">
                            <div class="col-md-12 no-pad">
                                <div class="col-md-6 no-pad" id="hidecheckbox">
                                    <label class="checkbox"><input type="checkbox" value="idcheckbox" id="idcheckbox">Click checkbox for alternate address</label>
                                    <%--<span class="checkbox"> </span>--%>
                                </div>
                                <div class="col-md-6 no-pad">
                                    <button type="button" class="saveb editbasicinfo-vitals submit_btn float-rt" id="submit_lab_request">Submit</button>
                                    <button type="button" class="saveb editbasicinfo-vitals submit_btn float-rt hide" id="back_lab_request">Back</button>
                                </div>
                            </div>
                        </div>
                 </form>
                    <form method="post" name="serviceRequest" id="altuserContactInfo" enctype='application/json' class="hide">
                        <div class="">
                            <div class="col-md-6 no-padding">
                                <div class="col-md-6 col-sm-6 no-pad ">
                                    <fieldset class="input-block">
                                        <label> Country<span style="color:red;">*</span></label>

                                        <div class="dropdown">
                                            <%-- <input type="hidden" name="userId" id="altUserId" value="">
                                             <input type="hidden"  id="alt_mobilePhone" value="">
                                             <input type="hidden"  id="alt_email" value="">--%>
                                            <select id="alt_ucicountry" name="alternateServiceContactInfo.country" required="required"  class="dropdown-select" onchange="combo('alt_ucicountry','alt_ucistate1','alt_ucistateInput1')">

                                            </select>

                                        </div>
                                        <!--/ .tooltip-->
                                    </fieldset>
                                </div>
                                <div class="col-md-6 col-sm-6 no-pad hide" id="dalt_ucistate1">
                                    <fieldset class="input-block">
                                        <label>State</label>

                                        <div class="dropdown">
                                            <select name="alternateServiceContactInfo.state" id="alt_ucistate1" title="State Name" required="required" class="dropdown-select"></select>

                                        </div>
                                        <!--/ .dropdown-->
                                        <!--/ .tooltip-->
                                    </fieldset>
                                </div>

                                <div class="col-md-6 col-sm-6 no-pad" id="dalt_ucistateInput1">
                                    <fieldset class="input-block">
                                        <label>State</label>
                                        <input type="text" name="alternateServiceContactInfo.otherState" id="alt_ucistateInput1" required="" placeholder="State Name" title="Id Number" pattern="^[a-zA-Z ]+$" maxlength="20">
                                        <!--/ .tooltip-->
                                    </fieldset>
                                </div>
                            </div>
                            <div class="col-md-6 no-padding">
                                <div class="col-md-6 col-sm-6 no-pad ">
                                    <fieldset class="input-block">
                                        <label>City<span style="color:red;">*</span></label>
                                        <input type="text" id="alt_city" name="alternateServiceContactInfo.city" placeholder="City" required="" title="City" pattern="^[a-zA-Z ]+$" maxlength="20">
                                        <!--/ .tooltip-->
                                    </fieldset>
                                </div>
                                <div class="col-md-6 col-sm-6 no-pad ">
                                    <fieldset class="input-block">
                                        <label>Location<span style="color:red;">*</span></label>
                                        <input type="text" id="alt_location" name="alternateServiceContactInfo.location" placeholder="Location" required="" title="Location" pattern="^[a-zA-Z ]+$" maxlength="20">
                                    </fieldset>
                                </div>
                            </div>
                            <div class="col-md-6 no-padding">
                                <div class="col-md-6 col-sm-6 no-pad ">
                                    <fieldset class="input-block">
                                        <label> Address<span style="color:red;">*</span></label>
                                        <input type="text" id="alt_address" name="alternateServiceContactInfo.address" required="" placeholder=" Address" title=" Address" maxlength="40">
                                        <!--/ .dropdown-->
                                    </fieldset>
                                </div>
                                <div class="col-md-6 col-sm-6 no-pad ">
                                    <fieldset class="input-block">
                                        <label>Zip Code<span style="color:red;">*</span></label>
                                        <input type="text" id="alt_zipCode" name="alternateServiceContactInfo.zipCode" required="" placeholder="Zip Code" title="Zip Code" maxlength="7" pattern="^[\+?\d]{6,7}">
                                    </fieldset>
                                </div>
                            </div>
                        </div>
                        <div class="">
                            <div class="col-md-12 no-pad">
                                <%--<div class="col-md-6 no-pad">
                                    <input type="checkbox" name="updateContInfo" id="updateContactInfo">
                                    <span class="checkbox">Earse old address and keep this address in contact details</span>
                                </div>--%>
                                <%--<div class="col-md-6 no-pad">
                                    <input type="checkbox" name="addAltContInfo" id="altContactDetail" value="true" checked>
                                    <span class="checkbox">keep this address as alternate contact details</span>
                                </div>--%>
                                <div class="col-md-6 no-pad">
                                    <button type="submit" class="saveb editbasicinfo-vitals submit_btn float-rt">Submit</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        <div class="clearfix"></div>
    </div>
</div>


<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
        <td class='hide'>
            <p class="size">Processing...</p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start hide" disabled>
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>Start</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name">
                {% if (file.url) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div style="font-size:13px;"><span class="label label_name">{%=file.fileName%}</span> <span class='label_status'>{%=file.error%}</span></div>
            {% } %}
        </td>
        <td class='hide'>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}
                <button class="btn btn-danger delete hide" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>Delete</span>
                </button>
                <input type="checkbox" name="delete" value="1" class="toggle">
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <span>Close</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>

<script type="text/javascript">
    $(document).ready(function () {

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

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

        $("#labrequest").click(function () {
            var recordsId = "";
            $.each($("input[name='selectedRecords']:checked"), function () {
                if (recordsId == "") {
                    recordsId += $(this).val();
                }
                else {
                    recordsId += "," + $(this).val();
                }
            });
            if(recordsId){
                $("#recordIds").val(recordsId);
                if($("input[name='preferredTime']:checked").length > 0){
                    var url = "api/user/patient/contactInfo";
                    var userId = $("#userId").text();
                    var jsondata = {"userId": userId};
                    $.ajax({
                        url: url,
                        type: 'POST',
                        dataType: 'json',
                        data: JSON.stringify(jsondata),
                        contentType: 'application/json',
                        mimeType: 'application/json',
                        success: function (data) {
                            var status = data['statusCode'];
                            var message = data['message'];
                            if (status == 400) {
                                $('body').load("/");
                            } else if (status == 200) {
                                getContactInfo(data);
                                $("#addressPopup").addClass("dailog-show");
                            }
                        },
                        error: function (data) {
                            window.location = 'invalidSession';
                        }
                    });
                }else{
                    $("#submit_lab_request").trigger('click');
                }
            }else{
                $('#s_confirmMessage').empty();
                $('#s_errorMessage').empty().prepend("Please select the prescription with lab request");
                $("#s_successMessage").addClass("dailog-show");
            }
        });

        $("#submit_lab_request").click(function () {
            var url = "api/user/patient/saveServiceRequests";
            alert(JSON.stringify(LabServiceRequestFormToJSON('#labrequestForm')));
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(LabServiceRequestFormToJSON('#labrequestForm')),
                contentType: 'application/json',
                success: function (data) {
                    var saveMessage = data['message'];
                    var saveStatus = data['statusCode'];
                    if (saveStatus == '200') {
                        document.getElementById("labrequestForm").reset();
                        $('#s_errorMessage').empty();
                        $('#s_confirmMessage').empty().prepend(saveMessage);
                    } else {
                        $('#s_confirmMessage').empty();
                        $('#s_errorMessage').empty().prepend(saveMessage);
                    }
                    $("#addressPopup").removeClass("dailog-show");
                      $("#s_successMessage").addClass("dailog-show");
                },
                error: function (data) {
                    window.location = 'invalidSession';
                }
            });
        });

        $("form#altuserContactInfo").submit(function (e) {
            e.preventDefault();
            var requestForm = LabServiceRequestFormToJSON('#labrequestForm');
            var contInfo = LabServiceRequestFormToJSON('#altuserContactInfo');
            $.extend(true,requestForm,contInfo);
            var updateContactInfo = $("#updateContactInfo").val();
            var altContactDetail = $("#altContactDetail").val();
            var url = "api/user/patient/saveServiceRequest";
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(requestForm),
                contentType: 'application/json',
                success: function (data) {
                    var saveMessage = data['message'];
                    var saveStatus = data['statusCode'];
                    if (saveStatus == '200') {
                        document.getElementById("labrequestForm").reset();
                        $('#s_confirmMessage').empty().prepend(saveMessage);
                    } else {
                        $('#s_errorMessage').empty().prepend(saveMessage);
                    }
                    $("#addressPopup").removeClass("dailog-show");
                    $("#s_successMessage").addClass("dailog-show");
                },
                error: function (data) {
                    window.location = 'invalidSession';
                }
            });
        });


        $("#idcheckbox").change(function(){
            if(this.checked){
                $("#submit_lab_request,#maincontactInfo,#hidecheckbox").addClass("hide");
                $("#back_lab_request,#altuserContactInfo").removeClass("hide");
                var url = "common/getCountries";
                $.ajax({
                    url: url,
                    type: 'GET',
                    dataType: 'json',
                    contentType: 'application/json',
                    success: function (data) {
                        getCountry(data, 'alt_ucicountry');
                    },
                    error: function (data) {

                    }
                });
            }
        });

       /* $('#updateContactInfo').change(function(){
           if(this.checked){
               $(this).val(true);
               $('#altContactDetail').prop('checked', false);
            } else{
               $(this).val(false);
               $('#altContactDetail').prop('checked', true);
            }
        });

        $('#altContactDetail').change(function(){
            if(this.checked){
                $(this).val(true);
                $('#updateContactInfo').prop('checked', false);
            } else{
                $(this).val(false);
                $('#updateContactInfo').prop('checked', true);
            }
        });*/
        $("#back_lab_request").click(function(){
            $("#submit_lab_request,#maincontactInfo,#hidecheckbox").removeClass("hide");
            $("#back_lab_request,#altuserContactInfo").addClass("hide");
        });

        $("#cancel-common").click(function () {
            $("#s_successMessage").removeClass("dailog-show");
        });

        $(".cancel-close1").click(function () {
            $("#back_lab_request").trigger('click');
            $("#idcheckbox").prop('checked', false);
            $("#addressPopup").removeClass("dailog-show");
        });

        $("#viewlaborders").on('click', function () {
            $("#viewlaborders").html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">Please wait..');
            $(".xdsoft_datetimepicker.xdsoft_noselect.xdsoft_").css("display", "none");
            viewLaborders();
        });

    });

    function viewLaborders() {

        $('#ajaxloaddiv').load("patient/viewLabOrders", function () {
            $('#labOrderTable').dataTable({
                "bDestroy": true
            }).fnDestroy();

            $('#labOrderTable').dataTable({
                "pagingType": "full_numbers",
                responsive:true,
                "aaSorting": [],
                "language": {
                    "emptyTable": "No Scheduled Appointment Found !!!"
                }
            });
        });

    }

    function getDateInFormat(date) {
        var mnth = ("0" + (date.getMonth() + 1)).slice(-2);
        var day = ("0" + date.getDate()).slice(-2);
        return [day, mnth, date.getFullYear()].join("/") + " " + date.getHours() + ":" + date.getMinutes();
    }

</script>
<script type="text/javascript" src="resources/jsplugins/site.js"></script>
<script type="text/javascript" src="resources/jsplugins/dataTables.responsive.min.js"></script>
<script type="text/javascript" src="resources/js/server/patientRecords.js"></script>
<script type="text/javascript" src="resources/js/jsfiles/main.js"></script>

