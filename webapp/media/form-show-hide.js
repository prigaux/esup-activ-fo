
$(function() {
    $(".hide").load(function() {
    	if( $("." + $(this).attr("alt")+"show").size()==0){
    		$("." + $(this).attr("alt")+"hide:first").show();
    		$("." + $(this).attr("alt")+"hide:first").removeClass().addClass($(this).attr("alt")+"show");
    	}
    });
	
	$(".show").click(function () {
	    $("." + $(this).attr("alt")+"hide:first").show();
	    $("." + $(this).attr("alt")+"hide:first").removeClass().addClass($(this).attr("alt")+"show");
	});
	
	$(".hide").click(function () {
		if( $("." + $(this).attr("alt")+"show").size() > 1    ) {
			$("." + $(this).attr("alt")+"show:last").hide();
		    $("." + $(this).attr("alt")+"show:last").children("input,select").val("");
		    $("." + $(this).attr("alt")+"show:last").removeClass().addClass($(this).attr("alt")+"hide");
		}
	});
});


