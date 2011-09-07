<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account"
	locale="#{sessionController.locale}">
	<%@include file="_navigation.jsp"%>
	<%@include file="_includeScript.jsp"%>
	<e:section value="#{msgs['IDENTIFICATION.ACTIVATION.TITLE']}" />
	
	<t:div styleClass="thirdStepImage" rendered="#{accountController.activ == true}">
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Accueil" value="/media/images/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
		<t:htmlTag styleClass="firstStep" value="li"><t:outputText escape="false" value="#{msgs['ACTIVATION.COMPTE.ETAPE1.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="secondStep" value="li"><t:outputText escape="false" value="#{msgs['ACTIVATION.COMPTE.ETAPE2.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="currentTab" value="li"><t:outputText escape="false" value="#{msgs['ACTIVATION.COMPTE.ETAPE3.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="fourthStep" value="li"><t:outputText escape="false" value="#{msgs['ACTIVATION.COMPTE.ETAPE4.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>
	
	<e:messages />
	
	<e:paragraph escape="false" value="#{msgs['CHARTER.TEXT.TOP']}" />

	<h:form id="accountForm">

		<e:outputLabel for="charterAgreement" value="#{msgs['CHARTER.TEXT.AGREE']}" />
		<e:selectBooleanCheckbox id="charterAgreement" value="#{accountController.currentAccount.charterAgreement}" required="true" />
		<t:div style="margin-top:30;">
			<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushCharterAgreement}" />
		</t:div>
	</h:form>
	
	<h:form id="restart" style="display:none;">
		<e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>