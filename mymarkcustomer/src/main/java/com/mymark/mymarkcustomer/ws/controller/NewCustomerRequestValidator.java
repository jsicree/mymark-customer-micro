package com.mymark.mymarkcustomer.ws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mymark.customer.api.NewCustomerRequest;
import com.mymark.mymarkcustomer.data.domain.Customer;
import com.mymark.mymarkcustomer.service.CustomerService;
import com.mymark.mymarkcustomer.service.CustomerServiceException;
import com.mymark.mymarkcustomer.ws.ApiErrorCode;
import com.mymark.mymarkcustomer.ws.ApiMessages;

@Component
public class NewCustomerRequestValidator implements Validator {

		
		@Autowired
		private CustomerService customerService;
		
		protected final static Logger log = LoggerFactory
				.getLogger(NewCustomerRequestValidator.class);
		

		public NewCustomerRequestValidator() {
			super();
		}

		@Override
		public boolean supports(Class<?> arg0) {
			return NewCustomerRequest.class.isAssignableFrom(arg0);
		}

		@Override
		public void validate(Object target, Errors errors) {
			NewCustomerRequest request = (NewCustomerRequest) target;

			log.debug("Validating new customer request.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", ApiErrorCode.NULL_OR_EMPTY_VALUE.getCode(), ApiMessages.NULL_OR_EMPTY_VALUE_MSG);
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", ApiErrorCode.NULL_OR_EMPTY_VALUE.getCode(), ApiMessages.NULL_OR_EMPTY_VALUE_MSG);
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", ApiErrorCode.NULL_OR_EMPTY_VALUE.getCode(), ApiMessages.NULL_OR_EMPTY_VALUE_MSG);
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", ApiErrorCode.NULL_OR_EMPTY_VALUE.getCode(), ApiMessages.NULL_OR_EMPTY_VALUE_MSG);
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", ApiErrorCode.NULL_OR_EMPTY_VALUE.getCode(), ApiMessages.NULL_OR_EMPTY_VALUE_MSG);
			
			if (!errors.hasErrors()) {
				try {
					Customer c = customerService.lookupCustomerByUserName(request.getUserName());
					if (c != null) {
						errors.reject(ApiErrorCode.USERNAME_EXISTS.getCode(), ApiMessages.USERNAME_EXISTS_MSG);
					}
					c = customerService.lookupCustomerByEmail(request.getEmail());
					if (c != null) {
						errors.reject(ApiErrorCode.EMAIL_EXISTS.getCode(), ApiMessages.EMAIL_EXISTS_MSG);
					}

				} catch (CustomerServiceException e) {
					log.error("An exception occurred while looking up customer for create. Username: " + request.getUserName(), e);
					errors.reject(ApiErrorCode.SERVICE_EXCEPTION.getCode(), ApiMessages.SERVICE_EXCEPTION_MSG);
				}
			}		
		}

	}