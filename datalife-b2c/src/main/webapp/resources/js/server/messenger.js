$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });


    $('#minim_chat_window').click(function () {
        $('#collapse').slideDown();
        $("#show_names,#show_messages").addClass("hide");
        $('#minim_chat_window').addClass('panel-collapsed').removeClass('glyphicon-minus').addClass('glyphicon-plus');

    });

    $(document).off().on('click', 'ul#visitedDoctorsList li', function (e) {
        e.preventDefault();
        var id = $(this).attr("id");
        var userId = $("#userId").text();
        $("#patientId").val(userId);
        $("#doctorId").val(id);
        var url = "api/user/getMessagesFromPatient";
        var jsondata = {"patientId": userId, "doctorId": id};
        $("#msg_fullname").text($(this).text());
        $("#msg_userId").val(id);
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsondata),
            contentType: 'application/json',
            success: function (data) {
                var status = data['statusCode'];
                var messages = data['messages'];
                var imgURL = data['imageURL'];
                var main = "";
                for (var i = 0; i < messages.length; i++) {
                    var sent = messages[i]['sent'];
                    if (sent) {
                        main += '<div class="row msg_container base_sent"><div class="col-md-10 col-xs-10">' +
                            '<div class="messages msg_sent"><p>' + messages[i]['message'] + '</p><time datetime="2009-11-13T20:00">' + getDateFromSeconds(messages[i]['date']) + '</time>' +
                            '</div></div><div class="col-md-4 col-xs-2 avatar">' +
                            '<img src="' + imgURL + '' + userId + '" class=" img-responsive ">' +
                            '</div></div>';

                    } else {
                        main += ' <div class="row msg_container base_receive"><div class="col-md-4 col-xs-2 avatar">' +
                            '<img src="' + imgURL + '' + id + '" class=" img-responsive ">' +
                            '</div><div class="col-md-10 col-xs-10"><div class="messages msg_receive">' +
                            '<p>' + messages[i]['message'] + '</p><time datetime="2009-11-13T20:00">' + getDateFromSeconds(messages[i]['date']) + '</time></div></div></div>';

                    }

                }
                if (main != null && main != '' && main != 'undefined') {
                    $("#view_messenger").html(main);
                } else {
                    $("#view_messenger").html('Messages not found !');
                }
                $("#show_messages").removeClass("hide");
                $("#show_names").addClass("hide");
                updateScroll();
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });

    });

    $(".cancel-messenger").click(function () {
        $("#messenger").removeClass("dailog-show");
        $("#visitedDoctorsList").empty();
        $("#view_messenger").getNiceScroll().remove();
    });

    $("#msg_back").click(function () {
        $("#show_names").removeClass("hide");
        $("#show_messages").addClass("hide");
        $("#msg_message").empty();
    });

    $("#message").click(function () {
        $("#msg_message").empty();
    });

    /*
     $("#view_messenger").niceScroll();
     */
    $("#sendMessageForm").on('submit', function (e) {
        e.preventDefault();
        var message = $("#message").val();
        if (message) {
            var url = "api/user/sendMessage";
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(LoginFormToJSON('#sendMessageForm')),
                contentType: 'application/json',
                success: function (data) {
                    if (data['statusCode'] == 200) {
                        $("#msg_message").text(data['message']);
                        var id = $("#msg_userId").val();
                        var url = "api/user/getMessagesAfterSend";
                        var userId = $("#userId").text();
                        var jsondata = {"patientId": userId, "doctorId": id};
                        $.ajax({
                            url: url,
                            type: 'POST',
                            dataType: 'json',
                            data: JSON.stringify(jsondata),
                            contentType: 'application/json',

                            success: function (data) {
                                var status = data['statusCode'];
                                var messages = data['messages'];
                                var imgURL = data['imageURL'];
                                var main = "";
                                for (var i = 0; i < messages.length; i++) {
                                    var sent = messages[i]['sent'];
                                    if (sent) {
                                        main += '<div class="row msg_container base_sent"><div class="col-md-10 col-xs-10">' +
                                            '<div class="messages msg_sent"><p>' + messages[i]['message'] + '</p><time datetime="2009-11-13T20:00">' + getDateFromSeconds(messages[i]['date']) + '</time>' +
                                            '</div></div><div class="col-md-4 col-xs-2 avatar">' +
                                            '<img src="' + imgURL + '' + userId + '" class=" img-responsive ">' +
                                            '</div></div>';
                                    } else {
                                        main += ' <div class="row msg_container base_receive"><div class="col-md-4 col-xs-2 avatar">' +
                                            '<img src="' + imgURL + '' + id + '" class=" img-responsive ">' +
                                            '</div><div class="col-md-10 col-xs-10"><div class="messages msg_receive">' +
                                            '<p>' + messages[i]['message'] + '</p><time datetime="2009-11-13T20:00">' + getDateFromSeconds(messages[i]['date']) + '</time></div></div></div>';
                                    }
                                }
                                if (main != null && main != '' && main != 'undefined') {
                                    $("#view_messenger").html(main);
                                } else {
                                    $("#view_messenger").html('Messages not found !');
                                }
                                updateScroll();
                            },
                            error: function (data) {
                            }
                        });
                    }
                    else {
                        $("#msg_message").text(data['message']);
                    }
                    $("#message").val("");
                },
                error: function (data) {
                }
            });
        } else {
            $('#message').addClass('message').delay(5000).fadeIn(100, function () {
                $('#message').removeClass('message');
            });
        }
    });

    $("#messenger_but").off().click(function (e) {
        e.preventDefault();
        $('#minim_chat_window').removeClass('panel-collapsed').removeClass('glyphicon-plus').addClass('glyphicon-minus');

        $("#show_names").removeClass("hide");
        $("#show_messages").addClass("hide");
        $("#visitedDoctorsList").empty();
        /*   $("#messenger").addClass("dailog-show");*/
        var url = "api/user/getVisitedDoctors";
        var userId = $("#userId").text();
        var jsondata = {"userId": userId};
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsondata),
            contentType: 'application/json',

            success: function (data) {

                var status = data['statusCode'];
                if (status == 400) {

                } else if (status == 200) {
                    var li = "";
                    $.each(data['visitedDoctors'], function (k, v) {
                        var value = v.split(",");
                        if (value[1] == 0) {
                            li += "<a><li id=" + k + ">" + value[0] + "</li></a>";
                        } else {
                            li += "<div><span style='float:left'><li id=" + k + ">" + value[0] + "</li></span><span class='messanger-details' >"+value[1]+"</span></div>";
                        }

                    });
                    if (li != null && li != '' && li != 'undefined') {
                        $("#visitedDoctorsList").append(li);
                    }else{
                        $("#visitedDoctorsList").append("<div>No Patient Visited</div>");
                    }

                    if (data['totalCount'] == 0) {
                        $("#messages_count").text('');
                    } else if (data['totalCount'] == 1) {
                        $("#messages_count").text(data['totalCount'] + "new message");
                    } else {
                        $("#messages_count").text(data['totalCount'] + " new messages");
                    }
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

    $("#refresh").off().click(function () {
        $(this).html('<img src="resources/images/small_loading.gif" id="load_btn" class="small_loading" onclick="return false;">');
        var id = $("#msg_userId").val();
        var url = "api/user/getMessages";
        var userId = $("#userId").text();
        var jsondata = {"patientId": userId, "doctorId": id};
        /*var saveMessage = '';*/
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(jsondata),
            contentType: 'application/json',

            success: function (data) {
                var status = data['statusCode'];
                if (status == 400) {

                } else if (status == 200) {
                    $("#refresh").empty().html('<img src="resources/images/refresh.png" style="height:20px;">');

                    var messages = data['messages'];
                    var main = "";
                    for (var i = 0; i < messages.length; i++) {
                        var sent = messages[i]['sent'];
                        if (sent) {
                            main += '<div class="row msg_container base_sent"><div class="col-md-10 col-xs-10">' +
                                '<div class="messages msg_sent"><p>' + messages[i]['message'] + '</p><time datetime="2009-11-13T20:00">' + getDateFromSeconds(messages[i]['date']) + '</time>' +
                                '</div></div><div class="col-md-4 col-xs-2 avatar">' +
                                '<img src="resources/images/1.png" class=" img-responsive ">' +
                                '</div></div>';


                        } else {
                            main += ' <div class="row msg_container base_receive"><div class="col-md-4 col-xs-2 avatar">' +
                                '<img src="resources/images/1.png" class=" img-responsive ">' +
                                '</div><div class="col-md-10 col-xs-10"><div class="messages msg_receive">' +
                                '<p>' + messages[i]['message'] + '</p><time datetime="2009-11-13T20:00">' + getDateFromSeconds(messages[i]['date']) + '</time></div></div></div>';

                        }
                    }
                    if (main != null && main != '' && main != 'undefined') {
                        $("#view_messenger").html(main);
                    } else {
                        $("#view_messenger").html('Messages not found !');
                    }
                    updateScroll();
                }
            },
            error: function (data) {
                window.location = 'invalidSession';
            }
        });
    });

});

function updateScroll() {
    var element = document.getElementById("view_messenger");
    element.scrollTop = element.scrollHeight;
}
