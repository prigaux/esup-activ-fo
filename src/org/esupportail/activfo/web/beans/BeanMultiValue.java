package org.esupportail.activfo.web.beans;

import javax.faces.convert.Converter;

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
	
	public Converter getConverter();
	
	public void setConverter(Converter converter);
	
	public boolean isConvertedValue();

}
