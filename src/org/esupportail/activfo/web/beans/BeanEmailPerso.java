package org.esupportail.activfo.web.beans;



import org.esupportail.activfo.web.validators.ValidatorEmail;
import org.springframework.beans.factory.InitializingBean;

public class BeanEmailPerso implements InitializingBean,BeanInfo{
	
	/**
	 * 
	 */
	
	private String key;
	private String value;
	private ValidatorEmail validator;
	private String typeChamp;
	private String aide;
	

	public BeanEmailPerso(){
		
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


	public ValidatorEmail getValidator() {
		return validator;
	}
	
	


	public void setValidator(ValidatorEmail validator) {
		this.validator = validator;
	}
	
	
	

}
