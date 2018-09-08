package com.mymark.mymarkcustomer.ws.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mymark.customer.api.CustomerDto;
import com.mymark.customer.api.CustomerResponse;
import com.mymark.customer.api.NewCustomerRequest;
import com.mymark.mymarkcustomer.data.CustomerDTOConverter;
import com.mymark.mymarkcustomer.data.domain.Customer;
import com.mymark.mymarkcustomer.service.CustomerService;
import com.mymark.mymarkcustomer.service.CustomerServiceException;
import com.mymark.mymarkcustomer.ws.ApiException;

/**
 * Handles requests for the form page examples.
 */
@CrossOrigin
@RestController
@RequestMapping("api")
public class CustomerServiceController {

	@Autowired
	protected CustomerService customerService;
	
	@Autowired
	protected MessageSource messageSource;
		
	protected final static Logger log = LoggerFactory.getLogger(CustomerServiceController.class);

	public CustomerServiceController() {

	}

	@RequestMapping(value = "/customer", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<CustomerResponse> createNewCustomer(
			@RequestBody NewCustomerRequest request) throws ApiException {
		
		CustomerResponse response = new CustomerResponse();
		
		log.info("In createNewCustomer...");
		
		Customer newCustomer;
		try {
			newCustomer = customerService.createNewCustomer(request.getFirstName(), request.getLastName(), request.getUserName(), request.getEmail(), request.getPassword());
			CustomerDto dto = CustomerDTOConverter.toCustomerDto(newCustomer);
			response.setCustomer(dto);
		} catch (CustomerServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/customer/{userName}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<CustomerResponse> getCustomerByUserName(
			@PathVariable(required = true) String userName) throws ApiException {
		
		
		log.info("In getCustomerByUserName...");
		log.debug("Fetching customer: " + userName);
		
		CustomerResponse response = new CustomerResponse();

		try {
			Customer customer = customerService.lookupCustomerByUserName(userName);
			if (customer != null) {
				response.setCustomer(CustomerDTOConverter.toCustomerDto(customer));
			}
		} catch (CustomerServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/customer/{userName}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteCustomer(
			@PathVariable(required = true) String userName) throws ApiException {
		
		
		log.info("In deleteCustomer...");
		log.debug("Deleting customer: " + userName);
		
		try {
			Customer customer = customerService.lookupCustomerByUserName(userName);
			if (customer != null) {
				customerService.deleteCustomer(customer.getId());
			}
		} catch (CustomerServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(userName, HttpStatus.OK);
		
	}
	
	
}