/**
 * 
 */
package org.esupportail.activfo.domain.beans.channels;

/**
 * @author aanli
 *
 */
public interface Channel {
	
	public String getHomeMsg();
	public void setHomeMsg(String homeMsg);
	
	public String getCodeMsg();
	public void setCodeMsg(String codeMsg);
	
	public String getParamMsg();
	public void setParamMsg(String paramMsg);
	
	public String getName();
	public void setName(String name);		
	
	public String getLabel();
	public void setLabel(String label);

}
