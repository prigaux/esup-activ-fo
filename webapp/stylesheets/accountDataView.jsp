<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>
<script type="text/javascript" src="/media/scripts/accountDataTabs.js"></script>

<script>
$(function() {	
	// Afficher par défaut le 1er onglet
	$('.nav-stacked li:eq(0) a').tab('show');
	// Afficher par défaut l'onglet Afficher les données
	$('.nav-pills li:eq(2) a').tab('show');
	
});
</script>


<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}">
<div class="container-fluid">
	<!-- <e:section value="#{msgs['DATACHANGE.DATACHANGE.TITLE']}" /> -->
	
			<%@include file="_includeAccountData.jsp"%>
			<div class="col-md-9">
				<div class="tab-content">
				   <!-- Appeler la méthode getBeanData qui récupère les données, pour afficher la civilité et le displayName
					car avant cette méthode, aucune données n'est récupérées
					 -->
					<t:outputText value="#{accountController.beanData}" style="display:none"></t:outputText>
					<div class="page-header"><h:outputText style="text-transform : capitalize;"value="#{accountController.currentAccount.supannCivilite} #{accountController.currentAccount.displayName}" /></div>
	    
				   	<t:dataList value="#{accountController.beanData}"  var="category" >
						<t:div styleClass="hrefIdDetail tab-pane" rendered="#{category.access}">
							<t:htmlTag value="table">
							<t:htmlTag value="tr">
								<t:htmlTag value="td" styleClass="columnData">
									 <h:dataTable value="#{category.profilingListBeanField}" rendered="#{category.access&&#beanfield.value!=''}" var="beanfield" columnClasses="viewCol1,viewCol2">
										<h:column>						
										  <t:outputText styleClass="labeltexttop#{beanfield.size>1} portlet-section-text" value="#{msgs[beanfield.key]}" rendered="#{beanfield.value!=''}"/>
									   </h:column>
										
										<h:column>
											<t:div styleClass="portlet-section-text" rendered="#{sub.value!=''&&beanfield.fieldType=='link'}">   
										    	<h:outputText escape="false" converter="#{beanfield.converter}" rendered="#{beanfield.fieldType=='link'}" value="#{msgs[beanfield.value]}"/>
										    </t:div>	
											<t:dataList value="#{beanfield.values}" var="sub" rendered="#{beanfield.value!=''&&sub.value!=''}">
											    <t:div styleClass="portlet-section-text" rendered="#{sub.value!=''&&!sub.convertedValue}">
												    <h:outputText value="#{sub.value}" converter="#{beanfield.converter}" rendered="#{sub.value!=''}"/>			  	
										        </t:div>
									         </t:dataList>  
										</h:column>
									</h:dataTable>
								</t:htmlTag>
								<t:htmlTag value="td" styleClass="columnHelp">									
									<t:div styleClass="helppanel" >
										<t:div >	<e:paragraph escape="false" value="#{msgs['DATACHANGE.DISPLAY.TOP']}">
														<f:param value="#{msgs[category.title]}" />
													</e:paragraph> 
										</t:div>
									</t:div>
								</t:htmlTag>
							
							</t:htmlTag>
						</t:htmlTag>
							
						</t:div>				
					</t:dataList>
				   </div><!-- tab content -->
			</div>
			
		</div><!-- Fin row nav pills -->
	

</div><!-- Fin class="container" -->
<h:form id="accountForm" style="display:none;" >
	<e:commandButton id="preview" value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChangeInfoPerso}"/>
</h:form>
<h:form id="restart" style="display:none;">
	<e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
</h:form>
</e:page>
