/* (c) 2015 Felipe Astroza A.
 * Under BSD License
 * Based on https://github.com/rokgregoric/html5record/blob/master/recorder.js
 */
var worker = new Worker('resources/js/liveDictation/ogg_encoder_worker.js');
/*worker.addEventListener("error", function (evt) {
 alert("Line #" + evt.lineno + " - " + evt.message + " in " + evt.filename);
 }, false);*/

(function(window){

    var Recorder = function(source, cfg){

        var config = cfg || {};
        var bufferLen = config.bufferLen || 4096;
       /* alert(source);*/
        this.context = source.context;
        /*alert( this.context);*/
        if(!this.context.createScriptProcessor){
            this.node = this.context.createJavaScriptNode(bufferLen, 2, 2);
        } else {
            this.node = this.context.createScriptProcessor(bufferLen, 2, 2);
        }

        worker.postMessage({
            cmd: 'init',
            sampleRate: this.context.sampleRate
        });

        var recording = false;
        //currCallback;

		this.node.onaudioprocess = function(e){

			if (!recording) return;
			worker.postMessage({
				cmd: 'write',
				leftData: e.inputBuffer.getChannelData(0),
				rightData: e.inputBuffer.getChannelData(1),
				samplesCount: e.inputBuffer.getChannelData(0).length
			});
        };

        this.record = function(){
            recording = true;
        };

        this.stop = function(){
            //alert("LL");
			//document.getElementById("pause").style.visibility = "hidden";
            recording = false;
            worker.postMessage({cmd: 'finish'});
        };
        this.pause=function()
        {
            //alert("DDDD");
            recording = false;

        };
        this.resume=function()
        {
            recording = true;
        };

        worker.onmessage = function(e){
            //alert(e.data.outputLength);
            var blob = new Blob([new Uint8Array(e.data.buffer, 0, e.data.outputLength)], { type: 'audio/ogg' });
            //alert(document.getElementById('save1').value);
            var url = (window.URL || window.webkitURL).createObjectURL(blob);
            //alert(url);
            var a = document.createElement("a");
            document.body.appendChild(a);
            a.style = "display: none";
            var d = new Date();
            var c = d.getMonth();
            (++c < 10)? c = "0" + c : c;
            var dt = d.getDate();
            (dt < 10)? dt = "0" + dt : dt;
            var filename=fname+"_"+c+""+dt+""+d.getHours()+""+d.getMinutes()+""+d.getSeconds();
            // alert(filename);
            a.href = url;
            a.download = filename+".ogg";
            //alert(filename);
            a.click();
            //alert(filename);
            window.URL.revokeObjectURL(url);
            var username=fname;
            //upload to server using ajax
		        url= "dictation/upload";
            var xhr = new XMLHttpRequest();
            // xhr.file = file; // not necessary if you create scopes like this
            xhr.addEventListener('progress', function(e) {

                //var done = e.position || e.loaded, total = e.totalSize || e.total;
                //console.log('xhr progress: ' + (Math.floor(done/total*1000)/10) + '%');
            }, false);
            if ( xhr.upload ) {
                xhr.upload.onprogress = function(e) {
                    // var done = e.position || e.loaded, total = e.totalSize || e.total;
                    // console.log('xhr.upload progress: ' + done + ' / ' + total + ' = ' + (Math.floor(done/total*1000)/10) + '%');
                        //alert("stop");
				    document.getElementById("controls").style.display = 'block';
                    document.getElementById("inprogress").style.visibility='visible';
                    document.getElementById("inprogressimg").style.visibility='visible';
                    document.getElementById("analyser").style.visibility='hidden';
                };
            }
            xhr.onreadystatechange = function(e) {
                if ( 4 == this.readyState ) {

                    document.getElementById("inprogress").style.visibility='hidden';
                    document.getElementById("inprogressimg").style.visibility='hidden';
                    document.getElementById("controls").style.display = 'none';



                }
            };
	            xhr.open('post', url, true);
	            xhr.setRequestHeader("fname",filename+".ogg");
	            xhr.send(blob);
		};

		source.connect(this.node);
		this.node.connect(this.context.destination);
	};

	window.Recorder = Recorder;

})(window);
