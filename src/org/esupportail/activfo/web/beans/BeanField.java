package org.esupportail.activfo.web.beans;

import org.esupportail.activfo.web.validators.Validator;
import org.esupportail.activfo.web.validators.ValidatorDisplayName;

public interface BeanField {
	
	
	public String getKey();
	
	public void setKey(String key);
	
	public String getValue();
	
	public void setValue(String value);
	
	public String getTypeChamp();
	
	public void setTypeChamp(String typeChamp);
	
	public String getAide();
	
	public void setAide(String aide);
	
	public Validator getValidator();
	
	public void setValidator(Validator validator);
	
	public String getConverter();
	
	public void setConverter(String converter);
	
	public String getId();
	
	public void setId(String id);
	
	public String getValueAcceptation();
	
	public void setValueAcceptation(String valueAcceptation);
	
	public boolean isValue2();
	
	public void setValue2(boolean value2);
	
}
