
<title>datalife</title>
<script src="/resources/css/cssfiles/bootstrap.min.css"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/sketch.min.css">



<div class="container">

    <div class="tools">
        <a href="#tools_sketch" data-tool="marker">Marker</a>
        <a href="#tools_sketch" data-tool="eraser">Eraser</a>
    </div>
    <br>

    <ul id="flexiselDemo3">
        <li>
            <canvas id="tools_sketch" width="600" height="588" class="specialityImageBG" style="background: url(http://localhost:8081/resources/images/speciality/heart.png) 50% 50% no-repeat;"></canvas>
        </li>

    </ul>

</div>
<script src="/resources/jquery/jquery-1.11.2.min.js"></script>

<script src="/resources/jsplugins/bootstrap.min.js"></script>

<script type="text/javascript" src="/resources/jsplugins/sketch.js"></script>
<script type="text/javascript">
    $(function (e) {
        $.each(['#f00', '#ff0', '#0f0', '#0ff', '#00f', '#f0f', '#000', '#fff'], function () {
            $('#colors_demo .tools').append("<a href='#colors_sketch' data-color='" + this + "' style='width: 10px; background: " + this + ";'></a> ");

        });

        $.each([3, 5, 10, 15], function () {
            $('#colors_demo .tools').append("<a href='#colors_sketch' data-size='" + this + "' style='width: 10px; background: #ccc'>" + this + "</a> ");
        });
        $('#tools_sketch').sketch({defaultColor: "#ff0000"});

    });


</script>





