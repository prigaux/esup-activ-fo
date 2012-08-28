
$(function() {
    $(".hide").load(function() {
    	if( $("." + $(this).attr("alt")+"show").size()==0){
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
	
	$("[class$='_modifyLink']").click(function () {	
		$("." + $(this).attr("class").replace(/_modifyLink.*/,"")+"show").show(500);
		$("." + $(this).attr("class").replace(/_modifyLink.*/,"")+"modify").show(500);				
		$("." + $(this).attr("class").replace(/_modifyLink.*/,"")+"output").hide();	
		$("." + $(this).attr("class").replace(/_modifyLink.*/,"")+"constraint").show();
		$(this).hide();				
	});
	
	$("#AllModifyLink").click(function () {	
		$("[class*='_modifyLink']").click();		
	});
	
});


