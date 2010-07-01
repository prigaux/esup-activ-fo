<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account"
	locale="#{sessionController.locale}">
	<%@include file="_navigation.jsp"%>
	
	<e:section value="#{msgs['DISPLAYNAME.TITLE']}" />
	<e:messages />
	<e:paragraph value="#{msgs['DISPLAYNAME.TEXT.TOP']}" />
	
	<h:form id="activationForm" rendered="#{sessionController.currentUser == null}">
	
	<t:dataList value="#{accountController.currentAccount.personnelInfo}" var="entry" > 
		<e:outputLabel for="newDisplayName" value="#{entry.key}" rendered="#{entry.value != null}" />
	
		<e:inputText  id="newDisplayName"  value="#{entry.value}" rendered="#{entry.value != null}" required="true"/>
		
	</t:dataList>
	
	
	
	<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChangeDisplayName}" />
	</h:form>
	
	<h:form>
		<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChangeDisplayName}" />
		<e:commandButton value="#{msgs['ACTIVATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>