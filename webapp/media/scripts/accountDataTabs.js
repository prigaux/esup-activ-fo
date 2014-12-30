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
		 id2=id2+1;
		});	
		
	   // Une fois la validation terminée, rester sur l'onglet sélectionné	
	   var tabSelected = sessionStorage.getItem("tabSelected");
	   if(tabSelected!=null)
	     $('.nav-stacked a[href="#' + tabSelected + '"]').tab('show');
	   else
	   	 $('.nav-stacked a[href="#tab-1"]').tab('show');	   
		 
		
});