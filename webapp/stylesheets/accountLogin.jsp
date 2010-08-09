<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}" >
	<%@include file="_navigation.jsp"%>
	
	<e:section value="#{msgs['LOGIN.ACTIVATION.TITLE']}" rendered="#{accountController.activ == true}" />
	<e:section value="#{msgs['LOGIN.REINITIALISATION.TITLE']}" rendered="#{accountController.reinit == true}" />
	<e:section value="#{msgs['LOGIN.PASSWORDCHANGE.TITLE']}" rendered="#{accountController.passwChange == true}" />
	<e:section value="#{msgs['LOGIN.TITLE']}" rendered="#{accountController.loginChange == true}" />
	<e:messages />
	
	<e:paragraph escape="false" value="#{msgs['LOGIN.TEXT.TOP']}" rendered="#{accountController.activ == true}"/>
	

	<h:form id="accountForm" rendered="#{sessionController.currentUser == null}">
		
		
		<e:panelGrid columns="3" columnClasses="col1,col2,col3" >
			<e:outputLabel for="newlogin" value="#{msgs[beanNewLogin.key]}" />
			<e:inputText id="login" value="#{beanNewLogin.value}" required="true" validator="#{beanNewLogin.validator.validate}"> </e:inputText>
			<h:outputLink id="rolloverImage" value="#" rendered="#{beanNewLogin.aide!=null}">
				<h:graphicImage id="w3c" url="../media/aide.jpg"  style="border: 0;"/>
				<h:outputText id="aide" value="#{msgs[beanNewLogin.aide]}"/>
			</h:outputLink>
			<e:message for="newlogin" />
		</e:panelGrid>
		
				
			<t:div style="margin-top:30;">
			<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushLogin}" />
			</t:div>
	</h:form>
	
	<h:form>
		<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>
	
<% /* @include file="_debug.jsp" */ %>
</e:page>