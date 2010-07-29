/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.activfo.web.converters;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.util.StringUtils;

/**
 * A JSF converter to pass Integer instances.
 */
public class LdapDateConverter implements Converter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String formatStandardDate;
	private String formatLdapDate;

	/**
	 * Bean constructor.
	 */
	public LdapDateConverter() {
		super();
	}

	/**
	 * @see javax.faces.convert.Converter#getAsObject(
	 * javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	
	//affichage ldap
	public Object getAsObject(
			@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final String value) {

		if (!StringUtils.hasText(value)) {
			return null;
		}
		
		//String val=(String)value;
		Date date=null;
		try {
			date = stringToDate(value,formatStandardDate);
		} catch (ParseException e) {
			return value;
		}
		return  dateToString(date,formatLdapDate);
	}

	
	//Affichage standard
	/**
	 * @see javax.faces.convert.Converter#getAsString(
	 * javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(
			@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final Object value) {
				
		if (value == null || !StringUtils.hasText(value.toString())) {
			return "";
		}
		if (!(value instanceof String)) {
			throw new UnsupportedOperationException(
					"object " + value + " is not a String.");
		}
		
		String val=(String)value;
		Date date=null;
		try {
			date = stringToDate(val,formatLdapDate);
		} catch (ParseException e) {
			return "";
		}
		return dateToString(date,formatStandardDate);
	}
	
	private String dateToString(Date sDate,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(sDate);
    }
    
	private Date stringToDate(String sDate,String format) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(sDate);
    }

	public String getFormatStandardDate() {
		return formatStandardDate;
	}

	public void setFormatStandardDate(String formatStandardDate) {
		this.formatStandardDate = formatStandardDate;
	}

	public String getFormatLdapDate() {
		return formatLdapDate;
	}

	public void setFormatLdapDate(String formatLdapDate) {
		this.formatLdapDate = formatLdapDate;
	}

}
