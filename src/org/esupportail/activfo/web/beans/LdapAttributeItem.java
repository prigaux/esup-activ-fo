package org.esupportail.activfo.web.beans;



import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

import javax.faces.model.SelectItem;


public class LdapAttributeItem extends SelectItem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8114524781218224684L;
	private final Logger logger = new LoggerImpl(getClass());
	
	private  Account account;
	
	private String attributeName;
	private String preValue="";
	private String postValue="";
	
	public String getValue() {
		String value=account.getAttribute(attributeName);
		if(value==null) {
			logger.warn(account.getId()+"'s account contains no value for attribute "+attributeName);
			value="";
		}
		value=preValue+value+postValue;
		return value;		
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
	 * @return the preValue
	 */
	public String getPreValue() {
		return preValue;
	}


	/**
	 * @param preValue the preValue to set
	 * value=preValue+value
	 */
	public void setPreValue(String preValue) {
		this.preValue = preValue;
	}


	/**
	 * @return the postValue
	 */
	public String getPostValue() {
		return postValue;
	}


	/**
	 * @param postValue the postValue to set 
	 * value=value+postValue
	 */
	public void setPostValue(String postValue) {
		this.postValue = postValue;
	}


	/**
	 * @return the attributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}


	/**
	 * @param attributeName the attributeName to set
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	
}
