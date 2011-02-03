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
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Accueil" value="/media/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
		<t:htmlTag styleClass="firstStep" value="li"><t:outputText escape="false" value="#{msgs['ACTIVATION.COMPTE.ETAPE1.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="currentTab" value="li"><t:outputText escape="false" value="#{msgs['ACTIVATION.COMPTE.ETAPE2.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="thirdStep" value="li"><t:outputText escape="false" value="#{msgs['ACTIVATION.COMPTE.ETAPE3.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="fourthStep" value="li"><t:outputText escape="false" value="#{msgs['ACTIVATION.COMPTE.ETAPE4.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>
	
	<t:div styleClass="fourthStepImage5fleches" rendered="#{accountController.reinit == true}">
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Accueil" value="/media/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
		<t:htmlTag styleClass="firstStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE1.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="secondStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE2.TEXT']}" /></t:htmlTag>
		<t:htmlTag styleClass="thirdStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE3.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="currentTab" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE4.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="fifthStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.REINITIALISATION.ETAPE5.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>
	<t:div styleClass="secondStepImage3fleches" rendered="#{accountController.passwChange == true}" >
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Accueil" value="/media/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
		<t:htmlTag styleClass="firstStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE1.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="currentTab" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE2.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="thirdStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE3.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>
	
	<t:div styleClass="secondStepImage3fleches" rendered="#{accountController.loginChange == true}" >
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Accueil" value="/media/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
		<t:htmlTag styleClass="firstStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE1.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="currentTab" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE2.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="thirdStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.LOGINCHANGE.ETAPE3.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>

	<e:messages/>
	
	<e:paragraph value="#{msgs['PERSOINFO.TEXT.TOP']}" />
	
	<h:form id="accountForm" rendered="#{sessionController.currentUser == null}">
	  <h:dataTable value="#{accountController.listBeanPersoInfo}" var="entry" columnClasses="firstColumn,secondColumn,thirdColumn"> 
		<h:column>						
		  <e:outputLabel value="#{msgs[entry.key]}" />
		</h:column>
		<h:column>
		<t:dataList value="#{entry.values}" var="sub" style="Vertical-Align: Top;" rendered="#{entry.fieldType!='selectBooleanCheckbox'}" >
          <t:div rendered="#{sub.value!=''}" styleClass="#{entry.divName}show">
              <h:inputText value="#{sub.value}" required="#{entry.required}" size="35" validator="#{entry.validator.validate}" rendered="#{entry.fieldType==null&&entry.validator!=null&&sub.value!=''}" />
              <h:inputText value="#{sub.value}" size="35" rendered="#{entry.fieldType==null&&entry.validator==null&&sub.value!=''}" />
              <t:htmlTag value="br"  />
          </t:div>
          <t:div rendered="#{sub.value==''&&entry.isMultiValue!=null&&entry.isMultiValue!=true}" styleClass="#{entry.divName}show">
              <h:inputText value="#{sub.value}" required="#{entry.required}" size="35" validator="#{entry.validator.validate}" rendered="#{entry.fieldType==null&&entry.validator!=null&&sub.value==''}" />
              <h:inputText value="#{sub.value}" size="35" rendered="#{entry.fieldType==null&&entry.validator==null&&sub.value==''}" />
              <t:htmlTag value="br"  />
          </t:div>
          <t:div rendered="#{sub.value==''&&entry.isMultiValue!=null&&entry.isMultiValue==true}" style="display:none;" styleClass="#{entry.divName}hide" >
              <h:inputText value="#{sub.value}" required="#{entry.required}" size="35" validator="#{entry.validator.validate}" rendered="#{entry.fieldType==null&&entry.validator!=null&&sub.value==''}" />
              <h:inputText value="#{sub.value}" size="35" rendered="#{entry.fieldType==null&&entry.validator==null&&sub.value==''}" />
              <t:htmlTag value="br"  />
          </t:div>
        </t:dataList>

        <t:dataList value="#{accountController.checkList}" var="entry2" rendered="#{entry.fieldType=='selectBooleanCheckbox'}">
           <t:div styleClass="#{entry.divName}show" rendered="#{entry.fieldType=='selectBooleanCheckbox'}">
              <h:selectBooleanCheckbox value="true" styleClass="#{entry2[0]}" valueChangeListener="#{accountController.checkboxChanged}" rendered="#{entry.fieldType=='selectBooleanCheckbox'&&entry2[1]=='true'}" />
              <h:selectBooleanCheckbox value="false" styleClass="#{entry2[0]}" valueChangeListener="#{accountController.checkboxChanged}" rendered="#{entry.fieldType=='selectBooleanCheckbox'&&entry2[1]!='true'}" />
              <h:outputLabel value="#{entry2[0]}" rendered="#{entry.fieldType=='selectBooleanCheckbox'}"/>
              <t:htmlTag value="br"/>
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
	
	<h:form id="restart" style="display:none;">
		<e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>