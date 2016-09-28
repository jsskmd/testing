/*
 * jQuery File Upload Plugin JS Example 8.9.1
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */
/* global $, window */



$(function () {
    'use strict';
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $("tbody.files").on('click','.btn-warning',function(){
        $(this).closest('tr').remove();
    });

    var locationName = new Bloodhound({
        datumTokenizer: function (d) {
            return d.name;
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: 'common/%QUERY',

            replace: function(url, query) {
                if(query == ''){
                    query = "getLocations";
                    return url.replace("%QUERY",query);
                }else{
                    return url.replace("%QUERY","getLocationLike/"+query);
                }
            },
            filter: function (response) {
                return response;
            }
        },
        limit: 10
    });
    locationName.initialize();
    var quicksearchInput = $("#location");
    quicksearchInput.typeahead({
            hint:false,
            highlight: true,
            minLength: 0
        },
        {
            name:'locationName',
            displayKey: 'locationName',
            source: locationName.ttAdapter(),
            templates: {
                empty: [
                ].join('\n'),
                suggestion: Handlebars.compile('<div><strong>{{locationName}}</strong></div>')
            }
        });

    quicksearchInput.on("typeahead:opened", function () {
        var ev = $.Event("keydown");
        ev.keyCode = ev.which = 40;
        $(this).trigger(ev);
        return true
    });


    // Initialize the jQuery File Upload widget:
    $('#surgeryRequest').each(function () {
        $("#surgery_userId").val($("#userId").text());
        var url = 'api/user/patient/surgeryRequest';
        $(this).fileupload({
            formdata: {'_csrf': token},
            dataType: 'json',
            url: url,
            dropZone: $(this),
            autoUpload:false,
            add: function (e, data) {
                var that = this;
                if(!$('form#surgeryRequest')[0].checkValidity()){
                    $(this).find(':submit').click();
                    $(".align-link").addClass('hide');
                }else{
                    $("#submit_btn").addClass('hide').prop('type','button');
                    $("#appendbtn").find("#fileUploadDynamic").remove();
                    $("#preferredDate").val($("#date_timepicker_start").val()+"-"+$("#date_timepicker_end").val());
                    data.context = $('<button/>').text('SUBMIT').attr({
                        class :'start saveb editbasicinfo-vitals submit_btn',
                        id : 'fileUploadDynamic',
                        type:'submit'})
                        .appendTo("#appendbtn").click(function () {
                            data.submit();
                        });
                    $(".align-link").removeClass('hide');
                    $.blueimp.fileupload.prototype.options.add.call(that, e, data);
                }
            },
            done: function (e, data) {
                var res = data['result'];
                var files = res['files'];
                var status = files['statusCode'];
                var ord = files['orderId'];
                if (status == 200) {
                    $("#orderId").val(ord);
                    document.getElementById("surgeryRequest").reset();
                    $("tbody.files").empty();
                    $('#confirmMessage').empty().prepend('<div class="notification alert-success spacer-t10">' +
                        '<p>' + files['message'] + '</p><span class="close-btndemo"></span></div>').children(':first').delay(15000).fadeOut(100, function () {
                        $('#confirmMessage').empty();
                    });

                } else {
                   /* $("#errorMessage").prepend(files['message']);*/
                    $('#errorMessage').empty().prepend('<div class="notification alert-info spacer-t10">' +
                        '<p>'+files['message']+'</p><span class="close-btndemo"></span></div>').children(':first').delay(15000).fadeOut(100, function () {
                        $('#errorMessage').empty();
                    });
                }
            },
            fail: function (e, data) {
               /* $('#errorMessage').empty().prepend('<div class="notification alert-info spacer-t10">' +
                    '<p>Your upload is Unsuccessful. Try Again !!!</p><span class="close-btndemo"></span></div>').children(':first').delay(2000).fadeOut(100, function () {
                    $('#errorMessage').empty();
                });*/
            }
        })
    });

    // Enable iframe cross-domain access via redirect option:
    $('#surgeryRequest').fileupload(
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
        $('#surgeryRequest').addClass('fileupload-processing');
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


});
