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

import org.esupportail.activfo.web.beans.BeanField;


import org.esupportail.commons.services.ldap.LdapException;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;


/**
 * A visual bean for the welcome page.
 */
public class AccountController extends AbstractContextAwareController implements Serializable {

	private final Logger logger = new LoggerImpl(getClass());
	
	private  Account currentAccount;
	private boolean activ=false;
	private boolean reinit=false;
	private boolean passwChange=false;
	
	
	private String accountIdKey;
	private String accountMailKey;
	private String accountMailPersoKey;
	private String accountDNKey;
	private String accountCodeKey;
	
	//liste des champs pour l'affichage des informations personnelles
	private List<BeanField> listBeanPersoInfo;
	
	//liste des attributs pour l'affichage des informations personnelles
	private String attributesInfPerso;
	
	
	//liste générique d'attributs à valider
	private List<String> attrToValidate;
	
	private String attributesStudentToValidate;
	private String attributesPersonnelToValidate;
	private String attributesOldStudentToValidate;
	
	
	
	//liste générique des champs pour la validation
	private List<BeanField> listInfoToValidate;
	
	private List<BeanField> listInfoStudentToValidate;
	private List<BeanField> listInfoPersonnelToValidate;
	private List<BeanField> listInfoOldStudentToValidate;
	
	
	
	//liste des champs correspondant aux procedures
	private List<BeanField> listBeanProcedure;
	
	//liste des champs correspondant aux statuts de l'utilisateur
	private List<BeanField> listBeanStatus;
	
	//champ passwordPrincipal
	private BeanField beanPasswordPrincipal;
	
	//champ code
	private BeanField beanCode;
	
	
	//decriptif du compte suite à validation
	HashMap<String,String> accountDescr;
	
	private String procedureReinitialisation;
	private String procedureActivation;
	private String procedurePasswordChange;
	
	private String statusStudent;
	private String statusPersonnel;
	private String statusOldStudent;
	

	/**
	 * Bean constructor.
	 */
	public AccountController() {
		super();
		currentAccount = new Account();
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
		//enter();
	}

	/**
	 * @return true if the current user is allowed to view the page.
	 */
	public boolean isPageAuthorized() {
		return true;
	}
	
