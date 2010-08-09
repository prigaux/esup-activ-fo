package org.esupportail.activfo.services.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.esupportail.activfo.exceptions.AuthentificationException;
import org.esupportail.activfo.exceptions.LoginAlreadyExistsException;
import org.esupportail.activfo.exceptions.KerberosException;
import org.esupportail.activfo.exceptions.LdapProblemException;
import org.esupportail.activfo.exceptions.UserPermissionException;



public interface AccountManagement {
	
	
	public HashMap<String,String> validateAccount(HashMap<String,String> hashInfToValidate,List<String>attrPersoInfo) throws LdapProblemException;

	public boolean setPassword(String id,String code,final String currentPassword)throws LdapProblemException,UserPermissionException,KerberosException;

	public boolean updatePersonalInformations(String id,String code,HashMap<String,String> hashBeanPersoInfo)throws LdapProblemException,UserPermissionException;
	
	public boolean validateCode(String id,String code);
	
	public boolean getCode(String id,String canal)throws LdapProblemException;
		
	public HashMap<String,String> authentificateUser(String id,String password,List<String>attrPersoInfo)throws AuthentificationException,LdapProblemException;

	public boolean changeLogin(String id,String code,String newLogin)throws LdapProblemException,UserPermissionException,KerberosException,LoginAlreadyExistsException;
	
	
	
}
