<%@include file="_include.jsp"%>


<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}" >

<%-- Data mustn't be recorded in this form, even by using back button --%> 
<t:documentHead>
<meta http-equiv="Expires" content="0">
<meta http-equiv="cache-control" content="no-cache,no-store">
<meta http-equiv="pragma" content="no-cache">
</t:documentHead>

	<%@include file="_navigation.jsp"%>
	<e:section value="#{msgs['ACTIVATION.TITLE']}" />
	
	<e:messages />
	
	<e:text escape="false" value="#{msgs['ACTIVATION.TEXT.SUPPORTADRESS']}" rendered="#{accountController.currentAccount.activated}"/>



	<h:form id="activationForm" rendered="#{accountController.currentAccount.activated == false}">
	
	
		<e:paragraph value="#{msgs['ACTIVATION.TEXT.TOP']}" />
		<e:panelGrid columns="3" >
			<e:outputLabel for="harpegeNumber"
				value="#{msgs['ACTIVATION.TEXT.HARPEGENUMBER']}" />
			<e:inputText id="harpegeNumber" value="#{accountController.currentAccount.harpegeNumber}" required="true" validator="#{validator.validateHarpegeNumber}"/>
			<e:message for="harpegeNumber" /> 
				
			<e:outputLabel for="birthName" 
				value="#{msgs['ACTIVATION.TEXT.PATRONYMIC']}" />
			<e:inputText id="birthName" value="#{accountController.currentAccount.birthName}" required="true" >
			</e:inputText>
			<e:message for="birthName" />	


			<e:outputLabel for="birthDate" 
				value="#{msgs['ACTIVATION.TEXT.BIRTHDATE']}" />
			<t:inputCalendar  id="birthDate" monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" currentDayCellClass="currentDayCell" 
			renderAsPopup="true" value="#{accountController.currentAccount.birthDate}" required="true" 
			popupDateFormat="dd/MM/yyyy" renderPopupButtonAsImage="true" popupTodayString="#{msgs['_.DAY.TODAY']}" popupWeekString="#{msgs['_.DAY.WEEK']}">
				<f:convertDateTime pattern="d/M/yyyy" />
			</t:inputCalendar>
			<e:message for="birthDate" />
				
		</e:panelGrid>
		<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushValid}" />
	</h:form>
	
	<h:form>
		<e:commandButton value="#{msgs['ACTIVATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>
	
<% /* @include file="_debug.jsp" */ %>
</e:page>