package org.esupportail.activfo.web.validators;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.ValidatorException;

import org.esupportail.commons.beans.AbstractI18nAwareBean;

public class ValidatorList extends AbstractI18nAwareBean implements Validator {
	
	/**
	 * Validation via une succession de validateur
	 * Si un convertisseur est disponible, c'est la valeur convertie qui est passée aux validateurs
	 */
	private static final long serialVersionUID = 1L;
	private List<Validator> validators;	
	private Converter converter;
	

	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {					
		if (value instanceof String) {
			String strValue=value.toString();
			if(converter!=null)
				strValue = converter.getAsString(null, null, strValue);
			
			for(Validator v:validators)
				v.validate(context, componentToValidate, strValue);						
		}
	}


	/**
	 * @return the validators
	 */
	public List<Validator> getValidators() {
		return validators;
	}

	/**
	 * @param validators the validators to set
	 */
	public void setValidators(List<Validator> validators) {
		this.validators = validators;
	}


	/**
	 * @return the converter
	 */
	public Converter getConverter() {
		return converter;
	}


	/**
	 * @param converter the converter to set
	 */
	public void setConverter(Converter converter) {
		this.converter = converter;
	}
}