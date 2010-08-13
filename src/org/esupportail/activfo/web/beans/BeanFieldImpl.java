package org.esupportail.activfo.web.beans;

import javax.faces.convert.Converter;

import org.esupportail.activfo.web.validators.Validator;


public class BeanFieldImpl<T> implements BeanField<T> {
	
	private String key;
	private T value;
	private String fieldType;
	private String help;
	private Converter converter;
	private Validator validator;
	private String id;
	private String valueAcceptation;
	
	
	
	
	
	
	public String getValueAcceptation() {
		return valueAcceptation;
	}
	public void setValueAcceptation(String valueAcceptation) {
		this.valueAcceptation = valueAcceptation;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	
	public Validator getValidator() {
		return validator;
	}
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
	
	public Converter getConverter() {
		return converter;
	}
	public void setConverter(Converter converter) {
		this.converter = converter;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getHelp() {
		return help;
	}
	public void setHelp(String help) {
		this.help = help;
	}
	
}
