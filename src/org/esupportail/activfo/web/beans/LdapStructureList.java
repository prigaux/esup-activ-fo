package org.esupportail.activfo.web.beans;

import java.util.ArrayList;
import java.util.List;

import org.esupportail.commons.services.ldap.LdapEntity;
import org.esupportail.commons.services.ldap.LdapEntityService;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.beans.factory.InitializingBean;

import javax.faces.model.SelectItem;

public class LdapStructureList extends ArrayList<SelectItem> implements InitializingBean {
	
	private final Logger logger = new LoggerImpl(getClass());
	
	private LdapEntityService ldapEntityService;
	
	private String ldapSearchFilter;
	
	public void afterPropertiesSet() throws Exception {
		
		List<LdapEntity> ldapEntities = ldapEntityService.getLdapEntitiesFromFilter(ldapSearchFilter);
		logger.debug("filter is "+ldapSearchFilter);
		
		for(int i=0;i<ldapEntities.size();i++)
		{
			SelectItem si = new SelectItem();
			si.setLabel(ldapEntities.get(i).getAttribute("description"));
			si.setValue(ldapEntities.get(i).getAttribute("supannCodeEntite"));
			this.add(si);
		}
	}
	
	/**
	 * @return the ldapEntityService
	 */
	public LdapEntityService getLdapEntityService() {
		return ldapEntityService;
	}

	/**
	 * @param ldapEntityService the ldapEntityService to set
	 */
	public void setLdapEntityService(LdapEntityService ldapEntityService) {
		this.ldapEntityService = ldapEntityService;
	}

	/**
	 * @return the ldapSearchFilter
	 */
	public String getLdapSearchFilter() {
		return ldapSearchFilter;
	}


	/**
	 * @param ldapSearchFilter the ldapSearchFilter to set
	 */
	public void setLdapSearchFilter(String ldapSearchFilter) {
		this.ldapSearchFilter = ldapSearchFilter;
	}
		
}
