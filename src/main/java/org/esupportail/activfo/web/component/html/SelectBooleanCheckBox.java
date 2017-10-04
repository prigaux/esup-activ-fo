package org.esupportail.activfo.web.component.html;

import javax.faces.component.html.HtmlSelectBooleanCheckbox;

import org.esupportail.activfo.web.taglib.html.SelectBooleanCheckboxTag;

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
