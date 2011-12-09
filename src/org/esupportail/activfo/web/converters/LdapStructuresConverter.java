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
public class LdapStructuresConverter implements Converter {
	
	private LdapEntityService ldapEntityService;
	
	private final Logger logger = new LoggerImpl(getClass());
	
	public LdapStructuresConverter() {
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
    	
    	if(base.equals("")||base==null) return base;
    	
    	String filter = "(supannCodeEntite="+base+")";
    	String convertedValue=base;
    	try {
    	List<LdapEntity> ldapentite = ldapEntityService.getLdapEntitiesFromFilter(filter);
    	if(ldapentite.size()>0)
    		convertedValue=ldapentite.get(0).getAttribute("description");
    	else logger.debug("La valeur de rattachement "+base+" n'existe pas");
    	} catch (Exception e) {
    		logger.error(e);
    	}
    	return convertedValue;
    	
    
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