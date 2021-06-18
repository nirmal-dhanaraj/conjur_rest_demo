package com.conjur.lab.demo.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.conjur.lab.demo.service.AddSecretService;
import com.conjur.lab.demo.service.AuthService;
import com.conjur.lab.demo.service.LoadPolicyService;
@Controller
public class ConjurController  {
	
private final AuthService authService;
private final AddSecretService addSecretService;
private final LoadPolicyService loadPolicyService;
	
@Autowired
ConjurController(final AuthService authService, final AddSecretService addSecretService, final LoadPolicyService loadPolicyService){
	this.authService= authService;
	this.addSecretService= addSecretService;
	this.loadPolicyService= loadPolicyService;
}
	
	@RequestMapping("/getAccessToken")
	public ResponseEntity<String> getAccessToken() throws Exception {
		System.out.println("Inside : Controller : getAccessToken() ");
		return authService.getAccessToken();
	}
	
	@RequestMapping("/addSecret")
	public ResponseEntity<String> addSecret() throws Exception {
		System.out.println("Inside : Controller : addSecret()");
		return addSecretService.addSecret();
	}
	
	@RequestMapping("/loadPolicy")
       public ResponseEntity<String> loadPolicy() throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException{
		System.out.println("Inside : Controller : loadPolicy()");
       return loadPolicyService.loadPolicy();
    }
	
}