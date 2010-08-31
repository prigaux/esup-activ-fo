<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account"
	locale="#{sessionController.locale}">
	<%@include file="_navigation.jsp"%>
	<e:section value="#{msgs['CODE.TITLE']}" />
	<e:messages />
		
		
	<e:paragraph escape="false" value="#{msgs['CODE.TEXT.MAILPERSO.TOP']}" rendered="#{accountController.currentAccount.oneChoiceCanal==accountController.accountMailPersoKey}">
		  <f:param value="#{accountController.currentAccount.emailPerso}" />
	</e:paragraph>
	
	<e:paragraph escape="false" value="#{msgs['CODE.TEXT.PAGER.TOP']}" rendered="#{accountController.currentAccount.oneChoiceCanal==accountController.accountPagerKey}">
		  <f:param value="#{accountController.currentAccount.pager}" />
	</e:paragraph>
	
	<e:paragraph escape="false" value="#{msgs['CODE.TEXT.GEST.TOP']}" rendered="#{accountController.currentAccount.oneChoiceCanal==accountController.accountGestKey}">
		  
	</e:paragraph>
	
	<h:form id="accountForm" rendered="#{sessionController.currentUser == null}">
		<e:panelGrid columns="3">
			<e:outputLabel for="code" value="#{msgs[beanCode.key]}" />
			<e:inputText id="code" value="#{beanCode.value}" required="#{beanCode.required}" validator="#{beanCode.validator.validate}">
			</e:inputText>
			<h:outputLink  styleClass="help" id="rolloverImage" value="#" rendered="#{beanCode.help!=null}">
				<h:graphicImage url="../media/help.jpg"  style="border: 0;"/>
				<h:outputText  id="help" value="#{msgs[beanCode.help]}"/>
			</h:outputLink>
		</e:panelGrid>
		
		<t:div style="margin-top:30;">
			<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushVerifyCode}" />
		</t:div>
		<e:message for="code" />	
	
	</h:form>
	
	<h:form>
		<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}"action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>