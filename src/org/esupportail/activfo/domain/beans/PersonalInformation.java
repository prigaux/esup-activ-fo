package org.esupportail.activfo.domain.beans;


import java.util.HashMap;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.InitializingBean;

public class PersonalInformation  implements InitializingBean{
	
	private String key;
	private String value;
	
	public PersonalInformation(){
		
	}


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
	
	
	
	
	

	
		
}
