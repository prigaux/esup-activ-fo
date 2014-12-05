<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>

<script>
$(function() {
	progressBar(2);	
});
</script>
<div class="pc">
	<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}">
		<div class="container-fluid">	
				<%@include file="_includeBreadcrumb.jsp"%>
				<%@include file="_includeProgessBar.jsp"%>
				<e:messages />
				
				<e:paragraph escape="false" value="#{msgs['CHARTER.TEXT.TOP']}" />
			
				<h:form id="accountForm">
					<e:outputLabel for="charterAgreement" value="#{msgs['CHARTER.TEXT.AGREE']}" />
					<e:selectBooleanCheckbox id="charterAgreement" value="#{accountController.currentAccount.charterAgreement}" required="true" />
					<t:div style="margin-top:1em;">
						<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushCharterAgreement}" id="application" style="display:none;"/>
						<button  class="btn btn-primary" onclick="simulateLinkClick('accountForm:application');"><span class="glyphicon glyphicon-ok"></span><h:outputText value="#{msgs['_.BUTTON.CONFIRM']}" /></button>
					</t:div>
				</h:form>
				
				<h:form id="restart" style="display:none;">
					<e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
				</h:form>
		</div>
	</e:page>
</div>