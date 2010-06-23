/**
 * ESUP-Portail LDAP Account Activation - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ
 */
package org.esupportail.activfo.services.remote;



import java.util.Date;



/**
 * The class that represent net account.
 */
public interface AccountInt {
	
	
	public String getId();

	public void setId(final String id);

	public String getHarpegeNumber();

	public void setHarpegeNumber(String harpegeNumber);

	public String getBirthName();

	public void setBirthName(String birthName);

	public Date getBirthDate();

	public void setBirthDate(Date birthDate);
	
	public String getPassword();

	public void setPassword(String password);

	public String getDisplayName();

	public void setDisplayName(String displayName);
	
	public void encryptPassword();	

	public void generateInitialPassword();
	
	public String getInitialPassword();
	
	public void setInitialPassword(String initialPassword);

	public boolean changeDisplayName(String newDisplayName);
	
	public String getShadowLastChange();

	public void setShadowLastChange(String shadowLastChange);
	
	public boolean isActivated();

	public boolean isCharterAgreement();

	public void setCharterAgreement(boolean charterAgreement);

	public String getMail();

	public void setMail(String mail);

	

}
