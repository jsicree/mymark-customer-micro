package com.mymark.mymarkcustomer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mymark.mymarkcustomer.data.domain.Customer;

/**
 * Repository to manage {@link Customer} instances.
 * 
 */
@Repository 
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByUserName(String userName);

	Customer findByEmail(String email);

	Customer findByIdentifier(String identifier);

}

