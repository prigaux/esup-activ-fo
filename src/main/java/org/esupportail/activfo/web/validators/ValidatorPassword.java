package org.esupportail.activfo.web.validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.commons.beans.AbstractI18nAwareBean;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

import org.apache.commons.lang.CharUtils;



public class ValidatorPassword extends AbstractI18nAwareBean implements Validator{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8849185735359561457L;
	
	
	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
				if (value instanceof String) {
				String passwd = (String) value;
				
				for(int index=0;index<passwd.length();index++) 
					if (!CharUtils.isAsciiPrintable(passwd.charAt(index)) ) 
						
						throw new ValidatorException(getFacesErrorMessage("VALIDATOR.PASSWORD.CARACTERFORBIDDEN",passwd.charAt(index)));
				
				
		}
	}

	
}