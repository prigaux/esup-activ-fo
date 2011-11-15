/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.activfo.web.converters;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.commons.services.ldap.LdapEntity;
import org.esupportail.commons.services.ldap.LdapEntityService;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;


/**
 * A JSF converter to supannEtuEtape
 * Permet d'afficher le libellé d'un diplôme à partir de supannEtuEtape
 * Le libellé affiché est celui correspondant à la dernière année d'inscription de l'étudiant 
 */
public class EtuEtapeConverter implements Converter {
	
	private LdapEntityService ldapEntityService;
	private Account account;
	
	private String etablissement="{UAI:0751717J}";	
	private String branch="ou=diploma,o=Paris1,dc=univ-paris1,dc=fr";
	private String EtuAnneeInscription="supannEtuAnneeInscription";
	private String dn="entryDN";
	private String description="description";
	
	private final Logger logger = new LoggerImpl(getClass());
	
	public EtuEtapeConverter() {
    }
 
    public Object getAsObject(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final String value){
        return value;
    }
 
    public String getAsString(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final Object value) {

 
    	String etuEtape = value.toString().replace(etablissement, "");    	 	
    	
    	//on prend la dernère année d'inscription
    	List<String> etuAnneeInscriptions=account.getAttributes(EtuAnneeInscription);
    	String EtuAnneeInscription="";
    	if(etuAnneeInscriptions!=null &&etuAnneeInscriptions.size()>0)
    		EtuAnneeInscription=etuAnneeInscriptions.get(0);
    	for(String s:etuAnneeInscriptions)    		
    		if(s.compareTo(EtuAnneeInscription)>0) EtuAnneeInscription=s;
    	
    	String filter = "("+dn+"="+"ou="+etuEtape+",ou="+EtuAnneeInscription+","+branch+")";
    	
    	try {
    		List<LdapEntity> ldapentite = ldapEntityService.getLdapEntitiesFromFilter(filter);
    		return ldapentite!=null?ldapentite.get(0).getAttribute(description):value.toString();
    	} catch (Exception e) {
    		logger.debug("Le diplôme "+filter+" n'existe pas : "+e);
    	}    	
    	return value.toString();
    }

	/**
	 * @return the ldapEntityService
	 */
	public LdapEntityService getLdapEntityService() {
		return ldapEntityService;
	}

	/**
	 * @param ldapEntityService the ldapEntityService to set
	 */
	public void setLdapEntityService(LdapEntityService ldapEntityService) {
		this.ldapEntityService = ldapEntityService;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return the etuAnneeInscription
	 */
	public String getEtuAnneeInscription() {
		return EtuAnneeInscription;
	}

	/**
	 * @param etuAnneeInscription the etuAnneeInscription to set
	 */
	public void setEtuAnneeInscription(String etuAnneeInscription) {
		EtuAnneeInscription = etuAnneeInscription;
	}

	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @return the dn
	 */
	public String getDn() {
		return dn;
	}

	/**
	 * @param dn the dn to set
	 */
	public void setDn(String dn) {
		this.dn = dn;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}	
		
}