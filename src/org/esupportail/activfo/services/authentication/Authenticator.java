package org.esupportail.activfo.services.authentication;

import org.esupportail.activfo.domain.beans.User;

/**
 * The interface of authenticators.
 */
public interface Authenticator {

	/**
	 * @return the authenticated user.
	 */
	User getUser();

}