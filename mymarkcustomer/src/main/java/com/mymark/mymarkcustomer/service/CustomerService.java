/**
 * 
 */
package com.mymark.mymarkcustomer.service;

import com.mymark.mymarkcustomer.data.domain.Customer;

/**
 * @author joseph_sicree
 *
 */
public interface CustomerService {


	public Customer createNewCustomer(String firstName, String lastName, String userName, String email, String password) throws CustomerServiceException;
	
	public Customer lookupCustomerById(Long id) throws CustomerServiceException;

	public Customer lookupCustomerByIdentifier(String identifier) throws CustomerServiceException;

	public Customer lookupCustomerByUserName(String userName) throws CustomerServiceException;

	public Customer lookupCustomerByEmail(String email) throws CustomerServiceException;
	
	public void deleteCustomer(Long id) throws CustomerServiceException;

}
