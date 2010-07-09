<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account"
	locale="#{sessionController.locale}">
	<%@include file="_navigation.jsp"%>
	
	<e:section value="#{msgs['DISPLAYNAME.TITLE']}" />
	<e:messages />
	<e:paragraph value="#{msgs['DISPLAYNAME.TEXT.TOP']}" />
	
	<h:form id="activationForm" rendered="#{sessionController.currentUser == null}">
	
		
	<t:dataList value="#{accountController.listPersoInfo}" var="entry"> 
		<e:panelGrid columns="3" >
		<t:div id="infoDiv" rendered="#{entry.value != null}">
		
		<h:outputLabel id="infoLab" value="#{msgs[entry.key]}" />
		
		<h:inputText id="infoInput" value="#{entry.value}"  required="true" size="35"/>
		
		</t:div>
		</e:panelGrid>
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