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
			
		// Afficher par défaut l'onglet Données personneles
		 $('.nav-stacked li:eq(0) a').tab('show');
		
});