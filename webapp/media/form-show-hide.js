
$(function() {

	$(".show").click(function () {
	    $("." + $(this).attr("alt")+"hide:first").show();
	    $("." + $(this).attr("alt")+"hide:first").removeClass().addClass($(this).attr("alt")+"show");
	  
	    });
	$(".hide").click(function () {
	    
		//if ( $("." + $(this).attr("alt")+"show").children("#old")   ) {
		if( $("." + $(this).attr("alt")+"show:last").children("#old")         ) {
		   $("." + $(this).attr("alt")+"show:last").hide();
		   $("." + $(this).attr("alt")+"show:last").removeClass().addClass($(this).attr("alt")+"hide");
	     }

	      
	     
	    });
	

});


