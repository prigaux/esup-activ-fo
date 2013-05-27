<%@include file="_include.jsp"%>

<e:page stringsVar="msgs" menuItem="account"
	locale="#{sessionController.locale}">
	<%@include file="_includeScript.jsp"%>
	<%@include file="_navigation.jsp"%>
	<e:section value="#{msgs['IDENTIFICATION.REINITIALISATION.TITLE']}" />
	
	<t:div styleClass="thirdStepImage5fleches" rendered="#{accountController.reinit == true}">
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Accueil" value="/media/images/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
		<t:htmlTag styleClass="firstStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE1.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="secondStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE2.TEXT']}" /></t:htmlTag>
		<t:htmlTag styleClass="currentTab" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE3.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="fourthStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE4.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="fifthStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE5.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>
	
	<%@include file="_includeMessage.jsp"%>
					
	<e:paragraph escape="false" value="#{msgs[accountController.sentChannel.codeMsg]}">
		<f:param value="#{accountController.sentChannel.paramMsg}" />
	</e:paragraph>
		 	
	<e:paragraph escape="false" value="#{msgs['CHANNEL.SENT.TEXT']}"/>
		  	
	<h:form id="accountForm" >
		<e:panelGrid columns="4">
			<e:outputLabel for="code" value="#{msgs[beanCode.key]}" />
			<e:inputText id="code" value="#{beanCode.value}" required="#{beanCode.required}" validator="#{beanCode.validator.validate}">
			</e:inputText>
		    <h:graphicImage styleClass="helpTip" longdesc="#{msgs[beanCode.help]}" value="/media/images/help.jpg"  style="border: 0;" rendered="#{beanCode.help!=null}"/>
		    <e:message for="code" />	
		</e:panelGrid>
		
		<t:div style="margin-top:1em;">
			<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushVerifyCode}" />
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