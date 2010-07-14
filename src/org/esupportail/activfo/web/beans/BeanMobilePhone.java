package org.esupportail.activfo.web.beans;


import org.esupportail.activfo.web.validators.ValidatorMobilePhone;
import org.springframework.beans.factory.InitializingBean;

public class BeanMobilePhone implements InitializingBean,BeanInfo{
	
	/**
	 * 
	 */
	
	private String key;
	private String value;
	private ValidatorMobilePhone validator;
	private String typeChamp;
	private String aide;
	

	public BeanMobilePhone(){
		
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
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


	public ValidatorMobilePhone getValidator() {
		return validator;
	}


	public void setValidator(ValidatorMobilePhone validator) {
		this.validator = validator;
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
	
	
	

}
