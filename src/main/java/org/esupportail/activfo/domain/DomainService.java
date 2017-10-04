/**
 * ESUP-Portail esup-activ-fo - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ-fo
 */
package org.esupportail.activfo.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.List;

import org.esupportail.activfo.domain.beans.User;
import org.esupportail.activfo.exceptions.AuthentificationException;
import org.esupportail.activfo.exceptions.ChannelException;
import org.esupportail.activfo.exceptions.KerberosException;
import org.esupportail.activfo.exceptions.LdapProblemException;
import org.esupportail.activfo.exceptions.LoginAlreadyExistsException;
import org.esupportail.activfo.exceptions.LoginException;
import org.esupportail.activfo.exceptions.PrincipalNotExistsException;
import org.esupportail.activfo.exceptions.UserPermissionException;
import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.exceptions.UserNotFoundException;
import org.esupportail.commons.services.ldap.LdapEntityService;
import org.esupportail.commons.web.beans.Paginator;

/**
 * The domain service interface.
 */
public interface DomainService extends Serializable {

	public Map<String,String> validateAccount(Map<String,String> hashInfToValidate,List<String>attrPersoInfo) throws LdapProblemException,AuthentificationException,LoginException;
	
	public void setPassword(String id,String code,final String currentPassword)throws LdapProblemException,UserPermissionException,KerberosException,LoginException;
	
	public void setPassword(String id,String code,String newLogin,final String currentPassword)throws LdapProblemException,UserPermissionException,KerberosException,LoginException;
	
	public void updatePersonalInformations(String id,String code,Map<String,String> hashBeanPersoInfo)throws LdapProblemException,UserPermissionException,LoginException;
	
	public Map<String,String> authentificateUser(String id,String password,List<String>attrPersoInfo)throws AuthentificationException,LdapProblemException,UserPermissionException,LoginException;
	
	public void sendCode(String id,String canal)throws ChannelException;
		
	public boolean validateCode(String id,String code)throws UserPermissionException;
	
	public void changeLogin(String id, String code,String newLogin)throws LdapProblemException,UserPermissionException,KerberosException,LoginAlreadyExistsException,LoginException,PrincipalNotExistsException;
	
	public LdapEntityService getLdapEntityService();
	
	public void setLdapEntityService(LdapEntityService ldapEntityService);
	
	public Map<String,String> authentificateUserWithCas(String id,String proxyticket,String targetUrl,List<String>attrPersoInfo)throws AuthentificationException,LdapProblemException,UserPermissionException,LoginException;
	
	public Map<String,String> authentificateUserWithCodeKey(String id,String accountCodeKey,List<String>attrPersoInfo)throws AuthentificationException,LdapProblemException,UserPermissionException,LoginException;

}