/**
 * ESUP-Portail LDAP Account Activation - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ
 */
package org.esupportail.activfo.web.controllers;

import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.activfo.services.ldap.LdapBindFailedException;
import org.esupportail.activfo.services.remote.Information;
//import org.esupportail.activfo.services.ldap.LdapBindFailedException;
import org.esupportail.commons.services.ldap.LdapException;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.web.controllers.Resettable;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * A visual bean for the welcome page.
 */
public class AccountController extends AbstractContextAwareController {

	/**
	 * The class that represent net account.
	 */
	private Account currentAccount;
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 7013402020942988258L;

	/**
	 * If connected user want to update its password.
	 */
	private String currentPassword;
	
	
	private final Logger logger = new LoggerImpl(getClass());
	
	private String newDisplayName;
	

	/**
	 * Bean constructor.
	 */
	public AccountController() {
		super();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "#" + hashCode();
	}
	
	
	/**
	 * @see org.esupportail.activ.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		//this.currentAccount=null;
		enter();
	}

	/**
	 * @return true if the current user is allowed to view the page.
	 */
	public boolean isPageAuthorized() {
		return true;
	}

	/**
	 * JSF callback.
	 * @return a String.
	 */
	public String enter() {
		System.err.println("koukou");
		if (!isPageAuthorized()) {
			addUnauthorizedActionMessage();
			return null;
		}
		
		System.out.println(this.getDomainService().recupChaine());
		
		
		currentAccount = new Account();
		
		return "navigationActivation";
	}
	
	public String pushValid() {//pour tester si on doit activer ou non
		try {
			if (this.getDomainService().validateAccount(currentAccount)) {
				
				if (!currentAccount.isActivated()) {
					this.addInfoMessage(null, "ACTIVATION.MESSAGE.VALIDACCOUNT");
					newDisplayName = currentAccount.getDisplayName();
					System.out.println(newDisplayName);
					return "gotoDisplayNameChange";
				}
				else {
					addErrorMessage(null, "ACTIVATION.MESSAGE.ALREADYACTIVATEDACCOUNT");
				}
			}
			else {
				addErrorMessage(null, "ACTIVATION.MESSAGE.INVALIDACCOUNT");
			}
			
		}
		catch (LdapException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
			
		}
		return null;
	}
	
	/**
	 * JSF callback.
	 * @return A String. gotoPasswordChange
	 */
	public String pushChangeDisplayName() {
		
			logger.debug("currentAccount :" + currentAccount);

			if (currentAccount.changeDisplayName(newDisplayName))
			{
				this.addInfoMessage(null, "DISPLAYNAME.MESSAGE.CHANGE.SUCCESSFULL");
						
				return "gotoCharterAgreement";
			}
			
			newDisplayName = currentAccount.getDisplayName();
			this.addErrorMessage(null, "DISPLAYNAME.MESSAGE.CHANGE.UNSUCCESSFULL");
			return null;
	}
	
	/**
	 * JSF callback.
	 * @return A String. gotoPasswordChange
	 */
	public String pushCharterAgreement() {

			if (currentAccount.isCharterAgreement())
			{
				this.addInfoMessage(null, "CHARTER.MESSAGE.AGREE.SUCCESSFULL");
						
				return "gotoPasswordChange";
			}
			
			this.addErrorMessage(null, "CHARTER.MESSAGE.AGREE.UNSUCCESSFULL");
			return null;
	}
	

	
	/**
	 * JSF callback.
	 * @return A String.
	 */
	public String pushChangePassword() {
		try {
			
			currentAccount.encryptPassword();
			
			if (this.getDomainService().updateLdapAttributes(currentAccount, currentPassword))
			{	
			
				this.addInfoMessage(null, "PASSWORD.MESSAGE.CHANGE.SUCCESSFULL");
			
				/* May be useless, but no cost-effective */
				currentAccount.setBirthDate(null);
				currentAccount.setBirthName(null);
				currentAccount.setHarpegeNumber(null);
			
				return "gotoAccountEnabled";
			}
		}
		catch (LdapBindFailedException e) {
			addErrorMessage(null, "LDAP.MESSAGE.CANTBIND");
		}
		return null;
	}

	public Account getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(Account currentAccount) {
		this.currentAccount = currentAccount;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewDisplayName() {
		return newDisplayName;
	}

	public void setNewDisplayName(String newDisplayName) {
		this.newDisplayName = newDisplayName;
	}

	

}
