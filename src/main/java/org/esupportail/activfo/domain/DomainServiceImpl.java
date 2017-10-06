/**
 * ESUP-Portail esup-activ-fo - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ-fo
 */
package org.esupportail.activfo.domain;

import java.util.Map;
import java.util.List;

import org.esupportail.activfo.exceptions.AuthentificationException;
import org.esupportail.activfo.exceptions.ChannelException;
import org.esupportail.activfo.exceptions.KerberosException;
import org.esupportail.activfo.exceptions.LdapProblemException;
import org.esupportail.activfo.exceptions.LoginAlreadyExistsException;
import org.esupportail.activfo.exceptions.LoginException;
import org.esupportail.activfo.exceptions.PrincipalNotExistsException;
import org.esupportail.activfo.exceptions.UserPermissionException;
import org.esupportail.activfo.services.client.AccountManagement;
import org.esupportail.activfo.services.client.CasAccountManagement;
import org.esupportail.commons.services.ldap.LdapEntityService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.springframework.beans.factory.InitializingBean;

/**
 * The basic implementation of DomainService.
 * 
 * See /properties/domain/domain-example.xml 
 * @param <LdapSchema>
 */
public class DomainServiceImpl implements DomainService, InitializingBean {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -8200845058340254019L;

	private AccountManagement service;
	
	private CasAccountManagement casAcountService;
	

	/**
	 * {@link LdapUserService}.

	 */

	/**
	 * The LDAP attribute that contains the display name. 
	 */
	private String displayNameLdapAttribute;
	
		
	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());
	
	private LdapEntityService ldapEntityService;

	/**
	 * Bean constructor.
	 */
	public DomainServiceImpl() {
		super();
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.hasText(this.displayNameLdapAttribute, 
				"property displayNameLdapAttribute of class " + this.getClass().getName() 
				+ " can not be null");
	}

	/**
	 * @param displayNameLdapAttribute the displayNameLdapAttribute to set
	 */
	public void setDisplayNameLdapAttribute(final String displayNameLdapAttribute) {
		this.displayNameLdapAttribute = displayNameLdapAttribute;
	}

	//////////////////////////////////////////////////////////////
	// Misc
	//////////////////////////////////////////////////////////////
	
	
	public Map<String,String> validateAccount(Map<String,String> hashInfToValidate,List<String>attrPersoInfo) throws LdapProblemException,AuthentificationException,LoginException{
		return service.validateAccount(hashInfToValidate,attrPersoInfo);
	}
	
	
	public void setPassword(String id,String code,final String currentPassword)throws LdapProblemException,UserPermissionException,KerberosException,LoginException {
		service.setPassword(id,code,currentPassword);	
	}
	
	public void setPassword(String id,String code,String newLogin,final String currentPassword)throws LdapProblemException,UserPermissionException,KerberosException,LoginException{
		service.setPassword(id,code,newLogin,currentPassword);
	}
	
	public void updatePersonalInformations(String id,String code, Map<String,String> hashBeanPersoInfo)throws LdapProblemException,UserPermissionException,LoginException{
		service.updatePersonalInformations(id,code,hashBeanPersoInfo);
	}
	
	public void changeLogin(String id, String code,String newLogin)throws LdapProblemException,UserPermissionException,KerberosException,LoginAlreadyExistsException,LoginException,PrincipalNotExistsException{
		service.changeLogin(id, code, newLogin);
	}
	
	
	public void sendCode(String id,String canal)throws ChannelException{
		service.sendCode(id, canal);
	}
	
	public AccountManagement getService() {
		return service;
	}
	
	public void setService(AccountManagement service) {
		this.service = service;
	}
	
	public CasAccountManagement getCasAcountService() {
		return casAcountService;
	}

	public void setCasAcountService(CasAccountManagement casAcountService) {
		this.casAcountService = casAcountService;
	}

	
	public boolean validateCode(String id,String code)throws UserPermissionException{
		return service.validateCode(id, code);
	}
	
	public Map<String,String> authentificateUser(String id,String password,List<String>attrPersoInfo)throws AuthentificationException,LdapProblemException,UserPermissionException,LoginException{
		return service.authentificateUser(id, password,attrPersoInfo);
	}

	/**
	 * @return the ldapEntityService
	 */
	public LdapEntityService getLdapEntityService() {
		return ldapEntityService;
	}

	/**
	 * @param ldapEntityService the ldapEntityService to set
	 */
	public void setLdapEntityService(LdapEntityService ldapEntityService) {
		this.ldapEntityService = ldapEntityService;
	}
	
	public Map<String,String> authentificateUserWithCas(String id,String proxyticket,String targetUrl,List<String>attrPersoInfo)throws AuthentificationException,LdapProblemException,UserPermissionException,LoginException{
		return casAcountService.authentificateUserWithCas(id, proxyticket,targetUrl,attrPersoInfo);
	}
	
	public Map<String,String> authentificateUserWithCodeKey(String id,String accountCodeKey,List<String>attrPersoInfo)throws AuthentificationException,LdapProblemException,UserPermissionException,LoginException{
		return casAcountService.authentificateUserWithCodeKey(id, accountCodeKey,attrPersoInfo);
	}
	
	
}