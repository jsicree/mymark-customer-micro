package com.mymark.mymarkcustomer.ws;

/**
 * A wrapper around a <code>Exception</code> used for the API.
 * 
 * @author jsicree
 *
 */
public class ApiException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ApiException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApiException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ApiException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	public ApiException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	public ApiException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}



	

}
