package com.mymark.mymarkcustomer.api.client;

public class ClientException extends Exception {

	private static final long serialVersionUID = 1L;

	private Integer httpStatusCode;
	
	public ClientException() {
		// TODO Auto-generated constructor stub
	}

	public ClientException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ClientException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ClientException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ClientException(String message, Throwable cause, Integer httpStatusCode) {		
		super(message, cause);
		this.httpStatusCode = httpStatusCode;
	}
	
	public ClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(Integer httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	
}
