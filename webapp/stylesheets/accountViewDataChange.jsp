<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
<!--//--><![CDATA[//><!--
$(function() {
    $("h2.expand").toggler({initShow: "div.collapse:first"});
    $("#content").expandAll({trigger: "h2.expand", ref: "div.demo",  speed: 100, oneSwitch: false});
});
//--><!]]>
</script>

<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}">
	
	<t:documentHead>
		<meta http-equiv="Expires" content="0">
		<meta http-equiv="cache-control" content="no-cache,no-store">
		<meta http-equiv="pragma" content="no-cache">
	</t:documentHead>

	<%@include file="_navigation.jsp"%>
	
	<e:section value="#{msgs['DATACHANGE.DATACHANGE.TITLE']}" rendered="#{accountController.dataChange == true}" />

   
	<t:div styleClass="secondStepImage3fleches" rendered="#{accountController.dataChange == true}" >
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Accueil" value="/media/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
		<t:htmlTag styleClass="firstStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE1.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="secondStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE2.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="currentTab" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE3.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>
	
	<e:messages/>
	
	<e:paragraph value="#{msgs['PERSOINFO.TEXT.TOP']}" />
	
	<div class="demo">
            
	
	 <t:dataList value="#{accountController.listDataChangeCategory}"var="category">
	 
       <t:htmlTag value="h3" ><h:outputText value="#{msgs[category.key]}"/></t:htmlTag>
	 
	  <t:div styleClass="collapse" >
	   <h:dataTable value="#{accountController.listDataChangeInfos}" var="entry" columnClasses="firstColumn,secondColumn,thirdColumn"> 
		<h:column rendered="#{entry.category==category.name}" >						
		  <e:outputLabel value="#{msgs[entry.key]}" />
		</h:column>
		<h:column  rendered="#{entry.category==category.name}">
		<t:dataList value="#{entry.values}" var="sub"  rendered="#{entry.fieldType!='selectManyCheckbox'}" >
		
		    <t:div rendered="#{sub.value!=''&&entry.fieldType!='selectOneRadio'}" styleClass="#{entry.name}show">
			    <h:inputText value="#{sub.value}" required="#{entry.required}" size="35" validator="#{entry.validator.validate}" rendered="#{entry.fieldType=='inputText'&&entry.validator!=null&&sub.value!=''}" />
	            <h:inputText value="#{sub.value}" size="35" rendered="#{entry.fieldType=='inputText'&&entry.validator==null&&sub.value!=''}" />
	            <t:htmlTag value="br"  />
	        </t:div>
	         
	        <t:div rendered="#{sub.value==''&&entry.isMultiValue!=null&&entry.isMultiValue!=true&&entry.fieldType!='selectOneRadio'}" styleClass="#{entry.name}show">
			    <h:inputText value="#{sub.value}" required="#{entry.required}" size="35" validator="#{entry.validator.validate}" rendered="#{entry.fieldType=='inputText'&&entry.validator!=null&&sub.value==''}" />
	            <h:inputText value="#{sub.value}" size="35" rendered="#{entry.fieldType=='inputText'&&entry.validator==null&&sub.value==''}" />
	            <t:htmlTag value="br"  />
	        </t:div>
	        <t:div rendered="#{sub.value==''&&entry.isMultiValue!=null&&entry.isMultiValue==true&&entry.fieldType!='selectOneRadio'}" style="display:none;" styleClass="#{entry.name}hide" >    
	            <h:inputText value="#{sub.value}" required="#{entry.required}" size="35" validator="#{entry.validator.validate}" rendered="#{entry.fieldType=='inputText'&&entry.validator!=null&&sub.value==''}" />
	            <h:inputText value="#{sub.value}" size="35" rendered="#{entry.fieldType=='inputText'&&entry.validator==null&&sub.value==''}" />
	            <t:htmlTag value="br"  />
            </t:div>
          
        </t:dataList>
                       
                               
       		</h:column>  
        										
	  	</h:dataTable>
	  </t:div>
	  
	  </t:dataList>
										
</div>


     <e:subSection value="#{msgs['ENABLED.SUBTITLE.ESUPACCESS']}" />
	 <e:paragraph escape="false" value="#{msgs['ENABLED.TEXT.ESUPURL']}" />
	 <h:form>
		<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>
	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>