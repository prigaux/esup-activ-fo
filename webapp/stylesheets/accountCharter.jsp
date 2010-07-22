<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account"
	locale="#{sessionController.locale}">
	<%@include file="_navigation.jsp"%>
	<e:section value="#{msgs['CHARTER.TITLE']}" />
	<e:messages />

	<e:paragraph escape="false" value="#{msgs['CHARTER.TEXT.TOP']}" />

	<h:form id="charterForm"
		rendered="#{sessionController.currentUser == null}">

		<e:outputLabel for="charterAgreement"
			value="#{msgs['CHARTER.TEXT.AGREE']}" />
		<e:selectBooleanCheckbox id="charterAgreement"
			value="#{accountController.currentAccount.charterAgreement}"
			required="true" />

		<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}"
			action="#{accountController.pushCharterAgreement}" />
	</h:form>
	
	<h:form>
		<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>