<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<meta name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<link rel="stylesheet" type="text/css" href="resources/css/site.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/dataTables.responsive.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/jquery.datetimepicker.min.css">

<script type="text/javascript" src="resources/jsplugins/typeahead.bundle.js"></script>
<script type="text/javascript" src="resources/jsplugins/handlebars-v3.0.3.min.js"></script>
<script type="text/javascript" src="resources/js/server/patientDemographics.js"></script>
<script type="text/javascript" src="resources/js/server/clinicPatient.js"></script>
<script type="text/javascript" src="resources/js/server/patientRecords.js"></script>
<script type="text/javascript" src="resources/jsplugins/site.js"></script>
<script type="text/javascript" src="resources/jsplugins/dataTables.responsive.min.js"></script>
<script type="text/javascript" src="resources/jsplugins/datetimepicker.js"></script>
<script type="text/javascript" src="resources/js/server/patientRecords.js"></script>
<%--<link rel="stylesheet" href="resources/css/cssfiles/blueimp-gallery.min.css">
<link rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload.min.css">
<link rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload-ui.min.css">
<noscript>
    <link rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload-noscript.min.css">
</noscript>
<noscript>
    <link rel="stylesheet"
          href="resources/css/cssfiles/jquery.fileupload-ui-noscript.min.css">
</noscript>--%>

<!-- The main application script -->
<script type="text/javascript" src="resources/js/jsfiles/main.js"></script>

