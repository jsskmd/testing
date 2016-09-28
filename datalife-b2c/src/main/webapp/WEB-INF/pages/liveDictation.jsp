
<div>
<link href="resources/css/livedictation.css" rel="stylesheet" type="text/css"  />
<script type="text/javascript" src="resources/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="resources/js/liveDictation/audiodisplay.js"></script>
<script type="text/javascript" src="resources/js/liveDictation/recorder.js"></script>
	<style>
	canvas { 
		border-radius: 7px;
		display: inline-block;
		width: 92%;
		height: 09vh;
		/box-shadow: 0px 0px 20px black;
	}
	#controls {
		display: flex;
		flex-direction: row;
		align: center;
		justify-content: space-around;
		height: 20%;
		width: 100%;
	}
	#record { height: 10vh; 
	align-items: center;}
	#record.recording { 
		background: red;
		background: -webkit-radial-gradient(center, ellipse cover, #ff0000 0%,lightgrey 75%,lightgrey 100%,#7db9e8 100%); 
		background: -moz-radial-gradient(center, ellipse cover, #ff0000 0%,lightgrey 75%,lightgrey 100%,#7db9e8 100%); 
		background: radial-gradient(center, ellipse cover, #ff0000 0%,lightgrey 75%,lightgrey 100%,#7db9e8 100%); 
	}
	#save, #save img { height: 10vh; }
	#save { opacity: 0.25;}
	#save[download] { opacity: 1;}
	#viz {
		height: 10%;
		width: 30%;
		display: flex;
		flex-direction: column;
		justify-content: space-around;
		align-items: center;
	}

	@media (orientation: landscape) {
		body { flex-direction: row;}
		#controls { flex-direction: column; height: 100%; width: 100%;}
		#viz { height: 100%; width: 90%;}
	}

	</style>
<script>
    var fname;
    $(document).ready(function(){



        var docId =  $("#userId").text();
         var clnId = $("#select_clinic").val();
         var patId = $("#patient_id").text();
        var username = $("#userName").text();

        document.getElementById('pause3').style.pointerEvents = 'none';
        document.getElementById('stop').style.pointerEvents = 'none';
        var flag = false;
        $("#rec").on('click',function(){
            if(flag){
                flag = false;
                toggleRecording(this);
                document.getElementById('rec').style.pointerEvents = 'auto';
                document.getElementById('pause3').style.pointerEvents = 'none';
                document.getElementById('stop').style.pointerEvents = 'none';
            }else{
                toggleRecording(this);
                document.getElementById('rec').style.pointerEvents = 'none';
                document.getElementById('pause3').style.pointerEvents = 'auto';
                document.getElementById('stop').style.pointerEvents = 'auto';
            }

        });

        $("#pause3").on('click',function(){
            togglePause();
            document.getElementById('rec').style.pointerEvents = 'none';
        });

        $("#stop").on('click',function(){
            flag = true;
            $("#rec").trigger('click');
        });


    fname = clnId+'_'+docId+'_'+patId+'_'+username;
     /*initAudio();*/
    $(window).load(initAudio());
     /*window.addEventListener('load',initAudio);*/
     window.AudioContext = window.AudioContext || window.webkitAudioContext;
    });
    //var encoder = new OggVorbisEncoder(sampleRate, numChannels, options.ogg.quality);
    var audioContext = new AudioContext();
    var audioInput = null,
     realAudioInput = null,
     inputPoint = null,
     audioRecorder = null;
    var rafID = null;
    var analyserContext = null;
    var canvasWidth, canvasHeight;
    var recIndex = 0;
    var pauseToggle=true;

    function cancelAnalyserUpdates() {
        window.cancelAnimationFrame( rafID );
        rafID = null;
    }

    function updateAnalysers(time) {
        if (!analyserContext) {
            var canvas = document.getElementById("analyser");
            canvasWidth = canvas.width;
            canvasHeight = canvas.height;
            analyserContext = canvas.getContext('2d');
        }

        // analyzer draw code here
        {
            var SPACING = 3;
            var BAR_WIDTH = 1;
            var numBars = Math.round(canvasWidth / SPACING);
            var freqByteData = new Uint8Array(analyserNode.frequencyBinCount);

            analyserNode.getByteFrequencyData(freqByteData);

            analyserContext.clearRect(0, 0, canvasWidth, canvasHeight);
            analyserContext.fillStyle = '#F6D565';
            analyserContext.lineCap = 'round';
            var multiplier = analyserNode.frequencyBinCount / numBars;

            // Draw rectangle for each frequency bin.
            for (var i = 0; i < numBars; ++i) {
                var magnitude = 0;
                var offset = Math.floor( i * multiplier );
                // gotta sum/average the block, or we miss narrow-bandwidth spikes
                for (var j = 0; j< multiplier; j++)
                    magnitude += freqByteData[offset + j];
                magnitude = magnitude / multiplier;
                var magnitude2 = freqByteData[i * multiplier];
                analyserContext.fillStyle = "hsl( " + Math.round((i*360)/numBars) + ", 100%, 50%)";
                analyserContext.fillRect(i * SPACING, canvasHeight, BAR_WIDTH, -magnitude);
            }
        }

        rafID = window.requestAnimationFrame(updateAnalysers);
    }

    function togglePause(){

        if(pauseToggle){
            pauseToggle=false;
            document.getElementById("pause3").className = document.getElementById("pause3").className.replace( /(?:^|\s)link_list link2(?!\S)/g , 'link_list link4' );
            document.getElementById("pausebtn").textContent="RESUME";
            document.getElementById("analyser").style.visibility = "hidden";
            audioRecorder.pause();
        }
        else
        {
            pauseToggle=true;
            document.getElementById("pause3").className = document.getElementById("pause3").className.replace( /(?:^|\s)link_list link4(?!\S)/g , 'link_list link2' );
            document.getElementById("pausebtn").textContent="PAUSE";
            document.getElementById("analyser").style.visibility = "visible";
            audioRecorder.resume();
        }
    }

    function toggleStop() {
        document.getElementById("rec").click();
    }

    function toggleRecording( e ) {
        //alert("HI");
        if (e.classList.contains("recording")) {
            audioRecorder.stop();
            e.classList.remove("recording");

        } else {

            if (!audioRecorder)
                return;
            e.classList.add("recording");
            document.getElementById("analyser").style.visibility = 'visible';
            audioRecorder = new Recorder(inputPoint);
            audioRecorder.record();
        }
    }

    function gotStream(stream) {
        inputPoint = audioContext.createGain();

        // Create an AudioNode from the stream.
        realAudioInput = audioContext.createMediaStreamSource(stream);
        audioInput = realAudioInput;
        audioInput.connect(inputPoint);

//	    audioInput = convertToMono( input );

        analyserNode = audioContext.createAnalyser();
        analyserNode.fftSize = 4096;
        inputPoint.connect( analyserNode );

        audioRecorder = new Recorder( inputPoint );
        //realAudioInput.connect(context.destination);
        zeroGain = audioContext.createGain();
        zeroGain.gain.value = 0.0;
        inputPoint.connect( zeroGain );
        zeroGain.connect( audioContext.destination );
        updateAnalysers();
    }

    function initAudio() {
        if (!navigator.getUserMedia)
            navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia;
        if (!navigator.cancelAnimationFrame)
            navigator.cancelAnimationFrame = navigator.webkitCancelAnimationFrame || navigator.mozCancelAnimationFrame;
        if (!navigator.requestAnimationFrame)
            navigator.requestAnimationFrame = navigator.webkitRequestAnimationFrame || navigator.mozRequestAnimationFrame;

        navigator.getUserMedia(
                {
                    "audio": {
                        "mandatory": {
                            "googEchoCancellation": "false",
                            "googAutoGainControl": "false",
                            "googNoiseSuppression": "false",
                            "googHighpassFilter": "false"
                        },
                        "optional": []
                    }
                }, gotStream, function(e) {
                    alert('Error getting audio');
                    console.log(e);
                });
    }

</script>

<div id="append">
<div align="center" id="controls" class="hide">
    <label  style="visibility:hidden;align:center color: #0000FF;font-weight: bold;display: block;width: 100%;float: left" id="inprogress" >Dictation Upload is in progress Please wait.....</label>
    <img align="middle" id="inprogressimg" src="resources/images/progressbar2.gif" style="visibility:hidden">

</div>
<div class="login_page">
    <div class="get_login_details">
        <div class="login_hedaings">
            <h4 class="dict_facility">DataLife Dictation System</h4>
        </div>
        <div>
            <canvas id="analyser" style="visibility:hidden"></canvas></div>
        <div class="dict_facility">
            <div class="dict_link">
                <ul class="link_page">
                    <li>
                        <a class="link_pages">
                            <div id="rec" class="link_list link1"></div>
                            <span class="link_span">RECORD</span></a>
                    </li>
                    <li >
                        <a  class="link_pages">
                            <div id="pause3" class="link_list link2"></div>
                            <span class="link_span pause" id="pausebtn">PAUSE</span></a>
                    </li>

                    <li>
                        <a class="link_pages">
                            <div id="stop" class="link_list link3"></div>
                         <span class="link_span stop">STOP</span></a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="note-details">
<%--
        <h5 class="note-heading">Note:</h5>
--%>
        <p class="note1"><strong>Note:</strong>  When <img src="resources/images/pause.png" alt="pause"/> Stop button is pressed, dictation is saved in local system and uploaded to datalife automatically.</p>
    </div>
</div>
</div>
</div>



