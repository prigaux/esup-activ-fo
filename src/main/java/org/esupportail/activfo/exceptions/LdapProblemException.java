package org.esupportail.activfo.exceptions;

public class LdapProblemException extends Exception{
	
	/**
	 * The id for serialization.
	 */
	private static final long serialVersionUID = 8197090501242229324L;

	/**
	 * @param message
	 */
	public LdapProblemException(final String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public LdapProblemException(final Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LdapProblemException(final String message, final Throwable cause) {
		super(message, cause);
	}

}