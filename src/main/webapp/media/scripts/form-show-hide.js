
$(function() {

	var paramDialog="";
	
	// Gestion de l'infobulle
	$('.toolTipShow').attr("data-toggle","tooltip");	
	$('[data-toggle="tooltip"]').tooltip({
	    placement : 'right'
	});

	// Mettre la premiere ligne des champs multi évalués à beanName+show
   $(".hideField").load(function() {
    	if( $("." + $(this).attr("alt")+"show").size()==0){
    		$("." + $(this).attr("alt")+"hideField:first").removeClass($(this).attr("alt")+"hideField").addClass($(this).attr("alt")+"show");
    	}
    });
	
   //Permet de gérer l'ajout d'une ligne d'un champ multi évalué
	$(".showField").click(function () {
	    $("." + $(this).attr("alt")+"hideField:first").show();
	    $("." + $(this).attr("alt")+"hideField:first").removeClass($(this).attr("alt")+"hideField").addClass($(this).attr("alt")+"show");	  
	});
	
	 //Permet de gérer la suppression d'une ligne d'un champ multi évalué
	$(".hideField").click(function () {
		if( $("." + $(this).attr("alt")+"show").size() > 1    ) {
			$("." + $(this).attr("alt")+"show:last").hide();
		    $("." + $(this).attr("alt")+"show:last").children("input,select").val("");
		    $("." + $(this).attr("alt")+"show:last").removeClass().addClass($(this).attr("alt")+"hideField");
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
			$('.setDataURL').val("");
			$('.showDeletePhoto').show();
			$('.upload').hide();
			$(this).hide();
			$('.croppie-container').hide();
			$('.insertinnerHTMLRotation').hide();
			$('.export').hide();
			
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
	
	//appelée par $(".modifyByCategory").each(function(){	
	$("[class*='_modify']").click(function () {
		var field=$(this).attr('class').replace("_modifyLink","");
		modify(field,this);
	});
	
	
	//Au clic sur un champs de type case à cocher ou radio buton, seul le bouton editer passe à confirmer)
	$(".showHideButton").click(function () {
		 $(this).closest('.mainModifyLinkByCategory').find(".validate").show();
		 $(this).closest('.mainModifyLinkByCategory').find(".modifyByCategory").hide();
	});


	// Au clic sur un champ, afficher en mode modification
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
	
	// Au clic sur un libelle, afficher en mode modification le champ associé
	// util lorsque le champs est vide
	$("[class*='labeltexttop']").click(function () {
		// Prendre la premiere classe de la liste, celle qui contient beanName+output
		var getFirstClass = $(this).attr('class').split(' ')[0];
		var fieldConcate=getFirstClass+"_modifyLink";
		var find =$(this).closest('.mainModifyLinkByCategory').find("."+fieldConcate);
		if (find.size()>0){modify(fieldConcate,this);}
	});
	


	// Gérer les champs nécessitant la validation de la DRH:
	// Si c'est un champ à valider par la DRH et si la valeur initiale est différente de celle saisie 
	// alors ce champ va etre envoyé en paramètre à la boite de dialogue
	$("[class*='Popup']").change(function () {	
		var oldval;
		var currentval;
		var getFirstClass = $(this).attr('class').split(' ')[0];
		var fieldName=getFirstClass.replace("Popup","");
		getFirstClasstoValidate=fieldName+"toValidateDRH";
		var find =$(this).closest('.mainModifyLinkByCategory').find("."+getFirstClasstoValidate);
		// Si le champ est à valider par la DRH
		if (find.size()>0){	
			var tagName=$(this).get(0).tagName;
			// Impossibilité d'utiliser defaultValue pour les tagName de type select, d'ou utilisation de la solution suivante 
			if(tagName=='SELECT'){
				oldval = $("."+fieldName+"output").html();
				currentval=$(this).find("option:selected").text();
				
			}
			if(tagName=='INPUT'){
				oldval=this.defaultValue;
				currentval=this.value;
			}
			
			if(oldval!=this.value){
				var modifiedFields = $("."+fieldName).html();
				if(paramDialog=="")
					paramDialog=paramDialog+modifiedFields+": "+currentval+"</br>";
				else
					paramDialog="- "+paramDialog+"- "+modifiedFields+": "+currentval+"</br>";				
				}
		}//fin find
	});
	
	//Lors de la confirmation, si un champ modifié nécessite la validation de la DRH,
	// un popup s'affichera lui avertissant que le la donnée modifiée ne sera pas immédiatement prise en compte à l'écran 	
	$(".validate").click(function() {
		if(paramDialog!=""){
			$('.dataModifyToMyModal').html(paramDialog);
			paramDialog="";
			$("#myModal").modal('show');
			//Pouvoir déplacer la boite de dialogue
			$("#myModal").draggable({
			    handle: ".modal-header"
			});
		}
		else
			{
			simulateLinkClick('accountForm:saveDataChange');
			}
		
	 });
	//Faire disparaitre les messages d'erreur ou de confirmation d'enregistrement de l'onglet actuel, lorsque l'utilisateur clique sur un autre onglet
	$(".hrefId").click(function() {
	 $(".tab-content>ul").css("display","none");
	});
	
	// Afficher le(s) champ(s) en mode modification
	function modify(field,elt){
		 var field=field.replace("_modifyLink","");
		 $("." + field+"show").show();
		 $("." + field+"modify").show();
		 if(field!="jpegPhoto"){$("." + field+"output").hide();}
		 $("." + field+"constraint").show();
		 $("." + field+"digestConstraint").show();
		 $(elt).closest('.mainModifyLinkByCategory').find(".validate").show();
		 $(elt).closest('.mainModifyLinkByCategory').find(".modifyByCategory").hide();
		 if(field=="jpegPhoto"){
			 croppie();
			 $(".cr-slider-wrap").css("display","block");
			 $(".cr-viewport").addClass('noafter');
			 $('.croppie-container').show();
			 $('.insertinnerHTMLRotation').show();
			 $('.export').hide();
		 }
	}

});
