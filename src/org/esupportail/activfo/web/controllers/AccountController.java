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
import java.util.Set;

import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.activfo.domain.beans.User;
import org.esupportail.activfo.domain.beans.channels.Channel;
import org.esupportail.activfo.exceptions.AuthentificationException;
import org.esupportail.activfo.exceptions.ChannelException;
import org.esupportail.activfo.exceptions.KerberosException;
import org.esupportail.activfo.exceptions.LdapProblemException;
import org.esupportail.activfo.exceptions.LoginAlreadyExistsException;
import org.esupportail.activfo.exceptions.LoginException;
import org.esupportail.activfo.exceptions.PrincipalNotExistsException;
import org.esupportail.activfo.exceptions.UserPermissionException;
import org.esupportail.activfo.web.beans.BeanField;
import org.esupportail.activfo.web.beans.CategoryBeanField;
import org.esupportail.activfo.web.beans.BeanFieldImpl;
import org.esupportail.activfo.web.beans.BeanMultiValue;
import org.esupportail.activfo.web.beans.BeanMultiValueImpl;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.web.controllers.ExceptionController;

import org.esupportail.commons.services.smtp.AsynchronousSmtpServiceImpl;
import org.esupportail.commons.utils.Assert;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

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
	private boolean dataChange=false;
	private boolean viewDataChange=false;
	
	private String accountIdKey;
	private String accountMailKey;
	private String accountMailPersoKey;
	private String accountPagerKey;
	private String accountDNKey;
	private String accountCodeKey;
	private String accountGestKey;
	private String accountPossibleChannelsKey;
	private String accountEmpIdKey;
		
	
	//liste des champs pour l'affichage des informations personnelles
	private List<BeanField> listBeanPersoInfo;
		
	//liste des attributs pour l'affichage des informations personnelles
	private String attributesInfPerso;
		
	private List<Channel> availableChannels;
	
	
	//liste g�n�rique des champs pour la validation
	private List<BeanField> listInfoToValidate;
			
	private List<BeanField<String>> beanFieldChannels;	
	
	
	//liste des champs correspondant aux procedures
	private List<BeanField> listBeanProcedureWithCas;
	private List<BeanField> listBeanProcedureWithoutCas;
	
	//Les status pris en compte par l'application
	//Chaque status contient une liste de champs de validation spécifiques
	private HashMap<BeanField,List<BeanField>> beanFieldStatus;
	
	private List<CategoryBeanField> listBeanDataChange;
	private List<BeanField> listDataChangeInfos=new ArrayList<BeanField>();
	
	private List<CategoryBeanField> listBeanViewDataChange;
	
	private String attributesDataChange;
	
	
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
	
	private CategoryBeanField beanTestInfo;  
	
	//decriptif du compte suite � validation
	HashMap<String,String> accountDescr=new HashMap<String,String>();
	
	
	private String procedureReinitialisation;
	private String procedureActivation;
	private String procedurePasswordChange;
	private String procedureLoginChange;
	private String procedureDataChange;
		
	private String separator;
	
	private AsynchronousSmtpServiceImpl smtpService;
	private String subjectDataChange;
	private String body1DataChange;
	private String body2DataChange;
	
	private List<Channel> channels;
	private String oneChoiceCanal;
	
	private SessionController sessionController;
	private ExceptionController exceptionController;
	
	private String targetService;
	
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
			dataChange=false;
			viewDataChange=false;
		}
		else if (currentAccount.getOneRadioProcedure().equals(procedurePasswordChange)){
			passwChange=true;
			reinit=false;
			activ=false;
			loginChange=false;
			dataChange=false;
			viewDataChange=false;
			return "gotoAuthentification";
		}
		else if (currentAccount.getOneRadioProcedure().equals(procedureDataChange)) {
			passwChange=false;
			activ=false;
			reinit=false;
			loginChange=false;
			dataChange=true;
			viewDataChange=false;
			return "gotoDataChangeWithCas";
		}
		else if (currentAccount.getOneRadioProcedure().equals(procedureLoginChange)){
			passwChange=false;
			reinit=false;
			activ=false;
			dataChange=false;
			loginChange=true;
			viewDataChange=false;
			return "gotoAuthentification";
		}
		else{
			activ=true;
			reinit=false;
			passwChange=false;
			loginChange=false;
			dataChange=false;
			viewDataChange=false;
		}
		
		for(BeanField<String> bf : beanFieldStatus.keySet())
			if(bf.getValue().equals(currentAccount.getOneRadioValue()))
					this.listInfoToValidate=beanFieldStatus.get(bf);			
		
		return "goToInfoToValidate";
	}
	
		
	public String pushValid() {
		try {
			
			HashMap<String,String> hashInfToValidate=new HashMap<String,String>();
			for(BeanField<String> bf:listInfoToValidate)
				hashInfToValidate.put(bf.getName(), bf.getValue());
			
			logger.debug("La validation concerne les donn�es suivantes: "+hashInfToValidate.toString());
			
			//Attributs concernant les informations personnelles que l'on souhaite afficher
			
			List<String> attrPersoInfo=Arrays.asList(attributesInfPerso.split(","));
			
			accountDescr=this.getDomainService().validateAccount(hashInfToValidate,attrPersoInfo);
			
			logger.info("accoutDescr : "+accountDescr.toString());
			
			//recuperation liste pour attributs perso info
				
			logger.info("Identification valide");
			
			this.updateCurrentAccount();
				
			if (currentAccount.getAttribute(accountCodeKey)!=null) {
				if (reinit){
					logger.info("Reinitialisation impossible, compte non activ�");
					this.addErrorMessage(null, "IDENTIFICATION.REINITIALISATION.MESSAGE.ACCOUNT.NONACTIVATED");
				}else if(activ){
					logger.info("Construction de la liste des informations personnelles du compte");
					this.buildListPersoInfo(listBeanPersoInfo,attrPersoInfo);
					this.addInfoMessage(null, "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
					return "gotoPersonalInfo";
				}
			}
			else {
					
				if (reinit){
					logger.info("Construction de la liste des informations personnelles du compte");
					this.buildListPersoInfo(listBeanPersoInfo,attrPersoInfo);
					List<String> listPossibleChannels = currentAccount.getAttributes(accountPossibleChannelsKey);
					
					logger.debug("listpossible "+listPossibleChannels.toString());
					buildChannels(listPossibleChannels);
					if (beanFieldChannels.size()>1){						
						this.addInfoMessage(null, "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
						return "gotoChoice"; 	
					}	
					else if (beanFieldChannels.size()==1){		
						oneChoiceCanal=beanFieldChannels.get(0).getValue();
						this.getDomainService().sendCode(currentAccount.getAttribute(this.accountIdKey),beanFieldChannels.get(0).getValue());
						addInfoMessage(null, "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
						logger.debug("Code envoyé via le canal : "+beanFieldChannels.get(0).getValue());
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
	//TODO		addErrorMessage(null, statusStudent.equals(currentAccount.getOneRadioValue()) ? "IDENTIFICATION.MESSAGE.INVALIDACCOUNT.STUDENT" : "IDENTIFICATION.MESSAGE.INVALIDACCOUNT");
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
	
	public Channel getSentChannel(){		
		for(Channel c:channels)
			if(c.getName().equals(oneChoiceCanal))
				return c;
		return null;
	}
	
	public String pushChangeInfoPerso() {
			
			Iterator it;
			
			HashMap<String,String> DataChangeMaps=new HashMap<String,String>();
			
			
			if(dataChange) {
				List<String> attrPersoInfo=Arrays.asList(attributesDataChange.split(","));
			} else {
				List<String> attrPersoInfo=Arrays.asList(attributesInfPerso.split(","));
			}
			logger.info("List attrPersoInfo : "+attributesInfPerso.toString());
			
			try{
				logger.info("Mise � jour des informations personnelles");
				HashMap<String,String> hashBeanPersoInfo=new HashMap<String,String>();
				if(dataChange)it=listDataChangeInfos.iterator();
				else it=listBeanPersoInfo.iterator();	
				int i=0;
				HashMap<String,String> oldValue=new HashMap<String,String>();
				HashMap<String,String> newValue=new HashMap<String,String>();
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
						//if (beanPersoInfo.getName().contains("mailForwardingAddress")) valueBeanMulti=currentAccount.getEmailPerso();
						
						if(!currentAccount.getAttributes(beanPersoInfo.getName()).contains(bmv.getValue()) && !beanPersoInfo.isUpdateable()) {
							oldValue.put(beanPersoInfo.getName(), currentAccount.getAttributes(beanPersoInfo.getName()).toString());
							newValue.put(beanPersoInfo.getName(), bmv.getValue());
							//logger.debug("Attribute,oldValue and newValue : "+beanPersoInfo.getName()+", "+currentAccount.getAttributes(beanPersoInfo.getName()).toString()+","+bmv.getValue());
						}
						j++;
					}									
					if (beanPersoInfo.isUpdateable() && (!"".equals(beanPersoInfo.getValues()) || beanPersoInfo.getValues()!=null) ){
						hashBeanPersoInfo.put(beanPersoInfo.getName(), valueBeanMulti);
					}
					else if (beanPersoInfo.isUpdateable() && ("".equals(beanPersoInfo.getValues()) || beanPersoInfo.getValues()==null) ) {
						DataChangeMaps.put(beanPersoInfo.getName(), valueBeanMulti);
						hashBeanPersoInfo.put(beanPersoInfo.getName(), null);
					}					
					i++;
				}
				logger.info("Informations personnelles envoy�es au BO pour mise � jour: "+hashBeanPersoInfo.toString());
				
				this.getDomainService().updatePersonalInformations(currentAccount.getAttribute(accountIdKey),currentAccount.getAttribute(accountCodeKey),hashBeanPersoInfo);
					
				this.addInfoMessage(null, "PERSOINFO.MESSAGE.CHANGE.SUCCESSFULL");
				//Maj Account
				Set<String> keySet=hashBeanPersoInfo.keySet();
				for(String key:keySet)
					if(hashBeanPersoInfo.get(key)!=null)
						this.accountDescr.put(key, hashBeanPersoInfo.get(key));
				this.updateCurrentAccount();
				
				logger.debug("Informations Account mises à jour :"+accountDescr.toString());
				
				this.sendMessage(oldValue,newValue);
				
				if (activ){
					return "gotoCharterAgreement";
				}
				else if(loginChange){
					return "gotoLoginChange";
				}
				else if (dataChange) {
					viewDataChange=true;
					dataChange=false;
					return "gotoViewDataChange";
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
	public String pushAuthentificate() {
		
		try{
			//Attributs concernant les informations personnelles que l'on souhaite afficher
			
			List<String> attrPersoInfo=Arrays.asList(attributesInfPerso.split(","));
			List<String> attrDataChange=Arrays.asList(attributesDataChange.split(","));
			
			if (dataChange) {	
					User user=sessionController.getCurrentUser();
					accountDescr=this.getDomainService().authentificateUserWithCas(user.getId(),user.getProxyTicket(targetService),targetService,attrDataChange);
						
			}else
				accountDescr=this.getDomainService().authentificateUser(beanLogin.getValue().toString(), beanPassword.getValue().toString(),attrPersoInfo);
			
			logger.debug("accountDescr :"+accountDescr.toString());
			
			if (accountDescr!=null){
				
				logger.info("Authentification r�usssie");
				this.updateCurrentAccount();
								
				if (currentAccount.getAttribute(accountCodeKey)!=null) {
					logger.info("Construction de la liste des informations personnelles du compte");
					if (dataChange)
						this.buildListPersoInfo(listDataChangeInfos,attrDataChange);
					else this.buildListPersoInfo(listBeanPersoInfo,attrPersoInfo);
					
					//this.addInfoMessage(null, "AUTHENTIFICATION.MESSAGE.VALID");
					if (dataChange) {
						//viewDataChange=false;
						return null; 
					} else {
						logger.debug("Check isDataChangeProcedure : "+dataChange);
						return "gotoPersonalInfo";
					}
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
	        sessionController.restart();
			
		}catch (LdapProblemException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "LDAP.MESSAGE.PROBLEM");
			sessionController.restart();
		
		}catch (UserPermissionException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "APPLICATION.USERPERMISSION.PROBLEM");
			sessionController.restart();
		
		}catch (LoginException e) {
			logger.error(e.getMessage());
			addErrorMessage(null, "APPLICATION.MESSAGE.NULLLOGIN");
			sessionController.restart();
			
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
			
			this.getDomainService().sendCode(currentAccount.getAttribute(accountIdKey),oneChoiceCanal);
			logger.debug("Code demandé par utilisateur pour le recevoir sur le canal : "+oneChoiceCanal);
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
		
		addErrorMessage(null, "VALIDATOR.CODE.INVALID");
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
	
	private void buildListPersoInfo(List<BeanField> buildlist,List<String>attributesInfos){
		
		
		    for(int i=0;i<buildlist.size();i++)
			{													
					
				logger.debug("currentAccount attribute : "+buildlist.get(i).getName()+" "+currentAccount.getAttributes(buildlist.get(i).getName()));
				
				//listBeanPersoInfo.get(i).getName().contains(attributesInfos)
				
				if(attributesInfos.contains(buildlist.get(i).getName())) {
					List<BeanMultiValue> lbm = new ArrayList<BeanMultiValue>();
				
					for (String str : currentAccount.getAttributes(buildlist.get(i).getName())) {
						BeanMultiValue bmv = new BeanMultiValueImpl();
						bmv.setValue(str);
						lbm.add(bmv);
					}
												
					if(buildlist.get(i).isMultiValue()) {
						for (int j=0;j<buildlist.get(i).getNumberOfValue()-currentAccount.getAttributes(buildlist.get(i).getName()).size();j++) {
							BeanMultiValue bmv = new BeanMultiValueImpl();
							bmv.setValue("");
							lbm.add(bmv);
						}
					}
				    buildlist.get(i).setValues(lbm);
				}
			}
		    
		    int k=0;
		    while (k < buildlist.size())
		    	if (currentAccount.getAttributes(buildlist.get(k).getName()).size()==0) buildlist.remove(k);
		        else k++;
		    
	}
			
	public HashMap<String,List<String>> convertHash(HashMap<String,String> hash){
		
		HashMap<String,List<String>> newHash=new HashMap<String,List<String>>();
		Iterator<Map.Entry<String, String>> it=hash.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<String, String> e=it.next();
			//newHash.put(e.getKey(), Arrays.asList(e.getValue().split(",")));
			newHash.put(e.getKey(), Arrays.asList(e.getValue().split(getSeparator())));
			
		}
		return newHash;
	}
	
	
	
	public void updateCurrentAccount(){
		currentAccount.setAttributes(this.convertHash(accountDescr));
		currentAccount.setId(currentAccount.getAttribute(accountIdKey));
		currentAccount.setMail(currentAccount.getAttribute(accountMailKey));
		currentAccount.setEmailPerso(currentAccount.getAttribute(accountMailPersoKey));
		currentAccount.setPager(currentAccount.getAttribute(accountPagerKey));
	}
		
	//TODO externaliser cette fonction. 
	//Rendre plus générique pour permettre des envois de mail suivant le profil de l'utilisateur
	public void sendMessage(HashMap<String,String> oldValue, HashMap<String,String> newValue) {
		
		InternetAddress mail=null;
		
		String mailBody=this.body1DataChange;
		String mailBody2=this.body2DataChange;
		String newSubject = null;
		mailBody=mailBody.replace("{0}", currentAccount.getAttribute(accountDNKey)!=null?currentAccount.getAttribute(accountDNKey):"");
		mailBody=mailBody.replace("{1}", currentAccount.getAttribute(accountEmpIdKey)!=null?currentAccount.getAttribute(accountEmpIdKey):"");
		mailBody=mailBody+mailBody2;
		
        Iterator<Map.Entry<String, String>> it=oldValue.entrySet().iterator();
        Iterator<Map.Entry<String, String>> itnew=newValue.entrySet().iterator();
        
		while(it.hasNext()){
			Map.Entry<String, String> o=it.next();
			Map.Entry<String, String> n=itnew.next();
			mailBody=mailBody+"<tr><td>"+o.getKey()+"</td><td>"+o.getValue()+"</td><td>"+Arrays.asList(n.getValue().split(getSeparator()))+"</td><tr>";
		}
		
		mailBody=mailBody+"</table>";
		
		try {
			mail = new InternetAddress(smtpService.getFromAddress().getAddress());
		} catch (AddressException e) {
			logger.debug("Error Handling for InternetAddress ");
		}
		
		
		newSubject=subjectDataChange.replace("{0}", currentAccount.getAttribute(accountDNKey)!=null?currentAccount.getAttribute(accountDNKey):"");
		
		if (newValue.size()>0)
			smtpService.send(mail, newSubject, mailBody, "");
	}
	
	private void buildChannels(List<String>listPossibleChannels){
		beanFieldChannels=new ArrayList<BeanField<String>>();
		availableChannels=new ArrayList<Channel>();
		for(Channel channel:channels)
			if(listPossibleChannels.contains(channel.getName())){		
				BeanFieldImpl<String> bean=new BeanFieldImpl<String>();
				bean.setValue(channel.getName());
				bean.setKey(channel.getLabel());
				beanFieldChannels.add(bean);	
				availableChannels.add(channel);
			}
	}	

	public List<BeanField> getListInfoToValidate() {
		return listInfoToValidate;
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
	
	public String getProcedureDataChange() {
		return procedureDataChange;
	}

	public void setProcedureDataChange(String procedureDataChange) {
		this.procedureDataChange = procedureDataChange;
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
	
	public boolean isDataChange() {
		return dataChange;
	}

	public void setDataChange(boolean dataChange) {
		this.dataChange = dataChange;
	}

    /**
	 * @return the listBeanProcedureWithCas
	 */
	public List<BeanField> getListBeanProcedureWithCas() {
		return listBeanProcedureWithCas;
	}


	/**
	 * @param listBeanProcedureWithCas the listBeanProcedureWithCas to set
	 */
	public void setListBeanProcedureWithCas(List<BeanField> listBeanProcedureWithCas) {
		this.listBeanProcedureWithCas = listBeanProcedureWithCas;
	}


	/**
	 * @return the listBeanProcedureWithoutCas
	 */
	public List<BeanField> getListBeanProcedureWithoutCas() {
		return listBeanProcedureWithoutCas;
	}


	/**
	 * @param listBeanProcedureWithoutCas the listBeanProcedureWithoutCas to set
	 */
	public void setListBeanProcedureWithoutCas(
			List<BeanField> listBeanProcedureWithoutCas) {
		this.listBeanProcedureWithoutCas = listBeanProcedureWithoutCas;
	}
	
	public Set<BeanField> getListBeanStatus() {
		
		return beanFieldStatus.keySet();
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}


	

	public String getAttributesDataChange() {
		return attributesDataChange;
	}

	public void setAttributesDataChange(String attributesDataChange) {
		this.attributesDataChange = attributesDataChange;
	}


	/**
	 * @return the beanTestInfo
	 */
	public CategoryBeanField getBeanTestInfo() {
		return beanTestInfo;
	}


	/**
	 * @param beanTestInfo the beanTestInfo to set
	 */
	public void setBeanTestInfo(CategoryBeanField beanTestInfo) {
		this.beanTestInfo = beanTestInfo;
	}


	/**
	 * @return the listBeanDataChange
	 */
	public List<CategoryBeanField> getListBeanDataChange() {
		return listBeanDataChange;
	}


	/**
	 * @param listBeanDataChange the listBeanDataChange to set
	 */
	public void setListBeanDataChange(List<CategoryBeanField> listBeanDataChange) {
		
		this.listBeanDataChange = listBeanDataChange;
						
	}
	
	

	/**
	 * @return the listBeanViewDataChange
	 */
	public List<CategoryBeanField> getListBeanViewDataChange() {
		return listBeanViewDataChange;
	}


	/**
	 * @param listBeanViewDataChange the listBeanViewDataChange to set
	 */
	public void setListBeanViewDataChange(
			List<CategoryBeanField> listBeanViewDataChange) {
		this.listBeanViewDataChange = listBeanViewDataChange;
	}


	/**
	 * @return the viewDataChange
	 */
	public boolean isViewDataChange() {
		return viewDataChange;
	}

	/**
	 * @param viewDataChange the viewDataChange to set
	 */
	public void setViewDataChange(boolean viewDataChange) {
		this.viewDataChange = viewDataChange;
	}


	/**
	 * @return the smtpService
	 */
	public AsynchronousSmtpServiceImpl getSmtpService() {
		return smtpService;
	}


	/**
	 * @param smtpService the smtpService to set
	 */
	public void setSmtpService(AsynchronousSmtpServiceImpl smtpService) {
		this.smtpService = smtpService;
	}


	/**
	 * @return the subjectDataChange
	 */
	public String getSubjectDataChange() {
		return subjectDataChange;
	}


	/**
	 * @param subjectDataChange the subjectDataChange to set
	 */
	public void setSubjectDataChange(String subjectDataChange) {
		this.subjectDataChange = subjectDataChange;
	}


	/**
	 * @return the accountEmpIdKey
	 */
	public String getAccountEmpIdKey() {
		return accountEmpIdKey;
	}


	/**
	 * @param accountEmpIdKey the accountEmpIdKey to set
	 */
	public void setAccountEmpIdKey(String accountEmpIdKey) {
		this.accountEmpIdKey = accountEmpIdKey;
	}


	/**
	 * @return the body1DataChangeisFirstCasAuth=true;
	 */
	public String getBody1DataChange() {
		return body1DataChange;
	}


	/**
	 * @param body1DataChange the body1DataChange to set
	 */
	public void setBody1DataChange(String body1DataChange) {
		this.body1DataChange = body1DataChange;
	}


	/**
	 * @return the body2DataChange
	 */
	public String getBody2DataChange() {
		return body2DataChange;
	}


	/**
	 * @param body2DataChange the body2DataChange to set
	 */
	public void setBody2DataChange(String body2DataChange) {
		this.body2DataChange = body2DataChange;
	}
	
	@Override
	public void afterPropertiesSetInternal() {
		Assert.notNull(this.listBeanDataChange, "property listBeanDataChange of class " 
				+ this.getClass().getName() + " can not be null");
		for(CategoryBeanField cbf : listBeanDataChange){ 
			List<BeanField> bflist=cbf.getListBeanField();
			for(BeanField bf: bflist) {
			    logger.debug("Beanfield : "+bf);
			    if(!listDataChangeInfos.contains(bf))
			    	listDataChangeInfos.add(bf);
			}
		}
	}
	
	public List<CategoryBeanField> getBeanData() {
		if (dataChange) pushAuthentificate();
		if (viewDataChange) return listBeanViewDataChange;
		else return listBeanDataChange;
	}



	/**
	 * @return the sessionController
	 */
	public SessionController getSessionController() {
		return sessionController;
	}


	/**
	 * @param sessionController the sessionController to set
	 */
	public void setSessionController(SessionController sessionController) {
		this.sessionController = sessionController;
	}


	/**
	 * @return the exceptionController
	 */
	public ExceptionController getExceptionController() {
		return exceptionController;
	}


	/**
	 * @param exceptionController the exceptionController to set
	 */
	public void setExceptionController(ExceptionController exceptionController) {
		this.exceptionController = exceptionController;
	}


	/**
	 * @return the targetService
	 */
	public String getTargetService() {
		return targetService;
	}


	/**
	 * @param targetService the targetService to set
	 */
	public void setTargetService(String targetService) {
		this.targetService = targetService;
	}

	/**
	 * @param listBeanCanal the listBeanCanal to set
	 */
	public void setListBeanCanal(List<BeanField<String>> listBeanCanal) {
		this.beanFieldChannels = listBeanCanal;
	}


	/**
	 * @return the listBeanCanal
	 */
	public List<BeanField<String>> getListBeanCanal() {
		return beanFieldChannels;
	}

	/**
	 * @return the availableChannels
	 */
	public List<Channel> getAvailableChannels() {
		return availableChannels;
	}


	/**
	 * @return the channels
	 */
	public List<Channel> getChannels() {
		return channels;
	}


	/**
	 * @param channels the channels to set
	 */
	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}


	/**
	 * @return the oneChoiceCanal
	 */
	public String getOneChoiceCanal() {
		return oneChoiceCanal;
	}


	/**
	 * @param oneChoiceCanal the oneChoiceCanal to set
	 */
	public void setOneChoiceCanal(String oneChoiceCanal) {
		this.oneChoiceCanal = oneChoiceCanal;
	}


	/**
	 * @return the beanFieldStatus
	 */
	public HashMap<BeanField, List<BeanField>> getBeanFieldStatus() {
		return beanFieldStatus;
	}


	/**
	 * @param beanFieldStatus the beanFieldStatus to set
	 */
	public void setBeanFieldStatus(
			HashMap<BeanField, List<BeanField>> beanFieldStatus) {
		this.beanFieldStatus = beanFieldStatus;
	}
	
}
