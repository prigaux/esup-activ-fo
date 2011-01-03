<%@include file="_include.jsp"%>

<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}" >

	<%-- Data mustn't be recorded in this form, even by using back button --%> 
	<t:documentHead>
		<meta http-equiv="Expires" content="0">
		<meta http-equiv="cache-control" content="no-cache,no-store">
		<meta http-equiv="pragma" content="no-cache">
		
		<%@include file="_includeScript.jsp"%>
	</t:documentHead>

	<%@include file="_navigation.jsp"%>
	<e:section value="#{msgs['IDENTIFICATION.ACTIVATION.TITLE']}"rendered="#{accountController.activ == true}" />
	<e:section value="#{msgs['IDENTIFICATION.REINITIALISATION.TITLE']}"rendered="#{accountController.reinit == true}" />
	<e:section value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.TITLE']}"rendered="#{accountController.passwChange == true}" />
	
	<t:div styleClass="firstStepImage" rendered="#{accountController.activ == true}">
	<ul id="processSteps">
	<li id="currentTab"><h:outputText value="#{msgs['ACTIVATION.COMPTE.ETAPE1.TEXT']}"></h:outputText></li>
	<li id="secondStep"><h:outputText style="Vertical-Align:Top;" value="#{msgs['ACTIVATION.COMPTE.ETAPE2.TEXT']}"></h:outputText></li>
	<li id="thirdStep"><h:outputText value="#{msgs['ACTIVATION.COMPTE.ETAPE3.TEXT']}"></h:outputText></li>
	<li id="fourthStep"><h:outputText value="#{msgs['ACTIVATION.COMPTE.ETAPE4.TEXT']}"></h:outputText></li>
	</ul>
	</t:div>
	
	<t:div styleClass="firstStepImage5fleches" rendered="#{accountController.reinit == true}">
	<ul id="processSteps">
		<li id="currentTab"><h:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE1.TEXT']}"></h:outputText></li>
		<li id="secondStep"><h:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE2.TEXT']}" ></h:outputText></li>
		<li id="thirdStep"><h:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE3.TEXT']}"></h:outputText></li>
		<li id="fourthStep"><h:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE4.TEXT']}"></h:outputText></li>
		<li id="fifthStep"><h:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE5.TEXT']}"></h:outputText></li>
	</ul>
	</t:div>
	
	<e:messages/>

	<h:form id="accountForm" rendered="#{accountController.currentAccount.activated == false}">
	
		<e:paragraph  escape="false" value="#{msgs['IDENTIFICATION.TEXT.TOP']}" />
	
		<h:dataTable value="#{accountController.listInfoToValidate}" var="entry"> 
		 	<h:column>						  					
				<e:outputLabel value="#{msgs[entry.key]}" />
			</h:column>
			<h:column>						
				<e:inputText value="#{entry.value}"  required="#{entry.required}" size="25" validator="#{entry.validator.validate}" converter="#{entry.converter}" rendered="#{entry.converter!=null&&entry.validator!=null}"/>
				<e:inputText value="#{entry.value}"  required="#{entry.required}" size="25" validator="#{entry.validator.validate}"  rendered="#{entry.converter==null&&entry.validator!=null}"/>	
				<e:inputText value="#{entry.value}"  required="#{entry.required}" size="25"  rendered="#{entry.converter==null&&entry.validator==null}"/>
			</h:column>
			<h:column>									
				<t:graphicImage styleClass="helpTip" longdesc="#{msgs[entry.help]}" value="/media/help.jpg"  style="border: 0;" rendered="#{entry.help!=null}"/>
			</h:column>													
		</h:dataTable>
				
		<t:div style="margin-top:30;">
			<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushValid}" />
		</t:div>
	
	
	</h:form>
	
	<h:form>
		<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>
	
<% /* @include file="_debug.jsp" */ %>
</e:page>




