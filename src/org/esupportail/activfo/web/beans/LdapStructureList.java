package org.esupportail.activfo.web.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.Set;

import org.esupportail.commons.services.ldap.LdapEntity;
import org.esupportail.commons.services.ldap.LdapEntityService;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.beans.factory.InitializingBean;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

public class LdapStructureList extends ArrayList<SelectItem> implements InitializingBean {
	
	/**
	 * Permet de charger dynamiquement la liste des structures à partir de LDAP
	 */
	private static final long serialVersionUID = 1L;

	private final Logger logger = new LoggerImpl(getClass());
	
	private LdapEntityService ldapEntityService;
	private String ldapSearchFilter="(objectClass=supannEntite)";
	private String businessCategory="businessCategory";
	private String firstLabel=""; 
	private String label="description";	
	private String value="supannCodeEntite";
	private Map<String,String> businessCategoryMap;
	
	public void afterPropertiesSet() throws Exception {
		SelectItem si = new SelectItem();		
		si.setLabel(firstLabel);	
		si.setValue("");
		this.add(si);
		
		Set<String> keys= businessCategoryMap.keySet();
		for(String key:keys){
			SelectItemGroup sig =new SelectItemGroup();
			sig.setLabel(businessCategoryMap.get(key));
									
			String filter="(&("+businessCategory+"="+key+")"+ldapSearchFilter+")";
			logger.debug("Bussiness category Filter is : "+ filter);
			List<LdapEntity> ldapEntities = ldapEntityService.getLdapEntitiesFromFilter(filter);
			SelectItem[] entities=new SelectItem[ldapEntities.size()];
			int i=0;
			for(LdapEntity ldapEntity:ldapEntities)
			{
				si = new SelectItem();
				si.setLabel(ldapEntity.getAttribute(label));
				si.setValue(ldapEntity.getAttribute(value));
				entities[i++]=si;
			}
			
			Arrays.sort(entities,new Comparator<SelectItem>(){
			    public int compare(SelectItem o1, SelectItem o2) {
			         return o1.getLabel().compareTo(o2.getLabel());
			    }
			});		
			
			sig.setSelectItems(entities);
			this.add(sig);
		}
	}
	
	/**
	 * @return the ldapEntityService
	 */
	public LdapEntityService getLdapEntityService() {
		return ldapEntityService;
	}

	/**
	 * @param ldapEntityService the ldapEntityService to set
	 */
	public void setLdapEntityService(LdapEntityService ldapEntityService) {
		this.ldapEntityService = ldapEntityService;
	}

	/**
	 * @return the ldapSearchFilter
	 */
	public String getLdapSearchFilter() {
		return ldapSearchFilter;
	}


	/**
	 * @param ldapSearchFilter the ldapSearchFilter to set
	 */
	public void setLdapSearchFilter(String ldapSearchFilter) {
		this.ldapSearchFilter = ldapSearchFilter;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the firstLabel
	 */
	public String getFirstLabel() {
		return firstLabel;
	}

	/**
	 * @param firstLabel the firstLabel to set
	 */
	public void setFirstLabel(String firstLabel) {
		this.firstLabel = firstLabel;
	}

	/**
	 * @param businessCategory the businessCategory attribute to use
	 */
	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	/**
	 * @return the businessCategoryMap
	 */
	public Map<String, String> getBusinessCategoryMap() {
		return businessCategoryMap;
	}

	/**
	 * @param businessCategoryMap the businessCategoryMap to set
	 */
	public void setBusinessCategoryMap(Map<String, String> businessCategoryMap) {
		this.businessCategoryMap = businessCategoryMap;
	}
		
}
