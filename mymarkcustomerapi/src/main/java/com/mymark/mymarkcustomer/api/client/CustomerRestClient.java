package com.mymark.mymarkcustomer.api.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import com.mymark.customer.api.CustomerDto;
import com.mymark.customer.api.CustomerResponse;
import com.mymark.customer.api.NewCustomerRequest;

public class CustomerRestClient extends BaseRestClient {


	protected final static Logger log = LoggerFactory
			.getLogger(CustomerRestClient.class);
	
	public CustomerRestClient(String url, String username, String password) {
		super(url, username, password);        
	}
	
	public CustomerDto createNewCustomer(String firstName, String lastName, String userName, String email, String password) throws ClientException {

		CustomerResponse response = null;

		try {
			
	        HttpEntity<NewCustomerRequest> request = new HttpEntity<NewCustomerRequest>(new NewCustomerRequest(), getHeaders());
	        request.getBody().setFirstName(firstName);
	        request.getBody().setLastName(lastName);
	        request.getBody().setUserName(userName);
	        request.getBody().setEmail(email);
	        request.getBody().setPassword(password);

	        ResponseEntity<CustomerResponse> resp = getRestTemplate().exchange(getServiceUrl() + "/customer", HttpMethod.POST, request, CustomerResponse.class);
	        response = resp.getBody();
			
		} catch (HttpStatusCodeException sce) {
			log.error("An HttpStatusCodeException was thrown calling the /customer web service method. HTTP status code: " + sce.getRawStatusCode());
			log.error("ErrorResponse for HttpStatusCodeException: " + sce.getResponseBodyAsString());
 			throw new ClientException(sce.getResponseBodyAsString(), sce, sce.getRawStatusCode());			
		} catch (RestClientException rce) {
			log.error("A RestClientException was thrown calling the /customer web service method.");
			throw new ClientException("RestClientException caught after call to /customer web service method.", rce);					
		}
		return response.getCustomer();
	}

	public CustomerDto getCustomer(String customerIdentifier) throws ClientException {

		CustomerResponse response = null;

		try {			
			HttpEntity<?> request = new HttpEntity<Object>(getHeaders());
	        ResponseEntity<CustomerResponse> resp = getRestTemplate().exchange(getServiceUrl() + "/customer/" + customerIdentifier, HttpMethod.GET, request, CustomerResponse.class);
	        response = resp.getBody();
	        
		} catch (HttpStatusCodeException sce) {
			log.error("An HttpStatusCodeException was thrown calling the /customer (GET). HTTP status code: " + sce.getRawStatusCode());
			log.error("ErrorResponse for HttpStatusCodeException: " + sce.getResponseBodyAsString());
 			throw new ClientException(sce.getResponseBodyAsString(), sce, sce.getRawStatusCode());			
		} catch (RestClientException rce) {
			log.error("A RestClientException was thrown calling the /customer web service method.");
			throw new ClientException("RestClientException caught after call to /customer (GET).", rce);					
		}

		return response.getCustomer();
	}
	
	public void deleteCustomer(String customerIdentifier) throws ClientException {

		log.info("Deleting customer: " + customerIdentifier);
		try {
			HttpEntity<?> request = new HttpEntity<Object>(getHeaders());
			getRestTemplate().exchange(getServiceUrl() + "/customer/" + customerIdentifier, HttpMethod.DELETE, request, String.class);			
			
			log.info("Customer deleted.");
		} catch (HttpStatusCodeException sce) {
			log.error("An HttpStatusCodeException was thrown calling the /customer web service method. HTTP status code: " + sce.getRawStatusCode());
			log.error("ErrorResponse for HttpStatusCodeException: " + sce.getResponseBodyAsString());
			throw new ClientException("HttpStatusCodeException caught after call to /customer web service method. HTTP status code: " + sce.getRawStatusCode(), sce);			
		} catch (RestClientException rce) {
			log.error("A RestClientException was thrown calling the /customer web service method.");
			throw new ClientException("RestClientException caught after call to /customer web service method.", rce);					
		}
	}
	
}
