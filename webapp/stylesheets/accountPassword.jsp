<%@include file="_include.jsp"%>
<%@include file="_includeScript.jsp"%>

<e:page stringsVar="msgs" menuItem="account" locale="#{sessionController.locale}" >
	<%@include file="_navigation.jsp"%>
	<e:section value="#{msgs['PASSWORD.ACTIVATION.TITLE']}" rendered="#{accountController.activ == true}"/>
	<e:section value="#{msgs['PASSWORD.REINITIALISATION.TITLE']}" rendered="#{accountController.reinit == true}"/>
	<e:section value="#{msgs['PASSWORD.PASSWORDCHANGE.TITLE']}" rendered="#{accountController.passwChange == true}"/>
	<e:messages />
	<e:paragraph escape="false" value="#{msgs['PASSWORD.TEXT.TOP']}" rendered="#{accountController.passwChange == false}"/>
	<e:paragraph escape="false" value="#{msgs['PASSWORD.CHANGEMENT.TEXT.TOP']}" rendered="#{accountController.passwChange == true}"/>

<script language="javascript" type="text/javascript">
    
    var minpwlength = 4;
    var fairpwlength = 7;
    
    var STRENGTH_VERY_WEAK = 0;  // less than minpwlength 
    var STRENGTH_WEAK = 1;  // less than fairpwlength
    var STRENGTH_MEDIOCRE = 2;  // fairpwlength or over, no numbers
    var STRENGTH_STRONG = 3; // fairpwlength or over with at least one number
    var STRENGTH_STRONGER = 4; // fairpwlength or over with at least one number
                                
    var strengthcolors = Array( "red",
                                "#ffd801",
                                "orange",
                                "#3bce08" );

