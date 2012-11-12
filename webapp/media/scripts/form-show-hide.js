
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
	<!--Avant de supprimer la photo d''identité, afficher une boite de dialogue demandant la confirmation de la suppression-->
	<!--	Si oui, le beanFild deletePhoto aura la valeur 2 (cette variable permet de gérer l''affichage de l''icone de suppression de la page jsp mais également la sauvegarde de la photo) -->
	<!--	la photo sera remplacee par une image de suppression,le upload file et le bouton de suppression de photo seront rendus invisibles-->
	<!--	Sinon le beanFild deletePhoto aura la valeur 0.-->
	
	
	$(".delete").click(function () {	
		var answer = confirm('Etes-vous sûr de vouloir supprimer la photo?');
		if(answer){
			$('.deletePhoto').val(2);
			$('.photo').attr("src","/media/images/deletedPhoto.png");
			$('.upload').hide();
			$(this).hide();
			
		}
		else
			{
			$('.deletePhoto').val(0);
			}
		return answer
	
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


