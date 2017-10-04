
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
 * A JSF converter to delete "$" in the diplay of postal address
 */
public class PostalAddressConverter implements Converter {
	
	private final Logger logger = new LoggerImpl(getClass());
	
	private String initialValue;
	
	//envoi au BO
    public Object getAsObject(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final String value){
    	//retourne la valeur initiale si elle n'a pas été modifiée par l'utilisateur
    	if(initialValue.replaceAll("\\$"," ").equals(value))
    		return initialValue;
    	else
    		return value;
    }
 
  //Affichage standard
    public String getAsString(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final Object value) {
    	initialValue = (String) value;
    	return initialValue.replaceAll("\\$", " ");
    }
	
}