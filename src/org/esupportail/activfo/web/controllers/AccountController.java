/**
 * ESUP-Portail LDAP Account Activation - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ
 */
package org.esupportail.activfo.web.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.codehaus.xfire.XFireRuntimeException;
import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.activfo.domain.beans.User;
import org.esupportail.activfo.domain.beans.channels.Channel;
import org.esupportail.activfo.domain.beans.mailing.Mailing;
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
import org.esupportail.commons.utils.Assert;
import javax.faces.convert.Converter;

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
	private String accountCodeKey;
	private String accountGestKey;
	private String accountPossibleChannelsKey;
	
	private List<Mailing> mailing;
	private List<Mailing> mailingUpdateableField;
			
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
		
	private List<BeanField> listDataChangeInfos=new ArrayList<BeanField>();
	private List<BeanField> listBeanPersoInfo=new ArrayList<BeanField>();
	
	private List<CategoryBeanField> categoryBeanDataChangeDigest;
	private List<CategoryBeanField> categoryBeanDataChange;
	private List<CategoryBeanField> categoryBeanViewDataChange;
	
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
	
	private List<Channel> channels;
	private String oneChoiceCanal;
	
	private SessionController sessionController;
	private ExceptionController exceptionController;
	
	private String targetService;
	
	private String csvFileName;
	private String attributesCsvFile;
	HashMap<BeanField,List<String>> attrNewCsv= new HashMap<BeanField,List<String>>();
	HashMap<BeanField,List<String>> attrOldCsv= new HashMap<BeanField,List<String>>();

	
	/**
	 * Bean constructor.
	 */
	public AccountController() {
		super();
		
	}
	
	@Override
	public void afterPropertiesSetInternal() {
		Assert.notNull(this.categoryBeanDataChange, "property categoryBeanDataChange of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.categoryBeanDataChangeDigest, "property categoryBeanDataChangeDigest of class " 
				+ this.getClass().getName() + " can not be null");	
		Assert.notNull(this.mailing, "property mailing of class " 
				+ this.getClass().getName() + " can not be null");
	}
	
	private void buildProfilingListDataChangeInfos(){
		for(CategoryBeanField cbf : categoryBeanDataChange){ 
			List<BeanField> bflist=cbf.getProfilingListBeanField();
			for(BeanField bf: bflist)			 
			    if(!listDataChangeInfos.contains(bf))
			    	listDataChangeInfos.add(bf);			
		}
	}
	
	private List<BeanField> getAllData(){
		List<BeanField> data=new ArrayList<BeanField>();
		for(CategoryBeanField cbf : categoryBeanDataChange){ 
			List<BeanField> bflist=cbf.getListBeanField();
			for(BeanField bf: bflist)			 
			    if(!data.contains(bf))
			    	data.add(bf);			
		}
		for(CategoryBeanField cbf : categoryBeanViewDataChange){ 
			List<BeanField> bflist=cbf.getListBeanField();
			for(BeanField bf: bflist)			 
			    if(!data.contains(bf))
			    	data.add(bf);			
		}
		return data;
	}
	
	private void buildProfilingListBeanPersoInfo(){			
		for(CategoryBeanField cbf : categoryBeanDataChangeDigest){ 
			List<BeanField> bflist=cbf.getProfilingListBeanField();
			for(BeanField bf: bflist)
			    if(!listBeanPersoInfo.contains(bf))
			    	listBeanPersoInfo.add(bf);		
		}			
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
		
		if (currentAccount.getProcess().equals(procedureReinitialisation)){
			reinit=true;
			passwChange=false;
			activ=false;
			loginChange=false;
			dataChange=false;
			viewDataChange=false;
			
		}
		else if (currentAccount.getProcess().equals(procedurePasswordChange)){
			passwChange=true;
			reinit=false;
			activ=false;
			loginChange=false;
			dataChange=false;
			viewDataChange=false;
			return "gotoAuthentification";
		}
		else if (currentAccount.getProcess().equals(procedureDataChange)) {
			passwChange=false;
			activ=false;
			reinit=false;
			loginChange=false;
			dataChange=true;
			viewDataChange=false;
			return "gotoDataChangeWithCas";
		}
		else if (currentAccount.getProcess().equals(procedureLoginChange)){
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
			if(bf.getValue().equals(currentAccount.getStatus()))
					this.listInfoToValidate=beanFieldStatus.get(bf);			
		
		return "goToInfoToValidate";
	}
	
	/**
	 * But : Gestion des exceptions
	 * @param exception � catcher,
	 *        errMess vaut null si utilisation du Tag Messages sinon vaut "messageErrControleur" si utilisation du Tag Message
	 * @return Rien
	 *        
	 */
	public void exceptions(Exception exception, String errMess){
		logger.debug("exception",exception);
		if (exception instanceof XFireRuntimeException)addErrorMessage(errMess, "LDAP.MESSAGE.BO.INDISPONIBLE");
		else if (exception instanceof LdapProblemException)addErrorMessage(errMess, "LDAP.MESSAGE.PROBLEM");
		else if (exception instanceof LoginException)addErrorMessage(errMess, "APPLICATION.MESSAGE.NULLLOGIN");		
		else if (exception instanceof AuthentificationException)addErrorMessage(errMess,"AUTHENTIFICATION.MESSAGE.INVALID");
		else if (exception instanceof UserPermissionException)addErrorMessage(errMess, "APPLICATION.USERPERMISSION.PROBLEM");
		else if (exception instanceof KerberosException)addErrorMessage("messageErrControleur", "KERBEROS.MESSAGE.PROBLEM");
		else if (exception instanceof LoginAlreadyExistsException)addErrorMessage("messageErrControleur", "LOGIN.MESSAGE.PROBLEM");
		else if (exception instanceof ChannelException)addErrorMessage("messageErrControleur", "CODE.ERROR.SENDING");
		else if (exception instanceof RuntimeException) throw (RuntimeException)(exception);
		else logger.error("Erreur inattendue");
		
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
			
			this.updateCurrentAccount();
				
			if (currentAccount.getAttribute(accountCodeKey)!=null) {
				if (reinit){
					logger.info("Reinitialisation impossible, compte non activ�");
					this.addErrorMessage("messageErrControleur", "IDENTIFICATION.REINITIALISATION.MESSAGE.ACCOUNT.NONACTIVATED");
				}else if(activ){
					logger.info("Construction de la liste des informations personnelles du compte");
					this.buildProfilingListBeanPersoInfo();
					this.buildListPersoInfo(listBeanPersoInfo,attrPersoInfo);					
					this.addInfoMessage("messageErrControleur", "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
					return "gotoPersonalInfo";
				}
			}
			else {
					
				if (reinit){
					logger.info("Construction de la liste des informations personnelles du compte");
					this.buildProfilingListBeanPersoInfo();
					this.buildListPersoInfo(listBeanPersoInfo,attrPersoInfo);
					List<String> listPossibleChannels = currentAccount.getAttributes(accountPossibleChannelsKey);
					
					logger.debug("listpossible "+listPossibleChannels.toString());
					buildChannels(listPossibleChannels);
					if (beanFieldChannels.size()>1){						
						this.addInfoMessage("messageErrControleur", "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
						return "gotoChoice"; 	
					}	
					else if (beanFieldChannels.size()==1){		
						oneChoiceCanal=beanFieldChannels.get(0).getValue();
						this.getDomainService().sendCode(currentAccount.getAttribute(this.accountIdKey),beanFieldChannels.get(0).getValue());
						addInfoMessage("messageErrControleur", "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
						logger.debug("Code envoyé via le canal : "+beanFieldChannels.get(0).getValue());
						return "gotoPushCode";
					}
					else{
						logger.debug("aucun canal d'envoi n'est disponible");
						addInfoMessage("messageErrControleur", "IDENTIFICATION.MESSAGE.VALIDACCOUNT");
						addErrorMessage("messageErrControleur", "IDENTIFICATION.MESSAGE.NONECANAL");
					}
				
				}
				else if(activ){
					logger.info("Compte d�ja activ�");
					addErrorMessage("messageErrControleur", "IDENTIFICATION.ACTIVATION.MESSAGE.ALREADYACTIVATEDACCOUNT");
				}
			}
			
		}
		catch (AuthentificationException e) {
			logger.error(e.getMessage());	
			addErrorMessage("messageErrControleur", currentAccount.getStatus()!=null?"IDENTIFICATION.MESSAGE.INVALIDACCOUNT."+currentAccount.getStatus().toUpperCase():"IDENTIFICATION.MESSAGE.INVALIDACCOUNT");}
		catch(Exception  e){exceptions (e,"messageErrControleur");}
		
		return null;
	}
	
	public Channel getSentChannel(){		
		for(Channel c:channels)
			if(c.getName().equals(oneChoiceCanal))
				return c;
		return null;
	}
	
	private void setMailSendingValues(final BeanField beanPersoInfo,HashMap<String,List<String>> oldValue,HashMap<String,List<String>> newValue){
		
		if(isChange(beanPersoInfo)){
			Converter converter=beanPersoInfo.getConverter();			
			List<String> oldValueList=currentAccount.getAttributes(beanPersoInfo.getName());
			List<String> newValueList=getPersoInfoValues(beanPersoInfo);
			if(converter!=null)
			{
				oldValueList=new ArrayList<String>();
				for(String s:currentAccount.getAttributes(beanPersoInfo.getName()))
					oldValueList.add(converter.getAsString(null,null,s));
					
				newValueList=new ArrayList<String>();	
				for(String s:getPersoInfoValues(beanPersoInfo))
					newValueList.add(converter.getAsString(null,null,s));
			}
			oldValue.put(this.getI18nService().getString(beanPersoInfo.getKey()),oldValueList);
			newValue.put(this.getI18nService().getString(beanPersoInfo.getKey()),newValueList);
			attrNewCsv.put(beanPersoInfo, newValueList);
			attrOldCsv.put(beanPersoInfo, oldValueList);
		}						
	}
	
	/**
	 * 
	 * @param beanPersoInfo
	 * @return renvoi la liste des valeurs non vide et non null d'un BeanField
	 */
	private List<String> getPersoInfoValues(BeanField beanPersoInfo){
		List<BeanMultiValue> beanPersoInfoValues=beanPersoInfo.getValues();
		List<String> persoInfoValues=new ArrayList<String>();
		for(BeanMultiValue bmv:beanPersoInfoValues)
			if(bmv.getValue()!=null&&!bmv.getValue().isEmpty())				
				persoInfoValues.add(bmv.getValue());
		return persoInfoValues;
	}
	
	/**
	 * 
	 * @param beanPersoInfo
	 * @return indique si ce champ a été modifié ou non
	 */
	private boolean isChange(BeanField beanPersoInfo){							
		List<String> newValues=getPersoInfoValues(beanPersoInfo);
		List<String> currentValues=new ArrayList<String>();
		
		//on ne compare pas avec les champs vide ou null
		for(String s:currentAccount.getAttributes(beanPersoInfo.getName()))
		  if(!s.isEmpty() && s!=null)
			  currentValues.add(s);

		return !(newValues.containsAll(currentValues) &&
				currentValues.containsAll(newValues));				
	}
	
	// Ne pas utiliser des attributs d'objet
	// mais plut�t des param�tres car AccountController est un singleton, il est cr�er une seule fois au d�but, de ce fait les attributs ne sont pas correctement initialis�s contrairement aux param�tres).
	public String pushChangeInfoPersonal() {
		return _pushChangeInfoPerso(true);
	}
	
	public String pushChangeInfoPerso() {
		return _pushChangeInfoPerso(false);
	}

	private String _pushChangeInfoPerso(boolean fromAccountPersonalInfo) {
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
				
				HashMap<String,List<String>> oldValue=new HashMap<String,List<String>>();
				HashMap<String,List<String>> newValue=new HashMap<String,List<String>>();
				
				HashMap<String,List<String>> oldValueNotUpdateableFiel=new HashMap<String,List<String>>();
				HashMap<String,List<String>> newValueNotUpdateableFiel=new HashMap<String,List<String>>();
				
				// parcourir les champs
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
						
					// Si le champ est modifi� et � valider par le gestionnaire, sauvegarder l'ancienne et la nouvelle valeur
					if(!beanPersoInfo.isUpdateable() )
						this.setMailSendingValues(beanPersoInfo, oldValue, newValue);
					//  Si le champ est modifi�
					else if(isChange(beanPersoInfo)){
						hashBeanPersoInfo.put(beanPersoInfo.getName(), valueBeanMulti);
					}	
					//Si le champ est modifi� et si un mail est � envoyer
					if (beanPersoInfo.isSendMail())
						this.setMailSendingValues(beanPersoInfo, oldValueNotUpdateableFiel, newValueNotUpdateableFiel);
					
											
					i++;
				}
				
				if(hashBeanPersoInfo.size()>0){
					logger.info("Informations personnelles envoy�es au BO pour mise � jour: "+hashBeanPersoInfo.toString());
					this.getDomainService().updatePersonalInformations(currentAccount.getAttribute(accountIdKey),currentAccount.getAttribute(accountCodeKey),hashBeanPersoInfo);				
					
					
					// Pas d'envoie de message apr�s la MAJ des donn�es de la page "accountPersonalInfo.jsp".
					if (! fromAccountPersonalInfo)				
						this.addInfoMessage(null, "PERSOINFO.MESSAGE.CHANGE.SUCCESSFULL");
				}
					else logger.debug("Pas de mise à jour envoyée BO");
				
				
				//Maj Account
				Set<String> keySet=hashBeanPersoInfo.keySet();
				for(String key:keySet)
					if(hashBeanPersoInfo.get(key)!=null)
						this.accountDescr.put(key, hashBeanPersoInfo.get(key));
				this.updateCurrentAccount();
				
				logger.debug("Informations Account mises à jour :"+accountDescr.toString());
				if(!newValue.isEmpty()){
					// Envoi de mail demandant au gestionnaire de valider le(s) champ(s) modifi�s
					for(Mailing m:mailing)
						if(m.isAllowed(currentAccount))
							m.sendMessage(currentAccount,oldValue,newValue);
				
					//Les données à valider seront stockées dans un fichier csv pour faire un tableau de bord
					generateCsvFile();
				}
				// Envoi de mail informant le gestionnaire des modifications des champs
				if(!newValueNotUpdateableFiel.isEmpty())
					for(Mailing m:mailingUpdateableField)
						if(m.isAllowed(currentAccount))
							m.sendMessage(currentAccount,oldValueNotUpdateableFiel,newValueNotUpdateableFiel);
				
				// Rediriger vers la page ad�quate
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
				else if(viewDataChange){
					viewDataChange=false;
					dataChange=true;
					return "gotoDataChange";
				}
				else 
					return "gotoPasswordChange";
					
			}
			catch(Exception  e){exceptions (e,null);}
			
			return null;
			
	}
	public static String join(Iterable<?> elements, String separator) {
		return StringUtils.join(elements.iterator(), separator);
	}
	
	/**
	 */
	public void generateCsvFile() {
		String sep=", ";
		String newvalueList = "";
		String separator=";";
		String header = "Date";
		
		Calendar c = new GregorianCalendar();
		Date date=c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy HH:mm");
		String sdate=sdf.format(date);
		String attrValue=sdate;
		
		
		//Récupérer la valeur des données passées en paramètre (Date du jour, displayName, uid, supannEmpId, employeeType (corps))
		List<String> attrCsvFile=Arrays.asList(attributesCsvFile.split(","));
		for (int j=0;j<attrCsvFile.size();j++){	
			attrValue= attrValue+separator+join(currentAccount.getAttributes(attrCsvFile.get(j)),sep);	
			header=header+separator+attrCsvFile.get(j);
		}
		logger.debug("header:"+header);
		//Récupérer le nom des champs,les anciennes et les nouvelles valeurs)
		Set<BeanField> keys=attrNewCsv.keySet();
		for(BeanField<String> bf : keys)
		{
			String newAttrs=join(attrNewCsv.get(bf), sep);
			String oldAttrs=join(attrOldCsv.get(bf), sep);
			newvalueList=newvalueList+attrValue+separator+bf.getName()+separator+oldAttrs+separator+newAttrs+"\n";
			logger.debug("newvalueList:"+newvalueList);
		}
	
		try {
			BufferedReader lecteurAvecBuffer=null; 
			String ligne;
   		 	File file = new File(csvFileName);
   		 	if (!file.exists()) {
   		 		file.createNewFile();
   		 	}
   		 	//insérer entête, si fichier vide
   			lecteurAvecBuffer = new BufferedReader(new FileReader(csvFileName));      		 
	   		if ((ligne=lecteurAvecBuffer.readLine())==null) {
	   			header=header+";Champs modifié;Acienne valeur;Nouvelle valeur\n";
	   			newvalueList= header.toUpperCase()+newvalueList;
			}
	 	 	FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);			
			bw.write(newvalueList);
			bw.close();
			
			attrNewCsv.clear();
			attrOldCsv.clear();
			
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//TODO revoir cette procédure
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
			
			if (accountDescr!=null){
				
				logger.info("Authentification r�usssie");
				this.updateCurrentAccount();
								
				if (currentAccount.getAttribute(accountCodeKey)!=null) {
					logger.info("Construction de la liste des informations personnelles du compte");
					if (dataChange){
							this.buildProfilingListDataChangeInfos();
							this.buildListPersoInfo(this.getAllData(),attrDataChange);
					}
					else{
							this.buildProfilingListBeanPersoInfo();
							this.buildListPersoInfo(listBeanPersoInfo,attrPersoInfo);
					}
				
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
					this.addErrorMessage("messageErrControleur", "AUTHENTIFICATION.MESSAGE.ACCOUNT.NONACTIVATED");
				}
			
			}
			else {
				addErrorMessage("messageErrControleur", "AUTHENTIFICATION.MESSAGE.INVALID");
			}
		}catch(Exception  e){exceptions (e,"messageErrControleur");}
		
	
		return null;
	}
	
	public String pushLogin(){
		try {
			
			this.getDomainService().changeLogin(currentAccount.getAttribute(accountIdKey), currentAccount.getAttribute(accountCodeKey), beanNewLogin.getValue().toString());
			currentAccount.setId(beanNewLogin.getValue().toString());
			currentAccount.reset();
			logger.info("Changement de login r�ussi");
			this.addInfoMessage("messageErrControleur", "LOGIN.MESSAGE.CHANGE.SUCCESSFULL");
			return "gotoAccountEnabled";
			
		}
		catch (PrincipalNotExistsException e) {
			
			try{
				this.getDomainService().setPassword(currentAccount.getAttribute(accountIdKey),currentAccount.getAttribute(this.accountCodeKey),beanNewLogin.getValue().toString(),beanNewPassword.getValue().toString());
				currentAccount.setId(beanNewLogin.getValue().toString());
				logger.info("Changement de login r�ussi");
				this.addInfoMessage("messageErrControleur", "LOGIN.MESSAGE.CHANGE.SUCCESSFULL");
				currentAccount.reset();
				return "gotoAccountEnabled";
			
			}
			catch(Exception  e1){exceptions (e1,"messageErrControleur");}
		}
		catch(Exception  e){exceptions (e,"messageErrControleur");}

		return null;

	}
	
	public String pushChoice(){
		try{
			
			this.getDomainService().sendCode(currentAccount.getAttribute(accountIdKey),oneChoiceCanal);
			logger.debug("Code demandé par utilisateur pour le recevoir sur le canal : "+oneChoiceCanal);
			return "gotoPushCode";
			
					
		}catch(Exception  e){exceptions (e,null);}
		
		return null;
	}
	
	
	public String pushVerifyCode() {
		try{
			currentAccount.setAttribute(this.accountCodeKey, beanCode.getValue().toString());//.setCode(beanCode.getValue().toString());
			if (this.getDomainService().validateCode(currentAccount.getAttribute(accountIdKey), currentAccount.getAttribute(accountCodeKey))){
				logger.info("Code renseign� valide");
				this.addInfoMessage("messageErrControleur", "CODE.MESSAGE.CODESUCCESSFULL");
				beanCode.setValue("");
				return "gotoPersonalInfo";
			}
		}
		catch(Exception  e){exceptions (e,"messageErrControleur");}
		
		addErrorMessage("messageErrControleur", "VALIDATOR.CODE.INVALID");
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
			this.addInfoMessage("messageErrControleur", "PASSWORD.MESSAGE.CHANGE.SUCCESSFULL");
			currentAccount.reset();
			//beanNewPassword.setValue("");
			return "gotoAccountEnabled";

		}
		catch(Exception  e){exceptions (e,"messageErrControleur");}

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
		HashMap<String,List<String>> attributes=this.convertHash(accountDescr);
								
		if(attributes.get(accountIdKey)==null&&currentAccount.getAttribute(accountIdKey)!=null){
			ArrayList<String> accountIdValue=new ArrayList<String>();
			accountIdValue.add(currentAccount.getAttribute(accountIdKey));
			attributes.put(accountIdKey,accountIdValue);
		}
		
		if(attributes.get(accountCodeKey)==null&&currentAccount.getAttribute(accountCodeKey)!=null){
			ArrayList<String> accountCodeValue=new ArrayList<String>();			
			accountCodeValue.add(currentAccount.getAttribute(accountCodeKey));
			attributes.put(accountCodeKey,accountCodeValue);
		}
		
		currentAccount.setAttributes(attributes);
		
		currentAccount.setId(currentAccount.getAttribute(accountIdKey));
		currentAccount.setMail(currentAccount.getAttribute(accountMailKey));
		currentAccount.setEmailPerso(currentAccount.getAttribute(accountMailPersoKey));
		currentAccount.setPager(currentAccount.getAttribute(accountPagerKey));
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
	 * @return the categoryBeanDataChange
	 */
	public List<CategoryBeanField> getCategoryBeanDataChange() {
		return categoryBeanDataChange;
	}


	/**
	 * @param categoryBeanDataChange the categoryBeanDataChange to set
	 */
	public void setCategoryBeanDataChange(List<CategoryBeanField> categoryBeanDataChange) {
		
		this.categoryBeanDataChange = categoryBeanDataChange;
						
	}
	
	

	/**
	 * @return the categoryBeanViewDataChange
	 */
	public List<CategoryBeanField> getCategoryBeanViewDataChange() {
		return categoryBeanViewDataChange;
	}


	/**
	 * @param categoryBeanViewDataChange the categoryBeanViewDataChange to set
	 */
	public void setCategoryBeanViewDataChange(
			List<CategoryBeanField> categoryBeanViewDataChange) {
		this.categoryBeanViewDataChange = categoryBeanViewDataChange;
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
	
		
	public List<CategoryBeanField> getBeanData() {		
		if (viewDataChange) return categoryBeanViewDataChange;
		if(!reinit && !activ) pushAuthentificate();
		if(dataChange)return categoryBeanDataChange;
		else return categoryBeanDataChangeDigest;
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


	/**
	 * @return the categoryBeanDataChangeDigest
	 */
	public List<CategoryBeanField> getCategoryBeanDataChangeDigest() {
		return categoryBeanDataChangeDigest;
	}


	/**
	 * @param categoryBeanDataChangeDigest the categoryBeanDataChangeDigest to set
	 */
	public void setCategoryBeanDataChangeDigest(
			List<CategoryBeanField> categoryBeanDataChangeDigest) {
		this.categoryBeanDataChangeDigest = categoryBeanDataChangeDigest;
	}

	/**
	 * @return the mailing
	 */
	public List<Mailing> getMailing() {
		return mailing;
	}

	/**
	 * @param mailing the mailing to set
	 */
	public void setMailing(List<Mailing> mailing) {
		this.mailing = mailing;
	}

	public List<Mailing> getMailingUpdateableField() {
		return mailingUpdateableField;
	}

	public void setMailingUpdateableField(List<Mailing> mailingUpdateableField) {
		this.mailingUpdateableField = mailingUpdateableField;
	}

	public String getAttributesCsvFile() {
		return attributesCsvFile;
	}

	public void setAttributesCsvFile(String attributesCsvFile) {
		this.attributesCsvFile = attributesCsvFile;
	}

	public String getCsvFileName() {
		return csvFileName;
	}

	public void setCsvFileName(String csvFileName) {
		this.csvFileName = csvFileName;
	}

	
	
}
