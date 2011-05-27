package org.esupportail.activfo.web.validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.commons.beans.AbstractI18nAwareBean;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

public class ValidatorEmail extends AbstractI18nAwareBean implements Validator  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8849185735359561457L;
	
	private final Logger logger = new LoggerImpl(getClass());
	
	private String forbiddenEmail=".*univ-paris1.*";

	/**
	 * 
	 */

	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		
		if (value instanceof String) {
			String strValue = (String) value;
			if (!strValue.matches("^[a-zA-Z0-9._-]+@[a-z0-9.-]{2,}[.][a-z]{2,4}$")) {
				throw new ValidatorException(getFacesErrorMessage("VALIDATOR.EMAIL.INVALID"));
			}
			
			if (strValue.matches(getForbiddenEmail())) {
				throw new ValidatorException(getFacesErrorMessage("VALIDATOR.EMAIL.FORBIDDEN")); 
			}
		
		}
	}

	/**
	 * @return the forbiddenEmail
	 */
	public String getForbiddenEmail() {
		return forbiddenEmail;
	}

	/**
	 * @param forbiddenEmail the forbiddenEmail to set
	 */
	public void setForbiddenEmail(String forbiddenEmail) {
		this.forbiddenEmail = forbiddenEmail;
	}
	
	
		
}