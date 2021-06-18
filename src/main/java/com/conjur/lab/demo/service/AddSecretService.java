package com.conjur.lab.demo.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conjur.lab.demo.util.AuthUtil;

@Service
public class AddSecretService {
	@Value("${application.logon.url}")
	private String access_token_url;
	@Value("${account.name}")
	private String account_name;;
	
	//@Autowired
	public ResponseEntity<String> addSecret() throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException
	{		

		System.out.println("Inside : addSecret() ");
		String authorizationToken= AuthUtil.authlogin();		
		System.out.println("AddSecretService Token : "+authorizationToken); 
		RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();	  
	    String authToken="Token token="+"\""+authorizationToken+"\"";
	    headers.add("Authorization",authToken);
	    
	    // build the request
	    String body="ANdsfXsqerwK3edKOLPsdmndwzzzA";
	    HttpEntity<String> request = new HttpEntity<String>(body,headers);
	    // {{dapHostname}}/secrets/{{dapAccount}}/variable/{{dapSecretID}}
	    String access_token_url2=access_token_url+"/"+"secrets"+"/"+account_name+"/"+"variable"+"/"+"dev/app/db/password";  
	    System.out.println("Inside final REST "+access_token_url2);
		ResponseEntity<String> response=restTemplate.exchange(access_token_url2,HttpMethod.POST, request, String.class);
	    System.out.println("Add Secret Response  : "+response.getStatusCodeValue());
		return response;

	}
}
