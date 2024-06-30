package it.uniroma3.siw.controller;

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

import it.uniroma3.siw.controller.validation.IngredienteValidator;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.service.IngredienteService;
import jakarta.validation.Valid;

@Controller
public class IngredienteController {

	@Autowired
	private IngredienteService ingredienteService;

	@Autowired
	private IngredienteValidator ingredienteValidator;
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*----------------------------------------ELENCO INGREDIENTI---------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/

	@GetMapping("/elencoIngredienti")		//non servono validazioni 
	public String showElencoIngredienti(Model model) {
		model.addAttribute("ingredienti", this.ingredienteService.findAllByOrderByNomeAsc());
		return "elencoIngredienti.html";
	}
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*--------------------------------VISUALIZZAZIONE SINGOLO INGREDIENTE------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/

	@GetMapping("/ingrediente/{id}")
	public String showIngrediente(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ingrediente", this.ingredienteService.findById(id));
		return "ingrediente.html";
	}
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*---------------------------------------AGGIUNTA INGREDIENTE--------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/

	@GetMapping("/aggiungiIngrediente")
	public String showFormAggiungiIngrediente(Model model) {
		model.addAttribute("nuovoIngrediente", new Ingrediente());
		return "formAggiungiIngrediente.html";
	}

	@PostMapping("/aggiungiIngrediente")
	public String newIngrediente(@Valid @ModelAttribute("nuovoIngrediente") Ingrediente ingrediente, BindingResult bindingResult, Model model) {
		this.ingredienteValidator.validate(ingrediente, bindingResult);
		if(bindingResult.hasErrors()) {
			return "formAggiungiIngrediente.html";
		}
		else {
			this.ingredienteService.save(ingrediente);
			return "redirect:ingrediente/"+ingrediente.getId();
		}
	}
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*-----------------------------------------RICERCA INGREDIENTE-------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/

//	@GetMapping("/cercaIngredientePerNome")
//	public String showFormSearchIngrediente(Model model) {
//		return "formCercaIngredienti.html";
//	}
//	
//	@PostMapping("/cercaIngredientePerNome")
//	public String showIngredientiTrovati(Model model, @RequestParam String nome) {
//		Ingrediente ingredienteTrovato = this.ingredienteService.findByNome(nome);
//		model.addAttribute("ingrediente", ingredienteTrovato);
//		return "redirect:../ingrediente/"+ingredienteTrovato.getId();
//	}	
	
	/*-------------------------------------------------------------------------------------------------------*/
	/*-------------------------------------CANCELLAZIONE INGREDIENTE-----------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	@GetMapping("/rimuoviIngrediente")
	public String showFormRimuoviIngrediente(Model model) {
		model.addAttribute("ingredienteDaRimuovere", new Ingrediente());
		this.aggiungiAttributiIngrediente(model);
		return "formRimuoviIngrediente.html";
	}
	
	@PostMapping("/rimuoviIngrediente")
	public String deleteIngrediente(@Valid @ModelAttribute("ingredienteDaRimuovere") Ingrediente ingrediente, BindingResult bindingResult, Model model) {
		
		Ingrediente ingredienteDaRimuovere = this.ingredienteService.findByNome(ingrediente.getNome());
		Long trovato = this.ingredienteService.findIngredienteInRicette(ingredienteDaRimuovere.getId());
			
		this.ingredienteValidator.validate(ingrediente, bindingResult);		//verifico errori
		
		if(bindingResult.hasErrors()) {				
			if(bindingResult.getAllErrors().toString().contains("ingrediente.duplicato")) {	//ingrediente duplicato, allora è giusto
				if(trovato == null) {		//non è in nessuna ricetta, lo cancello willy-nilly
					this.ingredienteService.delete(ingrediente);						
					return "redirect:elencoIngredienti";
				}
				else {			//è in qualche ricetta, devo prima toglierlo da quelle e poi lo elimino
					this.ingredienteService.deleteIngredienteInAllRicette(trovato);
					this.ingredienteService.delete(ingrediente);								
					return "redirect:elencoIngredienti";
				}										
			}
			this.aggiungiAttributiIngrediente(model);		//se c'erano altri errori ridò la form
			return "formRimuoviIngrediente.html";
		}
		
		bindingResult.reject("ingrediente.nonEsiste");
		this.aggiungiAttributiIngrediente(model);		//se non c'erano errori, non avevo trovato nessun ingrediente che corrisponde
		return "formRimuoviIngrediente.html";	
		
	}
	
	/*-------------------------------------------------------------------------------------------------------*/	
	/*---------------------------------------------METODI DI SUPPORTO----------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------*/
	
	public void aggiungiAttributiIngrediente(Model model) {
		Set<String> nomiIngredienti = new TreeSet<String>();
		for(Ingrediente i : this.ingredienteService.findAllByOrderByNomeAsc()) {
			nomiIngredienti.add(i.getNome());
		}
		model.addAttribute("nomiIngredienti", nomiIngredienti);
	}
	
}
