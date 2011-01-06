<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}">
	
	<t:documentHead>
		<meta http-equiv="Expires" content="0">
		<meta http-equiv="cache-control" content="no-cache,no-store">
		<meta http-equiv="pragma" content="no-cache">
	</t:documentHead>

	<%@include file="_navigation.jsp"%>
	
	<e:section value="#{msgs['PERSOINFO.REINITIALISATION.TITLE']}" rendered="#{accountController.reinit == true}"/>
	<e:section value="#{msgs['PERSOINFO.ACTIVATION.TITLE']}" rendered="#{accountController.activ == true}" />
	<e:section value="#{msgs['PERSOINFO.PASSWORDCHANGE.TITLE']}" rendered="#{accountController.passwChange == true}" />
	<e:section value="#{msgs['PERSOINFO.LOGINCHANGE.TITLE']}" rendered="#{accountController.loginChange == true}" />

   
    <t:div styleClass="secondStepImage" rendered="#{accountController.activ == true}">
	<ul id="processSteps">
		<li id="firstStep"><h:outputText value="#{msgs['ACTIVATION.COMPTE.ETAPE1.TEXT']}"></h:outputText></li>
		<li id="currentTab"><h:outputText value="#{msgs['ACTIVATION.COMPTE.ETAPE2.TEXT']}"></h:outputText></li>
		<li id="thirdStep"><h:outputText value="#{msgs['ACTIVATION.COMPTE.ETAPE3.TEXT']}"></h:outputText></li>
		<li id="fourthStep"><h:outputText value="#{msgs['ACTIVATION.COMPTE.ETAPE4.TEXT']}"></h:outputText></li>
	</ul>
	</t:div>
	
	<t:div styleClass="fourthStepImage5fleches" rendered="#{accountController.reinit == true}">
	<ul id="processSteps">
		<li id="firstStep"><h:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE1.TEXT']}"></h:outputText></li>
		<li id="secondStep"><h:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE2.TEXT']}" ></h:outputText></li>
		<li id="thirdStep"><h:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE3.TEXT']}"></h:outputText></li>
		<li id="currentTab"><h:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE4.TEXT']}"></h:outputText></li>
		<li id="fifthStep"><h:outputText value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE5.TEXT']}"></h:outputText></li>
	</ul>
	</t:div>
	<t:div styleClass="secondStepImage3fleches" rendered="#{accountController.passwChange == true}" >
	<ul id="processSteps">
		<li id="firstStep"><h:outputText value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE1.TEXT']}"></h:outputText></li>
		<li id="currentTab"><h:outputText value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE2.TEXT']}"></h:outputText></li>
		<li id="thirdStep"><h:outputText value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE3.TEXT']}"></h:outputText></li>
	</ul>
	</t:div>
	
	<t:div styleClass="secondStepImage3fleches" rendered="#{accountController.loginChange == true}" >
	<ul id="processSteps">
		<li id="firstStep"><h:outputText value="#{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE1.TEXT']}"></h:outputText></li>
		<li id="currentTab"><h:outputText value="#{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE2.TEXT']}"></h:outputText></li>
		<li id="thirdStep"><h:outputText value="#{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE3.TEXT']}"></h:outputText></li>
	</ul>
	</t:div>

	<e:messages/>
	
	<e:paragraph value="#{msgs['PERSOINFO.TEXT.TOP']}" />
	
	<h:form id="accountForm" rendered="#{sessionController.currentUser == null}">
	  <h:dataTable value="#{accountController.listBeanPersoInfo}" var="entry" columnClasses="firstColumn,secondColumn,thirdColumn"> 
		<h:column>						
		  <e:outputLabel value="#{msgs[entry.key]}" />
		</h:column>
		<h:column>
		<t:dataList value="#{entry.values}" var="sub" style="Vertical-Align: Top;" >
		    <t:div rendered="#{sub.value!=''}" styleClass="#{entry.divName}show">
			    <h:inputText value="#{sub.value}" disabled="#{entry.isMultiValue!=true}" required="#{entry.required}" size="35" validator="#{entry.validator.validate}" rendered="#{entry.fieldType==null&&entry.validator!=null&&sub.value!=''}" />
	            <h:inputText value="#{sub.value}" disabled="#{entry.isMultiValue!=true}" size="35" rendered="#{entry.fieldType==null&&entry.validator==null&&sub.value!=''}" />
	            <t:htmlTag value="br"  />
	        </t:div>
	        <t:div rendered="#{sub.value==''}" style="display:none;" styleClass="#{entry.divName}hide" >    
	            <h:inputText value="#{sub.value}" required="#{entry.required}" size="35" validator="#{entry.validator.validate}" rendered="#{entry.fieldType==null&&entry.validator!=null&&sub.value==''}" />
	            <h:inputText value="#{sub.value}" size="35" rendered="#{entry.fieldType==null&&entry.validator==null&&sub.value==''}" />
	            <t:htmlTag value="br"  />
            </t:div>
          </t:dataList>


        </h:column>  
        <h:column>			
		  <h:graphicImage styleClass="helpTip" longdesc="#{msgs[entry.help]}" value="/media/help.jpg"  style="border: 0;" rendered="#{entry.help!=null}"/>
		  <t:div >
			  <h:graphicImage alt="#{entry.divName}" styleClass="show" value="/media/add.png"  style="border: 0;" rendered="#{entry.isMultiValue!=null&&entry.isMultiValue==true}"/>
			  <h:graphicImage alt="#{entry.divName}" styleClass="hide" value="/media/remove.png"  style="border: 0;" rendered="#{entry.isMultiValue!=null&&entry.isMultiValue==true}"/>
		  </t:div>
		</h:column>										
	  </h:dataTable>
										
	
	<t:div style="margin-top:30;">
		<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChangeInfoPerso}" />
	</t:div>
	
	</h:form>
	
	<h:form>
		<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>