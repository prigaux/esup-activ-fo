<script>
$(function() {	
	$('.progress-bar').each(function(i) {
		$('.progress div.progress-bar').eq(i).append("<span class=\"glyphicon glyphicon-step-forward\"></span>");
	});
	$('.accueil').attr("href","#");
	$('.accueil').attr("onclick", "simulateLinkClick('restart:restartButton');");
	
});
//Gestion de la progression de la barre
function progressBar(barActive){
	$('.progress-bar').each(function(i) {
	    if ( i == barActive || i < barActive ){ 	
	    	$('.progress div.progress-bar').eq(i).removeClass('progress-bar-info').addClass('progress-bar-success');
	    }
	});
}
</script>


<t:div rendered="#{accountController.reinit == true}">
	<t:div styleClass="row">
		<t:div styleClass="col-md-1">
			<t:htmlTag  value="a" styleClass="accueil"><t:htmlTag  value="span" styleClass="glyphicon glyphicon-home"> </t:htmlTag><h:outputText value="Accueil" /></t:htmlTag>					
		</t:div>
		<t:div styleClass="col-md-11">
			<t:div styleClass="progress">			
		        <t:div styleClass="progress-bar progress-bar-success" style="width: 20%">
		        	<t:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE1.TEXT']}"/> 
		        </t:div>
		        <t:div styleClass="progress-bar  progress-bar-info" style="width: 20%">
		        	<t:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE2.TEXT']}"/> 
		        </t:div>
		         <t:div styleClass="progress-bar  progress-bar-info" style="width: 20%">
		        	<t:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE3.TEXT']}"/> 
		        </t:div>
		         <t:div styleClass="progress-bar  progress-bar-info" style="width: 20%">
		        	<t:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE4.TEXT']}"/> 
		        </t:div>
		         <t:div styleClass="progress-bar  progress-bar-info" style="width: 20%">
		        	<t:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE5.TEXT']}"/> 
		        </t:div>
			</t:div>
		</t:div>
	</t:div>
</t:div>

<t:div rendered="#{accountController.activ == true}">
	<t:div styleClass="row">
		<t:div styleClass="col-md-1">
			<t:htmlTag  value="a" styleClass="accueil"><t:htmlTag  value="span" styleClass="glyphicon glyphicon-home"> </t:htmlTag><h:outputText value="Accueil" /></t:htmlTag>					
		</t:div>
		<t:div styleClass="col-md-11">
			<t:div styleClass="progress">			
		        <t:div styleClass="progress-bar progress-bar-success" style="width: 25%">
		        	<t:outputText value="#{msgs['ACTIVATION.COMPTE.ETAPE1.TEXT']}"/> 
		        </t:div>
		        <t:div styleClass="progress-bar  progress-bar-info" style="width: 25%">
		        	<t:outputText value="#{msgs['ACTIVATION.COMPTE.ETAPE2.TEXT']}"/> 
		        </t:div>
		         <t:div styleClass="progress-bar  progress-bar-info" style="width: 25%">
		        	<t:outputText value="#{msgs['ACTIVATION.COMPTE.ETAPE3.TEXT']}"/> 
		        </t:div>
		         <t:div styleClass="progress-bar  progress-bar-info" style="width: 25%">
		        	<t:outputText value="#{msgs['ACTIVATION.COMPTE.ETAPE4.TEXT']}"/> 
		        </t:div>		      
			</t:div>
		</t:div>
	</t:div>
</t:div>


<t:div rendered="#{accountController.passwChange == true}">
	<t:div styleClass="row">
		<t:div styleClass="col-md-1">
			<t:htmlTag  value="a" styleClass="accueil"><t:htmlTag  value="span" styleClass="glyphicon glyphicon-home"> </t:htmlTag><h:outputText value="Accueil" /></t:htmlTag>					
		</t:div>
		<t:div styleClass="col-md-11">
			<t:div styleClass="progress">			
		        <t:div styleClass="progress-bar progress-bar-success" style="width: 33.5%">
		        	<t:outputText value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE1.TEXT']}"/> 
		        </t:div>
		        <t:div styleClass="progress-bar  progress-bar-info" style="width: 33.5%">
		        	<t:outputText value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE2.TEXT']}"/> 
		        </t:div>
		         <t:div styleClass="progress-bar  progress-bar-info" style="width: 33%">
		        	<t:outputText value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE3.TEXT']}"/> 
		        </t:div>	      
			</t:div>
		</t:div>
	</t:div>
</t:div>

<t:div rendered="#{accountController.loginChange == true}">
	<t:div styleClass="row">
		<t:div styleClass="col-md-1">
			<t:htmlTag  value="a" styleClass="accueil"><t:htmlTag  value="span" styleClass="glyphicon glyphicon-home"></t:htmlTag><h:outputText value="Accueil" /></t:htmlTag>					
		</t:div>
		<t:div styleClass="col-md-11">
			<t:div styleClass="progress">			
		        <t:div styleClass="progress-bar progress-bar-success" style="width: 33.5%">
		        	<t:outputText value="#{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE1.TEXT']}"/> 
		        </t:div>
		        <t:div styleClass="progress-bar  progress-bar-info" style="width: 33.5%">
		        	<t:outputText value="#{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE2.TEXT']}"/> 
		        </t:div>
		         <t:div styleClass="progress-bar  progress-bar-info" style="width: 33%">
		        	<t:outputText value="#{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE3.TEXT']}"/> 
		        </t:div>	      
			</t:div>
		</t:div>
	</t:div>
</t:div>
