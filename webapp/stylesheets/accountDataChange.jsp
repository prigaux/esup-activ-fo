<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
  <c:when test="${accountController.viewDataChange==false}">
  <script type="text/javascript">
  <!--//--><![CDATA[//><!--
  $(function() {
    $("h2.expand").toggler({initShow: "div.collapse:all"});
    $("#content").expandAll({trigger: "h2.expand", ref: "div.demo",  speed: 100, oneSwitch: false});
    
  });
  //--><!]]>
  </script>	
  </c:when>
  <c:otherwise>
  <script type="text/javascript">
  <!--//--><![CDATA[//><!--
  $(function() {
    $("h2.expand").toggler({initShow: "div.collapse:all"});
    $("#content").expandAll({trigger: "h2.expand", ref: "div.demo",  speed: 100, oneSwitch: false});
  });
  //--><!]]>
  </script>	
  </c:otherwise>
</c:choose>

<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}">
	
	<t:documentHead>
		<meta http-equiv="Expires" content="0">
		<meta http-equiv="cache-control" content="no-cache,no-store">
		<meta http-equiv="pragma" content="no-cache">
	</t:documentHead>

	<%@include file="_navigation.jsp"%>
	
	<e:section value="#{msgs['DATACHANGE.DATACHANGE.TITLE']}"  />
   
	<t:div styleClass="secondStepImage3fleches" rendered="#{accountController.dataChange == true&&accountController.viewDataChange == false}" >
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Accueil" value="/media/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
		<t:htmlTag styleClass="firstStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE1.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="currentTab" value="li"><t:outputText escape="false" value="#{msgs['DATACHANGE.MODIFICATION.ETAPE2.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="thirdStep" value="li"><t:outputText escape="false" value="#{msgs['DATACHANGE.RESULTA.ETAPE3.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>
	
	<t:div styleClass="thirdStepImage3fleches" rendered="#{accountController.dataChange == false&&accountController.viewDataChange == true}" >
	<t:htmlTag styleClass="processSteps" value="ul">
	    <t:htmlTag styleClass="homeStep" value="li"><t:graphicImage title="Accueil" value="/media/home.jpg"  style="border: 0;cursor:pointer;" onclick="simulateLinkClick('restart:restartButton');"/></t:htmlTag>
		<t:htmlTag styleClass="firstStep" value="li"><t:outputText escape="false" value="#{msgs['IDENTIFICATION.PASSWORDCHANGE.ETAPE1.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="secondStep" value="li"><t:outputText escape="false" value="#{msgs['DATACHANGE.MODIFICATION.ETAPE2.TEXT']}"/></t:htmlTag>
		<t:htmlTag styleClass="currentTab" value="li"><t:outputText escape="false" value="#{msgs['DATACHANGE.RESULTA.ETAPE3.TEXT']}"/></t:htmlTag>
	</t:htmlTag>
	</t:div>
	
	<e:messages/>
	
	<e:paragraph value="#{msgs['PERSOINFO.TEXT.TOP']}" />
	
	<h:form id="accountForm" >
	
	<div class="demo">
	
	 <t:dataList value="#{accountController.beanData}" var="category">
	  
	 
      <t:htmlTag value="h2" styleClass="expand" rendered="#{category.access}" ><h:outputText value="#{msgs[category.title]}"/></t:htmlTag>
	 
	  <t:div styleClass="collapse" >
	   <h:dataTable value="#{category.listBeanField}" rendered="#{category.access}" var="beanfield" columnClasses="firstColumn,secondColumn,thirdColumn">
	  
	   <h:column  >						
		  <e:outputLabel style="vertical-align:top;" value="#{msgs[beanfield.key]}" rendered="#{beanfield.size>1}"/>
		  <e:outputLabel value="#{msgs[beanfield.key]}" rendered="#{beanfield.size<=1}"/>
	   </h:column>
	   <h:column >
	    
		<t:dataList value="#{beanfield.values}" var="sub"  rendered="#{beanfield.fieldType!='selectManyCheckbox'&&!accountController.viewDataChange}" >
		    
		    <t:div rendered="#{sub.value!=''&&beanfield.fieldType!='selectOneRadio'&&!sub.convertedValue&&beanfield.fieldType!='selectOneMenu'}" styleClass="#{beanfield.name}show">
			    <h:inputText value="#{sub.value}"  disabled="#{beanfield.disable}" converter="#{beanfield.converter}" validator="#{beanfield.validator.validate}"  required="#{beanfield.required}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator!=null&&sub.value!=''&&!sub.convertedValue}" />
	            <h:inputText value="#{sub.value}"  disabled="#{beanfield.disable}" converter="#{beanfield.converter}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator==null&&sub.value!=''&&!sub.convertedValue}" />
	            <t:htmlTag value="br"  />
	        </t:div>
	        
	        
	        <t:div rendered="#{sub.value==''&&beanfield.isMultiValue!=null&&beanfield.isMultiValue!=true&&beanfield.fieldType!='selectOneRadio'&&beanfield.fieldType!='selectOneMenu'}" styleClass="#{beanfield.name}show">
			    <h:inputText value="#{sub.value}" required="#{beanfield.required}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator!=null&&sub.value==''}" />
	            <h:inputText value="#{sub.value}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator==null&&sub.value==''}" />
	            <t:htmlTag value="br"  />
	        </t:div>
	        
	        <t:div rendered="#{sub.value==''&&beanfield.isMultiValue!=null&&beanfield.isMultiValue==true&&beanfield.fieldType!='selectOneRadio'&&beanfield.fieldType!='selectOneMenu'}" style="display:none;" styleClass="#{beanfield.name}hide" >    
	            <h:inputText value="#{sub.value}" required="#{beanfield.required}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator!=null&&sub.value==''}" />
	            <h:inputText value="#{sub.value}" size="35" rendered="#{beanfield.fieldType=='inputText'&&beanfield.validator==null&&sub.value==''}" />
	            <t:htmlTag value="br" />
            </t:div>
            
            <t:div rendered="#{beanfield.fieldType=='selectOneMenu'}">             
             	<h:selectOneMenu value="#{sub.value}" rendered="#{beanfield.fieldType=='selectOneMenu'}" >
                  <f:selectItems value="#{beanfield.displayItems}" />
             	</h:selectOneMenu>        
            </t:div> 
            
        </t:dataList>
        
        <t:dataList value="#{beanfield.values}" var="sub"  rendered="#{beanfield.fieldType!='selectManyCheckbox'&&accountController.viewDataChange&&beanfield.fieldType!='selectOneMenu'}" >
		    <t:div rendered="#{sub.value!=''&&beanfield.fieldType!='selectOneRadio'&&!sub.convertedValue&&beanfield.fieldType!='selectOneMenu'}" styleClass="portlet-section-text">
			    <h:outputText value="#{sub.value}" converter="#{beanfield.converter}" rendered="#{!sub.convertedValue}"/>
	            <t:htmlTag value="br" />
	        </t:div>
        </t:dataList>
        
        
        
      
  		</h:column>  
 
       	<h:column >
       	  <h:graphicImage styleClass="helpTip" longdesc="#{msgs[beanfield.help]}" value="/media/help.jpg"  style="border: 0;" rendered="#{!beanfield.updateable&&!beanfield.disable}"/>			
          <h:graphicImage styleClass="helpTip" longdesc="#{msgs[beanfield.notice]}" value="/media/redtriangular.jpg"  style="border: 0;" rendered="#{!beanfield.updateable&&!beanfield.disable}"/>
          <h:graphicImage alt="#{beanfield.name}" styleClass="show" value="/media/add.png"  style="border: 0;" rendered="#{beanfield.isMultiValue!=null&&beanfield.isMultiValue==true&&beanfield.fieldType=='inputText'&&(!beanfield.disable)}"/>
		  <h:graphicImage alt="#{beanfield.name}" styleClass="hide" value="/media/remove.png"  style="border: 0;" rendered="#{beanfield.isMultiValue!=null&&beanfield.isMultiValue==true&&beanfield.fieldType=='inputText'&&(!beanfield.disable)}"/>
		</h:column>										
	  </h:dataTable>
	 </t:div>
	 
	 
	
    </t:dataList>
      
  </div>


   <!-- 
		<h:selectOneMenu id="som" value="TableBean.perInfoAll" title="select any one in this menu">
  <f:selectItem id="si1" itemLabel="Thums Up" itemValue="11" />
  <f:selectItem id="si2" itemLabel="Limca" itemValue="22" />
  <f:selectItem id="si3" itemLabel="Pepsi" itemValue="33" />
  <f:selectItem id="si4" itemLabel="Sprite" itemValue="44" />
  <f:selectItem id="si5" itemLabel="Frooti" itemValue="55" />
  <f:selectItem id="si6" itemLabel="Coca-Cola" itemValue="66" />
</h:selectOneMenu>
		 -->


	<t:div style="margin-top:30;" rendered="#{accountController.viewDataChange == false}">
	  <e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChangeInfoPerso}" />
	</t:div>
	
  </h:form>
	
	<h:form id="restart" style="display:none;"  >
	  <e:commandButton id="restartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
	</h:form>
	
	<h:form  rendered="#{accountController.viewDataChange == true}">
	  <e:commandButton id="retartButton" value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>