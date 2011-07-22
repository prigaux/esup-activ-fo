package org.esupportail.activfo.services.client;

import java.util.HashMap;
import java.util.List;

import org.esupportail.activfo.exceptions.AuthentificationException;
import org.esupportail.activfo.exceptions.LdapProblemException;
import org.esupportail.activfo.exceptions.LoginException;
import org.esupportail.activfo.exceptions.UserPermissionException;


public interface CasAccountManagement {
	
	public HashMap<String,String> authentificateUserWithCas(String id,String proxyticket,String targetUrl,List<String>attrPersoInfo)throws AuthentificationException,LdapProblemException,UserPermissionException, LoginException;
	
	public HashMap<String,String> authentificateUserWithCodeKey(String id,String accountCodeKey,List<String>attrPersoInfo)throws AuthentificationException,LdapProblemException,UserPermissionException, LoginException;	
	

}
