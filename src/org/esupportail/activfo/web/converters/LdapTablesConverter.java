/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
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
public class LdapTablesConverter implements Converter {
	
	private LdapEntityService ldapEntityService;
	private String attribute="supannRoleGenerique";
	private String displayValue="displayName";
	private String dn="entryDN";
	private String branch="ou=supannRoleGenerique,ou=tables,dc=univ-paris1,dc=fr";
	
	//permet de renvoyer les codes associés au displayValue.
	//il y a une bijection entre displayValue et code
	private Map<String,String> codes=new HashMap<String,String>(); 
	
	private final Logger logger = new LoggerImpl(getClass());
	
	public LdapTablesConverter() {
    }
 
    public Object getAsObject(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final String value){
    	if(codes.containsKey(value))
    		return codes.get(value);
    	else
    		return value;
    }
 
    public String getAsString(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final Object value) {

    	String code = value.toString();
    	
    	if(code.isEmpty()||code==null) return code;
    	
    	String filter = "("+dn+"="+attribute+"="+code+","+branch+")";    	
    	String convertedValue=code;
    	try {
    	List<LdapEntity> ldapEntities = ldapEntityService.getLdapEntitiesFromFilter(filter);    	
    	if(!ldapEntities.isEmpty()){
    		convertedValue=ldapEntities.get(0).getAttribute(displayValue);
    		codes.put(convertedValue, code);
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

	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @return the dn
	 */
	public String getDn() {
		return dn;
	}

	/**
	 * @param dn the dn to set
	 */
	public void setDn(String dn) {
		this.dn = dn;
	}	
		
}