package org.esupportail.activfo.web.beans;

import java.util.HashMap;
import java.util.List;

import org.esupportail.activfo.domain.beans.Account;

public interface CategoryBeanField {

	public String getTitle();

	public void setTitle(String title);

	public String getName();

	public void setName(String name);
	
	public List<BeanField> getListBeanField();
	
	public void setListBeanField(List<BeanField> listBeanField);
	
	public HashMap<String, List<String>> getProfile();
	
	public void setProfile(HashMap<String, List<String>> profile);
	
	public Account getAccount();
	
	public void setAccount(Account account);
	
	public boolean getAccess();
	
}
