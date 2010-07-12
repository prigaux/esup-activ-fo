/**
 * ESUP-Portail esup-activ-fo - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ-fo
 */
package org.esupportail.activfo.domain;


import java.util.Date;
import java.util.HashMap;

import java.util.List;


import org.esupportail.activfo.dao.DaoService;
import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.activfo.domain.beans.User;
import org.esupportail.activfo.domain.beans.VersionManager;
import org.esupportail.activfo.exceptions.KerberosException;
import org.esupportail.activfo.exceptions.LdapProblemException;
import org.esupportail.activfo.exceptions.UserPermissionException;
import org.esupportail.activfo.services.client.AccountManagement;
import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.exceptions.UserNotFoundException;
import org.esupportail.commons.services.application.Version;
import org.esupportail.commons.services.ldap.LdapException;
import org.esupportail.commons.services.ldap.LdapUser;
import org.esupportail.commons.services.ldap.LdapUserService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.web.beans.Paginator;
import org.springframework.beans.factory.InitializingBean;

/**
 * The basic implementation of DomainService.
 * 
 * See /properties/domain/domain-example.xml
 */
public class DomainServiceImpl implements DomainService, InitializingBean {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -8200845058340254019L;

	private AccountManagement service;
	
	
	/**
	 * {@link DaoService}.
	 */
	private DaoService daoService;

	/**
	 * {@link LdapUserService}.

	 */
	private LdapUserService ldapUserService;
	
	
	


