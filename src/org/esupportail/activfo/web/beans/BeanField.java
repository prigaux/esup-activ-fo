package org.esupportail.activfo.web.beans;

import java.util.List;

import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.esupportail.activfo.web.validators.Validator;


public interface BeanField<T> {
	
	public static final String MANYCHECKBOX="selectManyCheckbox";
	public static final String INPUTTEXT="inputText";
	public static final String ONERADIO="selectOneRadio";
	public static final String INPUTFILE="inputFileUpload";
	
	public String getKey();
	
	public void setKey(String key);
	
	public T getValue();
	
	public void setValue(T value);
	
	public String getFieldType();
	
	public void setFieldType(String fieldType);
	
	public String getHelp();
	
	public void setHelp(String help);
	
	public Validator getValidator();
	
	public void setValidator(Validator validator);
	
	public Converter getConverter();
	
	public void setConverter(Converter converter);
	
	public boolean isRequired();
	
	public void setRequired(boolean required);
		
	public boolean isDisabled();
	public void setDisabled(boolean disabled);
	
	public String getId();
	public void setId(String id);
	
	public List<BeanMultiValue> getValues();
	public void setValues(List<BeanMultiValue> values);
	
	public boolean isMultiValue();
	public void setMultiValue(boolean multiValue);
	
	public String getName();
	public void setName(String name);
	
	public int getNumberOfValue();
	public void setNumberOfValue(int numberOfValue);
	
	public List<String> getSelectedItems();
	public void setSelectedItems(List<String> selectedItems);
	
	public List<SelectItem> getDisplayItems();
	public void setDisplayItems(List<SelectItem> displayItems);
	
	public boolean isUpdateable();
	public void setUpdateable(boolean updateable);
	
	public String getNotice();
	public void setNotice(String notice);
	
	public String getConstraint();
	public void setConstraint(String constraint);
	
	public String getDigestConstraint();
	public void setDigestConstraint(String digestConstraint);
		
	public int getSize();
	
	public void setUseDisplayItems(boolean useDisplayItems);
	
	public boolean isUseConvertedValue();	
	public void setUseConvertedValue(boolean useConvertedValue);
	
	public boolean isSendMail();
	public void setSendMail(boolean sendMai);
	
	public UploadedFile getFileUpLoad();
	public void setFileUpLoad(UploadedFile fileUpLoad);
	
	
}
