<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>

<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}">
	
	<t:documentHead>
		<meta http-equiv="Expires" content="0">
		<meta http-equiv="cache-control" content="no-cache,no-store">
		<meta http-equiv="pragma" content="no-cache">
	</t:documentHead>

	<%@include file="_navigation.jsp"%>
	
	<e:section value="#{msgs['PERSOINFO.REINITIALISATION.TITLE']}" rendered="#{accountController.reinit == true}"/>
	<e:section value="#{msgs['PERSOINFO.ACTIVATION.TITLE']}" rendered="#{accountController.activ == true}" />
	<e:section value="#{msgs['PERSOINFO.PASSWORDCHANGE.TITLE']}" rendered="#{accountController.passwChange == true}" />
	<e:section value="#{msgs['PERSOINFO.LOGINCHANGE.TITLE']}" rendered="#{accountController.loginChange == true}" />

	<e:messages/>
	
	<e:paragraph value="#{msgs['PERSOINFO.TEXT.TOP']}" />
	
	<h:form id="accountForm" rendered="#{sessionController.currentUser == null}">
	
	
		
	<h:dataTable value="#{accountController.listBeanPersoInfo}" var="entry"> 
					<h:column>						
						<e:outputLabel value="#{msgs[entry.key]}" />
					</h:column>
					<h:column>		
						<e:inputText value="#{entry.value}"  required="#{entry.required}" size="35" validator="#{entry.validator.validate}" rendered="#{entry.fieldType==null}"/>
					</h:column>
					<h:column>			
						<h:graphicImage styleClass="helpTip" longdesc="#{msgs[entry.help]}" value="/media/help.jpg"  style="border: 0;" rendered="#{entry.help!=null}"/>
					</h:column>										
	</h:dataTable>
	
	
	<t:div style="margin-top:30;">
		<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChangeInfoPerso}" />
	</t:div>
	</h:form>
	
	<h:form>
		
		<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>