		<div class="row"  style="height:70px;">
		<!-- Menu principal -->
			<div class="col-md-offset-3 col-md-9">
	     	    <ul class="nav nav-pills">
			      <li><a href="#" 	onclick="simulateLinkClick('restart:restartButton');"><span class="glyphicon glyphicon-home"></span>Accueil</a></li>
			      <li><a href="#"	onclick="simulateLinkClick('accountForm:preview');"><span class="glyphicon glyphicon-edit"></span><t:outputText value="#{msgs['DATACHANGE.MODIFICATION.TEXT']}" /></a></li>
			      <li><a href="#" 	onclick="simulateLinkClick('accountForm:next');"><span class="glyphicon glyphicon-list-alt"></span><t:outputText value="#{msgs['DATACHANGE.DISPLAY.TEXT']}"/></a></li>
			    </ul>
		 	</div>
		 
	    </div>
	    <div class="row">
			<div class="col-md-offset-3 col-md-4">
			   <e:paragraph styleClass="text-success" escape="false" value="#{msgs['PERSOINFO.MESSAGE.CHANGE.SUCCESSFULL']}" rendered="#{accountController.viewDataChange}"/>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-3">
			<!-- Sous menus -->
			 	<ul class="nav nav-pills nav-stacked sidenav">
					<div class="breadcrumbVertical">
						<t:outputText value="#{msgs['DATACHANGE.MODIFICATION.TEXT']}" rendered="#{!accountController.viewDataChange}"/>
						<t:outputText value="#{msgs['DATACHANGE.DISPLAY.TEXT']}" rendered="#{accountController.viewDataChange}"/>
					</div>
				 	<t:dataList value="#{accountController.beanData}"  var="category">
						<t:htmlTag styleClass="homeStep" value="li" rendered="#{category.access}" >
							<t:htmlTag  value="a" styleClass="hrefId">
								<t:htmlTag  value="span" styleClass="glyphicon #{msgs[category.icone]}"></t:htmlTag>								
								<h:outputText rendered="#{category.access}" value="#{msgs[category.title]}" />	
								<t:htmlTag  value="span" styleClass="glyphicon glyphicon-chevron-right"></t:htmlTag>
							</t:htmlTag>
						</t:htmlTag>
					</t:dataList>
				</ul>
			</div>
			