<%@include file="_include.jsp"%>



<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}" >

<%-- Data mustn't be recorded in this form, even by using back button --%> 
<t:documentHead>
<meta http-equiv="Expires" content="0">
<meta http-equiv="cache-control" content="no-cache,no-store">
<meta http-equiv="pragma" content="no-cache">
</t:documentHead>

	<%@include file="_navigation.jsp"%>
	<e:section value="#{msgs['IDENTIFICATION.ACTIVATION.TITLE']}"rendered="#{accountController.reinit == false}" />
	<e:section value="#{msgs['IDENTIFICATION.REINITIALISATION.TITLE']}"rendered="#{accountController.reinit == true}" />
	<e:messages />
	
	<e:text escape="false" value="#{msgs['IDENTIFICATION.TEXT.SUPPORTADRESS']}" rendered="#{accountController.currentAccount.activated}"/>


	<h:form id="activationForm" rendered="#{accountController.currentAccount.activated == false}">
	
	
	<e:paragraph value="#{msgs['IDENTIFICATION.TEXT.TOP']}" />
		
	<t:dataList value="#{accountController.listInfoToValidate}" var="entry"> 
		<e:panelGrid>
			<t:div rendered="#{entry.value!=null}" >
				<h:outputLabel value="#{msgs[entry.key]}" />
				<h:inputText value="#{entry.value}"  required="true" size="25" validator="#{entry.validator.validate}" converter="#{ldapDateConverter}" rendered="#{entry.converter!=null}"/>
				<h:inputText value="#{entry.value}"  required="true" size="25" validator="#{entry.validator.validate}"  rendered="#{entry.converter==null}"/>
				
				<h:outputLink id="rolloverImage" value="#" onclick="drawAlert('#{entry.aide}')" rendered="#{entry.aide!=null}">
					<h:graphicImage id="w3c" url="../media/aide.jpg"  style="border: 0;"/>
					<h:outputText id="aide" value="#{msgs[entry.aide]}"/>
				</h:outputLink>
			</t:div>
		</e:panelGrid>
	</t:dataList>
		
		
		<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushValid}" />
	</h:form>
	
	<h:form>
		<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>
	
<% /* @include file="_debug.jsp" */ %>
</e:page>

<script type="text/javascript" language="javascript">
function drawAlert(msg)
{
    alert (msg);
}
</script>