<script>

    $(document).ready(function () {

        $(".cancel-common").click(function(){
            $("#clinic_accesspatient").removeClass("dailog-show");
            $("#access_clinicpatient").html("");
            $("body").css("overflow","auto");
        });
        var fullDate=new Date();

        $("#records_datepicker").datetimepicker({
            format:'d/m/Y H:i',
            value: getDateInFormat(fullDate),
            maxDate:new Date()
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
                    if (idx == 0 || obj['name'] == 'Select Speciality') {
                        $("#recordSpecialities").append($("<option value='1' disabled selected></option>")
                                .text('Select Speciality')).append($("<option></option>")
                                .attr("value", obj['name'])
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

        $("#cp_profile_head").click(function(){
            $("#cp_profile").removeClass("hide");
            $("#cp_records").addClass("hide");
        });

        $("#cp_records_head").click(function(){
            $("#confirmMessage").empty();
            $("#cp_profile,#uploadRecords").addClass("hide");
            $("#cp_records").removeClass("hide");
            $("#fileType").val(0);
        });

    });

    function getDateInFormat(date) {
        var mnth = ("0" + (date.getMonth() + 1)).slice(-2);
        var day = ("0" + date.getDate()).slice(-2);
        return [day, mnth, date.getFullYear()].join("/") + " " + date.getHours() + ":" + date.getMinutes();
    }

</script>

    <div class="wrapper">
        <div class="form-container">
            <div id="tmm-form-wizard" class="container substrate boxshadownone">

            <div class="row clr form-login-heading">
                <div class="col-md-6 col-sm-6 no-pad" ><h2 class="app_for_patients_cls">Patient Details </h2></div>
            </div>

            <div class=" splitcode">
                <ul class="todayappointmentul">
                    <li>
                        <div class=" searchalign">
                            <div id="cp_profile_head" class="search-doc"><i class="icon-search-1"></i>
                                Demographics
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="searchalign">
                            <div id="cp_records_head" class="ad-doc"><i class="icon-plus-circled"></i>Upload Records</div>
                        </div>
                    </li>
                    <input type="hidden" id="cp_patientId"/>
                </ul>
            </div>

            <div id="cp_profile">
                <%--<jsp:include page="/patient/profile"></jsp:include>--%>
            </div>

            <div id="cp_records" class="hide" style="padding-bottom: 20px;">
                <form id="fileupload" name="uploadFile" method="POST" enctype="multipart/form-data">
                    <div class="note"></div>
                    <div class="form-wrapper">
                        <div class="uploadpopuprecords">
                            <div class="basicdetails-modal">
                                <div class="row">
                                    <div class="col-md-3  no-pad ">
                                        <div class="col-md-12  no-pad ">
                                            <fieldset class="input-block">
                                                <label class="select-records">Select Record Type</label>
                                                <div class="dropdown">
                                                    <select id="fileType" class="dropdown-select" name="fileType">
                                                        <option value="0" selected disabled>Select</option>
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
                                    <div class="col-md-9 no-pad">


                                    </div>
                                </div>
                            </div>

                            <div id="uploadRecords" class="hide">
                                <div class="basicdetails-modal">
                                    <div class="row">
                                        <div class="col-md-6 no-pad">
                                            <div class="col-md-6  no-pad "  >
                                                <fieldset class= "input-block">
                                                    <label class="select-records"> Choose Visit Date</label>
                                                    <input type="text" title=" Choose Visit Date" name="encDate" required
                                                           placeholder="dd/mm/yyyy hh:mm a" id="records_datepicker" readonly>
                                                    <input type="hidden" name="user.userId" id="recordUserId" value=""/>
                                                    <!--/ .dropdown-->
                                                </fieldset>
                                            </div>
                                            <div class="col-md-6  no-pad ">
                                                <fieldset class="input-block">
                                                    <label class="select-records">Specialty</label>
                                                    <div class="dropdown">
                                                        <select class="dropdown-select" id="recordSpecialities" name="speciality" title="Specialty"></select>
                                                    </div>
                                                </fieldset>
                                            </div>
                                        </div>
                                        <div class="col-md-6 no-pad">
                                            <div class="col-md-6  no-pad">
                                                <fieldset class="input-block">
                                                    <label class="select-records">Reason for visit</label>
                                                    <input type="text" title=" Reason for visit " name="chiefComplaint"
                                                           placeholder=" Reason for visit" id="chiefcomplaint"/>
                                                </fieldset>
                                            </div>
                                            <div class="col-md-6  no-pad">
                                                <fieldset class="input-block">
                                                    <label class="select-records">Diagnosis</label>
                                                    <input type="text" title=" Diagnosis " name="diagnosis"
                                                           placeholder=" Diagnosis" id="diagnosis">
                                                    <!--/ .dropdown-->
                                                </fieldset>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="basicdetails-modal">
                                    <div class="row">
                                        <div class="col-md-12 no-pad">
                                            <div class="col-md-12 no-pad">
                                                <div class=" basicdetails-modal " style="border:none">
                                                    <div class="row fileupload-buttonbar" style="clear: both; padding: 0 15px">
                                                        <div class="col-md-12">
                                                            <div class="btn btn-success fileinput-button">
                                                                <i class="icon-search-circled"></i>
                                                                <span class="upload-information">Browse</span>
                                                                <input type="file" name="multiUploadedFileList" multiple="multiple">
                                                            </div>
                                                            <button type="submit" class="btn btn-primary start">
                                                                <i class="icon-up-circled"></i>
                                                                <span class="upload-information">Upload</span>
                                                            </button>
                                                            <span class="fileupload-process"></span>
                                                        </div>
                                                        <div class="col-lg-5 fileupload-progress width100per ">
                                                            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                                                                <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                                                            </div>
                                                            <div class="progress-extended">&nbsp;</div>
                                                            <div id="confirmMessage"></div>
                                                            <div id="errorMessage"></div>
                                                        </div>
                                                    </div>
                                                    <table role="presentation" class="table table-striped">
                                                        <tbody class="files"></tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="note">Note: Multiple files can be uploaded.</div>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        </div>
</div>

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
        <td style="text-align: right;">
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

<%--
<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
<script src="resources/js/jsfiles/vendor/jquery.ui.widget.js"></script>
<!-- The Templates plugin is included to render the upload/download listings -->
<script src="resources/js/jsfiles/tmpl.min.js"></script>
<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
<script src="resources/js/jsfiles/load-image.all.min.js"></script>
<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script src="resources/js/jsfiles/canvas-to-blob.min.js"></script>
<!-- Bootstrap JS is not required, but included for the responsive demo navigation -->
<script src="resources/jsplugins/bootstrap.min.js"></script>
<!-- blueimp Gallery script -->
<script src="resources/js/jsfiles/jquery.blueimp-gallery.min.js"></script>
<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
<script src="resources/js/jsfiles/jquery.iframe-transport.js"></script>
<!-- The basic File Upload plugin -->
<script src="resources/js/jsfiles/jquery.fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script src="resources/js/jsfiles/jquery.fileupload-process.js"></script>
<!-- The File Upload image preview & resize plugin -->
<script src="resources/js/jsfiles/jquery.fileupload-image.js"></script>
<!-- The File Upload audio preview plugin -->
<script src="resources/js/jsfiles/jquery.fileupload-audio.js"></script>
<!-- The File Upload video preview plugin -->
<script src="resources/js/jsfiles/jquery.fileupload-video.js"></script>
<!-- The File Upload validation plugin -->
<script src="resources/js/jsfiles/jquery.fileupload-validate.js"></script>
<!-- The File Upload user interface plugin -->
<script src="resources/js/jsfiles/jquery.fileupload-ui.js"></script>

<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
<!--[if (gte IE 8)&(lt IE 10)]-->
<script src="resources/js/jsfiles/cors/jquery.xdr-transport.js"></script>--%>


