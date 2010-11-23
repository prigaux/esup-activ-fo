<%@include file="_include.jsp"%>
<e:page stringsVar="msgs" menuItem="account"
	locale="#{sessionController.locale}">
	<%@include file="_navigation.jsp"%>
	<e:section value="#{msgs['CHARTER.TITLE']}" />
	
	<t:div>
	<table  border="0"  cellpadding="0" cellspacing="0" >
	<tr>
		<td><img src="/media/bouton-4etape-roll_01.jpg"></td>
		<td><img src="/media/bouton-4etape-roll_02.jpg"></td>
		<td><img src="/media/bouton-4etape_03.jpg"></td>
		<td><img src="/media/bouton-4etape-roll_04.jpg"></td>
	</tr>
    </table>
    </t:div>
	
	<e:messages />
	
	<e:paragraph escape="false" value="#{msgs['CHARTER.TEXT.TOP']}" />

	<h:form id="accountForm"
		rendered="#{sessionController.currentUser == null}">

		<e:outputLabel for="charterAgreement" value="#{msgs['CHARTER.TEXT.AGREE']}" />
		<e:selectBooleanCheckbox id="charterAgreement" value="#{accountController.currentAccount.charterAgreement}" required="true" />
		<t:div style="margin-top:30;">
			<e:commandButton value="#{msgs['_.BUTTON.CONFIRM']}" action="#{accountController.pushCharterAgreement}" />
		</t:div>
	</h:form>
	
	<h:form>
		<e:commandButton value="#{msgs['APPLICATION.BUTTON.RESTART']}" action="#{exceptionController.restart}" />
	</h:form>

	<%
	/* @include file="_debug.jsp" */
	%>
</e:page>