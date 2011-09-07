package org.esupportail.activfo.web.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.esupportail.activfo.domain.beans.Account;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.springframework.beans.factory.InitializingBean;


public class CategoryBeanFieldImpl implements CategoryBeanField,InitializingBean {
	
	private String title;
	private String name;
	private List<BeanField> listBeanField=new ArrayList<BeanField>(); 
	private HashMap<String,List<String>> profile;
	private HashMap<BeanField,HashMap<String,List<String>>> beanFieldProfile;
	private  Account account;
	private final Logger logger = new LoggerImpl(getClass());
	
	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		if (profile==null && beanFieldProfile==null && account!=null)  {
			Assert.notNull(this.profile, 
					"property profile or beanFieldProfile of class " + this.getClass().getName() + " can not be null");
			Assert.hasText(this.profile.toString(),"property profile or beanFieldProfile of class  " + this.getClass().getName()+ " can not be null");
		}
		
		if ((profile!=null ||beanFieldProfile!=null) && account==null)  {
			Assert.notNull(this.account, 
					"property account of class " + this.getClass().getName() + " can not be null");
			Assert.hasText(this.account.toString(),"property account of class  " + this.getClass().getName()+ " can not be null");
		}
		
	}
	
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
	 * @return the profiling listBeanField
	 */
	//TODO améliorer la lisibilité de l'algo
	public List<BeanField> getProfilingListBeanField() {
		if(beanFieldProfile==null) return listBeanField;
		
		List<BeanField> profilingListBeanField = new ArrayList<BeanField>();
		for(BeanField beanField:listBeanField){
			HashMap<String,List<String>> fieldProfiling=beanFieldProfile.get(beanField);
			boolean nextBeanField=false;
			if(fieldProfiling!=null && account!=null){
				Set<String> keySet = fieldProfiling.keySet();
				for(String attribute : keySet) {
					List<String> profileValues=fieldProfiling.get(attribute);
					List<String> accountValues=account.getAttributes(attribute);
					for(String profileValue:profileValues)
						if(accountValues.contains(profileValue)){		
							profilingListBeanField.add(beanField);
							nextBeanField=true;
							break;
						}
					if(nextBeanField) break; //Pas besoin de tester les autres attributs du beanField courant
				}				
			}
			else profilingListBeanField.add(beanField);			
		}	
		
		return profilingListBeanField;
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

	/**
	 * @return the profile
	 */
	public HashMap<String, List<String>> getProfile() {
		return profile;
	}

	/**
	 * @param profile the profile to set
	 */
	public void setProfile(HashMap<String, List<String>> profile) {
		this.profile = profile;
	}

	public boolean getAccess() {
				
		if (profile!=null && account !=null) {
			Set<String> keySet = profile.keySet();
			for(String attribute : keySet) {
				List<String> profileValues=profile.get(attribute);
				List<String> accountValues=account.getAttributes(attribute);
				for(String accountValue:accountValues)
					if(profileValues.contains(accountValue))		
						return true;																					
			}
		} else
			return true;
		
		return false;
    }

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return the beanFieldProfile
	 */
	public HashMap<BeanField, HashMap<String, List<String>>> getBeanFieldProfile() {
		return beanFieldProfile;
	}

	/**
	 * @param beanFieldProfile the beanFieldProfile to set
	 */
	public void setBeanFieldProfile(
			HashMap<BeanField, HashMap<String, List<String>>> beanFieldProfile) {
		this.beanFieldProfile = beanFieldProfile;
	}

	
}
