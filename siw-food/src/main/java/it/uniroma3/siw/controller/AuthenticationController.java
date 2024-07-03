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
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	/*=======================================================================================================*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*----------------------------------------SERVICE E VALIDATOR--------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*=======================================================================================================*/
	
	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private CredentialsValidator credentialsValidator;

	@Autowired
	private CuocoService cuocoService;

	@Autowired
	private UserService userService;
	
	/*=======================================================================================================*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*------------------------------------------REGISTRAZIONE------------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*=======================================================================================================*/
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		//	model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		model.addAttribute("cuoco", new Cuoco());
		return "formRegister.html";
	}

	@PostMapping("/register")
	public String newUser(/*@Valid @ModelAttribute("user") User user, BindingResult bindingResultUser, */
			@Valid @ModelAttribute("credentials") Credentials credentials, BindingResult bindingResultCredentials, 
			@Valid @ModelAttribute("cuoco") Cuoco cuoco, BindingResult bindingResultCuoco, 
			Model model) {
		User user = new User();
		user.setNome(cuoco.getNome());
		user.setCognome(cuoco.getCognome());
		if(this.cuocoService.existsByNomeAndCognomeAndDataNascita(cuoco.getNome(), cuoco.getCognome(), cuoco.getDataNascita())) {
			user.setCuoco(this.cuocoService.findByNomeAndCognomeAndDataNascita(cuoco.getNome(), cuoco.getCognome(), cuoco.getDataNascita()));
		}
		else {	
			user.setCuoco(cuoco);
		}
		credentials.setUser(user);
		this.credentialsValidator.validate(credentials, bindingResultCredentials);


		if(bindingResultCredentials.hasErrors() || bindingResultCuoco.hasErrors()) {		//aggiungi alla form gli errori dell'user			
			model.addAttribute("cuocoErrors", bindingResultCuoco);	
			return "formRegister.html";																	//così può stampare anche i suoi messaggi di errore	
		}

		else {
			credentialsService.saveCredentials(credentials); //Role lo setto qui, anche l'hash della pwd
			return "redirect:login"; //finito di registrare redirecto a /
		}

	}
	
	/*=======================================================================================================*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*--------------------------------------MOSTRA INDEX OPPORTUNO-------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*=======================================================================================================*/
	
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
				return "/admin/index.html";
			}
			return "indexCuoco.html";
		}

	}
	
	/*=======================================================================================================*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*----------------------------------------MOSTRA FORM LOGIN----------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*=======================================================================================================*/
	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "login.html";
	}
	
	/*=======================================================================================================*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*----------------------------------------METODI DI SUPPORTO---------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*=======================================================================================================*/
	
	public Cuoco getCuocoSessioneCorrente() {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials utenteSessioneCorrente = this.credentialsService.findByUsername(user.getUsername());
		Cuoco cuoco = utenteSessioneCorrente.getUser().getCuoco();
		return cuoco;
	}

}
