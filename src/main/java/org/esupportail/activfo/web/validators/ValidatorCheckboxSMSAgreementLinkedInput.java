package org.esupportail.activfo.web.validators;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.activfo.web.beans.BeanField;
import org.esupportail.activfo.web.beans.BeanMultiValue;
import org.esupportail.commons.beans.AbstractI18nAwareBean;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
/**
 * @author csar
 * 
 * Validateur du checkbox SMSAgreement lié à un input (passé via beanField). 
 * Lance ValidatorException si le checkbox est coché (autre que l'autorisation photo, ce dernier n'a pas besoin d'être controlé) et que l'Input est vide.
 */

public class ValidatorCheckboxSMSAgreementLinkedInput extends AbstractI18nAwareBean implements Validator{
	

	private static final long serialVersionUID = 8849185735359561457L;

	private  BeanField<String> beanField;
	private String errorMessage;
	private boolean verify=false;
	private final Logger logger = new LoggerImpl(getClass());
    
	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {		
		ArrayList<String> selectedValues=(ArrayList<String>)value;

        logger.debug("ValidatorCheckboxSMSAgreementLinkedInput " + value + " vs " + (beanField.getValues().isEmpty() ? "null" : beanField.getValues().get(0).getValue()));

		if(!selectedValues.isEmpty()){
		for(int i=0;i<selectedValues.size();i++){
			if((!selectedValues.get(i).isEmpty() && i>=1) || (i==0 && !selectedValues.get(i).equals("{PHOTO}ACTIVE") )){
				verify=true;
				break;
					
				}
			}
		}
		
		
		if(verify ){			
			List<BeanMultiValue> values=beanField.getValues();

			if(!values.isEmpty()&&values.get(0).getValue().equals("")) {//erreur de validation si la 1ère valeur du beanField lié est vide
				logger.info("ValidatorCheckboxSMSAgreementLinkedInput " + errorMessage + " " + selectedValues + " " + values);
				throw new ValidatorException(getFacesErrorMessage(errorMessage));
			}
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