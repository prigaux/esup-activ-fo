		<div class="row"  style="height:70px;">
		<!-- Menu principal -->
			<div class="col-md-offset-3 col-md-9">
	     	    <ul class="nav nav-pills">
			      <li><a href="#"	onclick="simulateLinkClick('accountForm:toDataChange');"><span class="glyphicon glyphicon-edit"></span><t:outputText value="#{msgs['DATACHANGE.MODIFICATION.TEXT']}" /></a></li>
			      <li><a href="#" 	onclick="simulateLinkClick('accountForm:toDataView');"><span class="glyphicon glyphicon-list-alt"></span><t:outputText value="#{msgs['DATACHANGE.DISPLAY.TEXT']}"/></a></li>
			    </ul>
		 	</div>
		 
	    </div>
		<div class="row">
			<div class="col-md-3">
			<!-- Sous menus -->
			 	<ul class="nav nav-stacked sidenav">
				 	<t:dataList value="#{accountController.beanData}"  var="category">
						<t:htmlTag styleClass="homeStep" value="li" rendered="#{category.access}" >
							<t:htmlTag  value="a" styleClass="hrefId">
								<h:outputText rendered="#{category.access}" value="#{msgs[category.title]}" />	
								<t:htmlTag  value="span" styleClass="glyphicon glyphicon-chevron-right"></t:htmlTag>
							</t:htmlTag>
						</t:htmlTag>
					</t:dataList>
				</ul>
			</div>
			