package org.esupportail.activfo.web.validators;



import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.commons.beans.AbstractI18nAwareBean;


public class ValidatorLogin extends AbstractI18nAwareBean implements Validator{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8849185735359561457L;
	
	private Account account;
	private String displayNameAttr;

	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		String val=(String)value;
		
		if(!isPermitLogin(val)) throw new ValidatorException(getFacesErrorMessage("VALIDATOR.LOGIN.INVALID"));
		
	}
	
	private boolean isPermitLogin(String val)
	{
		boolean permit=false;
		String displayName=account.getAttribute(this.displayNameAttr);
		if(displayName!=null && val.length()>2)
			if(displayName.contains(val.substring(1)) || 
			   displayName.contains(val.substring(2)) ||
			   displayName.contains(val.substring(0, val.length()-2)) ||
			   displayName.contains(val.substring(1, val.length()-2))
					) 
				permit=true; //le nouveau login doit être de long min de 3 et contenir une ss chaîne de nom ou du prenom
		
		return permit;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @param displayNameAttr the displayNameAttr to set
	 */
	public void setDisplayNameAttr(String displayNameAttr) {
		this.displayNameAttr = displayNameAttr;
	}
	

	
}
