/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.activfo.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


/**
 * A JSF converter to convert empty string to string contains emptyValue
 */
public class EmptyConverter implements Converter {
	
	private String emptyValue="";
	

    public Object getAsObject(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final String value){
    	if(context!=null && value!=null && value.isEmpty())
            return emptyValue;  
    	
    	if(context==null && value!=null && value.equals(emptyValue))
    		return "";
    	
    	return value;
    }
 
    public String getAsString(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final Object value) {
    	
     if(value!=null)	
    	return value.toString().replace(emptyValue, "");
     
     return null; 
    	
    }
    
	/**
	 * @return the emptyValue
	 */
	public String getEmptyValue() {
		return emptyValue;
	}

	/**
	 * @param emptyValue the emptyValue to set
	 */
	public void setEmptyValue(String emptyValue) {
		this.emptyValue = emptyValue;
	}
	

		
}