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

    // Initialize the jQuery File Upload widget:
    $('#fileupload').each(function () {
        var url = 'api/user/records/upload';
        $(this).fileupload({
            formdata: {'_csrf': token},
            dataType: 'json',
            url: url,
            dropZone: $(this)
        });
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

    /*if (window.location.hostname === 'blueimp.github.io') {
        // Demo settings:
        $('#fileupload').fileupload('option', {
            url: '//jquery-file-upload.appspot.com/',
            // Enable image resizing, except for Android and Opera,
            // which actually support image resizing, but fail to
            // send Blob objects via XHR requests:
            disableImageResize: /Android(?!.*Chrome)|Opera/
                .test(window.navigator.userAgent),
            maxFileSize: 209715200,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i
        });
        // Upload server status check for browsers with CORS support:
        if ($.support.cors) {
            $.ajax({
                url: '//jquery-file-upload.appspot.com/',
                type: 'HEAD'
            }).fail(function () {
                $('<div class="alert alert-danger"/>')
                    .text('Upload server currently unavailable - ' +new Date()).appendTo('#fileupload');
            });
        }
    } else {*/
        // Load existing files:
        $('#fileupload').addClass('fileupload-processing');
        $.ajax({
            // Uncomment the following to send cross-domain cookies:
            //xhrFields: {withCredentials: true},
            url: $('#fileupload').fileupload('option', 'url'),
            dataType: 'json',
            context: $('#fileupload')[0]
        }).always(function () {
            $(this).removeClass('fileupload-processing');
        }).done(function (result) {
            $(this).fileupload('option', 'done')
                .call(this, $.Event('done'), {result: result});
        });
  /*  }*/

});
$(function(){
    'use strict';
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    // Initialize the jQuery File Upload widget:
    $('#uploadLabReport').each(function () {
        var url = 'api/lab/upload';

        $(this).fileupload({
            formdata: {'_csrf': token},
            dataType: 'json',
            url: url,
            dropZone: $(this),
            done: function (e, data) {

                var res = data['result'];
                var files = res['files'];
                var search = $("#lastsearchInput").val();
                if(search) {
                    $("#searchInput").val(search);
                    $("#mobilePhone").val(search);
                    $("tbody.files").empty().append("<tr><td><p class='name'>"+files[0]['error']+"</p><strong class='error text-danger'></strong></td>").delay(6000).fadeOut(100, function () {
                        $("tbody.files").empty();
                    });
                }else{
                    $("#uploaded").val(true);
                    $("tbody.files").empty().append("<tr><td><p class='name'>"+files[0]['error']+"</p><strong class='error text-danger'></strong></td>").delay(6000).fadeOut(100, function () {
                        $("tbody.files").empty();
                    });
                }
            },
            fail:function (e, data) {
                $('#errorMessage').empty().prepend(data['message']);
            }
        });
    });

    // Enable iframe cross-domain access via redirect option:
    $('#uploadLabReport').fileupload(
        'option',
        'redirect',
        window.location.href.replace(
            /\/[^\/]*$/,
            '/cors/result.html?%s'
        )
    );

    /*if (window.location.hostname === 'blueimp.github.io') {
        // Demo settings:
        $('#fileupload').fileupload('option', {
            url: '//jquery-file-upload.appspot.com/',
            // Enable image resizing, except for Android and Opera,
            // which actually support image resizing, but fail to
            // send Blob objects via XHR requests:
            disableImageResize: /Android(?!.*Chrome)|Opera/
                .test(window.navigator.userAgent),
            maxFileSize: 209715200,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i
        });
        // Upload server status check for browsers with CORS support:
        if ($.support.cors) {
            $.ajax({
                url: '//jquery-file-upload.appspot.com/',
                type: 'HEAD'
            }).fail(function () {
                $('<div class="alert alert-danger"/>')
                    .text('Upload server currently unavailable - ' +new Date()).appendTo('#fileupload');
            });
        }
    } else {*/
        // Load existing files:
        $('#uploadLabReport').addClass('fileupload-processing');
        $.ajax({
            // Uncomment the following to send cross-domain cookies:
            //xhrFields: {withCredentials: true},
            url: $('#uploadLabReport').fileupload('option', 'url'),
            dataType: 'json',
            context: $('#uploadLabReport')[0]
        }).always(function () {
            $(this).removeClass('fileupload-processing');
        }).done(function (result) {
            $(this).fileupload('option', 'done')
                .call(this, $.Event('done'), {
                    result: result
                });
        });
    /*}*/
});