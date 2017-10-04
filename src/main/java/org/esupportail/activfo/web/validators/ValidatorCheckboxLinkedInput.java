package org.esupportail.activfo.web.validators;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.activfo.web.beans.BeanField;
import org.esupportail.activfo.web.beans.BeanMultiValue;
import org.esupportail.commons.beans.AbstractI18nAwareBean;
/**
 * @author aanli
 * 
 * Validateur d'un checkbox lié à un input (passé via beanField). 
 * Lance ValidatorException si le checkbox est coché et que l'Input est vide.
 */

public class ValidatorCheckboxLinkedInput extends AbstractI18nAwareBean implements Validator{
	

	private static final long serialVersionUID = 8849185735359561457L;

	private  BeanField<String> beanField;
	private String errorMessage;

	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {		
		ArrayList<String> selectedValues=(ArrayList<String>)value;
		
		if(!selectedValues.isEmpty()){
			List<BeanMultiValue> values=beanField.getValues();

			if(!values.isEmpty()&&values.get(0).getValue().equals("")) //erreur de validation si la 1ère valeur du beanField lié est vide
				throw new ValidatorException(getFacesErrorMessage(errorMessage));
		}
		
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

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set if the validator fails
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}