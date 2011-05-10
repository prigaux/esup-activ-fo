package org.esupportail.activfo.web.beans;

import java.util.List;



public interface CategoryBeanField {

	public String getTitle();

	public void setTitle(String title);

	public String getName();

	public void setName(String name);
	
	public List<BeanField> getListBeanField();
	
	public void setListBeanField(List<BeanField> listBeanField);

}
