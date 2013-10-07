$(function() {
		// modifier le DOM pour pouvoir utiliser la m�thode tabs() de jquery UI 
		var id=1;
		var id2=1;
		 
		
		$(".hrefId").each(function(){	
		 $(this).attr("href","#tabs-"+id);
		 id=id+1;
		});	
		$(".hrefIdDetail").each(function(){
			 $(this).attr("id","tabs-"+id2);
			 id2=id2+1;
			});	
		
		
		$(".hrefFirstTab").attr("href","#firstTab");
		$(".idFirstTab").attr("id","firstTab");
		//les id="xx" sont g�rer en javascript, car jsf rajoute accountForm apr�s chaque div
		$(".hrefSecondTab").attr("href","#secondTab");		
		$(".idSecondTab").attr("id","secondTab");
		//simuler le click
		$('.hrefSecondTab').attr("onclick", "simulateLinkClick('accountForm:nextprev');");
		// Ex�cuter la m�thode tabs permettant de g�n�rer la gestion des onglets 
		$(".tabs" ).tabs();
		$( ".moretabs" ).tabs();// sous tab
		// Afficher par défaut l'onglet coordonnées professionnelles
		$( ".moretabs" ).tabs({ active: 2 });
			
});