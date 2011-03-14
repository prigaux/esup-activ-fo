
/**
 * ESUP-Portail Commons - Copyright (c) 2006-2009 ESUP-Portail consortium.
 */
package org.esupportail.activfo.web.converters;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.model.SelectItem;

import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.activfo.web.beans.BeanField;
import org.esupportail.activfo.web.beans.BeanMultiValue;


/**
 * A JSF converter to pass Integer instances.
 */
public class AnotherStudentConverter implements Converter {
	
	
	private BeanField etablissement;
	
	public AnotherStudentConverter() {
    }
 
    public Object getAsObject(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final String value){

    	String returnValue=null;
    	returnValue="{UAI:"+etablissement.getValue()+":ETUID}"+value;
        return returnValue;
    }
 
  //Affichage standard
    public String getAsString(
    		@SuppressWarnings("unused") final FacesContext context, 
			@SuppressWarnings("unused") final UIComponent component, 
			final Object value) {
    	
    	return "";
    }

	/**
	 * @return the etablissement
	 */
	public BeanField getEtablissement() {
		return etablissement;
	}

	/**
	 * @param etablissement the etablissement to set
	 */
	public void setEtablissement(BeanField etablissement) {
		this.etablissement = etablissement;
	}

	
    	
	
	
}