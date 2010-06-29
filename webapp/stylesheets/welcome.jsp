<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="welcome" locale="#{sessionController.locale}" >
	<%@include file="_navigation.jsp"%>
	<e:section value="#{msgs['WELCOME.TITLE']}" />
	<e:paragraph escape="false" value="#{msgs['WELCOME.TEXT.TOP']}" />
	
	<e:messages/>
	<t:messages/>

	<h:panelGroup rendered="#{sessionController.currentUser != null}">
		<e:paragraph value="#{msgs['WELCOME.TEXT.AUTHENTICATED']}" />
	</h:panelGroup>
	<h:form id="welcomeForm" rendered="#{sessionController.currentUser == null}">
		<e:paragraph value="#{msgs['WELCOME.TEXT.UNAUTHENTICATED']}" />
		
		
	
	<e:commandButton id="activation" value="#{msgs['NAVIGATION.TEXT.ACTIVATION']}"
	action="#{accountController.enterActivation}"
	accesskey="#{msgs['NAVIGATION.ACCESSKEY.ETUDIANT']}" />
	
	<e:commandButton id="reinit" value="#{msgs['NAVIGATION.TEXT.REINIT']}"
	action="#{accountController.enterReinitialisation}"
	accesskey="#{msgs['NAVIGATION.ACCESSKEY.ANCIEN']}" />
	
	<e:commandButton  id="password" value="#{msgs['NAVIGATION.TEXT.MDP']}"
	action="#{accountController.enterReinitialisation}"
	accesskey="#{msgs['NAVIGATION.ACCESSKEY.PERSONNEL']}" />
			
	</h:form>
	
<script type="text/javascript">	
	hideButton("welcomeForm:localeChangeButton");		
</script>
<% /* @include file="_debug.jsp" */ %>
</e:page>
