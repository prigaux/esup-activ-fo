
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
    	String returnValue=null;
    	if ("".equals(strValue))
    		return 	value;
    	else if (strValue.length()!=10)
    		return strValue;
    	else {
	    	for(int i=0;i<5;i++) {
	    		if (i==0) returnValue="+33 "+strValue.substring(1,2);
	    		else returnValue+=strValue.substring(i*2, i*2+2);
	    		if (i!=4) returnValue+=" ";
	    	}
    	}
        return returnValue;
    }
 
  //Affichage standard
    public String getAsString(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final Object value) {
    	String strValue = (String) value;
    	String newString[]=null;
    	String splitString=null;
    	String returnValue=null;
    	
    	if ("".equals(strValue))
    		returnValue=null;
    	else {
    		newString = strValue.split("\\+33");
    		splitString=newString[1].replaceAll(" ", "");
    		returnValue="0"+splitString;
    	}
    	
    	return returnValue;
    }
	
}