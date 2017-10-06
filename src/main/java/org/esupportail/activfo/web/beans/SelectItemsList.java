package org.esupportail.activfo.web.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

import javax.faces.model.SelectItem;

public class SelectItemsList extends ArrayList<SelectItem>  {
	
	private final Logger logger = new LoggerImpl(getClass());
	
	
	
	/**
	 * @return the selectItemList
	 */
	public Map<String, String> getSelectItemList() {
		return new HashMap<String,String>();
	}

	/**
	 * @param selectItemList the selectItemList to set
	 */
	public void setSelectItemList(Map<String, String> selectItemList) {
		Set<String> keys = selectItemList.keySet();
		for(String key: keys) {
			String s = selectItemList.get(key);
			SelectItem si = new SelectItem();
			si.setLabel(s);
			si.setValue(key);
			this.add(si);
		}
	}
	
	
	

	

	
	
		
}
