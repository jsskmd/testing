<link rel="stylesheet" type="text/css" href="resources/css/site.min.css"/>
<link rel="stylesheet" type="text/css" href="resources/css/dataTables.responsive.min.css"/>
<%--<link rel="stylesheet" type="text/css" href="resources/css/jquery-ui.min.css">--%>
<script type="text/javascript" src="resources/jsplugins/site.js"></script>
<script type="text/javascript" src="resources/jsplugins/dataTables.responsive.min.js"></script>

<script type="text/javascript" src="resources/jsplugins/jquery-ui.js"></script>
<script type="text/javascript" src="resources/jsplugins/jquery.datetimepicker.min.js"></script>
<%--<script type="text/javascript" src="resources/js/server/messenger.js"></script>--%>
<script type="text/javascript" src="resources/js/server/bills.js"></script>

<link rel="stylesheet" href="resources/css/cssfiles/blueimp-gallery.min.css"/>
<link rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload.min.css"/>
<link rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload-ui.min.css"/>

<noscript>
    <link rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload-noscript.min.css"/>
</noscript>
<noscript>
    <link rel="stylesheet" href="resources/css/cssfiles/jquery.fileupload-ui-noscript.min.css"/>
</noscript>

<div class="wrapper">
  <div class="form-container">

        <div id="tmm-form-wizard" class="container substrate register-margin">
            <div class="row">
                <div class=" clr form-login-heading">

                    <div class="col-md-6 col-sm-6 no-pad"><h2 class="app_for_patients_cls">My Medical Bills </h2></div>
                    <div class=" right-document ">
                        <span class="dataSize"></span></h2>
                    </div>
                </div>
            </div>
            <!--/ .row-->
            <!--/ .row-->

            <!--/ .row-->
            <form name="bill" id="billsuploadform" method="post" enctype='application/json'>

                <div class="form-wizard margintop-20">
                    <div class="">
                        <div class="row">
                            <div class="col-md-6 no-padding">
                                <div class="col-md-6 no-pad  ">
                                    <fieldset class="input-block">
                                        <label>Date<span style="color:red;">*</span></label>
                                        <input type="text" id="billsdatepicker" class="form-icon form-icon-user" placeholder="Date" required title="Date" name="billDate"
                                               maxlength="10" pattern="^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$" onblur="checkBillDate(this.id);this.checkValidity();"/>
                                        <input type="hidden" name="user.userId" id="billUserId"/>
                                        <!--/ .tooltip-->
                                    </fieldset>
                                </div>
                                <div class="col-md-6  no-pad">
                                    <fieldset class="input-block">
                                        <label>Paid To<span style="color:red;">*</span></label>
                    <input type="text" id="paidTo" placeholder="Paid To" required title="Paid To" name="paidTo"
                           pattern="^[a-zA-Z\.? ]+$" maxlength="30"/>
                                        <!--/ .tooltip-->
                                    </fieldset>
                                </div>
                            </div>
                            <div class="col-md-6  no-padding">
                                <div class="col-md-6  no-pad ">
                                    <fieldset class="input-block">
                                        <label> Paid For</label>
                                        <div class="dropdown">
                                            <select name="paidFor" class="dropdown-select" id="paidFor" >
                                                <option  value="0" selected="selected" disabled="">Select</option>
                                                <option  value="Consultation Fees">Consultation Fees</option>
                                                <option value="Pharmacy">Pharmacy</option>
                                                <option value="Diagnostic">Diagnostic</option>
                                                <option value="Hospital">Hospital Bills </option>
                                                <option value="Others">Others</option>
                                            </select>
                                        </div>
                                        <!--/ .tooltip-->
                                    </fieldset>
                                </div>
                <div class="col-md-6 no-padding">
                  <div class="col-md-6 no-pad  ">
                                    <fieldset class="input-block">
                                        <label> Amount<span style="color:red;">*</span> </label>
                      <input type="text" placeholder="Amount" required title="Amount" id="amount" name="amount"
                             pattern="^\d+(\.\d{1,2})?$"/>
                                        <!--/ .dropdown-->
                                        <!--/ .tooltip-->

                                    </fieldset>
                                </div>
                                    <div class="col-md-6 no-pad ">
                                        <fieldset class="input-block">
                                            <label style="padding-top: 39px;"> </label>
                      <button type="submit" class="saveb editbasicinfo-vitals submit_btn" form="billsuploadform"
                              value="Save">Save
                      </button>
                                            <!--/ .dropdown-->
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div></div>
                            </div>
                        </div>

                    </div>
                    <div id="confirmMessage" class="hide"></div>
                    <div id="errorMessage" class="hide"></div>
                </div>
                <!--/ .row-->
                <!--/ .form-wizard-->
            </form>

            <div class="form-wizard doctor-info">
                <div class=" ">
                    <table id="billsTable" class="display" cellspacing="0" width="99%">
                        <thead>
                        <tr>
                            <th style=" width: 250px;text-align: center">Date</th>
                            <th style=" width: 250px; text-align: center">Paid to</th>
                            <th style="width:250px;text-align: center">Paid for</th>
                            <th style=" width: 250px;text-align: center">Amount</th>
                            <th style=" width: 250px;text-align: center">View /Download</th>
                        </tr>
                        </thead>
                        <tbody id="billFiles">

                        </tbody>
                    </table>
                </div>
                <div  style="float:left"><span>Total Bill : </span><span id="totalBill"></span></div>
                <div id="viewmore_bills" style="cursor: pointer">View More</div>
                <div class="clear"></div>
            </div>

            <form name="bill" method="post" id="billsTotalForm">
                <div>
                    <div class="form-wizard">
                        <div class="row">
                            <div class="col-md-6  no-padding">


                                <div class="col-md-6 ">

                                    <fieldset class="input-block">
                                        <label>From Date<span style="color:red;">*</span></label>
                                        <input type="hidden" id="tbUserId" name="user.userId" value="">
                                        <input type="text" id="fromDate" class="form-icon form-icon-user" placeholder="Date" required title="Date" name="billDate"
                                               maxlength="10" pattern="^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$"
                                               onblur="checkBillDate(this.id);this.checkValidity();"/>
                                        <!--/ .tooltip-->
                                    </fieldset>
                                </div>
                                <div class="col-md-6">
                                    <fieldset class="input-block">
                                        <label>To Date<span style="color:red;">*</span></label>
                                        <input type="text" id="toDate" class="form-icon form-icon-user" placeholder="Date" required title="Date" name="billsDate"
                                               maxlength="10" pattern="^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$"
                                               onblur="checkBillDate(this.id);this.checkValidity();"/>
                                        <!--/ .tooltip-->
                                    </fieldset>
                                </div>

                            </div>

                            <div class="col-md-6  no-padding">

                                <div class="col-md-6 col-sm-6 no-pad ">
                                    <div class="col-md-6 col-sm-6 ">
                                        <button type="submit" id="getTotal" style="float:left;" class=" gettotalbtn saveb editbasicinfo-vitals submit_btn">Get Total</button>
                                    </div>
                                    <div class="col-md-6 col-sm-6 totalamountmg ">
                                        <fieldset class="input-block">
                                            <label class="total-amount">Total :</label>
                                            <span id="totalAmount"></span>
                                            <!--/ .tooltip-->
                                        </fieldset>
                                    </div>
                                </div>


                            </div>


                        </div>

                    </div>
                </div>


            </form>
            <!--/ .form-wizard-->
        </div>


        <!--/ .container-->
    </div>
