package org.esupportail.activfo.web.beans;

import java.util.Map;
import java.util.List;

import org.esupportail.activfo.domain.beans.Account;

public interface CategoryBeanField {

	public String getTitle();
	public void setTitle(String title);

	public String getName();
	public void setName(String name);
		
	public List<BeanField> getProfilingListBeanField();
	
	public List<BeanField> getListBeanField();
	public void setListBeanField(List<BeanField> listBeanField);
	
	public Map<String, List<String>> getProfile();
	public void setProfile(Map<String, List<String>> profile);
	
	public Account getAccount();
	public void setAccount(Account account);
	
	public boolean getAccess();
	
	public Map<BeanField, Map<String, List<String>>> getBeanFieldProfile();
	public void setBeanFieldProfile(Map<BeanField, Map<String, List<String>>> beanFieldProfile);
	
	public Map<BeanField, Map<String, List<String>>> getDeniedBeanFieldProfile();
	public void setDeniedBeanFieldProfile(Map<BeanField, Map<String, List<String>>> deniedBeanFieldProfile);
	
	
}
