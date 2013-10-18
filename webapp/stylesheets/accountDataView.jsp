<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>
<%@include file="_navigation.jsp"%>
<script type="text/javascript" src="/media/scripts/accountDataTabs.js"></script>
<script>
$(function() {	
	// Afficher l'onglet Données affichées par défaut
	$( ".tabs" ).tabs({ active: 1 });
	//simuler le click
	$('.hrefFirstTab').attr("onclick", "simulateLinkClick('accountForm:preview');");	
});
</script>

<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}">
	<e:section value="#{msgs['DATACHANGE.DATACHANGE.TITLE']}" />
	<t:div styleClass="tabs" >
		<t:htmlTag styleClass="processSteps" value="ul">
				<t:htmlTag styleClass="homeStep" value="li">
					<t:graphicImage title="Accueil" value="/media/images/home.jpg"style="border: 0;cursor:pointer;"onclick="simulateLinkClick('restart:restartButton');" />
				</t:htmlTag>				
				<t:htmlTag styleClass="homeStep" value="li" >
					<t:htmlTag  value="a" styleClass="hrefFirstTab"><t:outputText value="#{msgs['DATACHANGE.MODIFICATION.TEXT']}" /></t:htmlTag>
				</t:htmlTag>
				<t:htmlTag styleClass="homeStep" value="li">
					<t:htmlTag  value="a" styleClass="hrefSecondTab"><t:outputText value="#{msgs['DATACHANGE.DISPLAY.TEXT']}" /></t:htmlTag>
				</t:htmlTag>
			</t:htmlTag>
		
		<t:div styleClass="idFirstTab" >
		</t:div>		
		<t:div styleClass="idSecondTab" >
			<e:paragraph escape="false" value="#{msgs['PERSOINFO.MESSAGE.CHANGE.SUCCESSFULL']}"/>
			<t:div styleClass="moretabs" >
				<!-- Parcourir les catégories et générer dynamiquement les onglets -->
				<t:htmlTag styleClass="processSteps" value="ul">
					<t:dataList value="#{accountController.beanData}"  var="category">
						<t:htmlTag styleClass="homeStep" value="li" rendered="#{category.access}" >
							<t:htmlTag  value="a" styleClass="hrefId"><h:outputText rendered="#{category.access}" value="#{msgs[category.title]}" /></t:htmlTag>
						</t:htmlTag>
					</t:dataList>
				</t:htmlTag>
				<!-- Pour chaque catégorie récupérer les données associées -->	
				<t:dataList value="#{accountController.beanData}"  var="category" >	
					<t:div styleClass="hrefIdDetail" rendered="#{category.access}">
						<t:htmlTag value="table">
							<t:htmlTag value="tr">
								<t:htmlTag value="td" styleClass="columnData">
									 <h:dataTable value="#{category.profilingListBeanField}" rendered="#{category.access&&#beanfield.value!=''}" var="beanfield" columnClasses="viewCol1,viewCol2">
										<h:column>						
										  <t:outputText styleClass="labeltexttop#{beanfield.size>1}" value="#{msgs[beanfield.key]}" rendered="#{beanfield.value!=''}"/>
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
			</t:div>
		</t:div>
	</t:div>	
	
	<h:form id="accountForm" style="display:none;" >
		<e:commandButton id="preview" value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChangeInfoPerso}"/>
	</h:form>
	<h:form id="restart" style="display:none;"  >
	  <e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
	</h:form>
</e:page>