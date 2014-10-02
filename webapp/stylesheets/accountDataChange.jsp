<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>
<script type="text/javascript" src="/media/scripts/accountDataTabs.js"></script>

<script>
$(function() {
	// Afficher par défaut l'onglet modifier les données
	$('.nav-pills li:eq(1) a').tab('show');
	
});
</script>

<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}">
<div class="pc md">
<div class="container-fluid">
		<!-- <e:section value="#{msgs['DATACHANGE.DATACHANGE.TITLE']}" /> -->
	
	
	<h:form id="accountForm" enctype="multipart/form-data">
		<%@include file="_includeAccountData.jsp"%>	
			<div class="col-md-9">
				<div class="tab-content">
					<!-- Afficher message d'erreur -->
					 <e:messages />
					 
			   		<!-- Appeler la méthode getBeanData qui récupère les données, pour afficher la civilité et le displayName
					car avant cette méthode, aucune données n'est récupérées
					 -->				
	   				<t:outputText value="#{accountController.beanData}" style="display:none"></t:outputText>
					<div class="page-header"><h:outputText style="text-transform : capitalize;"value="#{accountController.currentAccount.supannCivilite} #{accountController.currentAccount.displayName}" /></div>
	    
				  	<t:dataList value="#{accountController.beanData}"  var="category" >
						<t:div styleClass="hrefIdDetail mainModifyLinkByCategory tab-pane" rendered="#{category.access}">
								<t:htmlTag value="table">
									<t:htmlTag value="tr">
										<t:htmlTag value="td" styleClass="columnData">
											<h:dataTable value="#{category.profilingListBeanField}"	rendered="#{category.access}" var="beanfield" columnClasses="firstColumn,secondColumn,thirdColumn,fourthColumn">
												<h:column>
													<t:outputText styleClass="#{beanfield.name} labeltexttop#{beanfield.size>1} portlet-section-text"	value="#{msgs[beanfield.key]}"	rendered="#{beanfield.fieldType!='inputFileUpload'}" />
													<t:div rendered="#{beanfield.fieldType=='inputFileUpload'}">
														<h:graphicImage value="#{beanfield.value}" width="100px"height="100px" styleClass="photo" />
													</t:div>			
												</h:column>
												<h:column>
													<t:dataList value="#{beanfield.values}" var="sub" >
														<!----------------- En affichage -->
														<t:div styleClass="#{beanfield.name}output portlet-section-text" rendered="#{!beanfield.disable&&beanfield.fieldType!='selectManyCheckbox'&&beanfield.fieldType!='selectOneRadio'&&beanfield.fieldType!='inputFileUpload'&&!sub.convertedValue}">
															<h:outputText value="#{sub.value}" rendered="#{beanfield.fieldType!='selectManyCheckbox'&&beanfield.fieldType!='selectOneRadio'&&beanfield.fieldType!='inputFileUpload'}" converter="#{beanfield.converter}"/>
														</t:div>
														<!--griser les champs non modifable  -->
														<t:div styleClass="legendlabel" rendered="#{beanfield.disable&&beanfield.fieldType!='selectManyCheckbox'&&beanfield.fieldType!='selectOneRadio'&&beanfield.fieldType!='inputFileUpload'&&!sub.convertedValue}">
															<h:outputText value="#{sub.value}" rendered="#{beanfield.fieldType!='selectManyCheckbox'&&beanfield.fieldType!='selectOneRadio'&&beanfield.fieldType!='inputFileUpload'}" converter="#{beanfield.converter}"/>
														</t:div>
														
														<!-- ---------------En modification -->
														<!--* Champs avec valeur non vide -->       	    
														<t:div rendered="#{sub.value!=''&&!sub.convertedValue||(sub.value==''&&!beanfield.multiValue)}" style="display:none;" styleClass="#{beanfield.name}show portlet-section-text">
														   <!-- Avec validator -->
														    <h:inputText value="#{sub.value}"  disabled="#{beanfield.disable}" converter="#{beanfield.converter}" required="#{beanfield.required}" size="35" validator="#{beanfield.validator.validate}"  
														    	rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator!=null&&(sub.value!=''||(sub.value==''&&!beanfield.multiValue))}" immediate="true" valueChangeListener="#{sub.setValue}"/>
												            <!-- Sans validator -->
												            <h:inputText value="#{sub.value}"  disabled="#{beanfield.disable}" converter="#{beanfield.converter}" required="#{beanfield.required}" size="35" 
												            	rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator==null&&(sub.value!=''&&!sub.convertedValue||(sub.value==''&&!beanfield.multiValue))}" immediate="true" valueChangeListener="#{sub.setValue}"/>
												            <!--Liste déroulante  -->
												            <h:selectOneMenu value="#{sub.value}" style="max-width:23em" rendered="#{beanfield.fieldType=='selectOneMenu'&&(sub.value!=''&&!sub.convertedValue||(sub.value==''&&!beanfield.multiValue))}" >
											                  <f:selectItems value="#{beanfield.displayItems}" />
											             	</h:selectOneMenu>               	             	             	  
												        </t:div>    	    
															       	    
															       	    
													    <!--* Champs avec valeur vide -->		       	        
														<t:div rendered="#{sub.value==''&&beanfield.multiValue}" style="display:none;" styleClass="#{beanfield.name}hide portlet-section-text" >  
															<h:inputText value="#{sub.value}" size="35" converter="#{beanfield.converter}" validator="#{beanfield.validator.validate}"  rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator!=null&&sub.value==''&&beanfield.multiValue}" immediate="true" valueChangeListener="#{sub.setValue}"/>
															 <h:inputText value="#{sub.value}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator==null&&sub.value==''&&beanfield.multiValue}" immediate="true" valueChangeListener="#{sub.setValue}"/>
															
															 <h:selectOneMenu value="#{sub.value}" style="max-width:23em" rendered="#{beanfield.fieldType=='selectOneMenu'&&sub.value==''&&beanfield.multiValue}" >
														     	 <f:selectItems value="#{beanfield.displayItems}" />
														 	  </h:selectOneMenu> 	            
														</t:div>          
													</t:dataList>
													<!-- Modifier la photo -->
													<t:div rendered="#{beanfield.fieldType=='inputFileUpload'}"	style="display:none;" styleClass="#{beanfield.name}show portlet-section-text" >
														<t:inputFileUpload value="#{beanfield.fileUpLoad}"	styleClass="upload" storage="file" accept="image/jpeg"></t:inputFileUpload>
														<h:graphicImage alt="#{beanfield.name}" styleClass="delete"	value="/media/images/delete.png" style="float:right;margin:0;"	rendered="#{beanfield.fieldType=='inputFileUpload'&&beanfield.deleteJpegPhoto==1}" />
														<h:inputText value="#{beanfield.deleteJpegPhoto}" styleClass="deletePhoto" style="display:none;" />
													</t:div>
													
													<!--Afficher la données sous forme de checkbox  -->
													<t:div rendered="#{beanfield.fieldType=='selectManyCheckbox'}" >
														<h:selectManyCheckbox value="#{beanfield.selectedItems}" styleClass="portlet-section-text" rendered="#{beanfield.fieldType=='selectManyCheckbox'}" validator="#{beanfield.validator.validate}" layout="pageDirection">
															<f:selectItems value="#{beanfield.displayItems}" />
														</h:selectManyCheckbox>
													</t:div>
													<!--Afficher la données sous forme de radio bouton  -->
													<t:div rendered="#{beanfield.fieldType=='selectOneRadio'}"  >
														<h:selectOneRadio value="#{beanfield.value}" styleClass="portlet-section-text" rendered="#{beanfield.fieldType=='selectOneRadio'}">
															<f:selectItems value="#{beanfield.displayItems}" />
														</h:selectOneRadio>
													</t:div>
													
													
													<!--Afficher les messages de contrainte ( en gris)  -->
													<h:outputText style="display:none" styleClass="#{beanfield.name}constraint constraint" value="#{msgs[beanfield.constraint]}" rendered="#{beanfield.constraint!=null}" />
													<h:outputText styleClass="constraint" value="#{msgs[beanfield.constraint]}" rendered="#{beanfield.constraint!=null&&(beanfield.fieldType=='selectOneRadio'||beanfield.fieldType=='selectManyCheckbox')}" />
													<!--Afficher les messages d'information (les données seront valides ultérieurement)  -->
													<t:div>
												 	<h:outputText style="display:none" styleClass="#{beanfield.name}digestConstraint digestConstraint" value="#{msgs[beanfield.digestConstraint]}" rendered="#{beanfield.digestConstraint!=null}"/>
			     									</t:div>
			     										
												</h:column>
												<!--Afficher les boutons ajouter, supprimer et l'aide  -->
												<h:column>
													<h:graphicImage title="#{msgs[beanfield.help]}"	value="/media/images/help.jpg" style="border: 0;" rendered="#{beanfield.help!=null&&!accountController.viewDataChange}" />
													<t:div style="display:none" styleClass="#{beanfield.name}modify">
														<h:graphicImage title="#{msgs[beanfield.notice]}" styleClass="#{beanfield.name}toValidateDRH" value="/media/images/redtriangular.jpg" style="border: 0;" rendered="#{!beanfield.updateable&&!beanfield.disable&&!accountController.viewDataChange}" />
														<h:graphicImage alt="#{beanfield.name}" styleClass="show" value="/media/images/add.png" style="border: 0;cursor:pointer" rendered="#{beanfield.multiValue&&!beanfield.disable&&(beanfield.fieldType=='inputText'||beanfield.fieldType=='selectOneMenu')}" />
														<h:graphicImage alt="#{beanfield.name}" styleClass="hide" value="/media/images/remove.png" style="border: 0;cursor:pointer"	rendered="#{beanfield.multiValue&&!beanfield.disable&&(beanfield.fieldType=='inputText'||beanfield.fieldType=='selectOneMenu')}" />
													</t:div>
												</h:column>
												<!--Permet de gérer l'affichage des champs en modification (voir form-show-hide.js)-->
												<h:column>
													<h:outputText styleClass="#{beanfield.name}_modifyLink" rendered="#{!beanfield.disable&&beanfield.fieldType!='selectManyCheckbox'&&beanfield.fieldType!='selectOneRadio'}"/>
												</h:column>
										</h:dataTable>
										<t:div style="margin-top:1em;">
											<t:htmlTag style="display:none"rendered="#{category.access}" styleClass="validate btn btn-primary" value="button" ><t:htmlTag  value="span" styleClass="glyphicon glyphicon glyphicon-ok"></t:htmlTag><h:outputText value="#{msgs['_.BUTTON.CONFIRM']}" /></t:htmlTag>
											<t:htmlTag rendered="#{category.access}" styleClass="modifyByCategory btn btn-primary" value="button" ><t:htmlTag  value="span" styleClass="glyphicon glyphicon glyphicon-edit"></t:htmlTag><h:outputText value="Editer" /></t:htmlTag>
										</t:div>		
									</t:htmlTag><!-- Fin class=columnData -->
									
									<t:htmlTag value="td" styleClass="columnHelp">									
										<t:div styleClass="helppanel" >
											<t:div>
												<h:panelGrid >
													<t:div styleClass="portlet-section-text">	<h:outputText value="#{msgs['DATACHANGE.TEXT.LEGEND']}" /></t:div>
										
													<t:div>
													<h:graphicImage value="/media/images/help.jpg" /><h:outputText styleClass="legendlabel" value=": #{msgs['DATACHANGE.TEXT.LEGEND.HELP']}" />
													</t:div>
													<t:div>
														<h:graphicImage value="/media/images/redtriangular.jpg" /><h:outputText styleClass="legendlabel" value=": #{msgs['DATACHANGE.TEXT.LEGEND.NOTE']}" />
													</t:div>
													<t:div>
														<h:graphicImage value="/media/images/add.png" /><h:outputText styleClass="legendlabel"	value=": #{msgs['DATACHANGE.TEXT.LEGEND.ADD']}" />
													</t:div>
													<t:div>
														<h:graphicImage value="/media/images/remove.png" /><h:outputText styleClass="legendlabel" value=": #{msgs['DATACHANGE.TEXT.LEGEND.REMOVE']}" />
													</t:div>
												</h:panelGrid>
											</t:div>
										</t:div>
									</t:htmlTag><!-- Fin class=columnHelp -->
								</t:htmlTag>
							</t:htmlTag>
						</t:div>	
					</t:dataList>
					<!-- 
					<t:div style="margin-top:1em;">
						<button style="display:none" type="submit" class="btn btn-primary validate" onclick="simulateLinkClick('accountForm:next');"><span class="glyphicon glyphicon-saved">Confirmer</span></button>
						<button  class="btn btn-primary modifyByCategory" ><span class="glyphicon glyphicon-edit"></span>Editer</button>
					</t:div>
					 -->
			    </div><!-- tab content -->
			</div>			
		</div><!-- Fin row nav pills -->
	

		<t:div style="display:none">
			<e:commandButton id="next" value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChangeInfoPerso}" />
		</t:div>
		
	</h:form>
</div><!-- Fin class="container" -->
</div><!-- Fin class="pc md" -->

<h:form id="restart" style="display:none;">
	<e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
</h:form>
</e:page>