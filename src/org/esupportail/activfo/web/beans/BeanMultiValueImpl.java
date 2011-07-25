package org.esupportail.activfo.web.beans;

import javax.faces.component.UIInput;
import javax.faces.convert.Converter;
import javax.faces.event.ValueChangeEvent;

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

//immediate input hack: update model at apply-request, not update-model
public void setValue(ValueChangeEvent evt) {
	setValue((String) evt.getNewValue());
	  // prevent setter being called again during update-model phase
	  ((UIInput) evt.getComponent()).setLocalValueSet(false);
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
