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
	
	/*=======================================================================================================*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*----------------------------------------SERVICE E VALIDATOR--------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*=======================================================================================================*/

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
	
	/*=======================================================================================================*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*-----------------------------------------ELENCO RICETTE------------------------------------------------*/
	/*-------------------------------------------(Per tutti)-------------------------------------------------*/
	/*=======================================================================================================*/
	
	@GetMapping("/elencoRicette")		//non servono validazioni 
	public String showElencoRicette(Model model) {
		model.addAttribute("ricette", this.ricettaService.findAllByOrderByTitoloAsc());
		return "elencoRicette.html";
	}
	
	/*=======================================================================================================*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*----------------------------------VISUALIZZAZIONE SINGOLA RICETTA--------------------------------------*/
	/*-------------------------------------------(Per tutti)-------------------------------------------------*/
	/*=======================================================================================================*/
	
	@GetMapping("/ricetta/{id}") 
	public String showRicetta(@PathVariable("id") Long id, Model model) {
		Ricetta ricetta =  this.ricettaService.findById(id);
		if(ricetta != null && ricetta.getTuttiPathDelleImmagini()!=null) {	//questo if si può togliere se tutte le 
			ricetta.setPathImmagini(ricetta.getTuttiPathDelleImmagini());	//ricette nel db hanno immagini associate
		}
		model.addAttribute("ricetta", ricetta);
		
		if(ricetta != null) {		//controllo per chi inserisce un id che non esiste nel path
			model.addAttribute("listaIngredienti", ricetta.getListaIngredienti());
		}
		return "ricetta.html";
	}
	
	/*=======================================================================================================*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*------------------------------------------AGGIUNTA RICETTA---------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*=======================================================================================================*/
	
	/*-----------------------------------------------CUOCO---------------------------------------------------*/
	
	@GetMapping("/aggiungiRicetta")
	public String showFormAggiungiRicetta(Model model) {
		model.addAttribute("nuovaRicetta", new Ricetta());	
		return "formAggiungiRicetta.html";
	}

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

	@GetMapping("/admin/aggiungiRicetta")
	public String showFormAggiungiRicettaAdmin(Model model) {
		model.addAttribute("nuovaRicetta", new Ricetta());	
		return "/admin/formAggiungiRicetta.html";
	}

	@PostMapping("/admin/aggiungiRicetta")
	public String newRicettaAdmin(@Valid @ModelAttribute("nuovaRicetta") Ricetta ricetta, BindingResult bindingResult, 
			Model model) {

		this.ricettaValidator.validateSimple(ricetta, bindingResult);	//controllo errori
		if(bindingResult.hasErrors()) {
			return "/admin/formAggiungiRicetta.html";
		}
		else {
			this.ricettaService.save(ricetta);			//se non ci sono errori salva la ricetta
			return "redirect:/ricetta/"+ricetta.getId();
		}

	}
	
	/*=======================================================================================================*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*---------------------------------AGGIUNTA RICETTA CON CUOCO ASSOCIATO----------------------------------*/
	/*--------------------------------------------(Per admin)------------------------------------------------*/
	/*=======================================================================================================*/

	@GetMapping("/admin/aggiungiRicettaCompleta")
	public String showFormAggiungiRicettaCompleta(Model model) {
		model.addAttribute("nuovaRicetta", new Ricetta());	
		model.addAttribute("cuoco", new Cuoco());
		this.aggiungiAttributiCuochi(model);		//vedi metodi di supporto
		return "/admin/formAggiungiRicettaCompleta.html";
	}

	@PostMapping("/admin/aggiungiRicettaCompleta")
	public String newRicettaCompleta(@Valid @ModelAttribute("nuovaRicetta") Ricetta ricetta, BindingResult bindingResult, 
			@ModelAttribute("cuoco") Cuoco cuoco, Model model) {

		Cuoco cuocoAssociato = this.cuocoService.findByNomeAndCognomeAndDataNascita(cuoco.getNome(), cuoco.getCognome(), 
				cuoco.getDataNascita());			//cerco il cuoco associato alla ricetta e glielo associo
		ricetta.setCuoco(cuocoAssociato);

		this.ricettaValidator.validate(ricetta, bindingResult);
		if(bindingResult.hasErrors()) {
			this.aggiungiAttributiCuochi(model);	//vedi metodi di supporto
			return "/admin/formAggiungiRicettaCompleta.html";
		}
		else {
			this.ricettaService.save(ricetta);
			return "redirect:/ricetta/"+ricetta.getId();
		}

	}
	
	/*=======================================================================================================*/
	/*-------------------------------------------------------------------------------------------------------*/	
	/*------------------------------------------AGGIORNAMENTO RICETTA----------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*=======================================================================================================*/
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*----------------------------------------VISUALIZZA ELENCO RICETTE--------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	/*-----------------------------------------------CUOCO---------------------------------------------------*/

	@GetMapping("/elencoAggiornaRicette")		//non servono validazioni 
	public String showElencoAggiornaRicette(Model model) {
		Cuoco curr = this.authenticationController.getCuocoSessioneCorrente();
		model.addAttribute("ricette", this.ricettaService.findAllByCuocoOrderByTitoloAsc(curr)); //cerco tutte 
		return "elencoAggiornaRicette.html";				//le ricette del cuoco della sessione corrente
	}
	
	/*-----------------------------------------------ADMIN---------------------------------------------------*/

	@GetMapping("/admin/elencoAggiornaRicette")		//non servono validazioni 
	public String showElencoAggiornaRicetteAdmin(Model model) {
		model.addAttribute("ricette", this.ricettaService.findAllByOrderByTitoloAsc());		//cerco tutte
		return "/admin/elencoAggiornaRicette.html";			//le ricette del cuoco della sessione corrente
	}
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*------------------------------------------IMPOSTAZIONE CUOCO-------------------------------------------*/
	/*---------------------------------------------(Per admin)-----------------------------------------------*/

	@GetMapping("/admin/impostaCuocoARicetta/{idRicetta}") 
	public String showImpostaCuocoARicetta(@PathVariable Long idRicetta, Model model) {
		if(this.ricettaService.findById(idRicetta) == null) {	//controllo per chi inserisce id strani nel path
			return "redirect:/admin/elencoAggiornaRicette";
		}
		model.addAttribute("listaCuochi", this.cuocoService.findAllByOrderByCognomeAsc());
		model.addAttribute("idRicetta", idRicetta);
		return "/admin/elencoCuochiImpostaCuocoARicetta.html";
	}

	@GetMapping("/admin/impostaCuocoARicetta/{idRicetta}/{idCuoco}") 
	public String impostaCuocoARicetta(@PathVariable Long idRicetta, @PathVariable Long idCuoco, Model model) {
		Ricetta ricetta = this.ricettaService.findById(idRicetta);
		Cuoco cuoco = this.cuocoService.findById(idCuoco);
		if(ricetta == null) {			//controllo per chi inserisce id strani nel path
			return "redirect:/admin/elencoAggiornaRicette";
		}
		if(cuoco == null) {				//controllo per chi inserisce id strani nel path
			return "redirect:/admin/impostaCuocoARicetta/" + idRicetta;
		}
		ricetta.setCuoco(cuoco);
		this.ricettaService.save(ricetta);
		return "redirect:/ricetta/"+ricetta.getId();
	}
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*-----------------------------------------MODIFICA INGREDIENTI------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	/*-----------------------------------------------CUOCO---------------------------------------------------*/
	
	@GetMapping("/modificaIngredientiRicetta/{idRicetta}") 
	public String showModificaIngredientiRicetta(@PathVariable Long idRicetta, Model model) {
		Cuoco curr = this.authenticationController.getCuocoSessioneCorrente();
		Ricetta ricetta = this.ricettaService.findById(idRicetta);
		if(ricetta == null) {			//controllo per chi inserisce id strani nel path
			return "redirect:/elencoAggiornaRicette";
		}
		Cuoco cuocoRicetta = ricetta.getCuoco();
		if(curr.equals(cuocoRicetta)) {			//se il cuoco della ricetta è il cuoco della sessione corrente(io)
			this.setUpPerModificaRicetta(model, idRicetta);		//vedi metodi di supporto
			return "elencoIngredientiPerModificareRicetta.html";
		}
		model.addAttribute("ricette", this.ricettaService.findAllByCuocoOrderByTitoloAsc(curr));
		return "elencoAggiornaRicette.html";	//se non ero io, riportami all'elenco per aggiornare le MIE ricette
	}

	@GetMapping("/aggiungiIngredienteARicetta/{idRicetta}/{idIngrediente}")
	public String scegliQuantitàPerIngrediente(@PathVariable Long idRicetta, @PathVariable Long idIngrediente, Model model) {
		Cuoco curr = this.authenticationController.getCuocoSessioneCorrente();
		Ricetta ricetta = this.ricettaService.findById(idRicetta);
		if(ricetta == null) {			//controllo per chi inserisce id strani nel path
			return "redirect:/elencoAggiornaRicette";
		}
		Cuoco cuocoRicetta = ricetta.getCuoco();
		if(curr.equals(cuocoRicetta)) {		//se il cuoco della ricetta è il cuoco della sessione corrente (io)
			Ingrediente ingrediente = this.ingredienteService.findById(idIngrediente);
			if(ingrediente == null) {			//controllo per chi inserisce id strani nel path
				return "redirect:/modificaIngredientiRicetta/" + idRicetta;
			}
			model.addAttribute("idRicetta", idRicetta);
			model.addAttribute("idIngrediente", idIngrediente);
			model.addAttribute("ingrediente", ingrediente);
			return "formSelezionaQuantitàAggiungiIngredienteARicetta.html";	//tutto ok, ti faccio selezionare la quantità
		}
		model.addAttribute("ricette", this.ricettaService.findAllByCuocoOrderByTitoloAsc(curr));
		return "elencoAggiornaRicette.html";
	}

	@PostMapping("/aggiungiIngredienteARicetta/{idRicetta}/{idIngrediente}") 
	public String aggiungiIngredientiRicetta(@PathVariable Long idRicetta, @PathVariable Long idIngrediente, @RequestParam Long quantità, Model model) {
		if(this.ricettaService.findById(idRicetta)==null) { //controllo per chi inserisce id strani nel path
			return "redirect:/admin/elencoAggiornaRicette";
		}
		if(this.ingredienteService.findById(idIngrediente)==null) { //controllo per chi inserisce id strani nel path
			return "redirect:/admin/modificaIngredientiRicetta" + idRicetta;
		}
		if(quantità>0) {		//la quantità va bene
			this.ingredienteService.saveIngredienteInRicetta(idIngrediente, idRicetta, quantità);
			return "redirect:/modificaIngredientiRicetta/" + idRicetta;		//salvo l'ingrediente nella ricetta
		}
		else {			//la quantità non è valida
			this.setUpPerModificaRicetta(model, idRicetta);
			return "elencoIngredientiPerModificareRicettaErrore.html";		//ridò la form di errore
		}
	}

	@GetMapping("/rimuoviIngredientiDaRicetta/{idRicetta}/{idIngrediente}") 
	public String rimuoviIngredientiRicetta(@PathVariable Long idRicetta, @PathVariable Long idIngrediente, Model model) {
		Cuoco curr = this.authenticationController.getCuocoSessioneCorrente();
		Ricetta ricetta = this.ricettaService.findById(idRicetta);
		if(ricetta == null) { 	//controllo per chi inserisce id strani nel path
			return "redirect:/elencoAggiornaRicette";
		}
		if(this.ingredienteService.findById(idIngrediente)==null) {		//controllo per chi inserisce id strani nel path
			return "redirect:/modificaIngredientiRicetta/" + idRicetta;
		}
		Cuoco cuocoRicetta = ricetta.getCuoco();
		if(curr.equals(cuocoRicetta)) {		//se il cuoco della ricetta è il cuoco della sessione corrente (io)
			this.ingredienteService.deleteIngredienteInRicetta(idIngrediente, idRicetta);
			return "redirect:/modificaIngredientiRicetta/" + idRicetta;
		}
		return "redirect:/modificaIngredientiRicetta/" + idRicetta;
	}
	
	/*-----------------------------------------------ADMIN---------------------------------------------------*/

	@GetMapping("/admin/modificaIngredientiRicetta/{idRicetta}") 
	public String showModificaIngredientiRicettaAdmin(@PathVariable Long idRicetta, Model model) {
		if(this.ricettaService.findById(idRicetta)==null) {		//controllo per chi inserisce id strani nel path
			return "redirect:/admin/elencoAggiornaRicette";
		}
		this.setUpPerModificaRicetta(model, idRicetta);
		return "/admin/elencoIngredientiPerModificareRicetta.html";
	}

	@GetMapping("/admin/aggiungiIngredienteARicetta/{idRicetta}/{idIngrediente}")
	public String scegliQuantitàPerIngredienteAdmin(@PathVariable Long idRicetta, @PathVariable Long idIngrediente, Model model) {
		if(this.ricettaService.findById(idRicetta)==null) {		//controllo per chi inserisce id strani nel path
			return "redirect:/admin/elencoAggiornaRicette";
		}
		if(this.ingredienteService.findById(idIngrediente)==null) {		//controllo per chi inserisce id strani nel path
			return "redirect:/admin/modificaIngredientiRicetta" + idRicetta;
		}
		model.addAttribute("idRicetta", idRicetta);
		model.addAttribute("idIngrediente", idIngrediente);
		model.addAttribute("ingrediente", this.ingredienteService.findById(idIngrediente));
		return "/admin/formSelezionaQuantitàAggiungiIngredienteARicetta.html";	//tutto ok, ti faccio selezionare la quantità
	}

	@PostMapping("/admin/aggiungiIngredienteARicetta/{idRicetta}/{idIngrediente}") 
	public String aggiungiIngredientiRicettaAdmin(@PathVariable Long idRicetta, @PathVariable Long idIngrediente, @RequestParam Long quantità, Model model) {
		if(this.ricettaService.findById(idRicetta)==null) { //controllo per chi inserisce id strani nel path
			return "redirect:/admin/elencoAggiornaRicette";
		}
		if(this.ingredienteService.findById(idIngrediente)==null) { //controllo per chi inserisce id strani nel path
			return "redirect:/admin/modificaIngredientiRicetta" + idRicetta;
		}
		if(quantità>0) {	//la quantità è valida
			this.ingredienteService.saveIngredienteInRicetta(idIngrediente, idRicetta, quantità);
			return "redirect:/admin/modificaIngredientiRicetta/" + idRicetta;
		}
		else {		//la quantità non è valida, ti ridò la form di errore
			this.setUpPerModificaRicetta(model, idRicetta);
			return "/admin/elencoIngredientiPerModificareRicettaErrore.html";
		}
	}

	@GetMapping("/admin/rimuoviIngredientiDaRicetta/{idRicetta}/{idIngrediente}") 
	public String rimuoviIngredientiRicettaAdmin(@PathVariable Long idRicetta, @PathVariable Long idIngrediente, Model model) {
		if(this.ricettaService.findById(idRicetta)==null) { //controllo per chi inserisce id strani nel path
			return "redirect:/admin/elencoAggiornaRicette";
		}
		if(this.ingredienteService.findById(idIngrediente)==null) {	//controllo per chi inserisce id strani nel path
			return "redirect:/admin/modificaIngredientiRicetta" + idRicetta;
		}
		this.ingredienteService.deleteIngredienteInRicetta(idIngrediente, idRicetta);
		return "redirect:/admin/modificaIngredientiRicetta/" + idRicetta;
	}
	
	/*=======================================================================================================*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*-------------------------------------------RICERCA RICETTA---------------------------------------------*/
	/*---------------------------------------------(Per tutti)-----------------------------------------------*/
	/*=======================================================================================================*/
	
	@GetMapping("/cercaRicettaPerTitolo")
	public String showFormSearchRicetta(Model model) {
		return "formCercaRicetta.html";
	}

	@PostMapping("/cercaRicettaPerTitolo")
	public String showRicetteTrovate (@RequestParam String titolo, Model model) {
		model.addAttribute("ricetta", this.ricettaService.findByTitolo(titolo));
		return "elencoRicetteTrovate.html";
	}

	/*=======================================================================================================*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*-----------------------------------------CANCELLAZIONE RICETTA-----------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*=======================================================================================================*/
	
	/*-----------------------------------------------ADMIN---------------------------------------------------*/
	
	@GetMapping("/admin/rimuoviRicetta")
	public String showFormRimuoviRicettaAdmin(Model model) {
		model.addAttribute("ricettaDaRimuovere", new Ricetta());
		this.aggiungiAttributiRicetteDaRimuovere(model);		//setup per menu a tendina
		return "/admin/formRimuoviRicetta.html";
	}
	
	@PostMapping("/admin/rimuoviRicetta")
	public String deleteRicettaAdmin(@Valid @ModelAttribute("ricettaDaRimuovere") Ricetta ricetta, BindingResult bindingResult, Model model) {

		this.ricettaValidator.validateSimple(ricetta, bindingResult);				//controllo errori

		if(bindingResult.hasErrors()) {				
			if(bindingResult.getAllErrors().toString().contains("ricetta.duplicata")) {		//se gli errori contengono
				this.ricettaService.delete(this.ricettaService.findByTitolo(ricetta.getTitolo()));	//ricetta duplicata, allora è giusto
				return "redirect:/elencoRicette";									//e la cancello
			}
			this.aggiungiAttributiRicetteDaRimuovere(model);		//se c'erano altri errori ridò la form
			return "/admin/formRimuoviRicetta.html";
		}

		bindingResult.reject("ricetta.nonEsiste");
		this.aggiungiAttributiRicetteDaRimuovere(model);	//se non c'erano errori, non avevo trovato nessuna ricetta che corrisponde
		return "/admin/formRimuoviRicetta.html";		//quindi dà errore e redirecta

	}
	
	/*-----------------------------------------------CUOCO---------------------------------------------------*/
	
	@GetMapping("/rimuoviRicetta")
	public String showFormRimuoviRicetta(Model model) {
		model.addAttribute("ricettaDaRimuovere", new Ricetta());
		Cuoco curr = this.authenticationController.getCuocoSessioneCorrente();
		this.aggiungiAttributiRicetteDaRimuovereConCuoco(model, curr);	//setup per menu a tendina
		return "formRimuoviRicetta.html";
	}
	
	//Per admin e cuoco //TODO DA SDOPPIARE CON CONTROLLI SU CUOCO
	@PostMapping("/rimuoviRicetta")
	public String deleteRicetta(@Valid @ModelAttribute("ricettaDaRimuovere") Ricetta ricetta, BindingResult bindingResult, Model model) {
		Cuoco curr = this.authenticationController.getCuocoSessioneCorrente();
		this.ricettaValidator.validateSimple(ricetta, bindingResult);		//controllo errori

		if(bindingResult.hasErrors()) {				
			if(bindingResult.getAllErrors().toString().contains("ricetta.duplicata")) {		//se gli errori contengono
				this.ricettaService.delete(this.ricettaService.findByTitolo(ricetta.getTitolo()));		//ricetta duplicata, allora è giusto
				return "redirect:elencoRicette";		//e la cancello
			}
			this.aggiungiAttributiRicetteDaRimuovereConCuoco(model, curr);		//se c'erano altri errori ridò la form
			return "formRimuoviRicetta.html";
		}

		bindingResult.reject("ricetta.nonEsiste");
		this.aggiungiAttributiRicetteDaRimuovereConCuoco(model, curr);	//se non c'erano errori, non avevo trovato nessuna ricetta che corrisponde
		return "formRimuoviRicetta.html";		//quindi dà errore e redirecta

	}
	
	/*=======================================================================================================*/
	/*-------------------------------------------------------------------------------------------------------*/	
	/*-------------------------------------------METODI DI SUPPORTO------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	/*=======================================================================================================*/
	
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
