
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
 * A JSF converter to pass Integer instances.
 */
public class LdapPhoneFaxConverter implements Converter {
	
	private final Logger logger = new LoggerImpl(getClass());
	
	public LdapPhoneFaxConverter() {
    }
 
    public Object getAsObject(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final String value){
    	
    	
    	String strValue=(String) value;
        strValue=ldapFormatPhoneNumber(toFrenchPhoneNumber(strValue));
    	
        return strValue;
    }
 
  //Affichage standard
    public String getAsString(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final Object value) {
    	String strValue = (String) value;
    	
        if ((strValue.replaceAll(" ", "")).matches("(^(((\\+|00)33)|0)[1-9]\\d{8}$)")) strValue=strValue.replaceAll(" ", "").replaceAll("^\\+330", "0").replaceAll("^\\+33", "0");
    	
    	return strValue;
    }

    private String toFrenchPhoneNumber(String phoneNumber) {
      String s = phoneNumber.replaceAll(" ", "");
      if (s.matches("(^(((\\+|00)33)|0)[1-9]\\d{8}$)")){
          // Si tel francais remplacer +33 ou 033 par 0
          s = s.replaceAll("^\\+33", "0").replaceAll("^0033", "0");
      }
      else{
          // Si tel non francais ne rien remplacer
          s = phoneNumber;
      }
      logger.debug("toFrenchPhoneNumber :"+s);
      return s;
  }


   public String ldapFormatPhoneNumber(String phoneNumber) {
      String s = phoneNumber.replaceAll(" ", "");
      if (s.matches("^0[1-9]\\d{8}")) {
          // espacement par 2 chiffres replaceAll("\\d\\d", " $0"), remplacer premier 0 par +33
          s = s .replaceAll("\\d\\d", " $0").replaceAll("^ 0", "+33 ");
      }
      else
          // Si tel étranger, remplacer 00 par +
          s = phoneNumber.replaceAll("^00", "+");
      return s;
  }
}