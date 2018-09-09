package com.mymark.mymarkcustomer.service;

import com.mymark.shoppingcart.api.ShoppingCartDto;

public interface ShoppingCartWebService {

	ShoppingCartDto createShoppingCart(String customerIdentfier);
	String deleteShoppingCart(String customerIdentfier);
	
}
