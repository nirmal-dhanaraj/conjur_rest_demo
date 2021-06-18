package com.conjur.lab.demo.service;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import com.conjur.lab.demo.util.AuthUtil;



@Service
public class LoadPolicyService {
	@Value("${application.logon.url}")
	private String access_token_url;
	@Value("${account.name}")
	private String account_name;;

	
	public ResponseEntity<String> loadPolicy() throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException
	{
		System.out.println("Inside : loadPolicy() : Service ");
	
		RestTemplate restTemplate = new RestTemplate();
		String authorizationToken= AuthUtil.authlogin();		
		System.out.println("AddSecretService Token : "+authorizationToken); 
		
	    HttpHeaders headers = new HttpHeaders();	  
	    String authToken="Token token="+"\""+authorizationToken+"\"";
	    headers.add("Authorization",authToken);
		
		String policyBranch="root";
		String access_token_url2=access_token_url+"/"+"policies"+"/"+account_name+"/"+"policy"+"/"+policyBranch;  
	
	// Just copy & paste your YML file below 
		String body="- !group\r\n" + 
				"  id: dap-admins\r\n" + 
				"  owner: !user admin\r\n" + 
				"  annotations:\r\n" + 
				"    Department: DAP Security Admins\r\n" + 
				"\r\n" + 
				"# PERMIT: Assign admins-dap GROUP full control permissions (root Branch)\r\n" + 
				"- !permit\r\n" + 
				"  role: !group dap-admins\r\n" + 
				"  privileges: [ read, create, update ]\r\n" + 
				"  resources:\r\n" + 
				"    - !policy root\r\n" + 
				"\r\n" + 
				"# POLICY: Create dev Branch \r\n" + 
				"- !policy\r\n" + 
				"  id: dev\r\n" + 
				"  owner: !group dap-admins\r\n" + 
				"  annotations:\r\n" + 
				"    Environment: Development";
	 	 
	    headers.set("Accept", "text/plain");
	    // build the request
	    HttpEntity<String> request2 = new HttpEntity<String>(body,headers);
		ResponseEntity<String> response2= restTemplate.postForEntity(access_token_url2, request2,String.class);
		System.out.println("\n Policy Loaded :\n       "+response2.getBody().toString());
		System.out.println(" Status Code  : "+response2.getStatusCode());
		return response2;
	
	}
}