</div>
<div id="pdfdisplay">

    <div id="confirmAppForm" class="form-wrapper confirmAppForm class-popup" style="max-width: 800px !important;">
        <div class="cancel-common"></div>
        <div class="confirmpadding">
            <iframe src="" id="iframe" class="preview-iframe"></iframe>
            <img src="" id="imgiframe" class="preview-iframe" width="100%"/>
        </div>
    </div>


</div>
<div id="billUploadPopup">
    <div class="form-wrapper confirmAppForm class-smpopup" id="confirmAppForm">
        <div class="cancel-close1"></div>
        <div id="pconfirmMessage" class="hide"></div>

        <div class="confirmpadding" id="tmm-form-wizard">
            <form class="billfileupload" name="bill"
                  method="POST"
                  enctype="multipart/form-data" data-upload-template-id="template-upload-2"
                  data-download-template-id="template-download-2">
                <div class="note"></div>
                <div class="form-wrapper">
                    <div class="confirmappointemnt">
                        Upload Bill
                    </div>

                    <div>
                        <input type="hidden" name="user.userId" id="bill_userId"/>
                        <input type="hidden" name="id" id="bill_id">
                        <div class=" basicdetails-modal button-information">
                            <div class="row fileupload-buttonbar" style="clear: both; padding: 0 15px">
                                <div class="col-md-12">
                                    <div class="note">Click below to upload new bill </div>
                                        <span class="btn btn-success fileinput-button">
                                            <i class="icon-search-circled"></i>
                                            <span class="upload-information">Upload</span>
                                            <input type="file" name="multipartFile">
                                        </span>

                                    <span class="fileupload-process"></span>
                                </div>

                                <div class="col-lg-5 fileupload-progress width100per ">

                                    <div class="progress progress-striped active" role="progressbar"
                                         aria-valuemin="0"
                                         aria-valuemax="100">
                                        <div class="progress-bar progress-bar-success"
                                             style="width:0;"></div>
                                    </div>

                                    <div class="progress-extended">&nbsp;</div>
                                </div>
                            </div>
                            <%-- <div id="perrorMessage"></div>--%>
                            <table role="presentation" class="table table-striped">
                                <tbody class="files"></tbody>
                            </table>
                        </div>


                    </div>

                </div>

            </form>
        </div>
    </div>
</div>
<script id="template-upload-2" type="text/x-tmpl">
    {% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">

    <td>
    <p class="name">{%=file.name%}</p>
    <strong class="error text-danger"></strong>
    </td>
    <td class="hide">
    <p class="size">Processing...</p>
    <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
    </td>
    <td class="hide">
    {% if (!i && !o.options.autoUpload) { %}
    <button class="btn btn-primary start">
    <i class="icon-up-circled"></i>
    <span>Start</span>
    </button>
    {% } %}
    {% if (!i) { %}

    {% } %}
    </td>
    </tr>
    {% } %}


</script>
<!-- The template to display files available for download -->
<script id="template-download-2" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">

        <td>
            <p class="name">
                {% if (file.vdownloadUrl) { %}
                    <a href="{%=file.vdownloadUrl+"&alf_ticket="+file.ticket%}" title="{%=file.fileName%}">{%=file.fileName%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div class="error_records">{%=file.error%}</div>
            {% } %}
        </td>

        <td>
            {% if (file.deleteUrl) { %}

            {% } else { %}
                <button class="btn btn-warning cancel">

                    <span>Close</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}


</script>
<!-- The template to display files available for upload -->
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


<script type="text/javascript" src="resources/js/server/mainbills.js"></script>
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
<!--[if (gte IE 8)&(lt IE 10)]-->

<script src="resources/js/jsfiles/cors/jquery.xdr-transport.js"></script>

