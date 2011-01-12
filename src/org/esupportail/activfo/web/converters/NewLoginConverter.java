/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.activfo.web.converters;

import java.text.Normalizer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.esupportail.activfo.domain.beans.Account;

/**
 * A JSF converter to pass Integer instances.
 */
public class NewLoginConverter implements Converter {
	
	private Account account;
	private String displayName;
	
    

	public NewLoginConverter() {
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
    	
    	String stringTrim[] = null;
    	String newLogin="";
    	String displayNameOrigin = account.getAttribute(this.displayName);
    	String displayNameNormalize = Normalizer.normalize(displayNameOrigin, Normalizer.Form.NFD);
		String displayNameChange=displayNameNormalize.replaceAll("[^\\p{ASCII}]","");
    	stringTrim=displayNameChange.split(" ");
    	
    	for(int i=0;i<stringTrim.length;i++) {
    		if (i!=stringTrim.length-1)
    			newLogin+=stringTrim[i].substring(0,1);
    		else newLogin+=stringTrim[i];
    	}
      return newLogin.trim().toLowerCase();
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
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
    
   
	
}
