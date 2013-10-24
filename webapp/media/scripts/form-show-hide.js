
$(function() {
	// Mettre la premiere ligne des champs multi évalués à beanName+show
   $(".hide").load(function() {
    	if( $("." + $(this).attr("alt")+"show").size()==0){
    		$("." + $(this).attr("alt")+"hide:first").removeClass($(this).attr("alt")+"hide").addClass($(this).attr("alt")+"show");
    	}
    });
	
   //Permet de gérer l'ajout d'une ligne d'un champ multi évalué
	$(".show").click(function () {
	    $("." + $(this).attr("alt")+"hide:first").show();
	    $(("." + $(this).attr("alt")+"hide:first").removeClass($(this).attr("alt")+"hide").addClass($(this).attr("alt")+"show")).show(500);
	});
	
	 //Permet de gérer la suppression d'une ligne d'un champ multi évalué
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
		var answer = confirm('Etes-vous s\373r de vouloir supprimer la photo?');
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
	
	//Si l'utilisateur clique sur le lien "Editer", n'afficher en modification que les champs de la catégorie sélectionnée 
	$(".modifyByCategory").each(function(){	
	 $(this).click(function() {
		$(this).closest('.mainModifyLinkByCategory').find("[class*='_modify']").click();
	    return false;
	  });
	});	
	

	$("[class*='_modify']").click(function () {	
		var field=$(this).attr('class').replace("_modifyLink","");
		modify(field,this);
	});
	
	// Au click sur un champ, afficher en mode modification
	$("[class*='output']").click(function () {	
		// Prendre la premiere classe de la liste, celle qui contient beanName+output
		var getFirstClass = $(this).attr('class').split(' ')[0];
		var field=getFirstClass.replace("output","");
		var fieldConcate=field+"_modifyLink";
		//Tester si le champ est modifiable, cad si le champ à modifier possède une classe de meme nom (nomChamp+_modifyLink)
		var find =$(this).closest('.mainModifyLinkByCategory').find("."+fieldConcate);
		if (find.size()>0){modify(fieldConcate,this);}
		//console.log(find);
	});
	// Lorsque l'utilisateur modifie un champ nécessitant la validation de la DRH, 
	// un popup s'affiche lui avertissant que le la donnée modifiée ne sera pas immédiatement prise en compte à l'écran 
	$("[class*='show']").focusout(function () {	
		var getFirstClass = $(this).attr('class').split(' ')[0];
		getFirstClass=getFirstClass.replace("show","");
		getFirstClass=getFirstClass+"toValidateDRH";
		var find =$(this).closest('.mainModifyLinkByCategory').find("."+getFirstClass);
		if (find.size()>0){
			var val = $(".digestConstraint").html();
			dialog(val);}
	});
	
	// Afficher le(s) champ(s) en mode modification
	function modify(field,elt){
		var field=field.replace("_modifyLink","");
		 $("." + field+"show").show();
		 $("." + field+"modify").show();
		 $("." + field+"output").hide();
		 $("." + field+"constraint").show();		 
		 $(elt).closest('.mainModifyLinkByCategory').find(".validate").show();
		 $(elt).closest('.mainModifyLinkByCategory').find(".modifyByCategory").hide();
	}
	
	function dialog(a){
		$("<div />", { text: a }).dialog({
			title:"Attention",
		    width: 370,
	        position: [300,200],
	        buttons: {
	            "Ok": function() {
	                $(this).dialog("close");
	            }
	        }
	    });
	};
	
	
	
	
});