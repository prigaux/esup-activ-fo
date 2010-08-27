package org.esupportail.activfo.web.component.html;

import java.io.IOException;

import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.lang.StringUtils;
import org.esupportail.activfo.web.taglib.html.SelectBooleanCheckboxTag;
import org.fckfaces.util.Util;

/**
 * 
 * @author srecinto
 *
 */
public class SelectBooleanCheckBox extends HtmlSelectBooleanCheckbox {
	
	
	public static final String COMPONENT_FAMILY = "SelectBooleanCheckBoxFamily";


	

	/**
	 * 
	 */
	

	public String getComponentType() { 
		
		return SelectBooleanCheckboxTag.COMPONENT_TYPE; 
	}
	
	/**
	 * 
	 */
	public String getRendererType() { 
		return SelectBooleanCheckboxTag.RENDERER_TYPE;
	} 
	
	
	/**
	 * 
	 * @return
	 */
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
	
	
	

	
}
