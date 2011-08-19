package org.esupportail.activfo.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.convert.Converter;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.esupportail.activfo.web.validators.Validator;


public class BeanFieldImpl<T> implements BeanField<T> {
	
	private String key;
	private T value;
	private String fieldType=INPUTTEXT;
	private String help;
	private Converter converter;
	private Validator validator;
	private boolean required;
	private boolean disabled;
	private String id;
	
	private List<BeanMultiValue> values=new ArrayList<BeanMultiValue>();
	
	private List<String> selectedItems=new ArrayList<String>(); // les champs sélectionnés par l'utilisateur
	private List<SelectItem> displayItems=new ArrayList<SelectItem>(); // les champs à afficher à l'utilisateur
	private List<String> stringDisplayItems=new ArrayList<String>();
	
	private List<BeanMultiValue> hideItems=new ArrayList<BeanMultiValue>(); // valeurs recupérées du BO mais non exploitées par le FO. Lors de l'enregistrement, à renvoyer au BO
	
	private List<SelectItem> oneRadioItems = new ArrayList<SelectItem>();
	
	private boolean multiValue;
	
	private String name;
	
	private int numberOfValue;
	
	private boolean disable;
	
	private boolean updateable;
	private boolean useDisplayItems; //si oui, ne considère que les valeurs présentes dans le champ displayItems. Sinon ne prend pas en compte displayItems
	private boolean useConvertedValue;
	
	private String notice;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public boolean isDisabled() {
		return disabled;
	}
	
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public Validator getValidator() {
		return validator;
	}
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
	
	public Converter getConverter() {
		return converter;
	}
	public void setConverter(Converter converter) {
		this.converter = converter;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getHelp() {
		return help;
	}
	public void setHelp(String help) {
		this.help = help;
	}
	
	public List<BeanMultiValue> getValues()	
	{
       if(MANYCHECKBOX.equals(fieldType))
		{
    	   values.clear();			
			for(String s : selectedItems){
				BeanMultiValue bmv = new BeanMultiValueImpl();
				bmv.setValue(s);
				bmv.setConverter(converter);
				bmv.setUseConvertedValue(useConvertedValue);
				values.add(bmv);				
			}
			for(BeanMultiValue bmv: hideItems)
				this.values.add(bmv);								
		}		 
		return this.values;
	}

	public void setValues(List<BeanMultiValue> values){
		this.values=values;
		selectedItems.clear();
		hideItems.clear();
		if(MANYCHECKBOX.equals(fieldType)){			
			if(useDisplayItems){
				for(BeanMultiValue bmv : values)
					if(stringDisplayItems.contains(bmv.getValue()))
						selectedItems.add(bmv.getValue());
					else 
						hideItems.add(bmv);
			}
			else for(BeanMultiValue bmv : values)				
					selectedItems.add(bmv.getValue());
		}
		for(BeanMultiValue bmv : values) 
			bmv.setConverter(converter);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumberOfValue() {
		return numberOfValue;
	}
	public void setNumberOfValue(int numberOfValue) {
		this.numberOfValue = numberOfValue;
	}
	/**
	 * @return the selectedItems
	 */
	public List<String> getSelectedItems() {		 
		return selectedItems;
	}
	/**
	 * @param selectedItems the selectedItems to set
	 */
	public void setSelectedItems(List<String> selectedItems) {		
		this.selectedItems = selectedItems;
	}
	/**
	 * @return the displayItems
	 */
	public List<SelectItem> getDisplayItems() {
		return displayItems;
	}
	/**
	 * @param displayItems the displayItems to set
	 */
	public void setDisplayItems(List<SelectItem> displayItems) {
				
		for(SelectItem si:displayItems){
			 stringDisplayItems.add(String.valueOf(si.getValue()));
			 //i18n
		}
		this.displayItems = displayItems;
	}
	/**
	 * @return the disable
	 */
	public boolean isDisable() {
		return disable;
	}
	/**
	 * @param disable the disable to set
	 */
	public void setDisable(boolean disable) {
		this.disable = disable;
	}
	

	 public void changeValue(ValueChangeEvent evt) {
       this.value= (T) evt.getNewValue();
	 }
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the oneRadioItems
	 */
	public List<SelectItem> getOneRadioItems() {
		return oneRadioItems;
	}

	/**
	 * @param oneRadioItems the oneRadioItems to set
	 */
	public void setOneRadioItems(List<SelectItem> oneRadioItems) {
		this.oneRadioItems = oneRadioItems;
	}

		/**
	 * @return the updateable
	 */
	public boolean isUpdateable() {
		return updateable;
	}

	/**
	 * @param updateable the updateable to set
	 */
	public void setUpdateable(boolean updateable) {
		this.updateable = updateable;
	}

	

	/**
	 * @return the notice
	 */
	public String getNotice() {
		return notice;
	}

	/**
	 * @param notice the notice to set
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}

	public int getSize() {
		if (values!=null)
			return values.size();
		else
			return 0;
	}

	/**
	 * @return the useDisplayItems
	 */
	public boolean isUseDisplayItems() {
		return useDisplayItems;
	}

	/**
	 * @param useDisplayItems the useDisplayItems to set
	 */
	public void setUseDisplayItems(boolean useDisplayItems) {
		this.useDisplayItems = useDisplayItems;
	}

	/**
	 * @return the useConvertedValue
	 */
	public boolean isUseConvertedValue() {
		return useConvertedValue;
	}

	/**
	 * @param useConvertedValue the useConvertedValue to set
	 */
	public void setUseConvertedValue(boolean useConvertedValue) {
		this.useConvertedValue = useConvertedValue;
	}

	/**
	 * @return the multiValue
	 */
	public boolean isMultiValue() {
		return multiValue;
	}

	/**
	 * @param multiValue the multiValue to set
	 */
	public void setMultiValue(boolean multiValue) {
		this.multiValue = multiValue;
	}
	
}
