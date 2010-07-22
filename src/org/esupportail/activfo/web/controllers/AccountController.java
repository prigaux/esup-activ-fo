/**
 * ESUP-Portail LDAP Account Activation - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ
 */
package org.esupportail.activfo.web.controllers;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

import org.esupportail.activfo.web.beans.BeanInfo;

import org.esupportail.commons.services.ldap.LdapException;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;


/**
 * A visual bean for the welcome page.
 */
public class AccountController extends AbstractContextAwareController implements Serializable {

	private final Logger logger = new LoggerImpl(getClass());
	
	
	private  Account currentAccount;
	private boolean reinit=false;
	
	private String accountIdKey;
	private String accountMailKey;
	private String accountSLCKey;
	private String accountDNKey;
	private String accountCodeKey;
	
	private String attributesInfPerso;
	private String attributesToValidate;
	
	private List<BeanInfo> listBeanInfoToValidate;
	private List<BeanInfo> listBeanPersoInfo;
	private BeanInfo beanPasswordPrincipal;
	private BeanInfo beanCode;
	
	
	HashMap<String,String> accountDescr;
	
	
	private HashMap<String,String> hashBeanPersoInfo=new HashMap<String,String>();
	
	private HashMap<String,String> hashInfToValidate=new HashMap<String,String>();
	
	
	

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
		reinit=false;
				
