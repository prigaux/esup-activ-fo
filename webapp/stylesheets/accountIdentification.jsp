<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>
<div class="pc">
<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}" >
		<div class="container-fluid">
		<%-- Data mustn't be recorded in this form, even by using back button --%> 
			<t:documentHead>
				<meta http-equiv="Expires" content="0">
				<meta http-equiv="cache-control" content="no-cache,no-store">
				<meta http-equiv="pragma" content="no-cache">
			</t:documentHead>
			
			<e:section value="#{msgs['IDENTIFICATION.ACTIVATION.TITLE']}"rendered="#{accountController.activ == true}" />
			<e:section value="#{msgs['IDENTIFICATION.REINITIALISATION.TITLE']}"rendered="#{accountController.reinit == true}" />
			<e:section value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.TITLE']}"rendered="#{accountController.passwChange == true}" />
			
			<!-- Barre de progression pour la réinitialisation de mot de passe-->
			<%@include file="_includeProgessBar.jsp"%>
			<%@include file="_includeMessage.jsp"%>
			
			<div class="row">
				<div class="col-md-12">
					
					<h:form id="accountForm" rendered="#{accountController.currentAccount.activated == false}">
					
						<e:paragraph  escape="false" value="#{msgs['IDENTIFICATION.TEXT.TOP']}" />
					
						<h:dataTable value="#{accountController.listInfoToValidateRequired}" var="entry"  columnClasses="firstColumn,secondColumn,thirdColumn"> 
						 	<h:column>						  					
								<t:outputText styleClass="labeltext"  value="#{msgs[entry.key]}" />
							</h:column>
							<h:column>	
							 <t:div>					
								<h:inputText id="mess1" value="#{entry.value}"  required="#{entry.required}" validator="#{entry.validator.validate}" converter="#{entry.converter}" rendered="#{entry.converter!=null&&entry.validator!=null&&entry.fieldType=='inputText'}"/>
								<h:inputText id="mess2" value="#{entry.value}"  required="#{entry.required}" converter="#{entry.converter}" rendered="#{entry.converter!=null&&entry.validator==null&&entry.fieldType=='inputText'}"/>
								<h:inputText id="mess3" value="#{entry.value}"  required="#{entry.required}" validator="#{entry.validator.validate}"  rendered="#{entry.converter==null&&entry.validator!=null&&entry.fieldType=='inputText'}"/>	
								<h:inputText id="mess4" value="#{entry.value}"  required="#{entry.required}" rendered="#{entry.converter==null&&entry.validator==null&&entry.fieldType=='inputText'}"/>
								<h:selectOneMenu value="#{entry.value}" rendered="#{entry.fieldType=='selectOneMenu'}" valueChangeListener="#{entry.changeValue}" immediate="true">
					                 <f:selectItems value="#{entry.displayItems}" />
					            	</h:selectOneMenu>
					         	 </t:div>
					            <h:outputText styleClass="constraint" value="#{msgs[entry.constraint]}" rendered="#{entry.constraint!=null}"/>
							</h:column>
							<h:column>									
								<t:graphicImage title="#{msgs[entry.help]}"	 value="/media/images/help.jpg"  style="border: 0;" rendered="#{entry.help!=null}"/>
							</h:column>	
							<h:column>									
								<e:message for="mess1" />
								<e:message for="mess2" />
								<e:message for="mess3" />
								<e:message for="mess4" />
							</h:column>													
						</h:dataTable>
								
						<t:div style="margin-top:1em;">
							<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushValid}" id="application" style="display:none;"/>
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
</div>