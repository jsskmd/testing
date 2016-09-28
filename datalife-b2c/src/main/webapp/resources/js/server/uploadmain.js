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
    $('.profileImageUpload').each(function () {
        var url = 'api/user/patient/profileImageUpload';
        $(this).fileupload({
            add: function (e, data) {
                var uploadErrors = [];
                var acceptFileTypes = /^image\/(gif|jpe?g|png)$/i;
                if (data.files[0]['size'] && !acceptFileTypes.test(data.files[0]['type'])) {
                    uploadErrors.push('Not an accepted file type');
                }
                if (data.files[0]['size'] && data.files[0]['size'] > 5000000) {
                    uploadErrors.push('Filesize is too big,max size 5MB');
                }
                if (uploadErrors.length > 0) {
                    $.each(uploadErrors, function (index, value) {
                        $('#perrorMessage').empty().prepend('<div id="errorMessage">' + value + '</div>').children(':first').delay(5000).fadeOut(100);
                    });
                } else {
                    data.submit();
                    $('#perrorMessage').empty().prepend('<div id="errorMessage">SUCCESS</div>').children(':first').delay(10000);
                }
            },
            formdata: {'_csrf': token},
            dataType: 'json',
            url: url,
            autoUpload: true,
            async: true,
            dropZone: $(this),
            done: function (e, data) {
                var userId = $("#userId").text();
                var myContextPath = $("#myContextPath").val();
                $("#getProfilePtientImage,#sampleimg").attr("src", "api/user/patient/userthumbnail/" + userId + "?" + new Date().getTime());
                $("#validImage").val('true');
                $("tbody.files").empty();
                $("#imageuploadpatient").removeClass('dailog-show');
            },
            fail: function (e, data) {
                $('#perrorMessage').empty().prepend('<div id="errorMessage">Your profile image is not uploaded. Try Again !!!</div>').children(':first').delay(5000).fadeOut(100);
            }
        });
    });

    // Enable iframe cross-domain access via redirect option:
    $('.profileImageUpload').fileupload(
        'option',
        'redirect',

        window.location.href.replace(
            /\/[^\/]*$/,
            '/cors/result.html?%s'
        )
    );

    // Initialize the jQuery File Upload widget:
    $('.IdentityImageUpload').each(function () {

        var url = 'api/user/patient/identityImageUpload';
        $(this).fileupload({
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
                    data.submit();
                }
            },
            formdata: {'_csrf': token},
            dataType: 'json',
            url: url,
            autoUpload: true,
            dropZone: $(this),
            done: function (e, data) {
                var res = data['result'];
                var imageUrl = res['imageUrl'];
                $("#sampleIdentityimg,#viewIdentityimg").attr("src", imageUrl+"?" + new Date().getTime());
                $("#idImage").val('true');
                $(".progress-extended,.files").html("");
            },
            fail: function (e, data) {
                $('#errorMessage').empty().prepend('Enter and Save mandatory personal details first, before uploading ID proof');

            }
        });
    });

    // Enable iframe cross-domain access via redirect option:
    $('.IdentityImageUpload').fileupload(
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
     .text('Upload server currently unavailable - ' +
     new Date())
     .appendTo('#fileupload');
     });
     }
    } else {
        // Load existing files:
        $('.profileImageUpload').addClass('fileupload-processing');
        $.ajax({
            // Uncomment the following to send cross-domain cookies:
            //xhrFields: {withCredentials: true},
            url: $('.profileImageUpload').fileupload('option', 'url'),
            dataType: 'json',
            context: $('.profileImageUpload')[0]
        }).always(function () {
            $(this).removeClass('fileupload-processing');
        }).done(function (result) {
            $(this).fileupload('option', 'done')
                .call(this, $.Event('done'), {result: result});
        });
    }
});
