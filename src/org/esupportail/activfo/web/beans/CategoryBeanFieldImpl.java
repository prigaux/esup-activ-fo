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
	private  Account account;
	private final Logger logger = new LoggerImpl(getClass());
	
	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		if ((profile==null && account !=null))  {
			Assert.notNull(this.profile, 
					"property profile of class " + this.getClass().getName() + " can not be null");
			Assert.hasText(this.profile.toString(),"property profile of class  " + this.getClass().getName()+ " can not be null");
		}
		
		if (profile!=null && account ==null)  {
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
		
		boolean returnValue=false;
		//String vs;
		
		if (profile!=null && account !=null) {
			Set keySet = profile.keySet();
			for(Object ks : keySet) {
				List<String> ls=profile.get(ks);
				//logger.debug("  key:"+ks.toString());
				for (String vs : ls) { 
					//logger.debug("    vs:"+vs);
					if (account.getAttribute(ks.toString()).contains(vs)) {
						returnValue=true;
						//logger.debug("    vs:"+vs+"** : "+account.getAttribute(ks.toString())+"T/F:"+account.getAttribute(ks.toString()).contains(vs));
					}
				}
			}
		} else
			returnValue=true;
		
		return returnValue;
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

	
}
