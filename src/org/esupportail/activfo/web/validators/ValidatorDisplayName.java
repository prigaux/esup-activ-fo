package org.esupportail.activfo.web.validators;


import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.activfo.domain.tools.StringTools;
import org.esupportail.activfo.web.beans.BeanField;
import org.esupportail.commons.beans.AbstractI18nAwareBean;


public class ValidatorDisplayName extends AbstractI18nAwareBean implements Validator{
	
	
	private Account account;
	
	private List<BeanField> liste;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8849185735359561457L;

	/**
	 * 
	 */
	
	
	

	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		String val=(String)value;
		
		if (StringTools.compareInsensitive(account.getDisplayName(), val)) {
			
			
		}
		else{
			throw new ValidatorException(getFacesErrorMessage("VALIDATOR.DISPLAYNAME.INVALID"));
		}
			
		
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
}
