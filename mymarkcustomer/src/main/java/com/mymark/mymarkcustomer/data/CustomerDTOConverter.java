package com.mymark.mymarkcustomer.data;

import com.mymark.customer.api.CustomerDto;
import com.mymark.mymarkcustomer.data.domain.Customer;


public final class CustomerDTOConverter {

	public static CustomerDto toCustomerDto(Customer c) {
		CustomerDto dto = new CustomerDto();
		dto.setId(c.getId());
		dto.setFirstName(c.getFirstName());
		dto.setLastName(c.getLastName());
		dto.setUserName(c.getUserName());
		dto.setEmail(c.getEmail());
		dto.setIdentifier(c.getIdentifier());
		return dto;
	}

}
