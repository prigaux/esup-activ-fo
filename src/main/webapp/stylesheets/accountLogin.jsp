<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}" >
<%@include file="_includeScript.jsp"%>
<script>
$(function() {
	progressBar(2);	
});
</script>
<div class="pc">
		<div class="container-fluid">
		
			<%@include file="_includeBreadcrumb.jsp"%>
			<%@include file="_includeProgessBar.jsp"%>
			<%@include file="_includeMessage.jsp"%>
			<br/>
			<br/>
			
			<e:paragraph escape="false" value="#{msgs['LOGIN.TEXT.TOP']}" rendered="#{accountController.activ == true}"/>
		
			<h:form id="accountForm" >				
				<e:panelGrid columns="4">
					<e:outputLabel for="newlogin" value="#{msgs[beanNewLogin.key]}" />
					<e:inputText id="login" value="#{beanNewLogin.value}" required="#{beanNewLogin.required}" validator="#{beanNewLogin.validator.validate}" converter="#{beanNewLogin.converter}">
					</e:inputText>
					<t:graphicImage styleClass="toolTipShow" title="#{msgs[beanNewLogin.help]}"	 value="/media/images/help.jpg"  style="border: 0;" rendered="#{beanNewLogin.help!=null}"/>
					<e:message for="login" />
				</e:panelGrid>								
				<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushLogin}" style="display:none;" id="application"/>
			</h:form>
			<button  class="btn btn-primary" onclick="simulateLinkClick('accountForm:application');"><span class="glyphicon glyphicon-ok"></span><h:outputText value="#{msgs['_.BUTTON.CONFIRM']}" /></button>
		
			<h:form id="restart" style="display:none;">
				<e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
			</h:form>
		</div>
</div>
</e:page>
