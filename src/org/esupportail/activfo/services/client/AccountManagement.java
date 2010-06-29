package org.esupportail.activfo.services.client;

import java.util.Date;
import java.util.HashMap;

import org.esupportail.commons.services.ldap.LdapException;



public interface AccountManagement {
	
	
	public HashMap<String,String> validAccount(String number,String birthName,Date birthDate) throws LdapException;

	public boolean updateLdap(final String currentPassword,String id,String code)throws LdapException;

	public void updateDisplayName(String displayName,String id, String code);
	
	public void setMailPerso(String id,String mailPerso);
	
	public int validateCode(String id,String code);
}
