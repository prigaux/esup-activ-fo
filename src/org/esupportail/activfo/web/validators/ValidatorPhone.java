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
	
	private String caracterPattern;

	/**
	 * 
	 */

	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		
		if (value instanceof String) {
			String strValue = (String) value; 
			
			// 17 length : +33 1 62 00 00 00
			if ( strValue.length() != 17  || !strValue.contains("+33") ) {
				throw new ValidatorException(getFacesErrorMessage("VALIDATOR.PHONELENGTH.INVALID"));
			}
			
			String newString[]=null;
	    	String splitString=null;
	    	
			newString = strValue.split("\\+33");
			splitString=newString[1].replaceAll(" ", "");
			
			Pattern pattern = Pattern.compile(caracterPattern);
	        Matcher matcher = pattern.matcher(splitString);
	        
	        
	        if (!matcher.matches() ) {
				throw new ValidatorException(getFacesErrorMessage("VALIDATOR.PHONE.INVALID"));
			}
		} 
	}

	/**
	 * @return the caracterPattern
	 */
	public String getCaracterPattern() {
		return caracterPattern;
	}

	/**
	 * @param caracterPattern the caracterPattern to set
	 */
	public void setCaracterPattern(String caracterPattern) {
		this.caracterPattern = caracterPattern;
	}
	
	
}