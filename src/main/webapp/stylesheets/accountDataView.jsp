<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}">
<%@include file="_includeScript.jsp"%>
<script type="text/javascript" src="../media/scripts/accountDataTabs.js"></script>

<script>
$(function() {	
	// Afficher par défaut le 1er onglet
	$('.nav-stacked li:eq(0) a').tab('show');
	// Afficher par défaut l'onglet Afficher les données
	$('.nav-pills li:eq(1) a').tab('show');
	//Ouvrir sur une nouvelle page
	$('.linkPerso').attr({"target" : "_blank"});
	//Supprimer l'élément de session tabSelected,afin de remttre à l'état initiale les onglets de accountDataChange.jsp
	 sessionStorage.removeItem("tabSelected");
});
</script>

<div class="pc">
	<div class="container-fluid">
			<%@include file="_includeBreadcrumb.jsp"%>
			<div class="mainBlock">
				<%@include file="_includeAccountData.jsp"%>
				<div class="col-md-9">
					<div class="tab-content">
					   	<t:dataList value="#{accountController.beanData}"  var="category" >
							<t:div styleClass="hrefIdDetail tab-pane" rendered="#{category.access}">							
								<t:htmlTag value="table">									
									<t:htmlTag value="tr">
										<t:htmlTag value="td">
											 <h:dataTable value="#{category.profilingListBeanField}" rendered="#{category.access&&#beanfield.value!=''}" var="beanfield" columnClasses="viewCol1,viewCol2">
												<h:column>						
												  <t:outputText styleClass="labeltexttop#{beanfield.size>1} portlet-section-text" value="#{msgs[beanfield.key]}" rendered="#{beanfield.value!=''}"/>
											   </h:column>
												
												<h:column>
													<t:div rendered="#{sub.value!=''&&beanfield.fieldType=='link'}">   
												    	<h:outputText escape="false" converter="#{beanfield.converter}" rendered="#{beanfield.fieldType=='link'}" value="#{msgs[beanfield.value]}"/>
												    </t:div>	
													<t:dataList value="#{beanfield.values}" var="sub" rendered="#{beanfield.value!=''&&sub.value!=''}">
													    <t:div rendered="#{sub.value!=''&&!sub.convertedValue&&beanfield.fieldType!='linkPerso'}">
														    <h:outputText value="#{sub.value}" converter="#{beanfield.converter}" rendered="#{sub.value!=''}"/>			  	
												        </t:div>
												         <t:div rendered="#{sub.value!=''&&!sub.convertedValue&&beanfield.fieldType=='linkPerso'}">
														   <h:outputLink styleClass="linkPerso" value="#{sub.value}">
															    <h:outputText value="#{sub.value}" />
															</h:outputLink>	  	
												        </t:div>
											         </t:dataList>  
												</h:column>
											</h:dataTable>
										</t:htmlTag>
									</t:htmlTag>
							</t:htmlTag>
							</t:div>				
						</t:dataList>
					</div><!-- tab content -->
				</div><!-- class="col-md-9" -->	
				</div><!--Fin row de  _includeAccountData.jsp -->
				<t:div style="margin-top:1em;"/>					
			</div><!-- Fin class="mainBlock"-->
	</div><!-- Fin container-fluid -->
<h:form id="accountForm" style="display:none;" >
	<e:commandButton id="toDataChange" value="#{msgs['_.BUTTON.CONFIRM']} " action="#{accountController.pushFromDataView}"/>
</h:form>
<h:form id="restart" style="display:none;margin-top:1em">
	<e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
</h:form>
</div><!-- Fin class="pc" -->
</e:page>
