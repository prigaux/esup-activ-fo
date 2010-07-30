package org.esupportail.activfo.services.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.esupportail.activfo.exceptions.KerberosException;
import org.esupportail.activfo.exceptions.LdapProblemException;
import org.esupportail.activfo.exceptions.OldPasswordException;
import org.esupportail.activfo.exceptions.UserPermissionException;
import org.esupportail.commons.services.ldap.LdapException;



public interface AccountManagement {
	
	
	public HashMap<String,String> validateAccount(HashMap<String,String> hashInfToValidate,List<String>attrPersoInfo) throws LdapProblemException;

	public boolean setPassword(String id,String code,final String currentPassword)throws LdapProblemException,UserPermissionException,KerberosException;

	public boolean updatePersonalInformations(String id,String code,HashMap<String,String> hashBeanPersoInfo)throws LdapProblemException,UserPermissionException;
	
	public boolean validateCode(String id,String code);
	
	public String getCode(String id,String canal);
	
	public String getCode(String id);
	
	public HashMap<String,String> setPassword(String id,String oldPassword,final String currentPassword,List<String>attrPersoInfo)throws LdapProblemException,UserPermissionException,KerberosException,OldPasswordException;
	
}
