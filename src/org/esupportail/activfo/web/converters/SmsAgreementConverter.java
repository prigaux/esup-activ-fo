/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.activfo.web.converters;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.util.StringUtils;

/**
 * A JSF converter to pass Integer instances.
 */
public class SmsAgreementConverter implements Converter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Account currentAccount;
	private final Logger logger = new LoggerImpl(getClass());
	private String smsAccepted;

	

	/**
	 * Bean constructor.
	 */
	public SmsAgreementConverter() {
		super();
		
	}

	/**
	 * @see javax.faces.convert.Converter#getAsObject(
	 * javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	
	//affichage ldap
	public Object getAsObject(
			@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final String value) {
		
		
		if (value.equals("true")){
			currentAccount.setSmsAgreement(smsAccepted);
			return smsAccepted;
		}
		else{
			currentAccount.setSmsAgreement(null);
			return null;
		}
	}

	
	//Affichage standard
	/**
	 * @see javax.faces.convert.Converter#getAsString(
	 * javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(
			@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final Object value) {
						
		String val=(String)value;
		
		if (smsAccepted.equals(currentAccount.getSmsAgreement()))
				return "true";
		
		else
			return "false";
	
	}

	public Account getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(Account currentAccount) {
		this.currentAccount = currentAccount;
	}

	public String getSmsAccepted() {
		return smsAccepted;
	}

	public void setSmsAccepted(String smsAccepted) {
		this.smsAccepted = smsAccepted;
	}
	
	
	
	
	
	
	
}
