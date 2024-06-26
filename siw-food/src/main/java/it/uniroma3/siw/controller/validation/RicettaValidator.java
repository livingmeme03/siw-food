package it.uniroma3.siw.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.RicettaService;

public class RicettaValidator implements Validator{

	@Autowired 
	private RicettaService ricettaService;


	@Override
	public void validate(Object o, Errors errors) {
		Ricetta ricetta = (Ricetta) o;
		//TODO: scrivere metodo che dice che non possono esserci ricette duplicate
		//per farlo serve il metodo apposito del repository!! (existsBy...)

	}

	@Override
	public boolean supports(Class<?> aClass) {

		return Ricetta.class.equals(aClass);
	}

}
