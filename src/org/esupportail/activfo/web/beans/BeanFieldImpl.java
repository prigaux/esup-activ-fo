package org.esupportail.activfo.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.convert.Converter;

import org.esupportail.activfo.web.validators.Validator;


public class BeanFieldImpl<T> implements BeanField<T> {
	
	private String key;
	private T value;
	private String fieldType;
	private String help;
	private Converter converter;
	private Validator validator;
	private boolean required;
	private String typeBean;
	private boolean disabled;
	private String id;
	
	private List<BeanMultiValue> values;
	
	private String isMultiValue;
	
	private String divName;
	
	private int numberOfValue;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public String getTypeBean() {
		return typeBean;
	}
	public void setTypeBean(String typeBean) {
		this.typeBean = typeBean;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
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
	
	public List<BeanMultiValue> getValues()
	{
		return values;
	}
	
	public void setValues(List<BeanMultiValue> values){
		this.values=values;
	}
	
	public String getIsMultiValue() {
		return isMultiValue;
	}
	public void setIsMultiValue(String isMultiValue) {
		this.isMultiValue = isMultiValue;
	}
	
	public String getDivName() {
		return divName;
	}
	public void setDivName(String divName) {
		this.divName = divName;
	}
	public int getNumberOfValue() {
		return numberOfValue;
	}
	public void setNumberOfValue(int numberOfValue) {
		this.numberOfValue = numberOfValue;
	}
	
	
	
}
