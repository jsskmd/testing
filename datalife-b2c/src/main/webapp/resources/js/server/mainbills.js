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
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    // Initialize the jQuery File Upload widget:
    $('.billfileupload').each(function () {
        var url='api/user/records/saveBills';
        $(this).fileupload({
            formdata:{'_csrf': token},
            dataType: 'json',
            url: url,
            autoUpload:true,
            dropZone: $(this),
            done: function (e, data) {
                $('#successMessages').empty().prepend('<div class="notification alert-success spacer-t10">' +
                    '<p>' +data['message'] + '</p><span class="close-btndemo"></span></div>').children(':first').delay(1500).fadeOut(100, function () {
                    $('#successMessages').empty();
                });
                $("#bills").trigger("click");
            },
            fail:function (e, data) {
                $('#perrorMessage').empty().prepend(data['message']);
            }

        });
    });

    // Enable iframe cross-domain access via redirect option:
    $('.billfileupload').fileupload(
        'option',
        'redirect',
        window.location.href.replace(
            /\/[^\/]*$/,
            '/cors/result.html?%s'
        )
    );

    if (window.location.hostname === 'blueimp.github.io') {
        // Demo settings:
        $('#fileupload').fileupload('option', {
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
                    .appendTo('#fileupload');
            });
        }
    } else {
        // Load existing files:
        $('.billfileupload').addClass('fileupload-processing');
        $.ajax({
            // Uncomment the following to send cross-domain cookies:
            //xhrFields: {withCredentials: true},
            url: $('.billfileupload').fileupload('option', 'url'),
            dataType: 'json',
            context: $('.billfileupload')[0]
        }).always(function () {
            $(this).removeClass('fileupload-processing');
        }).done(function (result) {
            $(this).fileupload('option', 'done')
                .call(this, $.Event('done'), {result: result});
        });
    }

});