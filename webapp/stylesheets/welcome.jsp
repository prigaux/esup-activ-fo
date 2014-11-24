<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>
<script type="text/javascript">
 $(document).ready(function(){  
	 
	// selectionner les premiers boutons par d�faut.
	$('input:radio:first').attr('checked',true);
	$('input:radio[name="welcomeForm:statusRadio"]:first').attr('checked','checked');
	  
	// Affichier ou cacher les radios boutons "statut" en fonctions des proc�dures selectionn�es 
	// en mode connect� 
	 if($("input:radio[value='activation']").attr("checked") ||
	    $("input:radio[value='reinitialisation']").attr("checked"))
	  $("#statusDiv").show();
	 else  $("#statusDiv").hide();		
	 
	// en mode non connect� 
	 $(":radio").click(function(){
	  if(this.value=="activation" || this.value=="reinitialisation"){ 
	  	$("#statusDiv").show();
	  }
	   else 
	   if(this.value=="passwordchange" || this.value=="loginchange" || this.value=="datachange"){     
	  	 $("#statusDiv").hide();
	   }
	 });
 });
</script>
<div class="pc">
	<e:page stringsVar="msgs" menuItem="welcome" locale="#{sessionController.locale}" >
			<div class="container-fluid">
				<div class="page-header"><t:outputText value="#{msgs['WELCOME.TITLE']}" /></div>
				<div class="lead"><t:outputText escape="false" value="#{msgs['WELCOME.EXPLAIN']}" style="font-style:italic;color:#263F82"/></div>
				
				<e:messages/>
				<h:form id="welcomeForm">
					<e:paragraph escape="false" value="#{msgs['WELCOME.PROCEDURE.TEXT.TOP']}" />
					<t:selectOneRadio layout="pageDirection" required="true" value="#{accountController.currentAccount.process}" rendered="#{sessionController.currentUser==null}" styleClass="labelOverride">
			     		<t:selectItems value="#{accountController.listBeanProcedureWithoutCas}" var="entry" itemLabel="#{msgs[entry.key]}" itemValue="#{entry.value}" ></t:selectItems>
					</t:selectOneRadio>
					<t:selectOneRadio layout="pageDirection" required="true" value="#{accountController.currentAccount.process}" rendered="#{sessionController.currentUser!=null}" styleClass="labelOverride">
			    		<t:selectItems value="#{accountController.listBeanProcedureWithCas}" var="entry" itemLabel="#{msgs[entry.key]}" itemValue="#{entry.value}" ></t:selectItems>
					</t:selectOneRadio>					
				
					<div id="statusDiv">
						<e:paragraph escape="false" value="#{msgs['WELCOME.STATUS.TEXT.TOP']}" />		
						<t:selectOneRadio id="statusRadio" required="false" value="#{accountController.currentAccount.status}" styleClass="labelOverride">
							<t:selectItems value="#{accountController.listBeanStatus}" var="entry" itemLabel="#{msgs[entry.key]}" itemValue="#{entry.value}"></t:selectItems>
						</t:selectOneRadio>	
					</div>
								
					<t:div>
						<e:commandButton id="application" value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.enter}" style="display:none"/>
						<button  class="btn btn-primary" onclick="simulateLinkClick('welcomeForm:application');"><span class="glyphicon glyphicon-ok"></span><h:outputText value="#{msgs['_.BUTTON.CONFIRM']}" /></button>
					</t:div>
				</h:form>
			</div>	
	</e:page>
</div>
