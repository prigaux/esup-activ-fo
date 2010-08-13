package org.esupportail.activfo.web.beans;

import javax.faces.convert.Converter;

import org.esupportail.activfo.web.validators.Validator;


public interface BeanField<T> {
	
	
	public String getKey();
	
	public void setKey(String key);
	
	public T getValue();
	
	public void setValue(T value);
	
	public String getFieldType();
	
	public void setFieldType(String fieldType);
	
	public String getHelp();
	
	public void setHelp(String help);
	
	public Validator getValidator();
	
	public void setValidator(Validator validator);
	
	public Converter getConverter();
	public void setConverter(Converter converter);
	
	public String getId();
	
	public void setId(String id);
	
	public String getValueAcceptation();
	
	public void setValueAcceptation(String valueAcceptation);
	
	
	
}
