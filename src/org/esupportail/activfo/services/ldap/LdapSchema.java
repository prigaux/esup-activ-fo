/*
 * Created on Apr 6, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.esupportail.activfo.services.ldap;


import org.springframework.beans.factory.InitializingBean;



public class LdapSchema implements InitializingBean{
	
	private String uid;
	
	private String cn;
	
	private String description;
	
	private String businessCategory;
	
	private String labeledURI;
	
	private String ou;
	
	private String supannCodeEntite;
	
	private String displayName;
	
	
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
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

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the cn
	 */
	public String getCn() {
		return cn;
	}

	/**
	 * @param cn the cn to set
	 */
	public void setCn(String cn) {
		this.cn = cn;
	}

	/**
	 * @return the businessCategory
	 */
	public String getBusinessCategory() {
		return businessCategory;
	}

	/**
	 * @param businessCategory the businessCategory to set
	 */
	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	/**
	 * @return the labeledURI
	 */
	public String getLabeledURI() {
		return labeledURI;
	}

	/**
	 * @param labeledURI the labeledURI to set
	 */
	public void setLabeledURI(String labeledURI) {
		this.labeledURI = labeledURI;
	}

	/**
	 * @return the ou
	 */
	public String getOu() {
		return ou;
	}

	/**
	 * @param ou the ou to set
	 */
	public void setOu(String ou) {
		this.ou = ou;
	}

	/**
	 * @return the supannCodeEntite
	 */
	public String getSupannCodeEntite() {
		return supannCodeEntite;
	}

	/**
	 * @param supannCodeEntite the supannCodeEntite to set
	 */
	public void setSupannCodeEntite(String supannCodeEntite) {
		this.supannCodeEntite = supannCodeEntite;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	

}