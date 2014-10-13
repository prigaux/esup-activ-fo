<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>
<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}" >
<div class="pc">
		<div class="container-fluid">	
	
			<%-- Data mustn't be recorded in this form, even by using back button --%> 
			<t:documentHead>
				<meta http-equiv="Expires" content="0">
				<meta http-equiv="cache-control" content="no-cache,no-store">
				<meta http-equiv="pragma" content="no-cache">		
			</t:documentHead>
			
			<e:section value="#{msgs['PASSWORD.PASSWORDCHANGE.TITLE']}" rendered="#{accountController.passwChange == true}"/>
			<e:section value="#{msgs['AUTHENTIFICATION.LOGINCHANGE.TITLE']}" rendered="#{accountController.loginChange == true}"/>
			<e:section value="#{msgs['DATACHANGE.DATACHANGE.TITLE']}" rendered="#{accountController.dataChange == true}"/>
			
			<%@include file="_includeProgessBar.jsp"%>
			<%@include file="_includeMessage.jsp"%>
			 
			<e:paragraph escape="false" value="#{msgs['AUTHENTIFICATION.TEXT.TOP']}" />
			<h:form id="accountForm" >		
				<e:panelGrid columns="4">
					<e:outputLabel for="login" value="#{msgs[beanLogin.key]}" />
					<e:inputText id="login" value="#{beanLogin.value}" required="#{beanLogin.required}" > </e:inputText>
					<t:graphicImage styleClass="helpTip" longdesc="#{msgs[beanLogin.help]}" value="/media/images/help.jpg"  style="border: 0;" rendered="#{beanLogin.help!=null}"/>
					<t:htmlTag value="span" rendered="#{beanLogin.help==null}"/>
					<e:message for="login" />
		
					<e:outputLabel for="password" value="#{msgs[beanPassword.key]}" />
					<e:inputSecret id="password" value="#{beanPassword.value}" required="#{beanPassword.required}" validator="#{beanPassword.validator.validate}"> </e:inputSecret> 
					<h:graphicImage styleClass="helpTip" longdesc="#{msgs[beanPassword.help]}" value="/media/images/help.jpg"  style="border: 0;" rendered="#{beanPassword.help!=null}"/>
					<t:htmlTag value="span" rendered="#{beanLogin.help==null}"/>
					<e:message for="password" />
				</e:panelGrid>
				
				<t:div style="margin-top:1em;">
					<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushAuthentificate}" id="application" style="display:none;"/>
					<button  class="btn btn-primary" onclick="simulateLinkClick('accountForm:application');"><span class="glyphicon glyphicon-ok"></span><h:outputText value="#{msgs['_.BUTTON.CONFIRM']}" /></button>
				</t:div>
			</h:form>
			
			<h:form id="restart" style="display:none;">
				<e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}"
					action="#{exceptionController.restart}" />
			</h:form>
		</div>
	</div>
</e:page>