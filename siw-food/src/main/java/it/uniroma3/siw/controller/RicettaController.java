package it.uniroma3.siw.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validation.RicettaValidator;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.RicettaService;
import jakarta.validation.Valid;

@Controller
public class RicettaController {

	@Autowired
	private RicettaService ricettaService;

	@Autowired
	private CuocoService cuocoService;

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

	@GetMapping("/aggiungiRicetta")
	public String showFormAggiungiRicetta(Model model) {
		model.addAttribute("nuovaRicetta", new Ricetta());	
		model.addAttribute("cuoco", new Cuoco());
	//	model.addAttribute("ingredienti", new String());
	//	model.addAttribute("quantitàIngredienti", new String());
	//	model.addAttribute("immagini", new String());
		return "formAggiungiRicetta.html";
	}

	@PostMapping("/aggiungiRicetta")
	public String newRicetta(@Valid @ModelAttribute("nuovaRicetta") Ricetta ricetta, BindingResult bindingResult, 
			 @ModelAttribute("cuoco") Cuoco cuoco, 
			//@ModelAttribute("ingredienti") String ingredienti, 
			// @ModelAttribute("immagini") String immagini, 
			// @ModelAttribute("quantitàIngredienti") String quantitàIngredienti, 
			Model model) {
		System.out.println(ricetta.getTitolo());
		System.out.println(cuoco.toString());
		System.out.println(cuoco.getNome());
		System.out.println(cuoco.getCognome());
		Cuoco cuocoAssociato = this.cuocoService.findByNomeAndCognomeAndDataNascita(cuoco.getNome(), cuoco.getCognome(), cuoco.getDataNascita());
		ricetta.setCuoco(cuocoAssociato);
		//List<String> nomiIngredienti = Arrays.asList(ingredienti.split(","));
		this.ricettaValidator.validate(ricetta, bindingResult);
		if(bindingResult.hasErrors()) {
			return "formAggiungiRicetta.html";
		}
		else {
			this.ricettaService.save(ricetta);
			return "redirect:ricetta/"+ricetta.getId();
		}
		
		
					
//		Percorsi file immagini: <input type="text" th:field="${immagini}">
//		<br><br>
//		Lista ingredienti: <input type="text" th:field="${ingredienti}">
//    	<span th:if="${#fields.hasErrors('ingredienti')}" th:errors="*{ingredienti}"></span>
//		<br><br>
//		Quantità ingredienti: <input type="text" th:field="${quantitàIngredienti}">
//    	<span th:if="${#fields.hasErrors('quantitàIngredienti')}" th:errors="*{quantitàIngredienti}"></span>
//		<br><br>
	}
}
