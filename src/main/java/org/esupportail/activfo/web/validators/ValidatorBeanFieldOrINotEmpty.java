package org.esupportail.activfo.web.validators;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.activfo.web.beans.BeanField;
import org.esupportail.commons.beans.AbstractI18nAwareBean;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

public class ValidatorBeanFieldOrINotEmpty extends AbstractI18nAwareBean implements Validator {

	/**
	 * Un des deux champs est obligatoire
	 */
	private static final long serialVersionUID = 1L;
	private String errorMsg;
	private final Logger logger = new LoggerImpl(getClass());
	private List<BeanField> beanFields;

	@Override
	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
			List<BeanField> listBeanField = new ArrayList<BeanField>();
			logger.debug("ValidatorBeanFieldOrINotEmpty");;
			boolean valuesEmpty=true;
			for(BeanField bean:beanFields){
				//Faire bean.getValues() avant bean.getValue() car la valeur d'un des 2 bean n'est pas à jour (getValues dans BeanFieldImpl fait des get ET updates)
				bean.getValues();
				String val=(String) bean.getValue();
				if (!bean.getValue().toString().isEmpty()) {
					valuesEmpty=false;}
			}
			if (valuesEmpty) throw new ValidatorException(getFacesErrorMessage(errorMsg));


	}



	public String getErrorMsg() {
		return errorMsg;
	}



	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}



	public List<BeanField> getBeanFields() {
		return beanFields;
	}


	public void setBeanFields(List<BeanField> beanFields) {
		this.beanFields = beanFields;
	}


}