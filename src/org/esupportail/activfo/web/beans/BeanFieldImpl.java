package org.esupportail.activfo.web.beans;

import org.esupportail.activfo.web.validators.Validator;
import org.esupportail.activfo.web.validators.ValidatorDisplayName;

public class BeanFieldImpl implements BeanField {
	
	private String key;
	private String value;
	private String typeChamp;
	private String aide;
	private String converter;
	private Validator validator;
	private String id;
	private String valueAcceptation;
	private boolean value2;
	
	
	
	
	public boolean isValue2() {
		return value2;
	}
	public void setValue2(boolean value2) {
		this.value2 = value2;
	}
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTypeChamp() {
		return typeChamp;
	}
	public void setTypeChamp(String typeChamp) {
		this.typeChamp = typeChamp;
	}
	public String getAide() {
		return aide;
	}
	public void setAide(String aide) {
		this.aide = aide;
	}
	public Validator getValidator() {
		return validator;
	}
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
	public String getConverter() {
		return converter;
	}
	public void setConverter(String converter) {
		this.converter = converter;
	}
	
}
