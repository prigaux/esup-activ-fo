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

<e:section value="#{msgs['IDENTIFICATION.REINITIALISATION.TITLE']}" />

<t:div styleClass="secondStepImage5fleches" rendered="#{accountController.reinit == true}">
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Accueil" value="/media/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
		<t:htmlTag styleClass="firstStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE1.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="currentTab" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE2.TEXT']}" /></t:htmlTag>
		<t:htmlTag styleClass="thirdStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE3.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="fourthStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE4.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="fifthStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE5.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
</t:div>

<e:messages />

	<h:form id="accountForm" rendered="#{sessionController.currentUser == null}">
		
		<e:paragraph escape="false" value="#{msgs['CHOICE.TEXT.WITHOUTEMAIL.TOP']}" rendered="#{accountController.partialMailPerso==''&&accountController.partialPager!=''}">
		  <f:param value="#{accountController.partialPager}" />
		</e:paragraph>
		
		<e:paragraph escape="false" value="#{msgs['CHOICE.TEXT.WITHOUTPHONE.TOP']}" rendered="#{accountController.partialMailPerso!=''&&accountController.partialPager==''}">
		  <f:param value="#{accountController.partialMailPerso}" />
		</e:paragraph>
		
		<e:paragraph escape="false" value="#{msgs['CHOICE.TEXT.TOP']}" rendered="#{accountController.partialMailPerso!=''&&accountController.partialPager!=''}">
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
	
	<h:form id="restart" style="display:none;">
		<e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>