	public String enter() {
		
		if (!isPageAuthorized()) {
			addUnauthorizedActionMessage();
			return null;
		}
		
		if (currentAccount.getOneRadioProcedure().equals(procedureReinitialisation)){
			reinit=true;
			passwChange=false;
			activ=false;
		}
		else if (currentAccount.getOneRadioProcedure().equals(procedurePasswordChange)){
			passwChange=true;
			reinit=false;
			activ=false;
		}
		else{
			activ=true;
			reinit=false;
			passwChange=false;
		}
		
		
		if(currentAccount.getOneRadioValue().equals(statusStudent)){

			this.listInfoToValidate=listInfoStudentToValidate;
			attrToValidate=Arrays.asList(attributesStudentToValidate.split(","));
		}
		else if (currentAccount.getOneRadioValue().equals(this.statusPersonnel)){
			this.listInfoToValidate=listInfoPersonnelToValidate;
			attrToValidate=Arrays.asList(attributesPersonnelToValidate.split(","));
		}
		else{
			this.listInfoToValidate=listInfoOldStudentToValidate;
			attrToValidate=Arrays.asList(attributesOldStudentToValidate.split(","));
		}
				
		return "navigationActivation";
	}
	
		
	public String pushValid() {
		try {
			
			HashMap<String,String> hashInfToValidate;
			hashInfToValidate=this.getMap(listInfoToValidate, attrToValidate);

			logger.info("La validation concerne les données suivantes: "+hashInfToValidate.toString());

			//Attributs concernant les informations personnelles que l'on souhaite afficher
			List<String> attrPersoInfo=Arrays.asList(attributesInfPerso.split(","));
			
			accountDescr=this.getDomainService().validateAccount(hashInfToValidate,attrPersoInfo);
			
			if (accountDescr!=null) {
				
				logger.info("Validation des identificateurs réussie");
				
				currentAccount.setId(accountDescr.get(accountIdKey));
				currentAccount.setMail(accountDescr.get(accountMailKey));
				currentAccount.setCode(accountDescr.get(accountCodeKey));
				
				currentAccount.setEmailPerso(accountDescr.get(accountMailPersoKey));

				if (currentAccount.getCode()!=null) {
					if (reinit|passwChange){
						logger.info("Compte non activé");
						this.addErrorMessage(null, "IDENTIFICATION.REINITIALISATION.MESSAGE.ACCOUNT.NONACTIVATED");

					}else if(activ){
						logger.info("Compte non activé");
						logger.info("Construction de la liste des informations personnelles du compte");
						for(int i=0;i<attrPersoInfo.size();i++){
							System.out.println("kkkk"+accountDescr.get(attrPersoInfo.get(i)));
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
					else if(activ){
						logger.info("Compte déja activé");
						addErrorMessage(null, "IDENTIFICATION.ACTIVATION.MESSAGE.ALREADYACTIVATEDACCOUNT");
					}
					
					else if(passwChange){
						this.addInfoMessage(null, "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
						return "gotoPasswordChange";
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
				
				HashMap<String,String> hashBeanPersoInfo=new HashMap<String,String>();
				Iterator it=listBeanPersoInfo.iterator();
				
				while(it.hasNext()){
					BeanField beanPersoInfo=(BeanField)it.next();
					hashBeanPersoInfo.put(this.getString(beanPersoInfo.getKey()), beanPersoInfo.getValue());
				}
				
				logger.info("Récupération des informations personnelles modifiées par l'utilisateur");
				
				if (this.getDomainService().updatePersonalInformations(currentAccount.getId(),currentAccount.getCode(),hashBeanPersoInfo)){
					logger.info("Informations personnelles envoyées au BO pour mise à jour: "+hashBeanPersoInfo.toString());
					
					this.addInfoMessage(null, "PERSOINFO.MESSAGE.CHANGE.SUCCESSFULL");
					
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
			//this.enterReinitialisation();
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

	

	public String getAccountDNKey() {
		return accountDNKey;
	}

	public void setAccountDNKey(String accountDNKey) {
		this.accountDNKey = accountDNKey;
	}

	public List<BeanField> getListBeanPersoInfo() {
		return listBeanPersoInfo;
	}

	public void setListBeanPersoInfo(List<BeanField> listBeanPersoInfo) {
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
	
	

	public String getAttributesInfPerso() {
		return attributesInfPerso;
	}

	public void setAttributesInfPerso(String attributesInfPerso) {
		this.attributesInfPerso = attributesInfPerso;
	}

	public BeanField getBeanPasswordPrincipal() {
		return beanPasswordPrincipal;
	}

	public void setBeanPasswordPrincipal(BeanField beanPasswordPrincipal) {
		this.beanPasswordPrincipal = beanPasswordPrincipal;
	}

	public BeanField getBeanCode() {
		return beanCode;
	}

	public void setBeanCode(BeanField beanCode) {
		this.beanCode = beanCode;
	}
	
	public boolean isPasswChange() {
		return passwChange;
	}

	public void setPasswChange(boolean passwChange) {
		this.passwChange = passwChange;
	}

	public List<BeanField> getListBeanProcedure() {
		return listBeanProcedure;
	}

	public void setListBeanProcedure(List<BeanField> listBeanProcedure) {
		this.listBeanProcedure = listBeanProcedure;
	}

	
	public List<BeanField> getListBeanStatus() {
		return listBeanStatus;
	}

	public void setListBeanStatus(List<BeanField> listBeanStatus) {
		this.listBeanStatus = listBeanStatus;
	}

	public String getAttributesOldStudentToValidate() {
		return attributesOldStudentToValidate;
	}

	public void setAttributesOldStudentToValidate(
			String attributesOldStudentToValidate) {
		this.attributesOldStudentToValidate = attributesOldStudentToValidate;
	}

	public List<BeanField> getListInfoStudentToValidate() {
		return listInfoStudentToValidate;
	}

	public void setListInfoStudentToValidate(
			List<BeanField> listInfoStudentToValidate) {
		this.listInfoStudentToValidate = listInfoStudentToValidate;
	}

	public List<BeanField> getListInfoPersonnelToValidate() {
		return listInfoPersonnelToValidate;
	}

	public void setListInfoPersonnelToValidate(
			List<BeanField> listInfoPersonnelToValidate) {
		this.listInfoPersonnelToValidate = listInfoPersonnelToValidate;
	}

	public List<BeanField> getListInfoOldStudentToValidate() {
		return listInfoOldStudentToValidate;
	}

	public void setListInfoOldStudentToValidate(
			List<BeanField> listInfoOldStudentToValidate) {
		this.listInfoOldStudentToValidate = listInfoOldStudentToValidate;
	}

	private HashMap<String,String> getMap(List<BeanField> listeInfoToValidate,List<String>attrToValidate){
		
		HashMap<String,String> hashInfToValidate=new HashMap<String,String>();
		Iterator it=listeInfoToValidate.iterator();
		int j=0;
		while(it.hasNext()){
			BeanField beanInfoToValidate=(BeanField)it.next();
			hashInfToValidate.put(attrToValidate.get(j), beanInfoToValidate.getValue());
			beanInfoToValidate.setValue("");//security reason
			j++;
		}
		return hashInfToValidate;
		
	}

	public String getAttributesStudentToValidate() {
		return attributesStudentToValidate;
	}

	public void setAttributesStudentToValidate(String attributesStudentToValidate) {
		this.attributesStudentToValidate = attributesStudentToValidate;
	}

	public String getAttributesPersonnelToValidate() {
		return attributesPersonnelToValidate;
	}

	public void setAttributesPersonnelToValidate(
			String attributesPersonnelToValidate) {
		this.attributesPersonnelToValidate = attributesPersonnelToValidate;
	}

	public List<BeanField> getListInfoToValidate() {
		return listInfoToValidate;
	}

	public void setListInfoToValidate(List<BeanField> listInfoToValidate) {
		this.listInfoToValidate = listInfoToValidate;
	}

	

	public List<String> getAttrToValidate() {
		return attrToValidate;
	}

	public void setAttrToValidate(List<String> attrToValidate) {
		this.attrToValidate = attrToValidate;
	}

	public String getProcedureReinitialisation() {
		return procedureReinitialisation;
	}

	public void setProcedureReinitialisation(String procedureReinitialisation) {
		this.procedureReinitialisation = procedureReinitialisation;
	}

	public String getProcedureActivation() {
		return procedureActivation;
	}

	public void setProcedureActivation(String procedureActivation) {
		this.procedureActivation = procedureActivation;
	}

	public String getProcedurePasswordChange() {
		return procedurePasswordChange;
	}

	public void setProcedurePasswordChange(String procedurePasswordChange) {
		this.procedurePasswordChange = procedurePasswordChange;
	}

	public String getStatusStudent() {
		return statusStudent;
	}

	public void setStatusStudent(String statusStudent) {
		this.statusStudent = statusStudent;
	}

	public String getStatusPersonnel() {
		return statusPersonnel;
	}

	public void setStatusPersonnel(String statusPersonnel) {
		this.statusPersonnel = statusPersonnel;
	}

	public String getStatusOldStudent() {
		return statusOldStudent;
	}

	public void setStatusOldStudent(String statusOldStudent) {
		this.statusOldStudent = statusOldStudent;
	}

	public boolean isActiv() {
		return activ;
	}

	public void setActiv(boolean activ) {
		this.activ = activ;
	}

	public String getAccountMailPersoKey() {
		return accountMailPersoKey;
	}

	public void setAccountMailPersoKey(String accountMailPersoKey) {
		this.accountMailPersoKey = accountMailPersoKey;
	}
	
	

}
