package org.esupportail.activfo.web.converters;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.esupportail.commons.services.ldap.LdapEntity;
import org.esupportail.commons.services.ldap.LdapEntityService;
import org.esupportail.commons.services.ldap.LdapException;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;


/**
 * To converter ldap code value to a display value
 */
public class LdapActiviteConverter implements Converter {
	
	private LdapEntityService ldapEntityService;
	private String attribute="up1TableKey";
	private String displayValue="displayName";
	private String subFilter = "(up1TableName=supannActivite)(objectClass=up1TableEntry)";
	
	//Récupérer le displayName de SupannActivite
	
	//private Map<String,String> codes=new HashMap<String,String>(); 
	
	private final Logger logger = new LoggerImpl(getClass());
	
	public LdapActiviteConverter() {
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

    	String code = value.toString();
    	
    	if(code.isEmpty()||code==null) return code;
    	
    	String filter = "(&("+attribute+"="+code+")" + subFilter + ")";
    	String convertedValue=code;
    	try {
    	List<LdapEntity> ldapEntities = ldapEntityService.getLdapEntitiesFromFilter(filter);    	
    	if(!ldapEntities.isEmpty()){
    		convertedValue=ldapEntities.get(0).getAttribute(displayValue);
    		//codes.put(convertedValue, code);
    	}
    	else logger.warn("La valeur de rattachement "+code+" n'existe pas");
    	}
    	catch(LdapException e)
    	{
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

	/**
	 * @return the attribute
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return the displayValue
	 */
	public String getDisplayValue() {
		return displayValue;
	}

	/**
	 * @param displayValue the displayValue to set
	 */
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
		
}
