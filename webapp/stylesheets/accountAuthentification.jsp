<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}" >
	
	<%-- Data mustn't be recorded in this form, even by using back button --%> 
	<t:documentHead>
		<meta http-equiv="Expires" content="0">
		<meta http-equiv="cache-control" content="no-cache,no-store">
		<meta http-equiv="pragma" content="no-cache">
	</t:documentHead>
	
	<%@include file="_navigation.jsp"%>
	<e:section value="#{msgs['AUTHENTIFICATION.PASSWORDCHANGE.TITLE']}" rendered="#{accountController.passwChange == true}"/>
	<e:section value="#{msgs['AUTHENTIFICATION.LOGINCHANGE.TITLE']}" rendered="#{accountController.loginChange == true}"/>
	<e:messages />
	
	<e:paragraph escape="false" value="#{msgs['AUTHENTIFICATION.TEXT.TOP']}" />
	

	<h:form id="accountForm" rendered="#{sessionController.currentUser == null}">
		
		
		<e:panelGrid columns="3" columnClasses="col1,col2,col3" >
			<e:outputLabel for="login" value="#{msgs[beanLogin.key]}" />
			<e:inputText id="login" value="#{beanLogin.value}" required="#{beanLogin.required}" validator="#{beanLogin.validator.validate}"> </e:inputText>
			<h:outputLink id="rolloverImage" value="#" rendered="#{beanLogin.help!=null}">
				<h:graphicImage id="w3c" url="../media/aide.jpg"  style="border: 0;"/>
				<h:outputText id="help" value="#{msgs[beanLogin.help]}"/>
			</h:outputLink>
			<e:message for="login" />
		</e:panelGrid>
		
		<e:panelGrid columns="3" columnClasses="col1,col2,col3" >
			<e:outputLabel for="password" value="#{msgs[beanPassword.key]}" />
			<e:inputSecret id="password" value="#{beanPassword.value}" required="#{beanPassword.required}"> </e:inputSecret>
			<h:outputLink  styleClass="help" value="#" rendered="#{beanPassword.help!=null}">
				<h:graphicImage url="../media/help.jpg"  style="border: 0;"/>
				<h:outputText id="help" value="#{msgs[beanPassword.help]}"/>
			</h:outputLink>
			<e:message for="password" />
		</e:panelGrid>
		
		
			
			
			
			<t:div style="margin-top:30;">
			<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushAuthentificate}" />
			</t:div>
	</h:form>
	
	<h:form>
		<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>
	
<% /* @include file="_debug.jsp" */ %>
</e:page>