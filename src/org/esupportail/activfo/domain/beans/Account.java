/**
 * ESUP-Portail LDAP Account Activation - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ
 */
package org.esupportail.activfo.domain.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.esupportail.commons.web.controllers.Resettable;
import org.springframework.beans.factory.InitializingBean;

/**
 * The class that represent net account.
 */
public class Account implements InitializingBean, Resettable {
	
	private static final long serialVersionUID = 5854730800181753413L;
	
	private String id;
    
    private String displayName;
    
	private boolean activated;
	
	private String mail;
	
	private boolean charterAgreement=false;
	
	private String smsAgreement;
		
	private String emailPerso;
	
	private String pager;
	
	private HashMap<String, List<String>> attributes = new HashMap<String,List<String>>();
	
	private String oneRadioValue;
	
	private String oneRadioProcedure;
	
	private String oneChoiceCanal;
	
	
	public String getOneChoiceCanal() {
		return oneChoiceCanal;
	}

	public void setOneChoiceCanal(String oneChoiceCanal) {
		this.oneChoiceCanal = oneChoiceCanal;
	}

	public String getOneRadioValue() {
		return oneRadioValue;
	}

	public void setOneRadioValue(String oneRadioValue) {
		this.oneRadioValue = oneRadioValue;
	}

	public Account() {
		super();
		
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public String getEmailPerso() {
		return emailPerso;
	}

	public void setEmailPerso(String emailPerso) {
		this.emailPerso = emailPerso;
	}

	public String getOneRadioProcedure() {
		return oneRadioProcedure;
	}

	public void setOneRadioProcedure(String oneRadioProcedure) {
		this.oneRadioProcedure = oneRadioProcedure;
	}

	
	public String getPager() {
		return pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	}

	public String getSmsAgreement() {
		return smsAgreement;
	}

	public void setSmsAgreement(String smsAgreement) {
		this.smsAgreement = smsAgreement;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	public String getAttribute(final String name) {
		List<String> values = getAttributes(name);
		if (values.size() < 1) {
			return null;
		}
		return values.get(0);
	}
	
	public List<String> getAttributes(final String name) {
		List<String> result = attributes.get(name);
		if (result == null) {
			result = new ArrayList<String>();
		}
		return result;
	}
	
	public void setAttributes(
			final HashMap<String, List<String>> attributes) {
		this.attributes = attributes;
	}
	
	public void setAttribute(String name,String value) {
		ArrayList<String>liste;
		liste=new ArrayList<String>();
		liste.add(value);
		attributes.put(name, liste);
		
	}

	/* (non-Javadoc)
	 * @see org.esupportail.commons.web.controllers.Resettable#reset()
	 */
	public void reset() {
		attributes=new HashMap<String,List<String>>();
		
	}
}