$(document).ready(function () {

        fetchCountry('providerCountry');

        fetchRecordSpecialities('speciality');

        jQuery('#date_timepicker_start').datetimepicker({
            format: 'd/m/Y',
            minDate:$("#curDate").val(),
            timepicker:false,
            onSelectDate:function(){
                $("#date_timepicker_end").removeClass('hide');
            }
        });

        jQuery('#date_timepicker_end').datetimepicker({
            format: 'd/m/Y',
            onShow:function( ct ){
                this.setOptions({
                    minDate:jQuery('#date_timepicker_start').val()?jQuery('#date_timepicker_start').val():false
                })
            },
            timepicker:false
        });

        var locationName = new Bloodhound({
            datumTokenizer: function (d) {
                return d.name;
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            remote: {
                url: 'common/%QUERY',
                replace: function(url, query) {
                    var countryId = $("#providerCountry").val();
                    if(query == ''){
                        query = "getHospitalCities/"+countryId;
                        return url.replace("%QUERY",query);
                    }else{
                        return url.replace("%QUERY","getHospitalCitiesLike/"+countryId+"/"+query);
                    }
                },
                filter: function (response) {
                    return response;
                }
            },
            limit: 10
        });
        locationName.initialize();
        var quicksearchInput = $("#city");
        quicksearchInput.typeahead({
                hint:false,
                highlight: true,
                minLength: 0
            },
            {
                name:'name',
                displayKey: 'name',
                source: locationName.ttAdapter(),
                templates: {
                    empty: [].join('\n'),
                    suggestion: Handlebars.compile('<div><strong>{{name}}</strong></div>')
                }
            });

        quicksearchInput.on("typeahead:opened", function () {
            var ev = $.Event("keydown");
            ev.keyCode = ev.which = 40;
            $(this).trigger(ev);
            return true
        });


    $('#ifAnyInsurance').on('change', function () {
        if (this.checked) {
            $("#showInsField").removeClass('hide').val('');
            $('#ifAnyInsurance').val(true);
        } else {
            $("#showInsField").addClass('hide').val('');
        }
    });

    $('form#surgeryRequest').submit(function(e){
        e.preventDefault();
        var flag = true;
        if($(this).find('textarea').hasClass('textareavalidate')){
            flag = false;
            $('#message').empty().prepend('<div class="notification alert-error spacer-t10">' +
                '<p>Please correct the fields highlighted in red</p><span class="close-btndemo"></span></div>').children(':first').delay(7000).fadeOut(100, function () {
                $('#message').empty();
            });
        }

        if($('#surgeryRequest')[0].checkValidity() && flag) {
            var url = "api/user/patient/saveServiceRequests";

            alert(JSON.stringify(serviceRequestFormToJSON('#surgeryRequest')));
            $.ajax({
                url: url,
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(serviceRequestFormToJSON('#surgeryRequest')),
                contentType: 'application/json',
                success: function (data) {
                    var statusCode = data['statusCode'];
                    var message = data['message'];
                    switch (statusCode) {
                        case 200:
                            document.getElementById("surgeryRequest").reset();
                            $("#showInsField").addClass('hide');
                            $('#message').empty().prepend('<div class="notification alert-success spacer-t10">' +
                                '<p>' + message + '</p><span class="close-btndemo"></span></div>').children(':first').delay(7000).fadeOut(100, function () {
                                $('#message').empty();
                            });
                            break;
                        case 100:
                            $('#message').empty().prepend('<div class="notification alert-error spacer-t10">' +
                                '<p>' + message + '</p><span class="close-btndemo"></span></div>').children(':first').delay(7000).fadeOut(100, function () {
                                $('#message').empty();
                            });
                            break;
                    }

                },
                error: function (data) {
                    window.location = 'invalidSession';
                }
            });
        }
    });

});

/*function getCitiesByCountry(country, cityId) {

    var url = "common/getListedCities/" + country+"/"+$("#role").val();
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {

            $("#" + cityId).empty().append("<option value='' disabled selected>Select City</option>");
            $.each(data, function (idx, obj) {
                $("#" + cityId).append($("<option></option>")
                    .attr("value", obj)
                    .text(obj));
            });

        },
        error: function (data) {

        }
    });
}*/

function getProcedureTypeBySpeciality(specialityId, appendToId) {
    var url = "common/getProcedureTypes/" + specialityId;
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {

            var procedureType = data['getProcedureType'];
            if(procedureType && procedureType.length > 0){
                $("#" + appendToId).empty().append("<option value='' disabled selected>Select Procedure</option>");
                $.each(procedureType, function (idx, obj) {
                    $("#" + appendToId).append($("<option></option>")
                        .attr("value", obj['id'])
                        .text(obj['procedureName']));
                });
                $("#" + appendToId).removeClass('hide').attr({
                    required:true,
                    name:"surgeryRequests.typeOfProcedure"
                });
                $("#" + appendToId+"Text").addClass('hide').removeAttr('required name');
            }else{
                $("#" + appendToId+"Text").removeClass('hide').attr({
                    required:true,
                    name:"surgeryRequests.typeOfProcedureText"
                });
                $("#" + appendToId).addClass('hide').removeAttr('required name');
            }
        },
        error: function (data) {

        }
    });
}

