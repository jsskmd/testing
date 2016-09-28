$(document).ready(function(){
	/* This code is executed after the DOM has been completely loaded */
		
	$('nav a,footer a.up').click(function(e){
										  
		// If a link has been clicked, scroll the page to the link's hash target:
		
		$.scrollTo( this.hash || 0, 1500);
		
		e.preventDefault();
	});

$("#loginbut").click(function(e){
	e.preventDefault();
	
	});
	
	$(document).on("click",".accordion",function(e)
{
	e.preventDefault();
	
	var id=$(this).attr("id");
	
	for(var i=1;i<=5;i++){
		
			$("#"+i).find(".accordion-item").removeClass("active");
		
		$("#"+i).find(".accordion-content").css("display","none");
	
		
	if(i==id){
			
					$("#"+i).find(".accordion-item").addClass("active");
					
					$("#"+i).find(".accordion-content").css("display","block");
					
				
		}
	
	
	}
	e.stopPropagation();
});		

});

$(document).ready( function() {

	$('#backTop').backTop({
       'position' : 400,
	   'speed' : 500,
      'color' : 'red',

});

  wow = new WOW(
                      {
                      boxClass:     'wow',      // default
                      animateClass: 'animated', // default
                      offset:       0,          // default
                      mobile:       true,       // default
                      live:         true        // default
                    }
                    )
                    wow.init();

	});
	
	
	


