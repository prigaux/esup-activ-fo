package org.esupportail.activfo.web.taglib.html;

import org.apache.myfaces.taglib.html.HtmlSelectBooleanCheckboxTag;

/**
 * 
 * @author srecinto
 *
 */
public class SelectBooleanCheckboxTag extends HtmlSelectBooleanCheckboxTag {
	public static final String COMPONENT_TYPE = "SelectBooleanCheckBox";
	public static final String RENDERER_TYPE = "SelectBooleanCheckboxRenderer";
	
	
	public SelectBooleanCheckboxTag(){
		super();
	}
	/**
	 * 
	 */
	public String getComponentType() { 
		
		return COMPONENT_TYPE; 
	}
	
	/**
	 * 
	 */
	public String getRendererType() { 
		
		return RENDERER_TYPE;
	}
	
	
}
