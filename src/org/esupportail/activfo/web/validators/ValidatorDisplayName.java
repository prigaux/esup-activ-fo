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

import org.apache.commons.lang.StringUtils;
import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.commons.beans.AbstractI18nAwareBean;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;

public class ValidatorDisplayName extends AbstractI18nAwareBean implements Validator{
	
	/** But: 
	 * Le champ "Nom usage" (displayName) est une combinaison possible des champs Nom usage, Nom de jeune fille, Pr�nom et autres pr�noms.
	 * Les accents, les tirets,les espaces, les virgules et les majuscules sont acceptées.
	 * En entrée: Nom usage
	 * En sortie:  Retourne un message d'erreur s'il ne correspond à aucune des combinaisons définies.
	 * 
	 * Un displayName est composé de 2 termes : term1+term2. l'ordre doit être respecté.
	 * Un terme peut être composé d'un ou plusieurs valeurs. Il n'y a pas relation d'ordre entre ces valeurs au sein d'un terme.
	 * 
	 * Exple, 
	 * term1=givenName+up1AltGivenName ET term2=up1BirthName+sn
	 * displayName=givenName+up1AltGivenName+up1BirthName+sn
	 * 
	 */
	private static final long serialVersionUID = 8849185735359561457L;
	private final Logger logger = new LoggerImpl(getClass());

	private Account account;
	private String term1,term2;
	
	private List<String> attrsTerm1,attrsTerm2;
	
	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		
		Assert.notNull(this.account, 
				"property account of class " + this.getClass().getName() + " can not be null");
		
		Assert.notNull(this.term1, 
				"property term1 of class " + this.getClass().getName() + " can not be null");
		Assert.notNull(this.term2, 
				"property term2 of class " + this.getClass().getName() + " can not be null");
						
		attrsTerm1=Arrays.asList(term1.split(","));
		attrsTerm2=Arrays.asList(term2.split(","));
		
	}

	/* (non-Javadoc)
	 * @see org.esupportail.activfo.web.validators.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		
		String strValue=(String)value;
		ArrayList<String> attrsTerm1Values=new ArrayList<String>();
		ArrayList<String> attrsTerm2Values=new ArrayList<String>();
		
		
		if (value instanceof String) {	
			
			strValue =normalize(strValue);
			
			// Récupérer la valeur des attributs de LDAP			
			for (String attr:attrsTerm1){
				String valueAttr=account.getAttribute(attr);
				if(valueAttr!=null && !attrsTerm1Values.contains(valueAttr=normalize(Pattern.quote(valueAttr)))) attrsTerm1Values.add(valueAttr);	
			}
											
			for (String attr:attrsTerm2){
				String valueAttr=account.getAttribute(attr);
				if(valueAttr!=null && !attrsTerm2Values.contains(valueAttr=normalize(Pattern.quote(valueAttr)))) attrsTerm2Values.add(valueAttr);	
			}	
			
			String p1=this.getPattern(attrsTerm1Values);
			String p2=this.getPattern(attrsTerm2Values);
			String p="^"+p1+p2+"$";		
			
			logger.debug("Pattern is : "+p);
			logger.debug("displayName to check: "+strValue);

			// Rechercher si le displayName saisi correspond à l'expression régulière 
			Pattern pat = Pattern.compile(p);
			Matcher match = pat.matcher(strValue);
			if (! (match.find()))throw new ValidatorException(getFacesErrorMessage("VALIDATOR.DISPLAYNAME.INVALID"));
			
		}
		
	}
	
	/**
	 * construit une combinaison parmi les valeurs fourni dans une liste
	 * 
	 * @param t liste de valeurs à combiner
	 * @param v stocke les indices des valeurs déjà présentes dans une combinaison courante 
	 * @param c contient les combinaisons
	 * @param s une chaîne de caractères d'une combinaison
	 */
    private void combine(final ArrayList<String> t,
            final ArrayList<Integer> v, ArrayList<String>c, final String s){
        
            for(int i=0;i<t.size();i++)
                if(!v.contains(i)){
                    String r=(s==null?t.get(i):s+t.get(i));
                    c.add(r);
                    
                    ArrayList<Integer> w=(ArrayList<Integer>) v.clone();
                    w.add(i);
                    combine(t,w,c,r);                    
            }        
    }
    
    /**
     * créé le pattern (regex) devant vérifier une liste de valeur
     * 
     * @param t liste de valeurs
     * @return pattern
     */
    private String getPattern(ArrayList<String> t){
    	ArrayList<String>c=new ArrayList<String>();
    	combine(t,new ArrayList<Integer>(),c,null);
    	
        String p="(";
        p+=StringUtils.join(c.toArray(),'|');
        p+=")";
        
        return p;
    }
	
	
	private String normalize(String value)
	{
			// Convertir une chaîne accentué en chaîne sans accent.
			value = Normalizer.normalize(value, Normalizer.Form.NFD);
			// Supprimer les espaces,les caractères diacritiques, le tiret et la virgule  
			value=value.replaceAll("[\u0300-\u036F]|\\s|-|,", "");
			
			return value.toUpperCase();    	
	}
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @param term1 the term1 to set
	 */
	public void setTerm1(String term1) {
		this.term1 = term1;
	}

	/**
	 * @param term2 the term2 to set
	 */
	public void setTerm2(String term2) {
		this.term2 = term2;
	}
		
}