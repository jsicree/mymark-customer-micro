package com.mymark.mymarkcustomer;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.mymark.mymarkcustomer.service.CustomerService;
import com.mymark.mymarkcustomer.service.ShoppingCartWebService;
import com.mymark.mymarkcustomer.service.impl.CustomerServiceImpl;
import com.mymark.mymarkcustomer.service.impl.ShoppingCartWebServiceImpl;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@PropertySource({"classpath:application.properties"})
public class MyMarkCustomerApplication {

	@Resource
	private Environment environment;

	
	@Bean
	public CustomerService customerService() {
		return new CustomerServiceImpl();
	}

	@Bean
	public ShoppingCartWebService cartService() {
		return new ShoppingCartWebServiceImpl();
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(MyMarkCustomerApplication.class, args);
	}
}
