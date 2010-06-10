/**
 * ESUP-Portail esup-activ-fo - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ-fo
 */
package org.esupportail.activfo.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.esupportail.activfo.dao.DaoService;
import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.activfo.domain.beans.User;
import org.esupportail.activfo.domain.beans.VersionManager;
import org.esupportail.activfo.domain.tools.StringTools;
import org.esupportail.activfo.services.ldap.InvalidLdapAccountException;
import org.esupportail.activfo.services.ldap.LdapSchema;
import org.esupportail.activfo.services.ldap.NotUniqueLdapAccountException;
import org.esupportail.activfo.services.remote.Information;
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

	private Information service;
	
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
	
	
	public boolean validateAccount(Account account) throws LdapException {

		try {
			System.out.println(account.getHarpegeNumber());
			System.out.println("account1");
			if (this.ldapUserService.getClass()==null){
				System.out.println("kkkkkkkkkkk");
			}
			List<LdapUser> ldapUserList = this.ldapUserService
					.getLdapUsersFromFilter("(supannEmpId="
							+ account.getHarpegeNumber() + ")");
			System.out.println("account2");
			if (ldapUserList.size() > 1) {
				throw new NotUniqueLdapAccountException(
						"LDAP account with supannEmpId "
								+ account.getHarpegeNumber() + " not unique");
			} else if (ldapUserList.size() == 0) {
				return false;
			}

			LdapUser ldapUser = ldapUserList.get(0);

			if (logger.isDebugEnabled()) {
				logger.debug("Validating account for : " + ldapUser);
				logger.debug("Birthdate checking");
			}

			/*
			 * Birthdate checking
			 */

			String ldapUserBirthdateStr = ldapUser.getAttribute(LdapSchema
					.getBirthdate());

			if (ldapUserBirthdateStr == null) {
				String errmsg = "LDAP account with supannEmpId "
						+ account.getHarpegeNumber() + " has no birthdate";
				logger.error(errmsg);
				throw new InvalidLdapAccountException(errmsg);
			}

			Date ldapUserBirthdate;

			try {
				SimpleDateFormat formatter = new SimpleDateFormat(LdapSchema
						.getBirthdateFormat());

				/*
				 * TimeZone must be the same in user interface and LDAP, UTC is
				 * chosen
				 */
				TimeZone tz = TimeZone.getTimeZone("UTC");
				formatter.setTimeZone(tz);

				ldapUserBirthdate = formatter.parse(ldapUserBirthdateStr);
			} catch (ParseException e) {
				throw new InvalidLdapAccountException(e.getMessage());
			}

			if (account.getBirthDate().compareTo(ldapUserBirthdate) != 0) {
				logger.info("Invalid birthdate (" + ldapUserBirthdate
						+ ") for LDAP account with supannEmpId "
						+ account.getHarpegeNumber());
				return false;
			}

			/*
			 * Patronymic name checking
			 */
			logger.debug("Patronymic name checking");

			List<String> ldapUserBirthnameList = ldapUser
					.getAttributes(LdapSchema.getBirthName());
			if (ldapUserBirthnameList == null
					|| ldapUserBirthnameList.size() == 0) {
				throw new InvalidLdapAccountException(
						"LDAP account with supannEmpId "
								+ account.getHarpegeNumber()
								+ " has no patronymic name");
			}

			Iterator<String> i = ldapUserBirthnameList.iterator();
			String sn;
			boolean isInLdap = false;
			while (i.hasNext()) {
				sn = i.next();
				if (StringTools.compareInsensitive(account.getBirthName(), sn)) {
					isInLdap = true;
					break;
				}
			}

			if (!isInLdap) {
				logger.info("Invalid patronymic ("
						+ ldapUserBirthnameList.toArray()
						+ ") for LDAP account with supannEmpId "
						+ account.getHarpegeNumber());
				return false;
			}

			account.setId(ldapUser.getId());
			account.setDisplayName(ldapUser.getAttribute(LdapSchema
					.getDisplayName()));

			if (logger.isDebugEnabled()) {
				logger.debug("Setting account mail : "
						+ ldapUser.getAttribute(LdapSchema.getMail()));
			}
			account.setMail(ldapUser.getAttribute(LdapSchema.getMail()));
			
			if (logger.isDebugEnabled()) {
				logger.debug("Setting account shadowLastChange : "
						+ ldapUser.getAttribute(LdapSchema.getShadowLastChange()));
			}
			account.setShadowLastChange(ldapUser.getAttribute(LdapSchema
					.getShadowLastChange()));


			account.generateInitialPassword();

			/* for security reasons */
			account.setBirthName(null);
			account.setBirthDate(null);
			account.setHarpegeNumber(null);

			return true;

		} catch (LdapException e) {
			logger.debug("Exception thrown by validateAccount() : "
					+ e.getMessage());
			throw e;
		}
	}
	
	public void recupChaine(){
		System.out.println("===============");
		System.out.println(service.obtenirChaine());
	}

	public Information getService() {
		return service;
	}

	public void setService(Information service) {
		this.service = service;
	}

	

}
