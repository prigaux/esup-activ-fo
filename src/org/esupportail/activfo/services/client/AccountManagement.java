package org.esupportail.activfo.services.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.esupportail.activfo.exceptions.KerberosException;
import org.esupportail.activfo.exceptions.LdapProblemException;
import org.esupportail.activfo.exceptions.UserPermissionException;
import org.esupportail.commons.services.ldap.LdapException;



public interface AccountManagement {
	
	
	public HashMap<String,String> validateAccount(String number,String birthName,Date birthDate,List<String>attrPersoInfo) throws LdapProblemException;

	public boolean setPassword(String id,String code,final String currentPassword)throws LdapProblemException,UserPermissionException,KerberosException;

	public boolean updateInfoPerso(String id,String code,HashMap<String,String> infoPerso)throws LdapProblemException,UserPermissionException;
	
	//public void updateDisplayName(String displayName,String id, String code);
	
	//public void setMailPerso(String id,String mailPerso);
	
	//public int validateCode(String id,String code);
}
