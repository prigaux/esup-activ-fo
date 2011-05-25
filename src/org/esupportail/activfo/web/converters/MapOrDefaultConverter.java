/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.activfo.web.converters;

import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

/**
 * A JSF converter to pass Integer instances.
 */
public class MapOrDefaultConverter implements Converter {
	
	private HashMap<String,String> mapping = new HashMap<String,String>();
	
	private String defaultValue="";
	
	private final Logger logger = new LoggerImpl(getClass());

	public MapOrDefaultConverter() {
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
     	
    	
    	if (mapping.get(value)==null) {
    		return getDefaultValue();
    	}
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
	
	public String getDefaultValue() {
		return defaultValue;
	}

		
}