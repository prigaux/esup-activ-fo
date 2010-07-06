<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account"
	locale="#{sessionController.locale}">
	<%@include file="_navigation.jsp"%>
	
	<e:section value="#{msgs['DISPLAYNAME.TITLE']}" />
	<e:messages />
	<e:paragraph value="#{msgs['DISPLAYNAME.TEXT.TOP']}" />
	
	<h:form id="activationForm" rendered="#{sessionController.currentUser == null}">
	
		
	<t:dataList value="#{accountController.personnelInfo}" var="entry" > 
		<!--<h:panelGroup rendered="#{entry.value != null}">-->
		<h:outputLabel value="#{msgs[entry.key]}" />
	
		<h:inputText  value="#{entry.value}"  required="true"/>
		<!--</h:panelGroup>-->
	</t:dataList>
	
	<h:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChangeInfoPerso}" />
	</h:form>
	
	<h:form>
		
		<e:commandButton value="#{msgs['ACTIVATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>