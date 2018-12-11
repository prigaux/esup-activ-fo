<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="welcome" locale="#{sessionController.locale}" >
<%@include file="_includeScript.jsp"%>

 <script type="text/javascript">
 $(document).ready(function(){  
	 
	// selectionner les premiers boutons par d�faut.
	$('input:radio:first').attr('checked',true);
	$('input:radio[name="welcomeForm:statusRadio"]:first').attr('checked','checked');

	$('input:radio:first').parent().addClass('selected');
	  $('input:radio[name="welcomeForm:statusRadio"]:first').parent().addClass('selected');
	  $(":radio").click(function(){
      var name=$(this).attr('name');
      $('input:radio[name="' + name + '"]').parent().removeClass("selected");
      $(this).parent().addClass('selected');
  });

  $('.col-md-7>table>tbody>tr>td').each(function(i) {
     var val = $(this).find('input').val();
     $(this).addClass('btn-process'+val);
   });
   $('#statusDiv>table>tbody>tr>td').each(function(i) {
     $(this).addClass('btn-status'+i);
   });
	  
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
	//Supprimer l'élément de session tabSelected,afin de remttre à l'état initiale les onglets de accountDataChange.jsp
	 sessionStorage.removeItem("tabSelected");
	
 });

</script>

 <div class="pc">
  	<div class="container-fluid">
	  	<div class="breadcrumbAccueil"><span class="glyphicon glyphicon-home"></span><h:outputText value="Gestion de compte"/></div>
	    <div class="text-info"><t:outputText escape="false" value="#{msgs['WELCOME.EXPLAIN']}"/></div>
	 		<e:messages/>
			<h:form id="welcomeForm">
	        <div id="processDiv">
	          <div class="row">
	            <div class="col-md-7">
	              <div class="titleProcess"><span class="badge">1</span><t:outputText value="#{msgs['WELCOME.PROCEDURE.TEXT.TOP']}" /></div>
	              <t:selectOneRadio id="procedure1" layout="pageDirection" required="true" value="#{accountController.currentAccount.process}" rendered="#{sessionController.currentUser==null}" styleClass="labelOverride">
	                  <t:selectItems value="#{accountController.listBeanProcedureWithoutCas}" var="entry" itemLabel="#{msgs[entry.key]}" itemValue="#{entry.value}" ></t:selectItems>
	              </t:selectOneRadio>
	              <t:selectOneRadio id="procedure2" layout="pageDirection" required="true" value="#{accountController.currentAccount.process}" rendered="#{sessionController.currentUser!=null}" styleClass="labelOverride">
	                  <t:selectItems value="#{accountController.listBeanProcedureWithCas}" var="entry" itemLabel="#{msgs[entry.key]}" itemValue="#{entry.value}" ></t:selectItems>
	              </t:selectOneRadio>
	            </div>
	            <div class="col-md-3" id="statusDiv">
	              <div class="titleProcess"><span class="badge">2</span><t:outputText value="#{msgs['WELCOME.STATUS.TEXT.TOP']}" /></div>   
	              <t:selectOneRadio layout="pageDirection" id="statusRadio" required="false" value="#{accountController.currentAccount.status}" styleClass="labelOverride">
	                <t:selectItems value="#{accountController.listBeanStatus}" var="entry" itemLabel="#{msgs[entry.key]}" itemValue="#{entry.value}"></t:selectItems>
	              </t:selectOneRadio>
	            </div>
	            <div style="margin-top:1em;">            
	              <e:commandButton id="application" value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.enter}" style="display:none"/>               
	              <e:commandButton id="logout" value="#{msgs['_.BUTTON.LOGOUT']}" action="#{sessionController.logout}" style="display:none"/>
	            </div>
	          </div>      
	       </div>             
	    </h:form>          
		  <button class="btn btn-primary" onclick="simulateLinkClick('welcomeForm:application');">
				<span class="glyphicon glyphicon-ok"></span> <h:outputText value="#{msgs['_.BUTTON.CONFIRM']}" />					
			</button> 
  </div>
 </e:page>
