<%--
  Created by IntelliJ IDEA.
  User: dscblpo
  Date: 4/11/2016
  Time: 1:36 PM
  To change this template use File | Settings | File Templates.
--%>

<noscript>
    <link rel="stylesheet" href="../resources/css/cssfiles/jquery.fileupload-ui-noscript.min.css">
</noscript>
<link rel="stylesheet" href="../resources/css/jquery.datetimepicker.min.css">
<link rel="stylesheet" href="../resources/css/cssfiles/blueimp-gallery.min.css">
<link rel="stylesheet" href="../resources/css/cssfiles/jquery.fileupload.min.css">
<link rel="stylesheet" href="../resources/css/cssfiles/jquery.fileupload-ui.min.css">
<noscript>
    <link rel="stylesheet" href="../resources/css/cssfiles/jquery.fileupload-noscript.min.css">
</noscript>

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

<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
<script src="../resources/js/jsfiles/vendor/jquery.ui.widget.js"></script>
<!-- The Templates plugin is included to render the upload/download listings -->
<script src="../resources/js/jsfiles/tmpl.min.js"></script>
<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
<script src="../resources/js/jsfiles/load-image.all.min.js"></script>
<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script src="../resources/js/jsfiles/canvas-to-blob.min.js"></script>
<!-- Bootstrap JS is not required, but included for the responsive demo navigation -->
<script src="../resources/jsplugins/bootstrap.min.js"></script>
<!-- blueimp Gallery script -->
<script src="../resources/js/jsfiles/jquery.blueimp-gallery.min.js"></script>
<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
<script src="../resources/js/jsfiles/jquery.iframe-transport.js"></script>
<!-- The basic File Upload plugin -->
<script src="../resources/js/jsfiles/jquery.fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script src="../resources/js/jsfiles/jquery.fileupload-process.js"></script>
<!-- The File Upload image preview & resize plugin -->
<script src="../resources/js/jsfiles/jquery.fileupload-image.js"></script>
<!-- The File Upload audio preview plugin -->
<script src="../resources/js/jsfiles/jquery.fileupload-audio.js"></script>
<!-- The File Upload video preview plugin -->
<script src="../resources/js/jsfiles/jquery.fileupload-video.js"></script>
<!-- The File Upload validation plugin -->
<script src="../resources/js/jsfiles/jquery.fileupload-validate.js"></script>
<!-- The File Upload user interface plugin -->
<script src="../resources/js/jsfiles/jquery.fileupload-ui.js"></script>
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
<!--[if (gte IE 8)&(lt IE 10)]-->
<script src="../resources/js/jsfiles/cors/jquery.xdr-transport.js"></script>


