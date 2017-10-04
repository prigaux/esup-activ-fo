/**
 * ESUP-Portail LDAP Account Activation - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ
 */
package org.esupportail.activfo.domain.beans.channels;

import org.esupportail.activfo.domain.beans.Account;

/**
 * @author aanli
 *
 */
public class ChannelMailPerso extends ChannelImpl{	
	
	private  Account account;

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
	 * @return the paramMsg
	 */
	public String getParamMsg() {
		String mailPerso=account.getEmailPerso();
		if(mailPerso!=null) mailPerso="****"+mailPerso.substring(4);
		return mailPerso;
		
	}		

}
