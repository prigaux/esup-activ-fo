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
				<!-- Barre de progression pour la réinitialisation de mot de passe-->
				<%@include file="_includeProgessBar.jsp"%>
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
					    <t:graphicImage title="#{msgs[beanCode.help]}"	 value="/media/images/help.jpg"  style="border: 0;" rendered="#{beanCode.help!=null}"/>
					    <e:message for="code" />	
					</e:panelGrid>
					<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" id="application" action="#{accountController.pushVerifyCode}" style="display:none;"/>
				</h:form>
				<button  class="btn btn-primary" onclick="simulateLinkClick('accountForm:application');"><span class="glyphicon glyphicon-ok"></span><h:outputText value="#{msgs['_.BUTTON.CONFIRM']}" /></button>
				
				<h:form id="restart" style="display:none;">
					<e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}"
						action="#{exceptionController.restart}" />
				</h:form>
			</div>
	</e:page>
</div>