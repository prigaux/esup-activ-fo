
/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.activfo.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;


/**
 * A JSF converter to pass Integer instances.
 */
public class LdapPhoneFaxConverter implements Converter {
	
	private final Logger logger = new LoggerImpl(getClass());
	
	public LdapPhoneFaxConverter() {
    }
 
    public Object getAsObject(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final String value){
    	
    	
    	String strValue=(String) value;
    	if ("".equals(strValue))
    		return 	value;
    	else if (strValue.length()!=10)
    		return strValue;
    	else {
    		strValue = strValue.replaceAll("(..)(..)(..)(..)(..)", "$1 $2 $3 $4 $5");
	    	strValue = strValue.replaceAll("^0", "+33 "); 
    	}
        return strValue;
    }
 
  //Affichage standard
    public String getAsString(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final Object value) {
    	String strValue = (String) value;
    	
    	if ("".equals(strValue))
    		return null;
    	else
    		strValue=strValue.replaceAll(" ", "").replaceAll("^\\+330", "0").replaceAll("^\\+33", "0");
    	
    	return strValue;
    }
}