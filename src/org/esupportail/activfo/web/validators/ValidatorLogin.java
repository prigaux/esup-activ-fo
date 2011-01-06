package org.esupportail.activfo.web.validators;



import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.commons.beans.AbstractI18nAwareBean;
import org.esupportail.activfo.domain.beans.Account;



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
		int permitCount=0;
		String compVal=null;
		String displayName=account.getAttribute(this.displayNameAttr).toLowerCase();
		
		if(displayName!=null && val.length()>2) {
			compVal=val.toLowerCase();
			String stringTrim[] = null;
	    	stringTrim=displayName.split(" ");
	    	for(int i=0;i<stringTrim.length;i++) {
	    	 if (compVal.contains(stringTrim[i]))
	    		 permit=true;
	    	 if (compVal.contains(stringTrim[i].substring(0,1)))
	    		 permitCount++;
	    	}
	    	if (!permit && permitCount >= 2)
	    		permit=true;
		}
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