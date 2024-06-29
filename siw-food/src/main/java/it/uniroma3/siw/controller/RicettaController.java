package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
import it.uniroma3.siw.model.Ingrediente;
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
	
	/*-------------------------------------------------------------------------------------*/
	/*----------------------------------ELENCO RICETTE-------------------------------------*/
	/*-------------------------------------------------------------------------------------*/
	
	@GetMapping("/elencoRicette")		//non servono validazioni 
	public String showElencoRicette(Model model) {
		model.addAttribute("ricette", this.ricettaService.findAllByOrderByTitoloAsc());
		return "elencoRicette.html";
	}
	
	/*-------------------------------------------------------------------------------------*/
	/*----------------------------VISUALIZZAZIONE SINGOLA RICETTA--------------------------*/
	/*-------------------------------------------------------------------------------------*/
	
	@GetMapping("/ricetta/{id}") 
	public String showRicetta(@PathVariable("id") Long id, Model model) {
		Ricetta ricetta =  this.ricettaService.findById(id);
		if(ricetta.getTuttiPathDelleImmagini()!=null) {						//questo if si può togliere se tutte le 
			ricetta.setPathImmagini(ricetta.getTuttiPathDelleImmagini());	//ricette nel db hanno immagini associate
		}
		model.addAttribute("ricetta", ricetta);
		model.addAttribute("listaIngredienti", this.ricettaService.findById(id).getListaIngredienti());

		return "ricetta.html";
	}
	
	/*-------------------------------------------------------------------------------------*/
	/*----------------------------------AGGIUNTA RICETTA-----------------------------------*/
	/*-------------------------------------------------------------------------------------*/

	@GetMapping("/aggiungiRicetta")
	public String showFormAggiungiRicetta(Model model) {
		model.addAttribute("nuovaRicetta", new Ricetta());	
		return "formAggiungiRicetta.html";
	}

	@PostMapping("/aggiungiRicetta")
	public String newRicetta(@Valid @ModelAttribute("nuovaRicetta") Ricetta ricetta, BindingResult bindingResult, Model model) {

		this.ricettaValidator.validateSimple(ricetta, bindingResult);
		if(bindingResult.hasErrors()) {
			return "formAggiungiRicetta.html";
		}
		else {
			this.ricettaService.save(ricetta);
			return "redirect:ricetta/"+ricetta.getId();
		}

	}
	
	/*-------------------------------------------------------------------------------------*/
	/*------------------------AGGIUNTA RICETTA CON CUOCO ASSOCIATO-------------------------*/
	/*-------------------------------------------------------------------------------------*/
	
	@GetMapping("/aggiungiRicettaCompleta")
	public String showFormAggiungiRicettaCompleta(Model model) {
		model.addAttribute("nuovaRicetta", new Ricetta());	
		model.addAttribute("cuoco", new Cuoco());
		this.aggiungiAttributiCuochi(model);
		return "formAggiungiRicettaCompleta.html";
	}

	@PostMapping("/aggiungiRicettaCompleta")
	public String newRicettaCompleta(@Valid @ModelAttribute("nuovaRicetta") Ricetta ricetta, BindingResult bindingResult, 
			@ModelAttribute("cuoco") Cuoco cuoco, Model model) {

		Cuoco cuocoAssociato = this.cuocoService.findByNomeAndCognomeAndDataNascita(cuoco.getNome(), cuoco.getCognome(), cuoco.getDataNascita());
		ricetta.setCuoco(cuocoAssociato);

		this.ricettaValidator.validate(ricetta, bindingResult);
		if(bindingResult.hasErrors()) {
			this.aggiungiAttributiCuochi(model);
			return "formAggiungiRicettaCompleta.html";
		}
		else {
			this.ricettaService.save(ricetta);
			return "redirect:ricetta/"+ricetta.getId();
		}

	}
	
	/*-------------------------------------------------------------------------------------*/	
	/*------------------------------AGGIORNAMENTO RICETTA----------------------------------*/
	/*-------------------------------------------------------------------------------------*/
	
	@GetMapping("/elencoAggiornaRicette")		//non servono validazioni 
	public String showElencoAggiornaIngredienti(Model model) {
		model.addAttribute("ricette", this.ricettaService.findAllByOrderByTitoloAsc());
		return "elencoAggiornaRicette.html";
	}
	
	@GetMapping("/impostaCuocoARicetta/{idRicetta}") 
	public String showImpostaCuocoARicetta(@PathVariable Long idRicetta, Model model) {
		model.addAttribute("listaCuochi", this.cuocoService.findAllByOrderByCognomeAsc());
		model.addAttribute("idRicetta", idRicetta);
		return "elencoCuochiImpostaCuocoARicetta.html";
	}
	
	@GetMapping("/impostaCuocoARicetta/{idRicetta}/{idCuoco}") 
	public String impostaCuocoARicetta(@PathVariable Long idRicetta, @PathVariable Long idCuoco, Model model) {
		Ricetta ricetta = this.ricettaService.findById(idRicetta);
		ricetta.setCuoco(this.cuocoService.findById(idCuoco));
		this.ricettaService.save(ricetta);
		return "redirect:../../ricetta/"+ricetta.getId();
	}
	
	/*-------------------------------------------------------------------------------------*/	
	/*------------------------------CANCELLAZIONE RICETTA----------------------------------*/
	/*-------------------------------------------------------------------------------------*/
	

	@GetMapping("/rimuoviRicetta")
	public String showFormRimuoviRicetta(Model model) {
		model.addAttribute("ricettaDaRimuovere", new Ricetta());
		model.addAttribute("cuoco", new Cuoco());
		this.aggiungiAttributiRicette(model);
		return "formRimuoviRicetta.html";
	}

	@PostMapping("/rimuoviRicetta")
	public String deleteCuoco(@Valid @ModelAttribute("ricettaDaRimuovere") Ricetta ricetta, BindingResult bindingResult, 
			@Valid @ModelAttribute("cuoco") Cuoco cuoco, BindingResult bindingResult2, Model model) {


		Cuoco cuocoAssociato = this.cuocoService.findByNomeAndCognomeAndDataNascita(cuoco.getNome(), cuoco.getCognome(), cuoco.getDataNascita());
		ricetta.setCuoco(cuocoAssociato);			//ricerca del cuoco associato alla ricetta 
		//e setting del cuoco alla ricetta

		this.ricettaValidator.validate(ricetta, bindingResult);				//controllo errori

		if(bindingResult.hasErrors()) {				
			if(bindingResult.getAllErrors().toString().contains("ricetta.duplicata")) {		//se gli errori contengono
				this.ricettaService.delete(ricetta);								//ricetta duplicata, allora è giusto
				return "redirect:elencoRicette";									//e la cancello
			}
			this.aggiungiAttributiRicette(model);		//se c'erano altri errori ridò la form
			return "formRimuoviRicetta.html";
		}

		bindingResult.reject("ricetta.nonEsiste");
		this.aggiungiAttributiRicette(model);		//se non c'erano errori, non avevo trovato nessuna ricetta che corrisponde
		return "formRimuoviRicetta.html";				//quindi dà errore e redirecta

	}

	/*-------------------------------------------------------------------------------------*/	
	/*---------------------------------METODI DI SUPPORTO----------------------------------*/
	/*-------------------------------------------------------------------------------------*/	
	
	public void aggiungiAttributiRicette(Model model) {
		Set<String> titoliRicette = new TreeSet<String>();
		Set<String> nomiCuochi = new TreeSet<String>();
		Set<String> cognomiCuochi = new TreeSet<String>();
		Set<String> dateNascitaCuochi = new TreeSet<String>();
		for(Cuoco c : this.cuocoService.findAllByOrderByCognomeAsc()) {
			nomiCuochi.add(c.getNome());
			cognomiCuochi.add(c.getCognome());
			dateNascitaCuochi.add(c.getDataNascita().toString());
		}
		for (Ricetta r : this.ricettaService.findAllByOrderByTitoloAsc()) {
			titoliRicette.add(r.getTitolo());
		}
		model.addAttribute("nomiCuochi", nomiCuochi);
		model.addAttribute("cognomiCuochi", cognomiCuochi);
		model.addAttribute("dateNascitaCuochi", dateNascitaCuochi);
		model.addAttribute("titoliRicette", titoliRicette);
	}

	public void aggiungiAttributiCuochi(Model model) {
		Set<String> nomiCuochi = new TreeSet<String>();
		Set<String> cognomiCuochi = new TreeSet<String>();
		Set<String> dateNascitaCuochi = new TreeSet<String>();
		for(Cuoco c : this.cuocoService.findAllByOrderByCognomeAsc()) {
			nomiCuochi.add(c.getNome());
			cognomiCuochi.add(c.getCognome());
			dateNascitaCuochi.add(c.getDataNascita().toString());
		}
		model.addAttribute("nomiCuochi", nomiCuochi);
		model.addAttribute("cognomiCuochi", cognomiCuochi);
		model.addAttribute("dateNascitaCuochi", dateNascitaCuochi);
	}
}
