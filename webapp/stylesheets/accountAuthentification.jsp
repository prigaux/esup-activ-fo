<%@include file="_include.jsp"%>

<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}" >
	
	<%-- Data mustn't be recorded in this form, even by using back button --%> 
	<t:documentHead>
		<meta http-equiv="Expires" content="0">
		<meta http-equiv="cache-control" content="no-cache,no-store">
		<meta http-equiv="pragma" content="no-cache">
		
		<%@include file="_includeScript.jsp"%>
		
	</t:documentHead>
	
	<%@include file="_navigation.jsp"%>
	<e:section value="#{msgs['AUTHENTIFICATION.PASSWORDCHANGE.TITLE']}" rendered="#{accountController.passwChange == true}"/>
	<e:section value="#{msgs['AUTHENTIFICATION.LOGINCHANGE.TITLE']}" rendered="#{accountController.loginChange == true}"/>
	
	<t:div styleClass="firstStepImage3fleches" rendered="#{accountController.passwChange == true}" >
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Acceuil" value="/media/home.jpg"  style="border: 0;cursur:pointer" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
		<t:htmlTag styleClass="currentTab" value="li"><e:paragraph escape="false" value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE1.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="secondStep" value="li"><e:paragraph escape="false" value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE2.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="thirdStep" value="li"><e:paragraph escape="false" value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE3.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>
	
	<t:div styleClass="firstStepImage3fleches" rendered="#{accountController.loginChange == true}" >
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Acceuil" value="/media/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
		<t:htmlTag styleClass="currentTab" value="li"><e:paragraph escape="false" value="#{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE1.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="secondStep" value="li"><e:paragraph escape="false" value="#{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE2.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="thirdStep" value="li"><e:paragraph escape="false" value="#{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE3.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>
	
	<e:messages />
	
	<e:paragraph escape="false" value="#{msgs['AUTHENTIFICATION.TEXT.TOP']}" />
	

	<h:form id="accountForm" rendered="#{sessionController.currentUser == null}">
		
		
		<e:panelGrid columns="4">
			<e:outputLabel for="login" value="#{msgs[beanLogin.key]}" />
			<e:inputText id="login" value="#{beanLogin.value}" required="#{beanLogin.required}" > </e:inputText>
			<t:graphicImage styleClass="helpTip" longdesc="#{msgs[beanLogin.help]}" value="/media/help.jpg"  style="border: 0;" rendered="#{beanLogin.help!=null}"/>
			<t:htmlTag value="span" rendered="#{beanLogin.help==null}"/>
			<e:message for="login" />

			<e:outputLabel for="password" value="#{msgs[beanPassword.key]}" />
			<e:inputSecret id="password" value="#{beanPassword.value}" required="#{beanPassword.required}"> </e:inputSecret>
			<h:graphicImage styleClass="helpTip" longdesc="#{msgs[beanPassword.help]}" value="/media/help.jpg"  style="border: 0;" rendered="#{beanPassword.help!=null}"/>
			<t:htmlTag value="span" rendered="#{beanLogin.help==null}"/>
			<e:message for="password" />
		</e:panelGrid>
												
		<t:div style="margin-top:30;">
		<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushAuthentificate}" />
		</t:div>
	</h:form>
	
	<h:form id="restart" style="display:none;">
		<e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>
	
<% /* @include file="_debug.jsp" */ %>
</e:page>