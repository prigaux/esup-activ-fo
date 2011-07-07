package org.esupportail.activfo.web.beans;



import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import javax.faces.model.SelectItem;


public class MailforwardItem extends SelectItem {
	
	private  Account account;
	
	private final Logger logger = new LoggerImpl(getClass());
	
	private String attributename;
	
	private String separator="\\";
	
	public String getValue() {
		String returnvalue="";
		if (attributename.contains("supannMailPerso")) returnvalue=account.getEmailPerso();
		else if (attributename.contains("uid")) returnvalue=getSeparator()+account.getId();
		return returnvalue;
	}
	

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}


	/**
	 * @return the attributename
	 */
	public String getAttributename() {
		return attributename;
	}


	/**
	 * @param attributename the attributename to set
	 */
	public void setAttributename(String attributename) {
		this.attributename = attributename;
	}


	/**
	 * @return the separator
	 */
	public String getSeparator() {
		return separator;
	}


	/**
	 * @param separator the separator to set
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}
	
	


	


	

	
	

	
}
