package org.esupportail.activfo.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;


public class CategoryBeanFieldImpl implements CategoryBeanField {
	
	//private String label;
	private String title;
	private String name;
	private List<BeanField> listBeanField=new ArrayList<BeanField>(); // les champs à afficher à l'utilisateur

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the listBeanField
	 */
	public List<BeanField> getListBeanField() {
		return listBeanField;
	}

	/**
	 * @param listBeanField the listBeanField to set
	 */
	public void setListBeanField(List<BeanField> listBeanField) {
		this.listBeanField = listBeanField;
	}

	
	

	
}
