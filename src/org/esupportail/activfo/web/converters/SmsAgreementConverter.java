/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.activfo.web.converters;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.util.StringUtils;

/**
 * A JSF converter to pass Integer instances.
 */
public class SmsAgreementConverter implements Converter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

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
		
		System.out.println("koukkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk888888888888");
		
		if (!StringUtils.hasText(value)) {
			return null;
		}
		if (value.equals("true")){
			return "{}";
		}
		else
			return "";
		
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
		System.out.println("koukkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"+val);		
		if (value == null || !StringUtils.hasText(value.toString())) {
			return "false";
		}
		
		
		
		if (val.equals("{lol}")){
			System.out.println("koukkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
			return "true";
		}
		else
			System.out.println("koukkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk222");
			return "false";
		
	}
	
	
	
}
