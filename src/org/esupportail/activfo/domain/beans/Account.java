/**
 * ESUP-Portail LDAP Account Activation - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ
 */
package org.esupportail.activfo.domain.beans;


import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

//import org.acegisecurity.providers.ldap.authenticator.LdapShaPasswordEncoder;
import org.esupportail.activfo.domain.DomainService;
import org.esupportail.activfo.domain.tools.StringTools;
//import org.esupportail.activfo.domain.tools.StringTools;
//import org.esupportail.activfo.services.ldap.LdapSchema;
import org.esupportail.commons.beans.AbstractI18nAwareBean;
import org.esupportail.commons.services.ldap.LdapEntity;
import org.esupportail.commons.services.ldap.LdapUser;
import org.esupportail.commons.services.ldap.LdapUserService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.strings.StringUtils;
import org.acegisecurity.providers.ldap.authenticator.LdapShaPasswordEncoder;


/**
 * The class that represent net account.
 */
public class Account extends AbstractI18nAwareBean {
	
	/**
	 * Id of the account.
	 */
	private String id;
	
    /**
	 * Harpege number of this account
	 */
    private String harpegeNumber;
    
    /**
	 * Display Name of the user.
	 */
    
    private String displayName;
    
	private String birthName;
	
	private String shadowLastChange;
	
	private Date birthDate;
	
	private String password;
	
	private String mail;
	
	private String initialPassword;
	
	private boolean charterAgreement;

	/**
	 * Bean constructor.
	 */
	public Account() {
		super();
		charterAgreement=false;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Account)) {
			return false;
		}
		return id.equals(((Account) obj).getId());
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User#" + hashCode() + "[id=[" + id + "], harpegeNumber=[" + harpegeNumber 
		+ "], birthName=[" + birthName + "],displayName=[" + displayName + "],   birthDate=[" + birthDate + "], shadowLastChange=[" + shadowLastChange + "]";
	}

	/**
	 * @return  the id of the user.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(final String id) {
		this.id = StringUtils.nullIfEmpty(id);
	}

	public String getHarpegeNumber() {
		return harpegeNumber;
	}

	public void setHarpegeNumber(String harpegeNumber) {
		this.harpegeNumber = harpegeNumber;
	}

	public String getBirthName() {
		return birthName;
	}

	public void setBirthName(String birthName) {
		this.birthName = birthName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDisplayName() {
	
		return displayName;
		
	}

	public void setDisplayName(String displayName) {
		
		
		this.displayName = displayName;
	}
	
	public void encryptPassword() {
		/*
		 * If we look at phpldapadmin SSHA encryption algorithm in :
		 * /usr/share/phpldapadmin/lib/functions.php function password_hash(
		 * $password_clear, $enc_type ) salt length for SSHA is 4
		 */
		final int SALT_LENGTH = 4;
		
		LdapShaPasswordEncoder ldapShaPasswordEncoder = new LdapShaPasswordEncoder();
		/* Salt generation */
		byte[] salt = new byte[SALT_LENGTH];
		Random generator = new Random();
		generator.nextBytes(salt);
		/* SSHA encoding */
		String encryptedPassword = ldapShaPasswordEncoder.encodePassword(this.getPassword(), salt);
		

		this.setPassword(encryptedPassword);
	}
	
	public void generateInitialPassword() {
		/* TODO : input your password generation algorithm here */
		initialPassword = "initialseed#";
		SimpleDateFormat format =
            new SimpleDateFormat("ddMMyyyy");
		initialPassword += format.format(this.birthDate)+"#";
		initialPassword += this.harpegeNumber+"#";
		
		initialPassword += StringTools.cleanAllSpecialChar(this.birthName)+"#";
		
	}
	
	public String getInitialPassword() {
		return initialPassword;
	}

	public void setInitialPassword(String initialPassword) {
		this.initialPassword = initialPassword;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esupportail.activ.domain.DomainService#changeDisplayName(org.esupportail.activ.domain.beans.Account,
	 *      java.lang.String)
	 */
	public boolean changeDisplayName(String newDisplayName) {

		// Compare newDisplayName and displayName

		if (StringTools.compareInsensitive(this.getDisplayName(), newDisplayName)) {
			this.setDisplayName(newDisplayName);
			return true;
		}

		return false;
	}

	public String getShadowLastChange() {
		return shadowLastChange;
	}

	public void setShadowLastChange(String shadowLastChange) {
		this.shadowLastChange = shadowLastChange;
	}
	
	public boolean isActivated() {
		return (this.shadowLastChange!=null && this.shadowLastChange.length()!=0);
	}

	public boolean isCharterAgreement() {
		return charterAgreement;
	}

	public void setCharterAgreement(boolean charterAgreement) {
		this.charterAgreement = charterAgreement;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	

}
