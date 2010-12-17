<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account"
	locale="#{sessionController.locale}">
	<%@include file="_navigation.jsp"%>
	<%@include file="_includeScript.jsp"%>
	<e:section value="#{msgs['CHARTER.TITLE']}" />
	
	<t:div styleClass="thirdStepImage" rendered="#{accountController.activ == true}">
	<ul id="processSteps">
		<li id="firstStep"><h:outputText value="#{msgs['ACTIVATION.COMPTE.ETAPE1.TEXT']}"></h:outputText></li>
		<li id="secondStep"><h:outputText value="#{msgs['ACTIVATION.COMPTE.ETAPE2.TEXT']}"></h:outputText></li>
		<li id="currentTab"><h:outputText value="#{msgs['ACTIVATION.COMPTE.ETAPE3.TEXT']}"></h:outputText></li>
		<li id="fourthStep"><h:outputText value="#{msgs['ACTIVATION.COMPTE.ETAPE4.TEXT']}"></h:outputText></li>
	</ul>
	</t:div>
	
	<e:messages />
	
	<e:paragraph escape="false" value="#{msgs['CHARTER.TEXT.TOP']}" />

	<h:form id="accountForm"
		rendered="#{sessionController.currentUser == null}">

		<e:outputLabel for="charterAgreement" value="#{msgs['CHARTER.TEXT.AGREE']}" />
		<e:selectBooleanCheckbox id="charterAgreement" value="#{accountController.currentAccount.charterAgreement}" required="true" />
		<t:div style="margin-top:30;">
			<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushCharterAgreement}" />
		</t:div>
	</h:form>
	
	<h:form>
		<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>