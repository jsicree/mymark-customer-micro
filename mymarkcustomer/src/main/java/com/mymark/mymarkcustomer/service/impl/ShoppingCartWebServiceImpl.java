package com.mymark.mymarkcustomer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mymark.mymarkcustomer.service.ShoppingCartWebService;
import com.mymark.mymarkshoppingcart.api.client.ClientException;
import com.mymark.mymarkshoppingcart.api.client.ShoppingCartRestClient;
import com.mymark.shoppingcart.api.ShoppingCartDto;

public class ShoppingCartWebServiceImpl implements ShoppingCartWebService {

	protected final static Logger log = LoggerFactory.getLogger(ShoppingCartWebServiceImpl.class);

	
	
	@Override
	public ShoppingCartDto createShoppingCart(String customerIdentifier) {

		ShoppingCartRestClient cartClient = new ShoppingCartRestClient("http://localhost:8083/api", null, null);
		ShoppingCartDto dto = null;
		try {
			log.info("Creating shopping cart for " + customerIdentifier);
			dto = cartClient.createShoppingCart(customerIdentifier);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public String deleteShoppingCart(String customerIdentifier) {

		String retValue = null;
		ShoppingCartRestClient cartClient = new ShoppingCartRestClient("http://localhost:8083/api", null, null);
		try {
			log.info("Deleting shopping cart for " + customerIdentifier);
			retValue = cartClient.deleteShoppingCart(customerIdentifier);
			log.info("Deleting shopping cart response: " + retValue);
			
		} catch (ClientException e) {
			e.printStackTrace();
		}
		
		return retValue;
	}

}
