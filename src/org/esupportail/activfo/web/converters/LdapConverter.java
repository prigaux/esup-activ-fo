/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.activfo.web.converters;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.esupportail.commons.services.ldap.LdapEntity;
import org.esupportail.commons.services.ldap.LdapEntityService;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;


/**
 * A JSF converter to pass Integer instances.
 */
public class LdapConverter implements Converter {
	
	private LdapEntityService ldapEntityService;
	
	private final Logger logger = new LoggerImpl(getClass());
	
	public LdapConverter() {
    }
 
    public Object getAsObject(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final String value){
        return value;
    }
 
    public String getAsString(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final Object value) {

 
    	String base = value.toString();
    	
    	String filter = "(supannCodeEntite="+base+")";
    	String returnValue=null;
    	try {
    	List<LdapEntity> ldapentite = ldapEntityService.getLdapEntitiesFromFilter(filter);
    	returnValue=ldapentite.get(0).getAttribute("description");
    	} catch (Exception e) {
    		logger.debug("La valeur de rattachement "+base+" n'existe pas : "+e);
    	}
    	return returnValue;
    	
    
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