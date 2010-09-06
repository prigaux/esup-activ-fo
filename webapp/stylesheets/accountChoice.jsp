<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}">

<%-- Data mustn't be recorded in this form, even by using back button --%> 
	<t:documentHead>
		<meta http-equiv="Expires" content="0">
		<meta http-equiv="cache-control" content="no-cache,no-store">
		<meta http-equiv="pragma" content="no-cache">
	</t:documentHead>

<%@include file="_navigation.jsp"%>

<e:section value="#{msgs['CHOICE.TITLE']}" />
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