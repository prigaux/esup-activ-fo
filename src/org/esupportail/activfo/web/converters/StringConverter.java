/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.activfo.web.converters;

import java.text.Normalizer;
import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.esupportail.activfo.domain.beans.Account;

/**
 * A JSF converter to pass Integer instances.
 */
public class StringConverter implements Converter {
	
	//private Account account;
	//private String eduPersonPrimaryAffiliation;
	private HashMap<String,String> mapping = new HashMap<String,String>();

	public StringConverter() {
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
    	
    	if (mapping.get(value)==null) return (String) value;
    	else return mapping.get(value);
    }

	/**
	 * @return the mapping
	 */
	public HashMap<String, String> getMapping() {
		return mapping;
	}

	/**
	 * @param mapping the mapping to set
	 */
	public void setMapping(HashMap<String, String> mapping) {
		this.mapping = mapping;
	}
	
	

		
}