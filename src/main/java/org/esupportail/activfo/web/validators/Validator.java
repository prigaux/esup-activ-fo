package org.esupportail.activfo.web.validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public interface Validator  {
	
	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException;
		
}