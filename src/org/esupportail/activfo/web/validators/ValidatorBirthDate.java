package org.esupportail.activfo.web.validators;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.commons.beans.AbstractI18nAwareBean;


public class ValidatorBirthDate extends AbstractI18nAwareBean implements Validator{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8849185735359561457L;
	
	private String formatLdapDate;
	
	/**
	 * 
	 */
	
	
	

	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		if (value instanceof String) {
			String strValue = (String) value;
			SimpleDateFormat sdf = new SimpleDateFormat(formatLdapDate);
			Date testDate;
			
			try{
				testDate = sdf.parse(strValue);
			}

			catch (ParseException e){
				throw new ValidatorException(getFacesErrorMessage("VALIDATOR.DATE.FORMAT.INVALID"));
			}

			  
			if (!sdf.format(testDate).equals(strValue)){
				throw new ValidatorException(getFacesErrorMessage("VALIDATOR.DATE.FORMAT.INVALID"));
			}
			   
			
		}
		else{ 
			throw new ValidatorException(getFacesErrorMessage("VALIDATOR.NOTSTRING"));
		}
	}

	public String getFormatLdapDate() {
		return formatLdapDate;
	}

	public void setFormatLdapDate(String formatLdapDate) {
		this.formatLdapDate = formatLdapDate;
	}
	
}
