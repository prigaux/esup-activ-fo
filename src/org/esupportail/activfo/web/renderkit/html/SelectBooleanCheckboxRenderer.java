package org.esupportail.activfo.web.renderkit.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;


import org.apache.myfaces.renderkit.html.HtmlCheckboxRenderer;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;


public class SelectBooleanCheckboxRenderer extends HtmlCheckboxRenderer {
	private final Logger logger = new LoggerImpl(getClass());
	
	public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws ConverterException { 
		
		Converter converter= ((ValueHolder) component).getConverter(); 
		return converter.getAsObject(context, component, (String)submittedValue.toString()); 
		

		
	}
	
	
	protected void renderCheckbox(FacesContext context, UIComponent component,String value, String label,boolean disabled,boolean checked,boolean renderId){
		
		Converter converter= ((ValueHolder) component).getConverter(); 
		
		if (converter!=null){
			value=converter.getAsString(context, component, value);
			
			if (value.equals("false")) checked=false;
			else checked=true;
		}
		
		
		try {
			super.renderCheckbox(context, component,value,label,disabled,checked,renderId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
}
