package org.esupportail.activfo.web.validators;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.activfo.web.beans.BeanField;
import org.esupportail.activfo.web.beans.BeanMultiValue;
import org.esupportail.commons.beans.AbstractI18nAwareBean;

public class ValidatorBeanFieldOrINotEmpty extends AbstractI18nAwareBean implements Validator {
	
	/**
	 * Le beanField courant ou le beanField en paramètre ne doivent pas être vides en même temps.
	 * Un des deux champs est obligatoire
	 */
	private static final long serialVersionUID = 1L;
	private BeanField<String> beanField;
	private String errorMsg;

	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {					
			if(value!=null && value.toString().isEmpty()){
				List<BeanMultiValue> values=beanField.getValues();
				
				if(values.isEmpty() ||(!values.isEmpty() && values.get(0).getValue().isEmpty()))							
					throw new ValidatorException(getFacesErrorMessage(errorMsg));
			}
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