<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>
<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}" >
	<div class="pc">
			<div class="container-fluid">
			
				<t:div rendered="#{accountController.activ == true}">
					<e:section value="#{msgs['ENABLED.TITLE']}" />
					<e:messages />
					<e:paragraph value="#{msgs['ENABLED.TEXT.TOP']}" />
			
			
					<e:paragraph escape="false" value="#{msgs['ENABLED.TEXT.UNAUTHENTICATED']}">
					  <f:param value="#{accountController.currentAccount.id}" />
					  <f:param value="#{accountController.currentAccount.mail}" />
					</e:paragraph>
				</t:div>
				<t:div rendered="#{accountController.reinit == true}">
					<e:section value="#{msgs['REINITIALIZED.TITLE']}" />
					<e:messages />
					<e:paragraph value="#{msgs['REINITIALIZED.TEXT.TOP']}" />
			
					<e:paragraph escape="false" value="#{msgs['ENABLED.TEXT.UNAUTHENTICATED']}">
					  <f:param value="#{accountController.currentAccount.id}" />
					  <f:param value="#{accountController.currentAccount.mail}" />
					 </e:paragraph>
				 </t:div>
				 
				
				<t:div rendered="#{accountController.passwChange == true}">
					<e:section value="#{msgs['PASSWORDCHANGED.TITLE']}" />
					<e:messages />
					<e:paragraph value="#{msgs['PASSWORDCHANGED.TEXT.TOP']}" />
			
					<e:paragraph escape="false" value="#{msgs['ENABLED.TEXT.UNAUTHENTICATED']}">
					  <f:param value="#{accountController.currentAccount.id}" />
					  <f:param value="#{accountController.currentAccount.mail}" />
					 </e:paragraph>
				 </t:div>
				
				<t:div rendered="#{accountController.loginChange == true}">
					<e:section value="#{msgs['LOGINCHANGED.TITLE']}" />
					<e:messages />
					<e:paragraph value="#{msgs['LOGINCHANGED.TEXT.TOP']}" />
			
					<e:paragraph escape="false" value="#{msgs['ENABLED.TEXT.UNAUTHENTICATED']}">
					  <f:param value="#{accountController.currentAccount.id}" />
					  <f:param value="#{accountController.currentAccount.mail}" />
					 </e:paragraph>
				 </t:div>
					 	 
				 <e:subSection value="#{msgs['ENABLED.SUBTITLE.ESUPACCESS']}" />
				 <e:paragraph escape="false" value="#{msgs['ENABLED.TEXT.ESUPURL']}" />
				 <h:form id="accountForm">
					<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}"action="#{exceptionController.restart}" id="application" style="display:none;"/>
					<button  class="btn btn-primary" onclick="simulateLinkClick('accountForm:application');"><span class="glyphicon glyphicon-ok"></span><h:outputText value="#{msgs['APPLICATION.BUTTON.RESTART']}" /></button>
				</h:form>
		 
		</div>
	</div>
</e:page>