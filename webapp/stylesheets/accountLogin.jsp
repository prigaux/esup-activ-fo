<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>

<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}" >
	
		
	<%@include file="_navigation.jsp"%>
	
	<e:section value="#{msgs['LOGIN.ACTIVATION.TITLE']}" rendered="#{accountController.activ == true}" />
	<e:section value="#{msgs['LOGIN.REINITIALISATION.TITLE']}" rendered="#{accountController.reinit == true}" />
	<e:section value="#{msgs['LOGIN.PASSWORDCHANGE.TITLE']}" rendered="#{accountController.passwChange == true}" />
	<e:section value="#{msgs['LOGIN.TITLE']}" rendered="#{accountController.loginChange == true}" />
	
	<t:div styleClass="thirdStepImage3fleches" rendered="#{accountController.loginChange == true}" >
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Acceuil" value="/media/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
		<t:htmlTag styleClass="firstStep" value="li"><e:paragraph escape="false" value="#{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE1.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="secondStep" value="li"><e:paragraph escape="false" value="#{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE2.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="currentTabLogin" value="li"><e:paragraph escape="false" value="#{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE3.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>
	
	
	<e:messages />
	
	<e:paragraph escape="false" value="#{msgs['LOGIN.TEXT.TOP']}" rendered="#{accountController.activ == true}"/>
	

	<h:form id="accountForm" rendered="#{sessionController.currentUser == null}">
		
		
		<e:panelGrid columns="4">
			<e:outputLabel for="newlogin" value="#{msgs[beanNewLogin.key]}" />
			<e:inputText id="login" value="#{beanNewLogin.value}" required="#{beanNewLogin.required}" validator="#{beanNewLogin.validator.validate}" converter="#{beanNewLogin.converter}">
			</e:inputText>
			<h:graphicImage styleClass="helpTip" longdesc="#{msgs[beanNewLogin.help]}" value="/media/help.jpg"  style="border: 0;" rendered="#{beanNewLogin.help!=null}"/>
			<e:message for="newlogin" />
		</e:panelGrid>
						
		<t:div style="margin-top:30;">
		<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushLogin}" />
		</t:div>
	</h:form>
	
	<h:form id="restart" style="display:none;">
		<e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>
	
<% /* @include file="_debug.jsp" */ %>
</e:page>