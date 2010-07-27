<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account"
	locale="#{sessionController.locale}">
	<%@include file="_navigation.jsp"%>
	<e:section value="#{msgs['CODE.TITLE']}" />
	<e:messages />
		
		
	<e:paragraph escape="false" value="#{msgs['CODE.TEXT.TOP']}">
		  <f:param value="#{accountController.currentAccount.emailPerso}" />
	</e:paragraph>
	
	<e:paragraph value="#{msgs['CODE.TEXT.TOP2']}" />
	<e:paragraph value="#{msgs['CODE.TEXT.TOP3']}" />
	
	
	<h:form id="activationForm" rendered="#{sessionController.currentUser == null}">
		<e:panelGrid columns="3">
			<e:outputLabel for="code" value="#{msgs[beanCode.key]}" />
			<e:inputText id="code"
				value="#{beanCode.value}"
				required="true" validator="#{beanCode.validator.validate}">
			</e:inputText>
			<h:outputLink id="rolloverImage" value="#" rendered="#{beanCode.aide!=null}">
				<h:graphicImage id="w3c" url="../media/aide.jpg"  style="border: 0;"/>
				<h:outputText id="aide" value="#{msgs[beanCode.aide]}"/>
			</h:outputLink>
			
		</e:panelGrid>
		<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushVerifyCode}" />
		<e:message for="code" />	
	</h:form>
	
	<h:form>
		<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}"action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>