/**
 * 
 */
package com.mymark.mymarkcustomer.service;

/**
 * @author joseph_sicree
 *
 */
public class CustomerServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public CustomerServiceException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public CustomerServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public CustomerServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CustomerServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