<form name="providerDetails" id="fileupload" method="POST" enctype="multipart/form-data">
<input type="hidden" name="role" value="ROLE_HOSPITAL">
<input type="hidden" name="type" value="Hospital">
    <div class="form-wizard">
    <div class="">
        <div class="row">
            <div class="col-md-9">
                <div class="form-header">
                    <div class="form-title slots"><b>Hospital Details</b></div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 no-padding">
                <div class="col-md-6 col-sm-6 no-pad">
                    <fieldset class="input-block">
                        <label>Hospital Name / Firm Name<span style="color:red;">*</span></label>
                        <input type="text" id="hosp_name" name="name" placeholder="Hospital Name/Firm Name" required="" title="Hospital Name/Firm Name" pattern="^[a-zA-Z ]+$" maxlength="50"/>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
                <div class="col-md-6 col-sm-6 no-pad">
                    <fieldset class="input-block">
                        <label>Registration/Licence No<span style="color:red;">*</span></label>
                        <input type="text" id="hosp_licenceNumber" name="licNo" placeholder="Registration Number" required="" title="Registration Number" onBlur="checkLicRegExistsAllReady(this.value,'hosp_licenceNumber');"/>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
            </div>
            <div class="col-md-6 no-padding">
                <div class="col-md-6 col-sm-6 no-pad ">
                    <fieldset class="input-block">
                        <label>Established Year </label>
                        <input type="text" id="hosp_establishmentYear" name="yearofEstablishment" placeholder="Established Year" required="" pattern="[0-9]{4}"
                               title="Established Year(eg: 1932)"/>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
                <div class="col-md-6 col-sm-6 no-pad ">
                    <fieldset class="input-block">
                        <label>Services<span style="color:red;">*</span></label>
                        <div class="dropdown overflow-visible">
                            <select class="dropdown-select SlectBox-grp" required multiple="multiple" id="hosServices">

                            </select>
                            <input type="hidden" name="speciality" id="servicesOfHos">
                            <!--/ .tooltip-->
                        </div>
                    </fieldset>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-9">
                <div class="form-header">
                    <div class="form-title slots"><b> Contact Info</b></div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 no-padding">
                <div class="col-md-6 col-sm-6 no-pad">
                    <fieldset class="input-block">
                        <label>Contact Person<span style="color:red;">*</span></label>
                        <input type="text" placeholder="Contact Person" name="contactPerson" required="required" pattern="^[a-zA-Z ]+$" class="form-icon form-icon-user"  title="only Alphabets with 30 character">
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
                <div class="col-md-6 col-sm-6 no-pad">
                    <fieldset class="input-block">
                        <label>Contact Person Mobile No<span style="color:red;">*</span></label>
                        <input type="text" pattern="^\d{10}$" required="required" name="mobilePhone" id="hos_mob" placeholder="Mobile No" title="Use number only Eg: 9*******99" onblur="checkMobileExistsAllReady(this.value,'ROLE_HOSPITAL','hos_mob')">
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
            </div>
            <div class="col-md-6  no-padding">
                <div class="col-md-6 col-sm-6 no-pad">
                    <fieldset class="input-block">
                        <label> Work Phone<span style="color:red;">*</span></label>
                        <input type="text" pattern="^\d{10}$" name="workPhone" placeholder="Work Phone" required title="Use number only Eg: 9*******99" />
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
                <div class="col-md-6 col-sm-6 no-pad">
                    <fieldset class="input-block">
                        <label>Email<span style="color:red;">*</span></label>
                        <input type="text" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" name="email" placeholder="Email" id="hos_email" required onblur="checkEmailExistsAllReady(this.value,'ROLE_HOSPITAL','hos_email');"
                               title="Eg:xyz@datalife.com"/>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 no-padding">
                <div class="col-md-6 col-sm-6 no-pad ">
                    <fieldset class="input-block">
                        <label>Website</label>
                        <input type="text" id="hosp_website" name="website" placeholder="Website" pattern="^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\?([^#]*))?(#(.*))?" title="http://www.datalife.in or http://www.datalife.com">
                    </fieldset>
                </div>
                <div class="col-md-6 col-sm-6 no-pad">
                    <fieldset class="input-block">
                        <label>Address <span style="color:red;">*</span></label>
                        <input type="text" required="required" name="address" placeholder="Address" title="Eg: #00 00th main 00th cross street">
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
            </div>
            <div class="col-md-6  no-padding">
                <div class="col-md-6 col-sm-6 no-pad">
                    <fieldset class="input-block">
                        <label>Location<span style="color:red;">*</span></label>
                        <input type="text" maxlength="30" pattern="^[a-zA-Z ]+$" name="location" placeholder="Location" required
                               title="Eg: Rajajinagar,Kormagala"/>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>

                <div class="col-md-6 col-sm-6 no-pad">
                    <fieldset class="input-block" id="surcity_fieldset">
                        <label> Select City<span style="color:red;">*</span></label>
                        <div class="dropdown">
                            <select class="dropdown-select" id="surCity" name="city"  required=""></select>
                        </div>
                    </fieldset>
                </div>

            </div>
        </div>
        <div class="row">
            <div class="col-md-6 no-padding">
                <div class="col-md-6 col-sm-6 no-pad">
                    <fieldset class="input-block">
                        <label>ZipCode<span style="color:red;">*</span></label>
                        <input type="text" maxlength="7" pattern="^[\+?\d]{6,7}" name="zipCode" placeholder="zipCode" required
                               title="Eg: 560010 or 5600101"/>
                        <!--/ .tooltip-->
                    </fieldset>
                </div>
                <div class="col-md-6 col-sm-6 no-pad">
                    <fieldset class="input-block">
                        <label>Country <span style="color:red;">*</span></label>
                        <div class="dropdown">
                            <select id="alt_ucicountry" name="country" required="required"  class="dropdown-select">
                            </select>
                            <!--/ .tooltip-->
                        </div>
                    </fieldset>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-9">
                <div class="form-header">
                    <div class="form-title slots"><b> Additional Information</b></div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6  no-padding">
            <div class="col-md-6 col-sm-6 no-pad">
                <fieldset class="input-block">
                    <label> Facilities<span style="color:red;">*</span></label>
                    <textarea title="Facilities" required="" id="hosp_facilities" placeholder="Facilities" name="facilities"></textarea>                    <!--/ .tooltip-->
                </fieldset>
            </div>
            <div class="col-md-6 col-sm-6 no-pad">
                <fieldset class="input-block">
                    <label>Accreditations/Awards If Any </label>
                    <textarea name="awardsIfAny" placeholder="Accreditations/Awards If Any" id="hosp_awards"
                              title="Accreditations/Awards If Any"></textarea>
                    <!--/ .tooltip-->
                </fieldset>
            </div>
        </div>
            <div class="col-md-6  no-padding">
                <div class="col-md-6 col-sm-6 no-pad ">
            <fieldset class="input-block">
                <label> </label>
                <div class="sr_req_reg">
                    <div class=" ">
                            <div class=" basicdetails-modal button-information">
                                <div class="row fileupload-buttonbar" style="clear: both; padding: 0 15px">
                                    <p>Upload Brochures/Packages </p>
                                    <div class="col-md-12">
                                        <div class="btn btn-success fileinput-button">
                                            <i class="icon-search-circled"></i>
                                            <span class="upload-information">Browse</span>
                                            <input type="file" name="multiUploadedFileList">
                                        </div>
                                        <%--<button &lt;%&ndash;type="submit"&ndash;%&gt; id="upload" class="btn btn-primary start">
                                            <i class="icon-up-circled"></i>
                                            <span class="upload-information">Upload</span>
                                        </button>
                                        <input type="hidden" name="id" id="providerId">--%>
                                        <div id="progress" class="progress">
                                            <div class="progress-bar progress-bar-success"></div>
                                        </div>
                                    </div>
                                 <%--   <div class="col-lg-5 fileupload-progress width100per ">
                                        <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                                            <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                                        </div>
                                        <div class="progress-extended">&nbsp;</div>

                                    </div>--%>
                                    <div id="confirmMessage"></div>
                                    <div id="errorMessage"></div>
                                </div>
                                <table role="presentation" class="table table-striped">
                                    <tbody class="files"></tbody>
                                </table>
                            </div>
                    </div>
                </div>
            </fieldset>
        </div>
            </div>
            <div class="clearfix"></div>
            <div class="margin-top33">
                <div class="registration-btn" id="appendbtn">
                    <button class="register-btn" id="hospitalRegistration" value="Register" type="submit">Submit</button>
                </div>
                <div class="label-demo"> By clicking Sign Up, you agree to our <a target="_blank" class="terms-link"> Terms and Conditions</a> </div>
            </div>
        </div>
    </div>
