package it.uniroma3.siw.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CredentialsService;

@Component
public class CredentialsValidator implements Validator{

	@Autowired 
	private CredentialsService credentialsService;


	@Override
	public void validate(Object o, Errors errors) {

		Credentials credentials = (Credentials) o;

		if(credentials.getUsername()!=null && this.credentialsService.existsByUsername(credentials.getUsername())) {
			errors.reject("credentials.duplicate");
		}

	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Credentials.class.equals(aClass);
	}

}
