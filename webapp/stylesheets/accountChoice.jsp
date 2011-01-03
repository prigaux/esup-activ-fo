<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>
<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}">

<%-- Data mustn't be recorded in this form, even by using back button --%> 
	<t:documentHead>
		<meta http-equiv="Expires" content="0">
		<meta http-equiv="cache-control" content="no-cache,no-store">
		<meta http-equiv="pragma" content="no-cache">
	</t:documentHead>

<%@include file="_navigation.jsp"%>

<e:section value="#{msgs['CHOICE.TITLE']}" />

<t:div styleClass="secondStepImage5fleches" rendered="#{accountController.reinit == true}">
	<ul id="processSteps">
		<li id="firstStep"><h:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE1.TEXT']}"></h:outputText></li>
		<li id="currentTab"><h:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE2.TEXT']}" ></h:outputText></li>
		<li id="thirdStep"><h:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE3.TEXT']}"></h:outputText></li>
		<li id="fourthStep"><h:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE4.TEXT']}"></h:outputText></li>
		<li id="fifthStep"><h:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE5.TEXT']}"></h:outputText></li>
	</ul>
</t:div>



<e:messages />

	<h:form id="accountForm" rendered="#{sessionController.currentUser == null}">
		
		<e:paragraph escape="false" value="#{msgs['CHOICE.TEXT.TOP']}">
		  <f:param value="#{accountController.partialMailPerso}" />
		  <f:param value="#{accountController.partialPager}" />
		</e:paragraph>
		
		<t:div>
			<t:selectOneRadio required="true" value="#{accountController.currentAccount.oneChoiceCanal}">
	 			<t:selectItems value="#{accountController.listBeanCanal}" var="entry" itemLabel="#{msgs[entry.key]}" itemValue="#{entry.value}"></t:selectItems>
			</t:selectOneRadio>	
		</t:div>
		
		<t:div style="margin-top:30;">
			<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChoice}" />
		</t:div>
	</h:form>
	
	<h:form>
		<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>