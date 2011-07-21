package org.esupportail.activfo.web.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.esupportail.commons.services.ldap.LdapEntity;
import org.esupportail.commons.services.ldap.LdapEntityService;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

import javax.faces.model.SelectItem;

public class LdapStructureList extends ArrayList<SelectItem>  {
	
	private final Logger logger = new LoggerImpl(getClass());
	
	private LdapEntityService ldapEntityService;
	
	private HashMap<String,String> ldapStructureList;
	
	
	
	/**
	 * @return the selectItemList
	 */
	public HashMap<String, String> getLdapStructureList() {
		return new HashMap<String,String>();
	}

	/**
	 * @param selectItemList the selectItemList to set
	 */
	public void setLdapStructureList(HashMap<String, String> ldapStructureList) {
		
		this.ldapStructureList = ldapStructureList;
		
    	/*
    	returnValue=ldapentite.get(0).getAttribute("description");
        
    	logger.debug("return value is "+returnValue);
		*/
		
		
		Set<String> keys = ldapStructureList.keySet();
		for(String key: keys) {
			String s = ldapStructureList.get(key);
			SelectItem si = new SelectItem();
			si.setLabel(s);
			si.setValue(key);
			this.add(si);
		}
	}
	
	public List<SelectItem> getStructureList() {
		
		String ret=null;
		
		String filter = "(supannCodeEntite=*)";
    	List<LdapEntity> ldapentite = ldapEntityService.getLdapEntitiesFromFilter(filter);
        ret=ldapentite.get(0).getAttribute("description");
        
    	logger.debug("return value is "+ret);
		return null;
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
		
}
