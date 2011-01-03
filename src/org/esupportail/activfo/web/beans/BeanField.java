package org.esupportail.activfo.web.beans;

import java.util.List;

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
	
	public boolean isRequired();
	
	public void setRequired(boolean required);
	
	public String getTypeBean();
	public void setTypeBean(String typeBean);
	
	public boolean isDisabled();
	public void setDisabled(boolean disabled);
	
	public String getId();
	public void setId(String id);
	
	public List<BeanMultiValue> getValues();
	public void setValues(List<BeanMultiValue> values);
	
    public String getIsMultiValue();
	public void setIsMultiValue(String isMultiValue);
	
	public String getDivName();
	public void setDivName(String divName);
	
	public int getNumberOfValue();
	public void setNumberOfValue(int numberOfValue);
	
	
	
	
}
