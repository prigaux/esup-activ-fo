package org.esupportail.activfo.web.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.commons.beans.AbstractI18nAwareBean;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

public class ValidatorPhone extends AbstractI18nAwareBean implements Validator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * 
	 */

	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		
		if (value instanceof String) {
			String strValue = (String) value; //+33 1 44 07 89 65
			String expression = "^\\+33\\s1[0-9 ]{12}|^\\+331[0-9 ]";
			Pattern pattern = Pattern.compile(expression);
	        Matcher matcher = pattern.matcher(strValue);
	        
			if (!matcher.matches()) {
				throw new ValidatorException(getFacesErrorMessage("VALIDATOR.PHONE.INVALID"));
			}
		}
	}
}