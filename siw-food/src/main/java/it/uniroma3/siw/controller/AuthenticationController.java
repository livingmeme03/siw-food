package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	private UserService userService;
}
