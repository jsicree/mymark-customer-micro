package com.mymark.mymarkcustomer.ws.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mymark.customer.api.ApiFieldError;
import com.mymark.customer.api.ApiGlobalError;
import com.mymark.customer.api.ErrorResponse;
import com.mymark.mymarkcustomer.ws.ApiErrorCode;
import com.mymark.mymarkcustomer.ws.ApiException;
import com.mymark.mymarkcustomer.ws.ApiMessages;

@ControllerAdvice(basePackages={"com.mymark.mymarkcustomer.ws.controller"})
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	protected final static Logger log = LoggerFactory
			.getLogger(ApiExceptionHandler.class);
		
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		log.debug("In handleMethodArgumentNotValid.");

		BindingResult bindingResult = ex.getBindingResult();
		
		ArrayList<ApiFieldError> fieldErrors = new ArrayList<ApiFieldError>();
		
		for (FieldError fe: bindingResult.getFieldErrors()) {
			ApiFieldError fieldError = new ApiFieldError();
			fieldError.setCode(fe.getCode());
			fieldError.setField(fe.getField());
			fieldError.setMessage(fe.getDefaultMessage());
			fieldErrors.add(fieldError);
		}
		
		ArrayList<ApiGlobalError> globalErrors = new ArrayList<ApiGlobalError>();
		
		for (ObjectError oe : bindingResult.getGlobalErrors()) {
			ApiGlobalError globalError = new ApiGlobalError();
			globalError.setCode(oe.getCode());
			globalError.setMessage(oe.getDefaultMessage());
			globalErrors.add(globalError);
		}

		ErrorResponse resp = new ErrorResponse();
		resp.getFieldErrors().addAll(fieldErrors);
		resp.getGlobalErrors().addAll(globalErrors);

		return new ResponseEntity<>(resp, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	/**
	 * Handler for <code>ApiException</code>.
	 * 
	 * @param ex
	 * @param request
	 * @return 
	 */
	@ExceptionHandler(value={ApiException.class})
	public ResponseEntity<Object> handleApiException(Exception ex, WebRequest request) {

		log.debug("In handleApiException. Exception = " + ex.getMessage());
		log.error(ex.getMessage());
		
		ArrayList<ApiGlobalError> globalErrors = new ArrayList<ApiGlobalError>();
		
		ApiGlobalError globalError = new ApiGlobalError();
		globalError.setCode(ApiErrorCode.API_EXCEPTION.getCode());
		globalError.setMessage(ApiMessages.API_EXCEPTION_MSG);
		globalErrors.add(globalError);

		ErrorResponse resp = new ErrorResponse();
		resp.getGlobalErrors().addAll(globalErrors);
				
		return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

	@ExceptionHandler(value={IllegalArgumentException.class})
	public ResponseEntity<Object> handleIllegalArgumentException(Exception ex, WebRequest request) {

		log.debug("In handleIllegalArgumentException. Exception = " + ex.getMessage());
		log.error(ex.getMessage());
		
		ArrayList<ApiGlobalError> globalErrors = new ArrayList<ApiGlobalError>();
		
		ApiGlobalError globalError = new ApiGlobalError();
		globalError.setCode(ApiErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getCode());
		globalError.setMessage(ex.getMessage());
		globalErrors.add(globalError);

		ErrorResponse resp = new ErrorResponse();
		resp.getGlobalErrors().addAll(globalErrors);
				
		return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
		
	}
	
	
}
