package org.esupportail.activfo.web.beans;

import javax.faces.convert.Converter;
import javax.faces.event.ValueChangeEvent;

/**
 * @author BANG
 *
 */
public interface BeanMultiValue {
	
	/**
	 * @return
	 */
	public String getValue();
	
	/**
	 * @param value
	 */
	public void setValue(String value);
	public void setValue(ValueChangeEvent evt);
	
	public Converter getConverter();
	
	public void setConverter(Converter converter);
	
	public boolean isConvertedValue();
	
	public boolean isUseConvertedValue();	
	public void setUseConvertedValue(boolean useConvertedValue);
	
}
