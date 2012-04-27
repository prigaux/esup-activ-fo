/**
 * permet l'envoi de mail pour signaler des modifications de données personnelles nécessitant une validation
 */
package org.esupportail.activfo.domain.beans.mailing;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.services.smtp.AsynchronousSmtpServiceImpl;

/**
 * @author aanli
 *
 */
public class MailingImpl implements Mailing {

	private final Logger logger = new LoggerImpl(getClass());
	
	private String regexAttribute="(\\{([^{}]*)\\})";
	private String none="";
	private String sep=", ";
	
	private AsynchronousSmtpServiceImpl smtpService;
	
	private String subjectDataChange;
	private String body1DataChange;
	private String body2DataChange;
	private String mail2Gest;
	
	private HashMap<String,List<String>> access;
	private HashMap<String,List<String>> deny;
	
	public void sendMessage(Account currentAccount, HashMap<String,List<String>> oldValue, HashMap<String,List<String>> newValue) {
		
		InternetAddress mail=null;		
		
		String mailBody=attributeReplace(currentAccount,this.body1DataChange);
		String mailBody2=this.body2DataChange;
		String newSubject = null;
	
		mailBody=mailBody+mailBody2;
		
		Set<String> keys=oldValue.keySet();
		
		for(String key:keys)
		{
			mailBody=mailBody+"<tr><td>"+key+"</td><td>";
			String oldAttrs=join(oldValue.get(key), sep);
			String newAttrs=join(newValue.get(key), sep);
						
			mailBody+=oldAttrs+"</td><td>"+newAttrs+"</td><tr>";;						
		}
		
    	mailBody=mailBody+"</table>";
    	newSubject=attributeReplace(currentAccount,subjectDataChange);
    	
		try {
			mail = new InternetAddress(mail2Gest);
			if (newValue.size()>0)
				smtpService.send(mail, newSubject, mailBody, "");
		} 
		catch (AddressException e) {
			logger.error("Error Handling for InternetAddress ");
		}
	}
	
	public boolean isAllowed(Account currentAccount){
		if(access==null && deny==null) return true; //si pas de définition de droit d'accès, le mail de notification est envoyé pour tout profil
		
		if(deny!=null && profileMatches(deny,currentAccount))				
			return false;																					
					
		if(access!=null && profileMatches(access,currentAccount))
			return true;
		
		if(deny!=null) return true; 
		else return false;
	}
	
	private boolean profileMatches(HashMap<String,List<String>> profile, Account currentAccount){
		Set<String> keySet = profile.keySet();
		for(String attribute : keySet) {
			List<String> values=profile.get(attribute);
			List<String> userValues=currentAccount.getAttributes(attribute);				
			for(String userValue:userValues)
				if(values.contains(userValue))		
					return true;																					
		}
		return false;
	}
	
	public static String join(Iterable<?> elements, String separator) {
		return StringUtils.join(elements.iterator(), separator);
	}
	
	private  String attributeReplace(Account account,String text){
		Pattern p=Pattern.compile(regexAttribute);
   	  	Matcher m=p.matcher(text);
   	  	while(m.find()) {
   	  		List<String> attrValues=account.getAttributes(m.group(2));
   	  		String attrValue=none;
   	  		if(attrValues!=null)
   	  			attrValue=attrValues.size()==1?attrValues.get(0):attrValues.toString();
   	  	
   	  		text=text.replace(m.group(1),attrValue);
   	  	}
   	  	return text;   	 
	}
	
	/**
	 * @return the smtpService
	 */
	public AsynchronousSmtpServiceImpl getSmtpService() {
		return smtpService;
	}

	/**
	 * @param smtpService the smtpService to set
	 */
	public void setSmtpService(AsynchronousSmtpServiceImpl smtpService) {
		this.smtpService = smtpService;
	}

	/**
	 * @return the subjectDataChange
	 */
	public String getSubjectDataChange() {
		return subjectDataChange;
	}

	/**
	 * @param subjectDataChange the subjectDataChange to set
	 */
	public void setSubjectDataChange(String subjectDataChange) {
		this.subjectDataChange = subjectDataChange;
	}

	/**
	 * @return the body1DataChange
	 */
	public String getBody1DataChange() {
		return body1DataChange;
	}

	/**
	 * @param body1DataChange the body1DataChange to set
	 */
	public void setBody1DataChange(String body1DataChange) {
		this.body1DataChange = body1DataChange;
	}

	/**
	 * @return the body2DataChange
	 */
	public String getBody2DataChange() {
		return body2DataChange;
	}

	/**
	 * @param body2DataChange the body2DataChange to set
	 */
	public void setBody2DataChange(String body2DataChange) {
		this.body2DataChange = body2DataChange;
	}

	/**
	 * @return the regexAttribute
	 */
	public String getRegexAttribute() {
		return regexAttribute;
	}



	/**
	 * @param regexAttribute the regexAttribute to set
	 */
	public void setRegexAttribute(String regexAttribute) {
		this.regexAttribute = regexAttribute;
	}



	/**
	 * @return the none
	 */
	public String getNone() {
		return none;
	}



	/**
	 * @param none the none to set
	 */
	public void setNone(String none) {
		this.none = none;
	}



	/**
	 * @return the mail2Gest
	 */
	public String getMail2Gest() {
		return mail2Gest;
	}



	/**
	 * @param mail2Gest the mail2Gest to set
	 */
	public void setMail2Gest(String mail2Gest) {
		this.mail2Gest = mail2Gest;
	}

	/**
	 * @return the access
	 */
	public HashMap<String, List<String>> getAccess() {
		return access;
	}

	/**
	 * @param access the access to set
	 */
	public void setAccess(HashMap<String, List<String>> access) {
		this.access = access;
	}

	/**
	 * @return the deny
	 */
	public HashMap<String, List<String>> getDeny() {
		return deny;
	}

	/**
	 * @param deny the deny to set
	 */
	public void setDeny(HashMap<String, List<String>> deny) {
		this.deny = deny;
	}

	/**
	 * @return the sep
	 */
	public String getSep() {
		return sep;
	}

	/**
	 * @param sep the sep to set
	 */
	public void setSep(String sep) {
		this.sep = sep;
	}
}
