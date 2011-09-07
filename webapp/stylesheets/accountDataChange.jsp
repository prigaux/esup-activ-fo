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
   
	<t:div styleClass="secondStepImage3fleches">
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Accueil" value="/media/images/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
		<t:htmlTag styleClass="firstStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE1.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="currentTab" value="li"><t:outputText escape="false" value="#{msgs['DATACHANGE.MODIFICATION.ETAPE2.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="thirdStep" value="li"><t:outputText escape="false" value="#{msgs['DATACHANGE.RESULTA.ETAPE3.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>
	
	<e:messages/>
	
	<e:paragraph value="#{msgs['PERSOINFO.TEXT.TOP']}"/>
	<h:form id="accountForm" >
	<div class="mainBlock">
	
	 <t:dataList value="#{accountController.beanData}" var="category">
	  
	 
      <t:htmlTag value="h2" styleClass="expand" rendered="#{category.access}" ><h:outputText value="#{msgs[category.title]}"/></t:htmlTag>
	 
	  <t:div styleClass="collapse" >
	   <h:dataTable value="#{category.listBeanField}" rendered="#{category.access}" var="beanfield" columnClasses="firstColumn,secondColumn,thirdColumn">
	  
	   <h:column  >						
		  <e:outputLabel style="vertical-align:top;" value="#{msgs[beanfield.key]}" rendered="#{beanfield.size>1}"/>
		  <e:outputLabel value="#{msgs[beanfield.key]}" rendered="#{beanfield.size<=1}"/>
	   </h:column>
	   <h:column >
	    
		<t:dataList value="#{beanfield.values}" var="sub"  >
		    
		    <t:div rendered="#{sub.value!=''&&!sub.convertedValue||(sub.value==''&&!beanfield.multiValue)}" styleClass="#{beanfield.name}show">
			    <h:inputText value="#{sub.value}"  disabled="#{beanfield.disable}" converter="#{beanfield.converter}" validator="#{beanfield.validator.validate}"  required="#{beanfield.required}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator!=null&&(sub.value!=''||(sub.value==''&&!beanfield.multiValue))}" immediate="true" valueChangeListener="#{sub.setValue}"/>
	            <h:inputText value="#{sub.value}"  disabled="#{beanfield.disable}" converter="#{beanfield.converter}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator==null&&(sub.value!=''&&!sub.convertedValue||(sub.value==''&&!beanfield.multiValue))}" immediate="true" valueChangeListener="#{sub.setValue}"/>
	            <h:selectOneMenu value="#{sub.value}" style="max-width:23em" rendered="#{beanfield.fieldType=='selectOneMenu'&&(sub.value!=''&&!sub.convertedValue||(sub.value==''&&!beanfield.multiValue))}" >
                  <f:selectItems value="#{beanfield.displayItems}" />
             	</h:selectOneMenu>    
	        </t:div>
	        	       	        
	        <t:div rendered="#{sub.value==''&&beanfield.multiValue}" style="display:none;" styleClass="#{beanfield.name}hide" >    
	            <h:inputText value="#{sub.value}" required="#{beanfield.required}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator!=null&&sub.value==''&&beanfield.multiValue}" immediate="true" valueChangeListener="#{sub.setValue}"/>
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
  		</h:column>  
       	<h:column >
       	  <h:graphicImage styleClass="helpTip" longdesc="#{msgs[beanfield.notice]}" value="/media/images/redtriangular.jpg"  style="border: 0;" rendered="#{!beanfield.updateable&&!beanfield.disable&&!accountController.viewDataChange}"/>
       	  <h:graphicImage styleClass="helpTip" longdesc="#{msgs[beanfield.help]}" value="/media/images/help.jpg"  style="border: 0;" rendered="#{beanfield.help!=null&&!accountController.viewDataChange}"/>			        
          <h:graphicImage alt="#{beanfield.name}" styleClass="show" value="/media/images/add.png"  style="border: 0;" rendered="#{beanfield.multiValue&&!beanfield.disable&&(beanfield.fieldType=='inputText'||beanfield.fieldType=='selectOneMenu')}"/>
		  <h:graphicImage alt="#{beanfield.name}" styleClass="hide" value="/media/images/remove.png"  style="border: 0;" rendered="#{beanfield.multiValue&&!beanfield.disable&&(beanfield.fieldType=='inputText'||beanfield.fieldType=='selectOneMenu')}"/>
		</h:column>										
	  </h:dataTable>
	 </t:div>	 	 
    </t:dataList>      
  </div>

	<t:div style="margin-top:30;">
	  <e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChangeInfoPerso}" />
	</t:div>
	
  </h:form>
	
	<h:form id="restart" style="display:none;"  >
	  <e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
	</h:form>
	

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>