/**
 * ESUP-Portail LDAP Account Activation - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-activ
 */
package org.esupportail.activfo.domain.beans.channels;

/**
 * @author aanli
 *
 */
public class ChannelImpl implements Channel{	
	private String homeMsg="";
	private String codeMsg="";
	private String paramMsg="";
	private String label;
	private String name;
	/**
	 * @return the homeMsg
	 */
	public String getHomeMsg() {
		return homeMsg;
	}
	/**
	 * @param homeMsg the homeMsg to set
	 */
	public void setHomeMsg(String homeMsg) {
		this.homeMsg = homeMsg;
	}
	/**
	 * @return the sentMsg
	 */
	public String getCodeMsg() {
		return codeMsg;
	}
	/**
	 * @param sentMsg the codeMsg to set
	 */
	public void setCodeMsg(String codeMsg) {
		this.codeMsg = codeMsg;
	}
	/**
	 * @return the paramMsg
	 */
	public String getParamMsg() {
		return paramMsg;
	}
	/**
	 * @param paramMsg the paramMsg to set
	 */
	public void setParamMsg(String paramMsg) {
		this.paramMsg = paramMsg;
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

}
