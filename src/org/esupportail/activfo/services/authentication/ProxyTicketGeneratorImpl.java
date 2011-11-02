/**
 * ESUP-Portail esup-activ - Copyright (c) 2004-2011 ESUP-Portail consortium.
 */
package org.esupportail.activfo.services.authentication; 

import java.io.Serializable;

import org.esupportail.commons.services.cas.CasService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.springframework.beans.factory.InitializingBean;

/**
 * A basic ProxyTicketGeneratorImpl implementation.
 */
public class ProxyTicketGeneratorImpl implements Serializable, InitializingBean, ProxyTicketGenerator {

	private static final long serialVersionUID = -4705071016710084224L;
	
	private final Logger logger = new LoggerImpl(getClass());
	
	private CasService casService;
	private String targetService;
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(casService, 
				"property authenticationService of class " + this.getClass().getName() 
				+ " can not be null");		
	}

	/* (non-Javadoc)
	 * @see org.esupportail.activfo.services.authentication.ProxyTicketGenerator#getProxyTicket()
	 */
	public String getProxyTicket() {
		String proxyTicket=casService.getProxyTicket(targetService);
		logger.debug("Proxy Ticket for targetUrl :"+targetService+" is : "+proxyTicket);
		return proxyTicket;
	}
	
	/* (non-Javadoc)
	 * @see org.esupportail.activfo.services.authentication.ProxyTicketGenerator#getProxyTicket(java.lang.String)
	 */
	public String getProxyTicket(String targetUrl) {
		String proxyTicket=casService.getProxyTicket(targetUrl);
		logger.debug("Proxy Ticket for targetUrl :"+targetUrl+" is : "+proxyTicket);			
		return proxyTicket;
	}

	/**
	 * @return the casService
	 */
	public CasService getCasService() {
		return casService;
	}

	/**
	 * @param casService the casService to set
	 */
	public void setCasService(CasService casService) {
		this.casService = casService;
	}

	/**
	 * @return the targetService
	 */
	public String getTargetService() {
		return targetService;
	}

	/**
	 * @param targetService the targetService to set
	 */
	public void setTargetService(String targetService) {
		this.targetService = targetService;
	}
}
