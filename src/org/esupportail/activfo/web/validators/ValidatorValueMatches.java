package org.esupportail.activfo.web.validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.commons.beans.AbstractI18nAwareBean;

public class ValidatorValueMatches extends AbstractI18nAwareBean implements Validator {
	
	/**
	 * La valeur du champ doit matcher l'expression regulière
	 */
	private static final long serialVersionUID = 1L;
	private String regex;
	private String errorMsg;

	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		
		if (value instanceof String) {
			String strValue = (String) value;
			if (!strValue.matches(regex)) {
				throw new ValidatorException(getFacesErrorMessage(errorMsg));
			}
		}
	}

	/**
	 * @return the regex
	 */
	public String getRegex() {
		return regex;
	}

	/**
	 * @param regex the regex to set
	 */
	public void setRegex(String regex) {
		this.regex = regex;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}