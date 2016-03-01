/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.activfo.web.converters;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

/**
 * A JSF converter to pass Integer instances.
 */
public class MapOrEqualConverter implements Converter {
	
	private Map<String,String> mapping = new HashMap<String,String>();
	
	private final Logger logger = new LoggerImpl(getClass());

	public MapOrEqualConverter() {
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
     	
    	if (mapping.get(value)==null) return value.toString();
    	else return mapping.get(value).toString();
    }

	/**
	 * @return the mapping
	 */
	public Map<String, String> getMapping() {
		return mapping;
	}

	/**
	 * @param mapping the mapping to set
	 */
	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}
	
	

		
}