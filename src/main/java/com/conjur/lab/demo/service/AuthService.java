package com.conjur.lab.demo.service;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {
	@Value("${application.logon.url}")
	private String access_token_url;
	@Value("${account.name}")
	private String account_name;;
	@Value("${auth.username}")
	String username;
	@Value("${auth.pass}")
	 String password;
	public ResponseEntity<String> getAccessToken() throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException
	{
		System.out.println("Inside : getAccessToken() : Service ");
		// =======================================================================
		// Step 1 : Service Credentials must converted Base64 format
		// =======================================================================
		String originalInput = username+":"+password;
		String encodedString = Base64.encodeBase64String(originalInput.getBytes());
		RestTemplate restTemplate = new RestTemplate();
		RestTemplate restTemplate2 = new RestTemplate();

		// =======================================================================
		// Step 2: Connect to Conjur/DAP REST API to retrieve API Key
		// =======================================================================
	    HttpHeaders headers = new HttpHeaders();
	    // set `content-type` header
	    headers.setContentType(MediaType.APPLICATION_JSON);	
	    headers.set("Accept-Encoding","base64");
	    headers.set("Authorization", "Basic "+encodedString);
	    // build the request
	    HttpEntity<String> request = new HttpEntity<String>(headers);	
	
	    String api_key_url=access_token_url+"/"+"authn"+"/"+account_name+"/"+"login";
		ResponseEntity<String> response= restTemplate.exchange(api_key_url,HttpMethod.GET,request,String.class);
				
		// =======================================================================
		//Step 3: Connect to Conjur/DAP REST API to obtain Temporary Access Token
		// =======================================================================
		// RestTemplate restTemplate2 = new RestTemplate();
	    HttpHeaders headers2 = new HttpHeaders();	 
	    headers2.setContentType(MediaType.APPLICATION_JSON);		
	    headers2.set("Accept-Encoding","base64");
	    // build the request
	    HttpEntity<String> request2 = new HttpEntity<String>(response.getBody().toString(),headers2);
	    // {{dapHostname}}/authn/{{dapAccount}}/{{dapUsername}}/authenticate
	    access_token_url=access_token_url+"/"+"authn"+"/"+account_name+"/"+username+"/"+"authenticate";  
		ResponseEntity<String> response2= restTemplate2.postForEntity(access_token_url, request2,String.class);
		System.out.println("Start Lived Token is : \n "+response2);
		return response2;
		
	}
}