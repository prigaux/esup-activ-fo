/**
 * ESUP-Portail LDAP Account Activation - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ
 */
package org.esupportail.activfo.web.controllers;

import java.io.Serializable;

import org.esupportail.activfo.domain.beans.Account;

import org.esupportail.commons.services.ldap.LdapException;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;


/**
 * A visual bean for the welcome page.
 */
public class AccountController extends AbstractContextAwareController implements Serializable {

	/**
	 * The class that represent net account.
	 */
	private  Account currentAccount;
	

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
		
		if (!isPageAuthorized()) {
			addUnauthorizedActionMessage();
			return null;
		}
		
		currentAccount = new Account();

		return "navigationActivation";
	}
	
	public String pushValid() {//pour tester si on doit activer ou non
		try {
		
			if (this.getDomainService().validateAccount(currentAccount)) {
				
				currentAccount=this.getDomainService().constrAccount(currentAccount);
				
				/* for security reasons */
				currentAccount.setBirthName(null);
				currentAccount.setBirthDate(null);
				currentAccount.setHarpegeNumber(null);

				if (!currentAccount.isActivated()) {
					this.addInfoMessage(null, "ACTIVATION.MESSAGE.VALIDACCOUNT");
					newDisplayName = currentAccount.getDisplayName();
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
			
			if (currentAccount.changeDisplayName(newDisplayName)){
				/*modification du displayName au niveau du BO*/
				this.getDomainService().accountSetDisplayName(currentAccount.getDisplayName());
				
				this.addInfoMessage(null, "DISPLAYNAME.MESSAGE.CHANGE.SUCCESSFULL");
				return "gotoCharterAgreement";
			}
			
			newDisplayName=currentAccount.getDisplayName();
			this.addErrorMessage(null, "DISPLAYNAME.MESSAGE.CHANGE.UNSUCCESSFULL");
			return null;
	}
	
	/**
	 * JSF callback.
	 * @return A String. gotoPasswordChange
	 */
	public String pushCharterAgreement() {

			if (currentAccount.isCharterAgreement()){
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
						
			if (this.getDomainService().updateLdapAttributes(currentAccount.getPassword())){	
				this.addInfoMessage(null, "PASSWORD.MESSAGE.CHANGE.SUCCESSFULL");
			
				/* For security reasons, all passwords are erased */
				currentAccount.setPassword(null);
		
				return "gotoAccountEnabled";
			}
		}

		catch (LdapException e) {
			addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
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
