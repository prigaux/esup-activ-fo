package org.esupportail.activfo.services.authentication;

/**
 * The interface of ProxyTicketGenerator.
 */
public interface ProxyTicketGenerator {

	public String getProxyTicket();
	public String getProxyTicket(String targetUrl);
}