<%@include file="_include.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="_includeScript.jsp"%>
<div class="pc">
<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}">

<script>
$(function() {
	<!-- Ne pas utiliser ${accountController.activ}), cela ne fonctionne pas en portlet-->
	<!-- Utiliser un tag jsf comme outputText-->
	<!-- Mettre cette partie de code après e:page, sinon outputText ne sera jamais evalué -->
	if(<t:outputText value="#{accountController.reinit}" />){progressBar(3);}
	if(<t:outputText value="#{accountController.activ || accountController.passwChange || accountController.loginChange}" />){progressBar(1);}
	
	
});
</script>



		<div class="container-fluid">
				<t:documentHead>
					<meta http-equiv="Expires" content="0">
					<meta http-equiv="cache-control" content="no-cache,no-store">
					<meta http-equiv="pragma" content="no-cache">
				</t:documentHead>
				<%@include file="_includeBreadcrumb.jsp"%>
				<%@include file="_includeProgessBar.jsp"%>
				<e:messages/>
				
				<e:paragraph value="#{msgs['PERSOINFO.TEXT.TOP']}" escape="false"/>
			
				<h:form id="accountForm" >
				<t:dataList value="#{accountController.beanData}" var="category">
				  <h:dataTable  value="#{category.profilingListBeanField}" rendered="#{category.access}" var="beanfield" columnClasses="firstColumn,secondColumn,thirdColumn"> 
					<h:column >						
					  <t:outputText styleClass="labeltext" value="#{msgs[beanfield.key]}" />
					</h:column>
					<h:column>
					<t:dataList value="#{beanfield.values}" var="sub">
						<t:div rendered="#{sub.value!=''&&!sub.convertedValue||(sub.value==''&&!beanfield.multiValue)}" styleClass="#{beanfield.name}show">
						    <h:inputText value="#{sub.value}"  disabled="#{beanfield.disable}" converter="#{beanfield.converter}" validator="#{beanfield.validator.validate}"  required="#{beanfield.required}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator!=null&&(sub.value!=''||(sub.value==''&&!beanfield.multiValue))}" immediate="true" valueChangeListener="#{sub.setValue}"/>
				            <h:inputText value="#{sub.value}"  disabled="#{beanfield.disable}" converter="#{beanfield.converter}" required="#{beanfield.required}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator==null&&(sub.value!=''&&!sub.convertedValue||(sub.value==''&&!beanfield.multiValue))}" immediate="true" valueChangeListener="#{sub.setValue}"/>
				            <h:selectOneMenu value="#{sub.value}" style="max-width:23em" rendered="#{beanfield.fieldType=='selectOneMenu'&&(sub.value!=''&&!sub.convertedValue||(sub.value==''&&!beanfield.multiValue))}" >
			                  <f:selectItems value="#{beanfield.displayItems}" />
			             	</h:selectOneMenu>    
				        </t:div>
				        	       	        
				        <t:div rendered="#{sub.value==''&&beanfield.multiValue}" style="display:none;" styleClass="#{beanfield.name}hide" >    
				            <h:inputText value="#{sub.value}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator!=null&&sub.value==''&&beanfield.multiValue}" immediate="true" valueChangeListener="#{sub.setValue}"/>
				            <h:inputText value="#{sub.value}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator==null&&sub.value==''&&beanfield.multiValue}" immediate="true" valueChangeListener="#{sub.setValue}"/>
				            <h:selectOneMenu value="#{sub.value}" style="max-width:23em" rendered="#{beanfield.fieldType=='selectOneMenu'&&sub.value==''&&beanfield.multiValue}" >
			                  <f:selectItems value="#{beanfield.displayItems}" />
			             	</h:selectOneMenu> 	            
			            </t:div>   		
			        </t:dataList>             
			        <t:div rendered="#{beanfield.fieldType=='selectManyCheckbox'}">             
			             	<h:selectManyCheckbox value="#{beanfield.selectedItems}" rendered="#{beanfield.fieldType=='selectManyCheckbox'}" validator="#{beanfield.validator.validate}" layout="pageDirection">
			                  <f:selectItems value="#{beanfield.displayItems}" />
			             	</h:selectManyCheckbox>        
			        </t:div> 
			        <t:div rendered="#{beanfield.fieldType=='selectOneRadio'}">        
			            	<h:selectOneRadio value="#{beanfield.value}">
			                  <f:selectItems value="#{beanfield.displayItems}" />
			        		</h:selectOneRadio>              
			         </t:div>   
			         <h:outputText styleClass="constraint" value="#{msgs[beanfield.constraint]}" rendered="#{beanfield.constraint!=null}"/>                 
			       		</h:column>  
			        	<h:column>
			        		<h:graphicImage title="#{msgs[beanfield.notice]}" value="/media/images/redtriangular.jpg"  style="border: 0;" rendered="#{!beanfield.updateable&&!beanfield.disable&&!accountController.viewDataChange}"/>
			       	 		<h:graphicImage title="#{msgs[beanfield.help]}" value="/media/images/help.jpg"  style="border: 0;" rendered="#{beanfield.help!=null&&!accountController.viewDataChange}"/>					  	
					  	<t:div >
						  <h:graphicImage alt="#{beanfield.name}" styleClass="show" value="/media/images/add.png"  style="border: 0;" rendered="#{beanfield.multiValue&&beanfield.fieldType=='inputText'&&(!beanfield.disable)}"/>
						  <h:graphicImage alt="#{beanfield.name}" styleClass="hide" value="/media/images/remove.png"  style="border: 0;" rendered="#{beanfield.multiValue&&beanfield.fieldType=='inputText'&&(!beanfield.disable)}"/>
					  	</t:div>
						</h:column>										
				  	</h:dataTable>
				</t:dataList>										
				<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChangeInfoPersonal}"  id="application" style="display:none;"/>
				</h:form>
				<button  class="btn btn-primary" onclick="simulateLinkClick('accountForm:application');"><span class="glyphicon glyphicon-ok"></span><h:outputText value="#{msgs['_.BUTTON.CONFIRM']}" /></button>
				
				<h:form id="restart" style="display:none;">
					<e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}"
						action="#{exceptionController.restart}" />
				</h:form>
		</div>	
</e:page>
</div>