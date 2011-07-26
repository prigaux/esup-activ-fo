package org.esupportail.activfo.web.validators;

import java.text.Collator;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.commons.beans.AbstractI18nAwareBean;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

public class ValidatorDisplayName extends AbstractI18nAwareBean implements Validator{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8849185735359561457L;
	private final Logger logger = new LoggerImpl(getClass());

	private Account account;
	private String displayNameAttr;
	private String caracterForbiddenDisplayName;
	
	/**
	 * 
	 */

	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		String val=(String)value;
		
		Pattern p;
		Matcher m;
		String specialmessage=null;
		
		p=Pattern.compile(caracterForbiddenDisplayName);
		m=p.matcher(val);
			
		if(! m.find() || m.group(0).equals(" ")) {
			if (this.isContainFirstName(val) && this.isContainLastName(val)) {
				
			} else {
				throw new ValidatorException(getFacesErrorMessage("VALIDATOR.DISPLAYNAME.INVALID"));
			}
			
		} else {
			specialmessage=m.group(0);
			throw new ValidatorException(getFacesErrorMessage("VALIDATOR.PASSWORD.CARACTERFORBIDDEN",specialmessage));
		}
		
		/*
		if (this.compareInsensitive(account.getAttribute(this.displayNameAttr), val)) {
			
		}
		else{
			throw new ValidatorException(getFacesErrorMessage("VALIDATOR.DISPLAYNAME.INVALID"));
		}
		*/
	}
	
	public boolean compareInsensitive(String str1, String str2) {

		if (str2==null) return false;
		
		logger.debug("str1 et str2 sont "+str1+", "+str2);
		
		String strTmp1 = str1.replaceAll("[-|']+", " ");
		String strTmp2 = str2.replaceAll("[-|']+", " ");
		Collator collator = Collator.getInstance();
		collator.setStrength(Collator.PRIMARY);
		

		if (collator.compare(strTmp1, strTmp2) == 0) {
			logger.debug("Strings are equivalent");
			return true;
		}

		logger.debug("Strings  are different");
		return false;
	}
	
	private boolean isContainFirstName(String firstName)
	{
		boolean permit=false;
		
		String compareSN=account.getAttribute("sn").toLowerCase();
		String compareUp1BirthName=account.getAttribute("up1BirthName").toLowerCase();
				
    	/*
    	
    	String stringTrim[] = null;
		stringTrim=returnValue.toLowerCase().split(" ");
		
    	for(int i=0;i<stringTrim.length;i++) {
    		if (compareSN.contains(stringTrim[i]) || compareUp1BirthName.contains(stringTrim[i]))
    			permit=true;
    	}
    	*/
    	if (firstName.toLowerCase().contains(compareSN)) {
    		logger.debug("True : The string("+firstName+") contains SN ("+compareSN+")");
    		permit=true;
    	}
    	if (firstName.toLowerCase().contains(compareUp1BirthName)) {
    		logger.debug("True : The string("+firstName+") contains up1BirthName ("+compareUp1BirthName+")");
    		permit=true;
    	}
    	
    	return permit;
	}

	private boolean isContainLastName(String lastName)
	{
		boolean permit=false;
		
		String compareGivenName=account.getAttribute("givenName").toLowerCase();
		String compareUp1AltGivenName=account.getAttribute("up1AltGivenName").toLowerCase();
				
    	/*
    	
    	String stringTrim[] = null;
		stringTrim=returnValue.toLowerCase().split(" ");
		
    	for(int i=0;i<stringTrim.length;i++) {
    		if (compareGivenName.contains(stringTrim[i]) || compareUp1AltGivenName.contains(stringTrim[i]))
    			permit=true;
    	}
    	*/
    	if (lastName.toLowerCase().contains(compareGivenName)) {
    		logger.debug("True : The string("+lastName+") contains givenName("+compareGivenName+")");
    		permit=true;
    	}
    	if (lastName.toLowerCase().contains(compareUp1AltGivenName)) {
    		logger.debug("True : The string("+lastName+") contains up1AltGivenName("+compareUp1AltGivenName+")");
    		permit=true;
    	}
    	return permit;
	}
	
	
	public static String cleanAllSpecialChar(String str) {

		//Logger.debug("Comparing : " + str1 + " and " + str2);

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

	/**
	 * @return the caracterForbiddenDisplayName
	 */
	public String getCaracterForbiddenDisplayName() {
		return caracterForbiddenDisplayName;
	}

	/**
	 * @param caracterForbiddenDisplayName the caracterForbiddenDisplayName to set
	 */
	public void setCaracterForbiddenDisplayName(String caracterForbiddenDisplayName) {
		this.caracterForbiddenDisplayName = caracterForbiddenDisplayName;
	}
	
	
	
}