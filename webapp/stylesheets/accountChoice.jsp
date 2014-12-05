<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>
<script>
$(function() {
	progressBar(1);	
});
</script>

<div class="pc">
	<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}">
			<div class="container-fluid">
				<%-- Data mustn't be recorded in this form, even by using back button --%> 
				<t:documentHead>
					<meta http-equiv="Expires" content="0">
					<meta http-equiv="cache-control" content="no-cache,no-store">
					<meta http-equiv="pragma" content="no-cache">
				</t:documentHead>
				
				<%@include file="_includeBreadcrumb.jsp"%>
				<%@include file="_includeProgessBar.jsp"%>
				<e:messages />
				<t:dataList value="#{accountController.availableChannels}" var="entry">
					<e:paragraph escape="false" value="#{msgs[entry.homeMsg]}">
						<f:param value="#{entry.paramMsg}" />
					</e:paragraph>			
				</t:dataList>
				<e:paragraph escape="false" value="#{msgs['CHANNEL.HOME.TEXT']}"/>
					
				<h:form id="accountForm" >										
					<t:div>
						<t:selectOneRadio required="true" value="#{accountController.oneChoiceCanal}">
				 			<t:selectItems value="#{accountController.listBeanCanal}" var="entry" itemLabel="#{msgs[entry.key]}" itemValue="#{entry.value}"></t:selectItems>
						</t:selectOneRadio>	
					</t:div>
					
					<t:div style="margin-top:1em;">
						<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" id="application" action="#{accountController.pushChoice}" style="display:none;"/>
						<button  class="btn btn-primary" onclick="simulateLinkClick('accountForm:application');"><span class="glyphicon glyphicon-ok"></span><h:outputText value="#{msgs['_.BUTTON.CONFIRM']}" /></button>
					</t:div>
				</h:form>
				
				<h:form id="restart" style="display:none;">
					<e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}"
						action="#{exceptionController.restart}" />
				</h:form>
			</div>
	</e:page>
</div>