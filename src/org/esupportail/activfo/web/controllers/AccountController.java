/**
 * ESUP-Portail LDAP Account Activation - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ
 */
package org.esupportail.activfo.web.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.activfo.domain.beans.PersonalInformation;
import org.esupportail.activfo.domain.beans.PersonalInformation;
import org.esupportail.activfo.domain.tools.StringTools;
import org.esupportail.activfo.exceptions.KerberosException;
import org.esupportail.activfo.exceptions.LdapProblemException;
import org.esupportail.activfo.exceptions.UserPermissionException;
import org.esupportail.activfo.web.beans.BeanDisplayName;
import org.esupportail.activfo.web.beans.BeanInfo;

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
	
	private String accountIdKey;
	private String accountMailKey;
	private String accountSLCKey;
	private String accountDNKey;
	
	private String attributes;
	private String labels;

	
	private List<PersonalInformation> listPersoInfo;
	
	HashMap<String,String> accountDescr;
	/**
	 * If connected user want to update its password.
	 */
	private String currentPassword;
	
	
	private final Logger logger = new LoggerImpl(getClass());
	
	private String newDisplayName;
	
	private String code;
	
	private List<BeanInfo> listBeanPersoInfo;
	
	private HashMap<String,String> hashInf=new HashMap<String,String>();
	

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
		this.currentAccount=null;
		//enter();
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
	public String enterActivation() {
		
		if (!isPageAuthorized()) {
			addUnauthorizedActionMessage();
			return null;
		}
		
		currentAccount = new Account();

		return "navigationActivation";
	}
	
	
	public String enterReinitialisation() {
		
		if (!isPageAuthorized()) {
			addUnauthorizedActionMessage();
			return null;
		}
		
		currentAccount = new Account();

		return "navigationReinitialisation";
	}

	
	
	public String pushValid() {//pour tester si on doit activer ou non
		try {
			//On met dans deux listes les attributs et les labels(contenus dans le config.properties) correspondant aux informations personnelles qu'on souhaite afficher au niveau de l'interface
			List<String> attrPersoInfo=Arrays.asList(attributes.split(","));
			List<String>labPersoInfo=Arrays.asList(labels.split(","));
						
			accountDescr=this.getDomainService().validateAccount(currentAccount.getHarpegeNumber(),currentAccount.getBirthName(),currentAccount.getBirthDate(),attrPersoInfo);
			
			if (accountDescr!=null) {
				logger.info("Identification réussie");
				
				currentAccount.setShadowLastChange(accountDescr.get(accountSLCKey));
				currentAccount.setId(accountDescr.get(accountIdKey));
				currentAccount.setMail(accountDescr.get(accountMailKey));
				
				code=accountDescr.get("code");
							
				
				logger.info("Construction de la liste des informations personnelles du compte");
				
				//listPersoInfo = new ArrayList<PersonalInformation>();
				for(int i=0;i<attrPersoInfo.size();i++){
					listBeanPersoInfo.get(i).setKey(labPersoInfo.get(i));
					listBeanPersoInfo.get(i).setValue(accountDescr.get(attrPersoInfo.get(i)));
					if (listBeanPersoInfo.get(i) instanceof BeanDisplayName){
						currentAccount.setDisplayName(listBeanPersoInfo.get(i).getValue());
					}
				}
				
				
				/* for security reasons */
				currentAccount.setBirthName(null);
				currentAccount.setBirthDate(null);
				currentAccount.setHarpegeNumber(null);

				if (!currentAccount.isActivated()) {
					logger.info("Compte non activé");
					this.addInfoMessage(null, "ACTIVATION.MESSAGE.VALIDACCOUNT");
					return "gotoPersonnelInfo";
				}
				else {
					logger.info("Compte déja activé");
					addErrorMessage(null, "ACTIVATION.MESSAGE.ALREADYACTIVATEDACCOUNT");
				}
			}
			else {
				logger.info("Identifation utilisateur non valide");
				addErrorMessage(null, "ACTIVATION.MESSAGE.INVALIDACCOUNT");
			}
			
		}
		catch (LdapProblemException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
			
		}
		return null;
	}
	
	
	
	
	
	/**
	 * JSF callback.
	 * @return A String. gotoPasswordChange
	 */
	public String pushChangeInfoPerso() {
			
			try{
				logger.info("Mise à jour des informations personnelles");
				
				Iterator it=listBeanPersoInfo.iterator();
				while(it.hasNext()){
					BeanInfo beanPersoInfo=(BeanInfo)it.next();
					if (beanPersoInfo instanceof BeanDisplayName){
						if (!StringTools.compareInsensitive(currentAccount.getDisplayName(), beanPersoInfo.getValue())) {
							beanPersoInfo.setValue(currentAccount.getDisplayName());
							this.addErrorMessage(null, "PERSONALINFO.DISPLAYNAME.MESSAGE.CHANGE.UNSUCCESSFULL");
							return null;
						}
					}
					
					hashInf.put(this.getString(beanPersoInfo.getKey()), beanPersoInfo.getValue());
				}
				logger.info("Récupération des informations personnelles modifiées par l'utilisateur");
				
				if (this.getDomainService().updateInfoPerso(currentAccount.getId(),code,hashInf)){
					logger.info("Informations personnelles envoyées au BO pour mise à jour: "+hashInf.toString());
					
					this.addInfoMessage(null, "PERSONALINFO.MESSAGE.CHANGE.SUCCESSFULL");
					return "gotoCharterAgreement";
				}	
						
			}
			catch (LdapProblemException e) {
				logger.error(e.getMessage());
				addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
			
			}catch (UserPermissionException e) {
				logger.error(e.getMessage());
				addErrorMessage(null, "APPLICATION.USERPERMISSION.PROBLEM");
			}
			
			
			return null;
			
	}
	
	/**
	 * JSF callback.
	 * @return A String. gotoPasswordChange
	 */
	public String pushCharterAgreement() {
	
			if (currentAccount.isCharterAgreement()){
				logger.info("Charte acceptée");
				this.addInfoMessage(null, "CHARTER.MESSAGE.AGREE.SUCCESSFULL");
				return "gotoPasswordChange";
			}
			logger.info("Charte non acceptée");
			this.addErrorMessage(null, "CHARTER.MESSAGE.AGREE.UNSUCCESSFULL");
			return null;
	}
	
	/**
	 * JSF callback.
	 * @return A String.
	 */
	public String pushChangePassword() {
		try {
			
			if (this.getDomainService().setPassword(currentAccount.getId(),code,currentAccount.getPassword())){	
				logger.info("Mot de passe enregistré au niveau du BO");
				this.addInfoMessage(null, "PASSWORD.MESSAGE.CHANGE.SUCCESSFULL");
			
				/* For security reasons, all passwords are erased */
				currentAccount.setPassword(null);
		
				return "gotoAccountEnabled";
			}
			
			logger.info("Mot de passe non enregistré au niveau du BO");
			return null;
			
		}

		catch (LdapProblemException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
		
		}catch (UserPermissionException e) {
			logger.error(e.getMessage());
			this.enterReinitialisation();
			addErrorMessage(null, "APPLICATION.USERPERMISSION.PROBLEM");
		
		}catch (KerberosException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "KERBEROS.MESSAGE.PROBLEM");
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

	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAccountIdKey() {
		return accountIdKey;
	}

	public void setAccountIdKey(String accountIdKey) {
		this.accountIdKey = accountIdKey;
	}

	public String getAccountMailKey() {
		return accountMailKey;
	}

	public void setAccountMailKey(String accountMailKey) {
		this.accountMailKey = accountMailKey;
	}

	public String getAccountSLCKey() {
		return accountSLCKey;
	}

	public void setAccountSLCKey(String accountSLCKey) {
		this.accountSLCKey = accountSLCKey;
	}

	public String getAccountDNKey() {
		return accountDNKey;
	}

	public void setAccountDNKey(String accountDNKey) {
		this.accountDNKey = accountDNKey;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public List<PersonalInformation> getListPersoInfo() {
		return listPersoInfo;
	}

	public void setListPersoInfo(List<PersonalInformation> listPersoInfo) {
		this.listPersoInfo = listPersoInfo;
	}

	public List<BeanInfo> getListBeanPersoInfo() {
		return listBeanPersoInfo;
	}

	public void setListBeanPersoInfo(List<BeanInfo> listBeanPersoInfo) {
		this.listBeanPersoInfo = listBeanPersoInfo;
	}

		
	/*public String pushEmailPerso() {
	
	this.getDomainService().setMailPerso(this.currentAccount.getId(),this.currentAccount.getEmailPerso());
	this.addInfoMessage(null, "EMAILPERSO.MESSAGE.EMAILPERSOSUCCESSFUL");
	return "gotoPutCode";
}*/


/*public String pushVerifyCode() {
	
	int state=this.getDomainService().validateCode(this.currentAccount.getId(), this.code);
	if (state==2){
		this.addInfoMessage(null, "CODE.MESSAGE.CODESUCCESSFULL");
		return "gotoDisplayNameChange";
	}
	else if (state==1){
		addErrorMessage(null, "CODE.MESSAGE.CODENOTVALIDE");
	}
	else
		addErrorMessage(null, "CODE.MESSAGE.CODETIMEOUT");
	
	return null;

		
}*/
	
	
}
