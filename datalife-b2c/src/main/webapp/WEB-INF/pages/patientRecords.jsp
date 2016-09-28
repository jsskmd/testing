
<link rel="stylesheet" href="resources/css/jquery.datetimepicker.min.css">
<div class="wrapper">
    <div class="form-container">
        <div id="tmm-form-wizard" class="container substrate register-margin">
            <div class="row">
                <div class="col-xs-12">
                    <h2 class="form-login-heading">My Records </h2>
                </div>
            </div>
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
                                            <select class="dropdown-select" id="recordType">
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
                           <%-- <div class="col-md-6 col-sm-6  no-padding ">
                                <div class="col-md-4search"> <img alt="Search-doctor" src="resources/images/upload.png"> </div>
                                <div class="col-md-8 ">
                                    <div class="form-header-demo-search margin-top33 " id="showUploadPopup">
                                        <label style="font-size: 14px;display:block;">Click here to upload Records</label>
                                        <input type="button" value="Upload Records" class="saveb editbasicinfo available ">
                                        <span id="upload"><img alt="uploadbtn" src="resources/images/uploadicon.png"></span>
                                    </div>
                                </div>
                            </div>--%>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
                <!--/ .row-->
                <!--/ .form-wizard-->
            </form>

            <!--/ .row-->
            <!--/ .row-->
            <div class="">
                <div class="col-md-7">
                    <!--/ .form-header-->
                    <div class="hospital-records" id="record_type">Clinic Records</div>
                </div>
                <div class="col-md-5">
                    <div class="form-header-demo-page">
                        <div class="col-md-12 click">
                            <div class="dataSize"></div>
                        </div>

                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <!--/ .row-->

            <div class="form-wizard doctor-info">
                <div class=" ">
                    <table id="mybills" class="display" cellspacing="0" width="99%">
                        <thead>
                        <tr>
                            <th style="text-align:center;">Select to share</th>
                            <th style="text-align:center;">Date of visit</th>
                            <th style="text-align:center;"> Specialty</th>
                            <th style="text-align:center;">Reason for visit</th>
                            <th style="text-align:center;">Diagnosis</th>
                            <th style="text-align:center;">View / Share</th>
                            <th style="text-align:center;">Action</th>
                        </tr>
                        </thead>
                        <tbody id="myrecords">

                        </tbody>
                    </table>
                </div>
                <div id="viewmore_records"  style="cursor: pointer">View More</div>
                <div class="note">Note: Microsoft word, excel files cannot be viewed.  Download such files before opening.</div>
            </div>
        </div>
        <!--/ .container-->
    </div>
</div>


<div id="shareRecordPopup">
    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-smpopup">
        <div class="cancel-common"></div>
        <div class="confirmpadding">
            <div id="bodypsv">
                <div class="confirmappointemnt">
                    <h1> Share Document</h1>
                </div>
                <div class="note">Note: Enter email id (can enter multiple email id's with comma separation) to share
                    documents.
                </div>
                <div id="perrorMessage"></div>
                <form name="user" method="post" id="shareReportForm">
                    <input type="hidden" id="shareUserId" name="userId" value="">
                    <div class="shareemailpasswordholder">
                        <div class="shareemail">
                            <div class=""><label>Enter Email</label></div>
                            <input type="text" id="demo-input-facebook-theme" name="email"
                                   placeholder="Enter Email Address" required=""/>
                        </div>

                        <div class="clear"></div>
                    </div>
                    <input type="hidden" id="selectedsummary" name="reports"/>
                    <input type="hidden" id="shareticket" name="alfresAuthTicket"/>

                    <div id="sharebuttons">
                        <%--<button type="submit" value="Share" class="register-btn" id="send">Share</button>--%>
                        <button type="submit" value="Share" class="register-btn saveb editbasicinfodemo shareRecordbut">Share</button>
                        <%--<img src="resources/images/small_loading.gif" class="hide small_loading">--%>
                        <div class="clear"></div>
                    </div>
                </form>
                <div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="moveRecordPopup">
    <div class="form-wrapper confirmAppForm class-smpopup" id="confirmAppForm">
        <div class="cancel-common"></div>
        <div class="confirmpadding" id="tmm-form-wizard">
            <form name="uploadFile" method="post" id="moveReportForm">
                <div class="note"></div>
                <div class="form-wrapper">
                    <div class="confirmappointemnt">
                        Move Document
                    </div>
                    <div>
                        <div id="moveMessage"></div>
                        <div class="basicdetails-modal no-pad">
                            <div class="">
                                <div class="col-md-12 no-padding">
                                    <fieldset class="input-block no-pad">
                                        <label class="select-records">Select Record Type</label>
                                        <div class="dropdown">
                                            <select id="movefileType" class="dropdown-select" name="fileType" required="">
                                                <option value="" selected disabled>Select</option>
                                                <option value="hospital">Clinic Records</option>
                                                <option value="prescription">Prescription</option>
                                                <option value="laborder">Lab Order</option>
                                                <option value="diagnostic">Lab Reports</option>
                                                <option value="xray">X-ray</option>
                                                <option value="mri">MRI</option>
                                                <option value="others">Miscellaneous</option>
                                            </select>
                                            <input type="hidden" id="fileId" name="fileId"/>
                                        </div>
                                        <!--/ .tooltip-->
                                    </fieldset>
                                </div>
                            </div>
                        </div>
                        <div>
                            <button type="submit" class="saveb editbasicinfodemo moveRecordbut" value="Move">
                                Move
                            </button>

                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<%--<!-- The blueimp Gallery widget -->
<div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls" data-filter=":even">
    <div class="slides"></div>
    <h3 class="title"></h3>
    <a class="prev">‹</a>
    <a class="next">›</a>
    <a class="close">×</a>
    <a class="play-pause"></a>
    <ol class="indicator"></ol>
</div>--%>
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

<script type="text/javascript" src="resources/jsplugins/site.js"></script>
<script type="text/javascript" src="resources/jsplugins/dataTables.responsive.min.js"></script>
<script type="text/javascript" src="resources/jsplugins/datetimepicker.js"></script>
<script type="text/javascript" src="resources/js/server/patientRecords.js"></script>
<!-- The main application script -->
<script type="text/javascript" src="resources/js/jsfiles/main.js"></script>
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
                        $("#recordSpecialities").append($("<option value='1' disabled selected></option>")
                                .text(obj['name']));
                    } else {
                        $("#recordSpecialities").append($("<option></option>")
                                .attr("value", obj['name'])
                                .text(obj['name']));
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


    });
    function getDateInFormat(date) {
        var mnth = ("0" + (date.getMonth() + 1)).slice(-2);
        var day = ("0" + date.getDate()).slice(-2);
        return [day, mnth, date.getFullYear()].join("/") + " " + date.getHours() + ":" + date.getMinutes();
    }

</script>



