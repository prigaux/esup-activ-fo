package org.esupportail.activfo.web.validators;


import java.text.Collator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.commons.beans.AbstractI18nAwareBean;


public class ValidatorDisplayName extends AbstractI18nAwareBean implements Validator{
	
	
	private Account account;
	private String displayNameAttr;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8849185735359561457L;

	/**
	 * 
	 */
	
	
	

	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		String val=(String)value;
		
		if (this.compareInsensitive(account.getAttribute(this.displayNameAttr), val)) {
			
			
		}
		else{
			throw new ValidatorException(getFacesErrorMessage("VALIDATOR.DISPLAYNAME.INVALID"));
		}
			
		
	}
	
	public boolean compareInsensitive(String str1, String str2) {

		if (str2==null) return false;
		String strTmp1 = str1.replaceAll("[-|']+", " ");
		String strTmp2 = str2.replaceAll("[-|']+", " ");
		Collator collator = Collator.getInstance();
		collator.setStrength(Collator.PRIMARY);

		if (collator.compare(strTmp1, strTmp2) == 0) {
//			logger.debug("Strings are equivalent");
			return true;
		}

//		logger.debug("Strings  are different");
		return false;
	}
	
	public static String cleanAllSpecialChar(String str) {

//		Logger.debug("Comparing : " + str1 + " and " + str2);

		String strTmp1 = str.toLowerCase();
		strTmp1 = strTmp1.replaceAll("[^a-z]+", "");

		//strTmp1 = Normalizer.normalize(strTmp1, Normalizer.DECOMP, 0);
	    return strTmp1.replaceAll("[^\\p{ASCII}]","");

	}


	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return the displayNameAttr
	 */
	public String getDisplayNameAttr() {
		return displayNameAttr;
	}

	/**
	 * @param displayNameAttr the displayNameAttr to set
	 */
	public void setDisplayNameAttr(String displayNameAttr) {
		this.displayNameAttr = displayNameAttr;
	}


	
}
