<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account"
	locale="#{sessionController.locale}">
	<%@include file="_navigation.jsp"%>
	
	<e:section value="#{msgs['PERSOINFO.REINITIALISATION.TITLE']}" rendered="#{accountController.reinit == true}"/>
	<e:section value="#{msgs['PERSOINFO.ACTIVATION.TITLE']}" rendered="#{accountController.reinit == false}" />
	
	<e:messages />
	<e:paragraph value="#{msgs['PERSOINFO.TEXT.TOP']}" />
	
	<h:form id="activationForm" rendered="#{sessionController.currentUser == null}">
	
		
	<t:dataList value="#{accountController.listBeanPersoInfo}" var="entry"> 
		<e:panelGrid>
		<t:div rendered="#{entry.value!=null}" >
		
		<e:outputLabel value="#{msgs[entry.key]}" />
		
		<e:inputText value="#{entry.value}"  required="true" size="35" validator="#{entry.validator.validate}"/>
		
		</t:div>
		</e:panelGrid>
	</t:dataList>
	
	<h:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChangeInfoPerso}" />
	</h:form>
	
	<h:form>
		
		<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>