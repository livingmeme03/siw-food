package it.uniroma3.siw.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.validation.CuocoValidator;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.RicettaService;
import jakarta.validation.Valid;

@Controller
public class CuocoController {

	@Autowired
	private CuocoService cuocoService;
	
	@Autowired
	private RicettaService ricettaService;

	@Autowired 
	private CuocoValidator cuocoValidator;
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*------------------------------------------ELENCO CUOCHI------------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/

	@GetMapping("/elencoCuochi")		//non servono validazioni 
	public String showElencoCuochi(Model model) {
		model.addAttribute("cuochi", this.cuocoService.findAllByOrderByCognomeAsc());
		return "elencoCuochi.html";
	}

	/*-------------------------------------------------------------------------------------------------------*/
	/*----------------------------------VISUALIZZAZIONE SINGOLO CUOCO----------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	@GetMapping("/cuoco/{id}")
	public String showCuoco(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cuoco", this.cuocoService.findById(id));
		return "cuoco.html";
	}
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*-------------------------------------------AGGIUNTA CUOCO----------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	@GetMapping("/aggiungiCuoco")
	public String showFormAggiungiCuoco(Model model) {
		model.addAttribute("nuovoCuoco", new Cuoco());
		return "formAggiungiCuoco.html";
	}

	@PostMapping("/aggiungiCuoco")
	public String newCuoco(@Valid @ModelAttribute("nuovoCuoco") Cuoco cuoco, BindingResult bindingResult, Model model) {
		this.cuocoValidator.validate(cuoco, bindingResult);
		if(bindingResult.hasErrors()) {
			return "formAggiungiCuoco.html";
		}
		else {
			this.cuocoService.save(cuoco);
			return "redirect:cuoco/"+cuoco.getId();
		}
	}
	
	/*-------------------------------------------------------------------------------------------------------*/	
	/*------------------------------------------AGGIORNAMENTO CUOCO----------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/

	@GetMapping("/elencoAggiornaCuochi")		//non servono validazioni 
	public String showElencoAggiornaCuochi(Model model) {
		model.addAttribute("cuochi", this.cuocoService.findAllByOrderByCognomeAsc());
		return "elencoAggiornaCuochi.html";
	}
	
	@GetMapping("/modificaRicetteCuoco/{idCuoco}") 
	public String showModificaIngredientiRicetta(@PathVariable Long idCuoco, Model model) {
		List<Ricetta> ricetteDelCuoco = this.cuocoService.findById(idCuoco).getRicette();
		List<Ricetta> ricetteDaAggiungere = (List<Ricetta>) this.ricettaService.findAllByOrderByTitoloAsc();
		ricetteDaAggiungere.removeAll(ricetteDelCuoco);
		model.addAttribute("ricetteDelCuoco", ricetteDelCuoco);
		model.addAttribute("ricetteDaAggiungere", ricetteDaAggiungere);
		model.addAttribute("cuoco", this.cuocoService.findById(idCuoco));
		return "elencoRicettePerModificareCuoco.html";
	}
	
	@GetMapping("/aggiungiRicettaACuoco/{idCuoco}/{idRicetta}")
	public String aggiungiRicettaACuoco(@PathVariable Long idCuoco, @PathVariable Long idRicetta, Model model) {
		Ricetta ricetta = this.ricettaService.findById(idRicetta);
		Cuoco cuoco = this.cuocoService.findById(idCuoco);
		ricetta.setCuoco(cuoco);
		cuoco.getRicette().add(ricetta);
		this.ricettaService.save(ricetta);
		this.cuocoService.save(cuoco);
		return "redirect:/modificaRicetteCuoco/" + idCuoco;
	}
	
	@GetMapping("/rimuoviRicettaDaCuoco/{idCuoco}/{idRicetta}") 
	public String rimuoviRicettaDaCuoco(@PathVariable Long idCuoco, @PathVariable Long idRicetta, Model model) {
		Ricetta ricetta = this.ricettaService.findById(idRicetta);
		Cuoco cuoco = this.cuocoService.findById(idCuoco);
		ricetta.setCuoco(null);
		cuoco.getRicette().remove(ricetta);
		this.ricettaService.save(ricetta);
		this.cuocoService.save(cuoco);
		return "redirect:/modificaRicetteCuoco/" + idCuoco;
	}
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------RICERCA CUOCO------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/

	@GetMapping("/cercaCuocoPerCognome")
	public String showFormSearchCuoco(Model model) {
		return "formCercaCuoco.html";
	}
	
	@PostMapping("/cercaCuocoPerCognome")
	public String showCuochiTrovati(Model model, @RequestParam String cognome) {
		model.addAttribute("cuochi", this.cuocoService.findByCognome(cognome));
		return "/elencoCuochiTrovati.html";
	}	
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*-------------------------------------------CANCELLAZIONE CUOCO-----------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/

	@GetMapping("/rimuoviCuoco")
	public String showFormRimuoviCuoco(Model model) {
		model.addAttribute("cuocoDaRimuovere", new Cuoco());
		this.aggiungiAttributiCuochi(model);
		return "formRimuoviCuoco.html";
	}

	@PostMapping("/rimuoviCuoco")
	public String deleteCuoco(@Valid @ModelAttribute("cuocoDaRimuovere") Cuoco cuoco, BindingResult bindingResult, Model model) {

		this.cuocoValidator.validate(cuoco, bindingResult);		//verifico errori

		if(bindingResult.hasErrors()) {				
			if(bindingResult.getAllErrors().toString().contains("cuoco.duplicato")) {	//se gli errori contengono
				this.cuocoService.delete(cuoco);								//ingrediente duplicato, allora è giusto
				return "redirect:elencoCuochi";									//e lo cancello
			}
			this.aggiungiAttributiCuochi(model);		//se c'erano altri errori ridò la form
			return "formRimuoviCuoco.html";
		}

		bindingResult.reject("cuoco.nonEsiste");
		this.aggiungiAttributiCuochi(model);		//se non c'erano errori, non avevo trovato nessun ingrediente che corrisponde
		return "formRimuoviCuoco.html";	

	}
	
	/*-------------------------------------------------------------------------------------------------------*/	
	/*---------------------------------------------METODI DI SUPPORTO----------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	public void aggiungiAttributiCuochi(Model model) {
		List<String> nomiCuochi = new ArrayList<String>();
		List<String> cognomiCuochi = new ArrayList<String>();
		List<String> dateNascitaCuochi = new ArrayList<String>();
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
