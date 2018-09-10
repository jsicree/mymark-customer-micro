package com.mymark.mymarkcustomer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.mymark.mymarkcustomer.service.ShoppingCartWebService;
import com.mymark.mymarkshoppingcart.api.client.ClientException;
import com.mymark.mymarkshoppingcart.api.client.ShoppingCartRestClient;
import com.mymark.shoppingcart.api.ShoppingCartDto;

public class ShoppingCartWebServiceImpl implements ShoppingCartWebService {

	protected final static Logger log = LoggerFactory.getLogger(ShoppingCartWebServiceImpl.class);

	@Value("${SHOPPING_CART_MICRO_URL}")
	protected String serviceUrl;

	public ShoppingCartWebServiceImpl() {
		log.info("Initializing ShoppingCartWebService. ServiceURL: " + serviceUrl);
	}
	
	@Override
	public ShoppingCartDto createShoppingCart(String customerIdentifier) {

		ShoppingCartRestClient cartClient = new ShoppingCartRestClient(serviceUrl, null, null);
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
		ShoppingCartRestClient cartClient = new ShoppingCartRestClient(serviceUrl, null, null);
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
