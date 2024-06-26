package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.controller.validation.RicettaValidator;
import it.uniroma3.siw.service.RicettaService;

@Controller
public class RicettaController {
	
	@Autowired
	private RicettaService ricettaService;
	
	@Autowired 
	private RicettaValidator ricettaValidator;
	
	@GetMapping("/elencoRicette")		//non servono validazioni 
	public String showElencoIngredienti(Model model) {
		model.addAttribute("ricette", this.ricettaService.findAll());
		return "elencoRicette.html";
	}
	
	@GetMapping("/ricetta/{id}") 
	public String showRicetta(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ricetta", this.ricettaService.findById(id));
		model.addAttribute("listaIngredienti", this.ricettaService.findById(id).getListaIngredienti());
		return "ricetta.html";
	}
}
