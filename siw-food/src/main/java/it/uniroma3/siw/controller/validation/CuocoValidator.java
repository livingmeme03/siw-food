package it.uniroma3.siw.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.service.CuocoService;

public class CuocoValidator implements Validator{

	@Autowired
	private CuocoService cuocoService;


	@Override
	public void validate(Object o, Errors errors) {
		Cuoco cuoco = (Cuoco)o;
		//TODO: scrivere metodo che dice che non possono esserci cuochi duplicati
		//per farlo serve il metodo apposito del repository!! (existsBy...)
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Cuoco.class.equals(aClass);
	}


}