function updatestrength(passwd,msg_verystrong,msg_strong,msg_mediocre,msg_weak,msg_veryweak)
{
		var intScore   = 0
		var strVerdict = msg_veryweak
		var strLog     = ""
		var strengthlevel = 0;
		
		// PASSWORD LENGTH
		if (passwd.length<5)                         // length 4 or less
		{
			intScore = (intScore+3)
			strLog   = strLog + "3 points for length (" + passwd.length + ")\n"
		}
		else if (passwd.length>4 && passwd.length<8) // length between 5 and 7
		{
			intScore = (intScore+6)
			strLog   = strLog + "6 points for length (" + passwd.length + ")\n"
		}
		else if (passwd.length>7 && passwd.length<16)// length between 8 and 15
		{
			intScore = (intScore+12)
			strLog   = strLog + "12 points for length (" + passwd.length + ")\n"
		}
		else if (passwd.length>15)                    // length 16 or more
		{
			intScore = (intScore+18)
			strLog   = strLog + "18 point for length (" + passwd.length + ")\n"
		}
		
		
		// LETTERS (Not exactly implemented as dictacted above because of my limited understanding of Regex)
		if (passwd.match(/[a-z]/))                              // [verified] at least one lower case letter
		{
			intScore = (intScore+1)
			strLog   = strLog + "1 point for at least one lower case char\n"
		}
		
		if (passwd.match(/[A-Z]/))                              // [verified] at least one upper case letter
		{
			intScore = (intScore+5)
			strLog   = strLog + "5 points for at least one upper case char\n"
		}
		
		// NUMBERS
		if (passwd.match(/\d+/))                                 // [verified] at least one number
		{
			intScore = (intScore+5)
			strLog   = strLog + "5 points for at least one number\n"
		}
		
		if (passwd.match(/(.*[0-9].*[0-9].*[0-9])/))             // [verified] at least three numbers
		{
			intScore = (intScore+5)
			strLog   = strLog + "5 points for at least three numbers\n"
		}
		
		
		// SPECIAL CHAR
		if (passwd.match(/.[!,@,#,$,%,^,&,*,?,_,~]/))            // [verified] at least one special character
		{
			intScore = (intScore+5)
			strLog   = strLog + "5 points for at least one special char\n"
		}
		
									 // [verified] at least two special characters
		if (passwd.match(/(.*[!,@,#,$,%,^,&,*,?,_,~].*[!,@,#,$,%,^,&,*,?,_,~])/))
		{
			intScore = (intScore+5)
			strLog   = strLog + "5 points for at least two special chars\n"
		}
	
		
		// COMBOS
		if (passwd.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/))        // [verified] both upper and lower case
		{
			intScore = (intScore+2)
			strLog   = strLog + "2 combo points for upper and lower letters\n"
		}

		if (passwd.match(/([a-zA-Z])/) && passwd.match(/([0-9])/)) // [verified] both letters and numbers
		{
			intScore = (intScore+2)
			strLog   = strLog + "2 combo points for letters and numbers\n"
		}
 
									// [verified] letters, numbers, and special characters
		if (passwd.match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/))
		{
			intScore = (intScore+2)
			strLog   = strLog + "2 combo points for letters, numbers and special chars\n"
		}
	
	
		if(intScore < 12)
		{
		   strVerdict = msg_veryweak
		   strengthlevel = STRENGTH_VERY_WEAK
		}
		else if (intScore > 11 && intScore < 20)
		{
		   strVerdict = msg_weak
		   strengthlevel = STRENGTH_WEAK
		}
		else if (intScore > 19 && intScore < 27)
		{
		   strVerdict = msg_mediocre
		   strengthlevel = STRENGTH_MEDIOCRE
		}
		else if (intScore > 26 && intScore < 45)
		{
		   strVerdict = msg_strong
		   strengthlevel = STRENGTH_STRONG
		}
		else
		{
		   strVerdict = msg_verystrong
		   strengthlevel = STRENGTH_STRONGER
		}

		document.getElementById( 'accountForm:verdict' ).style.color = strengthcolors[ strengthlevel ];
		document.getElementById( 'accountForm:verdict' ).innerHTML = (strVerdict);
		
		strengthbarwidth=intScore*5;
		document.getElementById('accountForm:strengthbar').style.width = strengthbarwidth + "px";
		document.getElementById('accountForm:strengthbar').style.backgroundColor = strengthcolors[ strengthlevel ];
		
		document.getElementById( 'accountForm:score' ).value = (intScore)
		document.getElementById( 'accountForm:matchlog' ).value = (strLog)
	
}

</script>



	<h:form id="accountForm" rendered="#{sessionController.currentUser == null}">
						
		<e:panelGrid columns="4">
  			<e:outputLabel for="password" value="#{msgs[beanNewPassword.key]}"/>
	  		<e:inputSecret id="password" value="#{beanNewPassword.value}"
	     		required="#{beanNewPassword.required}" onkeyup="updatestrength( this.value, '#{msgs['PASSWORD.TEXT.PASSWORDSTRENGTH.VERYSTRONG']}', '#{msgs['PASSWORD.TEXT.PASSWORDSTRENGTH.STRONG']}', '#{msgs['PASSWORD.TEXT.PASSWORDSTRENGTH.MEDIUM']}', '#{msgs['PASSWORD.TEXT.PASSWORDSTRENGTH.WEAK']}', '#{msgs['PASSWORD.TEXT.PASSWORDSTRENGTH.VERYWEAK']}');" validator="#{beanNewPassword.validator.validate}" >
	  		</e:inputSecret>
	  		<h:graphicImage styleClass="helpTip" longdesc="#{msgs[beanNewPassword.help]}" value="/media/help.jpg"  style="border: 0;"/>
			<e:message for="password" />

      		<e:outputLabel for="verdict" value="#{msgs['PASSWORD.TEXT.PASSWORDSTRENGTH']}" />
      		<h:panelGroup>      		
	      		<h:outputText  id="verdict" value="#{msgs['PASSWORD.TEXT.PASSWORDSTRENGTH.VERYWEAK']}" />
	      		<t:htmlTag value="div" id="strengthbar" style="font-size: 1px; height: 3px; width: 0px; border: 1px solid white;" ></t:htmlTag>
	      	</h:panelGroup>	   
	      	<t:htmlTag value="span"/>   	      		      		      		
      		<e:message for="verdict" /> 
			
			 
  			<e:outputLabel for="password" value="#{msgs['PASSWORD.TEXT.LABEL.VERIFYPASSWORD']}" />
			<e:inputSecret id="verifyPassword" required="true"  >
				<t:validateEqual for="password" />
			</e:inputSecret>
			<t:htmlTag value="span"/>
  			<e:message for="verifyPassword" /> 
		</e:panelGrid>
									
		<t:div style="margin-top:30;">
		<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushChangePassword}" />
		</t:div>
	</h:form>
	
	<h:form>
		<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}"
			action="#{exceptionController.restart}" />
	</h:form>
	
	
<% /* @include file="_debug.jsp" */ %>
</e:page>