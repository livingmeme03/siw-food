package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validation.CredentialsValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegister.html";
	}

	@PostMapping("/register")
	public String newUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResultUser, 
			@Valid @ModelAttribute("credentials") Credentials credentials, BindingResult bindingResultCredentials, 
			Model model) {

		credentials.setUser(user);
		this.credentialsValidator.validate(credentials, bindingResultCredentials);
		
//		BindingResult br = bindingResultCredentials;
//		br.addAllErrors(bindingResultUser);
		if(bindingResultUser.hasErrors() || bindingResultCredentials.hasErrors()) {		//aggiungi alla form gli errori dell'user
			model.addAttribute("userErrors", bindingResultUser);				//così può stampare anche i suoi messaggi di errore
//			System.out.println(bindingResultUser.getAllErrors().toString());	
//            System.out.println(bindingResultCredentials.getAllErrors().toString());
			return "formRegister.html";
		}
		
		else {
			credentialsService.saveCredentials(credentials); //Role lo setto qui, anche l'hash della pwd
			return "redirect:login"; //finito di registrare redirecto a /
		}

	}


	@GetMapping("/")
	public String showIndex(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof AnonymousAuthenticationToken) {
			return "index.html";
		}
		else {
			UserDetails userDetails = (UserDetails)authentication.getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if(credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				return "admin/index.html";
			}
			return "index2.html";
		}

	}

	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "login.html";
	}

}
