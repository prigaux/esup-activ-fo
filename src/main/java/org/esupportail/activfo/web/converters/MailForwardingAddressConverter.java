/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.activfo.web.converters;

import java.util.ArrayList;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.esupportail.activfo.web.beans.BeanField;
import org.esupportail.activfo.web.beans.BeanMultiValue;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

/**
 * Afficher tous les mailForwardingAddress différents de supannMailPerso
 */
public class MailForwardingAddressConverter implements Converter {
	
	private final Logger logger = new LoggerImpl(getClass());
	private  BeanField<String> beanField;

	public MailForwardingAddressConverter() {
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
			Object value) {
    		String mail = null;
    		boolean b=false;
    		ArrayList<BeanMultiValue> values=(ArrayList<BeanMultiValue>) beanField.getValues();
    		
    		for (BeanMultiValue listVal : values){    			
    			if(listVal.getValue().equals(value)){
    				value=null;
    				break;
        		}
    		}
    		
    		return (String) value;
    		
    		
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