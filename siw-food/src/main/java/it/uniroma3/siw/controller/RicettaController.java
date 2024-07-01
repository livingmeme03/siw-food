package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.validation.RicettaValidator;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.IngredienteService;
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

	@Autowired
	private IngredienteService ingredienteService;
	
	@Autowired
	private AuthenticationController authenticationController;

	/*-------------------------------------------------------------------------------------------------------*/
	/*-----------------------------------------ELENCO RICETTE------------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/

	//Per tutti
	@GetMapping("/elencoRicette")		//non servono validazioni 
	public String showElencoRicette(Model model) {
		model.addAttribute("ricette", this.ricettaService.findAllByOrderByTitoloAsc());
		return "elencoRicette.html";
	}

	/*-------------------------------------------------------------------------------------------------------*/
	/*----------------------------------VISUALIZZAZIONE SINGOLA RICETTA--------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/

	//Per tutti
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

	/*-------------------------------------------------------------------------------------------------------*/
	/*------------------------------------------AGGIUNTA RICETTA---------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	/*-----------------------------------------------CUOCO---------------------------------------------------*/
	
	//Per cuoco 
	@GetMapping("/aggiungiRicetta")
	public String showFormAggiungiRicetta(Model model) {
		model.addAttribute("nuovaRicetta", new Ricetta());	
		return "formAggiungiRicetta.html";
	}

	//Per cuoco
	@PostMapping("/aggiungiRicetta")
	public String newRicetta(@Valid @ModelAttribute("nuovaRicetta") Ricetta ricetta, BindingResult bindingResult, 
			Model model) {

		this.ricettaValidator.validateSimple(ricetta, bindingResult);
		if(bindingResult.hasErrors()) {
			return "formAggiungiRicetta.html";
		}
		else {
			Cuoco curr = this.authenticationController.getCuocoSessioneCorrente();	//prendi il cuoco della sessione corrente
			ricetta.setCuoco(curr);					//(aka io) e mettimi come proprietario della ricetta
			this.ricettaService.save(ricetta);
			return "redirect:ricetta/"+ricetta.getId();
		}

	}
	
	/*-----------------------------------------------ADMIN---------------------------------------------------*/

	//Per admin
	@GetMapping("/admin/aggiungiRicetta")
	public String showFormAggiungiRicettaAdmin(Model model) {
		model.addAttribute("nuovaRicetta", new Ricetta());	
		return "/admin/formAggiungiRicetta.html";
	}

	//Per admin
	@PostMapping("/admin/aggiungiRicetta")
	public String newRicettaAdmin(@Valid @ModelAttribute("nuovaRicetta") Ricetta ricetta, BindingResult bindingResult, 
			Model model) {

		this.ricettaValidator.validateSimple(ricetta, bindingResult);
		if(bindingResult.hasErrors()) {
			return "/admin/formAggiungiRicetta.html";
		}
		else {
			this.ricettaService.save(ricetta);
			return "redirect:/ricetta/"+ricetta.getId();
		}

	}

	/*-------------------------------------------------------------------------------------------------------*/
	/*---------------------------------AGGIUNTA RICETTA CON CUOCO ASSOCIATO----------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/

	//Per admin
	@GetMapping("/admin/aggiungiRicettaCompleta")
	public String showFormAggiungiRicettaCompleta(Model model) {
		model.addAttribute("nuovaRicetta", new Ricetta());	
		model.addAttribute("cuoco", new Cuoco());
		this.aggiungiAttributiCuochi(model);
		return "/admin/formAggiungiRicettaCompleta.html";
	}

	//Per admin
	@PostMapping("/admin/aggiungiRicettaCompleta")
	public String newRicettaCompleta(@Valid @ModelAttribute("nuovaRicetta") Ricetta ricetta, BindingResult bindingResult, 
			@ModelAttribute("cuoco") Cuoco cuoco, Model model) {

		Cuoco cuocoAssociato = this.cuocoService.findByNomeAndCognomeAndDataNascita(cuoco.getNome(), cuoco.getCognome(), 
				cuoco.getDataNascita());
		ricetta.setCuoco(cuocoAssociato);

		this.ricettaValidator.validate(ricetta, bindingResult);
		if(bindingResult.hasErrors()) {
			this.aggiungiAttributiCuochi(model);
			return "/admin/formAggiungiRicettaCompleta.html";
		}
		else {
			this.ricettaService.save(ricetta);
			return "redirect:/ricetta/"+ricetta.getId();
		}

	}

	/*-------------------------------------------------------------------------------------------------------*/	
	/*------------------------------------------AGGIORNAMENTO RICETTA----------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/

	/*-----------------------------------------------CUOCO---------------------------------------------------*/
	
	//Per cuoco
	@GetMapping("/elencoAggiornaRicette")		//non servono validazioni 
	public String showElencoAggiornaRicette(Model model) {
		Cuoco curr = this.authenticationController.getCuocoSessioneCorrente();
		model.addAttribute("ricette", this.ricettaService.findAllByCuocoOrderByTitoloAsc(curr));
		return "elencoAggiornaRicette.html";
	}

	/*-----------------------------------------------ADMIN---------------------------------------------------*/
	
	//Per admin 
	@GetMapping("/admin/elencoAggiornaRicette")		//non servono validazioni 
	public String showElencoAggiornaRicetteAdmin(Model model) {
		model.addAttribute("ricette", this.ricettaService.findAllByOrderByTitoloAsc());
		return "/admin/elencoAggiornaRicette.html";
	}

	/*------------------------------------------IMPOSTAZIONE CUOCO-------------------------------------------*/

	//Per admin
	@GetMapping("/admin/impostaCuocoARicetta/{idRicetta}") 
	public String showImpostaCuocoARicetta(@PathVariable Long idRicetta, Model model) {
		model.addAttribute("listaCuochi", this.cuocoService.findAllByOrderByCognomeAsc());
		model.addAttribute("idRicetta", idRicetta);
		return "/admin/elencoCuochiImpostaCuocoARicetta.html";
	}

	//Per admin
	@GetMapping("/admin/impostaCuocoARicetta/{idRicetta}/{idCuoco}") 
	public String impostaCuocoARicetta(@PathVariable Long idRicetta, @PathVariable Long idCuoco, Model model) {
		Ricetta ricetta = this.ricettaService.findById(idRicetta);
		ricetta.setCuoco(this.cuocoService.findById(idCuoco));
		this.ricettaService.save(ricetta);
		return "redirect:/ricetta/"+ricetta.getId();
	}

	/*------------------------------------------MODIFICA INGREDIENTI----------------------------------------*/

	/*-----------------------------------------------CUOCO---------------------------------------------------*/
	
	//Per cuoco
	@GetMapping("/modificaIngredientiRicetta/{idRicetta}") 
	public String showModificaIngredientiRicetta(@PathVariable Long idRicetta, Model model) {
		Cuoco curr = this.authenticationController.getCuocoSessioneCorrente();
		Ricetta ricetta = this.ricettaService.findById(idRicetta);
		Cuoco cuocoRicetta = ricetta.getCuoco();
		if(curr.equals(cuocoRicetta)) {
			this.setUpPerModificaRicetta(model, idRicetta);
			return "elencoIngredientiPerModificareRicetta.html";
		}
		model.addAttribute("ricette", this.ricettaService.findAllByCuocoOrderByTitoloAsc(curr));
		return "elencoAggiornaRicette.html";
	}

	//Per cuoco 
	@GetMapping("/aggiungiIngredienteARicetta/{idRicetta}/{idIngrediente}")
	public String scegliQuantitàPerIngrediente(@PathVariable Long idRicetta, @PathVariable Long idIngrediente, Model model) {	
		model.addAttribute("idRicetta", idRicetta);
		model.addAttribute("idIngrediente", idIngrediente);
		model.addAttribute("ingrediente", this.ingredienteService.findById(idIngrediente));
		return "formSelezionaQuantitàAggiungiIngredienteARicetta.html";
	}

	//Per cuoco 
	@PostMapping("/aggiungiIngredienteARicetta/{idRicetta}/{idIngrediente}") 
	public String aggiungiIngredientiRicetta(@PathVariable Long idRicetta, @PathVariable Long idIngrediente, @RequestParam Long quantità, Model model) {
		if(quantità>0) {
			this.ingredienteService.saveIngredienteInRicetta(idIngrediente, idRicetta, quantità);
			return "redirect:/modificaIngredientiRicetta/" + idRicetta;
		}
		else {
			this.setUpPerModificaRicetta(model, idRicetta);
			return "elencoIngredientiPerModificareRicettaErrore.html";
		}
	}

	//Per cuoco 
	@GetMapping("/rimuoviIngredientiDaRicetta/{idRicetta}/{idIngrediente}") 
	public String rimuoviIngredientiRicetta(@PathVariable Long idRicetta, @PathVariable Long idIngrediente, Model model) {
		this.ingredienteService.deleteIngredienteInRicetta(idIngrediente, idRicetta);
		return "redirect:/modificaIngredientiRicetta/" + idRicetta;
	}
	
	/*-----------------------------------------------ADMIN---------------------------------------------------*/

	//Per admin
	@GetMapping("/admin/modificaIngredientiRicetta/{idRicetta}") 
	public String showModificaIngredientiRicettaAdmin(@PathVariable Long idRicetta, Model model) {
		this.setUpPerModificaRicetta(model, idRicetta);
		return "/admin/elencoIngredientiPerModificareRicetta.html";
	}

	//Per admin
	@GetMapping("/admin/aggiungiIngredienteARicetta/{idRicetta}/{idIngrediente}")
	public String scegliQuantitàPerIngredienteAdmin(@PathVariable Long idRicetta, @PathVariable Long idIngrediente, Model model) {	
		model.addAttribute("idRicetta", idRicetta);
		model.addAttribute("idIngrediente", idIngrediente);
		model.addAttribute("ingrediente", this.ingredienteService.findById(idIngrediente));
		return "/admin/formSelezionaQuantitàAggiungiIngredienteARicetta.html";
	}

	//Per admin
	@PostMapping("/admin/aggiungiIngredienteARicetta/{idRicetta}/{idIngrediente}") 
	public String aggiungiIngredientiRicettaAdmin(@PathVariable Long idRicetta, @PathVariable Long idIngrediente, @RequestParam Long quantità, Model model) {
		if(quantità>0) {
			this.ingredienteService.saveIngredienteInRicetta(idIngrediente, idRicetta, quantità);
			return "redirect:/admin/modificaIngredientiRicetta/" + idRicetta;
		}
		else {
			this.setUpPerModificaRicetta(model, idRicetta);
			return "/admin/elencoIngredientiPerModificareRicettaErrore.html";
		}
	}

	//Per admin
	@GetMapping("/admin/rimuoviIngredientiDaRicetta/{idRicetta}/{idIngrediente}") 
	public String rimuoviIngredientiRicettaAdmin(@PathVariable Long idRicetta, @PathVariable Long idIngrediente, Model model) {
		this.ingredienteService.deleteIngredienteInRicetta(idIngrediente, idRicetta);
		return "redirect:/admin/modificaIngredientiRicetta/" + idRicetta;
	}

	/*-------------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------RICERCA RICETTA------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/

	//Per tutti
	@GetMapping("/cercaRicettaPerTitolo")
	public String showFormSearchRicetta(Model model) {
		return "formCercaRicetta.html";
	}

	//Per tutti
	@PostMapping("/cercaRicettaPerTitolo")
	public String showRicetteTrovate (@RequestParam String titolo, Model model) {
		model.addAttribute("ricetta", this.ricettaService.findByTitolo(titolo));
		return "elencoRicetteTrovate.html";
	}

	/*-------------------------------------------------------------------------------------------------------*/
	/*---------------------------------------------CANCELLAZIONE RICETTA-------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	/*-----------------------------------------------ADMIN---------------------------------------------------*/
	
	@GetMapping("/admin/rimuoviRicetta")
	public String showFormRimuoviRicettaAdmin(Model model) {
		model.addAttribute("ricettaDaRimuovere", new Ricetta());
		this.aggiungiAttributiRicetteDaRimuovere(model);
		return "/admin/formRimuoviRicetta.html";
	}
	
	@PostMapping("/admin/rimuoviRicetta")
	public String deleteRicettaAdmin(@Valid @ModelAttribute("ricettaDaRimuovere") Ricetta ricetta, BindingResult bindingResult, Model model) {

		this.ricettaValidator.validateSimple(ricetta, bindingResult);				//controllo errori

		if(bindingResult.hasErrors()) {				
			if(bindingResult.getAllErrors().toString().contains("ricetta.duplicata")) {		//se gli errori contengono
				this.ricettaService.delete(this.ricettaService.findByTitolo(ricetta.getTitolo()));								//ricetta duplicata, allora è giusto
				System.out.println("CE L'HO FATTA MAMMA");
				return "redirect:/elencoRicette";									//e la cancello
			}
			this.aggiungiAttributiRicetteDaRimuovere(model);					//se c'erano altri errori ridò la form
			System.out.println("ALTRI ERRORI");
			return "/admin/formRimuoviRicetta.html";
		}

		bindingResult.reject("ricetta.nonEsiste");
		System.out.println("RICETTA NON ESISTE");
		this.aggiungiAttributiRicetteDaRimuovere(model);		//se non c'erano errori, non avevo trovato nessuna ricetta che corrisponde
		return "/admin/formRimuoviRicetta.html";		//quindi dà errore e redirecta

	}
	
	/*-----------------------------------------------CUOCO---------------------------------------------------*/
	
	@GetMapping("/rimuoviRicetta")
	public String showFormRimuoviRicetta(Model model) {
		model.addAttribute("ricettaDaRimuovere", new Ricetta());
		Cuoco curr = this.authenticationController.getCuocoSessioneCorrente();
		this.aggiungiAttributiRicetteDaRimuovereConCuoco(model, curr);
		return "formRimuoviRicetta.html";
	}
	
	//Per admin e cuoco //TODO DA SDOPPIARE CON CONTROLLI SU CUOCO
	@PostMapping("/rimuoviRicetta")
	public String deleteRicetta(@Valid @ModelAttribute("ricettaDaRimuovere") Ricetta ricetta, BindingResult bindingResult, Model model) {
		Cuoco curr = this.authenticationController.getCuocoSessioneCorrente();
		this.ricettaValidator.validateSimple(ricetta, bindingResult);				//controllo errori

		if(bindingResult.hasErrors()) {				
			if(bindingResult.getAllErrors().toString().contains("ricetta.duplicata")) {		//se gli errori contengono
				this.ricettaService.delete(this.ricettaService.findByTitolo(ricetta.getTitolo()));								//ricetta duplicata, allora è giusto
				return "redirect:elencoRicette";									//e la cancello
			}
			this.aggiungiAttributiRicetteDaRimuovereConCuoco(model, curr);					//se c'erano altri errori ridò la form
			return "formRimuoviRicetta.html";
		}

		bindingResult.reject("ricetta.nonEsiste");
		this.aggiungiAttributiRicetteDaRimuovereConCuoco(model, curr);			//se non c'erano errori, non avevo trovato nessuna ricetta che corrisponde
		return "formRimuoviRicetta.html";		//quindi dà errore e redirecta

	}

	/*-------------------------------------------------------------------------------------------------------*/	
	/*---------------------------------------------METODI DI SUPPORTO----------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/

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

	public void setUpPerModificaRicetta(Model model, Long idRicetta) {
		List<Ingrediente> listaIngredientiDaAggiungere = (List<Ingrediente>)this.ingredienteService.findAllByOrderByNomeAsc();
		Ricetta ricetta = this.ricettaService.findById(idRicetta);
		Set<Ingrediente> lista = ricetta.getIngredienti().keySet();
		List<Ingrediente> listaIngredientiPresenti = new ArrayList<Ingrediente>();
		listaIngredientiPresenti.addAll(lista);
		listaIngredientiDaAggiungere.removeAll(listaIngredientiPresenti);
		model.addAttribute("listaIngredientiPresenti", listaIngredientiPresenti);
		model.addAttribute("listaIngredientiDaAggiungere", listaIngredientiDaAggiungere);
		model.addAttribute("idRicetta", idRicetta);
		model.addAttribute("ricetta", this.ricettaService.findById(idRicetta));
	}
	
	public void aggiungiAttributiRicetteDaRimuovere(Model model) {
		Set<String> titoliRicette = new TreeSet<String>();
		for (Ricetta r : this.ricettaService.findAllByOrderByTitoloAsc()) {
			titoliRicette.add(r.getTitolo());
		}
		model.addAttribute("titoliRicette", titoliRicette);
	}
	
	public void aggiungiAttributiRicetteDaRimuovereConCuoco(Model model, Cuoco cuoco) {
		Set<String> titoliRicetteCuoco = new TreeSet<String>();
		for (Ricetta r : this.ricettaService.findAllByCuocoOrderByTitoloAsc(cuoco)) {
			titoliRicetteCuoco.add(r.getTitolo());
		}
		model.addAttribute("titoliRicetteCuoco", titoliRicetteCuoco);
	}
}
