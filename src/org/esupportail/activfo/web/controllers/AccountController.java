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
import org.esupportail.activfo.exceptions.AuthentificationException;
import org.esupportail.activfo.exceptions.ChannelException;
import org.esupportail.activfo.exceptions.KerberosException;
import org.esupportail.activfo.exceptions.LdapProblemException;
import org.esupportail.activfo.exceptions.LoginAlreadyExistsException;
import org.esupportail.activfo.exceptions.LoginException;
import org.esupportail.activfo.exceptions.PrincipalNotExistsException;
import org.esupportail.activfo.exceptions.UserPermissionException;
import org.esupportail.activfo.web.beans.BeanField;
import org.esupportail.activfo.web.beans.BeanFieldImpl;
import org.esupportail.activfo.web.beans.BeanMultiValue;
import org.esupportail.activfo.web.beans.BeanMultiValueImpl;
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
	private boolean loginChange=false;
	
	
	private String accountIdKey;
	private String accountMailKey;
	private String accountMailPersoKey;
	private String accountPagerKey;
	private String accountDNKey;
	private String accountCodeKey;
	private String accountGestKey;
	private String accountPossibleChannelsKey;
	
	private String labelCanalMailPerso;
	private String labelCanalPager;
	private String labelCanalGest;
	
	//liste des champs pour l'affichage des informations personnelles
	private List<BeanField> listBeanPersoInfo;
		
	//liste des attributs pour l'affichage des informations personnelles
	private String attributesInfPerso;
	
	
	//liste g�n�rique d'attributs � valider
	private List<String> attrToValidate;
	
	private String attributesStudentToValidate;
	private String attributesPersonnelToValidate;
	private String attributesOldStudentToValidate;
	private String attributesAnotherStudentToValidate;
	
	
	//liste g�n�rique des champs pour la validation
	private List<BeanField> listInfoToValidate;
	
	private List<BeanField> listInfoStudentToValidate;
	private List<BeanField> listInfoPersonnelToValidate;
	private List<BeanField> listInfoOldStudentToValidate;
	private List<BeanField> listInfoAnotherStudentToValidate;
	
	
	private List<BeanField> listBeanCanal;
	
	
	//liste des champs correspondant aux procedures
	private List<BeanField> listBeanProcedure;
	
	//liste des champs correspondant aux statuts de l'utilisateur
	private List<BeanField> listBeanStatus;
	
	//champ newpassword
	private BeanField beanNewPassword;
	
	//champ code
	private BeanField beanCode;
	
	//champ login
	private BeanField beanLogin;
	
	//champ Password
	private BeanField beanPassword;
	
	//champ newLogin
	private BeanField beanNewLogin;
	
	
	//decriptif du compte suite � validation
	HashMap<String,String> accountDescr=new HashMap<String,String>();
	
	
	private String procedureReinitialisation;
	private String procedureActivation;
	private String procedurePasswordChange;
	private String procedureLoginChange;
	
	private String statusStudent;
	private String statusPersonnel;
	private String statusOldStudent;
	private String statusAnotherStudent;
	
	private String separator;
	
	
	/**
	 * Bean constructor.
	 */
	public AccountController() {
		super();
		
	}

		
	/**
	 * @see org.esupportail.activ.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
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
			loginChange=false;
		}
		else if (currentAccount.getOneRadioProcedure().equals(procedurePasswordChange)){
			passwChange=true;
			reinit=false;
			activ=false;
			loginChange=false;
			return "gotoAuthentification";
		}
		else if (currentAccount.getOneRadioProcedure().equals(procedureLoginChange)){
			passwChange=false;
			reinit=false;
			activ=false;
			loginChange=true;
			return "gotoAuthentification";
		}
		else{
			activ=true;
			reinit=false;
			passwChange=false;
			loginChange=false;
		}
		
		
		if(currentAccount.getOneRadioValue().equals(statusStudent)){
			this.listInfoToValidate=listInfoStudentToValidate;
			attrToValidate=Arrays.asList(attributesStudentToValidate.split(","));
		}
		else if (currentAccount.getOneRadioValue().equals(this.statusPersonnel)){
			this.listInfoToValidate=listInfoPersonnelToValidate;
			attrToValidate=Arrays.asList(attributesPersonnelToValidate.split(","));
		}
		else if (currentAccount.getOneRadioValue().equals(this.statusAnotherStudent)) {
			this.listInfoToValidate=listInfoAnotherStudentToValidate;
			attrToValidate=Arrays.asList(attributesAnotherStudentToValidate.split(","));
		}
		else{
			this.listInfoToValidate=listInfoOldStudentToValidate;
			attrToValidate=Arrays.asList(attributesOldStudentToValidate.split(","));
		}
		return "goToInfoToValidate";
	}
	
		
	public String pushValid() {
		try {
			
			HashMap<String,String> hashInfToValidate;
			hashInfToValidate=this.getMap(listInfoToValidate, attrToValidate);
			
			logger.info("La validation concerne les donn�es suivantes: "+hashInfToValidate.toString());
			
			//Attributs concernant les informations personnelles que l'on souhaite afficher
			
			List<String> attrPersoInfo=Arrays.asList(attributesInfPerso.split(","));
			
			accountDescr=this.getDomainService().validateAccount(hashInfToValidate,attrPersoInfo);
			
			logger.info("accoutDescr : "+accountDescr.toString());
			
			//recuperation liste pour attributs perso info
				
			logger.info("Identification valide");
			
			logger.info("accoutDescr: "+accountDescr.toString());
			this.updateCurrentAccount();
				
			if (currentAccount.getAttribute(accountCodeKey)!=null) {
				if (reinit){
					logger.info("Reinitialisation impossible, compte non activ�");
					this.addErrorMessage(null, "IDENTIFICATION.REINITIALISATION.MESSAGE.ACCOUNT.NONACTIVATED");

				}else if(activ){
					logger.info("Construction de la liste des informations personnelles du compte");
					this.buildListPersoInfo(attrPersoInfo);
					this.addInfoMessage(null, "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
					return "gotoPersonalInfo";
				}
			}
			else {
					
				if (reinit){
					logger.info("Construction de la liste des informations personnelles du compte");
					this.buildListPersoInfo(attrPersoInfo);
					List<String> listPossibleChannels = currentAccount.getAttributes(accountPossibleChannelsKey);
						
					if (listPossibleChannels.size()>1){
						buildListBeanCanal(listPossibleChannels);
						this.addInfoMessage(null, "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
						return "gotoChoice"; 	
					}
						
					else if (listPossibleChannels.size()==1){
							
						currentAccount.setOneChoiceCanal(listPossibleChannels.get(0));
						this.getDomainService().sendCode(currentAccount.getAttribute(this.accountIdKey),listPossibleChannels.get(0));
						addInfoMessage(null, "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
						logger.debug("Code envoy�");
						return "gotoPushCode";
						
					}
												
					else{
						logger.debug("aucun canal d'envoi n'est disponible");
						addInfoMessage(null, "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
						addErrorMessage(null, "IDENTIFICATION.MESSAGE.NONECANAL");
					}
				
				}
				else if(activ){
					logger.info("Compte d�ja activ�");
					addErrorMessage(null, "IDENTIFICATION.ACTIVATION.MESSAGE.ALREADYACTIVATEDACCOUNT");
				}
			}
			
		}
		catch (LdapProblemException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
			
		}catch (AuthentificationException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "IDENTIFICATION.MESSAGE.INVALIDACCOUNT");
			
		}catch (LoginException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "APPLICATION.MESSAGE.NULLLOGIN");
			
		}catch (ChannelException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "CODE.ERROR.SENDING");
		}
		
		
		return null;
	}
	
	
	public String pushChangeInfoPerso() {
			
			List<String> attrPersoInfo=Arrays.asList(attributesInfPerso.split(","));
			
			
			try{
				logger.info("Mise � jour des informations personnelles");
				HashMap<String,String> hashBeanPersoInfo=new HashMap<String,String>();
				Iterator it=listBeanPersoInfo.iterator();
				
				int i=0;
				while(it.hasNext()){
					BeanField beanPersoInfo=(BeanField)it.next();
					
					List<BeanMultiValue> lbm = new ArrayList<BeanMultiValue>();
					
					Iterator itBeanPersoInfo=beanPersoInfo.getValues().iterator();
					
					String valueBeanMulti=null;
					int j=0;
					while(itBeanPersoInfo.hasNext()) {
						BeanMultiValue bmv=(BeanMultiValue)itBeanPersoInfo.next();
						if (j>0)valueBeanMulti+=getSeparator()+bmv.getValue();
						else valueBeanMulti=bmv.getValue();
						j++;
					}
					
					if (!"".equals(beanPersoInfo.getValues()) || beanPersoInfo.getValues()!=null ){
						hashBeanPersoInfo.put(beanPersoInfo.getName(), valueBeanMulti);
					}
					else {
						hashBeanPersoInfo.put(beanPersoInfo.getName(), null);
					}
					i++;
				}
				logger.info("Informations personnelles envoy�es au BO pour mise � jour: "+hashBeanPersoInfo.toString());
				
				this.getDomainService().updatePersonalInformations(currentAccount.getAttribute(accountIdKey),currentAccount.getAttribute(accountCodeKey),hashBeanPersoInfo);
					
				this.addInfoMessage(null, "PERSOINFO.MESSAGE.CHANGE.SUCCESSFULL");
					
				if (activ){
					return "gotoCharterAgreement";
				}
				else if(loginChange){
					return "gotoLoginChange";
				}
				else 
					return "gotoPasswordChange";

			
				
			}
			catch (LdapProblemException e) {
				logger.error(e.getMessage());
				addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
			
			}catch (UserPermissionException e) {
				logger.error(e.getMessage());
				addErrorMessage(null, "APPLICATION.USERPERMISSION.PROBLEM");
			}catch (LoginException e) {
				logger.error(e.getMessage());
				addErrorMessage(null, "APPLICATION.MESSAGE.NULLLOGIN");
				
			}
			
			return null;
			
	}
	public String pushAuthentificate(){
		try{
			//Attributs concernant les informations personnelles que l'on souhaite afficher
			List<String> attrPersoInfo=Arrays.asList(attributesInfPerso.split(","));
			
			
			
			accountDescr=this.getDomainService().authentificateUser(beanLogin.getValue().toString(), beanPassword.getValue().toString(),attrPersoInfo);
			
			logger.debug("beanlogin :"+beanLogin.getValue().toString());
			
			if (accountDescr!=null){
				
				logger.info("Authentification r�usssie");
				this.updateCurrentAccount();
								
				if (currentAccount.getAttribute(accountCodeKey)!=null) {
					logger.info("Construction de la liste des informations personnelles du compte");
					this.buildListPersoInfo(attrPersoInfo);
					this.addInfoMessage(null, "AUTHENTIFICATION.MESSAGE.VALID");
					return "gotoPersonalInfo";
				}
				else{
					logger.info("Changement de mot de passe impossible, compte non activ�");
					this.addErrorMessage(null, "AUTHENTIFICATION.MESSAGE.ACCOUNT.NONACTIVATED");
				}
			
			}
			else {
				addErrorMessage(null, "AUTHENTIFICATION.MESSAGE.INVALID");
			}
			
		}catch(AuthentificationException e){
			logger.error(e.getMessage());
			addErrorMessage(null, "AUTHENTIFICATION.MESSAGE.INVALID");
		
		}catch (LdapProblemException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
		
		}catch (UserPermissionException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "APPLICATION.USERPERMISSION.PROBLEM");
		
		}catch (LoginException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "APPLICATION.MESSAGE.NULLLOGIN");
			
		}
		
		
		return null;
	}
	
	public String pushLogin(){
		try {
			
			this.getDomainService().changeLogin(currentAccount.getAttribute(accountIdKey), currentAccount.getAttribute(accountCodeKey), beanNewLogin.getValue().toString());
			currentAccount.setId(beanNewLogin.getValue().toString());
			logger.info("Changement de login r�ussi");
			this.addInfoMessage(null, "LOGIN.MESSAGE.CHANGE.SUCCESSFULL");
			return "gotoAccountEnabled";
			
		}

		catch (LdapProblemException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
		}
		catch (LoginAlreadyExistsException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "LOGIN.MESSAGE.PROBLEM");
		
		}catch (UserPermissionException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "APPLICATION.USERPERMISSION.PROBLEM");
		
		}catch (KerberosException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "KERBEROS.MESSAGE.PROBLEM");
		
		}catch (LoginException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "APPLICATION.MESSAGE.NULLLOGIN");
		
		}catch (PrincipalNotExistsException e) {
			
			try{
				this.getDomainService().setPassword(currentAccount.getAttribute(accountIdKey),currentAccount.getAttribute(this.accountCodeKey),beanNewLogin.getValue().toString(),beanNewPassword.getValue().toString());
				currentAccount.setId(beanNewLogin.getValue().toString());
				logger.info("Changement de login r�ussi");
				this.addInfoMessage(null, "LOGIN.MESSAGE.CHANGE.SUCCESSFULL");
				return "gotoAccountEnabled";
			
			}catch (LdapProblemException ex) {
					logger.error(e.getMessage());
					addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
				
				}catch (UserPermissionException ex) {
					logger.error(e.getMessage());
					addErrorMessage(null, "APPLICATION.USERPERMISSION.PROBLEM");
				
				}catch (KerberosException ex) {
					logger.error(e.getMessage());
					addErrorMessage(null, "KERBEROS.MESSAGE.PROBLEM");
				
				}catch (LoginException ex) {
					logger.error(e.getMessage());
					addErrorMessage(null, "APPLICATION.MESSAGE.NULLLOGIN");
				}
		}

		return null;

	}
	
	public String pushChoice(){
		try{
			
			this.getDomainService().sendCode(currentAccount.getAttribute(accountIdKey),currentAccount.getOneChoiceCanal());
			logger.info("Code envoy� par le FO sur le canal choisi par l'utilisateur");
			return "gotoPushCode";
			
					
		}catch (ChannelException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "CODE.ERROR.SENDING");
		}
		
		return null;
	}
	
	
	public String pushVerifyCode() {
		try{
			currentAccount.setAttribute(this.accountCodeKey, beanCode.getValue().toString());//.setCode(beanCode.getValue().toString());
			if (this.getDomainService().validateCode(currentAccount.getAttribute(accountIdKey), currentAccount.getAttribute(accountCodeKey))){
				logger.info("Code renseign� valide");
				this.addInfoMessage(null, "CODE.MESSAGE.CODESUCCESSFULL");
				beanCode.setValue("");
				return "gotoPersonalInfo";
			}
			
		}catch (UserPermissionException e){
			logger.error(e.getMessage());
			addErrorMessage(null, "APPLICATION.USERPERMISSION.PROBLEM");
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
				logger.info("Charte accept�e");
				this.addInfoMessage(null, "CHARTER.MESSAGE.AGREE.SUCCESSFULL");
				return "gotoPasswordChange";
			}
			
			logger.info("Charte non accept�e");
			this.addErrorMessage(null, "CHARTER.MESSAGE.AGREE.UNSUCCESSFULL");
			return null;
	}
	
	/**
	 * JSF callback.
	 * @return A String.
	 */
	public String pushChangePassword() {
		try {
			this.getDomainService().setPassword(currentAccount.getAttribute(accountIdKey),currentAccount.getAttribute(this.accountCodeKey),beanNewPassword.getValue().toString());
			logger.info("Changement de mot de passe r�ussi");
			this.addInfoMessage(null, "PASSWORD.MESSAGE.CHANGE.SUCCESSFULL");
			//beanNewPassword.setValue("");
			return "gotoAccountEnabled";

		}

		catch (LdapProblemException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
		
		}catch (UserPermissionException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "APPLICATION.USERPERMISSION.PROBLEM");
		
		}catch (KerberosException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "KERBEROS.MESSAGE.PROBLEM");
		
		}catch (LoginException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "APPLICATION.MESSAGE.NULLLOGIN");
			
		}

		return null;
	}
	
	private void buildListPersoInfo(List<String>attrPersoInfo){
		
		
		    for(int i=0;i<listBeanPersoInfo.size();i++)
			{													
					
				logger.debug("currentAccount attribute : "+currentAccount.getAttributes(listBeanPersoInfo.get(i).getName()));
				
				if(attrPersoInfo.contains(listBeanPersoInfo.get(i).getName())) {
					
					
					
				    List<BeanMultiValue> lbm = new ArrayList<BeanMultiValue>();
				
					for (String str : currentAccount.getAttributes(listBeanPersoInfo.get(i).getName())) {
						BeanMultiValue bmv = new BeanMultiValueImpl();
						bmv.setValue(str);
						lbm.add(bmv);
					}
					
												
					if(listBeanPersoInfo.get(i).getIsMultiValue().equals("true")) {
						for (int j=0;j<listBeanPersoInfo.get(i).getNumberOfValue()-currentAccount.getAttributes(listBeanPersoInfo.get(i).getName()).size();j++) {
							BeanMultiValue bmv = new BeanMultiValueImpl();
							bmv.setValue("");
							lbm.add(bmv);
						}
					}
				    listBeanPersoInfo.get(i).setValues(lbm);
					
				}
			}
		    
		    int k=0;
		    while (k < listBeanPersoInfo.size()) { 
		        if (currentAccount.getAttributes(listBeanPersoInfo.get(k).getName()).size()==0)
		        	listBeanPersoInfo.remove(k);
		        else
		        	k++;
		      }
	}
	
	private HashMap<String,String> getMap(List<BeanField> listeInfoToValidate,List<String>attrToValidate){

		HashMap<String,String> hashInfToValidate=new HashMap<String,String>();
	
		for(int j=0;j<listeInfoToValidate.size();j++)
			hashInfToValidate.put(attrToValidate.get(j), listeInfoToValidate.get(j).getValue().toString());
		
		return hashInfToValidate;
		
	}
	
	
	public HashMap<String,List<String>> convertHash(HashMap<String,String> hash){
		
		HashMap<String,List<String>> newHash=new HashMap<String,List<String>>();
		Iterator<Map.Entry<String, String>> it=hash.entrySet().iterator();
		
		
		
		while(it.hasNext()){
			Map.Entry<String, String> e=it.next();
			newHash.put(e.getKey(), Arrays.asList(e.getValue().split(",")));
			//newHash.put(e.getKey(), Arrays.asList(e.getValue()));
		}
		return newHash;
	}
	
	
	public void updateCurrentAccount(){
		//currentAccount.setAttributes(this.convertHash(accountDescr));
		
		currentAccount.setAttributes(this.convertHash(accountDescr));
		currentAccount.setId(currentAccount.getAttribute(accountIdKey));
		currentAccount.setMail(currentAccount.getAttribute(accountMailKey));
		currentAccount.setEmailPerso(currentAccount.getAttribute(accountMailPersoKey));
		currentAccount.setPager(currentAccount.getAttribute(accountPagerKey));
	}
	
	
	public String getPartialMailPerso(){
		String mailPerso=currentAccount.getEmailPerso();
		String newMailPerso="";
		if (!"".equals(mailPerso)){	
			newMailPerso="xxxxxx"+mailPerso.substring(6);
		}
		return newMailPerso;
	}
	
	public String getPartialPager(){
		String pager=currentAccount.getPager();
		String newPager="";
		if (!"".equals(pager)){
		newPager=pager.substring(0,6)+"XXXX";
		}
		return newPager;	
	}
	
	private void buildListBeanCanal(List<String>listPossibleChannels){
		listBeanCanal=new ArrayList<BeanField>();
		for(int i=0;i<=listPossibleChannels.size()-1;i++){
			if (listPossibleChannels.get(i).equalsIgnoreCase(accountMailPersoKey)){
				BeanFieldImpl bean=new BeanFieldImpl();
				bean.setValue(accountMailPersoKey);
				bean.setKey(labelCanalMailPerso);
				listBeanCanal.add(bean);
			}
			
			if (listPossibleChannels.get(i).equalsIgnoreCase(accountGestKey)){
				BeanFieldImpl bean=new BeanFieldImpl();
				bean.setValue(accountGestKey);
				bean.setKey(labelCanalGest);
				listBeanCanal.add(bean);
			}
		
			if (listPossibleChannels.get(i).equalsIgnoreCase(accountPagerKey)){
				BeanFieldImpl bean=new BeanFieldImpl();
				bean.setValue(accountPagerKey);
				bean.setKey(labelCanalPager);
				listBeanCanal.add(bean);
			}
		}
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
	
	public String getStatusAnotherStudent() {
		return statusAnotherStudent;
	}

    public void setStatusAnotherStudent(String statusAnotherStudent) {
		this.statusAnotherStudent = statusAnotherStudent;
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

	public BeanField getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanField beanLogin) {
		this.beanLogin = beanLogin;
	}

	public String getAccountPagerKey() {
		return accountPagerKey;
	}

	public void setAccountPagerKey(String accountPagerKey) {
		this.accountPagerKey = accountPagerKey;
	}

	public List<BeanField> getListBeanCanal() {
		return listBeanCanal;
	}

	public void setListBeanCanal(List<BeanField> listBeanCanal) {
		this.listBeanCanal = listBeanCanal;
	}

	
	public boolean isLoginChange() {
		return loginChange;
	}

	public void setLoginChange(boolean loginChange) {
		this.loginChange = loginChange;
	}

	public String getProcedureLoginChange() {
		return procedureLoginChange;
	}

	public void setProcedureLoginChange(String procedureLoginChange) {
		this.procedureLoginChange = procedureLoginChange;
	}

	public BeanField getBeanNewLogin() {
		return beanNewLogin;
	}

	public void setBeanNewLogin(BeanField beanNewLogin) {
		this.beanNewLogin = beanNewLogin;
	}
	
	public BeanField getBeanNewPassword() {
		return beanNewPassword;
	}

	public void setBeanNewPassword(BeanField beanNewPassword) {
		this.beanNewPassword = beanNewPassword;
	}

	public BeanField getBeanPassword() {
		return beanPassword;
	}

	public void setBeanPassword(BeanField beanPassword) {
		this.beanPassword = beanPassword;
	}

	public String getAccountGestKey() {
		return accountGestKey;
	}

	public void setAccountGestKey(String accountGestKey) {
		this.accountGestKey = accountGestKey;
	}
	
	
	public String getLabelCanalMailPerso() {
		return labelCanalMailPerso;
	}

	public void setLabelCanalMailPerso(String labelCanalMailPerso) {
		this.labelCanalMailPerso = labelCanalMailPerso;
	}

	public String getLabelCanalPager() {
		return labelCanalPager;
	}

	public void setLabelCanalPager(String labelCanalPager) {
		this.labelCanalPager = labelCanalPager;
	}

	public String getLabelCanalGest() {
		return labelCanalGest;
	}

	public void setLabelCanalGest(String labelCanalGest) {
		this.labelCanalGest = labelCanalGest;
	}
	
	public String getAccountPossibleChannelsKey() {
		return accountPossibleChannelsKey;
	}

	public void setAccountPossibleChannelsKey(String accountPossibleChannelsKey) {
		this.accountPossibleChannelsKey = accountPossibleChannelsKey;
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
	
	public String getAttributesAnotherStudentToValidate() {
		return attributesAnotherStudentToValidate;
	}

    public void setAttributesAnotherStudentToValidate(
			String attributesAnotherStudentToValidate) {
		this.attributesAnotherStudentToValidate = attributesAnotherStudentToValidate;
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
	
	public List<BeanField> getListInfoAnotherStudentToValidate() {
		return listInfoAnotherStudentToValidate;
	}

	public void setListInfoAnotherStudentToValidate(
			List<BeanField> listInfoAnotherStudentToValidate) {
		this.listInfoAnotherStudentToValidate = listInfoAnotherStudentToValidate;
	}


	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

}
