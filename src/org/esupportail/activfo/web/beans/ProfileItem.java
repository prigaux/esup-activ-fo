package org.esupportail.activfo.web.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.model.SelectItem;

import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

public class ProfileItem extends SelectItem {
	
	private final Logger logger = new LoggerImpl(getClass());
	private  Account account;
	private String attributeName;
	private String formatDateLdap;
	
	
	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public Account getAccount() {
		
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}


	public String getFormatDateLdap() {
		return formatDateLdap;
	}

	public void setFormatDateLdap(String formatDateLdap) {
		this.formatDateLdap = formatDateLdap;
	}

	public String getAccountValue() {
		String value=account.getAttribute(attributeName);
		if(value==null||value.equals("")) {
			logger.warn(account.getId()+"'s account contains no value for attribute "+attributeName);
			value="";
		}
		return value;		
	}
	

	
	public boolean isAllowed() {
		if (account!=null)
				return isMajor(getAccountValue());
		else return true;
		
	}
	
	
	/* Calculer l'âge de la mjorité
	 * En entrée : date de naissance
	 * En sortie : true si la personne est majeure sinon false
	 * 
	 */
	private  boolean isMajor(String dateOfBirth)  {
	    boolean major=false;
	    Calendar curr = Calendar.getInstance();
	    Calendar birth = Calendar.getInstance();
		try {
			Date formatDateOfBirth = new SimpleDateFormat(getFormatDateLdap()).parse(dateOfBirth);
			birth.setTime(formatDateOfBirth);
		   int yeardiff = curr.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
		    curr.add(Calendar.YEAR,-yeardiff);
		    if(birth.after(curr))
		    {
		      yeardiff = yeardiff - 1;
		    }
		    
		    if (yeardiff>=18){
		      major=true;
		    }
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	    return major;
  }
	


}
