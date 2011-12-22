/**
 * permet l'envoi de mail pour signaler des modifications de données personnelles nécessitant une validation
 */
package org.esupportail.activfo.domain.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.services.smtp.AsynchronousSmtpServiceImpl;

/**
 * @author aanli
 *
 */
public class Mailing {

	private final Logger logger = new LoggerImpl(getClass());
	
	private AsynchronousSmtpServiceImpl smtpService;
	private String subjectDataChange;
	private String body1DataChange;
	private String body2DataChange;
	
	private String accountDNKey;
	private String accountEmpIdKey;

	
	public void sendMessage(Account currentAccount, HashMap<String,List<String>> oldValue, HashMap<String,List<String>> newValue) {
		
		InternetAddress mail=null;
		String sep=", ";
		
		String mailBody=this.body1DataChange;
		String mailBody2=this.body2DataChange;
		String newSubject = null;
		mailBody=mailBody.replace("{0}", currentAccount.getAttribute(accountDNKey)!=null?currentAccount.getAttribute(accountDNKey):"");
		mailBody=mailBody.replace("{1}", currentAccount.getAttribute(accountEmpIdKey)!=null?currentAccount.getAttribute(accountEmpIdKey):"");
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
		
		try {
			mail = new InternetAddress(smtpService.getFromAddress().getAddress());
		} catch (AddressException e) {
			logger.debug("Error Handling for InternetAddress ");
		}
		
		
		newSubject=subjectDataChange.replace("{0}", currentAccount.getAttribute(accountDNKey)!=null?currentAccount.getAttribute(accountDNKey):"");
		
		if (newValue.size()>0)
			smtpService.send(mail, newSubject, mailBody, "");
	}
	
	public static String join(Iterable<?> elements, String separator) {
		return StringUtils.join(elements.iterator(), separator);
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
	 * @return the accountDNKey
	 */
	public String getAccountDNKey() {
		return accountDNKey;
	}

	/**
	 * @param accountDNKey the accountDNKey to set
	 */
	public void setAccountDNKey(String accountDNKey) {
		this.accountDNKey = accountDNKey;
	}

	/**
	 * @return the accountEmpIdKey
	 */
	public String getAccountEmpIdKey() {
		return accountEmpIdKey;
	}

	/**
	 * @param accountEmpIdKey the accountEmpIdKey to set
	 */
	public void setAccountEmpIdKey(String accountEmpIdKey) {
		this.accountEmpIdKey = accountEmpIdKey;
	}
}
