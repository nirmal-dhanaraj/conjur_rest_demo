package com.conjur.lab.demo.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AuthUtil {
	public static String authlogin() {
		RestTemplate restTemplate = new RestTemplate();
		// System.out.println("====================== ");
		ResponseEntity<String> response= restTemplate.postForEntity("http://localhost:8095/getAccessToken",null,String.class);
		return response.getBody().toString();
		
	}		
}
