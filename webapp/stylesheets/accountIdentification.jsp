<%@include file="_include.jsp"%>



<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}" >

	<%-- Data mustn't be recorded in this form, even by using back button --%> 
	<t:documentHead>
		<meta http-equiv="Expires" content="0">
		<meta http-equiv="cache-control" content="no-cache,no-store">
		<meta http-equiv="pragma" content="no-cache">
	</t:documentHead>

	<%@include file="_navigation.jsp"%>
	<e:section value="#{msgs['IDENTIFICATION.ACTIVATION.TITLE']}"rendered="#{accountController.activ == true}" />
	<e:section value="#{msgs['IDENTIFICATION.REINITIALISATION.TITLE']}"rendered="#{accountController.reinit == true}" />
	<e:section value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.TITLE']}"rendered="#{accountController.passwChange == true}" />
	
	<e:messages/>
	
	


	<h:form id="accountForm" rendered="#{accountController.currentAccount.activated == false}">
	
	
		<e:paragraph  escape="false" value="#{msgs['IDENTIFICATION.TEXT.TOP']}" />
		
		<t:dataList value="#{accountController.listInfoToValidate}" var="entry"> 
		
			<e:panelGrid columns="4" columnClasses="col1,col2,col3" rendered="#{entry.value!=null}">
			
				<e:outputLabel value="#{msgs[entry.key]}" />
				<e:inputText value="#{entry.value}"  required="#{entry.required}" size="25" validator="#{entry.validator.validate}" converter="#{entry.converter}" rendered="#{entry.converter!=null&&entry.validator!=null}"/>
				<e:inputText value="#{entry.value}"  required="#{entry.required}" size="25" validator="#{entry.validator.validate}"  rendered="#{entry.converter==null&&entry.validator!=null}"/>
				<e:inputText value="#{entry.value}"  required="#{entry.required}" size="25"  rendered="#{entry.converter==null&&entry.validator==null}"/>
				
				<h:outputLink  styleClass="help" value="#"  rendered="#{entry.help!=null}">
					<h:graphicImage url="../media/help.jpg"  style="border: 0;"/>
					<h:outputText id="help" value="#{msgs[entry.help]}"/>
				</h:outputLink>
						
			</e:panelGrid>
		</t:dataList>
		
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




