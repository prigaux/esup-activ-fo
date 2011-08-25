<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>
<script type="text/javascript" src="/media/scripts/block.js"></script>

<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}">
	
	<t:documentHead>
		<meta http-equiv="Expires" content="0">
		<meta http-equiv="cache-control" content="no-cache,no-store">
		<meta http-equiv="pragma" content="no-cache">
	</t:documentHead>

	<%@include file="_navigation.jsp"%>
	
	<e:section value="#{msgs['DATACHANGE.DATACHANGE.TITLE']}"  />
   	
	<t:div styleClass="thirdStepImage3fleches">
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Accueil" value="/media/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
		<t:htmlTag styleClass="firstStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE1.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="secondStep" value="li"><t:outputText escape="false" value="#{msgs['DATACHANGE.MODIFICATION.ETAPE2.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="currentTab" value="li"><t:outputText escape="false" value="#{msgs['DATACHANGE.RESULTA.ETAPE3.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>
	
	<e:paragraph value="#{msgs['PERSOINFO.MESSAGE.CHANGE.SUCCESSFULL']}"/>
	
	<div class="mainBlock">
	
	 <t:dataList value="#{accountController.beanData}" var="category">
	  
	 
      <t:htmlTag value="h2" styleClass="expand" rendered="#{category.access}" ><h:outputText value="#{msgs[category.title]}"/></t:htmlTag>
	 
	  <t:div styleClass="collapse" >
	   <h:dataTable value="#{category.listBeanField}" rendered="#{category.access}" var="beanfield" columnClasses="viewCol1,viewCol2">	  
	   <h:column  >						
		  <e:outputLabel value="#{msgs[beanfield.key]}" rendered="#{beanfield.value!=''&&beanfield.size>1}"/>
		  <e:outputLabel value="#{msgs[beanfield.key]}" rendered="#{beanfield.value!=''&&beanfield.size<=1}"/>
	   </h:column>
	   <h:column >        
        <t:dataList value="#{beanfield.values}" var="sub">
		    <t:div styleClass="portlet-section-text">
			    <h:outputText value="#{sub.value}" converter="#{beanfield.converter}" rendered="#{sub.value!=''}"/>
	        </t:div>
         </t:dataList>                 
  		</h:column>  								
	  </h:dataTable>
	 </t:div>	 	 
    </t:dataList>      
  </div>
		
	<h:form>
	  <e:commandButton id="retartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
	</h:form>
	
	<h:form id="restart" style="display:none;"  >
	  <e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>