<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}" >
<%@include file="_includeScript.jsp"%>
<div class="pc">
		<div class="container-fluid">
			<%@include file="_includeBreadcrumb.jsp"%>
			<t:div rendered="#{accountController.activ == true}">
				<t:div styleClass="alert alert-success"><t:outputText  value="#{msgs['ENABLED.TITLE']}"/></t:div>
			</t:div>
			<t:div rendered="#{accountController.reinit == true}">
				<t:div styleClass="alert alert-success"><t:outputText  value="#{msgs['REINITIALIZED.TITLE']}"/></t:div>
			</t:div>
			<t:div rendered="#{accountController.passwChange == true}">
				<t:div styleClass="alert alert-success"><t:outputText  value="#{msgs['PASSWORDCHANGED.TITLE']}"/></t:div>
			</t:div>
			<t:div rendered="#{accountController.loginChange == true}">
				<t:div styleClass="alert alert-success"><t:outputText  value="#{msgs['LOGINCHANGED.TITLE']}"/></t:div>
			</t:div>
			
			<div>
				<e:messages />
				<e:paragraph escape="false" value="#{msgs['ENABLED.TEXT.UNAUTHENTICATED']}">
				<f:param value="#{accountController.currentAccount.id}" />
				<f:param value="#{accountController.currentAccount.mail}" />
				</e:paragraph>
			</div>
			<div style="margin-top:30px">
				<e:paragraph escape="false" value="Accès au portail #{msgs['ENABLED.TEXT.ESUPURL']}" />
			</div>
			
			<h:form id="restart">
				<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" id="restartButton" style="display:none;"/>
			</h:form>	 
		</div>
</div>
</e:page>