	/**
	 * The LDAP attribute that contains the display name. 
	 */
	private String displayNameLdapAttribute;
	
		
	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());

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
		Assert.notNull(this.daoService, 
				"property daoService of class " + this.getClass().getName() + " can not be null");
		Assert.notNull(this.ldapUserService, 
				"property ldapUserService of class " + this.getClass().getName() + " can not be null");
		Assert.hasText(this.displayNameLdapAttribute, 
				"property displayNameLdapAttribute of class " + this.getClass().getName() 
				+ " can not be null");
	}
	
	
	
	
	
	
	

	//////////////////////////////////////////////////////////////
	// User
	//////////////////////////////////////////////////////////////

	/**
	 * Set the information of a user from a ldapUser.
	 * @param user 
	 * @param ldapUser 
	 * @return true if the user was updated.
	 */
	private boolean setUserInfo(
			final User user, 
			final LdapUser ldapUser) {
		String displayName = null;
		List<String> displayNameLdapAttributes = ldapUser.getAttributes().get(displayNameLdapAttribute);
		if (displayNameLdapAttributes != null) {
			displayName = displayNameLdapAttributes.get(0);
		}
		if (displayName == null) {
			displayName = user.getId();
		}
		if (displayName.equals(user.getDisplayName())) {
			return false;
		}
		user.setDisplayName(displayName);
		return true;
	}

	/**
	 * @see org.esupportail.activfo.domain.DomainService#updateUserInfo(org.esupportail.activfo.domain.beans.User)
	 */
	public void updateUserInfo(final User user) {
		if (setUserInfo(user, ldapUserService.getLdapUser(user.getId()))) {
			updateUser(user);
		}
	}

	/**
	 * If the user is not found in the database, try to create it from a LDAP search.
	 * @see org.esupportail.activfo.domain.DomainService#getUser(java.lang.String)
	 */
	public User getUser(final String id) throws UserNotFoundException {
		User user = daoService.getUser(id);
		if (user == null) {
			LdapUser ldapUser = this.ldapUserService.getLdapUser(id);
			user = new User();
			user.setId(ldapUser.getId());
			setUserInfo(user, ldapUser);
			daoService.addUser(user);
			logger.info("user '" + user.getId() + "' has been added to the database");
		}
		return user;
	}

	/**
	 * @see org.esupportail.activfo.domain.DomainService#getUsers()
	 */
	public List<User> getUsers() {
		return this.daoService.getUsers();
	}

	/**
	 * @see org.esupportail.activfo.domain.DomainService#updateUser(org.esupportail.activfo.domain.beans.User)
	 */
	public void updateUser(final User user) {
		this.daoService.updateUser(user);
	}

	/**
	 * @see org.esupportail.activfo.domain.DomainService#addAdmin(org.esupportail.activfo.domain.beans.User)
	 */
	public void addAdmin(
			final User user) {
		user.setAdmin(true);
		updateUser(user);
	}

	/**
	 * @see org.esupportail.activfo.domain.DomainService#deleteAdmin(org.esupportail.activfo.domain.beans.User)
	 */
	public void deleteAdmin(
			final User user) {
		user.setAdmin(false);
		updateUser(user);
	}
	
	/**
	 * @see org.esupportail.activfo.domain.DomainService#getAdminPaginator()
	 */
	public Paginator<User> getAdminPaginator() {
		return this.daoService.getAdminPaginator();
	}

	/**
	 * @param displayNameLdapAttribute the displayNameLdapAttribute to set
	 */
	public void setDisplayNameLdapAttribute(final String displayNameLdapAttribute) {
		this.displayNameLdapAttribute = displayNameLdapAttribute;
	}

	//////////////////////////////////////////////////////////////
	// VersionManager
	//////////////////////////////////////////////////////////////

	/**
	 * @see org.esupportail.activfo.domain.DomainService#getDatabaseVersion()
	 */
	public Version getDatabaseVersion() throws ConfigException {
		VersionManager versionManager = daoService.getVersionManager();
		if (versionManager == null) {
			return null;
		}
		return new Version(versionManager.getVersion());
	}

	/**
	 * @see org.esupportail.activfo.domain.DomainService#setDatabaseVersion(java.lang.String)
	 */
	public void setDatabaseVersion(final String version) {
		if (logger.isDebugEnabled()) {
			logger.debug("setting database version to '" + version + "'...");
		}
		VersionManager versionManager = daoService.getVersionManager();
		versionManager.setVersion(version);
		daoService.updateVersionManager(versionManager);
		if (logger.isDebugEnabled()) {
			logger.debug("database version set to '" + version + "'.");
		}
	}

	/**
	 * @see org.esupportail.activfo.domain.DomainService#setDatabaseVersion(
	 * org.esupportail.commons.services.application.Version)
	 */
	public void setDatabaseVersion(final Version version) {
		setDatabaseVersion(version.toString());
	}

	//////////////////////////////////////////////////////////////
	// Authorizations
	//////////////////////////////////////////////////////////////

	/**
	 * @see org.esupportail.activfo.domain.DomainService#userCanViewAdmins(org.esupportail.activfo.domain.beans.User)
	 */
	public boolean userCanViewAdmins(final User user) {
		if (user == null) {
			return false;
		}
		return user.getAdmin();
	}

	/**
	 * @see org.esupportail.activfo.domain.DomainService#userCanAddAdmin(org.esupportail.activfo.domain.beans.User)
	 */
	public boolean userCanAddAdmin(final User user) {
		if (user == null) {
			return false;
		}
		return user.getAdmin();
	}

	/**
	 * @see org.esupportail.activfo.domain.DomainService#userCanDeleteAdmin(
	 * org.esupportail.activfo.domain.beans.User, org.esupportail.activfo.domain.beans.User)
	 */
	public boolean userCanDeleteAdmin(final User user, final User admin) {
		if (user == null) {
			return false;
		}
		if (!user.getAdmin()) {
			return false;
		}
		return !user.equals(admin);
	}

	//////////////////////////////////////////////////////////////
	// Misc
	//////////////////////////////////////////////////////////////

	/**
	 * @param daoService the daoService to set
	 */
	public void setDaoService(final DaoService daoService) {
		this.daoService = daoService;
	}

	/**
	 * @param ldapUserService the ldapUserService to set
	 */
	public void setLdapUserService(final LdapUserService ldapUserService) {
		this.ldapUserService = ldapUserService;
	}
	
	
	public HashMap<String,String> validateAccount(String number,String birthName,Date birthDate,List<String>attrPersoInfo) throws LdapProblemException{
		return service.validateAccount(number,birthName,birthDate,attrPersoInfo);
		
				
	}
	
	
	public boolean setPassword(String id,String code,final String currentPassword)throws LdapProblemException,UserPermissionException,KerberosException {

		return service.setPassword(id,code,currentPassword);	
	}
	
	public boolean updateInfoPerso(String id,String code, HashMap<String,String> infoPerso)throws LdapProblemException,UserPermissionException{
		return service.updateInfoPerso(id,code,infoPerso);
	}
	
	public AccountManagement getService() {
		return service;
	}
	
	public void setService(AccountManagement service) {
		this.service = service;
	}

	
	/*public void updateDisplayName(String displayName,String id, String code){
		service.updateDisplayName(displayName,id,code);
	}
	
	public void setMailPerso(String id,String mailPerso){
		service.setMailPerso(id,mailPerso);
	}*/
	
	/*public int validateCode(String id,String code){
		return service.validateCode(id, code);
	}*/
	
	

	

}