</div>
</form>

<script>
$(document).ready(function () {
        'use strict';
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

    fetchCountry("alt_ucicountry");

    $("#alt_ucicountry").on('change', function () {
        var country = $(this).val();
        var id = 'surcity_fieldset';
        var inputId = 'surCity';
        fetchHospitalCity(country,id,inputId);
    });

    var url1 = "../common/getRecordSpecialities";
    $.ajax({
        url: url1,
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            $.each(data, function (idx, obj) {
                $('#hosServices').append($("<option></option>").attr("value", obj['specialityId']).text(obj['name']));
            });
            $('.SlectBox-grp').SumoSelect({okCancelInMulti:true, selectAll:true});
        },
        error: function (data) {

        }
    });



    // Initialize the jQuery File Upload widget:
    $('#fileupload').each(function () {
        $("#surgery_userId").val($("#userId").text());
        var sol = "";
        $('.SlectBox-grp option:selected').each(function(){
            if(sol != ""){
                sol +=','+$(this).val();
            }else{
                sol = $(this).val();
            }
            $("#servicesOfHos").val(sol);

        });
        var url = '../api/user/provider/providerRegistration';
        $(this).fileupload({
            formdata: {'_csrf': token},
            dataType: 'json',
            url: url,
            dropZone: $(this),
            autoUpload:false,
            add: function (e, data) {

                var uploadErrors = [];
                var acceptFileTypes = /^image\/(gif|jpe?g|png|PDF|pdf)$/i;
                if (data.files[0]['size'] && !acceptFileTypes.test(data.files[0]['type'])) {
                    uploadErrors.push('Not an accepted file type(Allowed gif|jpe?g|png|PDF|pdf)');
                }
                if (data.files[0]['size'] && data.files[0]['size'] > 5000000) {
                    uploadErrors.push('Filesize is too big,max size 5MB');
                }
                if (uploadErrors.length > 0) {
                    $.each(uploadErrors, function (index, value) {
                        $('#errorMessage').empty().prepend(value);
                    });
                } else {

                var that = this;
                if (!$('form#fileupload')[0].checkValidity()) {
                    $(this).find(':submit').click();
                    $(".align-link").addClass('hide');
                } else {
                    $("#hospitalRegistration").addClass('hide').prop('type', 'button');
                    $("#appendbtn").find("#fileUploadDynamic").remove();
                    data.context = $('<button/>').text('SUBMIT').attr({
                        class: 'register-btn',
                        id: 'fileUploadDynamic',
                        type: 'submit'})
                            .appendTo("#appendbtn").click(function () {
                                $("#appendbtn").find(".register-btn").html('<img src="../resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
                                data.submit();
                            });
                    $(".align-link").removeClass('hide');
                    $.blueimp.fileupload.prototype.options.add.call(that, e, data);
                }
            }
            },
            done: function (e, data) {
                var res = data['result'];
                var files = res['files'];
                var status = files['statusCode'];
                if (status == 200) {
                    document.getElementById("fileupload").reset();
                    $("tbody.files").empty();
                } else {
                    $("#errorMessage").prepend(files['message']);
                    $('#errorMessage').empty().prepend('<div class="notification alert-info spacer-t10">' +
                            '<p>'+files['message']+'</p><span class="close-btndemo"></span></div>').children(':first').delay(2000).fadeOut(100, function () {
                        $('#errorMessage').empty();
                    });
                }
            },
            fail: function (e, data) {
            }
        })
    });

    // Enable iframe cross-domain access via redirect option:
    $('#fileupload').fileupload(
            'option',
            'redirect',
            window.location.href.replace(
                    /\/[^\/]*$/,
                    '/cors/result.html?%s'
            )
    );

    if (window.location.hostname === 'blueimp.github.io') {
        // Demo settings:
        $('#surgeryRequest').fileupload('option', {
            url: '//jquery-file-upload.appspot.com/',
            // Enable image resizing, except for Android and Opera,
            // which actually support image resizing, but fail to
            // send Blob objects via XHR requests:
            disableImageResize: /Android(?!.*Chrome)|Opera/
                    .test(window.navigator.userAgent),
            maxFileSize: 5000000,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i
        });
        // Upload server status check for browsers with CORS support:
        if ($.support.cors) {
            $.ajax({
                url: '//jquery-file-upload.appspot.com/',
                type: 'HEAD'
            }).fail(function () {
                $('<div class="alert alert-danger"/>')
                        .text('Upload server currently unavailable - ' +
                                new Date())
                        .appendTo('#serviceRequest');
            });
        }
    } else {
        // Load existing files:
        $('#fileupload').addClass('fileupload-processing');
        $.ajax({
            // Uncomment the following to send cross-domain cookies:
            //xhrFields: {withCredentials: true},
            url: $('#surgeryRequest').fileupload('option', 'url'),
            dataType: 'json',
            context: $('#surgeryRequest')[0]
        }).always(function () {
            $(this).removeClass('fileupload-processing');
        }).done(function (result) {
            $(this).fileupload('option', 'done')
                    .call(this, $.Event('done'), {result: result});
        });
    }

    $('form#fileupload').off().on('submit',function(e){
        e.preventDefault();
        if($('#fileupload')[0].checkValidity()) {
            $("#fileupload").find(".register-btn").html('<img src="../resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
            var sol = "";
            $('.SlectBox-grp option:selected').each(function(){
                if(sol != ""){
                    sol +=','+$(this).val();
                }else{
                    sol = $(this).val();
                }
                $("#servicesOfHos").val(sol);
            });

            var url = "../api/user/provider/submitDetails";
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(mobileActiveFormToJSON('#fileupload')),
                contentType: 'application/json',
                success: function (data) {
                    var statusCode = data['statusCode'];
                    var message = data['message'];
                    switch (statusCode) {
                        case 200:
                            document.getElementById("fileupload").reset();
                            $("body").load("../activate/afterSubmission", function () {
                                $('#userName').text(data['name']);
                                $("#message").empty().append(message);
                                ChangeUrl('page1', 'activate/afterSubmission');
                            });
                            break;
                        case 100:
                            break;
                    }

                },
                error: function (data) {

                }
            });
        }
    });


    });

function ProviderRegistrationFormToJSON(form) {
    var json = {};
    var newName;
    var parentName;
    var newVal;
    var array = jQuery(form).serializeArray();
    jQuery.each(array, function () {

        var name = this.name;
        if (name.indexOf(".") > -1) {
            parentName = name.split('.')[0];
            newName = name.split('.')[1];
        }
        else {
            parentName = this.name;
            newVal = this.value;
        }
        json[parentName] = newVal || '';

    });
    return json;
}

function ProviderRegistrationForm(form) {
    var json = {};
    var parentName;
    var newVal;
    jQuery.each(form, function () {

        parentName = this.name;
        newVal = this.specialityId;
        json[newVal] = parentName || '';
    });
    return json;
}
</script>
