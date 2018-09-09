package com.mymark.mymarkcustomer.api.client;

import java.util.Base64;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public abstract class BaseRestClient {

	private String serviceUrl;

	private RestTemplate restTemplate;
	private HttpHeaders headers;

	protected final static Logger log = LoggerFactory
			.getLogger(BaseRestClient.class);

	
	public BaseRestClient(String serviceUrl, String username, String password) {
		super();
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String authHeader = encodeCredentialsForBasicAuth(username, password);
        headers.set(HttpHeaders.AUTHORIZATION, authHeader );	
        this.serviceUrl = serviceUrl;
        
	}

    public BaseRestClient() {
		// TODO Auto-generated constructor stub
	}

	private static String encodeCredentialsForBasicAuth(String username, String password) {

    	String plainCreds = username + ":" + password;
    	byte[] plainCredsBytes = plainCreds.getBytes();
    	byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
    	String base64Creds = new String(base64CredsBytes);    	    	    	
    	return "Basic " + base64Creds;
    	
    }	
	
	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String url) {
		this.serviceUrl = url;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	
}
