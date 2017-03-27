/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.activfo.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

/**
 * A JSF converter to convert empty string to string contains emptyValue
 * Format phone number
 */
public class EmptyAndFormatPhoneNumberConverter implements Converter {
	
	private String emptyValue="";
	private final Logger logger = new LoggerImpl(getClass());
	

    public Object getAsObject(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final String value){
    	if(context!=null && value!=null && value.isEmpty())
            return emptyValue;  
    	
    	if(context==null && value!=null && value.equals(emptyValue))
    		return "";
    	
        if (value!=null && value.replaceAll(" ", "").length()>0) {
            return ldapFormatPhoneNumber(toFrenchPhoneNumber((String) value));
        }
    	return value;
    }
 
    //Affichage du Numéro de tel avec format français,sans espace
    public String getAsString(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final Object value) {
    	
     if(value!=null){
       //replaceAll(emptyValue, "") remplacer DEFAULT_VALUE par "", voir converters.xml
       String val=((String) value).replaceAll(" ", "").replaceAll("^\\+33", "0").replaceAll(emptyValue, "");
       return val;
     }
     
     return null; 
    	
    }
    
   private String toFrenchPhoneNumber(String phoneNumber) {
      String s = phoneNumber.replaceAll(" ", "");
      if (s.matches("(^(((\\+|00)33)|0)[1-9]\\d{8}$)")){
        // remplacer +33 ou 0033 par 0
        s = s.replaceAll("^\\+33", "0").replaceAll("^0033", "0");
      }
      return s;
    }


    private String ldapFormatPhoneNumber(String phoneNumber) {
      String s = phoneNumber.replaceAll(" ", "");
      if (s.matches("^0[1-9]\\d{8}")) {
          // espacement par 2 chiffres replaceAll("\\d\\d", " $0"), remplacer premier 0 par +33
          s = s .replaceAll("\\d\\d", " $0").replaceAll("^ 0", "+33 ");
      }
      return s;
    }

	/**
	 * @return the emptyValue
	 */
	public String getEmptyValue() {
		return emptyValue;
	}

	/**
	 * @param emptyValue the emptyValue to set
	 */
	public void setEmptyValue(String emptyValue) {
		this.emptyValue = emptyValue;
	}
	

		
}