		return "navigationActivation";
	}
	
	
	public String enterReinitialisation() {
		
		if (!isPageAuthorized()) {
			addUnauthorizedActionMessage();
			return null;
		}
		
		currentAccount = new Account();
		System.out.println("REINITIALISATION");
		reinit=true;
		return "navigationActivation";
	}

	
	
	public String pushValid() {
		try {

			//Attributs à valider
			List<String> attrToValidate=Arrays.asList(attributesToValidate.split(","));

			//Attributs concernant les informations personnelles que l'on souhaite afficher
			List<String> attrPersoInfo=Arrays.asList(attributesInfPerso.split(","));
			
			Iterator it=listBeanInfoToValidate.iterator();
			int j=0;
			while(it.hasNext()){
				
				BeanInfo beanInfoToValidate=(BeanInfo)it.next();
				hashInfToValidate.put(attrToValidate.get(j), beanInfoToValidate.getValue());
				beanInfoToValidate.setValue("");//security reason
				j++;
			}
			
			logger.info("La validation concerne les données suivantes: "+this.hashInfToValidate.toString());

			
			accountDescr=this.getDomainService().validateAccount(hashInfToValidate,attrPersoInfo);
			
			if (accountDescr!=null) {
				
				logger.info("Validation des identificateurs réussie");
				
				currentAccount.setId(accountDescr.get(accountIdKey));
				currentAccount.setMail(accountDescr.get(accountMailKey));
				currentAccount.setCode(accountDescr.get(accountCodeKey));
				currentAccount.setEmailPerso(accountDescr.get(accountMailKey));

				if (currentAccount.getCode()!=null) {
					if (reinit){
						logger.info("Compte non activé");
						this.addErrorMessage(null, "IDENTIFICATION.REINITIALISATION.MESSAGE.ACCOUNT.NONACTIVATED");

					}else{
						logger.info("Compte non activé");
						logger.info("Construction de la liste des informations personnelles du compte");
						for(int i=0;i<attrPersoInfo.size();i++){
							listBeanPersoInfo.get(i).setValue(accountDescr.get(attrPersoInfo.get(i)));
						}
						this.addInfoMessage(null, "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
						return "gotoPersonnelInfo";
					}
					
				}
				else {
					if (reinit){
						
						this.addInfoMessage(null, "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
						
						if (this.getDomainService().getCode(currentAccount.getId())!=null){
							logger.info("Code envoyé par le FO sur l'adresse mail de l'utilisateur");
							return "gotoPushCode";
						}
						else{
							logger.info("Code non envoyé par le FO");
							addErrorMessage(null, "CODE.ERROR.SENDING");
						}
						
					}
					else{
						logger.info("Compte déja activé");
						addErrorMessage(null, "IDENTIFICATION.ACTIVATION.MESSAGE.ALREADYACTIVATEDACCOUNT");
					}
				}
			}
			else {
				logger.info("Identifation utilisateur non valide");
				addErrorMessage(null, "IDENTIFICATION.MESSAGE.INVALIDACCOUNT");
			}
			
		}
		catch (LdapProblemException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
			
		}
		
		return null;
	}
	
	
	public String pushChangeInfoPerso() {
			
			try{
				logger.info("Mise à jour des informations personnelles");
				
				Iterator it=listBeanPersoInfo.iterator();
				
				while(it.hasNext()){
					BeanInfo beanPersoInfo=(BeanInfo)it.next();
					hashBeanPersoInfo.put(this.getString(beanPersoInfo.getKey()), beanPersoInfo.getValue());
				}
				
				logger.info("Récupération des informations personnelles modifiées par l'utilisateur");
				
				if (this.getDomainService().updatePersonalInformations(currentAccount.getId(),currentAccount.getCode(),hashBeanPersoInfo)){
					logger.info("Informations personnelles envoyées au BO pour mise à jour: "+hashBeanPersoInfo.toString());
					
					this.addInfoMessage(null, "PERSOINFO.MESSAGE.CHANGE.SUCCESSFULL");
					
					if (!reinit){
						return "gotoCharterAgreement";
					}else{
						return "gotoPushCode";
					}
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
	
	
	public String pushVerifyCode() {
		
		currentAccount.setCode(beanCode.getValue());
		if (this.getDomainService().validateCode(this.currentAccount.getId(), this.currentAccount.getCode())){
			this.addInfoMessage(null, "CODE.MESSAGE.CODESUCCESSFULL");
			return "gotoPasswordChange";
		}
		
		addErrorMessage(null, "CODE.MESSAGE.CODENOTVALIDE");
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
			currentAccount.setPassword(beanPasswordPrincipal.getValue());
			
			if (this.getDomainService().setPassword(currentAccount.getId(),currentAccount.getCode(),currentAccount.getPassword())){	
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

	public List<BeanInfo> getListBeanPersoInfo() {
		return listBeanPersoInfo;
	}

	public void setListBeanPersoInfo(List<BeanInfo> listBeanPersoInfo) {
		this.listBeanPersoInfo = listBeanPersoInfo;
	}

	public String getAccountCodeKey() {
		return accountCodeKey;
	}

	public void setAccountCodeKey(String accountCodeKey) {
		this.accountCodeKey = accountCodeKey;
	}

	public boolean isReinit() {
		return reinit;
	}

	public void setReinit(boolean reinit) {
		this.reinit = reinit;
	}
	
	public String getAttributesToValidate() {
		return attributesToValidate;
	}

	public void setAttributesToValidate(String attributesToValidate) {
		this.attributesToValidate = attributesToValidate;
	}

	public String getAttributesInfPerso() {
		return attributesInfPerso;
	}

	public void setAttributesInfPerso(String attributesInfPerso) {
		this.attributesInfPerso = attributesInfPerso;
	}

	public List<BeanInfo> getListBeanInfoToValidate() {
		return listBeanInfoToValidate;
	}

	public void setListBeanInfoToValidate(List<BeanInfo> listBeanInfoToValidate) {
		this.listBeanInfoToValidate = listBeanInfoToValidate;
	}

	public BeanInfo getBeanPasswordPrincipal() {
		return beanPasswordPrincipal;
	}

	public void setBeanPasswordPrincipal(BeanInfo beanPasswordPrincipal) {
		this.beanPasswordPrincipal = beanPasswordPrincipal;
	}

	public BeanInfo getBeanCode() {
		return beanCode;
	}

	public void setBeanCode(BeanInfo beanCode) {
		this.beanCode = beanCode;
	}

	public HashMap<String, String> getHashBeanPersoInfo() {
		return hashBeanPersoInfo;
	}

	public void setHashBeanPersoInfo(HashMap<String, String> hashBeanPersoInfo) {
		this.hashBeanPersoInfo = hashBeanPersoInfo;
	}

	
	
	
}
