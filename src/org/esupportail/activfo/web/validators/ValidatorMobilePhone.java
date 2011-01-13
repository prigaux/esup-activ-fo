package org.esupportail.activfo.web.validators;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.commons.beans.AbstractI18nAwareBean;


public class ValidatorMobilePhone extends AbstractI18nAwareBean implements Validator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	
	

	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		
		if (value instanceof String) {
			String strValue = (String) value;
			if (!strValue.matches("^0[6|7][0-9]{8}$|^00[0-9]{11,13}$")) {
				throw new ValidatorException(getFacesErrorMessage("VALIDATOR.MOBILE.INVALID"));
			}
		
		}
		
	}
	
	
}
