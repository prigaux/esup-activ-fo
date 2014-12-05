<script>
$(function() {	
	$('.progress-bar').each(function(i) {
		$('.progress div.progress-bar').eq(i).append("<span class=\"glyphicon glyphicon-step-forward\"></span>");
	});

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
		
		<t:div styleClass="col-md-12">
			
			<t:div styleClass="progress">			
		        <t:div styleClass="progress-bar progress-bar-success" style="width: 20%">
		        	<t:outputText value="1 - #{msgs['IDENTIFICATION.REINITIALISATION.ETAPE1.TEXT']}"/> 
		        </t:div>
		        <t:div styleClass="progress-bar  progress-bar-info" style="width: 20%">
		        	<t:outputText value="2 - #{msgs['IDENTIFICATION.REINITIALISATION.ETAPE2.TEXT']}"/> 
		        </t:div>
		         <t:div styleClass="progress-bar  progress-bar-info" style="width: 20%">
		        	<t:outputText value="3 - #{msgs['IDENTIFICATION.REINITIALISATION.ETAPE3.TEXT']}"/> 
		        </t:div>
		         <t:div styleClass="progress-bar  progress-bar-info" style="width: 20%">
		        	<t:outputText value="4 - #{msgs['IDENTIFICATION.REINITIALISATION.ETAPE4.TEXT']}"/> 
		        </t:div>
		         <t:div styleClass="progress-bar  progress-bar-info" style="width: 20%">
		        	<t:outputText value="5 - #{msgs['IDENTIFICATION.REINITIALISATION.ETAPE5.TEXT']}"/> 
		        </t:div>
			</t:div>
		</t:div>
	</t:div>
</t:div>

<t:div rendered="#{accountController.activ == true}">
	<t:div styleClass="row">
	<t:div styleClass="col-md-12">
			<t:div styleClass="progress">			
		        <t:div styleClass="progress-bar progress-bar-success" style="width: 25%">
		        	<t:outputText value="1 - #{msgs['ACTIVATION.COMPTE.ETAPE1.TEXT']}"/> 
		        </t:div>
		        <t:div styleClass="progress-bar  progress-bar-info" style="width: 25%">
		        	<t:outputText value="2 - #{msgs['ACTIVATION.COMPTE.ETAPE2.TEXT']}"/> 
		        </t:div>
		         <t:div styleClass="progress-bar  progress-bar-info" style="width: 25%">
		        	<t:outputText value="3 - #{msgs['ACTIVATION.COMPTE.ETAPE3.TEXT']}"/> 
		        </t:div>
		         <t:div styleClass="progress-bar  progress-bar-info" style="width: 25%">
		        	<t:outputText value="4 - #{msgs['ACTIVATION.COMPTE.ETAPE4.TEXT']}"/> 
		        </t:div>		      
			</t:div>
		</t:div>
	</t:div>
</t:div>


<t:div rendered="#{accountController.passwChange == true}">
	<t:div styleClass="row">
		<t:div styleClass="col-md-12">
			<t:div styleClass="progress">			
		        <t:div styleClass="progress-bar progress-bar-success" style="width: 33.5%">
		        	<t:outputText value="1 - #{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE1.TEXT']}"/> 
		        </t:div>
		        <t:div styleClass="progress-bar  progress-bar-info" style="width: 33.5%">
		        	<t:outputText value="2 - #{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE2.TEXT']}"/> 
		        </t:div>
		         <t:div styleClass="progress-bar  progress-bar-info" style="width: 33%">
		        	<t:outputText value="3 - #{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE3.TEXT']}"/> 
		        </t:div>	      
			</t:div>
		</t:div>
	</t:div>
</t:div>

<t:div rendered="#{accountController.loginChange == true}">
	<t:div styleClass="row">
		<t:div styleClass="col-md-12">
			<t:div styleClass="progress">			
		        <t:div styleClass="progress-bar progress-bar-success" style="width: 33.5%">
		        	<t:outputText value="1 - #{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE1.TEXT']}"/> 
		        </t:div>
		        <t:div styleClass="progress-bar  progress-bar-info" style="width: 33.5%">
		        	<t:outputText value="2 - #{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE2.TEXT']}"/> 
		        </t:div>
		         <t:div styleClass="progress-bar  progress-bar-info" style="width: 33%">
		        	<t:outputText value="3 - #{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE3.TEXT']}"/> 
		        </t:div>	      
			</t:div>
		</t:div>
	</t:div>
</t:div>
