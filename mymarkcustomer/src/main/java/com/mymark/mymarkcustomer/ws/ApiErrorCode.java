package com.mymark.mymarkcustomer.ws;

public enum ApiErrorCode {
	INVALID_VALUE("InvalidValue"),
	NULL_OR_EMPTY_VALUE("NullOrEmptyValue"),
	API_EXCEPTION("ApiException"),
	ILLEGAL_ARGUMENT_EXCEPTION("IllegalArgumentException"), 
	USERNAME_EXISTS("UserNameExists"), 
	EMAIL_EXISTS("EmailExists"), 
	SERVICE_EXCEPTION("ServiceException"), 
	ACCESS_DENIED_EXCEPTION("AccessDenied");
	
	private String code;
	
	ApiErrorCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}
