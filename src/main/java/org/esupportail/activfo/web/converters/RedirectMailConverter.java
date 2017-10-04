/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.activfo.web.converters;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.esupportail.activfo.web.beans.BeanField;
import org.esupportail.activfo.web.beans.BeanMultiValue;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;


/**
 * A JSF converter 
 * permet d'utiliser la dernière valeur saisie par l'utilisateur pour alimenter la valeur à renvoyer au BO lorsque l'utilisateur coche "Redirection de mail"
 */
public class RedirectMailConverter implements Converter {
	
	
	private final Logger logger = new LoggerImpl(getClass());
	private  BeanField<String> beanField;
	
	 //valeur utilisée pour être envoyée au bo
    public Object getAsObject(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final String value){
    	
		String convertedValue=value;
	    if(!convertedValue.startsWith("\\")){
	    	List<BeanMultiValue> values=beanField.getValues();
	    	convertedValue=values.get(0).getValue();
	    }

		return convertedValue;        
    }
    
    public String getAsString(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final Object value) {
        	
    		return value.toString();
    }

	/**
	 * @return the beanField
	 */
	public BeanField<String> getBeanField() {
		return beanField;
	}

	/**
	 * @param beanField the beanField to set
	 */
	public void setBeanField(BeanField<String> beanField) {
		this.beanField = beanField;
	}

}