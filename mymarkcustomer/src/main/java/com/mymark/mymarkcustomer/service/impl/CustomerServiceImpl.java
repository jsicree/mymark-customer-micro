/**
 * 
 */
package com.mymark.mymarkcustomer.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.mymark.mymarkcustomer.data.domain.Customer;
import com.mymark.mymarkcustomer.repository.CustomerRepository;
import com.mymark.mymarkcustomer.service.CustomerService;
import com.mymark.mymarkcustomer.service.CustomerServiceException;
import com.mymark.mymarkcustomer.service.ShoppingCartWebService;
import com.mymark.shoppingcart.api.ShoppingCartDto;


/**
 * @author joseph_sicree
 *
 */
public class CustomerServiceImpl implements CustomerService {

	protected final static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private ShoppingCartWebService cartService;

	public CustomerServiceImpl() {
		// TODO Auto-generated constructor stub
	}


	public CustomerServiceImpl(CustomerRepository customerRepo) {
		this.customerRepo = customerRepo;
	}


	@Override
	public Customer createNewCustomer(String firstName, String lastName, String userName, String email, String password)
			throws CustomerServiceException {

		log.info("Creating new customer " + userName);
		Customer newCustomer = saveCustomer(firstName, lastName, userName, email, password);
		
		log.info("Creating shopping cart for " + newCustomer.getUserName());
		ShoppingCartDto cart = cartService.createShoppingCart(newCustomer.getIdentifier());

		return newCustomer;
	}
	
	@Override
	public Customer lookupCustomerByUserName(String userName) throws CustomerServiceException {
		
		Customer c = customerRepo.findByUserName(userName);
		
		return c;
	}

	@Override
	public Customer lookupCustomerByEmail(String email) throws CustomerServiceException {

		Customer c = customerRepo.findByEmail(email);
		
		return c;
	}
	
	public Customer lookupCustomerById(Long id) throws ServiceException {
		Optional<Customer> c = customerRepo.findById(id);
		
		if (c.isPresent()) {
			return c.get();
		} else {
			return null;
		}
	}


	@Override
	public Customer lookupCustomerByIdentifier(String identifier) throws CustomerServiceException {
		Customer c = customerRepo.findByIdentifier(identifier);
		
		return c;
	}

	@Override
	public void deleteCustomer(Long id) throws CustomerServiceException {

		log.info("Deleting customer " + id);

		Optional<Customer> c = customerRepo.findById(id);

		if (c.isPresent()) {
			log.info("Customer found. Deleting cart");
			String cartCustIdent = cartService.deleteShoppingCart(c.get().getIdentifier());
			if (c.get().getIdentifier().equalsIgnoreCase(cartCustIdent)) {
				log.info("Cart deleted.");				
			}
			customerRepo.deleteById(id);
		}
	}

	@Transactional(isolation = Isolation.DEFAULT)
	private Customer saveCustomer(String firstName, String lastName, String userName, String email, String password) 
			throws CustomerServiceException {
		Customer existingCustomer = customerRepo.findByUserName(userName);
		if (existingCustomer != null) {
			throw new CustomerServiceException("A customer exists with the specified userName " + userName);
		}

		existingCustomer = customerRepo.findByEmail(email);
		if (existingCustomer != null) {
			throw new CustomerServiceException("A customer exists with the specified email " + email);
		}

		String identifier = UUID.randomUUID().toString();
		
		Customer newCustomer = customerRepo.saveAndFlush(new Customer(userName, firstName, lastName, email, identifier));
		
		return newCustomer;
		
	}
	
}
