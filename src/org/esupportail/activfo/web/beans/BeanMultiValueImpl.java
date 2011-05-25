package org.esupportail.activfo.web.beans;

import javax.faces.convert.Converter;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

/**
 * @author BANG
 *
 */
public class BeanMultiValueImpl implements BeanMultiValue{
  /**
 * 
 */
private String value;

private Converter converter;

private final Logger logger = new LoggerImpl(getClass());

private String convertedValue=null;

/**
 * @return the value
 */
public String getValue() {
	return value;
}

/**
 * @param value the value to set
 */
public void setValue(String value) {
	this.value = value;
}

public boolean isConvertedValue() {
	
	if (converter!=null)
		convertedValue=converter.getAsString(null, null, value);
	
	if ("".equals(convertedValue) )
		return true;
	else 
		return false;
}

/**
 * @return the converter
 */
public Converter getConverter() {
	return converter;
}

/**
 * @param converter the converter to set
 */
public void setConverter(Converter converter) {
	this.converter = converter;
}



  
 	
}
