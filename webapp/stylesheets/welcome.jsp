<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>

<e:page stringsVar="msgs" menuItem="welcome" locale="#{sessionController.locale}" >
	<script type="text/javascript">
	 $(document).ready(function(){  
		 
	  // selectionner les premiers boutons par défaut.
	  $('input:radio:first').attr('checked',true);
	  $("#[name='welcomeForm:statusRadio']:first").attr("checked","checked");
		 
		 
	   if($("#[value='activation']").attr("checked") ||
	      $("#[value='reinitialisation']").attr("checked"))
	   		$("#[id='welcomeForm:statusDiv']").show();
	   else $("#[id='welcomeForm:statusDiv']").hide();		
	   
	   $(":radio").click(function(){
	     if(this.value=="activation" || this.value=="reinitialisation"){       
	       $("#[id='welcomeForm:statusDiv']").show();     
	    }
	     else 
	     if(this.value=="passwordchange" || this.value=="loginchange" || this.value=="datachange"){        
	           $("#[id='welcomeForm:statusDiv']").hide();
	           }
	   });
	 });
	</script>
 
	<e:section value="#{msgs['WELCOME.TITLE']}" />
	<t:outputText escape="false" value="#{msgs['WELCOME.EXPLAIN']}" style="font-style:italic;color:#263F82"/>
	
	<e:messages/>
	<!-- 
	<h:panelGroup rendered="#{sessionController.currentUser != null}">
		<e:paragraph value="#{msgs['WELCOME.TEXT.AUTHENTICATED']}" />
	</h:panelGroup>
	 -->
	<h:form id="welcomeForm">
		<e:paragraph escape="false" value="#{msgs['WELCOME.PROCEDURE.TEXT.TOP']}" />
		<t:selectOneRadio layout="pageDirection" required="true" value="#{accountController.currentAccount.process}" rendered="#{sessionController.currentUser==null}">
     		<t:selectItems value="#{accountController.listBeanProcedureWithoutCas}" var="entry" itemLabel="#{msgs[entry.key]}" itemValue="#{entry.value}" ></t:selectItems>
		</t:selectOneRadio>
		<t:selectOneRadio layout="pageDirection" required="true" value="#{accountController.currentAccount.process}" rendered="#{sessionController.currentUser!=null}">
    		<t:selectItems value="#{accountController.listBeanProcedureWithCas}" var="entry" itemLabel="#{msgs[entry.key]}" itemValue="#{entry.value}" ></t:selectItems>
		</t:selectOneRadio>					
	
		<t:div id="statusDiv">
		<e:paragraph escape="false" value="#{msgs['WELCOME.STATUS.TEXT.TOP']}" />		
			<t:selectOneRadio id="statusRadio" required="false" value="#{accountController.currentAccount.status}">
				<t:selectItems value="#{accountController.listBeanStatus}" var="entry" itemLabel="#{msgs[entry.key]}" itemValue="#{entry.value}"></t:selectItems>
			</t:selectOneRadio>	
		</t:div>
					
		<t:div style="margin-top:1em;">
			<e:commandButton id="application" value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.enter}"/>
		</t:div>
	</h:form>
	
<% /* @include file="_debug.jsp" */ %>
</e:page>
