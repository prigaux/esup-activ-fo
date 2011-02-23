package org.esupportail.activfo.web.validators;

import java.text.Normalizer;
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
	private String invalidMessage;
	private int minSize=3;
	private int maxSize=14;
	
	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		String val=(String)value;
		setInvalidMessage("VALIDATOR.LOGIN.INVALID.WITHOUTDISPLAYNAME");
		if(!isPermitLogin(val)) throw new ValidatorException(getFacesErrorMessage(this.invalidMessage));
	}
	
	private boolean isPermitLogin(String val)
	{
		boolean permit=false;
		int permitCount=0;
		String compVal=null;
		String displayNameOrigin=account.getAttribute(this.displayNameAttr).toLowerCase();
		String displayNameNormalize = Normalizer.normalize(displayNameOrigin, Normalizer.Form.NFD);
		String displayName=displayNameNormalize.replaceAll("[^\\p{ASCII}]","");
		
		if (val.length()>this.minSize && val.length()<this.maxSize ) {
	    	if (val.matches("^[a-zA-Z]+[a-zA-Z0-9_\\-\\.]*$")) {
	    		if(displayName!=null) {
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
			} else {
				if (val.matches("^[0-9]+[a-zA-Z0-9_\\-\\.]*$"))
					this.invalidMessage="VALIDATOR.LOGIN.INVALID.FIRSTNUMBER";
				else this.invalidMessage="VALIDATOR.LOGIN.INVALID.SPECIALCHAR";
			}
		} else {
			this.invalidMessage="VALIDATOR.LOGIN.LENGTH";
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

	/**
	 * @return the invalidMessage
	 */
	public String getInvalidMessage() {
		return invalidMessage;
	}

	/**
	 * @param invalidMessage the invalidMessage to set
	 */
	public void setInvalidMessage(String invalidMessage) {
		this.invalidMessage = invalidMessage;
	}

	/**
	 * @return the minSize
	 */
	public int getMinSize() {
		return minSize;
	}

	/**
	 * @param minSize the minSize to set
	 */
	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}

	/**
	 * @return the maxSize
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * @param maxSize the maxSize to set
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}