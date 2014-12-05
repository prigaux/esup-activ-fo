<script>
$(function() {	
	$('.accueil').attr("onclick", "simulateLinkClick('restart:restartButton');");
});
</script>

<ul class="breadcrumb">
 	<li><a href="#"><span class="glyphicon glyphicon-home"></span><h:outputText value="Accueil" styleClass="accueil"/></a></li>	
    <li class="active">
   		<t:outputText value="#{msgs['IDENTIFICATION.ACTIVATION.TITLE']}" rendered="#{accountController.activ == true}" />
   		<t:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.TITLE']}"  rendered="#{accountController.reinit == true}" />
		<t:outputText value="#{msgs['AUTHENTIFICATION.LOGINCHANGE.TITLE']}"	rendered="#{accountController.loginChange == true}" />
		<t:outputText value="#{msgs['PASSWORD.PASSWORDCHANGE.TITLE']}"	rendered="#{accountController.passwChange == true}" />
		<t:outputText value="#{msgs['DATACHANGE.DATACHANGE.TITLE']}"	rendered="#{accountController.dataChange == true || accountController.viewDataChange == true}" />
   </li>
</ul>