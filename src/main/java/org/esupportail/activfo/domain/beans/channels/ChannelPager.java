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
public class ChannelPager extends ChannelImpl{

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
	@Override
	public String getParamMsg() {
		String pager=account.getPager();
		if(pager!=null) pager=pager.replaceAll(" ", "").replaceAll("^\\+33", "0").substring(0, 6)+"****";
		return pager;

	}

}
