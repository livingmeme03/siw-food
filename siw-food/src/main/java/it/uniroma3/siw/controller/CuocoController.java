package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.uniroma3.siw.controller.validation.CuocoValidator;
import it.uniroma3.siw.controller.validation.RicettaValidator;
import it.uniroma3.siw.service.CuocoService;

@Controller
public class CuocoController {
	
	@Autowired
	private CuocoService cuocoService;
	
	
	@Autowired 
	private CuocoValidator cuocoValidator;
}
