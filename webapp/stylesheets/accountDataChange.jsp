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
		<t:htmlTag styleClass="currentTab" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE2.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="thirdStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE3.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>
	
	<e:messages/>
	
	<e:paragraph value="#{msgs['PERSOINFO.TEXT.TOP']}" />
	
	<h:form id="accountForm" rendered="#{sessionController.currentUser == null}">
	<div class="demo">
            
	
	 <t:dataList value="#{accountController.listDataChangeCategory}"var="category">
	 
       <t:htmlTag value="h2" styleClass="expand"><h:outputText value="#{msgs[category.key]}"/></t:htmlTag>
	 
	  <t:div styleClass="collapse" >
	   <h:dataTable value="#{accountController.listDataChangeInfos}" var="entry" columnClasses="firstColumn,secondColumn,thirdColumn"> 
		<h:column rendered="#{entry.category==category.name}" >						
		  <e:outputLabel value="#{msgs[entry.key]}" />
		</h:column>
		<h:column  rendered="#{entry.category==category.name}">
		<t:dataList value="#{entry.values}" var="sub"  rendered="#{entry.fieldType!='selectManyCheckbox'}" >
		
		    <t:div rendered="#{sub.value!=''&&entry.fieldType!='selectOneRadio'}" styleClass="#{entry.name}show">
			    <h:inputText value="#{sub.value}"  required="#{entry.required}" size="35" validator="#{entry.validator.validate}" rendered="#{entry.fieldType=='inputText'&&entry.validator!=null&&sub.value!=''}" />
	            <h:inputText value="#{sub.value}"  size="35" rendered="#{entry.fieldType=='inputText'&&entry.validator==null&&sub.value!=''}" />
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
        	<h:column  rendered="#{entry.category==category.name}">			
		  	<h:graphicImage styleClass="helpTip" longdesc="#{msgs[entry.help]}" value="/media/help.jpg"  style="border: 0;" rendered="#{entry.help!=null}"/>
		  	<t:div >
			  <h:graphicImage alt="#{entry.name}" styleClass="show" value="/media/add.png"  style="border: 0;" rendered="#{entry.isMultiValue!=null&&entry.isMultiValue==true&&entry.fieldType=='inputText'&&(!entry.disable)}"/>
			  <h:graphicImage alt="#{entry.name}" styleClass="hide" value="/media/remove.png"  style="border: 0;" rendered="#{entry.isMultiValue!=null&&entry.isMultiValue==true&&entry.fieldType=='inputText'&&(!entry.disable)}"/>
		  	</t:div>
			</h:column>										
	  	</h:dataTable>
	  </t:div>
	  
	  </t:dataList>
										
</div>

	<t:div style="margin-top:30;">
		<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChangeInfoPerso}" />
	</t:div>
	
	</h:form>
	
	<h:form id="restart" style="display:none;">
		<e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>