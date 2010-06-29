<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account"
	locale="#{sessionController.locale}">
	<%@include file="_navigation.jsp"%>
	<e:section value="#{msgs['EMAILPERSO.TITLE']}" />
	
	<e:messages />
		
	<e:paragraph value="#{msgs['EMAILPERSO.TEXT.TOP']}" />
		
	<h:form id="activationForm"
		rendered="#{sessionController.currentUser == null}">

	<e:outputLabel for="emailPerso"
		value="#{msgs['EMAILPERSO.TEXT.LABEL']}" />
	<e:inputText id="emailPerso"
		value="#{accountController.currentAccount.emailPerso}"
		required="true">
		<t:validateEmail/>
	</e:inputText>
	

		<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}"
			action="#{accountController.pushEmailPerso}" />
		<e:message for="emailPerso"/>
	</h:form>
	
	<h:form>
		<e:commandButton value="#{msgs['ACTIVATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>