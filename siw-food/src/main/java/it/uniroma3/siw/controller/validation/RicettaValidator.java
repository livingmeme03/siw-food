package it.uniroma3.siw.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.RicettaService;

@Component
public class RicettaValidator implements Validator{

	@Autowired 
	private RicettaService ricettaService;


	@Override
	public void validate(Object o, Errors errors) {
		
		Ricetta ricetta = (Ricetta) o;
		if(ricetta.getTitolo()!=null && ricetta.getCuoco() !=null 
				&& this.ricettaService.existsByTitoloAndCuoco(ricetta.getTitolo(), ricetta.getCuoco())) {
			errors.reject("ricetta.duplicata");
		}
		if(ricetta.getCuoco()==null) {
			errors.reject("cuoco.nonEsiste");
		}

	}
	
	
	public void validateSimple(Object o, Errors errors) {
		
		Ricetta ricetta = (Ricetta) o;
		if(ricetta.getTitolo()!=null && this.ricettaService.existsByTitolo(ricetta.getTitolo())) {
			errors.reject("ricetta.duplicata");
		}

	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Ricetta.class.equals(aClass);
	}

}
