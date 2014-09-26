$(function() {
		// modifier le DOM pour pouvoir utiliser les onglets de bootstrap 
		var id=1;
		var id2=1;
		 
		
		$(".hrefId").each(function(){	
		$(this).attr("href","#tab-"+id);
		//Gérer l'activation des onglets lors des clics
		$(this).attr("data-toggle","pill");
		id=id+1;
		});	
		//
		$(".hrefIdDetail").each(function(){
		 $(this).attr("id","tab-"+id2);
		 //$(this).attr("class","tab-pane");
		 id2=id2+1;
		});	
			
		// Afficher par défaut l'onglet coordonnées professionnelles
		 $('.nav-stacked li:eq(2) a').tab('show');
		
});