<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>

<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}" >

	<%-- Data mustn't be recorded in this form, even by using back button --%> 
	<t:documentHead>
		<meta http-equiv="Expires" content="0">
		<meta http-equiv="cache-control" content="no-cache,no-store">
		<meta http-equiv="pragma" content="no-cache">
	</t:documentHead>
	
	<e:section value="#{msgs['IDENTIFICATION.ACTIVATION.TITLE']}"rendered="#{accountController.activ == true}" />
	<e:section value="#{msgs['IDENTIFICATION.REINITIALISATION.TITLE']}"rendered="#{accountController.reinit == true}" />
	<e:section value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.TITLE']}"rendered="#{accountController.passwChange == true}" />
	
	<t:div styleClass="firstStepImage" rendered="#{accountController.activ == true}">
	<t:htmlTag styleClass="processSteps" value="ul">
	<t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Acceuil" value="/media/images/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
	<t:htmlTag styleClass="currentTab" value="li"><t:outputText escape="false" value="#{msgs['ACTIVATION.COMPTE.ETAPE1.TEXT']}" /></t:htmlTag>
	
	<t:htmlTag styleClass="secondStep" value="li">
	<t:outputText escape="false" value="#{msgs['ACTIVATION.COMPTE.ETAPE2.TEXT']}"/>
	</t:htmlTag>
	
	<t:htmlTag styleClass="thirdStep" value="li">
	<t:outputText escape="false" value="#{msgs['ACTIVATION.COMPTE.ETAPE3.TEXT']}"/>
	</t:htmlTag>
	
	<t:htmlTag styleClass="fourthStep" value="li">
	<t:outputText escape="false" value="#{msgs['ACTIVATION.COMPTE.ETAPE4.TEXT']}"/>
	</t:htmlTag>
	
	</t:htmlTag>
	</t:div>
	
	<t:div styleClass="firstStepImage5fleches" rendered="#{accountController.reinit == true}">
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Accueil" value="/media/images/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
		<t:htmlTag styleClass="currentTab" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE1.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="secondStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE2.TEXT']}" /></t:htmlTag>
		<t:htmlTag styleClass="thirdStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE3.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="fourthStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE4.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="fifthStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE5.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>


	<%@include file="_includeMessage.jsp"%>

	<h:form id="accountForm" rendered="#{accountController.currentAccount.activated == false}">
	
		<e:paragraph  escape="false" value="#{msgs['IDENTIFICATION.TEXT.TOP']}" />
	
		<h:dataTable value="#{accountController.listInfoToValidateRequired}" var="entry"  columnClasses="firstColumn,secondColumn,thirdColumn"> 
		 	<h:column>						  					
				<t:outputText styleClass="labeltext"  value="#{msgs[entry.key]}" />
			</h:column>
			<h:column>	
			 <t:div>					
				<h:inputText id="mess1" value="#{entry.value}"  required="#{entry.required}" validator="#{entry.validator.validate}" converter="#{entry.converter}" rendered="#{entry.converter!=null&&entry.validator!=null&&entry.fieldType=='inputText'}"/>
				<h:inputText id="mess2" value="#{entry.value}"  required="#{entry.required}" converter="#{entry.converter}" rendered="#{entry.converter!=null&&entry.validator==null&&entry.fieldType=='inputText'}"/>
				<h:inputText id="mess3" value="#{entry.value}"  required="#{entry.required}" validator="#{entry.validator.validate}"  rendered="#{entry.converter==null&&entry.validator!=null&&entry.fieldType=='inputText'}"/>	
				<h:inputText id="mess4" value="#{entry.value}"  required="#{entry.required}" rendered="#{entry.converter==null&&entry.validator==null&&entry.fieldType=='inputText'}"/>
				<h:selectOneMenu value="#{entry.value}" rendered="#{entry.fieldType=='selectOneMenu'}" valueChangeListener="#{entry.changeValue}" immediate="true">
                  <f:selectItems value="#{entry.displayItems}" />
             	</h:selectOneMenu>
          	 </t:div>
             <h:outputText styleClass="constraint" value="#{msgs[entry.constraint]}" rendered="#{entry.constraint!=null}"/>
			</h:column>
			<h:column>									
				<t:graphicImage styleClass="helpTip" longdesc="#{msgs[entry.help]}" value="/media/images/help.jpg"  style="border: 0;" rendered="#{entry.help!=null}"/>
			</h:column>	
			<h:column>									
				<e:message for="mess1" />
				<e:message for="mess2" />
				<e:message for="mess3" />
				<e:message for="mess4" />
			</h:column>													
		</h:dataTable>
				
		<t:div style="margin-top:1em;">
			<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushValid}" />
		</t:div>
		
		
		     	        
        
	
	</h:form>
	
	<h:form id="restart" style="display:none;">
		<e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>
	
<% /* @include file="_debug.jsp" */ %>
</e:page>




