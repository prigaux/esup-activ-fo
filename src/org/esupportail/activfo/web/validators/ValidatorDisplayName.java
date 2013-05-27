package org.esupportail.activfo.web.validators;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.commons.beans.AbstractI18nAwareBean;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

public class ValidatorDisplayName extends AbstractI18nAwareBean implements Validator{
	
	/** But: 
	 * Le champ "Nom usage" est une combinaison possible des champs Nom usage, Nom de jeune fille, Pr�nom et autres pr�noms.
	 * Les accents, les tiret,les espace et les majuscules sont accept�s.
	 * En entr�e: Nom usage
	 * En sortie:  Retourne un message d'erreur s'il ne correspond � aucune des combinaisons d�finies.
	 * 
	 */
	private static final long serialVersionUID = 8849185735359561457L;
	private final Logger logger = new LoggerImpl(getClass());

	private Account account;
	private String displayNameAttr;
	private String caracterForbiddenDisplayName; 
	
	/**
	 * 
	 */

	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		
		String strPattern="";
		String strValue=(String)value;
		List<String> listAtt=new ArrayList<String>();
		
		
		if (value instanceof String) {	
			
			strValue =normalize(strValue);
			
			// R�cup�rer la valeur des attributs de LDAP
			List<String> attrPersoInfo=Arrays.asList(displayNameAttr.split(","));
			
			for (int j=0;j<attrPersoInfo.size();j++){
				listAtt.add(normalize(account.getAttribute(attrPersoInfo.get(j))));	
			}
			
			//Liste de combinaisons possibles (sn,up1BirthName,givenName,up1AltGivenName nomm�s respectivement 0,1,2,3)
			/* [0,2][0,3][2,0][3,0][0,2,3][0,3,2] ( sn+givenName,up1AltGivenName)
			 * [1,2][1,3][2,1][3,1][1,2,3][1,3,2]( up1BirthName+ givenName,up1AltGivenName) 
			 * [0,1,2][0,1,3][0,1,2,3][0,1,3,2]((sn,up1BirthName) + givenName,up1AltGivenName)	
			 * [1,0,2][1,0,3][1,0,2,3][1,0,3,2]((up1BirthName,sn) + givenName,up1AltGivenName)
			*/
			// Construction de l'expression r�guli�re
			if (listAtt.size()>0 && attrPersoInfo.size()==listAtt.size()) {
                String e0 = (listAtt.get(0).length())>0? listAtt.get(0):"";
                String e1 = (listAtt.get(1).length())>0? listAtt.get(1):"";
                String e2 = (listAtt.get(2).length())>0? listAtt.get(2):"";
                String e3 = (listAtt.get(3).length())>0? listAtt.get(3):"";
                
                strPattern=
                "^("
                  +e0+e2+"|"+e0+e3+"|"+e2+e0+"|"+e3+e0+"|"+e0+e2+e3+"|"+e0+e3+e2 +"|"    
                  +e1+e2+"|"+e1+e3+"|"+e2+e1+"|"+e3+e1+"|"+e1+e2+e3+"|"+e1+e3+e2+"|"
                  +e0+e1+e2+"|"+ e0+e1+e3+"|"+ e0+e1+e2+"|"+e0+e1+e3+e2+"|"
                  +e1+e0+e2+"|"+ e2+e1+e3+"|"+e1+e0+e2+e3+"|"+e1+e0+e3+e2+           
                ")$";
            }

			// Rechercher si le displayName saisi correspond � l'expression r�guli�re 
			Pattern pat = Pattern.compile(strPattern);
			Matcher match = pat.matcher(strValue);
			if (! (match.find()))throw new ValidatorException(getFacesErrorMessage("VALIDATOR.DISPLAYNAME.INVALID"));
			
		}
		
	}
	
	
	private String normalize(String strValue)
	{
		String returnValue="";
		if (strValue instanceof String) {	
			// Convertir une cha�ne accentu� en cha�ne sans accent.
			returnValue = Normalizer.normalize(strValue, Normalizer.Form.NFD);
			// Supprimer les espaces,les caract�res diacritiques et le tiret  
			returnValue=returnValue.replaceAll("[\u0300-\u036F\\s|-]", "");
			
			returnValue=returnValue.toUpperCase();
		}
    	return returnValue;
	}

	
	
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return the displayNameAttr
	 */
	public String getDisplayNameAttr() {
		return displayNameAttr;
	}

	/**
	 * @param displayNameAttr the displayNameAttr to set
	 */
	public void setDisplayNameAttr(String displayNameAttr) {
		this.displayNameAttr = displayNameAttr;
	}

	/**
	 * @return the caracterForbiddenDisplayName
	 */
	public String getCaracterForbiddenDisplayName() {
		return caracterForbiddenDisplayName;
	}

	/**
	 * @param caracterForbiddenDisplayName the caracterForbiddenDisplayName to set
	 */
	public void setCaracterForbiddenDisplayName(String caracterForbiddenDisplayName) {
		this.caracterForbiddenDisplayName = caracterForbiddenDisplayName;
	}
	
	
}