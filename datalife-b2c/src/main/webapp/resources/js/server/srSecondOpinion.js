
$(document).ready(function() {

    fetchCountry('providerCountry');
    fetchRecordSpecialities('speciality');
    fetchRecordSpecialities('recordSpecialities');

    var locationName = new Bloodhound({
        datumTokenizer: function (d) {
            return d.name;
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: 'common/%QUERY',
            replace: function (url, query) {
                var countryId = $("#providerCountry").val();
                if (query == '') {
                    query = "getHospitalCities/" + countryId;
                    return url.replace("%QUERY", query);
                } else {
                    return url.replace("%QUERY", "getHospitalCitiesLike/" + countryId + "/" + query);
                }
            },
            filter: function (response) {
                return response;
            }
        },
        limit: 10
    });
    locationName.initialize();
    var quicksearchInput = $("#providerCity");
    quicksearchInput.typeahead({
            hint: false,
            highlight: true,
            minLength: 0
        },
        {
            name: 'name',
            displayKey: 'name',
            source: locationName.ttAdapter(),
            templates: {
                empty: [
                ].join('\n'),
                suggestion: Handlebars.compile('<div><strong>{{name}}</strong></div>')
            }
        });

    quicksearchInput.on("typeahead:opened", function () {
        var ev = $.Event("keydown");
        ev.keyCode = ev.which = 40;
        $(this).trigger(ev);
        return true
    });

});


$(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $("form#secondOpinionForm").submit(function (e) {
        e.preventDefault();
        var flag = true;

        if($(this).find('textarea').hasClass('textareavalidate')){
            flag = false;
            $('#message').empty().prepend('<div class="notification alert-error spacer-t10">' +
                '<p>Please correct the fields highlighted in red</p><span class="close-btndemo"></span></div>').children(':first').delay(7000).fadeOut(100, function () {
                $('#message').empty();
            });
        }
        if(flag) {
            var url = "api/user/patient/saveServiceRequests";
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(serviceRequestFormToJSON('#secondOpinionForm')),
                contentType: 'application/json',
                success: function (data) {
                    var saveMessage = data['message'];
                    var saveStatus = data['statusCode'];
                    if (saveStatus == '200') {
                        document.getElementById("secondOpinionForm").reset();

                        $('#message').empty().prepend('<div class="notification alert-success spacer-t10">' +
                            '<p>' + saveMessage + '</p><span class="close-btndemo"></span></div>').children(':first').delay(7000).fadeOut(100, function () {
                            $('#message').empty();
                        });
                    } else {
                        $('#message').empty().prepend('<div class="notification alert-error spacer-t10">' +
                            '<p>' + saveMessage + '</p><span class="close-btndemo"></span></div>').children(':first').delay(7000).fadeOut(100, function () {
                            $('#message').empty();
                        });
                    }
                },
                error: function (data) {
                    window.location = 'invalidSession';
                }
            });
        }
    });

});


