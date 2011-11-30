/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.activfo.web.converters;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.esupportail.activfo.domain.beans.Account;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;


/**
 * A JSF converter 
 * Renvoie la chaîne en remplaçant les '{attribute}' par leur valeur associée au compte de l'utilisateur courant  
 */
public class AttributesReplaceConverter implements Converter {
		
	private Account account;
	private String regex="(\\{([^{}]*)\\})";
		
	private final Logger logger = new LoggerImpl(getClass());
		
 
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

    	String text=value.toString();
    	Pattern p=Pattern.compile(regex);
   	  	Matcher m=p.matcher(text);
   	  	while(m.find()) 
   	  		text=text.replace(m.group(1), account.getAttribute(m.group(2)));
   	  	
   	  	logger.debug("Converted string : "+text);
    	
    	return text;
    }


	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return the regex
	 */
	public String getRegex() {
		return regex;
	}

	/**
	 * @param regex the regex to set
	 */
	public void setRegex(String regex) {
		this.regex = regex;
	}
		
}