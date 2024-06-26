package it.uniroma3.siw.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.service.IngredienteService;

public class IngredienteValidator implements Validator{

	@Autowired
	private IngredienteService ingredienteService;


	@Override
	public void validate(Object o, Errors errors) {
		Ingrediente ingrediente = (Ingrediente) o;
		//TODO: scrivere metodo che dice che non possono esserci ingredienti duplicati
		//per farlo serve il metodo apposito del repository!! (existsBy...)

	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Ingrediente.class.equals(aClass);
	}

}
